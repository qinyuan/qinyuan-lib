package com.qinyuan.lib.lang.time;

import com.qinyuan.lib.lang.IntegerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Tool class about date
 * Created by qinyuan on 15-1-5.
 */
public class DateUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {
    }

    /**
     * create Date Object by String such as '2000-12-01' or '2000-12-01 12:12:05'
     *
     * @param dateStr date String such as '2000-12-01' or '2000-12-01 13:13:24'
     * @return a {@link java.sql.Date} instance
     */
    public static Date newDate(String dateStr) {
        try {
            if (isDate(dateStr)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return new Date(dateFormat.parse(dateStr).getTime());
            } else if (isDateTime(dateStr)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return new Date(dateFormat.parse(dateStr).getTime());
            }
        } catch (ParseException e) {
            LOGGER.error("error in parsing date String '{}': {}", dateStr, e);
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Invalid date format: '" + dateStr + "'");
    }

    /**
     * Create Calendar object by date string such as '2000-12-15' or '2012-12-01 12:12:23'
     *
     * @param dateString date string  such as '2000-12-15' or '2012-12-01 12:12:23'
     * @return a {@link java.sql.Date} instance
     */
    public static Calendar newCalendar(String dateString) {
        if (isDate(dateString)) {
            String[] stringArray = dateString.split("-");
            int year = Integer.parseInt(stringArray[0]);
            int month = Integer.parseInt(stringArray[1]) - 1;
            int day = Integer.parseInt(stringArray[2]);
            return new GregorianCalendar(year, month, day);
        } else if (isDateTime(dateString)) {
            String[] stringArray = dateString.split("\\D");
            int year = Integer.parseInt(stringArray[0]);
            int month = Integer.parseInt(stringArray[1]) - 1;
            int day = Integer.parseInt(stringArray[2]);
            int hour = Integer.parseInt(stringArray[3]);
            int minute = Integer.parseInt(stringArray[4]);
            int second = Integer.parseInt(stringArray[5]);
            return new GregorianCalendar(year, month, day, hour, minute, second);
        } else {
            throw new RuntimeException("Invalid date format: '" + dateString + "'");
        }
    }

    /**
     * Convert date value to long style string such as "2015-01-01 12:12:12"
     *
     * @param date date value to convert
     * @return long style date string
     */
    public static String toLongString(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * build date string by year, month and day.
     * for example, buildDateString(2015, 1, 3) will return 2015-01-03
     *
     * @param year  year of date, from 1900 to 2100
     * @param month month part of date, from 1 to 12
     * @param day   day part of date, from 1 to 31
     * @return date string
     */
    public static String buildDateString(Integer year, Integer month, Integer day) {
        if (!IntegerUtils.isPositive(year) || !IntegerUtils.isPositive(month)
                || !IntegerUtils.isPositive(day)) {
            return null;
        }

        return new DecimalFormat("0000").format(year) + "-" + new DecimalFormat("00").format(month)
                + "-" + new DecimalFormat("00").format(day);
    }

    public static int currentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int currentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * @return a string represent current time such as "2015-06-26 00:59:23"
     */
    public static String nowString() {
        return toLongString(now());
    }

    public static String todayStartTime() {
        return now().toString() + " 00:00:00";
    }

    public static String todayEndTime() {
        return now().toString() + " 23:59:59";
    }

    private final static String DATE_PATTERN = "\\d{4}-\\d{1,2}-\\d{1,2}";

    public static boolean isDate(String date) {
        return date != null && date.matches("^" + DATE_PATTERN + "$");
    }

    private final static String DATE_TIME_PATTERN = DATE_PATTERN + " " + TimeUtils.TIME_PATTERN;

    public static boolean isDateTime(String dateTime) {
        return dateTime != null && dateTime.matches("^" + DATE_TIME_PATTERN + "$");
    }

    private final static String MYSQL_DATE_TIME_PATTERN = DATE_TIME_PATTERN + "\\.\\d+";

    public static boolean isDateTimeFromMySQL(String dateTime) {
        return dateTime != null && dateTime.matches("^" + MYSQL_DATE_TIME_PATTERN + "$");
    }

    public static boolean isDateOrDateTime(String value) {
        return isDate(value) || isDateTime(value);
    }

    /**
     * calculate the days between first date and second date
     *
     * @param date1 first date
     * @param date2 second date
     * @return a number greater than zero if date1 and is earlier than date2
     */
    public static int getDayDiff(Date date1, Date date2) {
        return getSecondDiff(date1, date2) / 3600 / 24;
    }

    /**
     * calculate the seconds between first date and second date
     *
     * @param date1 first date
     * @param date2 second date
     * @return a number greater than zero if date1 and is earlier than date2
     */
    public static int getSecondDiff(Date date1, Date date2) {
        long timeStampDiff = date2.getTime() - date1.getTime();
        return (int) (timeStampDiff / 1000);
    }

    public static int SECONDS_OF_ONE_DAY = 3600 * 24;

    public static Date threeDaysAgo() {
        long secondsOfThreeDays = 3 * SECONDS_OF_ONE_DAY;
        return new Date(System.currentTimeMillis() - secondsOfThreeDays * 1000);
    }

    public static Date threeMonthsAgo() {
        long secondsOfThreeMonths = 90 * SECONDS_OF_ONE_DAY;
        return new Date(System.currentTimeMillis() - secondsOfThreeMonths * 1000);
    }

    public static Date oneDayLater() {
        return new Date(System.currentTimeMillis() + SECONDS_OF_ONE_DAY * 1000);
    }

    public static Date oneYearLater() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        return new Date(cal.getTimeInMillis());
    }

    public static Date oneYearAgo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * Delete the  milli second part of date
     * <p>
     * In MySQL, the date string queried from database looks like
     * "2015-01-01 15:15:15.5", we need to convert it to format such as
     * "2015-01-01 15:15:15"
     * </p>
     *
     * @param dateString date string queried from MySQL
     * @return adjusted date string
     */
    public static String trimMilliSecond(String dateString) {
        return dateString == null ? null : dateString.replaceAll("\\.\\d*$", "");
    }

    public static String getDatePart(String dateTime) {
        return dateTime == null ? null : dateTime.replaceAll("\\s.*$", "");
    }
}
