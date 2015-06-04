package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IMessageDao;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl implements IMessageDao {

	public MessageDaoImpl() {
		super("tsc_message");
	}

}
