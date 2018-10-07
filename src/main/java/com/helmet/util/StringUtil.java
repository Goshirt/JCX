package com.helmet.util;

/**
 * 字符串工具类
 * @author java1234 小锋 老师
 *
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 截取传入参数的最后四位并增加1
	 * @param string
	 * @return
	 */
	public static String genFourCode(String string){
		String subStr=string.substring(string.length()-4, string.length());
		Integer numStr=Integer.valueOf(subStr)+1;
		String code=numStr.toString();
		int numStrLength=code.length();
		for(int i=4;i>numStrLength;i--){
			code="0"+code;
		}
		return code;
	}
	
	public static void main(String[] args) {
		System.out.println(genFourCode("JH2034800001"));
	}
	
}
