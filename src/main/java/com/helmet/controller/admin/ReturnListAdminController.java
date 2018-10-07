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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helmet.entity.Log;
import com.helmet.entity.ReturnList;
import com.helmet.entity.ReturnListGoods;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.ReturnListService;
import com.helmet.service.UserService;
import com.helmet.util.DateUtil;
import com.helmet.util.StringUtil;

/**
 * 退货单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/returnList")
public class ReturnListAdminController {
	
	@Resource
	private ReturnListService returnListService;
	
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
	 * 生成退货单号
	 * @return
	 */
	@RequestMapping("/genReturnNumber")
	@RequiresPermissions(value="退货出库")
	public String genReturnNumber(){
		StringBuffer sBuffer=new StringBuffer("TH");
		sBuffer.append(DateUtil.formatCurentDate());
		String lastReturnNumber=returnListService.getTodayMaxReturnNumber();
		if (lastReturnNumber!=null) {
			sBuffer.append(StringUtil.genFourCode(lastReturnNumber));
		}else {
			//如果为空，证明这是这一天的第一个退货单
			sBuffer.append("0001");
		}
		return sBuffer.toString();
	}
	
	/**
	 * 保存退货单，退货单的商品，以及更新库存商品的，库存量
	 * @param returnList
	 * @param goodsJson
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="退货出库")
	public Map<String, Object> save(ReturnList returnList,String returnGoodsToJson) {
		Map<String, Object> resultMap=new HashMap<>();
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		if (returnList!=null) {
			returnList.setUser(currentUser);
		}
		Gson gson = new Gson();
		//通过Gson将json串转为ReturnListGoods的List集
		List<ReturnListGoods> returnListGoodsList = gson.fromJson(returnGoodsToJson, new TypeToken<List<ReturnListGoods>>(){}.getType());
		returnListService.save(returnList, returnListGoodsList);
		logService.log(new Log(Log.ADD_ACTION,"新增一个退货单"));
		resultMap.put("success", true);
		return resultMap;
	}
}
