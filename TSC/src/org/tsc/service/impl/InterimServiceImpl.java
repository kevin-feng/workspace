package org.tsc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IInterimService;

@Service
public class InterimServiceImpl implements IInterimService {

	@Resource(name="interimDao")
	private IBaseDao interimDao;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.interimDao.insertData(sql);
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
			this.interimDao.deleteData(sql);
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
			this.interimDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.interimDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.interimDao.queryForMap(sql);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return this.interimDao.getById(id);
	}

}
