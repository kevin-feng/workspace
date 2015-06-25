package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IReviewDao;

@Repository("reviewDao")
public class ReviewDaoImpl extends BaseDaoImpl implements IReviewDao {

	public ReviewDaoImpl() {
		super("tsc_review");
	}
}
