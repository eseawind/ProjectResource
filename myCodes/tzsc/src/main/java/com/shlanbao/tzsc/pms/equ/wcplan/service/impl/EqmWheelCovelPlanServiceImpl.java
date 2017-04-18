package com.shlanbao.tzsc.pms.equ.wcplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmWheelCovelPlanDaoI;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCalendar;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelPlanBean;
import com.shlanbao.tzsc.pms.equ.wcplan.service.EqmWheelCovelPlanServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmWheelCovelPlanServiceImpl extends BaseService implements EqmWheelCovelPlanServiceI {
	
	@Autowired
	private EqmWheelCovelPlanDaoI eqmWheelCovelPlanDao;

	//查询功能
	@Override
	public DataGrid queryWCPlan(EqmWheelCovelPlanBean wcpBean,PageParams pageParams) throws Exception {
		StringBuilder hql=new StringBuilder();
		hql.append("from EqmWheelCovelPlan o where 1=1 and o.del=0 "); 
		if(StringUtil.notNull(wcpBean.getPlanName())){
			hql.append(" and o.planName like '%"+ wcpBean.getPlanName() +"%'");
		}
		if(StringUtil.notNull(wcpBean.getPlanNo())){
			hql.append(" and o.planNo like '%"+ wcpBean.getPlanNo() +"%'");
		}	
		if(StringUtil.notNull(wcpBean.getDate1())){
			hql.append(" and o.scheduleDate >= '"+wcpBean.getDate1()+"'");
		}
		if(StringUtil.notNull(wcpBean.getDate2())){
			hql.append(" and o.scheduleEndDate <= '"+wcpBean.getDate2()+"'");
		}
		if(StringUtil.notNull(wcpBean.getWheelCoverType())){
			hql.append(" and o.wheelCoverType = '"+ wcpBean.getWheelCoverType() +"' ");
		}
		List<EqmWheelCovelPlan> eqmWheelCovelPlan = eqmWheelCovelPlanDao.queryByPage(hql.toString(), pageParams);	
		List<EqmWheelCovelPlanBean> list = new ArrayList<EqmWheelCovelPlanBean>();
		for(EqmWheelCovelPlan wcp : eqmWheelCovelPlan){
			EqmWheelCovelPlanBean bean =new EqmWheelCovelPlanBean();
			BeanConvertor.copyProperties(wcp, bean);
			bean.setEqmName(wcp.getMdEquipment().getEquipmentName());
			
			if(wcp.getMdShift()!=null){
				bean.setMdShiftName(wcp.getMdShift().getName());
				bean.setMdShiftId(wcp.getMdShift().getId());
			}
			list.add(bean);
		}
		
		hql.insert(0,"select count(*) ");
		
		long total = eqmWheelCovelPlanDao.queryTotal(hql.toString());
		return new DataGrid(list,total);
	}
	
	//轮保日历查询功能
	@Override
	public List<EqmWheelCalendar> queryWCPlanCalendar(String date1,String date2) throws Exception {
		StringBuilder hql=new StringBuilder();
		hql.append("from EqmWheelCovelPlan o where 1=1 and o.del=0 "); 
		
		if(StringUtil.notNull(date1)){
			hql.append(" and o.scheduleDate >= '"+date1+"'");
		}
		if(StringUtil.notNull(date2)){
			hql.append(" and o.scheduleEndDate <= '"+date2+"'");
		}
		List<EqmWheelCovelPlan> eqmWheelCovelPlan = eqmWheelCovelPlanDao.query(hql.toString());	
		List<EqmWheelCalendar> list=new ArrayList<EqmWheelCalendar>();
		for(EqmWheelCovelPlan wcp : eqmWheelCovelPlan){
			EqmWheelCalendar bean =new EqmWheelCalendar();
			bean.setName(wcp.getPlanName());
			bean.setId(wcp.getId());
			bean.setStart(DateUtil.formatDateToString(wcp.getScheduleDate(),"yyyy-MM-dd"));
			bean.setTitle(wcp.getPlanName());
			// 0\新增 1、审核通过2、运行3、停止运行4、操作工操作完毕5、轮保工操作完毕6、操作工、轮保工操作完毕7、审核完毕8、计划完成
			if(wcp.getWheelCoverType().equals('7')){
				bean.setColor("GREEN");
			}else{
				bean.setColor("Red");
			}
			
			list.add(bean);
		}
		return list;
	}
	//修改轮保内容状态
	@Override
	public void updateWCPlanStatus(String id, String status) {
		EqmWheelCovelPlan bean=eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id);
		bean.setWheelCoverType(status);
		eqmWheelCovelPlanDao.save(bean);
	}

	@Override
	public void addWCPlan(EqmWheelCovelPlanBean wcpBean, String userId) throws Exception {
		EqmWheelCovelPlan wcp = BeanConvertor.copyProperties(wcpBean, EqmWheelCovelPlan.class);
		//设备ID赋值
		wcp.setMdEquipment(new MdEquipment(wcpBean.getEqmId()));
		//创建人，创建时间赋值（只在设备管理员这赋值）
		SysUser sysUser = new SysUser();
		sysUser.setId(userId);
		wcp.setCreateDate(new Date());
		wcp.setSysUserByCreateId(sysUser);
		//轮保状态 设置为已经保存
		wcp.setWheelCoverType("新建");
		//责任人赋值
		wcp.setMdShift(new MdShift(wcpBean.getMdShiftId()));
		SysUser sysUser2 = new SysUser();
		sysUser2.setId(wcpBean.getDutyPeopleId());
		wcp.setSysUserByDutyPeopleId(sysUser2);
		wcp.setDel("0");
		eqmWheelCovelPlanDao.save(wcp);
	}
	
	@Override
	public void editWCPlan(EqmWheelCovelPlanBean wcpBean) throws Exception{
		EqmWheelCovelPlan WCPlan = eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, wcpBean.getId());
		if(StringUtil.notNull(wcpBean.getId())){
			WCPlan.setEqmWheelCovelPlan(new EqmWheelCovelPlan(wcpBean.getId()));
		}
		WCPlan.setMdShift(new MdShift(wcpBean.getMdShiftId()));
		WCPlan.setMdEquipment(new MdEquipment(wcpBean.getEqmId()));
		WCPlan.setSysUserByDutyPeopleId(new SysUser(wcpBean.getDutyPeopleId()));
		
		BeanConvertor.copyProperties(wcpBean,WCPlan);
	}

	@Override
	public EqmWheelCovelPlanBean getById(String id) throws Exception {
		EqmWheelCovelPlan eqmWheelCovelPlan = eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id);
		EqmWheelCovelPlanBean wcpBean = BeanConvertor.copyProperties(eqmWheelCovelPlan,EqmWheelCovelPlanBean.class);
		wcpBean.setEqmId(eqmWheelCovelPlan.getMdEquipment().getId());//设备id
		wcpBean.setDutyPeopleId(eqmWheelCovelPlan.getSysUserByDutyPeopleId().getId());//责任人id
		wcpBean.setEqmName(eqmWheelCovelPlan.getMdEquipment().getEquipmentName());
		if(null!=eqmWheelCovelPlan.getSysUserByDutyPeopleId()){
			wcpBean.setDutyPeopleName(eqmWheelCovelPlan.getSysUserByDutyPeopleId().getName());
		}
		if(eqmWheelCovelPlan.getMdShift()!=null){
			wcpBean.setMdShiftId(eqmWheelCovelPlan.getMdShift().getId());
		}
		return wcpBean;
	}

	
	@Override
	public void deleteWCPlan(String id) throws Exception {
		String sql_eqm_wheel_plan="DELETE from EQM_WHEEL_COVEL_PLAN where id='"+id+"'";
		eqmWheelCovelPlanDao.updateBySql(sql_eqm_wheel_plan, null);
		String sql_eqm_wheel_plan_param="DELETE from EQM_WHEEL_COVEL_PARAM where pid='"+id+"'";
		eqmWheelCovelPlanDao.updateBySql(sql_eqm_wheel_plan_param, null);
		//eqmWheelCovelPlanDao.findById(EqmWheelCovelPlan.class, id).setDel("1");
	}

	

}
