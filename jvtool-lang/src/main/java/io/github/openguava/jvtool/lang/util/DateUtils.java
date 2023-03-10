package io.github.openguava.jvtool.lang.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.github.openguava.jvtool.lang.exception.UtilException;
import io.github.openguava.jvtool.lang.time.FastDateParser;
import io.github.openguava.jvtool.lang.time.enums.DateField;
import io.github.openguava.jvtool.lang.time.enums.DateWeek;

import java.util.Map.Entry;

/**
 * 日期工具类
 * @author openguava
 *
 */
public class DateUtils {
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	
	public static final String FORMAT_TIME = "HH:mm:ss";
	
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	/** date 默认格式化表达式集合 */
	public static final String[] FORMAT_PATTERNS = { 
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM", "yyyy-MM-dd HH:mm:ss.SSS",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd", "yyyy/MM", "yyyy/MM/dd HH:mm:ss.SSS",
			"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd", "yyyy.MM", "yyyy.MM.dd HH:mm:ss.SSS"
			};
	
	/**
	 * 当前时间
	 * @return
	 */
	public static Date now() {
		return new Date();
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getDate() {
		return new Date();
	}
	
	/**
	 * 获取时间
	 * @param date
	 * @return
	 */
	public static Date getDate(long date) {
		return new Date(date);
	}
	
	/**
	 * 获取时间
	 * @param date 
	 * @param hour 小时
	 * @param minute 分钟
	 * @param second 秒
	 * @param millisecond 毫秒
	 * @return
	 */
	public static Date getDate(Date date, int hour, int minute, int second, int millisecond){
		Calendar calendar = getCalendar(date, null, null, null, hour, minute, second, millisecond);
		return calendar.getTime();
	}
	
	/**
	 * 根据特定格式格式化日期
	 * @param date 被格式化的日期
	 * @param format 日期格式
	 * @return
	 */
	public static String format(Date date, String format) {
		return format(date, format, TimeZone.getDefault());
	}
	
	/**
	 * 根据特定格式格式化日期
	 * @param date 被格式化的日期
	 * @param format 日期格式
	 * @param timeZone 时区
	 * @return
	 */
	public static String format(Date date, String format, TimeZone timeZone) {
		if (null == date || StringUtils.isBlank(format)) {
			return null;
		}
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(timeZone != null) {
			sdf.setTimeZone(timeZone);
		}
		return format(date, sdf);
	}
	
	/**
	 * 根据特定格式格式化日期
	 * 
	 * @param date 被格式化的日期
	 * @param format {@link SimpleDateFormat}
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, DateFormat format) {
		if (null == format || null == date) {
			return null;
		}
		return format.format(date);
	}
	
	/**
	 * 获取日期格式化字符
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return format(date, FORMAT_DATE);
	}
	
	/**
	 * 获取日期时间格式化字符
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return format(date, FORMAT_DATETIME);
	}
	
	/**
	 * 将特定格式的日期转换为Date对象
	 * @param dateStr 特定格式的日期
	 * @param format 格式，例如yyyy-MM-dd
	 * @return
	 */
	public static Date parse(String dateStr, String format){
		return parse(dateStr, format, TimeZone.getDefault());
	}
	
	/**
	 * 将特定格式的日期转换为Date对象
	 * @param dateStr 特定格式的日期
	 * @param format 格式，例如yyyy-MM-dd
	 * @param timeZone 时区
	 * @return
	 */
	public static Date parse(String dateStr, String format, TimeZone timeZone){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		if(timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}
		return parse(dateStr, dateFormat);
	}
	
	/**
	 * 将特定格式的日期转换为Date对象
	 * @param dateStr 特定格式的日期
	 * @param dateFormat 格式
	 * @return
	 */
	public static Date parse(String dateStr, DateFormat dateFormat){
		try {
			return dateFormat.parse(dateStr);
		} catch (Exception e) {
			String pattern;
			if (dateFormat instanceof SimpleDateFormat) {
				pattern = ((SimpleDateFormat) dateFormat).toPattern();
			} else {
				pattern = dateFormat.toString();
			}
			throw new UtilException(StringUtils.format("Parse [{}] with format [{}] error!", dateStr, pattern), e);
		}
	}
	
	/**
	 * 智能解析 date 格式
	 * @param str
	 * @return
	 */
	public static Date parse(String str) {
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		// 数字转时间
    	if(NumberUtils.isNumber(str) && str.length() >= 10) {
    		if(str.length() == 10) {
    			return DateUtils.getDate(NumberUtils.parseLong(str) * 1000);
    		} else if(str.length() == 13) {
    			return DateUtils.getDate(NumberUtils.parseLong(str));
    		}
    	}
    	try {
    		return parse(str, FORMAT_PATTERNS);
		} catch (Exception e) {
			LogUtils.debug(DateUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     *
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser will be lenient toward the parsed date.
     *
     * @param str  the date to parse, not null
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException if none of the date patterns were suitable (or there were none)
     */
    public static Date parse(final String str, final String[] parsePatterns) throws ParseException {
        return parse(str, null, parsePatterns);
    }
    
    /**
     * <p>Parses a string representing a date by trying a variety of different parsers,
     * using the default date format symbols for the given locale.</p>
     *
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser will be lenient toward the parsed date.
     *
     * @param str  the date to parse, not null
     * @param locale the locale whose date format symbols should be used. If {@code null},
     * the system locale is used (as per {@link #parseDate(String, String...)}).
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException if none of the date patterns were suitable (or there were none)
     * @since 3.2
     */
    public static Date parse(final String str, final Locale locale, final String[] parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, locale, parsePatterns, true);
    }
    
    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     *
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     *
     * @param str  the date to parse, not null
     * @param locale the locale to use when interpreting the pattern, can be null in which
     * case the default system locale is used
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @param lenient Specify whether or not date/time parsing is to be lenient.
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException if none of the date patterns were suitable
     * @see java.util.Calendar#isLenient()
     */
    private static Date parseDateWithLeniency(final String str, final Locale locale, final String[] parsePatterns,
        final boolean lenient) throws ParseException {
        if (str == null || parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }

        final TimeZone tz = TimeZone.getDefault();
        final Locale lcl = locale != null ? locale : Locale.getDefault();
        final ParsePosition pos = new ParsePosition(0);
        final Calendar calendar = Calendar.getInstance(tz, lcl);
        calendar.setLenient(lenient);

        for (final String parsePattern : parsePatterns) {
            final FastDateParser fdp = new FastDateParser(parsePattern, tz, lcl);
            calendar.clear();
            try {
                if (fdp.parse(str, pos, calendar) && pos.getIndex() == str.length()) {
                    return calendar.getTime();
                }
            } catch (final IllegalArgumentException ignore) {
                // leniency is preventing calendar from being set
            }
            pos.setIndex(0);
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }
	
	/**
	 * 解析日期格式化字符
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr){
		return parse(dateStr, FORMAT_DATE);
	}
	
	/**
	 * 解析日期时间格式化字符
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateTime(String dateStr){
		return parse(dateStr, FORMAT_DATETIME);
	}
	
	/**
	 * 获取当前日历
	 * @return
	 */
	public static Calendar getCalendar() {
        return Calendar.getInstance();
    }
	
	/**
	 * 获取 calendar 对象
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		if(date != null) {
			calendar.setTime(date);
		}
		return calendar;
	}
	
	/**
	 * 获取 calendar 对象 
	 * @param date 日期
	 * @param year 年份
	 * @param month 月份(从0开始计数)
	 * @param day 天
	 * @return
	 */
	public static Calendar getCalendarOfDate(Date date, Integer year, Integer month, Integer day) {
		return getCalendar(date, year, month, day, null, null, null, null);
	}
	
	/**
	 * 获取 calendar 对象 
	 * @param day 天
	 * @param hour 小时
	 * @param minute 分钟
	 * @param second 秒
	 * @param millisecond 毫秒
	 * @return
	 */
	public static Calendar getCalendarOfTime(Date date, Integer hour, Integer minute, Integer second, Integer millisecond) {
		return getCalendar(date, null, null, null, hour, minute, second, millisecond);
	}
	
	/**
	 * 获取 calendar 对象 
	 * @param date 日期
	 * @param year 年份
	 * @param month 月份(从0开始计数)
	 * @param day 天
	 * @param hour 小时
	 * @param minute 分钟
	 * @param second 秒
	 * @param millisecond 毫秒
	 * @return
	 */
	public static Calendar getCalendar(Date date, Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer millisecond) {
		Calendar calendar = Calendar.getInstance();
		if(date != null) {
			calendar.setTime(date);
		}
		if(year != null) {
			calendar.set(Calendar.YEAR, year.intValue());
		}
		if(month != null) {
			calendar.set(Calendar.MONTH, month.intValue());
		}
		if(day != null) {
			calendar.set(Calendar.DAY_OF_MONTH, day.intValue());
		}
		if(hour!= null) {
			calendar.set(Calendar.HOUR_OF_DAY, hour.intValue());
		}
		if(minute != null) {
			calendar.set(Calendar.MINUTE, minute.intValue());
		}
		if(second != null) {
			calendar.set(Calendar.SECOND, second.intValue());
		}
		if(millisecond != null) {
			calendar.set(Calendar.MILLISECOND, millisecond.intValue());
		}
		return calendar;
	}

	/**
	 * 时间增加
	 * @param date 当前时间
	 * @param calendarField 增加的部分
	 * @param amount 增加的值
	 * @return
	 */
	public static Date add(final Date date, final int calendarField, final int amount){
		if(date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarField, amount);
		return calendar.getTime();
	}
	
	/**
	 * 时间增加年份
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addYears(final Date date, final int amount) {
        return add(date, Calendar.YEAR, amount);
    }

	/**
	 * 时间增加月份
	 * @param date
	 * @param amount
	 * @return
	 */
    public static Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 时间增加天数
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(final Date date, final int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 时间增加小时
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(final Date date, final int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 时间增加分钟
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(final Date date, final int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 时间增加秒
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(final Date date, final int amount) {
        return add(date, Calendar.SECOND, amount);
    }
    
    /**
     * 时间增加毫秒
     * @param date
     * @param amount
     * @return
     */
    public static Date addMillseconds(final Date date, final int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 获得年的部分
     * @param date
     * @return
     */
    public static int getYear(Date date) {
    	return getDateField(date, DateField.YEAR);
    }
    
    /**
     * 获得月份
     * @param date 时间
     * @param startWithZero 是否从零开始计数
     * @return
     */
    public static int getMonth(Date date, boolean startWithZero) {
    	int month = getDateField(date, DateField.MONTH);
    	if(startWithZero) {
    		return month;
    	} else {
    		return month + 1;
    	}
    }
    
    /**
     * 获得指定日期是这个日期所在月份的第几天
     * @param date
     * @return
     */
    public static int getDay(Date date) {
    	return getDateField(date, DateField.DAY_OF_MONTH);
    }
    
    /**
     * 获得指定日期的小时数部分
     * @param date
     * @return
     */
    public static int getHour(Date date) {
    	return getHour(date, true);
    }
    
    /**
     * 获得指定日期的小时数部分
     * @param date
     * @param is24HourClock 是否24小时制
     * @return
     */
    public static int getHour(Date date, boolean is24HourClock) {
    	return getDateField(date, is24HourClock ? DateField.HOUR_OF_DAY : DateField.HOUR);
    }
    
    /**
     * 获得指定日期的分钟数部分
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
    	return getDateField(date, DateField.MINUTE);
    }
    
    /**
     * 获得指定日期的秒数部分
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
    	return getDateField(date, DateField.SECOND);
    }
    
    /**
     * 获得指定日期的毫秒数部分
     * @param date
     * @return
     */
    public static int getMillsecond(Date date) {
    	return getDateField(date, DateField.MILLISECOND);
    }
    
    /**
     * 获得指定日期所属季度，从1开始计数
     * @param date
     * @return
     */
    public static int getQuarter(Date date) {
    	return getDateField(date, DateField.MONTH) / 3 + 1;
    }
    
    /**
     * 获得指定日期是所在年份的第几周
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
    	return getDateField(date, DateField.WEEK_OF_YEAR);
    }
    
    /**
     * 获得指定日期是所在月份的第几周
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
    	return getDateField(date, DateField.WEEK_OF_MONTH);
    }
    
    /**
	 * 获得指定日期是星期几
	 * 
	 * @param date 日期
	 * @return {@link Week}
	 */
	public static DateWeek dayOfWeekEnum(Date date) {
		return DateWeek.of(getDateField(date, DateField.DAY_OF_WEEK));
	}
    
    /**
     * 获得指定日期是星期几
     * @param date
     * @param startWithSunday 是否从周日开始计算(true:1表示周日，2表示周一,false:1表示周一，2表示周二)
     * @return
     */
    public static int getDayOfWeek(Date date, boolean startWithSunday) {
    	if(startWithSunday) {
    		return getDateField(date, DateField.DAY_OF_WEEK);
    	} else {
    		DateWeek week = DateWeek.of(getDateField(date, DateField.DAY_OF_WEEK));
    		return week.toChineseValue();
    	}
    }
    
    /**
	 * 获得日期的某个部分<br>
	 * 例如获得年的部分，则使用 getField(DatePart.YEAR)
	 *
	 * @param field 表示日期的哪个部分的枚举 {@link DateField}
	 * @return 某个部分的值
	 */
    public static int getDateField(Date date, DateField field) {
    	return getCalendar(date).get(field.getValue());
    }

	/**
	 * 获取日期时间开始
	 * @param date
	 * @return
	 */
	public static Date getDateTimeBegin(Date date) {
		Calendar calendar = getCalendar(date, null, null, null, 0, 0, 0, 0);
		return calendar.getTime();
	}
	
	/**
	 * 判断是否为时间开始
	 * @param date
	 * @return
	 */
	public static boolean isDateTimeBegin(Date date){
		Calendar calendar = getCalendar(date);
		boolean timeBegin = calendar.get(Calendar.HOUR_OF_DAY) == 0 
				&& calendar.get(Calendar.MINUTE) == 0
				&& calendar.get(Calendar.SECOND) == 0;
		return timeBegin;
	}
	
	/**
	 * 获取日期时间结束
	 * @param date
	 * @return
	 */
	public static Date getDateTimeEnd(Date date) {
		//获取时间开始
		Date begin = getDateTimeBegin(date);
		Calendar calendar = getCalendar(begin);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		Date end = calendar.getTime();
		return end;
	}
	
	/**
	 * 判断是否为时间末尾
	 * @param date
	 * @return
	 */
	public static boolean isDateTimeEnd(Date date){
		Calendar calendar = getCalendar(date);
		boolean timeEnd = calendar.get(Calendar.HOUR_OF_DAY) == 23 
				&& calendar.get(Calendar.MINUTE) == 59
				&& calendar.get(Calendar.SECOND) == 59;
		return timeEnd;
	}
	
	/**
	 * 获取日期月份开始
	 * @param date
	 * @return
	 */
	public static Date getDateMonthBegin(Date date) {
		Calendar calendar = getCalendar(date, null, null, 1, 0, 0, 0, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取日期月份结束
	 * @param date
	 * @return
	 */
	public static Date getDateMonthEnd(Date date) {
		//获取月份开始
		Date begin = getDateMonthBegin(date);
		Calendar calendar = getCalendar(begin);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		Date end = calendar.getTime();
		return end;
	}
	
	/**
	 * 获取日期年份开始
	 * @param date
	 * @return
	 */
	public static Date getDateYearBegin(Date date) {
		Calendar calendar = getCalendar(date, null, 0, 1, 0, 0, 0, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取日期年份结束
	 * @param date
	 * @return
	 */
	public static Date getDateYearEnd(Date date) {
		//获取年份开始
		Date begin = getDateYearBegin(date);
		Calendar calendar = getCalendar(begin);
		calendar.add(Calendar.YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		Date end = calendar.getTime();
		return end;
	}
    
    /**
	 * 获取时间区间
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param roundUp 向后舍入时间
	 * @return
	 */
	public static List<Entry<Date, Date>> getDateMonthRange(Date beginDate, Date endDate, Boolean roundUp){
		Date startDate = beginDate;
		List<Entry<Date, Date>> array = new ArrayList<Entry<Date,Date>>();
		do{
			//计算本次结束时间
			Date date = addSeconds(addMonths(startDate, 1), -1);
			//判断结束时间是否在范围内
			if(date.getTime() <= endDate.getTime()){
				array.add(new java.util.AbstractMap.SimpleEntry<Date, Date>(startDate, date));
			}else{
				//判断超出时间是否舍入
				if(date.getTime() > endDate.getTime() && (roundUp == null || !roundUp.booleanValue())){
					date = endDate;
				}
				array.add(new java.util.AbstractMap.SimpleEntry<Date,Date>(startDate, date));
			}
			//计算下次时间(将之前减去都1秒加回来)
			startDate = addSeconds(date, 1);
			
		} while(startDate.getTime() < endDate.getTime());
		
		return array;
	}
	
	/**
	 * 是否闰年
	 * 
	 * @param year 年
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}
    
	/**
	 * 转换为 Date
	 * @param value
	 * @return
	 */
	public static Date toDate(Object value) {
		return toDate(value, null);
	}
	
	/**
	 * 转换为 Date
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Date toDate(Object value, Date defaultValue) {
		if(value == null) {
			return null;
		}
		if(value instanceof Long) {
			return new Date((Long)value);
		}
		if(value instanceof Date) {
			return (Date)value;
		}
		if(value instanceof Calendar) {
			return ((Calendar)value).getTime();
		}
		if(value instanceof LocalDateTime) {
			ZonedDateTime zdt = ((LocalDateTime)value).atZone(ZoneId.systemDefault());
			return Date.from(zdt.toInstant());
		}
		if(value instanceof LocalDate) {
			LocalDateTime localDateTime = LocalDateTime.of((LocalDate)value, LocalTime.of(0, 0, 0));
			ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
			return Date.from(zdt.toInstant());
		}
		if(value instanceof java.sql.Date) {
			return new Date(((java.sql.Date)value).getTime());
		}
		final String valueStr = StringUtils.toStringOrNull(value);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		Date val = parse(value.toString());
		return val != null ? val : defaultValue;
	}
	
    public static void main(String[] args) throws ParseException {
    	System.out.println(parse(" "));
    }
}