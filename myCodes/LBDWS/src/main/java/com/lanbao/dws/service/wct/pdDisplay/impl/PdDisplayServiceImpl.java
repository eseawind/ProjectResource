package com.lanbao.dws.service.wct.pdDisplay.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.ibm.framework.dal.transaction.template.CallBackTemplate;
import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.common.data.dac.DataReceiveHandler;
import com.lanbao.dws.common.data.dac.handler.DataHandler;
import com.lanbao.dws.common.data.webservices.client.SendMessageToJyscClient;
import com.lanbao.dws.common.init.BaseParams;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.common.tools.WebContextUtil;
import com.lanbao.dws.common.tools.XmlUtil;
import com.lanbao.dws.model.wct.dac.OutputBean;
import com.lanbao.dws.model.wct.pddisplay.CalendarBean;
import com.lanbao.dws.model.wct.pddisplay.PdDisplayBean;
import com.lanbao.dws.model.wct.pddisplay.WorkOrderBean;
import com.lanbao.dws.service.wct.pdDisplay.IPdDisplayService;
import com.lanbao.dws.service.wct.realTimeData.IRealTimeDataService;

@Service("ipdDisplaySevice")
public class PdDisplayServiceImpl implements IPdDisplayService {

	@Autowired
    IPaginationDalClient dalClient;
	@Autowired
	IRealTimeDataService realTimeDataService;
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * [功能说明]：生产工单-跳转查询
	 * @param url-返回相应页面路径
	 * @param pagination分页对象
	 * */
	@Override
	public PaginationResult<List<PdDisplayBean>> queryWorkOrderByList(Pagination pagination,PdDisplayBean bean) {
		return dalClient.queryForList("pddisplay.queryWorkOrderByList", bean,PdDisplayBean.class, pagination);
	}

	/**
	 * [工能说明]：卷包车间排班-查询排班信息
	 * @author wanchanghuang
	 * @date 2016年6月29日20:58:51
	 * @param url：返回页面链接
	 * */
	@Override
	public List<CalendarBean> getCurMonthCalendars(CalendarBean cdbean) {
		// TODO Auto-generated method stub
		return dalClient.queryForList("pddisplay.querySchCalendarByList", cdbean, CalendarBean.class);
	}

