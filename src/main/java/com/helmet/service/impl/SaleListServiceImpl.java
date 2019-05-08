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
import com.helmet.entity.SaleList;
import com.helmet.entity.SaleListGoods;
import com.helmet.repository.GoodsRepository;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.repository.SaleListGoodsRepository;
import com.helmet.repository.SaleListRepository;
import com.helmet.service.SaleListService;
import com.helmet.util.MathUtil;
import com.helmet.util.StringUtil;

/**
 * 销售单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("saleListService")
public class SaleListServiceImpl implements SaleListService{
	
	@Resource
	private SaleListRepository saleListRepository;
	
	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public String getTodayMaxSaleNumber() {
		return saleListRepository.getTodayMaxSaleNumber();
	}

	@Override
	@Transactional
	public void save(SaleList saleList, List<SaleListGoods> saleListGoodsList) {
		for (SaleListGoods saleListGoods : saleListGoodsList) {
			//保存每一个销售单商品的商品类型
			saleListGoods.setGoodsType(goodsTypeRepository.findOne(saleListGoods.getGoodsTypeId()));  
			 //添加销售单商品和销售单的关联
			saleListGoods.setSaleList(saleList);
			//保存销售单商品到数据库
			saleListGoodsRepository.save(saleListGoods);
			
			//获取销售单商品对应的仓库中的商品信息
			Goods goods = goodsRepository.findOne(saleListGoods.getGoodsId());
			//更新仓库中商品的库存数量（减去本次销售的数量）
			goods.setInventoryQuantity(goods.getInventoryQuantity()-saleListGoods.getNum());
			//更新仓库中商品的上次进价(是本次销售单中该商品的进货价)
			goods.setLastPuchasingPrice(saleListGoods.getPrice());
			//更新商品的状态
			goods.setState(2);
			//加上本次的进货价，计算平均价
			float salePrice = (goods.getPurchasingPrice()*goods.getInventoryQuantity()+saleListGoods.getPrice()*saleListGoods.getNum())/(goods.getInventoryQuantity()+saleListGoods.getNum());
			//平均价保留两位小数，保存到数据库
			goods.setPurchasingPrice(MathUtil.float2Bit(salePrice));
			goodsRepository.save(goods);
		}
		saleListRepository.save(saleList);
	}

	@Override
	public List<SaleList> list(SaleList saleList, Direction direction,String... propertis) {
		List<SaleList> pageSaleList = saleListRepository.findAll(new Specification<SaleList>() {
			
			@Override
			public Predicate toPredicate(Root<SaleList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (saleList != null) {
					if (StringUtil.isNotEmpty(saleList.getSaleNumber())) {
						predicate.getExpressions().add(cb.like(root.get("saleNumber"), "%"+saleList.getSaleNumber()+"%"));
					}
					if (saleList.getCustomer()!= null && saleList.getCustomer().getCustomerId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("customer").get("customerId"), saleList.getCustomer().getCustomerId()));
					}
					if (saleList.getbSaleDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("saleDate"), saleList.getbSaleDate()));
					}
					if (saleList.geteSaleDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("saleDate"), saleList.geteSaleDate()));
					}
					if (saleList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), saleList.getState()));
					}
				}
				return predicate;
			}
		});
		return pageSaleList;
	}

	@Override
	public Long count(SaleList saleList) {
		Long count = saleListRepository.count(new Specification<SaleList>() {

			@Override
			public Predicate toPredicate(Root<SaleList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (saleList != null) {
					if (StringUtil.isNotEmpty(saleList.getSaleNumber())) {
						predicate.getExpressions().add(cb.like(root.get("saleNumber"), "%"+saleList.getSaleNumber()+"%"));
					}
					if (saleList.getCustomer()!= null && saleList.getCustomer().getCustomerId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("customer").get("customerId"), saleList.getCustomer().getCustomerId()));
					}
					if (saleList.getbSaleDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("saleDate"), saleList.getbSaleDate()));
					}
					if (saleList.geteSaleDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("saleDate"), saleList.geteSaleDate()));
					}
					if (saleList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), saleList.getState()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	@Transactional
	public void delete(Integer saleListId) {
		saleListGoodsRepository.deleteBySaleListId(saleListId);
		saleListRepository.delete(saleListId);
	}

	@Override
	@Transactional
	public void updateState(Integer saleListId) {
		saleListRepository.updateState(saleListId);
	}

	@Override
	public List<Object> getCountPerDaySaleByDate(String beginDate, String endDate) {
		return saleListRepository.getCountPerDaySaleByDate(beginDate,endDate);
	}


	@Override
	public List<Object> getCountPerMonthSaleByDate(String beginDate, String endDate){
		return saleListRepository.getCountPerMonthSaleByDate(beginDate,endDate);
	}


}
