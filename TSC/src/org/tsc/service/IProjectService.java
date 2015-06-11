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
		
		/**
		 * 批量更新项目的status以及projectCode
		 * @param list
		 * @return
		 */
		public int[] batchUpdateProjectStatus(List<Map<String, Object>> list);
		
		/**
		 * 根据project的status，设置相应的项目编码，格式为2015JDA0511241，2015JDB0511242，
		 * 其中如果立项资助则为A，立项不资助则为B
		 * @param ids
		 * @param statuses
		 * @return
		 */
		public List<Map<String, Object>> setProjectCodeByStatus(String ids,String statuses);

}