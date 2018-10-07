package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色实体
 * 
 * @author Helmet
 * 2018年5月29日
 */
@Entity
@Table(name="t_role")
public class Role {

	@Id
	@GeneratedValue
	private Integer roleId;
	
	@Column(length=50)
	private String roleName;
	
	@Column(length=1000)
	private String remark;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", remark=" + remark + "]";
	}
	
	
}
