package com.lanbao.dws.common.data.dac.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lanbao.dac.data.CommonData;
import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.dac.EquipmentData;

/**
 * DataSnapshot->NeedData 数据拷贝线程
 * @author wanchanghuang
 * @create 2016年7月21日15:51:55
 */
public class CopyThread extends Thread{
	protected Logger log = Logger.getLogger(this.getClass());
	@Override
	public void run(){
		int code=0;
		try{
			//实例化业务电表数据
			Map<String,String> dataPonits = NeedDataPonits.getDataPonits();
			//获取所有轮询后的卷包数据
			List<EquipmentData> equipmentDatas = DataSnapshot.getInstance().getEqpData();
			//遍历每一台设备数据（设备信息，设备Data）
			for(EquipmentData data : equipmentDatas){
				String type="";
				try {
					type = data.getType().toUpperCase();
				} catch (Exception e) {
					// TODO: handle exception
				}
				//PMS关注数据点
				if(dataPonits.containsKey(type)){//通过点表过滤需要的信息
					//设备code
					code=data.getEqp();
					//基础数据
					EquipmentData equipmentData = new EquipmentData(data.getEqp(), data.getShift(), data.isOnline(),type);
					//设备数据
					List<CommonData> commonDatas = data.getAllData();
					//点位集合
					List<String> points = StringUtil.splitToStringList(dataPonits.get(type), ",");
					//拷贝数据 
					/**
					 *  equipmentData-已经装配好的设备基本信息  
					 *  commonDatas-设备下的数据集合 
					 *  points-设备点位   type-类型 
					 */
					copyDatas(equipmentData,commonDatas,points,type);
					//装配到NeedData
					NeedData.getInstance().setEquipmentData(equipmentData);	
                }
			}
		}catch(Exception e){
			log.error(code+":::CopyThread is error:"+e.getMessage());
		}
	}
	
	
	DataHandler hand = null;
	/**
	 * 
	 * @author Leejean
	 * @create 2015年1月7日下午3:38:26
	 * @param equipmentData 当前机台数据
	 * @param commonDatas 源datas
	 * @param points 允许的数据点
	 * @param isFl 是否为辅料数据
	 * equipmentData-已经装配好的设备基本信息   commonDatas-设备下的数据集合 points-设备点位   type-类型
	 */
	private void copyDatas(EquipmentData equipmentData,List<CommonData> commonDatas,List<String> points,String type) {
		Map<String, Object> calcValues = DataHandler.getCalcValues();
		List<CommonData> needsCommonDatas =  new ArrayList<CommonData>();
		for (CommonData commonData : commonDatas) {
			String id = commonData.getId();
			//当前数据点在needDataPoints.properties有设置
			if(points.contains(id)){
				if(type.contains("_FL")){ 
				  //所有设备辅料处理
				  commonDesposeFLDatas(needsCommonDatas, commonData,  type, equipmentData,calcValues);
				  continue;
				}
				//卷烟机数据处理
				else if(equipmentData.getEqp()<31){
					//转化辅料数据格式不是_FL类型的数据（现在处理了卷烟机辅料数据，辅料在主数据里面）
					exchangeOtherEqpFL(needsCommonDatas,equipmentData, calcValues, commonData, id);
				}else {
					//其他不需要修改的数据
					needsCommonDatas.add(commonData);
				}
			}		
		}
		equipmentData.setData(needsCommonDatas);
	}


