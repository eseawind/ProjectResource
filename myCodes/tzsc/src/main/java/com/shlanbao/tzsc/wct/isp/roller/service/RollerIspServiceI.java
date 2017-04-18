package com.shlanbao.tzsc.wct.isp.roller.service;

import com.shlanbao.tzsc.wct.isp.roller.beans.RollerData;

public interface RollerIspServiceI {
	/**
	 * 初始化卷烟机基本信息
	 * @author Leejean
	 * @create 2015年1月5日上午10:52:15
	 * @param equipmentCode 设备编码
	 * @return
	 */
	RollerData initRollerBaseInfo(String equipmentCode);
	/**
	 * 获取卷烟机实时数据
	 * @author Leejean
	 * @create 2015年1月5日上午10:53:45
	 * @param equipmentCode 设备编码
	 * @return
	 */
	RollerData getRollerData(String equipmentCode);

}
