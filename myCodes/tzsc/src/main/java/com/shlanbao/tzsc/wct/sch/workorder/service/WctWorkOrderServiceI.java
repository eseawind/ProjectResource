package com.shlanbao.tzsc.wct.sch.workorder.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.wct.sch.workorder.beans.WorkOrderBean;

/**
 * 工单业务接口
 * @author Leejean
 * @create 2014年11月18日下午3:53:04
 */
public interface WctWorkOrderServiceI {
	/**
	 * 查询所有工单
	 * @author Leejean
	 * @create 2014年11月18日下午3:55:00
	 * @param workOrderBean 查询条件
	 * @param pageParams 分页参数
	 * @return
	 */
	public List<WorkOrderBean> getNewWorkOrders(String equipmentCode) throws Exception;
	/**
	 * 修改工单状态
	 * @author Leejean
	 * @create 2014年12月16日上午11:43:56
	 * @param id 
	 * @param sts
	 * 1,下发 <br>
	 * 2,运行 -->运行时在生产实绩中保存val=0的数据，即采集程序只做update操作<br>
	 * 3,暂停 -->MES取消撤销工单时<br>
	 * 4,完成 -->工单完成,更新工单结束时间<br>
	 * 5,终止 -->错误的工单运行时，执行本操作删除生产实绩相关数据<br>
	 * 6,结束 -->工单已经反馈<br>
	 * 7,锁定 -->MES发起撤销时<br>
	 * 8,撤销 -->MES确定撤销时<br>
	 * @return 1：成功 2：已经有工单在运行，需完成前工单 3：MES接收失败<br><br><br>
	 
	 */
	public int editWorkOrderStatus(String id,Long sts,HttpServletRequest request) throws Exception;
	/**
	 * 是否有运行的工单
	 * @param id
	 * @param sts
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int queryIsRunWork(String id, Long sts, HttpServletRequest request)
			throws Exception;
	public void AMSSendTroubleMessageTOTSPM(SchCalendarBean bean);
}
