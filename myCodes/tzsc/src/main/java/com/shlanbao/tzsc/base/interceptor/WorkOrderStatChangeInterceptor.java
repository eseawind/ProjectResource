package com.shlanbao.tzsc.base.interceptor;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.shlanbao.tzsc.base.dao.SchStatOutputDaoI;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.data.webservice.client.SendMessageClient;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.StringUtil;


public class WorkOrderStatChangeInterceptor {

	
	/**
	 *说明：向MES发送工单状态变化信息(当前只在手动切换工单状态的时候调用了该方法)
	 *@param schWorkorder 不为空表示是手动改变工单运行状态
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
		SDCStartEntry   开启工单   一天一个工单	2
        SDCSFinishEntry 完成工单	4
        SDCSShiftChange 换班
        SDCSShiftStart  换班后开始  一天三个班	
        SDCSEntryPause 工单暂停	3	
        SDCSEntryRestart 工单暂停后启动
        
       ## MES业务：
        1、工单应支持可以跨天运行，甚至一周内一个机台只跑一个工单；
		2、工单开始（工单的第一个班）运行时，反馈信号“1”；
		3、当班生产结束但工单未结束，不应反馈工单结束信号，应反馈工单换班信号“2”；
		4、当班生产开始但工单未结束，不应反馈工单开始信号，应反馈工单换班后开始运行信号“5”；
		5、工单运行期间，如有暂停信号，请反馈“4”，暂停后恢复运行请反馈“6”。便于MES系统的生产任务监视模块运行。
		6、工单结束时反馈信号“3”；
	   ## 
	 * @return
	 * shisihai  
	 * 20162016年2月22日下午12:30:52
	 */
	public boolean sendWorkorderChangeInfoToMES(SchWorkorder schWorkorder,String id,Long sts){
		boolean flag=true;
		OutputBean bean=null;
		SendMessageClient sendMessageClient=new SendMessageClient();
		if(schWorkorder!=null){
			bean = new OutputBean();
			//订单号
			//bean.setOrderNum(StringUtil.convertObjToString(schWorkorder.getErpCode()));
			bean.setBth( StringUtil.convertObjToString(schWorkorder.getBth()) );
			//工单号
			bean.setWorkorderCode(StringUtil.convertObjToString(schWorkorder.getErp_code()));
			//设备code
			bean.setEqpCode(StringUtil.convertObjToString(schWorkorder.getMdEquipment().getMesEqpCode()));
			//实际开始时间
			bean.setStim(DateUtil.formatDateToString(schWorkorder.getRealStim(), "yyyy-MM-dd HH:mm:ss"));
			//时间结束时间
			bean.setEtim(DateUtil.formatDateToString(schWorkorder.getRealEtim(), "yyyy-MM-dd HH:mm:ss"));
			//班次
			//bean.setShift(StringUtil.convertObjToString(MESConvertToJB.convertShift(schWorkorder.getMdShift().getCode(), 2)));
			bean.setShift(StringUtil.convertObjToString(schWorkorder.getMdShift().getCode()));
			//班组
			bean.setTeam(StringUtil.convertObjToString(schWorkorder.getMdTeam().getCode()));
		}else{
			String sql=this.getWorkOrderInfo(id);
			SchStatOutputDaoI dao=ApplicationContextUtil.getBean(SchStatOutputDaoI.class);
			List<?> ls=dao.queryBySql(sql);
			bean=packBean(ls);
		}
		String type="";
		// 1-早    2-中   3-晚       不支持工单暂停
		if( "1".equals(bean.getShift()) ){
			if(sts==2){
				sts=1l;//工单开始信息
				type="SDCSStartEntry";
			}else if(sts==4){
				sts=2l; //工单换班信号
				type="SDCSShiftStart";
			}
		}else if( "2".equals(bean.getShift()) ){
			if(sts==2){
				sts=5l;//工单换班后开始运行信号“5”
				type="SDCSEntryRestart";
			}else if(sts==4){
				sts=2l; //工单换班信号
				type="SDCSShiftStart";
			}
		}else if( "3".equals(bean.getShift()) ){
			if(sts==2){
				sts=5l;//工单换班后开始运行信号“5”
				type="SDCSEntryRestart";
			}else if(sts==4){
				sts=3l; //工单结束
				type="SDCSFinishEntry";
			}
		}
		String xmlStr=createXMLStr(bean,sts);
		//反馈mes
		try {
			if(!"".equals(type)){ //跳过工单状态： 暂停
				sendMessageClient.SendMessageToMes(type, xmlStr);
			}
		} catch (Exception e) {
			flag=false;
		}
		if(!flag){
			ParsingXmlDataInterceptor.getInstance().addLog("工单信号反馈:"+type,0,xmlStr); 
		}else{
			/**日志 1-成功*/
		    ParsingXmlDataInterceptor.getInstance().addLog("工单信号反馈"+type,1,xmlStr); 
		}
		return flag;
	}
	
	
	/**
	 *说明：生成工单信号，反馈给mes
	 * @param map
	 * @return
	 * shisihai  
	 * 20152015年2月19日下午2:20:21
	 */
	private String createXMLStr(OutputBean bean,Long sts) {
		try {
			Document doc=DocumentHelper.createDocument();
			Element root=doc.addElement("EntrySignal").addAttribute("SignalType", StringUtil.convertObjToString(sts));//根节点
			Element entrySignalInfo=root.addElement("EntrySignalInfo");
				//订单号
				//entrySignalInfo.addElement("OrderID").setText(bean.getOrderNum());
			    entrySignalInfo.addElement("OrderID").setText(bean.getBth()); //订单号，批次号
				//工单号
				entrySignalInfo.addElement("EntryID").setText(bean.getWorkorderCode());
				//设备编号
				entrySignalInfo.addElement("DeviceID").setText(bean.getEqpCode());
				//实际开始时间
				entrySignalInfo.addElement("ActualStartTime").setText(bean.getStim());
				//实际结束时间
				entrySignalInfo.addElement("ActualEndTime").setText(bean.getEtim());
				//班次
				entrySignalInfo.addElement("ShiftID").setText(bean.getShift());
				//班组
				entrySignalInfo.addElement("TeamID").setText(bean.getTeam());
				StringBuffer xmlStr=new StringBuffer(doc.asXML());
				return xmlStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 *说明：根据工单id查询工单相关信息
	 * @param id
	 * @return
	 * shisihai  
	 * 20162016年2月22日上午11:52:23
	 */
	private String getWorkOrderInfo(String id){
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT swo.erp_code,swo.CODE,(SELECT me.MES_EQPCODE from MD_EQUIPMENT me where me.id=swo.EQP),CONVERT(VARCHAR,swo.REAL_STIM,120) as stim,CONVERT(VARCHAR,swo.REAL_ETIM,120) as etim,swo.SHIFT,swo.TEAM");
		sb.append(" from SCH_WORKORDER swo where swo.ID='"+id+"'");
		return sb.toString();
	}
	
	/**
	 * 
	 *说明：封装outPutbean
	 * @param ls
	 * @return
	 * shisihai  
	 * 20162016年2月22日下午12:03:18
	 */
	private OutputBean packBean(List ls){
		Object[] o=null;
		OutputBean bean=null;
		if (ls!=null && ls.size()>0) {
			for (Object obj : ls) {
				o = (Object[]) obj;
				bean = new OutputBean();
				//订单号
				bean.setOrderNum(StringUtil.convertObjToString(o[0]));
				//工单号
				bean.setWorkorderCode(StringUtil.convertObjToString(o[1]));
				//设备code
				bean.setEqpCode(StringUtil.convertObjToString(o[2]));
				//实际开始时间
				bean.setStim(StringUtil.convertObjToString(o[3]));
				//时间结束时间
				bean.setEtim(StringUtil.convertObjToString(o[4]));
				//班次
				bean.setShift(MESConvertToJB.convertShift(StringUtil.convertObjToString(o[5]), 2));
				//班组
				bean.setTeam(StringUtil.convertObjToString(o[6]));
			} 
		}
		return bean;
	}
}
