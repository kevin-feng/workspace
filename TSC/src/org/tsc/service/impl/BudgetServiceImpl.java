package org.tsc.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.MapKey;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IBudgetService;

import com.sun.corba.se.spi.orb.StringPair;

@Service
@Transactional
public class BudgetServiceImpl implements IBudgetService {

	@Resource(name="budgetDao")
	private IBaseDao budgetDao;
	
	@Override
	public boolean save(String sql) {
		// TODO Auto-generated method stub
		try {
			this.budgetDao.insertData(sql);
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
			this.budgetDao.deleteData(sql);
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
			this.budgetDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.budgetDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.budgetDao.queryForMap(sql);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return this.budgetDao.getById(id);
	}

	@Override
	public int[] batchSave(Map<String, Object> map,int type) {
		final Long id = (Long)map.get("id");
		final String[] subjectName = map.get("subjectName").toString().split(",");
		final String[] amount = map.get("amount").toString().split(",");
		System.out.println("subject_size = "+subjectName.length+  " amount_size = "+amount.length);
		String sql = null;
		if (type == 0) {
			sql = "insert into tsc_budget (addTime,subjectName,amount,project_id) values(NOW(),?,?,?)";
		}else {
			sql = "insert into tsc_budget (addTime,subjectName,amount,termination_id) values(NOW(),?,?,?)";
		}
		//批量插入数据
		int[] updateCounts = this.budgetDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, subjectName[arg1]);
				arg0.setInt(2, Integer.parseInt(amount[arg1]));
				arg0.setLong(3, id);	
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				if (subjectName.length > amount.length) {
					return amount.length;
				}else {
					return subjectName.length;		
				}
			}
		});
		return updateCounts;
	}

	@Override
	public int[] batchUpdate(Map<String, Object> map) {
		final String[] subjectName = map.get("subjectName").toString().split(",");
		final String[] amount = map.get("amount").toString().split(",");
		final String[] id = map.get("budget_id").toString().split(",");
		System.out.println("subject_size = "+subjectName.length+  " amount_size = "+amount.length);
		String sql = "update tsc_budget set subjectName=?,amount=? where id=?";
		//批量更新数据
		int[] updateCounts = this.budgetDao.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement arg0, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, subjectName[arg1]);
				arg0.setInt(2, Integer.parseInt(amount[arg1]));
				arg0.setLong(3, Long.parseLong(id[arg1]));	
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return subjectName.length;		
			}
		});
		return updateCounts;	
	}



	
}
