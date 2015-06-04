package org.tsc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService{

	@Resource(name="projectDao")
	private IBaseDao projectDao;
	
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
	public List<Map<String, Object>> getProjectsAndReviews(int status) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> projects = new ArrayList<Map<String,Object>>();
		projects = queryForList("select id,name,status from tsc_project where status="+status);
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


}
