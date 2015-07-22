package org.tsc.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.commons.lang3.StringUtils;
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
	
	/**
	 * 根据状态查询项目
	 * @param status
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getProjectsByStatus(String status) {
		List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
		projects = queryForList("select id,name,status,project_code from tsc_project where status in ("+status+")");
		return projects;
	}
	
	/**
	 * 通过项目编号和项目状态获取项目列表
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> getProjectByCodeAndStatus(String status) {
		return null;
	}

	@Override
	public List<Map<String, Object>> getProjectsAndReviews(String status,String userRole) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
		projects = getProjectsByStatus(status);
		if (projects != null && projects.size() > 0) {
			for (Map<String, Object> map : projects) {
				Long id = (Long) map.get("id");
//				String sqlString = "select user.id,user.trueName from tsc_user2project u2p inner join "
//						+ "tsc_user user on user.id=u2p.user_id where u2p.type="+type+" and u2p.project_id="+id+" and user.userRole='EXPERT'";
				String sqlString = "SELECT user.id,user.trueName FROM tsc_user2project u2p INNER JOIN tsc_user user ON u2p.user_id = `user`.id WHERE `user`.userRole = '"+userRole+"' AND u2p.project_id = '"+id+"' ";
				System.out.println(sqlString);
				List<Map<String, Object>> experts = new ArrayList<Map<String,Object>>();
				experts = queryForList(sqlString); 
				map.put("experts", experts);
				int sta = Integer.parseInt(map.get("status").toString());
				String sql = "";
				if (userRole.equals("EXPERT") && sta >= 2 && sta <= 5) {
					sql = "select user.id as user_id,user.trueName,review.id as review_id,review.reviewResult,review.suggestion,review.remark"
							+ " from tsc_review review inner join tsc_user user on review.user_id=user.id  where review.project_id="+ id;
				}else if (userRole.equals("INTERIM_EXPERT") && sta >= 8 && sta <= 10) {
					sql = "SELECT `user`.id as user_id,`user`.trueName,interim_id,interim.workSituation,"
							+ "review.id as review_id,review.remark FROM tsc_review review INNER JOIN tsc_interim interim "
							+ "ON review.interim_id = interim.id INNER JOIN tsc_user user ON `user`.id = review.user_id  WHERE interim.project_id=1";
				}else if (userRole.equals("TERMINATION_EXPERT") && sta >=13 && sta <=15) {
					sql = "SELECT `user`.id as user_id,`user`.trueName,termination_id,termination.*,"
							+ "review.id as review_id,review.remark FROM tsc_review review INNER JOIN tsc_termination termination "
							+ "ON review.termination_id = termination.id INNER JOIN tsc_user user ON `user`.id = review.user_id  WHERE termination.project_id=1";
				}
				List<Map<String, Object>> reviews = new ArrayList<Map<String, Object>>();
				reviews = queryForList(sql);
				map.put("reviews", reviews);
			}
		}
		return projects;
	}

	@Override
	public int[] batchUpdateStatusById(String id,String status) {
		final String[] ids = id.split(",");
		final String[] statuses = status.split(",");
		String sql = "update tsc_project set status=? where id=?";
		int[] updateCount = this.projectDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setInt(1, Integer.parseInt(statuses[arg1]));
				arg0.setLong(2, Long.parseLong(ids[arg1]));
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return ids.length;
			}
		});
		return updateCount;
	}
	
	@Override
	public int[] batchUpdateProjectStatus(final List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		String sql = "";
		if (list.get(0).containsKey("projectCode")) {
			sql = "update tsc_project set status=?,project_code=? where id=?";			
		}else {
			sql = "update tsc_project set status=? where id=?";
		}
		int[] updateCount = this.projectDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setInt(1,Integer.parseInt(list.get(arg1).get("status").toString()));
				if (list.get(0).containsKey("projectCode")) {
					arg0.setString(2, list.get(arg1).get("projectCode").toString());
					arg0.setLong(3, Long.parseLong(list.get(arg1).get("id").toString()));
				}else {
					arg0.setLong(2, Long.parseLong(list.get(arg1).get("id").toString()));
				}
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
		String[] id = ids.split(",");
		String [] status = statuses.split(",");
		System.out.println(id.length);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int code = 0;
		boolean flag = true;//记录是否是第一次查询最大projectCode的标识
		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (Integer.parseInt(status[i]) == 3 || Integer.parseInt(status[i]) == 4) {
				StringBuilder projectCode = new StringBuilder();
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				projectCode.append(year).append("JD");
				if (Integer.parseInt(status[i]) == 3) {
					projectCode.append("A");
				} else if (Integer.parseInt(status[i]) == 4) {
					projectCode.append("B");
				}
				
				if (flag) {
					code = getMaxProCode();
					flag = false;
				}
				code++;
				String proCode = generateLast3Code(String.valueOf(code));
				projectCode.append(proCode);;
				map.put("projectCode", projectCode);					
			}else if (Integer.parseInt(status[i]) == 5) {
				map.put("projectCode", "");	
			}
			map.put("id", id[i]);
			map.put("status", status[i]);
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
	
	/**
	 * 从数据库的tsc_project表中查出project_code，获取project_code的后三位数字，并对其进行快速排序，
	 * 返回最大的那个数字
	 * @return
	 */
	public int getMaxProCode() {
		String sql = "SELECT project_code FROM tsc_project WHERE project_code != ''";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = projectDao.selectData(sql);
		if (list.size() > 0) {
			int[] projectCodes = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String projectCode = list.get(i).get("project_code").toString();
				if (projectCode.contains("A")) {
					projectCode = projectCode.substring(projectCode
							.indexOf('A') + 1);
				} else if (projectCode.contains("B")) {
					projectCode = projectCode.substring(projectCode
							.indexOf('B') + 1);
				}
				projectCodes[i] = Integer.parseInt(projectCode);
			}
			Arrays.sort(projectCodes);
			return projectCodes[list.size() - 1];
		}else {
			return 0;
		}
	}
	
}
