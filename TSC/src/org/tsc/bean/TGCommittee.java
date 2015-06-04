package org.tsc.bean;

import org.tsc.core.bean.IdEntity;

public class TGCommittee extends IdEntity {
	private String name;//教学指导委员会名称
	private String introduction;//教指委简介
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
}
