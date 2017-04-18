package com.shlanbao.tzsc.pms.equ.lubricate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantPlanDaoI;
import com.shlanbao.tzsc.base.mapping.EqmLubricant;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmBubricantfBean;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantPlanBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanServiceI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmLubricantPlanServiceImpl extends BaseService implements EqmLubricantPlanServiceI{

	@Autowired
	private BaseDaoI<Object> baseDao;
	@Autowired
	private EqmLubricantPlanDaoI eqmLubricantPlanDaoI;
	@Autowired
	private EqmLubricantDaoI eqmLubricantDaoI;

	
	/**
	* @Title: lubricantSavePlan 
	* @Description: 根据设备周期创建设备润滑计划  
	* @param  设备润滑周期ID
	* @param  设备ID
	* @return boolean    返回类型 
	* @throws
	 */
	public EqmLubricantPlan lubricantSavePlan(String lid,String eqpId){
		EqmLubricant bean=eqmLubricantDaoI.findById(EqmLubricant.class, lid);
		EqmLubricantPlan plan=new EqmLubricantPlan();
		plan.setEqp(new MdEquipment(eqpId));//设备id
		//plan.setRule_id(rule_id);
		plan.setLub_id(bean.getId());//设备润滑周期id
		plan.setStatus(0);//默认新建状态
		plan.setDate_p(new Date());//计划完成执行日期
		eqmLubricantPlanDaoI.save(plan);
		return plan;
	}
	
	@Override
	public EqmLubricantPlan saveBean(EqmLubricantPlan bean) {
		eqmLubricantPlanDaoI.saveBackKey(bean);
		return bean;
	}
	@Override
	public EqmLubricantPlan getBeanById(String id) {
		return eqmLubricantPlanDaoI.findById(EqmLubricantPlan.class, id);
	}
	
	@Override
	public boolean updateBean(EqmLubricantPlanBean bean) throws Exception {
		EqmLubricantPlan b=getBeanById(bean.getId());
		beanConvertor.copyProperties(bean, b);
		b.setDate_p(DateUtil.formatStringToDate(bean.getDate_plan(), "yyyy-MM-dd"));
		eqmLubricantPlanDaoI.saveOrUpdate(b);
		return false;
	}

	@Override
	public DataGrid queryDataGrid(EqmLubricantPlanBean bean,PageParams pageParams) throws Exception {
		String hql="from EqmLubricantPlan o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())){
			params.append("and o.id = '" + bean.getId() +"' ");
		}
		if(StringUtil.notNull(bean.getEqp_id())){
			params.append("and o.eqp.id= '" + bean.getEqp_id() +"' ");
		}
		if(StringUtil.notNull(bean.getCode())){
			params.append("and o.code like '%" + bean.getCode() +"%' ");
		}
		if(StringUtil.notNull(bean.getDate_plan1())){
			params.append("and convert(varchar(32),o.date_p,23) >= '" + bean.getDate_plan1() +"' ");
		}
		if(StringUtil.notNull(bean.getDate_plan2())){
			params.append("and convert(varchar(32),o.date_p,23) <= '" + bean.getDate_plan2() +"' ");
		}
		List<EqmLubricantPlan> rows = eqmLubricantPlanDaoI.queryByPage(hql.concat(params.toString())+" order by o.date_p desc,o.eqp.equipmentCode ",pageParams);
		hql = "select count(*) from EqmLubricantPlan o where 1=1 ";
		long total=eqmLubricantPlanDaoI.queryTotal(hql.concat(params.toString()));
		List<EqmLubricantPlanBean> beans = new ArrayList<EqmLubricantPlanBean>();
		for(EqmLubricantPlan b:rows){
			EqmLubricantPlanBean plan=new EqmLubricantPlanBean();
			beanConvertor.copyProperties(b, plan);
			if(b.getEqp()!=null){
				plan.setEqp_id(b.getEqp().getId());
				plan.setEqp_name(b.getEqp().getEquipmentName());
			}
			
			if(b.getLubricating()!=null){
				plan.setLubricating_id(b.getLubricating().getId());
				plan.setLubricating_name(b.getLubricating().getName());
			}
			if(b.getOperatives()!=null){
				plan.setOperatives_id(b.getOperatives().getId());
				plan.setOperatives_name(b.getOperatives().getName());
			}
			plan.setDate_plan(DateUtil.formatDateToString(b.getDate_p(),"yyyy-MM-dd"));
			if(b.getEtim()!=null){
				plan.setEtims(DateUtil.formatDateToString(b.getEtim(),"yyyy-MM-dd"));
			}
			if(b.getShift_id()!=null){
				if(b.getShift_id().equals(SysEqpTypeBase.shift_0)){//班次  1-早  2-中  3-晚   0-全部
					plan.setShift_name("全部");//获得班次
				}
				if(b.getShift_id().equals(SysEqpTypeBase.shift_1)){
					plan.setShift_name("早班");
				}
				if(b.getShift_id().equals(SysEqpTypeBase.shift_2)){
					plan.setShift_name("中班");
				}
				if(b.getShift_id().equals(SysEqpTypeBase.shift_3)){
					plan.setShift_name("晚班");
				}
			}
			if(b.getLub_id()!=null){
				if(b.getLub_id().equals(SysEqpTypeBase.period_1)){//日润滑
					plan.setLub_id("日润滑");//获得润滑类型
				}
				if(b.getLub_id().equals(SysEqpTypeBase.period_2)){//周润滑
					plan.setLub_id("周润滑");//获得润滑类型
				}
				if(b.getLub_id().equals(SysEqpTypeBase.period_3)){//月润滑
					plan.setLub_id("月润滑");//获得润滑类型
				}
				if(b.getLub_id().equals(SysEqpTypeBase.period_4)){//年润滑
					plan.setLub_id("日润滑");//获得润滑类型
				}
				
			}
			beans.add(plan);
		}
		return new DataGrid(beans,total);
	}

	@Override
	public EqmLubricantPlanBean getBeanByIds(String id) throws Exception {
		EqmLubricantPlan b=eqmLubricantPlanDaoI.findById(EqmLubricantPlan.class, id);
		EqmLubricantPlanBean plan=new EqmLubricantPlanBean();
		beanConvertor.copyProperties(b, plan);
		if(b.getEqp()!=null){
			plan.setEqp_id(b.getEqp().getId());
			plan.setEqp_name(b.getEqp().getEquipmentName());
		}
		if(b.getLubricating()!=null){
			plan.setLubricating_id(b.getLubricating().getId());
			plan.setLubricating_name(b.getLubricating().getName());
		}
		if(b.getOperatives()!=null){
			plan.setOperatives_id(b.getOperatives().getId());
			plan.setOperatives_name(b.getOperatives().getName());
		}
		plan.setDate_plan(DateUtil.formatDateToString(b.getDate_p(),"yyyy-MM-dd"));
		if(b.getEtim()!=null){
			plan.setEtims(DateUtil.formatDateToString(b.getEtim(),"yyyy-MM-dd"));
		}
		return plan;
	}

	/**
	 * 功能说明：设备润滑计划管理-添加
	 * @author wanchanghuang
	 * @time 2015年8月21日16:35:42
	 * 
	 * */
	@Override
	public List<?> addEqmLubricatPlan(EqmBubricantfBean bean) {
		String sql="select id,eqp_type_id,equipment_code,equipment_name from MD_EQUIPMENT where eqp_type_id='"+bean.getEqp_typeId()+"'";
		List<?> listMdEquipment= baseDao.queryBySql(sql);
		return listMdEquipment;
	}
	
	
	/**
	 *润滑管理主表批量删除以及字表批量删除
	 * 2015.9.9--张璐
	 */
	@Override
	public void deleteCycle(String ids) throws Exception {
		ids=ids.replaceAll(",", "','");
		String sqlZ="delete from EQM_LUBRICAT_PLAN_PARAM where PLAN_ID in ( '"+ids+"')";
		String sqlM="delete from EQM_LUBRICAT_PLAN where ID in ( '"+ids+"')"	;
		baseDao.updateBySql(sqlZ, null);
		baseDao.updateBySql(sqlM, null);
	}

	
}
