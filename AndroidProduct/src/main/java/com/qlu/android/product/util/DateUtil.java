package com.qlu.android.product.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	private static final String[] WEEKDAYS_CN = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	
	private static final String[] WEEKDAYS_EN = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
	
	public static String getENWeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return WEEKDAYS_EN[w];
	}
	
	public static String getCNWeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return WEEKDAYS_CN[w];
	}
	
	public static String getCurrentHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		return String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
	}
	
	public static String getBeforeOneHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		return String.valueOf(cal.get(Calendar.HOUR_OF_DAY) == 0 
				? 23 : cal.get(Calendar.HOUR_OF_DAY) - 1);
	}
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDD2 = "yyyyMMdd";
	/**
	 * 日期转换成字符串 
	 * @param date  日期
	 * @param format 转换格式
	 * @return
	 */
	public static String format(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	


	public static synchronized String format(String str){
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		SimpleDateFormat sdf2 = new SimpleDateFormat(YYYYMMDD2);
		try {
			return sdf2.format(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(String date,String fromat,String fromat2){
		if(StringUtils.isNotBlank(date)){
			SimpleDateFormat sdf = new SimpleDateFormat(fromat);
			Date dates = null;
			try {
				dates = sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sdf = new SimpleDateFormat(fromat2);
			return sdf.format(dates);
		}
		return "";
	}
	
	/**
	 * 获得格式化时间
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return String
	 * @throws ParseException
	 */
	public static Date stringToDate(String strDate, String pattern)
			throws ParseException {
		if ("".equals(strDate.trim()))
			return null;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date d = df.parse(strDate);
		return d;
	}
	/**
	 * 获取当前时间所在周的第一天(周一为第一天)
	 * @return
	 */
	public static String getFirstDayOfWeek(){
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return DateUtil.format(c.getTime(),DateUtil.YYYYMMDDHHMMSS);
	}
	
	/**
	 * 获取当前时间所在周的最后一天
	 * @return
	 */
	public static String getLastDayOfWeek(){
		Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return DateUtil.format(c.getTime(),DateUtil.YYYYMMDDHHMMSS);
	}


	
	public static void main(String[] args) {
		try {
			DateUtil.stringToDate("20160101", DateUtil.YYYYMMDD2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
