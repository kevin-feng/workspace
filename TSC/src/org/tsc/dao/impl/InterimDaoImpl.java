package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IInterimDao;

@Repository("interimDao")
public class InterimDaoImpl extends BaseDaoImpl implements IInterimDao {

	public InterimDaoImpl() {
		super("tsc_interim");
	}
}
