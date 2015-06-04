package org.tsc.manage.background;

import java.util.ArrayList;
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
import org.tsc.bean.User;
import org.tsc.service.IProjectService;
import org.tsc.service.IUserService;

@Controller
@RequestMapping("/bg")
public class ExpertManageController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	
	//显示添加专家页面
	@RequestMapping(value="/showExperts.htm",method=RequestMethod.GET)
	public ModelAndView showExperts(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-index-addexpert.html");
		return mv;
	}
	
	//批量生成专家的账号密码
	@RequestMapping(value="batchAddAccount.htm",method=RequestMethod.POST)
	public ModelAndView batchAddAccount(HttpServletRequest request,HttpServletResponse response,
			int count) {
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-index-addexpert_list.html");
		List<Map<String, Object>> accounts = new ArrayList<Map<String,Object>>();
		accounts = userService.batchCreateAccount(count);
		mv.addObject("accounts", accounts);
		return mv;
	}
	
	//批量保存专家的信息
	@RequestMapping(value="/batchAddExpert.htm",method=RequestMethod.POST)
	public void batchAddExpert(HttpServletRequest request,HttpServletResponse response,
			User user) {
		System.out.println(user.getTrueName()+"   "+user.getUserName()+"   "+user.getPassword());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trueName", user.getTrueName());
		map.put("userName", user.getUserName());
		map.put("password", user.getPassword());
		userService.batchAddExperts(map);
	}
	
	//显示专家列表页
	@RequestMapping(value="/showExpertsList.htm",method=RequestMethod.GET)
	public ModelAndView showExpertsList(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-index-expert_list.html");
		List<Map<String, Object>>experts = userService.queryForList("select * from tsc_user where userRole='EXPERT'");
		mv.addObject("experts", experts);
		return mv;
	}
	
	//删除某个专家
	@RequestMapping(value="/deleteExpert.htm",method=RequestMethod.GET)
	public String deleteExpert(HttpServletRequest request,HttpServletResponse response,
			Long userId){
		userService.delete("delete from tsc_user where id="+userId);
		System.out.println("delete from tsc_user where id="+userId);
		return "redirect:showExpertsList.htm";
	}
	
	//批量删除专家
	@RequestMapping(value="batchDeleExpert.htm",method=RequestMethod.GET)
	public String batchDeleExperts(HttpServletRequest request,HttpServletResponse response,
			String userIds) {
		userService.batchDeleById(userIds);
		return "redirect:showExpertsList.htm";
	}
	
	//显示专家分配页面
	@RequestMapping(value="/showAssignExpert.htm",method=RequestMethod.GET)
	public ModelAndView showAssignExpert(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-index-assign_expert.html");
		List<Map<String, Object>>experts = userService.queryForList("select * from tsc_user where userRole='EXPERT'");
		List<Map<String, Object>>projects = projectService.queryForList("select * from tsc_project where pass=0");
		mv.addObject("experts", experts);
		mv.addObject("projects", projects);
		return mv;
	}
	
	//批量保存专家的分配情况
	@RequestMapping(value="/batchAssignExpert.htm",method=RequestMethod.POST)
	public void batchAssignExpert(HttpServletRequest request,HttpServletResponse response,
			String project_ids,String user_ids){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project_ids", project_ids);
		map.put("user_ids", user_ids);
		userService.batchAssignExperts(map);
	}
}
