package com.lanbao.dws.service.wct.qm;

import java.util.List;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.qm.QMOnlineCheck;

/**
 * 综合测试台检测数据
 */
public interface IOnlineQmCheckService {
	/**
	* @author 作者 : 
	* @version 创建时间：2016年7月2日 下午8:04:18 
	* 功能说明 ：查询综合测试台质量检测记录
	 */
	PaginationResult<List<QMOnlineCheck>> queryOnlineCheck(QMOnlineCheck param,Pagination pagination);
}
