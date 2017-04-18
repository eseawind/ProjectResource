package jysc.webservice.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.lanbao.dws.common.data.webservices.service.ParsingXmlDataInterceptor;


@WebService
public class WctWebServiceIpml implements WctWebServiceI{
	//使用线程池处理消息
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
	
	/**
	 *pms发送WCT 刷新设备滚轴系数和WCT中combobox数据
	 */
	@Override
	public String pmsSendRefrushEqpParams(String strXml) {
		System.out.println("========刷新设备滚轴系数和WCT中combobox数据===================");
    	//System.out.println(strXml);
    	pmsDataReceive("PMSSendRefrushEqpParams",strXml);
    	return null;
	}

	
	/**
	 * wct向PMs发送设备实时数据
	 */
	@Override
	public String wctSendEqpRealTimeData(String strXml) {
		System.out.println("=======wct向PMs发送设备实时数据===================");
    	//System.out.println(strXml);
    	pmsDataReceive("WCTSendEqpRealTimeData",strXml);
    	return null;
	}
	
	
	
	//线程处理webService信息
	public String pmsDataReceive(String type,String str) {
		str=str.trim();//去除字符串两边空格
		Document doc=null;
		//XML空判断
		if("".equals(str)){return "ERROR:数据异常..."+type+str;} 
		//将xml字符串转换成document
		try {
			doc=DocumentHelper.parseText(str);
		} catch (Exception e) {
		//	ParsingXmlDataInterceptor.getInstance().addLog("XML字符串转换Document对象异常！请检查XML数据格式是否规范",0,str,e.getMessage());
			System.out.println("ERROR:XML字符串转换Document对象异常..."+str);
			e.printStackTrace();
		}
		//线程处理
		final Document fdoc=doc;
		final String  ftype=type;
	    cachedThreadPool.execute(new Runnable() {  
			public void run() {
				if("PMSSendRefrushEqpParams".equals(ftype)){
					/**刷新设备滚轴系数和WCT中combobox数据 */
				     ParsingXmlDataInterceptor.getInstance().alalyReFrushEqpParamAndInitData(fdoc);
				}else if("WCTSendEqpRealTimeData".equals(ftype)){
					/**wct向PMs发送设备实时数据 */
					 ParsingXmlDataInterceptor.getInstance().alalyGetEqpDatas(fdoc);
				}
		    }
	    });
	    return "";
	}
}
