package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.helmet.entity.Goods;
import com.helmet.entity.OverflowStatement;
import com.helmet.entity.OverflowStatementGoods;
import com.helmet.repository.GoodsRepository;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.repository.OverflowStatementGoodsRepository;
import com.helmet.repository.OverflowStatementRepository;
import com.helmet.service.OverflowStatementService;

/**
 * 报溢单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("overflowStatementService")
public class OverflowStatementServiceImpl implements OverflowStatementService{
	
	@Resource
	private OverflowStatementRepository overflowStatementRepository;
	
	@Resource
	private OverflowStatementGoodsRepository overflowStatementGoodsRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public String getTodayMaxOverflowNumber() {
		return overflowStatementRepository.getTodayMaxOverflowNumber();
	}

	@Override
	@Transactional
	public void save(OverflowStatement overflowStatement, List<OverflowStatementGoods> overflowStatementGoodsList) {
		for (OverflowStatementGoods overflowStatementGoods : overflowStatementGoodsList) {
			//保存每一个报溢单商品的商品类型
			overflowStatementGoods.setGoodsType(goodsTypeRepository.findOne(overflowStatementGoods.getGoodsTypeId()));  
			 //添加报溢单商品和报溢单的关联
			overflowStatementGoods.setOverflowStatement(overflowStatement);
			//保存报溢单商品到数据库
			overflowStatementGoodsRepository.save(overflowStatementGoods);
			//获取报溢单商品对应的仓库中的商品信息
			Goods goods = goodsRepository.findOne(overflowStatementGoods.getGoodsId());
			//更新仓库中商品的库存数量（减去本次报溢的数量）
			goods.setInventoryQuantity(goods.getInventoryQuantity()-overflowStatementGoods.getNum());
			goodsRepository.save(goods);
		}
		overflowStatementRepository.save(overflowStatement);
	}

	@Override
	public List<OverflowStatement> list(OverflowStatement overflowStatement, Direction direction,String... propertis) {
		List<OverflowStatement> overflowStatements = overflowStatementRepository.findAll(new Specification<OverflowStatement>() {
			
			@Override
			public Predicate toPredicate(Root<OverflowStatement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (overflowStatement != null) {
					if (overflowStatement.getbCreateDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate"), overflowStatement.getbCreateDate()));
					}
					if (overflowStatement.geteCreateDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate"), overflowStatement.geteCreateDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, propertis));
		return overflowStatements;
	}

	@Override
	public Long count(OverflowStatement overflowStatement) {
		Long count = overflowStatementRepository.count(new Specification<OverflowStatement>() {

			@Override
			public Predicate toPredicate(Root<OverflowStatement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (overflowStatement != null) {
					if (overflowStatement.getbCreateDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate"), overflowStatement.getbCreateDate()));
					}
					if (overflowStatement.geteCreateDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate"), overflowStatement.geteCreateDate()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	@Transactional
	public void delete(Integer overflowStatementId) {
		overflowStatementGoodsRepository.deleteByOverflowStatementId(overflowStatementId);
		overflowStatementRepository.delete(overflowStatementId);
	}

	
}
