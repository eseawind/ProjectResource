package com.shlanbao.tzsc.wct.eqm.lube.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantPlanDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantPlanParamDaoI;
import com.shlanbao.tzsc.base.dao.SysEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.EqmLubricant;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanBeans;
import com.shlanbao.tzsc.wct.eqm.lube.service.EqmLubricantPlansServiceI;

@Service
public class EqmLubricantPlansServiceImpl extends BaseService implements EqmLubricantPlansServiceI{

	@Autowired
	private BaseDaoI<Object> baseDao;
	@Autowired
	private EqmLubricantPlanDaoI eqmLubricantPlanDaoI;
	@Autowired
	private EqmLubricantDaoI eqmLubricantDaoI;
	@Autowired
	private EqmLubricantPlanParamDaoI eqmLubricantPlanParamDaoI;
	@Autowired
	private SysEqpTypeDaoI sysEqpTypeDao;
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
		plan.setLub_id(bean.getId());//设备润滑周期id
		plan.setStatus(0);//默认新建状态
		plan.setDate_p(new Date());//计划完成执行日期
		eqmLubricantPlanDaoI.save(plan);
		return plan;
	}
	
	@Override
	public EqmLubricantPlan saveBean(EqmLubricantPlan bean) {
		eqmLubricantPlanDaoI.saveOrUpdate(bean);
		return bean;
	}
	@Override
	public EqmLubricantPlan getBeanById(String id) {
		return eqmLubricantPlanDaoI.findById(EqmLubricantPlan.class, id);
	}
	
	@Override
	public boolean updateBean(EqmLubricantPlanBeans bean) throws Exception {
		EqmLubricantPlan b=getBeanById(bean.getId());
		beanConvertor.copyProperties(bean, b);
		b.setDate_p(DateUtil.formatStringToDate(bean.getDate_plan(), "yyyy-MM-dd"));
		eqmLubricantPlanDaoI.saveOrUpdate(b);
		return false;
	}

	
	@Override
	public DataGrid queryDataGrid(EqmLubricantPlanBeans bean,PageParams pageParams) throws Exception {
		String hql="from EqmLubricantPlan o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())){
			params.append("and o.id = '" + bean.getId() +"' ");
		}
		if(StringUtil.notNull(bean.getEqp_id())){
			params.append("and o.eqp.id = '" + bean.getEqp_id() +"' ");
		}
		if(StringUtil.notNull(bean.getDate_plan())){
			params.append("and convert(varchar(32),o.date_p,23) >= '" + bean.getDate_plan() +"' ");
		}
		if(StringUtil.notNull(bean.getAttr1())){
			params.append("and convert(varchar(32),o.date_p,23) <= '"+bean.getAttr1()+"' ");
		}
		List<EqmLubricantPlan> rows = eqmLubricantPlanDaoI.queryByPage(hql.concat(params.toString()),pageParams);
		hql = "select count(*) from EqmLubricantPlan o where 1=1 ";
		long total=eqmLubricantPlanDaoI.queryTotal(hql.concat(params.toString()));
		List<EqmLubricantPlanBeans> beans = new ArrayList<EqmLubricantPlanBeans>();
		SysEqpTypeBase sysEqpTypeBase = new SysEqpTypeBase();
		for(EqmLubricantPlan b:rows){
			EqmLubricantPlanBeans plan=new EqmLubricantPlanBeans();
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
			if(b.getShift_id()!=null){
				if(b.getShift_id().equals(SysEqpTypeBase.shift_0)){//0-全部，1-早班，2中班，3-晚班--维护类中。
					plan.setShiftName("全部");//班次
				}else if(b.getShift_id().equals(SysEqpTypeBase.shift_1)){
					plan.setShiftName("早班");//班次
				}else if(b.getShift_id().equals(SysEqpTypeBase.shift_2)){
					plan.setShiftName("中班");//班次
				}else if(b.getShift_id().equals(SysEqpTypeBase.shift_3)){
					plan.setShiftName("晚班");//班次
				}
			}
			if(b.getLub_id()!=null){
				if(b.getLub_id().equals(SysEqpTypeBase.period_1)){//周期  1-日  2-周  3-月   4-年
					plan.setLub_id_name("日润滑");//润滑类型
				}else if(b.getLub_id().equals(SysEqpTypeBase.period_2)){
					plan.setLub_id_name("周润滑");
				}else if(b.getLub_id().equals(SysEqpTypeBase.period_3)){
					plan.setLub_id_name("月润滑");
				}else if(b.getLub_id().equals(SysEqpTypeBase.period_4)){
					plan.setLub_id_name("年润滑");
				}
				
			}
			plan.setDate_plan(DateUtil.formatDateToString(b.getDate_p(),"yyyy-MM-dd"));
			beans.add(plan);
		}
		return new DataGrid(beans,total);
	}

	@Override
	public EqmLubricantPlanBeans getBeanByIds(String id) throws Exception {
		EqmLubricantPlan b=eqmLubricantPlanDaoI.findById(EqmLubricantPlan.class, id);
		EqmLubricantPlanBeans plan=new EqmLubricantPlanBeans();
		beanConvertor.copyProperties(b, plan);
		if(b.getEqp()!=null){
			plan.setEqp_id(b.getEqp().getId());
			plan.setEqp_name(b.getEqp().getEquipmentName());
			plan.setEqp_code(b.getEqp().getEquipmentCode());
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
		return plan;
	}

	@Override
	public void createLubriPlan(String lubri_id,String eqp_id,String eqp_code){
		//查询设备润滑周期表
		/*EqmLubricant lubri=eqmLubricantDaoI.findById(EqmLubricant.class,lubri_id);
		EqmLubricantPlan plan=new EqmLubricantPlan();
		plan.setEqp(new MdEquipment(eqp_id));
		if(lubri.getCycle()>0){
			plan.setDate_p(DateUtil.dateCals(new Date(),lubri.getCycle()));
		}else{
			plan.setDate_p(new Date());
		}
		plan.setCode(DateUtil.formatDateToString(new Date(), "yyyyMMdd")+"E"+eqp_code);
		plan.setLub_id(lubri_id);
		plan.setRule_id(lubri.getRule_id());
		plan.setStatus(0);
		//保存至设备润滑计划
		plan=this.saveBean(plan);
		//获取设备润滑规则详细项
		SysEqpTypeBean sysEqpType=new SysEqpTypeBean();
		sysEqpType.setCategoryId(lubri.getRule_id());
		//根据润滑规则查找到具体的项
		List<SysEqpType> lists=queryBean(sysEqpType);
		//获取刚创建的设备润滑计划ID
		String plan_id=plan.getId();
		for(SysEqpType sys:lists){
			EqmLubricantPlanParam param=new EqmLubricantPlanParam();
			param.setCode(sys.getCode());
			param.setDes(sys.getDes());
			//润滑剂单位
			if(sys.getFillUnitBean()!=null){
				param.setFill_unit(sys.getFillUnitBean().getName());
			}
			param.setFill_quantity(sys.getFillQuantity());
			//润滑剂名称
			if(sys.getOilIdBean()!=null){
				param.setOiltd(sys.getOilIdBean().getLubricantName());
			}
			param.setName(sys.getName());
			param.setPlan_id(plan_id);
			//润滑方式
			if(sys.getFashionBean()!=null){
				param.setMethods(sys.getFashionBean().getName());
			}
			param.setSysEqpType(new SysEqpType(sys.getId()));
			eqmLubricantPlanParamDaoI.saveOrUpdate(param);
		}*/
	}
	
	public List<SysEqpType> queryBean(SysEqpTypeBean mdTypeBean){
		String hql = "from SysEqpType o ";
		hql +=" left join fetch  o.sysEqpCategory sys1  ";//
		hql +=" left join fetch  o.oilIdBean sys2  ";	//润滑油
		hql +=" left join fetch  o.fillUnitBean sys3  ";	//单位
		hql +=" left join fetch  o.fashionBean sys4  ";	//方式
		hql +=" where o.del=0  ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(mdTypeBean.getName())){
			params.append(" and o.name like '%" + mdTypeBean.getName() +"%'");
		}
		if(StringUtil.notNull(mdTypeBean.getCode())){
			params.append(" and o.code like '%" + mdTypeBean.getCode() +"%'");
		}
		if(StringUtil.notNull(mdTypeBean.getCategoryId())){
			params.append(" and o.sysEqpCategory.id='"+mdTypeBean.getCategoryId()+"'");
		}
		if(StringUtil.notNull(mdTypeBean.getId())){
			params.append(" and o.id='"+mdTypeBean.getId()+"'");
		}
		List<SysEqpType> rows = sysEqpTypeDao.query(hql.concat(params.toString()));
		return rows;
	}
}
