package com.lanbao.dws.common.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanbao.dws.model.combobox.MdEquipment;
import com.lanbao.dws.model.combobox.MdFixCode;
import com.lanbao.dws.model.combobox.MdMat;
import com.lanbao.dws.model.combobox.MdMatGrp;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.pddisplay.MdMatParamBean;
import com.lanbao.dws.model.wct.pddisplay.WorkOrderBean;
/**
 * 下拉框数据定义类
 */
public class BaseParams {
	/**
	 * 所有设备
	 */
	private static List<MdEquipment> eqps=new ArrayList<>();
	/**
	 * 所有卷烟机
	 */
	private static List<MdEquipment> rollerEqps=new ArrayList<>();
	/**
	 * 所有包装机
	 */
	private static List<MdEquipment> packerEqps=new ArrayList<>();
	/**
	 * 所有封箱机
	 */
	private static List<MdEquipment> FXeqps=new ArrayList<>();
	/**
	 * 所有成型机
	 */
	private static List<MdEquipment> Filtereqps=new ArrayList<>();
	
	/**
	 * 综合基础数据
	 */
	private static List<MdFixCode> fixCode=new ArrayList<>();
	/**
	 * 牌号
	 */
	private static List<MdMat> mat=new ArrayList<>();
	/**
	 * 物料组
	 */
	private static List<MdMatGrp> matGroup=new ArrayList<>();
	
	/**
	 * 保存卷包机DAC换班指令 
	 * */
	private static Map<String,String> dacMap=new HashMap<String, String>();
	
	/**
	 * 保存成型机DAC换班指令 
	 * */
	private static Map<String,String> filterDacMap=new HashMap<String, String>();
	
	
	/** 设备保养工单用户登录信息 */
	private static Map<String,EqmByLoginBean> mapebb=new HashMap<String, EqmByLoginBean>();
	
	
	
	
	
	public static Map<String, String> getFilterDacMap() {
		return filterDacMap;
	}
	public static void setFilterDacMap(Map<String, String> filterDacMap) {
		BaseParams.filterDacMap = filterDacMap;
	}
	public static Map<String, String> getDacMap() {
		return dacMap;
	}
	public static void setDacMap(Map<String, String> dacMap) {
		BaseParams.dacMap = dacMap;
	}
	public static List<MdEquipment> getEqps() {
		return eqps;
	}
	public static void setEqps(List<MdEquipment> eqps) {
		BaseParams.eqps = eqps;
	}
	public static List<MdEquipment> getRollerEqps() {
		return rollerEqps;
	}
	public static void setRollerEqps(List<MdEquipment> rollerEqps) {
		BaseParams.rollerEqps = rollerEqps;
	}
	public static List<MdEquipment> getPackerEqps() {
		return packerEqps;
	}
	public static void setPackerEqps(List<MdEquipment> packerEqps) {
		BaseParams.packerEqps = packerEqps;
	}
	public static List<MdEquipment> getFXeqps() {
		return FXeqps;
	}
	public static void setFXeqps(List<MdEquipment> fXeqps) {
		FXeqps = fXeqps;
	}
	public static List<MdEquipment> getFiltereqps() {
		return Filtereqps;
	}
	public static void setFiltereqps(List<MdEquipment> filtereqps) {
		Filtereqps = filtereqps;
	}
	public static List<MdFixCode> getFixCode() {
		return fixCode;
	}
	public static void setFixCode(List<MdFixCode> fixCode) {
		BaseParams.fixCode = fixCode;
	}
	public static List<MdMat> getMat() {
		return mat;
	}
	public static void setMat(List<MdMat> mat) {
		BaseParams.mat = mat;
	}
	public static List<MdMatGrp> getMatGroup() {
		return matGroup;
	}
	public static void setMatGroup(List<MdMatGrp> matGroup) {
		BaseParams.matGroup = matGroup;
	}
	public static Map<String, EqmByLoginBean> getMapebb() {
		return mapebb;
	}
	public static void setMapebb(Map<String, EqmByLoginBean> mapebb) {
		BaseParams.mapebb = mapebb;
	}
	
	
	
}
