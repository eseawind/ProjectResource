package com.lanbao.dws.service.wct.qm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.qm.CraftRules;
import com.lanbao.dws.service.wct.qm.ICraftRulesService;
/**
 * 工艺规程
 * @author shisihai
 *
 */
@Service
public class CraftRulesServiceImpl implements ICraftRulesService{
	@Autowired
    IPaginationDalClient dalClient;
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 上午11:07:52 
	* 功能说明 ：查询工艺规程
	 */
	@Override
	public PaginationResult<List<CraftRules>> queryCraftRules(CraftRules param, Pagination pagination) {
		return dalClient.queryForList("qmInfo.queryCraftRules", param, CraftRules.class, pagination);
	}
	
	
}
