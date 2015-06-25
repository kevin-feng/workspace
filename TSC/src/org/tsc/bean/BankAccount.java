package org.tsc.bean;

import org.tsc.core.bean.IdEntity;

/**
 * 项目的拨款账号类
 * 
 * */
public class BankAccount extends IdEntity {

	private String bank;//医院开户行
	private String accountHolder;//开户人姓名
	private String account;//银行账号
	private Project project;//该开户账号对应的项目
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
