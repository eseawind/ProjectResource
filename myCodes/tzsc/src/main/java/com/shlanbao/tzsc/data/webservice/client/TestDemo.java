package com.shlanbao.tzsc.data.webservice.client;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.tempuri.WebService1Soap;

public class TestDemo {
	
	public static void main(String[] args) throws RemoteException, ServiceException {
		//向MES发送
		String strConnectorName = "SDCS";  //Web Service Connector名称，在DIS中配置的连接器名称
		String strDisServerHost = "MESDIS"; //DIS 服务器地址名称，需在C:\WINDOWS\system32\drivers\etc\hosts文件中配置DIS服务器IP地址与名称的对应关系
		String strMessage = "1222";  // xml
		String strMessageType = "SDCStartEntry";  //文件类型
		String strMessageSchema = "SDCStartEntry"; //Message schema，消息主题，与Type一起标
		String address = "http://10.114.81.103:82/WebService1.asmx";
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
		bean.setAddress(address);
		bean.setServiceClass(WebService1Soap.class);
		WebService1Soap ws = (WebService1Soap) bean.create();
		String str=ws.sendMessage(strConnectorName, strDisServerHost, strMessage, strMessageType, strMessageSchema, "");
		System.out.println(str);
	}

}
