package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="t_customer")
public class Customer {

	@Id
	@GeneratedValue
	private Integer customerId;   //供应商Id
	
	@NotEmpty(message="供应商名字不能为空")
	@Column(length=200)
	private String name;   //供应商名字
	
	@NotEmpty(message="联系人姓名不能为空")
	@Column(length=50)
	private String concatName;   //联系人姓名
	
	@NotEmpty(message="联系人电话不能为空")
	@Column(length=100)
	private String phoneNumber;   //联系人电话
	
	@Column(length=400)
	private String address;  //联系地址
	
	@NotEmpty(message="联系地址不能为空")
	@Column(length=1000)
	private String remark;   //备注

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConcatName() {
		return concatName;
	}

	public void setConcatName(String concatName) {
		this.concatName = concatName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", concatName=" + concatName + ", phoneNumber="
				+ phoneNumber + ", address=" + address + ", remark=" + remark + "]";
	}
	
	
}
