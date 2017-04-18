package com.shlanbao.tzsc.pms.sch.stat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmipmentsDaoI;
import com.shlanbao.tzsc.base.dao.MdUnitDaoI;
import com.shlanbao.tzsc.base.dao.SchStatFaultDaoI;
import com.shlanbao.tzsc.base.dao.SchStatInputDaoI;
import com.shlanbao.tzsc.base.dao.SchStatOutputDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderBomDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.dao.StatShiftFLInfoDaoI;
import com.shlanbao.tzsc.base.interceptor.WorkOrderFeedbackInterceptor;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.mapping.SchStatFault;
import com.shlanbao.tzsc.base.mapping.SchStatInput;
import com.shlanbao.tzsc.base.mapping.SchStatOutput;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.mapping.SchWorkorderBom;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataHandler;
import com.shlanbao.tzsc.data.runtime.handler.DbOutputOrInputInfos;
import com.shlanbao.tzsc.data.runtime.handler.FaultDataCalc;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.pms.md.mat.beans.MatUnitBean;
import com.shlanbao.tzsc.pms.md.unit.beans.UnitBean;
import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.RoutputBean;
import com.shlanbao.tzsc.pms.sch.stat.service.StatServiceI;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.excel.ExportExcel;
import com.shlanbao.tzsc.utils.params.EquipmentTypeDefinition;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;
import com.shlanbao.tzsc.wct.isp.boxer.beans.BoxerData;
import com.shlanbao.tzsc.wct.isp.boxer.service.BoxerIspServiceI;
import com.shlanbao.tzsc.wct.isp.filter.beans.FilterData;
import com.shlanbao.tzsc.wct.isp.filter.service.FilterIspServiceI;
import com.shlanbao.tzsc.wct.isp.packer.beans.PackerData;
import com.shlanbao.tzsc.wct.isp.packer.service.PackerIspServiceI;
import com.shlanbao.tzsc.wct.isp.roller.beans.RollerData;
import com.shlanbao.tzsc.wct.isp.roller.service.RollerIspServiceI;
@Service
public class StatServiceImpl extends BaseService implements StatServiceI{
	@Autowired
	private SchStatInputDaoI schStatInputDao;
	@Autowired
	private SchStatOutputDaoI schStatOutputDao;
	@Autowired
	private SchWorkorderBomDaoI schWorkorderBomDao;
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Autowired
	private MdUnitDaoI mdUnitDao;
	@Autowired
	private RollerIspServiceI rollerIspService;
	@Autowired
	private PackerIspServiceI packerIspService;
	@Autowired
	private BoxerIspServiceI boxerIspService;
	@Autowired
	private FilterIspServiceI filterIspService;
	@Autowired
	private WorkOrderServiceI workOrderService;
	@Autowired
	private SchStatFaultDaoI SchStatFaultDao;
	@Autowired 
	private StatShiftFLInfoDaoI  statShiftFLInfoDao; //交接班
	@Autowired
	private EqmipmentsDaoI eqmipmentsDao;//设备具体机台信息
	@Autowired
	private BaseDaoI<Object> basedao;
	
	private StringBuffer sqlbf=new StringBuffer(1000);
	
