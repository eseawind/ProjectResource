package com.lanbao.dws.service.wct.qm;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.tools.JsonBean;
import com.lanbao.dws.model.wct.qm.QMOutWandDelInfo;
import com.lanbao.dws.model.wct.qm.QMOutWandItem;
import com.lanbao.dws.model.wct.qm.QMOutWard;
import com.lanbao.dws.model.wct.qm.QMUser;
import com.lanbao.dws.model.wct.qm.QmOutWandInfoBean;

/**
 * 外观检测
 * @author shisihai
 *
 */
public interface IAppearanceCheckService {
	//质量外观检测登录
	String qmLogin(QMUser token);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月7日 下午4:52:00 
	* 功能说明 ：保存数据
	 */
	JsonBean saveQmAppearanceCheck(String jsonData,String[] checkItemCodes,Integer[] checkVals);
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月8日 上午11:37:38 
	* 功能说明 ：查询外观巡检小项，用于选则
	 */
	PaginationResult<List<QMOutWandItem>>  queryQmOutWardItem(QMOutWandItem item,Pagination pagination);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月11日 下午3:13:07 
	* 功能说明 ：查询外观巡检主目录
	 */
	PaginationResult<List<QMOutWard>>  queryQmOutWardItem(QMOutWard item,Pagination pagination);
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月11日 下午4:43:10 
	* 功能说明 ：保存外观巡检主数据
	 */
	void saveQmOutWand(QMOutWard item,HttpSession session,String checkType);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月12日 上午11:22:42 
	* 功能说明 ：根据检测批次号，查询当前批次的检测详细
	 */
	PaginationResult<List<QMOutWandDelInfo>>  queryQmOutWardItemByBatch(QMOutWandDelInfo item,Pagination pagination);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月15日 上午9:29:50 
	* 功能说明 ：外观缺陷检测记录查询
	 */
	PaginationResult<List<QmOutWandInfoBean>> queryQmOutWandInfo(QmOutWandInfoBean param, Pagination pagination);
}

