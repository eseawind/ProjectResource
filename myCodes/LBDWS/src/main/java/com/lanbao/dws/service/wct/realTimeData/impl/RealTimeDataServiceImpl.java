package com.lanbao.dws.service.wct.realTimeData.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.support.PaginationDalClient;
import com.ibm.framework.dal.transaction.template.CallBackTemplate;
import com.lanbao.dac.data.CommonData;
import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.common.data.dac.handler.DataHandler;
import com.lanbao.dws.common.data.dac.handler.DataSnapshot;
import com.lanbao.dws.common.data.dac.handler.DbOutputOrInputInfos;
import com.lanbao.dws.common.data.dac.handler.EquipmentTypeDefinition;
import com.lanbao.dws.common.data.dac.handler.NeedData;
import com.lanbao.dws.common.data.webservices.client.SendMessageToJyscClient;
import com.lanbao.dws.common.init.BaseParams;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.GetValueUtil;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.combobox.MdFixCode;
import com.lanbao.dws.model.wct.dac.BoxerData;
import com.lanbao.dws.model.wct.dac.EquipmentData;
import com.lanbao.dws.model.wct.dac.FilterData;
import com.lanbao.dws.model.wct.dac.InputBean;
import com.lanbao.dws.model.wct.dac.LauncherData;
import com.lanbao.dws.model.wct.dac.OutputBean;
import com.lanbao.dws.model.wct.dac.PackerData;
import com.lanbao.dws.model.wct.dac.RollerData;
import com.lanbao.dws.model.wct.dac.RollerPackerData;
import com.lanbao.dws.model.wct.eqpManager.SchStatFaultBean;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.pdStat.EqpRuntime;
import com.lanbao.dws.model.wct.pdStat.InputPageModel;
import com.lanbao.dws.model.wct.pddisplay.WorkOrderBean;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService;
import com.lanbao.dws.common.data.dac.handler.FaultDataCalc;

@Service
public class RealTimeDataServiceImpl implements IRealTimeDataService{
	@Autowired
	PaginationDalClient dalClient;
	@Autowired
	InitComboboxData initService;
	private static Logger log = Logger.getLogger(RealTimeDataServiceImpl.class);
	
