package com.lanbao.dws.service.wct.qm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.qm.QMOnlineCheck;
import com.lanbao.dws.service.wct.qm.IOnlineQmCheckService;

@Service
public class OnlineQmCheckServiceImpl implements IOnlineQmCheckService {
	@Autowired
    IPaginationDalClient dalClient;

	/**
	* @author 作者 : 
	* @version 创建时间：2016年7月2日 下午8:05:49 
	* 功能说明 ：查询综合测试台数据
	 */
	@Override
	public PaginationResult<List<QMOnlineCheck>> queryOnlineCheck(QMOnlineCheck param, Pagination pagination) {
		return dalClient.queryForList("qmInfo.queryOnlineCheck", param, QMOnlineCheck.class, pagination);
	}

}