	@Override
	public DataGrid getAllOutputs(OutputBean outputBean, PageParams pageParams) {
		/*String hql = "from SchStatOutput o left join fetch o.schWorkorder sw left join fetch sw.mdEquipment me left join fetch me.mdEqpType et left join fetch sw.mdMat mm left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch o.mdUnit mu left join fetch o.timeUnit tu where o.del='0' and et.id in('4028998449bcb4b10149bcb558780002','4028998449bcb4b10149bcb56b6e0003','4028998449bcdd9f0149bcdef25d0001','8af2d4904de08ff1014df4fbec1a0149','8af2d4904de08ff1014df526e189014e')";
		*/
		String hql = "from SchStatOutput o left join fetch o.schWorkorder sw left join fetch sw.mdEquipment me left join fetch me.mdEqpType et left join fetch sw.mdMat mm left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch o.mdUnit mu left join fetch o.timeUnit tu where o.del='0'  and   sw.type<>4  and sw.sts='4' ";
		
		String params="";
		
		if(StringUtil.notNull(outputBean.getWorkShop())){
			/* 定义
			  code  name
			  1		卷包车间
			  2		成型车间
			  3		动力车间
			  4		制丝车间
			*/
			params = params.concat(" and me.mdWorkshop.code='"+outputBean.getWorkShop()+"'");
		}
		
		if(StringUtil.notNull(outputBean.getShift())){
			params = params.concat(" and ms.id='"+outputBean.getShift()+"'");
		}
		if(StringUtil.notNull(outputBean.getTeam())){
			params = params.concat(" and mt.id='"+outputBean.getTeam()+"'");
		}
		if(outputBean.getIsFeedback() != null){
			params = params.concat(" and o.isFeedback="+outputBean.getIsFeedback());
		}
		if(StringUtil.notNull(outputBean.getDate())){
			params = params.concat(" and sw.date >= '"+outputBean.getDate()+"'");
		}
		if(StringUtil.notNull(outputBean.getDate2())){
			params = params.concat(" and sw.date <= '"+outputBean.getDate2()+"'");
		}
		if(StringUtil.notNull(outputBean.getEquipment())){
			params=params.concat(" and sw.mdEquipment.id='"+outputBean.getEquipment()+"'");
		}
		if(StringUtil.notNull(outputBean.getEqpType())){
			params=params.concat(" and sw.type="+outputBean.getEqpType());
		}
		
		List<SchStatOutput> rows=schStatOutputDao.queryByPage(hql.concat(params), pageParams);
		
		
		List<OutputBean> outputBeans = new ArrayList<OutputBean>();
		
		OutputBean bean = null;
		try {
			
			for (SchStatOutput output : rows) {
				
				bean = BeanConvertor.copyProperties(output, OutputBean.class);
				
				
				SchWorkorder workorder = output.getSchWorkorder();
				
				bean.setDate(DateUtil.formatDateToString(workorder.getDate(), "yyyy-MM-dd"));
				
				bean.setShift(workorder.getMdShift().getName());
				
				bean.setTeam(workorder.getMdTeam().getName());
				
				bean.setEquipment(workorder.getMdEquipment().getEquipmentName());
				bean.setQty(bean.getQty());
				bean.setBadQty(MathUtil.roundHalfUp(bean.getBadQty(),4));
				/*//换算产量剔除
				if(Integer.valueOf(workorder.getMdEquipment().getEquipmentCode())<31){
					//卷烟机
					//bean.setQty(MathUtil.roundHalfUp(bean.getQty()/50,2));
					bean.setBadQty(MathUtil.roundHalfUp(bean.getBadQty()/50, 2));
				}else if(Integer.valueOf(workorder.getMdEquipment().getEquipmentCode())<61){
					//包装机
					//bean.setQty(MathUtil.roundHalfUp(bean.getQty()/2500,2));
					bean.setBadQty(MathUtil.roundHalfUp(bean.getBadQty()/2500, 2));
				}*/
				
				bean.setWorkorder(workorder.getId());
				
				bean.setWorkorderCode(workorder.getCode());
				
				bean.setMatProd(workorder.getMdMat().getName()==null?"":workorder.getMdMat().getName());
				
				bean.setUnit(output.getMdUnit().getName());
				
				bean.setTimeunit(output.getTimeUnit().getName());
				
				outputBeans.add(bean);
				
				bean = null;
			}
			
			hql= "select count(o) ".concat(hql.replace("fetch", ""));
			
			long total=schStatOutputDao.queryTotal(hql.concat(params));
			
			return new DataGrid(outputBeans, total);
			
		} catch (Exception e) {
			
			log.error("POVO转换异常", e);
			
		}
		
		return null;
	}
	/**
	 * 查询成型生产实绩
	 * @author 张璐
	 * @create 2015年10月12日
	 */
	@Override
	public DataGrid getAllForming(OutputBean outputBean, PageParams pageParams) {
		/*String hql = "from SchStatOutput o left join fetch o.schWorkorder sw left join fetch sw.mdEquipment me left join fetch me.mdEqpType et left join fetch sw.mdMat mm left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch o.mdUnit mu left join fetch o.timeUnit tu where o.del='0' and et.id in('8af2d4474c54dd20014c54df22640000','8af2d4474c54dd20014c54df587c0001')";
		*/
		String hql = "from SchStatOutput o left join fetch o.schWorkorder sw left join fetch sw.mdEquipment me left join fetch me.mdEqpType et left join fetch sw.mdMat mm left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch o.mdUnit mu left join fetch o.timeUnit tu where o.del='0' and sw.type='4' and sw.sts='4' ";
		
		String params="";
		/*
		if(StringUtil.notNull(outputBean.getWorkShop())){
			 定义
			  code  name
			  1		卷包车间
			  2		成型车间
			  3		动力车间
			  4		制丝车间
			
			//params = params.concat(" and me.mdWorkshop.code='"+outputBean.getWorkShop()+"'");
		}*/
		
		if(StringUtil.notNull(outputBean.getShift())){
			params = params.concat(" and ms.id='"+outputBean.getShift()+"'");
		}
		if(StringUtil.notNull(outputBean.getTeam())){
			params = params.concat(" and mt.id='"+outputBean.getTeam()+"'");
		}
		if(outputBean.getIsFeedback() != null){
			params = params.concat(" and o.isFeedback="+outputBean.getIsFeedback());
		}
		if(StringUtil.notNull(outputBean.getDate())){
			params = params.concat(" and sw.date = '"+outputBean.getDate()+"'");
		}
		
		
		List<SchStatOutput> rows=schStatOutputDao.queryByPage(hql.concat(params), pageParams);
		
		
		List<OutputBean> outputBeans = new ArrayList<OutputBean>();
		
		OutputBean bean = null;
		try {
			
			for (SchStatOutput output : rows) {
				
				bean = BeanConvertor.copyProperties(output, OutputBean.class);
				
				
				SchWorkorder workorder = output.getSchWorkorder();
				
				bean.setDate(DateUtil.formatDateToString(workorder.getDate(), "yyyy-MM-dd"));
				
				bean.setShift(workorder.getMdShift().getName());
				
				bean.setTeam(workorder.getMdTeam().getName());
				
				bean.setEquipment(workorder.getMdEquipment().getEquipmentName());
				bean.setQty(bean.getQty());
				bean.setBadQty(MathUtil.roundHalfUp(bean.getBadQty(),4));
				/*//换算产量剔除
				if(Integer.valueOf(workorder.getMdEquipment().getEquipmentCode())<31){
					//卷烟机
					//bean.setQty(MathUtil.roundHalfUp(bean.getQty()/50,2));
					bean.setBadQty(MathUtil.roundHalfUp(bean.getBadQty()/50, 2));
				}else if(Integer.valueOf(workorder.getMdEquipment().getEquipmentCode())<61){
					//包装机
					//bean.setQty(MathUtil.roundHalfUp(bean.getQty()/2500,2));
					bean.setBadQty(MathUtil.roundHalfUp(bean.getBadQty()/2500, 2));
				}*/
				
				bean.setWorkorder(workorder.getId());
				
				bean.setWorkorderCode(workorder.getCode());
				
				bean.setMatProd(workorder.getMdMat().getName());
				
				bean.setUnit(output.getMdUnit().getName());
				
				bean.setTimeunit(output.getTimeUnit().getName());
				
				outputBeans.add(bean);
				
				bean = null;
			}
			
			hql= "select count(o) ".concat(hql.replace("fetch", ""));
			
			long total=schStatOutputDao.queryTotal(hql.concat(params));
			
			return new DataGrid(outputBeans, total);
			
		} catch (Exception e) {
			
			log.error("POVO转换异常", e);
			
		}
		
		return null;
	}
	@Override
	public DataGrid getAllInputsByOutput(String id) throws Exception {
		List<SchStatInput> list = schStatInputDao.query("from SchStatInput o left join fetch o.mdMat mm left join fetch o.mdUnit mu where o.schStatOutput.id=?", id);
		
		List<InputBean> rows = new ArrayList<InputBean>();
		
		InputBean bean = null;
		
		for (SchStatInput schStatInput : list) {
			
			bean = BeanConvertor.copyProperties(schStatInput, InputBean.class);
			
			bean.setMat(schStatInput.getMdMat().getName());
			bean.setUnit(schStatInput.getMdUnit().getName());
			
			rows.add(bean);
			
			bean = null;
		}
		
		return new DataGrid(rows, 0L);
	}
	@Override
	public int addOutput(OutputBean outputBean,int flag) throws Exception {
		SchStatOutput o = new SchStatOutput();
		String workorder = outputBean.getWorkorder();
		if(flag==1){//人为新增
			//判断当前新增工单是否已经在生产实绩中有记录
			if(schStatOutputDao.queryTotal("select count(o) from SchStatOutput o where o.del='0' and o.schWorkorder.id=?",outputBean.getWorkorder())>0){
				return 0;
			}
			o = BeanConvertor.copyProperties(outputBean, SchStatOutput.class);
			
			o.setMdUnit(new MdUnit(outputBean.getUnit()));//来源于工单计划产量单位
			//时间单位统一用分钟
			o.setLastUpdateTime(new Date());
			
		}else if(flag==0){
			schStatOutputDao.deleteByParams("delete from SchStatOutput o where o.schWorkorder.id = ?", workorder);
			o.setStim(DateUtil.formatStringToDate(outputBean.getStim(), "yyyy-MM-dd HH:mm:ss"));
			//初始值未空
			o.setQty(0D);
			o.setBadQty(0D);
			o.setRunTime(0D);
			o.setStopTime(0D);
			o.setStopTimes(0L);
			//给产量单位赋值
			o.setMdUnit(mdUnitDao.unique(MdUnit.class, "select o.mdUnit from SchWorkorder o where o.id=?", workorder));
			
		}
		
		o.setSchWorkorder(new SchWorkorder(workorder));//工单
		o.setTimeUnit(mdUnitDao.unique(MdUnit.class,"from MdUnit o where o.name=?", "分钟"));
		o.setIsFeedback(0L);//是否反馈
		o.setDel(0L);//是否删除
		schStatOutputDao.save(o);
		
		if(flag==0){//运行工单时，新增的生产实绩的消耗数据从工单bom中来
			Map<String, String> type_statInput = addInputsFromWorkOrderBoms(workorder,o);
			//保存产出实绩和消耗实绩信息到内存中，避免轮询保存实时数据时，重复从数据库获得这些信息
			DbOutputOrInputInfos outputOrInputInfos = new DbOutputOrInputInfos();
			outputOrInputInfos.setStatOutputId(o.getId());
			outputOrInputInfos.setType_statInputId(type_statInput);
			SchWorkorder order = schWorkorderDao.findById(SchWorkorder.class,workorder);
			String equipmentCode = order.getMdEquipment().getEquipmentCode();
			DataHandler.setDbOutputOrInputInfosIntoMap(equipmentCode, outputOrInputInfos);
		}
		return 1;
	}
	public Map<String,String> addInputsFromWorkOrderBoms(String workorder,SchStatOutput schStatOutput){
		//查询工单的bom
		List<SchWorkorderBom> boms = schWorkorderBomDao.query("from SchWorkorderBom o where del=0 and o.schWorkorder.id=?", workorder);
		//循环保存bom到input中，计数为零
		//优点：及时采集程序没有采集到数据，补录数据也无需做新增这一繁琐工作，只需修改数据即可
		Map<String,String> inputs = new HashMap<String, String>();
		for (SchWorkorderBom schWorkorderBom : boms) {
			SchStatInput o = new SchStatInput(schWorkorderBom.getMdUnit(), schStatOutput, schWorkorderBom.getMdMat(), 0D, 0D);
			schStatInputDao.save(o);
			inputs.put(schWorkorderBom.getMdMat().getMdMatType().getCode(), o.getId());
		}
		return inputs;
	}
	@Override
	public int editOutput(OutputBean outputBean) {
		SchStatOutput output =schStatOutputDao.findById(SchStatOutput.class, outputBean.getId());
		try {
			BeanConvertor.copyProperties(outputBean, output);
			output.setLastUpdateTime(new Date());
		} catch (Exception e) {
			log.error("转换异常", e);
		}
		return 1;
	}
	@Override
	public void deleteOutput(String id) {
		schStatOutputDao.updateByParams("update SchStatOutput o set o.del='1' where o.id=?",id);
	}
	@Override
	public void deleteOutputByWorkOrder(String workorder) {
		schStatOutputDao.updateByParams("update SchStatOutput o set o.del='1' where o.schWorkorder.id=?",workorder);
	}
	@Override
	public int addInput(InputBean inputBean) throws Exception {
		 SchStatInput o = BeanConvertor.copyProperties(inputBean, SchStatInput.class);
		 o.setMdUnit(new MdUnit(inputBean.getUnit()));
		 o.setSchStatOutput(new SchStatOutput(inputBean.getOutputId()));
		 o.setMdMat(new MdMat(inputBean.getMat()));
		 schStatInputDao.save(o);
		 return 1;
	}
	@Override
	public int editInput(InputBean inputBean) {
		SchStatInput o = schStatInputDao.findById(SchStatInput.class,inputBean.getId());		
		o.setQty(inputBean.getQty());//只编辑消耗量
		return 1;
	}
	@Override
	public void deleteInput(String id) {
		schStatInputDao.deleteById(id, SchStatInput.class);
	}
	@Override
	public OutputBean getOutputById(String id) {
		
		SchStatOutput output =schStatOutputDao.findById(SchStatOutput.class, id);
		
		OutputBean bean =null;
		try {
			
			bean = BeanConvertor.copyProperties(output, OutputBean.class);
			
			SchWorkorder workorder = output.getSchWorkorder();
			
			bean.setDate(DateUtil.formatDateToString(workorder.getDate(), "yyyy-MM-dd"));
			
			bean.setShift(workorder.getMdShift().getName());
			
			bean.setTeam(workorder.getMdTeam().getName());
			
			bean.setEquipment(workorder.getMdEquipment().getEquipmentName());
			
			bean.setWorkorder(workorder.getId());
			
			bean.setWorkorderCode(workorder.getCode());
			
			bean.setMatProd(workorder.getMdMat().getName());
			
			bean.setUnitName(output.getMdUnit().getName());
			
			bean.setUnit(output.getMdUnit().getId());
			
			bean.setTimeunit(output.getTimeUnit().getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	@Override
	public InputBean getInputById(String id) throws Exception {
		SchStatInput input = schStatInputDao.findById(SchStatInput.class,id);
		InputBean bean = BeanConvertor.copyProperties(input, InputBean.class);
	    bean.setUnit(input.getMdUnit().getName());
	    bean.setMat(input.getMdMat().getName());
		return bean;
	}
	@Override
	public List<Combobox> getBomsWorkorderId(String workorderId) throws Exception {
		String hql = "select o.mdMat from SchWorkorderBom o where o.schWorkorder.id=? and o.mdMat.id not in (select i.mdMat.id from SchStatInput i where i.schStatOutput.schWorkorder.id=?)";
		return BeanConvertor.copyList(schStatInputDao.query(List.class, hql,workorderId,workorderId), Combobox.class);
	}
	@Override
	public UnitBean getUnitByMatId(String workorder, String matId) {
		String hql = "select o.mdUnit from SchWorkorderBom o where o.schWorkorder.id=? and o.mdMat.id=?";
		MdUnit obj = (MdUnit)schStatOutputDao.unique(hql,workorder,matId);
		return new UnitBean(obj.getId(), obj.getName());
	}
	
	
	/**
	 * 修改人 shisihai 工单反馈
	 */
	@Override
	public int sendDataToMES(String idOrIds,String feedbackUser) {
		WorkOrderFeedbackInterceptor workOrderFeedback=new WorkOrderFeedbackInterceptor();
		return workOrderFeedback.sendWorkOrderDetails(idOrIds, feedbackUser);
	}
	/**
	 * 修改生产实绩反馈状态
	 * @author Leejean
	 * @create 2014年12月4日下午2:58:50
	 */
	public void editOutputIsFeedback(String id,String feedbackUser){
		SchStatOutput output = schStatOutputDao.findById(SchStatOutput.class, id);
		output.setIsFeedback(1L);
		output.setFeedbackTime(new Date());
		output.setFeedbackUser(feedbackUser);
		
	}
	@Override
	public void saveAllData(List<EquipmentData> eqpData) {
		log.info("定时保存所有实时数据中...");
		for (EquipmentData equipmentData : eqpData) {
			if(equipmentData.getType().toLowerCase().indexOf("_FL")>0){
				continue;
			}
			this.saveEquipmentData(String.valueOf(equipmentData.getEqp()), equipmentData);
		}
	}
	
	
	/**
	 * [功能描述]：超过班次时间内，未进行交接班，默认保存虚退值为0
	 *   1）获取当前班次
	 *   2）开始时间（当前时间） 毫秒
	 *   3）结束时间（获取开始时间+2小时）=毫秒
	 *   4）判断  开始时间>结束时间
	 *   
	 * */
	public boolean schStatShiftFLInfo(){
		try {
			SchCalendarBean scbean=NeedData.getInstance().getScbean();
			if(scbean==null){
				return false; 
			}
			String startStr = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
			long startLong = DateUtil.getTime(startStr,"yyyy-MM-dd HH:mm:ss");
			//结束时间
			Date d=DateUtil.strToDate(scbean.getStim(), "yyyy-MM-dd HH:mm:ss");
			String endStr=DateUtil.dateAdd("h", 2, d, "yyyy-MM-dd HH:mm:ss");
			long endLong= DateUtil.getTime(endStr,"yyyy-MM-dd HH:mm:ss");
			if(startLong>endLong){ //换班后2小时内，都可以交接班；所以只能2小时候才能对上一班进行自动交接班
				String shift_id=scbean.getMdShiftCode();
				String oldty=startStr.substring(0,10);
				//根据班次，得到上一班次
				if("1".equals(shift_id)){
					shift_id="3"; //早-》昨天晚上晚
					oldty=DateUtil.dateFormatCal(oldty, -1);
				}else if("2".equals(shift_id)){
					shift_id="1"; //中——》早
				}else if("3".equals(shift_id)){
					shift_id="2"; //晚-》中
				}
			    sqlbf.setLength(0);//清空
				sqlbf.append(" select type,eqp,team,shift,id,mat,(select name from md_mat where id=mat) as mat_name From SCH_WORKORDER where 1=1 ");
				sqlbf.append(" and shift='"+shift_id+"' and convert(varchar(50),date,23)='"+oldty+"' ");
				sqlbf.append(" and sts=4 and del=0 and id not in ( select b.oid from SCH_WORKORDER a left join SCH_STAT_SHIFT_FLINFO b on  a.id=b.oid  where 1=1");
				sqlbf.append(" and a.sts=4 and a.del=0 and  b.fl_type=3  and a.shift='"+shift_id+"'  and convert(varchar(50),a.date,23)='"+oldty+"' ) ");
				List<?> list=schWorkorderDao.queryBySql(sqlbf.toString());
				if(list.size()>0 && list!=null){
					StatShiftFLInfo ssf=null;
					StatShiftFLInfo ssf2=null;
					StatShiftFLInfo ssf3=null;
					for(int i=0;i<list.size();i++){
						Object[] temp=(Object[]) list.get(i);
						ssf=new StatShiftFLInfo();
						ssf2=new StatShiftFLInfo();
						ssf3=new StatShiftFLInfo();
						ssf.setMec_id(StringUtil.convertObjToString(temp[0].toString()));
						ssf.setEqp_id(StringUtil.convertObjToString(temp[1].toString()));
						ssf.setTeam_id(StringUtil.convertObjToString(temp[2].toString()));
						ssf.setShift_id(StringUtil.convertObjToString(temp[3].toString()));
						ssf.setOid(StringUtil.convertObjToString(temp[4].toString()));
						ssf.setMat_id(StringUtil.convertObjToString(temp[5].toString()));
						ssf.setMat_name(StringUtil.convertObjToString(temp[6].toString()));
						ssf.setZc_type(0);
						ssf.setHandover_user_name("自动交接班");
						ssf.setHandover_user_id("-1");
						ssf.setCreate_time(new Date());
						ssf.setUpdate_time(ssf.getCreate_time());
						ssf.setStatus(1);
						//添加 1-实领  2-虚领 3-虚退
						taddStatShiftFLInfo(ssf,ssf2,ssf3);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void taddStatShiftFLInfo(StatShiftFLInfo ssf,StatShiftFLInfo ssf2,StatShiftFLInfo ssf3){
		//实领
		ssf2.setFl_type(1);//实领
		if(queryFLInfo(ssf2)){
			ssf2.setMec_id(ssf.getMec_id());
			ssf2.setEqp_id(ssf.getEqp_id());
			ssf2.setTeam_id(ssf.getTeam_id());
			ssf2.setShift_id(ssf.getShift_id());
			ssf2.setOid(ssf.getOid());
			ssf2.setMat_id(ssf.getMat_id());
			ssf2.setMat_name(ssf.getMat_name());
			ssf2.setZc_type(0);
			ssf2.setHandover_user_name("自动交接班");
			ssf2.setHandover_user_id("-1");
			ssf2.setCreate_time(ssf.getCreate_time());
			ssf2.setUpdate_time(ssf.getUpdate_time());
			ssf2.setStatus(1);
			statShiftFLInfoDao.saveBackKey(ssf2);
		}
		//虚领
		ssf3.setFl_type(2);//虚领
		if(queryFLInfo(ssf3)){
			ssf3.setMec_id(ssf.getMec_id());
			ssf3.setEqp_id(ssf.getEqp_id());
			ssf3.setTeam_id(ssf.getTeam_id());
			ssf3.setShift_id(ssf.getShift_id());
			ssf3.setOid(ssf.getOid());
			ssf3.setMat_id(ssf.getMat_id());
			ssf3.setMat_name(ssf.getMat_name());
			ssf3.setZc_type(0);
			ssf3.setHandover_user_name("自动交接班");
			ssf3.setHandover_user_id("-1");
			ssf3.setCreate_time(ssf.getCreate_time());
			ssf3.setUpdate_time(ssf.getUpdate_time());
			ssf3.setStatus(1);
			statShiftFLInfoDao.saveBackKey(ssf3);
		}
		//虚退
		ssf.setFl_type(3);
		statShiftFLInfoDao.saveBackKey(ssf);
	}
	
	//验证辅料是否存在
	public boolean queryFLInfo(StatShiftFLInfo ssf){
		try {
			sqlbf.setLength(0);
			sqlbf.append(" select id from SCH_STAT_SHIFT_FLINFO where 1=1 and mec_id='"+ssf.getMec_id()+"' and eqp_id='"+ssf.getEqp_id()+"' ");
			sqlbf.append(" and shift_id='"+ssf.getShift_id()+"' and mat_id='"+ssf.getMat_id()+"' and fl_type='"+ssf.getFl_type()+"' and del=0");
			List<?> list=statShiftFLInfoDao.queryBySql(sqlbf.toString());
			if(list.size()>0 && list!=null){
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;		
	}
	
	/**
	 * code   name      
	[卷烟机]
	2	            卷烟纸(盘纸)	
	3	            水松纸                
	4	            滤棒                      
	[包装机]
	5	             小盒烟膜
	6	             条盒烟膜
	7	             小盒纸     
	8	             条盒纸     
	9	             内衬纸 	
	[成型机]
	13	            滤棒盘纸
	 */
	/**
	 * 保存单个机台实时数据
	 * 说明 当工单完成时，需要调用本法方，且 data 参数不给值
	 * @author Leejean
	 * @create 2015年1月30日下午2:58:50
	 */
	@Override
	public void saveEquipmentData(String equipmentCode, Object... data) {
		EquipmentData equipmentData = null;
		if(data.length>0){
			equipmentData = (EquipmentData)data[0];
		}else{
			equipmentData = NeedData.getInstance().getEquipmentData(Integer.valueOf(equipmentCode));
		}
		//执行保存操作
		if(equipmentData!=null){
			String type = equipmentData.getType();
			//1.查询当前的工单是否运行状态 如果是运行状态 在去判断是 什么类型的工单 进行保存数据,保存的时候需要判定是否为 0 如果为 0 就不要保存了
			SchWorkorder runWork = null;
			try {
				runWork = workOrderService.getRunSchWorkorder(equipmentCode);//3100是辅料数据，不用管它
			} catch (Exception e1) {
				log.error("StatServiceImpl->saveEquipmentData is error :"+e1.getMessage());
			}
			if(null!=runWork){
				Map<String, DbOutputOrInputInfos> dbOutputOrInputInfos = DataHandler.getDbOutputOrInputInfos();
				String dbOutputId = DataHandler.getDbOutputId(dbOutputOrInputInfos, equipmentCode);
				//由于这里 是 点击运行工单是放在 内存里面的，如果tomcat 挂掉了 在重新启动就会有问题了
				//add by luther.zhang 20150422
				if(null==dbOutputId||"".equals(dbOutputId)){//如果卷烟机 包装机 查询 辅料数据
					if(EquipmentTypeDefinition.getRoller().contains(type)
							||EquipmentTypeDefinition.getPacker().contains(type)
						){
						//这里如果为空根据 设备CODE 重新查询下,保证有正在运行的工单;重新赋值 查询运行的工单 并赋值 辅料等系数
						try { 
							workOrderService.queryRunWorkFl(runWork);
						} catch (Exception e) {
							log.error("StatServiceImpl->saveEquipmentData:"+e.getMessage());
						}
						OutputBean outputBean = new OutputBean();
						outputBean.setWorkorder(runWork.getId());
						String realStim = DateUtil.datetoStr(runWork.getRealStim(), "yyyy-MM-dd HH:mm:ss");
						outputBean.setStim(realStim);//实际开始时间
						try {
							this.addOutput(outputBean, 0);
						} catch (Exception e) {
							log.error("StatServiceImpl->addOutput:"+e.getMessage());
						}
						dbOutputOrInputInfos = DataHandler.getDbOutputOrInputInfos();//重新取值
						dbOutputId = DataHandler.getDbOutputId(dbOutputOrInputInfos, equipmentCode);
					}
				}
				//end
				//卷烟机=====================
				Map<String, String> type_InputId = DataHandler.getType_InputId(dbOutputOrInputInfos, equipmentCode);	
				if(EquipmentTypeDefinition.getRoller().contains(type)){
					RollerData edata = rollerIspService.getRollerData(equipmentCode);
					if(dbOutputId!=null){//修改产量数据					
						if(edata.getQty()>0){//产量只要大于0 才记录，防止 PLC换班了，程序自动换班没有换班,赋值 0
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty(), 
									edata.getBadQty(), 
									edata.getRunTime(), 
									edata.getStopTime(), 
									Long.valueOf(edata.getStopTimes().toString()), 
									DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");
						}
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]");
					}
					if(type_InputId!=null){
						String inputId = DataHandler.getInputIdByType(type_InputId, "2");
						InputBean inputBean = null;
						
						if(inputId!=null){//装配、保存【卷烟纸】
							inputBean = new InputBean(inputId, 0D, edata.getJuanyanzhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=卷烟纸,value="+edata.getJuanyanzhiVal()+"]");
							}
						}
						inputId = DataHandler.getInputIdByType(type_InputId, "3");
						if(inputId!=null){//装配、保存【水松纸】
							inputBean = new InputBean(inputId, 0D, edata.getShuisongzhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=水松纸,value="+edata.getShuisongzhiVal()+"]");
							}
						}
						inputId = DataHandler.getInputIdByType(type_InputId, "4");
						if(inputId!=null){//装配、保存【滤棒】
							inputBean = new InputBean(inputId, 0D, edata.getLvbangVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=滤棒,value="+edata.getLvbangVal()+"]");
							}
						}
					}
				}
				//====================
				
				//包装机=====================
				if(EquipmentTypeDefinition.getPacker().contains(type)){
					PackerData edata = packerIspService.getPackerData(equipmentCode);			
					if(dbOutputId!=null){//修改产量数据		
						if(edata.getQty()>0){
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty(), 
									edata.getBadQty(), 
									edata.getRunTime(), 
									edata.getStopTime(), 
									Long.valueOf(edata.getStopTimes().toString()), 
									DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");	
						}
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]");
					}
					if(type_InputId!=null){
						String inputId = DataHandler.getInputIdByType(type_InputId, "5");
						InputBean inputBean = null;
						
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getXiaohemoVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=小盒膜,value="+edata.getXiaohemoVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "6");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getTiaohemoVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=条盒膜,value="+edata.getTiaohemoVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "7");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getXiaohezhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=小盒纸,value="+edata.getXiaohezhiVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "8");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getTiaohezhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=条盒纸,value="+edata.getTiaohezhiVal()+"]");
							}
						}
						
						inputId = DataHandler.getInputIdByType(type_InputId, "9");
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getNeichenzhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=内衬纸,value="+edata.getNeichenzhiVal()+"]");
							}
						}
						
					}
				}
				//=====================
				
				//封箱机=====================
				if(EquipmentTypeDefinition.getBoxer().contains(type)){
					BoxerData edata = boxerIspService.getBoxerData(equipmentCode);
					if(dbOutputId!=null){//修改产量数据		
						if(edata.getQty()>0){
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty(), 
									edata.getBadQty(), 
									edata.getRunTime(), 
									edata.getStopTime(), 
									Long.valueOf(edata.getStopTimes().toString()), 
									DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");
						}
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]");
					}
				}
				//================================
				
