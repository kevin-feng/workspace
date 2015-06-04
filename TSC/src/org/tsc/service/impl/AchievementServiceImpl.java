package org.tsc.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IAchievementService;

@Service
@Transactional
public class AchievementServiceImpl implements IAchievementService {
	@Resource(name="achievementDao")
	private IBaseDao achievementDao;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.achievementDao.insertData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(String sql) {
		// TODO Auto-generated method stub
		try {
			this.achievementDao.deleteData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(String sql) {
		// TODO Auto-generated method stub
		try {
			this.achievementDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.achievementDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.achievementDao.queryForMap(sql);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return this.achievementDao.getById(id);
	}
	/**
	 *批量保存数据
	 *@param map 要插入的键值对
	 *@param type 0则将interim_id插入表中，否则将ternation_id插入表中
	 */
	@Override
	public int[] batchSave(Map<String, Object> map,int type) {
		final String[] titles = map.get("title").toString().split(",");
		final String[] authors = map.get("author").toString().split(",");
		final String[] achiForms = map.get("achiForm").toString().split(",");
		final String[] publications = map.get("publication").toString().split(",");
		final String[] wordNumbers = map.get("wordNumber").toString().split(",");
		final String[] publishTimes = map.get("publishTime").toString().split(",");
		final Long id = (Long)map.get("id");;
		String sql = null;
		if (type == 0) {
			sql = "insert into tsc_achievement (addTime,title,author,achiForm,publication,"
					+ "wordNumber,publishTime,interim_id) values (NOW(),?,?,?,?,?,?,?)";	
		}else {
			sql = "insert into tsc_achievement (addTime,title,author,achiForm,publication,"
					+ "wordNumber,publishTime,termination_id) values (NOW(),?,?,?,?,?,?,?)";
		}
		int[] updateCounts = this.achievementDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1,titles[arg1] );
				arg0.setString(2,authors[arg1] );
				arg0.setString(3,achiForms[arg1] );
				arg0.setString(4,publications[arg1] );
				arg0.setInt(5, Integer.parseInt(wordNumbers[arg1]));
				arg0.setString(6,publishTimes[arg1] );
				arg0.setLong(7,id);
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return titles.length;
			}
		});
		return updateCounts;
	}

	@Override
	public int[] batchUpdate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		final String[] titles = map.get("title").toString().split(",");
		final String[] authors = map.get("author").toString().split(",");
		final String[] achiForms = map.get("achiForm").toString().split(",");
		final String[] publications = map.get("publication").toString().split(",");
		final String[] wordNumbers = map.get("wordNumber").toString().split(",");
		final String[] publishTimes = map.get("publishTime").toString().split(",");
		final String[] achievement_ids = map.get("achievement_id").toString().split(",");
		String sql = "update tsc_achievement set title=?,author=?,achiForm=?,publication=?,"
				+ "wordNumber=?,publishTime=? where id=?";
		int[] updateCounts = this.achievementDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1,titles[arg1] );
				arg0.setString(2,authors[arg1] );
				arg0.setString(3,achiForms[arg1] );
				arg0.setString(4,publications[arg1] );
				arg0.setInt(5, Integer.parseInt(wordNumbers[arg1]));
				arg0.setString(6,publishTimes[arg1] );
				arg0.setLong(7,Long.parseLong(achievement_ids[arg1]) );
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return titles.length;
			}
		});
		return updateCounts;
	}
}
