package com.lanbao.dws.common.tools;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonUtil {
	/**
	 * @author 作者 : shisihai
	 * @version 创建时间：2016年7月8日 下午12:02:57 功能说明 ：对象转json字符窜
	 */
	public static String bean2Json(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月8日 下午12:04:28 
	* 功能说明 ：字符串转bean
	 */
	public static <T> T fromJson2Bean(String josn, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(josn, type);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月8日 下午12:04:28 
	* 功能说明 ：字符串转bean
	 */
	 public static <T> T fromJson2Bean(String str, Class<T> type) {  
	        Gson gson = new Gson();  
	        return gson.fromJson(str, type);  
	 }
	 
}
