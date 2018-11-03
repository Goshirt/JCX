package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.helmet.entity.Goods;
import com.helmet.entity.PuchaseList;
import com.helmet.entity.PuchaseListGoods;
import com.helmet.repository.GoodsRepository;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.repository.PuchaseListGoodsRepository;
import com.helmet.repository.PuchaseListRepository;
import com.helmet.service.PuchaseListService;
import com.helmet.util.MathUtil;
import com.helmet.util.StringUtil;

/**
 * 进货单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("puchaseListService")
public class PuchaseListServiceImpl implements PuchaseListService{
	
	@Resource
	private PuchaseListRepository puchaseListRepository;
	
	@Resource
	private PuchaseListGoodsRepository puchaseListGoodsRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public String getTodayMaxPuchaseNumber() {
		return puchaseListRepository.getTodayMaxPuchaseNumber();
	}

	@Override
	@Transactional
	public void save(PuchaseList puchaseList, List<PuchaseListGoods> puchaseListGoodsList) {
		for (PuchaseListGoods puchaseListGoods : puchaseListGoodsList) {
			//保存每一个进货单商品的商品类型
			puchaseListGoods.setGoodsType(goodsTypeRepository.findOne(puchaseListGoods.getGoodsTypeId()));  
			 //添加进货单商品和进货单的关联
			puchaseListGoods.setPuchaseList(puchaseList);
			//保存进货单商品到数据库
			puchaseListGoodsRepository.save(puchaseListGoods);
			
			//获取进货单商品对应的仓库中的商品信息
			Goods goods = goodsRepository.findOne(puchaseListGoods.getGoodsId());
			//更新仓库中商品的库存数量（加上本次进货的数量）
			goods.setInventoryQuantity(goods.getInventoryQuantity()+puchaseListGoods.getNum());
			//更新仓库中商品的上次进货价(是本次进货单中该商品的进货价)
			goods.setLastPuchasingPrice(puchaseListGoods.getPrice());
			//更新商品的状态
			goods.setState(2);
			//加上本次的进货价，计算平均价
			float puchasePrice = (goods.getPurchasingPrice()*goods.getInventoryQuantity()+puchaseListGoods.getPrice()*puchaseListGoods.getNum())/(goods.getInventoryQuantity()+puchaseListGoods.getNum());
			//平均价保留两位小数，保存到数据库
			goods.setPurchasingPrice(MathUtil.float2Bit(puchasePrice));
			goodsRepository.save(goods);
		}
		puchaseListRepository.save(puchaseList);
	}

	@Override
	public List<PuchaseList> list(PuchaseList puchaseList, Integer page, Integer pageSize, Direction direction,
			String... propertis) {
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<PuchaseList> pagePuchaseList = puchaseListRepository.findAll(new Specification<PuchaseList>() {
			
			@Override
			public Predicate toPredicate(Root<PuchaseList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (puchaseList != null) {
					if (StringUtil.isNotEmpty(puchaseList.getPuchaseNumber())) {
						predicate.getExpressions().add(cb.like(root.get("puchaseNumber"), "%"+puchaseList.getPuchaseNumber()+"%"));
					}
					if (puchaseList.getSupplier()!= null && puchaseList.getSupplier().getSupplierId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("supplierId"), puchaseList.getSupplier().getSupplierId()));
					}
					if (puchaseList.getbPuchaseDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("puchaseDate"), puchaseList.getbPuchaseDate()));
					}
					if (puchaseList.getePuchaseDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("puchaseDate"), puchaseList.getePuchaseDate()));
					}
					if (puchaseList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), puchaseList.getState()));
					}
				}
				return predicate;
			}
		}, pageable);
		return pagePuchaseList.getContent();
	}

	@Override
	public Long count(PuchaseList puchaseList) {
		Long count = puchaseListRepository.count(new Specification<PuchaseList>() {

			@Override
			public Predicate toPredicate(Root<PuchaseList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (puchaseList != null) {
					if (StringUtil.isNotEmpty(puchaseList.getPuchaseNumber())) {
						predicate.getExpressions().add(cb.like(root.get("puchaseNumber"), "%"+puchaseList.getPuchaseNumber()+"%"));
					}
					if (puchaseList.getSupplier()!= null && puchaseList.getSupplier().getSupplierId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("supplierId"), puchaseList.getSupplier().getSupplierId()));
					}
					if (puchaseList.getbPuchaseDate() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("puchaseDate"), puchaseList.getbPuchaseDate()));
					}
					if (puchaseList.getePuchaseDate() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("puchaseDate"), puchaseList.getePuchaseDate()));
					}
					if (puchaseList.getState() != null) {
						predicate.getExpressions().add(cb.equal(root.get("state"), puchaseList.getState()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	@Transactional
	public void delete(Integer puchaseListId) {
		puchaseListGoodsRepository.deleteByPuchaseListId(puchaseListId);
		puchaseListRepository.delete(puchaseListId);
	}

	
}
