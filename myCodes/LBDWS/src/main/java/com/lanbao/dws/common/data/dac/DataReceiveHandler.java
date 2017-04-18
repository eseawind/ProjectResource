package com.lanbao.dws.common.data.dac;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.lanbao.dws.common.data.Constants;

public class DataReceiveHandler extends IoHandlerAdapter{

	protected Logger log = Logger.getLogger(this.getClass());
	private List<String> validIPs;
	private static DataReceiveBusiness business = DataReceiveBusiness.getInstance();
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		log.error("Socket连接异常:"+session.getRemoteAddress().toString().split(":")[0].substring(1),cause);
	}
	/**
	 * 服务端发消息到客户端
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}
	/**
	 * [功能说明]：验证链接是否合法
	 *       解析数据前，验证数据链接IP和当前equipment.xml配置文件IP是否一致
	 * 
	 * 
	 * */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		//log.info("接收到客户端数据:" + session.getRemoteAddress());
		String ip = session.getRemoteAddress().toString().split(":")[0].substring(1);
		if(!business.checkIp(validIPs, ip)){
			log.error("来自 " + ip + "的Socket连接地址非法");
		}else{
			//log.error("来自 " + ip + "的Socket连接地址");
			business.updateDataSnapshot(message);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("来自" + session.getRemoteAddress() + "的Socket连接已关闭");
		String ip = session.getRemoteAddress().toString().split(":")[0].substring(1);
		//检测IP是否合法
		if(!business.checkIp(validIPs, ip)){
			log.error("来自 " + ip + "的Socket连接地址非法");
		}else{
			//移除Dac关闭的链接
			Constants.dacMap.remove(ip);
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("有新的连接:" + session.getRemoteAddress());
		String ip = session.getRemoteAddress().toString().split(":")[0].substring(1);
		if(!business.checkIp(validIPs, ip)){
			log.error("来自 " + ip + "的Socket连接地址非法");
		}else{
			//将新监听到的链接放到Map中
			Constants.dacMap.put(ip, session);
		}
	}
	public List<String> getValidIPs() {
		return validIPs;
	}
	public void setValidIPs(List<String> validIPs) {
		this.validIPs = validIPs;
	}
}
