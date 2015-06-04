package org.tsc.bean;

import org.tsc.core.bean.IdEntity;

public class Review extends IdEntity {

	private String reviewResult;//评审结果
	private int suggestion;//评审建议       0为立项资助   1为立项不资助   2为不立项
	private String remark;//评语 (用来存放未立项的评语  或  中期的专家评审意见  或 结题材料的专家评审意见)
	private String eduRemark;//教育厅评审意见
	private Integer ranking;//排名
	private String reviewIndex1;//评审指标1(优 一般 差)    选题明确，为临床教学关注点，利于解决教学的实际问题。
	private String reviewIndex2;//评审指标2(优 一般 差)    研究设计的科学性，内容的针对性，目标的可观测性。
	private String reviewIndex3;//评审指标3(优 一般 差)    研究方法的可行性，具体可操作性，预期目的可及性。
	private String reviewIndex4;//评审指标4(优 一般 差)    项目的特色，对全省临床教学基地的推广作用。
	private Long project;//该评审对应的项目
	private User expert;//该评审对应的专家
	private Interim interim;//该评审对应的中期检查
	private Termination termination;//该评审对应的结题材料
	
	public String getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getReviewIndex1() {
		return reviewIndex1;
	}
	public void setReviewIndex1(String reviewIndex1) {
		this.reviewIndex1 = reviewIndex1;
	}
	public String getReviewIndex2() {
		return reviewIndex2;
	}
	public void setReviewIndex2(String reviewIndex2) {
		this.reviewIndex2 = reviewIndex2;
	}
	public String getReviewIndex3() {
		return reviewIndex3;
	}
	public void setReviewIndex3(String reviewIndex3) {
		this.reviewIndex3 = reviewIndex3;
	}
	public String getReviewIndex4() {
		return reviewIndex4;
	}
	public void setReviewIndex4(String reviewIndex4) {
		this.reviewIndex4 = reviewIndex4;
	}
	public Long getProject() {
		return project;
	}
	public void setProject(Long project) {
		this.project = project;
	}
	public User getExpert() {
		return expert;
	}
	public void setExpert(User expert) {
		this.expert = expert;
	}
	public String getEduRemark() {
		return eduRemark;
	}
	public void setEduRemark(String eduRemark) {
		this.eduRemark = eduRemark;
	}
	public Interim getInterim() {
		return interim;
	}
	public void setInterim(Interim interim) {
		this.interim = interim;
	}
	public Termination getTermination() {
		return termination;
	}
	public void setTermination(Termination termination) {
		this.termination = termination;
	}
	public int getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(int suggestion) {
		this.suggestion = suggestion;
	}
	
	
	
}
