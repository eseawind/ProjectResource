package com.shlanbao.tzsc.pms.sch.workorder.service;

import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.base.mapping.ChangeShiftDatas;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdMatType;
import com.shlanbao.tzsc.base.mapping.QmChangeShift;
import com.shlanbao.tzsc.base.mapping.QmChangeShiftInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfoParams;
import com.shlanbao.tzsc.base.mapping.SchCalendar;
import com.shlanbao.tzsc.base.mapping.SchConStd;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.mapping.SchWorkorderBom;
import com.shlanbao.tzsc.base.mapping.SysMessageQueue;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.ParsingXmlDataBean;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;
import com.shlanbao.tzsc.pms.sch.workorder.beans.WorkOrderBean;

/**
 * 工单业务接口
 * @author Leejean
 * @create 2014年11月18日下午3:53:04
 */
public interface WorkOrderServiceI {
	/**
	 * 根据设备CODE 获取正在运行的工单 并填充辅料
	 * @throws Exception
	 */
	public SchWorkorder getWork(String eqp) throws Exception;
	/**
	 * 查询所有工单
	 * @author Leejean
	 * @create 2014年11月18日下午3:55:00
	 * @param workOrderBean 查询条件
	 * @param pageParams 分页参数
	 * @return
	 */
	public DataGrid getAllWorkOrders(WorkOrderBean workOrderBean,PageParams pageParams) throws Exception;
	/**
	 * 审核工单 单个
	 * @author Leejean
	 * @create 2014年11月25日下午1:09:15
	 * @param id 工单id号
	 * @throws Exception
	 */
	public void checkWorkOrder(String id) throws Exception;
	/**
	 * 审核工单 批量
	 * @author Leejean
	 * @create 2014年11月25日下午1:09:15
	 * @param ids
	 * @throws Exception
	 */
	public void checkWorkOrders(String ids) throws Exception;
	/**
	 * 修改工单状态
	 * @author Leejean
	 * @create 2014年11月25日下午1:12:11
	 * @param id 
	 * 1,下发 <br>
	 * 2,运行 -->运行时在生产实绩中保存val=0的数据，即采集程序只做update操作<br>
	 * 3,暂停 -->MES取消撤销工单时<br>
	 * 4,完成 -->工单完成,更新工单结束时间<br>
	 * 5,终止 -->错误的工单运行时，执行本操作删除生产实绩相关数据<br>
	 * 6,结束 -->工单已经反馈<br>
	 * 7,锁定 -->MES发起撤销时<br>
	 * 8,撤销 -->MES确定撤销时<br>
	 * @throws Exception
	 */
	public void editWorkOrderStatus(String id,Long sts) throws Exception;
	/**
	 * 根据id获取工单
	 * @author Leejean
	 * @create 2014年12月4日上午10:59:07
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public WorkOrderBean getWorkOrderById(String id) throws Exception;
	/**
	 * 新增工单
	 * @author Leejean
	 * @create 2014年12月24日下午1:22:48
	 * @param workOrderBean
	 * @return
	 */
	public void addOrder(WorkOrderBean workOrderBean) throws Exception;
	/**
	 * 初始化所有运行的工单辅料计数参数
	 * @author Leejean
	 * @create 2015年1月20日下午3:25:36
	 */
	public void initAllRunnedWorkOrderCalcValues() throws Exception;
	
	
	/**
	 * 自动换班
	 * @author luther.zhang
	 * @create 2015-04-16
	 */
	public void updateShiftData() throws Exception;
	/**
	 * 运行的工单
	 * @param code
	 * @return
	 */
	public SchWorkorder getRunSchWorkorder(String code)throws Exception;
	/**
	 * 根据设备CODE 获取正在运行的工单 并填充辅料
	 * @throws Exception
	 */
	public void queryRunWorkFl(SchWorkorder workorder) throws Exception;
	
