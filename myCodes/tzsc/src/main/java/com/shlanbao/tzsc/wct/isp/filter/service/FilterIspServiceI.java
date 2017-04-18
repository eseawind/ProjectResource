package com.shlanbao.tzsc.wct.isp.filter.service;

import com.shlanbao.tzsc.wct.isp.filter.beans.FilterData;

public interface FilterIspServiceI {
	/**
	 * 初始化成型机基本信息
	 * @author Leejean
	 * @create 2015年1月5日上午10:52:15
	 * @param equipmentCode 设备编码
	 * @return
	 */
	FilterData initFilterBaseInfo(String equipmentCode);
	/**
	 * ZL22A型成型机获取成型机实时数据
	 * @author Leejean
	 * @create 2015年1月5日上午10:53:45
	 * @param equipmentCode 设备编码
	 * @return
	 */
	FilterData getFilterData(String equipmentCode);
	/**
	 * ZL22C型成型机获取成型机实时数据
	 * @author cmc
	 * @create 2016年12月14日下午14：07
	 * @param equipmentCode
	 * @return
	 */
	FilterData getFilterDataCX_FL(String equipmentCode);
}
