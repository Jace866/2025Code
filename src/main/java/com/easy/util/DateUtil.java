/*
 * Created on 2005-6-10
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/********************************************************
*  Copyright (C)
*  File name:      // 文件名
*  Author:         // 作者
*  Date:           
*  Description:    // 详细说明此程序文件完成的主要功能
*  Others:         // 其它内容的说明
*  History:
*    1. Date:           // 修改日期
*       Author:         // 作者
*       Modification:   
********************************************************/
package com.easy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jason
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateUtil {
	public final static String yyyy_MM_dd = "yyyy-MM-dd";
	public final static String yyMMdd = "yyMMdd";
	public final static String yyyyMMdd = "yyyyMMdd";
	public final static String yyyyMM = "yyyyMM";
	public final static String yyyy_MM = "yyyy-MM";
	public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public final static String yyyyMMddHHmm = "yyyyMMddHHmm";
	public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public final static String yyMMddHHmmss = "yyyyMMddHHmmss";
	public final static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	
	/**
	 * 将字符串时间改成Date类型
	 * @param format
	 * @param dateStr
	 * @return
	 */
	public  static Date strToDate(String format,String dateStr) {
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * 将Date时间转成字符串
	 * @param format
	 * @param date
	 * @return
	 */
	public static String dateToStr(String format,Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);	
	}

	/**
	 * 将Date时间转成字符串 "yyyy-MM-dd HH:mm:ss";
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		return simpleDateFormat.format(date);
	}
	

	/**
	 * 现在日期
	 * @return
	 */
	public static String today(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd);
		return simpleDateFormat.format(new Date());	
	}

	/**
	 * 现在时间
	 * @return
	 */
	public static String now(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss);
		return simpleDateFormat.format(new Date());	
	}

	/**
	 * 现在时间
	 * @return
	 */
	public static String nowtime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		return simpleDateFormat.format(new Date());	
	}

	/**
	 * 现在时间
	 * @return
	 */
	public static String getPrefix(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyMMdd);
		return simpleDateFormat.format(new Date());	
	}
	public static String getPrefix(String nowtime){
		return nowtime.substring(2,10).replaceAll("-", "");
	}

	/**
	* 获取2个字符日期的天数差
	* @param p_startDate
	* @param p_endDate
	* @return 天数差
	*/
	public static long getDaysOfTowDiffDate( String p_startDate, String p_endDate ){
		Date l_startDate = DateUtil.strToDate(DateUtil.yyyy_MM_dd, p_startDate);
		Date l_endDate = DateUtil.strToDate(DateUtil.yyyy_MM_dd, p_endDate);
		long l_startTime = l_startDate.getTime();
		long l_endTime = l_endDate.getTime();
		long betweenDays = (long) ( ( l_endTime - l_startTime ) / ( 1000 * 60 * 60 * 24 ) );
		return betweenDays;
	}
	

	/**
	* 获取2个Date型日期的分钟数差值
	* @param p_startDate
	* @param p_endDate
	* @return 分钟数差值
	*/
	public static long getMinutesOfTowDiffDate( String p_startDate, String p_endDate ){
		Date l_startDate = DateUtil.strToDate(DateUtil.yyyy_MM_dd_HH_mm_ss, p_startDate);
		Date l_endDate = DateUtil.strToDate(DateUtil.yyyy_MM_dd_HH_mm_ss, p_endDate);
		return getMinutesOfTowDiffDate(l_startDate, l_endDate);
	}

	/**
	 * 获取2个Date型日期的分钟数差值
	 * @param l_startDate
	 * @param l_endDate
	 * @return 分钟数差值
	 */
	public static long getMinutesOfTowDiffDate( Date l_startDate, Date l_endDate ){
		long l_startTime = l_startDate.getTime();
		long l_endTime = l_endDate.getTime();
		long betweenMinutes = (long) ( ( l_endTime - l_startTime ) / ( 1000 * 60 ) );
		return betweenMinutes;
	}

	/**
	 * 获取2个字符日期的天数差
	 * @param l_startDate
	 * @param l_endDate
	 * @return 天数差
	 */
	public static long getDaysOfTowDiffDate( Date l_startDate, Date l_endDate ){
		long l_startTime = l_startDate.getTime();
		long l_endTime = l_endDate.getTime();
		long betweenDays = (long) ( ( l_endTime - l_startTime ) / ( 1000 * 60 * 60 * 24 ) );
		return betweenDays;
	}
	
	
	/**
	 * 给出日期添加一段时间后的日期
	 * @param dateStr
	 * @param plus
	 * @return
	 */
	public static String getPlusDays(String format,String dateStr,long plus){
		Date date = DateUtil.strToDate(format, dateStr);
		long time = date.getTime()+ plus*24*60*60*1000;
		return DateUtil.dateToStr(format,new Date(time));
	}
	
	/**
	 * 给出日期添加多少天后的日期
	 * @param date
	 * @param plus
	 * @return
	 */
	public static Date getPlusDays(Date date,long plus){
		long time = date.getTime()+ plus*24*60*60*1000;
		return new Date(time);
	}
	
	public static Date getPlusSecond(Date date,long plus){
		long time = date.getTime()+ plus*1000;
		return new Date(time);
	}

	/**
	 * 给出日期添加一段时间后的日期
	 * @param format
	 * @param date
	 * @param plus
	 * @return
	 */
	public static String getPlusDays(String format,Date date,long plus){
		long time = date.getTime()+ plus*24*60*60*1000;
		return DateUtil.dateToStr(format,new Date(time));
	}
	
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
	 * @throws ParseException 
     */
    public static String getWeekOfDate(String dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dt));
		} catch (ParseException e) {
			return "";
		}
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        return weekDays[w];
    }
    
    /**
     * 获取当年当月的总天数
     * @param MONTH
     * @param YEAR
     * @return int
     */
    public static int getMaxDateOfMonth(int MONTH, int YEAR){
		if(MONTH>7){
			if(MONTH%2==0)
				return 31;
			else
				return 30;
		}else{
			if(MONTH==2){
				if(YEAR%400==0||(YEAR%4==0&&YEAR%100!=0))
					return 29;
				else
					return 28;
			}else{
				if(MONTH%2==0)
					return 30;
				else
					return 31;
			}
		}
	}    
        
	public static int getDiffMonthUper(Date start_date, Date end_date) {
		Calendar cal_start = Calendar.getInstance();
		cal_start.setTime(start_date);
		Calendar cal_end = Calendar.getInstance();
		cal_end.setTime(end_date);
		int diffmonth = (cal_end.get(Calendar.YEAR)-cal_start.get(Calendar.YEAR))*12
				+(cal_end.get(Calendar.MONTH)-cal_start.get(Calendar.MONTH));
		if(cal_end.get(Calendar.DAY_OF_MONTH)>cal_start.get(Calendar.DAY_OF_MONTH)){
			diffmonth = diffmonth+1;
		}
		return diffmonth;
	}
	

	public static Date addMonth(Date start_date, int month) {
		Calendar cal_start = Calendar.getInstance();
		cal_start.setTime(start_date);
		cal_start.add(Calendar.MONTH, month); 
		return cal_start.getTime();
	}

	public static String strToDateFormat(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMdd);
		formatter.setLenient(false);
		Date newDate= null;
		try {
			newDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter = new SimpleDateFormat(yyyy_MM_dd);
		return formatter.format(newDate);
	}

	/**
	 * 获取过去第几天的日期
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来第 past 天的日期
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 现在日期
	 * @return
	 */
	public static String getCurrDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd);
		return simpleDateFormat.format(new Date());
	}

	public static String yesterday(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date today = new Date();
		Date calendar = yesterday(today);
		return formatter.format(calendar.getTime());
	}
	/**
	 * 返回昨天
	 * @return
	 */
	public static Date yesterday(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		return calendar.getTime();
	}

	public static String tomorrow(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date today = new Date();
		Date calendar = tomorrow(today);
		return formatter.format(calendar.getTime());
	}
	/**
	 * 返回明天
	 * @return
	 */
	public static Date tomorrow(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		return calendar.getTime();
	}

	/**
	 * 判断时间是否在时间段内
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(String beginTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		Date nowTime =null;
		Date beginDate = null;
		Date endData = null;
		try {
			nowTime = df.parse(df.format(new Date()));
			beginDate = df.parse(beginTime);
			endData = df.parse(endTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginDate);

		Calendar end = Calendar.getInstance();
		end.setTime(endData);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	//获取本周第一天
	public static String getWeekStartDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date calendar = getWeekStartDate();
		return formatter.format(calendar.getTime());
	}
	public static Date getWeekStartDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date date = cal.getTime();
		return date;
	}
	//获取本月第一天
	public static String getMonthFirstDay(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date calendar = getMonthFirstDay();
		return formatter.format(calendar.getTime());
	}
	public static Date getMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.MONTH,0);
		return calendar.getTime();
	}

	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}


	/**
	 * 获取当年的第一天
	 */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取过去多少分钟后的时间
	 * @param past
	 * @return
	 */
	public static String getPastDateTime(int past) {
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, past);
		Date time = nowTime.getTime();
		SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		String result = format.format(time);
		return result;
	}
	/**
	 * 校验时间格式是否为 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Boolean isDateVail(String date) {
		//用于指定 日期/时间 模式
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(yyyy_MM_dd_HH_mm_ss);
		boolean flag = true;
		try {
			//Java 8 新添API 用于解析日期和时间
			LocalDateTime.parse(date, dtf);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	//是不是昨天
	public static boolean isYesToday(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = new Date(System.currentTimeMillis());
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);
		int tmp = 86400000;
		long day1 = date.getTime() / tmp;
		long day2 = date2.getTime() / tmp;
		if (day2-day1 == 1) {
			return true;
		} else {
			return false;
		}
	}
}
