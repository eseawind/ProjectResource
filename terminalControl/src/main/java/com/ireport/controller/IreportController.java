package com.ireport.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baseCore.controller.BaseController;
import com.ireport.service.IireportService;

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
    
    
    /**
     * 查询数据
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/queryData")
    public String queryData(HttpSession session,HttpServletResponse response){
    	try {
    		Map<String, Object> params = new HashMap<>();
    		List<Map<String, ?>> data = iireportService.queryData(params);
    		ServletContext content=session.getServletContext();
    		String jasperPath = content.getRealPath("/") + "module/ireportFiles/test.jasper";
			iireportService.IreportTools(data, jasperPath, false,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return  "/ireportFiles/test.html";
    }
    
    /**
     * 导出
     * @param session
     * @param response
     * @param exportFileSuffix
     * @param exportFileName
     */
    @RequestMapping("/exportFile")
    public void exportFile(HttpSession session,HttpServletResponse response,String exportFileSuffix,String exportFileName){
    	try {
    		Map<String, Object> params = new HashMap<>();
    		List<Map<String, ?>> data = iireportService.queryData(params);
    		ServletContext content=session.getServletContext();
    		String jasperPath = content.getRealPath("/") + "/module/ireportFiles/test.jasper";
    		Map<String,Object> exportProperties=new HashMap<>();
    		exportProperties.put("exportFileSuffix", exportFileSuffix);
    		exportProperties.put("exportFileName", exportFileName);
			iireportService.IreportTools(data, jasperPath, true, exportProperties, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
