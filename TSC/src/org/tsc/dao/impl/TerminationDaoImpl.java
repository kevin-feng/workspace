package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.ITerminationDao;

@Repository("terminationDao")
public class TerminationDaoImpl extends BaseDaoImpl implements ITerminationDao {

	public TerminationDaoImpl() {
		super("tsc_termination");
	}
}
