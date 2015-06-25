package org.tsc.service;

import java.util.List;
import java.util.Map;

import org.tsc.dao.IAccessoryDao;

public interface IAccessoryService{
	
	//增
	public boolean save(String sql);
	//删
	public boolean delete(String sql);
	//改
	public boolean update(String sql); 
	//查
	public List<Map<String, Object>> queryForList(String sql);
	
	public Map<String, Object> queryForMap(String sql);
	
	//获取一行数据通过id
	public Map<String, Object> getById(Long id);
	
	//通过map来生成一个Accessory,并返回其id
	public Long saveAccessoryByMap(Map<String, Object> map);
}
