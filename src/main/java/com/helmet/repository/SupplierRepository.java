package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.Supplier;


/**
 *供应商Repository
 * 
 * @author Helmet
 * 2018年8月20日
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer> ,JpaSpecificationExecutor<Supplier>{
	
	/**
	 * 通过名字模糊查询获取供应商
	 * @param name
	 * @return
	 */
	@Query(value="SELECT * FROM t_supplier WHERE NAME LIKE ?1",nativeQuery=true)
	public List<Supplier> getSupplierByName(String name);
}
