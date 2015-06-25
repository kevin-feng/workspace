package org.tsc.bean;

import java.util.List;

import org.tsc.core.bean.IdEntity;
/**
 * 立项的项目对应的中期检查类
 * 
 */
public class Interim extends IdEntity {

	private String workSituation;//中期检查的工作情况
	private Project project;//中期检查对应的项目
	private List<Achievement> achievements;//中期检查对应的阶段性成果；
	
	public String getWorkSituation() {
		return workSituation;
	}
	public void setWorkSituation(String workSituation) {
		this.workSituation = workSituation;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Achievement> getAchievements() {
		return achievements;
	}
	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}
	
	
}
