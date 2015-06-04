package org.tsc.core.base;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库通用操作接口
 * 
 * @author tianya
 * 
 * @param <T>
 *            需要持久化的对象（实体类）
 */
public interface IBaseDao {

	/**
	 * 保存一条记录
	 * @param properties 字段名
	 * @param objs	字段值
	 * @return
	 */
	int save(String[] properties, Object[] objs);
	
	
	// ------------------------更新记录----------------------------------------------//
	// ---------------------------------------------------------------------------//
	/**
	 * 更新一条记录
	 * 
	 * @param properties
	 *            字段名
	 * @param objs
	 *            属性值
	 * @param id
	 *            记录的id
	 * @return
	 */
	int update(String[] properties, Object[] objs, Object id);

	/**
	 * 通过 id 删除指定对象
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Object id);

	/**
	 * 通过对象的唯一属性值，删除指定对象
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	int deleteByUniqueProperty(String propertyName, Object value);

	/**
	 * 通过id批量删除
	 * <p>
	 * 注意返回值都是 0.
	 * </p>
	 * 
	 * @param ids  id字符串
	 * @return
	 */
	int deleteManyByIds(String id);

	JdbcTemplate getJdbcTemplate();

	// -----------------------获取单个对象--------------------------------------------//
	// ---------------------------------------------------------------------------//
	/**
	 * 通过 id 获取对象 T
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> getById(Object id);

	/**
	 * 通过对象的某唯一属性值查询指定对象
	 * <p>
	 * 如可以通过唯一的用户名，邮箱，手机号等查询对象
	 * </p>
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 当对象存在时返回指定对象；找不到指定对象返回 null
	 */
	Map<String, Object> getByUniqueProperty(String property, Object value);
	
	/**
	 *通过表的某个字段来查找对应的数据 
	 */
	List<Map<String, Object>> getByOneProperty(String property, Object value);

	// ------------------------分页查询------------------------//
	/**
	 * 分页查询
	 * 
	 * @param properties
	 * @param objs
	 * @param pageNo
	 * @param pageSize
	 * @param orderColumn
	 *            排序的字段（默认是 addTime)
	 * @param orderType
	 *            排序的类型（asc 或 desc ）
	 * @return
	 */
	public List <Map<String,Object>> getDataByList(String [] properties,Object []objs);
	
	
	List<Map<String, Object>> getPageList(String[] properties, Object[] objs, int pageNo, int pageSize,
			String orderColumn, String orderType);

	List<Map<String, Object>> getPageList(String[] properties, Object[] objs, int pageNo, int pageSize);

		//增
		public int insertData(String sql);
		//删
		public int deleteData(String sql);
		//查
		public List<Map<String,Object>> selectData(String sql);
		//改
		public int updateData(String sql);
		//查
		public Map<String, Object> queryForMap(String sql);	

}

