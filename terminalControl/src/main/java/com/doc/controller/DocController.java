package com.doc.controller;

import com.baseCore.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SShi11 on 5/2/2017.
 * 文档处理
 */
@Controller
@RequestMapping("/doc")
public class DocController extends BaseController{
    public String preDoc(){
        return "/doc/preViewDOc.ftl";
    }



}
