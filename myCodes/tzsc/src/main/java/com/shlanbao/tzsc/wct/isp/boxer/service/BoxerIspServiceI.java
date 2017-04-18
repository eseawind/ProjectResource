package com.shlanbao.tzsc.wct.isp.boxer.service;

import com.shlanbao.tzsc.wct.isp.boxer.beans.BoxerData;

public interface BoxerIspServiceI {
	/**
	 * 初始化封箱机基本信息
	 * @author Leejean
	 * @create 2015年1月5日上午10:52:15
	 * @param equipmentCode 设备编码
	 * @return
	 */
	BoxerData initBoxerBaseInfo(String equipmentCode);
	/**
	 * 获取封箱机实时数据
	 * @author Leejean
	 * @create 2015年1月5日上午10:53:45
	 * @param equipmentCode 设备编码
	 * @return
	 */
	BoxerData getBoxerData(String equipmentCode);
}
