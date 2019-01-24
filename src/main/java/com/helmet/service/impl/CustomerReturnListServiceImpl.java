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
import com.helmet.entity.CustomerReturnList;
import com.helmet.entity.CustomerReturnListGoods;
import com.helmet.repository.GoodsRepository;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.repository.CustomerReturnListGoodsRepository;
import com.helmet.repository.CustomerReturnListRepository;
import com.helmet.service.CustomerReturnListService;
import com.helmet.util.StringUtil;

/**
 * 客户退货单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("customerReturnListService")
public class CustomerReturnListServiceImpl implements CustomerReturnListService{
	
	@Resource
	private CustomerReturnListRepository customerReturnListRepository;
	
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public String getTodayMaxCustomerReturnNumber() {
		return customerReturnListRepository.getTodayMaxCustomerReturnNumber();
	}

	@Override
	@Transactional
	public void save(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> customerReturnListGoodsList) {
		for (CustomerReturnListGoods customerReturnListGoods : customerReturnListGoodsList) {
			//保存每一个客户退货单商品的商品类型
			customerReturnListGoods.setGoodsType(goodsTypeRepository.findOne(customerReturnListGoods.getGoodsTypeId()));  
			 //添加客户退货单商品和客户退货单的关联
			customerReturnListGoods.setCustomerReturnList(customerReturnList);
			//保存客户退货单商品到数据库
			customerReturnListGoodsRepository.save(customerReturnListGoods);
			
			//获取客户退货单商品对应的仓库中的商品信息
			Goods goods = goodsRepository.findOne(customerReturnListGoods.getGoodsId());
			//更新仓库中商品的库存数量（加上本次客户退货的数量）
			goods.setInventoryQuantity(goods.getInventoryQuantity()+customerReturnListGoods.getNum());
			/*//更新仓库中商品的上次进货价(是本次客户退货单中该商品的进货价)
			goods.setLastPuchasingPrice(customerReturnListGoods.getPrice());
			//更新商品的状态
			goods.setState(2);
			//加上本次的进货价，计算平均价
			float customerReturnPrice = (goods.getPurchasingPrice()*goods.getInventoryQuantity()+customerReturnListGoods.getPrice()*customerReturnListGoods.getNum())/(goods.getInventoryQuantity()+customerReturnListGoods.getNum());
			//平均价保留两位小数，保存到数据库
			goods.setPurchasingPrice(MathUtil.float2Bit(customerReturnPrice));*/
			goodsRepository.save(goods);
		}
		customerReturnListRepository.save(customerReturnList);
	}

	@Override
	public List<CustomerReturnList> list(CustomerReturnList customerReturnList, Direction direction,String... propertis) {
		List<CustomerReturnList> pageCustomerReturnList = customerReturnListRepository.findAll(new Specification<CustomerReturnList>() {
			
			@Override
			public Predicate toPredicate(Root<CustomerReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (customerReturnList != null) {
					if (StringUtil.isNotEmpty(customerReturnList.getCustomerReturnNumber())) {
						predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"), "%"+customerReturnList.getCustomerReturnNumber()+"%"));
					}
					if (customerReturnList.getCustomer()!= null && customerReturnList.getCustomer().getCustomerId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("customer").get("customerId"), customerReturnList.getCustomer().getCustomerId()));
					}
					if (customerReturnList.getbCustomerReturnDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.getbCustomerReturnDate()));
					}
					if (customerReturnList.geteCustomerReturnDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.geteCustomerReturnDate()));
					}
					if (customerReturnList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), customerReturnList.getState()));
					}
				}
				return predicate;
			}
		});
		return pageCustomerReturnList;
	}

	@Override
	public Long count(CustomerReturnList customerReturnList) {
		Long count = customerReturnListRepository.count(new Specification<CustomerReturnList>() {

			@Override
			public Predicate toPredicate(Root<CustomerReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (customerReturnList != null) {
					if (StringUtil.isNotEmpty(customerReturnList.getCustomerReturnNumber())) {
						predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"), "%"+customerReturnList.getCustomerReturnNumber()+"%"));
					}
					if (customerReturnList.getCustomer()!= null && customerReturnList.getCustomer().getCustomerId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("customer").get("customerId"), customerReturnList.getCustomer().getCustomerId()));
					}
					if (customerReturnList.getbCustomerReturnDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.getbCustomerReturnDate()));
					}
					if (customerReturnList.geteCustomerReturnDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.geteCustomerReturnDate()));
					}
					if (customerReturnList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), customerReturnList.getState()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	@Transactional
	public void delete(Integer customerReturnListId) {
		customerReturnListGoodsRepository.deleteByCustomerReturnListId(customerReturnListId);
		customerReturnListRepository.delete(customerReturnListId);
	}

	@Override
	@Transactional
	public void updateState(Integer customerReturnListId) {
		customerReturnListRepository.updateState(customerReturnListId);
	}

	
}
