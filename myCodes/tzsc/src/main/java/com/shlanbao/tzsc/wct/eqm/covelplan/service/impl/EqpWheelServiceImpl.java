package com.shlanbao.tzsc.wct.eqm.covelplan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmWheelCovelParamDaoI;
import com.shlanbao.tzsc.base.dao.EqmWheelCovelPlanDaoI;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.covelplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.wct.eqm.covelplan.beans.EqpWCPBean;
import com.shlanbao.tzsc.wct.eqm.covelplan.service.EqpWheelServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class EqpWheelServiceImpl extends BaseService implements EqpWheelServiceI {
	@Autowired 
	private EqmWheelCovelPlanDaoI  covelPlanDaoI;
	@Autowired
	private EqmWheelCovelParamDaoI eqmWheelCovelParamDaoI;
	@Override
	public DataGrid getEqpList(EqpWCPBean eqmWCPBean, int pageIndex)throws Exception {
		StringBuilder hql=new StringBuilder();
		hql.append(" from EqmWheelCovelPlan e where 1=1 and e.del=0 ");
		if(StringUtil.notNull(eqmWCPBean.getMdEquipmentId())){//机台
			hql.append(" and e.mdEquipment.id='"+eqmWCPBean.getMdEquipmentId()+"' ");
		}
		//时间
		hql.append(StringUtil.fmtDateBetweenParams("e.scheduleDate",eqmWCPBean.getStartTime(), eqmWCPBean.getEndTime()));
		
		List<EqmWheelCovelPlan> eqmWheelCovelPlans=  covelPlanDaoI.queryByPage(hql.toString(), pageIndex, 10);
		List<EqpWCPBean> covelPlanBeans=new ArrayList<EqpWCPBean>();
		if(eqmWheelCovelPlans!=null){
			for (EqmWheelCovelPlan eqmWheelCovelPlan : eqmWheelCovelPlans) {
				EqpWCPBean covelPlan=beanConvertor.copyProperties(eqmWheelCovelPlan, EqpWCPBean.class);
				if(eqmWheelCovelPlan.getMdEquipment()!=null){//机台
					covelPlan.setMdEquipmentId(eqmWheelCovelPlan.getMdEquipment().getId());
					covelPlan.setEquipmentName(eqmWheelCovelPlan.getMdEquipment().getEquipmentName());
				}
				if(eqmWheelCovelPlan.getMdShift()!=null){//班次
					covelPlan.setMdShiftName(eqmWheelCovelPlan.getMdShift().getName());
					covelPlan.setMdShiftId(eqmWheelCovelPlan.getMdShift().getId());
				}
				if(eqmWheelCovelPlan.getSysUserByCreateId()!=null){//制单人
					covelPlan.setSysUserByCreateName(eqmWheelCovelPlan.getSysUserByCreateId().getName());
					covelPlan.setSysUserByCreate(eqmWheelCovelPlan.getSysUserByCreateId().getId());
				}
				if(eqmWheelCovelPlan.getMdEquipment().getMdWorkshop()!=null){//车间
					covelPlan.setWorkShopId(eqmWheelCovelPlan.getMdEquipment().getMdWorkshop().getId());
					covelPlan.setWorkShopName(eqmWheelCovelPlan.getMdEquipment().getMdWorkshop().getName());
				}
				covelPlanBeans.add(covelPlan);
			}
			String str="select count(*) ";
			long total=covelPlanDaoI.queryTotal(str+hql.toString());
			
			return new DataGrid(covelPlanBeans, total);
		}
		return null;
	}
	//查询
	@Override
	public DataGrid queryParamList(EqmWheelCovelParamBean bean,int pageIndex){
		StringBuffer hql=new StringBuffer();
		hql.append("from EqmWheelCovelParam o where 1=1 ");
		//班次
		if(StringUtil.notNull(bean.getShiftName())){
			hql.append("and o.pid.mdShift.id = '"+bean.getShiftName()+"' ");
		}
		if(StringUtil.notNull(bean.getUserId())){
			hql.append("and o.user.id = '"+bean.getUserId()+"' ");
		}
		//设备Id
		if(StringUtil.notNull(bean.getEqpId())){
			hql.append("and o.pid.mdEquipment.id = '"+bean.getEqpId()+"' ");
		}
		//保养名称
		if(StringUtil.notNull(bean.getJobName())){
			hql.append("and o.pid.planName = '"+bean.getJobName()+"' ");
		}
		if(StringUtil.notNull(bean.getPid())){
			hql.append("and o.pid.id = '"+bean.getPid()+"' ");
		}
		//保养类型
		if(StringUtil.notNull(bean.getJobType())){
			hql.append("and o.pid.maintenanceType = '"+bean.getJobType()+"' ");
		}
		//时间比较
		if(bean.getStartTime()!=null){
			hql.append("and convert(varchar(23),o.planTime,23) >= '"+DateUtil.formatDateToString(bean.getStartTime(),"yyyy-MM-dd")+"' ");
		}
		if(bean.getEndTime()!=null){
			hql.append("and convert(varchar(23),o.planTime,23) <= '"+DateUtil.formatDateToString(bean.getEndTime(),"yyyy-MM-dd")+"' ");
		}
		List<EqmWheelCovelParam> list=eqmWheelCovelParamDaoI.queryByPage(hql.toString()+" order by o.planTime desc ",pageIndex, 10);
		List<EqmWheelCovelParamBean> returnList=new ArrayList<EqmWheelCovelParamBean>();
		for(EqmWheelCovelParam param:list){
			System.out.println(param.getPlanTime());
			returnList.add(mappingBean(param));
		}
		hql.insert(0,"select count(*) ");
		long total=eqmWheelCovelParamDaoI.queryTotal(hql.toString());
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
	 public DataGrid queryPlanList(EqmWheelCovelParamBean bean,int pageIndex){
		 StringBuffer hql=new StringBuffer();
			hql.append("from EqmWheelCovelPlan o ");
			hql.append("left join fetch o.mdEquipment sb "); //设备
			hql.append("left join fetch o.mdShift bc "); //班次
			hql.append("where o.del=0 ");
			List<Object> params = new ArrayList<Object>();
			hql.append("and o.wheelCoverType in('2','8') ");
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
			List<EqmWheelCovelPlan> list = covelPlanDaoI.queryByPage(hql.toString()+" order by o.scheduleDate desc ", pageIndex, 10, params);
			List<EqpWCPBean> returnList=new ArrayList<EqpWCPBean>();
			long total=0;
			String lastHql = hql.toString().replaceAll("left join fetch o.mdEquipment sb", "");
			lastHql = lastHql.replaceAll("left join fetch o.mdShift bc", "");
			total = covelPlanDaoI.queryTotal("select count(*) "+lastHql,params);
			if(total>0){
				for(EqmWheelCovelPlan plan:list){
					EqpWCPBean oneBean = new EqpWCPBean();
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
					returnList.add(oneBean);
				}
			}
			return new DataGrid(returnList,total);
	 }
	/**
	 * 
	* @Title: mappingBean 
	* @Description: EqmWheelCovelParam Convert to EqmWheelCovelParamBean  
	* @param  param
	* @return EqmWheelCovelParamBean    返回类型 
	* @throws
	 */
	private EqmWheelCovelParamBean mappingBean(EqmWheelCovelParam param){
		EqmWheelCovelParamBean newBean=new EqmWheelCovelParamBean();
		newBean.setActualTime(param.getActualTime());
		newBean.setEnable(param.getEnable());
		newBean.setId(param.getId());
		newBean.setPlanTime(param.getPlanTime());
		if(param.getPid()!=null){
			newBean.setJobContext(param.getPid().getMaintenanceContent());
			newBean.setJobName(param.getPid().getPlanName());
			newBean.setJobType(param.getPid().getMaintenanceType());
			newBean.setPid(param.getPid().getId());
			if(param.getPid().getMdShift()!=null){
				newBean.setShiftName(param.getPid().getMdShift().getName());
			}
			newBean.setEqpName(param.getPid().getMdEquipment().getEquipmentName());
		}
		if(param.getUser()!=null)
		newBean.setUserName(param.getUser().getName());
		return newBean;
	}
	
	@Override
	public boolean updateBean(EqmWheelCovelParam bean){
		return eqmWheelCovelParamDaoI.saveOrUpdate(bean);
	}
	//根据Id查询Bean
	public EqmWheelCovelParam findById(String id){
		return eqmWheelCovelParamDaoI.findById(EqmWheelCovelParam.class, id);
	}
	
	//根据设备大类 查询其以下的所有维护项
	@Override
	public DataGrid queryWheelParts(EqmWheelCovelParamBean bean,PageParams pageParams)throws Exception{
		StringBuffer sql=new StringBuffer();
		sql.append("select syset.CODE,syset.NAME,syset.DES,syset.LX_TYPE,");//4个syset.LX_TYPE,
		sql.append("ewcp.status,ewcp.id,ewcp.pid,ewcp.ACTUAL_TIME,ewcp.USER_ID,su.NAME as USER_NAME,");//6个
		sql.append("ewcp.PLAN_TIME,ewcp.REMARKS,ewcp.SET_ID ");//3个
		sql.append(",ewcp.CZG_USERID,ewcp.CZG_DATE,ewcp.LBG_USERID,ewcp.LBG_DATE,ewcp.SHG_USERID,ewcp.SHG_DATE ");//7个
		//sql.append(",rec.ID as Fix_Rec_ID ");
		sql.append("from EQM_WHEEL_COVEL_PARAM ewcp  ");
		sql.append("left join SYS_USER su on ewcp.USER_ID=su.id  ");
		sql.append("left join SYS_EQP_TYPE syset on ewcp.SET_ID=syset.id  ");
		//sql.append("left join Eqm_Fix_Rec rec on ewcp.id=rec.PLAN_PARAM_ID  ");
		if(null!=bean.getPid()&&!"".equals(bean.getPid())){
			sql.append("where ewcp.PID='"+bean.getPid()+"' ");//这个条件是肯定的
		}
		if("all".equals(bean.getRoleType())||"shg".equals(bean.getRoleType())){
			
		}
//		else if(null!=bean.getLxType()&&!"".equals(bean.getLxType())){//角色
//			sql.append(" and syset.LX_TYPE='"+bean.getLxType()+"' ");
//		}
		else if(null!=bean.getRoleType()&&!"".equals(bean.getRoleType())){//角色
			//角色id 工种角色id：操作工、维修工、维修主管、润滑工、轮保工
			//czg/lbg/wxg/c_w/c_l/l_w
			String[] roids=SysEqpTypeBase.getArrayDJRoleAll();
			String ids=null;
			if(bean.getRoleType().equals("czg")){
				ids=roids[0];
			}else if(bean.getRoleType().equals("lbg")){
				ids=roids[4];
			}else if(bean.getRoleType().equals("dqLbg")){
				ids=roids[7];
			}else if(bean.getRoleType().equals("c_jl")){
				ids=roids[0]+"','"+roids[4];
			}else if(bean.getRoleType().equals("c_dl")){
				ids=roids[0]+"','"+roids[7];
			}else if(bean.getRoleType().equals("jl_dl")){
				ids=roids[4]+"','"+roids[7];
			}
			sql.append(" and syset.LX_TYPE in ('"+ids+"') ");
		}
		sql.append("order by syset.CODE desc ");
		
		List<Object> objs = new ArrayList<Object>();
		List<?> list = eqmWheelCovelParamDaoI.queryBySql(sql.toString(), objs);
		List<EqmWheelCovelParamBean> lastList = new ArrayList<EqmWheelCovelParamBean>();
		if(null!=list&&list.size()>0){
			EqmWheelCovelParamBean childBean = null;
			for(int i=0;i<list.size();i++){
				childBean = new EqmWheelCovelParamBean();
				Object[] arr=(Object[]) list.get(i);
				childBean.setSbCode(ObjectUtils.toString(arr[0]));//设备CODE
				childBean.setSbName(ObjectUtils.toString(arr[1]));//设备名称
				childBean.setSbDes(ObjectUtils.toString(arr[2]));//设备描述
				childBean.setLxType(ObjectUtils.toString(arr[3]));//设备类型
				String isFinsh = ObjectUtils.toString(arr[4]);
				childBean.setStatus(Integer.parseInt(isFinsh));//是否完成
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
				//childBean.setEqmFixRecId(ObjectUtils.toString(arr[19]));//维修主键ID
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
	 public void editBean(EqmWheelCovelParamBean[] eqpBean,LoginBean login) throws Exception{
		 String planId="";
		 String roleType="";
		 for(EqmWheelCovelParamBean bean :eqpBean){//更新备注信息
			 EqmWheelCovelParam updateParam=eqmWheelCovelParamDaoI.findById(EqmWheelCovelParam.class,bean.getId());
			 updateParam.setRemarks(bean.getRemarks());
			 updateParam.setActualTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			 SysUser user = new SysUser();
			 user.setId(login.getUserId());
			 updateParam.setUser(user);
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
			 }
		 }
		//回写主表数据
		 EqmWheelCovelPlan updatePlan=covelPlanDaoI.findById(EqmWheelCovelPlan.class,planId);
		 
		 updatePlan.setModifyDate(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));//修改时间
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
		 	EqmWheelCovelPlan updatePlan=covelPlanDaoI.findById(EqmWheelCovelPlan.class,planId);
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
	  * 更新维修类型和备注
	  * @param paramId   //完成时间，状态
	  * status 1-通过   空--审核不通过
	  * 
	  */
	@Override
	public void updateCovelParam(String paramId,int fixType,String remarks,String planId) throws Exception{
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime=sdf.format(new Date());
		boolean flag=true;
		try{
			if(fixType!=0||fixType!=1){
				fixType=0;
			}
			String sql="UPDATE EQM_WHEEL_COVEL_PARAM SET FIX_TYPE='"+fixType+"',REMARKS='"+remarks+"',status=1,actual_time='"+nowTime+"' WHERE ID='"+paramId+"' ";
			eqmWheelCovelParamDaoI.updateBySql(sql, null);
			String sql2="SELECT status from EQM_WHEEL_COVEL_PARAM where pid= '"+planId+"'";
			List<?> lt=eqmWheelCovelParamDaoI.queryBySql(sql2);
			for (int i = 0; i < lt.size(); i++) {
				Integer status= (Integer) lt.get(i);
				if(status!=1){
					flag=false;
				}
			}
			//全部已经完成
			if(flag){
				//暂时写死，优化的时候要把8添加到map中维护
				String sql3="UPDATE EQM_WHEEL_COVEL_PLAN set WHEEL_COVER_TYPE= '"+SysEqpTypeBase.Wctype+"' ,MODIFY_DATE= '"+sdf.format(new Date())+"' where id= '"+planId+"'";
				eqmWheelCovelParamDaoI.updateBySql(sql3,null);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于WCT轮保查询，用作修改备注
	 * 张璐-2015.11.2
	 * @param id
	 * @return
	 */
	@Override
	public EqmWheelCovelParamBean queryLB(String id){
		StringBuffer sb =new StringBuffer(1000);
		sb.append("SELECT et.name,ewcp.remarks from EQM_WHEEL_COVEL_PARAM ewcp,SYS_EQP_TYPE et where et.id=ewcp.set_id and ewcp.id='"+id+"'");
		List<?> list = eqmWheelCovelParamDaoI.queryBySql(sb.toString());
		EqmWheelCovelParamBean bean = new EqmWheelCovelParamBean();
		if(list.size()>0){
			Object[] o =(Object[]) list.get(0);
			bean.setJobName(o[0].toString());
			bean.setRemarks(o[1].toString());
			return bean;
		}
		return null;
	}
}
