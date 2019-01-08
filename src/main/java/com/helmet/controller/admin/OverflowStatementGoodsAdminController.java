package com.helmet.controller.admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helmet.entity.OverflowStatementGoods;
import com.helmet.service.LogService;
import com.helmet.service.OverflowStatementGoodsService;

/**
 * 报溢单商品Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/overflowStatementGoods")
public class OverflowStatementGoodsAdminController {
	
	@Resource
	private OverflowStatementGoodsService overflowStatementGoodsService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 根据报溢单的Id查询该报溢单的具体商品列表
	 * @param overflowStatementId
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> list(Integer overflowStatementId){
		Map<String, Object> resultMap = new HashMap<>();
		List<OverflowStatementGoods> goodsList = overflowStatementGoodsService.getOverflowStatementGoodsByOverflowStatementId(overflowStatementId);
		resultMap.put("rows", goodsList);
		return resultMap;
	}
}
