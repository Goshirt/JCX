package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.Customer;

/**
 * 客户Service
 * 
 * @author Helmet
 * 2018年8月20日
 */
public interface CustomerService {
	
	/**
	 * 分页获取客户数据
	 * @param customer 搜索条件
	 * @param page 
	 * @param pageSize
	 * @param direction 排序：降序或者升序
	 * @param properties 排序条件
	 * @return
	 */
	public List<Customer>  getCustomers(Customer customer,Integer page,Integer pageSize,Direction direction,String...properties);
	
	
	/**
	 * 统计获取到的客户数据的数量
	 * @param customer
	 * @return
	 */
	public Long count(Customer customer);
	
	
	/**
	 * 保存一个客户信息
	 * @param customer
	 */
	public void save(Customer customer);
	
	
	/**
	 * 删除一个客户信息
	 * @param customerId
	 */
	public void delete(int customerId);
	
	/**
	 * 通过id获取客户信息
	 * @param customerId
	 * @return
	 */
	public Customer getCustomerById(int customerId);

}
