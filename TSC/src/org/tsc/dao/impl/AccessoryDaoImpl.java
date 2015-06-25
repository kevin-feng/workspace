package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IAccessoryDao;

@Repository("accessoryDao")
public class AccessoryDaoImpl extends BaseDaoImpl implements IAccessoryDao {
	
	public AccessoryDaoImpl() {
		super("tsc_accessory");
	}
}
