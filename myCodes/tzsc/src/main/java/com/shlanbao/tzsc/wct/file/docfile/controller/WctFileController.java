package com.shlanbao.tzsc.wct.file.docfile.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.wct.file.docfile.beans.WctFileBean;
import com.shlanbao.tzsc.wct.file.docfile.beans.WctTreeBean;
import com.shlanbao.tzsc.wct.file.docfile.service.WctfileServiceI;
import com.sun.net.httpserver.HttpServer;
@Controller
@RequestMapping("/wct/file/docfile")
public class WctFileController extends BaseController {
	@Autowired
	private WctfileServiceI wctfileServiceI;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	/**
	 * 查所有
	 */
	@ResponseBody
	@RequestMapping("getList")
	public DataGrid getList(String filename,int pageIndex) throws Exception{
		return wctfileServiceI.getTreeList(filename,pageIndex);
	} 
	
	/**
	 *返回子级
	 */
	@ResponseBody
	@RequestMapping("getTreeList")
	public  List<WctTreeBean> getTreeList(String pid,HttpSession session) throws Exception{
	    session.setAttribute("service_url", bundle.getString("service_url"));
		return wctfileServiceI.getByPid(pid);
	} 
	
	/**
	 *预览 
	 */
	@ResponseBody
	@RequestMapping("getReadFileJsp")
	public Json getReadFileJsp(String id,HttpSession session){
		Json json=new Json();
		try {
			WctFileBean fileBean=wctfileServiceI.getById(id);
			if(fileBean!=null){
				String str=fileBean.getUrl();
				String name=str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));
				String swfPath=bundle.getString("swf")+"/"+name+".swf";
				session.setAttribute("wctswfPath", swfPath);
				json.setSuccess(true);
				json.setMsg("加载成功");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("加载失败！");
			e.printStackTrace();
		}
		return json;
	
	}
	
}
