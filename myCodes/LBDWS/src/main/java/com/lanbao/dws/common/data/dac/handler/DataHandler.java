package com.lanbao.dws.common.data.dac.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.lanbao.dws.common.tools.ApplicationContextUtil;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.ThreadManager;
/**
 * 实时数据处理实现
 * @author Leejean
 * @create 2015年1月7日下午1:15:49
 */
import com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService;

import business.MainBussiness;
public class DataHandler{
	private static IRealTimeDataService  realTimeDataService=null;
	private static Logger log = Logger.getLogger(DataHandler.class);
	private static DataHandler instance = null;
	/**
	 * 保存辅料计算参数集合
	 * Key ：equipmentCode
	 * Value ： FilterCalcValue or RollerCalcValue or PakcerCalcValue
	 */
	private static Map<String,Object> calcValues = new HashMap<String, Object>();
	
	/**
	 * 产出和辅料消耗
	 */
	private static Map<String,DbOutputOrInputInfos> dbOutputOrInputInfos =new HashMap<String, DbOutputOrInputInfos>();	
	
	public static DataHandler getInstance(){
		if (instance == null){
			instance = new DataHandler();
			instance.copyData();
		}
		return instance;
	}
	
	/**
	 * 实时数据处理
	 * @author Leejean
	 * @create 2015年1月7日下午1:40:12
	 */
	public void copyData(){
		//log.info("====启动[实时数据->业务数据]拷贝线程...");
		CopyThread copyThread=new CopyThread();
		ThreadManager.getInstance().addSchedule(copyThread, 3, 2, TimeUnit.SECONDS);
		ThreadManager.getInstance().addSchedule(FaultDataCalc.getInstance(), 3, 2, TimeUnit.SECONDS);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午10:38:19 
	* 功能说明 ：设备产出实绩信息和消耗实绩信息
	 */
	public static void setDbOutputOrInputInfosIntoMap(String equipmentCode,DbOutputOrInputInfos value){
		DataHandler.dbOutputOrInputInfos.put(equipmentCode, value);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午10:38:48 
	* 功能说明 ：获得生产实际产出ID
	 */
	public static String getDbOutputId(Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos,String equipmentCode) {
		 DbOutputOrInputInfos dbOutput = dbOutputOrInputInfos.get(equipmentCode);
		if(dbOutput!=null){
			return dbOutput.getStatOutputId();
		}
		return null;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午10:39:16 
	* 功能说明 ：获得辅料类别与生产实际消耗数据ID
	 */
	public static Map<String, String> getType_InputId(Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos,String equipmentCode) {
		 DbOutputOrInputInfos dbOutput = dbOutputOrInputInfos.get(equipmentCode);
		if(dbOutput!=null){
			return dbOutput.getType_statInputId();
		}
		return null;
	}
	/**
	 * 根据物料类型获得消耗ID
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午10:39:52 
	* 功能说明 ：
	 */
	public static String getInputIdByType(Map<String, String> type_InputId,String type) {
		String dbOutput = type_InputId.get(type);
		if(dbOutput!=null){
			return dbOutput;
		}
		return null;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午10:40:18 
	* 功能说明 ： 实时数据保存
	 */
	public void saveAllData(){
		try {
			if(realTimeDataService==null){
				realTimeDataService=ApplicationContextUtil.getBean(IRealTimeDataService.class);
			}
			realTimeDataService.saveAllData(NeedData.getInstance().getEqpData()); 
		} catch (Exception e) {
			log.info("w::实时保存数据异常！！");
		}
	}
	/**
	 * 故障数据保存
	 * @author luo
	 * @create 2015年9月18日09:15:46
	 */
	public void saveAllErrorData(){
		if(realTimeDataService==null){
			realTimeDataService=ApplicationContextUtil.getBean(IRealTimeDataService.class);
		}
		realTimeDataService.saveAllErrorData(FaultDataCalc.getInstance().getAllFaultList()); 
	}
	
	/**
	 * [工能说明]：定时结束上一班次工单，启动当前所有工单
	 * @author wanchanghuang
	 * @date 2016年8月4日11:26:40
	 * */
	public void edWorkOrder(){
		try {
			if(realTimeDataService==null){
				realTimeDataService=ApplicationContextUtil.getBean(IRealTimeDataService.class);
			}
			realTimeDataService.edWorkOrder();
			log.info("定时换班任务执行完毕！"+DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			e.printStackTrace();
			log.info("定时换班异常!"+DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
		
	}
	
	/**
	 * 定时任务 ： 高频保存产耗数据
	 * <p>功能描述：每隔3s保存一次机台的实时数据，用于监控分析。只保持一天数据。</p>
	 *shisihai
	 *2016上午9:52:43
	 */
	public void saveRealTimeDataTimer(){
		if(realTimeDataService==null){
			realTimeDataService=ApplicationContextUtil.getBean(IRealTimeDataService.class);
		}
		realTimeDataService.saveRealTimeDataTimer();
		log.info("高频保存数据执行完毕！");
	}
	/**
	 * 定时任务
	 * <p>功能描述：删除昨天的实时数据</p>
	 *shisihai
	 *2016上午9:55:48
	 */
	public void deleteRealTimeData(){
		realTimeDataService.deleteRealTimeData();
	}
	/**
	 * <p>功能描述：SqlLite 定时保存任务</p>
	 *shisihai
	 *2016上午8:18:05
	 */
	public void sqlLiteToolsSave(){
		MainBussiness.getInstance().saveMemoryData(DataSnapshot.getInstance().getEqpData());
	}
	
	public static Map<String, Object> getCalcValues() {
		return calcValues;
	}
	public static void setCalcValues(Map<String, Object> calcValues) {
		DataHandler.calcValues = calcValues;
	}
	public static void setCalcValueIntoMap(String eqpcode, Object calcValue) {
		DataHandler.calcValues.put(eqpcode, calcValue);
	}
	public static  Map<String, DbOutputOrInputInfos> getDbOutputOrInputInfos() {
		return dbOutputOrInputInfos;
	}
	public static void setDbOutputOrInputInfos(
			Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos) {
		DataHandler.dbOutputOrInputInfos = dbOutputOrInputInfos;
	}
	
	
}
