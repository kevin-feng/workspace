package org.tsc.bean;

import java.util.Date;

import org.tsc.core.bean.IdEntity;

/*
 * 申报项目的成员类
 * 包括 项目负责人 和 项目组普通成员
 *  
 **/

public class Member extends IdEntity {
	
	private String name;//姓名
	private String sex;//性别
	private String birthday;//出生年月
	private String mobileNo;//手机号
	private String email;//邮箱
	private String majorPosition;//专业技术职务
	private String adminPosition;//行政职务
	private String major;//专业	
	private String resume;//临床工作及教学简历
	private String researchResult;//主要研究成果
	private String organization;//工作单位
	private String finishWork;//实际承担和完成的课题研究工作  (在结题材料处用到)
	private int type;//类型（默认为0）   0为项目组普通成员  1为项目负责人
	private Project project;//该成员对应的项目
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMajorPosition() {
		return majorPosition;
	}
	public void setMajorPosition(String majorPosition) {
		this.majorPosition = majorPosition;
	}
	public String getAdminPosition() {
		return adminPosition;
	}
	public void setAdminPosition(String adminPosition) {
		this.adminPosition = adminPosition;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getResearchResult() {
		return researchResult;
	}
	public void setResearchResult(String researchResult) {
		this.researchResult = researchResult;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getFinishWork() {
		return finishWork;
	}
	public void setFinishWork(String finishWork) {
		this.finishWork = finishWork;
	}

	
	
	
	
}
