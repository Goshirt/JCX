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
 * 销售单实体
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Entity
@Table(name="t_saleList")
public class SaleList {
	@Id
	@GeneratedValue
	private Integer saleListId;    //编号
	
	@Column(length=100)
	private String saleNumber;    //销售单号
	
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;    //客户
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date saleDate;    //销售日期
	
	private float shouldPayMoney;    //应付金额
	
	private float truePayMoney;     //实付金额
	
	private Integer state;    //交易状态    1、已付钱；   2、未付钱
	
	@Transient
	private Date bSaleDate;  //搜索起始时间，不映射数据库
	
	
	@Transient
	private Date eSaleDate;  //搜索结束时间，不映射数据库
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;    //操作用户
	
	@Column(length=1000)
	private String remark;    //备注

	public Integer getSaleListId() {
		return saleListId;
	}

	public void setSaleListId(Integer saleListId) {
		this.saleListId = saleListId;
	}

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	//使用自定义的日期格式，获取数据库中的date类型的数据，并转化为json中的时间格式
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
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

	public Date getbSaleDate() {
		return bSaleDate;
	}

	public void setbSaleDate(Date bSaleDate) {
		this.bSaleDate = bSaleDate;
	}

	public Date geteSaleDate() {
		return eSaleDate;
	}

	public void seteSaleDate(Date eSaleDate) {
		this.eSaleDate = eSaleDate;
	}

	@Override
	public String toString() {
		return "SaleList [saleListId=" + saleListId + ", saleNumber=" + saleNumber + ", customer="
				+ customer + ", saleDate=" + saleDate + ", shouldPayMoney=" + shouldPayMoney + ", truePayMoney="
				+ truePayMoney + ", state=" + state + ", user=" + user + ", remark=" + remark + "]";
	}
	
	
	
	
}
