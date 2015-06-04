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
import org.tsc.service.IMemberService;

@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

	@Resource(name="memberDao")
	private IBaseDao memberDao;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.memberDao.insertData(sql);
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
			this.memberDao.deleteData(sql);
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
			this.memberDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.memberDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.memberDao.queryForMap(sql);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return this.memberDao.getById(id);
	}

	@Override
	public int[] batchSave(Map<String, Object> map) {
		// TODO Auto-generated method stub
		final Long project_id = (Long)map.get("project_id");
		final String[] name = map.get("name").toString().split(",");
		final String[] mobileNo = map.get("mobileNo").toString().split(",");
		final String[] adminPosition = map.get("adminPosition").toString().split(",");
		final String[] organization = map.get("organization").toString().split(",");
		final String[] major = map.get("major").toString().split(",");
		String sql = "insert into tsc_member (addTime,name,mobileNo,adminPosition,organization,major,project_id) values (NOW(),?,?,?,?,?,"+project_id+")";
		int[] updateCounts = this.memberDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, name[arg1]);
				arg0.setString(2, mobileNo[arg1]);
				arg0.setString(3, adminPosition[arg1]);
				arg0.setString(4, organization[arg1]);
				arg0.setString(5, major[arg1]);
				
			}	
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return name.length;
			}
		});
		return updateCounts;
	}

	@Override
	public int[] batchUpdate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		final String[] name = map.get("name").toString().split(",");
		final String[] mobileNo = map.get("mobileNo").toString().split(",");
		final String[] adminPosition = map.get("adminPosition").toString().split(",");
		final String[] organization = map.get("organization").toString().split(",");
		final String[] major = map.get("major").toString().split(",");
		final String[] member_id = map.get("member_id").toString().split(",");
		String sql = "update tsc_member set name=?,mobileNo=?,adminPosition=?,organization=?,major=? where id=?";
		int[] updateCounts = this.memberDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, name[arg1]);
				arg0.setString(2, mobileNo[arg1]);
				arg0.setString(3, adminPosition[arg1]);
				arg0.setString(4, organization[arg1]);
				arg0.setString(5, major[arg1]);
				arg0.setLong(6, Long.parseLong(member_id[arg1]));
				
			}	
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return name.length;
			}
		});
		return updateCounts;
	}

	@Override
	public int[] batchUpdate2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		final String[] name = map.get("name").toString().split(",");
		final String[] adminPosition = map.get("adminPosition").toString().split(",");
		final String[] finishWorks = map.get("finishWork").toString().split(",");
		final String[] member_id = map.get("member_id").toString().split(",");
		String sql = "update tsc_member set name=?,adminPosition=?,finishWork=? where id=?";
		int[] updateCounts = this.memberDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, name[arg1]);
				arg0.setString(2, adminPosition[arg1]);
				arg0.setString(3, finishWorks[arg1]);
				arg0.setLong(4, Long.parseLong(member_id[arg1]));
			}	
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return name.length;
			}
		});
		return updateCounts;
	}

}
