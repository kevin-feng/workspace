package org.tsc.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.bean.Achievement;
import org.tsc.bean.Budget;
import org.tsc.bean.Member;
import org.tsc.bean.Termination;
import org.tsc.core.tools.SysUtils;
import org.tsc.service.IAchievementService;
import org.tsc.service.IBudgetService;
import org.tsc.service.IInterimService;
import org.tsc.service.IMemberService;
import org.tsc.service.IProjectService;
import org.tsc.service.ITerminationService;
import org.tsc.service.IUserService;

@Controller
public class ProjectBuildController {

	@Autowired
	private IInterimService interimService;
	@Autowired
	private IAchievementService achievementService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private ITerminationService terminationService;
	@Autowired
	private IBudgetService budgetService;
	
	//显示中期检查表
	@RequestMapping(value="/showInterim.htm",method=RequestMethod.GET)
	public ModelAndView showInterim(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_pj_interim.html");
		String userRole = userService.getUserRole(request, response);
		if (userRole != null && userRole.equals("DECLARER")) {
			Long userId = (Long)request.getSession().getAttribute("userId");
			String sql = "select project.id, project.name, project.organization from tsc_user2project u2p "
					+ "inner join tsc_project project on u2p.project_id=project.id  where user_id="+userId;
			Map<String, Object> project = projectService.queryForMap(sql);
			Long project_id = (Long)project.get("id");
			Map<String, Object> member = memberService.queryForMap("select name,email from tsc_member where type=1 and project_id="+project_id);
			Map<String, Object> interim = interimService.queryForMap("select * from tsc_interim where project_id="+project_id);
			if (interim != null) {
				List<Map<String, Object>> achievements = achievementService.queryForList("select * from tsc_achievement where interim_id="+interim.get("id"));
				mv.addObject("achievements", achievements);		
			}
			mv.addObject("project", project);
			mv.addObject("member", member);
			mv.addObject("interim", interim);
		}
		return mv;
	}
	
	//保存中期检查表
	@RequestMapping(value="/addInterim.htm",method=RequestMethod.POST)
	public String addInterim(HttpServletRequest request,HttpServletResponse response,
			String workSituation,Long project_id,Achievement achievement,String wordNumber,
			Long interim_id,String achievement_id,int size,int trueSize) {
		System.out.println("size="+size+"  truesize="+trueSize);
		String sqlString = null;
		if (interim_id != null) {
			sqlString = "update tsc_interim set workSituation='"+workSituation+"' where id="+interim_id;
			interimService.update(sqlString);
		}else {
			sqlString = "insert into tsc_interim (addTime,workSituation,project_id)"
					+ " values(NOW(),'"+workSituation+"',"+project_id+")";
			interimService.save(sqlString);
		}
		//获取刚插入的中期检查表的id值
		Map<String, Object> map = interimService.queryForMap("select id from tsc_interim where project_id="+project_id);
		Long id = (Long)map.get("id");
		Map<String, Object> map2 = new HashMap<String, Object>();
		//size!=0表示原本已经插入了achievement，现在是更新
		if (size != 0) {
			//if (trueSize > size)表示原本已经有achievement了，现在又插入了几条，同时也可能更改了之前插入的achievement
			if (trueSize > size) {
				map2.put("title", SysUtils.getSubString(achievement.getTitle(), ',', size, 1));
				map2.put("author", SysUtils.getSubString(achievement.getAuthor(), ',', size, 1));
				map2.put("achiForm", SysUtils.getSubString(achievement.getAchiForm(), ',', size, 1));
				map2.put("publication", SysUtils.getSubString(achievement.getPublication(), ',', size, 1));
				map2.put("wordNumber", SysUtils.getSubString(wordNumber, ',', size, 1));
				map2.put("publishTime", SysUtils.getSubString(achievement.getPublishTime(), ',', size, 1));
				map2.put("id", id);
				achievementService.batchSave(map2,0);			
			}
			//if (trueSize <= size)则表示没有新插入数据，则直接更新原来插入的数据
			map2.put("title", SysUtils.getSubString(achievement.getTitle(), ',', size, 0));
			map2.put("author", SysUtils.getSubString(achievement.getAuthor(), ',', size, 0));
			map2.put("achiForm", SysUtils.getSubString(achievement.getAchiForm(), ',', size, 0));
			map2.put("publication", SysUtils.getSubString(achievement.getPublication(), ',', size, 0));
			map2.put("wordNumber", SysUtils.getSubString(wordNumber, ',', size, 0));
			map2.put("publishTime", SysUtils.getSubString(achievement.getPublishTime(), ',', size, 0));
			map2.put("achievement_id", achievement_id);
			achievementService.batchUpdate(map2);
		}
		//否则 则为直接插入achievement
		else {
			map2.put("title", achievement.getTitle());
			map2.put("author", achievement.getAuthor());
			map2.put("achiForm", achievement.getAchiForm());
			map2.put("publication", achievement.getPublication());
			map2.put("wordNumber", wordNumber);
			map2.put("publishTime", achievement.getPublishTime());
			map2.put("id", id);
			achievementService.batchSave(map2,0);		
		}
		return "redirect:index.htm";
	}
	
