package com.lanbao.dws.common.data.dac;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lanbao.dac.data.CommonData;
import com.lanbao.dws.common.data.dac.handler.DataHandler;
import com.lanbao.dws.common.data.dac.handler.DataSnapshot;
import com.lanbao.dws.common.data.realTimeData.SaveLastSnapshotDataContorller;
import com.lanbao.dws.common.init.BaseParams;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.dac.EquipmentData;
import com.lanbao.dws.common.data.dac.handler.FaultDataCalc;



public class DataReceiveBusiness {

	private Logger log = Logger.getLogger(this.getClass());
	private static DataReceiveBusiness instance = null;
	private static SaveLastSnapshotDataContorller lastSnapshotData =null;
	
	private DataReceiveBusiness() {}
	public static DataReceiveBusiness getInstance() {
		if (instance == null) {
			instance = new DataReceiveBusiness();
			lastSnapshotData =new SaveLastSnapshotDataContorller();
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
			    *  #判断DAC是否发送换班指令  
			    *  #1-早   2-中  3-晚    
			    *     1）将DAC发送过来的数据轮询替换更新，保留新数据，替换旧数据并保存到快照里面；
			    *  #91-换成早班  92-换成中班   93-换成晚班   
			    *     1）跳过数据替换更新处理   2）保存最后快照数据； 3）开启下一班次工单信息；
                */
			   String chgShift=dataMap.get("SHIFTINFO").getVal().toString();
			   int equipmentID =-1;
			   if( "1".equals(chgShift) || "2".equals(chgShift) || "3".equals(chgShift) ){
				   
				   
				    //md_equipment表 code
					equipmentID = Integer.valueOf(dataMap.get("EquipmentID").getVal());
					//注销卷包车间DAC换班指令
				   if(BaseParams.getDacMap().size()>0){
					   if(StringUtil.queryWorkshopByEqpCode(equipmentID)==1){
						   BaseParams.getDacMap().clear();
					   }
				   };
				   //注销成型车间DAC换班指令
				   if(BaseParams.getFilterDacMap().size()>0){
					   if(StringUtil.queryWorkshopByEqpCode(equipmentID)==2){
						   BaseParams.getFilterDacMap().clear();
					   }
				   };
					
					EquipmentData eqpData = DataSnapshot.getInstance().getEquipmentData(equipmentID);
					//创建对象
					if (eqpData == null) {
						eqpData = new EquipmentData();
						eqpData.setEqp(equipmentID);
					}
					//班次
					try {
						//md_shift表code
						String shiftCode = dataMap.get("SHIFTINFO").getVal().toString();
						eqpData.setShiftCode(shiftCode);
					} catch (Exception e) {
						log.error("socket获取设备:"+equipmentID+" 设备班次异常");
					}
					//设备型号
					try {
						String type = dataMap.get("TYPE").getVal().toString();
						eqpData.setType(type);
					} catch (Exception e) {
						log.error("socket获取设备："+equipmentID+" 设备型号异常");
					}
					//网络状态
					try {
						Boolean bOnline = Boolean.valueOf(dataMap.get("NETSTATE").getVal().equalsIgnoreCase("1"));
						eqpData.setOnline(bOnline);
					} catch (Exception e) {
							log.error("socket获取设备:"+equipmentID+" 网络状态异常");
					}
					//遍历map数据
					Iterator<String> itr = dataMap.keySet().iterator();
					while (itr.hasNext()) {
						String key = itr.next();
						//跳过基本信息： 设备code||网络状态||网络类型,存储辅料，故障，产量等数据
						if (key.equalsIgnoreCase("EquipmentID") || key.equalsIgnoreCase("NETSTATE") || 
								key.equalsIgnoreCase("TYPE") || key.equalsIgnoreCase("SHIFTINFO") || key.equalsIgnoreCase("TEAMINFO")) {
							continue;
						}
						//轮询设备详细数据
						CommonData cd = dataMap.get(key);
						eqpData.setData(cd);
					}
					/**
					 * 【功能说明】：存放内存中  （这个方法是 把数据放到 全局变量的,方便不同业务读取） 
					 *          读取缓存中的数据，请参考PackerIspServiceImpl.java类
					 *          
					 * */ 
					//轮询设备数据
					DataSnapshot.getInstance().setEquipmentData(eqpData);
					//增加设备故障统计,放入队列
					FaultDataCalc.getInstance().getQueue().add(dataMap);
					//实例化数据等待线程处理
					DataHandler.getInstance();
			   }else{
				   /**  接收DAC换班指令:
				    * 1）保存当前班次最后快照数据；
				    * 2）关闭当前工单 
				    * 3)下一班次工单会在定时器自动开启，无需处理
				   */
				   log.info("接收到DAC换班信号!"+DateUtil.formatDateToString(new Date(), "yyyy-MM-dd hh:mm:ss"));
				   //区分卷包和成型机
				   lastSnapshotData.saveLastSnapshotDatas(chgShift,equipmentID);
			   }
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
