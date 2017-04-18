package com.shlanbao.tzsc.data.modbus.tcp.slave;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import com.lanbao.pub.mapping.bean.MdEquipment;
//import com.lanbao.pub.md.service.intf.IMdEquipmentService;
import com.serotonin.modbus4j.BasicProcessImage;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.tcp.TcpSlave;
import com.shlanbao.tzsc.utils.thread.ThreadManager;
import com.shlanbao.tzsc.utils.thread.WriteModbusRegister;
import com.shlanbao.tzsc.utils.tools.XmlUtil;


/////////////////////////////////////////////////////////////////
/////////  modBUS Data intialized process	 
/////////  The following code is integrated from GZDAMS by tuxiao
/////////  Date:  2016-09-09
//////////////////////////////////////////////////////////////////
/**
 * 服务器modBUS实时数据转发监听(在程序启动时，web.xml注册本监听类，并开始工作)
 * @author liliangting
 * @create 2016年9月25日上午11:30:00
 */
public class DataPostListener implements ServletContextListener {
	protected Logger log = Logger.getLogger(this.getClass());
	private static int port = 502;
	//private IMdEquipmentService service;
	private static ModbusSlaveSet slaveSet = null;
	private static DataPostListener instance;
	
	public DataPostListener(){
	}
	
	public static DataPostListener getInstance(){
		if(instance == null){
			instance = new DataPostListener();
		}
		return instance;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(null != slaveSet) slaveSet.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		if (slaveSet==null){
			slaveSet = new TcpSlave(port,false);
		}
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		initProcessImage(applicationContext);
		Runnable slaveListener = new Runnable(){
			@Override
			public void run(){
				try {
					slaveSet.start();
				} catch (ModbusInitException e) {
					log.error("modBUS监听服务初始化失败 ", e);
					e.printStackTrace();
				}
			}
		};
		Runnable writeRegister = new Runnable() {
			@Override
			public void run() {
				WriteModbusRegister task = WriteModbusRegister.getInstance();
				task.run();
			}
			
		};
		ThreadManager.getInstance().add(slaveListener);
		ThreadManager.getInstance().addSchedule(writeRegister, 3, 2, TimeUnit.SECONDS);
		log.info("启动 modBUS.tcp.slave 监听服务:" + port);
	}
	
	private void initProcessImage(WebApplicationContext ctx){
		String xmlPath = ctx.getServletContext().getRealPath(File.separator)
				+ "xmlcfg" + File.separator + "equipment.xml";
		int eqp = -1;
		String EquCod;
		Node node;
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "equipment");
		for (int iNode = 0; iNode < nodeList.getLength(); iNode++) {
			node = nodeList.item(iNode);
			EquCod = XmlUtil.getValueByNodeName(node, "code");
			eqp = Integer.parseInt(EquCod);
			if(eqp<=0) continue;
			
			BasicProcessImage processImage = new BasicProcessImage(eqp);
	        processImage.setAllowInvalidAddress(false);
	        for(int i=0; i<4999;i++){
	        	//processImage.setCoil(i,false);  	//(address start from 00001)
	        	processImage.setInput(i, false);	//(address start from 10001)
	        	processImage.setInputRegister(i*2, DataType.FOUR_BYTE_INT_SIGNED_SWAPPED,0); //(address start from 30001)
	        	processImage.setHoldingRegister(i*2, DataType.FOUR_BYTE_FLOAT_SWAPPED,0.0); //(address start from 40001)
	        }
	        slaveSet.addProcessImage(processImage);
		}	
	}

	public static ModbusSlaveSet getSlaveSet() {
		return slaveSet;
	}

}
