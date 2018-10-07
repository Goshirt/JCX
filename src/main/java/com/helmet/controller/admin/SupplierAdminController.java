package com.helmet.controller.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helmet.entity.Log;
import com.helmet.entity.Supplier;
import com.helmet.service.LogService;
import com.helmet.service.SupplierService;
import com.helmet.util.StringUtil;

/**
 * 
 * Supplier
 * @author Helmet
 * 2018年8月20日
 */
@Controller
@RequestMapping("/admin/supplier")
public class SupplierAdminController {
	
	@Resource
	private SupplierService supplierService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 获取供应商列表
	 * @param supplier
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/supplierList")
	@RequiresPermissions(value="供应商管理")
	public Map<String, Object> getList(Supplier supplier,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		Map<String, Object> resultMap=new HashMap<>();
		List<Supplier> suppliers=supplierService.getSuppliers(supplier, page, pageSize, Direction.ASC, "supplierId");
		Long count=supplierService.count(supplier);
		logService.log(new Log(Log.SELECT_ACTION, "获取供应商列表"));
		resultMap.put("rows", suppliers);
		resultMap.put("total", count);
		return resultMap;
	}
	
	
	/**
	 * 添加或者修改供应商信息
	 * @param supplier
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value="供应商管理")
	public Map<String, Object> save(Supplier supplier){
		Map<String, Object> resultMap=new HashMap<>();
		if (supplier.getSupplierId()==null) {
			logService.log(new Log(Log.ADD_ACTION, "添加一个供应商:"+supplier.toString()));
		}else {
			logService.log(new Log(Log.UPDATE_ACTION, "更改了一个供应商信息:"+supplier.toString()));
		}
		supplierService.save(supplier);
		resultMap.put("success", true);
		return resultMap;
	}
	
	
	/**
	 * 删除一个指定的供应商
	 * @param supplierId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value="供应商管理")
	public Map<String, Object> delete(Integer supplierId){
		Map<String, Object> resultMap=new HashMap<>();
		logService.log(new Log(Log.DELETE_ACTION, "删除了一个供应商信息:"+supplierService.getSupplierById(supplierId).toString()));
		supplierService.delete(supplierId);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 获取进货入库combobox下拉框的数据
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/comboboxList")
	@RequiresPermissions(value="进货入库")
	public List<Supplier> comboboxList(String name)throws Exception{
		if (StringUtil.isEmpty(name)) {
			name="";
		}
		return supplierService.getSupplierByName("%"+name+"%");
	}
}
