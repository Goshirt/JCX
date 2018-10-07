package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.helmet.entity.Customer;


/**
 *客户Repository
 * 
 * @author Helmet
 * 2018年8月20日
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> ,JpaSpecificationExecutor<Customer>{

}
