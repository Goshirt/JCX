package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户实体
 * 
 * @author Helmet
 * 2018年5月29日
 */
@Entity
@Table(name="t_user")
public class User {
	@Id
	@GeneratedValue
	private Integer userId;
	
	@NotEmpty(message="用户名不能为空")
	@Column(length=50)
	private String userName;
	
	@NotEmpty(message="密码不能为空")
	@Column(length=50)
	private String password;
	
	@Column(length=50)
	private String trueName;
	
	@Column(length=1000)
	private String remark;
	
	//用户拥有的角色      不映射到数据库中
	@Transient
	private String haveRole;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHaveRole() {
		return haveRole;
	}
	public void setHaveRole(String haveRole) {
		this.haveRole = haveRole;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", trueName=" + trueName
				+ ", remark=" + remark + ", haveRole=" + haveRole + "]";
	}
	
	
}
