package com.helmet.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helmet.entity.Log;
import com.helmet.entity.SaleList;
import com.helmet.entity.SaleListGoods;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.SaleListService;
import com.helmet.service.UserService;
import com.helmet.util.DateUtil;
import com.helmet.util.StringUtil;

/**
 * 销售单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/saleList")
public class SaleListAdminController {
	
	@Resource
	private SaleListService saleListService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LogService logService;
	
	//按指定的格式 格式前台传来的JSON中的日期字段为Date格式
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 生成销售单号
	 * @return
	 */
	@RequestMapping("/genSaleNumber")
	@RequiresPermissions(value="销售出库")
	public String genSaleNumber(){
		StringBuffer sBuffer=new StringBuffer("XS");
		sBuffer.append(DateUtil.formatCurentDate());
		String lastSaleNumber=saleListService.getTodayMaxSaleNumber();
		if (lastSaleNumber!=null) {
			sBuffer.append(StringUtil.genFourCode(lastSaleNumber));
		}else {
			//如果为空，证明这是这一天的第一个销售单
			sBuffer.append("0001");
		}
		return sBuffer.toString();
	}
	
	/**
	 * 保存销售单，销售单的商品，以及更新库存商品的销售价，库存量
	 * @param saleList
	 * @param goodsJson
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="销售出库")
	public Map<String, Object> save(SaleList saleList,String saleGoodsToJson) {
		Map<String, Object> resultMap=new HashMap<>();
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		if (saleList!=null) {
			saleList.setUser(currentUser);
		}
		Gson gson = new Gson();
		//通过Gson将json串转为SaleListGoods的List集
		List<SaleListGoods> saleListGoodsList = gson.fromJson(saleGoodsToJson, new TypeToken<List<SaleListGoods>>(){}.getType());
		saleListService.save(saleList, saleListGoodsList);
		logService.log(new Log(Log.ADD_ACTION,"新增一个销售单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根據用戶輸入的查詢條件獲取進貨單列表信息
	 * @param saleList
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="销售单据查询")
	public Map<String, Object> list(SaleList saleList,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer pageSize){
		Map<String, Object> resultMap = new HashMap<>();
		List<SaleList> saleLists = saleListService.list(saleList, page, pageSize, Direction.DESC, "saleDate");
		Long count = saleListService.count(saleList);
		resultMap.put("rows", saleLists);
		resultMap.put("total", count);
		return resultMap;
	}
	
	/**
	 * 根据id删除销售单
	 * @param saleListId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="销售单据查询")
	public Map<String, Object> delete(Integer saleListId) {
		Map<String, Object> resultMap = new HashMap<>();
		saleListService.delete(saleListId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.DELETE_ACTION, "删除销售单"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}
}