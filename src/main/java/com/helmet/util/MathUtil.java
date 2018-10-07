package com.helmet.util;

/**
 * 数字工具类
 * 
 * @author Helmet
 * 2018年9月17日
 */
public class MathUtil {

	/**
	 * float类型数据保留两位小数
	 * @param num
	 * @return
	 */
	public static float float2Bit(float num){
		float formatNum = (float)Math.round((num*100))/100;
		return formatNum;
	}
	
	public static void main(String[] args) {
		System.out.println(float2Bit(3.1234f));
	}
}
