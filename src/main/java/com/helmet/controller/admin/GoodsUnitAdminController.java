package com.helmet.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helmet.entity.GoodsUnit;
import com.helmet.service.GoodsUnitService;

/**
 * 商品单位Controller
 * 
 * @author Helmet
 * 2018年8月28日
 */
@Controller
@RequestMapping("/admin/goodsUnit")
public class GoodsUnitAdminController {
	
	@Resource
	private GoodsUnitService goodsUnitService;
	
	/**
	 * 获取cobbox的下拉框的值
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/combobox")
	@RequiresPermissions(value="商品管理")
	public List<GoodsUnit> getComboboxList(){
		return goodsUnitService.getUnits();
	}
	
	/**
	 * 获取单位集
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getList")
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> getList(){
		Map<String, Object> resultMap=new HashMap<>();
		List<GoodsUnit> units=goodsUnitService.getUnits();
		resultMap.put("rows", units);
		return resultMap;
	}
	
	/**
	 * 保存一个单位
	 * @param goodsUnit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> save(GoodsUnit goodsUnit) {
		Map<String, Object> resultMap=new HashMap<>();
		goodsUnitService.save(goodsUnit);
		resultMap.put("success", true);
		return resultMap;
	}
	
	
	/**
	 * 删除一个单位
	 * @param unitId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> delete(Integer unitId) {
		Map<String, Object> resultMap=new HashMap<>();
		goodsUnitService.delete(unitId);
		resultMap.put("success", true);
		return resultMap;
	}
	
}
