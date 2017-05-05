package com.ireport.service.impl;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commonUtil.OperationException;
import com.commonUtil.StringUtils;
import com.ireport.dao.IireportDao;
import com.ireport.service.IireportService;

import net.sf.jasperreports.engine.JRDataSource;
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

/**
 * Created by SShi11 on 5/3/2017.
 */
@Service
public class IreportServiceImpl implements IireportService {
	@Autowired
	private IireportDao iireportDao;

	@Override
	public List<Map<String, Object>> queryMapList(Map<String, Object> params) throws Exception {
		return null;
	}

	@Override
	public int updateEntity(Map<String, Object> params) throws Exception {
		return 0;
	}

	@Override
	public Map<String, Object> queryUsers(Map<String, Object> params, Map<String, Object> model) throws Exception {
		List<Map<String, ?>> rs;
		try {
			rs = iireportDao.queryUsers(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationException("查询异常！");
		}
		JRDataSource jrDataSource = new JRMapCollectionDataSource(rs);
		model.put("url", "/module/ireportFiles/test.jasper");
		model.put("format", "html");
		model.put("dataSource", jrDataSource);
		return model;
	}

	/**
	 * 导出
	 * 
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> ExportUsers(Map<String, Object> params, Map<String, Object> model) throws Exception {
		List<Map<String, ?>> rs;
		String type = StringUtils.toStr(params.get("type"));
		try {
			rs = iireportDao.queryUsers(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationException("查询异常！");
		}
		JRDataSource jrDataSource = new JRMapCollectionDataSource(rs);
		model.put("url", "/module/ireportFiles/test.jasper");
		model.put("format", type);
		model.put("dataSource", jrDataSource);
		return model;
	}

	@Override
	public String queryUsers2(Map<String, Object> params, Map<String, Object> model, ServletContext content)
			throws Exception {
		List<Map<String, ?>> rs;
		try {
			rs = iireportDao.queryUsers(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationException("查询异常！");
		}
		JRDataSource jrDataSource = new JRMapCollectionDataSource(rs);
		model.put("reportDataKey", jrDataSource);
		String path = content.getRealPath("/") + "/module/ireportFiles/test.jasper";
		String string = JasperFillManager.fillReportToFile(path, model, jrDataSource);
		return JasperExportManager.exportReportToHtmlFile(string);
	}

	@Override
	public void ExportUsers2(Map<String, Object> params, Map<String, Object> model, ServletContext content,
			HttpServletResponse response) throws Exception {
		List<Map<String, ?>> rs;
		String type = StringUtils.toStr(params.get("type"));
		try {
			rs = iireportDao.queryUsers(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationException("查询异常！");
		}
		JRDataSource jrDataSource = new JRMapCollectionDataSource(rs);
		model.put("reportDataKey", jrDataSource);
		String path = content.getRealPath("/") + "/module/ireportFiles/test.jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(path, model, jrDataSource);
		ServletOutputStream outputStream = response.getOutputStream();
		if (type.equals("PDF")) {
			String files = "导出PDF文档" + ".pdf";
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			String fileName = new String(files.getBytes("utf-8"), "ISO8859_1");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		} else if (type.equals("XLS")) {

			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			String files = "导出xls文档" + ".xls";
			String fileName = new String(files.getBytes("utf-8"), "ISO8859_1");
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(true);//分sheet页导出
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			xlsReportConfiguration.setDetectCellType(false);
			xlsReportConfiguration.setWhitePageBackground(false);
			xlsReportConfiguration.setCollapseRowSpan(false);
			xlsExporter.setConfiguration(xlsReportConfiguration);
			xlsExporter.exportReport();
		} else if (type.equals("DOC")) {

			String files = "导出DOC文档" + ".doc";
			response.setContentType("application/msword;charset=utf-8");
			String fileName = new String(files.getBytes("utf-8"), "ISO8859_1");
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			JRRtfExporter rtfRxporter = new JRRtfExporter();
			rtfRxporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			rtfRxporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
			rtfRxporter.exportReport();
		}
		outputStream.flush();
		outputStream.close();

	}
	
	/**
	 * ireport查询导出通用类
	 */
	@Override
	public void IreportTools(List<Map<String, ?>> data, String jasperPath, boolean isExport,Map<String,Object> fileProperties,HttpServletResponse response)
			throws Exception {
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
	}

	@Override
	public List<Map<String, ?>> queryData(Map<String, Object> params) throws Exception {
		List<Map<String, ?>> rs=null;
		try {
			rs = iireportDao.queryUsers(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationException("查询异常！");
		}
		return rs;
	}
}
