package org.tsc.manage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.types.FileList.FileName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsc.core.base.DBToolKit;
import org.tsc.core.base.PageList;
import org.tsc.core.tools.DownloadUtils;
import org.tsc.dao.IMessageDao;
import org.tsc.service.IAccessoryService;
import org.tsc.service.IMessageService;
import org.tsc.service.impl.AccessoryServiceImpl;


@Controller
public class MessageController {
	
	@Autowired
	private IMessageDao messageDao;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IAccessoryService accessoryService;
	
	//临床教学基地教指委首页
	@RequestMapping(value="/index.htm",method=RequestMethod.GET)
	public ModelAndView homePage(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_l.html");
		List<Map<String, Object>> messages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> workMessages = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> courseMessages = new ArrayList<Map<String,Object>>();
		String sqlString  = "SELECT * FROM tsc_message where type=0 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString2 = "SELECT * FROM tsc_message where type=1 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		String sqlString3 = "SELECT * FROM tsc_message where type=4 AND deleteStatus=0 ORDER BY addTime DESC LIMIT 0,6";
		messages = messageDao.selectData(sqlString);
		workMessages = messageDao.selectData(sqlString2);
		courseMessages = messageDao.selectData(sqlString3);
		mv.addObject("messages", messages);		
		mv.addObject("workMessages", workMessages);
		mv.addObject("courseMessages", courseMessages);
		return mv;
	}
	//显示消息列表
	@RequestMapping(value="/showMessages.htm",method=RequestMethod.GET)
	public ModelAndView showMessages(HttpServletRequest request,HttpServletResponse response,
			int type,int pageNo) {
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_list_l.html");
		PageList pageList = new PageList(messageDao);
		StringBuilder sql = null;
		if (type==7) {
			sql = new StringBuilder("SELECT acce.path,message.* FROM tsc_message message INNER JOIN tsc_accessory acce ON message.acce_id = acce.id")
			.append(" AND message.deleteStatus=0 ORDER BY message.addTime DESC");
		}else {
			sql = new StringBuilder("SELECT * FROM tsc_message where type=")
			.append(type).append(" AND deleteStatus=0 ORDER BY addTime DESC");
		}
		List<Map<String, Object>> neweastMessages = messageService.getMessagesByType(type, 0, 10);
		List<Map<String, Object>> hotMessages = messageService.getHotMessagesByType(type, 10);
		mv.addObject("neweastMessages", neweastMessages);
		mv.addObject("hotMessages", hotMessages);	
		pageList.getPageList(sql, pageNo, 20);
		//获取最近添加的消息列表和热门消息列表
		mv.addObject("pageList", pageList);
		mv.addObject("messages", pageList.getResult());
		mv.addObject("type", type);
		return mv;
	}
	
	//显示某篇消息
	@RequestMapping(value="/showMessage.htm",method=RequestMethod.GET)
	public ModelAndView showMessage(HttpServletRequest request,HttpServletResponse response,
			Long id){
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_notice_l.html");
		//当点击某篇消息时，将该篇消息的访问次数count+1
		int count = (Integer)messageDao.getById(id).get("count")+1;
		String sqlString1 = "UPDATE tsc_message SET count="+count+" WHERE id="+id;
		messageDao.updateData(sqlString1);
		Map<String, Object> message = messageDao.getById(id);
		int type = (Integer)message.get("type");
		//获取消息对应的附件
		Long acce_id = (Long)message.get("acce_id");
		Map<String, Object> accessory = accessoryService.getById(acce_id);
		//获取最近添加的消息列表和热门消息列表
		List<Map<String, Object>> neweastMessages = messageService.getMessagesByType(type, 0, 10);
		List<Map<String, Object>> hotMessages = messageService.getHotMessagesByType(type, 10);
		Map<String, Object> preMessage = messageService.getPreMessageById(id);
		Map<String, Object> nextMessage = messageService.getNextMessageById(id);
		mv.addObject("message", message);
		mv.addObject("accessory", accessory);
		mv.addObject("neweastMessages", neweastMessages);
		mv.addObject("hotMessages", hotMessages);
		mv.addObject("preMessage", preMessage);
		mv.addObject("nextMessage",nextMessage);
		return mv;
	}
	
	//文件下载
	@RequestMapping(value="/downloadFile.htm")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,
			Long id) {
		String sqlString ="select acce_id from tsc_message where id="+id;
		Long acce_id = (Long)messageService.queryForMap(sqlString).get("acce_id");
		Map<String, Object> map =  accessoryService.getById(acce_id);
		//每次下载附件将下载次数+1
		int count = (Integer)map.get("count")+1;
		System.out.println("acce_id = "+acce_id+"  count = "+count);
		accessoryService.update("UPDATE tsc_accessory SET count="+count+" WHERE id="+acce_id);
		String path = (String)map.get("path");
		String fileName = (String)map.get("name");
		DownloadUtils.downloadFile(request, response,path,fileName);		
	}
	
	//站内搜索
	@RequestMapping(value="/search.htm",method=RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response,
			String keyWord,int pageNo) {
		System.out.println("keyWord = "+keyWord);
		ModelAndView mv = new ModelAndView("lcjxjd/lcjsjd_list_l.html");
		PageList pageList = new PageList(messageDao);
		StringBuilder sql = new StringBuilder("SELECT * FROM tsc_message WHERE deleteStatus=0 AND title LIKE '%")
		.append(keyWord).append("%'").append(" ORDER BY addTime DESC");
		System.out.println("sql = "+sql);
		pageList.getPageList(sql, pageNo, 20);
		mv.addObject("pageList", pageList);
		mv.addObject("messages", pageList.getResult());
		return mv;
	}
	
	//下载中心列表
	@RequestMapping(value="/showDownloads.htm",method=RequestMethod.GET)
	public ModelAndView showDownloads(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String sqlString = "SELECT acce.* FROM tsc_message message INNER JOIN tsc_accessory acce ON message.acce_id = acce.id";
		List<Map<String , Object>> downloads = new ArrayList<Map<String,Object>>();
		downloads = messageService.queryForList(sqlString); 
		mv.addObject("downloads", downloads);
		return mv;
	}
}
