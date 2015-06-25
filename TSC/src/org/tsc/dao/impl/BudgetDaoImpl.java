package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IBudgetDao;

@Repository("budgetDao")
public class BudgetDaoImpl extends BaseDaoImpl implements IBudgetDao{

	public BudgetDaoImpl() {
		super("tsc_budget");
	}
}
