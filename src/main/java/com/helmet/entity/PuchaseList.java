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
 * 进货单实体
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Entity
@Table(name="t_puchaseList")
public class PuchaseList {
	@Id
	@GeneratedValue
	private Integer puchaseListId;    //编号
	
	@Column(length=100)
	private String puchaseNumber;    //进货单号
	
	@ManyToOne
	@JoinColumn(name="suppplierId")
	private Supplier supplier;    //供应商
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date puchaseDate;    //进货日期
	
	private float shouldPayMoney;    //应付金额
	
	private float truePayMoney;     //实付金额
	
	private Integer state;    //交易状态    1、已付钱；   2、未付钱
	
	@Transient
	private Date bPuchaseDate;  //搜索起始时间，不映射数据库
	
	
	@Transient
	private Date ePuchaseDate;  //搜索结束时间，不映射数据库
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;    //操作用户
	
	@Column(length=1000)
	private String remark;    //备注

	public Integer getPuchaseListId() {
		return puchaseListId;
	}

	public void setPuchaseListId(Integer puchaseListId) {
		this.puchaseListId = puchaseListId;
	}

	public String getPuchaseNumber() {
		return puchaseNumber;
	}

	public void setPuchaseNumber(String puchaseNumber) {
		this.puchaseNumber = puchaseNumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	//使用自定义的日期格式，获取数据库中的date类型的数据，并转化为json中的时间格式
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getPuchaseDate() {
		return puchaseDate;
	}

	public void setPuchaseDate(Date puchaseDate) {
		this.puchaseDate = puchaseDate;
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

	public Date getbPuchaseDate() {
		return bPuchaseDate;
	}

	public void setbPuchaseDate(Date bPuchaseDate) {
		this.bPuchaseDate = bPuchaseDate;
	}

	public Date getePuchaseDate() {
		return ePuchaseDate;
	}

	public void setePuchaseDate(Date ePuchaseDate) {
		this.ePuchaseDate = ePuchaseDate;
	}

	@Override
	public String toString() {
		return "PuchaseList [puchaseListId=" + puchaseListId + ", puchaseNumber=" + puchaseNumber + ", supplier="
				+ supplier + ", puchaseDate=" + puchaseDate + ", shouldPayMoney=" + shouldPayMoney + ", truePayMoney="
				+ truePayMoney + ", state=" + state + ", user=" + user + ", remark=" + remark + "]";
	}
	
	
	
	
}
