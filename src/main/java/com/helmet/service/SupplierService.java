package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.Supplier;

/**
 * 供应商Service
 * 
 * @author Helmet
 * 2018年8月20日
 */
public interface SupplierService {
	
	/**
	 * 分页获取供应商数据
	 * @param supplier 搜索条件
	 * @param page 
	 * @param pageSize
	 * @param direction 排序：降序或者升序
	 * @param properties 排序条件
	 * @return
	 */
	public List<Supplier>  getSuppliers(Supplier supplier,Integer page,Integer pageSize,Direction direction,String...properties);
	
	
	/**
	 * 统计获取到的供应商数据的数量
	 * @param supplier
	 * @return
	 */
	public Long count(Supplier supplier);
	
	
	/**
	 * 保存一个供应商信息
	 * @param supplier
	 */
	public void save(Supplier supplier);
	
	
	/**
	 * 删除一个供应商信息
	 * @param supplierId
	 */
	public void delete(int supplierId);
	
	/**
	 * 通过id获取供应商信息
	 * @param supplierId
	 * @return
	 */
	public Supplier getSupplierById(int supplierId);
	
	/**
	 * 通过名字模糊查询获取供应商
	 * @param name
	 * @return
	 */
	public List<Supplier> getSupplierByName(String name);

}
