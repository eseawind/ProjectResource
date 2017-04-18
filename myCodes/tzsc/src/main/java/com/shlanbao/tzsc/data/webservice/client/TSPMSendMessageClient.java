package com.shlanbao.tzsc.data.webservice.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.tspm.RECDASServiceSoap;

public class TSPMSendMessageClient {

	public TSPMSendMessageClient() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 功能说明：向TSPM发送 设备故障信息 
	 * @author Administrator
	 * @date 2016年11月25日6:02:09
	 * 
	 * */
    public String SendMessageToTSPM(String xml){
		String address = "http://10.114.81.244:6666/DAS/DAS_REC/REC_DAS_Service.asmx";
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
		bean.setAddress(address);
		bean.setServiceClass(RECDASServiceSoap.class);
		try {
			RECDASServiceSoap ws = (RECDASServiceSoap) bean.create();
			ws.executeXMLDAS(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "";
    }
}
