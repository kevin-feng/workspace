package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IProjectDao;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl implements IProjectDao {
	
	public ProjectDaoImpl() {
		super("tsc_project");
	}
}
