package com.jin.crawler.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public final static String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm";
    public final static String DATE_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_CHINA_DEFAULT = "yyyy年MM月dd日";
    public static final long SECOND = 1000; // 1秒 java已毫秒为单位

    public static final long MINUTE = SECOND * 60; // 一分钟

    public static final long HOUR = MINUTE * 60; // 一小时

    public static final long DAY = HOUR * 24; // 一天

    public static final long WEEK = DAY * 7; // 一周

    public static final long YEAR = DAY * 365; // 一年

    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss"; // 默认时间格式

    public static final String FORMAT_TIME_MINUTE = "yyyy-MM-dd HH:mm"; // 默认时间格式

    public static final String FORTER_DATE = "yyyy-MM-dd"; // 默认日期格式

    private static final Map<Integer, String> WEEK_DAY = new HashMap<Integer, String>();

    static {
        WEEK_DAY.put(7, "星期六");
        WEEK_DAY.put(1, "星期天");
        WEEK_DAY.put(2, "星期一");
        WEEK_DAY.put(3, "星期二");
        WEEK_DAY.put(4, "星期三");
        WEEK_DAY.put(5, "星期四");
        WEEK_DAY.put(6, "星期五");
    }

    /**
     * 获取指定日期前后num天的日期
     *
     * @param date
     * @param num
     * @return
     */
    public static String getDay(String date, int num) {
        return getDay(date, num, DATE_FORMAT_DEFAULT);
    }

    /**
     * 获取指定日期前后num天的日期
     *
     * @param date
     * @param num
     * @param format
     * @return
     */
    public static String getDay(String date, int num, String format) {
        long t = parseStringToLong(date);
        return getDay(t, num, DATE_FORMAT_DEFAULT);
    }

    /**
     * 获取指定日期前后num天的日期
     *
     * @param date
     * @param num
     * @return
     */
    public static String getDay(long date, int num) {
        return getDay(date, num, DATE_FORMAT_DEFAULT);
    }

    /**
     * 获取指定日期前后num天的日期
     *
     * @param date
     * @param num    正数 多少天之后的日期  负数 多少天之后的日期
     * @param format 日期格式
     * @return
     */
    public static String getDay(long date, int num, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + num);
        return longToString(calendar.getTimeInMillis(), format);
    }

    /**
     * 将毫秒时间转换为yyyy-MM-dd格式的时间
     *
     * @param time 毫秒数
     * @return
     */
    public static String longToString(long time) {
        return longToString(time, DATE_FORMAT_DEFAULT);
    }

    /**
     * 将毫秒时间转换为指定格式的时间
     *
     * @param time   毫秒数
     * @param format 日期格式
     * @return
     * @author yangwenkui
     * @time 2017年10月6日 下午5:56:40
     */
    public static String longToString(long time, String format) {
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_DEFAULT;
        }
        DateTime dTime = new DateTime(time);
        return dTime.toString(format);
    }

    /**
     * 获取今天开始的时间
     *
     * @return
     */
    public static Timestamp getTodayStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 获取指定日期开始的当日开始时间
     *
     * @param date
     * @return
     */
    public static long getDayStartTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(parseStringToLong(date));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }

    /**
     * 获取指定日期结束时间
     *
     * @param date
     * @return
     */
    public static long getDayEndTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(parseStringToLong(date));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    /**
     * 获得当前日期
     */
