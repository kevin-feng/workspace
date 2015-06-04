package org.tsc.service;

import java.util.List;
import java.util.Map;

public interface IAchievementService {
			//增
			public boolean save(String sql);
			//删
			public boolean delete(String sql);
			//改
			public boolean update(String sql); 
			//查
			public List<Map<String, Object>> queryForList(String sql);
			//获取一行数据
			public Map<String, Object> queryForMap(String sql);
			//获取一行数据通过id
			public Map<String, Object> getById(Long id);
			//批量保存数据
			public int[] batchSave(Map<String, Object> map,int type);
			//批量更新数据
			public int[] batchUpdate(Map<String, Object> map);
}
