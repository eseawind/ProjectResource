package com.shlanbao.tzsc.pms.fileManage.moldingFile.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.DocFileManageDaoI;
import com.shlanbao.tzsc.base.mapping.DocFilemanage;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.fileManage.moldingFile.beans.FileManageBean;
import com.shlanbao.tzsc.pms.fileManage.moldingFile.service.MoldingFileServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 文件管理实现类
 * @author liuligong
 * @create 2014年9月21日上午9:12
 */
@Service
public class MoldingFileServiceImpl extends BaseService implements MoldingFileServiceI{
	@Autowired
	private DocFileManageDaoI docFileManageDao;
	
	@Override
	public void fileUpdate(HttpSession session,HttpServletRequest request,FileManageBean fmBean) throws Exception{
		request.setCharacterEncoding("utf-8");
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		//DocFilemanage dfManage = BeanConvertor.copyProperties(fmBean, DocFilemanage.class);
	
		//获取文件名
		String[] fileUrls = (String[])request.getParameterValues("fileUrl")[0].split(",");
		String[] fileNames = (String[])request.getParameterValues("fileName")[0].split(",");
		
		Long L = Long.valueOf(request.getParameter("securityLevel"));
		
		for (int i = 0; i < fileUrls.length; i++) {
			DocFilemanage dfManage = new DocFilemanage();
			dfManage.setCreateName(sessionInfo.getUser().getName());
			dfManage.setCreatePersonId(sessionInfo.getUser().getId());
			dfManage.setCreateTime(new java.util.Date());
			dfManage.setSecurityLevel(L);

			dfManage.setFileId(fileUrls[i]);
			dfManage.setFileName(fileNames[i].substring(0,fileNames[i].lastIndexOf(".")));
			dfManage.setFileType(fileUrls[i].substring(fileUrls[i].lastIndexOf(".")+1));
			dfManage.setIsDeleted("0");
			docFileManageDao.save(dfManage);
		}
		
	}

	@Override
	public DataGrid queryFile(Long securityLevel,FileManageBean fmBean, PageParams pageParams) {
		//sql 语句拼装
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from DocFilemanage o where 1=1 and o.isDeleted='0' and o.securityLevel = ");
		strBuffer.append(securityLevel);
		String hql =strBuffer.toString();
		String params="";
		if(StringUtil.notNull(fmBean.getFileName())){
			params +=" and o.fileName like '%"+fmBean.getFileName()+"%'";
		}
		//执行查询 
		List rows = docFileManageDao.queryByPage(hql.concat(params), pageParams);
		
		strBuffer = new StringBuffer();
		strBuffer.append("select count(*) from DocFilemanage o where 1=1 and o.isDeleted='0' and o.securityLevel = ");
		strBuffer.append(securityLevel);
		hql = strBuffer.toString();
		
		try {
			rows = BeanConvertor.copyList(rows, FileManageBean.class);
			long total = docFileManageDao.queryTotal(hql.concat(params));
			hql = null;
			params = null;
			strBuffer = null;
			return new DataGrid(rows, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}

	@Override
	public void delFile(String id) throws Exception {
		docFileManageDao.findById(DocFilemanage.class, id).setIsDeleted("1");
	}
}
