package com.lanbao.dws.common.data.dac.handler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.lanbao.dac.data.CommonData;
import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;

/**
 * 设备故障停机时间和次数统计
 * @author luo
 *
 */
public class FaultDataCalc extends Thread{
	private static FaultDataCalc fauleData;
	//private static Hashtable<String,String> gd_Compile;
	//保存当前设备运行的班次，来源于DAC采集数据
	private Hashtable<Integer,String> currentEqpShift=new Hashtable<Integer,String>();;
	private Queue<Map<String, CommonData>> queue = new LinkedList<Map<String, CommonData>>(); 
	private Logger log = Logger.getLogger(this.getClass());
	//实例化
	public static FaultDataCalc getInstance() {
		if (fauleData == null) {
			fauleData = new FaultDataCalc();
			/*try{
				//如果初始化错误，则new一个对象
				gd_Compile=ConfigUtil.readTxtFile();
			}catch(Exception e){
				gd_Compile=new Hashtable<String,String>();
			}*/
		}
		return fauleData;
	}
	
	public Hashtable<Integer, String> getCurrentEqpShift() {
		return currentEqpShift;
	}

	public void setCurrentEqpShift(Hashtable<Integer, String> currentEqpShift) {
		this.currentEqpShift = currentEqpShift;
	}
	
