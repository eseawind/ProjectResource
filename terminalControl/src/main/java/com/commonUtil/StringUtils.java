package com.commonUtil;

public class StringUtils {
	
	/**
	 * <p>功能描述：null 或 空串 都为true</p>
	 *@param obj
	 *@return
	 *作者：SShi11
	 *日期：Apr 6, 2017 1:08:51 PM
	 */
	public static  boolean isEmpty(Object obj){
		boolean flag=true;
		if(obj!=null && obj.toString().trim()!=""){
			flag=false;
		}
		return flag;
	}
	
	
	/**
	 * <p>功能描述：object 到 str</p>
	 *@param obj
	 *@return
	 *作者：SShi11
	 *日期：Apr 15, 2017 2:11:09 PM
	 */
	public static String toStr(Object obj){
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static int toInt(Object obj){
		if(obj!=null){
			return Integer.valueOf(toStr(obj));
		}else{
			return 0;
		}
	}
}
