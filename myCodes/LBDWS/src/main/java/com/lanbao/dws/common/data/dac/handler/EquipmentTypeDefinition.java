package com.lanbao.dws.common.data.dac.handler;

import java.util.ArrayList;
import java.util.List;
/**
 * 设备类型与设备型号定义
 * @author Leejean
 * @create 2015年1月16日上午9:23:05
 */
public class EquipmentTypeDefinition {
	/**
	 * 卷烟机类型集合
	 */
	private static List<String> roller = new ArrayList<String>();
	/**
	 * 包装机集合
	 */
	private static List<String> packer = new ArrayList<String>();
	/**
	 * 成型机集合
	 */
	private static List<String> filter = new ArrayList<String>();
	/**
	 * 封箱机集合
	 */
	private static List<String> boxer = new ArrayList<String>();
	/**
	 * 发射机集合
	 */
	private static List<String> shooter = new ArrayList<String>();
	
	/**
	 * 卸盘机
	 */
	private static List<String> shuter = new ArrayList<String>();
	/**
	 * 
	 */
	private static EquipmentTypeDefinition instance = null;
	private EquipmentTypeDefinition() {
		//卷烟机
		roller.add("ZJ17");
		roller.add("ZJ19");
		roller.add("PASSIM");
		roller.add("PROTOS70");
		
		//包装机
		packer.add("GDX2");
		packer.add("ZB45");
		packer.add("ZB25");
		packer.add("GD");
		//成型机
		filter.add("ZL26C");
		filter.add("ZL22C");
		
		//装箱机
		boxer.add("YP18");
		boxer.add("YP19");
		boxer.add("YP11A");
		boxer.add("YP18B");
		
		//发射机
		shooter.add("YF25");
		
		//卸盘机
		shuter.add("YB17B");
		//装盘机
		shuter.add("YJ35D");
	}
	private static void init(){
		if(instance==null){
			instance = new EquipmentTypeDefinition();
		}
	}
	public static List<String> getRoller() {
		init();
		return roller;
	}
	public static void setRoller(List<String> roller) {
		EquipmentTypeDefinition.roller = roller;
	}

	public static List<String> getPacker() {
		init();
		return packer;
	}

	public static void setPacker(List<String> packer) {
		EquipmentTypeDefinition.packer = packer;
	}
	public static List<String> getFilter() {
		init();
		return filter;
	}
	public static void setFilter(List<String> filter) {
		EquipmentTypeDefinition.filter = filter;
	}
	public static List<String> getBoxer() {
		init();
		return boxer;
	}
	public static void setBoxer(List<String> boxer) {
		EquipmentTypeDefinition.boxer = boxer;
	}
	public static List<String> getShooter() {
		init();
		return shooter;
	}
	public static void setShooter(List<String> shooter) {
		EquipmentTypeDefinition.shooter = shooter;
	}
	public static EquipmentTypeDefinition getInstance() {
		return instance;
	}
	public static void setInstance(EquipmentTypeDefinition instance) {
		EquipmentTypeDefinition.instance = instance;
	}
	public static List<String> getShuter() {
		return shuter;
	}
	public static void setShuter(List<String> shuter) {
		EquipmentTypeDefinition.shuter = shuter;
	}
	
	
}
