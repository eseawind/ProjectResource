package com.lanbao.dws.service.wct.pdDisplay;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.pddisplay.CalendarBean;
import com.lanbao.dws.model.wct.pddisplay.PdDisplayBean;
import com.lanbao.dws.model.wct.pddisplay.WorkOrderBean;

public interface IPdDisplayService {

	PaginationResult<List<PdDisplayBean>> queryWorkOrderByList(Pagination pagination,PdDisplayBean pbean);

	List<CalendarBean> getCurMonthCalendars(CalendarBean cdbean);

	void updateWorkOrder(WorkOrderBean bean);
	
	void sendMsgToDAC(HttpServletRequest request,WorkOrderBean bean);
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月2日 上午11:19:12 
	* 功能说明 ：获取成型机、封箱机工单
	 */
	PaginationResult<List<PdDisplayBean>> queryOtherWorkOrderByList(Pagination pagination,Map<String, Object> map);

	List<WorkOrderBean> queryWorkOrderByList();

	void saveLastSnapshotDatas(String chgShift, int equipmentID);
	

}
