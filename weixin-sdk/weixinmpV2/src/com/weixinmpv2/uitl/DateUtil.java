package com.weixinmpv2.uitl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 404972054487686254L;
	public static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final DateFormat XML_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Integer propertyYear;

	public static void setPropertyYear(Integer propertyYear) {
		DateUtil.propertyYear = propertyYear;
	}

	public static Integer getPropertyYear() {
		return propertyYear;
	}

	public static String getDefaultDateTimeStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return DEFAULT_FORMAT.format(calendar.getTime());
	}

	public static Date addDay(Date time, Integer days) {
		Date date = new Date();
		long nowLong = time.getTime();
		long dayl = days;
		nowLong += (long) 1000L * 60L * 60L * 24L * dayl;
		date.setTime(nowLong);
		return date;
	}

	public static Date addMonth(final Date time, int AddMonthCount) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MONTH, AddMonthCount);
		return c.getTime();
	}

	// 2006-01-01 12:25:10 to 2006-01-01 00:00:00
	public static Date getMinOfDate(final Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	// 2006-01-01 12:25:10 to 2006-01-02 23:59:59
	public static Date getMaxOfDate(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date _date = calendar.getTime();
		_date = DateUtils.truncate(_date, Calendar.DAY_OF_MONTH);
		calendar.setTime(_date);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}

	public static Date getPreviousYearEnd(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date _date = calendar.getTime();
		_date = DateUtils.truncate(_date, Calendar.YEAR);
		calendar.setTime(_date);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}

	// 判断是否为同一月
	public static boolean isSameMonth(final Date date, final Date anotherDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _year = c2.get(Calendar.YEAR);
		int _month = c2.get(Calendar.MONTH);

		if ((year == _year) && (month == _month)) {
			return true;
		} else {
			return false;
		}
	}

	// 判断是否为同一天
	public static boolean isSameDate(final Date date, final Date anotherDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _year = c2.get(Calendar.YEAR);
		int _month = c2.get(Calendar.MONTH);
		int _day = c2.get(Calendar.DAY_OF_MONTH);

		if ((year == _year) && (month == _month) && (day == _day)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSameTime(final Date Time, final Date anotherTime) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(Time);
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherTime);
		int _hour = c2.get(Calendar.HOUR_OF_DAY);
		int _minute = c2.get(Calendar.MINUTE);

		if ((hour == _hour) && (minute == _minute)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean afterTime(final Date Time, final Date anotherTime) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(Time);
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherTime);
		int _hour = c2.get(Calendar.HOUR_OF_DAY);
		int _minute = c2.get(Calendar.MINUTE);
		
		if(hour == _hour){
			if(minute > _minute){
				return true;
			}else{
				return false;
			}
		}else{
			if(hour > _hour){
				return true;
			}else{
				return false;
			}
		}
	}

	// 判断生日是否为同一天
	public static boolean isSameBirthDate(final Date date, final Date anotherDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int month = c1.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _month = c2.get(Calendar.MONTH);
		int _day = c2.get(Calendar.DAY_OF_MONTH);

		if ((month == _month) && (day == _day)) {
			return true;
		} else {
			return false;
		}
	}

	// 返回年的第一天
	public static Date getFirstDayOfYear(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.DAY_OF_YEAR, 1);
		return c1.getTime();
	}

	// 返回年的最后天
	public static Date getEndDayOfYear(Integer year) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.MONTH, 11);
		c1.set(Calendar.DATE, 31);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		return c1.getTime();
	}

	// 返回当前月份的第一天
	public static Date getFirstDayOfMonth(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		return c1.getTime();
	}

	// 返回当前月份最后一天
	public static Date getLastDayOfMonth(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int month = c1.get(Calendar.MONTH);
		c1.set(Calendar.MONTH, month + 1);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.add(Calendar.SECOND, -1);
		return c1.getTime();
	}

	// 返回当前年最后一天
	public static Date getLastDayOfYear(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		c1.set(Calendar.YEAR, year + 1);
		c1.set(Calendar.MONTH, 0);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.add(Calendar.SECOND, -1);
		return c1.getTime();
	}

	public static Date getLastMonthDate(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getFirstDayOfMonth(date));
		int month = c1.get(Calendar.MONTH);
		c1.set(Calendar.MONTH, month - 1);
		return c1.getTime();
	}

	// 判断是否为同一时间，精确到分钟
	public static boolean isSameDateTime(final Date date, final Date anotherDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);
		// int second = c1.get(Calendar.SECOND);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _year = c2.get(Calendar.YEAR);
		int _month = c2.get(Calendar.MONTH);
		int _day = c2.get(Calendar.DAY_OF_MONTH);
		int _hour = c2.get(Calendar.HOUR_OF_DAY);
		int _minute = c2.get(Calendar.MINUTE);
		// int _second = c2.get(Calendar.SECOND);

		if ((year == _year) && (month == _month) && (day == _day) && (hour == _hour) && (minute == _minute)) {
			return true;
		} else {
			return false;
		}
	}

	public static Integer getMonth(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);// 0 表示一月
		month = month + 1;
		return new Integer(month);
	}

	public static Integer getDayOfMonth(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);// 0 表示第一天
		return new Integer(day);
	}

	public static Integer getDayOfWeek(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);// 1 表示星期天
		return new Integer(day);
	}

	/**
	 * 判断month是否在开始月份和结束月份之间，开始月份和结束月份可能是跨年度的，如开始月份为11月，结束月份为4月
	 * 
	 * @param month
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public static boolean betweenMonth(int month, int beginMonth, int endMonth) {
		if (beginMonth > endMonth) {
			if ((month >= 1) && (month <= endMonth)) {
				return true;
			}
			if ((month >= beginMonth) && (month <= 12)) {
				return true;
			}
		} else {
			if ((month >= beginMonth) && (month <= endMonth)) {
				return true;
			}
		}
		return false;
	}

	public static boolean betweenDate(Date date, Date startDate, Date endDate) {
		int val1 = date.compareTo(startDate);// >=0
		int val2 = date.compareTo(endDate);// <=0
		if ((val1 >= 0) && (val2 <= 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断时间点是否在startTime/endTime之间
	 * 
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean betweenTime(Date time, Date startTime, Date endTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(0, 0, 0);
		long timeVal = c.getTime().getTime();

		Calendar c1 = Calendar.getInstance();
		c1.setTime(startTime);
		c1.set(0, 0, 0);
		long startTimeVal = c1.getTime().getTime();

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endTime);
		c2.set(0, 0, 0);
		long endTimeVal = c2.getTime().getTime();

		if (startTimeVal >= endTimeVal) {
			if (timeVal >= startTimeVal) {
				return true;
			}

			if (timeVal <= endTimeVal) {
				return true;
			}
		} else {
			if ((timeVal >= startTimeVal) && (timeVal <= endTimeVal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为后半夜班
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isNotLobsterShift(final Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour >= 17) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isLobsterShift(final Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour <= 6) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 日期增加天数或者减少天数
	 */
	public static Date addDay(final Date time, int AddDayCount) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DAY_OF_MONTH, AddDayCount);
		return c.getTime();
	}

	// 增加指定的分钟时间后的日期时间值
	public static Date addMinute(final Date time, int addedMinutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, addedMinutes);
		return c.getTime();
	}

	// 结束时间 与 开始时间 之间的时间差(按分钟计量)
	public static int diffMinute(Date startTime, Date endTime) {
		if(startTime.after(endTime)){
			Date time = startTime;
			startTime = endTime;
			endTime = time;
		}
		float startTimeVal = startTime.getTime();
		float endTimeVal = endTime.getTime();

		float minutes = (endTimeVal - startTimeVal) / (1000 * 60);

		return new Float(minutes).intValue();
	}

	// 结束时间 与 开始时间 之间的时间差(按分钟计量)
	public static int diffSecond(final Date startTime, final Date endTime) {
		long startTimeVal = startTime.getTime();
		long endTimeVal = endTime.getTime();

		Long minutes = (endTimeVal - startTimeVal) / 1000;
		return minutes.intValue();
	}

	// 结束时间 与 开始时间 之间的时间差(按天数计量)
	public static int diffDay(final Date startTime, final Date endTime) {
		float startTimeVal = startTime.getTime();
		float endTimeVal = endTime.getTime();

		float days = (endTimeVal - startTimeVal) / (1000 * 60 * 60 * 24);

		return new Float(days).intValue();
	}

	/**
	 * Calculates the number of days between two calendar days in a manner which
	 * is independent of the Calendar type used.
	 * 
	 * @param d1
	 *            The first date.
	 * @param d2
	 *            The second date.
	 * 
	 * @return The number of days between the two dates. Zero is returned if the
	 *         dates are the same, one if the dates are adjacent, etc. The order
	 *         of the dates does not matter, the value returned is always >= 0.
	 *         If Calendar types of d1 and d2 are different, the result may not
	 *         be accurate.
	 */
	public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	// 两个时间点的月份差
	public static BigDecimal getQuarterOfYearBetween(java.util.Date d1, java.util.Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.after(c2)) {
			Calendar swap = c1;
			c1 = c2;
			c2 = swap;
		}
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
			if (c2.get(Calendar.MONTH) == c1.get(Calendar.MONTH)) {
				return new BigDecimal(1);
			} else {
				return new BigDecimal(c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH));
			}
		} else {
			int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
			return new BigDecimal((c2.get(Calendar.MONTH) + year * 12 - c1.get(Calendar.MONTH)));
		}
	}

	// 判断时间大小
	public static boolean CompareTime(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int c1Hour = c1.get(Calendar.HOUR_OF_DAY);
		int c1Minute = c1.get(Calendar.MINUTE);
		int c2Hour = c2.get(Calendar.HOUR_OF_DAY);
		int c2Minute = c2.get(Calendar.MINUTE);
		if (c2Hour == 0 && c2Minute == 0) {
			return false;
		}
		if ((c1Hour > c2Hour)) {
			return true;
		} else if ((c1Hour == c2Hour) && (c1Minute >= c2Minute)) {
			return true;
		} else {
			return false;
		}
	}

	// 判断时间大小 返回1 则date1 > date2 0 相等 -1 date1 < date2
	public static int compareDay(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int c1Year = c1.get(Calendar.YEAR);
		int c1Month = c1.get(Calendar.MONTH);
		int c1Day = c1.get(Calendar.DAY_OF_MONTH);

		int c2Year = c2.get(Calendar.YEAR);
		int c2Month = c2.get(Calendar.MONTH);
		int c2Day = c2.get(Calendar.DAY_OF_MONTH);
		if (c1Year > c2Year) {
			return 1;
		} else if (c1Year == c2Year && c1Month > c2Month) {
			return 1;
		} else if (c1Year == c2Year && c1Month == c2Month && c1Day > c2Day) {
			return 1;
		} else if (c1Year == c2Year && c1Month == c2Month && c1Day == c2Day) {
			return 0;
		} else {
			return -1;
		}
	}

	public static int getDaysBetween(java.util.Date d1, java.util.Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return getDaysBetween(c1, c2);
	}

	public static String getDateTimeString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static String getLongTimeString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static String getJobTimeString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("dd/HHmm", Locale.CHINA);
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static String getCurrentDateTimeStr() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}

	public static String getSmsSendDateTimeStr() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(dt);
	}

	public static String getDateTimeStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(argument);
	}

	public static String getDateStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(argument);
	}

	public static String getDateShortStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(argument);
	}
	
	public static String getDateShorterStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(argument);
	}

	public static String getDay(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(argument);
	}

	public static String getTimeStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(argument);
	}

	public static String getBirthDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date);
	}
	
	public static String getDayDD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(date);
	}

	public static Date getDateTimeMd(String arg) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		try {
			return sdf.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}
	
	
	public static String getDateShortStrMD(Date argument) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        return sdf.format(argument);
    }
	
	public static Date getDateTimeYMd(String arg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static Date getDateTimeHMS(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static Date getXmlDate(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static Date getXmlTDate(String arg) {
		try {
			return XML_FORMAT.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static Date getDateTimeShortHMS(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static String format(final String pattern, final Date date) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static Date getDateTimeStr(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getLongDateTimeStr(Date argument) {
		if (argument == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(argument);
	}
	
	public static String getLessLongDateTimeStr(Date argument) {
		if (argument == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(argument);
	}

	public static String getShortDateTimeStr(Date argument) {
		if (argument == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(argument);
	}

	public static String getShortDateTimeMonthStr(Date argument) {
		if (argument == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(argument);
	}

	public static String getTimeString(final Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFmt = new SimpleDateFormat("HHmm");
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static String getYearString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy");
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static int getYear(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static String getTableYear(Date date) {
		// if(propertyYear == null){
		// return "";
		// }
		// Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// Integer year = c.get(Calendar.YEAR);
		// if(year <= propertyYear){
		// return "_" + year.toString();
		// }
		return "";
	}

	public static String getDateMonthType(String bizType) {
		String dateType = bizType.substring(0, 4) + "-" + bizType.substring(4, 6);
		return dateType;
	}

	public static String getDateType(String bizType) {
		String dateType = bizType.substring(0, 4) + "-" + bizType.substring(4, 6) + "-" + bizType.substring(6);
		return dateType;
	}

	public static Date getZeroSecondDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date getFirstDayOfWeek() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(Calendar.getInstance().getTime());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	// 增加指定的秒
	public static Date addSecond(final Date time, int addedSeconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.SECOND, addedSeconds);
		return c.getTime();
	}
	

	// 增加指定的小时
	public static Date addHour(final Date time, int addHours) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.HOUR, addHours);
		return c.getTime();
	}

	public static String getTableYear(String propertyYear) {
		if (propertyYear == null) {
			return "";
		}
		// return "_" + propertyYear;
		return "";
	}
	
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
	
}
