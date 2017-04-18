package com.shlanbao.tzsc.pms.equ.sbglplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.dao.EqmWheelCovelParamDaoI;
import com.shlanbao.tzsc.base.dao.EqmWheelCovelPlanDaoI;
import com.shlanbao.tzsc.base.dao.EqmipmentsDaoI;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EqmPlanBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.service.EqmPlanServiceI;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.BatchWCPlan;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeChildBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmPlanServiceImpl extends BaseService implements EqmPlanServiceI {
	
	@Autowired
	private EqmWheelCovelPlanDaoI eqmWheelCovelPlanDao;
	
	@Autowired
	private EqmWheelCovelParamDaoI eqmWheelCovelParamDao;
	
	
	@Autowired
	private EqmipmentsDaoI equipmentsDao;

	@Override
	public DataGrid queryEqu(EquipmentsBean equBean, PageParams pageParams) throws Exception {
		//hql 语句拼装
		String hql = "from MdEquipment o "
				+ "left join fetch o.mdEqpType met "
				+ "left join fetch met.mdEqpCategory mc "
				+ "where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(equBean.getEquipmentCode())){
			params.append(" and o.equipmentCode like '%"+ equBean.getEquipmentCode() +"%'");
		}
		if(StringUtil.notNull(equBean.getEquipmentName())){
			params.append(" and o.equipmentName like '%"+ equBean.getEquipmentName() +"%'");
		}
		//加入两个条件
		if(StringUtil.notNull(equBean.getWorkShopId())){
			params.append(" and o.mdWorkshop.id='"+ equBean.getWorkShopId() +"'");
		}
			
		if(StringUtil.notNull(equBean.getWorkShopName())){//1卷包车间2成型车间也是对象id
			params.append(" and o.mdWorkshop.id='"+equBean.getWorkShopName()+"'");
		}
		
		if(StringUtil.notNull(equBean.getEqpTypeName())){//传进来Id根据对象比较
			params.append(" and o.mdEqpType.id='"+equBean.getEqpTypeName()+"'");
		}
		
		String param = params.toString();
		List<MdEquipment> rows = equipmentsDao.queryByPage(hql.concat(param), pageParams);
		List<EquipmentsBean> equBeans = new ArrayList<EquipmentsBean>();
		for(MdEquipment me : rows){
			String typeId = me.getMdEqpType().getId();
			String typeName = me.getMdEqpType().getName();
			EquipmentsBean eb = new EquipmentsBean();
			BeanConvertor.copyProperties(me, eb);
			eb.setEqpTypeId(typeId);
			eb.setEqpTypeName(typeName);
			if(null!=me.getMdWorkshop()){
				eb.setWorkShopId(me.getMdWorkshop().getId());
				eb.setWorkShopName(me.getMdWorkshop().getName());
			}
			if(null!=me.getMdEqpType()&&null!=me.getMdEqpType().getMdEqpCategory()){
				eb.setMdEqpCategoryId(me.getMdEqpType().getMdEqpCategory().getId());//设备类型
				eb.setMdEqpCategoryCode(me.getMdEqpType().getMdEqpCategory().getCode());//设备类型
				eb.setMdEqpCategoryName(me.getMdEqpType().getMdEqpCategory().getName());//设备类型
			}
			equBeans.add(eb);
		}
		hql = "select count(*) from MdEquipment o where 1=1 and o.del=0 ";
		long total = equipmentsDao.queryTotal(hql.concat(param));
		return new DataGrid(equBeans,total);
	}
	/**
	 * 根据设备型号ID查询对应的轮保规则
	 * @return
	 */
	@Override
	public DataGrid queryEqpTypeChild(EquipmentsBean equBean,PageParams pageParams)throws Exception{
		
		StringBuffer sql=new StringBuffer();
		sql.append("select sec.id,sec.CODE, sec.NAME,metc.SEC_PID,metc.MET_PID,metc.TYPE,metc.ID as METC_ID ");
		sql.append("from MD_EQP_TYPE_child metc left join SYS_EQP_CATEGORY sec on sec.id = metc.sec_pid ");
		sql.append("where metc.del=0 and metc.MET_PID=?  ");	
		List<Object> objs = new ArrayList<Object>();
		objs.add(equBean.getQueryEqpTypeId());
		if(null!=equBean.getQueryType()&&!"".equals(equBean.getQueryType())){
			sql.append("and metc.TYPE=?  ");
			objs.add(equBean.getQueryType());
		}
		List<?> list = eqmWheelCovelPlanDao.queryBySql(sql.toString(), objs);
		List<MdEqpTypeChildBean> lastList = new ArrayList<MdEqpTypeChildBean>();
		if(null!=list&&list.size()>0){
			MdEqpTypeChildBean childBean = null;
			for(int i=0;i<list.size();i++){
				childBean = new MdEqpTypeChildBean();
				Object[] arr=(Object[]) list.get(i);
				childBean.setSecId(ObjectUtils.toString(arr[0]));
				childBean.setSecCode(ObjectUtils.toString(arr[1]));
				childBean.setSecName(ObjectUtils.toString(arr[2]));
				childBean.setSecPid(ObjectUtils.toString(arr[3]));
				childBean.setMetPid(ObjectUtils.toString(arr[4]));
				childBean.setType(ObjectUtils.toString(arr[5]));
				childBean.setMetcId(ObjectUtils.toString(arr[6]));
				lastList.add(childBean);
			}
		}
		return new DataGrid(lastList, 100L);
	}
	
	
	@Override
	public EqmWheelCovelPlan addWCPlan(EqmPlanBean wcpBean, String userId) throws Exception {
		EqmWheelCovelPlan wcp = new EqmWheelCovelPlan();
		wcp.setId(UUID.randomUUID().toString());
		
		SysUser addUser = new SysUser();
		addUser.setId(userId);
		wcp.setSysUserByCreateId(addUser);//创建人
		
		MdEquipment mdEquipment = new MdEquipment();//设备
		mdEquipment.setId(wcpBean.getEquipmentId());//设备ID,设备主数据中的ID
		wcp.setMdEquipment(mdEquipment);
		
		MdShift mdShift = new MdShift();//班次
		mdShift.setId(wcpBean.getMdShiftId());
		wcp.setMdShift(mdShift);
		wcp.setPlanNo(StringUtil.trim(wcpBean.getPlanCode()));//计划编号
		wcp.setPlanName(StringUtil.trim(wcpBean.getPlanName()));//计划名称

		wcp.setScheduleDate(DateUtil.strToDate(wcpBean.getScheduleDate(), "yyyy-MM-dd"));//计划日期
		wcp.setScheduleStrDate(wcpBean.getScheduleDate());//计划日期
		wcp.setScheduleEndDate(DateUtil.strToDate(wcpBean.getScheduleDate(), "yyyy-MM-dd"));//计划结束时间
		wcp.setMaintenanceLength(wcpBean.getEquipmentMinute());//计划时长
		wcp.setMaintenanceContent(StringUtil.trim(wcpBean.getMaintenanceContent()));//计划内容
		wcp.setPlanner(userId);//制单人
		wcp.setMaintenanceType(wcpBean.getMaintenanceType());//计划类型
		wcp.setWheelParts(wcpBean.getMetcId());//轮保部件
		wcp.setCreateDate(new Date());//创建日期
		wcp.setWheelCoverType(wcpBean.getWheelCoverType());//工单状态  类型
		wcp.setDel(wcpBean.getDel());
		wcp.setMdMatId(wcpBean.getMatId());//牌号
		//private String period;//周期
		//private String remindCycle;//提醒周期
		//private String dutyPeopleName;//责任人id
		//private Date endMaintainDate;//最后维修日期
		//private String locationCode;//车间代码
		//private SysUser sysUserByDutyPeopleId;//责任人
		//private Date startTime;//实际开始时间
		//private Date endTime;//实际结束时间
		eqmWheelCovelPlanDao.save(wcp);
		return wcp;
	}
	/**
	 * 添加计划对应的设备
	 * @param planBean
	 * @throws Exception
	 */
	@Override
	public void addEqmWheelCovelParam(EqmWheelCovelPlan planBean,String ruleId) throws Exception{
		//新增保修项
		StringBuffer insertSql = new StringBuffer();
		insertSql.append(" insert into EQM_WHEEL_COVEL_PARAM ");
		insertSql.append(" select newid() as ID,'").append(planBean.getId()).append("'PID,null ACTUAL_TIME,null USER_ID,")
		.append("'").append(planBean.getScheduleStrDate()).append("'PLAN_TIME,'0'ENABLE,ID as SET_ID,''REMARKS ")
		.append(",null CZG_USERID,null CZG_DATE,null LBG_USERID,null LBG_DATE,null SHG_USERID,null SHG_DATE,null FIX_TYPE,'0' status")
		.append(" from SYS_EQP_TYPE "); 
		insertSql.append(" where DEL=0 and cid='"+ruleId+"' "); 
		
		try{
			eqmWheelCovelPlanDao.updateInfo(insertSql.toString(),null);
		}catch(Exception e){
			log.error("新增数据失败:"+e.getMessage());
		}
	}
	
	@Override
	public void editWCPlan(EqmPlanBean wcpBean, String userId) throws Exception {
		
		EqmWheelCovelPlan wcp = eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, wcpBean.getPlanId());
		MdEquipment mdEquipment = new MdEquipment();//设备
		mdEquipment.setId(wcpBean.getEquipmentId());//设备ID,设备主数据中的ID
		wcp.setMdEquipment(mdEquipment);
		
		MdShift mdShift = new MdShift();//班次
		mdShift.setId(wcpBean.getMdShiftId());
		wcp.setMdShift(mdShift);
		wcp.setPlanNo(StringUtil.trim(wcpBean.getPlanCode()));//计划编号
		wcp.setPlanName(StringUtil.trim(wcpBean.getPlanName()));//计划名称

		wcp.setScheduleDate(DateUtil.strToDate(wcpBean.getScheduleDate(), "yyyy-MM-dd"));//计划日期
		wcp.setScheduleEndDate(DateUtil.strToDate(wcpBean.getScheduleDate(), "yyyy-MM-dd"));//计划结束时间
		wcp.setMaintenanceLength(wcpBean.getEquipmentMinute());//计划时长
		wcp.setMaintenanceContent(StringUtil.trim(wcpBean.getMaintenanceContent()));//计划内容
		//wcp.setPlanner(userId);//制单人
		wcp.setMaintenanceType(wcpBean.getMaintenanceType());//计划类型
		wcp.setWheelParts(wcpBean.getMetcId());//轮保部件
		wcp.setEndMaintainDate(new Date());//最后修改时间
		//wcp.setWheelCoverType(wcpBean.getWheelCoverType());//工单状态  类型
		wcp.setDel(wcpBean.getDel());
		wcp.setMdMatId(wcpBean.getMatId());//牌号
		try{
			//如果编号 不一样 删除保修项 后 新增保修项 
			String delSql = "delete from EQM_WHEEL_COVEL_PARAM where pid='"+wcp.getId()+"' ";
			eqmWheelCovelPlanDao.updateInfo(delSql,null);
			//新增保修项
			StringBuffer insertSql = new StringBuffer();
			insertSql.append(" insert into EQM_WHEEL_COVEL_PARAM ");
			insertSql.append(" select newid() as ID,'").append(wcp.getId()).append("'PID,null ACTUAL_TIME,null USER_ID,")
			.append("'").append(wcpBean.getScheduleDate()).append("'PLAN_TIME,'0'ENABLE,ID as SET_ID,''REMARKS ")
			.append(",null CZG_USERID,null CZG_DATE,null LBG_USERID,null LBG_DATE,null SHG_USERID,null SHG_DATE ")
			.append(" from SYS_EQP_TYPE "); 
			insertSql.append("where DEL=0 and cid=(select sec_pid from MD_EQP_TYPE_child where DEL=0 and id='")
					.append(wcp.getWheelParts()).append("') "); 
			try{
				eqmWheelCovelPlanDao.updateInfo(insertSql.toString(),null);
			}catch(Exception e){
				log.error("新增数据失败:"+e.getMessage());
			}
		}catch(Exception e){
			log.error("删除数据失败:"+e.getMessage());
		}
		
		/*审核人 AUDIT_USERID
				AUDIT_TIME
		批准人 RATIFY_USERID
		RATIFY_TIME*/
		//private String period;//周期
		//private String remindCycle;//提醒周期
		//private String dutyPeopleName;//责任人id
		//private String locationCode;//车间代码
		//private SysUser sysUserByDutyPeopleId;//责任人
		//private Date startTime;//实际开始时间
		//private Date endTime;//实际结束时间
	}
	
	@Override
	public EqmPlanBean getById(String id) throws Exception{
		EqmPlanBean bean = new EqmPlanBean();
		EqmWheelCovelPlan wcp = eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class,id);
		bean.setPlanId(wcp.getId());
		bean.setPlanCode(wcp.getPlanNo());//计划编号
		bean.setPlanName(wcp.getPlanName());
		if(null!=wcp.getMdEquipment()){
			bean.setEquipmentId(wcp.getMdEquipment().getId());//设备ID
			bean.setEquipmentName(wcp.getMdEquipment().getEquipmentName());//设备名称
			if(null!=wcp.getMdEquipment().getMdEqpType()){
				bean.setEquipmentXhId(wcp.getMdEquipment().getMdEqpType().getId());//设备型号 ZJ17
				bean.setEquipmentXhName(wcp.getMdEquipment().getMdEqpType().getName());
			}
			if(null!=wcp.getMdEquipment().getMdEqpType().getMdEqpCategory()){
				bean.setEquipmentLxId(wcp.getMdEquipment().getMdEqpType().getMdEqpCategory().getId());//设备类型 卷烟机组
				bean.setEquipmentLxName(wcp.getMdEquipment().getMdEqpType().getMdEqpCategory().getName());
			}
		}
		bean.setPlanner(wcp.getPlanner());//制单人
		bean.setMaintenanceType(wcp.getMaintenanceType());//轮保类别
		bean.setEquipmentMinute(wcp.getMaintenanceLength());//轮保时长
		bean.setScheduleDate(DateUtil.datetoStr(wcp.getScheduleDate(), "yyyy-MM-dd"));//轮保日期 计划日期
		bean.setMatId(wcp.getMdMatId());//牌号
		bean.setMdShiftId(wcp.getMdShift().getId());//班次
		bean.setMaintenanceContent(wcp.getMaintenanceContent());//备注
		bean.setMetcId(wcp.getWheelParts());//轮保部位;绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
		bean.setWheelCoverType(wcp.getWheelCoverType());//工单状态
		bean.setDel(wcp.getDel());
		return bean;
	}
	
	@Override
	public void updateWCPlanStatus(String id, String statusId, String userId) {
		EqmWheelCovelPlan bean=eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id);
		bean.setWheelCoverType(statusId);
		if("1".equals(statusId)){//审核
			bean.setAuditUserId(userId);
			bean.setAuditTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
		}else if("2".equals(statusId)){//批准
			bean.setRatifyUserId(userId);
			bean.setRatifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
		}
	}
	
	@Override
	public void batchUpdateWCPlanStatus(String ids, String statusId, String userName) {
		for(String id:StringUtil.splitToStringList(ids, ",")){
			EqmWheelCovelPlan bean=eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id);
			/*String sql = "select * from SYS_USER_ROLE WHERE UID_="+userId;
			PageParams params = new PageParams(); 
			List role = eqmWheelCovelPlanDao.queryBySql(sql, params);*/
			if("1".equals(statusId)&&bean.getWheelCoverType().equals("0")){//审核--主任审核
				bean.setWheelCoverType(statusId);
				bean.setzrName(userName);
				bean.setAuditTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				eqmWheelCovelPlanDao.update(bean);
			}else if("2".equals(statusId)&&bean.getWheelCoverType().equals("1")){//批准运行--管理批准
				bean.setWheelCoverType(statusId);
				bean.setglName(userName);
				bean.setRatifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				eqmWheelCovelPlanDao.update(bean);
			}
			//删除状态
			if(bean.getWheelCoverType().equals("9")){//删除
				bean.setWheelCoverType("10");
				bean.setzrName(userName);
				bean.setAuditTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				eqmWheelCovelPlanDao.update(bean);
			}else if(bean.getWheelCoverType().equals("10")){//删除
				bean.setglName(userName);
				bean.setRatifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				eqmWheelCovelPlanDao.update(bean);
			}
			//编辑状态
			if(bean.getWheelCoverType().equals("11")){//修改状态
				bean.setWheelCoverType("12");
				bean.setzrName(userName);
				bean.setAuditTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				eqmWheelCovelPlanDao.update(bean);
			}else if(bean.getWheelCoverType().equals("12")){//修改状态
				bean.setglName(userName);
				bean.setRatifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				eqmWheelCovelPlanDao.update(bean);
			}
		}
	}
	
	@Override
	public DataGrid queryBeanList(EqmWheelCovelParamBean bean)throws Exception{
		
		StringBuffer sql=new StringBuffer();
		sql.append("select syset.CODE,syset.NAME,syset.DES,syset.LX_TYPE, ");
		sql.append("ewcp.status,ewcp.id,ewcp.pid,ewcp.ACTUAL_TIME,ewcp.USER_ID,su.NAME as USER_NAME,");
		sql.append("ewcp.PLAN_TIME,ewcp.REMARKS,ewcp.SET_ID ");
		sql.append("from EQM_WHEEL_COVEL_PARAM ewcp  ");
		sql.append("left join SYS_USER su on ewcp.USER_ID=su.id  ");
		sql.append("left join SYS_EQP_TYPE syset on ewcp.SET_ID=syset.id  ");
		if(null!=bean.getPid()&&!"".equals(bean.getPid())){
			sql.append("where ewcp.PID='"+bean.getPid()+"' ");//这个条件是肯定的
		}
		if("all".equals(bean.getLxType())){
			
		}else if(null!=bean.getLxType()&&!"".equals(bean.getLxType())){//角色
			sql.append(" and syset.LX_TYPE='"+bean.getLxType()+"' ");
		}
		sql.append("order by syset.CODE desc ");
		
		List<Object> objs = new ArrayList<Object>();
		List<?> list = eqmWheelCovelPlanDao.queryBySql(sql.toString(), objs);
		List<EqmWheelCovelParamBean> lastList = new ArrayList<EqmWheelCovelParamBean>();
		if(null!=list&&list.size()>0){
			EqmWheelCovelParamBean childBean = null;
			for(int i=0;i<list.size();i++){
				childBean = new EqmWheelCovelParamBean();
				Object[] arr=(Object[]) list.get(i);
				childBean.setSbCode(ObjectUtils.toString(arr[0]));//设备CODE
				childBean.setSbName(ObjectUtils.toString(arr[1]));//设备名称
				childBean.setSbDes(ObjectUtils.toString(arr[2]));//设备描述
				String sqlRole ="SELECT name from SYS_ROLE where id='"+arr[3]+"'";
				List<?> listRle = eqmWheelCovelPlanDao.queryBySql(sqlRole);
				if(listRle.size()>0){
					childBean.setLxType(ObjectUtils.toString(listRle.get(0)));//角色ID
				}
				String isFinsh = ObjectUtils.toString(arr[4]);
				childBean.setEnable(isFinsh);//是否完成
				if("1".equals(isFinsh)){//完成
					childBean.setIsCheck("true");
				}else{
					childBean.setIsCheck("false");
				}
				childBean.setId(ObjectUtils.toString(arr[5]));//设备主键ID
				childBean.setSbPid(ObjectUtils.toString(arr[6]));//PID 计划ID
				childBean.setActualStrTime(ObjectUtils.toString(arr[7]));//计划开始时间
				childBean.setUserId(ObjectUtils.toString(arr[8]));//设备保养人
				childBean.setUserName(ObjectUtils.toString(arr[9]));//设备保养人
				childBean.setPlanTimeStr(ObjectUtils.toString(arr[10]));
				childBean.setRemarks(ObjectUtils.toString(arr[11]));//备注
				childBean.setSetId(ObjectUtils.toString(arr[12]));//数据字典 维护项目主键ID
				lastList.add(childBean);
			}
		}
		Long len = Long.parseLong(String.valueOf(lastList.size()));
		return new DataGrid(lastList, len);
	}
	
	/**
	 * 功能说明：设备轮保-批量添加
	 * 
	 * */
	@Override
	public String saveWcPlanf(EqmWheelCovelPlan ep) {
		String id=eqmWheelCovelPlanDao.saveBackKey(ep);
		return id;
	}
	
	/**
	 * type lb -轮保
	 * 
	 * met_pid -md_eqp_type表ID
	 * sec_pid -
	 * 
	 * */
	@Override
	public List<?> queryEqpTypeChild(BatchWCPlan b) {
		String sql="select * from MD_EQP_TYPE_CHILD where sec_pid='"+b.getRuleId()+"' and type='lb' and met_pid='"+b.getEqp_typeId()+"'";
		List<?> list=equipmentsDao.queryBySql(sql);
		return list;
	}
	
	/**
	 * 查询SYS_EQP_TYPE
	 * 
	 * */
	@Override
	public Map<String, String> querySysEqpType(BatchWCPlan b) {
		String sql="select id,cid from SYS_EQP_TYPE ";
		List<?> list=equipmentsDao.queryBySql(sql);
		Map<String, String> map=new HashMap<String, String>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			map.put(temp[0].toString(), temp[1].toString());
		}
		return map;
	}
	
	@Override
	public void saveWcParam(EqmWheelCovelParam ecpb) {
		eqmWheelCovelParamDao.save(ecpb);
	}
	
	@Override
	public void deleteWCPlan(String id){
		EqmWheelCovelPlan bean=eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id);
		if(bean.getWheelCoverType().equals("0")){
			bean.setWheelCoverType("9");
		}
	}
	@Override
	public void edit(String id){
		EqmWheelCovelPlan bean=eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id);
		if(bean.getWheelCoverType().equals("0")){
			bean.setWheelCoverType("11");
		}
	}
}
