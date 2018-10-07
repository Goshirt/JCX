package com.helmet.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author Helmet
 * 2018年9月8日
 */
public class DateUtil {
	
	/**
	 * 获取当前时间的字符串形式
	 * @return
	 */
	public static String formatCurentDate(){
		Date date=new Date();
		SimpleDateFormat sFormat=new SimpleDateFormat("yyyyMMdd");
		return sFormat.format(date);
	}
}
