package org.tsc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IBankAccountService;

@Service
@Transactional
public class BankAccountServiceImpl implements IBankAccountService {

	@Resource(name="bankAccountDao")
	private IBaseDao bankAccountDao;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.bankAccountDao.insertData(sql);
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
			this.bankAccountDao.deleteData(sql);
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
			this.bankAccountDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.bankAccountDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.bankAccountDao.queryForMap(sql);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return this.bankAccountDao.getById(id);
	}

}
