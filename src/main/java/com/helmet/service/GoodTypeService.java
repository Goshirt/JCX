package com.helmet.service;

import java.util.List;

import com.helmet.entity.GoodsType;

/**
 * 商品类别Service
 * 
 * @author Helmet
 * 2018年8月22日
 */
public interface GoodTypeService {
	
	/**
	 * 通过父节点获取子节点商品类型
	 * @param parentId
	 * @return
	 */
	public List<GoodsType> geGoodsTypesByParentId(Integer parentId);
	
	/**
	 * 通过Id获取商品类型实体
	 * @param goodsTypeId
	 * @return
	 */
	public GoodsType getGoodsTypeById(Integer goodsTypeId);
	
	
	/**
	 * 保存一个商品类型实体
	 * @param goodsType
	 */
	public void save(GoodsType goodsType);
	
	/**
	 * 根据Id删除一个商品实体
	 */
	public void delete(Integer goodsTypeId);
}
