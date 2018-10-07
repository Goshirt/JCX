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

import com.helmet.entity.Supplier;
import com.helmet.repository.SupplierRepository;
import com.helmet.service.SupplierService;
import com.helmet.util.StringUtil;

/**
 * supperlierService接口实现
 * 
 * @author Helmet
 * 2018年8月20日
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService{
	
	@Resource
	private SupplierRepository supplierRepository;

	
	
	@Override
	public List<Supplier> getSuppliers(Supplier supplier, Integer page, Integer pageSize, Direction direction,String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize);
		Page<Supplier> pageSuppliers=supplierRepository.findAll(new Specification<Supplier>() {
			
			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (supplier!=null) {
					if (StringUtil.isNotEmpty(supplier.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+supplier.getName()+"%"));
					}
				}
				return predicate;
			}
		}, pageable);

		return pageSuppliers.getContent();
	}
	
	

	@Override
	public Long count(Supplier supplier) {
		Long count=supplierRepository.count(new Specification<Supplier>() {

			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (supplier!=null) {
					if (StringUtil.isNotEmpty(supplier.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+supplier.getName()+"%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	
	
	@Override
	public void save(Supplier supplier) {
		supplierRepository.save(supplier);
	}

	
	
	@Override
	public void delete(int supplierId) {
		supplierRepository.delete(supplierId);
	}

	@Override
	public Supplier getSupplierById(int supplierId) {
		return supplierRepository.getOne(supplierId);
	}



	@Override
	public List<Supplier> getSupplierByName(String name) {
		return supplierRepository.getSupplierByName(name);
	}

}
