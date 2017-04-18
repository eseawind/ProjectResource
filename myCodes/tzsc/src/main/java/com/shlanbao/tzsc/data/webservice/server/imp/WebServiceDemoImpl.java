package com.shlanbao.tzsc.data.webservice.server.imp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.xml.sax.InputSource;

import com.shlanbao.tzsc.base.interceptor.ParsingXmlDataInterceptor;
import com.shlanbao.tzsc.data.webservice.server.WebServiceDemoI;
import com.shlanbao.tzsc.utils.tools.DateUtil;

/**
 * [功能说明]：MES webService interface
 * @author wanchanghuang
 * @createTime  2016年5月3日15:29:05
 * */
@WebService
public class WebServiceDemoImpl implements WebServiceDemoI {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
    
    /**
     * [功能说明]：webservcie调用的方法，且传递 type，str参数
     *  @param  type-XML类型
     *  @param str- XML格式字符串
     *  @ruture  返回空表示成功，返回不为空表示失败
     *  
     * */
	@Override
	public String mesDataReceive(String type,String str) {
		/**
		 * 保存xml到本地D:\\workOrder文件夹下
		 * 
		 * */
		try {
			//创建文件 
		   File file=new File("D:\\workOrder\\"+DateUtil.getNowDateTime("yyyyMMddHHmmssSSS")+"_"+type+".xml");    
		   if(!file.exists()){ 
			   file.createNewFile();
		   }
		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
		   BufferedWriter bw = new BufferedWriter(fw);
		   bw.write(str);
		   bw.close();
		   fw.close();
		} catch (Exception e) {
			
		}
		//
		Document doc=null;
		//XML空判断
		if("".equals(str)){return "ERROR:数据异常..."+type+str;} 
		//将xml字符串转换成document
		try {
			StringReader sr = new StringReader(str);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			doc = (Document) builder.parse(is);
		} catch (Exception e) {}
		try {
			if(doc==null){
				doc = DocumentHelper.parseText(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("ERROR:XML字符串转换Document对象异常..."+str);
		}
		
		//线程处理
		final Document fdoc=doc;
		final String  ftype=type;
		final String fstr=str;
	    cachedThreadPool.execute(new Runnable() {  
			public void run() {
				if("SDCSCalendarDownload".equals(ftype)){
					/**工厂日历 */
				    ParsingXmlDataInterceptor.getInstance().alalyCalenderXML(ftype,fstr,fdoc);
				}else if("SDCSEntryCancel".equals(ftype)){
					/**工单撤销 */
					ParsingXmlDataInterceptor.getInstance().updateTntryCancel(ftype,fstr,fdoc);
				}else if("SDCSDownloadEntry".equals(ftype)){
					/**工单下发 */
					ParsingXmlDataInterceptor.getInstance().addSendCancel(ftype,fstr,fdoc);
				}else if("SDCSMatmasDownload".equals(ftype)){
					/**物料主数据 */
					ParsingXmlDataInterceptor.getInstance().addSendMatmas(ftype,fstr,fdoc);
				}else if("SDCSReceiveMat".equals(ftype)){
					/**虚领需退 */
					ParsingXmlDataInterceptor.getInstance().addSendReceiveMat(ftype,fstr,fdoc);
				}
		    }
	    });
		return "";
	}

}
