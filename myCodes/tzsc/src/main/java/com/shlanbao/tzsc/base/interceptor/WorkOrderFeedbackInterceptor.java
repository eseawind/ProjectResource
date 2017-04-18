package com.shlanbao.tzsc.base.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;
import com.shlanbao.tzsc.base.dao.SchStatOutputDaoI;
import com.shlanbao.tzsc.base.dao.SysMessageQueueDaoI;
import com.shlanbao.tzsc.base.mapping.QmChangeShiftInfo;
import com.shlanbao.tzsc.base.mapping.SchStatOutput;
import com.shlanbao.tzsc.base.mapping.SysMessageQueue;
import com.shlanbao.tzsc.data.webservice.client.SendMessageClient;
import com.shlanbao.tzsc.pms.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 工单产耗信息反馈
 * @author shisihai
 *
 */
public class WorkOrderFeedbackInterceptor {

	private SchCalendarDaoI dao;
	
	/**
	 * 
	 *说明：工单产耗信息反馈
	 *  1)核对量：昆船虚领虚退量
	 *  2)实际量：数采过程数据
	 * @param idOrIds -SCH_STAT_OUTPUT表主键ID集
	 * @param feedbackUser -当前登录用户名称
	 * @return 
	 * shisihai  
	 * 20162016年2月22日上午9:06:15
	 */
	public Integer sendWorkOrderDetails(String idOrIds,String feedbackUser){
		if(dao==null){
			dao=ApplicationContextUtil.getBean(SchCalendarDaoI.class);
		}
		String xmlStr="";
		SendMessageClient sendMessageClient=new SendMessageClient();
		for(String id : StringUtil.splitToStringList(idOrIds, ",")){
			try {
				//获得工单基本信息
				Map<String,String> woMap=queryWorkOrderByOutId(id);
				//得到虚领虚退消耗BOM
				List<QmChangeShiftInfo> listInfo=getQmChangeShiftInfoBomSql(woMap.get("eqp"),woMap.get("code"));
				//MES虚领虚退没有下发到数采，数采的工单实绩不能反馈
				if(listInfo.size()<=0){ continue; }
				//工单基本信息及产出信息
				String sql=getFeedbackSql(woMap.get("id"));         //工单生产基本信息
				String sql_bom=getWorkOrderBomSql(woMap.get("id")); //物料实绩消耗BOM
				List<?> lsBean=dao.queryBySql(sql);
				List<?> bomBean=dao.queryBySql(sql_bom);
				//封装MAP
				Map<String,Object> map=packData(lsBean,listInfo,bomBean);
				//得到XML
				xmlStr=createXMLStr(map);
				//反馈MES
				sendMessageClient.SendMessageToMes("SDCSFinishedEntryInfo", xmlStr);
				/**日志 1-成功*/
			    ParsingXmlDataInterceptor.getInstance().addLog("工单实绩反馈（虚领虚退）",1,xmlStr);
			    
				xmlStr=qmChangeInfoXMLStr(map);
				sendMessageClient.SendMessageToMes("EntryShiftORFinishedInTime", xmlStr);//反馈MES
                //修改工单状态
				this.editOutputIsFeedback(id, feedbackUser);
			    ParsingXmlDataInterceptor.getInstance().addLog("工单实绩反馈（数采）",1,xmlStr);/**日志 1-成功*/
		    } catch (Exception e) {
				e.printStackTrace();
				/**日志 0-失败*/
			    ParsingXmlDataInterceptor.getInstance().addLog("工单实绩反馈",0,xmlStr);
			}
		}
		return 1;
	}

