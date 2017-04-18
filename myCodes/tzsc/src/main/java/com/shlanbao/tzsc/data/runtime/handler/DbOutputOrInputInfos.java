package com.shlanbao.tzsc.data.runtime.handler;

import java.util.Map;

/**
 * 设备产出实绩和消耗实绩信息
 * @author Leejean
 * @create 2015年1月27日下午4:24:49
 */
public class DbOutputOrInputInfos {
	/**
	 * 存储产出对象ID
	 */
	private String statOutputId;
	/**
	 * 存储辅料类型->消耗对象ID
	 */
	private Map<String,String> type_statInputId;
	public String getStatOutputId() {
		return statOutputId;
	}
	public void setStatOutputId(String statOutputId) {
		this.statOutputId = statOutputId;
	}
	public Map<String, String> getType_statInputId() {
		return type_statInputId;
	}
	public void setType_statInputId(Map<String, String> type_statInputId) {
		this.type_statInputId = type_statInputId;
	}
	@Override
	public String toString() {
		return "[生产实际产出ID=" + statOutputId
				+ ", 辅料类-生产实际消耗ID=" + type_statInputId + "]";
	}
	
	
}
