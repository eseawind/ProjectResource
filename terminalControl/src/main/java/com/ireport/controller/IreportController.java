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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
     * 不使用配置文件
     * @param model
     * @return
     */
    @RequestMapping("/testIreportHasSql")
    public String testIreportHasSql(Map<String, Object> model,HttpSession session) {
    	try {
    		  ServletContext context=session.getServletContext();
        	  Map<String, Object> params = new HashMap<>();
        	  iireportService.queryUsers2(params, model,context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return "/ireportFiles/test.html";
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
    public String exportExcel(String type,Map<String, Object> model) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("type", type);
            iireportService.ExportUsers(params, model);
        } catch (Exception e) {
			e.printStackTrace();
		}
        return "testIreport";
    }
    
    
    /**
     * 导出
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/exportExcel2")
    public void exportExcel2(String type,Map<String, Object> model,HttpSession session,HttpServletResponse response) {
        try {
            Map<String, Object> params = new HashMap<>();
            ServletContext context=session.getServletContext();
            params.put("type", type);
            iireportService.ExportUsers2(params, model,context,response);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
