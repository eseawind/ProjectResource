package com.lanbao.dws.service.wct.realTimeData;

import java.util.List;

import com.lanbao.dws.model.wct.dac.BoxerData;
import com.lanbao.dws.model.wct.dac.EquipmentData;
import com.lanbao.dws.model.wct.dac.FilterData;
import com.lanbao.dws.model.wct.dac.LauncherData;
import com.lanbao.dws.model.wct.dac.OutputBean;
import com.lanbao.dws.model.wct.dac.PackerData;
import com.lanbao.dws.model.wct.dac.RollerData;
import com.lanbao.dws.model.wct.dac.RollerPackerData;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.pdStat.EqpRuntime;
import com.lanbao.dws.model.wct.pdStat.InputPageModel;

public interface IRealTimeDataService {

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 上午9:46:13 
	* 功能说明 ：获取卷烟机实时数据
	 */
	RollerData getRollerData(String equipmentCode);

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 上午9:46:26 
	* 功能说明 ：获取包装机实时数据
	 */
	PackerData getPackerData(String equipmentCode);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月3日 下午1:54:15 
	* 功能说明 ：获取发射机实时数据
	 */
	LauncherData getLauncherData(String equipmentCode);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 下午2:27:10 
	* 功能说明 ：初始化卷烟机机台信息
	 */
	String initRollerMachine(LoginBean loginBean);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 下午4:38:36 
	* 功能说明 ：初始化包装机数据
	 */
	String initPackerMachine(LoginBean loginBean);

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:28:33 
	* 功能说明 ：获取卷烟机实时产量信息
	 */
	List<EqpRuntime> getRollerOutData();
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:35:34 
	* 功能说明 ：获取包装机
	 */
	List<EqpRuntime> getPackerOutData();
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:36:10 
	* 功能说明 ：获取封箱机实时产量
	 */
	List<EqpRuntime> getBoxerOutData();
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:36:40 
	* 功能说明 ：获取成型机实时产量
	 */
	List<EqpRuntime> getFilterOutData();

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月27日 上午9:24:05 
	* 功能说明 ：获取卷烟机辅料实时消耗
	 */
	List<InputPageModel> getRollerInputData();

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月27日 上午9:34:50 
	* 功能说明 ：获取包装机实时辅料消耗
	 */
	List<InputPageModel> getPackerInputData();
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午11:14:24 
	* 功能说明 ：实时保存产量和消耗数据
	 */
	void saveAllData(List<EquipmentData> eqpData);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 上午11:17:40 
	* 功能说明 ：保存单个机台实时数据		说明 当工单完成时，需要调用本法方，且 data 参数不给值
	 */
	public void saveEquipmentData(String equipmentCode, Object... data);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午5:15:26 
	* 功能说明 ：修改工单状态为运行时，新建产出数据
	 */
	int addOutput(OutputBean outputBean,int flag);

	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月3日 上午9:21:18 
	* 功能说明 ：获取当前所有设备的实时数据
	 */
	List<EquipmentData> getAll();

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月3日 上午9:28:11 
	* 功能说明 ：获取指定设备的实时数据
	 */
	EquipmentData get(String code);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月4日 上午9:02:10 
	* 功能说明 ：获取封箱机实时数据
	 */
	BoxerData getBoxerData(String equipmentCode);

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月4日 上午9:12:27 
	* 功能说明 ：初始化封箱机数据
	 */
	String initBoxerMachine(LoginBean loginBean);

	

	/**
	 * [共能说明]：定时结束上一班次工单，启动当前所有工单
	 * @author wanchanghuang
	 * @date 2016年8月4日11:26:40
	 * */
	void edWorkOrder();
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月8日 下午3:39:53 
	* 功能说明 ：获取成型机实时数据
	 */
	FilterData getFilterData(String equipmentCode);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月8日 下午9:59:30 
	* 功能说明 ：初始化成型机数据
	 */
	String initFilterMachine(LoginBean loginBean);
	/**
	 * PMS系统获取卷包机组产量信息（实时数据监控页面）
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月16日 下午4:55:51 
	* 功能说明 ：
	 */
	List<RollerPackerData> getAllRollerPackerDatas();
	
	
	/**
	 * <p>功能描述：每隔3s保存一次机台的实时数据，用于监控分析。只保持一天数据</p>
	 *shisihai
	 *2016上午10:02:36
	 */
	void saveRealTimeDataTimer();
	/**
	 * <p>功能描述：删除昨天的实时数据</p>
	 *shisihai
	 *2016上午10:02:41
	 */
	void deleteRealTimeData();

	void saveAllErrorData(List<String[]> allFaultList);
}
