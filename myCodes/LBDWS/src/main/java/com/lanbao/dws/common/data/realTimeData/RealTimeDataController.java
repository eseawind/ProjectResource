package com.lanbao.dws.common.data.realTimeData;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.dac.BoxerData;
import com.lanbao.dws.model.wct.dac.EquipmentData;
import com.lanbao.dws.model.wct.dac.FilterData;
import com.lanbao.dws.model.wct.dac.LauncherData;
import com.lanbao.dws.model.wct.dac.PackerData;
import com.lanbao.dws.model.wct.dac.RollerData;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.pdStat.EqpRuntime;
import com.lanbao.dws.model.wct.pdStat.InputPageModel;
import com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService;


/**
 * 系统管理主控制层
 * @author shisihai
 */
@Controller
@RequestMapping("wct/realTimeData")
public class RealTimeDataController {
	@Autowired
	private IRealTimeDataService realTimeDataService;

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 上午9:44:41 
	* 功能说明 ：获取卷烟机实时数据
	 */
	@ResponseBody
	@RequestMapping("/getRealTimeRollerData")
	public String getResultPagePath(HttpSession session){
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		if(loginBean==null){
			return GsonUtil.bean2Json(new RollerData());	
		}
		String equipmentCode=loginBean.getRollerEquipmentCode();
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return GsonUtil.bean2Json(realTimeDataService.getRollerData(equipmentCode));
		}else{
			return GsonUtil.bean2Json(new RollerData());	
		}
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 上午9:56:03 
	* 功能说明 ：获取包装机实时数据
	 */
	@ResponseBody
	@RequestMapping("/getRealTimePackerData")
	public String getPackerData(HttpSession session){
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		if(loginBean==null){
			return GsonUtil.bean2Json(new PackerData());
		}
		String equipmentCode=loginBean.getPackerEquipmentCode();
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return GsonUtil.bean2Json(realTimeDataService.getPackerData(equipmentCode));
		}else{
			return GsonUtil.bean2Json(new PackerData());
		}
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月3日 下午1:52:58 
	* 功能说明 ：获取发射机实时数据
	 */
	@ResponseBody
	@RequestMapping("/getRealTimeLauncherData")
	public String getRealDataLanuncher(HttpSession session){
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		if(loginBean==null){
			return GsonUtil.bean2Json(new LauncherData());	
		}
		String equipmentCode=null;
		//发射机有三个
		List<LauncherData> launcherDatas=new ArrayList<>();
		equipmentCode=loginBean.getLaunchEquipmentCode0();
		if(StringUtil.notEmpty(equipmentCode)){
			launcherDatas.add(realTimeDataService.getLauncherData(equipmentCode));
		}
		equipmentCode=loginBean.getLaunchEquipmentCode1();
		if(StringUtil.notEmpty(equipmentCode)){
			launcherDatas.add(realTimeDataService.getLauncherData(equipmentCode));
		}
		equipmentCode=loginBean.getLaunchEquipmentCode2();
		if(StringUtil.notEmpty(equipmentCode)){
			launcherDatas.add(realTimeDataService.getLauncherData(equipmentCode));
		}
		return GsonUtil.bean2Json(launcherDatas);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月4日 上午8:59:58 
	* 功能说明 ：封箱机实时数据
	 */
	@ResponseBody
	@RequestMapping("/getRealTimeBoxerData")
	public String getRealDataBoxer(HttpSession session){
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		if(loginBean==null){
			return GsonUtil.bean2Json(new BoxerData());	
		}
		String equipmentCode=null;
		//发射机有三个
		List<BoxerData> boxerDatas=new ArrayList<>();
		equipmentCode=loginBean.getBoxerEquipmentCode0();
		if(StringUtil.notEmpty(equipmentCode)){
			boxerDatas.add(realTimeDataService.getBoxerData(equipmentCode));
		}
		equipmentCode=loginBean.getBoxerEquipmentCode1();
		if(StringUtil.notEmpty(equipmentCode)){
			boxerDatas.add(realTimeDataService.getBoxerData(equipmentCode));
		}
		return GsonUtil.bean2Json(boxerDatas);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月8日 下午1:44:13 
	*功能说明 ：获取成型机实时数据(1,2一个屏)
	 */
	@ResponseBody
	@RequestMapping("/getRealTimeFilterData")
	public String getRealDataFilter(HttpSession session){
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		if(loginBean==null){
			return GsonUtil.bean2Json(new BoxerData());	
		}
		String equipmentCode=null;
		List<FilterData> filterDatas=new ArrayList<>();
		equipmentCode=loginBean.getFilterEquipmentCode0();
		if(StringUtil.notEmpty(equipmentCode)){
			filterDatas.add(realTimeDataService.getFilterData(equipmentCode));
		}
		equipmentCode=loginBean.getFilterEquipmentCode1();
		if(StringUtil.notEmpty(equipmentCode)){
			filterDatas.add(realTimeDataService.getFilterData(equipmentCode));
		}
		return GsonUtil.bean2Json(filterDatas);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 下午4:51:56 
	* 功能说明 ：初始化包装机数据
	 */
	@ResponseBody
	@RequestMapping("/initPackerData")
	public String initPackerData(HttpSession session){
		//获取当前机台的初始化数据
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		return realTimeDataService.initPackerMachine(loginBean);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月25日 下午4:51:40 
	* 功能说明 ：初始化卷烟机数据
	 */
	@ResponseBody
	@RequestMapping("/initRollerData")
	public String initRollerData(HttpSession session){
		//获取当前机台的初始化数据
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		return realTimeDataService.initRollerMachine(loginBean);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月4日 上午9:11:37 
	* 功能说明 ：初始化封箱机数据
	 */
	@ResponseBody
	@RequestMapping("/initBoxerData")
	public String initBoxData(HttpSession session){
		//获取当前机台的初始化数据
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		return realTimeDataService.initBoxerMachine(loginBean);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月8日 下午9:58:23 
	* 功能说明 ：初始化成型机数据
	 */
	@ResponseBody
	@RequestMapping("/initfilterData")
	public String initfilterData(HttpSession session){
		//获取当前机台的初始化数据
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		return realTimeDataService.initFilterMachine(loginBean);
	}
	
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月26日 下午2:09:39 
	* 功能说明 ：获取实时产量信息，根据设备code规则，包装机（包）除以2500得到箱，卷烟机（千支）除以50 得到箱。
	 */
	@RequestMapping("/getOutData")
	@ResponseBody
	public List<EqpRuntime> getOutData(Long type){
		try {
			List<EqpRuntime> list = null;
			if(type==1){
				//获取卷烟机实时产出数据
				list = realTimeDataService.getRollerOutData();
			}			
			if(type==2){
				//获取包装机实时产出数据
				list = realTimeDataService.getPackerOutData();
			}			
			if(type==3){
				//获取封箱机实时产出数据
				list = realTimeDataService.getBoxerOutData();
			}			
			if(type==4){
				//获取成型机实时产出数据
				list = realTimeDataService.getFilterOutData();
			}			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月27日 上午9:20:49 
	* 功能说明 ：获取卷烟机实时辅料数据
	 */
	@RequestMapping("/getRollerInputData")
	@ResponseBody
	public List<InputPageModel> getRollerInputData(){
		return realTimeDataService.getRollerInputData();
	} 
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月27日 上午9:34:20 
	* 功能说明 ：获取包装机实时辅料数据消耗
	 */
	@RequestMapping("/getPackerInputData")
	@ResponseBody
	public List<InputPageModel> getPackerInputData(){
		return realTimeDataService.getPackerInputData();
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月3日 上午9:20:43 
	* 功能说明 ：获取当前所有设备实时数据
	 */
	@RequestMapping("/getAll")
	@ResponseBody
	public List<EquipmentData> getAll(){
		try {
			return realTimeDataService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月3日 上午9:26:09 
	* 功能说明 ：根据设备code 获取该设备的实时数据
	 */
	@RequestMapping("/get")
	@ResponseBody
	public EquipmentData get(String code){
		try {
			return realTimeDataService.get(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new EquipmentData();
	}
	
}
