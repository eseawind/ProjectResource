package com.shlanbao.tzsc.data.runtime.handler;

import java.util.Map;

import com.shlanbao.tzsc.utils.tools.ConfigUtil;



/**
 * 说明：并不是DAC采集的数据数采系统都需要，通过本类定义的点表来过滤
 * @author Leejean
 * @create 2015年1月7日下午2:08:04
 */
public class NeedDataPonits {
	private static Map<String,String> dataPonits = null;
	/**
	 * 初始化需要的数据点
	 * @author Leejean
	 * @create 2015年1月22日下午2:15:59
	 */
	private static void instanceDataPonits(){
		if (dataPonits == null){
			dataPonits = ConfigUtil.getParamsMap("needDataPoints");
		}
	}
	public static Map<String, String> getDataPonits() {
		instanceDataPonits();
		return dataPonits;
	}
	
}
