package com.lanbao.dws.service.wct.pdDisplay.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.pddisplay.FLOfOrderResult;
import com.lanbao.dws.model.wct.pddisplay.OrderResultBean;
import com.lanbao.dws.service.wct.pdDisplay.IOrderResultService;

/**
 * 工作实际
 * 
 * @author shisihai
 *
 */
@Service
public class OrderResultServiceImpl implements IOrderResultService {
	@Autowired
	IPaginationDalClient dalClient;

	/**
	* @author 作者 : 
	* @version 创建时间：2016年6月30日 下午12:33:41 
	* 功能说明 ：查询工单实际概要
	 */
	@Override
	public PaginationResult<List<OrderResultBean>> queryWorkOrderByList(OrderResultBean params,
			Pagination pagination) {
		 return dalClient.queryForList("orderResult.queryOrderInfo", params, OrderResultBean.class, pagination);
	}
	
	/**
	* @author 作者 : 
	* @version 创建时间：2016年6月30日 下午4:12:41 
	* 功能说明 ：查询工单辅料详细
	 */
	@Override
	public List<FLOfOrderResult> getOrderDetailById(Model model, String orderId) {
		Map<String,Object> map=new HashMap<>();
		map.put("OrderId",orderId);
		List<FLOfOrderResult> datas=dalClient.queryForList("orderResult.queryOrderDetails", map, FLOfOrderResult.class);
		return datas;
	}

}
