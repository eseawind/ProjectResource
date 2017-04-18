package com.lanbao.dws.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:38:11
 */
public class ConfigUtil {

	private static ResourceBundle bundle = null;
	
	public static void initBundle(String name) {
		bundle = java.util.ResourceBundle.getBundle(name);
	}
	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	public static final String get(String configName,String key) {
		initBundle(configName);
		return bundle.getString(key);
	}
	
	
	
	public static final Map<String,String> getParamsMap(String configName){
		initBundle(configName);
		Map<String,String> paramsMap=new HashMap<String, String>();
		 Enumeration<String> enumeration=bundle.getKeys();
		 while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			paramsMap.put(key, bundle.getString(key));
			System.out.println(key);
		}
		return paramsMap;
	}
	
	/**
	 * @deprecated
	 * 读取GD故障德中文对照关系
	 * @return
	 */
	public static Hashtable<String,String> readTxtFile(){
		String filePath = ConfigUtil.class.getClassLoader().getResource("GD_Compile.txt").getPath().toString();
		Hashtable<String,String> ht=new Hashtable<String,String>();
        try {  
            String encoding="UTF-8";  
            File file=new File(filePath);  
            if(file.isFile() && file.exists()){ //判断文件是否存在  
                InputStreamReader read = new InputStreamReader(  
                new FileInputStream(file),encoding);//考虑到编码格式  
                BufferedReader bufferedReader = new BufferedReader(read);  
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){  
                	try{
                		ht.put(lineTxt.substring(0, lineTxt.indexOf("^")).trim(), lineTxt.substring(lineTxt.indexOf("^")+1,lineTxt.length()).trim());
                	}catch(Exception ex){
                		System.out.println("读取GD故障中英文转换错误");
                	}
                }  
                read.close();  
	        }else{  
	            System.out.println("读取GD故障中英文转换错误_找不到指定的文件");  
	        }  
        } catch (Exception e) {  
            System.out.println("读取GD故障中英文转换错误_读取文件内容出错");  
            e.printStackTrace();  
        }  
        return ht;
      
    } 
}
