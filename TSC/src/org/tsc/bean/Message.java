package org.tsc.bean;

import java.util.Date;
import java.util.List;

import org.tsc.core.bean.IdEntity;
/*
 *消息类，包括（最新通知，工作动态，政策规定等等） 
 *
 **/
public class Message extends IdEntity {
	private String title;//消息的标题
	private String source;//消息的来源
	private String author;//消息的作者
	private String text;//消息的正文
	private Accessory accessory;//消息的附件
	private int type;//消息的类型（默认值为0），0为通知文件，1为工作动态，2为政策规定，3为理论探索，4为教学课程
					 //5为基地评审，6为结果公示, 7为下载中心
	private int count;//某篇消息被点击的次数

	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Accessory getAccessory() {
		return accessory;
	}
	public void setAccessory(Accessory accessory) {
		this.accessory = accessory;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
