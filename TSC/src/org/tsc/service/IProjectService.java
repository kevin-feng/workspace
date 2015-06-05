package org.tsc.service;

import java.util.List;
import java.util.Map;

public interface IProjectService {

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
		/**
		 * 获取项目列表以及每个项目对应的评审
		 * @param pass 0为未立项项目，1为立项项目
		 * @return
		 */
		public List<Map<String, Object>> getProjectsAndReviews(String status);
		
		public int[] batchUpdateProjectStatus(Map<String, Object> map);
		
		public List<Map<String, Object>> setProjectCodeByStatus(String ids,String statuses);

}