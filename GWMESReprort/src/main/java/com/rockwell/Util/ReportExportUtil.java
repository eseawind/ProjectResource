package com.rockwell.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class ReportExportUtil {
	private final static Logger loger = LoggerFactory.getLogger(ReportExportUtil.class);
	public static final String  EXPORTFILESUFFIX="exportFileSuffix";// 导出文件的后缀  支持 html，pdf，doc，xls
	public static final String  EXPORTFILENAME="exportFileName";//导出的文件名称
	public static final String  ONEPAGEPERSHEET="exportFileSuffix";//excel导出是否每页占一个sheet 默认false
	/**
	 * 注入数据集
	 * @param data  数据集  List<Map<String, ?>>
	 * @param jasperPath  jasperPath 绝对路径
	 * @param isExport    是否为导出操作   true / false
	 * @param fileProperties <br>导出参数Map<String,Object> fileProperties<br>
	 * <font color="red">exportFileSuffix  导出文件的后缀  支持 html，pdf，doc，xls</font><br>
	 * <font color="red">exportFileName  导出的文件名称</font><br>
	 * <font color="red">onePagePerSheet  excel导出是否每页占一个sheet 默认false</font><br>
	 * <font color="green">Map  sqlParams   查询sql的参数 </font>
	 * <font color="green">Map  showInfoParams   页面显示的查询条件  </font>
	 * @param response          响应
	 * @throws Exception
	 * @author SShi11
	 */
	@SuppressWarnings("unchecked")
	public static void IreportInjectionData(Object data, String jasperPath, boolean isExport,Map<String,Object> fileProperties,HttpServletResponse response) throws OperationException{
		try {
			JRDataSource jrDataSource=null;
			Map<String,Object> model=new HashMap<>();
			JasperPrint jasperPrint =null;
			//对于注入数据集合
			if( data instanceof Collection ){
				jrDataSource = new JRMapCollectionDataSource((Collection<Map<String, ?>>) data);
				model.put("reportDataKey", jrDataSource);
				if(isExport){
					jasperPrint = JasperFillManager.fillReport(jasperPath, model, jrDataSource);
				}
			}else if(data instanceof Connection){
				//注入查询参数
				Object object=fileProperties.get("sqlParams");
				if(object != null){
					Map<String,?> showTextMap=(Map<String, ?>) object;
					model.putAll(showTextMap);
				}
				//注入显示参数
				object=fileProperties.get("showInfoParams");
				if(object != null){
					Map<String,?> showInfoParams=(Map<String, ?>) object;
					model.putAll(showInfoParams);
				}
				if(isExport){
					jasperPrint = JasperFillManager.fillReport(jasperPath, model, (Connection) data);
				}
			}
			//导出数据
			if(isExport){
				//对数据非空校验
				//.....
				//文件名
				String exportFileName=StringUtils.toStr(fileProperties.get("exportFileName"));
				//文件后缀
				String exportFileSuffix=StringUtils.toStr(fileProperties.get("exportFileSuffix"));
				exportFileSuffix=exportFileSuffix.toUpperCase();
				
				OutputStream os=response.getOutputStream();
				String fileName=null;
				switch (exportFileSuffix) {
				case "XLS":
					response.setContentType("application/vnd.ms-excel");
					response.setCharacterEncoding("UTF-8");
					fileName = exportFileName + ".xls";
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
					response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
					JRXlsExporter xlsExporter = new JRXlsExporter();
					xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
					SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
					//导出exlcel 文件格式设置
					//每页占一个sheef
					Object onePagePerSheetObj=fileProperties.get("onePagePerSheet");
					boolean onePagePerSheet=onePagePerSheetObj==null?false:(Boolean)onePagePerSheetObj;
					
					xlsReportConfiguration.setOnePagePerSheet(onePagePerSheet);//分sheet页导出
					xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
					xlsReportConfiguration.setDetectCellType(false);
					xlsReportConfiguration.setWhitePageBackground(false);
					xlsReportConfiguration.setCollapseRowSpan(false);
					xlsExporter.setConfiguration(xlsReportConfiguration);
					xlsExporter.exportReport();
					break;
				case "DOC":
					fileName = exportFileName + ".doc";
					response.setContentType("application/msword;charset=utf-8");
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
					response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
					JRRtfExporter rtfRxporter = new JRRtfExporter();
					rtfRxporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					rtfRxporter.setExporterOutput(new SimpleWriterExporterOutput(os));
					rtfRxporter.exportReport();
					break;
				default:
					fileName = exportFileName + ".pdf";
					response.setContentType("application/pdf");
					response.setCharacterEncoding("UTF-8");
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
					response.setHeader("Content-disposition", "attachment; filename=" + fileName);
					JasperExportManager.exportReportToPdfStream(jasperPrint, os);
				}
				os.flush();
				os.close();
				loger.info(new String(fileName.getBytes("ISO8859_1"), "utf-8")+"报表已生成", "报表导出工具",null);
			}else{
				//查询数据
				String middleFile=JasperFillManager.fillReportToFile(jasperPath, model, jrDataSource);
				String htmlName=JasperExportManager.exportReportToHtmlFile(middleFile);
				loger.info("报表已生成,路径为："+new String(htmlName.getBytes("ISO8859_1"), "utf-8"), "报表导出工具",null);
			}
		} catch (UnsupportedEncodingException e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		} catch (JRException e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		} catch (IOException e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		}catch(Exception e){
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		}
	}
	
	
	
	public static void IreportInjectionData(List<Map<String, ?>> data, String jasperPath, boolean isExport,Map<String,Object> fileProperties,HttpServletResponse response) throws OperationException{
		try {
			JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
			Map<String,Object> model=new HashMap<>();
			model.put("reportDataKey", jrDataSource);
			//导出数据
			if(isExport){
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, model, jrDataSource);
				//对数据非空校验
				//.....
				//文件名
				String exportFileName=StringUtils.toStr(fileProperties.get("exportFileName"));
				//文件后缀
				String exportFileSuffix=StringUtils.toStr(fileProperties.get("exportFileSuffix"));
				exportFileSuffix=exportFileSuffix.toUpperCase();
				
				OutputStream os=response.getOutputStream();
				String fileName=null;
				switch (exportFileSuffix) {
				case "XLS":
					response.setContentType("application/vnd.ms-excel");
					response.setCharacterEncoding("UTF-8");
					fileName = exportFileName + ".xls";
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
					response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
					JRXlsExporter xlsExporter = new JRXlsExporter();
					xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
					SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
					//导出exlcel 文件格式设置
					//每页占一个sheef
					Object onePagePerSheetObj=fileProperties.get("onePagePerSheet");
					boolean onePagePerSheet=onePagePerSheetObj==null?false:(Boolean)onePagePerSheetObj;
					
					xlsReportConfiguration.setOnePagePerSheet(onePagePerSheet);//分sheet页导出
					xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
					xlsReportConfiguration.setDetectCellType(false);
					xlsReportConfiguration.setWhitePageBackground(false);
					xlsReportConfiguration.setCollapseRowSpan(false);
					xlsExporter.setConfiguration(xlsReportConfiguration);
					xlsExporter.exportReport();
					break;
				case "DOC":
					fileName = exportFileName + ".doc";
					response.setContentType("application/msword;charset=utf-8");
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
					response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
					JRRtfExporter rtfRxporter = new JRRtfExporter();
					rtfRxporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					rtfRxporter.setExporterOutput(new SimpleWriterExporterOutput(os));
					rtfRxporter.exportReport();
					break;
				default:
					fileName = exportFileName + ".pdf";
					response.setContentType("application/pdf");
					response.setCharacterEncoding("UTF-8");
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
					response.setHeader("Content-disposition", "attachment; filename=" + fileName);
					JasperExportManager.exportReportToPdfStream(jasperPrint, os);
				}
				os.flush();
				os.close();
				loger.info(new String(fileName.getBytes("ISO8859_1"), "utf-8")+"报表已生成", "报表导出工具",null);
			}else{
			//查询数据
				String middleFile=JasperFillManager.fillReportToFile(jasperPath, model, jrDataSource);
				String htmlName=JasperExportManager.exportReportToHtmlFile(middleFile);
				loger.info("报表已生成,路径为："+new String(htmlName.getBytes("ISO8859_1"), "utf-8"), "报表导出工具",null);
			}
		} catch (UnsupportedEncodingException e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		} catch (JRException e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		} catch (IOException e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		}catch(Exception e){
			loger.error(ExceptionUtil.formatStackTrace(e), "报表导出工具方法IreportTools",null);
			throw new OperationException("报表导出异常！");
		}
	}
}
