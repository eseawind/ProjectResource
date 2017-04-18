package com.shlanbao.tzsc.pms.equ.sbglplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmCheckcatPlanDaoI;
import com.shlanbao.tzsc.base.dao.EqmSpotchRecordDaoI;
import com.shlanbao.tzsc.base.mapping.EqmCheckcatPlan;
import com.shlanbao.tzsc.base.mapping.EqmSpotchRecode;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EqmSpotchRecodeBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.service.EqmCheckcatPlanServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmCheckcatPlanServiceImpl extends BaseService implements EqmCheckcatPlanServiceI {
	
	@Autowired
	private EqmCheckcatPlanDaoI eqmCheckcatPlanDao;
	@Autowired
	private EqmSpotchRecordDaoI eqmSpotchRecordDao;
	/**
	 * 根据id查询到具体的点修记录详情
	 * @param esrBean
	 * @return  czgId,wxgId,wxzugId
	 */
	
	public DataGrid queryInfoById(String id){
		//String sql="select eqp_name,shift_name,team_name,ch_location,ch_status,ch_date,create_user_name,update_time,remark from EQM_SPOTCH_RECODE where id='"+id+"'";
		String hql="from EqmSpotchRecode where id='"+id+"'";
		List<EqmSpotchRecode> map=eqmSpotchRecordDao.query(hql, null);
		List<EqmSpotchRecodeBean> rows = new ArrayList<EqmSpotchRecodeBean>();
		if(map.size()>0){
			for(EqmSpotchRecode b:map){
				EqmSpotchRecodeBean bean = new EqmSpotchRecodeBean();
				bean.setEqpName(b.getEqpName());
				bean.setShiftName(b.getShiftName());
				bean.setTeamName(b.getTeamName());
				bean.setChLocation(b.getChLocation());
				/*int sta = b.getChStatus();
				String status="";
				if(sta==0){//状态，暂时写死，0-未完成，1-已完成，2-有故障
					status="未完成";
				}else if(sta==1){
					status="完成";
				}else if(sta==2){
					status="有故障";
				}*/
				bean.setChStatus(b.getChStatus());
				bean.setChDate(b.getChDate().toString());
				bean.setCreateUserName(b.getCreateUserName());
				bean.setUpdateTime(b.getUpdateTime().toString());
				bean.setRemark(b.getRemark());
				bean.setId(b.getId());
				bean.setEqmId(b.getEqmId().toString());
				rows.add(bean);
			}
		}
		return new DataGrid(rows,null);
		/*String hql=null;
		List<EqmSpotchRecodeBean> rows=new ArrayList<EqmSpotchRecodeBean>();
		if (StringUtil.notNull(czgId)&&!czgId.equals("undefined")&&!czgId.equals("null")) {
			hql="from EqmSpotchRecode o where o.id= ?";
			EqmSpotchRecode map=(EqmSpotchRecode) eqmCheckcatPlanDao.unique(hql.toString(), czgId);
			EqmSpotchRecodeBean bean=new EqmSpotchRecodeBean(
					czgId,
					map.getEqmId().getId(), 
					map.getEqpName(),
					map.getShiftId().getId(),
					map.getShiftName(),
					map.getTeamId(), 
					map.getTeamName(),
					map.getChTypeId(), 
					map.getChTypeName(), 
					map.getChLocation(), 
					map.getChStandard(), 
					map.getChMethod(), 
					DateUtil.formatDateToString(map.getChDate(),"yyyy-MM-dd"),
					map.getCreateUserId(), 
					map.getCreateUserName(),
					DateUtil.formatDateToString(map.getCreateTime(),"yyyy-MM-dd HH:mm:ss"),
					map.getChStatus(), 
					map.getBreLocation(), 
					map.getRemark(),
					map.getBreId(),
					map.getUpdateUserId(), 
					map.getUpdateUserName(),
					DateUtil.formatDateToString(map.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"),
					map.getDel()
					);
			rows.add(bean);
			
		}
		if (StringUtil.notNull(wxgId)&&!wxgId.equals("undefined")&&!wxgId.equals("null")) {
			hql="from EqmSpotchRecode o where o.id= ?";
			EqmSpotchRecode map=(EqmSpotchRecode) eqmCheckcatPlanDao.unique(hql.toString(), wxgId);
			EqmSpotchRecodeBean bean=new EqmSpotchRecodeBean(
					czgId,
					map.getEqmId().getId(), 
					map.getEqpName(),
					map.getShiftId().getId(),
					map.getShiftName(),
					map.getTeamId(), 
					map.getTeamName(),
					map.getChTypeId(), 
					map.getChTypeName(), 
					map.getChLocation(), 
					map.getChStandard(), 
					map.getChMethod(), 
					DateUtil.formatDateToString(map.getChDate(),"yyyy-MM-dd"),
					map.getCreateUserId(), 
					map.getCreateUserName(),
					DateUtil.formatDateToString(map.getCreateTime(),"yyyy-MM-dd HH:mm:ss"),
					map.getChStatus(), 
					map.getBreLocation(), 
					map.getRemark(),
					map.getBreId(),
					map.getUpdateUserId(), 
					map.getUpdateUserName(),
					DateUtil.formatDateToString(map.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"),
					map.getDel()
					);
			rows.add(bean);
			
		}
		if (StringUtil.notNull(wxzugId)&&!wxzugId.equals("undefined")&&!wxzugId.equals("null")) {
			hql="from EqmSpotchRecode o where o.id= ?";
			EqmSpotchRecode map=(EqmSpotchRecode) eqmCheckcatPlanDao.unique(hql.toString(), wxzugId);
			EqmSpotchRecodeBean bean=new EqmSpotchRecodeBean(
					czgId,
					map.getEqmId().getId(), 
					map.getEqpName(),
					map.getShiftId().getId(),
					map.getShiftName(),
					map.getTeamId(), 
					map.getTeamName(),
					map.getChTypeId(), 
					map.getChTypeName(), 
					map.getChLocation(), 
					map.getChStandard(), 
					map.getChMethod(), 
					DateUtil.formatDateToString(map.getChDate(),"yyyy-MM-dd"),
					map.getCreateUserId(), 
					map.getCreateUserName(),
					DateUtil.formatDateToString(map.getCreateTime(),"yyyy-MM-dd HH:mm:ss"),
					map.getChStatus(), 
					map.getBreLocation(), 
					map.getRemark(),
					map.getBreId(),
					map.getUpdateUserId(), 
					map.getUpdateUserName(),
					DateUtil.formatDateToString(map.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"),
					map.getDel()
					);
			rows.add(bean);
		
		}
		return new DataGrid(rows, 10L);*/
	}
	
	/**
	 * 根据工单查点检记录
	 */
	@Override
	public DataGrid queryPlanInfo(EqmSpotchRecodeBean eSRBean,PageParams pageParams) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("(SELECT eqp_name,CONVERT(varchar(50),create_time,23) as date_p,");
		sb.append(" shift_name,ch_date,(SELECT DES from SYS_EQP_TYPE et where et.id=s.set_id) des,(SELECT name from SYS_EQP_TYPE et where et.id=s.set_id) sys_name,ch_type_name,create_user_name,id,s.ch_status,ROW_NUMBER() OVER(ORDER BY s.ch_date DESC) num  FROM EQM_SPOTCH_RECODE s where 1=1");
		if(StringUtil.notNull(eSRBean.getShiftId())){
			sb.append(" and shift_id='"+eSRBean.getShiftId()+"'");
		}
		if(StringUtil.notNull(eSRBean.getEqmId())){
			sb.append(" and eqp_id='"+eSRBean.getEqmId()+"'");
		}
		//执行开始时间
		if(StringUtil.notNull(eSRBean.getChDate())){
			sb.append(" and CONVERT(varchar(50),create_time,23)>='"+eSRBean.getChDate()+"'");
		}
		//执行结束时间
		if(StringUtil.notNull(eSRBean.getEndChDate())){
			sb.append(" and CONVERT(varchar(50),create_time,23)<='"+eSRBean.getEndChDate()+"'");
		}		
		sb.append(" ) ");
		String sta="SELECT * from ";
		String end="zl where zl.num>"+pageParams.getRows()*(pageParams.getPage()-1)+" and zl.num<="+pageParams.getRows()*pageParams.getPage()+" ORDER BY zl.ch_date DESC";
		List<?> list= eqmCheckcatPlanDao.queryBySql(sta+sb.toString()+end);
		List<?> listFY= eqmCheckcatPlanDao.queryBySql(sb.toString());
		List<EqmSpotchRecodeBean> beans=new ArrayList<EqmSpotchRecodeBean>();
		if(list.size()>0){
			for(Object o:list){
				try {
					Object[] temp=(Object[]) o;
					EqmSpotchRecodeBean b= new EqmSpotchRecodeBean();
					b.setEqpName(temp[0].toString());
					if(temp[1]!=null){
						b.setOrderDate(temp[1].toString());
					}
					b.setShiftName(temp[2].toString());
					b.setChDate(temp[3].toString());
					b.setEqpTypeDes(temp[4].toString());
					b.setEqpTypeName(temp[5].toString());
					b.setChTypeName(temp[6].toString());
					b.setCreateUserName(temp[7].toString());
					b.setId(temp[8].toString());
					b.setStatus(Integer.valueOf(temp[9].toString()));
					beans.add(b);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		long total=listFY.size();
		return new DataGrid(beans, total);
	}
}
