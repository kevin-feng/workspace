package org.tsc.service;

import java.util.List;
import java.util.Map;

import org.tsc.core.base.PageList;

public interface IMessageService {

		//增
		public boolean save(String sql);
		//删
		public boolean delete(String sql);
		//改
		public boolean update(String sql); 
		//查
		public List<Map<String, Object>> queryForList(String sql);
		
		public Map<String, Object> queryForMap(String sql);
		
		//通过类型type获取最新添加消息列表    当type=7时为最新下载列表
		public List<Map<String, Object>> getMessagesByType(int type,int begin,int max);
		
		//通过类型type获取 热门 消息列表   当type=7时为热门下载列表
		public List<Map<String, Object>> getHotMessagesByType(int type,int max);
		
		//通过id获取某篇消息的上一篇消息
		public Map<String, Object> getPreMessageById(Long id);
		
		//通过id获取某篇消息的下一篇消息
		public Map<String, Object> getNextMessageById(Long id);
	
}
