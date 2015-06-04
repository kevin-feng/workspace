package org.tsc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.ITerminationService;

@Service
@Transactional
public class TerminationServiceImpl implements ITerminationService {

	@Resource(name="terminationDao")
	private IBaseDao terminationDao;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.terminationDao.insertData(sql);
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
			this.terminationDao.deleteData(sql);
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
			this.terminationDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.terminationDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.terminationDao.queryForMap(sql);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return this.terminationDao.getById(id);
	}

	@Override
	public boolean save(String[] keys, Object[] values) {
		// TODO Auto-generated method stub
		try {
			this.terminationDao.save(keys, values);		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(String[] keys, Object[] values, Object id) {
		// TODO Auto-generated method stub
		try {
			this.terminationDao.update(keys, values, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
}
