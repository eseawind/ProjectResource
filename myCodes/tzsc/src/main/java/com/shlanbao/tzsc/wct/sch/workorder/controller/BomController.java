package com.shlanbao.tzsc.wct.sch.workorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.sch.workorder.service.BomServiceI;
/**
 * BOM控制器
 * @author Leejean
 * @date 2015年3月2日 下午2:14:29
 */
//@Controller
@RequestMapping("/wct/bom")
public class BomController extends BaseController {
	@Autowired
	private BomServiceI bomService;
	/**
	 * 根据工单获得BOM
	 * @author Leejean
	 * @date 2015年3月2日 下午2:12:26 
	 * @param workOrderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBomByWorkOrder")
	public DataGrid getBomByWorkOrder(String workOrderId){
		try {
			return bomService.queryBomByWorkOrder(workOrderId);
		} catch (Exception e) {
			log.error("获取工单["+workOrderId+"]物料清单异常",e);
		}
		return null;
	}
}
