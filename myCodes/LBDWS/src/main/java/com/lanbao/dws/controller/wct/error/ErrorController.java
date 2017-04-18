package com.lanbao.dws.controller.wct.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**404,400,405 错误页跳转*/

@Controller
@RequestMapping("/wct/error")
public class ErrorController {

	//404
	@RequestMapping("/gotoError")
	public String ERROR404( Model model,String eurl){
		return eurl;
	}
	
	
}
