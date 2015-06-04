package org.tsc.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.bean.Review;
import org.tsc.core.base.PageList;
import org.tsc.dao.IInterimDao;
import org.tsc.dao.IProjectDao;
import org.tsc.dao.ITerminationDao;
import org.tsc.service.IAchievementService;
import org.tsc.service.IInterimService;
import org.tsc.service.IProjectService;
import org.tsc.service.IReviewService;
import org.tsc.service.ITerminationService;
import org.tsc.service.IUserService;

@Controller
public class ProjectReviewController {

	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IReviewService reviewService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IInterimDao interimDao;
	@Autowired
	private ITerminationDao terminationDao;
	@Autowired
	private IInterimService interimService;
	@Autowired
	private ITerminationService terminationService;	
	@Autowired
	private IAchievementService achievementService;
	//显示项目列表
	@RequestMapping(value="/showProjects.htm",method=RequestMethod.GET)
	public ModelAndView showProjects(HttpServletRequest request,HttpServletResponse response,
			int pageNo) {
		String userRole = userService.getUserRole(request, response);
		ModelAndView mView = null;
		if (userRole!=null && userRole.equals("EXPERT")) {
			mView = new ModelAndView("lcjxjd/lcjsjd_projects.html");
			List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
			PageList pageList = new PageList(projectDao);
			StringBuilder sqlBuilder = new StringBuilder(
					"SELECT * FROM tsc_project WHERE deleteStatus=0 ORDER BY addTime DESC");
			pageList.getPageList(sqlBuilder, pageNo, 20);
			mView.addObject("pageList", pageList);
			mView.addObject("projects", pageList.getResult());		
		}
		return mView;
	}
	//显示项目评审页面
	@RequestMapping(value="/showProject.htm",method=RequestMethod.GET)
	public ModelAndView showProject(HttpServletRequest request,HttpServletResponse response,
			Long project_id) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_index_review.html");
		Map<String, Object> project = projectService.queryForMap("select * from tsc_project where id="+project_id);
		Map<String, Object> reviewIdMap = reviewService.queryForMap("select id from tsc_review where project_id="+project_id);
		System.out.println("reviewIdMap  "+reviewIdMap);
		if (reviewIdMap != null) {
			Long reviewId = (Long)reviewIdMap.get("id");
			mv.addObject("review_id", reviewId);
		}
		mv.addObject("project", project);
		return mv;
	}
	
	//保存专家对项目的评审结果
	@RequestMapping(value="/addReview.htm",method=RequestMethod.POST)
	public String addReview(HttpServletRequest request,HttpServletResponse response,
			Review review,Long project_id,Long review_id){
		System.out.println("review _id = "+review_id);
		Long userId = userService.getUserId(request, response);
		if (userId != null) {
			String sqlString = null;
			if (review_id != null) {
				sqlString = "update tsc_review set reviewIndex1='"+review.getReviewIndex1()+"',reviewIndex2='"+review.getReviewIndex2()+"',"
						+ "reviewIndex3='"+review.getReviewIndex3()+"',reviewIndex4='"+review.getReviewIndex4()+"',reviewResult='"+review.getReviewResult()+"',"
						+ "suggestion='"+review.getSuggestion()+"',remark='"+review.getRemark()+"',ranking='"+review.getRanking()+"' where id="+review_id;
				reviewService.update(sqlString);
			}else{
				sqlString = "INSERT INTO tsc_review (addTime,reviewIndex1,reviewIndex2,reviewIndex3,"
						+ "reviewIndex4,reviewResult,suggestion,remark,ranking,project_id,user_id) "
						+ "VALUES (NOW(),'"+review.getReviewIndex1()+"','"+review.getReviewIndex2()+"','"+review.getReviewIndex3()+"',"
						+ "'"+review.getReviewIndex4()+"','"+review.getReviewResult()+"','"+review.getSuggestion()+"',"
						+ "'"+review.getRemark()+"',"+review.getRanking()+","+project_id+",'"+userId+"')";
				reviewService.save(sqlString);			
			}
		}
		return "redirect:index.htm";
	}
	
	//显示立项的项目列表
	@RequestMapping(value="/showProjectsBuilt.htm",method=RequestMethod.GET)
	public ModelAndView showProjectsBuilt(HttpServletRequest request,HttpServletResponse response,
			Integer pageNo,int type) {
		ModelAndView mv = null;
		String userRole = userService.getUserRole(request, response);
		if (userRole != null && userRole.equals("EXPERT")) {
			mv = new ModelAndView("lcjxjd/lcjsjd_projects_built.html");
			List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
			StringBuilder sqlBuilder = null;
			PageList pageList = null;
			if (type == 1) {
				pageList = new PageList(terminationDao);
				sqlBuilder = new StringBuilder(
						"SELECT termination.id,project.name FROM tsc_termination termination inner join tsc_project project"
						+ " on termination.project_id = project.id WHERE termination.deleteStatus=0 ORDER BY termination.addTime DESC");
				pageList.getPageList(sqlBuilder, pageNo, 20);
				mv.addObject("projects", pageList.getResult());		
			}else {
				pageList = new PageList(interimDao);
				sqlBuilder = new StringBuilder(
						"SELECT interim.id,project.name FROM tsc_interim interim inner join tsc_project project"
						+ " on interim.project_id = project.id WHERE interim.deleteStatus=0 ORDER BY interim.addTime DESC");
				pageList.getPageList(sqlBuilder, pageNo, 20);	
				mv.addObject("projects", pageList.getResult());		
			}
			mv.addObject("pageList", pageList);
			mv.addObject("type", type);
		}
		return mv;
	}
	
	//显示立项的项目评审页面
	@RequestMapping(value="showReview.htm",method=RequestMethod.GET)
	public ModelAndView showReview(HttpServletRequest request,HttpServletResponse response,
			Long id,int type){
		ModelAndView mv = null;
		String sql = null;
		String sql1 = null;
		String sql2 = null;
		Map<String, Object> project = null;
		Map<String, Object> review = null;
		List<Map<String, Object>> achievements = null;
		
		if(type == 0){
			mv = new ModelAndView("lcjxjd/lcjsjd_pj_interim_review.html");
			sql = "select interim.id as interim_id,project.name,interim.workSituation from tsc_interim interim "
					+ "inner join tsc_project project on interim.project_id=project.id "
					+ "where interim.id="+id;
			project = interimService.queryForMap(sql);
			sql1 = "select * from tsc_achievement achievement where achievement.interim_id="+id;
			sql2 = "select id,remark,eduRemark from tsc_review where interim_id="+id;
		}else {
			mv = new ModelAndView("lcjxjd/lcjsjd_pj_termination_review.html");
			sql ="select project.name,termination.* from tsc_termination termination inner join tsc_project project on termination.project_id=project.id"
					+ " where termination.id="+id;
			project = terminationService.queryForMap(sql);
			sql1 = "select * from tsc_achievement achievement where achievement.termination_id="+id;
			sql2 = "select id,remark,eduRemark from tsc_review where termination_id="+id;
			
		}
		achievements = achievementService.queryForList(sql1);
		review = reviewService.queryForMap(sql2);
		mv.addObject("project", project);
		mv.addObject("achievements", achievements);
		mv.addObject("review", review);
		return mv;
		
	}
	
	//添加中期检查的专家评审 教育厅评审
	@RequestMapping(value="/addInterimReviews.htm",method=RequestMethod.POST)
	public String addInterimReviews(HttpServletRequest request,HttpServletResponse response,
			String remark,String eduRemark,Long interim_id,Long review_id) {
		Long userId = (Long)request.getSession().getAttribute("userId");
		String sqlString = null;
		if (review_id != null) {
			sqlString = "update tsc_review set remark='"+remark+"',eduRemark='"+eduRemark+"'";
			reviewService.update(sqlString);
		}else {
			sqlString = "insert into tsc_review (addTime,remark,eduRemark,interim_id,user_id) values (NOW(),"
					+ "'"+remark+"','"+eduRemark+"',"+interim_id+","+userId+")";
			reviewService.save(sqlString);		
		}
		return "redirect:index.htm";
	}
	
	//添加结题材料检查的专家评审 教育厅评审
		@RequestMapping(value="/addTerminationReviews.htm",method=RequestMethod.POST)
		public String addTerminationReviews(HttpServletRequest request,HttpServletResponse response,
				String remark,String eduRemark,Long termination_id,Long review_id) {
			Long userId = (Long)request.getSession().getAttribute("userId");
			String sqlString = null;
			if (review_id != null) {
				sqlString = "update tsc_review set remark='"+remark+"',eduRemark='"+eduRemark+"'";
				reviewService.update(sqlString);
			}else {
				sqlString = "insert into tsc_review (addTime,remark,eduRemark,termination_id,user_id) values (NOW(),"
						+ "'"+remark+"','"+eduRemark+"',"+termination_id+","+userId+")";
				reviewService.save(sqlString);		
			}
			return "redirect:index.htm";
		}
}
