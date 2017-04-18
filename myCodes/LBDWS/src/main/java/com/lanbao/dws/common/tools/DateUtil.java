package com.lanbao.dws.common.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期转换工具类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:13:19
 */
public class DateUtil {
	private static String GLOBAL_DATE_PATTERN="yyyy-MM-dd HH:mm:ss";
	//private static Logger log = Logger.getLogger(DateUtil.class);
	/**
	 * 字符串转日期
	 * @param str 被格式化字符串
	 * @param pattern 格式化字符,不传入pattern时,本方法采用GLOBAL_DATE_PATTERN格式化
	 * @return Date
	 */
	public static Date formatStringToDate(String str,String...pattern) {
		Date date = null;
		if(str==null||"".equals(str.trim())){
			return date;
		}
		if(pattern.length==0){			
			pattern=new String[1];
			pattern[0]=GLOBAL_DATE_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern[0]);
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 日期转字符串
	 * @param date 被格式化日期
	 * @param pattern 格式化字符,不传入pattern时,本方法采用GLOBAL_DATE_PATTERN格式化
	 * @return String
	 */
	public static String formatDateToString(Date date, String... pattern) {
		try {
			if(date==null){
				return "";
			}
			if(pattern.length==0){			
				pattern=new String[1];
				pattern[0]=GLOBAL_DATE_PATTERN;
			}			
			SimpleDateFormat sdf = new SimpleDateFormat(pattern[0]);
			return sdf.format(date).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 取得现在的日期及时间
	 * @param formatLayout 格式化 如：yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static  String getNowDateTime(String formatLayout) {
		java.text.SimpleDateFormat sdf;
		if (formatLayout == null || formatLayout.length() <= 0)
			sdf = new java.text.SimpleDateFormat(GLOBAL_DATE_PATTERN);
		else
			sdf = new java.text.SimpleDateFormat(formatLayout);

		return (sdf.format(new java.util.Date()));
	}
	/**
	 * 根据传过来的Str和格式,格式化str到Date
	 * @param str 日期
	 * @param formatLayout 转换格式 如：yyyy-MM-dd HH:mm:ss
	 * @return Date
	 * @throws ParseException
	 */
	public static Date strToDate(String str,String formatLayout){
		if (str == null || str.trim().length() < 1)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(formatLayout);
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	/**
	 * 根据转过来的date和时间格式，把date转化为指点格式的字符串
	 * @param dateTime
	 * @return string
	 */
	public static String datetoStr(Date date,String dateStr){
		if(date==null||"".equals(date)){
			return  null;
		}else{
			return new SimpleDateFormat(dateStr).format(date);
		}
	}
	 /**
	 * 将dateFormat格式的time转成toDateFormat格式的时间，返回
	 * @author luther.zhang
	 * @param time           时间
	 * @param dateFormat     time 格式 yyyy-MM-dd
	 * @param toDateFormat   期望转成的格式 yyyyMMdd
	 * @return
	 */
	public static String formatStrDate(String time,String dateFormat,String toDateFormat){
		if(null!=time&&!"".equals(time.trim())){
			time = time.trim();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				Date date = sdf.parse(time);
				sdf = new SimpleDateFormat(toDateFormat);
				time = sdf.format(date);
			} catch (Exception e) {
				if(time.length()>10){
					time=time.substring(0,10);
				}
			}
		}else{
			time = "";
		}
		return time;
			
	}
	/**
	 * 返回已添加指定时间间隔的日期
	 * 
	 * @param interval
	 *            表示要添加的时间间隔("y":年;"d":天;"m":月;如有必要可以自行增加)
	 * @param number
	 *            表示要添加的时间间隔的个数
	 * @param date
	 *            java.util.Date()
	 * @param dateFormat
	 *            返回的日期格式
	 * 
	 * @return String 默认为yyyy-MM-dd HH:mm:ss.SSS格式的日期字串
	 * @author pepsi.liao
	 */
	public static String dateAdd(String interval, int number,
			java.util.Date date, String dateFormat) {
		String strTmp = "";
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss sss";
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		// 加若干年
		if (interval.equals("y")) {
			int currYear = gc.get(Calendar.YEAR);
			gc.set(Calendar.YEAR, currYear + number);
		}
		// 加若干月
		else if (interval.equals("m")) {
			int currMonth = gc.get(Calendar.MONTH);
			gc.set(Calendar.MONTH, currMonth + number);
		}
		// 加若干天
		else if (interval.equals("d")) {
			int currDay = gc.get(Calendar.DATE);
			gc.set(Calendar.DATE, currDay + number);
		}
		// 加若小时
		else if (interval.equals("h")) {
			int currDay = gc.get(Calendar.HOUR);
			gc.set(Calendar.HOUR, currDay + number);
		}
		// 加若分
		else if (interval.equals("f")) {
			int currDay = gc.get(Calendar.MINUTE);
			gc.set(Calendar.MINUTE, currDay + number);
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateFormat);
		strTmp = bartDateFormat.format(gc.getTime());
		return strTmp;
	}
	
	/**
	 * 把 yyyy-MM-dd HH:mm:ss.SSS的数据转变为long型
	 * @return
	 * @throws ParseException
	 */
	public static  long getTime(String strDate,String patten) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat();
	 	format.applyPattern(patten);//"yyyy-MM-dd HH:mm:ss.SSS"	      
        java.util.Date date = format.parse( strDate );
		return date.getTime();
	}
	
	
	static protected java.util.Calendar getDateCalendar(
			java.util.Calendar calendar, String strDate) throws Exception {

		if (strDate != null) {
			calendar.set(java.util.Calendar.YEAR, new Integer(strDate
					.substring(0, 4)).intValue());
			calendar.set(java.util.Calendar.MONTH, new Integer(strDate
					.substring(4, 6)).intValue() - 1);
			calendar.set(java.util.Calendar.DATE, new Integer(strDate
					.substring(6, 8)).intValue());
		}
		return (calendar);
	}
	/**
	 * 计算两个日期所间隔的天数
	 * @param strDate1 开始的日期
	 * @param strDate2 结束的日期
	 * @return int
	 *  输出结果:
	 *  diff=366
	 *  diff=-2
	 *  diff=0
	 * </pre>
	 */
	static public int computerDiffDate(String strDate1, String strDate2)
			throws Exception {
		java.util.Calendar c1 = DateUtil.getDateCalendar(java.util.Calendar
				.getInstance(), strDate1);
		java.util.Calendar c2 = DateUtil.getDateCalendar(java.util.Calendar
				.getInstance(), strDate2);

		int day = (int) ((c2.getTime().getTime() - c1.getTime().getTime()) / 86400000);

		return (day);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月14日 上午11:40:04 
	* 功能说明 ：求时间差（分钟）
	* @param sDate  开始时间
	* @param eDate	结束时间
	 */
	public static int calcDiffDate(Date sDate,Date eDate){
		int result=-1;
		try {
			long diff=eDate.getTime()-sDate.getTime();
			result=(int)diff/(1000*60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	* @Title: getMonthStartDay 
	* @Description: 获取月初Date  
	* @param  year
	* @param  month
	* @return Date    返回类型 
	* @throws
	 */
	public static Date getMonthStartDay(int year,int month){
		Calendar ca =Calendar.getInstance();
		ca.set(Calendar.YEAR, year);
		ca.set(Calendar.MONTH, month-1);
		ca.set(Calendar.DAY_OF_MONTH,1);
		return ca.getTime();
	}
	/**
	 * 
	* @Title: getMonthEndDay 
	* @Description: 获取月末Date  
	* @param  year
	* @param  month
	* @return Date    返回类型 
	* @throws
	 */
	public static Date getMonthEndDay(int year,int month){
		Date d=getMonthStartDay(year,month);
		Calendar ca =Calendar.getInstance();
		ca.setTime(d);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
	}
	/**
	* @Title: getMonthStartDay 
	* @Description: TODO  
	* @param  year
	* @param  month
	* @param  formatString
	* @return String    返回类型 
	* @throws
	 */
	public static String getMonthStartDay(int year,int month,String formatString){
		SimpleDateFormat format = new SimpleDateFormat(formatString); 
		return format.format(getMonthStartDay(year, month));
	}
	/**
	* @Title: getMonthEndDay 
	* @Description: 返回月末的String格式  
	* @param  year
	* @param  month
	* @param  formatString
	* @return String    返回类型 
	* @throws
	 */
	public static String getMonthEndDay(int year,int month,String formatString){
		SimpleDateFormat format = new SimpleDateFormat(formatString); 
		return format.format(getMonthEndDay(year, month));
	}
	/**
	 * 
	* @Title: dateCal 
	* @Description: 日期相加   
	* @param  date
	* @param  num 分钟
	* @return Date    返回类型 
	* @throws
	 */
	public static Date dateCal(Date date,int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //加天
        cal.add(Calendar.MINUTE, num);
        return cal.getTime();
	}
	
	public static Date dateCals(Date date,int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //加天
        cal.add(Calendar.DAY_OF_MONTH, num);
        return cal.getTime();
	}
	
	public static String getYesterdayf(){
		  Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		  return yesterday;
	}
	
	/**
	 * 传日期+多少天
	 * 
	 * */
	@SuppressWarnings("static-access")
	public static String dateFormatCal(String titleDate,int num){
		try {
			 SimpleDateFormat sdf=new SimpleDateFormat( "yyyy-MM-dd");
			  Date date = sdf.parse(titleDate); 
		      Calendar   calendar   =   new   GregorianCalendar();   
		      calendar.setTime(date);   
		      calendar.add(calendar.DATE,num);//把日期往后增加一天.整数往后推,负数往前移动   
		      date=calendar.getTime();   //这个时间就是日期往后推一天的结果   
		      String putDate = sdf.format(date);
		      //System.out.println(putDate);
		      return putDate;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("wct交接班查询工单一次");
		}
      return null;
	}

}