	//以下方法是测试方法=====================================================
	/**
	 * 批量新增卷烟机、包装机 工单
	 * @author luther.zhang
	 * @create 2015-04-16
	 */
	public void saveBatckWork() throws Exception;
	/**
	 * 新增排班
	 * @param workOrderBean
	 * @return
	 */
	public boolean addPbWork(WorkOrderBean workOrderBean) throws Exception;
	/**
	 * 批量新增指定机台数据
	 * @author luther.zhang
	 * @create 2015-04-29
	 */
	public boolean saveBatckWork(WorkOrderBean workBean,ConStdBean[] flListBean,String date1,String date2) throws Exception;
	//END================================================================
	/**
	 * 张璐 2015.10.12
	 * 查询成型车间工单
	 */
	public DataGrid getFormingWorkOrders(WorkOrderBean workOrderBean,PageParams pageParams) throws Exception;
	
	/**
	 * 查询上一班次采集数据详细
	 * @createTime 2015年10月26日08:35:09
	 * @author wanchanghuang
	 * */
	public Map<Integer,List<ChangeShiftDatas>> getMapChangeShiftDates(ChangeShiftDatas csd);
	/**
	 * 删除工单
	 * @param ids
	 * shisihai
	 */
	public void deleteWorkOrders(String ids,String eqpName,String team,String date,String mat,SessionInfo info);
	/**
	 * 批量删除工单
	 * @param ids
	 * shisihai
	 */
	public void deleteBeatchWorkOrders(String ids, String eqpName, String team, String date, String mat,
			SessionInfo info);
	/**
	 * 【功能说明】：MES工单撤销
	 * @author wanchanghuang
	 * @createTime 2015年12月29日13:35:28
	 * */
	public boolean updateTntryCancel(SchWorkorder pxd);
	/**
	 * 【功能说明】：工单信号
	 * @author wch
	 * @return 
	 * @createTime 2015年12月18日09:07:30
	 * 
	 * */
	public boolean deleteAndCancelWS(ParsingXmlDataBean pxd);
	/**
	 * [功能说明]：质量接口-基础表QUALITY_INSPECTION
	 * @author wanchanghuang
	 * @crateTime 2016年1月12日16:40:37
	 * 
	 * */
	public QualityCheckInfo saveQualityInfo(QualityCheckInfo qci);
	/**
	 * [功能说明]：质量接口-基础详细QUALITY_INSPECTION_PARAM
	 * @author wanchanghuang
	 * @crateTime 2016年1月12日16:40:37
	 * 
	 * */
	public void saveQualityParamInfo(QualityCheckInfoParams qcip);
	
	/**
	 * [功能描述]：接口解析完成，调用日志
	 * @author wanchanghuang
	 * @createTime 2016年1月12日17:36:35
	 * */
	public void saveAndReturn(SysMessageQueue log);
	/**
	 * [功能描述]：接口解析,保存工厂日历
	 * @author wanchanghuang
	 * @createTime 2016年1月13日09:37:12
	 * */
	public void saveSchCalendar(SchCalendar scr1);
	/**
	 * [功能描述]：接口解析,工单下发，保存工单主数据
	 * @author wanchanghuang
	 * @createTime 2016年1月13日09:37:12
	 * */
	public SchWorkorder addSchWorkOrder(SchWorkorder swd);
	/**
	 * [功能描述]：接口解析,工单下发，保存工单对应的辅料主数据
	 * @author wanchanghuang
	 * @createTime 2016年1月13日09:37:12
	 * */
	public SchWorkorderBom saveSchWorkOrderBom(SchWorkorderBom swb);
	
	/**
	 * [功能描述]：物料主数据
	 * @return 
	 * 
	 * */
	public String queryRetByCode(MdMatType mmt);
	/**
	 * [功能描述]：插入md_mat表数据
	 * 
	 * */
	public void saveMdMatByCode(MdMat mm);
	
	public void saveSchConStd(SchConStd scs);
	
	
	public QmChangeShift saveQmChangeShift(QmChangeShift qcs);
	
	public void saveQmChangeShiftInfo(QmChangeShiftInfo qcsInfo);

	
}
