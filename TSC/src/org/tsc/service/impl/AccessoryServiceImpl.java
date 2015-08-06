package org.tsc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.DividePtg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IAccessoryService;

@Service
@Transactional
public class AccessoryServiceImpl implements IAccessoryService {
	
	@Resource(name="accessoryDao")
	private IBaseDao accessoryDao;
	@Autowired
	private static Logger logger = Logger.getLogger(AccessoryServiceImpl.class);

	@Override
	public boolean save(String sql) {
		try {
			this.accessoryDao.insertData(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("持久化数据异常！sql="+sql,e);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(String sql) {
		try {
			this.accessoryDao.deleteData(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除数据异常！sql="+sql,e);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(String sql) {
		try {
			this.accessoryDao.updateData(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新数据异常！sql="+sql,e);
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>();
		try {
			listMaps = this.accessoryDao.selectData(sql);
		} catch (Exception e) {
			logger.error("查询数据异常！sql="+sql,e);
		}
		return listMaps;
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = this.accessoryDao.queryForMap(sql);
		} catch (Exception e) {
			logger.error("查询数据异常！sql="+sql,e);
		}
		return map;
	}
	
	@Override
	public Long saveAccessoryByMap(Map<String, Object> map) {	
		String fileName = (String)map.get("fileName");
		String oldName = (String)map.get("oldName");
		String ext = (String)map.get("mime");
		Long fileSize = (Long)map.get("fileSize");
		String path = (String)map.get("savePath");
		String sqlString = "INSERT INTO tsc_accessory (addTime,name,ext,oldName,size,path)"
				+"VALUES (NOW(),'"+fileName+"','"+ext+"','"+oldName+"','"+fileSize+"','"+path+"')";
		Map<String, Object> map2 = new HashMap<String, Object>();
		Long id = null;
		try {
			accessoryDao.insertData(sqlString);
			map2 = accessoryDao.queryForMap("SELECT MAX(id) AS id from tsc_accessory");
			id = (Long)map2.get("id");
		} catch (Exception e) {
			logger.error("通过Map持久化Accessory，并查询出最大id异常！",e);
		}
		return id;
	}

	@Override
	public Map<String, Object> getById(Long id) {
		return accessoryDao.getById(id);
	}

}
