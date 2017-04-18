package com.shlanbao.tzsc.wct.sch.fault.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchStatFaultDaoI;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.sch.fault.beans.FaultBean;
import com.shlanbao.tzsc.wct.sch.fault.service.FaultServiceI;
/**
* @ClassName: FaultServiceImpl 
* @Description: 生产故障详细信息 
* @author luo 
* @date 2015年9月22日 下午4:27:09 
*
 */
@Service
public class FaultServiceImpl extends BaseService implements FaultServiceI{
	@Autowired
	private SchStatFaultDaoI schStatFaultDao;
	
	@Override
	public List<FaultBean> getFaultAllList(String eid,String shiftId,String teamId,String startTime,String endTime,String orderType){
		List<FaultBean> list=new ArrayList<FaultBean>();
		//查询工单运行时间，停机时间，停机次数
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select sum(sso.RUN_TIME) as run_time,sum(sso.STOP_TIME) as stop_time ,sum(sso.STOP_TIMES) as times ");
		sqlBuffer.append("from SCH_WORKORDER wo ");
		sqlBuffer.append("left join SCH_STAT_OUTPUT sso on wo.id=sso.OID ");
		sqlBuffer.append("where 1=1 ");
		if(StringUtil.notNull(orderType)){
			sqlBuffer.append("and wo.TYPE='"+orderType+"' ");
		}
		if(StringUtil.notNull(eid)){
			sqlBuffer.append("and wo.EQP='"+eid+"' ");
		}
		if(StringUtil.notNull(shiftId)){
			sqlBuffer.append("and wo.SHIFT='"+shiftId+"' ");
		}
		if(StringUtil.notNull(teamId)){
			sqlBuffer.append("and wo.TEAM='"+teamId+"' ");
		}
		if(StringUtil.notNull(startTime)){
			sqlBuffer.append("and CONVERT(varchar(100), wo.DATE, 23) >='"+startTime+"' ");
		}
		if(StringUtil.notNull(endTime)){
			sqlBuffer.append("and CONVERT(varchar(100), wo.DATE, 23) <='"+endTime+"' ");
		}
		List<?> obj=schStatFaultDao.queryBySql(sqlBuffer.toString());
		double sum_stop_time=0.0;
		//获取工单总运行时间、停机时间、停机次数
		if(obj.size()>0){
			Object[] o=(Object[]) obj.get(0);
			FaultBean bean=new FaultBean();
			bean.setName("故障总停机时间统计");
			if(o[0]!=null&&StringUtil.notNull(o[0].toString())){
				double time=MathUtil.roundHalfUp(Double.parseDouble(o[0].toString()), 2);
				bean.setTime_s(time);
			}
			if(o[1]!=null&&StringUtil.notNull(o[1].toString())){
				sum_stop_time=MathUtil.roundHalfUp(Double.parseDouble(o[1].toString()), 2);
				bean.setTime(sum_stop_time);
			}
			if(o[2]!=null&&StringUtil.notNull(o[2].toString())){
				bean.setTimes(Long.parseLong(o[2].toString()));
			}
			list.add(bean);
		}else{
			return null;
		}
		//查询故障名称，故障时长，故障发生次数
		sqlBuffer=new StringBuffer();
		sqlBuffer.append("select ssf.NAME,sum(ssf.TIME) as time ,sum(ssf.TIMES) as times  ");
		sqlBuffer.append("from SCH_WORKORDER wo ");
		sqlBuffer.append("left join SCH_STAT_OUTPUT sso on wo.id=sso.OID ");
		sqlBuffer.append("left join SCH_STAT_FAULT ssf on ssf.OID=sso.ID ");
		sqlBuffer.append("where ssf.NAME is not null ");
		if(StringUtil.notNull(orderType)){
			sqlBuffer.append("and wo.TYPE='"+orderType+"' ");
		}
		if(StringUtil.notNull(eid)){
			sqlBuffer.append("and wo.EQP='"+eid+"' ");
		}
		if(StringUtil.notNull(shiftId)){
			sqlBuffer.append("and wo.SHIFT='"+shiftId+"' ");
		}
		if(StringUtil.notNull(teamId)){
			sqlBuffer.append("and wo.TEAM='"+teamId+"' ");
		}
		if(StringUtil.notNull(startTime)){
			sqlBuffer.append("and CONVERT(varchar(100), wo.DATE, 23) >='"+startTime+"' ");
		}
		if(StringUtil.notNull(endTime)){
			sqlBuffer.append("and CONVERT(varchar(100), wo.DATE, 23) <='"+endTime+"' ");
		}
		sqlBuffer.append("group by ssf.NAME order by sum(ssf.TIME) desc ");
		obj=schStatFaultDao.queryBySql(sqlBuffer.toString());
		for(Object temp:obj){
			Object[] o=(Object[]) temp;
			FaultBean bean=new FaultBean();
			bean.setName(o[0].toString());
			//停机总时间
			bean.setTime_s(sum_stop_time);
			//故障停机时间明细
			if(o[1]!=null&&StringUtil.notNull(o[1].toString())){
				double time=MathUtil.roundHalfUp(Double.parseDouble(o[1].toString()), 2);
				bean.setTime(time);
			}
			//故障停机次数明细
			if(o[2]!=null&&StringUtil.notNull(o[2].toString())){
				bean.setTimes(Long.parseLong(o[2].toString()));
			}
			list.add(bean);
		}
		//暂无排序
		return list;
	}
	
}
