package org.tsc.service;

import java.util.List;
import java.util.Map;

public interface IBankAccountService {

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
}
