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
import com.helmet.entity.Customer;
import com.helmet.service.LogService;
import com.helmet.service.CustomerService;

/**
 * 
 * Customer
 * @author Helmet
 * 2018年8月20日
 */
@Controller
@RequestMapping("/admin/customer")
public class CustomerAdminController {
	
	@Resource
	private CustomerService customerService;
	
	@Resource
	private LogService logService;
	
	
	/**
	 * 获取客户列表
	 * @param customer
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/customerList")
	@RequiresPermissions(value="客户管理")
	public Map<String, Object> getList(Customer customer,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		Map<String, Object> resultMap=new HashMap<>();
		List<Customer> customers=customerService.getCustomers(customer, page, pageSize, Direction.ASC, "customerId");
		Long count=customerService.count(customer);
		logService.log(new Log(Log.SELECT_ACTION, "获取客户列表"));
		resultMap.put("rows", customers);
		resultMap.put("total", count);
		return resultMap;
	}
	
	
	/**
	 * 添加或者修改客户信息
	 * @param customer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value="客户管理")
	public Map<String, Object> save(Customer customer){
		Map<String, Object> resultMap=new HashMap<>();
		if (customer.getCustomerId()==null) {
			logService.log(new Log(Log.ADD_ACTION, "添加一个客户:"+customer.toString()));
		}else {
			logService.log(new Log(Log.UPDATE_ACTION, "更改了一个客户信息:"+customer.toString()));
		}
		customerService.save(customer);
		resultMap.put("success", true);
		return resultMap;
	}
	
	
	/**
	 * 删除一个指定的客户
	 * @param customerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value="客户管理")
	public Map<String, Object> delete(Integer customerId){
		Map<String, Object> resultMap=new HashMap<>();
		logService.log(new Log(Log.DELETE_ACTION, "删除了一个客户信息:"+customerService.getCustomerById(customerId).toString()));
		customerService.delete(customerId);
		resultMap.put("success", true);
		return resultMap;
	}
}
