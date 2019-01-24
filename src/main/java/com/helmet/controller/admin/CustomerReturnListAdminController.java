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
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helmet.entity.Log;
import com.helmet.entity.CustomerReturnList;
import com.helmet.entity.CustomerReturnListGoods;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.CustomerReturnListService;
import com.helmet.service.UserService;
import com.helmet.util.DateUtil;
import com.helmet.util.StringUtil;

/**
 * 客户退货单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/customerReturnList")
public class CustomerReturnListAdminController {
	
	@Resource
	private CustomerReturnListService customerReturnListService;
	
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
	 * 生成客户退货单号
	 * @return
	 */
	@RequestMapping("/genCustomerReturnNumber")
	@RequiresPermissions(value="客户退货")
	public String genCustomerReturnNumber(){
		StringBuffer sBuffer=new StringBuffer("KHTH");
		sBuffer.append(DateUtil.formatCurentDate());
		String lastCustomerReturnNumber=customerReturnListService.getTodayMaxCustomerReturnNumber();
		if (lastCustomerReturnNumber!=null) {
			sBuffer.append(StringUtil.genFourCode(lastCustomerReturnNumber));
		}else {
			//如果为空，证明这是这一天的第一个客户退货单
			sBuffer.append("0001");
		}
		return sBuffer.toString();
	}
	
	/**
	 * 保存客户退货单，客户退货单的商品，以及更新库存商品的客户退货价，库存量
	 * @param customerReturnList
	 * @param customerReturnGoodsToJson
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="客户退货")
	public Map<String, Object> save(CustomerReturnList customerReturnList,String customerReturnGoodsToJson) {
		Map<String, Object> resultMap=new HashMap<>();
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		if (customerReturnList!=null) {
			customerReturnList.setUser(currentUser);
		}
		Gson gson = new Gson();
		//通过Gson将json串转为CustomerReturnListGoods的List集
		List<CustomerReturnListGoods> customerReturnListGoodsList = gson.fromJson(customerReturnGoodsToJson, new TypeToken<List<CustomerReturnListGoods>>(){}.getType());
		customerReturnListService.save(customerReturnList, customerReturnListGoodsList);
		logService.log(new Log(Log.ADD_ACTION,"新增一个客户退货单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根據用戶輸入的查詢條件獲取進貨單列表信息
	 * @param customerReturnList
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="客户退货查询")
	public Map<String, Object> list(CustomerReturnList customerReturnList){
		Map<String, Object> resultMap = new HashMap<>();
		List<CustomerReturnList> customerReturnLists = customerReturnListService.list(customerReturnList,Direction.DESC, "customerReturnDate");
		Long count = customerReturnListService.count(customerReturnList);
		resultMap.put("rows", customerReturnLists);
		resultMap.put("total", count);
		return resultMap;
	}
	
	/**
	 * 根据id删除客户退货单
	 * @param customerReturnListId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="客户退货查询")
	public Map<String, Object> delete(Integer customerReturnListId) {
		Map<String, Object> resultMap = new HashMap<>();
		customerReturnListService.delete(customerReturnListId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.DELETE_ACTION, "删除客户退货单"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 更新客户退货单付款状态
	 * @param customerReturnListId
	 * @return
	 */
	@RequestMapping("/modifyState")
	@RequiresPermissions(value="客户统计")
	public Map<String, Object> modifyState(Integer customerReturnListId){
		Map<String, Object> resultMap = new HashMap<>();
		customerReturnListService.updateState(customerReturnListId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.UPDATE_ACTION, "更新客户单的付款状态为已付:"+customerReturnListId+":"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}
}
