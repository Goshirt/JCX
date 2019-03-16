package com.helmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问控制
 * 
 * @author Helmet
 * 2018年5月30日
 */
@Controller

public class IndexController {
	
	/**
	 * 访问项目url时都先跳转到登录页面
	 * 使用RequestMapping注解会自动将返回值解析为跳转路径，但加上RequestBody后不会
	 * @return
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "redirect:/login.html";
	}
}
