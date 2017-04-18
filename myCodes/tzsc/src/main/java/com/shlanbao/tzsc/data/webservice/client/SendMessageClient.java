package com.shlanbao.tzsc.data.webservice.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.tempuri.WebService1Soap;

public class SendMessageClient {

	public SendMessageClient() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 功能说明：向MES发送反馈信息
	 * @param  type-xml数据类型
	 * @param   xml-xml数据
	 * @return  ""-成功   不等于空表示-失败
	 * //MessageType --- MessageSchema  ---- the same
		//SDCSParameterBaseData 质量检验数据
	    //SDCSMaterialConsumptionDownload  辅料物耗转发
		//SDCSParameterBaseData 质量检验数据
		//SDCStartEntry   开启工单   一天一个工单
        //SDCSFinishEntry 完成工单
        //SDCSShiftChange 换班
        //SDCSShiftStart  换班后开始  一天三个班
        //SDCSFinishedEntryInfo 工单实绩信息(工单实际反馈)
        //SDCSEntryPause 工单暂停
        //SDCSEntryRestart 工单暂停后启动
	 * 
	 * */
    public String SendMessageToMes(String type,String xml){
    	//向MES发送
		String strConnectorName = "SDCS";  //Web Service Connector名称，在DIS中配置的连接器名称
		//String strDisServerHost = "MESDIS"; //DIS 服务器地址名称，需在C:\WINDOWS\system32\drivers\etc\hosts文件中配置DIS服务器IP地址与名称的对应关系
		String strDisServerHost = "TZDIS";
		String strMessage = xml;  // xml
		String strMessageType = type;  //文件类型
		String strMessageSchema = type; //Message schema，消息主题，与Type一起标
		String address = "http://10.114.94.103:82/WebService1.asmx";
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
		bean.setAddress(address);
		bean.setServiceClass(WebService1Soap.class);
		WebService1Soap ws = (WebService1Soap) bean.create();
		String str=ws.sendMessage(strConnectorName, strDisServerHost, strMessage, strMessageType, strMessageSchema, "");
		//System.out.println(str);
    	return str;
    }
}
