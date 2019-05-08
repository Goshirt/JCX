package com.helmet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	/**
	 * 格式化日期為字符串
	 * @param date
	 * @param pattern 格式化成字符串的樣式
	 * @return
	 */
	public static String dateToString(Date date,String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (date != null){
			return simpleDateFormat.format(date);
		}else {
			return null;
		}
	}

	/**
	 * 日期字符串轉換為日期格式
	 * @param string  日期字符串
	 * @param pattern  格式
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String string,String pattern) throws ParseException {
		if (StringUtil.isEmpty(string)){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(string);
	}

	/**
	 * 獲取範圍時間段的每一天日期的集合
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getPerDay(String startDate, String endDate) throws ParseException {
		List<String> perDateList = new ArrayList<>();
		Calendar startCalender = Calendar.getInstance();
		Calendar endCalender = Calendar.getInstance();
		startCalender.setTime(stringToDate(startDate,"yyyy-MM-dd"));
		endCalender.setTime(stringToDate(endDate,"yyyy-MM-dd"));
		while (startCalender.before(endCalender)){
			perDateList.add(dateToString(startCalender.getTime(),"yyyy-MM-dd"));
			startCalender.add(Calendar.DAY_OF_MONTH,1);
		}
		perDateList.add(endDate);
		return perDateList;
	}

	/**
	 * 获取时间段内的月份集
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getPerMonth(String beginDate,String endDate) throws  ParseException{
		ArrayList<String> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(sdf.parse(beginDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		max.setTime(sdf.parse(endDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			List<String> perDateList = getPerMonth("2018-10-11","2019-12-10");
			System.out.println(perDateList.size());
			for (String stringDate:perDateList) {
				System.out.println(stringDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
