package com.helmet.controller.admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helmet.entity.LossStatementGoods;
import com.helmet.service.LogService;
import com.helmet.service.LossStatementGoodsService;

/**
 * 报损单商品Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/lossStatementGoods")
public class LossStatementGoodsAdminController {
	
	@Resource
	private LossStatementGoodsService lossStatementGoodsService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 根据报损单的Id查询该报损单的具体商品列表
	 * @param lossStatementId
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> list(Integer lossStatementId){
		Map<String, Object> resultMap = new HashMap<>();
		List<LossStatementGoods> goodsList = lossStatementGoodsService.getLossStatementGoodsByLossStatementId(lossStatementId);
		resultMap.put("rows", goodsList);
		return resultMap;
	}
}
