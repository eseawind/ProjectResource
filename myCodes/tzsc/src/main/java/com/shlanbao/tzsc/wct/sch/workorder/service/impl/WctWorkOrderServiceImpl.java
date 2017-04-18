package com.shlanbao.tzsc.wct.sch.workorder.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.interceptor.ParsingXmlDataInterceptor;
import com.shlanbao.tzsc.base.interceptor.WorkOrderStatChangeInterceptor;
import com.shlanbao.tzsc.base.mapping.MdManualShift;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.handler.DataHandler;
import com.shlanbao.tzsc.data.runtime.handler.DbOutputOrInputInfos;
import com.shlanbao.tzsc.data.runtime.handler.FilterCalcValue;
import com.shlanbao.tzsc.data.runtime.handler.PackerCalcValue;
import com.shlanbao.tzsc.data.runtime.handler.RollerCalcValue;
import com.shlanbao.tzsc.data.webservice.client.TSPMSendMessageClient;
import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.pms.sch.manualshift.service.ManualShiftServiceI;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.pms.sch.stat.service.StatServiceI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;
import com.shlanbao.tzsc.wct.sch.workorder.beans.WorkOrderBean;
import com.shlanbao.tzsc.wct.sch.workorder.service.WctWorkOrderServiceI;
@Service
public class WctWorkOrderServiceImpl extends BaseService implements WctWorkOrderServiceI{
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Autowired
	private StatServiceI statService;
	@Autowired
	private ManualShiftServiceI wctManualShiftService;
	@Autowired
	private StatServiceI statServiceImpl;
	@Override
	public int queryIsRunWork(String id, Long sts,
			HttpServletRequest request) throws Exception {
		if(sts==2){//执行运行操作时
			//判断本机台有无工单仍在运行
			Object runningOrder = schWorkorderDao.unique("from SchWorkorder o where o.sts=2 and o.mdEquipment.id=? and o.date='"+MESConvertToJB.rutDateStr()+"' ",id);
			if(null!=runningOrder){
				return 2;
			}
			String eqCode =request.getParameter("eqCode");
			if(null!=eqCode&&!"".equals(eqCode)){//查询 上班 结束的 工单 DAC是否处理好了
				MdManualShift shift = wctManualShiftService.getManualShift(eqCode,"E");
				if(null!=shift){
					if(shift.getDasprocess()!=1){
						return 3;
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public int editWorkOrderStatus(String id, Long sts,
			HttpServletRequest request) throws Exception {
		
		SchWorkorder schWorkorder = schWorkorderDao.findById(SchWorkorder.class, id);
		if(schWorkorder != null){
			/* 1,下发 <br>
			 * 2,运行 -->运行时在生产实绩中保存val=0的数据，即采集程序只做update操作<br>
			 * 3,暂停 -->MES取消撤销工单时<br>
			 * 4,完成 -->工单完成,更新工单结束时间<br>
			 * 5,终止 -->错误的工单运行时，执行本操作删除生产实绩相关数据<br>
			 * 6,结束 -->工单已经反馈<br>
			 * 7,锁定 -->MES发起撤销时<br>
			 * 8,撤销 -->MES确定撤销时<br>
			 */
			if(sts==1){
				schWorkorder.setIsAuto("Y");//下发工单 默认自动运行
			}
			if(sts==2){//运行工单时
				schWorkorder.setSts(sts);
				Date date = new Date();
				schWorkorder.setRealStim(date);//实绩开始时间
				//保存生产实绩空数据
				OutputBean outputBean = new OutputBean();
				outputBean.setWorkorder(id);
				outputBean.setStim(DateUtil.formatDateToString(date, "yyyy-MM-dd HH:mm:ss"));//保证工单与生产实绩开始时间同步。
				statService.addOutput(outputBean, 0);
				//向DataHandler中calcValues保存辅料计数参数和烟机滚轴系数
				this.setCalcValue(schWorkorder);//这段 wct中没有添加， add by luther.zhang 20150415
				//记录换班换牌信息放在回调函数里面处理
			}else if(sts==0){
				schWorkorder.setIsAuto("N");//表示 手动结束工单 add by luther.zhang 20150417
			}else if(sts==4){//完成
				String ecode=schWorkorder.getMdEquipment().getEquipmentCode();
				Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos = DataHandler.getDbOutputOrInputInfos();
				String dbOutputId = DataHandler.getDbOutputId(dbOutputOrInputInfos, ecode);
				if(dbOutputId!=null){
					OutputBean outputBean=new OutputBean();
					outputBean.setId(dbOutputId);
					//2015年6月10日 15:33:37 	增加实绩完成时间 by·luo
					outputBean.setEtim(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					statServiceImpl.editOutput(outputBean);
				}
				schWorkorder.setSts(sts);
				schWorkorder.setRealEtim(new Date());//实绩结束时间	
				statService.saveEquipmentData(ecode);
				//完成时保存交班信息;记录换班换牌信息放在回调函数里面处理
				
			}else if(sts==5){
				schWorkorder.setSts(sts);
				//删除生产实绩数据
				statService.deleteOutputByWorkOrder(id);
			}
			//next step向MES系统发送工单状态变化信号(MES接口暂未开发)
			//...发送MES失败标示为3
			//return 3;
			schWorkorder.setSts(sts);
			WorkOrderStatChangeInterceptor workOrderStatChangeInterceptor=new WorkOrderStatChangeInterceptor();
			workOrderStatChangeInterceptor.sendWorkorderChangeInfoToMES(schWorkorder,id, sts);
		}else{
			log.error("id:"+id+"的工单不存在");
			throw new Exception();
		}
		return 1;
	}
	/**
	 * 运行工单时，向DataHandler中calcValues保存辅料计数参数和烟机滚轴系数
	 * @author Leejean
	 * @create 2015年1月19日上午11:41:01
	 * @param schWorkorder
	 * @throws Exception 
	 */
	private void setCalcValue(SchWorkorder schWorkorder) throws Exception {
		// 获得工单类型
		/**
		    1: "卷烟机工单";
			2: "包装机工单";
			3: "封箱机工单";
			4: "成型机工单";
		 */
		Long workorderType = schWorkorder.getType();
		String equipmentCode = schWorkorder.getMdEquipment().getEquipmentCode();
		String equipmentName = schWorkorder.getMdEquipment().getEquipmentName();
		String workOrderCode = schWorkorder.getCode();
		Object calcValue = null;
		if(workorderType==1){
			String hql= "select mp.val,mp.mdMat.mdMatType.code from SchWorkorderBom bom,MdMatParam mp where bom.schWorkorder.id=? and bom.mdMat.id=mp.mdMat.id";
			List<Object[]> vals = schWorkorderDao.queryObjectArray(hql, schWorkorder.getId());
			if(vals==null){
				throw new Exception("工单号:"+workOrderCode+" 物料信息未在辅料计数参数中配置，请联系管理员配置");
			}
			Double shuisongzhiValue = null, //水松纸系数
					shuisongzhiAxisValue = null,//水松纸滚轴
					juanyanzhiAxisValue = null;//卷烟纸滚轴
			
			for (Object[] o : vals) {
				if(o[1].toString().equals("3")){//水松纸系数
					shuisongzhiValue = Double.valueOf(o[0].toString());
				}				
			}
			
			List<Object[]> axles = schWorkorderDao.queryObjectArray(
					"select mep.axlePz,mep.axleSsz from MdEquipmentParam mep where mep.mdEquipment.equipmentCode=? ", schWorkorder.getMdEquipment().getEquipmentCode());
			if(axles!=null&&axles.size()>0){
				juanyanzhiAxisValue = Double.valueOf(axles.get(0)[0].toString());
				shuisongzhiAxisValue = Double.valueOf(axles.get(0)[1].toString());
			}
			//判断系数是否配置
			if(juanyanzhiAxisValue==null){
				throw new Exception(equipmentName+"卷烟纸滚轴系数未配置");
			}
			if(shuisongzhiAxisValue==null){
				throw new Exception(equipmentName+"水松纸滚轴系数未配置");
			}
			calcValue = new RollerCalcValue(shuisongzhiValue, shuisongzhiAxisValue, juanyanzhiAxisValue);
			
			DataHandler.setCalcValueIntoMap(equipmentCode, calcValue);
		}
		else if(workorderType==2){
			String hql= "select mp.val,mp.mdMat.mdMatType.code from SchWorkorderBom bom,MdMatParam mp where bom.schWorkorder.id=? and bom.mdMat.id=mp.mdMat.id";
			List<Object[]> vals = schWorkorderDao.queryObjectArray(hql, schWorkorder.getId());
			if(vals==null){
				throw new Exception("本工单物料信息未在辅料计数参数中配置，请联系管理员配置");
			}
			 Double xiaohemoValue = null, //小盒膜系数
					tiaohemoValue = null, //条盒膜系数
					neichenzhiValue = null;//内衬纸系数
			 for (Object[] o : vals) {
					if(o[1].toString().equals("5")){//小盒膜系数
						xiaohemoValue = Double.valueOf(o[0].toString());
					}
					if(o[1].toString().equals("6")){//条盒膜系数
						tiaohemoValue = Double.valueOf(o[0].toString());			
					}
					if(o[1].toString().equals("9")){//内衬纸系数
						neichenzhiValue = Double.valueOf(o[0].toString());
					}
				}
			//判断是否配置完整
			if(xiaohemoValue==null){
				throw new Exception("工单号:"+workOrderCode+" 小盒膜辅料计数参数未配置");
			}
			if(tiaohemoValue==null){
				throw new Exception("工单号:"+workOrderCode+" 条盒膜辅料计数参数未配置");
			}
			if(neichenzhiValue==null){
				throw new Exception("工单号:"+workOrderCode+" 内衬纸辅料计数参数未配置");
			}
			calcValue = new PackerCalcValue(xiaohemoValue, tiaohemoValue, neichenzhiValue);
			
			DataHandler.setCalcValueIntoMap(equipmentCode, calcValue);
		}
		else if(workorderType==4){
			Double panzhiAxisValue = null;
			Object obj = schWorkorderDao.unique(
					"select mep.axlePz from MdEquipmentParam mep where mep.mdEquipment.equipmentCode=? ", schWorkorder.getMdEquipment().getEquipmentCode());
			if(obj!=null){
				panzhiAxisValue = Double.valueOf(obj.toString());
			}
			if(panzhiAxisValue==null){
				throw new Exception(equipmentName+" 滚轴系数未配置");
			}
			calcValue = new FilterCalcValue(panzhiAxisValue);
			
			DataHandler.setCalcValueIntoMap(equipmentCode, calcValue);
		}
		
	}
	
	@Override
	public List<WorkOrderBean> getNewWorkOrders(String equipmentCode)
			throws Exception {
		String todayTime=MESConvertToJB.rutDateStr();
		String tody="and o.date>='"+todayTime+"' and o.date<='"+todayTime+"'";
		String hql = "from SchWorkorder o left join fetch o.mdTeam t left join fetch o.mdShift s left join fetch o.mdEquipment e  left join fetch o.mdUnit u  left join fetch o.mdMat m where o.del=0 "+tody+" and o.isCheck =1 and o.sts<>4 and e.equipmentCode=?  ORDER BY o.stim asc";
		List<SchWorkorder> schWorkorders = schWorkorderDao.query(hql, equipmentCode);
		List<WorkOrderBean> orderBeans = new ArrayList<WorkOrderBean>();
		for (SchWorkorder schWorkorder : schWorkorders) {
			WorkOrderBean e = new WorkOrderBean();
			e.setId(schWorkorder.getId());
			e.setCode(schWorkorder.getCode());
			e.setMat(schWorkorder.getMdMat().getName());
			//e.setEquipment(schWorkorder.get);
			e.setShift(schWorkorder.getMdShift().getName());
			e.setTeam(schWorkorder.getMdTeam().getName());
			e.setQty(schWorkorder.getQty());
			e.setUnit(schWorkorder.getMdUnit().getName());
			e.setStim(DateUtil.formatDateToString(schWorkorder.getStim(), "MM-dd HH:mm"));
			e.setEtim(DateUtil.formatDateToString(schWorkorder.getEtim(),"HH:mm"));
			e.setSts(schWorkorder.getSts());
			//add by luther.zhang 20150401
			if(null!=schWorkorder.getMdEquipment()){
				e.setEqp(schWorkorder.getMdEquipment().getId());
			}
			e.setTyle(schWorkorder.getType());
			if(null!=schWorkorder.getMdShift()){
				e.setShiftCode(schWorkorder.getMdShift().getCode());//班次
				e.setTeamCode(schWorkorder.getMdTeam().getCode());//班组
			}
			if(null==schWorkorder.getIsAuto()||"".equals(schWorkorder.getIsAuto())){
				e.setIsAuto("Y");
			}else{
				e.setIsAuto(schWorkorder.getIsAuto());
			}
			//end
			orderBeans.add(e);
		}
		return orderBeans;
	}

	
	/**
	 * TSPM 接口
	 * @bean  根据当前工厂日历信息，
	 *       查询所有当班结束的工单信息 
	 * 
	 * */
	@Override
	public void AMSSendTroubleMessageTOTSPM(SchCalendarBean bean) {
		StringBuffer sb=new StringBuffer(1000);
		String xml="";
		Document doc=DocumentHelper.createDocument();
		Element root=doc.addElement("Msg");//根节点
		Element head=root.addElement("Head");
		head.addElement("MsgID").setText("DAMS"+DateUtil.getNowDateTime("yyyyMMddHHmmss"));
		head.addElement("InterfaceCode").setText("DAS_TSPM_G001");
		head.addElement("InterfaceDescription").setText("停机原因接口");
		head.addElement("Source").setText("MES");
		head.addElement("MsgMark").setText("");
		head.addElement("WsMethod").setText("");
		head.addElement("Date").setText(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
		head.addElement("Cryp").setText("");
		head.addElement("User").setText("DAMS");
		head.addElement("StateCode").setText("");
		head.addElement("StateDesription").setText("");
		head.addElement("DataDefine").setText("");
        Element datalist=root.addElement("DATALIST");
		try {
			//查询工单
			sb.append(" select b.id,a.erp_code,(select MES_EQPCODE from MD_EQUIPMENT where id=a.eqp) as eqpCode,(select EQUIPMENT_NAME from MD_EQUIPMENT where id=a.eqp) as eqpName, ");
			sb.append(" CONVERT(varchar, a.date, 23 ) as date,CONVERT(varchar, a.stim, 120 ) as stim,CONVERT(varchar, a.etim, 120 ) as etim,CONVERT(varchar, b.stim, 120 ) as stim2,CONVERT(varchar, b.etim, 120 ) as etim2,a.shift,(select name from MD_SHIFT where id=a.shift) as shiftName, ");
			sb.append(" a.team,(select name from md_team where id=a.team) as teamName, (select yie_id from MD_EQUIPMENT where id=a.eqp) as eqpTscl,a.qty,b.qty as qty2 "); 
			sb.append(" From SCH_WORKORDER a,SCH_STAT_OUTPUT b where  a.del=0 and a.id=b.oid  and a.sts=4 and a.date='"+bean.getDate()+"' and a.shift='"+bean.getMdShiftCode()+"' ");
			List<?> list= schWorkorderDao.queryBySql(sb.toString());
			if(list.size()>0 && list!=null){
				for(int i=0;i<list.size();i++){
					Object[] temp=(Object[]) list.get(i);
					Element data=datalist.addElement("DATA");
					data.addElement("ORDERCODE").setText(StringUtil.convertObjToString(temp[1]));
					data.addElement("EQPCODE").setText(StringUtil.convertObjToString(temp[2]));
					data.addElement("EQPNAME").setText(StringUtil.convertObjToString(temp[3]));
					data.addElement("ORDERDATE").setText(StringUtil.convertObjToString(temp[4]));
					data.addElement("PLANSTIME").setText(StringUtil.convertObjToString(temp[5]));
					data.addElement("PLANETIME").setText(StringUtil.convertObjToString(temp[6]));
					data.addElement("REALSTIME").setText(StringUtil.convertObjToString(temp[7]));
					data.addElement("REALETIME").setText(StringUtil.convertObjToString(temp[8]));
					data.addElement("SHIFTID").setText(StringUtil.convertObjToString(temp[9]));
					data.addElement("SHIFTNAME").setText(StringUtil.convertObjToString(temp[10]));
					data.addElement("TEAMID").setText(StringUtil.convertObjToString(temp[11]));
					data.addElement("TEAMNAME").setText(StringUtil.convertObjToString(temp[12]));
					data.addElement("UNDERSTANDSPEED").setText(StringUtil.convertObjToString(temp[13]));
					data.addElement("UPDERSTANDOUTPUT").setText(StringUtil.convertObjToString(temp[14]));
					data.addElement("REALOUTPUT").setText(StringUtil.convertObjToString(temp[15]));
					data.addElement("H_REMARK0").setText("");
					data.addElement("H_REMARK1").setText("");
					data.addElement("H_REMARK2").setText("");
					data.addElement("H_REMARK3").setText("");
					data.addElement("H_REMARK4").setText("");
					//通过工单ID，查询故障信息
					sb.setLength(0);
					sb.append("select name,time,times from SCH_STAT_FAULT where oid='"+temp[0].toString()+"'");
					List<?> bom_list=schWorkorderDao.queryBySql(sb.toString());
					if(bom_list.size()>0){
						for(int t=0;t<bom_list.size();t++){
							Object[] bom=(Object[]) bom_list.get(t);
							Element item=data.addElement("ITEM");
							item.addElement("ERROENAME").setText(StringUtil.convertObjToString(bom[0]));
							item.addElement("STOPNUM").setText(StringUtil.convertObjToString(bom[2]));
							item.addElement("STOPTIME").setText(StringUtil.convertObjToString(bom[1]));
							item.addElement("I_REMARK0").setText("");
							item.addElement("I_REMARK1").setText("");
							item.addElement("I_REMARK2").setText("");
							item.addElement("I_REMARK3").setText("");
							item.addElement("I_REMARK4").setText("");
						}
					}
				}
			}
			//将xml装换成string类型
			StringBuffer xmlStr=new StringBuffer(doc.asXML());
			//调用TSPM，发送信息
			xml=xmlStr.toString();
			TSPMSendMessageClient tspmMC=new TSPMSendMessageClient();
			tspmMC.SendMessageToTSPM(xml);
			/**日志 1-成功*/
		    ParsingXmlDataInterceptor.getInstance().addLog("TSPM设备故障信息",1,xml); 
		} catch (Exception e) {
			/**日志 0-失败*/
		    ParsingXmlDataInterceptor.getInstance().addLog("TSPM设备故障信息",0,xml); 
		}
	}

}