	//创建高频次保存数据集合
	//卷烟机
	private static List<HashMap<String, Object>> rollerDatas=new ArrayList<>();
	//包装机
	private static List<HashMap<String, Object>> packerDatas=new ArrayList<>();
	//风箱机
	private static List<HashMap<String, Object>> boxerDatas=new ArrayList<>();
	//发射机
	private static List<HashMap<String, Object>> launcherDatas=new ArrayList<>();
	//成型机
	private static List<HashMap<String, Object>> filterDatas=new ArrayList<>();
	//储烟机
	private static List<HashMap<String, Object>> containerDatas=new ArrayList<>();
	//提升机
	private static List<HashMap<String, Object>> conveyorerDatas=new ArrayList<>();
	//外围设备
	private static List<HashMap<String, Object>> outerEqpDatas=new ArrayList<>();
	//批量保存数据条数
	private static int count=20;
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月25日 上午9:45:07 
	* 功能说明 ：获取卷烟机实时数据
	 */
	@Override
	public RollerData getRollerData(String equipmentCode) {
		int id = Integer.valueOf(equipmentCode);
		
		EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
		//嘴棒接收机
		EquipmentData filterReData = NeedData.getInstance().getEquipmentData(id+200);
		
		//储烟设备
		EquipmentData cyData = NeedData.getInstance().getEquipmentData(id+70);
		Double qty = 0D; 
		//剔除量
		double badQty=0D;
		
		Double runTime = 0D; 
		Double stopTime = 0D;
		Integer stopTimes = 0; 
		Integer speed = 0;
		Integer online = 0;
		//消耗
		Double lvbangVal = 0D; 
		Double juanyanzhiVal = 0D;
		Double shuisongzhiVal = 0D; 
		//包装机信息
		Integer cy_cs=0; //包装机车速
		String cy_online="false";//包装机运行状态
		//
		Double cy=0D;
		int filterReNum=0;//嘴棒接收机数量
		if(mainData!=null){
			List<CommonData> datas = mainData.getAllData();
			//网络
			online = mainData.isOnline()?1:0;
			//车速
			speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "15"));
			//产量
			qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7"))/50,2);
			//剔除
			badQty= MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "11"))/50000,4);
			//运行时间
			runTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "16")),0);;
			//停机时间
			try {
				stopTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "18")),0);;
			} catch (Exception e) {
				e.printStackTrace();
				//打印日志
				System.out.println("\n\n\n停机时间异常！>>>>"+NeedData.getInstance().getDataValue(datas, "18")+">>>"+mainData.getEqp()+">>>"+mainData.getName()+">>>"+mainData.getType()+">>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
			
			//停机次数
			stopTimes = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "19"));
			
			//辅料取值
			lvbangVal =  MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8"))/10000,4);
			juanyanzhiVal =  GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "21"));
			shuisongzhiVal =   GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "22"));
		}
		//储烟机
		if(cyData!=null){
			List<CommonData> datas = cyData.getAllData();
			cy =MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8"))/100, 2);
			cy_cs =GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "1"));//包装机车速
			cy_online=NeedData.getInstance().getDataValue(datas, "22");//包装机运行状态
		}
		//嘴棒接收机
		if(filterReData!=null){
			List<CommonData> datas = filterReData.getAllData();
			int td1=GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "16"));
			int td2=GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "17"));
			filterReNum=(td1+td2)/10000;
			lvbangVal+=filterReNum;
		}
		return new RollerData(qty,badQty, lvbangVal, juanyanzhiVal,
				shuisongzhiVal, runTime, stopTime,
				stopTimes, speed,online,cy,cy_cs,cy_online);
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月25日 上午9:47:47 
	* 功能说明 ：获取包装机实时数据
	 */
	@Override
	public PackerData getPackerData(String equipmentCode) {
		int id = Integer.valueOf(equipmentCode);
		
		EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
		EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);
		//提升级数据
		EquipmentData TSData = NeedData.getInstance().getEquipmentData(151);
		//网络
		Integer online = 0;
		//停机次数
		Integer stopTimes = 0;
		//产量
		Double qty = 0D;
		//剔除量
		double badQty=0D;
		//车速
		Integer speed = 0; 
		//运行时间
		Double runTime =0D; 
		//停机时间
		Double stopTime = 0D;
		//消耗数据(小盒膜，条盒膜，小盒纸，条盒纸，内衬纸)
		Double xiaohemoVal = 0D;
		Double tiaohemoVal = 0D;
		Double xiaohezhiVal = 0D; 
		Double tiaohezhiVal = 0D;
		Double neichenzhiVal = 0D; 
		int tsQty=0;//提升机产量
		if(mainData!=null){
			List<CommonData> datas = mainData.getAllData();
			//网络
			online = mainData.isOnline()?1:0;
			//车速
			speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "2"));
			//产量
			qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3"))/2500,2);
			//剔除量
			badQty=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4"))/2500,2);
			//运行时间
			runTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5"))/60,0);
			//停机时间
			stopTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "6"))/60,0);
			//停机次数
			stopTimes = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "7"));
		}
		if(flData!=null){
			List<CommonData> datas = flData.getAllData();
			neichenzhiVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1"));
			xiaohezhiVal =  GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2"));
			xiaohemoVal =   GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3"));
			tiaohezhiVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4"));
			tiaohemoVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5"));
		}
		//提升机数据id和机台code对应关系
		if(TSData!=null){
			List<CommonData> datas = TSData.getAllData();
			tsQty=GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, ""+(id-6)));
		}
		//当DAC采集的产量为0时，将提升机数据复制给DAC采集量
		if(qty==0){
			qty=MathUtil.roundHalfUp(tsQty/250.0, 2);
		}
		return new PackerData(online, qty,badQty, xiaohemoVal, tiaohemoVal, xiaohezhiVal, tiaohezhiVal, neichenzhiVal, runTime, stopTime, stopTimes, speed,tsQty);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月3日 下午1:55:39 
	* 功能说明 ：获取发射机实时数据
	 */
	@Override
	public LauncherData getLauncherData(String equipmentCode) {
		Double xp1Qty0=0D;
		Double xp1Qty1=0D;
		//发射管发射量
		Double qty0=0D, qty1=0D, qty2=0D, qty3=0D, qty4=0D, qty5=0D, 
			   qty6=0D, qty7=0D, qty8=0D, qty9=0D;
		//发射管发射速度
		Double speed0=0D, speed1=0D, speed2=0D, speed3=0D, speed4=0D, speed5=0D, 
		speed6=0D, speed7=0D, speed8=0D, speed9=0D;
		
		//产量
		Double qty = 0D;
		
		if(StringUtil.isInteger(equipmentCode)){
			int id = Integer.valueOf(equipmentCode);
			EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
			//卸盘机数据
			EquipmentData xpEqp1 = NeedData.getInstance().getEquipmentData(id+50);
			EquipmentData xpEqp2 = NeedData.getInstance().getEquipmentData(id+51);
			if(mainData!=null){
				List<CommonData> datas = mainData.getAllData();
				qty0=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1")),3);//小数
				qty1=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2")),3);//小数
				qty2=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3")),3);//小数
				qty3=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4")),3);//小数
				qty4=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5")),3);//小数
				qty5=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "6")),3);//小数
				qty6=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7")),3);//小数
				qty7=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8")),3);//小数
				qty8=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "9")),3);//小数
				qty9=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "10")),3);//小数
				speed0=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "11")),1);//整数
				speed1=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "12")),1);//整数
				speed2=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "13")),1);//整数
				speed3=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "14")),1);//整数
				speed4=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "15")),1);//整数
				speed5=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "16")),1);//整数
				speed6=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "17")),1);//整数
				speed7=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "18")),1);//整数
				speed8=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "19")),1);//整数
				speed9=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "20")),1);//整数
				//产量(10个管道和)
				qty = qty0+qty1+qty2+qty3+qty4+qty5+qty6+qty7+qty8+qty9;
				}
			if(xpEqp1!=null){
				List<CommonData> datas = xpEqp1.getAllData();
				xp1Qty0=GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "49"));
			}
			if(xpEqp2!=null){
				List<CommonData> datas = xpEqp1.getAllData();
				xp1Qty1=GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "49"));
			}
		}
		return new LauncherData(equipmentCode,
				xp1Qty0, xp1Qty1,
				qty0, qty1, qty2, qty3, qty4, qty5,
				qty6, qty7, qty8, qty9, 
				speed0, speed1, speed2, speed3, speed4, speed5, 
				speed6, speed7, speed8, speed9,qty);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月4日 上午9:03:14 
	* 功能说明 ：获取封箱机实时数据
	 */
	@Override
	public BoxerData getBoxerData(String equipmentCode) {
		double qty=0D;
		int online=0;
		if(StringUtil.isInteger(equipmentCode)){
			int id = Integer.valueOf(equipmentCode);
			EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
			if(mainData!=null){
				List<CommonData> datas = mainData.getAllData();
				//网络
				online = mainData.isOnline()?1:0;
				qty= GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "53"));
				//封箱机的产量是件，5件是1箱
				qty=MathUtil.roundHalfUp(qty/5, 2);
				}
			}
		return new BoxerData(equipmentCode, online, qty);
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月8日 下午3:40:49 
	* 功能说明 ：获取成型机实时数据
	 */
	@Override
	public FilterData getFilterData(String equipmentCode) {
		//网络
		Integer online = 0;
		//停机次数
		Integer stopTimes = 0;
		//产量
		Double qty = 0D;
		//剔除量
		Double badQty=0D;
		//车速
		Integer speed = 0; 
		//运行时间
		Double runTime =0D; 
		//停机时间
		Double stopTime = 0D;
		//消耗数据(盘纸)
		Double pzVal = 0D;
		//装盘机数量
		Double zpVal=0D;
		if(StringUtil.isInteger(equipmentCode)){
			int id = Integer.valueOf(equipmentCode);
			EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
			EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);
			//装盘机数据(计算)161  101
			EquipmentData zpData = NeedData.getInstance().getEquipmentData(id+60);
			if(mainData!=null){
				List<CommonData> datas = mainData.getAllData();
				//网络
				online = mainData.isOnline()?1:0;
				//车速
				speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "11"));
				//产量
				qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1"))/10000,2);
				//剔除量
				badQty=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2"))/10000,2);
				//运行时间
				runTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7")),0);
				//停机时间
				stopTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8")),0);
				//停机次数
				stopTimes = 0;//GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "7"));
			}
			//盘纸数据
			if(flData!=null){
				List<CommonData> datas = flData.getAllData();
				pzVal = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5")),2);
			}
			//装盘机数据
			if(zpData!=null){
				List<CommonData> datas = zpData.getAllData();
				zpVal = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1")),1);
			}
		}
		return new FilterData(equipmentCode, zpVal, online, qty, badQty, pzVal, runTime, stopTime, stopTimes, speed);
	}	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月4日 上午9:12:50 
	* 功能说明 ：初始化封箱机数据
	 */
	@Override
	public String initBoxerMachine(LoginBean loginBean) {
		Map<String,Object> paramsMap=new HashMap<>();
		List<String> codes=new ArrayList<>();
		codes.add(loginBean.getBoxerEquipmentCode0());
		codes.add(loginBean.getBoxerEquipmentCode1());
		paramsMap.put("eqpCodes", codes);
		List<BoxerData> boxerDatas=dalClient.queryForList("login.initBoxerMachineData", paramsMap, BoxerData.class);
		return GsonUtil.bean2Json(boxerDatas);
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月8日 下午10:01:25 
	* 功能说明 ：初始化成型机数据
	 */
	@Override
	public String initFilterMachine(LoginBean loginBean) {
		Map<String,Object> paramsMap=new HashMap<>();
		List<String> codes=new ArrayList<>();
		codes.add(loginBean.getFilterEquipmentCode0());
		codes.add(loginBean.getFilterEquipmentCode1());
		paramsMap.put("eqpCodes", codes);
		paramsMap.put("matType", "13");//滤棒盘纸
		List<FilterData> filterDatas=dalClient.queryForList("login.initFilterData", paramsMap, FilterData.class);
 		return GsonUtil.bean2Json(filterDatas);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月25日 下午4:33:36 
	* 功能说明 ：初始化卷包烟机数据
	 */

	@Override
	public String initRollerMachine(LoginBean loginBean) {
		String rollerEqpCode=loginBean.getRollerEquipmentCode();
		HashMap<String, Object> params=new HashMap<>();
		params.put("eqpCode", rollerEqpCode);
		RollerData rollerData=dalClient.queryForObject("login.initMachineData", params, RollerData.class);
		//查询辅料信息
		if(rollerData!=null){
			String oid=rollerData.getOrderId();
			params.put("oid", oid);
			//水松纸3
			params.put("tid", "3");
			Map<String,Object> result=null;
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			rollerData.setShuisongzhiBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			rollerData.setShuisongzhiEuval(StringUtil.converObj2Double(result.get("euval")));
			//滤棒 4
			params.put("tid", "4");
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			rollerData.setLvbangBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			rollerData.setLvbangEuval(StringUtil.converObj2Double(result.get("euval")));
			//卷烟纸2
			params.put("tid", "2");
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			rollerData.setJuanyanzhiBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			rollerData.setJuanyanzhiEuval(StringUtil.converObj2Double(result.get("euval")));
		}else{
			rollerData=new RollerData();
		}
		return GsonUtil.bean2Json(rollerData);
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月25日 下午4:43:12 
	* 功能说明 ：初始化包装机数据
	 */
	@Override
	public String initPackerMachine(LoginBean loginBean) {
		String packerEqpCode=loginBean.getPackerEquipmentCode(); 
	    HashMap<String, Object> params=new HashMap<>();
		params.put("eqpCode", packerEqpCode);
		PackerData packerData=dalClient.queryForObject("login.initMachineData", params, PackerData.class);
		if(packerData!=null){
			String oid=packerData.getOrderId();
			params.put("oid", oid);
			//5	            小盒烟膜
			params.put("tid", "5");
			Map<String,Object> result=null;
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			packerData.setXiaohemoBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			packerData.setXiaohemoEuval(StringUtil.converObj2Double(result.get("euval")));
			
			//6	            条盒烟膜
			params.put("tid", "6");
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			packerData.setTiaohemoBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			packerData.setTiaohemoEuval(StringUtil.converObj2Double(result.get("euval")));
			
			//7	            小盒纸
			params.put("tid", "7");
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			packerData.setXiaohezhiBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			packerData.setXiaohezhiEuval(StringUtil.converObj2Double(result.get("euval")));
			
			//8	            条盒纸
			params.put("tid", "8");
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			packerData.setTiaohezhiBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			packerData.setTiaohezhiEuval(StringUtil.converObj2Double(result.get("euval")));
			
			//9	            内衬纸
			params.put("tid", "9");
			result=dalClient.queryForMap("login.initFLData", params);
			result=setMap2Zero(result);
			packerData.setNeichenzhiBzdh(StringUtil.converObj2Double(result.get("bzDH")));
			packerData.setNeichenzhiEuval(StringUtil.converObj2Double(result.get("euval")));
		}else{
			packerData=new PackerData();
		}
		return GsonUtil.bean2Json(packerData);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 下午4:16:52 
	* 功能说明 ：将查询结果为空时，赋值为0
	 */
	private Map<String,Object> setMap2Zero(Map<String,Object> result){
		if(result==null){
			result=new HashMap<>();
			result.put("bzDH", 0.0);
			result.put("euval", 0.0);
		}
		return result;
	}

	@Override
	public List<EqpRuntime> getRollerOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getRoller(),
				"roller");
	}

	@Override
	public List<EqpRuntime> getPackerOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getPacker(),
				"packer");
	}

	@Override
	public List<EqpRuntime> getBoxerOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getBoxer(),
				"boxer");
	}

	@Override
	public List<EqpRuntime> getFilterOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getFilter(),
				"filter");
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:38:50 
	* 功能说明 ：获取产量数据
	 */
	private List<EqpRuntime> getOutAndBadQtyData(List<String> equipentTypes,
			String flag) {
		try {
			List<EqpRuntime> list = new ArrayList<EqpRuntime>();
			List<EquipmentData> allData = NeedData.getInstance().getEqpData();

			for (EquipmentData equipmentData : allData) {

				String type = equipmentData.getType().toUpperCase();

				if (type.indexOf("_FL") > 0) {
					continue;
				}// 辅料数据跳过

				List<CommonData> datas = equipmentData.getAllData();

				String eqpCod = String.valueOf(equipmentData.getEqp());

				if (flag.equals("roller") && equipentTypes.contains(type)) {
					String outDataPonit = "7";
					String badQtyDataPonit = "11";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				} else if (flag.equals("packer") && equipentTypes.contains(type)) {
					String outDataPonit = "3";
					String badQtyDataPonit = "4";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				} else if (flag.equals("boxer") && equipentTypes.contains(type)) {
					String outDataPonit = "53";
					String badQtyDataPonit = "-1";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				} else if (flag.equals("filter") && equipentTypes.contains(type)) {
					String outDataPonit = "1";
					String badQtyDataPonit = "2";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				}

			}

			return list;
		} catch (Exception e) {
			System.out.println("获取辅料错误>>>>");
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:39:42 
	* 功能说明 ：实时产量数据处理
	 */
	private void getCurOutAndBadQtyData(List<EqpRuntime> list,
			List<CommonData> datas, String eqpCod, String outDataPonit,
			String badQtyDataPonit) {
		Double curOut = GetValueUtil.getDoubleValue(NeedData.getInstance()
				.getDataValue(datas, outDataPonit));
		// 千字换成箱
		Double badQty = 0d;
		if (!badQtyDataPonit.equals("-1")) {
			String t = NeedData.getInstance().getDataValue(datas,
					badQtyDataPonit);
			badQty = GetValueUtil.getDoubleValue(t);
		}
		int eqmCode=Integer.valueOf(eqpCod);
		if(eqmCode>=31 && eqmCode<=60){
			curOut=curOut/2500;
			badQty=badQty/2500;
		}else if(eqmCode>=1 && eqmCode<=30){
			curOut=curOut/50;
			badQty=badQty/50000;
		}
		list.add(new EqpRuntime(eqpCod, curOut, badQty));
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月27日 上午9:24:31 
	* 功能说明 ：获取卷烟机实时辅料数据消耗
	 */
	@Override
	public List<InputPageModel> getRollerInputData() {
		List<InputPageModel> list = new ArrayList<InputPageModel>();
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		for (EquipmentData equipmentData : allData) {
			String equipmentCode = String.valueOf(equipmentData.getEqp());
			if (EquipmentTypeDefinition.getRoller().contains(
					equipmentData.getType().toUpperCase())) {
				RollerData rollerData = getRollerData(equipmentCode);
				list.add(new InputPageModel(equipmentCode, rollerData
						.getShuisongzhiVal(), rollerData.getJuanyanzhiVal(),
						rollerData.getLvbangVal(), rollerData.getQty()));
			}
		}
		return list;
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月27日 上午9:35:29 
	* 功能说明 ：获取包装机实时辅料消耗
	 */
	@Override
	public List<InputPageModel> getPackerInputData() {
		List<InputPageModel> list = new ArrayList<InputPageModel>();
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		for (EquipmentData equipmentData : allData) {
			String equipmentCode = String.valueOf(equipmentData.getEqp());
			if (EquipmentTypeDefinition.getPacker().contains(
					equipmentData.getType().toUpperCase())) {
				PackerData packerData = getPackerData(equipmentCode);
				list.add(new InputPageModel(equipmentCode, packerData.getQty(),
						packerData.getXiaohemoVal(), packerData.getTiaohemoVal(),
						packerData.getXiaohezhiVal(), packerData.getTiaohezhiVal(), packerData.getNeichenzhiVal()));
			}
		}
		return list;
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月1日 上午11:15:35 
	* 功能说明 ：实时保存消耗和产出数据
	 */
	@Override
	public void saveAllData(List<EquipmentData> eqpData) {
		for (EquipmentData equipmentData : eqpData) {
			if(equipmentData.getType().toLowerCase().indexOf("_FL")>0){
				continue;
			}
			this.saveEquipmentData(String.valueOf(equipmentData.getEqp()), equipmentData);
		}
	}
	
	@Override
	public void saveAllErrorData(List<String[]> list) {
		Hashtable<String,String> ht=getAllWorkOrderOutputListHT();
		log.info("保存所有机台故障汇总信息……");
		//故障
		saveTrouble(null,ht);
	}
	private Hashtable<String,String> eqpCodeOutputList;
	public Hashtable<String,String> getAllWorkOrderOutputListHT() {
		Hashtable<String,String> ht=new Hashtable<String,String>();
		eqpCodeOutputList=new Hashtable<String,String>();
		List<SchStatFaultBean> list=dalClient.queryForList("sch_stat_fault.queryStartWorkOrderFaul", null,SchStatFaultBean.class);
		if(list.size()>0 && list!=null){
			for(SchStatFaultBean sbean:list){
				if(eqpCodeOutputList.get(sbean.getEqp().trim())==null){
					eqpCodeOutputList.put(sbean.getEqp().trim(), sbean.getOutId().trim());
				}
				//过滤没有当前设备运行工单没有故障信息
				if(sbean.getFaultName()!=null && StringUtil.notNull(sbean.getFaultName()) && ht.get(sbean.getEqp().trim()+sbean.getFaultName().trim())==null){
					//key 设备code,value 0:生产实际ID，1:故障ID,2:故障名称
					ht.put(sbean.getEqp().trim()+sbean.getFaultName().trim(), sbean.getFaultId().trim());
				}
			}
		}
		return ht;
	}
	
	/**
	 * 根据设备ID保存设备故障汇总信息
	 * @param eid
	 * @return
	 */
	public boolean saveTrouble(String eid,Hashtable<String,String> faultHt){
		//根据设备ID获取设备故障集合，String[] //0:机台Code，1:班次，2:故障描述,3:次数,4:故障发生总时长
		List<String[]> list=null;
		if(StringUtil.notNull(eid)){
			list=FaultDataCalc.getInstance().getFaultList(eid);
		}else{
			list=FaultDataCalc.getInstance().getAllFaultList();
		}
		for(String[] string:list){
			if(!StringUtil.notNull(string[0])){
				log.error("保存故障信息失败，设备CODE("+string[0]+")不存在。");
				continue;
			}
			String value=eqpCodeOutputList.get(string[0]);
			if(value==null||!StringUtil.notNull(value)){
				log.info("实时保存故障汇总信息失败，无法获取当前设备Code("+string[0]+")对应的运行工单实际产量ID信息");
				continue;	
			}
			SchStatFaultBean fault=new SchStatFaultBean();
			//如果故障ID已存在,并且故障名称一致，则进行修改
			if(StringUtil.notNull(value)){
				String faultId=faultHt.get(string[0]+string[2]);
				if(faultId!=null){
					fault.setId(faultId);
				}
			}
			fault.setId(StringUtil.getUUID());;
			//SCH_STAT_OUTPUT ID
			fault.setOid(value);
			//故障名称
			fault.setName(string[2]);
			//时长
			fault.setTime(Double.parseDouble(string[4]));
			//次数
			fault.setTimes((long)Double.parseDouble(string[3]));
			//反馈状态
			fault.setFlag(0L);
			//先查询，后覆盖
			SchStatFaultBean ssfbean=dalClient.queryForObject("sch_stat_fault.querySchStatFault", fault, SchStatFaultBean.class);
			if(ssfbean==null){
				dalClient.execute("sch_stat_fault.insertSchStatFault", fault);
			}else{
				fault.setId(ssfbean.getId());
				dalClient.execute("sch_stat_fault.updateSchStatFault", fault);
			}
			
		}
		return false;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午11:17:40 
	* 功能说明 ：保存单个机台实时数据		说明 当工单完成时，需要调用本法方，且 data 参数不给值
	 */
	@Override
	public void saveEquipmentData(String equipmentCode, Object... data) {
		EquipmentData equipmentData = null;
		if(data.length>0){
			equipmentData = (EquipmentData)data[0];
		}else{
			equipmentData = NeedData.getInstance().getEquipmentData(Integer.valueOf(equipmentCode));
		}
		//执行保存操作
		if(equipmentData!=null){
			String type = equipmentData.getType();
			//1.查询当前的工单是否运行状态 如果是运行状态 在去判断是 什么类型的工单 进行保存数据,保存的时候需要判定是否为 0 如果为 0 就不要保存了
			WorkOrderBean runWork = null;
			try {
				//根据设备code获取当前运行的工单，id，实际开始时间
				runWork = getRunSchWorkorder(equipmentCode);//3100是辅料数据，不用管它
			} catch (Exception e1) {
				log.error("实时保存产耗数据获取当前机台运行工单出错！ :"+e1.getMessage());
			}
			if(null!=runWork){
				Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos = DataHandler.getDbOutputOrInputInfos();
				String dbOutputId = DataHandler.getDbOutputId(dbOutputOrInputInfos, equipmentCode);
				//当前运行的工单的产出表id
				String oid=runWork.getOutId();
				//判断产出表id是否一致，不一致将dbOutputId设置为null。
				if(oid==null ||(oid!=null && !oid.equals(dbOutputId))){
					dbOutputId=null;
				}
				//由于这里 是 点击运行工单是放在 内存里面的，如果tomcat 挂掉了 在重新启动就会有问题了
				//手动点击工单运行
				if(null==dbOutputId||"".equals(dbOutputId)){//如果卷烟机 包装机 成型机  封箱机 查询 辅料数据
					if(EquipmentTypeDefinition.getRoller().contains(type)
					   ||EquipmentTypeDefinition.getPacker().contains(type)
					   ||EquipmentTypeDefinition.getFilter().contains(type)
					   ||EquipmentTypeDefinition.getBoxer().contains(type)){
						OutputBean outputBean = new OutputBean();
						outputBean.setWorkorder(runWork.getId());
						String realStim = runWork.getRealStim();
						outputBean.setStim(realStim);//实际开始时间
						try {
							this.addOutput(outputBean, 0);
						} catch (Exception e) {
							log.error("StatServiceImpl->addOutput:"+e.getMessage());
						}
						dbOutputOrInputInfos = DataHandler.getDbOutputOrInputInfos();//重新取值
						dbOutputId = DataHandler.getDbOutputId(dbOutputOrInputInfos, equipmentCode);
					}
				}
				//end
				//卷烟机=====================
				Map<String, String> type_InputId = DataHandler.getType_InputId(dbOutputOrInputInfos, equipmentCode);	
				if(EquipmentTypeDefinition.getRoller().contains(type)){
					RollerData edata = getRollerData(equipmentCode);
					if(dbOutputId!=null){//修改产量数据					
						if(edata.getQty()>0){//产量只要大于0 才记录，防止 PLC换班了，程序自动换班没有换班,赋值 0
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty()>0?edata.getQty():null, 
									edata.getBadQty()>0?edata.getBadQty():null, 
									edata.getRunTime()>0?edata.getRunTime():null, 
									edata.getStopTime()>0?edata.getStopTime():null, 
									Long.valueOf(edata.getStopTimes().toString())>0?Long.valueOf(edata.getStopTimes().toString()):null, 
									new Date(),edata.getQty()));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");
						}
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]；或者sch_stat_output表没有生成对应数据");
					}
					if(type_InputId!=null){
						String inputId = DataHandler.getInputIdByType(type_InputId, "2");
						InputBean inputBean = null;
						if(inputId!=null){//装配、保存【卷烟纸】
							inputBean = new InputBean(inputId, 0D, edata.getJuanyanzhiVal());
							if(edata.getJuanyanzhiVal()>1){//产量大于 0 才更新//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=卷烟纸,value="+edata.getJuanyanzhiVal()+"]");
							}
						}
						inputId = DataHandler.getInputIdByType(type_InputId, "3");
						if(inputId!=null){//装配、保存【水松纸】
							inputBean = new InputBean(inputId, 0D, edata.getShuisongzhiVal());
							if(edata.getShuisongzhiVal()>1){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=水松纸,value="+edata.getShuisongzhiVal()+"]");
							}
						}
						inputId = DataHandler.getInputIdByType(type_InputId, "4");
						if(inputId!=null){//装配、保存【滤棒】
							inputBean = new InputBean(inputId, 0D, edata.getLvbangVal());
							if(edata.getLvbangVal()>1){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=滤棒,value="+edata.getLvbangVal()+"]");
							}
						}
					}
				}
				//====================
				
				//包装机=====================产量为提升机产量    dacqty保存的是包转箱数据
				if(EquipmentTypeDefinition.getPacker().contains(type)){
					PackerData edata = getPackerData(equipmentCode);			
					if(dbOutputId!=null){//修改产量数据	
						if(edata.getTsQty()>0){
							this.editOutput(new OutputBean(
										dbOutputId, 
										edata.getTsQty()>0?(edata.getTsQty()/250.0):null,
										edata.getBadQty()>0?edata.getBadQty():null, 
										edata.getRunTime()>0?edata.getRunTime():null, 
										edata.getStopTime()>0?edata.getStopTime():null, 
										Long.valueOf(edata.getStopTimes().toString())>0?Long.valueOf(edata.getStopTimes().toString()):null, 
										new Date(),edata.getQty()
										)
									);
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");	
						}
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]；或者sch_stat_output没有生成数据");
					}
					if(type_InputId!=null){
						String inputId = DataHandler.getInputIdByType(type_InputId, "5");
						InputBean inputBean = null;
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getXiaohemoVal());
							if(edata.getXiaohemoVal()>1){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=小盒膜,value="+edata.getXiaohemoVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "6");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getTiaohemoVal());
							if(edata.getTiaohemoVal()>1){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=条盒膜,value="+edata.getTiaohemoVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "7");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getXiaohezhiVal());
							if(edata.getXiaohezhiVal()>1){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=小盒纸,value="+edata.getXiaohezhiVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "8");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getTiaohezhiVal());
							if(edata.getTiaohezhiVal()>1){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=条盒纸,value="+edata.getTiaohezhiVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "9");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getNeichenzhiVal());
							if(edata.getNeichenzhiVal()>0){//if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=内衬纸,value="+edata.getNeichenzhiVal()+"]");
							}
						}
						
					}
				}
				//=====================
				
				//封箱机=====================需要确认封箱机能采集的数据
				if(EquipmentTypeDefinition.getBoxer().contains(type)){
					BoxerData edata = getBoxerData(equipmentCode);
					if(dbOutputId!=null){//修改产量数据		
						if(edata.getQty()>0){
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty(), 
									0D, 
									0D, 
									0D, 
									0L, 
									new Date(),edata.getQty()));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");
						}
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]");
					}
				}
				//================================
				
				//成型机===========================
				if(EquipmentTypeDefinition.getFilter().contains(type)){
					FilterData edata = getFilterData(equipmentCode);
					if(dbOutputId!=null){//修改产量数据
						if(edata.getQty()>0){
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty(), 
									edata.getBadQty(), 
									edata.getRunTime(), 
									edata.getStopTime(), 
									Long.valueOf(edata.getStopTimes().toString()), 
									new Date(),edata.getQty()));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");
						}
						
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]");
					}
					if(type_InputId!=null){
						String inputId = DataHandler.getInputIdByType(type_InputId, "13");
						InputBean inputBean = null;
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getPanzhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=滤棒盘纸,value="+edata.getPanzhiVal()+"]");
							}
						}
					}
				}
				
			}
		}else{
			log.info("当前无实时数据...");
		}
	  }
	

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午12:23:13 
	* 功能说明 ：获取当前机台运行的工单
	 */
	private WorkOrderBean getRunSchWorkorder(String equipmentCode){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("eqpCode", equipmentCode);
		return dalClient.queryForObject("pdStat.queryNowRunOrder", paramMap, WorkOrderBean.class);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午2:14:54 
	* 功能说明 ：保存生产实际,并将产出id  和    辅料消耗id与辅料类型Map  存放到内存中
	 */
	@Override
	public int addOutput(OutputBean outputBean,int flag) {
		String workorder = outputBean.getWorkorder();
		//查询条件Map
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("orderId", workorder);
		//查询结果Map
		Map<String,Object> resultMap=null;
		//判断产出表是否已经有数据
		resultMap=dalClient.queryForMap("pdStat.isExistsOutPut", paramMap);
		if(flag==1){//人为新增
			//判断当前新增工单是否已经在生产实绩中有记录
			if(resultMap!=null && resultMap.get("outId")!=null){
				return 0;
			}
//			o = BeanConvertor.copyProperties(outputBean, SchStatOutput.class);
//			
//			o.setMdUnit(new MdUnit(outputBean.getUnit()));//来源于工单计划产量单位
//			//时间单位统一用分钟
//			o.setLastUpdateTime(new Date());
			
		}else if(flag==0){
			//已经有产出数据
			if(resultMap!=null && resultMap.get("outId")!=null){
				paramMap.put("outId", resultMap.get("outId"));
			}else{
				//dalClient.execute("pdStat.deleteOutPutByOrderId", paramMap);
				paramMap.put("outId", StringUtil.getUUID());
				paramMap.put("stim", DateUtil.formatStringToDate(outputBean.getStim(), "yyyy-MM-dd HH:mm:ss"));
				//初始值未空
				paramMap.put("qty", 0.0);
				paramMap.put("badQty", 0.0);
				paramMap.put("runTime", 0.0);
				paramMap.put("stopTime", 0.0);
				paramMap.put("stopTimes", 0);
				//给产量单位赋值
				resultMap=dalClient.queryForMap("pdStat.queryUnitByOrderId", paramMap);
				if(resultMap!=null){
					paramMap.put("unitId", resultMap.get("unitId"));
				}else{
					log.error("保存产量数据时没有查询到工单产量单位ID！");
				}
			}
		}
		//已经有产出数据
		if(resultMap!=null && resultMap.get("outId")!=null){
			//已经有产出数据，就不需要重复添加
		}else{
			paramMap.put("timeUnitId", Constants.unitMins);//时间单位（后期优化）
			paramMap.put("isFeedBack", 0);//是否反馈
			paramMap.put("del", 0);//是否删除
			//保存output表
			dalClient.execute("pdStat.saveOutPut", paramMap);
		}
		
		if(flag==0){//运行工单时，新增的生产实绩的消耗数据从工单bom中来
			Map<String, String> type_statInput = addInputsFromWorkOrderBoms(workorder,StringUtil.convertObjToString(paramMap.get("outId")));
			//保存产出实绩和消耗实绩信息到内存中，避免轮询保存实时数据时，重复从数据库获得这些信息
			DbOutputOrInputInfos outputOrInputInfos = new DbOutputOrInputInfos();
			outputOrInputInfos.setStatOutputId(StringUtil.convertObjToString(paramMap.get("outId")));
			outputOrInputInfos.setType_statInputId(type_statInput);
			resultMap=dalClient.queryForMap("pdStat.queryEqpCodeByOrder", paramMap);
			DataHandler.setDbOutputOrInputInfosIntoMap(StringUtil.convertObjToString(resultMap.get("eqpCode")), outputOrInputInfos);
		}
		return 1;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午8:32:19 
	* 功能说明 ：根据工单Bom，插入sch_stat_input 数据。（辅料消耗数据）并将    物料类型--sch_stat_input id封装成Map
	 */
	private  Map<String,String> addInputsFromWorkOrderBoms(String workorder,String outPutId){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("orderId", workorder);
		List<Map<String,Object>> boms=dalClient.queryForList("pdStat.queryOrderBom", paramMap);
		paramMap.put("outPutId", outPutId);
		Map<String,String> inputs = new HashMap<String, String>();
		//查询消耗是否已经存在数据，若已经存在，直接把数据放到缓存中
		List<Map<String,Object>> resultMap=null;
		resultMap=dalClient.queryForList("pdStat.queryInputByOutId", paramMap);
		if(resultMap!=null && resultMap.size()>0){
			//已经存在只需要将数据重新放入缓存
			for (Map<String, Object> map : resultMap) {
				inputs.put(StringUtil.convertObjToString(map.get("matType")), StringUtil.convertObjToString(map.get("inputId")));
			}
		}
		else{
			//没有消耗数据，新增数据
			for (Map<String, Object> map : boms) {
				paramMap.put("unitId", map.get("unitId"));
				paramMap.put("matId", map.get("matId"));
				String inputId=StringUtil.getUUID();
				paramMap.put("id", inputId);
				dalClient.execute("pdStat.saveInput", paramMap);
				inputs.put(StringUtil.convertObjToString(map.get("matType")), inputId);
			}
		}
		return inputs;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午4:05:33 
	* 功能说明 ：更新产量信息
	 */
	private int editOutput(OutputBean outputBean){
		dalClient.execute("pdStat.updateOutPut", outputBean);
		return 1;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午4:29:48 
	* 功能说明 ：更新辅料消耗数据
	 */
	private int editInput(InputBean inputBean) {
		dalClient.execute("pdStat.updateInput", inputBean);
		return 1;
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月3日 上午9:21:54 
	* 功能说明 ：获取所有设备的实时数据
	 */
	@Override
	public List<EquipmentData> getAll() {
		return DataSnapshot.getInstance().getEqpData();
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月3日 上午9:28:44 
	* 功能说明 ：获取指定设备的实时数据
	 */
	@Override
	public EquipmentData get(String code) {
		int id = Integer.valueOf(code);
		try {
			for(EquipmentData data : DataSnapshot.getInstance().getEqpData()){
				if (data.getEqp()==id){
					return data;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new EquipmentData(id,"无当前机台数据");
	}

	
	/**
	 * [共能说明]：定时结束上一班次工单，启动当前所有未启动的工单（班内换牌需要手动结束第一个工单）
	 * @author wanchanghuang
	 * @date 2016年8月4日11:26:40
	 * @param 1:早班   2：中班   3：晚班
	 * */
	@Override
	public void edWorkOrder() {
		dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>(){
			@Override
			public Integer invoke() {
				/*Date now=new Date();
				String nowTime=DateUtil.formatDateToString(now, "yyyy-MM-dd HH:mm:ss");*/
				String nowTime=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"); 
				Map<String, Object> paramsMap=new HashMap<>();
				paramsMap.put("workShop", "1");
				paramsMap.put("nowTime", nowTime);
				//卷包车间当前班次
				WorkOrderBean bean=dalClient.queryForObject("pddisplay.querySchCalendarByDateTime", paramsMap, WorkOrderBean.class);
				//当前时间分钟加1，跳到下一班次开始时间段内
				String nowTimeAdd=DateUtil.dateAdd("f", 1, new Date(), "yyyy-MM-dd HH:mm:ss");
				if(null!=bean){
					bean.setEdate(bean.getEdate().substring(0,10));
					//查询需要修改为完成状态的工单,并将修改的工单状态发送给PMS系统，让PMS系统发送给MES系统，
					bean.setOrderType("1,2,3");
					bean.setNowTime(nowTimeAdd);
					List<Map<String,Object>> finishedDatas=dalClient.queryForList("pddisplay.queryAllNeedFinishedOrders", bean);
					if(finishedDatas!=null && finishedDatas.size()>0){
						//关闭工单
						finishedOrder(finishedDatas);
						log.info("工单定时器已经关闭卷包工单上个班次工单！"+nowTime);
					}
				}else{
					//关闭所有没有运行状态的的工单
					bean=new WorkOrderBean();
					bean.setOrderType("1,2,3");
					bean.setNowTime(nowTimeAdd);
					List<Map<String,Object>> finishedDatas=dalClient.queryForList("pddisplay.queryAllNeedFinishedOrders", bean);
					if(finishedDatas!=null && finishedDatas.size()>0){
						//关闭工单
						finishedOrder(finishedDatas);
						log.info("工单定时器查询到卷包车间没有下个班次的排班信息，将要关闭下个班次之前的所有运行的工单！"+nowTime);
					}
				}
				//查询需要启动的工单，并将修改的工单状态发送给PMS系统，让PMS系统发送给MES系统
				List<Map<String, Object>> startedDatas = dalClient.queryForList("pddisplay.queryNeedStartedOrders", bean);
				if (startedDatas != null && startedDatas.size() > 0) {
					//开启工单
					startOrder(startedDatas);
					log.info("工单定时器已经开启卷包工单下个班次工单！" + nowTime);
				} 
				//成型车间
				paramsMap.put("workShop", "2");
				bean=dalClient.queryForObject("pddisplay.querySchCalendarByDateTime", paramsMap, WorkOrderBean.class);
				if(null!=bean){
					bean.setEdate(bean.getEdate().substring(0,10));
					//查询需要修改为完成状态的工单,并将修改的工单状态发送给PMS系统，让PMS系统发送给MES系统
					bean.setOrderType("4");
					bean.setNowTime(nowTimeAdd);
					List<Map<String,Object>> finishedDatas=dalClient.queryForList("pddisplay.queryAllNeedFinishedOrders", bean);
					if(finishedDatas!=null && finishedDatas.size()>0){
						//关闭工单
						finishedOrder(finishedDatas);
						log.info("工单定时器已经关闭成型工单！"+nowTime);
					}
				}else{
					//关闭所有没有运行状态的的工单
					bean=new WorkOrderBean();
					bean.setOrderType("4");
					bean.setNowTime(nowTimeAdd);
					List<Map<String,Object>> finishedDatas=dalClient.queryForList("pddisplay.queryAllNeedFinishedOrders", bean);
					if(finishedDatas!=null && finishedDatas.size()>0){
						//关闭工单
						finishedOrder(finishedDatas);
						log.info("工单定时器查询到成型车间没有下个班次的排班信息，将要关闭下个班次之前的所有运行的工单！"+nowTime);
					}
				}
				//查询需要启动的工单，并将修改的工单状态发送给PMS系统，让PMS系统发送给MES系统
				List<Map<String,Object>> filterStartedDatas=dalClient.queryForList("pddisplay.queryNeedStartedOrders", bean);
				if(filterStartedDatas!=null && filterStartedDatas.size()>0){
					//开启工单
					startOrder(filterStartedDatas);
					log.info("工单定时器已经开启下个班次的成型工单！"+nowTime);
				}
				return 1;
			}
	    });
	}
	/**
	 * 关闭工单
	 * @param finishedDatas
	 */
	private void finishedOrder(List<Map<String, Object>> finishedDatas) {
		Map<String,Object> params=new HashMap<>();
		//将工单id封装成list
		String oids=convertList2Str(finishedDatas);
		//结束之前保存一次数据（实时消耗需要初始化产耗数据）
		DataHandler.getInstance().saveAllData();
		//根据当前时间，结束上一班次工单
		params.put("oids", oids);
		params.put("nowTime",new Date());
		dalClient.execute("pddisplay.eupWorkOrderByShift",params);
		//修改产出表结束时间
		dalClient.execute("pddisplay.closeOrderUpdateOutEtime",params);
		//向PMS发送结束工单id
		try {
			SendMessageToJyscClient.getInstance().sendOrderStatusChange(finishedDatas, "4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 开启工单
	 * @param startedDatas
	 */
	private void startOrder(List<Map<String, Object>> startedDatas) {
		Map<String,Object> params=new HashMap<>();
		//将工单id封装成list
		String oids=convertList2Str(startedDatas);
		//根据当前时间，启动当前未启动的工单；
		params.put("oids", oids);
		params.put("nowTime", new Date());
		dalClient.execute("pddisplay.startWorkOrderByShiftDac",params);
		//开启工单之后需要将设备系数和辅料系数刷新     产出和消耗id 刷新
		// 产出和消耗id 刷新
		DataHandler.getDbOutputOrInputInfos().clear();
		//设备系数和辅料系数刷新
		initService.initWorkOrderInfo();
		//开启之后保存一下数据，生成产出等信息
		DataHandler.getInstance().saveAllData();
		//向PMS发送开启工单id
		try {
			SendMessageToJyscClient.getInstance().sendOrderStatusChange(startedDatas, "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * PMS实时监控页面数据获取（卷包机组）
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月16日 下午4:56:20 
	* 功能说明 ：
	 */
	@Override
	public List<RollerPackerData> getAllRollerPackerDatas() {
		//获取所有实时数据
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		//用于存放过滤后的数据
		List<RollerPackerData> rollerPackerDatas = new ArrayList<RollerPackerData>();
		//过滤数据
		for (EquipmentData equipmentData : allData) {
			String type = equipmentData.getType();
			RollerPackerData  data= null;
			if(EquipmentTypeDefinition.getRoller().contains(type)){//卷烟机
				data = new RollerPackerData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "7")));
				data.setSpeed(GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "15")));
			}
			if(EquipmentTypeDefinition.getPacker().contains(type)){//包装机
				data = new RollerPackerData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "3")));
				data.setSpeed(GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "2")));
			}
			if(data!=null){
				rollerPackerDatas.add(data);
			}
		}
		return rollerPackerDatas;
	}
	
	/**
	 * 将list<map> 转 map<list>
	 * @param list
	 * @return
	 */
	private String  convertList2Str(List<Map<String,Object>> list){
		StringBuffer sb=new StringBuffer();
		for (Map<String, Object> map : list) {
			sb.append(StringUtil.convertObjToString(map.get("id")));
			sb.append(",");
		}
		if(sb.length()>1){
			//去掉最后一个“,”
			int index=sb.lastIndexOf(",");
			return sb.substring(0, index);
		}
		return sb.toString();
	}
	
	/**
	 * 将班次转数组，并从小到大排序
	 * @param list
	 * @return
	 */
	private int[] convertList2StringArr(List<Map<String,Object>> list){
		int size=list.size();
		int[] shiftCode=new int[size];
		Map<String,Object> map=null;
		for (int i=0;i<size;i++) {
			map=list.get(i);
			int shift=Integer.valueOf(StringUtil.convertObjToString(map.get("shiftCode")));
			shiftCode[i]=shift;
		}
		Arrays.sort(shiftCode);
		return shiftCode;
	}
	
	/**
	 * <p>功能描述：</p>
	 *@see com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService#saveRealTimeDataTimer()
	 *shisihai
	 *2016上午11:06:36
	 */
	@Override
	public void saveRealTimeDataTimer() {
		List<EquipmentData> eqpDatas=NeedData.getInstance().getEqpData();
		int eqpCode=0;
		EquipmentData mainData =null;
		List<CommonData> datas=null;
		for (EquipmentData equipmentData : eqpDatas) {
			eqpCode=equipmentData.getEqp();
			if(eqpCode<201){
				//设备主数据
				mainData= NeedData.getInstance().getEquipmentData(eqpCode);
				if(mainData==null){
					continue;
				}
				//卷烟机
				if(eqpCode>0 && eqpCode<31){
					realTimeSaveRollerData(eqpCode, mainData,datas);
				}
				//包装机
				else if(eqpCode>30 && eqpCode<61){
					realTimeSavePackerData(eqpCode, mainData, datas);
				}
				//封箱机
				else if(eqpCode>60 && eqpCode<71){
					saveBoxerRealTimeData(eqpCode,mainData,datas);
				}
				//储烟机
				else if(eqpCode>70 && eqpCode<101){
					saveCYRealTimeData(eqpCode, mainData,datas);
				}
				//成型机
				else if(eqpCode>100 && eqpCode<131){
					saveFilterRealTimeData(eqpCode, mainData,datas);
				}
				//发射机
				else if(eqpCode>130 && eqpCode<141){
					saveLauncherRealTimeData(eqpCode, mainData,datas);
				}
				//条烟输送
				else if(eqpCode==151){
					mainData=DataSnapshot.getInstance().getEquipmentData(eqpCode);
					saveTYRealTimeData(eqpCode,mainData,datas);
				}
			}
		}
	}

	/**
	 * <p>功能描述：保存提升机数据</p>
	 *@param params
	 *@param mainData
	 *@param datas
	 *shisihai
	 *2016下午3:11:10
	 */
	private void saveTYRealTimeData(int eqpCode,EquipmentData mainData,List<CommonData> datas) {
		try {
			HashMap<String,Object> params=new HashMap<>();
			//主键id
			//params.put("id", StringUtil.getUUID());
			//设备code
			params.put("eqpid", eqpCode);
			//保存数据时间
			params.put("saveTime", new Date());
			datas = mainData.getAllData();
			//网络
			params.put("online", mainData.isOnline()?1:0);
			//链条机状态
			params.put("ltState1", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "1")));
			params.put("ltState2", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "2")));
			params.put("ltState3", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "3")));
			params.put("ltState4", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "4")));
			params.put("ltState5", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "5")));
			params.put("ltState6", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "6")));
			params.put("ltState7", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "7")));
			params.put("ltState8", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "8")));
			//提升机状态
			params.put("state1", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "9")));
			params.put("state2", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "10")));
			params.put("state3", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "11")));
			params.put("state4", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "12")));
			params.put("state5", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "13")));
			params.put("state6", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "14")));
			params.put("state7", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "15")));
			params.put("state8", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "16")));
			params.put("state9", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "17")));
			params.put("state10", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "18")));
			params.put("state11", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "19")));
			params.put("state12", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "20")));
			//排包机状态
			params.put("outState1", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "21")));
			params.put("outState2", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "22")));
			params.put("outState3", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "23")));
			params.put("outState4", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "24")));
			//排包机产量
			params.put("outQty1", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "37")));
			params.put("outQty2", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "38")));
			params.put("outQty3", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "39")));
			params.put("outQty4", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "40")));
			//提升机产量
			params.put("qty1", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "25")));
			params.put("qty2", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "26")));
			params.put("qty3", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "27")));
			params.put("qty4", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "28")));
			params.put("qty5", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "29")));
			params.put("qty6", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "30")));
			params.put("qty7", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "31")));
			params.put("qty8", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "32")));
			params.put("qty9", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "33")));
			params.put("qty10", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "34")));
			params.put("qty11", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "35")));
			params.put("qty12", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "36")));
			//延时保存
			if(conveyorerDatas.size()>=count){
				for (HashMap<String, Object> conveyorerData : conveyorerDatas) {
					dalClient.execute("pdStat.saveConveyorRealTimeData", conveyorerData);
				}
				conveyorerDatas.clear();
			}
			conveyorerDatas.add(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("高频保存提升机数据异常！");
		}
	}

	/**
	 * <p>功能描述：保存发射机数据</p>
	 *@param params
	 *@param mainData
	 *@param datas
	 *shisihai
	 *2016下午3:09:51
	 */
	private void saveLauncherRealTimeData(int eqpCode, EquipmentData mainData,List<CommonData> datas) {
		try {
			HashMap<String,Object> params=new HashMap<>();
			//主键id
			//params.put("id", StringUtil.getUUID());
			//设备code
			params.put("eqpid", eqpCode);
			//保存数据时间
			params.put("saveTime", new Date());
			datas = mainData.getAllData();
			params.put("online", mainData.isOnline()?1:0);
			params.put("qty0",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1")),3));//小数
			params.put("qty1",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2")),3));//小数
			params.put("qty2",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3")),3));//小数
			params.put("qty3",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4")),3));//小数
			params.put("qty4",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5")),3));//小数
			params.put("qty5",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "6")),3));//小数
			params.put("qty6",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7")),3));//小数
			params.put("qty7",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8")),3));//小数
			params.put("qty8",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "9")),3));//小数
			params.put("qty9",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "10")),3));//小数
			params.put("speed0",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "11")),1));//整数
			params.put("speed1",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "12")),1));//整数
			params.put("speed2",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "13")),1));//整数
			params.put("speed3",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "14")),1));//整数
			params.put("speed4",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "15")),1));//整数
			params.put("speed5",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "16")),1));//整数
			params.put("speed6",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "17")),1));//整数
			params.put("speed7",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "18")),1));//整数
			params.put("speed8",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "19")),1));//整数
			params.put("speed9",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "20")),1));//整数
			params.put("faultInfo", "数据点暂时没有写");
			params.put("runTime", 0);
			params.put("stopTime", 0);
			params.put("stopTimes", 0);
			if(launcherDatas.size()>=count){
				for (HashMap<String, Object> launcherData : launcherDatas) {
					dalClient.execute("pdStat.saveLauncherData", launcherData);
				}
				launcherDatas.clear();
			}
			launcherDatas.add(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("高频保存发射机数据异常！");
		}
	}

	/**
	 * <p>功能描述：保存成型机数据</p>
	 *@param eqpCode
	 *@param params
	 *@param mainData
	 *@param datas
	 *shisihai
	 *2016下午3:08:10
	 */
	private void saveFilterRealTimeData(int eqpCode,EquipmentData mainData,List<CommonData> datas) {
		try {
			HashMap<String,Object> params=new HashMap<>();
			//主键id
		//	params.put("id", StringUtil.getUUID());
			//设备code
			params.put("eqpid", eqpCode);
			//保存数据时间
			params.put("saveTime", new Date());
			datas = mainData.getAllData();
			//网络
			params.put("online",mainData.isOnline()?1:0);
			//车速
			params.put("speed", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "11")));
			//产量
			params.put("qty", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1"))/10000,2));
			//剔除量
			params.put("badQty", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2"))/10000,2));
			//运行时间
			params.put("runTime", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7")),0));
			//停机时间
			params.put("stopTime", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8")),0));
			//停机次数
			params.put("stopTimes", 0);
			//丝束
			params.put("ssVal", 0);//丝束
			params.put("gyVal", 0);//甘油
			mainData = NeedData.getInstance().getEquipmentData(eqpCode*1000);
			//盘纸
			if(mainData!=null){
				datas = mainData.getAllData();
				params.put("pzVal", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5")),2));
			}else{
				params.put("pzVal",0);
			}
			params.put("faultInfo", "故障信息");
			//延时保存
			if(filterDatas.size()>=count){
				for (HashMap<String, Object> filterData : filterDatas) {
					dalClient.execute("pdStat.saveFilterRealTimeData", filterData);
				}
				filterDatas.clear();
			}
			filterDatas.add(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("高频保存成型机数据异常！");
		}
	}

	/**
	 * <p>功能描述：保存储烟机器数据</p>
	 *@param params
	 *@param mainData
	 *@param datas
	 *@return
	 *shisihai
	 *2016下午3:05:35
	 */
	private void saveCYRealTimeData(int eqpCode,EquipmentData mainData,List<CommonData> datas) {
		try {
			HashMap<String,Object> params=new HashMap<>();
			//主键id
			//params.put("id", StringUtil.getUUID());
			//设备code
			params.put("eqpid", eqpCode);
			//保存数据时间
			params.put("saveTime", new Date());
			//储烟机
			datas = mainData.getAllData();
			//网络
			params.put("online", mainData.isOnline()?1:0);
			//储烟量
			params.put("cyRate",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8"))/100, 2));
			//故障信息
			params.put("faultInfo", "数据点暂时没有写");
			params.put("runTime", 0);
			params.put("stopTime", 0);
			params.put("stopTimes", 0);
			params.put("speed", 0);
			//延时保存数据
			if(containerDatas.size()>=count){
				for (HashMap<String, Object> containerData : containerDatas) {
					dalClient.execute("pdStat.saveCYRealTimeData", containerData);
				}
				containerDatas.clear();
			}
			containerDatas.add(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("高频保存储烟机数据异常！");
		}
	}
	/**
	 * <p>功能描述：实时保存封箱机数据</p>
	 *@param params
	 *@param mainData
	 *@param datas
	 *shisihai
	 *2016下午3:04:05
	 */
	private void saveBoxerRealTimeData(int eqpCode, EquipmentData mainData,List<CommonData> datas) {
		try {
			HashMap<String,Object> params=new HashMap<>();
			//主键id
			//params.put("id", StringUtil.getUUID());
			//设备code
			params.put("eqpid", eqpCode);
			//保存数据时间
			params.put("saveTime", new Date());
			datas = mainData.getAllData();
			//网络
			params.put("online", mainData.isOnline()?1:0);
			//封箱机的产量是件，5件是1箱
			params.put("qty", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "53"))/5,2));
			//剔除
			params.put("badQty", 0);
			//纸箱消耗
			params.put("zxVal", 0);
			//故障信息
			params.put("faultInfo", "数据点暂时没有写");
			params.put("runTime", 0);
			params.put("stopTime", 0);
			params.put("stopTimes", 0);
			params.put("speed", 0);
			//延时保存数据
			if(boxerDatas.size()>=count){
				for (HashMap<String, Object> boxerData : boxerDatas) {
					dalClient.execute("pdStat.saveBoxerRealTimeData", boxerData);
				}
				boxerDatas.clear();
				reSetSaveCount();
			}
			boxerDatas.add(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("高频保存封箱机数据异常！");
		}
	}

	private void realTimeSavePackerData(int eqpCode, EquipmentData mainData,List<CommonData> datas) {
			try {
				HashMap<String,Object> params=new HashMap<>();
				//主键id
				//params.put("id", StringUtil.getUUID());
				//设备code
				params.put("eqpid", eqpCode);
				//保存数据时间
				params.put("saveTime", new Date());
				datas = mainData.getAllData();
				//网络
				params.put("online",mainData.isOnline()?1:0);
				//车速
				params.put("speed", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "2")));
				//产量
				params.put("qty",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3"))/2500,2));
				//剔除量
				params.put("badQty", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4"))/2500,2));
				//运行时间
				params.put("runTime",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5"))/60,0));
				//停机时间
				params.put("stopTime", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "6"))/60,0));
				//停机次数
				params.put("stopTimes", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "7")));
				//故障信息
				params.put("faultInfo", DataSnapshot.getInstance().getEquipmentData(eqpCode).getData("8").getVal());
				//辅料数据
				mainData=NeedData.getInstance().getEquipmentData(eqpCode*1000);
				if(mainData!=null){
					datas = mainData.getAllData();
					//内衬纸
					params.put("nczVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1")));
					//小盒纸
					params.put("xhzVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2")));
					//小盒膜
					params.put("xhmVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3")));
					//条盒纸
					params.put("thzVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4")));
					//条盒膜
					params.put("thmVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5")));
				}
				//延时插入数据
				if(packerDatas.size()>=count){
					for (HashMap<String, Object> packData : packerDatas) {
						dalClient.execute("pdStat.savePackerTimeData", packData);
					}
					packerDatas.clear();
					reSetSaveCount();
				}
				packerDatas.add(params);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("高频保存包装机数据异常！");
			}
	}
	/**
	 * <p>功能描述：实时保存卷烟机数据</p>
	 *@param eqpCode
	 *@param params
	 *@param mainData
	 *shisihai
	 *2016下午2:29:56
	 */
	private void realTimeSaveRollerData(int eqpCode, EquipmentData mainData,List<CommonData> datas) {
			try {
				HashMap<String,Object> params=new HashMap<>();
				//主键id
				//params.put("id", StringUtil.getUUID());
				//设备code
				params.put("eqpid", eqpCode);
				//保存数据时间
				params.put("saveTime", new Date());
				double lbVal=0;
				datas = mainData.getAllData();
				//网络
				params.put("online", mainData.isOnline()?1:0);
				//车速
				params.put("speed", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "15")));
				//产量
				params.put("qty", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7"))/50,2));
				//剔除
				params.put("badQty",MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "11"))/50000,4));
				//运行时间
				params.put("runTime", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "16")),0));
				//停机时间
				params.put("stopTime", MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "18")),0));
				//停机次数
				params.put("stopTimes", GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "19")));
				//故障信息
				params.put("faultInfo", "");
				//辅料取值
				lbVal= MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8"))/10000,4);
				params.put("jyzVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "21")));
				params.put("sszVal", GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "22")));
				//嘴棒接收机
				mainData = NeedData.getInstance().getEquipmentData(eqpCode+200);
				if(mainData!=null){
					datas = mainData.getAllData();
					int td1=GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "16"));
					int td2=GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "17"));
					lbVal=lbVal+(td1+td2)/10000;
				}
				params.put("lbVal", lbVal);
				//延时保存数据
				if(rollerDatas.size()>=count){
					for (HashMap<String,Object> rollerData : rollerDatas) {
						dalClient.execute("pdStat.saveRollerRealTimeData",rollerData);
					}
					rollerDatas.clear();
					reSetSaveCount();
				}
				rollerDatas.add(params);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("高频保存卷烟机数据异常！");
			}
	}
	/**
	 * <p>功能描述：</p>
	 *@see com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService#deleteRealTimeData()
	 *shisihai
	 *2016上午11:06:42
	 */
	@Override
	public void deleteRealTimeData() {
		
	}
	
	private void reSetSaveCount(){
		List<MdFixCode> fixCodes=BaseParams.getFixCode();
		for (MdFixCode mdFixCode : fixCodes) {
			if(StringUtil.notEmpty(mdFixCode.getCode())){
				if(mdFixCode.getCode().equals("MAXREALTIMESAVECOUNT")){
					try {
						count=Integer.valueOf(mdFixCode.getMesCode());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
			continue;
		}
	}
}
