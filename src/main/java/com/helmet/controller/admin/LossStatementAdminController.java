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
import com.helmet.entity.LossStatement;
import com.helmet.entity.LossStatementGoods;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.LossStatementService;
import com.helmet.service.UserService;
import com.helmet.util.DateUtil;
import com.helmet.util.StringUtil;

/**
 * 报损单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/lossStatement")
public class LossStatementAdminController {
	
	@Resource
	private LossStatementService lossStatementService;
	
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
	 * 生成报损单号
	 * @return
	 */
	@RequestMapping("/genLossNumber")
	@RequiresPermissions(value="商品报损")
	public String genLossNumber(){
		StringBuffer sBuffer=new StringBuffer("BS");
		sBuffer.append(DateUtil.formatCurentDate());
		String lastLossNumber=lossStatementService.getTodayMaxLossNumber();
		if (lastLossNumber!=null) {
			sBuffer.append(StringUtil.genFourCode(lastLossNumber));
		}else {
			//如果为空，证明这是这一天的第一个报损单
			sBuffer.append("0001");
		}
		return sBuffer.toString();
	}
	
	/**
	 * 保存报损单，报损单的商品，以及更新库存商品的报损价，库存量
	 * @param lossStatement
	 * @param goodsJson
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="商品报损")
	public Map<String, Object> save(LossStatement lossStatement,String lossGoodsToJson) {
		Map<String, Object> resultMap=new HashMap<>();
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		if (lossStatement!=null) {
			lossStatement.setUser(currentUser);
		}
		Gson gson = new Gson();
		//通过Gson将json串转为LossStatementGoods的List集
		List<LossStatementGoods> lossStatementGoodsList = gson.fromJson(lossGoodsToJson, new TypeToken<List<LossStatementGoods>>(){}.getType());
		lossStatementService.save(lossStatement, lossStatementGoodsList);
		logService.log(new Log(Log.ADD_ACTION,"新增一个报损单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根據用戶輸入的查詢條件獲取進貨單列表信息
	 * @param lossStatement
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> list(LossStatement lossStatement){
		Map<String, Object> resultMap = new HashMap<>();
		List<LossStatement> lossStatements = lossStatementService.list(lossStatement,Direction.DESC, "createDate");
		Long count = lossStatementService.count(lossStatement);
		resultMap.put("rows", lossStatements);
		resultMap.put("total", count);
		return resultMap;
	}
	
	/**
	 * 根据id删除报损单
	 * @param lossStatementId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> delete(Integer lossStatementId) {
		Map<String, Object> resultMap = new HashMap<>();
		lossStatementService.delete(lossStatementId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.DELETE_ACTION, "删除报损单"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}
}
