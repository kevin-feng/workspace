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

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

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
		List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
		//type=0为项目管理页面，1为立项管理的中期检查项目的页面，2为立项管理的结题材料的页面
		if (type == 0) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-index-project-list_weilixiang.html");			
			projects = projectService.getProjectsAndReviews("0,1,2,5");
		}else if (type == 1) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-index-project-list_lixiang.html");
			projects = projectService.getProjectsAndReviews("3,4,6,7,8,9,10");
		}else if (type == 2) {
			modelAndView = new ModelAndView("lcjxjd_back/ps-index-project-list_lixiang.html");
			projects = projectService.getProjectsAndReviews("11,12,13,14,15");	
		}
		System.out.println(projects);
		modelAndView.addObject("projects", projects);
		return modelAndView;
	}
	
	//保存项目的立不立项的操作
	@RequestMapping(value="updateProjectStatus.htm",method=RequestMethod.GET)
	public String updateProjectStatus(HttpServletRequest request,HttpServletResponse response,
			String id,String status) {
		char[] ids = id.toCharArray();
		char[] statuses = status.toCharArray();
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]+"-------"+statuses[i]);
			String sqlString = "update tsc_project set status="+statuses[i]+" where id="+ids[i];
			projectService.update(sqlString);
		}
		return "redirect:showProjects.htm?type=0";
	}
}
