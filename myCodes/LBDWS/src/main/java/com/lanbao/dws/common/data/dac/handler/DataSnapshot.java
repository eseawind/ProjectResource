package com.lanbao.dws.common.data.dac.handler;
import java.util.ArrayList;
import java.util.List;

import com.lanbao.dws.model.wct.dac.EquipmentData;

public class DataSnapshot {

	private static DataSnapshot instance = null;
	private List<EquipmentData> eqpData;// 全局变量 供大家使用
	private DataSnapshot() {
		eqpData = new ArrayList<EquipmentData>();
	}
	
	public static DataSnapshot getInstance(){
		if (instance == null){
			instance = new DataSnapshot();
		}
		return instance;
	}
	
	/**
	 * 【功能说明】：通过当前Id-5  取出前一次的设备数据
	 * @createTime  2015年10月19日17:34:29
	 * @author wanchanghuang
	 * */
	public EquipmentData getEquipmentData(int eqp){
		for(int i=0;i<eqpData.size();i++){
			if (eqpData.get(i).getEqp()==eqp){
				return eqpData.get(i);
			}
		}
		return null;
	}
	
	public void setEquipmentData(EquipmentData data){
		boolean b = false;
		for(int i=0;i<eqpData.size();i++){
			if (eqpData.get(i).getEqp()==data.getEqp()){
				b = true;
				eqpData.set(i, data);
			}
		}
		if(!b){
			addEquipmentData(data);
		}
	}
	
	public void addEquipmentData(EquipmentData data){
		if(getEquipmentData(data.getEqp())==null){
			eqpData.add(data);
		}
	}

	public List<EquipmentData> getEqpData() {
		return eqpData;
	}
}