	/**
	 * [工能说明]：卷包车间修改工单状态
	 * @author wanchanghuang
	 * @date 2016年6月29日20:58:51
	 * @param url：返回页面链接
	 * */
	@Override
	public void updateWorkOrder(final WorkOrderBean bean) {
		int flag=0;
		try {
			flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
				@Override
				public Integer invoke() {
					//修改工单状态
					dalClient.execute("workOrder.updateWorkOrder", bean);
					//如果是运行工单，创建sch_stat_out 和sch_stat_input  初始数据
					if(bean.getSts()==Constants.WORKORDERSTATUS2){
						OutputBean outputBean=new OutputBean();
						outputBean.setWorkorder(bean.getId());
						outputBean.setStim(DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
						//WCT手动点击了运行工单，则添加该工单的产出数据和辅料消耗数据
						realTimeDataService.addOutput(outputBean, 0);
					}else if(bean.getSts()==Constants.WORKORDERSTATUS4){
						//修改工单实际完成时间
						dalClient.execute("workOrder.updateWorkOrderEndTime", bean);
						//修改产出表实际结束时间
						dalClient.execute("workOrder.updateWorkOrderOutEndTime", bean);
					}
					//向PMS发送工单改变消息
					sendOrderStatusToPMs(bean);
					return 1;
				}
			});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	/**
	 * 向PMS发送工单状态变化信息
	 * @param bean
	 */
	private void sendOrderStatusToPMs(WorkOrderBean bean) {
		List<Map<String,Object>> orders=new ArrayList<>();
		Map<String,Object> map=new HashMap<>();
		map.put("id", bean.getId());
		orders.add(map);
		SendMessageToJyscClient.getInstance().sendOrderStatusChange(orders,String.valueOf(bean.getSts()));
	}

	/**
	 * [功能说明]：查询当天所有已运行的工单
	 * @author wanchanghuang
	 * @date 2016年8月2日11:06:48
	 * */
	@Override
	public List<WorkOrderBean> queryWorkOrderByList() {
		return dalClient.queryForList("pddisplay.querySchWorkOrder", null,WorkOrderBean.class);
	}

	/** 
	 * [说明]：1）保存当前班次最后快照数据；2）关闭当前工单  3)注销session
	 *       #91-换成早班  92-换成中班   93-换成晚班   
	 * @param chgShift 
	 *  
	 * */
	@Override
	public void saveLastSnapshotDatas(String chgShift,int eqpCode) {
		//区分卷包和成型
		int workShop=StringUtil.queryWorkshopByEqpCode(eqpCode);
		Map<String,String> map =null;
		//卷包机
		if(workShop==1){
			//卷包机
			map=BaseParams.getDacMap();
			//工单类型
			map.put("types","1,2,3");
		}else if(workShop==2){
			//成型机
			map=BaseParams.getFilterDacMap();
			//工单类型
			map.put("types","4");
		}else{
			return;
		}
		
		if(!chgShift.equals( map.get("eshift") )){
			map=Constants.getShiftId(new HashMap<String, String>(),chgShift,workShop);
			if(null!=map.get("shift")){
				//完成当前班次工单(关闭当前工单) 完成当天当前班次
				//1.查询符合条件的工单，放到集合中，之后发送给PMS，由PMS反馈给MES（工单完成信号）
				List<Map<String,Object>> orders=dalClient.queryForList("pddisplay.queryNeedcloseWorkOrderByShift", map);
				if(orders!=null && orders.size()>0){
					//保存最后一次数据
					DataHandler.getInstance().saveAllData();
					//2.修改工单状态为完成
					Map<String,Object> params=new HashMap<>();
					String  oids=convertList2Str(orders);
					params.put("oids", oids);
					params.put("nowTime", new Date());
					dalClient.execute("pddisplay.closeWorkOrderByShift",params);
					//3.更新产出表工单结束时间
					dalClient.execute("pddisplay.closeOrderUpdateOutEtime",params);
					//向PMS发送消息
					try {
						SendMessageToJyscClient.getInstance().sendOrderStatusChange(orders,"4");
					} catch (Exception e) {
						e.printStackTrace();
					}
					log.info("接收DAC换班指令并执行完成！"+DateUtil.formatDateToString(new Date(), "yyyy-MM-dd hh:mm:ss"));
				}
			}
		}
		//注销所有session
		//WebContextUtil.getSession().invalidate();
	}
	private String  convertList2Str(List<Map<String,Object>> list){
		StringBuffer sb=new StringBuffer();
		for (Map<String, Object> map : list) {
			sb.append(StringUtil.convertObjToString(map.get("id")));
			sb.append(",");
		}
		if(sb.length()>1){
			//去掉最后一个“,”
			int index=sb.lastIndexOf(",");
			return sb.substring(0, index);
		}
		return sb.toString();
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年8月2日 上午11:19:44 
	* 功能说明 ：获取成型机、封箱机工单
	 */
	@Override
	public PaginationResult<List<PdDisplayBean>> queryOtherWorkOrderByList(Pagination pagination, Map<String, Object> map) {
		return dalClient.queryForList("pddisplay.queryOtherWorkOrderByList", map,PdDisplayBean.class, pagination);
	}

	/**
	 * 将List<map> 转 Map<List>
	 * @param list
	 * @return
	 */
	private HashMap<String,List<String>>  convertList2Map(List<Map<String,Object>> list){
		HashMap<String,List<String>> paramsMap=new HashMap<>();
		List<String> ids=new ArrayList<>();
		for (Map<String, Object> map : list) {
			ids.add(StringUtil.convertObjToString(map.get("id")));
		}
		paramsMap.put("oids", ids);
		return paramsMap;
	}
	
	/**
	 * 根据工单号切换牌号，向DAC发送换牌清空数据消息
	 * @param request
	 * @param bean
	 */
	@Override
	public void sendMsgToDAC(HttpServletRequest request,WorkOrderBean bean) {
		try {
			//根据工单号查询设备code，
			String eqpCode="";
			Map<String,Object> result=dalClient.queryForMap("pddisplay.queryEqpCodeByOrderId", bean);
			if(result!=null){
				eqpCode=StringUtil.convertObjToString(result.get("eqpCode"));
			}
			if(!StringUtil.notEmpty(eqpCode)){
				return;
			}
			int queryCode=Integer.valueOf(eqpCode);
			if(queryCode>30 && queryCode<71){
				//匹配到DAC的IP地址
				String xmlPath = WebContextUtil.getVirtualPath("", request)+File.separator + "WEB-INF\\classes\\equipment.xml" ;
				NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "equipment");
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					//设备code
					String code=XmlUtil.getValueByNodeName(node, "code");
					if(code.equals(eqpCode)){
						//根据IP取出会话，向改会话发送消息。
						String ip=XmlUtil.getValueByNodeName(node, "ip");
						IoSession session=Constants.dacMap.get(ip);
						//dac接收0x63为班内换牌,只有包装机需要发送清空指令
						byte[] message={0x63,0x63};
						session.write(message);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
