package org.tsc.dao.impl;

import org.springframework.stereotype.Repository;
import org.tsc.core.base.impl.BaseDaoImpl;
import org.tsc.dao.IAchievementDao;

@Repository("achievementDao")
public class AchievementDaoImpl extends BaseDaoImpl implements IAchievementDao {

	public AchievementDaoImpl() {
		super("tsc_achievement");
	}
}
