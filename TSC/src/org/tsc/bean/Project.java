package org.tsc.bean;

import java.util.List;

import org.tsc.core.bean.IdEntity;
/**
 * 申报的项目类
 * */
public class Project extends IdEntity {
	
	private String projectCode;//项目编码，立项时才有，格式为2014JDA001,2014JDB002，其中A代表立项资助，B代表立项不资助
	private String name;//项目名称
	private String category;//项目类别
	private String organization;//所在单位
	private int amount;//项目的申请金额
	private String meaning;//本课题所要解决的问题及其对临床教学的意义
	private String content;//研究设计、内容、目标、主要特色
	private String expectedEffect;//预期效果与具体成果
	private String schedule;//具体安排及进度
	private int totalBudget;//该项目的总经费预算
	private int status;//项目的状态
	//0为项目初始状态，1为项目已分配专家，2为专家已评审，3为立项资助，4为立项不资助，5为不立项，6为已填写中期报告，7为中期评审专家已分配，
	//8为中期材料已评审，9为中期评审通过，10为中期评审不通过，11为已填写结题材料，12为结题评审专家已分配，13为结题已评审，
	//14为结题评审已通过，15为结题评审不通过
	private List<Member> members;//项目对应的成员 （包括负责人和普通成员）
	private List<Budget> budgets;//项目对应的经费预算
	private List<Review> reviews;//项目对应的评审
	
	
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExpectedEffect() {
		return expectedEffect;
	}
	public void setExpectedEffect(String expectedEffect) {
		this.expectedEffect = expectedEffect;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public int getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(int totalBudget) {
		this.totalBudget = totalBudget;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public List<Budget> getBudgets() {
		return budgets;
	}
	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
	
}
