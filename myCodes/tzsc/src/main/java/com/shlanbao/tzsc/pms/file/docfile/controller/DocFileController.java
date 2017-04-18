package com.shlanbao.tzsc.pms.file.docfile.controller;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.file.docfile.beans.DocFileBean;
import com.shlanbao.tzsc.pms.file.docfile.service.DocFileServiceI;
import com.shlanbao.tzsc.utils.tools.FileOptionsUtil;

@Controller
@RequestMapping("/pms/file/docfile")
public class DocFileController extends BaseController {
	@Autowired
	private DocFileServiceI docFileServiceI;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	/*
	 * 全查
	 */
	@ResponseBody
	@RequestMapping("getList")
	public List<DocFileBean> getList(HttpServletRequest request) throws Exception {
		//保存预览地址
		request.setAttribute("service_url", bundle.getString("service_url"));
		return docFileServiceI.getDocFileAll(null);
	}

	
	/**
	 * 功能说明：wct文档管理-预览 （跳转）
	 * @author wanchanghuang
	 * @createTime 2015年12月2日14:26:56
	 * */
	@RequestMapping("gotoView")
	public void gotoView(HttpServletResponse response, HttpServletRequest request,HttpSession session,String fileId) {
		String path=bundle.getString("service_url")+fileId;
		session.setAttribute("service_url", path);
		try {
			response.sendRedirect(request.getContextPath()+"/pms/FileManage/flexpaper/readFile.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("fileuploadJsp")
	public String getfileuploadJsp() {
		return "/pms/file/docfile/fileupload";
	}

	/*
	 * 上传
	 */
	@ResponseBody
	@RequestMapping("uploadfile")
	public Json uploadfile(String parentId, HttpServletRequest request,
			HttpSession session) {
		Json json = new Json();
		try {
			String err= docFileServiceI.uploadDocFile(parentId, request, session);
			json.setMsg("文件上传成功！");
			json.setSuccess(true);
			if(err!=null){
				json.setMsg(err);
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("文件上传异常", e);
			json.setMsg("文件上传失败！");
			json.setSuccess(false);
		}
		return json;

	}

	/*
	 * 下载
	 */
	@RequestMapping("filedownload")
	public void filedownload(HttpServletRequest request,
			HttpServletResponse response) {

		String fileurl = request.getParameter("fileurl");
		String fileName = request.getParameter("fileName");//传文件名
		FileOptionsUtil fo = new FileOptionsUtil();	
		//String filePath = request.getServletContext().getRealPath(bundle.getString("upload"))+fileurl.substring(fileurl.lastIndexOf("/"));//文件绝对路径
		String filePath = bundle.getString("save_url")+bundle.getString("upload")+fileurl.substring(fileurl.lastIndexOf("/"));//文件绝对路径
		boolean bool = fo.download2(filePath, fileName, response);

		if (bool) {
			log.info("下载成功");
		} else {
			log.error("下载失败");
		}

	}

	/*
	 * 删除文件
	 */
	@ResponseBody
	@RequestMapping("deleteFile")
	public Json deleteFile(String ids) {
		Json json = new Json();
		try {
			String err= docFileServiceI.deleteFile(ids);
			json.setMsg("删除产品规程文档成功!");
			json.setSuccess(true);
			if(err!=null){
				json.setMsg(err);
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除产品规程文档失败!");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("fileAddJsp")
	public String fileAddJsp() {
		return "/pms/file/docfile/fileAdd";
	}
	/**
	 * 文件夹添加
	 * */
	@ResponseBody
	@RequestMapping("savefile")
	public Json savefile(String parentId, HttpServletRequest request,
			HttpSession session, String filename) {
		Json json = new Json();
		try {
			String err= docFileServiceI.saveFile(parentId, filename, request, session);
			
			json.setMsg("文件夹添加成功！");
			json.setSuccess(true);
			if(err!=null){
				json.setMsg(err);
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("文件夹添加异常", e);
			json.setMsg("文件夹添加失败！");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("fileupdateJsp")
	public String fileupdateJsp(String id,HttpServletRequest request) throws Exception{
		DocFile docfile=  docFileServiceI.DocFileById(id);
		request.setAttribute("file", docfile);
		return "/pms/file/docfile/fileupdate";
	}
	@ResponseBody
	@RequestMapping("updatefilename")
	public Json updatefilename(DocFileBean fileBean,HttpSession session){
		Json json = new Json();
		try {
			String err= docFileServiceI.updatefile(fileBean,session);	
			json.setMsg("文件修改成功！");
			json.setSuccess(true);
				if(err!=null){
				json.setMsg(err);
				json.setSuccess(false);
				}
		} catch (Exception e) {
			log.error("文件修改异常", e);
			json.setMsg("文件修改失败！");
			json.setSuccess(false);
		}
		return json;
	}
	
}
