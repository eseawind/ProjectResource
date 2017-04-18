package com.shlanbao.tzsc.pms.equ.wcplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmWheelCovelParamDaoI;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.pms.equ.wcplan.service.EqmWheelCovelParamServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmWheelCovelParamServiceImpl implements EqmWheelCovelParamServiceI{

	@Autowired
	private EqmWheelCovelParamDaoI eqmWheelCovelParamDaoI;
	
	//查询
	@Override
	public DataGrid queryBeanList(EqmWheelCovelParamBean bean){
		StringBuffer hql=new StringBuffer();
		hql.append("from EqmWheelCovelParam o where 1=1 ");
		//班次
		if(StringUtil.notNull(bean.getShiftName())){
			hql.append("and o.pid.mdShift.id = '"+bean.getShiftName()+"' ");
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
		List<EqmWheelCovelParam> list=eqmWheelCovelParamDaoI.query(hql.toString()+" order by o.planTime desc ");
		List<EqmWheelCovelParamBean> returnList=new ArrayList<EqmWheelCovelParamBean>();
		for(EqmWheelCovelParam param:list){
			returnList.add(mappingBean(param));
		}
		hql.insert(0,"select count(*) ");
		long total=eqmWheelCovelParamDaoI.queryTotal(hql.toString());
		return new DataGrid(returnList,total);
	}
	
	@Override
	public boolean updateBean(EqmWheelCovelParamBean bean){
		
		return false;
	}
	
	@Override
	public boolean addWheelCovelParam(EqmWheelCovelParam bean){
		return eqmWheelCovelParamDaoI.saveOrUpdate(bean);
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
		}
		if(param.getUser()!=null)
		newBean.setUserName(param.getUser().getName());
		return newBean;
	}
	//根据日期生产维保计划
	@Override
	public void buildWheelCovelPlan(){
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select ");
		sqlBuffer.append("GETDATE()+ cast(o.PERIOD as int) ");//计划执行日期0
		sqlBuffer.append(",o.EQU_ID ");//设备编号1
		sqlBuffer.append(",o.SHIFT ");//计划班次2
		sqlBuffer.append(",o.MAINTENANCE_TYPE ");//保养类型3
		sqlBuffer.append(",o.WHEEL_PARTS ");//保养部位4
		sqlBuffer.append(",PLAN_NAME ");//保养名称5
		sqlBuffer.append(",o.MAINTENANCE_CONTENT ");//保养内容6
		sqlBuffer.append(",o.id ");//ID7
		sqlBuffer.append(",o.PERIOD ");//周期8
		sqlBuffer.append("from EQM_WHEEL_COVEL_PLAN o ");
		sqlBuffer.append("where 1=1 and o.del=0  ");
		sqlBuffer.append("and o.SCHEDULE_DATE<=GETDATE() ");
		sqlBuffer.append("and o.SCHEDULE_end_DATE>=GETDATE() ");
		sqlBuffer.append("and o.WHEEL_COVER_TYPE='运行' ");
		//计划开始日期+周期=当前日期
		sqlBuffer.append("and ( CONVERT(nvarchar(21),o.SCHEDULE_DATE+CAST(o.PERIOD as int),23)=CONVERT(nvarchar(21),GETDATE(),23) ");
		//(当前日期-计划开始日期)%周期=0
		sqlBuffer.append("or datediff(DAY,getdate(),o.SCHEDULE_DATE)%cast(o.PERIOD as int)=0 )");
		List<?> list=eqmWheelCovelParamDaoI.queryBySql(sqlBuffer.toString());
		for(Object o:list){
			Object[] temp=(Object[])o;
			if(queryParamByPid(temp[7].toString(),Integer.parseInt(temp[8].toString()))){
				EqmWheelCovelParam bean=new EqmWheelCovelParam();
				bean.setPlanTime(temp[0].toString());
				bean.setEnable("0");
				bean.setPid(new EqmWheelCovelPlan(temp[7].toString()));
				eqmWheelCovelParamDaoI.save(bean);
			}
		}		
	}
	/**
	 * 
	* @Title: queryParamByPid 
	* @Description: 根据Pid和周期查看是否已经生成维保计划  ，计划时间=当天时间+周期
	* @param  pid
	* @param  period
	* @return boolean    返回类型 
	* @throws
	 */
	private boolean queryParamByPid(String pid,int period){
		String sql="select count(*) from EqmWheelCovelParam o where pid='"+pid
				+"' and convert(nvarchar(23),getdate()+"+period+",23)=convert(nvarchar(23),o.planTime,23) ";
		long total=eqmWheelCovelParamDaoI.queryTotal(sql);
		if(total>0)
			return false;
		else
			return true;
	}
}
