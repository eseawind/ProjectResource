package com.shlanbao.tzsc.base.interceptor;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdMatGrp;
import com.shlanbao.tzsc.base.mapping.MdMatType;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.mapping.MdWorkshop;
import com.shlanbao.tzsc.base.mapping.QmChangeShift;
import com.shlanbao.tzsc.base.mapping.QmChangeShiftInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfoParams;
import com.shlanbao.tzsc.base.mapping.SchCalendar;
import com.shlanbao.tzsc.base.mapping.SchConStd;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.mapping.SchWorkorderBom;
import com.shlanbao.tzsc.base.mapping.SysMessageQueue;
import com.shlanbao.tzsc.base.model.ParsingXmlDataBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * [功能说明]:MES接口  工单撤销，工单下发，工单信号  XML解析类
 * @author wanchanghuang
 * @createTime 2015年12月29日11:58:14
 * @param [最后修改人]
 * @param [最后修改时间]
 * */
public class ParsingXmlDataInterceptor {
	@Autowired
	private  WorkOrderServiceI workOrderService;
	
	private static ParsingXmlDataInterceptor instance = null;
	
	
	//构造方法初始化
	public ParsingXmlDataInterceptor(){
		if(null==workOrderService){
			workOrderService = ApplicationContextUtil.getBean(WorkOrderServiceI.class);
		}
	}
	public static ParsingXmlDataInterceptor getInstance(){
		if (instance == null){
			instance = new ParsingXmlDataInterceptor();
		}
		return instance;
	}
	
	
	/**
	 * 【功能说明】：工单撤销 - 删除该工单
	 * @author wch
	 * @param ftype 
	 * @param fstr 
	 * @createTime 2015年12月18日09:07:30
	 * 
	 * */
	public void updateTntryCancel(String ftype, String fstr, Document doc){
		try {
			SchWorkorder pxd=new SchWorkorder();
			Element node=doc.getRootElement();
			Element fnode= node.element("EntryCancleInfo");
			pxd.setCode(fnode.elementTextTrim("EntryID"));//工单号
			String sts=fnode.elementTextTrim("CancleTag");
			pxd.setSts(Long.parseLong(sts)); //工单撤销固定值为：1
			workOrderService.updateTntryCancel(pxd);//更新工单状态
			/**日志 1-成功*/
		    ParsingXmlDataInterceptor.getInstance().addLog("工单撤销",1,fstr); 
		} catch (Exception e) {
			/**日志 0-失败*/
		    ParsingXmlDataInterceptor.getInstance().addLog("工单撤销",0,fstr); 
			System.out.println("REEOR:工单撤销接口解析失败..."+ftype+":"+fstr);
			e.printStackTrace();
		}
	}


	/**
	 * 【功能说明】：工单信号
	 * @author wch
	 * @createTime 2015年12月18日09:07:30
	 * 
	 * */
	public boolean deleteAndCancelWS(Element node) {
		ParsingXmlDataBean pxd=new ParsingXmlDataBean();
		//获得数据父节点
		Element fnode= node.element("segmentItem");
		//获得节点数据
		pxd.setEqu_id(fnode.elementTextTrim("equ_id"));
		pxd.setWo_id( fnode.elementTextTrim("wo_id"));
		pxd.setMod_id(fnode.elementTextTrim("mod_id"));
		pxd.setWo_state(fnode.elementTextTrim("wo_state"));
		pxd.setStartTime(fnode.elementTextTrim("startTime"));
		pxd.setEndTime(fnode.elementTextTrim("endTime"));
		pxd.setFeedback_type(fnode.elementTextTrim("feedback_type"));
		//更新工单状态
		boolean flag=workOrderService.deleteAndCancelWS(pxd);
		return flag;
	}


