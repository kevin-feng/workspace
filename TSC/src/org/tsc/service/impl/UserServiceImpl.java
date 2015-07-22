package org.tsc.service.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.core.tools.SysUtils;
import org.tsc.service.IProjectService;
import org.tsc.service.IUserService;

import com.sun.mail.iap.Response;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource(name="userDao")
	private IBaseDao userDao;
	@Autowired
	private IProjectService projectService;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			this.userDao.insertData(sql);
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
			// TODO Auto-generated method stub
			this.userDao.deleteData(sql);
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
			// TODO Auto-generated method stub
			this.userDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.userDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.userDao.queryForMap(sql);
	}

	@Override
	public String getUserName(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println((String)request.getSession().getAttribute("userName"));
		String userName = (String)request.getSession().getAttribute("userName");
		return userName;
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
		
	}

	@Override
	public Long getUserId(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Long userId = (Long) request.getSession().getAttribute("userId");
		if (null == userId) {
			try {
				response.sendRedirect("login.htm");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return userId;
	}

	@Override
	public String getUserRole(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userRole = (String) request.getSession().getAttribute("userRole");
		if (userRole == null) {
			try {
				response.sendRedirect("login_index.htm");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userRole;
	}

	@Override
	public List<Map<String, Object>> batchCreateAccount(int count) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", SysUtils.randomInt(9));
			map.put("password", SysUtils.randomInt(6));
			list.add(map);
		}
		return list;
	}

	@Override
	public int[] batchAddExperts(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String trueNames = (String)map.get("trueName");
		String userNames = (String)map.get("userName");
		String passwords = (String)map.get("password");
		int type = (Integer)map.get("type");
		final String[] trueName = trueNames.split(",");
		final String[] userName = userNames.split(",");
		final String[] password = passwords.split(",");
		String userRole = "";
		if(type == 0){
			userRole = "EXPERT";
		}else if (type == 1) {
			userRole = "EXPERT_INTERIM";
		}else {
			userRole = "EXPERT_TERMINATION";
		}
		String sql = "insert into tsc_user (addTime,trueName,userName,password,userRole) values (NOW(),?,?,?,'"+userRole+"')";
		int[] updateCounts = this.userDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, trueName[arg1]);
				arg0.setString(2, userName[arg1]);
				arg0.setString(3, password[arg1]);
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return userName.length;
			}
		});
		return updateCounts;
	}

	@Override
	public int batchDeleById(String id) {
		// TODO Auto-generated method stub
		try {
			this.userDao.deleteManyByIds(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int[] batchAssignExperts(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String project_ids = (String)map.get("project_ids");
		String user_ids = (String)map.get("user_ids");
		final String[] project_id = project_ids.split(",");
		final String[] user_id = user_ids.split(",");
		String sql = "insert into tsc_user2project (user_id,project_id) values (?,?)";
		int[] updateCounts = this.userDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setLong(1, Long.parseLong(user_id[arg1]));
				arg0.setLong(2, Long.parseLong(project_id[arg1]));
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return user_id.length;
			}
		});
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < project_id.length; i++) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("id", project_id[i]);
			map2.put("status", map.get("status"));
			list.add(map2);
		}
		projectService.batchUpdateProjectStatus(list);
		return updateCounts;
	}

}
