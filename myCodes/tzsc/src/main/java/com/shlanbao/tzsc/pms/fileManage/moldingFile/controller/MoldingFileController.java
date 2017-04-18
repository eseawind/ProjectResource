	package com.shlanbao.tzsc.pms.fileManage.moldingFile.controller;
	
	import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
	



	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
	



	import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.fileManage.moldingFile.beans.FileManageBean;
import com.shlanbao.tzsc.pms.fileManage.moldingFile.service.MoldingFileServiceI;
import com.shlanbao.tzsc.utils.tools.FileOptionsUtil;
	/**
	 * 文档管理控制器
	 */
	@Controller
	@RequestMapping("/pms/mfc")
	public class MoldingFileController extends BaseController {
		@Autowired
		private MoldingFileServiceI service;
		
		@RequestMapping("/goTofileUploadJsp")
		public String goTofileUploadJsp(HttpServletRequest request){
			return "/pms/FileManage/moldingFile/fileUpload";
		}
		
		/**
		 * 文件上传
		 * @author liuligong
		 * @create 2014-9-17 下午15:26
		 */
		@ResponseBody
		@RequestMapping("/updateFile")
		public Json fileUpdate(HttpSession session,HttpServletRequest request,FileManageBean fmBean){
			Json json = new Json();
			try {
				//上传文件
				service.fileUpdate(session,request,fmBean);
				json.setMsg("文件上传成功！");
				json.setSuccess(true);
			} catch (Exception e) {
				log.error("文件上传异常", e);
				json.setMsg("文件上传失败！");
				json.setSuccess(false);
			}
			return json;
		}
		
		/**
		 * 查询file
		 * @param session 
		 * @param fmBean beans数据
		 * @param pageParams sql参数
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/queryFile")
		public DataGrid queryFile(HttpSession session,FileManageBean fmBean,PageParams pageParams){
			try {
				//获取用户安全级别
				SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
				Long securityLevel = sessionInfo.getUser().getSecurityLevel();
				//查询file数据
				return service.queryFile(securityLevel,fmBean, pageParams);
			} catch (Exception e) {
				log.error(message, e);
			}
			return null;
		}
		
		/**
		 * 下载file
		 * @param request
		 */
		@RequestMapping("/downloadFile")
		public void downloadFile(HttpServletRequest request,HttpServletResponse response){
			String fileId = request.getParameter("fileId");
			String fileName = request.getParameter("fileName")+fileId.substring(fileId.lastIndexOf("."));
			fileId = fileId.substring(fileId.lastIndexOf("/"));
			
			ResourceBundle bundle = ResourceBundle.getBundle("config");
			
			String filePath = request.getServletContext().getRealPath(bundle.getString("upload"))+fileId;
			
			FileOptionsUtil fo = new FileOptionsUtil();
			boolean bool = fo.download2(filePath, fileName, response);
			//boolean bool = fo.download(filePath, response);
			System.out.println(filePath);
			if(bool) {
				log.info("下载成功");
			} else {
				log.error("下载失败");
			}
		}
		
		/**
		 * 删除文件
		 * @param id
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/delFile")
		public Json delFile(String id){
			Json json = new Json();
			try {
				service.delFile(id);
				json.setMsg("文件删除成功！");
				json.setSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				json.setMsg("文件删除失败！");
				json.setSuccess(false);
			}
			return json;
		}
	}
