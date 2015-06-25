package org.tsc.bean;

import org.tsc.core.bean.IdEntity;

/**
 *立项后的项目的 中期检查 和 结题材料 对应的阶段性成果类
 *@author fyxiang
 *
 */
public class Achievement extends IdEntity {
	
	private String title;//标题
	private String author;//作者
	private String achiForm;//成果形式
	private String publication;//刊物名或出版社
	private int wordNumber;//字数
	private String publishTime;//刊物发表时间
	private Interim interim;//成果对应的中期检查
	private Termination termination;//成果对应的结题材料
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAchiForm() {
		return achiForm;
	}
	public void setAchiForm(String achiForm) {
		this.achiForm = achiForm;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public int getWordNumber() {
		return wordNumber;
	}
	public void setWordNumber(int wordNumber) {
		this.wordNumber = wordNumber;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
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
	
}
