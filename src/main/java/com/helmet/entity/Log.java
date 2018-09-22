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
 * 日志实体
 * 记录用户的操作
 * @JsonSerialize(using=CustomDateTimeSerializer.class) // 在json数据中，使用自定义的类的格式获取数据库中返回的日期格式
 * @author Helmet
 * 2018年8月18日
 */
@Entity
@Table(name="t_log")
public class Log {
	
	public final static String LOGIN_ACTION="登录操作";
	public final static String LOGOUT_ACTION="退出操作";
	public final static String SELECT_ACTION="查询操作";
	public final static String ADD_ACTION="添加操作";
	public final static String UPDATE_ACTION="更新操作";
	public final static String DELETE_ACTION="删除操作";
	
	
	@Id
	@GeneratedValue
	private int logId;	//实体Id
	
	@Column(length=100)
	private String type; //日志类型
	
	@Column(length=1000)
	private String content;	//日志内容
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	
	/**
	 * @Temporal(TemporalType.TIMESTAMP),日期时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;	//操作时间

	@Transient
	private Date startTime;//在系统日志中搜索的起始时间，不映射到数据库
	
	@Transient
	private Date endTime;	//在系统日志中的结束时间，不映射到数据库
	
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 使用自定义的CustomDateTimeSerializer的格式保存时间格式
	 * @return
	 */
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public Log() {
		super();
	}

	public Log(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	
	

}
