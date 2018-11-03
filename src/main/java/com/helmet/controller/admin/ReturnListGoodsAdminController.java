package com.helmet.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helmet.entity.ReturnListGoods;
import com.helmet.service.LogService;
import com.helmet.service.ReturnListGoodsService;
import com.helmet.service.UserService;

/**
 * 退货单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/returnListGoods")
public class ReturnListGoodsAdminController {
	
	@Resource
	private ReturnListGoodsService returnListGoodsService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 根据退货单的Id查询该退货单的具体商品列表
	 * @param returnListId
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="退货单据查询")
	public Map<String, Object> list(Integer returnListId){
		Map<String, Object> resultMap = new HashMap<>();
		List<ReturnListGoods> goodsList = returnListGoodsService.getReturnListGoodsByReturnId(returnListId);
		resultMap.put("rows", goodsList);
		return resultMap;
	}
	
}
