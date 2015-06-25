package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IMemberDao;

@Repository("memberDao")
public class MemberDaoImpl extends BaseDaoImpl implements IMemberDao {

	public MemberDaoImpl() {
		super("tsc_member");
	}
}