	/**
	 * 转化辅料数据格式不是_FL类型的数据（现在处理了卷烟机辅料数据，辅料在主数据里面）
	 * @param equipmentData
	 * @param calcValues
	 * @param commonData
	 * @param id
	 */
	private void exchangeOtherEqpFL(List<CommonData> needsCommonDatas,EquipmentData equipmentData, Map<String, Object> calcValues, CommonData commonData,
			String id) {
			Object object= calcValues.get(String.valueOf(equipmentData.getEqp()));
			if(object!=null){
				CommonData newCommonData =  new CommonData();
				RollerCalcValue calcValue = (RollerCalcValue) object;
				newCommonData.setId(commonData.getId());
				newCommonData.setDes(commonData.getDes());
				Double oldVal=0.0;
				if(StringUtil.notNull(commonData.getVal())){
					oldVal=Double.valueOf(commonData.getVal());
				}
				//处理【卷烟纸】
				if(id.equals("21")){
					//米=卷烟纸滚轴系数(周长)*脉冲数
					Double newVal =calcValue.getJuanyanzhiAxisValue() *oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}
				//处理【水松纸】
				else if(id.equals("22")){
					//水松纸辅料系数=水松纸宽度(0.06m)*克重(1.2g/㎡)
					//千克=水松纸滚轴系数*水松纸辅料系数*脉冲数
					Double newVal =calcValue.getShuisongzhiAxisValue()*calcValue.getShuisongzhiValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}else{
					newCommonData.setVal(commonData.getVal());
					newCommonData.setVariable(commonData.getVal());
				}
				needsCommonDatas.add(newCommonData);
			}
	}
	

	
	/**
	 * 【功能说明】：辅料公共处理方法-控制流转
	 * @createTime 2015年10月23日11:21:19
	 * @author   wanchanghuang 
	 * needsCommonDatas -空   
	 * commonDatas - 原设备下数据集合
	 * calcValues -map
	 * equipmentData -封装已知设备基础信息：班次，班组，转速等
	 * */
	private void commonDesposeFLDatas(List<CommonData> needsCommonDatas,CommonData commonDatas,
			String type,EquipmentData equipmentData,Map<String, Object> calcValues){
			//得到设备型号
			String eqpType = type.substring(0, type.indexOf("_"));
//			if(EquipmentTypeDefinition.getRoller().contains(eqpType) ){ //卷烟机
//				calcRollerData(needsCommonDatas,commonDatas,equipmentData.getEqp(),calcValues);
//			}
			if(EquipmentTypeDefinition.getPacker().contains(eqpType) ){ //包装机
				calcPackerData(needsCommonDatas,commonDatas,equipmentData.getEqp(),calcValues);				
			}
			if(EquipmentTypeDefinition.getFilter().contains(eqpType) ){ //成型机
				calcFilterData(needsCommonDatas,equipmentData.getEqp(),commonDatas,calcValues);
			}	
	}
	
	
	
	/** 修改后的辅料数据 更新-未被修改的辅料数据 */
	private void addCommonData(List<CommonData> needsCommonDatas,
			CommonData commonData) {
		CommonData isExsitData=null;
		for (CommonData data : needsCommonDatas) {
			if(data.getId().equals(commonData.getId())){
				data.setVal(commonData.getVal());
				isExsitData = data;
				break;
			}
		}
		if(isExsitData==null){
			needsCommonDatas.add(commonData);
		}
	}
	
	
	
	
	
