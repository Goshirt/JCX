package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.helmet.entity.Goods;
import com.helmet.entity.ReturnList;
import com.helmet.entity.ReturnListGoods;
import com.helmet.repository.GoodsRepository;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.repository.ReturnListGoodsRepository;
import com.helmet.repository.ReturnListRepository;
import com.helmet.service.ReturnListService;
import com.helmet.util.StringUtil;

/**
 * 退货单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("returnListService")
public class ReturnListServiceImpl implements ReturnListService{
	
	@Resource
	private ReturnListRepository returnListRepository;
	
	@Resource
	private ReturnListGoodsRepository returnListGoodsRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public String getTodayMaxReturnNumber() {
		return returnListRepository.getTodayMaxReturnNumber();
	}

	@Override
	@Transactional
	public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList) {
		for (ReturnListGoods returnListGoods : returnListGoodsList) {
			//保存每一个退货单商品的商品类型
			returnListGoods.setGoodsType(goodsTypeRepository.findOne(returnListGoods.getGoodsTypeId()));  
			 //添加退货单商品和退货单的关联
			returnListGoods.setReturnList(returnList);
			//保存退货单商品到数据库
			returnListGoodsRepository.save(returnListGoods);
			
			//获取退货单商品对应的仓库中的商品信息
			Goods goods = goodsRepository.findOne(returnListGoods.getGoodsId());
			//更新仓库中商品的库存数量（加上本次退货的数量）
			goods.setInventoryQuantity(goods.getInventoryQuantity()-returnListGoods.getNum());
			/*//更新仓库中商品的上次退货价(是本次退货单中该商品的退货价)
			goods.setLastPuchasingPrice(returnListGoods.getPrice());
			//更新商品的状态
			goods.setState(2);
			//加上本次的退货价，计算平均价
			float returnPrice = (goods.getPurchasingPrice()*goods.getInventoryQuantity()+returnListGoods.getPrice()*returnListGoods.getNum())/(goods.getInventoryQuantity()+returnListGoods.getNum());
			//平均价保留两位小数，保存到数据库
			goods.setPurchasingPrice(MathUtil.float2Bit(returnPrice));*/
			goodsRepository.save(goods);
		}
		returnListRepository.save(returnList);
	}

	@Override
	public List<ReturnList> list(ReturnList returnList, Direction direction,
			String... propertis) {
		List<ReturnList> pageReturnList = returnListRepository.findAll(new Specification<ReturnList>() {
			
			@Override
			public Predicate toPredicate(Root<ReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (returnList != null) {
					if (StringUtil.isNotEmpty(returnList.getReturnNumber())) {
						predicate.getExpressions().add(cb.like(root.get("returnNumber"), "%"+returnList.getReturnNumber()+"%"));
					}
					if (returnList.getbReturnListDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("returnDate"), returnList.getbReturnListDate()));
					}
					if (returnList.geteReturnListDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("returnDate"), returnList.geteReturnListDate()));
					}
					if (returnList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), returnList.getState()));
					}
					if (returnList.getSupplier() != null && returnList.getSupplier().getSupplierId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("supplierId"), returnList.getSupplier().getSupplierId()));
						
					}
				}
				return predicate;
			}
		});
		return pageReturnList;
	}

	@Override
	public Long count(ReturnList returnList) {
		Long count = returnListRepository.count(new Specification<ReturnList>() {

			@Override
			public Predicate toPredicate(Root<ReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (returnList != null) {
					if (StringUtil.isNotEmpty(returnList.getReturnNumber())) {
						predicate.getExpressions().add(cb.like(root.get("returnNumber"), "%"+returnList.getReturnNumber()+"%"));
					}
					if (returnList.getbReturnListDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("returnDate"), returnList.getbReturnListDate()));
					}
					if (returnList.geteReturnListDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("returnDate"), returnList.geteReturnListDate()));
					}
					if (returnList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), returnList.getState()));
					}
					if (returnList.getSupplier() != null && returnList.getSupplier().getSupplierId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("supplierId"), returnList.getSupplier().getSupplierId()));
						
					}
				}
				return predicate;
			}
		});
		return count;
		
	}

	@Override
	@Transactional
	public void delete(Integer returnListId) {
		returnListGoodsRepository.deleteByReturnListId(returnListId);
		returnListRepository.delete(returnListId);
	}

	@Override
	@Transactional
	public void updateState(Integer returnListId) {
		returnListRepository.updateState(returnListId);
	}


}
