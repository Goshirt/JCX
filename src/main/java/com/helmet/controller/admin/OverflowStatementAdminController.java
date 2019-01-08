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
import com.helmet.entity.OverflowStatement;
import com.helmet.entity.OverflowStatementGoods;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.OverflowStatementService;
import com.helmet.service.UserService;
import com.helmet.util.DateUtil;
import com.helmet.util.StringUtil;

/**
 * 报溢单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/overflowStatement")
public class OverflowStatementAdminController {
	
	@Resource
	private OverflowStatementService overflowStatementService;
	
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
	 * 生成报溢单号
	 * @return
	 */
	@RequestMapping("/genOverflowNumber")
	@RequiresPermissions(value="商品报溢")
	public String genOverflowNumber(){
		StringBuffer sBuffer=new StringBuffer("BY");
		sBuffer.append(DateUtil.formatCurentDate());
		String lastOverflowNumber=overflowStatementService.getTodayMaxOverflowNumber();
		if (lastOverflowNumber!=null) {
			sBuffer.append(StringUtil.genFourCode(lastOverflowNumber));
		}else {
			//如果为空，证明这是这一天的第一个报溢单
			sBuffer.append("0001");
		}
		return sBuffer.toString();
	}
	
	/**
	 * 保存报溢单，报溢单的商品，以及更新库存商品的报溢价，库存量
	 * @param overflowStatement
	 * @param goodsJson
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="商品报溢")
	public Map<String, Object> save(OverflowStatement overflowStatement,String overflowGoodsToJson) {
		Map<String, Object> resultMap=new HashMap<>();
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		if (overflowStatement!=null) {
			overflowStatement.setUser(currentUser);
		}
		Gson gson = new Gson();
		//通过Gson将json串转为OverflowStatementGoods的List集
		List<OverflowStatementGoods> overflowStatementGoodsList = gson.fromJson(overflowGoodsToJson, new TypeToken<List<OverflowStatementGoods>>(){}.getType());
		overflowStatementService.save(overflowStatement, overflowStatementGoodsList);
		logService.log(new Log(Log.ADD_ACTION,"新增一个报溢单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根據用戶輸入的查詢條件獲取進貨單列表信息
	 * @param overflowStatement
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> list(OverflowStatement overflowStatement){
		Map<String, Object> resultMap = new HashMap<>();
		List<OverflowStatement> overflowStatements = overflowStatementService.list(overflowStatement, Direction.DESC, "createDate");
		Long count = overflowStatementService.count(overflowStatement);
		resultMap.put("rows", overflowStatements);
		resultMap.put("total", count);
		return resultMap;
	}
	
	/**
	 * 根据id删除报溢单
	 * @param overflowStatementId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> delete(Integer overflowStatementId) {
		Map<String, Object> resultMap = new HashMap<>();
		overflowStatementService.delete(overflowStatementId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.DELETE_ACTION, "删除报溢单"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}
}
