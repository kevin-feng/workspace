package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IUserDao;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements IUserDao {
	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
		super("tsc_user");
	} 
}