	public Queue<Map<String, CommonData>> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Map<String, CommonData>> queue) {
		this.queue = queue;
	}

	@Override
	public void run(){
		while(queue.peek()!=null){
			Map<String, CommonData> dataMap=queue.poll();
			if(dataMap!=null && dataMap.size()>0){
				setFauleData(dataMap);
			}
		}
	}
	
	//汇总故障数据
	public void setFauleData(Map<String, CommonData> dataMap){
		int equipmentID=0;
		try{
			equipmentID = Integer.valueOf(dataMap.get("EquipmentID").getVal());//设备
			String type = dataMap.get("TYPE").getVal().toString();
			String shiftId = dataMap.get("SHIFTINFO").getVal().toString();
			if(equipmentID<1||type.equals("null")||shiftId.equals("null")){
				log.info("设备Code："+equipmentID+",设备类型："+type+",班次:"+shiftId);
			}else if( type.toUpperCase().equals("ZJ17") ){
				//设备类型判断必须要与DAC一致
				//String[] fauleDetail=new String[3];
				StringBuffer[] fauleDetail = new StringBuffer[3];
				//卷烟机 
				//卷烟机85-114点位为故障点位，其中3个为一组。例如85：故障名称，86：停机时长(停机时长格式为01:50，需转换成Integer)，87：停机次数
				for(int i=85;i<115;i=i+3){
					for(int j=0;j<3;j++){
						if(fauleDetail[j]!=null){
							fauleDetail[j].setLength(0);
						}else{
							fauleDetail[j] = new StringBuffer();
						}
						CommonData data=dataMap.get(String.valueOf(i+j));
						if(data!=null){
							String val=data.getVal();
							if(val!=null&&StringUtil.notNull(val)&&!val.equals("null")){
								fauleDetail[j].append(val.trim());
							}else{
								break;
							}
						}
					}
					if(fauleDetail[0]==null||fauleDetail[1]==null||fauleDetail[2]==null||fauleDetail[0].equals("0")){
						continue;
					}
					try{
						String[] time=fauleDetail[1].toString().split(":");
						if(time.length==2){
							fauleDetail[1].setLength(0);
							fauleDetail[1].append(String.valueOf((Integer.parseInt(time[1])+Integer.parseInt(time[0])*60)));
							setFaultByEqpId(String.valueOf(equipmentID), fauleDetail[0].toString(), fauleDetail[2].toString(), fauleDetail[1].toString(),shiftId.trim());
						}
					}catch(Exception ex){
						log.info("设备Code："+equipmentID+",故障名称："+fauleDetail[0]+",故障时间:"+fauleDetail[1]+",故障次数:"+fauleDetail[2]);
					}
				}
			}else if( type.toUpperCase().equals("ZB45") ){
				//ZB25包装机 故障内容保存在8号点位表中
				CommonData data=dataMap.get("8");
				if(data!=null&&data.getVal()!=null&&StringUtil.notNull(data.getVal().trim())&&!data.getVal().trim().equals("null")){
					String faultString=data.getVal().trim();
					String[] fault=null;
					if(faultString.indexOf("&")>0){
						fault=data.getVal().split("&");
					}else{
						fault=new String[]{faultString};
					}
					for(String temp:fault){
						String[] fauleDetail=temp.split(":");
						if(fauleDetail.length!=3){
							continue;
						}
						String name=StringUtil.notNull(fauleDetail[0])?fauleDetail[0].trim():"";
						//由于包装机上传的停机时长单位是秒，所以包装机需要除以60
						double faultTime=MathUtil.roundHalfUp(Double.parseDouble(fauleDetail[1].trim())/60.00,2);
						setFaultByEqpId(String.valueOf(equipmentID), name, fauleDetail[2].trim(), String.valueOf(faultTime),shiftId.trim());
					}
				}
			}
			currentEqpShift.put(equipmentID, shiftId);
		}catch(Exception ex){
			log.error("设备ID："+equipmentID+"故障统计出错",ex);
		}
	}
	/**
	 * 跟新设备故障
	 * @param eid 设备ID
	 * @param faultName 故障名称
	 * @param fauleNumber 故障发生次数
	 * @param fauleTime 故障发生时长
	 * @param shiftId 班次ID
	 */
	public void setFaultByEqpId(String eid,String faultName,String fauleNumber,String fauleTime,String shiftId){
		if(StringUtil.notNull(eid)&&StringUtil.notNull(faultName)&&StringUtil.notNull(fauleNumber)&&StringUtil.notNull(fauleTime)&&StringUtil.notNull(shiftId)){
			//后去当前设备的班次
			String oldShift=currentEqpShift.get(eid);
			//如果当前设备在一个班次上运行，并且当前运行的班次和DAC传过来的班次不一致，则清空当前设备的故障信息
			if(StringUtil.notNull(oldShift)&&!oldShift.equals(shiftId)){
				//保存换班前的数据，然后remove
				this.removeFaultListByEid(eid);
			}
			Hashtable<String,Hashtable<String,Double[]>> ht=NeedData.getInstance().getFaultHt();
			Hashtable<String,Double[]> eqpHt=ht.get(eid);
			if(eqpHt==null){
				eqpHt=new Hashtable<String,Double[]>();
			}
			Double[] faultNum=eqpHt.get(faultName);
			if(faultNum==null){
				faultNum=new Double[2];
			}
			if(StringUtil.isDouble(fauleNumber)&&StringUtil.isDouble(fauleTime)){
				faultNum[0]=Double.valueOf(fauleNumber);
				faultNum[1]=Double.valueOf(fauleTime);
				eqpHt.put(faultName, faultNum);
				ht.put(eid, eqpHt);
				NeedData.getInstance().setFaultHt(ht);
			}else{
				log.error("设备ID："+eid+"设备停机时间("+fauleTime+"),或停机次数("+fauleNumber+"),数据错误");
			}
		}
	}
	
	//根据设备ID获取设备故障集合，String[] //0:机台ID，1:班次，2:故障描述,3:次数,4:故障发生总时长
	public List<String[]> getFaultList(String eid){
		if(NeedData.getInstance().getFaultHt()==null)
			return null;
		Hashtable<String,Double[]> ht=NeedData.getInstance().getFaultHt().get(eid);
		if(ht!=null){
			List<String[]> list=new ArrayList<String[]>();
			for(Iterator it=ht.keySet().iterator();it.hasNext();){ 
				//key 故障描述
                String key=(String)it.next();  
                //value 0:故障次数，1:故障发生时长
                Double[] value=ht.get(key); 
                //根据设备ID获取班次信息 
                String shiftId=currentEqpShift.get(Integer.parseInt(eid));
                if(StringUtil.notNull(shiftId)){
                	//0:机台ID，1:班次，2:故障描述,3:次数,4:故障发生总时长
                    String[] temp={eid,shiftId,key,String.valueOf(value[0]),String.valueOf(value[1])};
                    list.add(temp);
                }else{
                	log.equals("获取设备故障信息出错,原因:根据设备ID："+eid+",无法获取班次信息。");
                }
            }  
			return list;
		}else{
			return null;
		}
	}
	//获取所有设备故障集合 0:机台ID，1:故障描述,2:次数,3:故障发生总时长
	public List<String[]> getAllFaultList(){
		Hashtable<String,Hashtable<String,Double[]>> ht=NeedData.getInstance().getFaultHt();
		if(ht==null){
			return null;
		}else{
			List<String[]> list=new ArrayList<String[]>();
			for(Iterator its=ht.keySet().iterator();its.hasNext();){  
				//key 设备ID
                String key=(String)its.next();   
                Hashtable<String,Double[]> value=ht.get(key);   
                for(Iterator i=value.keySet().iterator();i.hasNext();){  
                	//key 故障描述
                    String keys=(String)i.next();   
                    //value 0:故障次数，1:故障发生时长
                    Double[] v=value.get(keys);   
                    //根据设备ID获取班次信息 
                    String shiftId=currentEqpShift.get(Integer.parseInt(key));
                    if(StringUtil.notNull(shiftId)){
                    	//0:机台ID，1:班次，2:故障描述,3:次数,4:故障发生总时长
                        String[] temp={key,shiftId,keys,String.valueOf(v[0]),String.valueOf(v[1])};
                        list.add(temp);
                    }else{
                    	log.equals("获取设备故障信息出错,原因:根据设备ID："+key+",无法获取班次信息。");
                    }
                }  
            }  
			return list;
		}
	}
	//初始化故障集合
	public void removeFaultList(){
		NeedData.getInstance().setFaultHt(null);
	}
	//清空某一个设备的故障集合
	public void removeFaultListByEid(String eid){
		NeedData.getInstance().getFaultHt().remove(eid);
		/*Hashtable<String,Hashtable<String,Double[]>> ht=NeedData.getInstance().getFaultHt();
		ht.remove(eid);
		NeedData.getInstance().setFaultHt(ht);*/
	}
	
}
