package com.ireport.controller;

import com.baseCore.controller.BaseController;
import com.ireport.service.IireportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/testIreport")
    public String testIreport(Map<String, Object> model) {
        try {
            Map<String,Object> params=new HashMap<>();
            iireportService.queryUsers(params,model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "testIreport";
    }

    /**
     * 跳转到测试ireport界面
     * @return
     */
    @RequestMapping("/gotoIreportTest")
    public String gotoIreportTest() {
        return "/ireportFiles/ireportTest.jsp";
    }
}
