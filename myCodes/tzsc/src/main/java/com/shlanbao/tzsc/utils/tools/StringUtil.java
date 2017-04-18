package com.shlanbao.tzsc.utils.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串处理工具类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:13:57
 */
public class StringUtil {
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
				return " and ( "+o_date+" <= '"+to+"' ) ";
			}
			if(!StringUtil.notNull(to)){				
				return " and ( "+o_date+" >= '"+from+"' ) ";
			}
			return " and ("+o_date+" between '"+from+"' and '"+to+"') ";
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
	
	
	
	
	
	
	
}
