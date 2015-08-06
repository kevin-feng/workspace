package org.tsc.core.base.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sun.istack.internal.FinalArrayList;

public class BaseDaoImpl implements IBaseDao {
	
	private String tableName; // 数据库表名

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 数据库表名
	 * @param tableName
	 */
	public BaseDaoImpl(String tableName) {
		// 获取持久化对象的类型
		this.tableName = tableName;
	}
	public BaseDaoImpl() {}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	

	@Override
	public int save(String[] properties, Object[] objs) {
		StringBuilder sql = new StringBuilder("insert into " + tableName + "(addTime,");
		StringBuffer values = new StringBuffer(" values(NOW(),");
		int len = properties.length - 1;
		for (int i = 0; i < len; i++) {
			sql.append(properties[i]+",");
			values.append("?,");
			
		}
		sql.append(properties[len] + ")").append(values.append("?)"));
		return jdbcTemplate.update(sql.toString(), objs);
	}
	
	@Override
	public Map<String, Object> getById(Object id) {
		String sql = " select * from " + tableName + " where id = ?";
		List<Map<String,Object>> list =  jdbcTemplate.queryForList(sql, id);
		if(list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Map<String, Object> getByUniqueProperty(String property, Object value) {
		String sql = " select * from " + tableName + " where "+property +" = ?";
		return jdbcTemplate.queryForMap(sql, value);
	}
	
	@Override
	public List<Map<String, Object>> getByOneProperty(String property, Object value) {
		String sql = " select * from " + tableName + " where "+property +" = ?";
		return jdbcTemplate.queryForList(sql, value);
	}


	@Override
	public int update(String[] properties, Object[] objs, Object id) {
		StringBuilder sql = new StringBuilder("update " + tableName + " set ");
		int len = properties.length - 1;
		for (int i = 0; i < len; i++) {
			sql.append(properties[i] + "=?, ");
		}
		sql.append(properties[len] + " = ? where id = " + id);
		return jdbcTemplate.update(sql.toString(), objs);
	}
	
	@Override
	public int deleteById(Object id) {
		String sql = " delete from " + tableName + " where id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public int deleteByUniqueProperty(String propertyName, Object value) {
		String sql = "delete from " + tableName + " where " + propertyName + " =?";
		return jdbcTemplate.update(sql, value);
	}
	
	@Override
	public int deleteManyByIds(String id) {
		String[] ids = id.split(",");
		String sql = "delete from " + tableName + " where id = ?";
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			jdbcTemplate.update(sql, ids[i]);
		}
		
		return 0;
	}
	
	@Override
	public List<Map<String, Object>> getPageList(String[] properties, Object[] objs, int pageNo, int pageSize) {
		return getPageList(properties, objs, pageNo, pageSize, "addTime", "asc");
	}
	
	@Override
	public List<Map<String, Object>> getPageList(String[] properties, Object[] objs, int pageNo, int pageSize,String orderColumn,String orderType) {
		StringBuilder sql = new StringBuilder("select * from  " + tableName);
		if (properties != null) {
			sql.append(" where ");
			int len = properties.length - 1;
			for (int i = 0; i < len; i++) {
				sql.append(properties[i] + "=? and ");
			}
			sql.append(properties[len] + " = ?");
		}
		sql.append(" order by " + orderColumn +" " + orderType + " limit ");
		if (pageNo > 0) {
			int first = (pageNo-1)*pageSize;	//偏移量
			sql.append( first + ",");
		}
		if (pageSize > 0) {
			sql.append(pageSize);
		}else {
			sql.append(10);
		}
		System.out.println(sql);
		return jdbcTemplate.queryForList(sql.toString(), objs);
	}
	public List <Map<String,Object>> getDataByList(String [] properties,Object []objs){
		
		StringBuilder sql = new StringBuilder("select * from  " + tableName);
		if (properties != null) {
			sql.append(" where ");
			int len = properties.length - 1;
			for (int i = 0; i <len; i++) {
				sql.append(properties[i] + "=? and ");
			}
			sql.append(properties[len] + " = ?");
		}else {
			return null;
		}
		sql.append(" order by id desc");
		System.out.println("sql = "+sql);
		return jdbcTemplate.queryForList(sql.toString(), objs);
	}
	
	
	
	//  新增的、通用的增删查改
	
	//增
	public int insertData(String sql){
		return jdbcTemplate.update(sql.toString());
	}
	//删
	public int deleteData(String sql) {
		return jdbcTemplate.update(sql.toString());
	}
	//查
	public List<Map<String,Object>> selectData(String sql){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = jdbcTemplate.queryForList(sql);
		return list;
	}
	//改
	public int updateData(String sql){
		return jdbcTemplate.update(sql.toString());
	}
	//查
	public Map<String, Object> queryForMap(String sql) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = jdbcTemplate.queryForMap(sql);
		return map;
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}	
	
}