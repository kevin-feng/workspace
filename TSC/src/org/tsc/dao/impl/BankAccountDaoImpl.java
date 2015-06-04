package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IBankAccountDao;

@Repository("bankAccountDao")
public class BankAccountDaoImpl extends BaseDaoImpl implements IBankAccountDao {

	public BankAccountDaoImpl() {
		super("tsc_bankAccount");
	}
}
