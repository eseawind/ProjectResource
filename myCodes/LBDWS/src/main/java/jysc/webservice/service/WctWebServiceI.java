package jysc.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 	用于WCT与PMS系统信息通信
 * */
@WebService
public interface WctWebServiceI {

	/**
	 * pms向WCT系统发送刷新设备滚轴系数和combobox信息
	 * @param strXml
	 * @return
	 */
	@WebMethod
    public String pmsSendRefrushEqpParams(String strXml);
	/**
	 * WCT向pms发送请求的设备实时数据
	 * @param strXml
	 * @return
	 */
	@WebMethod
    public String wctSendEqpRealTimeData(String strXml);
	
	
	
    
}
