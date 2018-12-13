package jUtil.util.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jUtil.util.string.StringUtils;

/**
* @author  DontRest
* @version 1.0
* @since   2018-12-13 
*/
public class DateTimeUtils {
	public static final Locale LOCALE_TH = new Locale("th", "TH");
	public static final Locale LOCALE_EN = new Locale("en", "EN");
	
	/**
	 * get current Date with time
	 */
	public static Date getCurrentDateTime() {
		return Calendar.getInstance(LOCALE_EN).getTime();
	}
	/**
	 * get current Date without time
	 * ex. 01/02/2018 00:00:00
	 */
	public static Date getCurrentDate() {
		return resetTime(Calendar.getInstance(LOCALE_EN).getTime());
	}
	
	/**
	 * get age from string of date.
	 * 
	 * @param dateStr string of date.
	 * @param formatStr string of date 's format.
	 * @return age
	 */
	public static int getAgeFromDate(String dateStr, String formatStr) throws Exception {
		LocalDate currentDate = LocalDate.now();
		LocalDate birthday = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		int age = Period.between(birthday, currentDate).getYears();
		return age;
	}
	
	/**
	 * reset time of day to 00:00:00
	 * ex. input 1/1/2018 12:01:03 output 1/1/2018 00:00:00
	 * 
	 * @param date 
	 * @return date
	 */
	public static Date resetTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * compare two date by ignore time of date 
	 * 
	 * 
	 * if you want to compare date with time of date
	 * you should use currentDate.compareTo(anotherDate)
	 * 
	 * @param currentDate 
	 * @param anotherDate
	 * @return 	1 currentDate > AnotherDate  
	 * 			0 currentDate = AnotherDate
	 * 			-1 currentDate < AnotherDate
	 */			
	public static int compareDateWithoutTime(Date currentDate, Date anotherDate) {
		Date curDate = resetTime(currentDate);
		Date aDate = resetTime(anotherDate);
		return curDate.compareTo(aDate);
	}
	
