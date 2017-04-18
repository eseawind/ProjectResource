package com.shlanbao.tzsc.base.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class QualityManagementMESInterface {
	protected Logger log = Logger.getLogger(this.getClass());//日志
	private SAXReader reader=null;
	private final String xml_error="C:\\RECEIVEENTY\\XML_ERROR\\";             //错误文档保存路径
	private final String xml_copy="C:\\RECEIVEENTY\\XML_COPY\\";               //解析完成后，备份路径
	private final String factoryCalendar="C:\\RECEIVEENTY\\FactoryCalendar\\"; //工厂日历
	private final String selfTest="C:\\RECEIVEENTY\\SelfTest\\";               //自检
	private final String sendEntryCancel="C:\\RECEIVEENTY\\SendEntryCancel\\"; //工单撤销
	private final String sendEntry="C:\\RECEIVEENTY\\SendEntry\\";             //工单下发  
	private final String sendMatmas="C:\\RECEIVEENTY\\SendMatmas\\";           //物料主数据
	
	public void cgListType(List<String> filenames,String fileTyname,String typename){
		boolean flag=false;
		if(filenames.size()>0 && filenames!=null){
			Document doc=null;
			String path="";
			for(String name:filenames){
				try {
					path=fileTyname+name;
					//将xml转换document
					doc= this.parse(path);
					//异常数据处理
					/*if(doc==null){
						ParsingXmlDataInterceptor.getInstance().addLog(name,0,null);//写入日志
						removeXML(path,xml_error+name);//备份异常XML
					}*/
					if("factoryCalendar".equalsIgnoreCase(typename)){ 
						ParsingXmlDataInterceptor.getInstance().alalyCalenderXML(null,null,doc);//工厂日历
					}else if("selfTest".equalsIgnoreCase(typename)){
						//ParsingXmlDataInterceptor.getInstance().handleQCXML(null,null,doc); //自检
					}else if("sendEntryCancel".equalsIgnoreCase(typename)){
						ParsingXmlDataInterceptor.getInstance().updateTntryCancel(null,null,doc); //工单撤销
					}else if("sendEntry".equalsIgnoreCase(typename)){
						ParsingXmlDataInterceptor.getInstance().addSendCancel(null,null,doc);//工单下发
						//ParsingXmlDataInterceptor.getInstance().addSendReceiveMat(null,null,doc); //工单实绩反馈
					}else if("sendMatmas".equalsIgnoreCase(typename)){
						ParsingXmlDataInterceptor.getInstance().addSendMatmas(null,null,doc);//辅料主数据
					}
					//日志保存
					//ParsingXmlDataInterceptor.getInstance().addLog("解析XML："+path,flag==true?1:0);
					//备份xml
					if(flag){
						removeXML(path,xml_copy+name);
					}
				} catch (Exception e) {
					//异常将xml文件拷贝异常文件夹
					removeXML(path,xml_error+name);//移除
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 【功能说明】：质量管理数据接口 - mes向dis服务端发送数据，dis客户端将xml数据转发服务器
	 *         再由本方法读取xml解析保存相关数据
	 * @author wanchanghuang
	 * @createTime 2015年11月25日14:07:28
	 */
	public void ReadAndWriteXML(){
		List<String> filenames=null;
		try {
			//工厂日历
			filenames=getFileName(factoryCalendar);
			cgListType(filenames,factoryCalendar,"factoryCalendar");
			//自检
			/*filenames=getFileName(selfTest);
			cgListType(filenames,selfTest,"selfTest");*/
			//工单撤销
			filenames=getFileName(sendEntryCancel);
			cgListType(filenames,sendEntryCancel,"sendEntryCancel");
			//工单下发
			filenames=getFileName(sendEntry);
			cgListType(filenames,sendEntry,"sendEntry");
			//物料主数据
			filenames=getFileName(sendMatmas);
			cgListType(filenames,sendMatmas,"sendMatmas");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	/** 
	 * 功能描述：解析过的xml拷贝
	 *   C:\\RECEIVEENTY\\XML_COPY\\
     *   并保存解析日志  qmPathName:开始路径    copyPathName:备份路径
     * @author wanchanghuang
     * @createTime 2015年11月27日11:36:32
	 */
	public void removeXML(String qmPath,String copyPath){
		FileInputStream is=null;
		FileOutputStream out=null;
		Reader r=null;
		Writer w=null;
		try {
			is=new FileInputStream(qmPath);
			out=new FileOutputStream(copyPath);
			r=new InputStreamReader(is, "UTF-8");
			w=new OutputStreamWriter(out,"UTF-8");
			char[] b = new char[8192];
			int len = 0;
			while ((len = r.read(b, 0, 8192)) != -1) {
				w.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				w.flush();
				r.close();
				w.close();
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//解析过后的xml备份完成并删除
		File f = new File(qmPath);
		f.delete();
	}
	

	
	/** 
	 * [功能说明]：获取文件夹下所有文件名称
	 *  @author wanchanghuang
	 *  @createTime 2015年12月29日09:29:06
	 */
	public List<String> getFileName(String path) {
		List<String> list=new ArrayList<String>();
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return null;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory()) {
            	list.add(fs.getName());
                System.out.println(fs.getName() + " [目录]");
            }
        }
        return list;
	}
	/**
	 * [功能说明]：获取Document
	 * @author shisihai
	 * @param url
	 * createTime 2015年12月13日上午10:22:53
	 */
	private Document parse(String url) throws Exception {
		Document document=null;
		FileInputStream is=null;
		try {
			is=new FileInputStream(url);
			if(reader==null){
				reader = new SAXReader();
			}
			document = reader.read(is);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			is.close();
		}
		document = reader.read(new File(url));
		return document;
	}
	
	
	//测试入口
	public static void main(String[] args) {
		new QualityManagementMESInterface().ReadAndWriteXML();
	}
	
	
	
}
