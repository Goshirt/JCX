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
import com.helmet.entity.LossStatement;
import com.helmet.entity.LossStatementGoods;
import com.helmet.repository.GoodsRepository;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.repository.LossStatementGoodsRepository;
import com.helmet.repository.LossStatementRepository;
import com.helmet.service.LossStatementService;

/**
 * 报损单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("lossStatementService")
public class LossStatementServiceImpl implements LossStatementService{
	
	@Resource
	private LossStatementRepository lossStatementRepository;
	
	@Resource
	private LossStatementGoodsRepository lossStatementGoodsRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public String getTodayMaxLossNumber() {
		return lossStatementRepository.getTodayMaxLossNumber();
	}

	@Override
	@Transactional
	public void save(LossStatement lossStatement, List<LossStatementGoods> lossStatementGoodsList) {
		for (LossStatementGoods lossStatementGoods : lossStatementGoodsList) {
			//保存每一个报损单商品的商品类型
			lossStatementGoods.setGoodsType(goodsTypeRepository.findOne(lossStatementGoods.getGoodsTypeId()));  
			 //添加报损单商品和报损单的关联
			lossStatementGoods.setLossStatement(lossStatement);
			//保存报损单商品到数据库
			lossStatementGoodsRepository.save(lossStatementGoods);
			//获取报损单商品对应的仓库中的商品信息
			Goods goods = goodsRepository.findOne(lossStatementGoods.getGoodsId());
			//更新仓库中商品的库存数量（减去本次报损的数量）
			goods.setInventoryQuantity(goods.getInventoryQuantity()-lossStatementGoods.getNum());
			goodsRepository.save(goods);
		}
		lossStatementRepository.save(lossStatement);
	}

	@Override
	public List<LossStatement> list(LossStatement lossStatement,Direction direction,String... propertis) {
		List<LossStatement> lossStatements = lossStatementRepository.findAll(new Specification<LossStatement>() {
			
			@Override
			public Predicate toPredicate(Root<LossStatement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (lossStatement != null) {
					if (lossStatement.getbCreateDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate"), lossStatement.getbCreateDate()));
					}
					if (lossStatement.geteCreateDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate"), lossStatement.geteCreateDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, propertis));
		return lossStatements;
	}

	@Override
	public Long count(LossStatement lossStatement) {
		Long count = lossStatementRepository.count(new Specification<LossStatement>() {

			@Override
			public Predicate toPredicate(Root<LossStatement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (lossStatement != null) {
					if (lossStatement.getbCreateDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate"), lossStatement.getbCreateDate()));
					}
					if (lossStatement.geteCreateDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate"), lossStatement.geteCreateDate()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	@Transactional
	public void delete(Integer lossStatementId) {
		lossStatementGoodsRepository.deleteByLossStatementId(lossStatementId);
		lossStatementRepository.delete(lossStatementId);
	}

	
}