	/**
	 * 【功能说明】：查询虚领虚退
	 * EVICE_ID：MES设备号
     * ENTRY_ID：数采工单号
     * @date 2016年9月14日
     * @author wch
     * @param  1:实领  2：实退  3：虚领
	 * */
	private List<QmChangeShiftInfo> getQmChangeShiftInfoBomSql(String eqp,String code) {
		List<QmChangeShiftInfo> listInfo=new ArrayList<QmChangeShiftInfo>();
		String sql="select b.MATERIAL_CLASS,b.MATERIAL_ID,b.LOT_ID,b.UOM from QM_CHANGSHIFT a, QM_CHANGSHIFT_INFO b where a.id=b.QM_CSID and a.DEVICE_ID='"+eqp+"' and a.ENTRY_ID='"+code+"' and type in (1,2,3)  group by b.MATERIAL_CLASS,b.MATERIAL_ID,b.LOT_ID,b.UOM ";
		List<?> list=dao.queryBySql(sql);
		if(list.size()>0){
			QmChangeShiftInfo qcsf=null;
			for(int i=0;i<list.size();i++){
				Object[] temp=(Object[]) list.get(i);
				qcsf=new QmChangeShiftInfo();
				qcsf.setMaterialClass(temp[0].toString());
				qcsf.setMaterialId(temp[1].toString());
				qcsf.setLotId(temp[2].toString());
				qcsf.setUom(temp[3].toString());
				//产量
				qcsf.setQty( resource(temp[1].toString()) );
				//添加
				listInfo.add(qcsf);
			}
		}
		return listInfo;
	}
	
	/**返回消耗量*/
	public Float resource(String materialId){
		StringBuffer sbf=new StringBuffer(1000);
		sbf.append(" select "); 
		sbf.append(" isnull( (select qty from QM_CHANGSHIFT_INFO where type='1' and material_id='"+materialId+"') ,0)+ "); 
		sbf.append(" isnull( (select qty from QM_CHANGSHIFT_INFO where type='3' and material_id='"+materialId+"') ,0)- "); 
		sbf.append(" isnull( (select qty from QM_CHANGSHIFT_INFO where type='2' and material_id='"+materialId+"') ,0)  "); 
		sbf.append(" from QM_CHANGSHIFT_INFO where material_id='"+materialId+"' GROUP BY material_id "); 
		List<?> list=dao.queryBySql(sbf.toString());
		return Float.parseFloat(list.get(0).toString());
	}

	private Map<String, String> queryWorkOrderByOutId(String out_id) {
		Map<String,String> map=new HashMap<String, String>();
		String sql="select id,code,shift,team,(select MES_EQPCODE from MD_EQUIPMENT where id=eqp),CONVERT(VARCHAR(10),date,23) as dat from SCH_WORKORDER where del=0 and id=(select oid from SCH_STAT_OUTPUT where id='"+out_id+"');";
		List<?> list= dao.queryBySql(sql);
		Object[] temp=(Object[]) list.get(0);
		if(temp[0]!=null){
			map.put("id",temp[0].toString());
		}
		if(temp[1]!=null){
			map.put("code", temp[1].toString());
		}
		if(temp[2]!=null){
			map.put("shift", temp[2].toString());
		}
		if(temp[3]!=null){
			map.put("team", temp[3].toString());
		}
		if(temp[4]!=null){
			map.put("eqp", temp[4].toString());
		}
		if(temp[5]!=null){
			map.put("date", temp[5].toString());
		}
		return map;
	}

	/**
	 * 功能说明：虚领虚退反馈MES
	 * @param out_id: sch_stat_input表ID
	 * @param sendMessageClient
	 * */
	public String qmChangeShiftSend(String out_id,SendMessageClient sendMessageClient){
		StringBuffer sbf=new StringBuffer(1000);
		//sbf.append("  ");
		sbf.append(" select c.id,c.code,b.* from QM_CHANGSHIFT_INFO b  ");
		sbf.append(" left join QM_CHANGSHIFT a  ");
		sbf.append(" on a.id=b.qm_csid   ");
		sbf.append(" left join SCH_WORKORDER c ");
		sbf.append(" on a.entry_id=c.code  ");
		sbf.append(" where c.del=0 and  a.shift_id=c.shift ");
		sbf.append(" and a.team_id=c.team ");
		sbf.append(" and c.id=(select oid from SCH_STAT_OUTPUT where id='"+out_id+"') ");
		
		
		return "";
	}
	
	
	
