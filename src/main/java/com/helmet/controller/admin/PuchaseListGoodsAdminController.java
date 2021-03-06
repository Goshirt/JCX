package com.helmet.controller.admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helmet.entity.PuchaseListGoods;
import com.helmet.service.LogService;
import com.helmet.service.PuchaseListGoodsService;

/**
 * 进货单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/puchaseListGoods")
public class PuchaseListGoodsAdminController {
	
	@Resource
	private PuchaseListGoodsService puchaseListGoodsService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 根据退货单的Id查询该退货单的具体商品列表
	 * @param puchaseListId
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="进货单据查询")
	public Map<String, Object> list(Integer puchaseListId){
		Map<String, Object> resultMap = new HashMap<>();
		List<PuchaseListGoods> goodsList = puchaseListGoodsService.getGoodsListByPuchaseListId(puchaseListId);
		resultMap.put("rows", goodsList);
		return resultMap;
	}
}
