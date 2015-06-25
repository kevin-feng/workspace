package org.tsc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsc.core.base.IBaseDao;
import org.tsc.service.IAccessoryService;

@Service
@Transactional
public class AccessoryServiceImpl implements IAccessoryService {
	
	@Resource(name="accessoryDao")
	private IBaseDao accessoryDao;

	@Override
	public boolean save(String sql) {
		try {
			// TODO Auto-generated method stub
			this.accessoryDao.insertData(sql);
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
			this.accessoryDao.deleteData(sql);
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
			this.accessoryDao.updateData(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>();
		listMaps = this.accessoryDao.selectData(sql);
		return listMaps;
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql) {
		// TODO Auto-generated method stub
		return this.accessoryDao.queryForMap(sql);
	}
	
	@Override
	public Long saveAccessoryByMap(Map<String, Object> map) {	
		String fileName = (String)map.get("fileName");
		String oldName = (String)map.get("oldName");
		String ext = (String)map.get("mime");
		Long fileSize = (Long)map.get("fileSize");
		String path = (String)map.get("savePath");
//		String width = (String)map.get("width");
//		String height = (String)map.get("height");
		String sqlString = "INSERT INTO tsc_accessory (addTime,name,ext,oldName,size,path)"
				+"VALUES (NOW(),'"+fileName+"','"+ext+"','"+oldName+"','"+fileSize+"','"+path+"')";
		accessoryDao.insertData(sqlString);
		Map<String, Object> map2 = accessoryDao.queryForMap("SELECT MAX(id) AS id from tsc_accessory");
		return (Long)map2.get("id");
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// TODO Auto-generated method stub
		return accessoryDao.getById(id);
	}
	
	
}
