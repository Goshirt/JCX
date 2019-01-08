package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.Customer;


/**
 *客户Repository
 * 
 * @author Helmet
 * 2018年8月20日
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> ,JpaSpecificationExecutor<Customer>{
	
	@Query(value="select * from t_customer where name like ?1",nativeQuery=true)
	public List<Customer> getCustomerByName(String name);

}
