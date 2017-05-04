package com.baseCore.core;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporterHelper;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.export.annotations.ExporterParameter;

import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SShi11 on 5/3/2017.
 */
public class ApplicationIReportView extends JasperReportsMultiFormatView {
    private JasperReport jasperReport;

    public ApplicationIReportView() {
        super();
    }

    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
        if (model.containsKey("url")) {
            setUrl(String.valueOf(model.get("url")));
        }
        this.jasperReport = loadReport();
        return super.fillReport(model);
    }

    protected JasperReport getReport() {
        return this.jasperReport;
    }
}
