package org.tsc.manage.background;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.core.tools.MD5;
import org.tsc.service.IUserService;

@Controller
@RequestMapping("/bg")
public class LoginManageController {
	
	@Autowired
	private IUserService userService;
	
	//返回到登录页面
	@RequestMapping(value="/login.htm",method=RequestMethod.GET)
	public ModelAndView login_index(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-login.html");
		return mv;
	}
	
	//登录
	@RequestMapping(value="/sub_login.htm",method=RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response,
			String userName,String password) {
		if (userName != null && !userName.equals("") && password != null && !password.equals("")) {
			String encodePassword = MD5.md5Encode(password);
			String sqlString = "SELECT * FROM tsc_user WHERE userName='"
		+userName+"' AND password='"+encodePassword+"'";
			Map<String, Object> map = userService.queryForMap(sqlString);
			//登录成功
			if (map != null) {
				request.getSession().setAttribute("userId", map.get("id"));
				request.getSession().setAttribute("userName", userName);
				request.getSession().setAttribute("userRole", map.get("userRole"));
				return "redirect:index.htm";
			}
		}
		return "redirect:login.htm";
	}
	
	//退出登录
	@RequestMapping(value="/logout.htm",method=RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		request.getSession(false).removeAttribute("userName");
		request.getSession(false).removeAttribute("userId");
		request.getSession(false).removeAttribute("userRole");
		return "redirect:index.htm";
	}
	
	//返回权限页面
	@RequestMapping(value="/authority.htm",method=RequestMethod.GET)
	public ModelAndView authority(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-authority.html");
		return mv;
	}
}
