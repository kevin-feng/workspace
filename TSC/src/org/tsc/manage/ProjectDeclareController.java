package org.tsc.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JComboBox.KeySelectionManager;

import jcifs.util.transport.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.bean.BankAccount;
import org.tsc.bean.Budget;
import org.tsc.bean.Member;
import org.tsc.bean.Project;
import org.tsc.core.tools.SysUtils;
import org.tsc.service.IBankAccountService;
import org.tsc.service.IBudgetService;
import org.tsc.service.IMemberService;
import org.tsc.service.IProjectService;
import org.tsc.service.IUserService;

@Controller
public class ProjectDeclareController {
	
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IBudgetService budgetService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IBankAccountService bankAccountService;
	
	//网上申报项目页面
	@RequestMapping(value="toDeclare.htm",method=RequestMethod.GET)
	public ModelAndView toDeclare(HttpServletRequest request,HttpServletResponse response,
			Integer pageNo) {
		System.out.println("pageNo = "+pageNo);
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_index_declare.html");
		String userRole = userService.getUserRole(request, response);
		if (userRole != null && userRole.equals("DECLARER")) {
			Long userId = (Long)request.getSession(false).getAttribute("userId");
			String sqlString = "SELECT project_id FROM tsc_user2project WHERE user_id="+userId;
			Map<String, Object> map = projectService.queryForMap(sqlString);
			System.out.println("map = "+map);
			if (map != null) {
				Long project_id = (Long)map.get("project_id");
				Map<String, Object> project = projectService.queryForMap("SELECT * FROM tsc_project WHERE id="+project_id);
				List<Map<String, Object>> budgets = budgetService.queryForList("SELECT * FROM tsc_budget WHERE project_id="+project_id);
				Map<String, Object> member = memberService.queryForMap("SELECT * FROM tsc_member WHERE type=1 AND project_id="+project_id);
				List<Map<String, Object>> otherMembers = memberService.queryForList("SELECT * FROM tsc_member WHERE type=0 AND project_id="+project_id);	
				Map<String, Object> account = bankAccountService.queryForMap("SELECT * FROM tsc_bankaccount WHERE project_id="+project_id);
				mv.addObject("project", project);
				mv.addObject("budgets", budgets); 
				mv.addObject("member", member);
				System.out.println("----"+member);
				mv.addObject("otherMembers", otherMembers);
				mv.addObject("account", account);
			}
		}
		mv.addObject("pageNo", pageNo);
		return mv;
	}
	
	//添加项目基本资料
	@RequestMapping(value="/addProject.htm",method=RequestMethod.POST)
	public String addProject(HttpServletRequest request,HttpServletResponse response,
			Project project,Long project_id) {
		Long userId = userService.getUserId(request, response);
		if (userId != null) {
			//更新项目基本资料
			if (project_id != null) {
				String sqlString = "UPDATE tsc_project SET addTime=NOW(),name='"+project.getName()+"',category='"+project.getCategory()+"',"
						+ "organization='"+project.getOrganization()+"',amount='"+project.getAmount()+"',meaning='"+project.getMeaning()+"',"
						+ "content='"+project.getContent()+"',expectedEffect='"+project.getExpectedEffect()+"',schedule='"+project.getSchedule()+"',"
						+ "totalBudget="+project.getTotalBudget()+" WHERE id="+project_id;
				projectService.update(sqlString);
			}
			//添加项目基本资料
			else {
				String sqlString = "INSERT INTO tsc_project (addTime,name,category,organization,"
						+ "amount,meaning,content,expectedEffect,schedule,totalBudget) "
						+ " VALUES (NOW(),'"+project.getName()+"','"+project.getCategory()+"','"+project.getOrganization()+"',"
						+ project.getAmount()+",'"+project.getMeaning()+"','"+project.getContent()+"',"
						+ "'"+project.getExpectedEffect()+"','"+project.getSchedule()+"','"+project.getTotalBudget()+"')";
				projectService.save(sqlString);
				//查询刚插入的project_id
				Long projectId = (Long)projectService.queryForMap("SELECT MAX(id) AS id FROM tsc_project").get("id");
				projectService.save("INSERT INTO tsc_user2project (user_id,project_id) VALUES ("+userId+","+projectId+")");	
			}
		}
		return "redirect:toDeclare.htm?pageNo=2";
	}

