package com.rockwell.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 时间Util
 * @author SShi11
 *
 */
public class DateUtils {
	public static final String COMMONPATTERN="yyyy-MM-dd HH:mm:ss";
	
	private static SimpleDateFormat format(String pattern){
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		return format;
	}
	public static String nowTime(Date date,String pattern){
		return format(pattern).format(date);
	}
}
