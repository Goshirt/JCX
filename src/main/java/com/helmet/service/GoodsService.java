package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.Goods;

/**
 * 商品Service
 * 
 * @author Helmet
 * 2018年8月23日
 */
public interface GoodsService {
	
	/**
	 * 判断指定的商品类型下是否有商品
	 * @param goodsTypeId
	 * @return
	 */
	public boolean isGoodsTypeHaveGoods(Integer goodsTypeId);
	
	/**
	 * 分页获取商品
	 * @param goods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Goods> getGoodsList(Goods goods,Integer page,Integer pageSize,Direction direction,String...properties);

	/**
	 * 统计获取到的商品数量
	 * @param goods
	 * @return
	 */
	public Long count(Goods goods);
	
	/**
	 * 保存一个商品实体
	 * @param goods
	 */
	public void save(Goods goods);
	
	/**
	 * 根据goodsId删除一个商品
	 * @param goodsId
	 */
	public void delete(int goodsId);
	
	/**
	 * 根据goodsId获取一个商品实体
	 * @param goodsId
	 * @return
	 */
	public Goods getGoodsByGoodsId(int goodsId);
	
	/**
	 * 获取最大的商品码
	 * @return
	 */
	public String getMaxCode();
	
	/**
	 * 根据商品码或者商品名字获分页获取库存为0（inventoryQuantity=0）/未入库的商品
	 * @param codeOrName
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Goods> getGoodsListWithNoInventoryQuantity(String codeOrName,Integer page,Integer pageSize,Direction direction,String...properties);
	
	/**
	 * 根据商品码或者商品名字获取库存为0（inventoryQuantity=0）/未入库的商品总数
	 * @param codeOrName
	 * @return
	 */
	public Long countGoodsListWithNoInventoryQuantity(String codeOrName);
	
	
	/**
	 * 根据商品码或者商品名字获分页获取库存大于0（inventoryQuantity>0）/已入库的商品
	 * @param codeOrName
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Goods> getGoodsListWithHaveInventoryQuantity(String codeOrName,Integer page,Integer pageSize,Direction direction,String...properties);
	
	/**
	 * 根据商品码或者商品名字获取库存大于0（inventoryQuantity>0）/已入库的商品总数
	 * @param codeOrName
	 * @return
	 */
	public Long countGoodsListWithHaveInventoryQuantity(String codeOrName);
}
