package com.shlanbao.tzsc.pms.sch.workorder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;
import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.pms.sch.workorder.beans.WorkOrderBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.wct.sch.workorder.service.WctWorkOrderServiceI;
/**
 * 工单控制器
 * @author Leejean
 * @create 2014年11月18日下午2:23:51
 */
@Controller
@RequestMapping("/pms/workorder")
public class WorkOrderController extends BaseController {
	@Autowired
	private WorkOrderServiceI workOrderService;
	/**
	 * 查询工单
	 */
	@ResponseBody
	@RequestMapping("/getAllWorkOrders")
	public DataGrid getAllWorkOrders(WorkOrderBean workOrderBean,PageParams pageParams){
		try {
			return workOrderService.getAllWorkOrders(workOrderBean,pageParams);
		} catch (Exception e) {
			log.error("查询工单异常", e);
		}
		return null;
	}
	/**
	 * 张璐 2015.10.12
	 * 查询成型车间工单
	 */
	@ResponseBody
	@RequestMapping("/getFormingWorkOrders")
	public DataGrid getFormingWorkOrders(WorkOrderBean workOrderBean,PageParams pageParams){
		try {
			return workOrderService.getFormingWorkOrders(workOrderBean,pageParams);
		} catch (Exception e) {
			log.error("查询工单异常", e);
		}
		return null;
	}
	/**
	 * 查看工单详情
	 */
	@RequestMapping("/goToWorkOrderDetail")
	public String goToWorkOrderDetail(HttpServletRequest request,String id){
		request.setAttribute("workOrderId",id);
		return "/pms/sch/workorder/workorderDetail";
	}
	/**
	 * 审核工单
	 * @author Leejean
	 * @create 2014年11月25日下午3:55:56
	 * @param id 工单ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkWorkOrder")
	public Json checkWorkOrder(String id){
		Json json = new Json();
		try {
			workOrderService.checkWorkOrder(id);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("审核工单失败", e);
			json.setMsg("审核工单失败!");
		}
		return json;
	}
	/**
	 * 批量审核工单
	 * @author Leejean
	 * @create 2014年11月25日下午3:56:15
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkWorkOrders")
	public Json checkWorkOrders(String ids){
		Json json = new Json();
		try {
			workOrderService.checkWorkOrders(ids);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("审核工单失败", e);
			json.setMsg("审核工单失败!");
		}
		return json;
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
	public Json editWorkOrderStatus(String id,Long sts){
		Json json = new Json();
		try {
			workOrderService.editWorkOrderStatus(id, sts);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("修改工单状态失败", e);
			json.setSuccess(false);
			json.setMsg("修改工单状态失败!错误原因:"+e.getMessage());
		}
		return json;
	}
	/**
	 * 新增工单
	 * @author Leejean
	 * @create 2014年12月24日下午1:22:48
	 * @param workOrderBean
	 * @return
	 */
	@RequestMapping("/goToOrderAddJsp")
	public String goToOrderAddJsp(){
		return "/pms/sch/workorder/orderAdd";
	}
	/**
	 * 新增工单
	 * @author Leejean
	 * @create 2014年12月24日下午1:22:48
	 * @param workOrderBean
	 * @return
	 */
	@RequestMapping("/goToOrderEditJsp")
	public String goToOrderEditJsp(){
		return "/pms/sch/workorder/orderEdit";
	}
	/**
	 * 新增工单
	 * @author Leejean
	 * @create 2014年12月24日下午1:22:48
	 * @param workOrderBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrder")
	public Json addOrder(WorkOrderBean workOrderBean){
		Json json = new Json();
		try {
			workOrderService.addOrder(workOrderBean);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("新增工单失败", e);
			json.setMsg("新增工单失败!");
		}
		return json;
	}
	/**
	 * 跳转至工单选择页面
	 * @author Leejean
	 * @create 2014年12月2日下午6:49:14
	 * @param request
	 * @param workshop 车间CODE
	 * @return
	 */
	@RequestMapping("/goToWorkOrderPickJsp")
	public String goToWorkOrderPickJsp(HttpServletRequest request,String workshop){
		request.setAttribute("workshop", workshop);
		return "/pms/sch/workorder/workorderPicker";
	}
	/**
	 * 张璐
	 * 成型实绩添加页面跳转
	 * @param request
	 * @param workshop
	 * @return
	 */
	@RequestMapping("/goToWorkOrderPickJspCX")
	public String goToWorkOrderPickJspCX(HttpServletRequest request,String workshop){
		request.setAttribute("workshop", workshop);
		return "/pms/sch/workorder/workorderPickerCX";
	}
	/**
	 * 实例化所有运行工单的计数参数
	 */
	@ResponseBody
	@RequestMapping("/initAllRunnedWorkOrderCalcValues")
	public Json initAllRunnedWorkOrderCalcValues(){
		Json json = new Json();
		try {
			workOrderService.initAllRunnedWorkOrderCalcValues();
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("修改工单状态失败", e);
			json.setMsg("修改工单状态失败!错误原因:"+e.getMessage());
		}
		return json;
	}
	
