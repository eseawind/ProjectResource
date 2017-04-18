package com.lanbao.dws.service.wct.docManager;

import java.util.List;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.docManager.DocFile;

public interface IDocFileService {
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月29日 上午10:13:50 
	* 功能说明 ：查询系统文档
	 */
	PaginationResult<List<DocFile>> getDocFiles(Pagination pagination,DocFile docFile);
}