	/**
	 *说明：修改生产实绩反馈状态
	 * @param id
	 * @param feedbackUser
	 * shisihai  
	 * 20162016年2月22日上午9:03:11
	 */
	private void editOutputIsFeedback(String id,String feedbackUser){
		SchStatOutputDaoI schStatOutputDao=ApplicationContextUtil.getBean(SchStatOutputDaoI.class);
		SchStatOutput output = schStatOutputDao.findById(SchStatOutput.class, id);
		output.setIsFeedback(1L);
		output.setFeedbackTime(new Date());
		output.setFeedbackUser(feedbackUser);
		
	}
	
	
	
	/**
	 *说明：接口日志
	 * @param des
	 * @param flag 1成功  0失败
	 * shisihai  
	 * 20152015年12月28日下午4:56:58
	 */
	private void addLog(String des,Integer flag) {
		SysMessageQueueDaoI sysMessageQueueDaoI=ApplicationContextUtil.getBean(SysMessageQueueDaoI.class);
		SysMessageQueue log=new SysMessageQueue();
		log.setSysReceive(2L);//接受方
		log.setSysSend(1L);//发送方
		log.setDel(0L);
		log.setMsgType(1L);
		log.setDes(des);//描述
		log.setFlag(flag);
		log.setDate_(new Date());
		try {
			sysMessageQueueDaoI.saveAndReturn(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 说明：工单实绩反馈- 辅料实绩消耗反馈
	 * @param map
	 * @return
	 * shisihai  
	 * 20152015年12月28日下午2:20:21
	 */
	@SuppressWarnings("unchecked")
	private String createXMLStr(Map<String, Object> map) {
		OutputBean out=(OutputBean) map.get("out");
		List<InputBean> inputs2=(List<InputBean>) map.get("input");
		try {
			Document doc=DocumentHelper.createDocument();
			Element root=doc.addElement("EntryShiftORFinished");//根节点
			Element entryBasicInfo=root.addElement("EntryBasicInfo");
			if (out!=null) {
				//订单号
				entryBasicInfo.addElement("OrderID").setText( out.getBth());
				//工单号
				entryBasicInfo.addElement("EntryID").setText(out.getOrderNum());
				//生产日期
				entryBasicInfo.addElement("ProduceDate").setText(out.getDate());
				//牌号编码
				entryBasicInfo.addElement("OutMaterialID").setText(out.getMatId());
				//批次作业号
				entryBasicInfo.addElement("LotID").setText(out.getBth());
				//产量信息
				Element outMaterialQuantity = entryBasicInfo.addElement("OutMaterialQuantity");
				//实际产量
				outMaterialQuantity.addElement("RealOutMaterialQuantity")
						.setText(StringUtil.convertObjToString(out.getQty()));
				//修改产量
				outMaterialQuantity.addElement("ChangeMaterialQuantity")
						.setText(StringUtil.convertObjToString(out.getChangeQty()));
				//修改产量标识
				outMaterialQuantity.addElement("ChangeIdentifer").setText(out.getChangeFlag());
				//单位
				entryBasicInfo.addElement("UOM").setText(out.getUnitName());
				//设备编码
				entryBasicInfo.addElement("DeviceID").setText(out.getEqpCode());
				//批次作业顺序号
				entryBasicInfo.addElement("SequenceNumber").setText(out.getSequenceNumber());
				//计划开始时间
				entryBasicInfo.addElement("PlanningStartTime").setText(out.getPstim());
				//计划结束时间
				entryBasicInfo.addElement("PlanningFinishedTime").setText(out.getPetim());
				//实际开始时间
				entryBasicInfo.addElement("ActualStartTime").setText(out.getStim());
				//实际结束时间
				entryBasicInfo.addElement("ActualFinishedTime").setText(out.getEtim());
				//外联设备相关信息
				Element destination = entryBasicInfo.addElement("Destination");
				destination.addElement("DestinationID");
				destination.addElement("DestinationType");
				Element sourceDevice = entryBasicInfo.addElement("SourceDevice");
				for (int i = 0; i < 1; i++) {
					Element item = sourceDevice.addElement("Item");
					item.addElement("SouceDeviceID");
					item.addElement("SouceDeviceType");
				}
				//工单类型编号
				entryBasicInfo.addElement("EntryType").setText(out.getOrderType());
				//班次
				entryBasicInfo.addElement("ShiftID").setText(out.getShift());
				//班组
				entryBasicInfo.addElement("TeamID").setText(out.getTeam());
				//结果类型
				entryBasicInfo.addElement("Result").setText(out.getResult());
				//删除标志
				entryBasicInfo.addElement("DeleteFlag").setText(StringUtil.convertObjToString(out.getDel()));
			}
			if (inputs2!=null) {
				//辅料消耗信息
				Element materialInfo = root.addElement("MaterialInfo");
				for (InputBean input : inputs2) {
					Element item = materialInfo.addElement("Item");
					//物料类
					item.addElement("MaterialClass").setText(input.getMatType());
					//辅料编码
					item.addElement("MaterialID").setText(input.getMatCode());
					//物料批次号
					item.addElement("LotID").setText(input.getBth());
					//消耗
					item.addElement("Quantity").setText(StringUtil.convertObjToString(input.getQty()));
					//单位
					item.addElement("UOM").setText(input.getUnit());
				} 
		    }
			StringBuffer xmlStr=new StringBuffer(doc.asXML());
			return xmlStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return	"";
	}
	
	/**
	 *说明：工单实绩反馈- 辅料实绩消耗反馈
	 * @param map
	 * @return
	 * @author Administrator
	 * 20152015年12月28日下午2:20:21
	 */
	@SuppressWarnings("unchecked")
	private String qmChangeInfoXMLStr(Map<String, Object> map) {
		OutputBean out=(OutputBean) map.get("out");
		List<InputBean> inputs=(List<InputBean>) map.get("input2");//工单实际过程BOM
		try {
			Document doc=DocumentHelper.createDocument();
			Element root=doc.addElement("EntryShiftORFinished");//根节点
			Element entryBasicInfo=root.addElement("EntryBasicInfo");
			if (out!=null) {
				//订单号
				entryBasicInfo.addElement("OrderID").setText( out.getBth());
				//工单号
				entryBasicInfo.addElement("EntryID").setText( out.getOrderNum());
				//生产日期
				entryBasicInfo.addElement("ProduceDate").setText(out.getDate());
				//牌号编码
				entryBasicInfo.addElement("OutMaterialID").setText(out.getMatId());
				//批次作业号
				entryBasicInfo.addElement("LotID").setText(out.getBth());
				//产量
				entryBasicInfo.addElement("OutMaterialQuantity").setText( StringUtil.convertObjToString(out.getQty()) );
				//单位
				entryBasicInfo.addElement("UOM").setText(out.getUnitName());
				//设备编码
				entryBasicInfo.addElement("DeviceID").setText(out.getEqpCode());
				//批次作业顺序号
				entryBasicInfo.addElement("SequenceNumber").setText(out.getSequenceNumber());
				//计划开始时间
				entryBasicInfo.addElement("PlanningStartTime").setText(out.getPstim());
				//计划结束时间
				entryBasicInfo.addElement("PlanningFinishedTime").setText(out.getPetim());
				//实际开始时间
				entryBasicInfo.addElement("ActualStartTime").setText(out.getStim());
				//实际结束时间
				entryBasicInfo.addElement("ActualFinishedTime").setText(out.getEtim());
				//外联设备相关信息
				Element destination = entryBasicInfo.addElement("Destination");
				destination.addElement("DestinationID");
				destination.addElement("DestinationType");
				Element sourceDevice = entryBasicInfo.addElement("SourceDevice");
				for (int i = 0; i < 1; i++) {
					Element item = sourceDevice.addElement("Item");
					item.addElement("SouceDeviceID");
					item.addElement("SouceDeviceType");
				}
				//工单类型编号
				entryBasicInfo.addElement("EntryType").setText(out.getOrderType());
				//班次
				entryBasicInfo.addElement("ShiftID").setText(out.getShift());
				//班组
				entryBasicInfo.addElement("TeamID").setText(out.getTeam());
				//结果类型
				entryBasicInfo.addElement("Result").setText(out.getResult());
				//删除标志
				entryBasicInfo.addElement("DeleteFlag").setText(StringUtil.convertObjToString(out.getDel()));
			}
			root.addElement("MaterialInfo");
			if (inputs!=null) {
				//辅料消耗信息
				Element materialInfo = root.addElement("ZBMaterial");
				for (InputBean input : inputs) {
					//Element item = materialInfo.addElement("Item");
					//物料类
					materialInfo.addElement("MaterialClass").setText(input.getMatType());
					//辅料编码
					materialInfo.addElement("MaterialID").setText(input.getMatCode());
					//物料批次号
					materialInfo.addElement("LotID").setText(input.getBth());
					materialInfo.addElement("ZBSendQuantity").setText("");//退回立库量
					materialInfo.addElement("ZBGetQuantity").setText(""); //领取立库量
					//消耗(数采消耗量)
					materialInfo.addElement("ZBUseQuantity").setText(StringUtil.convertObjToString(input.getQty()));
					//单位
					materialInfo.addElement("UOM").setText(input.getUnit());
					
				} 
			}
			Element AllWasterMaterial = root.addElement("AllWasterMaterial");
			Element item = AllWasterMaterial.addElement("Item");
			item.addElement("WasterID").setText("");
			item.addElement("Quantity").setText("");
			item.addElement("UOM").setText("");
			StringBuffer xmlStr=new StringBuffer(doc.asXML());
			return xmlStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return	"";
	}
	
	/**
	 * 
	 *说明：查询工单产耗信息
	 * @param id
	 * @return
	 * shisihai  
	 * 20162016年2月22日上午10:52:04
	 */
	private String getFeedbackSql(String id){
		StringBuffer sb=new StringBuffer(1000);
		sb.append(" select "); 
		sb.append(" a.code,a.erp_code,convert(varchar(20),a.date,23) as date, "); 
		sb.append(" (select code from md_mat where id=a.mat ) as OutMaterialID, "); 
		sb.append(" a.bth, "); 
		sb.append(" b.qty, "); 
		sb.append(" ( select mes_code from MD_UNIT where id=b.unit) as uomName, "); 
		sb.append(" ( select mes_eqpcode from MD_EQUIPMENT where id=a.eqp ) as devicelID, "); 
		sb.append(" convert(varchar(20),a.stim,120) as stim,convert(varchar(20),a.etim,120) as etim, "); 
		sb.append(" convert(varchar(20),a.real_stim,120) as real_stim,convert(varchar(20),a.real_etim,120) as real_etim, "); 
		sb.append(" a.type,a.prod_type, "); 
		sb.append(" a.shift, "); 
		sb.append(" a.team, "); 
		sb.append(" a.sts "); 
		sb.append(" from SCH_WORKORDER a,SCH_STAT_OUTPUT b where a.del=0 and a.id=b.oid and a.id='"+id+"' "); 
		return sb.toString();
	}
	
	/** 物料bom信息  
	 *  其他点位默认为0，数采辅料消耗为真实消耗
	 * */
	private String getWorkOrderBomSql(String id){
		StringBuffer sb=new StringBuffer(1000);
		sb.append("  select (select code from MD_MAT where id=mat) as matName,'0' as qty,(select mes_code from MD_UNIT where id=unit) as unitName,bom_lot_id,material_class from SCH_WORKORDER_BOM where del=1 and oid='"+id+"' ");	
		sb.append("  union all ");
		sb.append("  select ");
		sb.append("    (select code from MD_MAT where id=a.mat) as matName,a.qty,(select mes_code from MD_UNIT where id=a.unit) as unitName,b.bom_lot_id,b.material_class ");
		sb.append("  from ");
		sb.append("    (select mat,qty,unit,( select oid from SCH_STAT_OUTPUT where id=out_id) as toid from SCH_STAT_INPUT) a ");
		sb.append("    left join SCH_CON_STD b on a.toid=b.oid where a.mat=b.MAT and a.toid='"+id+"' ");
		//sb.append("  select (select code from MD_MAT where id=mat) as matName,qty,(select mes_code from MD_UNIT where id=unit) as unitName,bom_lot_id from SCH_STAT_INPUT where out_id=(select id from SCH_STAT_OUTPUT where oid='"+id+"' )" );
		return sb.toString();
	}
	

	
	
	/**
	 * 
	 *说明：封装工单详细反馈数据
	 * @param ls
	 * @param FeedbackType 反馈类型 0-结束工单  1-换班
	 * @return
	 * shisihai  
	 * 20152015年12月28日下午1:51:22
	 */
	private Map<String, Object> packData(List<?> ls,List<QmChangeShiftInfo> listInfo,List<?> bomBean) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<InputBean> inputs=new ArrayList<InputBean>();
		InputBean input=null;
		InputBean input2=null;
		List<InputBean> inputs2=new ArrayList<InputBean>();
		OutputBean out=null;
		if(ls!=null && ls.size()>0){
			Object[] obj=null;
			for (int i = 0; i < ls.size(); i++) {
				obj=(Object[]) ls.get(0);
				/** 工单基本信息 */
				out=new OutputBean();
				//ERP订单号
				out.setOrderNum(StringUtil.convertObjToString(obj[1]));
				//工单号
				out.setWorkorderCode(StringUtil.convertObjToString(obj[0]));
				//工单日期
				out.setDate(StringUtil.convertObjToString(obj[2]));
				//牌号编码
				out.setMatId(StringUtil.convertObjToString(obj[3]));
				//作业批次号
				out.setBth(StringUtil.convertObjToString(obj[4]));
				//实际产量
				out.setQty(this.convDouble(obj[5]));
				//单位
				out.setUnitName(StringUtil.convertObjToString(obj[6]));
				//设备code
				out.setEqpCode(StringUtil.convertObjToString(obj[7]));
				//计划开始
				out.setPstim(StringUtil.convertObjToString(obj[8]));
				//计划结束
				out.setPetim(StringUtil.convertObjToString(obj[9]));
				//实际开始
				out.setStim(StringUtil.convertObjToString(obj[10]));
				//实际结束
				out.setEtim(StringUtil.convertObjToString(obj[11]));
				//批次作业顺序号 根据班次区分顺序， 123-早中晚
				out.setSequenceNumber(StringUtil.convertObjToString(obj[14])); 
				//修改产量
				out.setChangeQty(0d);
				//修改标志
				out.setChangeFlag("");
				//工单类型编号
				out.setOrderType(StringUtil.convertObjToString(obj[13])+StringUtil.convertObjToString(obj[12]));
				//班次
				out.setShift(StringUtil.convertObjToString(obj[14]));
				//班组
				out.setTeam(StringUtil.convertObjToString(obj[15]));
				//结果
				out.setResult("0");
				//删除状态
				out.setDel(0L);
				//工单状态
				out.setOrderStat(StringUtil.convertObjToString(obj[16]));
				map.put("out", out);
			}
		}
		
		//虚领虚退MES辅料
		for(QmChangeShiftInfo qm:listInfo){
			input=new InputBean();
			/** 工单BOM基本信息 */
			input.setMatType(qm.getMaterialClass());
			input.setMatCode(qm.getMaterialId());
			input.setBth(qm.getLotId());
			input.setQty(Double.parseDouble(qm.getQty().toString()));
			input.setUnit(qm.getUom());
			inputs.add(input);
		}
		map.put("input", inputs);
		
		//数采真实辅料消耗
		if(bomBean!=null && bomBean.size()>0){
			Object[] obj=null;
			for (int i = 0; i < bomBean.size(); i++) {
				obj=(Object[]) bomBean.get(i);
				input2=new InputBean();
				//辅料类型
				input2.setMatType(StringUtil.convertObjToString(obj[4]));
				//辅料code
				input2.setMatCode(StringUtil.convertObjToString(obj[0]));
				//物料批次
				input2.setBth(StringUtil.convertObjToString(obj[3]));
				//消耗量
				input2.setQty(convDouble(obj[1]));
				//单位
				input2.setUnit(StringUtil.convertObjToString(obj[2]));
				inputs2.add(input2);
			}
			map.put("input2", inputs2);
		}
		return map;
	}
	
	/**
	 * Obje 转Double
	 * TODO
	 * @param o
	 * @return
	 * TRAVLER
	 * 2015年12月2日下午4:30:32
	 */
	private Double convDouble(Object o){
		Double d=0.0;
		if(o!=null&&StringUtil.isDouble(o.toString())){
			d=Double.valueOf(o.toString());
		}
		return d;
	}
	
}
