package com.lanbao.dws.common.data.webservices.service;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lanbao.dws.common.data.webservices.client.SendMessageToJyscClient;
import com.lanbao.dws.common.tools.ApplicationContextUtil;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.dac.EquipmentData;
import com.lanbao.dws.model.wct.dac.FilterData;
import com.lanbao.dws.model.wct.dac.PackerData;
import com.lanbao.dws.model.wct.dac.RollerData;
import com.lanbao.dws.model.wct.dac.RollerPackerData;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService;

public class ParsingXmlDataInterceptor {
	private static  ParsingXmlDataInterceptor instance=null;
	
	IRealTimeDataService realTimeDataService;
	private ParsingXmlDataInterceptor() {
		if(null==realTimeDataService){
			realTimeDataService=ApplicationContextUtil.getBean(IRealTimeDataService.class);
		}
	}
	
	public static ParsingXmlDataInterceptor getInstance(){
		if(instance==null){
			instance=new ParsingXmlDataInterceptor();
		}
		
		return instance;
	}
	/**
	 * PMS系统发送刷新设备参数和初始化数据
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月16日 上午11:43:35 
	* 功能说明 ：
	 */
	public void alalyReFrushEqpParamAndInitData(Document fdoc) {
		try {
			InitComboboxData comboboxs=ApplicationContextUtil.getBean(InitComboboxData.class);
			comboboxs.initCombobox();
			comboboxs.initWorkOrderInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取机台实时数据
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月16日 下午1:30:26 
	* 功能说明 ：code=-1 所有数据    code>0 当前设备   code =-2  卷包机组数据
	 */
	public void alalyGetEqpDatas(Document fdoc) {
		String result="";
	    Element eqpNode=(Element) fdoc.selectSingleNode("//xml/Body/EqpCode");
	    Element typeNode=(Element) fdoc.selectSingleNode("//xml/Body/Type");
	    Element keyNode=(Element) fdoc.selectSingleNode("//xml/Body/Key");
	    String key=keyNode.getTextTrim();//请求的PMS当前会话id
	    long eqpCode=StringUtil.converObj2Long(eqpNode.getTextTrim());
	    String typeCodeStr=typeNode.getTextTrim();
	    long typeCode=StringUtil.converObj2Long(typeCodeStr);
	    if(typeCode==1){
	    	 if(eqpCode==-1){
	 	    	//所有数据
	 	    	List<EquipmentData> eqpDatas=realTimeDataService.getAll();
	 	    	result=GsonUtil.bean2Json(eqpDatas);
	 	    }else if(eqpCode>0){
	 	    	//当前设备
	 	    	EquipmentData eqpData=realTimeDataService.get(String.valueOf(eqpCode));
	 	    	result=GsonUtil.bean2Json(eqpData);
	 	    }else if(eqpCode==-2){
	 	    	//卷包机组数据
	 	    	List<RollerPackerData> eqpData=realTimeDataService.getAllRollerPackerDatas();
	 	    	result=GsonUtil.bean2Json(eqpData);
	 	    }
	    }else if(typeCode==2){
	    	if(eqpCode>0 && eqpCode<31){
	    		//卷烟机
	    		RollerData rolerData=realTimeDataService.getRollerData(String.valueOf(eqpCode));
	    		result=GsonUtil.bean2Json(rolerData);
	    	}else if(eqpCode>30 && eqpCode<61){
	    		//包装机
	    		PackerData packData=realTimeDataService.getPackerData(String.valueOf(eqpCode));
	    		result=GsonUtil.bean2Json(packData);
	    	}else if(eqpCode>100 && eqpCode<131){
	    		//成型机
	    		FilterData filterData=realTimeDataService.getFilterData(String.valueOf(eqpCode));
	    		result=GsonUtil.bean2Json(filterData);
	    	}
	    }
	  
	    //将查询结果数据封装成XML返回
	    Document doc=DocumentHelper.createDocument();
	    Element root = doc.addElement("xml");
		Element body =  root.addElement("Body");
		Element datas=body.addElement("Datas");
		//返回PMS会话id，用于PMS保存该数据Key
		Element pmsKey =  body.addElement("Key");
		Element type=body.addElement("Type");//数据类型   1  未处理数据    2处理后数据
		type.setText(typeCodeStr);
		pmsKey.setText(key);
		datas.addText(result);
		SendMessageToJyscClient.getInstance().SendMessageToJysc(doc.asXML(), "SendEqpRealTimeData");
	}
	
	
}
