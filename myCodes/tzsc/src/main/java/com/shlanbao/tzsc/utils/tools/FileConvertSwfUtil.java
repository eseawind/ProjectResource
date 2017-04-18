package com.shlanbao.tzsc.utils.tools;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class FileConvertSwfUtil {
	private static Logger log=Logger.getLogger(FileConvertSwfUtil.class);
	/**
	 * 文件转换为swf格式
	 * @param sourceFilePath 服务器 源文件路径    EG：D:\tomcat\webapp\a.txt
	 * @param pdfPath	 	   服务器PDF文件路径EG：D:\tomcat\webapp\a.pdf
	 * @param swfPath		   服务器SWF文件路径EG：D:\tomcat\webapp\a.swf
	 * @throws Exception
	 */
	public static void fileConvert(String sourceFilePath,String pdfPath,String swfPath) throws Exception{
		  final File sourceFile;  //目标源文件
		  final File pdfFile;     //PDF目标文件
		  final File swfFile;     //swf目标文件
		//判断源文件是否为PDF文件
		if("pdf".equals(sourceFilePath.substring((sourceFilePath.lastIndexOf(".")+1)))){
			sourceFile = new File(sourceFilePath);
			pdfFile = new File(sourceFilePath);
			swfFile = new File(swfPath);
		}else{
			sourceFile = new File(sourceFilePath);
			pdfFile = new File(pdfPath);
			swfFile = new File(swfPath);
		}
		final Runtime runtime = Runtime.getRuntime();
		new Thread(){
			@Override
			public void run(){
				log.info("文件对象已生成，准备转换..");
				log.info(pdfFile.getName());
				if(sourceFile.exists()){
					if(!pdfFile.exists()){
						OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
						try {
							connection.connect();
							DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
							converter.convert(sourceFile, pdfFile);//源文件转换为PDF
							pdfFile.createNewFile();
							connection.disconnect();
							log.info("转换为PDF文件:"+pdfFile.getPath());
						} catch (ConnectException e) {
							log.error("openOffice 服务未启动: ");
							e.printStackTrace();
							try {
								throw e;
							} catch (ConnectException e1) {
								log.error("openOffice 服务未启动: ");
							}
						} catch (OpenOfficeException e){
							log.error("文件读取失败: ");
							e.printStackTrace();
							throw e;
						} catch (Exception e){
							log.error("未知异常:");
							e.printStackTrace();
						}finally{
							if(connection!=null){
								connection.disconnect();
							}
						}
				} else {
					log.info("Pdf文件已经转换，无需再次转。");
				} 
				}else{
					log.info("要转换的源文件不存在， 可能是路径错误。");
				}
				
				if(!swfFile.exists()){
					if(pdfFile.exists()){
						try {
							ResourceBundle bundle = ResourceBundle.getBundle("config");
							//p.waitFor();//当前线程等待，如有必要，一直要等到由该 Process 对象表示的进程已经终止
							String command=bundle.getString("exePath")+" -t \""+pdfFile.getPath()+"\" -o \""  
									+swfFile.getPath()+"\" -T 9";
							System.out.println(command);
							runtime.exec(command);//单独的进程中执行指定的字符串命令
							
							swfFile.createNewFile();
							
							log.info("swfFile 格式路径："+swfFile.getPath());
							
							log.info("swfFile 格式名称："+swfFile.getName());
							
						} catch (IOException e) {
							log.error("IOException:");
							e.printStackTrace();
						} /*catch (InterruptedException e) {
							System.out.println("InterruptedException:");
							e.printStackTrace();
						}*/
						
					} else {
						log.info("Pdf文件格式不存在，无法转换");
					}
				} else {
					log.info("已转换为SWF格式,无需再次转换!");
				}
			}
			
		}.start();
	}
}
