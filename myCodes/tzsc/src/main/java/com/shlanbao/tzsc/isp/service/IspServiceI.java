package com.shlanbao.tzsc.isp.service;

import java.util.List;

import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;

public interface IspServiceI {
	/**
	 * 根据设备Code获取该设备的实时数据
	 * @author Leejean
	 * @create 2015年1月6日下午12:04:00
	 * @param code 设备Code
	 * @return 实时数据
	 */
	public EquipmentData get(String code);
	/**
	 * 获取所有实时数据
	 * @author Leejean
	 * @create 2015年1月6日下午12:04:00
	 * @return 实时数据
	 */
	public List<EquipmentData> getAll();
	/**
	 * 获得所有格式化之后的数据
	 * @author Leejean
	 * @create 2015年1月22日下午3:01:02
	 * @return
	 */
	public List<EquipmentData> getAllFormatted();
	/**
	 * 根据设备code获得设备格式化后的数据
	 * @author Leejean
	 * @create 2015年1月22日下午3:01:18
	 * @param code
	 * @return
	 */
	public EquipmentData getFormatted(String code);
	
}
