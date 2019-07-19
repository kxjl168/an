
package com.kxjl.video.util;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/31 11:06
 * @since 1.0.0
 */
public class DateUtil {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat orderNoFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 获取当前系统时间
     * @return
     */
    public static String nowDateFormatString () {
        return format.format(new Date());
    }

    /**
     * 获取当前数据库时间戳
     * @return
     */
    public static String nowTimestampString () {
    	//后台时间
    	return nowDateFormatString();//String.valueOf(new DateTime().getMillis());
    	
        //return new Timestamp(System.currentTimeMillis()).toString();
    }

    /**
     * 根据系统当前时间获取订单编号
     * @return
     */
    public static String timestampOrderNo () {
        Random random = new Random();
        return orderNoFormat.format(new Date()) + random.nextInt(999);
    }

    public static String getDayString(String str){
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time=formatter.format(date);
        return time;
    }

    public static String getMinString(String str){
        if (str!=null&&!str.equals("")){
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time=formatter.format(date);
        return time;
        }else {
            return "";
        }
    }
    public static String getSecondString(String str){
        if (str!=null&&!str.equals("")){
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String time=format.format(date);
            return time;
        }else {
            return "";
        }
    }

    /**
     * 根据cell时间转成字符串
     * @param cell
     * @return
     */
    public static String getCellDateTime(Cell cell){
        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
        String value = format.format(date);
        return value;
    }


    public static String getLastWeek(Date dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.add(Calendar.DATE,-7);
        return new SimpleDateFormat( "yyyy-MM-dd 00:00:00").format(cal.getTime());
    }


    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        if (week==1){
            calendar.set(Calendar.DAY_OF_MONTH,1);
            return calendar.getTime();
        }
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // MONDAY
        return calendar.getTime();
    }
}
