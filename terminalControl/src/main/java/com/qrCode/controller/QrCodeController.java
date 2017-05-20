package com.qrCode.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baseCore.controller.BaseController;
import com.baseCore.core.QrCodeTool;
import com.qrCode.service.QrCodeService;

@Controller
@RequestMapping("/qrCode")
public class QrCodeController extends BaseController<QrCodeController> {
	@Autowired
	QrCodeService qrCodeService;

	/**
	 * <p>
	 * 功能描述：根据内容批量生成二维码图片
	 * </p>
	 *
	 * @return 作者：SShi11 日期：Apr 18, 2017 11:10:15 AM
	 */
	@RequestMapping("/batchGenQrcode")
	public String batchGenQrcode(String qrCodes, Map<String, Object> rs, HttpServletRequest request,
			HttpSession session) {
		try {
			rs.put("qrCodes", qrCodes);
			rs = qrCodeService.batchGenQrcode(rs);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put(ERRMSG, e.getMessage());
		}
		return "/qrCode/qrCodeIMG.jsp";
	}

	/**
	 * <p>
	 * 功能描述：跳转到批量生成二维码页面
	 * </p>
	 * *@param request
	 *
	 * @param session
	 * @return 作者：SShi11 日期：Apr 24, 2017 5:30:51 PM
	 */
	@RequestMapping("/goToGenQRCode")
	public String goToGenQRCode(HttpServletRequest request, HttpSession session) {
		return "/qrCode/areaQrCode.jsp";
	}
	
	@RequestMapping("/genQRcodes")
	public void genQRcodes(HttpSession session,HttpServletResponse response,String context){
		try {
			String imgIcon=session.getServletContext().getRealPath("/style/js/qrCode/"+new Random().nextInt(3)+".jpg");
			QrCodeTool.encode(context, 300, 300, imgIcon , response.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
