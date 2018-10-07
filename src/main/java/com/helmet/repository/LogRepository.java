package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.helmet.entity.Log;

/**
 * 日志操作 jpa
 * 
 * @author Helmet
 * 2018年8月18日
 */
public interface LogRepository extends JpaRepository<Log, Integer>,JpaSpecificationExecutor<Log>{

}
