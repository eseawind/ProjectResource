package com.shlanbao.tzsc.pms.sch.workorder.service.impl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.MdMatDaoI;
import com.shlanbao.tzsc.base.dao.MdMatTypeDaoI;
import com.shlanbao.tzsc.base.dao.QmChangeShiftDaoI;
import com.shlanbao.tzsc.base.dao.QmChangeShiftInfoDaoI;
import com.shlanbao.tzsc.base.dao.QualityCheckMesDataDaoI;
import com.shlanbao.tzsc.base.dao.QualityCheckMesDataParamsDaoI;
import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;
import com.shlanbao.tzsc.base.dao.SchConStdDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderBomDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.dao.SysLogDaoI;
import com.shlanbao.tzsc.base.dao.SysMessageQueueDaoI;
import com.shlanbao.tzsc.base.interceptor.WorkOrderStatChangeInterceptor;
import com.shlanbao.tzsc.base.mapping.ChangeShiftDatas;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdMat;
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
import com.shlanbao.tzsc.base.mapping.SysLog;
import com.shlanbao.tzsc.base.mapping.SysMessageQueue;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.ParsingXmlDataBean;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataHandler;
import com.shlanbao.tzsc.data.runtime.handler.DbOutputOrInputInfos;
import com.shlanbao.tzsc.data.runtime.handler.FilterCalcValue;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.data.runtime.handler.PackerCalcValue;
import com.shlanbao.tzsc.data.runtime.handler.RollerCalcValue;
import com.shlanbao.tzsc.init.BaseParams;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;
import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.pms.sch.manualshift.service.ManualShiftServiceI;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.pms.sch.stat.service.StatServiceI;
import com.shlanbao.tzsc.pms.sch.workorder.beans.WorkOrderBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.sch.calendar.service.WctCalendarServiceI;
import com.shlanbao.tzsc.wct.sch.workorder.service.WctWorkOrderServiceI;
@Service
public class WorkOrderServiceImpl extends BaseService implements WorkOrderServiceI{
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Autowired
	private SchWorkorderBomDaoI schWorkorderBomDao;
	@Autowired
	private StatServiceI statService;
	@Autowired
	private WctCalendarServiceI wctCalendarService;
	@Autowired
	private WctWorkOrderServiceI wctWorkOrderService;
	@Autowired
	private ManualShiftServiceI wctManualShiftService;
	@Autowired
	private SchCalendarDaoI schCalendarDao;
	@Autowired
	private SysLogDaoI logdao;
	@Autowired
	private SysMessageQueueDaoI  SysMessageQueueDao;
	@Autowired
	private StatServiceI statServiceImpl;
	@Autowired
	private SysLogDaoI sysLogDaoImpl;
	@Autowired
	private MdMatTypeDaoI mdMatTypeDao;
	@Autowired
	private MdMatDaoI mdMatDao;
	@Autowired
	private  SchConStdDaoI schConStdDaoI;
	@Autowired
	private  QmChangeShiftDaoI qmChangeShiftDaoI;
	@Autowired
	private QualityCheckMesDataParamsDaoI InfoParamsDao;
	@Autowired
	private QmChangeShiftInfoDaoI qmChangeShiftInfoDaoI;
	@Autowired
	private QualityCheckMesDataDaoI infoDao;
	
