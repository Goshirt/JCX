package com.helmet.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.helmet.entity.Log;
import com.helmet.repository.LogRepository;
import com.helmet.service.LogService;
import com.helmet.service.UserService;
import com.helmet.util.StringUtil;

@Service("logService")
public class LogServiceImpl implements LogService{
	
	@Resource
	private LogRepository logRepository;
	
	@Resource
	private UserService userService;

	@Override
	public void log(Log log) {
		//设置记录日志的时间
		log.setDate(new Date());
		//设置日志的操作用户
		log.setUser(userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal()));
		logRepository.save(log);
		
	}

	@Override
	public List<Log> getLogList(Log log, Integer page, Integer pageSize, Direction direction, String... propetries) {
		//把分页信息封装到Pageable(sprint Data 工具类)类，默认第一页是0,所以需要把传递的page-1
		Pageable pageable=new PageRequest(page-1, pageSize);
		//每一页的Log集
		Page<Log> pageLogs=logRepository.findAll(new Specification<Log>() {
			
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (log!=null) {
					if (StringUtil.isNotEmpty(log.getType())) {
						predicate.getExpressions().add(cb.equal(root.get("type"), log.getType()));
					}
					if (log.getUser()!=null && StringUtil.isNotEmpty(log.getUser().getTrueName())) {
						predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
					}
					if (log.getStartTime()!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("date"), log.getStartTime()));
					}
					if (log.getEndTime()!=null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("date"), log.getEndTime()));
					}
				}
				return predicate;
			}
		}, pageable);
		return pageLogs.getContent();
	}

	@Override
	public Long count(Log log) {
		Long count=logRepository.count(new Specification<Log>() {
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (log!=null) {
					if (StringUtil.isNotEmpty(log.getType())) {
						predicate.getExpressions().add(cb.equal(root.get("type"), log.getType()));
					}
					if (log.getUser()!=null && StringUtil.isNotEmpty(log.getUser().getTrueName())) {
						predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
					}
					if (log.getStartTime()!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("date"), log.getStartTime()));
					}
					if (log.getEndTime()!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("date"), log.getEndTime()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

}
