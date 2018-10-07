package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.helmet.entity.User;
import com.helmet.repository.UserRepository;
import com.helmet.service.UserService;
import com.helmet.util.StringUtil;


/**
 * 用户service是实现类
 * 
 * @author Helmet
 * 2018年5月30日
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserRepository userRepository;
	
	@Override
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUserName(userName);
	}

	/**
	 * 当使用spring data jpa 进行复杂查询时，需要使用到下面的方法，简单查询可以直接在Repository层写sql语句
	 */
	@Override
	public List<User> getUserList(User user, Integer page, Integer pageSize, Direction direction,
			String... propetries) {
		//把分页信息封装到Pageable(sprint Data 工具类)类，默认第一页是0,所以需要把传递的page-1
		Pageable pageable=new PageRequest(page-1, pageSize);
		//每一页的User集
		Page<User> pageUser=userRepository.findAll(new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (user!=null) {
					if (StringUtil.isNotEmpty(user.getUserName())) {
						//用户名的模糊查询
						predicate.getExpressions().add(cb.like(root.get("userName"), "%"+user.getUserName()+"%"));
					}
					//超级管理员的信息除外
					predicate.getExpressions().add(cb.notEqual(root.get("userId"), 1));
				}
				return predicate;
			}
		}, pageable);
		return pageUser.getContent();
	}

	@Override
	public Long countUser(User user) {
		Long count=userRepository.count(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (user!=null) {
					if (StringUtil.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(cb.like(root.get("userName"), "%"+user.getUserName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("userId"), 1));
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer deleteId) {
		userRepository.delete(deleteId);
	}

	@Override
	public User getUserById(Integer userId) {
		return userRepository.findOne(userId);
	}

	
}
