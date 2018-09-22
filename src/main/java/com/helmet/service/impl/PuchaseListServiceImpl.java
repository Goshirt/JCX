package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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


}
