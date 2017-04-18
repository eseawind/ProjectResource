package com.shlanbao.tzsc.wct.isp.packer.service;

import com.shlanbao.tzsc.wct.isp.packer.beans.PackerData;

public interface PackerIspServiceI {
	/**
	 * 初始化包装机基本信息
	 * @author Leejean
	 * @create 2015年1月5日上午10:52:15
	 * @param equipmentCode 设备编码
	 * @return
	 */
	PackerData initPackerBaseInfo(String equipmentCode);
	/**
	 * 获取包装机实时数据
	 * @author Leejean
	 * @create 2015年1月5日上午10:53:45
	 * @param equipmentCode 设备编码
	 * @return
	 */
	PackerData getPackerData(String equipmentCode);
}