	//添加项目组负责人
	@RequestMapping(value="/addMember.htm",method=RequestMethod.POST)
	public String addMember(HttpServletRequest request,HttpServletResponse response,
			Member member,Long member_id) {	
		System.out.println("member = "+member.toString()+"  "+member.getOrganization());
		Long userId = userService.getUserId(request, response);
		if (userId != null) {
			String sqlString = "SELECT project_id FROM tsc_user2project WHERE user_id="+userId;
			Map<String, Object> map = projectService.queryForMap(sqlString);
			if (map != null) {
				//更新项目成员
				if (member_id != null) {
					String sqlString1 = "UPDATE tsc_member SET addTime=NOW(),name='"+member.getName()+"',sex='"+member.getSex()+"',"
							+ "birthday='"+member.getBirthday()+"',mobileNo='"+member.getMobileNo()+"',email='"+member.getEmail()+"',"
							+ "majorPosition='"+member.getMajorPosition()+"',adminPosition='"+member.getAdminPosition()+"',major='"+member.getMajor()+"',"
							+ "resume='"+member.getResume()+"',researchResult='"+member.getResearchResult()+"'"
							+ " WHERE id="+member_id;
					System.out.println(sqlString1);
					memberService.update(sqlString1);
				}
				//添加项目成员
				else {
					String sqlString2 = "INSERT INTO tsc_member (addTime,name,sex,birthday,"
							+ "mobileNo,email,majorPosition,adminPosition,major,resume,researchResult,"
							+ "type,project_id) VALUES (NOW(),'"+member.getName()+"','"+member.getSex()+"',"
							+ "'"+member.getBirthday()+"','"+member.getMobileNo()+"','"+member.getEmail()+"',"
							+ "'"+member.getMajorPosition()+"','"+member.getAdminPosition()+"','"+member.getMajor()+"',"
							+ "'"+member.getResume()+"','"+member.getResearchResult()+"',1,"+map.get("project_id")+")";
					memberService.save(sqlString2);					
				}
			}
		}
		return "redirect:toDeclare.htm?pageNo=3";
	}
	
	//添加项目组其他成员
	@RequestMapping(value="addOtherMember.htm",method=RequestMethod.POST)
	public String addOtherMember(HttpServletRequest request,HttpServletResponse response,
			String name,String mobileNo,String adminPosition,String organization,
			String major,String member_id,Integer size,int trueSize) {
		
		Long userId = userService.getUserId(request, response);
		if (userId != null) {
			String sqlString = "SELECT project_id FROM tsc_user2project WHERE user_id="+ userId;
			Map<String, Object> map = projectService.queryForMap(sqlString);
			if (map != null) {
					Map<String, Object> memberMap = new HashMap<String, Object>();
					memberMap.put("name", name);
					memberMap.put("mobileNo", mobileNo);
					memberMap.put("adminPosition", adminPosition);
					memberMap.put("organization", organization);
					memberMap.put("major", major);
				if (size != 0) {
					if (trueSize > size) {
						memberMap.put("name", SysUtils.getSubString(name, ',', size, 0));
						memberMap.put("mobileNo", SysUtils.getSubString(mobileNo, ',', size, 0));
						memberMap.put("adminPosition", SysUtils.getSubString(adminPosition, ',', size, 0));
						memberMap.put("organization", SysUtils.getSubString(organization, ',', size, 0));
						memberMap.put("major", SysUtils.getSubString(major, ',', size, 0));
						memberMap.put("member_id", member_id);
						memberService.batchUpdate(memberMap);
						
						memberMap.put("name", SysUtils.getSubString(name, ',', size, 1));
						memberMap.put("mobileNo", SysUtils.getSubString(mobileNo, ',', size, 1));
						memberMap.put("adminPosition", SysUtils.getSubString(adminPosition, ',', size, 1));
						memberMap.put("organization", SysUtils.getSubString(organization, ',', size, 1));
						memberMap.put("major", SysUtils.getSubString(major, ',', size, 1));
						memberMap.put("project_id", map.get("project_id"));
						memberService.batchSave(memberMap);
					}else {
						memberMap.put("member_id", member_id);
						memberService.batchUpdate(memberMap);
					}
				}else {
					memberMap.put("project_id", map.get("project_id"));
					memberService.batchSave(memberMap);
				}
			}
		}
		return "redirect:toDeclare.htm?pageNo=4";
	}
	
