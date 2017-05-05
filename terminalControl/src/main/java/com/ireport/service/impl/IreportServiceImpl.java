package com.ireport.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;
import com.commonUtil.OperationException;
import com.commonUtil.StringUtils;
import com.ireport.dao.IireportDao;
import com.ireport.service.IireportService;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.RtfExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleRtfExporterConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
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
}
