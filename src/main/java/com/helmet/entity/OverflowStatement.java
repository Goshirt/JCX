package com.helmet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.helmet.util.CustomDateTimeSerializer;


/**
 * 报溢单实体
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Entity
@Table(name="t_overflowStatement")
public class OverflowStatement {
	@Id
	@GeneratedValue
	private Integer overflowStatementId;    //编号
	
	@Column(length=100)
	private String overflowNumber;    //报溢单号
	
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;    //客户
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;    //开单日期
	
	@Transient
	private Date bCreateDate;  //搜索起始时间，不映射数据库
	
	
	@Transient
	private Date eCreateDate;  //搜索结束时间，不映射数据库
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;    //操作用户
	
	@Column(length=1000)
	private String remark;    //备注

	public Integer getOverflowStatementId() {
		return overflowStatementId;
	}

	public void setOverflowStatementId(Integer overflowStatementId) {
		this.overflowStatementId = overflowStatementId;
	}

	public String getOverflowNumber() {
		return overflowNumber;
	}

	public void setOverflowNumber(String overflowNumber) {
		this.overflowNumber = overflowNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getbCreateDate() {
		return bCreateDate;
	}

	public void setbCreateDate(Date bCreateDate) {
		this.bCreateDate = bCreateDate;
	}

	public Date geteCreateDate() {
		return eCreateDate;
	}

	public void seteCreateDate(Date eCreateDate) {
		this.eCreateDate = eCreateDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "OverflowStatement [overflowStatementId=" + overflowStatementId + ", overflowNumber=" + overflowNumber + ", customer="
				+ customer + ", createDate=" + createDate + ", bCreateDate=" + bCreateDate + ", eCreateDate="
				+ eCreateDate + ", user=" + user + ", remark=" + remark + "]";
	}


	
	
	
	
}
