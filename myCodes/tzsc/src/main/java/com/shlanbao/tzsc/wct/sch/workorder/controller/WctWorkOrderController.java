package com.shlanbao.tzsc.wct.sch.workorder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.wct.sch.workorder.beans.WorkOrderBean;
import com.shlanbao.tzsc.wct.sch.workorder.service.WctWorkOrderServiceI;
/**
 * 工单控制器
 * @author Leejean
 * @create 2014年11月18日下午2:23:51
 */
@Controller
@RequestMapping("/wct/workorder")
public class WctWorkOrderController extends BaseController {
	@Autowired
	private WctWorkOrderServiceI wctWorkOrderService;
	/**
	 * 查询某机台工单按时间排序前10条工单
	 */
	@ResponseBody
	@RequestMapping("/getNewWorkOrders")
	public List<WorkOrderBean> getAllWorkOrders(String equipmentCode){
		try {
			return wctWorkOrderService.getNewWorkOrders(equipmentCode);
		} catch (Exception e) {
			log.error("查询工单异常", e);
		}
		return null;
	}
	/**
	 * 修改工单状态
	 * @author Leejean
	 * @create 2014年11月25日下午3:56:30
	 * @param id 工单ID
	 * @param sts 状态 工单状态
	 * (
	 * 1,下发 <br>
	 * 2,运行 -->运行时在生产实绩中保存val=0的数据，即采集程序只做update操作<br>
	 * 3,暂停 -->MES取消撤销工单时<br>
	 * 4,完成 -->工单完成,更新工单结束时间<br>
	 * 5,终止 -->错误的工单运行时，执行本操作删除生产实绩相关数据<br>
	 * 6,结束 -->工单已经反馈<br>
	 * 7,锁定 -->MES发起撤销时<br>
	 * 8,撤销 -->MES确定撤销时<br>
	 * )
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editWorkOrderStatus")
	public Json editWorkOrderStatus(String id,Long sts,HttpServletRequest request){
		Json json = new Json();
		try {
			int result = wctWorkOrderService.editWorkOrderStatus(id, sts, request);
			json.setObj(result);
			if(result==1){				
				json.setSuccess(true);
				json.setMsg("操作成功!");
			}else if(result==2){
				json.setMsg("本机台已有运行工单，请完成该工单在运行本工单!");
			}else if(result==3){
				json.setMsg("MES接收工单状态变化信号失败!");
			}
		} catch (Exception e) {
			log.error("修改工单状态失败", e);
			json.setMsg("修改工单状态失败!");
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/queryIsRunWork")
	public Json queryIsRunWork(String id,Long sts,HttpServletRequest request){
		Json json = new Json();
		try {
			
			int result = wctWorkOrderService.queryIsRunWork(id, sts, request);
			json.setObj(result);
			if(result==2){
				json.setMsg("本机台已有运行工单，请完成该工单在运行本工单!");
			}else if(result==3){
				json.setMsg("数采站正在清理数据,请稍后再试!");
			}
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("操作失败", e);
			json.setSuccess(false);
			json.setMsg("操作失败!");
		}
		return json;
	}
}
