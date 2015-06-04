package org.tsc.bean;

import java.util.List;

import org.tsc.core.bean.IdEntity;

/**
 *立项的项目对应的结题材料
 *@author fyxiang
 *
 **/
public class Termination extends IdEntity {

	private String planTime;//计划完成时间
	private String realTime;//实际完成时间
	private int appropriation;//临床教学基地教指委拨款
	private int subsidize;//单位配套资助
	private int otherFunds;//其他自筹经费
	private int totalFunds;//经费合计
	private String achiForm;//成果形式
	private String content;//成果的观点与内容
	private String process;//研究过程
	private String step;//主要研究方法与步骤
	private String result;//研究结果与分析
	private String effect;//实践效果或应用情况，社会影响等
	private Project project;//结题材料对应的项目
	private List<Budget> budgets;//结题材料对应的经费支出
	private List<Achievement> achievements;//结题材料对应的成果

	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getRealTime() {
		return realTime;
	}
	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}
	public int getAppropriation() {
		return appropriation;
	}
	public void setAppropriation(int appropriation) {
		this.appropriation = appropriation;
	}
	public int getSubsidize() {
		return subsidize;
	}
	public void setSubsidize(int subsidize) {
		this.subsidize = subsidize;
	}
	public int getOtherFunds() {
		return otherFunds;
	}
	public void setOtherFunds(int otherFunds) {
		this.otherFunds = otherFunds;
	}
	public int getTotalFunds() {
		return totalFunds;
	}
	public void setTotalFunds(int totalFunds) {
		this.totalFunds = totalFunds;
	}
	public String getAchiForm() {
		return achiForm;
	}
	public void setAchiForm(String achiForm) {
		this.achiForm = achiForm;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Budget> getBudgets() {
		return budgets;
	}
	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}
	public List<Achievement> getAchievements() {
		return achievements;
	}
	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}
	
	
}
