package com.shlanbao.tzsc.wct.file.docfile.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.file.docfile.beans.WctFileBean;
import com.shlanbao.tzsc.wct.file.docfile.beans.WctTreeBean;



public interface WctfileServiceI {

	/**
	 *查子节点 
	 */
	public List<WctTreeBean> getByPid(String pid);
	
	/**
	 * 查对象
	 */
	public WctFileBean getById(String id)throws Exception ;
	
	
	/**
	 * 查询所有文件
	 */
	public DataGrid getTreeList(String filename,int pageIndex) throws Exception;
}
