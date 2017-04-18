package com.lanbao.dws.service.wct.qm;

import java.util.List;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.qm.CraftRules;

/**
 * 工艺规程
 * @author shisihai
 *
 */
public interface ICraftRulesService {
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 上午10:58:55 
	* 功能说明 ： 查询工艺规程
	 */
	PaginationResult<List<CraftRules>> queryCraftRules(CraftRules param,Pagination pagination);
}
