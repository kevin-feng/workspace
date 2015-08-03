package org.tsc.manage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.bean.User;
import org.tsc.core.tools.MD5;
import org.tsc.core.tools.RSAUtil;
import org.tsc.service.IUserService;

@Controller
public class RegisterLoginController {

	@Autowired
	private IUserService userService;
	
	//返回注册页面
	@RequestMapping(value="/register_index.htm",method=RequestMethod.GET)
	public ModelAndView getRegister(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_register.html");
		return mv;
	}
	
	//注册用户，提交注册表单
	@RequestMapping(value="/register.htm",method=RequestMethod.POST)
	public String register(HttpServletRequest request,HttpServletResponse response,
			User user) {
		String password = user.getPassword();
		if (!"".equals(password)) {
			String encodePassword = MD5.md5Encode(password);
			String sqlString = "INSERT INTO tsc_user (addTime,userName,password,trueName,email,userRole) "
					+ "VALUES (NOW(),'"+user.getUserName()+"','"+encodePassword+"','"+user.getTrueName()+"',"
					+ "'"+user.getEmail()+"','DECLARER')";
			userService.save(sqlString);
		}
		return "redirect:login_index.htm";
	}
	
	//返回用户登录页面
	@RequestMapping(value="/login_index.htm",method=RequestMethod.GET)
	public ModelAndView getLogin(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_login.html");
		return mv;
	}
	
	//用户登录
	@RequestMapping(value="/login.htm",method=RequestMethod.POST)
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
		return "redirect:login_index.htm";
	}
	
	//退出登录
	@RequestMapping(value="/logout.htm",method=RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		request.getSession(false).removeAttribute("userName");
		request.getSession(false).removeAttribute("userId");
		request.getSession(false).removeAttribute("userRole");
		return "redirect:index.htm";
	}
}
