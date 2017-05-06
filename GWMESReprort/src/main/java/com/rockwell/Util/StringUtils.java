package com.rockwell.Util;

/**
 * 字符串处理工具
 * @author SShi11
 *
 */
public class StringUtils {
	public static String toStr(Object obj){
		return obj==null?"":obj.toString();
	}
	
	public boolean isEmpty(Object obj){
		return obj==null?true:obj.toString().trim().equals("");
	}
}
