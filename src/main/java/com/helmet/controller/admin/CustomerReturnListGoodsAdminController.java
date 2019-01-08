package com.helmet.controller.admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helmet.entity.CustomerReturnListGoods;
import com.helmet.service.LogService;
import com.helmet.service.CustomerReturnListGoodsService;

/**
 * 退货单商品Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/customerReturnListGoods")
public class CustomerReturnListGoodsAdminController {
	
	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 根据退货单的Id查询该退货单的具体商品列表
	 * @param customerReturnListId
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="客户退货查询")
	public Map<String, Object> list(Integer customerReturnListId){
		Map<String, Object> resultMap = new HashMap<>();
		List<CustomerReturnListGoods> goodsList = customerReturnListGoodsService.getCustomerReturnListGoodsByCustomerReturnListId(customerReturnListId);
		resultMap.put("rows", goodsList);
		return resultMap;
	}
}
