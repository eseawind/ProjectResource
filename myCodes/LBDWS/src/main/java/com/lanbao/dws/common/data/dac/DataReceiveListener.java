package com.lanbao.dws.common.data.dac;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import com.lanbao.dws.common.tools.XmlUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DataReceiveListener implements ServletContextListener{
	protected Logger log = Logger.getLogger(this.getClass());
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//获得本地IP和接收端口
		String localAddress = arg0.getServletContext().getInitParameter("localAddress");
		String localPort = arg0.getServletContext().getInitParameter("localPort");
		IoAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		DataReceiveHandler handler = new DataReceiveHandler();
		//获得合法终端IP
		handler.setValidIPs(validIPs(arg0));
		acceptor.setHandler(handler); 
		acceptor.getSessionConfig().setReadBufferSize(1024 * 1024); 
		try {
			acceptor.bind(new InetSocketAddress(localAddress, Integer.parseInt(localPort))); 
			log.info("启动Socket监听服务:" + localPort);
		} catch (NumberFormatException e) {
			log.error("非法的Socket监听端口配置", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Socket监听服务初始化失败 ", e);
			e.printStackTrace();
		}
		
	}
	
	/**读取配置文件IP，返回List<ip>集合 */
	private List<String> validIPs(ServletContextEvent e) {
		String xmlPath = e.getServletContext().getRealPath(File.separator)+File.separator + "WEB-INF/classes" + File.separator + "equipment.xml";
		List<String> validIPs = new ArrayList<String>();
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "equipment");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			validIPs.add(XmlUtil.getValueByNodeName(node, "ip"));
		}
		log.info("合法ip实例化成功:"+validIPs);
		return validIPs;
	}

}
