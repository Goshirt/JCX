package com.helmet.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helmet.entity.Log;
import com.helmet.service.LogService;

/**
 * 日志controller层
 * 
 * @author Helmet
 * 2018年8月18日
 */
@Controller
@RequestMapping("/admin/log")
public class LogAdminController {
	
	@Resource
	private LogService logService;
	
	
	//按指定的格式 格式前台传来的JSON中的日期字段为Date格式
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 获取日志列表
	 * @param log
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logList")
	@RequiresPermissions(value="系统日志")
	public Map<String, Object> getLog(Log log,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer pageSize){
		Map<String, Object> resultMap=new HashMap<>();
		//把获取到的分页数据根据time降序
		List<Log> logs=logService.getLogList(log, page, pageSize, Direction.ASC, "date");
		Long count=logService.count(log);
		resultMap.put("rows", logs);
		resultMap.put("total", count);
		return resultMap;
	}

}
