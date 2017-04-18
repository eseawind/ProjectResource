package com.shlanbao.tzsc.wct.sch.workorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.sch.workorder.service.CraftServiceI;
/**
 * 工艺参数控制器
 * @author Leejean
 * @create 2014年11月18日下午2:23:51
 */
//@Controller
@RequestMapping("/wct/craft")
public class CraftController extends BaseController {
	@Autowired
	private CraftServiceI craftService;
	/**
	 * 获取工单的Craft工艺参数
	 * @author Leejean
	 * @create 2014年11月20日下午4:32:11
	 * @param workOrderId 工单ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCraftByWorkOrder")
	public DataGrid getCraftByWorkOrder(String workOrderId){
		try {
			return craftService.queryCraftByWorkOrder(workOrderId);
		} catch (Exception e) {
			log.error("获取工单["+workOrderId+"]的工艺参数异常", e);
		}
		return null;
	}
}
