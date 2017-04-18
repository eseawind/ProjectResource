package com.lanbao.dws.common.data.webservices.client;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lanbao.dws.common.tools.StringUtil;

import jysc.webservice.pmsClient.WctWebServiceI;
import jysc.webservice.pmsClient.WctWebServiceIpmlService;

/**
 * 
 * @author Administrator
 *
 */

public class SendMessageToJyscClient {
	
	
	private SendMessageToJyscClient() {
		super();
	}


	private static SendMessageToJyscClient instance=null;

	public static SendMessageToJyscClient getInstance(){
		if(instance==null){
			instance=new SendMessageToJyscClient();
		}
		return instance;
	}
	/**
	 * [功能说明]：LBDAWS系统调用JYSC系统，使用PMS和WCT专有WebService通信
	 * 
	 * */
	public  void SendMessageToJysc(String xml,String type){
        try {
			WctWebServiceIpmlService factory = new WctWebServiceIpmlService();
			WctWebServiceI wsImpl = factory.getWctWebServiceIpmlPort();
			if(type.equalsIgnoreCase("SendOrderStatusChange")){
				wsImpl.wctSendOrderStatusChanges(xml);
			}else if(type.equalsIgnoreCase("SendEqpRealTimeData")){
				wsImpl.wctSendEqpRealTimeData(xml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * wct向PMS发送工单状态变化信息
	 * @param orders
	 * @param status
	 */
	public  void sendOrderStatusChange(List<Map<String,Object>> orders,String status){
		try {
			Document doc=DocumentHelper.createDocument();
			Element root=doc.addElement("xml");
			Element body=root.addElement("Body");
			for (Map<String, Object> map : orders) {
				Element orderInfo=body.addElement("OrderInfo");
				Element oid=orderInfo.addElement("oid");
				oid.addText(StringUtil.convertObjToString(map.get("id")));
				Element sts=orderInfo.addElement("Status");
				sts.setText(status);
			}
			SendMessageToJysc(doc.asXML(),"SendOrderStatusChange");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
