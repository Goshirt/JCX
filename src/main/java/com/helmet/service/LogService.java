package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.Log;

/**
 * 日志Service
 * 
 * @author Helmet
 * 2018年8月18日
 */
public interface LogService {
	
	/**
	 * 记录日志
	 * @param log
	 */
	public void log(Log log);
	
	/**
	 * 获取分页的日列表，并且可以根据传入的参数进行搜索显示
	 * @param log
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propetries 
	 * @return
	 */
	public List<Log> getLogList(Log log,Integer page,Integer pageSize,Direction direction,String...propetries);
	
	/**
	 * 统计日记列表数
	 * @param log
	 * @return
	 */
	public Long count(Log log);

}
