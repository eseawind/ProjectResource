package com.shlanbao.tzsc.data.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * [功能说明]：接收MES端发送的数据，并解析
 * @author wanchanghuang
 * @createTime 2016年4月26日14:05:01
 * @param  type 类型    str-xml数据
 * @return String  1-成功  2-失败
 * 
 * */
@WebService
public interface WebServiceDemoI {

	@WebMethod
	public String mesDataReceive(String type,String str);
}