//	public static String getCurrentTime() {
//		return getCurrentTime("yyyy-MM-dd");
//	}

    /**
     * 获得当前时间
     *
     * @param format 日期格式
     * @return
     */
    public static String getCurrentTime(String format) {
        DateTime dTime = new DateTime();
        return dTime.toString(format);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeStamp() {
        DateTime dateTime = new DateTime();
        return dateTime.getMillis();
    }

    /**
     * 将字符串类型的日期转换为毫秒数
     *
     * @param dateStr
     * @return
     */
    public static long parseStringToLong(String dateStr) {
        dateStr = dateStr.trim();
        if (dateStr.length() == 19 || dateStr.length() == 23) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        Integer.parseInt(dateStr.substring(8, 10)),
                        Integer.parseInt(dateStr.substring(11, 13)),
                        Integer.parseInt(dateStr.substring(14, 16)),
                        Integer.parseInt(dateStr.substring(17, 19)));
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }

        } else if (dateStr.length() == 16) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        Integer.parseInt(dateStr.substring(8, 10)),
                        Integer.parseInt(dateStr.substring(11, 13)),
                        Integer.parseInt(dateStr.substring(14, 16)));
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }

        } else if (dateStr.length() == 14) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(4, 6)) - 1,
                        Integer.parseInt(dateStr.substring(6, 8)),
                        Integer.parseInt(dateStr.substring(8, 10)),
                        Integer.parseInt(dateStr.substring(10, 12)),
                        Integer.parseInt(dateStr.substring(12, 14)));
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }
        } else if (dateStr.length() == 10 || dateStr.length() == 11) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        Integer.parseInt(dateStr.substring(8, 10)), 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }
        } else if (dateStr.length() == 8) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(4, 6)) - 1,
                        Integer.parseInt(dateStr.substring(6, 8)), 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }
        } else {
            try {
                return Long.parseLong(dateStr);
            } catch (Exception e) {
                return 0;
            }

        }
    }

    /**
     * 获取当前系统时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        DateTime dt = new DateTime();
        String time = dt.toString(FORMAT_TIME);
        return time;
    }

    /**
     * 获取当前系统时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date getCurrentUtilDate() {
        DateTime dt = new DateTime();
        return dt.toDate();
    }

    /**
     * 获取系统当前时间按照指定格式返回
     *
     * @param pattern yyyy/MM/dd hh:mm:a
     * @return
     */
    public static String getCurrentTimePattern(String pattern) {
        DateTime dt = new DateTime();
        String time = dt.toString(pattern);
        return time;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        DateTime dt = new DateTime();
        String date = dt.toString(FORTER_DATE);
        return date;
    }

    /**
     * 获取当前日期按照指定格式
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDatePattern(String pattern) {
        DateTime dt = new DateTime();
        String date = dt.toString(pattern);
        return date;
    }

    /**
     * 按照时区转换时间
     *
     * @param date
     * @param timeZone 时区
     * @param parrten
     * @return
     */
    public static String format(Date date, TimeZone timeZone, String parrten) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(parrten);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    /**
     * 获取指定时间
     *
     * @param year    年
     * @param month   月
     * @param day     天
     * @param hour    小时
     * @param minute  分钟
     * @param seconds 秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getPointTime(Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                      Integer seconds) {
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
        String date = dt.toString(FORMAT_TIME);
        return date;
    }

    /**
     * @param year    年
     * @param month   月
     * @param day     天
     * @param hour    小时
     * @param minute  分钟
     * @param seconds 秒
     * @param parrten 自定义格式
     * @return parrten
     */
    public static String getPointTimePattern(Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                             Integer seconds, String parrten) {
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
        String date = dt.toString(parrten);
        return date;
    }

    /**
     * 获取指定日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getPointDate(Integer year, Integer month, Integer day) {
        LocalDate dt = new LocalDate(year, month, day);
        String date = dt.toString(FORTER_DATE);
        return date;
    }

    /**
     * 获取指定日期 返回指定格式
     *
     * @param year
     * @param month
     * @param day
     * @param parrten
     * @return
     */
    public static String getPointDatParrten(Integer year, Integer month, Integer day, String parrten) {
        LocalDate dt = new LocalDate(year, month, day);
        String date = dt.toString(parrten);
        return date;
    }

    /**
     * 获取当前是一周星期几
     *
     * @return
     */
    public static String getWeek() {
        DateTime dts = new DateTime();
        String week = null;
        switch (dts.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                week = "星期日";
                break;

            case DateTimeConstants.MONDAY:
                week = "星期一";
                break;

            case DateTimeConstants.TUESDAY:
                week = "星期二";
                break;
            case DateTimeConstants.WEDNESDAY:
                week = "星期三";
                break;
            case DateTimeConstants.THURSDAY:
                week = "星期四";
                break;
            case DateTimeConstants.FRIDAY:
                week = "星期五";
                break;
            case DateTimeConstants.SATURDAY:
                week = "星期六";
            default:
                break;
        }
        return week;
    }

    /**
     * 获取指定时间是一周的星期几
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getWeekPoint(Integer year, Integer month, Integer day) {
        LocalDate dts = new LocalDate(year, month, day);
        String week = null;
        switch (dts.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                week = "星期日";
                break;
            case DateTimeConstants.MONDAY:
                week = "星期一";
                break;
            case DateTimeConstants.TUESDAY:
                week = "星期二";
                break;
            case DateTimeConstants.WEDNESDAY:
                week = "星期三";
                break;
            case DateTimeConstants.THURSDAY:
                week = "星期四";
                break;
            case DateTimeConstants.FRIDAY:
                week = "星期五";
                break;
            case DateTimeConstants.SATURDAY:
                week = "星期六";
                break;

            default:
                break;
        }
        return week;
    }

    /**
     * 获取指定时间是一周的星期几 数字展示 1氢气已
     *
     * @return
     */
    public static int getWeekNumber() {
        DateTime dts = new DateTime();
        String week = null;
        return dts.getDayOfWeek();
    }


    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(date);
    }

    /**
     * 格式化日期字符串
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 解析日期
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String date, String pattern) throws ParseException {
        if (date == null) {
            return null;
        }
        Date resultDate = null;
        try {
            resultDate = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {

        }
        return resultDate;
    }

    /**
     * 解析日期yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期字符串
     * @return
     */
    public static Date parse(String date) {
        if (date == null) {
            return null;
        }

        try {
            return new SimpleDateFormat(FORMAT_TIME).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }



    /**
     * 解析日期 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String format(Long timestamp, String pattern) {
        String dateStr = "";
        if (null == timestamp || timestamp.longValue() < 0) {
            return dateStr;
        }
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            dateStr = format.format(date);
        } catch (Exception e) {
            // ignore
        }

        return dateStr;
    }

    /**
     * 解析日期 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String format(Long timestamp) {
        String dateStr = "";
        if (null == timestamp || timestamp.longValue() < 0) {
            return dateStr;
        }
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
            dateStr = format.format(date);
        } catch (Exception e) {
            // ignore
        }

        return dateStr;
    }

    /**
     * 获取当前时间前几天时间,按指定格式返回
     *
     * @param days
     * @return
     */
    public static String forwardDay(Integer days, String format) {
        DateTime dt = new DateTime();
        DateTime y = dt.minusDays(days);
        return y.toString(format);
    }

    /**
     * 获取当前时间前几天时间
     *
     * @param days
     * @return
     */
    public static Date forwardDay(Integer days) {
        DateTime dt = new DateTime();
        DateTime y = dt.minusDays(days);
        return y.toDate();
    }

    /**
     * 获取当前时间是否晚于指定的时间
     *
     * @param days
     * @return
     */
    public static boolean inNumDays(Integer days,String date) throws Exception {
        Date startDay = day00(days,null,null);
        Date currentDay = parse(date,DateUtils.FORMAT_TIME_MINUTE);
        Integer day = compareDay(startDay,currentDay);
        return  day >= 0 ? true : false;
    }

    /**
     * 获取指定时间之后或者之前的某一天00:00:00 默认返回当天
     * -1 往后推
     * 1  往前算
     *
     * @param days
     * @return
     */
    public static Date day00(Integer days, String date, String zimeZone) throws Exception {
        DateTime dt;
        TimeZone timeZone;
        try {
            if (isBlank(zimeZone)) {
                timeZone = TimeZone.getDefault();
            } else {
                timeZone = TimeZone.getTimeZone(zimeZone);
            }
            if (isBlank(date)) {
                dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            } else {
                dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            }
        } catch (Exception e) {
//            throw new Throwable(e);
            throw e;
        }

        DateTime y = dt.minusDays(days).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        return y.toDate();
    }

    /**
     * 获取指定时间之后或者之前的某一天23:59:59 默认返回当天
     *
     * @param days 偏移量
     * @return
     */
    public static Date day59(Integer days, String date, String zimeZone) throws Exception {
        DateTime dt;
        TimeZone timeZone;
        try {
            if (isBlank(zimeZone)) {
                timeZone = TimeZone.getDefault();
            } else {
                timeZone = TimeZone.getTimeZone(zimeZone);
            }
            if (isBlank(date)) {

                dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            } else {
                dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            }
        } catch (Exception e) {
//            throw new Throwable(e);
            throw e;
        }
        DateTime y = dt.minusDays(days).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return y.toDate();
    }

    /**
     * 计算两个时间相差多少天
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer diffDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);
        int day = Days.daysBetween(dt1, dt2).getDays();
        return Math.abs(day);
    }

    /**
     * 比较两个时间相差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer compareDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);
        int day = Days.daysBetween(dt1, dt2).getDays();
        return day;
    }

    /**
     * 获取某月之前,之后某一个月最后一天,24:59:59
     *
     * @return
     */
    public static Date lastDay(Date date, Integer month) {
        DateTime dt1;
        if (month == null) {
            month = 0;
        }
        if (date == null) {
            dt1 = new DateTime().minusMonths(month);
        } else {
            dt1 = new DateTime(date).minusMonths(month);
        }
        DateTime lastDay = dt1.dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59)
                .withSecondOfMinute(59);
        return lastDay.toDate();
    }

    /**
     * 获取某月月之前,之后某一个月第一天,00:00:00
     *
     * @return
     */
    public static Date firstDay(Date date, Integer month) {
        DateTime dt1;
        if (month == null) {
            month = 0;
        }
        if (date == null) {
            dt1 = new DateTime().minusMonths(month);
        } else {
            dt1 = new DateTime(date).minusMonths(month);
        }
        DateTime lastDay = dt1.dayOfMonth().withMinimumValue().withHourOfDay(0).withMinuteOfHour(0)
                .withSecondOfMinute(0);
        return lastDay.toDate();
    }

    public static Date addDay(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusDays(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusDays(offset);
        return dt1.toDate();

    }

    /**
     *
     *获取指定小时前
     * 负数 往前推
     *@author Xingheng.Zhang
     *@date 2018-12-5 18:07
     *@param
     *@return
     */
    public static Date beforeHours(int offset) {
        DateTime dt1;
        dt1 = new DateTime().plusHours(offset);
        return dt1.toDate();

    }
    /**
     *
     *获取指定分钟前
     * 负数 往前推
     *@author Xingheng.Zhang
     *@date 2018-12-5 18:07
     *@param
     *@return
     */
    public static Date beforeMinutes(int offset) {
        DateTime dt1;
        dt1 = new DateTime().plusMinutes(offset);
        return dt1.toDate();

    }
    /**
     *
     *获取指定秒前
     * 负数 往前推
     *@author Xingheng.Zhang
     *@date 2018-12-5 18:07
     *@param
     *@return
     */
    public static Date beforeSeconds(int offset) {
        DateTime dt1;
        dt1 = new DateTime().plusSeconds(offset);
        return dt1.toDate();

    }

    /**
     * 传入日期时间与当前系统日期时间的比较, 若日期相同，则根据时分秒来返回 , 否则返回具体日期
     *
     * @return 日期或者 xx小时前||xx分钟前||xx秒前
     */
    public static String getNewUpdateDateString(Date now, Date createDate) {
        if (now == null || createDate == null) {
            return null;
        }
        Long time = (now.getTime() - createDate.getTime());
        if (time > (24 * 60 * 60 * 1000)) {
            return time / (24 * 60 * 60 * 1000) + "天前";
        } else if (time > (60 * 60 * 1000)) {
            return time / (60 * 60 * 1000) + "小时前";
        } else if (time > (60 * 1000)) {
            return time / (60 * 1000) + "分钟前";
        } else if (time >= 1000) {
            return time / 1000 + "秒前";
        }
        return "刚刚";
    }


    /**
     * 根据给定时分获取上一天时间
     *
     * @param time 07:50:00
     * @return
     */
    public static long getLastDayTime(String time) {
        Date lastDate = addDay(new Date(), -1);
        String lastDay = format(lastDate, "yyyy-MM-dd");
        String date = lastDay + " " + time;
        return parseStringToLong(date);
    }

    /**
     * 根据给定时分获取指定时间
     *
     * @param time 07:50:00
     * @return
     */
    public static long getCurrentDayTime(String time) {
        String lastDay = format(new Date(), "yyyy-MM-dd");
        String date = lastDay + " " + time;
        return parseStringToLong(date);
    }


    /**
     * 获取当前时间上一小时时间
     *
     * @return
     */
    public static Date dayLastHour() throws Exception {
        DateTime dt;
        TimeZone timeZone;
        try {
            timeZone = TimeZone.getDefault();
            dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
        } catch (Exception e) {
            throw e;
        }
//        DateTime y = dt.minusMinutes(1);
        DateTime y = dt.minusHours(1);
        return y.toDate();
    }

    /**
     * 获取本周开始时间 默认以当前时间为一周结束时间 往前推6天(不包括今天)
     * @return
     * @throws Exception
     */
    public static long getThisWeekStartTime() throws  Exception{
//        int num = getWeekNumber();
        Date startTime = day00((6),null,null);
        return startTime.getTime();
    }

    /**
     * 获取本周结束时间 默认以当前时间为一周结束时间
     * @return
     * @throws Exception
     */
    public static long getThisWeekEndTime() throws  Exception{
      Date endTime = day59(0,null,null);
      return endTime.getTime();
    }

    /**
     * 获取上周开始时间 默认以当前时间为一周结束时间 往前推13天(不包括今天)
     * @return
     * @throws Exception
     */
    public static long getLastWeekStartTime() throws  Exception{
        Date startTime = day00(13,null,null);
        System.out.println("last week start = "+startTime);
        return startTime.getTime();
    }

    /**
     * 获取上周结束时间 默认以当前时间为一周结束时间 往前推7天(不包括今天)
     * @return
     * @throws Exception
     */
    public static long getLastWeekEndTime() throws  Exception{
        Date endTime = day00(7,null,null);
        System.out.println("last week end = "+endTime);
        return endTime.getTime();
    }


    /**
     * @param @param  str
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isBlank
     * @Description: TODO(判断字符串是否为空或长度为0 或由空格组成)
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(beforeHours(1));
        System.out.println(beforeMinutes(5));
    }
}