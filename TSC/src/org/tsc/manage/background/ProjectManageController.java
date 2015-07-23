package org.tsc.manage.background;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
		String userName = userService.getUserName(request, response);
		if (userName == null) {
			try {
				response.sendRedirect("login.htm");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
			//type=0为项目管理页面，1为立项管理的中期检查项目的页面，2为立项管理的结题材料的页面
			String sql = "";
			if (type == 0) {
				modelAndView = new ModelAndView("lcjxjd_back/ps-project_manage.html");			
				projects = projectService.getProjectsAndReviews("0,1,2,5","EXPERT");
				sql = "select id,trueName from tsc_user where userRole='EXPERT'";
			}else if (type == 1) {
				modelAndView = new ModelAndView("lcjxjd_back/ps-project_interim_manage.html");
				projects = projectService.getProjectsAndReviews("3,4,6,7,8,9,10","INTERIM_EXPERT");
				sql = "select id,trueName from tsc_user where userRole='EXPERT_INTERIM'";
			}else if (type == 2) {
				modelAndView = new ModelAndView("lcjxjd_back/ps-project_termination_manage.html");
				projects = projectService.getProjectsAndReviews("9,11,12,13,14,15","TERMINATION_EXPERT");	
				sql = "select id,trueName from tsc_user where userRole='EXPERT_TERMINATION'";
			}
			if (projects.size() > 0) {
				String projectCode = null;
				List<Map<String, Object>> fundProjects = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> unfundProjects = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < projects.size(); i++) {
					projectCode = (String) projects.get(i).get("project_code");
					if (projectCode != null) {
						if (projectCode.contains("A")) {
							fundProjects.add(projects.get(i));
						} else if (projectCode.contains("B")) {
							unfundProjects.add(projects.get(i));
						}
					}
				}
				modelAndView.addObject("fundProjects", fundProjects);
				modelAndView.addObject("unfundProjects", unfundProjects);
			}
			List<Map<String, Object>>experts = userService.queryForList(sql);
			modelAndView.addObject("experts", experts);
			System.out.println(projects);
			modelAndView.addObject("projects", projects);
		}
		return modelAndView;
	}
	
	//保存项目的立不立项的操作
	@RequestMapping(value="updateProjectStatus.htm",method=RequestMethod.POST)
	public void updateProjectStatus(HttpServletRequest request,HttpServletResponse response,
			String id,String status)throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = projectService.setProjectCodeByStatus(id, status);
		projectService.batchUpdateProjectStatus(list);
	}
	
	//批量保存中期材料通不通过的操作
	@RequestMapping(value="updateStatus.htm",method=RequestMethod.POST)
	public void updateStatus(HttpServletRequest request,HttpServletResponse response,
			String id,String status) {
		projectService.batchUpdateStatusById(id, status);
	}
}
