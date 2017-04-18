package com.shlanbao.tzsc.data.runtime.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.base.mapping.ChangeShiftDatas;
import com.shlanbao.tzsc.pms.sch.stat.service.StatServiceI;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.ThreadManager;
/**
 * 实时数据处理实现
 * @author Leejean
 * @create 2015年1月7日下午1:15:49
 */
public class DataHandler{
	@Autowired
	private StatServiceI statService;
	@Autowired
	private WorkOrderServiceI workOrderService;
	
	private static Logger log = Logger.getLogger(DataHandler.class);
	public DataHandler() {}
	private static DataHandler instance = null;
	/**
	 * 保存辅料计算参数集合
	 * Key ：equipmentCode
	 * Value ： FilterCalcValue or RollerCalcValue or PakcerCalcValue
	 */
	private static Map<String,Object> calcValues = new HashMap<String, Object>();
	private static Map<String,DbOutputOrInputInfos> dbOutputOrInputInfos =new HashMap<String, DbOutputOrInputInfos>();	
	
	public static Map<String, Object> getCalcValues() {
		return calcValues;
	}
	public static void setCalcValues(Map<String, Object> calcValues) {
		DataHandler.calcValues = calcValues;
	}
	
	public static Map<String, DbOutputOrInputInfos> getDbOutputOrInputInfos() {
		return dbOutputOrInputInfos;
	}
	public static void setDbOutputOrInputInfos(
			Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos) {
		DataHandler.dbOutputOrInputInfos = dbOutputOrInputInfos;
	}
	/**
	 * 添加当前设备的辅料数据
	 * @author luther.zhang 
	 * @create 2015年4月18日
	 */
	public Map<String,Object> saveCalcValues(String type,int eqp){
		if("packer".equals(type)){
			if(null==workOrderService){
				workOrderService = ApplicationContextUtil.getBean(WorkOrderServiceI.class);	
			}
			String newEqp= String.valueOf(eqp/1000);//EG：31
			//根据设备号 查询 正在运行的工单辅料数据
			try {
				workOrderService.getWork(newEqp);
			} catch (Exception e) {
				log.error("DataHandler saveCalcValues :"+e.getMessage());
			}
		}
		return calcValues;
	}
	/**
	 * 往辅料计算参数集合添加辅料计算参数（该操作发生在运行工单时）
	 * @author Leejean
	 * @create 2015年1月9日上午8:34:19
	 * @param equipmentCode
	 * @param calcValue
	 */
	public static void setCalcValueIntoMap(String equipmentCode,Object calcValue){
		DataHandler.calcValues.put(equipmentCode, calcValue);
		//log.info("====工单运行,[equipmentCode="+equipmentCode+"]工单的Bom辅料计算系数,烟机滚轴系数已保存到[DataHandler.calcValues]\n value:"+calcValue.toString());
	}
	/**
	 * 
	 * @author Leejean
	 * @create 2015年1月27日下午4:36:26
	 * @param equipmentCode 设备code
	 * @param value 设备产出实绩信息和消耗实绩信息
	 */
	public static void setDbOutputOrInputInfosIntoMap(String equipmentCode,DbOutputOrInputInfos value){
		DataHandler.dbOutputOrInputInfos.put(equipmentCode, value);
		//log.info("====工单运行,[equipmentCode="+equipmentCode+"]工单产出实绩,消耗实绩ID已保存到[DataHandler.dbOutputOrInputInfos]\n value:"+value.toString());
	}
	/**
	 * 获得生产实际产出ID
	 * @author Leejean
	 * @create 2015年1月28日下午1:57:34
	 * @param dbOutputOrInputInfos
	 * @param equipmentCode
	 * @return
	 */
	public static String getDbOutputId(
			Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos,String equipmentCode) {
		 DbOutputOrInputInfos dbOutput = dbOutputOrInputInfos.get(equipmentCode);
		if(dbOutput!=null){
			return dbOutput.getStatOutputId();
		}
		return null;
	}
	
	/**
	 * 获得辅料类别与生产实际消耗数据ID
	 * @author Leejean
	 * @create 2015年1月28日下午1:57:48
	 * @param dbOutputOrInputInfos
	 * @param equipmentCode
	 * @return
	 */
	public static Map<String, String> getType_InputId(
			Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos,String equipmentCode) {
		 DbOutputOrInputInfos dbOutput = dbOutputOrInputInfos.get(equipmentCode);
		if(dbOutput!=null){
			return dbOutput.getType_statInputId();
		}
		return null;
	}
	/**
	 * 根据物料类型获得消耗ID
	 * @author Leejean
	 * @create 2015年1月28日下午2:30:10
	 * @param dbOutputOrInputInfos
	 * @param equipmentCode
	 * @return
	 */
	public static String getInputIdByType(
			Map<String, String> type_InputId,String type) {
		String dbOutput = type_InputId.get(type);
		if(dbOutput!=null){
			return dbOutput;
		}
		return null;
	}
	public static DataHandler getInstance(){
		if (instance == null){
			instance = new DataHandler();
			//初始化计数系数，测试阶段调用以下两行代码，实际情况为工单运行时，push计数系数，设备code相同时覆盖
			//setCalcValueIntoMap("5",new RollerCalcValue(1D, 1D, 1D));
			//setCalcValueIntoMap("35",new PackerCalcValue(0.000001D, 0.000001D, 0.000001D));
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
	 * 实时数据保存
	 * @author Leejean
	 * @create 2015年1月27日下午4:08:26
	 */
	public void saveAllData(){
		statService.saveAllData(NeedData.getInstance().getEqpData()); 
	}
	/**
	 * 故障数据保存
	 * @author luo
	 * @create 2015年9月18日09:15:46
	 */
	public void saveAllErrorData(){
		statService.saveAllErrorData(FaultDataCalc.getInstance().getAllFaultList()); 
	}
	
	
}
