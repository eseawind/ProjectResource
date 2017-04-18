package com.shlanbao.tzsc.base.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shlanbao.tzsc.base.editor.TimeStampPropertyEditor;
import com.shlanbao.tzsc.utils.extents.StringEscapeEditor;

/**
 * 基础控制器<br>
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能<br>
 * @author  Leejean
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {
	protected Logger log = Logger.getLogger(this.getClass());
	protected String message="程序异常,请稍后再试.";
	protected Long infoId;//消息id
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {      
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
		
		//自动转换timestamp类型的字段格式
		binder.registerCustomEditor(Timestamp.class, new TimeStampPropertyEditor("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 用户跳转JSP页面
	 * 
	 * 此方法不考虑权限控制
	 * 
	 * @param folder
	 *            路径
	 * @param jspName
	 *            JSP名称(不加后缀)
	 * @return 指定JSP页面
	 */
	@RequestMapping("/{folder}/{jspName}")
	public String redirectJsp(@PathVariable String folder, @PathVariable String jspName) {
		return "/" + folder + "/" + jspName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