	//显示结题材料
	@RequestMapping(value="showTermination.htm",method=RequestMethod.GET)
	public ModelAndView showTerminal(HttpServletRequest request,HttpServletResponse response,
			Integer pageNo) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_pj_termination.html");
		String userRole = userService.getUserRole(request, response);
		if (userRole != null && userRole.equals("DECLARER")) {
			Long userId = (Long)request.getSession().getAttribute("userId");
			String sql = "select project.id, project.name, project.organization from tsc_user2project u2p "
					+ "inner join tsc_project project on u2p.project_id=project.id  where user_id="+userId;
			Map<String, Object> project = projectService.queryForMap(sql);
			Long project_id = (Long)project.get("id");
			Map<String, Object> member = memberService.queryForMap("select name,email from tsc_member where type=1 and project_id="+project_id);
			List<Map<String, Object>> otherMembers = memberService.queryForList("select id,name,adminPosition,finishWork from tsc_member where type=0 and project_id="+project_id);
			Map<String, Object> termination = terminationService.queryForMap("select * from tsc_termination where project_id="+project_id);
			if (termination != null) {
				List<Map<String, Object>> budgets = budgetService.queryForList("select * from tsc_budget where termination_id="+termination.get("id"));
				List<Map<String, Object>> achievements = achievementService.queryForList("select * from tsc_achievement where termination_id="+termination.get("id"));
				mv.addObject("budgets", budgets);
				mv.addObject("achievements", achievements);
			}
			mv.addObject("project", project);
			mv.addObject("member", member);
			mv.addObject("otherMembers", otherMembers);
			mv.addObject("termination", termination);
		}
		mv.addObject("pageNo", pageNo);
		return mv;
	}
	
	//保存结题材料
	@RequestMapping(value="/addTermination.htm",method=RequestMethod.POST)
	public String addTermination(HttpServletRequest request,HttpServletResponse response,
			Termination termination,Member member,Long project_id,String member_id,Long termination_id){
		Date planTime = SysUtils.strToDate(termination.getPlanTime());
		Date realTime = SysUtils.strToDate(termination.getRealTime());
		System.out.println("planTime = "+termination.getContent()+"  realtime = "+termination.getStep());
		String[] keys = {"planTime","realTime","appropriation","subsidize","otherFunds","totalFunds",
				"achiForm","content","process","step","result","effect","project_id"};
		Object[] values = {termination.getPlanTime(),termination.getRealTime(),termination.getAppropriation(),
				termination.getSubsidize(),termination.getOtherFunds(),termination.getTotalFunds(),
				termination.getAchiForm(),termination.getContent(),termination.getProcess(),termination.getStep(),
				termination.getResult(),termination.getEffect(),project_id};	
		if (termination_id != null) {
			terminationService.update(keys, values, termination_id);
		}else {
			terminationService.save(keys, values);		
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", member.getName());
		map.put("adminPosition", member.getAdminPosition());
		map.put("finishWork", member.getFinishWork());
		map.put("member_id", member_id);
		memberService.batchUpdate2(map);
		return "redirect:showTermination.htm?pageNo=4";
	}
	
	//保存结题材料的阶段性成果
	@RequestMapping(value="/addAchievements.htm",method=RequestMethod.POST)
	public String addAchievements(HttpServletRequest request,HttpServletResponse response,
			Achievement achievement,String achievement_id,String wordNumber,int size,
			int trueSize,Long termination_id) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		//size!=0表示原本已经插入了achievement，现在是更新
		System.out.println("---"+achievement.getTitle());
		if (size != 0) {
			//if (trueSize > size)表示原本已经有achievement了，现在又插入了几条，同时也可能更改了之前插入的achievement
			if (trueSize > size) {
				map2.put("title", SysUtils.getSubString(achievement.getTitle(), ',', size, 1));
				map2.put("author", SysUtils.getSubString(achievement.getAuthor(), ',', size, 1));
				map2.put("achiForm", SysUtils.getSubString(achievement.getAchiForm(), ',', size, 1));
				map2.put("publication", SysUtils.getSubString(achievement.getPublication(), ',', size, 1));
				map2.put("wordNumber", SysUtils.getSubString(wordNumber, ',', size, 1));
				map2.put("publishTime", SysUtils.getSubString(achievement.getPublishTime(), ',', size, 1));
				map2.put("id", termination_id);
				achievementService.batchSave(map2,1);				
			}
			//if (trueSize <= size)则表示没有新插入数据，则直接更新原来插入的数据
			map2.put("title", SysUtils.getSubString(achievement.getTitle(), ',', size, 0));
			map2.put("author", SysUtils.getSubString(achievement.getAuthor(), ',', size, 0));
			map2.put("achiForm", SysUtils.getSubString(achievement.getAchiForm(), ',', size, 0));
			map2.put("publication", SysUtils.getSubString(achievement.getPublication(), ',', size, 0));
			map2.put("wordNumber", SysUtils.getSubString(wordNumber, ',', size, 0));
			map2.put("publishTime", SysUtils.getSubString(achievement.getPublishTime(), ',', size, 0));
			map2.put("achievement_id", achievement_id);
			achievementService.batchUpdate(map2);
		}
		//否则 则为直接插入achievement
		else {
			map2.put("title", achievement.getTitle());
			map2.put("author", achievement.getAuthor());
			map2.put("achiForm", achievement.getAchiForm());
			map2.put("publication", achievement.getPublication());
			map2.put("wordNumber", wordNumber);
			map2.put("publishTime", achievement.getPublishTime());
			map2.put("id", termination_id);
			achievementService.batchSave(map2,1);		
		}
		return "redirect:showTermination.htm";
	}
	
	//保存结题材料的经费支出情况
	@RequestMapping(value="addExpenditure.htm",method=RequestMethod.POST)
	public String addExpenditure(HttpServletRequest request,HttpServletResponse response,
			String subjectName,String amount,String budget_id,Long termination_id,int size,int trueSize) {
		if (termination_id != null) {
			Map<String, Object> budgetMap = new HashMap<String, Object>();	
			//批量更新及插入经费预算（因为更新的时候用户也可以进行添加操作，适用于曾经添加过，现在需要更新并继续添加的情形）
			if (size != 0) {
				//获取字符串的第size个逗号后面的字符串，即获取那些要进行插入操作的数据
				if (trueSize > size) {
					budgetMap.put("subjectName", SysUtils.getSubString(subjectName, ',', size, 1));
					budgetMap.put("amount", SysUtils.getSubString(amount, ',', size, 1));
					budgetMap.put("id", termination_id);
					System.out.println("budgetMap = " + budgetMap);
					budgetService.batchSave(budgetMap,1);	
				}
				//获取字符串的第size个逗号前面的字符串，即获取那些要进行更新操作的数据
				budgetMap.put("subjectName", SysUtils.getSubString(subjectName, ',', size, 0));
				budgetMap.put("amount", SysUtils.getSubString(amount, ',', size, 0));
				budgetMap.put("budget_id", budget_id);
				System.out.println("budgetMap2 = "+budgetMap);
				budgetService.batchUpdate(budgetMap);		
			}
			//单独批量添加经费预算（这种适用于第一次添加的情形）
			else {
				budgetMap.put("subjectName", subjectName);
				budgetMap.put("amount", amount);
				budgetMap.put("id", termination_id);
				System.out.println("budgetMap = " + budgetMap);
				budgetService.batchSave(budgetMap,1);
			}
		}
		return "redirect:showTermination.htm?pageNo=5";
	}
	//删除结题材料的项目成员的某一项
	@RequestMapping(value="deleteMember.htm",method=RequestMethod.GET)
	public String deleteMember(HttpServletRequest request,HttpServletResponse response,
			Long id) {
		memberService.delete("delete from tsc_member where id="+id);
		return "redirect:showTermination.htm?pageNo=3";
	}
	
	//删除结题材料的经费支出的某一项
	@RequestMapping(value="/deleteExpenditure.htm",method=RequestMethod.GET)
	public String deleteExpenditure(HttpServletRequest request,HttpServletResponse response,
			Long id) {
		budgetService.delete("delete from tsc_budget where id="+id);
		return "redirect:showTermination.htm?pageNo=4";
	}
	
	//删除阶段性成果的某一项
	@RequestMapping(value="/deleAchievement.htm",method=RequestMethod.GET)
	public String deleAchievement(HttpServletRequest request,HttpServletResponse response,
			Long id,int type) {
		achievementService.delete("delete from tsc_achievement where id="+id);
		if (type == 0) {
			return "redirect:showInterim.htm";	
		}else {
			return "redirect:showTermination.htm?pageNo=5";
		}
	}
}