	/**
	 * 计算卷烟机辅料系数
	 * @author Leejean
	 * @create 2015年1月9日上午8:45:22
	 * @param commonData
	 * @param calcValues <类型，List<系数对象> >
	 */
//	private void calcRollerData(List<CommonData> needsCommonDatas,List<CommonData> commonDatas,Integer eqp,
//			Map<String, Object> calcValues) {
//		
//		/*1
//		2	盘纸
//		3	水松纸
//		4         滤棒计数[滤棒计数取自[设备id/1000]上的ID=8，需要构造]*/
//		
//		Object object= calcValues.get(String.valueOf(eqp));
//		if(object!=null){
//		RollerCalcValue calcValue = (RollerCalcValue) object;
//		for (CommonData commonData : commonDatas) {
//			Double oldVal=0.0;
//			if(StringUtil.notNull(commonData.getVal())){
//				oldVal=Double.valueOf(commonData.getVal());
//			}
//			//处理【卷烟纸】
//			if(commonData.getId().equals("21")){
//				//米=卷烟纸滚轴系数(周长)*脉冲数
//				Double newVal =calcValue.getJuanyanzhiAxisValue() *oldVal;
//				commonData.setVariable(commonData.getVal());
//				commonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
//			}
//			//处理【水松纸】
//			if(commonData.getId().equals("22")){
//				//水松纸辅料系数=水松纸宽度(0.06m)*克重(1.2g/㎡)
//				//千克=水松纸滚轴系数*水松纸辅料系数*脉冲数
//				Double newVal =calcValue.getShuisongzhiAxisValue()*calcValue.getShuisongzhiValue() *oldVal;
//				commonData.setVariable(commonData.getVal());
//				commonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
//			}
//		}
//		//处理【滤棒】
//		//如五号卷烟机的主机数据的在eqp=5上面，则辅料在eqp=5000上
//		//得到所有主机数据，需要找id=8的滤棒数据
//		List<CommonData> rollerData = DataSnapshot.getInstance().getEquipmentData(eqp/1000).getAllData();
//		String lvbangData = "0";
//		if(rollerData!=null){
//			lvbangData = this.getLvbangData(rollerData);
//		}
//		CommonData newCommonData = new CommonData("4", "滤棒计数", "", "", lvbangData, "double");
//		addCommonData(needsCommonDatas, newCommonData);
//		}
//	}
	
	
//	private String getLvbangData(List<CommonData> rollerData){
//		for (CommonData commonData : rollerData) {
//			if(commonData.getId().equals("8")){
//				return commonData.getVal();
//			}
//		}
//		return "0";
//	}
	/**
	 * 计算包装机辅料系数
	 * @author Leejean
	 * @create 2015年1月9日上午8:45:22
	 * @param commonData
	 * @param calcValues
	 */
	private void calcPackerData(List<CommonData> needsCommonDatas,CommonData commonData,Integer eqp,
			Map<String, Object> calcValues) {
		/*
		1	铝箔纸
		2   NULL
		3	商标纸(小盒纸)
		4	小透纸
		5	条盒纸
		6          大透纸[val取自条盒纸计数，需要构造ID：6]*/
		
		Object object= calcValues.get(String.valueOf(eqp/1000));
		if(object!=null){	
			    PackerCalcValue calcValue = (PackerCalcValue) object;
				CommonData newCommonData =  new CommonData();
				newCommonData.setId(commonData.getId());
				newCommonData.setDes(commonData.getDes());
				newCommonData.setVal("0");//赋值0未初始值
				Double oldVal=0.0;
				if(StringUtil.notNull(commonData.getVal())){
					oldVal=Double.valueOf(commonData.getVal());
				}
				//处理【铝箔纸（内衬纸）】
				if(commonData.getId().equals("1")){
					//内衬纸辅料系数（面积*克重）
					//千克=张*内衬纸辅料系数
					Double newVal = calcValue.getNeichenzhiValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}
				//处理【商标纸(小盒纸)】 直接使用
				else if(commonData.getId().equals("2")){
					Double newVal = oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 0).toString());
				}
				//处理【小透纸（小盒膜）】
				else if(commonData.getId().equals("3")){
					//千克=张*小盒膜辅料系数
					Double newVal = calcValue.getXiaohemoValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}
			
				//处理【条盒纸】
				else if(commonData.getId().equals("4")){
					Double newVal = oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 0).toString());
					//处理【大透纸(条盒烟膜)
					for(CommonData cd: needsCommonDatas){
						if("5".equals(cd.getId())){
							Double tiaohemoNewVal = calcValue.getTiaohemoValue()*newVal;
							cd.setVariable(newVal.toString());
							cd.setVal(MathUtil.roundHalfUp(tiaohemoNewVal, 3).toString());
							addCommonData(needsCommonDatas, cd);
						}
					}
			}
				addCommonData(needsCommonDatas, newCommonData);
		}//object
		
	}
	
	/**
	 * 计算成型机辅料系数
	 * @author Leejean
	 * @create 2015年1月9日上午8:46:12
	 * @param commonData
	 * @param calcValues
	 * 
	 */
	private void calcFilterData(List<CommonData> needsCommonDatas,Integer eqp,CommonData commonData,
			Map<String, Object> calcValues) {
		//1：滤棒盘纸
		Object object= calcValues.get(String.valueOf(eqp/1000));
		if(object!=null){	
			FilterCalcValue calcValue = (FilterCalcValue) object;
				//处理【滤棒盘纸】
				if(commonData.getId().equals("1")){
					CommonData newCommonData =  new CommonData();
					newCommonData.setVal("0");//赋值0未初始值
					Double oldVal=0.0;
					if(StringUtil.notNull(commonData.getVal())){
						oldVal = Double.valueOf(commonData.getVal());
					}
					//Double oldVal = Double.valueOf(commonData.getVal());
					//米=盘纸滚轴系数*脉冲数
					Double newVal = calcValue.getPanzhiAxisValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(newVal.toString());
					addCommonData(needsCommonDatas, newCommonData);
				}
		}
		
	}
}
