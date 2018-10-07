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

import com.helmet.entity.Goods;
import com.helmet.repository.GoodsRepository;
import com.helmet.service.GoodsService;
import com.helmet.util.StringUtil;

/**
 * 商品Service实现
 * 
 * @author Helmet
 * 2018年8月23日
 */

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public boolean isGoodsTypeHaveGoods(Integer goodsTypeId) {
		Long count=goodsRepository.countGoodsByGoodsTypeId(goodsTypeId);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}


	@Override
	public List<Goods> getGoodsList(Goods goods, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize);
		Page<Goods> pageGoods=goodsRepository.findAll(new Specification<Goods>() {
			
			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(goods!=null){
					if (StringUtil.isNotEmpty(goods.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+goods.getName()+"%"));
					}
					if (goods.getGoodsType()!=null && goods.getGoodsType().getGoodsTypeId()!=null && goods.getGoodsType().getGoodsTypeId()!=1) {
						predicate.getExpressions().add(cb.equal(root.get("goodsType").get("goodsTypeId"), goods.getGoodsType().getGoodsTypeId()));
					}
					if (StringUtil.isNotEmpty(goods.getCodeOrName())) {
						predicate.getExpressions().add(cb.or(cb.like(root.get("name"), "%"+goods.getName()+"%"),cb.like(root.get("code"), "%"+goods.getCode()+"%")));
					}
				}
				return predicate;
			}
		}, pageable);
		return pageGoods.getContent();
	}


	@Override
	public Long count(Goods goods) {
		Long count=goodsRepository.count(new Specification<Goods>() {

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(goods!=null){
					if (StringUtil.isNotEmpty(goods.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+goods.getName()+"%"));
					}
					if (goods.getGoodsType()!=null && goods.getGoodsType().getGoodsTypeId()!=null && goods.getGoodsType().getGoodsTypeId()!=1) {
						predicate.getExpressions().add(cb.equal(root.get("goodsType").get("goodsTypeId"), goods.getGoodsType().getGoodsTypeId()));
					}
					if (StringUtil.isNotEmpty(goods.getCodeOrName())) {
						predicate.getExpressions().add(cb.or(cb.like(root.get("name"), "%"+goods.getName()+"%"),cb.like(root.get("code"), "%"+goods.getCode()+"%")));
					}
				}
				return predicate;
			}
		});
		return count;
	}


	@Override
	public void save(Goods goods) {
		goodsRepository.save(goods);
	}


	@Override
	public void delete(int goodsId) {
		goodsRepository.delete(goodsId);
	}


	@Override
	public Goods getGoodsByGoodsId(int goodsId) {
		return goodsRepository.getOne(goodsId);
	}


	@Override
	public String getMaxCode() {
		return goodsRepository.getMaxCode();
	}


	@Override
	public List<Goods> getGoodsListWithNoInventoryQuantity(String codeOrName, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize);
		Page<Goods> pageGoods=goodsRepository.findAll(new Specification<Goods>() {
			
			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (StringUtil.isNotEmpty(codeOrName)) {
					//按照code或者name模糊查询
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+codeOrName+"%"), cb.like(root.get("name"), "%"+codeOrName+"%")));
				}
				//库存为0
				predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0));
				return predicate;
			}
		}, pageable);
		return pageGoods.getContent();
	}


	@Override
	public Long countGoodsListWithNoInventoryQuantity(String codeOrName) {
		Long count=goodsRepository.count(new Specification<Goods>() {

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (StringUtil.isNotEmpty(codeOrName)) {
					//按照code或者name模糊查询
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+codeOrName+"%"), cb.like(root.get("name"), "%"+codeOrName+"%")));
				}
				//库存为0
				predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0));
				return predicate;
			}
		});
		return count;
	}


	@Override
	public List<Goods> getGoodsListWithHaveInventoryQuantity(String codeOrName, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize);
		Page<Goods> pageGoods=goodsRepository.findAll(new Specification<Goods>() {
			
			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (StringUtil.isNotEmpty(codeOrName)) {
					//按照code或者name模糊查询
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+codeOrName+"%"), cb.like(root.get("name"), "%"+codeOrName+"%")));
				}
				//库存大于0
				predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0));
				return predicate;
			}
		}, pageable);
		return pageGoods.getContent();
	}


	@Override
	public Long countGoodsListWithHaveInventoryQuantity(String codeOrName) {
		Long count=goodsRepository.count(new Specification<Goods>() {

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (StringUtil.isNotEmpty(codeOrName)) {
					//按照code或者name模糊查询
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+codeOrName+"%"), cb.like(root.get("name"), "%"+codeOrName+"%")));
				}
				//库存大于0
				predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0));
				return predicate;
			}
		});
		return count;
	}

}
