package org.tsc.bean;

import org.tsc.core.bean.IdEntity;

public class User extends IdEntity {
	private String userName;//用户名
	private String password;//密码
	private String trueName;//该用户真实姓名
	private String email;//邮箱
	private String userRole;//该用户的角色    DECLARER申报者   EXPERT专家    ADMIN管理员
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