	public SchWorkorder getRunSchWorkorder(String code){
		String hql="from SchWorkorder o where o.del=0 and o.isCheck=1 and o.sts=2 and o.mdEquipment.equipmentCode=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(code);
		SchWorkorder schWorkorder = null;
		try {
			schWorkorder = schWorkorderDao.unique(SchWorkorder.class, hql, params);
		} catch (Exception e) {
			log.debug("error :"+e.getMessage());
		}
		return schWorkorder;
	}
	/**
	 * 根据设备CODE 获取正在运行的工单 并填充辅料
	 * @throws Exception
	 */
	public void queryRunWorkFl(SchWorkorder schWorkorder){
		//向DataHandler中calcValues保存辅料计数参数和烟机滚轴系数
		try {
			if(null!=schWorkorder){
				this.setCalcValue(schWorkorder);
			}
		} catch (Exception e) {
			log.debug("error :"+e.getMessage());
		}
	}
	
	
	
	
	public SchWorkorder getWork(String code){
		String hql="from SchWorkorder o where o.del=0 and o.isCheck=1 and o.sts=2 and o.mdEquipment.equipmentCode=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(code);
		SchWorkorder schWorkorder = null;
		//有运行的工单
		//向DataHandler中calcValues保存辅料计数参数和烟机滚轴系数
		try {
			schWorkorder = schWorkorderDao.unique(SchWorkorder.class, hql, params);
			if(null!=schWorkorder){
				this.setCalcValue(schWorkorder);
			}
		} catch (Exception e) {
			log.debug("error :"+e.getMessage());
		}
		return schWorkorder;
	}
	@Override
	public DataGrid getAllWorkOrders(WorkOrderBean workOrderBean,
			PageParams pageParams) throws Exception{
		
		//String hql="from SchWorkorder o left join fetch o.mdTeam t left join fetch o.mdShift s left join fetch o.mdEquipment e left join fetch e.mdEqpType et left join fetch e.mdWorkshop w left join fetch o.mdUnit u  left join fetch o.mdMat m where o.del=0 and o.type<>4";//1 deleted data is not visible
		String hql="from SchWorkorder o left join fetch o.mdTeam t left join fetch o.mdShift s left join fetch o.mdEquipment e left join fetch e.mdEqpType et left join fetch e.mdWorkshop w left join fetch o.mdUnit u  left join fetch o.mdMat m where o.del=0 and o.type<>4";//1 deleted data is not visible
		String params="";
		if(StringUtil.notNull(workOrderBean.getTeam())){
			params = params.concat(" and t.code = '" + workOrderBean.getTeam() + "'");
		}
		if(StringUtil.notNull(workOrderBean.getShift())){
			params = params.concat(" and s.code = '" + workOrderBean.getShift() + "'");
		}
		if(StringUtil.notNull(workOrderBean.getMat())){
			params = params.concat(" and m.id = '" + workOrderBean.getMat()+ "'");
		}
		if(StringUtil.notNull(workOrderBean.getDate())){
			params = params.concat(" and o.date = '"+workOrderBean.getDate()+"'");
		}
		if(workOrderBean.getSts()!=null){
			params = params.concat(" and o.sts = "+workOrderBean.getSts()+"");
		}
		if(workOrderBean.getType()!=null){
			params = params.concat(" and o.type = "+workOrderBean.getType()+"");
		}
		if(workOrderBean.getIsCheck()!=null){
			params = params.concat(" and o.isCheck = "+workOrderBean.getIsCheck()+"");
		}
		if(StringUtil.notNull(workOrderBean.getCode())){
			params = params.concat(" and (o.code like '%"+workOrderBean.getCode()+"%' or m.name like '%"+workOrderBean.getCode()+"%')");
		}
		
		List<SchWorkorder> rows=schWorkorderDao.queryByPage(hql.concat(params), pageParams);
		
		
		List<WorkOrderBean> orderBeans = new ArrayList<WorkOrderBean>();
		
		WorkOrderBean bean = null;
		try {
			
			for (SchWorkorder schWorkorder : rows) {
				
				bean = BeanConvertor.copyProperties(schWorkorder, WorkOrderBean.class);
				try {
					bean.setTeam(schWorkorder.getMdTeam().getName());
					bean.setTeamId(schWorkorder.getMdTeam().getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					bean.setShift(schWorkorder.getMdShift().getName());
					bean.setShiftId(schWorkorder.getMdShift().getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					bean.setEquipment(schWorkorder.getMdEquipment().getEquipmentName());
					bean.setEquipmentId(schWorkorder.getMdEquipment().getId());
				} catch (Exception e) {
					// TODO: handle exception
				}	
				try {
					bean.setUnit(schWorkorder.getMdUnit().getName());
					bean.setUnitId(schWorkorder.getMdUnit().getId());//(新增生产实绩产量时需要同步工单计量单位)
				} catch (Exception e) {
					// TODO: handle exception
				}	
				try {
					bean.setMat(schWorkorder.getMdMat().getName());
					bean.setMatId(schWorkorder.getMdMat().getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				orderBeans.add(bean);
				bean = null;
			}
			
			hql= "select count(o) ".concat(hql.replace("fetch", ""));
			
			long total=schWorkorderDao.queryTotal(hql.concat(params));
			
			return new DataGrid(orderBeans, total);
			
		} catch (Exception e) {
			
			log.error("POVO转换异常", e);
			
		}
		
		return null;
	}
	/**
	 * 张璐 2015.10.12
	 * 查询成型车间工单
	 */
	@Override
	public DataGrid getFormingWorkOrders(WorkOrderBean workOrderBean,PageParams pageParams) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append("from SchWorkorder o left join fetch o.mdTeam t left join fetch o.mdShift s left join fetch o.mdEquipment e left join fetch e.mdEqpType et left join fetch e.mdWorkshop w left join fetch o.mdUnit u  left join fetch o.mdMat m where o.del=0 and o.type=4");//1 deleted data is not visible
		if(StringUtil.notNull(workOrderBean.getTeam())){
			hql.append(" and t.code = '" + workOrderBean.getTeam() + "'");
		}
		if(StringUtil.notNull(workOrderBean.getShift())){
			hql.append(" and s.code = '" + workOrderBean.getShift() + "'");
		}
		if(StringUtil.notNull(workOrderBean.getDate())){
			hql.append(" and o.date = '"+workOrderBean.getDate()+"'");
		}
		if(workOrderBean.getSts()!=null){
			hql.append(" and o.sts = "+workOrderBean.getSts()+"");
		}
		if(workOrderBean.getType()!=null){
			hql.append(" and o.type = "+workOrderBean.getType()+"");
		}
		if(workOrderBean.getIsCheck()!=null){
			hql.append(" and o.isCheck = "+workOrderBean.getIsCheck()+"");
		}
		if(StringUtil.notNull(workOrderBean.getCode())){
			hql.append(" and (o.code like '%"+workOrderBean.getCode()+"%' or m.name like '%"+workOrderBean.getCode()+"%')");
		}
		
		List<SchWorkorder> rows=schWorkorderDao.queryByPage(hql.toString(), pageParams);
		
		
		List<WorkOrderBean> orderBeans = new ArrayList<WorkOrderBean>();
		
		WorkOrderBean bean = null;
		try {
			
			for (SchWorkorder schWorkorder : rows) {
				
				bean = BeanConvertor.copyProperties(schWorkorder, WorkOrderBean.class);
				
				bean.setTeam(schWorkorder.getMdTeam().getName());
				bean.setShift(schWorkorder.getMdShift().getName());
				bean.setTeamId(schWorkorder.getMdTeam().getId());
				bean.setShiftId(schWorkorder.getMdShift().getId());
				bean.setEquipment(schWorkorder.getMdEquipment().getEquipmentName());
				bean.setEquipmentId(schWorkorder.getMdEquipment().getId());
				bean.setUnit(schWorkorder.getMdUnit().getName());
				bean.setUnitId(schWorkorder.getMdUnit().getId());//(新增生产实绩产量时需要同步工单计量单位)
				bean.setMat(schWorkorder.getMdMat().getName());
				bean.setMatId(schWorkorder.getMdMat().getId());
				
				orderBeans.add(bean);
				
				bean = null;
			}
			
			//hql= "select count(o) ".concat(hql.replace("fetch", ""));
			
			long total=rows.size();//schWorkorderDao.queryTotal(hql.concat(params));
			
			return new DataGrid(orderBeans, total);
			
		} catch (Exception e) {
			
			log.error("POVO转换异常", e);
			
		}
		
		return null;
	}
	@Override
	public void checkWorkOrder(String id) throws Exception {
		SchWorkorder schWorkorder = schWorkorderDao.findById(SchWorkorder.class, id);
		if(schWorkorder!=null){
			schWorkorder.setIsCheck(1L);
			schWorkorder.setSts(Long.parseLong("1"));//状态
			schWorkorder.setIsAuto("Y");
			schWorkorder.setCheckTime(new Date());
		}else{
			log.error("id:"+id+"的工单不存在");
			throw new Exception();
		}
	}
	@Override
	public void checkWorkOrders(String ids) throws Exception {
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			this.checkWorkOrder(id);
		}
	}
	@Override
	public void editWorkOrderStatus(String id, Long sts) throws Exception {
		SchWorkorder schWorkorder = schWorkorderDao.findById(SchWorkorder.class, id);
		if(schWorkorder!=null){
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
			if(sts==2){
				Date date = new Date();
				schWorkorder.setRealStim(date);//实绩开始时间
				//保存生产实绩空数据
				OutputBean outputBean = new OutputBean();
				outputBean.setWorkorder(id);
				outputBean.setStim(DateUtil.formatDateToString(date, "yyyy-MM-dd HH:mm:ss"));//保证工单与生产实绩开始时间同步。
				statService.addOutput(outputBean, 0);
				//向DataHandler中calcValues保存辅料计数参数和烟机滚轴系数
				this.setCalcValue(schWorkorder);
			}else if(sts==0){//表示 手动托班，需要手动结束工单 
				schWorkorder.setIsAuto("N");//表示 手动结束工单 
			}
			if(sts==4){
				String ecode=schWorkorder.getMdEquipment().getEquipmentCode();
				Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos = DataHandler.getDbOutputOrInputInfos();
				String dbOutputId = DataHandler.getDbOutputId(dbOutputOrInputInfos, ecode);
				if(dbOutputId!=null){
					OutputBean outputBean=new OutputBean();
					outputBean.setId(dbOutputId);
					//实绩完成时间
					outputBean.setEtim(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					statServiceImpl.editOutput(outputBean);
					
				}
				schWorkorder.setRealEtim(new Date());//实绩结束时间	
				statService.saveEquipmentData(ecode);
			}
			if(sts==5){
				//删除生产实绩数据
				statService.deleteOutputByWorkOrder(id);
				
			}
			//next step向MES系统发送工单状态变化信号(MES接口暂未开发)
			//...
			//this.sendOrderMsgToMES(schWorkorder.getCode(),sts);
			
			schWorkorder.setSts(sts);
			WorkOrderStatChangeInterceptor workOrderStatChangeInterceptor=new WorkOrderStatChangeInterceptor();
			workOrderStatChangeInterceptor.sendWorkorderChangeInfoToMES(schWorkorder,id, sts);
		}else{
			log.error("id:"+id+"的工单不存在");
			throw new Exception("id:"+id+"的工单不存在");
		}
	}
	/**
	 * 发送工单启停信号给MES系统
	 * 问题:当请求MES系统超时时，卷包工单是否能够正常运行，待MES系统正常之后，卷包使用何种机制补发信号以达到同步
	 * @author Leejean
	 * @create 2015年1月20日下午3:03:45
	 * @param code
	 * @param sts
	 * @throws Exception
	 */
	public void sendOrderMsgToMES(String code,Long sts) throws Exception{
		throw new Exception("发送工单信号到MES失败，请联系MES管理员.");
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
	public WorkOrderBean getWorkOrderById(String id) throws Exception  {
		SchWorkorder schWorkorder = schWorkorderDao.findById(SchWorkorder.class, id);
		
		WorkOrderBean bean = BeanConvertor.copyProperties(schWorkorder, WorkOrderBean.class);
		if(schWorkorder.getMdTeam().getName()!=null){
			bean.setTeam(schWorkorder.getMdTeam().getName());
		}
		
		bean.setShift(schWorkorder.getMdShift().getName());
		
		bean.setEquipment(schWorkorder.getMdEquipment().getEquipmentName());
		
		bean.setMat(schWorkorder.getMdMat().getSimpleName());
		
		return bean;
	}
	@Override
	public void addOrder(WorkOrderBean workOrderBean) throws Exception {
		SchWorkorder schWorkorder = BeanConvertor.copyProperties(workOrderBean, SchWorkorder.class);
		schWorkorder.setMdUnit(new MdUnit(workOrderBean.getUnitId()));
		schWorkorder.setMdEquipment(new MdEquipment(workOrderBean.getEquipmentId()));
		schWorkorder.setMdTeam(new MdTeam(workOrderBean.getTeamId()));
		schWorkorder.setMdMat(new MdMat(workOrderBean.getMatId()));
		schWorkorder.setMdShift(new MdShift(workOrderBean.getShiftId()));
		schWorkorder.setRunSeq(1L);
		schWorkorder.setEnable(1L);
		schWorkorder.setDel(0L);
		schWorkorderDao.save(schWorkorder);
	}
	@Override
	public void initAllRunnedWorkOrderCalcValues() throws Exception {
		List<SchWorkorder> schWorkorders = schWorkorderDao.query("from SchWorkorder o where o.date='"+MESConvertToJB.rutDateStr()+"' and o.sts=2" );
		for (SchWorkorder schWorkorder : schWorkorders) {
			this.setCalcValue(schWorkorder);
		}
	}
	
	public boolean addPbWork(WorkOrderBean workOrderBean) throws Exception{
		if(null!=workOrderBean){
			String start = workOrderBean.getStim();//开始日期
			String end = workOrderBean.getEtim();//结束日期
		    //一共多少天
			int days =  DateUtil.computerDiffDate(start.replaceAll("-", ""), end.replaceAll("-", ""));
			if(days>=0){//表示 包含当前
				//删除当前时间段的记录
				StringBuffer hql = new StringBuffer();
				hql.append("delete from SchCalendar s  ");
				List<Object> params = new ArrayList<Object>();
				hql.append("where s.date>=? and s.date<=? and s.mdWorkshop.id=? ");
				Date startDay = DateUtil.strToDate(start+" 00:00:00","yyyy-MM-dd HH:mm:ss");
				Date endDay = DateUtil.strToDate(end+" 23:59:59","yyyy-MM-dd HH:mm:ss");
				params.add(startDay);
				params.add(endDay);
				params.add(String.valueOf(workOrderBean.getType()));
				schWorkorderDao.deleteByParams(hql.toString(),params);
				this.addCurMonth((days+1),workOrderBean);
			}
		}
		return true;
		
	}
	/**
	 * 
	 */
	public void addCurMonth(int days,WorkOrderBean workOrderBean){
		for(int j=0;j<days;j++){//2天
			 	String stim = workOrderBean.getStim();
			 	Date beginDay = DateUtil.strToDate(stim,"yyyy-MM-dd");
				
			 	for(int i=0;i<3;i++){
					SchCalendar sch = new SchCalendar();
					MdWorkshop mdWorkshop = new MdWorkshop();
					mdWorkshop.setId(String.valueOf(workOrderBean.getType()));//1.卷包车间 2.成型车间
					sch.setId(UUID.randomUUID().toString());
					sch.setMdWorkshop(mdWorkshop);
					if(i==0){//早班
						String begin = DateUtil.dateAdd("d",j,beginDay,"yyyy-MM-dd");
						sch.setDate(DateUtil.strToDate(begin+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
						sch.setStim(DateUtil.strToDate(begin+" 07:00:00", "yyyy-MM-dd HH:mm:ss"));
						sch.setEtim(DateUtil.strToDate(begin+" 15:00:00", "yyyy-MM-dd HH:mm:ss"));
						MdShift mdShift = new MdShift();
						MdTeam team =new MdTeam();
						mdShift.setId(workOrderBean.getShiftId());
						mdShift.setName(workOrderBean.getShiftName());
						team.setId(workOrderBean.getTeamId());
						team.setName(workOrderBean.getTeamName());
						/*mdShift.setId("1");mdShift.setName("早班");
						if("1".equals(workOrderBean.getTeamId())){//B.早-甲
							team.setId("1");team.setName("甲");
						}else if("2".equals(workOrderBean.getTeamId())){//C.早-乙
							team.setId("2");team.setName("乙");
						}else if("3".equals(workOrderBean.getTeamId())){//A.早-丙
							team.setId("3");team.setName("丙");
						}*/
						sch.setMdTeam(team);
						sch.setMdShift(mdShift);
					}else if(i==1){//中班
						String begin = DateUtil.dateAdd("d",j,beginDay,"yyyy-MM-dd");
						sch.setDate(DateUtil.strToDate(begin+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
						sch.setStim(DateUtil.strToDate(begin+" 15:00:00", "yyyy-MM-dd HH:mm:ss"));
						sch.setEtim(DateUtil.strToDate(begin+" 23:00:00", "yyyy-MM-dd HH:mm:ss"));
						MdShift mdShift = new MdShift();
						MdTeam team =new MdTeam();
						mdShift.setId(workOrderBean.getShiftTwoId());
						mdShift.setName(workOrderBean.getShiftTwoName());
						team.setId(workOrderBean.getTeamTwoId());
						team.setName(workOrderBean.getTeamTwoName());
						/*mdShift.setId("2");mdShift.setName("中班");
						if("1".equals(workOrderBean.getTeamId())){//B.中-乙
							team.setId("2");team.setName("乙");
						}else if("2".equals(workOrderBean.getTeamId())){//C.中-丙
							team.setId("3");team.setName("丙");
						}else if("3".equals(workOrderBean.getTeamId())){//A.中-甲
							team.setId("1");team.setName("甲");
						}*/
						sch.setMdTeam(team);
						sch.setMdShift(mdShift);
					}else if(i==2){//晚班
						String begin = DateUtil.dateAdd("d",j,beginDay,"yyyy-MM-dd");
						sch.setDate(DateUtil.strToDate(begin+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
						
						sch.setStim(DateUtil.strToDate(begin+" 23:00:00", "yyyy-MM-dd HH:mm:ss"));
						begin = DateUtil.dateAdd("d",(j+1),beginDay,"yyyy-MM-dd");
						sch.setEtim(DateUtil.strToDate(begin+" 07:00:00", "yyyy-MM-dd HH:mm:ss"));
						MdShift mdShift = new MdShift();
						MdTeam team =new MdTeam();
						mdShift.setId(workOrderBean.getShiftThreeId());
						mdShift.setName(workOrderBean.getShiftThreeName());
						team.setId(workOrderBean.getTeamThreeId());
						team.setName(workOrderBean.getTeamThreeName());
						/*mdShift.setId("3");mdShift.setName("晚班");
						if("1".equals(workOrderBean.getTeamId())){//B.晚-丙
							team.setId("3");team.setName("丙");
						}else if("2".equals(workOrderBean.getTeamId())){//B.晚-甲
							team.setId("1");team.setName("甲");
						}else if("3".equals(workOrderBean.getTeamId())){//A.晚-乙
							team.setId("2");team.setName("乙");
						}*/
						sch.setMdTeam(team);
						sch.setMdShift(mdShift);
					}
					sch.setDel(0L);
					schCalendarDao.save(sch);
				}
			}
		}
	
	/**
	 * 批量新增卷烟机、包装机 工单
	 * @author luther.zhang
	 * @create 2015-04-16
	 */
	@Override
	public void saveBatckWork() throws Exception{
		//假删除大于今天的工单
		//新增一个星期的工单和辅料
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,CODE from  SCH_WORKORDER WITH (NOLOCK) ");
		sql.append("where DATE>=? and BOM_VERSION=? and del=0 ");
		//String nowDay = DateUtil.getNowDateTime("yyyy-MM-dd")+" 00:00:00";
		String nowDay = DateUtil.dateAdd("d",1,new Date(),"yyyy-MM-dd")+" 00:00:00";
		List<Object> params = new ArrayList<Object>();
		params.add(nowDay);
		params.add("V9999");
		List<?> list = schWorkorderDao.queryBySql(sql.toString(), params);//.query(hql.toString(), params);
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] arr=(Object[]) list.get(i);
				String key = ObjectUtils.toString(arr[0]);//主键
				//辅料 真删除=====================
				StringBuffer del = new StringBuffer();
				del.append("delete from SCH_WORKORDER_BOM where oid=?  ");
				List<Object> delParams = new ArrayList<Object>();
				delParams.add(key);
				schWorkorderDao.updateInfo(del.toString(),delParams);
				//end
				try{
					StringBuffer updateSql = new StringBuffer();
					updateSql.append("update SCH_WORKORDER set del=1 where id=? ");
					List<Object> upParams = new ArrayList<Object>();
					upParams.add(key);
					schWorkorderDao.updateInfo(updateSql.toString(), upParams);
				}catch(Exception e){
					log.error("删除失败:"+e.getMessage());
				}
				try{
					/*StringBuffer delSql = new StringBuffer();
					delSql.append("delete from SCH_WORKORDER where id=? ");//尝试着真删除工单	
					List<Object> delteParams = new ArrayList<Object>();
					delteParams.add(key);
					schWorkorderDao.updateInfo(delSql.toString(), delteParams);*/
				}catch(Exception e){
					log.error("删除失败:"+e.getMessage());
				}
				Thread.sleep(5);
			}	
		}
		for(int i=1;i<8;i++){
			//1号卷烟机begin================================================================================
			String begin = DateUtil.dateAdd("d",i,new Date(),"yyyy-MM-dd");
			String dataTime  = begin+" 00:00:00";
			String code =DateUtil.dateAdd("d",i,new Date(),"yyMMdd")+"HTSR#012101";
			//新增卷烟机 早班工单和辅料  
			String startTime = begin+" 07:00:00";
			String endTime   = begin+" 15:00:00";
			this.saveJyjWork(code,UUID.randomUUID().toString(),"8af2d4904cb7545d014cb781f4aa0000",dataTime,startTime,endTime,"V9999");
			//新增卷烟机 中班工单 和辅料
			startTime = begin+" 15:00:00";
			endTime   = begin+" 23:00:00";
			this.saveJyjWork(code,UUID.randomUUID().toString(),"8af2d4904cb7545d014cb78837240008",dataTime,startTime,endTime,"V9999");
			//新增卷烟机 晚班工单和辅料
			startTime = begin+" 23:00:00";
			begin = DateUtil.dateAdd("d",(i+1),new Date(),"yyyy-MM-dd");//第二天了
			endTime   = begin+" 07:00:00";
			this.saveJyjWork(code,UUID.randomUUID().toString(),"8af2d4904cb7545d014cb789d0df0009",dataTime,startTime,endTime,"V9999");
			//卷烟机 end================================================================================
			
			//1号包装机================================================================================
			begin = DateUtil.dateAdd("d",i,new Date(),"yyyy-MM-dd");
			dataTime  = begin+" 00:00:00";
			code =DateUtil.dateAdd("d",i,new Date(),"yyMMdd")+"HTSR#012201";
			//新增包装机 早班工单和辅料  
			startTime = begin+" 07:00:00";
			endTime   = begin+" 15:00:00";
			this.saveJyjWork(code,UUID.randomUUID().toString(),"8af2d4904cb7545d014cb782f5ed0001",dataTime,startTime,endTime,"V9999");
			//新增包装机 中班工单 和辅料
			startTime = begin+" 15:00:00";
			endTime   = begin+" 23:00:00";
			this.saveJyjWork(code,UUID.randomUUID().toString(),"8af2d4904cb7545d014cb78aa565000a",dataTime,startTime,endTime,"V9999");
			//新增包装机 晚班工单和辅料
			startTime = begin+" 23:00:00";
			begin = DateUtil.dateAdd("d",(i+1),new Date(),"yyyy-MM-dd");//第二天了
			endTime   = begin+" 07:00:00";
			this.saveJyjWork(code,UUID.randomUUID().toString(),"8af2d4904cb7545d014cb78b7c4d000b",dataTime,startTime,endTime,"V9999");
			//包装机 end================================================================================
			Thread.sleep(5);
		}
	}

	public void saveJyjWork(String code, String key, String id, String date,
			String stim, String etim, String bomVersion) {
		String bth = code.replaceFirst("#","");
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into SCH_WORKORDER ");
		insertSql.append("select '").append(key).append("' as ID,");
		insertSql.append("'"+code+"' CODE,'").append(bth).append("' BTH,IS_NEW,TYPE,PROD_TYPE,SHIFT,TEAM,EQP,MAT,QTY,UNIT,'");
		insertSql.append(date).append("' DATE,'").append(stim).append("' STIM,'").append(etim)
		.append("' ETIM, '").append(bomVersion).append("' BOM_VERSION,'1'STS,'0' IS_CHECK, ");
		insertSql.append("null CHECK_TIME,null REAL_STIM,null REAL_ETIM,RUN_SEQ,RECV_TIME,ENABLE,DEL,'Y' as IS_AUTO ");
		insertSql.append("from SCH_WORKORDER  where DEL = 0 and id='").append(id).append("'  ");
		try{
			schWorkorderDao.updateInfo(insertSql.toString(),null);
		}catch(Exception e){
			log.error("新增数据失败1:"+e.getMessage());
		}
		//新增卷烟机 早班辅料
		insertSql = new StringBuffer();
		insertSql.append("insert into SCH_WORKORDER_BOM ");
		insertSql.append("select newid() as ID, ");
		insertSql.append("MAT,QTY,UNIT,'").append(key).append("' OID from SCH_WORKORDER_BOM where OID='").append(id).append("'  ");
		//List<Object> updatePrams = new ArrayList<Object>();
		try{
			schWorkorderDao.updateInfo(insertSql.toString(),null);
		}catch(Exception e){
			log.error("新增数据失败2:"+e.getMessage());
		}
	}
	/**
	 * 批量新增指定机台数据
	 * @author luther.zhang
	 * @create 2015-04-29
	 */
	public boolean saveBatckWork(WorkOrderBean workBean,ConStdBean[] flListBean,String date1,String date2) throws Exception{
		//1.查询换班时间 SHIFT是班次(早中晚)
		//2.假删除大于今天的所有工单 以及 辅料 
		//for循环begin  
			//新增工单，新增工单对应的辅料数据
		//end
		Long type = workBean.getType();
		String workshop = "";//1.卷烟机工单 2.包装机工单 3.封箱机工单  4.成型机工单 车间
		if(type<=3){
			workshop = "1";//1号车间
		}else{
			workshop = "2";//2号车间 现场成型机 在2号车间里
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,SHIFT,TEAM,STIM,ETIM,WORKSHOP from SCH_CALENDAR WITH (NOLOCK) ");
		sql.append("where date>=? and DEL=0 and WORKSHOP=? and date<=? ");
		//String nowDay = DateUtil.dateAdd("d",1,new Date(),"yyyy-MM-dd")+" 00:00:00";//如果是 5.10
		//String endDay = DateUtil.dateAdd("d",number,new Date(),"yyyy-MM-dd")+" 23:59:59";//如果是 5.10
		
		String nowDay = date1.trim()+" 00:00:00";//如果是 5.10
		String endDay = date2.trim()+" 23:59:59";//如果是 5.10
		
		
		
		List<Object> params = new ArrayList<Object>();
		params.add(nowDay);params.add(workshop);params.add(endDay);
		List<?> list = schWorkorderDao.queryBySql(sql.toString(), params);
		if(null!=list&&list.size()>0){
			//假删除所有工单     (修改成删除时间段内的工单)
			StringBuffer temp = new StringBuffer();
			temp.append("update SCH_WORKORDER set DEL=1 where DEL=0  and DATE>='"+nowDay
					+"' and DATE<= '"+endDay+"' and type='"+type+"' and EQP='"+workBean.getEquipmentId()+"' ");//and BOM_VERSION='V9999'
			schWorkorderDao.updateInfo(temp.toString(),null);
			//辅料 真删除=====================
			temp = new StringBuffer();
			temp.append("delete from SCH_WORKORDER_BOM where oid in (")
				.append("	select id  from SCH_WORKORDER where DEL=1  and DATE>='"+nowDay
							+"' and DATE<= '"+endDay+"' and type='"+type+"' and EQP='"+workBean.getEquipmentId()+"' ") //and BOM_VERSION='V9999'
				.append(")  ");
			schWorkorderDao.updateInfo(temp.toString(),null);
			try{
				temp = new StringBuffer();
				temp.append(" delete from SCH_WORKORDER where DEL=1 and DATE>='"+nowDay
						+"' and DATE<='"+endDay+"' and type='"+type+"' and EQP='"+workBean.getEquipmentId()+"'  ");//and BOM_VERSION='V9999'
				schWorkorderDao.updateInfo(temp.toString(),null);
			}catch(Exception e){
				log.error("尝试着真删除 报错:"+e.getMessage());
			}
			//根据辅料 取对应的牌号CODE
			String ph = "";
			if(null!=flListBean){
				for(int i=0;i<flListBean.length;i++){
					ConStdBean bean = flListBean[i];
					ph =bean.getMatProdCode();
					break;
				}
			}
			List<Combobox>  comboxList = BaseParams.getAllEqpsCombobox(false);
			for(int i=0;i<list.size();i++){
				Object[] arr=(Object[]) list.get(i);
				//String key = ObjectUtils.toString(arr[0]);//主键
				String shift= ObjectUtils.toString(arr[1]);//SHIFT是班次(早中晚)
				String team= ObjectUtils.toString(arr[2]);//班组 甲乙丙
				String stim= ObjectUtils.toString(arr[3]);//开始时间 yyyy-MM-dd HH:mm:ss
				String etim= ObjectUtils.toString(arr[4]);//结束时间 yyyy-MM-dd HH:mm:ss
				//String workShop= ObjectUtils.toString(arr[5]);//车间
				//150430 HTSR #01[滕烟] 2[车间]  2[包装机] 01[机台]
				Date startTime = DateUtil.strToDate(stim,"yyyy-MM-dd HH:mm:ss");
				String startStr = DateUtil.datetoStr(startTime, "yyMMdd");
				
				String code =startStr+ph+"#012"+type;
				try{
				for(Combobox box :comboxList){
					if(box.getId().equals(workBean.getEquipmentId())){
						int haoIndex= box.getName().indexOf("号");
						if(haoIndex>0){
							String strHao = box.getName().substring(0, haoIndex);
							if(null!=strHao&&!"".equals(strHao)){
								int intHao= Integer.parseInt(strHao.trim());
								if(intHao<10){
									code+= "0"+intHao;
								}else{
									code+= intHao+"";
								}
							}
							break;
						}
					}
				}
				}catch(Exception e){
					log.error("创建编号 出错...:"+e.getMessage());
				}
				String workId = UUID.randomUUID().toString();
				//新增工单
				temp = new StringBuffer();
				temp.append("insert into SCH_WORKORDER(")
				.append("ID,CODE,BTH,IS_NEW,TYPE,PROD_TYPE,SHIFT,TEAM,EQP,MAT,")
				.append("QTY,UNIT,DATE,STIM,ETIM,BOM_VERSION,STS,IS_CHECK,RUN_SEQ,ENABLE,")
				.append("DEL,IS_AUTO) values(")
				.append(" '"+workId+"','"+code+"','"+code.replaceAll("#", "")+"','1','"+type  //ID,CODE,BTH,IS_NEW,TYPE
						+"','1','"+shift+"','"+team+"','"+workBean.getEquipmentId()+"','"+workBean.getMatId()+"' ")
				.append(",'"+workBean.getQty()+"','"+workBean.getUnitId()
						+"','"+DateUtil.datetoStr(startTime, "yyyy-MM-dd")+"','"+stim+"','"+etim
						+"','V9999','1','0','1','1' ")
				.append(",'0','Y' ) ");
				try{
					schWorkorderDao.updateInfo(temp.toString(),null);
					//新增工单对应的辅料
					if(null!=flListBean){
						for(int j=0;j<flListBean.length;j++){
							ConStdBean bean = flListBean[j];
							temp = new StringBuffer();
							temp.append("insert into SCH_WORKORDER_BOM(")
							.append("ID,MAT,QTY,UNIT,OID")
							.append(") values('"+UUID.randomUUID().toString()+"','"+bean.getMat()+"','"+bean.getMatCount()
									+"','"+bean.getMatUnitId()+"','"+workId+"') ");
							schWorkorderDao.updateInfo(temp.toString(),null);
						}
					}
				}catch(Exception e){
					log.error("新增工单 报错:"+e.getMessage());
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	
	
	/**
	 * 开启定时器,在一个工单时间周期内，根据排班时间开启关闭工单
	 * @author luther.zhang
	 * @create 2015-04-16
	 */
	int passCount = 0;
	boolean flag=true;
	public void updateShiftData() throws Exception{
			//1.根据当前时间获取排班计划
			StringBuffer sql = new StringBuffer();
			sql.append("select ID,SHIFT,TEAM,DATE,STIM,ETIM,WORKSHOP from Sch_Calendar  WITH (NOLOCK)  ");
			sql.append("where (? between STIM and ETIM) and del=0 ");
			String beginDay = DateUtil.dateAdd("d",0,new Date(),"yyyy-MM-dd HH:mm:ss");//当前换班时间
			List<Object> params = new ArrayList<Object>();
			params.add(beginDay);
			List<?>  list= schWorkorderDao.queryBySql(sql.toString(), params);
			passCount++;
			if(passCount%10==0){
				passCount =0;
				//begin 这部分历史日志记录  待系统稳定后可以去掉
				String updaSql ="insert into MD_MANUAL_SHIFT_HIS values(?,?,?,?,?,?) ";
				List<Object> prams = new ArrayList<Object>();
				prams.add(UUID.randomUUID().toString());//主键ID
				prams.add(beginDay);//当前时间
				prams.add("");//当前产量
				prams.add(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//新增当前时间
				prams.add("");
				prams.add("");
				try{
					wctManualShiftService.updateInfo(updaSql, prams);
				}catch(Exception e){
					log.error("WorkOrderServiceImpl->updateShiftData:"+e.getMessage());
				}
			}
			//end
			SchCalendarBean scb=null;
			if(null!=list&&list.size()>0){
				for(int k=0;k<list.size();k++){
					Object[] arr0=(Object[]) list.get(k);
					SchCalendarBean bean = new SchCalendarBean();
					bean.setId(ObjectUtils.toString(arr0[0]));
					bean.setMdShiftCode(ObjectUtils.toString(arr0[1]));//班次 早 中 晚  ...
					bean.setMdTeamCode(ObjectUtils.toString(arr0[2]));//班组 甲乙丙丁
					bean.setDate(ObjectUtils.toString(arr0[3]));//新增时间
					bean.setStim(ObjectUtils.toString(arr0[4]));//计划开始时间
					bean.setEtim(ObjectUtils.toString(arr0[5]));//计划结束时间
					bean.setMdWorkshopCode(ObjectUtils.toString(arr0[6]));//车间
					
					//第一次启动初始化班次，并保存起来。下面每次开始换班，更新初始化内容 参考= 10004行每次换班，更新初始化内容 wch
					scb= NeedData.getInstance().getScbean();
					if(scb==null){
					   NeedData.getInstance().setScbean(bean);
					}

					 //1.根据 排版时刻表 获取本班工单
					 //2.关工单:状态为  2 运行状态，自动 标识为 IS_AUTO='Y' 
					 //开工单时间
					 String startTime = bean.getStim();//时间 往后 10分钟
					 Date startDate = DateUtil.strToDate(startTime, "yyyy-MM-dd HH:mm:ss");
					 String beginDateStr = DateUtil.dateAdd("f",2,startDate,"yyyy-MM-dd HH:mm:ss"); //当前换班时间
					 //结束工单时间
					 String endTime = bean.getEtim();//整点 
					 Date endDate = DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss");
					 String endDateStr = DateUtil.dateAdd("f",-2,endDate,"yyyy-MM-dd HH:mm:ss");//提前10分钟
					 
					 String mdShiftCode   = bean.getMdShiftCode();//班次 早 中 晚  ...
					 String mdTeamCode    = bean.getMdTeamCode();//班组 甲乙丙丁
					 String workshopCode  = bean.getMdWorkshopCode();//车间
					
					 //查看当前时间 在 开启时间段 或 关闭时间段
					 String nowTime = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
				 	
					 long date0 = DateUtil.getTime(nowTime,"yyyy-MM-dd HH:mm:ss");
					 //15:00-15:10
					 long date1 = DateUtil.getTime(startTime,"yyyy-MM-dd HH:mm:ss");
					 long date2 = DateUtil.getTime(beginDateStr,"yyyy-MM-dd HH:mm:ss");
					 //22:50-23:00
					 long date3 = DateUtil.getTime(endDateStr,"yyyy-MM-dd HH:mm:ss");
					 long date4 = DateUtil.getTime(endTime,"yyyy-MM-dd HH:mm:ss");
					
					 String isStart = "";
					 if(date0>=date1&&date0<date2){
						 isStart ="START";
					 }else if(date0>=date3&&date0<date4){
						 isStart ="END";
					 }else{
						 isStart = "";
					 }
					 if("END".equals(isStart)){
						
						//关工单:状态为  2 运行状态，自动 标识为 IS_AUTO='Y' 
						params = new ArrayList<Object>();
						List<?> overList = this.getSchWorkorderList("2","1","Y",mdShiftCode, mdTeamCode,
								workshopCode, "END1", endDateStr, endTime, params,"");
						 if(null!=overList&&overList.size()>0){
							 for(int i=0;i<overList.size();i++){
								 Object[] arr = (Object[]) overList.get(i);
								 String eqpCode =ObjectUtils.toString(arr[1]);
								 String workType =ObjectUtils.toString(arr[3]);;//TYPE
								 String workShop="1";
								 if("4".equals(workType)){
									 workShop="2";
								 }
								 String proWorkId = ObjectUtils.toString(arr[2]);//工单ID
								 String shiftCode = ObjectUtils.toString(arr[13]);//班次
								 String teamCode = ObjectUtils.toString(arr[14]);//班组
								 String type ="A-E";
								 try{
									 //工单完成,更新工单结束时间
									 wctWorkOrderService.editWorkOrderStatus(proWorkId,Long.parseLong("4"), null);
									 //关闭工单
									 wctCalendarService.editProdWork(eqpCode, workShop, "adminId",
											 proWorkId, teamCode, shiftCode, type, "adminName",
											 workShop);
								 }catch(Exception e){
									 log.error("ERROR:1.根据排班时间关闭工单 出错；路径：WorkOrderServiceImpl->updateShiftData:"
												+ e.getMessage());
								 }
							 }
						 }
						 //关闭 手动的工单 如果手动的工单运行了 16小时
						 params = new ArrayList<Object>();
						//结束工单时间
						 endTime = bean.getEtim();//整点 
						 endDate = DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss");
						 endDateStr = DateUtil.dateAdd("h",-16,endDate,"yyyy-MM-dd HH:mm:ss");//提前16个小时的关闭
						 
						 List<?> over2List = this.getSchWorkorderList("2","1","",mdShiftCode, mdTeamCode,
								workshopCode, "END2", endDateStr, endTime, params,"");
						 if(null!=over2List&&over2List.size()>0){
							 for(int i=0;i<over2List.size();i++){
								 Object[] arr = (Object[]) over2List.get(i);
								 String eqpCode =ObjectUtils.toString(arr[1]);
								 String workType =ObjectUtils.toString(arr[3]);;//TYPE
								 String workShop="1";
								 if("4".equals(workType)){
									 workShop="2";
								 }
								 String proWorkId = ObjectUtils.toString(arr[2]);//工单ID
								 String shiftCode = ObjectUtils.toString(arr[13]);//班次
								 String teamCode = ObjectUtils.toString(arr[14]);//班组
								 String type ="A-E";
								 try{
									 //工单完成,更新工单结束时间
									 wctWorkOrderService.editWorkOrderStatus(proWorkId,Long.parseLong("4"), null);
									 //结束工单
									 wctCalendarService.editProdWork(eqpCode, workShop, "adminId",
											 proWorkId, teamCode, shiftCode, type, "adminName",
											 workShop);
								 }catch(Exception e){
									 log.error("ERROR:2.根据排班时间关闭工单 出错；路径：WorkOrderServiceImpl->updateShiftData:"
												+ e.getMessage());
								 }
							 }
						 }
						 
						//换班结束，实时保存提升机产量
						saveTSData(bean); 
						 /**
						 * [功能说明]：
						 * TSPM接口    
						 * 1）查询上班次所有已完成的工单
						 * 2）通过工单查询所有关联的设备故障信息
						 * 3）封装成XML，发送给TSPM
						 * @date  2016年11月25日7:11:06
						 * @author wanchanghuang
						 * */
						 if(flag){
							 flag=false;
							 wctWorkOrderService.AMSSendTroubleMessageTOTSPM(bean);
						 }
					 }else if("START".equals(isStart)){
						//每次启动工单，对上一班的（提升机产量）清空，wch
						NeedData.getInstance().setMapShiftDatas(null);
						//每次换班，更新换班信息（如：班次） wch
						NeedData.getInstance().setScbean(bean);
						if(!flag){
							flag=true;
						}
						
						//开启 符合条件的所有工单 ,DAC 标志位为0 的不处理,包装机的 可以忽略
						 params = new ArrayList<Object>();
						//是否有运行的工单(托班的情况下是有 运行的情况的)， 如果是托班的情况 则不开启该机台工单
						 List<?> startList = this.getSchWorkorderList("1","1","Y",mdShiftCode, mdTeamCode,
						 workshopCode, "START", startTime,beginDateStr , params,"Y");
						 if(null!=startList&&startList.size()>0){
							 for(int i=0;i<startList.size();i++){
								 Object[] arr = (Object[]) startList.get(i);
								 String eqpCode =ObjectUtils.toString(arr[1]);
								 String workType =ObjectUtils.toString(arr[3]);;//TYPE
								 String eqpId =ObjectUtils.toString(arr[4]);//卷烟机 还是 包装机 ...
								 String workShop="1";
								 if("4".equals(workType)){
									 workShop="2";
								 }
								 String proWorkId = ObjectUtils.toString(arr[2]);//工单ID
								 String shiftCode = ObjectUtils.toString(arr[13]);//班次
								 String teamCode = ObjectUtils.toString(arr[14]);//班组
								 String type ="A-S";
								 //判断本机台有无工单仍在运行
								 //根据当前  设备  查询DAC标识 1.如果为 1 就开启工单 2.如果是 运行状态-1
								 params = new ArrayList<Object>();
								 String dacSql =" select ID,DASPROCESS,PRO_WORK_ID from MD_MANUAL_SHIFT WITH (NOLOCK) where EQPID=? ";
								 params.add(eqpCode);
								 List<?>  dacList = schWorkorderDao.queryBySql(dacSql, params);
								 if(null!=dacList&&dacList.size()>0){
									 Object[] dacArr = (Object[]) dacList.get(0);
									 String dasStuts = (ObjectUtils.toString(dacArr[1]));
									 if("1".equals(dasStuts)){//DAC清理完毕
										 try{
											 //更新状态 添加 辅料数据
											 wctWorkOrderService.editWorkOrderStatus(proWorkId,Long.parseLong("2"), null);
											 //开启工单
											 wctCalendarService.editProdWork(eqpCode, workShop, "adminId",
													 proWorkId, teamCode, shiftCode, type, "adminName",
													 workShop);
										 }catch(Exception e){
											log.error("ERROR:根据排班时间开启工单 出错；路径：WorkOrderServiceImpl->updateShiftData:"
													+ e.getMessage());
										 }
									 }else if("0".equals(dasStuts)){//运行的工单 为 0  包装机的 就不要管了
										 List<Combobox> rollers =  BaseParams.getAllRollersCombobox(false);//所有卷烟机
										 List<String> jyjIds = new ArrayList<String>();
										 for(Combobox comBean: rollers){
											 jyjIds.add(comBean.getId()); //所有卷烟机ID
										 }
										 if(jyjIds.contains(eqpId)){//如果当前记录是 卷烟机
											 try{
												 //更新状态 添加 辅料数据
												 wctWorkOrderService.editWorkOrderStatus(proWorkId,Long.parseLong("2"), null);
												 //开启工单
												 wctCalendarService.editProdWork(eqpCode, workShop, "adminId",
														 proWorkId, teamCode, shiftCode, type, "adminName",
														 workShop);
											 }catch(Exception e){
												log.error("ERROR:根据排班时间开启工单 出错；路径：WorkOrderServiceImpl->updateShiftData:"
														+ e.getMessage());
											 }
										 }
									 }else if("-1".equals(dasStuts)){//手动运行的工单
										 
									 }
								 }else{
									 try{
										 //新增工单运行情况
										 wctCalendarService.editProdWork(eqpCode, workShop, "adminId",
												 proWorkId, teamCode, shiftCode, "A-S", "adminName",
											 workShop);
									 }catch(Exception e){
											log.error("ERROR:根据排班时间新增 初始化工单配置  出错；路径：WorkOrderServiceImpl->updateShiftData:"
													+ e.getMessage());
									 }
							 }
						 }
					 }
				 }
			}
		}
	}
	/**
	 * 根据条件查询 工单
	 * @param sts			工单状态：2运行工单，1.下发工单
	 * @param isAuto		是否 自动模式 （Y or null）;N 表示 拖班 手动模式
	 * @param isCheck		1:审核通过 0  审核未通过
	 * @param mdShiftCode	班次
	 * @param mdTeamCode	班组
	 * @param workshopCode	工作车间
	 * @param workType		开启 or  关闭 工单 
	 * @param date1			前后结束时间 
	 * @param date2			结束时间
	 * @param params		条件集合
	 * @param qcEqp			去除不开启工单的机台
	 * @return
	 */
	public List<?> getSchWorkorderList(String sts,String isCheck,String isAuto,String mdShiftCode, String mdTeamCode,
			String workshopCode, String workType,String date1, String date2,
			List<Object> params,String qcEqp) {
		 StringBuffer sql = new StringBuffer();
		 sql.append("select m.EQUIPMENT_NAME, m.EQUIPMENT_CODE ,s.id,s.TYPE,s.EQP,s.mat,");//5个
		 sql.append("s.date,s.STIM,s.ETIM,s.sts,s.IS_CHECK,s.RUN_SEQ,s.IS_AUTO,s.SHIFT,s.TEAM ");//5+9=14
		 sql.append(" from SCH_WORKORDER  s WITH (NOLOCK) left join MD_EQUIPMENT m  WITH (NOLOCK) on s.EQP=m.id ");
		 sql.append(" where s.DEL=0 ");
		 sql.append(" and s.STS=? and s.IS_CHECK=? ");
		 params.add(sts);//运行工单
		 params.add(isCheck);//审核通过
		 if("START".equals(workType)){
			 sql.append(" and (s.IS_AUTO=? or s.IS_AUTO is null)  ");
			 sql.append(" and s.SHIFT=? and s.TEAM=?  ");
			 params.add(isAuto);//是否托班 标识 
			 params.add(mdShiftCode);//班次
			 params.add(mdTeamCode);//班组 
			 sql.append(" and s.STIM>=? and s.STIM<=?  ");
			 params.add(date1);//开始时间  //2015-04-18 15:00:00.0
			 params.add(date2);//结束时间 //2015-04-18 15:10:00
			 if("Y".equals(qcEqp)){//有拖班的工单，不开启该机台的下一个工单
				sql.append(" and s.EQP not in (select distinct s.EQP from  SCH_WORKORDER  s WITH (NOLOCK) ")
				.append(" where s.DEL=0 and IS_CHECK=1 and STS=1 and IS_AUTO='N' ) ");//审核通过，运行的+ 手动模式工单
			 }
		 }else if("END1".equals(workType)){
			 sql.append(" and (s.IS_AUTO=? or s.IS_AUTO is null)  ");
			 sql.append(" and s.SHIFT=? and s.TEAM=?  ");
			 params.add(isAuto);//是否托班 标识 
			 params.add(mdShiftCode);//班次
			 params.add(mdTeamCode);//班组 
			 sql.append(" and s.ETIM>=? and s.ETIM<=?  ");
			 params.add(date1);//开始时间2015-04-18 22:50:00
			 params.add(date2);//结束时间2015-04-18 23:00:00.0
		 }else if("END2".equals(workType)){
			 sql.append(" and s.REAL_STIM<=? ");
			 params.add(date1);//开始时间 2015-04-18 06:00:00
		 }
		 List<?>  list = schWorkorderDao.queryBySql(sql.toString(), params);
		 return list;
	}
	
	
	
	/**
	 * 查询上一班次采集数据详细
	 * @createTime 2015年10月26日08:35:09
	 * @author wanchanghuang
	 * */
	@Override
	public Map<Integer,List<ChangeShiftDatas>> getMapChangeShiftDates(ChangeShiftDatas csd) {
		StringBuffer sbf=new StringBuffer();
		sbf.append(" select c.equipment_code,sum(b.qty)from SCH_WORKORDER a,SCH_STAT_OUTPUT b,MD_EQUIPMENT c where a.id=b.oid and a.eqp=c.id and a.sts='4' ");
        //获得班次
		Integer shift=csd.getShift();
		//dac采集   0=早班   1=中班   2=晚班    ->  数据库   1=早班   2=中班   3=晚班
		if(shift==2){ //=1，查询早班
			sbf.append(" and a.shift in ('"+(shift-1)+"')");
		}else if(shift==3){ //=2 ，查询早班+中班
			sbf.append(" and a.shift in ('"+(shift-2)+"','"+(shift-1)+"')");
		}
		//时间
		sbf.append(" and convert(varchar(24),a.date,23)='"+csd.getDate()+"'");
		//设备表code
		sbf.append(" group by c.equipment_code ");
		List<?>  list = schWorkorderDao.queryBySql(sbf.toString());
		ChangeShiftDatas  shiftDataf=null;
		List<ChangeShiftDatas> listShiftDatas=new ArrayList<ChangeShiftDatas>();
		Map<Integer,List<ChangeShiftDatas>> map= new HashMap<Integer, List<ChangeShiftDatas>>();
		//将object转换成bean
		if(list.size()>0){
			for(Object o:list){
				shiftDataf=new ChangeShiftDatas();
				Object[] temp=(Object[]) o;
				shiftDataf.setEquipment_code(temp[0].toString());
				shiftDataf.setQty(Double.parseDouble(temp[1].toString()));
				listShiftDatas.add(shiftDataf);
			}
		}
		map.put(shift, listShiftDatas);
		return map;
	}
	
	
	/**
	 * 【功能描述】：结束本班次，实时 保存提升机产量
	 * @author wanchanghuang
	 * @param bean 
	 * @createTime 2015年11月27日16:45:48 
	 * 1.获得数据库包装机CODE
	 * 2.通过code-30 得到id
	 * 3.通过id得到缓存中提升机的产量且判断产量是否为空；
	 * 4.保存不为空的产量到相关工单
	 * */
	public void saveTSData(SchCalendarBean bean){
		try {
			Double TSQty=0D;
			EquipmentData TSData = NeedData.getInstance().getEquipmentData(SysEqpTypeBase.TYTS_CODE);
			if(TSData!=null){
				//获得包装机code
				Map<String,String> map=  statService.queryByEquiment();
				if(map.size()>0){
					for (Map.Entry entry : map.entrySet()) {  
				        Object key = entry.getKey();
				        Integer k_val=Integer.parseInt(key.toString())-30;
				        TSQty=GetValueUtil.getDoubleValue( TSData.getVal(k_val.toString()) );
				        if(TSQty>0){
				        	//如果提升机产量>0，更新工单产量
				        	statService.supdateSchStatInputOutById(bean, MathUtil.roundHalfUp(TSQty/250.0,3), map.get(key) );
				        }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除工单
	 * shisihai
	 */
	@Override
	public void deleteWorkOrders(String ids,String eqpName,String team,String date,String mat,SessionInfo info) {
			this.deleteWorkOrderData(ids,eqpName,team,date,mat,info);
	}
	/**
	 * 删除工单和辅料数据
	 * @param id
	 */
	private void deleteWorkOrderData(String id,String eqpName,String team,String date,String mat,SessionInfo info){
		
		try {
			//删除辅料表数据
			String sql="DELETE from SCH_WORKORDER_BOM where OID='"+id+"'";
			schWorkorderDao.updateBySql(sql, null);
			//删除工单表
			sql="DELETE from SCH_WORKORDER where id='"+id+"'";
			schWorkorderDao.updateBySql(sql, null);
			//删除产量数据表
			sql="DELETE from SCH_STAT_OUTPUT where oid='"+id+"'";
			schWorkorderDao.updateBySql(sql, null);
			SysLog log=new SysLog();
			log.setSys("PMS");
			log.setName(info.getUser().getName());//操作人
			log.setIp(info.getIp());
			log.setOptname("删除工单以及与此工单相关的产量/辅料数据操作");//操作名或路径
			log.setParams("删除的工单id:"+id+"机台："+eqpName+"班组："+team+"牌号："+mat+"工单时间："+date);//参数
			log.setSuccess("操作成功");
			log.setDate(new Date());
			log.setDel(0L);
			logdao.save(log);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 批量删除工单
	 * @param ids
	 * @param eqpName
	 * @param team
	 * @param date
	 * @param mat
	 * @param info
	 */
	@Override
	public void deleteBeatchWorkOrders(String ids, String eqpName, String team, String date, String mat,
			SessionInfo info) {
		try {
			String[] id=ids.split(",");
			String[] ens=eqpName.split(",");
			String[] ts=team.split(",");
			String[] ds=date.split(",");
			String[] ms=mat.split(",");
			for (int i = 0; i < id.length; i++) {
				this.deleteWorkOrderData(id[i],ens[i],ts[i],ds[i],ms[i],info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 【功能说明】：MES工单撤销
	 * @author wanchanghuang
	 * @createTime 2016年1月13日10:36:22
	 * @state 1-审核完成状态
	 * */
	@Override
	public boolean updateTntryCancel(SchWorkorder pxd) {
		StringBuffer sbf=new StringBuffer();
		sbf.append("update SCH_WORKORDER set sts='"+pxd.getSts()+"' ,del='1',enable='0' where erp_code='"+pxd.getCode()+"' ");
		schWorkorderDao.updateInfo(sbf.toString(), null);
		return true;
	}
	/**
	 * 【功能说明】：工单信号
	 * @author wch
	 * @return 
	 * @createTime 2015年12月18日09:07:30
	 * 步骤：1）通过工单号查询，是否存在，存在再继续修改开始时间，结束时间，
	 *       工单状态为运行状态
	 * */
	@Override
	public boolean deleteAndCancelWS(ParsingXmlDataBean pxd) {
		StringBuffer sbf = new StringBuffer();
		SysLog slog=new SysLog();
		slog.setName("MES接口");
		try {
			slog.setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			// TODO: handle exception
		}
		slog.setSys("MES");
		sbf.append(" select id from SCH_WORKORDER where code='"+pxd.getWo_id()+"' and sts<>2 and del=0 and eqp='"+pxd.getEqu_id()+"'  and erp_code='"+pxd.getLot_id()+"' ");
		List<?> lt=schWorkorderDao.queryBySql(sbf.toString());
		if(lt.size()>0 && lt!=null){
			sbf.setLength(0); //清空
			sbf.append(" update SCH_WORKORDER set stim='"+pxd.getStartTime()+"' ,etim='"+pxd.getEndTime()+"' and sts=2 where eqp='"+pxd.getEqu_id()+"' and code='"+pxd.getWo_id()+"' and bth='"+pxd.getMod_id()+"'  and erp_code='"+pxd.getLot_id()+"' ");
		}
		slog.setSuccess("操作成功");
		slog.setDate(new Date());
		slog.setDel(0l);
		sysLogDaoImpl.saveBackKey(slog);
		return true;
	}
	
	/**
	 * [功能说明]：质量接口-基础表QUALITY_INSPECTION
	 * @author wanchanghuang
	 * @crateTime 2016年1月12日16:40:37
	 * 
	 * */
	@Override
	public QualityCheckInfo saveQualityInfo(QualityCheckInfo qci) {
		try {
			qci=infoDao.saveAndReturn(qci);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return qci;
	}
	/**
	 * [功能说明]：质量接口-基础详细QUALITY_INSPECTION_PARAM
	 * @author wanchanghuang
	 * @crateTime 2016年1月12日16:40:37
	 * 
	 * */
	@Override
	public void saveQualityParamInfo(QualityCheckInfoParams qcip) {
		InfoParamsDao.save(qcip);
	}
	
	/**
	 * [功能描述]：接口解析完成，调用日志
	 * @author wanchanghuang
	 * @createTime 2016年1月12日17:36:35
	 * */
	@Override
	public void saveAndReturn(SysMessageQueue log) {
		try {
			SysMessageQueueDao.saveAndReturn(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * [功能描述]：接口解析,保存工厂日历
	 * @author wanchanghuang
	 * @createTime 2016年1月13日09:37:12
	 * */
	@Override
	public void saveSchCalendar(SchCalendar scr1) {
		try {
			schCalendarDao.deleteByParams("delete from SchCalendar o where o.mdShift.id=?  and o.date=?  and o.mdWorkshop.id=? ",
				scr1.getMdShift().getId(),scr1.getDate(),scr1.getMdWorkshop().getId()
			);//删除  
		} catch (Exception e) {}
		//保存
		schCalendarDao.save(scr1);
	}
	
	/**
	 * [功能描述]：接口解析,保存工单下发主数据
	 * @author wanchanghuang
	 * @createTime 2016年1月13日09:37:12
	 * */
	@Override
	public SchWorkorder addSchWorkOrder(SchWorkorder swd) {
		try {
			SchWorkorder swd2=schWorkorderDao.saveAndReturn(swd);
			return swd2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * [功能描述]：接口解析,保存工单下发辅料数据
	 * @author wanchanghuang
	 * @createTime 2016年1月13日09:37:12
	 * */
	@Override
	public SchWorkorderBom saveSchWorkOrderBom(SchWorkorderBom swb) {
		if(querySchConStdByMClass(swb)){ //过滤物料重复问题
			swb.setDel(1); 
		}
		schWorkorderBomDao.save(swb);
		return swb;
	}
	
	public boolean querySchConStdByMClass(SchWorkorderBom swb) {
		try {
			StringBuffer sbf=new StringBuffer(100);
			sbf.append("select id from SCH_WORKORDER_BOM  where oid='"+swb.getSchWorkorder().getId()+"' and  material_class='"+swb.getMaterialClass()+"' and del='"+swb.getDel()+"' and qty='"+swb.getQty()+"' ");
			List<?> list=schWorkorderBomDao.queryBySql(sbf.toString());
			if(list.size()>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	/**
	 * [功能描述]：查询md_mat_type表数据,存在返回ID，不存在插入返回主键ID
	 * @param mes_code:mes传过来的物料组ID
	 * */
	@Override
	public String queryRetByCode(MdMatType mmt) {
		StringBuffer sbf=new StringBuffer();
		sbf.append("select id from MD_MAT_TYPE where mes_code='"+mmt.getMesCode()+"'");
		List<?> list=schWorkorderBomDao.queryBySql(sbf.toString());
		if(list.size()>0){
			Object temp=list.get(0);
			return StringUtil.convertObjToString(temp);
		}else{
			//t=mdMatTypeDao.saveBackKey(mmt);
			try {
				mmt.setCode(mmt.getMesCode());
				mmt=mdMatTypeDao.saveAndReturn(mmt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mmt.getId();
		}
	}
	
	/**
	 * [功能说明]：插入md_mat表数据，且验证是否重复插入  code-为MES的物料号，唯一的
	 * @author   wanchanghuang
	 * @createTime  2016年2月25日13:14:45
	 * */
	@Override
	public void saveMdMatByCode(MdMat mm) {
		StringBuffer sbf=new StringBuffer();
		sbf.append("select id from MD_MAT where code='"+mm.getId()+"'");
		List<?> list=schWorkorderBomDao.queryBySql(sbf.toString());
		if(list.size()<=0){
			mdMatDao.save(mm);
		}
	}
	
	//
	@Override
	public void saveSchConStd(SchConStd scs) {
		try {
			//通过查询，过滤重复
			Object obj = schConStdDaoI.unique("from SchConStd o where o.del=0 and o.mdMatByProd.id=? and o.mdMatByMat.id=? and o.oid=? ", scs.getMdMatByProd().getId(),scs.getMdMatByMat().getId(),scs.getOid());
			if(obj==null){
				schConStdDaoI.save(scs);
			}
			//schConStdDaoI.deleteByParams("delete from SchConStd o where o.del=0 and o.id=? ", scs.getOid());
			//schConStdDaoI.save(scs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public QmChangeShift saveQmChangeShift(QmChangeShift qcs) {
		try {
			return qmChangeShiftDaoI.saveAndReturn(qcs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public void saveQmChangeShiftInfo(QmChangeShiftInfo qcsInfo) {
		qmChangeShiftInfoDaoI.save(qcsInfo);
	}

}