	/**
	 * add day of Date
	 * 
	 * @param date 
	 * @param day number of day
	 * @return date
	 */
	public static Date increaseDay(Date date, Integer day) {
		if (date == null || day == null) {
			return date;
		}

		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.DATE, day);
		return cal1.getTime();
	}
	
	/**
	 * minus day of date
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date decreaseDay(Date date, Integer day) {
		if (date == null || day == null) {
			return date;
		}

		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.DATE, day*-1);
		return cal1.getTime();
	}
	
	/**
	 * plus month of date
	 * 
	 * @param date 
	 * @param month
	 * @return date
	 */
	public static Date increaseMonth(Date date, Integer month) {
		if (date == null || month == null) {
			return date;
		}

		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.MONTH, month);
		return cal1.getTime();
	}
	
	/**
	 * minus month of date
	 * 
	 * @param date 
	 * @param month
	 * @return date
	 */
	public static Date decreaseMonth(Date date, Integer month) {
		if (date == null || month == null) {
			return date;
		}

		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.MONTH, month*-1);
		return cal1.getTime();
	}

	/**
	 * plus year of date
	 * 
	 * @param date
	 * @param year
	 * @return date
	 */
	public static Date increaseYear(Date date, Integer year) {
		if (date == null || year == null) {
			return date;
		}

		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.YEAR, year);
		return cal1.getTime();
	}
	
	/**
	 * minus year of date
	 * 
	 * @param date
	 * @param year
	 * @return date
	 */
	public static Date decreaseYear(Date date, Integer year) {
		if (date == null || year == null) {
			return date;
		}

		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.YEAR, year*-1);
		return cal1.getTime();
	}

	/**
	 * get year from date
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance(LOCALE_TH);
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * get year from date in format of locale
	 * 
	 * @param date
	 * @param locale
	 * @return
	 */
	public static int getYear(Date date ,Locale locale) {
		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * convert String to date in basic format ddMMyyyy or dd/MM/yyyy 
	 * 
	 * @param dateStr
	 * @return date
	 * @throws Exception
	 */
	public static Date toDate(String dateStr) throws Exception {
		// return null when dateStr is blank or null
		if (StringUtils.isBlankOrNull(dateStr)) {
			return null;
		}
		SimpleDateFormat format = null;
		if (dateStr.indexOf('/') > 0) {
			format = new SimpleDateFormat("dd/MM/yyyy", LOCALE_EN);
		} else {
			format = new SimpleDateFormat("ddMMyyyy", LOCALE_EN);
		}
		return format.parse(dateStr);
	}
	
	/**
	 * convert String to date by dateFormat
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return date
	 * @throws Exception
	 */
	public static Date toDate(String dateStr,String dateFormat) throws Exception {
		// return null when dateStr is blank or null
		if (StringUtils.isBlankOrNull(dateStr)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat, LOCALE_EN);

		return format.parse(dateStr);
	}
	
	/**
	 * convert String to date by dateFormat and locale
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @param locale
	 * @return date
	 * @throws Exception
	 */
	public static Date toDate(String dateStr,String dateFormat,Locale locale) throws Exception {
		// return null when dateStr is blank or null
		if (StringUtils.isBlankOrNull(dateStr)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat, locale);

		return format.parse(dateStr);
	}
	/**
	 * get String of currentDate by dateFormat
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentDateStringByFormat(String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat, LOCALE_EN);
		return sf.format(Calendar.getInstance(LOCALE_EN).getTime());
	}
	
	/**
	 * get String of currentDate by dateFormat and locale
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentDateStringByFormat(String dateFormat,Locale locale) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat, locale);
		return sf.format(Calendar.getInstance(locale).getTime());
	}
	
	/**
	 * get String of currentDate by dateFormat 
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String getDateString(Date date , String dateFormat ){
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat, LOCALE_EN);
		Calendar calendar = Calendar.getInstance(LOCALE_EN);
		calendar.setTime(date);
		return sf.format(Calendar.getInstance(LOCALE_EN).getTime());	
	}
	
	/**
	 * get String of currentDate by dateFormat and locale
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String getDateString(Date date , String dateFormat, Locale locale){
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat, locale);
		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		return sf.format(Calendar.getInstance(locale).getTime());	
	}
	
	/**
	 * set time of Date by String of time
	 * 
	 * @param date
	 * @param time String of time in format 24hh:mm example "23:12" , "02:30"
	 * @return date
	 */
	public static Date setTime(Date date, String time) {
		if (date == null || StringUtils.isBlankOrNull(time)) {
			return date;
		}
		if (time.length() == 5 && time.indexOf(":") != 2) {
			return date;
		}
		String[] times = time.split(":");
		int hours = Integer.parseInt(times[0]);
		int minute = Integer.parseInt(times[1]);
		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.HOUR_OF_DAY, hours);
		cal1.add(Calendar.MINUTE, minute);
		return cal1.getTime();
	}
	
	/**
	 * set time of date
	 * 
	 * @param date
	 * @param hours hours of day 0-23
	 * @param minute minute of hour 0-59
	 * @return date
	 */
	public static Date setTime(Date date, int hours , int minute) {
		if (date == null) {
			return date;
		}
		Calendar cal1 = Calendar.getInstance(LOCALE_EN);
		cal1.setTime(date);
		cal1.add(Calendar.HOUR_OF_DAY,hours);
		cal1.add(Calendar.MINUTE,minute);
		return cal1.getTime();
	}
	
	public static Date dateYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateTimeUtils.getCurrentDate());
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	public static Date dateTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateTimeUtils.getCurrentDate());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static int getDiffDate(java.util.Date beginDate, java.util.Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		beginDate = resetTime(beginDate);
		endDate = resetTime(endDate);

		cal1.setTime(beginDate);
		cal2.setTime(endDate);

		int diffDay = 0;
		if (cal1.before(cal2)) {
			diffDay = countDiffDay(cal1, cal2);
		} else {
			diffDay = countDiffDay(cal2, cal1);
		}
		return diffDay;
	}
	
	public static int countDiffDay(Calendar cal1, Calendar cal2) {
		long millis1 = cal1.getTimeInMillis();
		long millis2 = cal2.getTimeInMillis(); 
		long diff = millis2 - millis1;
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);
		if(diffHours < 0L) {
			diffDays = diffDays-1L;
		} 
		return (int) diffDays;
	}
}
