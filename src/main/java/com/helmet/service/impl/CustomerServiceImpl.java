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

import com.helmet.entity.Customer;
import com.helmet.repository.CustomerRepository;
import com.helmet.service.CustomerService;
import com.helmet.util.StringUtil;

/**
 * customerService接口实现
 * 
 * @author Helmet
 * 2018年8月20日
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	
	@Resource
	private CustomerRepository customerRepository;

	
	
	@Override
	public List<Customer> getCustomers(Customer customer, Integer page, Integer pageSize, Direction direction,String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize);
		Page<Customer> pageCustomers=customerRepository.findAll(new Specification<Customer>() {
			
			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (customer!=null) {
					if (StringUtil.isNotEmpty(customer.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+customer.getName()+"%"));
					}
				}
				return predicate;
			}
		}, pageable);

		return pageCustomers.getContent();
	}
	
	

	@Override
	public Long count(Customer customer) {
		Long count=customerRepository.count(new Specification<Customer>() {

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (customer!=null) {
					if (StringUtil.isNotEmpty(customer.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+customer.getName()+"%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	
	
	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

	
	
	@Override
	public void delete(int customerId) {
		customerRepository.delete(customerId);
	}

	@Override
	public Customer getCustomerById(int customerId) {
		return customerRepository.getOne(customerId);
	}



	@Override
	public List<Customer> getCustomerByName(String name) {
		return customerRepository.getCustomerByName(name);
	}

}
