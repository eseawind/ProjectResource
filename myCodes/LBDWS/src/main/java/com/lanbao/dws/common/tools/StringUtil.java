package com.lanbao.dws.common.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 字符串处理工具类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:13:57
 */
public class StringUtil {
	
	public static boolean isDouble(Object obj){
		if(notEmpty(obj)){
			return isDouble(obj.toString());
		}
		return false;
	}
	
	public static boolean isFloat(String str){
		try {
			Float.parseFloat(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 去除两端空格
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if ((null!=str) && (!"".equals(str))) {
			return str.trim();
		}
		return str;
	}
	/**
	 * 不为"","   ","null","NULL",NULL 时返回true
	 * @param obj
	 * @return
	 */
	public static boolean notEmpty(Object obj){
		if(obj==null){
			return false;
		}else if(obj.toString().trim().equals("")){
			return false;
		}else if(obj.toString().trim().equalsIgnoreCase("null")){
			return false;
		}
		return true;
	}
	public static Double converObj2Double(Object o){
		Double res=0D;
		try {
			res=Double.valueOf(o.toString());
		} catch (Exception e) {
		}
		return res;
	}
	public static Long converObj2Long(Object o){
		Long res=0L;
		try {
			res=Long.valueOf(o.toString());
		} catch (Exception e) {
		}
		return res;
	}
	public static String convertObj(Object o){
		if(o==null){
			return null;
		}else{
			return o.toString().trim();
		}
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return true 不为空 false 空
	 */
	public static boolean notNull(String str) {
		if ((null!=str) && (!"".equals(str.trim()))) {
			return true;
		}
		return false;
	}
	/**
	 * 判断字符串是否是Integer类型
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
		try{
			Integer.parseInt(str.trim());
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	/**
	 * 判断字符串是否是Double类型
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str){
		try{
			Double.parseDouble(str.trim());
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	public static String convertObjToString(Object o){
		if(null!=o){
			return o.toString();
		}else{
			return "";
		}
	}
	/**
	 * 分割字符串 
	 * @param params 被分割的字符串
	 * @param splitChar 分割字符
	 * @return List<Long>
	 */
	public static List<Long> splitToLongList(String params, String splitChar) {
		List<Long> longlist = new ArrayList<Long>();
		if(params==null||params.trim().equals("")){return longlist;}
		String param[] = params.split(splitChar);
		for (String string : param) {
			if(string.equals("")){
				longlist.add(null);
			}else{	
				longlist.add(Long.parseLong(string));
			}
		}
		return longlist;
	}
	/**
	 * 将字符串转换为String数组
	 * @param params
	 * @param splitChar
	 * @return
	 */
	public static List<String> splitToStringList(String params, String splitChar) {
		List<String> stringlist = new ArrayList<String>();
		if(params==null||params.trim().equals("")){return stringlist;}
		String param[] = params.split(splitChar);
		for (String string : param) {
			if(string.equals("")){
				stringlist.add(null);
			}else{	
				stringlist.add(string);
			}
		}
		return stringlist;
	}
	/**
	 * 将字符串转换为Integer数组
	 * @param params
	 * @param splitChar
	 * @return
	 */
	public static List<Integer> splitToIntegerList(String params, String splitChar) {
		List<Integer> longlist = new ArrayList<Integer>();
		if(params==null||params.trim().equals("")){return longlist;}
		if(!params.trim().equals("")){
			String param[] = params.split(splitChar);
			for (String string : param) {
				if(string.equals("")){
					longlist.add(null);
				}else{				
					longlist.add(Integer.parseInt(string));
				}
			}
		}
		return longlist;
	}
	/**
	 * 将字符串转换为Double数组
	 * @param params
	 * @param splitChar
	 * @return
	 */
	public static List<Double> splitToDouble(String params, String splitChar) {
		List<Double> longlist = new ArrayList<Double>();
		if(params==null||params.trim().equals("")){return longlist;}
		if(!params.trim().equals("")){
			String param[] = params.split(splitChar);
			for (String string : param) {
				if(string.equals("")){
					longlist.add(null);
				}else{	
					longlist.add(Double.parseDouble(string));
				}
			}
		}
		return longlist;
	}
	/**
	 * 格式化
	 * @author Leejean
	 * @create 2014年9月16日下午2:13:17
	 * @param from
	 * @param to
	 * @return
	 */
	public static String fmtDateBetweenParams(String o_date,String from,String to){
		if(StringUtil.notNull(from)||StringUtil.notNull(to)){
			if(!StringUtil.notNull(from)){				
				return " and ( "+o_date+" <= to_date('"+to+"','yyyy-MM-dd') ) ";
			}
			if(!StringUtil.notNull(to)){				
				return " and ( "+o_date+" >= to_date('"+from+"','yyyy-MM-dd') ) ";
			}
			return " and ("+o_date+" between to_date('"+from+"','yyyy-MM-dd') and to_date('"+to+"','yyyy_MM-dd')) ";
		}
		return "";
	}
	
	/**
	* @Title: arrayToString 
	* @Description: String数组转 sql语句中的in '','',''
	* @param  params
	* @return String   返回类型 
	* @throws
	 */
	public static String arrayToStringBySqlin(String[] params){
		String returnString="";
		for(int i=0;i<params.length;i++){
			if(StringUtil.notNull(params[i])){
				if(i==params.length-1){
					returnString+="'"+params[i]+"' ";
				}else{
					returnString+="'"+params[i]+"',";
				}
			}
		}
		return returnString;
	}
	
	
	/**
	 * 判断字符串是否为null，如果为null返回空
	 * */
	public static String nullToString(String str){
		if(str == null ){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月11日 下午5:50:36 
	* 功能说明 ：获取主键
	 */
	public static synchronized String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static int queryWorkshopByEqpCode(int eqpCode){
		int i=-1;
		if(eqpCode>0 && eqpCode<=70){
			i=1;
		}else if(eqpCode>100 && eqpCode<=130){
			i=2;
		}
		return i;
	}
}
