package com.helmet.controller.admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helmet.entity.SaleListGoods;
import com.helmet.service.LogService;
import com.helmet.service.SaleListGoodsService;

/**
 * 销售单商品Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/saleListGoods")
public class SaleListGoodsAdminController {
	
	@Resource
	private SaleListGoodsService saleListGoodsService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 根据退货单的Id查询该退货单的具体商品列表
	 * @param saleListId
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="销售单据查询")
	public Map<String, Object> list(Integer saleListId){
		Map<String, Object> resultMap = new HashMap<>();
		List<SaleListGoods> goodsList = saleListGoodsService.getSaleListGoodsBySaleId(saleListId);
		resultMap.put("rows", goodsList);
		return resultMap;
	}
}