	//删除项目成员的某一项
	@RequestMapping(value="deleMember.htm",method=RequestMethod.GET)
	public String deleMember(HttpServletRequest request,HttpServletResponse response,
			Long id) {
		memberService.delete("delete from tsc_member where id="+id);
		return "redirect:toDeclare.htm?pageNo=3";
	}
		
	//添加经费预算
	@RequestMapping(value="/addBudget.htm",method=RequestMethod.POST)
	public String addBudget(HttpServletRequest request,HttpServletResponse response,
			String subjectName,String amount,String budget_id,Integer size,int trueSize) { 
		System.out.println("subjectName = "+subjectName+"  amount = "+amount+" budget_id = "+budget_id+" size="+size);
		Long userId = userService.getUserId(request, response);
		if (userId != null) {
				System.out.println(subjectName+"-----"+amount);
				String sqlString = "SELECT project_id FROM tsc_user2project WHERE user_id="+ userId;
				Map<String, Object> map = projectService.queryForMap(sqlString);
				if (map != null) {
					Map<String, Object> budgetMap = new HashMap<String, Object>();	
					//批量更新及插入经费预算（因为更新的时候用户也可以进行添加操作，适用于曾经添加过，现在需要更新并继续添加的情形）
					if (size != 0) {
						//获取字符串的第size个逗号后面的字符串，即获取那些要进行插入操作的数据
						if (trueSize > size) {
							budgetMap.put("subjectName", SysUtils.getSubString(subjectName, ',', size, 1));
							budgetMap.put("amount", SysUtils.getSubString(amount, ',', size, 1));
							budgetMap.put("id", map.get("project_id"));
							System.out.println("budgetMap = " + budgetMap);
							budgetService.batchSave(budgetMap,0);	
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
						budgetMap.put("id", map.get("project_id"));
						System.out.println("budgetMap = " + budgetMap);
						budgetService.batchSave(budgetMap,0);
					}
				}
		}
		return "redirect:toDeclare.htm?pageNo=5";
	}
	
	//删除经费预算的某一项
	@RequestMapping(value="/deleteBudget.htm",method=RequestMethod.GET)
	public String deleteBudget(HttpServletRequest request,HttpServletResponse response,
			Long budget_id) {
		budgetService.delete("delete from tsc_budget where id="+budget_id);
		return "redirect:toDeclare.htm?pageNo=4";
	}
	
	//添加拨款账户
	@RequestMapping(value="/addAccount.htm",method=RequestMethod.POST)
	public String addAccount(HttpServletRequest request,HttpServletResponse response,
			BankAccount account,Long account_id) {
		System.out.println("account = "+account.toString());
		Long userId = userService.getUserId(request, response);
		if (userId != null) {
			String sqlString = "SELECT project_id FROM tsc_user2project WHERE user_id="+userId;
			Map<String, Object> map = projectService.queryForMap(sqlString);
			if (map != null) {
				//更新拨款账户
				String sqlString1 = null;
				if (account_id != null) {
					sqlString1 = "UPDATE tsc_bankAccount SET addTime=NOW(),bank='"+account.getBank()+"',"
							+ "accountHolder='"+account.getAccountHolder()+"',account='"+account.getAccount()+"' WHERE id="+account_id;
					System.out.println(sqlString1);
					bankAccountService.update(sqlString1);
				}
				//添加拨款账户
				else {
					sqlString1 = "INSERT INTO tsc_bankAccount (addTime,bank,accountHolder,account,project_id)"
							+ " VALUES (NOW(),'"+account.getBank()+"','"+account.getAccountHolder()+"','"+account.getAccount()+"',"
									+ map.get("project_id")+")";
					bankAccountService.save(sqlString1);
				}
			}
		}
		return "redirect:index.htm";
	}
}
