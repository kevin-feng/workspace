package org.tsc.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.core.init.InitializeProcessor;
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
	public int[] batchUpdateProjectStatus(final List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		
		String sql = "update tsc_project set status=?,project_code=? where id=?";
		int[] updateCount = this.projectDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setInt(1,Integer.parseInt(list.get(arg1).get("status").toString()));
				arg0.setString(2, list.get(arg1).get("projectCode").toString());
				arg0.setLong(3, Long.parseLong(list.get(arg1).get("id").toString()));
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
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
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
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
			SimpleDateFormat sFormat = new SimpleDateFormat("hhmmss");
			String time = sFormat.format(new Date());
			projectCode.append(time).append(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id[i]);
			map.put("status", status[i]);
			map.put("projectCode", projectCode);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 产生project_code的最后3位数字
	 * @return
	 */
	public String generateLast3Code(String code) {
		StringBuilder sb = new StringBuilder();
		if (code.length() == 1) {
			sb.append("00").append(code);
		}else if (code.length() == 2) {
			sb.append("0").append(code);
		}else if (code.length() == 3) {
			sb.append(code);
		}
		return sb.toString();
	}
	
	public String getMaxProCode() {
		String projectCode = InitializeProcessor.getProjectCode();
		return null;
	}




}
