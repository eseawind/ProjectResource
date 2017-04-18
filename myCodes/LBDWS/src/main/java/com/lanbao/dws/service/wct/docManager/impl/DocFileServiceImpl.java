package com.lanbao.dws.service.wct.docManager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.support.PaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.docManager.DocFile;
import com.lanbao.dws.service.wct.docManager.IDocFileService;

@Service
public class DocFileServiceImpl implements IDocFileService {
	@Autowired
	PaginationDalClient dalClient;

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月29日 上午10:15:03 
	* 功能说明 ：查询当前文档
	 */
	@Override
	public PaginationResult<List<DocFile>> getDocFiles(Pagination pagination,DocFile docFile) {
		return dalClient.queryForList("docManager.getDocFiles",docFile,DocFile.class, pagination);
	}
	
	
	
}
