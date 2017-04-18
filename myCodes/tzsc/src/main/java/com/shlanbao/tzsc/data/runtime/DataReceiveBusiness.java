package com.shlanbao.tzsc.data.runtime;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.data.runtime.bean.DataSnapshot;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataEquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataHandler;
import com.shlanbao.tzsc.data.runtime.handler.FaultDataCalc;
/**
 * 底层数据接收处理
 * @author Leejean
 * @create 2015年1月6日上午9:48:26
 */
public class DataReceiveBusiness {
	
	private Logger log = Logger.getLogger(this.getClass());
	private static DataReceiveBusiness instance = null;
	
	private static DataEquipmentData equipmentData = null;
	
	private DataReceiveBusiness() {}
	public static DataReceiveBusiness getInstance() {
		if (instance == null) {
			instance = new DataReceiveBusiness();
		}
		return instance;
	}
	
	/**
	 * 【功能说明】：socket通信，接收数据信息
	 * 
	 * @createTime  2015年10月19日15:44:23
	 * @author wanchanghuang
	 * 
	 * */
	public void updateDataSnapshot(Object msg) {
		@SuppressWarnings("unchecked")
		Map<String, CommonData> dataMap = (Map<String, CommonData>) (msg);
		try {
				/** 
				 * 获取设备ID
				 * 注意：底层数采系统把设备的code定义为ID
				 * PMS系统解析时使用CODE字段与之匹配
				 * ps:数据库设计时，设备表的ID为UUID，非1,2,31,32
				 */
				int equipmentID = Integer.valueOf(dataMap.get("EquipmentID").getVal());
				EquipmentData eqpData = DataSnapshot.getInstance().getEquipmentData(equipmentID);
				if (eqpData == null) {
					eqpData = new EquipmentData();
					eqpData.setEqp(equipmentID);
				}
				//设备型号
				String type ="";
				try {
					//根据本参数调用不同解析方式
					type = dataMap.get("TYPE").getVal().toString();
					eqpData.setType(type);//设备类型、型号
				} catch (Exception e) {
					log.error("socket获取设备:"+equipmentID+" 设备型号异常");
				}
				//网络状态
				try {
					Boolean bOnline = Boolean.valueOf(dataMap.get("NETSTATE").getVal().equalsIgnoreCase("1"));
					eqpData.setOnline(bOnline);//网络状态
				} catch (Exception e) {
						log.error("socket获取设备:"+equipmentID+" 网络状态异常");
				}
				//循环替换数据（实时刷新数据）
				Iterator<String> itr = dataMap.keySet().iterator();
				while (itr.hasNext()) {
					String key = itr.next();
					if (key.equalsIgnoreCase("EquipmentID") || key.equalsIgnoreCase("NETSTATE") || key.equalsIgnoreCase("TYPE")) {
						continue;
					}
					CommonData cd = dataMap.get(key);
					eqpData.setData(cd);//替换上一次数据
				}
				/**
					//修改EquipmentData中的值 add by luther.zhang 20150402处理换班逻辑业务
					if(null==equipmentData){
						equipmentData = new DataEquipmentData();
					}
					//处理托班
					eqpData = equipmentData.editEquipmentData(eqpData); 
					
			    */
				/**
				 * 【功能说明】：存放内存中  （这个方法是 把数据放到 全局变量的,方便不同业务读取） 
				 *          读取缓存中的数据，请参考PackerIspServiceImpl.java类
				 *          
				 * */ 
				DataSnapshot.getInstance().setEquipmentData(eqpData);
				//增加设备故障统计,放入队列
				FaultDataCalc.getInstance().getQueue().add(dataMap);
				//实例化数据处理类,放入线程队列等待处理
				DataHandler.getInstance();
		} catch (Exception e) {
			log.error("updateDataSnapshot--socket接收后初步处理数据出错:" + e.getMessage());
		}
	}
	/**
	 * 检测ip是否合法
	 * @author Leejean
	 * @create 2015年1月6日上午9:05:35
	 * @param validIPs
	 * @param ip
	 * @return
	 */
	public boolean checkIp(List<String> validIPs, String ip) {
		return validIPs.contains(ip);
	}
	
}
