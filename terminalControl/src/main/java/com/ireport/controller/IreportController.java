package com.ireport.controller;

import com.baseCore.controller.BaseController;
import com.ireport.service.IireportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRJpaDataSource;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuterFactory;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SShi11 on 5/3/2017.
 * 报表
 */
@Controller
@RequestMapping("/ireport")
public class IreportController extends BaseController {
    @Autowired
    private IireportService iireportService;

    /**
     * 后台处理添加数据源
     * @param model
     * @return
     */
    @RequestMapping("/testIreport")
    public String testIreportNoSql(Map<String, Object> model) {
        try {
            Map<String, Object> params = new HashMap<>();
            iireportService.queryUsers(params, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "testIreport";
    }

    /**
     * 已有数据源
     * @param model
     * @return
     */
    @RequestMapping("/testIreportHasSql")
    public String testIreportHasSql(Map<String, Object> model) {
        try {
            Map<String, Object> params = new HashMap<>();
            model.put("url", "/module/ireportFiles/report2.jasper");
            model.put("format", "pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "testIreport";
    }


    /**
     * 跳转到测试ireport界面
     *
     * @return
     */
    @RequestMapping("/gotoIreportTest")
    public String gotoIreportTest() {
        return "/ireportFiles/ireportTest.jsp";
    }

    /**
     * 导出excel
     * @param session
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        try {
            response.setContentType("application/pdf");
            String jasperFile = "";
            InputStream jasperInStream = new FileInputStream(jasperFile);
            ServletOutputStream os = response.getOutputStream();
            Map map = new HashMap();
            JRDataSource ds =new JRBeanCollectionDataSource(null);//数据集
            JasperPrint print = JasperFillManager.fillReport(jasperInStream, map, ds);
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
