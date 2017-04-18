package com.lanbao.dws.common.tools;
/**
 * 本法方仅用于从快照中取数据时<br>
 * 有时快照中的数据为null<br> 
 * 或者为"" 此时我们不需给一个默认值<br>
 * @author Leejean
 * @create 2015年1月14日下午1:31:19
 */
public class GetValueUtil {
	/**
	 * 获得一个double数据  如果为 null||"" return 0 
	 * @author Leejean
	 * @create 2015年1月16日上午9:35:30
	 * @param value
	 * @return
	 */
	public static Double getDoubleValue(String value){
		if(value==null||value.trim().equals("")){
			return 0D;
		}else{
			return Double.valueOf(value);
		}
	}
	/**
	 * 获得一个integer数据  如果为 null||"" return 0 
	 * @author Leejean
	 * @create 2015年1月16日上午9:35:30
	 * @param value
	 * @return
	 */
	public static Integer getIntegerValue(String value){
		if(value==null||value.trim().equals("")){
			return 0;
		}else{
			if(value.contains(".")){
				double d = Double.valueOf(value);
				return (int)d;
			}
			return Integer.valueOf(value);
		}
	}
}
