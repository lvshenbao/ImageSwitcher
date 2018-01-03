package com.android.lv.imageswitcher.util;

import android.content.Context;
import android.text.format.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期格式化工具类
 * @author lv
 *
 */

public class DateUtil
{

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * long 格式化为指定格式的时间
	 * @param time
	 * @param formate
	 * @return
	 */
	public static String formateDate(long time, String formate)
	{
		if(formate == null || "".equals(formate)) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(formate, Locale.CHINA);
		return sdf.format(new Date(time));
	}
	
	/**
	 * 将字符串的时间转换为时间戳
	 * @param time
	 * @return
	 */
	public static long formateDateToLong(String time)
	{
		if(time == null || "".equals(time)) return 0;
		return new Date(time).getTime();
	}
	
	/**
	 * long 格式化  年 月  日  时  分  秒
	 * @param date
	 * @return
	 */
	public static String formatDateDT(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}
	
	/**
	 * long 格式化  年 月  日  时  分  
	 * @param date
	 * @return
	 */
	public static String formatDateDM(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}

	/**
	 * long 格式化  年 月  日
	 * @param date
	 * @return
	 */
	public static String formatDateD(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}
	
	/**
	 * long 格式化  月  日  时  分
	 * @param date
	 * @return
	 */
	public static String formatDateMD(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd  HH:mm", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}
	
	/**
	 * long 格式化  月  日  时  分
	 * @param date
	 * @return
	 */
	public static String formatDateMS(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}
	
	/**
	 * long 格式化  年 月  日
	 * @param date
	 * @return
	 */
	public static String formatDateDN(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}
	
	/**
	 * long 格式化 月  日
	 * @param date
	 * @return
	 */
	public static String formatDateMday(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}

	
	/**
	 * long 格式化时分
	 * @param date
	 * @return
	 */
	public static String formatDateMM(long date) {
		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}

	/**
	 * long 格式化 yyyy/MM/dd  HH:mm
	 * @param date
	 * @return
	 */
	public static String formatDetailedDate(long date) {
//		if(date == 0) return "";
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  HH:mm", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(currentdate);
	}
	
	/**
	 * 组织成字符串  2013年10月22日
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String formatPickerDate(int year, int month, int day) {

		month++;
		StringBuilder formatStr = new StringBuilder();
		formatStr.append(year);
		formatStr.append("年");
		if (month < 10) {
			formatStr.append(0);
		}
		formatStr.append(month);
		formatStr.append("月");
		if (day < 10) {
			formatStr.append(0);
		}
		formatStr.append(day);
		formatStr.append("日");

		return formatStr.toString();
	}
	
	/**
	 * 格林威治时间转现在时间
	 * @param date
	 * @return
	 */
	public static String fromGreenwichtoNow(long date)
	{
		if(date == 0) return "";
		return formatDateDM(date);
	}
	
	/**
	 * 获取年
	 * @param date
	 * @return
	 */
	public static int getYear(long date)
	{
		if(date == 0) return 0;
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String datetime = sdf.format(currentdate);
		return Integer.parseInt(datetime);
	}
	
	/**
	 * 获取月
	 * @param date
	 * @return
	 */
	public static int getMonth(long date)
	{
		if(date == 0) return 0;
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String datetime = sdf.format(currentdate);
		return Integer.parseInt(datetime);
	}
	
	/**
	 * 获取日
	 * @param date
	 * @return
	 */
	public static int getDay(long date)
	{
		if(date == 0) return 0;
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String datetime = sdf.format(currentdate);
		return Integer.parseInt(datetime);
	}
	
	/**
	 * 获取  几时
	 * @param date
	 * @return
	 */
	public static int getHours(long date)
	{
		if(date == 0) return 0;
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm-ss", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String datetime = sdf.format(currentdate);
		String hours = datetime.substring(datetime.indexOf(":")+1, datetime.lastIndexOf(":"));
		return Integer.parseInt(hours);
	}

	/**
	 * 获取  几分
	 * @param date
	 * @return
	 */
	public static int getMinute(long date)
	{
		if(date == 0) return 0;
		Date currentdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String datetime = sdf.format(currentdate);
		String minute = datetime.substring(datetime.indexOf(":")+1, datetime.lastIndexOf(":"));
		return Integer.parseInt(minute);
	}
	
	/**
	 * 比较日期是否一致
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Boolean isSameDay(long date1, long date2)
	{
		return formatDateDN(date1).equals(formatDateDN(date2)) ? true :false;
	}

	/**
	 * 比较两个时间是否足够一分钟
	 * @param newtime
	 * @param oldtime
     * @return
     */
	public static Boolean isCloseEnough(long newtime, long oldtime)
	{
		return newtime-oldtime<60*1000;
	}
	
	private static final String[] WEEKS = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	private static final int[] DAYOFWEEK = {7,1,2,3,4,5,6};//一周的第几天
	
	/**
	 * 通过Calendar获取年份
	 * @param calendar
	 * @return
	 */
	public static int getYear(Calendar calendar)
	{
		if(calendar == null) return 0;
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 通过Calendar获取周的第几天
	 * @param calendar
	 * @return
	 */
	public static int getDayOfWeek(Calendar calendar)
	{
		if(calendar == null) return 0;
		return DAYOFWEEK[calendar.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	/**
	 * 通过Calendar获取年的第几周
	 * @param calendar
	 * @return
	 */
	public static int getWeekOfYear(Calendar calendar)
	{
		if(calendar == null) return 0;
		int day = getDayOfWeek(calendar);
		return day==7?calendar.get(Calendar.WEEK_OF_YEAR)-1:calendar.get(Calendar.WEEK_OF_YEAR);
	}

	
	/**
	 * 获取指定日期的周
	 * @param calendar
	 * @return
	 */
	public static String[] getWeekDateMDByCalendar(Calendar calendar)
	{
		// ----计算所在周的日期start-----

		//logger.info("date="+DateUtil.formateDate(calendar.getTime().getTime(), "yyyy-MM-dd HH:mm:ss"));
		// 获取指定周的周一到周日的阳历和农历日期
		String[] dates = new String[WEEKS.length];// 阳历日期
		
		if(calendar == null) return dates;
		
		Calendar calendar_temp = null;
		for (int i = 0; i < WEEKS.length; i++)
		{
			calendar_temp = calendar;			

			// 获取阳历日期			
			calendar_temp.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置起始时间为指定周的周一
			calendar_temp.add(Calendar.DAY_OF_MONTH, i);// 获取后一天的日期
			dates[i] = formateDate(calendar_temp.getTime().getTime(), "yyyy.M.d");//计算该天的阳历日期					
		}				

		// ----计算今天所在周的日期end-----
		
		return dates;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d", Locale.CHINA);
	public static String[] getWeekDateMDByCalendar1(Calendar calendar)
	{
		// ----计算所在周的日期start-----

		//logger.info("date="+DateUtil.formateDate(calendar.getTime().getTime(), "yyyy-MM-dd HH:mm:ss"));
		// 获取指定周的周一到周日的阳历和农历日期
		String[] dates = new String[7];// 阳历日期
		
		if(calendar == null) return dates;
		
		//Calendar calendar_temp = null;
		for (int i = 0; i < dates.length; i++)
		{
			//calendar_temp = calendar;

			// 获取阳历日期
			
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置起始时间为指定周的周一
			calendar.add(Calendar.DAY_OF_MONTH, i);// 获取后一天的日期
			dates[i] = sdf.format(calendar.getTime());//计算该天的阳历日期			
		}				

		// ----计算今天所在周的日期end-----
		
		return dates;
	}
	
	/**
	 * 获取指定年的周数
	 * @param year
	 * @return
	 */
	public static int getWeekNumOfYear(int year) 
    {
		if(year == 0) return 0;
		
        int week = 0;
        int days = 365;
        int day = 0;
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) 
        {//判断是否闰年，闰年366天
            days = 366;
        }
        //得到一年所有天数然后除以7
        day = days % 7;//得到余下几天
        week = days / 7 + (day>0?1:0);//得到多少周
        return week;
    }
	
	/**
	 * 获取指定年份的周
	 * @param year
	 * @return
	 */
	public static Map<String, String[]> getWeeksOfYear(int year)
	{
		Map<String, String[]> weeks = new HashMap<String, String[]>();
		
		if(year == 0) return weeks;
		
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		//calendar.set(year, 0, 1);//置为指定年份的起始日
		int weeknum = getWeekNumOfYear(year);//获取指定年份的周数
		logger.info("本年周数："+weeknum);
		
		//Calendar cal_curr = Calendar.getInstance(Locale.CHINESE);
		//cal_curr.set(Calendar.MONTH, 6);
		int month_curr = calendar.get(Calendar.MONTH)+1;//获取当前月份
		logger.info("当前月份："+month_curr);				

		for(int week=0;week<weeknum;week++)
		{
			//Calendar cal = Calendar.getInstance(Locale.CHINESE);			
			//cal.set(year, 0, 1);//每次重置日期为年的起始日			
			
			//logger.info("day="+day);
			calendar.set(Calendar.WEEK_OF_YEAR, 1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.add(Calendar.DAY_OF_YEAR, week*7);
			//logger.info("date="+DateUtil.formateDate(cal.getTime().getTime(), "yyyy-MM-dd HH:mm:ss"));
			String[] week_days = DateUtil.getWeekDateMDByCalendar1(calendar);
			weeks.put(String.valueOf(week+1), week_days);
		}		
		
		return weeks;
	}
	
	public static Map<String, String[]> getWeeksOfYear1(int year)
	{
		Map<String, String[]> weeks = new HashMap<String, String[]>();
		
		if(year == 0) return weeks;
		
		Calendar calendar = new GregorianCalendar(Locale.CHINA);
		//Calendar calendar = Calendar.getInstance(Locale.CHINESE);		
		int weeknum = getWeekNumOfYear(year);//获取指定年份的周数
		logger.info("本年周数："+weeknum);
//		
//		int month_curr = calendar.get(Calendar.MONTH)+1;//获取当前月份
//		logger.info("当前月份："+month_curr);				

		for(int week=0;week<weeknum;week++)
		{
			calendar.set(Calendar.WEEK_OF_YEAR, 1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.add(Calendar.DAY_OF_YEAR, week*7);
			String[] week_days = DateUtil.getWeekDateMDByCalendar1(calendar);
			weeks.put(String.valueOf(week+1), week_days);
		}
		
		for(int week=0;week<weeknum;week++)
		{
			logger.error("第"+(week+1)+"周");
			calendar.set(Calendar.WEEK_OF_YEAR, 1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.add(Calendar.DAY_OF_YEAR, week*7);
			String[] datas = getWeekDateMDByCalendar1(calendar);
			
			for(int i =0;i<datas.length;i++)
			{
				logger.error(datas[i]);
			}			
		}
		
		return weeks;
	}
	
	/** 
	 * 将时间戳转为代表"距现在多久之前"的字符串 (一天内)
	 * @return 
	 */  
	public static String getStandardDate(Long dateTime) {
	  
		if(dateTime == 0) return "";
		
	    StringBuffer sb = new StringBuffer();
	    
	    //如果是格林威治时间 请 dateTime * 1000
	    long time = System.currentTimeMillis() - (dateTime);
	    
	    //long mill = (long) Math.floor(time/1000);  //秒
	  
	    long minute = (long) Math.floor(time/60/1000.0f);// 分钟
	  
	    long hour = (long) Math.floor(time/60/60/1000.0f);// 小时
	  
	    //long day = (long) Math.floor(time/24/60/60/1000.0f);// 天前  
	    
	    
	    if(hour < 24 && hour > 0)
	    {
	    	sb.append(hour);
	    	sb.append("小时前"); 
	    }
	    else
	    {
	    	 if(minute%60 > 30)
	 	    {
	 	    	sb.append("半小时前");
	 	    }
	 	    else if(minute%60 > 10)
	 	    {
	 	    	sb.append(minute);
	 	    	sb.append("分前");
	 	    }
	 	    else
	 	    {
	 	    	sb.append("刚刚");
	 	    }
	    }
	    return sb.toString();  
	}	
	
	// 如果为今天，则显示时间，如果不是今天则显示日期：
	public static String getDate(Context mContext, long date)
    {  
		if(date == 0) return "";
		
        String mdate = "";
          
        if (DateUtils.isToday(date))
        {  
        	//只显示  时 ：分
//            flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR;  
//            mdate = (String)DateUtils.formatDateTime(mContext, dateTime, flags);  
        	mdate = getStandardDate(date);
        }
        else  
        {  
            int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME;
            mdate = (String) DateUtils.formatDateTime(mContext, date, flags);
        }  
        return mdate;  
    }
	
	// 如果为今天，则显示时间，如果不是今天则显示日期("yyyy-MM-dd");
	public static String getNormalDate(Context mContext, long date)
    {  
		if(date == 0) return "";
		
        String mdate = "";
          
        if (DateUtils.isToday(date))
        {  
        	mdate = getStandardDate(date);
        }
        else  
        {  
            mdate = formatDateD(date);
        }  
        return mdate;  
    }
	
	public static String getDateNoHm(Context mContext, String date)
    {  
		if(date == null) return "";
		
		Long dateTime = Long.parseLong(date);
        String mdate = "";
          
        if (DateUtils.isToday(dateTime))
        {
        	mdate = getStandardDate(dateTime);
        }
        else  
        {  
            int flags = DateUtils.FORMAT_SHOW_DATE;
            mdate = (String) DateUtils.formatDateTime(mContext, dateTime, flags);
        }  
        return mdate;  
    }
	
	public static final String[] REPTIME = {"早餐","中餐","晚餐"};
	
	//获取菜谱时间处于一天中的哪个时段，早中晚
	public static String getTimeDurationInDay()
	{		
		String str = "";
		Date date = new Date();
		int hour = date.getHours();
		if(hour>=6 && hour<11)
		{
			str = "早上";
		}
		else if(hour>=11 && hour<16)
		{
			str = "中午";
		}
		
		else
		{
			str = "晚上";
		}
		
		return str;
	}
	
	/**
	 * 获取指定的时间戳
	 * @return
	 */
	public static long getCurrentYMD(String hms) throws Exception
	{		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH)+1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		return sdf.parse(y+"-"+m+"-"+d+" "+hms).getTime();
	}
	
	/**
	 * 获取指定时间与当前时间的差距
	 * @param dataTime
	 * @return
	 */
	public static String getElapsedTime(long dataTime)
	{
		String result = "";
		long diff = System.currentTimeMillis()-dataTime;
		long dayTime = 24*60*60*1000L; 
		long hourTime = 60*60*1000L;
		long minTime = 60*1000L;
		
		if(diff/dayTime>1)
		{
			result = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINESE).format(new Date(dataTime));
		}
		else if(diff/dayTime>0 && diff/dayTime<=1)
		{
			result = "昨天";
		}
		else if(diff/hourTime>0 && diff/hourTime<=24)
		{
			result = diff/hourTime+"小时前";
		}
		else if(diff/minTime>1 && diff/minTime<=60)
		{
			result = diff/minTime+"分钟前";
		}
		else
		{
			result = "刚刚";
		}
		
		return result;
	}
	
	/**
	 * 获取提问的时间
	 * @return
	 */
	public static String getQuestionTime(long datatime)
	{
		logger.info("datatime="+datatime);
		try
		{
			String ret = "";
			long create = datatime;
			Calendar now = Calendar.getInstance();
			int now_year = now.get(Calendar.YEAR);
			logger.info("now_year="+now_year);
			long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600
					+ now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));// 毫秒数
			long ms_now = now.getTimeInMillis();
			
			now.setTimeInMillis(datatime);
			if (ms_now - create < ms)
			{
				ret = new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date(datatime));
			}
			else if (ms_now - create < (ms + 24 * 3600 * 1000))
			{
				ret = "昨天";
			}
			else if (now.get(Calendar.YEAR) == now_year)
			{
				//今年
				ret = new SimpleDateFormat("MM/dd", Locale.CHINA).format(new Date(datatime));
			}
			else
			{
				ret = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA).format(new Date(datatime));
			}
			
			return ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
