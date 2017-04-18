package com.shlanbao.tzsc.wct.eqm.checkplan.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmCheckcatParamDaoI;
import com.shlanbao.tzsc.base.dao.EqmCheckcatPlanDaoI;
import com.shlanbao.tzsc.base.dao.EqmCullRecordDaoI;
import com.shlanbao.tzsc.base.dao.EqmPaulDayDaoI;
import com.shlanbao.tzsc.base.dao.EqmProtectRecordDaoI;
import com.shlanbao.tzsc.base.dao.EqmSpotchRecordDaoI;
import com.shlanbao.tzsc.base.dao.SysServiceInfoDaoI;
import com.shlanbao.tzsc.base.mapping.EqmCheckcatParam;
import com.shlanbao.tzsc.base.mapping.EqmCheckcatPlan;
import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.EqmProtectRecord;
import com.shlanbao.tzsc.base.mapping.EqmSpotchRecode;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.paul.beans.EqmPaulDayBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmCheckParamBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmProtectRecordPlan;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckRecordBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.service.EqpCheckServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class EqpCheckServiceImpl extends BaseService implements EqpCheckServiceI {
	@Autowired 
	private EqmCheckcatPlanDaoI  eqmCheckcatPlanDao;
	@Autowired
	private EqmCheckcatParamDaoI eqmCheckcatParamDao;
	@Autowired
	private EqmPaulDayDaoI eqmProtectDao;
	@Autowired
	private EqmProtectRecordDaoI eqmProtectRecordDao;
	@Autowired
	private EqmSpotchRecordDaoI eqmSpotchRecordDao;
	@Autowired
	private EqmCullRecordDaoI eqmCullRecordDao;
	@Autowired
	private SysServiceInfoDaoI sysSeviceInfo;
	
	@Override
	public DataGrid getEqpList(EqpCheckBean eqmBean, int pageIndex)throws Exception {
		StringBuilder hql=new StringBuilder();
		hql.append(" from EqmCheckcatPlan e where 1=1 and e.del=0 ");
		if(StringUtil.notNull(eqmBean.getMdEquipmentId())){//机台
			hql.append(" and e.mdEquipment.id='"+eqmBean.getMdEquipmentId()+"' ");
		}
		//时间
		hql.append(StringUtil.fmtDateBetweenParams("e.scheduleDate",eqmBean.getStartTime(), eqmBean.getEndTime()));
		
		List<EqmCheckcatPlan> eqmWheelCovelPlans=  eqmCheckcatPlanDao.queryByPage(hql.toString(), pageIndex, 10);
		List<EqpCheckBean> covelPlanBeans=new ArrayList<EqpCheckBean>();
		if(eqmWheelCovelPlans!=null){
			for (EqmCheckcatPlan eqmPlan : eqmWheelCovelPlans) {
				EqpCheckBean covelPlan=beanConvertor.copyProperties(eqmPlan, EqpCheckBean.class);
				if(null!=eqmPlan.getMdEquipment()){//机台
					covelPlan.setMdEquipmentId(eqmPlan.getMdEquipment().getId());
					covelPlan.setEquipmentName(eqmPlan.getMdEquipment().getEquipmentName());
				}
				if(null!=eqmPlan.getMdShift()){//班次
					covelPlan.setMdShiftName(eqmPlan.getMdShift().getName());
					covelPlan.setMdShiftId(eqmPlan.getMdShift().getId());
				}
				if(null!=eqmPlan.getSysUserByCreateId()){//制单人
					covelPlan.setSysUserByCreateName(eqmPlan.getSysUserByCreateId().getName());
					covelPlan.setSysUserByCreate(eqmPlan.getSysUserByCreateId().getId());
				}
				if(null!=eqmPlan.getMdEquipment()&&null!=eqmPlan.getMdEquipment().getMdWorkshop()){//车间
					covelPlan.setWorkShopId(eqmPlan.getMdEquipment().getMdWorkshop().getId());
					covelPlan.setWorkShopName(eqmPlan.getMdEquipment().getMdWorkshop().getName());
				}
				covelPlanBeans.add(covelPlan);
			}
			String str="select count(*) ";
			long total=eqmCheckcatPlanDao.queryTotal(str+hql.toString());
			
			return new DataGrid(covelPlanBeans, total);
		}
		return null;
	}
	//查询
	@Override
	public DataGrid queryParamList(EqmCheckParamBean bean,int pageIndex){
		StringBuffer hql=new StringBuffer();
		hql.append("from EqmCheckcatParam o where 1=1 ");
		//班次
		if(StringUtil.notNull(bean.getShiftName())){
			hql.append("and o.checkcatPid.mdShift.id = '"+bean.getShiftName()+"' ");
		}
		if(StringUtil.notNull(bean.getUserId())){
			hql.append("and o.user.id = '"+bean.getUserId()+"' ");
		}
		//设备Id
		if(StringUtil.notNull(bean.getEqpId())){
			hql.append("and o.checkcatPid.mdEquipment.id = '"+bean.getEqpId()+"' ");
		}
		//保养名称
		if(StringUtil.notNull(bean.getJobName())){
			hql.append("and o.checkcatPid.planName = '"+bean.getJobName()+"' ");
		}
		if(StringUtil.notNull(bean.getPid())){
			hql.append("and o.checkcatPid.id = '"+bean.getPid()+"' ");
		}
		//保养类型
		if(StringUtil.notNull(bean.getJobType())){
			hql.append("and o.checkcatPid.maintenanceType = '"+bean.getJobType()+"' ");
		}
		//时间比较
		if(bean.getStartTime()!=null){
			hql.append("and convert(varchar(23),o.planTime,23) >= '"+DateUtil.formatDateToString(bean.getStartTime(),"yyyy-MM-dd")+"' ");
		}
		if(bean.getEndTime()!=null){
			hql.append("and convert(varchar(23),o.planTime,23) <= '"+DateUtil.formatDateToString(bean.getEndTime(),"yyyy-MM-dd")+"' ");
		}
		List<EqmCheckcatParam> list=eqmCheckcatParamDao.queryByPage(hql.toString()+" order by o.planTime desc ",pageIndex, 10);
		List<EqmCheckParamBean> returnList=new ArrayList<EqmCheckParamBean>();
		for(EqmCheckcatParam param:list){
			returnList.add(mappingBean(param));
		}
		hql.insert(0,"select count(*) ");
		long total=eqmCheckcatParamDao.queryTotal(hql.toString());
		return new DataGrid(returnList,total);
	}
	 /**
	  * 
	 * @Title: queryParamList 
	 * @Description: 查询设备计划
	 * @param  bean
	 * @return DataGrid    返回类型 
	 * @throws
	  */
	 public DataGrid queryPlanList(EqmCheckParamBean bean,int pageIndex){
		 StringBuffer hql=new StringBuffer();
			hql.append("from EqmCheckcatPlan o ");
			hql.append("left join fetch o.mdEquipment sb "); //设备
			hql.append("left join fetch o.mdShift bc "); //班次
			hql.append("where o.del=0 ");
			List<Object> params = new ArrayList<Object>();
			hql.append("and o.wheelCoverType in('2','4','5','6','7','9','10','8') ");
			if(StringUtil.notNull(bean.getEqpCode())){
				hql.append("and o.mdEquipment.equipmentCode = ? ");
				params.add(bean.getEqpCode());
			}
			//时间比较
			if(StringUtil.notNull(bean.getQueryStartTime())){
				hql.append("and o.scheduleDate>=? ");
				Date dateStr = DateUtil.strToDate(bean.getQueryStartTime()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				params.add(dateStr);
			}
			if(StringUtil.notNull(bean.getQueryEndTime())){
				hql.append("and o.scheduleDate<=? ");
				Date dateStr = DateUtil.strToDate(bean.getQueryEndTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
				params.add(dateStr);
			}
			List<EqmCheckcatPlan> list = eqmCheckcatPlanDao.queryByPage(hql.toString()+" order by o.scheduleDate desc ", pageIndex, 10, params);
			List<EqpCheckBean> returnList=new ArrayList<EqpCheckBean>();
			long total=0;
			String lastHql = hql.toString().replaceAll("left join fetch o.mdEquipment sb", "");
			lastHql = lastHql.replaceAll("left join fetch o.mdShift bc", "");
			total = eqmCheckcatPlanDao.queryTotal("select count(*) "+lastHql,params);
			if(total>0){
				for(EqmCheckcatPlan plan:list){
					EqpCheckBean oneBean = new EqpCheckBean();
					oneBean.setId(plan.getId());//计划主键ID
					oneBean.setPlanNo(plan.getPlanNo());//计划编号
					oneBean.setPlanName(plan.getPlanName());//计划名称
					if(null!=plan.getMdEquipment()){
						oneBean.setMdEquipmentId(plan.getMdEquipment().getId());//设备主数据id
					}
					oneBean.setScheduleDate(DateUtil.datetoStr(plan.getScheduleDate(), "yyyy-MM-dd"));//计划时间
					oneBean.setMaintenanceContent(plan.getMaintenanceContent());//保养备注
					oneBean.setStartTime(DateUtil.datetoStr(plan.getStartTime(),"yyyy-MM-dd HH:mm:ss"));//实际开始时间
					oneBean.setEndTime(DateUtil.datetoStr(plan.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
					if(null!=plan.getMdShift()){
						oneBean.setMdShiftId(plan.getMdShift().getId());//班次
						oneBean.setMdShiftName(plan.getMdShift().getName());
					}
					oneBean.setWheelCoverType(plan.getWheelCoverType());//审核状态:0新增，1审核，2批准
					oneBean.setWheelParts(plan.getWheelParts());//绑定的设备维护项目ID
					if(null!=plan.getCzgUserId()){
						oneBean.setCzgUserId(plan.getCzgUserId().getId());//操作工ID
						oneBean.setCzgUserName(plan.getCzgUserId().getName());
					}
					oneBean.setCzgDate(plan.getCzgDate());//操作工操作时间
					if(null!=plan.getLbgUserId()){
						oneBean.setLbgUserId(plan.getLbgUserId().getId());//轮保工ID
						oneBean.setLbgUserName(plan.getLbgUserId().getName());
					}
					oneBean.setLbgDate(plan.getLbgDate());//轮保工操作时间
					if(null!=plan.getShgUserId()){
						oneBean.setShgUserId(plan.getShgUserId().getId());//审核工姓名
						oneBean.setShgUserName(plan.getShgUserId().getName());//审核工姓名
					}
					oneBean.setShgDate(plan.getShgDate());//审核工操作时间
					oneBean.setIsCzgFinsh(plan.getIsCzgFinsh());//操作工是否完成
					oneBean.setIsLbgFinsh(plan.getIsLbgFinsh());//轮保工是否完成
					oneBean.setIsShgFinsh(plan.getIsShgFinsh());//审核工是否完成
					oneBean.setModifyDate(plan.getModifyDate());
					oneBean.setIsZgCheck(plan.getIsZgCheck());//是否维修主管点检
					oneBean.setCheckNumber(plan.getCheckNumber());//点检次数
					returnList.add(oneBean);
				}
			}
			return new DataGrid(returnList,total);
	 }
	/**
	 * 转换Bean
	 * @param param
	 * @return
	 */
	private EqmCheckParamBean mappingBean(EqmCheckcatParam param){
		EqmCheckParamBean newBean=new EqmCheckParamBean();
		newBean.setActualTime(param.getActualTime());
		newBean.setEnable(param.getEnable());
		newBean.setId(param.getId());
		newBean.setPlanTime(param.getPlanTime());
		if(null!=param.getCheckcatPid()){
			newBean.setJobContext(param.getCheckcatPid().getMaintenanceContent());
			newBean.setJobName(param.getCheckcatPid().getPlanName());
			newBean.setJobType(param.getCheckcatPid().getMaintenanceType());
			newBean.setPid(param.getCheckcatPid().getId());
			if(null!=param.getCheckcatPid().getMdShift()){
				newBean.setShiftName(param.getCheckcatPid().getMdShift().getName());
			}
			newBean.setEqpName(param.getCheckcatPid().getMdEquipment().getEquipmentName());
		}
		if(null!=param.getUser())
		newBean.setUserName(param.getUser().getName());
		return newBean;
	}
	
	@Override
	public boolean updateBean(EqmCheckcatParam bean){
		return eqmCheckcatParamDao.saveOrUpdate(bean);
	}
	//根据Id查询Bean
	public EqmCheckcatParam findById(String id){
		return eqmCheckcatParamDao.findById(EqmCheckcatParam.class, id);
	}
	
	//根据设备大类 查询其以下的所有维护项
	@Override
	public DataGrid queryParts(EqmCheckParamBean bean,PageParams pageParams)throws Exception{
		//获取数据字典数据
		String sysSql ="select id,CODE,NAME from SYS_EQP_TYPE where CID=? ";//and DEL='0' 
		List<Object> sysObjs = new ArrayList<Object>();
		sysObjs.add(bean.getQuerySysCId());
		List<?> sysList = eqmCheckcatParamDao.queryBySql(sysSql, sysObjs);
		Map<String ,String> map = new HashMap<String ,String>();
		if(null!=sysList&&sysList.size()>0){
			for(int i=0;i<sysList.size();i++){
				Object[] arr=(Object[]) sysList.get(i);
				String key = ObjectUtils.toString(arr[0]);
				String value = ObjectUtils.toString(arr[2]);
				map.put(key, value);
			}
		}
		//end
		StringBuffer sql=new StringBuffer();
		sql.append("select syset.CODE,syset.NAME,syset.DES,syset.LX_TYPE, ");//4个
		sql.append("ewcp.ENABLE,ewcp.id,ewcp.pid,ewcp.ACTUAL_TIME,ewcp.USER_ID,su.NAME as USER_NAME,");//6个
		sql.append("ewcp.PLAN_TIME,ewcp.REMARKS,ewcp.SET_ID ");//3个
		sql.append(",ewcp.CZG_USERID,ewcp.CZG_DATE,ewcp.LBG_USERID,ewcp.LBG_DATE,ewcp.SHG_USERID,ewcp.SHG_DATE ");//7个
		sql.append(",syset.DJ_TYPE,syset.DJ_METHOD_ID,ewcp.NUMBER ");//3个 点检角色、点检方式、检查次数
		sql.append("from EQM_CHECKCAT_PARAM ewcp  ");
		sql.append("left join SYS_USER su on ewcp.USER_ID=su.id  ");
		sql.append("left join SYS_EQP_TYPE syset on ewcp.SET_ID=syset.id  ");
		if(null!=bean.getPid()&&!"".equals(bean.getPid())){
			sql.append("where ewcp.PID='"+bean.getPid()+"' ");//这个条件是肯定的
		}
		if("all".equals(bean.getDjType())||"shg".equals(bean.getDjType())){
		}else if(null!=bean.getDjType()&&!"".equals(bean.getDjType())){//角色 
			sql.append(" and syset.DJ_TYPE='"+bean.getDjType()+"' ");
		}
		sql.append("order by syset.CODE desc ");
		
		List<Object> objs = new ArrayList<Object>();
		List<?> list = eqmCheckcatParamDao.queryBySql(sql.toString(), objs);
		List<EqmCheckParamBean> lastList = new ArrayList<EqmCheckParamBean>();
		if(null!=list&&list.size()>0){
			EqmCheckParamBean childBean = null;
			for(int i=0;i<list.size();i++){
				childBean = new EqmCheckParamBean();
				Object[] arr=(Object[]) list.get(i);
				childBean.setSbCode(ObjectUtils.toString(arr[0]));//设备CODE
				childBean.setSbName(ObjectUtils.toString(arr[1]));//设备名称
				childBean.setSbDes(ObjectUtils.toString(arr[2]));//设备描述
				childBean.setLxType(ObjectUtils.toString(arr[3]));//设备类型
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
				childBean.setCzgUserId(ObjectUtils.toString(arr[13]));//操作工ID
				childBean.setCzgDate(ObjectUtils.toString(arr[14]));//操作工操作时间
				childBean.setLbgUserId(ObjectUtils.toString(arr[15]));//轮保工ID
				childBean.setLbgDate(ObjectUtils.toString(arr[16]));//轮保工操作时间
				childBean.setShgUserId(ObjectUtils.toString(arr[17]));//审核工ID
				childBean.setShgDate(ObjectUtils.toString(arr[18]));//审核工操作时间
				childBean.setDjType(ObjectUtils.toString(arr[19]));//点检角色
				childBean.setDjMethodId(ObjectUtils.toString(arr[20]));
				childBean.setNumber(ObjectUtils.toString(arr[21]));//检查次数
				String[] djMds =childBean.getDjMethodId().split(",");//分组 并到map中去替换成中文字符
				String values = "";
				if(null!=djMds&&!"".equals(djMds)){
					for(int j=0;j<djMds.length;j++){
						String key = djMds[j];
						if(null!=key&&!"".equals(key)){
							values += map.get(key);
							if(j<(djMds.length-1)){
								values +=",";
							}
						}
					}
				}
				childBean.setDjMethodName(values);
				lastList.add(childBean);
			}
		}
		Long len = Long.parseLong(String.valueOf(lastList.size()));
		return new DataGrid(lastList, len);
	}
	 /**
	  * 根据设备项目做备注更新
	  * @param eqpBean
	  * @param login
	  * @throws Exception
	  */
	 public void editBean(EqmCheckParamBean[] eqpBean,LoginBean login) throws Exception{
		 String planId="";
		 String roleType="";
		 int updateCount =0;
		 for(EqmCheckParamBean bean :eqpBean){//更新备注信息
			 EqmCheckcatParam updateParam=eqmCheckcatParamDao.findById(EqmCheckcatParam.class,bean.getId());
			 updateParam.setRemarks(bean.getRemarks());
			 updateParam.setActualTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			 SysUser user = new SysUser();
			 user.setId(login.getUserId());
			 updateParam.setUser(user);
			 if(null!=updateParam.getNumber()&&!"".equals(updateParam.getNumber())){
				 int count = Integer.parseInt(updateParam.getNumber())+1;
				 updateParam.setNumber(count+""); 
				 updateCount = count;
			 }else{
				 updateParam.setNumber("1");//默认是 1次
				 updateCount = 1;
			 }
			 planId= bean.getPlanId();
			 roleType = bean.getRoleType();//角色类型:0 表示 操作工 1表示 轮保工  all表示即是轮保工又是操作工
			 if("all".equals(roleType)){//角色
				 SysUser userCzg = new SysUser();
				 userCzg.setId(login.getUserId());
				 updateParam.setCzgUserId(userCzg);//操作工ID
				 updateParam.setCzgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//操作工操作时间
				 
				 SysUser userLbg = new SysUser();
				 userLbg.setId(login.getUserId());
				 updateParam.setLbgUserId(userLbg);//轮保工ID
				 updateParam.setLbgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//轮保工操作时间
			 }else if("czg".equals(roleType)){
				 SysUser userCzg = new SysUser();
				 userCzg.setId(login.getUserId());
				 updateParam.setCzgUserId(userCzg);//操作工ID
				 updateParam.setCzgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//操作工操作时间
			 }else if("lbg".equals(roleType)){
				 SysUser userLbg = new SysUser();
				 userLbg.setId(login.getUserId());
				 updateParam.setLbgUserId(userLbg);//轮保工ID
				 updateParam.setLbgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//轮保工操作时间
			 }else if("shg".equals(roleType)){
				 SysUser userShg = new SysUser();
				 userShg.setId(login.getUserId());
				 updateParam.setShgUserId(userShg);//审核工ID
				 updateParam.setShgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//审核工操作时间
				 updateParam.setEnable("1");//审核完毕 即 表示完成
			 }else if("wxzg".equals(roleType)){
				 if(null==updateParam.getCzgUserId()||"".equals(updateParam.getCzgUserId())){
					 SysUser userCzg = new SysUser();
					 userCzg.setId(login.getUserId());
					 updateParam.setCzgUserId(userCzg);//操作工操作时间 即 维修主管 第一次点检
					 updateParam.setCzgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				 }else{
					 SysUser userShg = new SysUser();
					 userShg.setId(login.getUserId());
					 updateParam.setShgUserId(userShg);//审核工操作时间 即 维修主管 第N次点检
					 updateParam.setShgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					 updateParam.setEnable("1");//审核完毕 即 表示完成
				 }
			 }
		 }
		//回写主表数据
		 EqmCheckcatPlan updatePlan=eqmCheckcatPlanDao.findById(EqmCheckcatPlan.class,planId);
		 
		 updatePlan.setModifyDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//修改时间
		 updatePlan.setCheckNumber(updateCount+""); 
		 if("all".equals(roleType)){//角色
			 updatePlan.setStartTime(new Date());//实际开始时间
			 SysUser userCzg = new SysUser();
			 userCzg.setId(login.getUserId());
			 updatePlan.setCzgUserId(userCzg);//操作工ID
			 updatePlan.setCzgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//操作工操作时间
			 
			 SysUser userLbg = new SysUser();
			 userLbg.setId(login.getUserId());
			 updatePlan.setLbgUserId(userLbg);//轮保工ID
			 updatePlan.setLbgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//轮保工操作时间
			 
			 updatePlan.setIsCzgFinsh("Y");
			 updatePlan.setIsLbgFinsh("Y");
			 updatePlan.setWheelCoverType("6");//状态 6: 操作工、轮保工操作完毕
			 
		 }else if("czg".equals(roleType)){
			 updatePlan.setStartTime(new Date());//实际开始时间
			 SysUser userCzg = new SysUser();
			 userCzg.setId(login.getUserId());
			 updatePlan.setCzgUserId(userCzg);//操作工ID
			 updatePlan.setCzgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//操作工操作时间
			 updatePlan.setIsCzgFinsh("Y");
			 updatePlan.setWheelCoverType("4");//状态 4: 操作工操作完毕
			 
		 }else if("lbg".equals(roleType)){
			 SysUser userCzg = new SysUser();
			 userCzg.setId(login.getUserId());
			 updatePlan.setLbgUserId(userCzg);//轮保工ID
			 updatePlan.setLbgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//轮保工操作时间
			 updatePlan.setIsLbgFinsh("Y");
			 updatePlan.setWheelCoverType("5");//状态 5: 轮保工操作完毕
			 if("Y".equals(updatePlan.getIsCzgFinsh())){
				 updatePlan.setWheelCoverType("6");//状态 6: 操作工、轮保工操作完毕
			 }
		 }else if("shg".equals(roleType)){
			 SysUser userShg = new SysUser();
			 userShg.setId(login.getUserId());
			 updatePlan.setShgUserId(userShg);//审核工ID
			 updatePlan.setShgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//审核工操作时间
			 updatePlan.setIsShgFinsh("Y");
			 updatePlan.setWheelCoverType("7");//状态 7: 审核完毕
			 
		 }else if("finsh".equals(roleType)){
			 updatePlan.setEndTime(new Date());//实际结束时间
			 updatePlan.setWheelCoverType("8");//状态 8: 计划完成
		 }else if("wxzg".equals(roleType)){
			 if(null==updatePlan.getCzgUserId()||"".equals(updatePlan.getCzgUserId())){
				 updatePlan.setStartTime(new Date());//实际开始时间
				 SysUser userCzg = new SysUser();
				 userCzg.setId(login.getUserId());
				 updatePlan.setCzgUserId(userCzg);//操作工操作时间 即 维修主管 第一次点检
				 updatePlan.setCzgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				 updatePlan.setIsCzgFinsh("Y");
				 updatePlan.setWheelCoverType("9");//状态 9: 维修主管第一次点检
			 }else{
				 SysUser userShg = new SysUser();
				 userShg.setId(login.getUserId());
				 updatePlan.setShgUserId(userShg);//审核工操作时间 即 维修主管 第N次点检
				 updatePlan.setShgDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				 updatePlan.setIsShgFinsh("Y");
				 updatePlan.setWheelCoverType("10");//状态 10: 维修主管第N次点检
			 }
		 }
	 }
	
	 /**
	  * 根据计划主键 更新状态
	  * 0 :新增;1:审核通过;2:运行;3:停止运行; 4:操作工操作完毕;5 轮保工操作完毕
	  * 6:操作工、轮保工操作完毕;7:审核完毕;8:计划完成;
	  * @param key
	  * @param loginBean
	  * @return
	  * @throws Exception
	  */
	 public boolean updatePlanStatus(String planId,LoginBean loginBean) throws Exception{
	 	boolean isPlan = false; 
	 	try{
		 	EqmCheckcatPlan updatePlan=eqmCheckcatPlanDao.findById(EqmCheckcatPlan.class,planId);
		 	updatePlan.setEndTime(new Date());
		 	updatePlan.setEndMaintainDate(new Date());
		 	SysUser userEnd = new SysUser();
		 	userEnd.setId(loginBean.getUserId());
		 	updatePlan.setEndUserId(userEnd);
		 	updatePlan.setWheelCoverType("8");
		 	isPlan = true;
	 	}catch(Exception e){
	 		isPlan = false;
	 	}
		 return isPlan;
	 }
	 
	 
	 
	 /**
	 * 功能说明：设备日保管理-查询
	 * @param bean
	 * @param pageIndex
	 * @return
	 * 最后修改人：2015.9.25-张璐，将状态给BEAN得isShow，以便页面判定是否显示保养,优化查询代码
	 * @author zhanglu
	 * @time 2015年7月8日15:39:57
	 * 
	 */
	@Override
	public DataGrid queryProtectList( HttpSession session,EqmPaulDayBean bean, int pageIndex) {
		StringBuffer clumn= new StringBuffer();
		StringBuffer countSql= new StringBuffer();
		StringBuffer listSql = new StringBuffer();
		String stime="";
		String etime="";
		String eqpId=""; //设备类型
		Integer start=1; //
		Integer ednum=1;
		//起始页-每页多少条
		if(pageIndex==1){// 1-10  10-20  20-30
			start=1;
			ednum=10;
		}else{
			ednum=pageIndex*10;
			start=ednum-10+1;
		}
		//获得设备ID
	    LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
	    eqpId=loginBean.getEquipmentId();
		Long total=(long) 0;
		
		listSql.append(" select * from ( ");
		countSql.append(" select count(*) from ( "); //总条数
		clumn.append(" SELECT ROW_NUMBER () OVER (ORDER BY date_p DESC) bh, f.id, c.equipment_name,(SELECT name FROM MD_SHIFT WHERE id = f.shift ) AS shift_name, ");
		clumn.append(" f.date_p, f.stim,f.etim, f.paul_id, f.status,ms.code  FROM MD_SHIFT ms, EQM_PAULDAY f, MD_EQP_CATEGORY a,MD_EQP_TYPE b,MD_EQUIPMENT c where c.eqp_type_id=b.id ");	
		clumn.append(" and b.cid=a.id and f.EQP_TYPE=a.id and ms.ID=f.shift");	
		if(StringUtil.notNull(eqpId)){
			clumn.append(" and c.id='"+eqpId+"'");
		}
		if(StringUtil.notNull(bean.getRuntime())){
			clumn.append(" and convert(varchar(50),date_p,23)>='"+bean.getRuntime()+"' "); 
		}
		if(StringUtil.notNull(bean.getEndtime())){
			clumn.append(" and convert(varchar(50),date_p,23)<='"+bean.getEndtime()+"' ");
		}
		clumn.append(" 	) t where 1=1 "); //总条数
		countSql.append(clumn);
		listSql.append(clumn);
		listSql.append("  and t.bh between "+start+" and "+ednum+"  ORDER BY t.date_p DESC,t.code ASC");
		//查询结果集
		List<?> eqmPaulDayList= eqmProtectDao.queryBySql(listSql.toString());
		//查询总条数
		List<?> tl= eqmProtectDao.queryBySql(countSql.toString());
		total=Long.parseLong(tl.get(0).toString());
		List<EqmProtectRecordPlan> listpl=new ArrayList<EqmProtectRecordPlan>();
		//转换实体
		try {
			if(eqmPaulDayList.size()>0){
				for(Object o:eqmPaulDayList){
					Object[] temp=(Object[]) o;
					EqmProtectRecordPlan b=new EqmProtectRecordPlan();
					b.setId(temp[1].toString());
					if(null!=temp[2]){
						b.setName(temp[2].toString());
					}
					b.setShiftName(temp[3].toString());
					b.setDate_p(temp[4].toString());
					b.setStim(temp[5].toString());
					b.setEtime(temp[6].toString());
					b.setPual_id(temp[7].toString());
					try {
						b.setIsShow(temp[8].toString());
					} catch (Exception e) {
						b.setIsShow("0");
					}
					listpl.add(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataGrid(listpl, total);
		
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(String date) {
		try {
			if(date!=""){
				date=date.substring(0, 11);
				return date;
		    }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("日期时间截取错误 ");
		}
	   return "";
	} 
	 
	//获取当天时间
	public static String getDateToStr(){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
		String dat = dateFormat.format( now );
		return dat;
	}
	
	/**
	 * 功能说明：设备日保管理-操作添加
	 * @param id,uname,uId
	 * @return 
	 * 最后修改人：2015.9.25-张璐，实现将保养计划状态改变，将详细记录插入到历史表中
	 * @author wchuang
	 * @time 2015年7月8日15:39:57
	 * 
	 */
	@Override
	public void addEqpProtect(EqmProtectRecord epr,String id_check,String id_nocheck) {
		String[] checked=id_check.split(",");//选中的ID
		String[] nochecked=id_nocheck.split(",");//未选中得ID
		int st0=0;//未完成
		int st1=1;//已完成
		for(int i=1;i<checked.length;i++){//完成   1完成，0未完成
			/*epr.setPaul_id(checked[i]);
			epr.setStatus(1);
			epr.setId(UUID.randomUUID().toString());
			eqmProtectRecordDao.save(epr);*/
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO EQM_PROTECT_RECORD(id,eqp_id,create_user_id,create_time,end_user_id,end_time,paulday_id,status,end_user_name,shift_id,paul_id) VALUES ");
			sb.append("(");
			sb.append("'"+UUID.randomUUID().toString()+"',");
			sb.append("'"+epr.getEqpId()+"',");
			sb.append("'"+epr.getCreateUserId()+"',");
			sb.append("'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(epr.getCreateTime())+"',");
			sb.append("'"+epr.getEndUserId()+"',");
			sb.append("'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(epr.getEndTime())+"',");
			sb.append("'"+epr.getPauldayId()+"',");
			sb.append("'"+st1+"',");
			sb.append("'"+epr.getEndUserName()+"',");
			sb.append("'"+epr.getShift_id()+"',");
			sb.append("'"+checked[i]+"'");
			sb.append(")");
			eqmProtectRecordDao.updateBySql(sb.toString(), null);
		}
		for(int i=1;i<nochecked.length;i++){//未完成   1完成，0未完成
			/*epr.setPaul_id(nochecked[i]);
			epr.setStatus(0);
			epr.setId(UUID.randomUUID().toString());
			eqmProtectRecordDao.save(epr);*/
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO EQM_PROTECT_RECORD(id,eqp_id,create_user_id,create_time,end_user_id,end_time,paulday_id,status,end_user_name,shift_id,paul_id) VALUES ");
			sb.append("(");
			sb.append("'"+UUID.randomUUID().toString()+"',");
			sb.append("'"+epr.getEqpId()+"',");
			sb.append("'"+epr.getCreateUserId()+"',");
			sb.append("'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(epr.getCreateTime())+"',");
			sb.append("'"+epr.getEndUserId()+"',");
			sb.append("'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(epr.getEndTime())+"',");
			sb.append("'"+epr.getPauldayId()+"',");
			sb.append("'"+st0+"',");
			sb.append("'"+epr.getEndUserName()+"',");
			sb.append("'"+epr.getShift_id()+"',");
			sb.append("'"+nochecked[i]+"'");
			sb.append(")");
			eqmProtectRecordDao.updateBySql(sb.toString(), null);
		}
		String sql="update EQM_PAULDAY set STATUS=3 where id='"+epr.getPauldayId()+"'";
		eqmProtectRecordDao.updateBySql(sql, null);
	}
	
	
	/**
	 * 功能描述：设备点检-查询
	 * @time 2015年9月24日18:03:17
	 * @author wanchanghuang
	 * @return
	 * @throws ParseException 
	 * */
	@Override
	public List<EqpCheckRecordBean> queryTayRecord(EqpCheckRecordBean ecrbean){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT c.*, d.ch_status FROM ( SELECT f.datep, a.eqp_id, ( SELECT EQUIPMENT_name FROM MD_EQUIPMENT WHERE id = a.eqp_id ) AS eqp_name, ");
		sql.append("	( SELECT name FROM 	SYS_ROLE WHERE id = b.dj_type ) AS role_name, a.check_position, 	a.check_standard, a.check_method, b.id, b.dj_type, 	f.shift,(SELECT id FROM SYS_ROLE WHERE id = b.dj_type) AS role_id ");
		sql.append(" FROM ( select pid,eqp_id,check_type,check_position,check_standard,check_method,dicid from EQM_CHECK_PLAN_PARAM where 1=1 " );
			if(StringUtil.notNull( ecrbean.getEqp_id() )){
				sql.append(" and eqp_id='"+ecrbean.getEqp_id()+"' ");
			}	
		sql.append("  ) a, ");
		sql.append("  ( select id,cid,dj_type from SYS_EQP_TYPE where del=0 ");
			if(StringUtil.notNull( ecrbean.getRoles() )){
				String roles=ecrbean.getRoles();
				roles=roles.replaceAll(",", "','");
				sql.append(" and dj_type in ('"+roles+"') ");
			}	
		sql.append(" ) b, ( select id,shift,datep,dicId from EQM_CHECK_PLAN where 1=1 " );	
			if(StringUtil.notNull( ecrbean.getDatep() )){
				sql.append(" and convert(varchar(50),datep,23)>='"+ecrbean.getDatep()+"' ");
			}
			if(StringUtil.notNull( ecrbean.getEtime())){
				sql.append(" and convert(varchar(50),datep,23)<='"+ecrbean.getEtime()+"' ");
			}
			if(StringUtil.notNull( ecrbean.getShift_id() )){
			   sql.append(" and  shift='"+ecrbean.getShift_id()+"' ");
			}
	    sql.append(" ) f ");
		sql.append(" WHERE 	a.dicid = b.id AND f.id = a.pid  and b.cid=f.dicId	) c LEFT JOIN ( SELECT 	* FROM EQM_SPOTCH_RECODE WHERE 	1 = 1 ");
		//sql.append("  AND shift_id = '1' AND eqp_id = '4028998449e5e4270149e62497df0000' AND CONVERT (VARCHAR(50), create_time, 23) = '2015-09-25' ");
			if(StringUtil.notNull( ecrbean.getShift_id() )){
				sql.append(" and  shift_id='"+ecrbean.getShift_id()+"' ");
			}
			if(StringUtil.notNull( ecrbean.getEqp_id() )){
				sql.append(" and eqp_id='"+ecrbean.getEqp_id()+"' ");
			}	
			if(StringUtil.notNull( ecrbean.getDatep() )){
				sql.append(" and convert(varchar(50),create_time,23)>='"+ecrbean.getDatep()+"' ");
			}
			if(StringUtil.notNull( ecrbean.getEtime() )){
				sql.append(" and convert(varchar(50),create_time,23)<='"+ecrbean.getEtime()+"' ");
			}
	    sql.append(" ) d ON c.id = d.set_id ");
		
		List<?> list= eqmCheckcatPlanDao.queryBySql(sql.toString());
		
		List<EqpCheckRecordBean> listEcrb=new ArrayList<EqpCheckRecordBean>();
		if(list.size()>0){
			for(Object o:list){
				EqpCheckRecordBean ecrb=new EqpCheckRecordBean();
				Object[] temp=(Object[]) o;
				if(temp[2]!=null){
					ecrb.setEqp_name(temp[2].toString());
				}
				if(temp[3]!=null){
					ecrb.setCh_type_name(temp[3].toString());				
				}
				if(temp[4]!=null){
					ecrb.setCheck_position(temp[4].toString());
				}
				if(temp[5]!=null){
					ecrb.setCheck_standard(temp[5].toString());
				}
				if(temp[6]!=null){
					ecrb.setCheck_method(temp[6].toString());
				}
				ecrb.setId(temp[7].toString());
				ecrb.setRoles(temp[10].toString());
				if(temp[11]!=null){
					ecrb.setCh_status(Integer.parseInt(temp[11].toString()));
				}else{
					ecrb.setCh_status(0);
				}
				
				listEcrb.add(ecrb);
			}
		}
			
		return listEcrb;
	}
	/**
	 * 功能描述：角色为维修主管时，单独查询
	 * 登录人角色赋值写死！！！-注意后期维护
	 * @author wchuang
	 * @time 2015年7月16日14:55:51
	 * @param 张璐
	 * @return
	 * @throws ParseException 
	 * */
	@Override
	public List<EqpCheckRecordBean> queryZG_USERID(EqpCheckRecordBean ecrbean,String zg_userid,String []del) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DJ_TYPE,DES,NAME,DJ_METHOD_ID,id from SYS_EQP_TYPE where 1=1");
		sb.append(" and cid in (SELECT SEC_PID FROM MD_EQP_TYPE_CHILD WHERE 1=1");
		sb.append(" and met_pid in (SELECT id FROM MD_EQP_TYPE WHERE 1=1");
		sb.append(" and id in (SELECT EQP_TYPE_ID FROM MD_EQUIPMENT WHERE 1=1");
		sb.append(" and id = '"+ecrbean.getEquipmentId()+"'");
		sb.append(")");
		sb.append(")");
		sb.append(" AND del = 0");
		sb.append(" AND type = 'dj'");
		sb.append(")");
		sb.append(" and DJ_TYPE = '"+zg_userid+"'");
		List<?> list = eqmProtectRecordDao.queryBySql(sb.toString());
		List<EqpCheckRecordBean> listbean= new ArrayList<EqpCheckRecordBean>();
		int i =0;
		if(list.size()>0){
			for(Object o:list){
				Object[] temp=(Object[]) o;
				EqpCheckRecordBean b=new EqpCheckRecordBean();
				try {
					b.setEqp_name(ecrbean.getEqp_name());
					//b.setCheck_type(temp[0].toString());
					b.setCheck_type("维修主管点检");
					if(temp[1]!=null){
						b.setCheck_position(temp[1].toString());
					}
					b.setCheck_standard(temp[2].toString());
					b.setCheck_method(temp[3].toString());
					b.setId(temp[4].toString());
					b.setDelf(del[i]);
					i++;
				} catch (Exception e) {
					// TODO: handle exception
				}
				listbean.add(b);
			}
		}
		return listbean;
	}
	
	
	public static String getTayRecordUrl(EqpCheckRecordBean ecrbean){
		StringBuffer sbf= new StringBuffer();
		sbf.append(" select a.id, b.name AS eqp_name, (select name from SYS_ROLE where id=a.dj_type) as ch_type_name, a.name AS ch_location, a.des AS ch_standard, a.dj_method_id AS ch_method,c.ch_status,c.set_id, a.cid ");
		sbf.append(" from SYS_EQP_TYPE a left join (select * from EQM_SPOTCH_RECODE where CONVERT (VARCHAR(32), create_time, 23)='"+getDateToStr()+"' and eqp_id='"+ecrbean.getEqp_id()+"' and shift_id='"+ecrbean.getShift_id()+"') c on c.set_id=a.id left join SYS_EQP_CATEGORY b on a.cid = b.id where  a.enable = 1 AND a.del = 0 AND b.enable = 1 AND b.del = 0   ");
		//设备类型ID
		//sbf.append(" AND  a.cid IN ( SELECT sec_pid FROM MD_EQP_TYPE_CHILD WHERE type = 'dj' AND del = 0 AND met_pid ='"+ecrbean.getEqp_type_id()+"') ");
		//普工类别ID
		String chTypeId=ecrbean.getCh_type_id();
		chTypeId=chTypeId.replaceAll(",", "','");
		sbf.append(" and a.dj_type in ('"+chTypeId+"') ");
		return sbf.toString();
	}
	
	
	/**
	 * 功能描述：设备点检管理- 点检审核
	 * 
	 * @author wchuang
	 * @time 2015年7月17日9:58:03
	 * @param ecrbean
	 * @return
	 * */
	@Override
	public boolean addEqmSpotchRecord(EqpCheckRecordBean ecrbean,String equipmentTypeId,EqmSpotchRecode bean){
		try {
			//String pass="true";//用来判断执行维修主管sql还是其他角色sql
			int status = ecrbean.getCh_status();//如果为不通过状态，转为有故障状态添加到表中--写死
			if(status==0){
				status=2;
			}
			/*String sql_eqpType="select DJ_TYPE from SYS_EQP_TYPE where id='"+ecrbean.getId()+"'";
			List list=eqmProtectRecordDao.queryBySql(sql_eqpType);//如果有数据，证明是维修主管
			if(list.size()>0){
				String sql="UPDATE MD_EQP_TYPE_CHILD SET DEL = '1' WHERE id in (SELECT id FROM MD_EQP_TYPE_CHILD WHERE met_pid = '"+equipmentTypeId+"' AND del = 0 AND type = 'dj')";
				eqmProtectRecordDao.updateBySql(sql, null);
				pass="false";
			}*/
			//if(pass.equals("true")){
				String sql_checkPlan_status="update EQM_CHECK_PLAN_PARAM set status='"+status+"' where id='"+ecrbean.getId()+"'";//2为有故障
			    eqmProtectRecordDao.updateBySql(sql_checkPlan_status, null);
			//}
			
			//将数据存入设备点检记录表中。
			bean.setCreateTime(new Date());
			bean.setUpdateTime(new Date());
			bean.setChDate(new Date());
			bean.setDel(1);
			bean.setChStatus(status);
			/*String sql="SELECT b.name as eqp_name,  a.dj_type as ch_type_id,(select name from SYS_ROLE where id=a.dj_type) as ch_type_name,a.name as ch_location, a.des as ch_standard, a.dj_method_id as ch_method "
					+ "FROM SYS_EQP_TYPE a, SYS_EQP_CATEGORY b WHERE a.cid = b.id AND a.id = '"+equipmentTypeId+"'";*/
			String sql_sys_eqtype="SELECT * FROM SYS_EQP_TYPE WHERE id = '"+ecrbean.getId()+"'";
			List<?> eqtyoeList =  eqmProtectDao.queryBySql(sql_sys_eqtype);
			String sql="";
			if(eqtyoeList.size()==0){
				sql="SELECT check_type,check_position,check_standard,check_method,CZ_USERID,WX_USERID from EQM_CHECK_PLAN_PARAM where id='"+ecrbean.getId()+"'";
				List<?> esrlit =  eqmProtectDao.queryBySql(sql);
				if(esrlit.size()>0){
					Object[] temp=(Object[]) esrlit.get(0);
					try {
						bean.setChTypeName(temp[0].toString());
						bean.setChLocation(temp[1].toString());
						bean.setChStandard(temp[2].toString());
						bean.setChMethod(temp[3].toString());
						if(temp[4]!=null&&!temp[4].equals("")){
							bean.setChTypeId(temp[4].toString());
						}else if(temp[5]!=null&&!temp[5].equals("")){
							bean.setChTypeId(temp[5].toString());
						}
					} catch (Exception e) {
						// TODO: handle exception
						}
					}
			}else{
				sql="select s.DJ_TYPE,s.DES,s.NAME,s.DJ_METHOD_ID,b.NAME as roleName from SYS_EQP_TYPE s LEFT JOIN SYS_ROLE b ON s.DJ_TYPE=b.ID where s.id='"+ecrbean.getId()+"'";
				List<?> esrlit =  eqmProtectDao.queryBySql(sql);
				if(esrlit.size()>0){
					Object[] temp=(Object[]) esrlit.get(0);
					try {
						bean.setChTypeId(temp[0].toString());
						bean.setChLocation(temp[1].toString());
						bean.setChStandard(temp[2].toString());
						bean.setChMethod(temp[3].toString());
						bean.setChTypeName(temp[4].toString());
					} catch (Exception e) {
						// TODO: handle exception
						}
					}
			}
					//保存
					eqmSpotchRecordDao.save(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 功能描述：设备剔除时间-日保
	 * 
	 * @author wchuang
	 * @time 2015年7月17日9:58:03
	 * @param ecrbean
	 * @return
	 * */
	@Override
	public void addEqmCullRecord(EqmCullRecordBean eqb) {
		eqmCullRecordDao.save(eqb);
	}
	
	
	/**
	 * [功能说明]：设备日保，点击日保养，跳转详细页面 -查询 
	 * 
	 * @author wanchanghuang
	 * @time  2015年9月8日16:32:46
	 * 
	 * */
	@Override
	public DataGrid querySysEqpType(HttpSession session, SysEqpTypeBean bean,
			int pageIndex) {
		Integer start=1;
		Integer ednum=1;
		long total=0;
		//起始页-每页多少条
		if(pageIndex==1){// 1-10  10-20  20-30
			start=1;
			ednum=10;
		}else{
			ednum=pageIndex*10;
			start=ednum-10;
		}
		String sql=" select t.id,t.code,t.name,t.des from ( "
				+" select  ROW_NUMBER() over (order by id desc) as tf, id,code,name,des from SYS_EQP_TYPE where cid='"+bean.getCategoryId()+"' and del='0'"
			    +" ) t where 1=1 and t.tf between '"+start+"' and '"+ednum+"'";
		
		String sqlCount="select count(*) from SYS_EQP_TYPE where cid='"+bean.getCategoryId()+"' and del='0'";
		
		//查询结果集
				List<?> eqmPaulDayList=  eqmProtectDao.queryBySql(sql);
				List<SysEqpTypeBean> list=new ArrayList<SysEqpTypeBean>();
				//转换实体
				try {
					if(eqmPaulDayList.size()>0){
						for(Object o:eqmPaulDayList){
							Object[] temp=(Object[]) o;
							SysEqpTypeBean b=new SysEqpTypeBean();
							try {
								b.setId(temp[0].toString());
								if(temp[1]!=null){
									b.setCode(temp[1].toString());
								}
								if(temp[2]!=null){
									b.setName(temp[2].toString());	
								}
								if(temp[3]!=null){
									b.setDes(temp[3].toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							list.add(b);
						}
						//查询总条数
						List<Object> tl= (List<Object>) eqmProtectDao.queryBySql(sqlCount);
						String t=tl.get(0).toString();
						total=Long.parseLong(t);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new DataGrid(list, total);
	}
	
	/**
	 * 想记录表中插入数据
	 * @param all_id
	 * @param all_num
	 * @param use_n
	 * @param name
	 * @param fixId
	 * @throws Exception
	 */
	@Override
	public void addFixRec(String all_id,String use_n,String all_num,String username,String userId,MdEquipment eqp_id,MdShift shift_id,String trouble_id,String type,int fix_type)throws Exception{
		String[] spareId=all_id.split(",");
		String[] useNum=use_n.split(",");
		String[] allNum=all_num.split(",");
		List<EqmFixRec> eqmFixRecs=new ArrayList<EqmFixRec>();
		for (int i = 0; i < allNum.length; i++){
			if(allNum[i].trim().equals("")|| useNum[i].trim().equals("")||useNum[i]==null||allNum[i]==null){
				continue;
			}
			int n=Integer.parseInt(useNum[i]);
		if(n>0){
		EqmFixRec fixRec  = new EqmFixRec();
		fixRec.setId(UUID.randomUUID().toString());
		fixRec.setMaintaiin_time(new Date());
		fixRec.setSpare_parts_id(spareId[i]);
		fixRec.setSpare_parts_num(Integer.parseInt(useNum[i]));
		fixRec.setMaintaiin_id(userId);
		fixRec.setMaintaiin_name(username);
		fixRec.setSource(type);//1-点检
		fixRec.setEqp_id(eqp_id);//
		fixRec.setShift_id(shift_id);
		fixRec.setTrouble_id(trouble_id);
		fixRec.setMaintaiin_type(fix_type);
		eqmFixRecs.add(fixRec);
		}
		//eqmFixDao.save(fixRec);
		
		}
		sysSeviceInfo.batchInsert(eqmFixRecs, EqmFixRec.class);
	}

	
	/**
	 * 用来写入故障表的模板，将来使用
	 * 张璐
	 * 2015-10-8
	 */
	@Override
	public void addTrouble(List<EqmTroubleBean> list){
		if(list.size()>0){
			for(EqmTroubleBean trouble:list){
				StringBuffer sb = new StringBuffer();
				sb.append("INSERT INTO EQM_TROUBLE (ID,EQU_ID,SHIFT_ID,DESCRIPTION,SOURCE,SOURCE_ID,CREATE_USER_ID,CREATE_DATE,TROUBLE_NAME) VALUES ('"+UUID.randomUUID().toString()+"'");
				if(StringUtil.notNull(trouble.getEqu_id())){
					sb.append(",'"+trouble.getEqu_id()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getShift_id())){
					sb.append(",'"+trouble.getShift_id()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getDescription())){
					sb.append(",'"+trouble.getDescription()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getSource())){
					sb.append(",'"+trouble.getSource()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getSource_id())){
					sb.append(",'"+trouble.getSource_id()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getCreate_user_id())){
					sb.append(",'"+trouble.getCreate_user_id()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getCreate_date())){
					sb.append(",'"+trouble.getCreate_date()+"'");
				}else{
					sb.append(",NULL");
				}
				if(StringUtil.notNull(trouble.getTrouble_name())){
					sb.append(",'"+trouble.getTrouble_name()+"'");
				}else{
					sb.append(",NULL");
				}
				sb.append(")");
				eqmCullRecordDao.updateBySql(sb.toString(), null);
			}
		}
	}
	
	/**
	 * 查询故障表，用作故障下拉框
	 * 张璐
	 * 2015-10-10
	 * @return
	 */
	@Override
	public List<?> queryTroubleComb(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TROUBLE_NAME FROM EQM_TROUBLE where TROUBLE_NAME is NOT null GROUP BY TROUBLE_NAME");
		return eqmCullRecordDao.queryBySql(sb.toString(), null);
		
	}
	
	
}
