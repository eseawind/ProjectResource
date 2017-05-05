package com.rockwell.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
	/**
	 * @param data  数据集  List<Map<String, ?>>
	 * @param jasperPath  jasperPath 绝对路径
	 * @param isExport    是否为导出操作   true / false
	 * @param exportFileSuffix  导出文件的后缀  支持 html，pdf，doc，xls
	 * @param response          响应
	 * @param exportFIleName  导出的文件名称
	 * @throws Exception
	 * @author SShi11
	 */
	public static void IreportTools(List<Map<String, ?>> data, String jasperPath, boolean isExport,Map<String,Object> fileProperties,HttpServletResponse response) throws OperationException{
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
				
			}else{
			//查询数据
				String middleFile=JasperFillManager.fillReportToFile(jasperPath, model, jrDataSource);
				JasperExportManager.exportReportToHtmlFile(middleFile);
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
		}
	}
}
