package com.lanbao.dws.service.wct.pdDisplay;

import java.util.List;

import org.springframework.ui.Model;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.pddisplay.FLOfOrderResult;
import com.lanbao.dws.model.wct.pddisplay.OrderResultBean;

/**
 * 工作实际
 * @author shisihai
 *
 */
public interface IOrderResultService {
	/**
	* @author 作者 : 
	* @version 创建时间：2016年6月30日 上午10:00:39 
	* 功能说明 ：工作实际查询
	 */
	PaginationResult<List<OrderResultBean>> queryWorkOrderByList(OrderResultBean params,Pagination pagination);
	
	/**
	* @author 作者 : 
	* @version 创建时间：2016年6月30日 下午4:01:54 
	* 功能说明 ：根据工单号获取工单详细
	 */
	List<FLOfOrderResult> getOrderDetailById(Model model,String orderId);

}
