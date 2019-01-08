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
 * 客户退货单实体
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Entity
@Table(name="t_customerReturnList")
public class CustomerReturnList {
	@Id
	@GeneratedValue
	private Integer customerReturnListId;    //编号
	
	@Column(length=100)
	private String customerReturnNumber;    //客户退货单号
	
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;    //客户
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date customerReturnDate;    //客户退货日期
	
	private float shouldPayMoney;    //应退金额
	
	private float truePayMoney;     //实退金额
	
	private Integer state;    //交易状态    1、已退钱；   2、未退钱
	
	@Transient
	private Date bCustomerReturnDate;  //搜索起始时间，不映射数据库
	
	
	@Transient
	private Date eCustomerReturnDate;  //搜索结束时间，不映射数据库
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;    //操作用户
	
	@Column(length=1000)
	private String remark;    //备注

	public Integer getCustomerReturnListId() {
		return customerReturnListId;
	}

	public void setCustomerReturnListId(Integer customerReturnListId) {
		this.customerReturnListId = customerReturnListId;
	}

	public String getCustomerReturnNumber() {
		return customerReturnNumber;
	}

	public void setCustomerReturnNumber(String customerReturnNumber) {
		this.customerReturnNumber = customerReturnNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	//使用自定义的日期格式，获取数据库中的date类型的数据，并转化为json中的时间格式
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getCustomerReturnDate() {
		return customerReturnDate;
	}

	public void setCustomerReturnDate(Date customerReturnDate) {
		this.customerReturnDate = customerReturnDate;
	}

	public float getShouldPayMoney() {
		return shouldPayMoney;
	}

	public void setShouldPayMoney(float shouldPayMoney) {
		this.shouldPayMoney = shouldPayMoney;
	}

	public float getTruePayMoney() {
		return truePayMoney;
	}

	public void setTruePayMoney(float truePayMoney) {
		this.truePayMoney = truePayMoney;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Date getbCustomerReturnDate() {
		return bCustomerReturnDate;
	}

	public void setbCustomerReturnDate(Date bCustomerReturnDate) {
		this.bCustomerReturnDate = bCustomerReturnDate;
	}

	public Date geteCustomerReturnDate() {
		return eCustomerReturnDate;
	}

	public void seteCustomerReturnDate(Date eCustomerReturnDate) {
		this.eCustomerReturnDate = eCustomerReturnDate;
	}

	@Override
	public String toString() {
		return "CustomerReturnList [customerReturnListId=" + customerReturnListId + ", customerReturnNumber=" + customerReturnNumber + ", customer="
				+ customer + ", customerReturnDate=" + customerReturnDate + ", shouldPayMoney=" + shouldPayMoney + ", truePayMoney="
				+ truePayMoney + ", state=" + state + ", user=" + user + ", remark=" + remark + "]";
	}
	
	
	
	
}
