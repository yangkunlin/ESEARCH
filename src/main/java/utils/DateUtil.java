package utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 *
 * @author YKL on 2018/6/12.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class DateUtil {

    /**
     * 返回 yyyy-MM-dd 格式的日期
     * @param milliseconds
     * @return
     */
    public static String dateStr(Long milliseconds){
        DateTime dateTime = new DateTime(milliseconds);
        return dateTime.toString("yyyy-MM-dd");
    }

    /**
     * 返回 yyyy-MM-dd HH:mm:ss 格式的日期
     * @param milliseconds
     * @return
     */
    public static String dateHMSStr(Long milliseconds){
        DateTime dateTime = new DateTime(milliseconds);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 接受一个时间戳的参数，返回日期和小时
     * @param milliseconds
     * @return
     */
    public static String dateHourStr(Long milliseconds){
        DateTime dateTime = new DateTime(milliseconds);
        return dateTime.toString("yyyy-MM-dd") + dateTime.toString("H");
    }

    /**
     *
     * @return 返回当前时间戳
     */
    public static Long getTimeNow() {
        DateTime now= new DateTime();
        return now.getMillis();
    }

    /**
     *
     * @return 返回当前的日期串
     */
    public static String getDateNow() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String YMD = dateFormat.format(now);
        return YMD;
    }

    /**
     *
     * @return 返回当前的月份
     */
    public static String getMonthNow() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String YM = dateFormat.format(now);
        return YM;
    }

    /**
     *
     * @return 返回当前的年份
     */
    public static String getYearNow() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String Y = dateFormat.format(now);
        return Y;
    }

    /**
     *
     * @return 返回昨日的日期串
     */
    public static String getYesterday() {
        Date date = new Date();
        String yesterday = getDaysBefore(date, 1);
        return yesterday;
    }

    /**
     * 指定日期和间隔天数，返回指定日期前N天的日期 date - N days
     *
     * @param date
     * @param interval
     * @return
     */
    public static String getDaysBefore(Date date, int interval) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -interval);
        String yesterday = dateFormat.format(cal.getTime());
        return yesterday;
    }

}
