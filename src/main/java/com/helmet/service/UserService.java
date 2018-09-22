package com.helmet.service;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.User;

/**
 * 用户Service
 * 
 * @author Helmet
 * 2018年5月30日
 */
public interface UserService {

	
	/**
	 * 根据用户名查找用户实体
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);
	
	
	/**
	 * 获取符合条件的所有用户,并按分页大小返回
	 * 
	 * @param user   查询条件
	 * @param page	 当前页
	 * @param pageSize	 页数大小
	 * @param direction		 排序
	 * @param propetries 	属性
	 * @return
	 */
	public List<User> getUserList(User user,Integer page,Integer pageSize,Direction direction,String...propetries);
	
	/**
	 * 获取符合条件的用户总数
	 * 
	 * @param user 	条件
	 * @return
	 */
	public Long countUser(User user);
	
	/**
	 * 添加或者修改用户
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * 删除用户
	 * @param deleteId
	 */
	public void deleteUser(Integer deleteId);
	
	/**
	 * 通过userId获取user实体
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId);
	
}