	//测试方法=================================================================
	/**
	 * 新增排班
	 * @param workOrderBean
	 * @return
	 */
	@RequestMapping("/goToPbWork")
	public String goToPbWork(){
		return "/pms/sch/workorder/orderAddPb";
	}
	/**
	 * 批量新增排班
	 */
	@ResponseBody
	@RequestMapping("/addPbWork")
	public Json addPbWork(WorkOrderBean workOrderBean){
		Json json=new Json();
		try {
			workOrderService.addPbWork(workOrderBean);
			json.setMsg("新增成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 新增工单
	 * @return
	 */
	@RequestMapping("/goToBatckWork")
	public String goToBatckWork(){
		return "/pms/sch/workorder/orderAddWork";
	}
	/**
	 * 删除工单
	 * @author Leejean
	 * @create 2014年11月25日下午3:56:15
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteWorkOrders")
	public Json deleteWorkOrders(String ids,String eqpName,String team,String date,String mat,HttpSession session){
		Json json = new Json();
		try {
			SessionInfo info=(SessionInfo) session.getAttribute("sessionInfo");
			workOrderService.deleteWorkOrders(ids,eqpName,team,date,mat,info);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("审核工单删除", e);
			json.setMsg("审核工单删除!");
		}
		return json;
	}
	
	/**
	 * 删除工单
	 * @author Leejean
	 * @create 2014年11月25日下午3:56:15
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/beatchDeleteWorkOrders")
	public Json beatchDeleteWorkOrders(String ids,String eqpName,String team,String date,String mat,HttpSession session){
		Json json = new Json();
		try {
			SessionInfo info=(SessionInfo) session.getAttribute("sessionInfo");
			workOrderService.deleteBeatchWorkOrders(ids,eqpName,team,date,mat,info);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("审核工单删除", e);
			json.setMsg("审核工单删除!");
		}
		return json;
	}
	
	
	/**
	 * 批量新增工单
	 */
	@ResponseBody
	@RequestMapping("/saveBatckWork")
	public Json saveBatckWork(String id, HttpServletRequest request){
		Json json=new Json();
		try {
			String type = request.getParameter("type");
			String equipmentId = request.getParameter("equipmentId");
			String qty = request.getParameter("qty");
			String unitId = request.getParameter("unitId");
			String matId = request.getParameter("matId");
			String reqString = request.getParameter("reqString");
			String date1=request.getParameter("date1");
			String date2=request.getParameter("date2");
			
			if(null!=reqString){
				WorkOrderBean bean = new WorkOrderBean();
				bean.setType(Long.parseLong(type));
				bean.setEquipmentId(equipmentId);
				bean.setQty(Double.parseDouble(qty));
				bean.setUnitId(unitId);
				bean.setMatId(matId);
				// 填充BEAN
				ConStdBean[] checkBean = (ConStdBean[]) JSONUtil.JSONString2ObjectArray(reqString,ConStdBean.class);
				boolean mes=workOrderService.saveBatckWork(bean,checkBean,date1,date2);
				if(mes){
					json.setMsg("新增成功!");
					json.setSuccess(true);
				}else{
					json.setMsg("新增失败,没有排班!");
					json.setSuccess(false);
				}
				
			}else{
				json.setMsg("请选择辅料!");
				json.setSuccess(false);
			}
			
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	
	
	/**
	 * 说明：该方法测试手动发送TSPM接口
	 * @author wch
	 * @date  2016年11月25日0:37:08
	 * */
	@ResponseBody
	@RequestMapping("/amsSendTOTSPM")
	public Json amsSendTOTSPM(){
		Json json = new Json();
		try {
			WctWorkOrderServiceI workOrderService = ApplicationContextUtil.getBean(WctWorkOrderServiceI.class);	
			SchCalendarBean bean =NeedData.getInstance().getScbean();
			workOrderService.AMSSendTroubleMessageTOTSPM(bean);
			json.setSuccess(true);
			json.setMsg("发送成功!");
		} catch (Exception e) {
			json.setMsg("发送失败!");
		}
		return json;
	}
}
