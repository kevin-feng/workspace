package org.tsc.manage.background;

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

@Controller
@RequestMapping("/bg")
public class ProjectManageController {
	@Autowired
	public IProjectService projectService;

	//显示未立项和立项的项目列表
	@RequestMapping(value="showProjects.htm")
	public ModelAndView showProjects(HttpServletRequest request,HttpServletResponse response,
			int type) {
		ModelAndView modelAndView = null;
		if (type == 0 || type == 3) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-index-project-list_weilixiang.html");			
		}else if (type == 1 || type  == 2) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-index-project-list_lixiang.html");
		}
		List<Map<String, Object>> projects = projectService.getProjectsAndReviews(type);
		System.out.println(projects);
		modelAndView.addObject("projects", projects);
		return modelAndView;
	}
	
	//保存项目的立不立项的操作
	@RequestMapping(value="updateProjectPass.htm",method=RequestMethod.GET)
	public String updateProjectPass(HttpServletRequest request,HttpServletResponse response,
			String id,String pass) {
		char[] ids = id.toCharArray();
		char[] passes = pass.toCharArray();
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]+"-------"+passes[i]);
			String sqlString = "update tsc_project set status="+passes[i]+" where id="+ids[i];
			projectService.update(sqlString);
		}
		return "redirect:showProjects.htm?type=0";
	}
}
