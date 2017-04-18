package com.lanbao.dws.common.tools;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {
	
private static Logger log = Logger.getLogger(XmlUtil.class);
	
	public static NodeList getRootNodes(String xmlPath,String root){
		try {
			File f=new File(xmlPath); 
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(f); 
		    return doc.getElementsByTagName(root); 
		} catch (Exception e) {
			log.error("解析xml异常", e);
		} 
		return null;
	}
	/**
	 * 获得节点值
	 * @author Leejean
	 * @create 2014年12月11日下午1:46:49
	 * @param node
	 * @return
	 */
	public static String getNodeValue(Node node){
		return node.getTextContent();
	}
	public static List<Node> getSubNodes(Node node){
		NodeList nodeList = node.getChildNodes();
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodes.add(nodeList.item(i));
		}
		return nodes;
	}
	/**
	 * 根据节点名称获得节点值
	 * @author Leejean
	 * @date 2015年3月2日 下午3:24:04 
	 * @param parentNode 父级节点
	 * @param nodeName
	 * @return
	 */
	public static String getValueByNodeName(Node parentNode,String nodeName){
		NodeList nodeList = parentNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeName().equalsIgnoreCase(nodeName)){
				return nodeList.item(i).getTextContent();
			}
		}
		return null;
	}
	public static Node getNodeByNodeName(Node parentNode,String nodeName){
		NodeList nodeList = parentNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeName().equalsIgnoreCase(nodeName)){
				return nodeList.item(i);
			}
		}
		return null;
	}

}
