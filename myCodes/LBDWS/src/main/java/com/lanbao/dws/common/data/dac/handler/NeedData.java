package com.lanbao.dws.common.data.dac.handler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.lanbao.dac.data.CommonData;
import com.lanbao.dws.model.wct.dac.EquipmentData;
/**
 * 业务需要的数据快照，具体过滤规则参考/resources/needDataPoints.properties 文件
 * @author Leejean
 * @create 2015年1月14日下午2:24:13
 */
public class NeedData {
	private static NeedData instance = null;
	private List<EquipmentData> eqpData;
	

	private NeedData() {
		eqpData = new ArrayList<EquipmentData>();
	}
	
	public static NeedData getInstance(){
		if (instance == null){
			instance = new NeedData();
		}
		return instance;
	}
	/**
	 * 估计设备code获取设备数据
	 * @author Leejean
	 * @create 2015年1月14日下午2:23:47
	 * @param eqp
	 * @return
	 */
	public EquipmentData getEquipmentData(int eqp){
		try {
			for(int i=0;i<eqpData.size();i++){
				if (eqpData.get(i).getEqp()==eqp){
					return eqpData.get(i);
				}
			}
		} catch (Exception e) {
			System.out.println("设备空指针异常:"+eqp);
			e.printStackTrace();
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
			eqpData.add(data);
	}

	public List<EquipmentData> getEqpData() {
		return eqpData;
	}
	/**
	 * 估计数据点，获取数据
	 * @author Leejean
	 * @create 2015年1月14日下午2:25:19
	 * @param datas
	 * @param id
	 * @return
	 */
	public String getDataValue(List<CommonData> datas,String id){
		for (CommonData commonData : datas) {
			if(commonData.getId().equals(id)){
				return commonData.getVal();
			}
		}
		return null;
	}
	
	//故障统计 key:设备ID，value:(key:故障描述，value[0,1]:0:故障发生次数、1：故障发生时长)
	private Hashtable<String,Hashtable<String,Double[]>> faultHt;
	
	public Hashtable<String, Hashtable<String, Double[]>> getFaultHt() {
		if(faultHt==null){
			return new Hashtable<String,Hashtable<String,Double[]>>();
		}
		return faultHt;
	}

	public void setFaultHt(Hashtable<String, Hashtable<String, Double[]>> fauleHt) {
		this.faultHt = fauleHt;
	}

	//根据设备ID获取设备故障信息
	public Hashtable<String,Double[]> getFaultByEqpId(String id){
		return faultHt.get(id);
	}

	
	
}
