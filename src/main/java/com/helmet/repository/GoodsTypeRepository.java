package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.GoodsType;

/**
 * 
 * 商品类别Repository
 * @author Helmet
 * 2018年8月22日
 */
public interface GoodsTypeRepository extends JpaRepository<GoodsType, Integer>,JpaSpecificationExecutor<GoodsType>{

	/**
	 * 通过父节点获取子节点商品类型
	 * @param parentId
	 * @return
	 */
	@Query(value="select * from t_goods_type where parent_id=?1",nativeQuery=true)
	public List<GoodsType> geGoodsTypesByParentId(Integer parentId);
}
