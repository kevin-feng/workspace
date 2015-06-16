package org.tsc.manage.background;

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
import org.tsc.bean.Project;
import org.tsc.service.IProjectService;
import org.tsc.service.IUserService;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

@Controller
@RequestMapping("/bg")
public class ProjectManageController {
	@Autowired
	public IProjectService projectService;
	@Autowired
	public IUserService userService;

	//显示未立项和立项的项目列表
	@RequestMapping(value="showProjects.htm")
	public ModelAndView showProjects(HttpServletRequest request,HttpServletResponse response,
			int type) {
		ModelAndView modelAndView = null;
		List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
		//type=0为项目管理页面，1为立项管理的中期检查项目的页面，2为立项管理的结题材料的页面
		if (type == 0) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-project_manage.html");			
			projects = projectService.getProjectsAndReviews("0,1,2,5",0);
			List<Map<String, Object>>experts = userService.queryForList("select id,trueName from tsc_user where userRole='EXPERT'");
			modelAndView.addObject("experts", experts);
		}else if (type == 1) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-project_bulid_manage.html");
			projects = projectService.getProjectsAndReviews("3,4,6,7,8,9,10",1);
		}else if (type == 2) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-project_bulid_manage.html");
			projects = projectService.getProjectsAndReviews("11,12,13,14,15",2);	
		}
		System.out.println(projects);
		modelAndView.addObject("projects", projects);
		return modelAndView;
	}
	
	//保存项目的立不立项的操作
	@RequestMapping(value="updateProjectStatus.htm",method=RequestMethod.POST)
	public void updateProjectStatus(HttpServletRequest request,HttpServletResponse response,
			String id,String status) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = projectService.setProjectCodeByStatus(id, status);
		projectService.batchUpdateProjectStatus(list);
	}
}
