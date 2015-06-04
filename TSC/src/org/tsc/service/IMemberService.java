package org.tsc.service;

import java.util.List;
import java.util.Map;

public interface IMemberService {

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
			
			//批量插入项目组成员
			public int[] batchSave(Map<String, Object> map);
			
			//批量更新项目组成员
			public int[] batchUpdate(Map<String, Object> map);
			//批量更新项目组成员  （更新的属性不同）
			public int[] batchUpdate2(Map<String, Object> map);
			
}
