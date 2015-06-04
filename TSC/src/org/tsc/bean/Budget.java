package org.tsc.bean;

import java.util.List;

import org.tsc.core.bean.IdEntity;
/**
 * 经费预算 支出类
 */
public class Budget extends IdEntity {
	
	private String subjectName;//经费开支科目
	private int amount;//经费预算 支出金额（元）,默认为0
	private Project project;//该经费预算对应的项目
	private Termination termination;//该经费支出对应的结题材料
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Termination getTermination() {
		return termination;
	}
	public void setTermination(Termination termination) {
		this.termination = termination;
	}

	
	
}
