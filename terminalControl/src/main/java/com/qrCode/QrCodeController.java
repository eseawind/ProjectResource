package com.qrCode;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baseCore.controller.BaseController;

@Controller
@RequestMapping("/qrCode")
public class QrCodeController extends BaseController {
	
	/**
	 * <p>功能描述：根据内容批量生成二维码图片</p>
	 *@return
	 *作者：SShi11
	 *日期：Apr 18, 2017 11:10:15 AM
	 */
	@RequestMapping("/batchGenQrcode")
	public String batchGenQrcode(String qrCodes,Map<String,Object> rs,HttpServletRequest request,HttpSession session){
		return BASEPATH+"qrCode/qrCodeIMG";
	}
	
	
	
	
}