				//发射机===========================
				if(EquipmentTypeDefinition.getFilter().contains(type)){
					FilterData edata = filterIspService.getFilterData(equipmentCode);
					if(dbOutputId!=null){//修改产量数据
						if(edata.getQty()>0){
							this.editOutput(new OutputBean(dbOutputId, 
									edata.getQty(), 
									edata.getBadQty(), 
									edata.getRunTime(), 
									edata.getStopTime(), 
									Long.valueOf(edata.getStopTimes().toString()), 
									DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")));
							log.info("保存产量[equipmentCode="+equipmentCode+",value="+edata.getQty()+"]");
						}
						
					}else{
						log.info("工单未运行[equipmentCode="+equipmentCode+"]");
					}
					if(type_InputId!=null){
						String inputId = DataHandler.getInputIdByType(type_InputId, "13");
						InputBean inputBean = null;
						if(inputId!=null){//装配、保存
							inputBean = new InputBean(inputId, 0D, edata.getPanzhiVal());
							if(edata.getQty()>0){//产量大于 0 才更新
								this.editInput(inputBean);
								log.info("保存消耗[equipmentCode="+equipmentCode+",type=滤棒盘纸,value="+edata.getPanzhiVal()+"]");
							}
						}
					}
				}
				
			}
		}else{
			log.info("当前无实时数据...");
		}
		
	}
	/**
	 * 根据设备ID保存设备故障汇总信息
	 * @param eid
	 * @return
	 */
	public boolean saveTrouble(String eid,Hashtable<String,String> faultHt){
		//根据设备ID获取设备故障集合，String[] //0:机台Code，1:班次，2:故障描述,3:次数,4:故障发生总时长
		List<String[]> list=null;
		if(StringUtil.notNull(eid)){
			list=FaultDataCalc.getInstance().getFaultList(eid);
		}else{
			list=FaultDataCalc.getInstance().getAllFaultList();
		}
		for(String[] string:list){
			if(!StringUtil.notNull(string[0])){
				log.error("保存故障信息失败，设备CODE("+string[0]+")不存在。");
				continue;
			}
			String value=eqpCodeOutputList.get(string[0]);
			if(value==null||!StringUtil.notNull(value)){
				log.info("实时保存故障汇总信息失败，无法获取当前设备Code("+string[0]+")对应的运行工单实际产量ID信息");
				continue;	
			}
			SchStatFault fault=new SchStatFault();
			//如果故障ID已存在,并且故障名称一致，则进行修改
			if(StringUtil.notNull(value)){
				String faultId=faultHt.get(string[0]+string[2]);
				if(faultId!=null){
					fault.setId(faultId);
				}
			}
			//SCH_STAT_OUTPUT ID
			fault.setSchStatOutput(new SchStatOutput(value));
			//故障名称
			fault.setName(string[2]);
			//时长
			fault.setTime(Double.parseDouble(string[4]));
			//次数
			fault.setTimes((long)Double.parseDouble(string[3]));
			//反馈状态
			fault.setFlag(0L);
			SchStatFaultDao.saveOrUpdate(fault);
		}
		return false;
	}
	
	@Override
	public void saveAllErrorData(List<String[]> list) {
		Hashtable<String,String> ht=getAllWorkOrderOutputListHT();
		log.info("保存所有机台故障汇总信息……");
		/**
		 * 当前班次时间内未做交接班，系统默认保存交接班数据值为：0
		 *    //定时器保存没有交接班，系统默认保存值为0的交接班数据
		 * wch  
		 * */
		//schStatShiftFLInfo(); 
		//故障
		saveTrouble(null,ht);
	}
	
	
	private Hashtable<String,String> eqpCodeOutputList;
	/**
	 * 获取所有运行的设备output Id
	 * @return ht<code,id>
	 */
	public Hashtable<String,String> getAllWorkOrderOutputListHT() {
		StringBuffer sqlBuffer=new StringBuffer();
		//设备Code,实际生产数据ID，故障id,日期
		sqlBuffer.append("select e.EQUIPMENT_CODE,ou.ID,fault.ID as fault_id,fault.NAME,wo.DATE from SCH_WORKORDER wo ");
		sqlBuffer.append("left join SCH_STAT_OUTPUT ou on wo.ID=ou.OID ");
		sqlBuffer.append("left join SCH_STAT_FAULT fault on ou.ID=fault.OID ");
		sqlBuffer.append("left join MD_EQUIPMENT e on wo.EQP=e.ID ");
		sqlBuffer.append("where wo.del=0 and wo.IS_CHECK=1 and wo.sts=2 ");
		sqlBuffer.append("and ou.ID is not null order by wo.DATE desc ");
		Hashtable<String,String> ht=new Hashtable<String,String>();
		eqpCodeOutputList=new Hashtable<String,String>();
		List<?> list=SchStatFaultDao.queryBySql(sqlBuffer.toString());
		for(Object o:list){
			Object[] temp=(Object[]) o;
			//保存设备code和正在运行工单的生产实绩表 对应关系
			//key eqpCode,value output ID
			if(eqpCodeOutputList.get(temp[0].toString().trim())==null){
				eqpCodeOutputList.put(temp[0].toString().trim(), StringUtil.convertObj(temp[1]));
			}
			//过滤没有当前设备运行工单没有故障信息
			if(temp[3]!=null&&StringUtil.notNull(temp[3].toString())&&ht.get(temp[0].toString().trim()+StringUtil.convertObj(temp[3]))==null){
				//key 设备code,value 0:生产实际ID，1:故障ID,2:故障名称
				ht.put(temp[0].toString().trim()+StringUtil.convertObj(temp[3]), StringUtil.convertObj(temp[2]));
			}
		}
		return ht;
	}
	
	/**
	 * [功能描述]：提升机数据，在开始结束本班次后，保存提升机产量到相关机台
	 * @author wanchanghuang
	 * @createTime 2015年11月27日17:43:30
	 * */
	@Override
	public Map<String, String> queryByEquiment() {
		Map<String, String> map = new HashMap<String, String>();
		//获取包装机code
		Integer code=SysEqpTypeBase.packerEqpTypeCode;
		String sql="select id,EQUIPMENT_CODE from MD_EQUIPMENT where EQP_TYPE_ID in(select a.id from MD_EQP_TYPE a,MD_EQP_CATEGORY b where a.cid=b.id and b.code='"+code+"')";
		List<?> list=schStatInputDao.queryBySql(sql);
		if(list.size()>0 && list!=null){
			for(int i=0;i<list.size();i++){
				Object[] temp=(Object[]) list.get(i);
				if(temp[0]!=null && temp[1]!=null){
					map.put(temp[1].toString(), temp[0].toString());
				}
			}
			
		}
		return map;
	}
	
	/**
	 * [功能说明]：用提升机产量更新工单产量
	 * @author wanchanghuang
	 * @createTime 2015年11月30日08:43:06
	 * @param tsqty-提升机产量    eqp_id-工单ID
	 * @param tSQty=条   /250 =箱
	 * */
	@Override
	public void supdateSchStatInputOutById(SchCalendarBean bean, Double tSQty,String eqp_id) {
		//StringBuffer hql=new StringBuffer(1000);
		sqlbf.setLength(0);
		sqlbf.append(" update SCH_STAT_OUTPUT  set dacqty=qty,qty='"+tSQty+"' where oid=( "); 
		sqlbf.append(" select b.id from SCH_STAT_OUTPUT a ,SCH_WORKORDER b where ");
		sqlbf.append(" a.oid=b.id and b.shift='"+bean.getMdShiftCode()+"' and  CONVERT(varchar(50),b.date,23)=CONVERT(varchar(50),GETDATE(),23) and b.eqp='"+eqp_id+"' ");
		sqlbf.append(" and a.dacqty is  null ) ");
		//修改
		try {
			schStatInputDao.updateInfo(sqlbf.toString(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出PMS卷包机产量信息
	 * TODO
	 * @param date
	 * @param date2
	 * @return
	 * TRAVLER
	 * 2015年12月2日下午2:48:51
	 */
	@Override
	public HSSFWorkbook exportQtyInfo(String date, String date2,String shift,String team,String eqp,String eqpType) {
		HSSFWorkbook workBook = null;
		ExportExcel ee = new ExportExcel();
		workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		workBook.setSheetName(0, "temp1", HSSFWorkbook.ENCODING_UTF_16);
		sheet.setVerticallyCenter(true);
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short)15);
		HSSFCellStyle tableStyle = ee.getTableStyle(workBook,true);
		HSSFCellStyle tdStyle = ee.getTableStyle(workBook,false);
		tdStyle.setWrapText(false);
		try {
			// th 当前开始行,当前结束行,  从1开始计算行  一共多少列 从0开始计算总列数
			int[] thTables = {1,3,13};
			List<String> th = new ArrayList<String>();

			th.add("1,1,13,1,卷包生产实际");//开始行 开始列  结束列  结束行
			th.add("2,1,2,2,日期");
			th.add("2,3,5,2,"+date+" 到  "+date2);
			th.add("3,1,1,3,班次");
			th.add("3,2,2,3,班组");
			th.add("3,3,3,3,设备");
			th.add("3,4,4,3,计划产量");
			th.add("3,5,5,3,计划开始时间");
			th.add("3,6,6,3,计划结束时间");
			th.add("3,7,7,3,实际产量");
			th.add("3,8,8,3,实际剔除");
			th.add("3,9,9,3,实际开始时间");
			th.add("3,10,10,3,实际结束时间");
			th.add("3,11,11,3,实际运行时间");
			th.add("3,12,12,3,实际停机时间");
			th.add("3,13,13,3,实际停机次数");
			//获取List集合
			List<RoutputBean> ls = this.queryQtyInfo(date,date2,shift,team,eqp,eqpType);
			//方法集合，同表头一致
			String[] method=new String[]{"getShift","getTeam","getEqpName","getpQty","getpSTime"
										 ,"getpETime","getQty","getBadQty","getStime",
										 "getEtime","getRunTime","getStopTime","getStopTimes"
										 };
			//开始行
			int startLine=3;
			ee.exportExcelMoreTh(thTables, th, startLine, method,RoutputBean.class,ls, sheet, tableStyle, tdStyle,null,null,null,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workBook;
	}
	/**
	 * 查询PMS卷包产量数据查询
	 * TODO
	 * @param date
	 * @param date2
	 * @return
	 * TRAVLER
	 * 2015年12月2日下午2:52:22
	 */
	private List<RoutputBean> queryQtyInfo(String date, String date2,String shift,String team,String eqp,String eqpType) {
		List<RoutputBean> beans=new ArrayList<RoutputBean>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select (select name from MD_SHIFT where id=a.shift) as '班次',(select equipment_name from MD_EQUIPMENT where id=a.eqp) as '设备名称', ");
		sb.append(" a.qty as '计划产量(箱)',convert(varchar(50),a.stim,20) as '计划开始时间',convert(varchar(50),a.etim,20) as '计划结束时间', ");
		sb.append(" b.qty as '实际产量',b.bad_qty as '实际剔除',convert(varchar(50),b.stim,20) as '实际开始时间',");
		sb.append(" convert(varchar(50),b.etim,20) as '实际结束时间',b.run_time as '实际运行时间',b.stop_time as '实际停机时间',");
		sb.append(" b.stop_times as '实际停机次数',(SELECT name FROM MD_TEAM	WHERE	id = a.TEAM ) AS '班组' from SCH_WORKORDER a,SCH_STAT_OUTPUT b where a.id=b.oid ");
		sb.append(" and 1=1 ");
		if(StringUtil.notNull(date)){
			sb.append(" and  convert(varchar(50),a.date,23)>='"+date+"'");
		}
		if(StringUtil.notNull(date2)){
			sb.append(" and convert(varchar(50),a.date,23)<='"+date2+"'");
		}
		//班组
		if(StringUtil.notNull(team)){
			sb.append(" AND a.TEAM="+team);
		}
		//班次
		if(StringUtil.notNull(shift)){
			sb.append(" AND a.SHIFT="+shift);
		}
		//机台
		if(StringUtil.notNull(eqp)){
			sb.append(" AND a.EQP='"+eqp+"'");
		}
		//机型
		if(StringUtil.notNull(eqpType)){
			sb.append(" and a.type="+eqpType);
		}
		sb.append(" and a.STS=4 order by a.date,(select equipment_name from MD_EQUIPMENT where id=a.eqp)");
		List<?> ls=schStatOutputDao.queryBySql(sb.toString());
		Object[] o=null;
		RoutputBean b=null;
		if(ls!=null && ls.size()>0){
			for (Object obj : ls) {
				b=new RoutputBean();
				o=(Object[]) obj;
				b.setShift(StringUtil.convertObjToString(o[0]));
				b.setEqpName(StringUtil.convertObjToString(o[1]));
				b.setpQty(this.convDouble(o[2]));
				b.setpSTime(StringUtil.convertObjToString(o[3]));
				b.setpETime(StringUtil.convertObjToString(o[4]));
				b.setQty(this.convDouble(o[5]));
				b.setBadQty(this.convDouble(o[6]));
				b.setStime(StringUtil.convertObjToString(o[7]));
				b.setEtime(StringUtil.convertObjToString(o[8]));
				b.setRunTime(StringUtil.convertObjToString(o[9]));
				b.setStopTime(StringUtil.convertObjToString(o[10]));
				b.setStopTimes(StringUtil.convertObjToString(o[11]));
				b.setTeam(StringUtil.convertObjToString(o[12]));
				beans.add(b);
			}
		}
		
		//添加合计
		b=new RoutputBean();
		b.setEqpName("合计");
		//计划产量
		Double pQty=0.0;
		//实际产量
		Double rQty=0.0;
		//实际剔除
		Double badQty=0.0;
		//实际运行时间
		Double runTime=0.0;
		//实际停机时间
		Double stopTime=0.0;
		//实际停机次数
		Double stopTimes=0.0;
		for (RoutputBean b0 : beans) {
			pQty += this.convDouble(b0.getpQty());
			rQty += this.convDouble(b0.getQty());
			badQty +=this.convDouble(b0.getBadQty());
			runTime += this.convDouble(b0.getRunTime());
			stopTime += this.convDouble(b0.getStopTime());
			stopTimes += this.convDouble(b0.getStopTimes());
		}
		b.setpQty(pQty);
		b.setQty(rQty);
		b.setBadQty(badQty);
		b.setRunTime(runTime.toString());
		b.setStopTime(stopTime.toString());
		b.setStopTimes(stopTimes.toString());
		beans.add(b);
		return beans;
		
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
	
	@Override
	public List<MdEquipment> queryEqpMents(int categroyid) {
		MdEquipment eqp=null;
		List<MdEquipment> eqps=new ArrayList<MdEquipment>();
		String sql="select eqp.ID,eqp.EQUIPMENT_NAME from MD_EQUIPMENT eqp,MD_EQP_TYPE eqpt,MD_EQP_CATEGORY eqpc where eqpc.ID=eqpt.CID and eqp.EQP_TYPE_ID=eqpt.ID and eqpc.CODE='"+categroyid+"'";
		List<?> eqpnames=eqmipmentsDao.queryBySql(sql);
		if(eqpnames!=null && eqpnames.size()>0){
			for(int i=0;i<eqpnames.size();i++){
				eqp=new MdEquipment();
				Object [] arr=(Object[])eqpnames.get(i);
				if(arr[0]!=null){
					eqp.setId(arr[0].toString());
				}
				if(arr[1]!=null){
					eqp.setEquipmentName(arr[1].toString());
				}
				eqps.add(eqp);
			}
		}
		return eqps;
	}
	@Override
	public MatUnitBean getUnitByworkorder(String wokorder, String matId) {
		String materialid="";
		String materialclass="";
		String materialgrptype="";
		String materialtypeid="";
		String hql = "select o.mdUnit from SchWorkorderBom o where o.schWorkorder.id=? and o.mdMat.id=?";
		MdUnit obj = (MdUnit)schStatOutputDao.unique(hql,wokorder,matId);
		String sql = "select mm.code,mmg.des,mmg.id as mmgid,mmt.id from MD_MAT mm,MD_MAT_TYPE mmt,MD_MAT_GRP mmg where 1=1 and mm.tid=mmt.id and mmt.gid=mmg.id and mm.id='"+matId+"'";
		List<?> ls=basedao.queryBySql(sql);
		Object[] o=(Object[])ls.get(0);
		if(StringUtil.notNull(o[0].toString())){
			materialid=o[0].toString();
		}
		if(StringUtil.notNull(o[1].toString())){
			materialclass=o[1].toString();
		}
		if(StringUtil.notNull(o[2].toString())){
			materialgrptype=o[2].toString();
		}
		if(StringUtil.notNull(o[3].toString())){
			materialtypeid=o[3].toString();
		}
		//System.out.println("产出物料编号为："+);
		System.out.println();
		System.out.println();
		System.out.println();
		return new MatUnitBean(obj.getId(), obj.getName(),obj.getCode(),materialid,materialclass,materialgrptype,materialtypeid);
	}
	
}
