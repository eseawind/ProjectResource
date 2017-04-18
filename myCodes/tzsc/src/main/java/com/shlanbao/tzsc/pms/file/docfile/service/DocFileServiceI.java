package com.shlanbao.tzsc.pms.file.docfile.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.pms.file.docfile.beans.DocFileBean;

public interface DocFileServiceI {
	/**
	 * 文件管理业务类
	 * @author sunyuhang
	 * @create 2015年1月29日上午
	 */
	
	/**
	 * 查询带模糊查询
	 * */
	public List<DocFileBean> getDocFileAll(DocFileBean fileBean) throws Exception;
	/**
	 * 删除
	 * param id
	 * */
	public void deleteDocFile(String Id) throws Exception;
	/**
	 * 修改
	 * param DocFileBean
	 * */
	public String uploadDocFile(String parentId,HttpServletRequest request,HttpSession session) throws Exception;
	
	/**
	 * 下载
	 * param Id
	 * */
	public  boolean download(String filePath,String fileName,
			HttpServletResponse response);
	
	/**
	 * 
	 * 查对象 
	 * */
	public DocFile DocFileById(String Id) throws Exception;
	/**
	 * 
	 * 查对象 是否已经存在
	 * */
	public boolean DocFileByFileName(String fileName) throws Exception;
	/**
	 * 删除文档
	 */
	public String deleteFile(String ids) throws Exception;
	/**
	 * 添加文件夹
	 * */
	public String saveFile(String filePath,String fileName,HttpServletRequest request,HttpSession session)throws Exception;

	/**
	 * 
	 *修改文件夹名字 
	 */
	public String updatefile(DocFileBean docFileBean,HttpSession session);
	
	
}
