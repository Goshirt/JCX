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
import com.helmet.entity.PuchaseList;
import com.helmet.entity.PuchaseListGoods;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.PuchaseListService;
import com.helmet.service.UserService;
import com.helmet.util.DateUtil;
import com.helmet.util.StringUtil;

/**
 * 进货单Controller
 * 
 * @author Helmet
 * 2018年9月8日
 */
@RestController
@RequestMapping("/admin/puchaseList")
public class PuchaseListAdminController {
	
	@Resource
	private PuchaseListService puchaseListService;
	
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
	 * 生成进货单号
	 * @return
	 */
	@RequestMapping("/genPuchaseNumber")
	@RequiresPermissions(value="进货入库")
	public String genPuchaseNumber(){
		StringBuffer sBuffer=new StringBuffer("JH");
		sBuffer.append(DateUtil.formatCurentDate());
		String lastPuchaseNumber=puchaseListService.getTodayMaxPuchaseNumber();
		if (lastPuchaseNumber!=null) {
			sBuffer.append(StringUtil.genFourCode(lastPuchaseNumber));
		}else {
			//如果为空，证明这是这一天的第一个进货单
			sBuffer.append("0001");
		}
		return sBuffer.toString();
	}
	
	/**
	 * 保存进货单，进货单的商品，以及更新库存商品的进货价，库存量
	 * @param puchaseList
	 * @param goodsJson
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="进货入库")
	public Map<String, Object> save(PuchaseList puchaseList,String puchaseGoodsToJson) {
		Map<String, Object> resultMap=new HashMap<>();
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		if (puchaseList!=null) {
			puchaseList.setUser(currentUser);
		}
		Gson gson = new Gson();
		//通过Gson将json串转为PuchaseListGoods的List集
		List<PuchaseListGoods> puchaseListGoodsList = gson.fromJson(puchaseGoodsToJson, new TypeToken<List<PuchaseListGoods>>(){}.getType());
		puchaseListService.save(puchaseList, puchaseListGoodsList);
		logService.log(new Log(Log.ADD_ACTION,"新增一个进货单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根據用戶輸入的查詢條件獲取進貨單列表信息
	 * @param puchaseList
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="进货单据查询")
	public Map<String, Object> list(PuchaseList puchaseList,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer pageSize){
		Map<String, Object> resultMap = new HashMap<>();
		List<PuchaseList> puchaseLists = puchaseListService.list(puchaseList, page, pageSize, Direction.DESC, "puchaseDate");
		Long count = puchaseListService.count(puchaseList);
		resultMap.put("rows", puchaseLists);
		resultMap.put("total", count);
		return resultMap;
	}
	
	/**
	 * 根据id删除进货单
	 * @param puchaseListId
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="进货单据查询")
	public Map<String, Object> delete(Integer puchaseListId) {
		Map<String, Object> resultMap = new HashMap<>();
		puchaseListService.delete(puchaseListId);
		//设置操作用户
		User currentUser=userService.getUserByUserName((String)SecurityUtils.getSubject().getPrincipal());
		logService.log(new Log(Log.DELETE_ACTION, "删除进货单"+currentUser.getUserName()));
		resultMap.put("success", true);
		return resultMap;
	}
}
