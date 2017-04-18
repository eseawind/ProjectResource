package com.shlanbao.tzsc.data.runtime.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.mapping.MdManualShift;
import com.shlanbao.tzsc.data.runtime.bean.DataSnapshot;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.pms.sch.manualshift.service.ManualShiftServiceI;
import com.shlanbao.tzsc.utils.params.EquipmentTypeDefinition;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

public class DataEquipmentData{
	@Autowired
	private ManualShiftServiceI wctManualShiftService;
	protected static Logger equLog = Logger.getLogger(DataEquipmentData.class);
	/*
	public static String plcBeforCl="";//PLC上一次产量
	public static EquipmentData plcEqpData =null;//PLC上一条记录
	public static int hbPyMinCount= 0;//自动换班偏移 阀值 次数 3次
	public static int hbCount = 0;//自动换班次数,目前最多2次
	private int hbMinFaZhi = 50;//自动换班阀值
	*/	
	//根据不同的设备 放入不同的变量，分别供他们修改使用
	public static Map<String,DataEquipmentDataBean> allMap = new HashMap<String,DataEquipmentDataBean>();
	//根据不同的设备 放入不同的班次信息、上一班信息，分别供他们修改使用 add by luther.zhang 20150721
	public static Map<String,MdManualShift> thisBcMap = new HashMap<String,MdManualShift>();
	/**
	 * 修改最原始的值
	 * @param eqpData
	 * @return
	 */
	public EquipmentData editEquipmentData(EquipmentData eqpData){
		if(null==wctManualShiftService){
			wctManualShiftService = ApplicationContextUtil.getBean(ManualShiftServiceI.class);	
		}
		String type = eqpData.getType().toUpperCase();//可以查询到主数据还是辅料数据
		if(type.contains("_FL")){//辅料数据
			eqpData = editFlEquipmentData(eqpData);//修改辅料数据
		}else{
			eqpData = editZjsEquipmentData(eqpData);//修改主数据
		}
		return eqpData;
	}
	/**
	 * 修改主数据
	 * @param eqpData
	 * @return
	 */
	public EquipmentData editFlEquipmentData(EquipmentData eqpData){//当前辅料信息
		//add by luther.zhang 20150402
		int eqpId = eqpData.getEqp();//设备编号
			eqpId = eqpId/1000;//辅料都要除以 1000
		/* */	
		//根据设备编号 获取正在运行的工单来确定班次信息
		try {
			//modify by luther.zhang 20150721
			MdManualShift shift = thisBcMap.get(String.valueOf(eqpId));
			if(null==shift||"".equals(shift)){
				shift = wctManualShiftService.getManualShift(String.valueOf(eqpId),"S");//这个查询出来的 含有辅料数据
				thisBcMap.put(String.valueOf(eqpId),shift);
			}
			//end
			//end
			//MdManualShift shift = wctManualShiftService.getManualShift(String.valueOf(eqpId),"S");//这个查询出来的 含有辅料数据 注销by luther.zhang 20150721
			if(null!=shift){
				if(null!=eqpData.getData()){//当前数据
					//处理辅料数据
					String startFlPv = shift.getPmvStartFlMsg();//工单开始辅料数据
					if(startFlPv.length()>2){//当前辅料数据 值
						try{
							String startType = shift.getWorkType();//机台型号 可以获取是包装机数据 还是  卷烟机数据
							int beginIndex = startFlPv.indexOf("[{");
							int endIndex = startFlPv.lastIndexOf("}]");
							String startFlArray= startFlPv.substring(beginIndex, (endIndex+2));
							//辅料值
							CommonData[] startFlBean = (CommonData[]) JSONUtil.JSONString2ObjectArray(startFlArray,CommonData.class);
							if(null!=startFlBean&&startFlBean.length>0){
								int passCount = 0;
								for(int i=0;i<startFlBean.length;i++){
									CommonData startData = startFlBean[i];//一行对象集合
									if(EquipmentTypeDefinition.getRoller().contains(startType)){//当前值 卷烟机
										
									}else if(EquipmentTypeDefinition.getPacker().contains(startType)){//当前值  包装机
										eqpData = this.modifyFlData(eqpData,"1",startData,i,shift);//修改 铝箔纸 量
										if(eqpData.isPass()){
											passCount=passCount+1;
										}
										eqpData = this.modifyFlData(eqpData,"3",startData,i,shift);//修改 商标纸 量
										if(eqpData.isPass()){
											passCount=passCount+1;
										}
										eqpData = this.modifyFlData(eqpData,"4",startData,i,shift);//修改 小透纸 量
										if(eqpData.isPass()){
											passCount=passCount+1;
										}
										eqpData = this.modifyFlData(eqpData,"5",startData,i,shift);//修改 条盒纸 量
										if(eqpData.isPass()){
											passCount=passCount+1;
										}
										if(passCount>=4){//全部找到并修改 辅料
											break;	
										}
									}else if(EquipmentTypeDefinition.getFilter().contains(startType)){//当前值  成型机
										
									}
									
								}
							}
						}catch(Exception e){
							equLog.error(" editFlEquipmentData is fuliaoshuju error :"+e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			equLog.error(" editFlEquipmentData is error :"+e.getMessage());
		}
		return eqpData;
	}
	/**
	 * 修改主数据
	 * @param eqpData
	 * @return
	 */
	public EquipmentData editZjsEquipmentData(EquipmentData eqpData){
		//add by luther.zhang 20150402
		int eqpId = eqpData.getEqp();//设备编号
		/* */	
		//根据设备编号 获取正在运行的工单来确定班次信息
		try {
			//modify by luther.zhang 20150721
			MdManualShift shift = thisBcMap.get(String.valueOf(eqpId));
			if(null==shift||"".equals(shift)){
				shift = wctManualShiftService.getManualShift(String.valueOf(eqpId),"S");//这个查询出来的 含有辅料数据
				thisBcMap.put(String.valueOf(eqpId),shift);
			}
			//end
			//MdManualShift shift = wctManualShiftService.getManualShift(String.valueOf(eqpId),"S");//这个查询出来的 含有辅料数据 注销by luther.zhang 20150721
			if(null!=shift){
				eqpData.setShift(Integer.parseInt(shift.getShiftCode()));//班次
				eqpData.setTeam(shift.getTeamCode());//班组
				if(null!=eqpData.getData()){//当前数据
					List<CommonData> list= eqpData.getData();//PLC所有对象
					if(null!=list){ 
						for(int i=0;i<list.size();i++){//获取设备下对应的所有数据
							CommonData commonData = list.get(i);//PLC当前所有值
							//根据设备ID 获取 之前修改保存的值 modify by luther.zhang 20150710
							DataEquipmentDataBean changeBeans = allMap.get(String.valueOf(eqpId));
							if(null==changeBeans||"".equals(changeBeans)){//第一次找不到 初始化值
								changeBeans = new DataEquipmentDataBean();
								allMap.put(String.valueOf(eqpId), changeBeans);//初始化值
							}
							String typeId ="";//卷烟机的产量对应的ID是 7 表示 当前产量
							if(EquipmentTypeDefinition.getRoller().contains(eqpData.getType())){//当前值 卷烟机
								typeId ="7";//产量ID
							}else if(EquipmentTypeDefinition.getPacker().contains(eqpData.getType())){//当前值  包装机
								typeId ="3";//产量ID
							}else if(EquipmentTypeDefinition.getFilter().contains(eqpData.getType())){//当前值  辅料
								
							}
							if(typeId.equals(commonData.getId())){//根据 当前产量来判断 是否换班
								//自动换班逻辑 begin======================================================================================
								double thisCl= Double.parseDouble(commonData.getVal());//PLC当前产量
								if("".equals(changeBeans.getPlcBeforCl())){//第一次 赋值
									changeBeans.setPlcBeforCl(String.valueOf(thisCl));//保存上一次的值
									changeBeans.setPlcEqpData(eqpData);//保存上一次对象
								}else{
									if(thisCl>=Double.parseDouble(changeBeans.getPlcBeforCl())){//当前值 大于 等于 PLC上一次的值 表示没有	自动换班
										changeBeans.setPlcBeforCl(String.valueOf(thisCl));
										changeBeans.setPlcEqpData(eqpData);
										//280 279 :281 280 :80  281 :30  281 :10000 281 
									}else if(thisCl<Double.parseDouble(changeBeans.getPlcBeforCl())&&thisCl<=changeBeans.getHbMinFaZhi()){//PLC 自动换班
										changeBeans.setHbPyMinCount((changeBeans.getHbPyMinCount()+1));
										if(changeBeans.getHbPyMinCount()>3){ //漂移 极小 容错
											changeBeans.setHbPyMinCount(0);//清空 计数器
											changeBeans.setHbCount((changeBeans.getHbCount()+1));//换班次数
											if(changeBeans.getHbCount()==1){//当前班 第一次自动换班
												String[] tes ={""};
												JsonConfig jsonConfig = JSONUtil.kickProperty(tes,EquipmentData.class);
												Map out = new HashMap();
												out.put("root",changeBeans.getPlcEqpData());//最原始的值
												String str = JSONObject.fromObject(out, jsonConfig).toString();
												String updateSql ="update MD_MANUAL_SHIFT set PMV1_MSG=?,PMV1_TIME=?,PMV1_FL_MSG=?,PMV2_MSG=?,PMV2_TIME=?,PMV2_FL_MSG=? where EQPID=? ";
												List<Object> updatePrams = new ArrayList<Object>();
												updatePrams.add(str);//PMV1_MSG
												updatePrams.add(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//PMV1_TIME
												//辅料数据保存 add by luther.zhang 20150715
												int flEqpId = eqpId*1000;
												EquipmentData flEqpData = DataSnapshot.getInstance().getEquipmentData(flEqpId);
												String flStr ="";
												if(null!=flEqpData){//自动开启工单  记录信息为空即可
													String[] flTes ={""};
													JsonConfig flJsonConfig = JSONUtil.kickProperty(flTes,EquipmentData.class);
													Map flOut = new HashMap();
													flOut.put("root",flEqpData);
													flStr = JSONObject.fromObject(out, flJsonConfig).toString();
												}
												//end
												updatePrams.add(flStr);//PMV1_FL_MSG 工单开始的辅料值
												updatePrams.add("");//PMV2_MSG
												updatePrams.add("");//PMV2_TIME
												updatePrams.add("");//PMV2_FL_MSG
												updatePrams.add(eqpId);//设备ID
												try{
													wctManualShiftService.updateInfo(updateSql, updatePrams);
												}catch(Exception e){
													equLog.error("DataEquipmentData->editEquipmentData:"+e.getMessage());
												}
												changeBeans.setPlcBeforCl("");//清空全局变量
												changeBeans.setPlcEqpData(null);//当前班 第二次自动换班
												//自动换班后 重新加载 换班信息 add by luther.zhang 20150721
												shift = wctManualShiftService.getManualShift(String.valueOf(eqpId),"S");//这个查询出来的 含有辅料数据
												thisBcMap.put(String.valueOf(eqpId),shift);
												//end
											}else if(changeBeans.getHbCount()==2){
												String[] tes ={""};
												JsonConfig jsonConfig = JSONUtil.kickProperty(tes,EquipmentData.class);
												Map out = new HashMap();
												out.put("root",changeBeans.getPlcEqpData());//最原始的值
												String str = JSONObject.fromObject(out, jsonConfig).toString();
												String updateSql ="update MD_MANUAL_SHIFT set PMV2_MSG=?,PMV2_TIME=?,PMV2_FL_MSG=? where EQPID=? ";
												List<Object> updatePrams = new ArrayList<Object>();
												updatePrams.add(str);
												updatePrams.add(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
												//辅料数据保存 add by luther.zhang 20150715
												int flEqpId = eqpId*1000;
												EquipmentData flEqpData = DataSnapshot.getInstance().getEquipmentData(flEqpId);
												String flStr ="";
												if(null!=flEqpData){//自动开启工单  记录信息为空即可
													String[] flTes ={""};
													JsonConfig flJsonConfig = JSONUtil.kickProperty(flTes,EquipmentData.class);
													Map flOut = new HashMap();
													flOut.put("root",flEqpData);
													flStr = JSONObject.fromObject(out, flJsonConfig).toString();
												}
												//end
												updatePrams.add(flStr);//PMV2_FL_MSG 工单开始的辅料值
												updatePrams.add(eqpId);//设备ID
												try{
													wctManualShiftService.updateInfo(updateSql, updatePrams);
												}catch(Exception e){
													equLog.error("DataEquipmentData->editZjsEquipmentData:"+e.getMessage());
												}
												changeBeans.setHbCount(0);//当前班 最多横跨二次班
												changeBeans.setPlcBeforCl("");//清空值
												changeBeans.setPlcEqpData(null);//清空全局变量
												//自动换班后 重新加载 换班信息 add by luther.zhang 20150721
												shift = wctManualShiftService.getManualShift(String.valueOf(eqpId),"S");//这个查询出来的 含有辅料数据
												thisBcMap.put(String.valueOf(eqpId),shift);
												//end
											}
										}
									}else{//发生 极大值 漂移处理
										changeBeans.setPlcBeforCl("");//清空全局变量
										changeBeans.setPlcEqpData(null);//清空所有值
									}
								}
								allMap.put(String.valueOf(eqpId), changeBeans);//更新 最后一次的所有值
								break;//跳出循环
								//自动换班逻辑 end======================================================================================
							}
						}
					}
					//数据处理 begin===================================================================================================
					String startPv = shift.getPmvStartMsg();//工单开始数据
					if(startPv.length()>2){//当前主数据 值
						try{
							String startType = shift.getWorkType();//机台型号 可以获取是包装机数据 还是  卷烟机数据
							int beginIndex = startPv.indexOf("[{");
							int endIndex = startPv.lastIndexOf("}]");
							String startArray= startPv.substring(beginIndex, (endIndex+2));
							//不同机器的  所有值
							CommonData[] startBean = (CommonData[]) JSONUtil.JSONString2ObjectArray(startArray,CommonData.class);
							
							if(null!=startBean&&startBean.length>0){
								int passCount = 0;
								for(int i=0;i<startBean.length;i++){
									CommonData startData = startBean[i];//一行对象集合
									if(EquipmentTypeDefinition.getRoller().contains(startType)){//当前值 卷烟机
										//修改主数据中的 产量
										eqpData = this.modifyZsjData(eqpData,"7",startData,i,shift);//修改	 	产量
										if(eqpData.isPass()){
											passCount= passCount+1;
										}
										eqpData = this.modifyZsjData(eqpData,"21",startData,i,shift);//修改 	盘纸 	 辅料产量
										if(eqpData.isPass()){
											passCount= passCount+1;
										}
										eqpData = this.modifyZsjData(eqpData,"22",startData,i,shift);//修改	水松纸  辅料产量
										if(eqpData.isPass()){
											passCount= passCount+1;
										}
										eqpData = this.modifyZsjData(eqpData,"8",startData,i,shift);//修改 	滤棒	 辅料产量
										if(eqpData.isPass()){
											passCount= passCount+1;
										}
										if(passCount>=4){//全部找到并修改 产量、辅料
											break;	
										}
									}else if(EquipmentTypeDefinition.getPacker().contains(startType)){//当前值  包装机
										//因为辅料和主数据 是 分 2次发送 ,所以辅料要到全局里面找 
										eqpData = this.modifyZsjData(eqpData,"3",startData,i,shift);//修改	 	良好烟包产量 这个在主数据中可以查询到
										if(eqpData.isPass()){//全部找到并修改 良好烟包产量  
											break;	
										}
									}else if(EquipmentTypeDefinition.getFilter().contains(startType)){//当前值  成型机
										
									}
								}
							}
						}catch(Exception e){
							equLog.error(" editZjsEquipmentData is zhushuju error :"+e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			equLog.error(" editZjsEquipmentData is error :"+e.getMessage());
		}
		return eqpData;
	}
	
	
	/**
	 * 修改辅料 数据
	 * @param eqpData	原始值
	 * @param typeId	对象主键ID
	 * @param startData	数据库保存的开始值
	 * @param i			当前第 i 行
	 * @param shift		当前班次信息
	 * @return
	 */
	private EquipmentData modifyFlData(EquipmentData eqpData,String typeId,CommonData startData, int i,MdManualShift shift){
		eqpData.setPass(false);
		if(typeId.equals(startData.getId())){//匹配到了
			eqpData.setPass(true);//只要进来了 就可以 pass了
			if(null==startData.getVal()||"".equals(startData.getVal())){
				startData.setVal("0");
			}
			double startCl = Double.parseDouble(startData.getVal());//当前产量值
			if(startCl==0){
				double pvInt =   0;//当前值
				double pmv1Int = 0;//第一次自动换班值
				//PMV1
				String pmv1Str=shift.getPmv1FlMsg();//.getPmv1Msg();//第一次自动换班点
				int begin1 = pmv1Str.indexOf("[{");
				int end1 = pmv1Str.lastIndexOf("}]");
				CommonData pmv1Data = null;
				if(null!=pmv1Str&&pmv1Str.length()>2){
					String pmv1Array= pmv1Str.substring(begin1, (end1+2));
					CommonData[] pmv1Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv1Array,CommonData.class);
					if(null!=pmv1Bean[i]){
						pmv1Data = pmv1Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv1Data&&typeId.equals(pmv1Data.getId())){
							if(null!=pmv1Data.getVal()&&!"".equals(pmv1Data.getVal())){
								pmv1Int = Double.parseDouble(pmv1Data.getVal());
							}
						}else{//重新找
							if(null!=pmv1Bean){
								for(CommonData bean:pmv1Bean){
									if(typeId.equals(bean.getId())){
										pmv1Int = Double.parseDouble(bean.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PV
				if(null!=eqpData.getAllData().get(i)){
					if(typeId.equals(eqpData.getAllData().get(i).getId())){//结构理论是一致的 ,除非DAC发送的数据不全
						String temp=eqpData.getAllData().get(i).getVal();
						if(StringUtil.notNull(temp)){
							pvInt = Double.parseDouble(temp);
						}
						//计算产量
						double cv = pvInt+pmv1Int;
						eqpData.getAllData().get(i).setCv(pvInt+"");//当前产量
						eqpData.getAllData().get(i).setVal(cv+"");
						//end
					}
				}else{//重新找
					for(CommonData bean:eqpData.getData()){
						if(typeId.equals(bean.getId())){
							pvInt = Double.parseDouble(bean.getVal());
							//计算产量
							double cv = pvInt+pmv1Int;
							bean.setVal(cv+"");
							bean.setCv(bean.getVal());//当时产量
							//end
							break;
						}
					}
				}
			}else{//托班 或 跨班
				double pvInt =   0;//当前值
				double pmv0Int = 0;//上一班手动换班产量
				double pmv1Int = 0;//第一次自动换班值
				double pmv2Int = 0;//第二次自动换班值
				//PMV
				String pmv0Str=shift.getPmvFlMsg();//.getPmvMsg();//上一次换班数据
				int begin0 = pmv0Str.indexOf("[{");
				int end0 = pmv0Str.lastIndexOf("}]");
				CommonData pmv0Data = null;
				if(null!=pmv0Str&&pmv0Str.length()>2){
					String pmv0Array= pmv0Str.substring(begin0, (end0+2));
					CommonData[] pmv0Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv0Array,CommonData.class);
					if(null!=pmv0Bean[i]){
						pmv0Data = pmv0Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv0Data&&typeId.equals(pmv0Data.getId())){
							if(null!=pmv0Data.getVal()&&!"".equals(pmv0Data.getVal())){
								pmv0Int = Double.parseDouble(pmv0Data.getVal());
							}
						}else{//重新找
							if(null!=pmv0Bean){
								for(CommonData bean0:pmv0Bean){
									if(typeId.equals(bean0.getId())){
										pmv0Int = Double.parseDouble(bean0.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PMV1
				String pmv1Str=shift.getPmv1FlMsg();//.getPmv1Msg();//第一次自动换班点
				int begin1 = pmv1Str.indexOf("[{");
				int end1 = pmv1Str.lastIndexOf("}]");
				CommonData pmv1Data = null;
				if(null!=pmv1Str&&pmv1Str.length()>2){
					String pmv1Array= pmv1Str.substring(begin1, (end1+2));
					CommonData[] pmv1Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv1Array,CommonData.class);
					if(null!=pmv1Bean[i]){
						pmv1Data = pmv1Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv1Data&&typeId.equals(pmv1Data.getId())){
							if(null!=pmv1Data.getVal()&&!"".equals(pmv1Data.getVal())){
								pmv1Int = Double.parseDouble(pmv1Data.getVal());
							}
						}else{//重新找
							if(null!=pmv1Bean){
								for(CommonData bean:pmv1Bean){
									if(typeId.equals(bean.getId())){
										pmv1Int = Double.parseDouble(bean.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PMV2
				String pmv2Str=shift.getPmv2FlMsg();//.getPmv2Msg();//第二次自动换班点
				int begin2 = pmv2Str.indexOf("[{");
				int end2 = pmv2Str.lastIndexOf("}]");
				CommonData pmv2Data = null;
				if(null!=pmv2Str&&pmv2Str.length()>2){
					String pmv2Array= pmv2Str.substring(begin2, (end2+2));
					CommonData[] pmv2Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv2Array,CommonData.class);
					if(null!=pmv2Bean[i]){
						pmv2Data = pmv2Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv2Data&&typeId.equals(pmv2Data.getId())){
							if(null!=pmv2Data.getVal()&&!"".equals(pmv2Data.getVal())){
								pmv2Int = Double.parseDouble(pmv2Data.getVal());
							}
						}else{//重新找
							if(null!=pmv2Bean){
								for(CommonData bean:pmv2Bean){
									if(typeId.equals(bean.getId())){
										pmv2Int = Double.parseDouble(bean.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PV
				if(null!=eqpData.getAllData().get(i)){
					if(typeId.equals(eqpData.getAllData().get(i).getId())){//结构理论是一致的 ,除非DAC发送的数据不全
						String temp=eqpData.getAllData().get(i).getVal();
						if(StringUtil.notNull(temp)){
							pvInt = Double.parseDouble(temp);
						}
						//计算产量 
						//PLC当前值-上一班手动换班产量+自动换班1的值+自动换班2的值
						double cv = (pvInt-pmv0Int)+pmv1Int+pmv2Int;
						if(cv<=0){//自动换班 出现了 问题,数据保存出现了问题,DAC出现了问题
							cv = pvInt;
						}
						eqpData.getAllData().get(i).setVal(cv+"");
						eqpData.getAllData().get(i).setCv(pvInt+"");
						//end
					}
				}else{//重新找
					for(CommonData bean:eqpData.getData()){
						if(typeId.equals(bean.getId())){
							pvInt = Double.parseDouble(bean.getVal());
							//计算产量 
							//当前值-上一班手动换班产量+换班1的值+换班2的值
							double cv = (pvInt-pmv0Int)+pmv1Int+pmv2Int;
							if(cv<=0){//自动换班 出现了 问题,数据保存出现了问题,DAC出现了问题
								cv = pvInt;
							}
							eqpData.getAllData().get(i).setVal(cv+"");
							eqpData.getAllData().get(i).setCv(pvInt+"");
							//end
							break;
						}
					}
				}
			}
			//break;//跳出循环  这里注销掉，产量修改好了 还有辅料没有修改 所以 注销掉 ,modify by luther.zhang 20150713
		}
		return  eqpData;
	}
	
	/**
	 * 修改主数据 中 的 产量
	 * @param eqpData	原始值
	 * @param typeId	对象主键ID
	 * @param startData	数据库保存的开始值
	 * @param i			当前第 i 行
	 * @param shift		当前班次信息
	 * @return
	 */
	private EquipmentData modifyZsjData(EquipmentData eqpData,String typeId,CommonData startData, int i,MdManualShift shift){
		eqpData.setPass(false);
		if(typeId.equals(startData.getId())){//表示 产量
			eqpData.setPass(true);//只要进来了 就可以 pass了
			if(null==startData.getVal()||"".equals(startData.getVal())){
				startData.setVal("0");
			}
			double startCl = Double.parseDouble(startData.getVal());//当前产量值
			if(startCl==0){
				double pvInt =   0;//当前值
				double pmv1Int = 0;//第一次自动换班值
				//PMV1
				String pmv1Str=shift.getPmv1Msg();//第一次自动换班点
				int begin1 = pmv1Str.indexOf("[{");
				int end1 = pmv1Str.lastIndexOf("}]");
				CommonData pmv1Data = null;
				if(null!=pmv1Str&&pmv1Str.length()>2){
					String pmv1Array= pmv1Str.substring(begin1, (end1+2));
					CommonData[] pmv1Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv1Array,CommonData.class);
					if(null!=pmv1Bean[i]){
						pmv1Data = pmv1Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv1Data&&typeId.equals(pmv1Data.getId())){
							if(null!=pmv1Data.getVal()&&!"".equals(pmv1Data.getVal())){
								pmv1Int = Double.parseDouble(pmv1Data.getVal());
							}
						}else{//重新找
							if(null!=pmv1Bean){
								for(CommonData bean:pmv1Bean){
									if(typeId.equals(bean.getId())){
										pmv1Int = Double.parseDouble(bean.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PV
				if(null!=eqpData.getAllData().get(i)){
					if(typeId.equals(eqpData.getAllData().get(i).getId())){//结构理论是一致的 ,除非DAC发送的数据不全
						String temp=eqpData.getAllData().get(i).getVal();
						if(StringUtil.notNull(temp)){
							pvInt = Double.parseDouble(temp);
						}
						//计算产量
						double cv = pvInt+pmv1Int;
						eqpData.getAllData().get(i).setCv(pvInt+"");//当前产量
						eqpData.getAllData().get(i).setVal(cv+"");
						//end
					}
				}else{//重新找
					for(CommonData bean:eqpData.getData()){
						if(typeId.equals(bean.getId())){
							pvInt = Double.parseDouble(bean.getVal());
							//计算产量
							double cv = pvInt+pmv1Int;
							bean.setVal(cv+"");
							bean.setCv(bean.getVal());//当时产量
							//end
							break;
						}
					}
				}
			}else{//托班 或 跨班
				double pvInt =   0;//当前值
				double pmv0Int = 0;//上一班手动换班产量
				double pmv1Int = 0;//第一次自动换班值
				double pmv2Int = 0;//第二次自动换班值
				//PMV
				String pmv0Str=shift.getPmvMsg();//上一次换班数据
				int begin0 = pmv0Str.indexOf("[{");
				int end0 = pmv0Str.lastIndexOf("}]");
				CommonData pmv0Data = null;
				if(null!=pmv0Str&&pmv0Str.length()>2){
					String pmv0Array= pmv0Str.substring(begin0, (end0+2));
					CommonData[] pmv0Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv0Array,CommonData.class);
					if(null!=pmv0Bean[i]){
						pmv0Data = pmv0Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv0Data&&typeId.equals(pmv0Data.getId())){
							if(null!=pmv0Data.getVal()&&!"".equals(pmv0Data.getVal())){
								pmv0Int = Double.parseDouble(pmv0Data.getVal());
							}
						}else{//重新找
							if(null!=pmv0Bean){
								for(CommonData bean0:pmv0Bean){
									if(typeId.equals(bean0.getId())){
										pmv0Int = Double.parseDouble(bean0.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PMV1
				String pmv1Str=shift.getPmv1Msg();//第一次自动换班点
				int begin1 = pmv1Str.indexOf("[{");
				int end1 = pmv1Str.lastIndexOf("}]");
				CommonData pmv1Data = null;
				if(null!=pmv1Str&&pmv1Str.length()>2){
					String pmv1Array= pmv1Str.substring(begin1, (end1+2));
					CommonData[] pmv1Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv1Array,CommonData.class);
					if(null!=pmv1Bean[i]){
						pmv1Data = pmv1Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv1Data&&typeId.equals(pmv1Data.getId())){
							if(null!=pmv1Data.getVal()&&!"".equals(pmv1Data.getVal())){
								pmv1Int = Double.parseDouble(pmv1Data.getVal());
							}
						}else{//重新找
							if(null!=pmv1Bean){
								for(CommonData bean:pmv1Bean){
									if(typeId.equals(bean.getId())){
										pmv1Int = Double.parseDouble(bean.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PMV2
				String pmv2Str=shift.getPmv2Msg();//第二次自动换班点
				int begin2 = pmv2Str.indexOf("[{");
				int end2 = pmv2Str.lastIndexOf("}]");
				CommonData pmv2Data = null;
				if(null!=pmv2Str&&pmv2Str.length()>2){
					String pmv2Array= pmv2Str.substring(begin2, (end2+2));
					CommonData[] pmv2Bean = (CommonData[]) JSONUtil.JSONString2ObjectArray(pmv2Array,CommonData.class);
					if(null!=pmv2Bean[i]){
						pmv2Data = pmv2Bean[i];//因为结构一致 理论在 第3条;有可能在第4条
						if(null!=pmv2Data&&typeId.equals(pmv2Data.getId())){
							if(null!=pmv2Data.getVal()&&!"".equals(pmv2Data.getVal())){
								pmv2Int = Double.parseDouble(pmv2Data.getVal());
							}
						}else{//重新找
							if(null!=pmv2Bean){
								for(CommonData bean:pmv2Bean){
									if(typeId.equals(bean.getId())){
										pmv2Int = Double.parseDouble(bean.getVal());
										break;
									}
								}
							}
						}
					}
				}
				//PV
				if(null!=eqpData.getAllData().get(i)){
					if(typeId.equals(eqpData.getAllData().get(i).getId())){//结构理论是一致的 ,除非DAC发送的数据不全
						String temp=eqpData.getAllData().get(i).getVal();
						if(StringUtil.notNull(temp)){
							pvInt = Double.parseDouble(temp);
						}
						//计算产量 
						//PLC当前值-上一班手动换班产量+自动换班1的值+自动换班2的值
						double cv = (pvInt-pmv0Int)+pmv1Int+pmv2Int;
						if(cv<=0){//自动换班 出现了 问题,数据保存出现了问题,DAC出现了问题
							cv = pvInt;
						}
						eqpData.getAllData().get(i).setVal(cv+"");
						eqpData.getAllData().get(i).setCv(pvInt+"");
						//end
					}
				}else{//重新找
					for(CommonData bean:eqpData.getData()){
						if(typeId.equals(bean.getId())){
							pvInt = Double.parseDouble(bean.getVal());
							//计算产量 
							//当前值-上一班手动换班产量+换班1的值+换班2的值
							double cv = (pvInt-pmv0Int)+pmv1Int+pmv2Int;
							if(cv<=0){//自动换班 出现了 问题,数据保存出现了问题,DAC出现了问题
								cv = pvInt;
							}
							eqpData.getAllData().get(i).setVal(cv+"");
							eqpData.getAllData().get(i).setCv(pvInt+"");
							//end
							break;
						}
					}
				}
			}
			//break;//跳出循环  这里注销掉，产量修改好了 还有辅料没有修改 所以 注销掉 ,modify by luther.zhang 20150713
		}
		return  eqpData;
	}
	
}
