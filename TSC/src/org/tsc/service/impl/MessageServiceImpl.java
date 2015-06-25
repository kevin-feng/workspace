package org.tsc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.core.base.PageList;
import org.tsc.service.IMessageService;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService {
	
	@Resource(name="messageDao")
	private IBaseDao messageDao;
	@Override
	public boolean save(String sql) {
		try {
			// TODO Auto-generated method stub
			this.messageDao.insertData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(String sql) {
		try {
			// TODO Auto-generated method stub
			this.messageDao.deleteData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(String sql) {
		try {
			// TODO Auto-generated method stub
			this.messageDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return this.messageDao.selectData(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.messageDao.queryForMap(sql);
	}
	
	@Override
	public List<Map<String, Object>> getMessagesByType(int type,int begin,int max) {
		List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
		String sqlString = null; 
		if (type == 7) {
			sqlString = "SELECT acce.path,message.* FROM tsc_message message INNER JOIN tsc_accessory acce ON message.acce_id = acce.id"
		+" AND message.deleteStatus=0 ORDER BY message.addTime DESC";
		}else {
			sqlString  = "SELECT * FROM tsc_message where type="+type+" AND deleteStatus=0"
					+ " ORDER BY addTime DESC LIMIT "+begin+","+max;			
		}
		messages = messageDao.selectData(sqlString);
		return messages;
	}

	@Override
	public List<Map<String, Object>> getHotMessagesByType(int type,int max) {
		List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
		String sqlString = null;
		if (type == 7) {
			sqlString = "SELECT acce.path,message.* FROM tsc_message message INNER JOIN tsc_accessory acce ON message.acce_id = acce.id"
			+ " AND message.deleteStatus=0 ORDER BY acce.count DESC LIMIT 0,"+max;
		}else {
			sqlString  = "SELECT * FROM tsc_message where type="+type+" AND deleteStatus=0"
					+ " ORDER BY count DESC LIMIT 0,"+max;			
		}
		messages = messageDao.selectData(sqlString);
		return messages;
	}

	@Override
	public Map<String, Object> getPreMessageById(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int type = (Integer)messageDao.getById(id).get("type");
		String sqlString = "SELECT * FROM tsc_message WHERE id>"+id+" AND type="+type+" LIMIT 1";	
		try {
			map = messageDao.queryForMap(sqlString);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return null;
		}
		System.out.println("map = "+map);
		return map;
	}

	@Override
	public Map<String, Object> getNextMessageById(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int type = (Integer)messageDao.getById(id).get("type");
		String sqlString = "SELECT * FROM tsc_message WHERE id<"+id+" AND type="+type+" ORDER BY id DESC LIMIT 1";	
		try {
			map = messageDao.queryForMap(sqlString); 
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return null;
		}
		return map;
	}
	
}
