package com.helmet.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.helmet.entity.*;
import com.helmet.util.MathUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
	 * @param saleGoodsToJson
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
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value={"销售单据查询","客户统计"})
	public Map<String, Object> list(SaleList saleList){
		Map<String, Object> resultMap = new HashMap<>();
		List<SaleList> saleLists = saleListService.list(saleList,Direction.DESC, "saleDate");
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
	
	/**
	 * 更新客户退货单付款状态
	 * @param saleListId
	 * @return
	 */
	@RequestMapping("/modifyState")
	@RequiresPermissions(value="客户统计")
	public Map<String, Object> modifyState(Integer saleListId){
		Map<String, Object> resultMap = new HashMap<>();
		saleListService.updateState(saleListId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.UPDATE_ACTION, "更新售货单的付款状态为已付:"+saleListId+":"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 获取符合时间段内的每天销售统计数据
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/perDaySaleCount")
	@RequiresPermissions(value = "每日销售统计")
	public Map<String, Object> getPerDaySale(String beginDate,String endDate) throws ParseException {
		Map<String, Object> resultMap = new HashMap<>();
		List<CountPerDaySale> resultList = new ArrayList<>();
		List<Object> countList = saleListService.getCountPerDaySaleByDate(beginDate,endDate);
		//获取搜索时间内的每一天时间集
		List<String> days = DateUtil.getPerDay(beginDate,endDate);
		for (String day: days) {
			CountPerDaySale perDaySale = new CountPerDaySale();
			perDaySale.setSaleDate(day);
			boolean flag = false;
			for (Object o: countList) {
				Object[] objects = (Object[]) o;
				//数据库搜索结果中有的日期
				String saleDay = objects[2].toString().substring(0,10);
				if (saleDay.equals(day)){
					perDaySale.setSaleTotalMoney(MathUtil.float2Bit(Float.parseFloat(objects[1].toString())));
					perDaySale.setCostTotalMoney(MathUtil.float2Bit(Float.parseFloat((objects[0].toString()))));
					perDaySale.setProfits(MathUtil.float2Bit(perDaySale.getSaleTotalMoney()-perDaySale.getCostTotalMoney()));
					flag = true;
				}
			}
			//搜索的时间段没有销售数据
			if (!flag){
				perDaySale.setProfits(0);
				perDaySale.setCostTotalMoney(0);
				perDaySale.setSaleTotalMoney(0);
			}
			resultList.add(perDaySale);
		}
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.UPDATE_ACTION, "查询了每天的销售统计:"+currentUser.getUserName()));
		resultMap.put("rows",resultList);
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 获取符合时间段内的每月销售统计数据
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/perMonthSaleCount")
	@RequiresPermissions(value = "每月销售统计")
	public Map<String, Object> getPerMonthSale(String beginDate,String endDate) throws ParseException {
		Map<String, Object> resultMap = new HashMap<>();
		List<CountPerMonthSale> resultList = new ArrayList<>();
		List<Object> countList = saleListService.getCountPerMonthSaleByDate(beginDate,endDate);
		//获取搜索时间内的每月时间集
		List<String> months = DateUtil.getPerMonth(beginDate,endDate);
		for (String month: months) {
			CountPerMonthSale perMonthSale = new CountPerMonthSale();
			perMonthSale.setSaleMonth(month);
			boolean flag = false;
			for (Object o: countList) {
				Object[] objects = (Object[]) o;
				//数据库搜索结果中有的日期
				String saleMonth = objects[2].toString().substring(0,7);
				if (saleMonth.equals(month)){
					perMonthSale.setSaleTotalMoney(MathUtil.float2Bit(Float.parseFloat(objects[0].toString())));
					perMonthSale.setCostTotalMoney(MathUtil.float2Bit(Float.parseFloat((objects[1].toString()))));
					perMonthSale.setProfits(MathUtil.float2Bit(perMonthSale.getSaleTotalMoney()-perMonthSale.getCostTotalMoney()));
					flag = true;
				}
			}
			//搜索的时间段没有销售数据
			if (!flag){
				perMonthSale.setProfits(0);
				perMonthSale.setCostTotalMoney(0);
				perMonthSale.setSaleTotalMoney(0);
			}
			resultList.add(perMonthSale);
		}
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.UPDATE_ACTION, "查询了每月的销售统计:"+currentUser.getUserName()));
		resultMap.put("rows",resultList);
		resultMap.put("success", true);
		return resultMap;
	}
}
