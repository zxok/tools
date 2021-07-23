package com.hzx.tools.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-10-31 11:36
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/** 锁对象 */
	private static final Object lockObj = new Object();

	/** 存放不同的日期模板格式的sdf的Map */
	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	/**
	 * @Description:  返回一个ThreadLocal的sdf,每个线程只会new一次sdf
	 * @Date: 2020/4/24 16:01
	 * @Version: V1.0
	 */
	public static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
					logger.info("put new sdf of pattern " + pattern + " to map");

					// 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					tl = new ThreadLocal<SimpleDateFormat>() {

						@Override
						protected SimpleDateFormat initialValue() {
							logger.info("thread: " + Thread.currentThread() + " init pattern: " + pattern);
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}

		return tl.get();
	}

	public static String getMonAndDay(){
		SimpleDateFormat MDFormat = new SimpleDateFormat("MM-dd");
		Date date = new Date();
		String format = MDFormat.format(date);
		return format;
	}
	/**
	 * yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String string = shortDateFormat.format(date);
		return string;
	}

    public static String formatDayBegin(String date) {
        return date + " 00:00:00";
    }

    public static String formatDayEnd(String date) {
        return date + " 23:59:59";
    }
	/**
	 * yyyy-MM-dd HH:00:00
	 * @param date
	 * @return
	 */
	public static String formatDayAndHour(Date date) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String string = longDateFormat.format(date);
		return string;
	}
	
	/**
	 * 获取某一分钟的当分钟零时
	 * @param date
	 * @return
	 */
	public static Date getCurrMinuteFirst(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0); //秒
		cal.set(Calendar.MILLISECOND, 0); //毫秒
		Date time2 = cal.getTime();
		return time2;
	}



	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDayAndTime(Date date) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = longDateFormat.format(date);
		return string;
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDayAndTime2(Date date) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		String string = longDateFormat.format(date);
		return string;
	}

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDayAndTime(Date date,String format) {
        SimpleDateFormat longDateFormat = new SimpleDateFormat(format);
        String string = longDateFormat.format(date);
        return string;
    }

	public static String formatDate(Date date, String string) {
		return new SimpleDateFormat(string).format(date);
	}

	/**
	 * 字符串转换为时间
	 * @param string
	 * @return yyyy-MM-dd
	 */
	public static Date parseDay(String string) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = shortDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}

	/**
	 *	字符串转换为时间
	 * @param string
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseDayAndTime(String string) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = longDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}
	/**
	 *
	 * @param string
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseDayAndTime2(String string) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
		Date date = null;
		try {
			date = longDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}
	/**
	 *
	 * @param string
	 * @return yyyy-MM-dd HH
	 */
	public static Date parseOnlyHour(String string) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		Date date = null;
		try {
			date = longDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}

	/**
	 * 
	 * @param string
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseDayAndHour(String string) {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		Date date = null;
		try {
			date = longDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}
	
	/**
	 * @param string
	 * @return yyyy-MM
	 */
	public static Date parseYearAndMonth(String string) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = shortDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}
	/**
	 * @param string
	 * @return yyyy-
	 */
	public static Date parseYear(String string) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy");
		Date date = null;
		try {
			date = shortDateFormat.parse(string);
		} catch (ParseException e) {
			logger.error("时间转换格式错误");
		}
		return date;
	}
	/**
	 * @param date yyyy-MM
	 * @return 
	 */
	public static String formatYearAndMonth(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM");
		String format = shortDateFormat.format(date);
		return format;
	}
	/**
	 * @param date yyyyMM
	 * @return 
	 */
	public static String formatYearAndMonthNoBar(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyyMM");
		String format = shortDateFormat.format(date);
		return format;
	}
	/**
	 * @param date yyyy
	 * @return 
	 */
	public static String formatYear(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy");
		String format = shortDateFormat.format(date);
		return format;
	}
	
	
	/**
	 * 获取指定日期的指定整点时间
	 * @param date 指定日期
	 * @param hour 小时
	 * @return
	 */
	public static Date getDate(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND,0);
		Date result = calendar.getTime();
		return result;
	}

	/**
	 * 获取当天日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static Date getTodayShort() {
		Date shortParse = parseDay(formatDay(new Date()));
		return shortParse;
	}
	/**
	 * 获取当天日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getTodayShortString() {
		String shortFormat = formatDay(new Date());
		return shortFormat;
	}
	/**
	 * 获取当天日期
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date getTodayLong() {
		Date shortParse = parseDayAndTime(formatDayAndTime(new Date()));
		return shortParse;
	}

	/**
	 * 获取两个时间之间的分组
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return startDate-endDate
	 */
	public static Integer getDiffMin(Date startDate, Date endDate) {
		long time = startDate.getTime();
		long time2 = endDate.getTime();
		int diffMin = (int) ((time - time2) / 1000 / 60);
		return diffMin;
	}

	/**
	 * 生成两个时间间隔内的日期
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static List<Date> dateSplit(Date start, Date end) throws Exception {
		if (!start.before(end)) {
			throw new Exception("开始时间应该在结束时间之后");
		}
		Long spi = end.getTime() - start.getTime();
		Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(start);
		for (int i = 1; i <= step; i++) {
			dateList.add(new Date(dateList.get(i - 1).getTime()
					+ (24 * 60 * 60 * 1000)));// 比上一天加一
		}
		return dateList;
	}

	/**
	 * 判断是否是周末
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isHolidays(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 判断日期是否是周六周日
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	/**
	 * 获取一年中第一个月的第一天
	 * 
	 * @return
	 */
	public static String getFirstMonthAndDay() {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return shortDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取一年中最后一个月的最后一天
	 * 
	 * @return
	 */
	public static String getLastMonthAndDay() {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return shortDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期
	 * @return yyyy-MM-dd
	 */
	public static String getTodayString() {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = shortDateFormat.format(date);
		return today;
	}

	/**
	 * 获取当前时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getTodayStringLong() {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String today = longDateFormat.format(date);
		return today;
	}
	/**
	 * 获取两个时间的差
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDay(Date startDate,Date endDate){
		long start = startDate.getTime();
		long end = endDate.getTime();
		long day=(start-end)/(1000 * 60 * 60 * 24);
		return day;
	}
	
	public static int getSecondTimestampTwo(Date date){
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime()/1000);
		return Integer.valueOf(timestamp);
	}
	
	public static String timeStamp2Date(String timestamp){
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(timestamp == null || timestamp.isEmpty() || timestamp.equals("null")){  
            return "";  
        }  
         
        return longDateFormat.format(new Date(Long.valueOf(timestamp+"000"))); 
	}
	/**
	 * 返回yyyyMMddHHmmss,
	 * @return
	 */
	public static String getTimespan() {
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = longDateFormat.format(new Date());
		return format;
	}

	public static Date getPreThreeDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -3);
		Date time = calendar.getTime();
		return time;
	}
	/**
	 * 获取当前年 yyyy
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = shortDateFormat.format(date);
		return year;
	}

	/**
	 * 获取指定时间年
	 * @param date
	 * @return yyyy
	 */
	public static String getYear(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy");
		return shortDateFormat.format(date);
	}
	/**
	 * 获取指定时间年-月
	 * @param date
	 * @return yyyy-MM
	 */
	public static String getYearMonth(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM");
		return shortDateFormat.format(date);
	}
	/**
	 * 获取指定时间年-月-日
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getYearMonthDay(Date date) {
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return shortDateFormat.format(date);
	}

	/**
	 * 获取下小时的零时
	 * @param date
	 * @return
	 */
	public static Date getNextHourFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取明天的零时
	 * @param date
	 * @return
	 */
	public static Date getNextDayFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取昨天的零时
	 * @return
	 */
	public static Date getLastDayFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取昨天的最后时间
	 * @return
	 */
	public static Date getLastDayLast(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取上小时的零时
	 * @return
	 */
	public static Date getLastHourFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某一时间的当小时零时
	 * @return
	 */
	public static Date getCurrHourFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获某一时间的取当天时的零时
	 * @return
	 */
	public static Date getCurrDayFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	
	/**
	 * 获取当月第一天
	 * @return
	 */
	public static Date getCurrMonthFirst(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static Date getCurrMonthLast(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某个时间的当月最后一天
	 * @return
	 */
	public static Date getCurrMonthLast(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某个时间的当月第一天
	 * @param date
	 * @return
	 */
	public static Date getCurrMonthFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某个时间的下个月第一天
	 * @param date
	 * @return
	 */
	public static Date getNextMonthFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取上月第一天
	 * @return
	 */
	public static Date getLastMonthFirst(){
		return getLastMonthFirst(new Date());
	}
	/**
	 * 获取上月最后一天
	 * @return
	 */
	public static Date getLastMonthLast(){
		return getLastMonthLast(new Date());
	}
	/**
	 * 获取某一时间点上月第一天
	 * @return
	 */
	public static Date getLastMonthFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某一时间点上月最后一天
	 * @return
	 */
	public static Date getLastMonthLast(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		Date first=calendar.getTime();
		return first;
	}
	
	/**
	 * 获取当前年第一天
	 * @return
	 */
	public static Date getCurrYearFirst(){
		Calendar calendar=Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return  getYearFirst(year);
	}
	/**
	 * 获取某年第一天
	 * @return
	 */
	public static Date getCurrYearFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return getYearFirst(year);
	}
	/**
	 * 获取明年第一天
	 * @return
	 */
	public static Date getNextYearFirst(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		int year = calendar.get(Calendar.YEAR);
		return getYearFirst(year);
	}
	/**
	 * 获取当前年最后一天
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar calendar=Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return  getYearLast(year);
	}
	/**
	 * 获取某年的最后一天
	 * @return
	 */
	public static Date getCurrYearLast(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return  getYearLast(year);
	}
	/**
	 * 获取某年的第一天
	 * @param year
	 * @return
	 */
	private static Date getYearFirst(int year) {
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某年的最后一天
	 * @param year
	 * @return
	 */
	private static Date getYearLast(int year) {
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date first=calendar.getTime();
		return first;
	}
	/**
	 * 获取某一年的第一天
	 * @return
	 */
	public static Date getLastYearFirst(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR)-1;
		return  getYearFirst(year);
	}
	/**
	 * 获取上一年的第一天
	 * @return
	 */
	public static Date getLastYearFirst() {
		return getLastYearFirst(new Date());
	}
	/**
	 * 获取上一年的最后一天
	 * @return
	 */
	public static Date getLastYearLast() {
		Calendar calendar=Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR)-1;
		return  getYearLast(year);
	}
	/**
	 * 获取上一年的最后一天
	 * @return
	 */
	public static Date getLastYearLast(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR)-1;
		return  getYearLast(year);
	}
	/**
	 * 获取上一年的今天
	 * @return
	 */
	public static Date getLastrYearToday() {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date time = calendar.getTime();
		return time;
	}
	
	/**
	 * 获取格里尼治时间
	 * @param date
	 * @return yyyy-MM-dd'T'hh:mm:ss'.'sss'Z'
	 */
	public static String date2GMT(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, -8);
		Date time = calendar.getTime();
		
		
//		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.ENGLISH);
//		longDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));
		SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
		String format = longDateFormat.format(time);
        return format; 
	}
	
	
	/**
	 * 获取Influx的时间
	 * @param date
	 * @return
	 */
	public static long date2InfluxTime(Date date){
		long time = date.getTime();
		return time*1000*1000;
	}
	/**
	 * 获取Influx的时间,字符串转long类型
	 * @param
	 * @return
	 */
	public static Long string2InfluxTime(String time){
		Date parse = null;
		try {
			parse = getSdf("yyyy-MM-dd HH:mm:ss").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null==parse?null:parse.getTime();
	}
	/**
	 * 生成当年第一月到当前月的第一天
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<Date> generFirstDayOfMonth() {
		List<Date> list=new ArrayList<Date>();
		Date now=new Date();
		int month = now.getMonth();
		for(int i=0;i<=month;i++){
			Date monthFirst = getMonthFirst(i);
			list.add(monthFirst);
		}
		return list;
	}
	/**
	 * 生成当年第一月到当前月的第一天
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<Date> generFirstDayOfMonthByYear(Date year) {
		List<Date> list=new ArrayList<Date>();
		Date now=DateUtil.getCurrYearLast(year);
		int yearInt = now.getYear()+1900;
		int month = now.getMonth();
		for(int i=0;i<=month;i++){
			Date monthFirst = getYearMonthFirst(yearInt,i);
			list.add(monthFirst);
		}
		return list;
	}
	/**
	 * 获取某个月的第一天，month==0表示1月
	 * @param month
	 * @return
	 */
	public static Date getMonthFirst(int month) {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH,month);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date=calendar.getTime();
		return date;
	}
	/**
	 * 获取某个月的第一天，month==0表示1月
	 * @param month
	 * @return
	 */
	public static Date getYearMonthFirst(int year,int month) {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.MONTH,month);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date=calendar.getTime();
		return date;
	}
	/**
	 * 获取制定时间两个时间段（小时）之间的零点时间
	 * @param inDate
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getHourZoreBetween(Date inDate,String start, String end) {
		List<Date> list=new ArrayList<Date>();
		int begin=Integer.parseInt(start);
		int finish=Integer.parseInt(end);
		for(int i=begin;i<=finish;i++){
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(inDate);
			calendar.set(Calendar.HOUR_OF_DAY, i);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date date=calendar.getTime();
			list.add(date);
		}
		return list;
	}

//    public static void main(String[] args) {
//		List<Date> monthZoreBetween = getMonthZoreBetween("2021-01", "2021-04");
//		monthZoreBetween.stream().forEach(bean->{
//			System.out.println(bean.toString());
//		});
//	}

    /**
	 * 获取两个时间段（天）之间的零点时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getDayZoreBetween(String start, String end) {
		List<Date> list=new ArrayList<Date>();
		Date begin=parseDay(start);
		Date finish=parseDay(end);
		Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(finish);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
		while (tempStart.before(tempEnd)){
			list.add(tempStart.getTime());
			tempStart.add(Calendar.DATE, 1);
		}
		return list;
	}
	/**
	 * 获取两个时间段（月）之间的零点时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getMonthZoreBetween(String start, String end) {
		List<Date> list=new ArrayList<Date>();
		Date begin=parseYearAndMonth(start);
		Date finish=parseYearAndMonth(end);
		Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(finish);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
		while (tempStart.before(tempEnd)){
			list.add(tempStart.getTime());
			tempStart.add(Calendar.MONTH, 1);
		}
		return list;
	}
	/**
	 * 获取两个时间段（年）之间的零点时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getYearZoreBetween(String start, String end) {
		List<Date> list=new ArrayList<Date>();
		Date begin=parseYear(start);
		Date finish=parseYear(end);
		Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(finish);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
		while (tempStart.before(tempEnd)){
			list.add(tempStart.getTime());
			tempStart.add(Calendar.YEAR, 1);
		}
		return list;
	}
	/**
	 * 获取两个时间段（年）之间的起始和结束
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getYearZore(String start, String end) {
		List<Date> list=new ArrayList<Date>();
		Date beginDate=getYearFirst(Integer.parseInt(start));
		Date endDate=getYearLast(Integer.parseInt(end));
		list.add(beginDate);
		list.add(endDate);
		return list;
	}
	/**
	 * 获取去年的此时间点
	 * @param date
	 * @return
	 */
	public static Date getLastYearOfNow(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		Date time = calendar.getTime();
		return time;
	}
	/**
	 * 根据毫秒数获取 yyyy-MM-dd HH:mm:ss格式的日期
	 * @param millisecond
	 * @return
	 */
	public static String getTimeByMillis(Long millisecond) {

		return getSdf("yyyy-MM-dd HH:mm:ss").format(millisecond);
	}


	/**
	 * @Description:  解决线程安全问题
	 * @Date: 2020/4/24 16:03
	 * @Version: V1.0
	 */
	public static Long getTimeByPattern(String dateStr, String pattern) throws ParseException {
		return getSdf(pattern).parse(dateStr).getTime();
	}

	/**
	 * @Description:  获取相对于当前时间任意时间的 日期-时-分-秒
	 * @param date 相对于当前时间的天
	 * @param  hour  时
	 * @param  minute   分
	 * @param  second   秒
	 * @Date: 2020/4/26 10:17
	 * @Version: V1.0
	 */
	public static Long getTimeByToday(Integer date,Integer hour,Integer minute,Integer second){
		//当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);
		return calendar.getTimeInMillis();
	}
	/**
	 * @Description:  获取相对于当前时间前半点或者整点的时间
	 * @Date: 2020/4/26 10:17
	 * @Version: V1.0
	 */
	public static Map<String,String> getHarfHourByToday(){
		Map<String, String> map = new HashMap<>();
		//当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		//calendar.add(Calendar.DATE, date);
		//calendar.set(Calendar.HOUR_OF_DAY,hour);
		int minute = calendar.get(Calendar.MINUTE);
		if (minute>=30){
			calendar.set(Calendar.MINUTE,30);
		}else {
			calendar.set(Calendar.MINUTE,0);
		}

		calendar.set(Calendar.SECOND,0);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		String startTime = simpleDateFormat.format(calendar.getTime());
		map.put("startTime",startTime);

		calendar.set(Calendar.SECOND,1);
		String endTime = simpleDateFormat.format(calendar.getTime());
		map.put("endTime",endTime);
		return map;
	}


    /**
     * 获取当天hour点往前num小时内的整点时间数组
     * @param hour
     * @param hour
     * @return
     */
	public static List<Date> getDateBeforeList(Integer hour,Integer num){
       List<Date> dateList = new ArrayList<>();
        Date date = getDate(hour);
        for (int i = 0; i < num; i++) {
            Date lastHourFirst = getLastHourFirst(date);
            date = lastHourFirst;
            dateList.add(lastHourFirst);
        }
        Collections.reverse(dateList);
        return  dateList;
    }


    /**
     * 获取当天某个时间点
     * @param hour
     * @return
     */
    public static Date getDate(Integer hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cale = Calendar.getInstance();
        Calendar calendar = new GregorianCalendar(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH), hour, 0, 0);
        Date date = calendar.getTime();
        return date;
    }
    /**
         * 获取当前时间的*分钟前时间
     * @param date
     * @param min
     * @return
     */
    public static Date getLastMinutes(Date date,int min){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
    /**
	* 判断给定日期是否为月末的一天
	*
	* @param date
	* @return true:是|false:不是
	*/
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}
	/**
	 * 获取两个日期之间的天数
	 * @param now
	 * @param returnDate
	 * @return
	 */
	public static int daysBetween(Date now, Date returnDate) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(now);
		cReturnDate.setTime(returnDate);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToDays(intervalMs);
	}

	/**
	 * 获取两个日期之间的分钟数
	 * @param intervalMs
	 * @return
	 */
	public static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}
	
	/**
	 * 获取两个日期之间的毫秒数
	 * @param calendar
	 */
	public static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}
	/**
	 * 判断是否是整点
	 * @param date
	 * @return
	 */
	public static boolean isOnTheHour(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		if( (gc.get(gc.MINUTE)==0) && (gc.get(gc.SECOND)==0) ) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	/**
	 * 获当前小时的低几分钟
	 * @return
	 */
	public static Date getMinute(Date date,int minute){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date first=calendar.getTime();
		return first;
	}


	/**
	 * 获取当前日期的前一天
	 * @param pattern 需要返回的日期格式，例如：yyyy-MM-dd HH:mm:ss
	 * @return 前一天日期字符串
	 */
	public static String beforeDayByNowDay(String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); //得到前一天
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 获得当天零时零分零秒
	 * @return
	 */
	public static Date getDayNew(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得当天23时59分59秒
	 * @return
	 */
	public static Date getDayFind(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
}
