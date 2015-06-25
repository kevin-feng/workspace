package org.tsc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {
			//增
			public boolean save(String sql);
			//删
			public boolean delete(String sql);
			//改
			public boolean update(String sql); 
			//查  返回一个List
			public List<Map<String, Object>> queryForList(String sql);
			//查  返回一个Map
			public Map<String, Object> queryForMap(String sql);
			//获取一个map通过id
			public Map<String, Object> getById(Long id);
			//判断用户是否登录,如果没有登录，则重定向到登录页面,如果登录了，则返回userName
			public String getUserName(HttpServletRequest request,HttpServletResponse response);
			//判断用户是否登录,如果没有登录，则重定向到登录页面,如果登录了，则返回userId
			public Long getUserId(HttpServletRequest request,HttpServletResponse response);
			//判断用户是否登录,如果没有登录，则重定向到登录页面,如果登录了，则返回userRole
			public String getUserRole(HttpServletRequest request,HttpServletResponse response);
			//批量产生专家的账号密码
			public List<Map<String, Object>> batchCreateAccount(int count);
			//批量保存专家的账号信息
			public int[] batchAddExperts(Map<String, Object> map);
			//批量删除user通过id
			public int batchDeleById(String id);
			//批量分配专家给项目
			public int[] batchAssignExperts(Map<String, Object> map);
}