	/**
	 * 【功能说明】：工单下发
	 * @author wch
	 * @param ftype 
	 * @param fstr 
	 * @createTime 2015年12月18日09:07:30
	 * 
	 *    业务描述： MES下发工单不分早班，中班，晚班   所以插入的时候， code表示数采工单code，erp_code表示MES下发code
	 *             且分别插入早班工单，中班工单，晚班工单
	 * 
	 * */
	public void addSendCancel(String ftype, String fstr, Document doc) {
		try {
			SchWorkorder swd=new SchWorkorder();
			Element node=doc.getRootElement();
			MdShift mf=null;
			Element fnode=node.element("EntryBasicInfo");
			//swd.setCode(fnode.elementTextTrim("EntryID")); //工单号
			swd.setErp_code(fnode.elementTextTrim("EntryID")); //MES工单号
			swd.setBth(fnode.elementTextTrim("LotID"));//批次号
			swd.setCode(fnode.elementTextTrim("OrderID")); //订单号
			//基础数据
			swd.setIsNew(1l); //是否新数据，默认1
			/**
			 * 编码A：枚举及释义
				1：正常工单
				2：中试工单
				3：试车工单
				4：在制品工单
				编码B：枚举及释义
				1：卷接机型
				2：包装机型
				3：装箱机型
				4：成型机型
				5：喂丝机型
				6：发射机型
				举例：编码“31”，表示卷接机试车工单
				
				//卷包数采
				2	包装机组
				1	卷接机组
				3	装封箱机
				4	成型机组
				5	发射机组
				6	装盘机
				7	卸盘机
			 * 
			 * */
			String typef=fnode.elementTextTrim("EntryType");
			swd.setType( Long.parseLong(typef.substring(1, 2)) ); 
			swd.setProdType(Long.parseLong(typef.substring(0, 1))); //1：中试生产2：正式生产
			if("6".equals(typef.substring(1, 2))){
				swd.setType(5l);
			}
			//班组MES下发默认为0，解析无意义
			/*MdTeam mm=new MdTeam();
			String teamId=fnode.elementTextTrim("TeamID"); 
			mm.setId(teamId);
			swd.setMdTeam(mm);//班组
			*/
			MdEquipment meqt=new MdEquipment();
			meqt.setId(MESConvertToJB.convertMESEqpCode2JBId(fnode.elementTextTrim("DeviceID")));//设备ID
			swd.setMdEquipment(meqt);
			MdMat mt=new MdMat();
			//mt.setId(fnode.elementTextTrim("OutMaterialID"));//物料ID   
			mt.setId(MESConvertToJB.MES2JBMatConvert(fnode.elementTextTrim("OutMaterialID")));//物料号  
			swd.setMdMat(mt); 
			String t=fnode.elementTextTrim("OutMaterialQuantity");
			swd.setQty(Double.parseDouble(fnode.elementTextTrim("OutMaterialQuantity")));
			MdUnit mu= new MdUnit();
			mu.setId(MESConvertToJB.MES2JBMatUnitConvert(fnode.elementTextTrim("UOM")));//单位ID
			swd.setMdUnit(mu); 
			swd.setBomVersion(fnode.elementTextTrim("BomVersion")); //版本号
			swd.setSts(1l);//工单状态
			swd.setIsCheck(1l);//是否审核  0-未审核 1-已审核
			swd.setRunSeq(Long.parseLong(fnode.elementTextTrim("SequenceNumber")));//运行顺序
			swd.setRecvTime(new Date());//工单接收时间
			swd.setEnable(1l);//1-启用  0-关闭
	        swd.setDel(0L); //0-默认   1-删除
	        swd.setIsAuto("Y");//工单自动启动
	        
	        //计划开始时间
	        String dt=fnode.elementTextTrim("PlanningStartTime");
	        dt=dt.substring(0, 11);
	        swd.setDate(DateUtil.strToDate(dt+"00:00:00", "yyyy-MM-dd HH:mm:ss"));//日期
	        swd.setQty(MathUtil.roundHalfUp( (swd.getQty()/3),2));
	        MdTeam mm=null;
	        //分别插入1-早，2-中，3-晚工单
	        for(int i=1;i<4;i++){
	        	if(i==1){
	        		//计划开始结束时间
	        		swd.setStim(DateUtil.strToDate(dt+"07:00:00", "yyyy-MM-dd HH:mm:ss"));
					swd.setEtim(DateUtil.strToDate(dt+"15:00:00", "yyyy-MM-dd HH:mm:ss"));
	        	}else if(i==2){
	        		//计划开始结束时间
					swd.setStim(DateUtil.strToDate(dt+"15:00:00", "yyyy-MM-dd HH:mm:ss"));
					swd.setEtim(DateUtil.strToDate(dt+"23:00:00", "yyyy-MM-dd HH:mm:ss"));
	        	}else if(i==3){
	        		//计划开始结束时间
					swd.setStim(DateUtil.strToDate(dt+"23:00:00", "yyyy-MM-dd HH:mm:ss"));
					swd.setEtim(DateUtil.strToDate( DateUtil.dateFormatCal(dt,1)+" 07:00:00", "yyyy-MM-dd HH:mm:ss"));
	        	}
	        	//班次
			    mf= new MdShift();
				mf.setId(i+"");
				swd.setMdShift(mf);
				/**由于MES下发班组默认为0，需要通过班次，及计划日期查询得到班次；*/
				mm=new MdTeam();
				mm.setId(MESConvertToJB.convertMESTeamCode2JBId(dt,i+""));
				swd.setMdTeam(mm);//班组
				//数采工单号
				swd.setCode(fnode.elementTextTrim("EntryID")+i);
	        	//保存并返回对象
		        swd=workOrderService.addSchWorkOrder(swd);
		        if(swd==null){
		        	continue;//插入工单对象，跳过返回值为null
		        }
		        //工单辅料
				SchWorkorderBom swb=null; 
				//
				SchConStd scs=null;
				Element flnode= node.element("MaterialInfo");
				List<Element> ellist=flnode.elements("Item");
				if(ellist.size()>0 && ellist!=null){
					MdMat mmt=null;
					MdMat pordmmt=null;//牌号
					MdUnit mut=null;
					String unitId="";
					String matId="";
					//String idf="";
					for(Element fl:ellist){
						swb = new SchWorkorderBom();
						scs = new SchConStd();
						mmt=new MdMat();
						pordmmt=new MdMat();//牌号
						matId=fl.elementTextTrim("MaterialID");
						/** 特殊说明：工单下发，有N个辅料过来，但卷包数采只需要个别采集点的辅料，
						 * 如：卷烟机：盘纸，水松纸，滤棒  其他辅料在sch_workOrder_bom表  del=1 过滤
						 *  1）获取md_mat_type表的code,返回map  
						 *  2） id：md_mat表id  tid：md_mat_type表code
						 *  3） code<16 的都是需要过来的辅料
						 *  _________________________
						 *  map=null表示未匹配到物料，直接过滤，且mat默认-1
						 *  map！=null表示可以匹配辅料，但是需要判断tid<16
						 * */
						Map<String,String> map=MESConvertToJB.MES2JBMatConvert2(matId);
						if(map!=null){
							mmt.setId(map.get("id"));
							if(Integer.parseInt(map.get("tid"))>9 && Integer.parseInt(map.get("tid"))!=304){
								swb.setDel(1);
							}else{
								swb.setDel(0);
							}
						}else{
							mmt.setId("-1"+matId);//物料号    
							swb.setDel(1);
						}
						/*idf=MESConvertToJB.MES2JBMatConvert(matId);
						if("-1".equals(idf) ){
							mmt.setId("-1");//物料号    
							swb.setDel(1);
						}else{
							mmt.setId(idf);//物料号    
						}*/
						swb.setMdMat(mmt);
						mut=new MdUnit();
						unitId=fl.elementTextTrim("UOM"); 
						mut.setId(MESConvertToJB.MES2JBMatUnitConvert(unitId));//单位ID
						swb.setMdUnit(mut);
						swb.setQty(Double.parseDouble(fl.elementTextTrim("Quantity")));//物料量
						swb.setSchWorkorder(swd);//工单ID
						swb.setBom_lot_id(fl.elementTextTrim("LotID"));//批次号
						swb.setMaterialClass( fl.elementTextTrim("MaterialClass") ); //物料类
						swb.setMaterialName(fl.elementTextTrim("MaterialName") );//物料名称
						//保存物料（ sch_workorder_bom）表
						swb=workOrderService.saveSchWorkOrderBom(swb);
						//保存标准单耗（sch_con_std）表
						if(swb.getDel()!=1){ //1-表示不是卷包机组采集的辅料，跳过
							pordmmt.setId(swd.getMdMat().getId());
							scs.setMdMatByProd(pordmmt);
							mmt.setId(swb.getMdMat().getId());
							scs.setMdMatByMat(mmt);
							scs.setVal(swb.getQty());
							scs.setUval(swb.getQty());
							scs.setLval(swb.getQty());
							scs.setEuval(swb.getQty());
							scs.setElval(swb.getQty());
							scs.setDes(swd.getMdUnit().getId());
							scs.setDel(0l);
							scs.setBomLotId(swb.getBom_lot_id()); //获取批次号
							scs.setOid(swd.getId());
							scs.setMaterialClass(swb.getMaterialClass() );//物料类
							workOrderService.saveSchConStd(scs);
						}
					}
				}
	        }
			/**日志 1-成功*/
		    ParsingXmlDataInterceptor.getInstance().addLog("工单下发",1,fstr); 
		} catch (Exception e) {
			/**日志 0-失败*/
		    ParsingXmlDataInterceptor.getInstance().addLog("工单下发",0,fstr); 
			System.out.println("REEOR:工单下发接口解析失败..."+ftype+":"+fstr);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 【功能说明】：自检
	 * @author wch
	 * @throws Exception 
	 * @createTime 2016年1月12日10:49:07
	 * 
	 * MESConvertToJB.transEqpCode("")//传MES设备ID，返回设备ID
	 * 
	 * */
	public boolean handleQCXML(Document doc) throws Exception {
		QualityCheckInfo qci=new QualityCheckInfo();
		Element node=doc.getRootElement();
		Element fnode= node.element("segmentItem");
		qci.setSc(fnode.elementTextTrim("sc"));
		qci.setSt(fnode.elementTextTrim("st"));
		qci.setCheckType(fnode.elementTextTrim("checkType"));
		
		String scScore=fnode.elementTextTrim("sc_score");
		if(!"".equals(scScore)){
			qci.setScScore(Double.parseDouble(scScore));
		}
		qci.setStVersion(fnode.elementTextTrim("st_version"));
		qci.setMatDes(fnode.elementTextTrim("description"));
		
		String date=fnode.elementTextTrim("value");
		if(!"".equals(date)){
			qci.setCreateDate(DateUtil.formatStringToDate(date, "yyyy-MM-dd HH:mm:ss"));
		}
		Element sampNode=fnode.element("sampleInfo");
		List<Element> ltInfo=sampNode.elements("infofield");
		String othername="";
		String othervalue="";
		for(Element lt:ltInfo){
			othername=lt.elementTextTrim("ii");
			if("ii_brand".equals(othername)){
				qci.setMatName(lt.elementTextTrim("value"));
			}else if ("ii_lot".equals(othername)) {
				qci.setIiLot(lt.elementTextTrim("value"));
			} else if ("ii_team".equals(othername)) {
				//班组
				othervalue=lt.elementTextTrim("value");//说明：由于班次，班次ID= 乙班,2形式，所以才截取字符串
				qci.setTeamId(othervalue.substring(0, othervalue.lastIndexOf(",")));
				qci.setTeamName(othervalue.substring(othervalue.lastIndexOf(",")+1, othervalue.length()));
			} else if ("ii_shift".equals(othername)) {  
				//MES班次
				othervalue=lt.elementTextTrim("value");
				qci.setShiftName(othervalue.substring(0,othervalue.lastIndexOf(",")));
				qci.setShiftId(othervalue.substring(othervalue.lastIndexOf(",")+1, othervalue.length()));
			} else if ("ii_chk".equals(othername)) {
				qci.setIiChk(lt.elementTextTrim("value"));
			} else if ("ii_adt".equals(othername)) {
				qci.setIiAdk(lt.elementTextTrim("value"));
			} else if ("ii_remark".equals(othername)) {
				qci.setRemark(lt.elementTextTrim("value"));
			} else if ("ii_coil_tz".equals(othername)) {
				//说明：1#到12#卷烟机 编码分别2101 --- 2112
				othervalue=lt.elementTextTrim("value");
				qci.setRollerName(othervalue.substring(0,othervalue.lastIndexOf(",")));
				othervalue=othervalue.substring(othervalue.lastIndexOf(",")+1, othervalue.length());
				qci.setRollerCode(MESConvertToJB.transEqpCode(othervalue));
			} else if ("ii_pack_jb_tz".equals(othername)) {
				//说明：1#到12#包装机 编码分别2201 --- 2212
				othervalue=lt.elementTextTrim("value");
				qci.setPackerName(othervalue.substring(0,othervalue.lastIndexOf(",")));
				othervalue=othervalue.substring(othervalue.lastIndexOf(",")+1, othervalue.length());
				qci.setPackerCode(MESConvertToJB.transEqpCode(othervalue));
			} else if ("ii_sealer_tz".equals(othername)) {
				//说明：1#到3#封箱机 编码分别2301 --- 2303
				othervalue=lt.elementTextTrim("value");
				qci.setSealerName(othervalue.substring(0,othervalue.lastIndexOf(",")));
				othervalue=othervalue.substring(othervalue.lastIndexOf(",")+1, othervalue.length());
				qci.setSealerCode(MESConvertToJB.transEqpCode(othervalue));
			} else if ("ii_order".equals(othername)) {
				qci.setOrderNum(lt.elementTextTrim("value"));
			} else if ("ii_samsequence".equals(othername)) {
				qci.setSamSequence(lt.elementTextTrim("value"));
			} else if ("ii_season_cpsh_tz".equals(othername)) {
				qci.setSeasonCpsh(lt.elementTextTrim("value"));
			} else if ("ii_chk_time_cpsh".equals(othername)) {
				othervalue=lt.elementTextTrim("value");
				if(!"".equals(othervalue)){
					qci.setCheckTime(DateUtil.formatStringToDate(othervalue, "yyyy-MM-dd HH:mm:ss"));
				}
			} else if ("ii_shift_tz".equals(othername)) {
				//滕州班次
				othervalue=lt.elementTextTrim("value");
				qci.setShiftTz(othervalue.substring(0,othervalue.lastIndexOf(",")));
				qci.setShiftTzCode(othervalue.substring(othervalue.lastIndexOf(",")+1, othervalue.length()));
			}
		}
		qci=workOrderService.saveQualityInfo(qci);
		//
		Element sampResult=fnode.element("sampleResults");
		List<Element> ltpter=sampResult.elements("parameter");
		List<Element> listcell=null;
		QualityCheckInfoParams qcip=null;
		String othervla="";
		String othernm="";
		if(ltpter.size()>0 && ltpter!=null){
			for(Element elt:ltpter){
				//说明：由于有2种不同集合
				listcell=elt.elements("cell");
				qcip=new QualityCheckInfoParams();
				if(listcell.size()>0 && listcell!=null){
					qcip.setPgDes(elt.elementTextTrim("pg"));
					qcip.setPaDes(elt.elementTextTrim("pa"));
					qcip.setPaType(elt.elementTextTrim("pa"));
					qcip.setValueF(elt.elementTextTrim("value_f"));
					othervla=elt.elementTextTrim("sc_deduct");
					if(!"".equals(othervla)){
						qcip.setScDeduct(Double.parseDouble(othervla));
					}
					qcip.setUnit( MESConvertToJB.MES2JBMatUnitConvert(elt.elementTextTrim("unit")) );//单位ID
					for(Element cel:listcell){
						othernm=cel.elementTextTrim("id");
						if("c_mean".equals(othernm)){
							qcip.setcMean(cel.elementTextTrim("value"));
						}else if("c_min".equals(othernm)){
							qcip.setcMin(cel.elementTextTrim("value"));
						}else if("c_max".equals(othernm)){
							qcip.setcMax(cel.elementTextTrim("value"));
						}else if("c_result".equals(othernm)){
							qcip.setcResult(cel.elementTextTrim("value"));
						}else if("c_sd".equals(othernm)){
							qcip.setcSd(cel.elementTextTrim("value"));
						}else if("c_cv".equals(othernm)){
							qcip.setcCv(cel.elementTextTrim("value"));
						}
					}
				}else{
					qcip.setPgDes(elt.elementTextTrim("pg")); //标题-质管处质检室检验物理指标
					
					othervla=elt.elementTextTrim("pa");
					qcip.setPaType(othervla.substring(0,othervla.indexOf(","))); //前面名称-烟支卷接端部落丝量
					qcip.setPaTypeCode(othervla.substring( othervla.indexOf(",")+1, othervla.length() )); //后面类型-B
					qcip.setValueF(elt.elementTextTrim("value_f"));
					othervla=elt.elementTextTrim("sc_deduct");
					if(!"".equals(othervla)){
						qcip.setScDeduct(Double.parseDouble(othervla));
					}
					qcip.setUnit( MESConvertToJB.MES2JBMatUnitConvert(elt.elementTextTrim("unit")) );//单位ID
				}
				qcip.setqId(qci.getId());
				//保存
				workOrderService.saveQualityParamInfo(qcip);
			}
		}
		
		return true;
		
	}
	
	/**
	 * 【功能描述】接口日志
	 *  @author shisihai
	 *  @createTime 2016年1月12日11:52:03
	 */
	public void addLog(String des,Integer flag,String xml){
		try {
			SysMessageQueue log=new SysMessageQueue();
			log.setSysReceive(1L);//接受方
			log.setSysSend(2L);//发送方
			log.setDel(0L);
			log.setMsgType(0L);
			log.setContent(xml);
			log.setDes(des);//描述
			log.setFlag(flag);//成功或失败
			log.setDate_(new  Date());
			workOrderService.saveAndReturn(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 【功能描述】工厂日历
	 *  @author wanchanghuang
	 * @param ftype 
	 * @param fstr 
	 *  @createTime 2016年1月13日08:37:51
	 *  
	 *  业务描述： 卷包工厂日历和成型工厂日历一样，
	 *           代码插入数据只改变workshop字段   1-卷包   2-成型
	 */
	public void alalyCalenderXML(String ftype, String fstr, Document doc) {
		try {
			Element node=doc.getRootElement();
			Element fnode= node.element("CalendarInfo");
			/*String shift1=MESConvertToJB.convertShift(fnode.elementTextTrim("ShiftID1"), 1);
			String shift2=MESConvertToJB.convertShift(fnode.elementTextTrim("ShiftID2"),1);
			String shift3=MESConvertToJB.convertShift(fnode.elementTextTrim("ShiftID3"),1);*/
			String shift1=fnode.elementTextTrim("ShiftID1");
			String shift2=fnode.elementTextTrim("ShiftID2");
			String shift3=fnode.elementTextTrim("ShiftID3");
			String stime1=fnode.elementTextTrim("stime1");
			String etime1=fnode.elementTextTrim("etime1");
			String stime2=fnode.elementTextTrim("stime2");
			String etime2=fnode.elementTextTrim("etime2");
			String stime3=fnode.elementTextTrim("stime3");
			String etime3=fnode.elementTextTrim("etime3");
			List<Element> elt=fnode.elements("Item");
			if(elt.size()>0 && elt!=null){
				SchCalendar scr1=null;
				SchCalendar scr2=null;
				SchCalendar scr3=null;
				MdShift mshift1=null;
				MdShift mshift2=null;
				MdShift mshift3=null;
				MdTeam mteam1=null;
				MdTeam mteam2=null;
				MdTeam mteam3=null;
				MdWorkshop mdWorkshop1=null;
				MdWorkshop mdWorkshop2=null;
				MdWorkshop mdWorkshop3=null;
				String date="";
				for(Element lt:elt){
					//早
					scr1=new SchCalendar();
					mshift1=new MdShift();
					mshift1.setId(shift1);
					scr1.setMdShift(mshift1);
					String teamID1=lt.elementTextTrim("TeamID1");
					if(StringUtil.notNull(teamID1)){
						mteam1=new MdTeam();
						mteam1.setId(lt.elementTextTrim("TeamID1"));
						scr1.setMdTeam(mteam1);
					}else{
						scr1.setMdTeam(null);
					}
					date=lt.elementTextTrim("Date");
					scr1.setDate(DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					scr1.setStim(DateUtil.strToDate(date+" "+stime1+":00", "yyyy-MM-dd HH:mm:ss"));
					scr1.setEtim(DateUtil.strToDate(date+" "+etime1+":00", "yyyy-MM-dd HH:mm:ss"));
					mdWorkshop1=new MdWorkshop();
					mdWorkshop1.setId("1");
					scr1.setMdWorkshop(mdWorkshop1);
					scr1.setDel(0l);
					if(!"5".equals(scr1.getMdTeam().getId())){
						workOrderService.saveSchCalendar(scr1);//卷包车间日历
						mdWorkshop2=new MdWorkshop();
						mdWorkshop2.setId("2");
						scr1.setMdWorkshop(mdWorkshop2);
						workOrderService.saveSchCalendar(scr1); //成型车间日历
					}
					//中
					scr2=new SchCalendar();
					mshift2=new MdShift();
					mshift2.setId(shift2);
					scr2.setMdShift(mshift2);
					String teamID2=lt.elementTextTrim("TeamID2");
					if(StringUtil.notNull(teamID2)){
						mteam2=new MdTeam();
						mteam2.setId(lt.elementTextTrim("TeamID2"));
						scr2.setMdTeam(mteam2);
					}else{
						scr2.setMdTeam(null);
					}
					scr2.setDate(DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					scr2.setStim(DateUtil.strToDate(date+" "+stime2+":00", "yyyy-MM-dd HH:mm:ss"));
					scr2.setEtim(DateUtil.strToDate(date+" "+etime2+":00", "yyyy-MM-dd HH:mm:ss"));
					mdWorkshop2=new MdWorkshop();
					mdWorkshop2.setId("1");
					scr2.setMdWorkshop(mdWorkshop2);
					scr2.setDel(0l);
					//跳过5-休息班
					if(!"5".equals(scr2.getMdTeam().getId())){
						workOrderService.saveSchCalendar(scr2); //卷包日历
						mdWorkshop2=new MdWorkshop();
						mdWorkshop2.setId("2");
						scr2.setMdWorkshop(mdWorkshop2);
						workOrderService.saveSchCalendar(scr2); //成型车间日历
					}
					//晚
					scr3=new SchCalendar();
					mshift3=new MdShift();
					mshift3.setId(shift3);
					scr3.setMdShift(mshift3);
					String teamID3=lt.elementTextTrim("TeamID3");
					if(StringUtil.notNull(teamID3)){
						mteam3=new MdTeam();
						mteam3.setId(lt.elementTextTrim("TeamID3"));
						scr3.setMdTeam(mteam3);
					}else{
						scr3.setMdTeam(null);
					}
					scr3.setDate(DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					scr3.setStim(DateUtil.strToDate(date+" "+stime3+":00", "yyyy-MM-dd HH:mm:ss"));
					scr3.setEtim(DateUtil.strToDate(DateUtil.dateFormatCal(date, 1) +" "+etime3+":00", "yyyy-MM-dd HH:mm:ss"));
					mdWorkshop3=new MdWorkshop();
					mdWorkshop3.setId("1");//车间
					scr3.setMdWorkshop(mdWorkshop3);
					scr3.setDel(0l);
					//跳过5-休息班
					if(!"5".equals(scr3.getMdTeam().getId())){
						workOrderService.saveSchCalendar(scr3);
						mdWorkshop2=new MdWorkshop();
						mdWorkshop2.setId("2");
						scr3.setMdWorkshop(mdWorkshop2);
						workOrderService.saveSchCalendar(scr3);
					}
				}
				/**日志 1-成功*/
			    ParsingXmlDataInterceptor.getInstance().addLog("工厂日历",1,fstr); 
			}
		} catch (Exception e) {
			/**日志 0-失败*/
		    ParsingXmlDataInterceptor.getInstance().addLog("工厂日历",0,fstr); 
			System.out.println("REEOR:工厂日历接口解析失败..."+ftype+":"+fstr);
			e.printStackTrace();
		}
	}
	
	/**
	 * 【功能描述】物料主数据
	 *  @author wanchanghuang
	 * @param ftype 
	 * @param fstr 
	 *  @createTime 2016年1月13日08:37:51
	 *  
	 *       步骤：1）解析xml数据，然后通过数据验证md_mat_type表，判断该数据是否存在，如果存在不插入；
	 *            2）再插入md_mat表,判断是否存在，存在不插入
	 *            3）select * from MD_MAT_GRP;  物料组 （成品    辅料   半成品   原料） #手动维护
	 *	          4）select * from MD_MAT_TYPE; 物料类型     MATKL,ZEINR
	 */
	public void addSendMatmas(String ftype, String fstr, Document doc) {
		try {
			//List list = doc.selectNodes("//idocPacket/idoc/segment/field[@fieldName='LAEDA']" );
			List list = doc.selectNodes("//idocPacket/idoc/segment/field" );
			//物料主数据名称和描述
			List ls2= doc.selectNodes("//idocPacket/idoc/segment/segment/field" );
			String matName="";
			for (Iterator mat=ls2.iterator();mat.hasNext();) {
				Element e = (Element) mat.next();
	            String strv=e.attributeValue("fieldName");
	            if("MAKTX".equals(strv)){
	            	matName=e.getText();
	            	break;
	            }
			}
			MdMat mm=new MdMat();
			mm.setName(matName);//物料主数据-名称
			mm.setSimpleName(matName); //物料主数据-描述
	    	mm.setDes(matName);  //物料主数据-备注
			MdUnit mu=new MdUnit(); 
			MdMatType mmt = new MdMatType(); 
			MdMatGrp mmg = new MdMatGrp();
			String desf=""; //描述+物料类型
			Long lg=0l;
			for(Iterator it=list.iterator();it.hasNext();){ 
				Element e = (Element) it.next();
	            String strv=e.attributeValue("fieldName");
	           /* if("MAKTX".equals(strv)){
	            	mm.setName(e.getText());       //物料主数据-名称
	            }else if("MAKTX".equals(strv)){
	            	mm.setSimpleName(e.getText()); //物料主数据-描述
	            	mm.setDes(e.getText());        //物料主数据-备注
	            }else */
	            if("MTART".equals(strv)){
	            	mm.setId( e.getText() );       //物料主数据-物料号(ID)
	            	desf=e.getText();
	            }else if("MATNR".equals(strv)){
	            	try {
	            		//将“000004656”类型的数据转换成 4656
						lg=Long.parseLong( e.getText() );
						mm.setCode( lg.toString() );     //物料主数据-物料类型(CODE)
					} catch (Exception e2) {}
	            }else if("MEINS".equals(strv)){
	            	mu.setId(MESConvertToJB.MES2JBMatUnitConvert(e.getText()));//物料主数据-单位ID
	            }else if("MATKL".equals(strv)){
	            	mmt.setMesCode( e.getText() );      //物料类型-MES_CODE (md_mat_type表id对应物料主数据的tid)
	            }else if("ZEINR".equals(strv)){
	            	mmt.setName(e.getText());      //物料类型-名称
	            	mmt.setDes(e.getText());       //物料类型-描述
	            }
	        }
			//md_mat_type表
			//mmg.setId("0");
			mmt.setMdMatGrp(mmg);
		    mmt.setEnable(1l);
		    mmt.setDel("0");
		    String typeId=workOrderService.queryRetByCode(mmt); //验证md_mat_type表是否重复插入
		    mmt.setId(typeId);
		    /** 特殊情况处理   CL0800%：条盒透明纸      CL0801%：小盒透明纸     照成这种情况原因是ERP不能区分透明纸明细（小盒，条盒），所以才想出此方法 */
		    if(mm.getCode().contains("CL0800")){
		    	mmt.setId("8"); //条盒纸
		    }else if(mm.getCode().contains("CL0801")){
		    	mmt.setId("7");//小盒纸
		    }
			//md_mat表
			mm.setDel("0");
			mm.setStandardVal(0d);//物料消耗标准值
			mm.setEnable(1l);
			mm.setLastUpdateTime(new Date());
			mm.setMdUnit(mu);
			mm.setMdMatType(mmt);
			mm.setDes(desf+"-"+mm.getDes()); //将MES物料类型存入描述中
			workOrderService.saveMdMatByCode(mm);
			/**日志 1-成功*/
		    ParsingXmlDataInterceptor.getInstance().addLog("物料主数据",1,fstr); 
		} catch (Exception e) {
			/**日志 0-失败*/
		    ParsingXmlDataInterceptor.getInstance().addLog("物料主数据",0,fstr); 
			System.out.println("REEOR:物料主数据接口解析失败..."+ftype+":"+fstr);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 【功能描述】：虚领虚退
	 * @date 2016年8月29日10:24:49
	 * @author wanchanghuang
	 * 
	 * */
	public void addSendReceiveMat(String ftype, String fstr, Document fdoc) {
		 try {
			 QmChangeShift qcs=new QmChangeShift();
			 Element node=fdoc.getRootElement();
			 Element fnode= node.element("EntryBasicInfo");
			 
			 //Element fnodes= fnode.element("EntryBasicInfo");
			 //解析工单基本信息
			 qcs.setDeviceId(fnode.elementTextTrim("DeviceID")); //MES工单号
			 qcs.setLotId(fnode.elementTextTrim("LotID")); //MES工单号
			 qcs.setMesEntryId(fnode.elementTextTrim("EntryID")); //MES工单号
			 qcs.setOutMaterialId(fnode.elementTextTrim("OutMaterialID")); //MES工单号
			 qcs.setOutMaterialNm(fnode.elementTextTrim("OutMaterialNM")); //MES工单号
			 qcs.setOutMaterialQty(Float.parseFloat(fnode.elementTextTrim("OutMaterialQuantity"))); //MES工单号
			 qcs.setUom(fnode.elementTextTrim("UOM")); //MES工单号
			 qcs.setShiftId(fnode.elementTextTrim("ShiftID")); //MES工单号
			 qcs.setShiftName(fnode.elementTextTrim("ShiftNM")); //MES工单号
			 qcs.setTeamId(fnode.elementTextTrim("TeamID")); //MES工单号
			 qcs.setTeamName(fnode.elementTextTrim("TeamNM")); //MES工单号
			 qcs.setWoState(fnode.elementTextTrim("WO_STATE")); //MES工单号
			 qcs.setPlanStartTime(fnode.elementTextTrim("PlanningStartTime")); //MES工单号
			 qcs.setPlanFinishedTime(fnode.elementTextTrim("PlanningFinishedTime")); //MES工单号
			 qcs.setActualStartTime(fnode.elementTextTrim("ActualStartTime")); //MES工单号
			 qcs.setActualFinishedTime(fnode.elementTextTrim("ActualFinishedTime")); 
			 qcs.setSupplyTime(fnode.elementTextTrim("SupplyTime")); //MES工单号
			 qcs.setCreateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			 qcs.setUpdateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			 qcs.setEntryId( qcs.getMesEntryId()+qcs.getShiftId());
			 //保存，并返回插入的对象
			 QmChangeShift qcsf =workOrderService.saveQmChangeShift(qcs);
			 if(qcsf!=null){
				 QmChangeShiftInfo qcsInfo=null;// new QmChangeShiftInfo();
				 //解析实领
				 Element slNood= node.element("MaterialInfoSGET");
				 List<Element> ellist=slNood.elements("Item");
				 if(ellist.size()>0 && ellist!=null){
					for(Element fl:ellist){
						qcsInfo=new QmChangeShiftInfo();
						qcsInfo.setMaterialClass(fl.elementTextTrim("MaterialClass"));
						qcsInfo.setMaterialId(fl.elementTextTrim("MaterialID"));
						//通过md_mat表code，查询tid
						Map<String,String> map=MESConvertToJB.MES2JBMatConvert2( fl.elementTextTrim("MaterialID") );
						if(map!=null){
							qcsInfo.setDel("0");
							qcsInfo.setTid( map.get("tid") );
						}else{
							qcsInfo.setDel("1");
							qcsInfo.setTid( fl.elementTextTrim("MaterialID") );
						}
						qcsInfo.setMaterialName(fl.elementTextTrim("MaterialName"));
						qcsInfo.setLotId(fl.elementTextTrim("LotID"));
						qcsInfo.setQty(Float.parseFloat(fl.elementTextTrim("Quantity")));
						qcsInfo.setUom(fl.elementTextTrim("UOM"));
						qcsInfo.setOpeTime(fl.elementTextTrim("OPETIME"));
						qcsInfo.setCreateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setUpdateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setType("1");
						qcsInfo.setQmcsid(qcsf.getId());
						//保存
						workOrderService.saveQmChangeShiftInfo(qcsInfo);
					}
				 }	
				 //解析实退
				 Element stNood= node.element("MaterialInfoSSEND");
				 List<Element> stlist=stNood.elements("Item");//.elements("item");
				 if(stlist.size()>0 && stlist!=null){
					for(Element fl:stlist){
						qcsInfo=new QmChangeShiftInfo();
						qcsInfo.setMaterialClass(fl.elementTextTrim("MaterialClass"));
						qcsInfo.setMaterialId(fl.elementTextTrim("MaterialID"));
						//通过md_mat表code，查询tid
						Map<String,String> map=MESConvertToJB.MES2JBMatConvert2( fl.elementTextTrim("MaterialID") );
						if(map!=null){
							qcsInfo.setDel("0");
							qcsInfo.setTid( map.get("tid") );
						}else{
							qcsInfo.setDel("1");
							qcsInfo.setTid( fl.elementTextTrim("MaterialID") );
						}
						qcsInfo.setMaterialName(fl.elementTextTrim("MaterialName"));
						qcsInfo.setLotId(fl.elementTextTrim("LotID"));
						qcsInfo.setQty(Float.parseFloat(fl.elementTextTrim("Quantity")));
						qcsInfo.setUom(fl.elementTextTrim("UOM"));
						qcsInfo.setOpeTime(fl.elementTextTrim("OPETIME"));
						qcsInfo.setCreateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setUpdateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setType("2");
						qcsInfo.setQmcsid(qcsf.getId());
						//保存
						workOrderService.saveQmChangeShiftInfo(qcsInfo);
					}
				 }	
				 //解析虚领
				 Element xlNood= node.element("MaterialInfoXGET");
				 List<Element> xllist=xlNood.elements("Item");
				 if(xllist.size()>0 && xllist!=null){
					for(Element fl:xllist){
						qcsInfo=new QmChangeShiftInfo();
						qcsInfo.setMaterialClass(fl.elementTextTrim("MaterialClass"));
						qcsInfo.setMaterialId(fl.elementTextTrim("MaterialID"));
						//通过md_mat表code，查询tid
						Map<String,String> map=MESConvertToJB.MES2JBMatConvert2( fl.elementTextTrim("MaterialID") );
						if(map!=null){
							qcsInfo.setDel("0");
							qcsInfo.setTid( map.get("tid") );
						}else{
							qcsInfo.setDel("1");
							qcsInfo.setTid( fl.elementTextTrim("MaterialID") );
						}
						qcsInfo.setMaterialName(fl.elementTextTrim("MaterialName"));
						qcsInfo.setLotId(fl.elementTextTrim("LotID"));
						qcsInfo.setQty(Float.parseFloat(fl.elementTextTrim("Quantity")));
						qcsInfo.setUom(fl.elementTextTrim("UOM"));
						qcsInfo.setOpeTime(fl.elementTextTrim("OPETIME"));
						qcsInfo.setCreateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setUpdateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setType("3");
						qcsInfo.setQmcsid(qcsf.getId());
						//保存
						workOrderService.saveQmChangeShiftInfo(qcsInfo);
					}
				 }	
				 //解析虚退
				 Element xtNood= node.element("MaterialInfoXSEND");
				 List<Element> xtlist=xtNood.elements("Item");
				 if(xtlist.size()>0 && xtlist!=null){
					for(Element fl:xtlist){
						qcsInfo=new QmChangeShiftInfo();
						qcsInfo.setMaterialClass(fl.elementTextTrim("MaterialClass"));
						qcsInfo.setMaterialId(fl.elementTextTrim("MaterialID"));
						//通过md_mat表code，查询tid
						Map<String,String> map=MESConvertToJB.MES2JBMatConvert2( fl.elementTextTrim("MaterialID") );
						if(map!=null){
							qcsInfo.setDel("0");
							qcsInfo.setTid( map.get("tid") );
						}else{
							qcsInfo.setDel("1");
							qcsInfo.setTid( fl.elementTextTrim("MaterialID") );
						}
						qcsInfo.setMaterialName(fl.elementTextTrim("MaterialName"));
						qcsInfo.setLotId(fl.elementTextTrim("LotID"));
						qcsInfo.setQty(Float.parseFloat(fl.elementTextTrim("Quantity")));
						qcsInfo.setUom(fl.elementTextTrim("UOM"));
						qcsInfo.setOpeTime(fl.elementTextTrim("OPETIME"));
						qcsInfo.setCreateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setUpdateTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
						qcsInfo.setType("4");
						qcsInfo.setQmcsid(qcsf.getId());
						//保存
						workOrderService.saveQmChangeShiftInfo(qcsInfo);
					}
				 }	
			 }
			 /**日志 1-成功*/
			 ParsingXmlDataInterceptor.getInstance().addLog("MES下发虚领虚退",1,fstr); 
		} catch (Exception e) {
			/**日志 0-失败*/
		    ParsingXmlDataInterceptor.getInstance().addLog("MES下发虚领虚退",0,fstr); 
			System.out.println("REEOR:虚领虚退数据接口解析失败..."+ftype+":"+fstr);
			e.printStackTrace();
		}		
	}

	
}
