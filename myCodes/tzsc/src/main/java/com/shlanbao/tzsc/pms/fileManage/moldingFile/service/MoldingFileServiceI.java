package com.shlanbao.tzsc.pms.fileManage.moldingFile.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.fileManage.moldingFile.beans.FileManageBean;
/**
 * 文件管理业务类
 * @author liuligong
 * @create 2014年10月11日上午
 */
public interface MoldingFileServiceI {

	/**
	 * 文件修改，上传
	 * @param session
	 * @param request
	 * @param fmBean
	 * @throws Exception
	 */
	public void fileUpdate(HttpSession session,HttpServletRequest request,FileManageBean fmBean) throws Exception;

	/**
	 * 查询 file
	 */
	public DataGrid queryFile(Long securityLevel,FileManageBean fmBean,PageParams pageParams);
	
	public void delFile(String id) throws Exception;


}

















