package org.tsc.manage.background;

import java.io.IOException;
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
import org.tsc.core.tools.MD5;
import org.tsc.core.tools.SysUtils;
import org.tsc.service.IUserService;

@Controller
@RequestMapping("/bg")
public class UserManageController {
	@Autowired
	private IUserService userService;
	
	//显示管理员
	@RequestMapping(value="/showUsers.htm",method=RequestMethod.GET)
	public ModelAndView showUsers(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = null;
		//如果没登录，或者登录的账号不是超级管理员，则为false
	//	boolean flag = false;
		String userRole = userService.getAdminRole(request, response);
		if (userRole != null) {
			String userName = (String)request.getSession().getAttribute("userName");
			if (userRole.equals("ADMIN") && "admin".equals(userName)) {
				mv = new ModelAndView("lcjxjd_back/ps-index-admin.html");
				List<Map<String, Object>> users = new ArrayList<Map<String,Object>>();
				Map<String, Object> admin = new HashMap<String, Object>();		
				admin = userService.queryForMap("SELECT * FROM tsc_user WHERE userName='admin'");
				String sqlString = "SELECT * FROM tsc_user WHERE userName!='admin' and userRole = 'ADMIN'";
				users = userService.queryForList(sqlString);
				System.out.println(users.size());
				System.out.println("admin = "+admin);
				mv.addObject("admin", admin);
				mv.addObject("users", users);
			}else {
				mv = new ModelAndView("lcjxjd_back/ps-authority.html");
			}
		}	
		return mv;
	}
	
	//添加管理员 如果id存在  则为修改密码
	@RequestMapping(value="/addUser.htm",method=RequestMethod.POST)
	public String addUser(HttpServletRequest request,HttpServletResponse response,
			String userName,String password,Long id) {
		String encodePassword = MD5.md5Encode(password);
		if (id != null) {
			String sqlString = "UPDATE tsc_user SET password='"+encodePassword+"' WHERE id="+id;
			userService.update(sqlString);
		}else {
			String sqlString="INSERT INTO tsc_user (userName,password,userRole) VALUES ('"+userName+"','"+encodePassword+"','ADMIN')";
			userService.save(sqlString);		
		}
		return "redirect:showUsers.htm";
	}
	
	//删除管理员
	@RequestMapping(value="/deleteUser.htm",method=RequestMethod.GET)
	public String deleteUser(HttpServletRequest request,HttpServletResponse response,
			Long id) {
		String sqlString = "DELETE FROM tsc_user WHERE id="+id;
		userService.delete(sqlString);
		return "redirect:showUsers.htm";
	}
}
