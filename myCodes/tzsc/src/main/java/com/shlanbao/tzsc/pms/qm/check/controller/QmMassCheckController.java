package com.shlanbao.tzsc.pms.qm.check.controller;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.qm.check.beans.QmMassDataBean;
import com.shlanbao.tzsc.pms.qm.check.service.QmCheckMassService;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
* @ClassName: QmMassCheckController 
* @Description: 自检历史记录 
* @author luo 
* @date 2015年10月29日 下午2:24:27 
 */
@Controller
@RequestMapping("/pms/massCheck")
public class QmMassCheckController extends BaseController{
	@Autowired
    private QmCheckMassService service;
	
	//自检包装机历史记录查询
	@ResponseBody
	@RequestMapping("/getList")
	public QmMassDataBean getList(QmMassCheck bean){
		try {
			QmMassDataBean ls = service.queryList(bean);
			return ls;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	//自检历史记录导出
	@ResponseBody
	@RequestMapping("/exportCheckInfo")
	public void exportCheckInfo(QmMassCheck bean,HttpServletResponse response,HttpServletRequest request){
		Json json = new Json();
		try {
			Map<String, Object> root=service.exportCheckInfo(bean);
			if(root==null){
				return;
			}
			Configuration cfg = new Configuration();
	        cfg.setDefaultEncoding("UTF-8");
	        String xmlPath = request.getServletContext().getRealPath(File.separator) + "template";
			cfg.setDirectoryForTemplateLoading(new File(xmlPath)); 
			Template template = cfg.getTemplate("PackageCheck.ftl");
			response.reset();
			response.addHeader("Content-Type", "application/vnd.ms-word");
			response.addHeader("Content-Type", "application/x-msword");
			response.setHeader("Content-Disposition","attachment; filename="+new String("包装机质量自检记录".getBytes(),"ISO8859-1")+".doc");
			ServletOutputStream os = response.getOutputStream();
			Writer out = new OutputStreamWriter(os,"UTF-8");
			template.process(root, out);  
			out.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			log.error("导出包装机质量自检记录失败", e);
		}
	}

	//自检历史记录导出
		@ResponseBody
		@RequestMapping("/exportRolerCheckInfo")
		public void exportRolerCheckInfo(QmMassCheck bean,HttpServletResponse response,HttpServletRequest request){
			try {
				Map<String, Object> root=service.exportRolerCheckInfo(bean);
				if(root==null){
					return;
				}
				Configuration cfg = new Configuration();
		        cfg.setDefaultEncoding("UTF-8");
		        String xmlPath = request.getServletContext().getRealPath(File.separator) + "template";
				cfg.setDirectoryForTemplateLoading(new File(xmlPath)); 
				Template template = cfg.getTemplate("rolerCheck.ftl");
				response.reset();
				response.addHeader("Content-Type", "application/vnd.ms-word");
				response.addHeader("Content-Type", "application/x-msword");
				response.setHeader("Content-Disposition","attachment; filename="+new String("卷烟机质量自检记录".getBytes(),"ISO8859-1")+".doc");
				ServletOutputStream os = response.getOutputStream();
				Writer out = new OutputStreamWriter(os,"UTF-8");
				template.process(root, out);  
				out.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				log.error("导出卷烟机质量自检记录失败", e);
			}
		}
	/**
	 * 获取卷烟机质量自检纪录
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRolerCheckDataList")
	public QmMassDataBean getRolerCheckDataList(QmMassCheck bean){
		try {
			QmMassDataBean ls = service.queryRolerCheckDataList(bean);
			return ls;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 张璐-2015.11.3
	 * 获取装封箱机质量自检纪录
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryFXJCheckDataList")
	public QmMassDataBean getFXJCheckDataList(QmMassCheck bean){
		try {
			QmMassDataBean ls = service.queryFXJCheckDataList(bean);
			return ls;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 张璐-2015.11.4
	 * PMS装封箱机EXCEL导出
	 * @param bean
	 * @param response
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/exportFXJCheckInfo")
	public void exportFXJCheckInfo(QmMassCheck bean,HttpServletResponse response,HttpServletRequest request){
		try {
			Map<String, Object> root=service.exportFXJCheckInfo(bean);
			if(root==null){
				return;
			}
			Configuration cfg = new Configuration();
	        cfg.setDefaultEncoding("UTF-8");
	        String xmlPath = request.getServletContext().getRealPath(File.separator) + "template";
			cfg.setDirectoryForTemplateLoading(new File(xmlPath)); 
			Template template = cfg.getTemplate("SealerCheck.ftl");
			response.reset();
			response.addHeader("Content-Type", "application/vnd.ms-word");
			response.addHeader("Content-Type", "application/x-msword");
			response.setHeader("Content-Disposition","attachment; filename="+new String("装封箱机质量自检记录".getBytes(),"ISO8859-1")+".doc");
			ServletOutputStream os = response.getOutputStream();
			Writer out = new OutputStreamWriter(os,"UTF-8");
			template.process(root, out);  
			out.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			log.error("导出装封箱机质量自检记录失败", e);
		}
	}
	
	/**
	 * 获取滤棒质量自检记录
	 * @author 景孟博
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFilterCheckDataList")
	public QmMassDataBean getFilterCheckDataList(QmMassCheck bean){
		try {
			QmMassDataBean ls = service.queryFilterCheckDataList(bean);
			return ls;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 张璐-2015.11.5
	 * 滤棒质量自检记录EXCEL导出
	 * @param bean
	 * @param response
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/exportFilterCheckDataList")
	public void exportFilterCheckDataList(QmMassCheck bean,HttpServletResponse response,HttpServletRequest request){
		try {
			Map<String, Object> root=service.exportFilterCheckDataList(bean);
			if(root==null){
				return;
			}
			Configuration cfg = new Configuration();
	        cfg.setDefaultEncoding("UTF-8");
	        String xmlPath = request.getServletContext().getRealPath(File.separator) + "template";
			cfg.setDirectoryForTemplateLoading(new File(xmlPath)); 
			Template template = cfg.getTemplate("FilterCheck.ftl");
			response.reset();
			response.addHeader("Content-Type", "application/vnd.ms-word");
			response.addHeader("Content-Type", "application/x-msword");
			response.setHeader("Content-Disposition","attachment; filename="+new String("滤棒质量自检记录".getBytes(),"ISO8859-1")+".doc");
			ServletOutputStream os = response.getOutputStream();
			Writer out = new OutputStreamWriter(os,"UTF-8");
			template.process(root, out);  
			out.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			log.error("导出滤棒质量自检记录失败", e);
		}
	}
}
