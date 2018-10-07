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


/**
 * 退货单实体
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Entity
@Table(name="t_returnList")
public class ReturnList {
	@Id
	@GeneratedValue
	private Integer returnListId;    //编号
	
	@Column(length=100)
	private String returnNumber;    //退货单号
	
	@ManyToOne
	@JoinColumn(name="suppplierId")
	private Supplier supplier;    //供应商
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate;    //退货日期
	
	private float shouldPayMoney;    //应付金额
	
	private float truePayMoney;     //实付金额
	
	private Integer state;    //交易状态    1、已付钱；   2、未付钱
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;    //操作用户
	
	@Column(length=1000)
	private String remark;    //备注

	public Integer getReturnListId() {
		return returnListId;
	}

	public void setReturnListId(Integer returnListId) {
		this.returnListId = returnListId;
	}

	public String getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	@Override
	public String toString() {
		return "ReturnList [returnListId=" + returnListId + ", returnNumber=" + returnNumber + ", supplier="
				+ supplier + ", returnDate=" + returnDate + ", shouldPayMoney=" + shouldPayMoney + ", truePayMoney="
				+ truePayMoney + ", state=" + state + ", user=" + user + ", remark=" + remark + "]";
	}
	
	
	
	
}
