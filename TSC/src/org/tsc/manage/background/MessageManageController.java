
package org.tsc.manage.background;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.http.Http.multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.core.base.DBToolKit;
import org.tsc.core.tools.UploadUtils;
import org.tsc.dao.IMessageDao;
import org.tsc.service.IAccessoryService;
import org.tsc.service.IMessageService;
import org.tsc.service.IUserService;
import org.tsc.service.impl.UserServiceImpl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.resources.PolicyMessages;

//后台管理程序---消息管理
@Controller
@RequestMapping("/bg")
public class MessageManageController {
	
	@Autowired
	private IMessageDao messageDao;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IAccessoryService accessoryService;
	@Autowired
	private IUserService userService;
	
	//后台管理主页  显示消息
	@RequestMapping(value="/index.htm")
	public ModelAndView bg_index(HttpServletRequest request,HttpServletResponse response) {
		//从session中获取用户名
		String userName = (String)request.getSession().getAttribute("userName");
		ModelAndView mv = new ModelAndView("ps-index.html");
		List<Map<String, Object>> messages =  new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> workMessages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> policyMessages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> theoryMessages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> courseMessages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> reviewMessages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> resultMessages = new ArrayList<Map<String,Object>>();
		String sqlString  = "SELECT * FROM tsc_message where type=0 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString2 = "SELECT * FROM tsc_message where type=1 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString3 = "SELECT * FROM tsc_message where type=2 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString4 = "SELECT * FROM tsc_message where type=3 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString5 = "SELECT * FROM tsc_message where type=4 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString6 = "SELECT * FROM tsc_message where type=5 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString7 = "SELECT * FROM tsc_message where type=6 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		messages = messageDao.selectData(sqlString);
		workMessages = messageDao.selectData(sqlString2);
		policyMessages = messageDao.selectData(sqlString3);
		theoryMessages = messageDao.selectData(sqlString4);
		courseMessages = messageDao.selectData(sqlString5);
		reviewMessages = messageDao.selectData(sqlString6);
		resultMessages = messageDao.selectData(sqlString7);
		
		mv.addObject("messages", messages);			
		mv.addObject("workMessages", workMessages);
		mv.addObject("policyMessages", policyMessages);
		mv.addObject("theoryMessages", theoryMessages);
		mv.addObject("courseMessages", courseMessages);
		mv.addObject("reviewMessages", reviewMessages);
		mv.addObject("resultMessages", resultMessages);
		mv.addObject("userName", userName);
		return mv;
	}
	
	//链接到发布 修改消息的页面
	@RequestMapping(value="/toAddMessage.htm",method=RequestMethod.GET)
	public ModelAndView toAddMessage(HttpServletRequest request,HttpServletResponse response,
			Integer type,Long id) {
		//判断用户是否已登录，若未登录则重定向到登录页面，若已登录，则返回用户名
		//String userName = userService.getUserName(request,response);
		//System.out.println(userName);
		ModelAndView mv = new ModelAndView("lcjxjd_back/ps-index-notice.html");	
		if (id != null && !id.equals("")) {
			Map<String, Object> message = messageDao.getById(id);
			mv.addObject("message", message);
		}
		mv.addObject("type", type);	
		return mv;
	}
	
	//发布消息
	@RequestMapping(value="/addMessage.htm",method=RequestMethod.POST)
	public String addMessage(HttpServletRequest request,HttpServletResponse response,
			String title,String text,String source,String author,Integer type,
			MultipartFile myFile,Long id){
		System.out.println("title = "+title+" text = "+text);
		System.out.println("myFile = "+myFile.getOriginalFilename()+"----"+myFile.getName());

		//附件上传
		Long acce_id = null;
		if (!myFile.getOriginalFilename().equals("")) {
			String path = null;
			//当要修改时，获取message的type
			if (id != null) {
				System.out.println("id = "+id);
				String sqlString = "SELECT * FROM tsc_message where id="+id;
				System.out.println(sqlString);
				Map<String, Object> map1 = messageService.queryForMap(sqlString);
				System.out.println("map = "+map1);
				type = (Integer)map1.get("type");
			}
			switch (type) {
				case 0:path = "messages";break;
				case 1:path = "workMessages";break;
				case 2:path = "policyMessages";break;
				case 3:path = "theoryMessages";break;
				case 4:path = "courseMessages";break;
				case 5:path = "reviewMessages";break;
				case 6:path = "resultMessages";break;
				
				default:break;
			}
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			String date = sdFormat.format(new Date());
			//上传
			Map<String, Object> map = UploadUtils.upload30MFile(request, "myFile", "upload/"+path+"/"+date);
			//用文件上传返回的map创建accessory
			acce_id = accessoryService.saveAccessoryByMap(map);
			System.out.println("acce_id = "+acce_id);
		} 
		//如果id不为空，则是修改，否则是添加
		if (id != null) {
			String sqlString = null;
			if (acce_id != null) {
				sqlString = "UPDATE tsc_message SET title='"+title+"',text='"+text+
						"',source='"+source+"',author='"+author+"',acce_id="+acce_id+" WHERE id="+id;
			}else {
				sqlString = "UPDATE tsc_message SET title='"+title+"',text='"+text+
						"',source='"+source+"',author='"+author+"' WHERE id="+id;				
			}
			messageDao.updateData(sqlString);
		}else {
			String sqlString  = "INSERT INTO tsc_message (addTime,text,title,source,author,type,acce_id)"
					+"VALUES (NOW(),'"+text+"','"+title+"','"+source+"','"+author+"',"+type+","+acce_id+")";
			messageDao.insertData(sqlString);	
		}
		return "redirect:index.htm";
	}
	
	//删除消息（不是真正的从数据库删除，而是让deleteStatus=1）
	@RequestMapping(value="/deleteMessage.htm",method=RequestMethod.GET)
	public String deleteMessage(HttpServletRequest request,HttpServletResponse response,Long id) {
		//判断用户是否已登录，若未登录则重定向到登录页面，若已登录，则返回用户名
		String userName = userService.getUserName(request,response);
		String sqlString = "UPDATE tsc_message SET deleteStatus=1 WHERE id="+id;
		messageDao.updateData(sqlString);
		return "redirect:index.htm";
	}
	
	//上传文件
	@RequestMapping(value="/uploadFile.htm",method=RequestMethod.POST)
	public String uploadFile(HttpServletRequest request,HttpServletResponse response){
		UploadUtils.upload5MImage(request, "myFile", "upload/message");
		return "redirect:index.htm";
	}
}
