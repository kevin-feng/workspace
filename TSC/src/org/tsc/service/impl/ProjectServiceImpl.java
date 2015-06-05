package org.tsc.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IProjectService;

import com.sun.org.glassfish.external.statistics.Statistic;

@Service
@Transactional
public class ProjectServiceImpl implements IProjectService{

	@Resource(name="projectDao")
	private IBaseDao projectDao;
	private static int code = 0;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.projectDao.insertData(sql);
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
			this.projectDao.deleteData(sql);
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
			this.projectDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.projectDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = this.projectDao.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return map;
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = this.projectDao.getById(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getProjectsAndReviews(String status) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
		projects = queryForList("select id,name,status from tsc_project where status in ("+status+")");
		if (projects != null && projects.size() > 0) {
			for (Map<String, Object> map : projects) {
				Long id = (Long) map.get("id");
				List<Map<String, Object>> reviews = new ArrayList<Map<String, Object>>();
				reviews = queryForList("select * from tsc_review where project_id="
						+ id);
				map.put("reviews", reviews);
			}
		}
		return projects;
	}

	@Override
	public int[] batchUpdateProjectStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		final String[] statuses = map.get("statuses").toString().split(",");
		final String[] ids = map.get("ids").toString().split(",");
		final String projectCode = "";
		
		String sql = "update tsc_project set status=?,project_code=? where id=?";
		int[] updateCount = this.projectDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setInt(1,Integer.parseInt(statuses[arg1]));
				arg0.setString(2, projectCode);
				arg0.setLong(3, Long.parseLong(ids[arg1]));
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		return updateCount;
	}

	@Override
	public List<Map<String, Object>> setProjectCodeByStatus(String ids,
			String statuses) {
		// TODO Auto-generated method stub
		char[] id = ids.toCharArray();
		char[] status = statuses.toCharArray();
		
		for (int i = 0; i < id.length; i++) {
			StringBuilder projectCode = new StringBuilder();
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			projectCode.append(year).append("JD");
			if (status[i] == 3) {
				projectCode.append("A");
			}else if (status[i] == 4) {
				projectCode.append("B");
			}
		}
		return null;
	}
	
	public String generateProjectCode() {
		StringBuilder projectCode = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		return null;
	}




}
