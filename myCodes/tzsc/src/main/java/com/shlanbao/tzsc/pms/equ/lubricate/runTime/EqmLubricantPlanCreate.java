package com.shlanbao.tzsc.pms.equ.lubricate.runTime;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.base.mapping.EqmLubricant;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanParamServiceI;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanServiceI;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantServiceI;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpTypeServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;

public class EqmLubricantPlanCreate {

	@Autowired
	public EqmLubricantServiceI lubricantServiceI;
	
	@Autowired
	public EqmLubricantPlanServiceI lubricantPlanService;
	
	@Autowired
	public EqmLubricantPlanParamServiceI lubricantPlanParamService;
	
	@Autowired
	public SysEqpTypeServiceI sysEqpTypeServiceImpl;
	
	
	public void createLubriPlan(){
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select l.ID l_id,e.ID e_id,e.EQUIPMENT_CODE e_code,l.CYCLE,lp.ID lp_id from EQM_LUBRICAT l ");
		sqlBuffer.append("left join MD_EQUIPMENT e on l.EQP_TYPE=e.EQP_TYPE_ID ");
		sqlBuffer.append("left join EQM_LUBRICAT_PLAN lp on l.ID=lp.LUB_ID and e.ID=lp.EQP_ID ");
		sqlBuffer.append("where lp.ID is null ");
		List<?> list=lubricantServiceI.getListBySql(sqlBuffer.toString());
		for(Object o:list){
			Object[] temp=(Object[])o;
			String lubri_id=temp[0].toString();
			String eqp_id=temp[1].toString();
			String eqp_code=temp[2].toString();
			createLubriPlan(lubri_id,eqp_id,eqp_code);
		}
		
	}
	
	public void createLubriPlan(String lubri_id,String eqp_id,String eqp_code){
		//查询设备润滑周期表
		EqmLubricant lubri=lubricantServiceI.getBeanById(lubri_id);
		EqmLubricantPlan plan=new EqmLubricantPlan();
		plan.setEqp(new MdEquipment(eqp_id));
		plan.setDate_p(DateUtil.dateCals(new Date(),lubri.getCycle()));
		plan.setCode(DateUtil.formatDateToString(new Date(), "yyyyMMdd")+"E"+eqp_code);
		plan.setLub_id(lubri_id);
		plan.setRule_id(lubri.getRule_id());
		plan.setStatus(0);
		//保存至设备润滑计划
		plan=lubricantPlanService.saveBean(plan);
		//获取设备润滑规则详细项
		SysEqpTypeBean sysEqpType=new SysEqpTypeBean();
		sysEqpType.setCategoryId(lubri.getRule_id());
		//根据润滑规则查找到具体的项
		List<SysEqpType> lists=sysEqpTypeServiceImpl.queryBean(sysEqpType);
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
			lubricantPlanParamService.savePlanParams(param);
		}
	}
}
