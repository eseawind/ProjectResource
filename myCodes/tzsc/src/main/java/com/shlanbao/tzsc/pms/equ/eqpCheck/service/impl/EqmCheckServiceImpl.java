package com.shlanbao.tzsc.pms.equ.eqpCheck.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmCheckPlanDaoI;
import com.shlanbao.tzsc.base.dao.EqmCheckPlanParamDaoI;
import com.shlanbao.tzsc.base.mapping.EqmCheckPlan;
import com.shlanbao.tzsc.base.mapping.EqmCheckPlanParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.eqpCheck.bean.EqmCheckPalnParamBean;
import com.shlanbao.tzsc.pms.equ.eqpCheck.bean.EqmCheckPlanBean;
import com.shlanbao.tzsc.pms.equ.eqpCheck.service.EqmCheckServiceI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 设备点检接口
 * 
 * @author shisihai
 *
 */
@Service
public class EqmCheckServiceImpl implements EqmCheckServiceI {
	@Autowired
	private EqmCheckPlanDaoI planDao;
	@Autowired
	private EqmCheckPlanParamDaoI paramDao;

	@Override
	public void batchAdd(EqmCheckPlanBean bean) throws Exception{
		String[] roel=SysEqpTypeBase.getArrayDJRoleAll();
		//生成一级点检计划 如果选择班次班组则是只为这些班次班组添加点检计划，不选择则是为所有班次班组添加点检计划。已工厂日历为准。
		//删除重复添加的计划  先删除二级
		StringBuffer sb=new StringBuffer();
		sb.append("DELETE EQM_CHECK_PLAN_PARAM where pid in ");
		sb.append("(SELECT id from EQM_CHECK_PLAN p ");
		sb.append(" where 1=1 ");
		if(StringUtil.notNull(bean.getShift())){
			sb.append(" and shift = "+ bean.getShift());
		}
		if(StringUtil.notNull(bean.getTeam())){
			sb.append(" and team= "+bean.getTeam()+")");
		}
		if(StringUtil.notNull(bean.getMdEqpCategory())){
			sb.append(" and EQP_TYPE='"+bean.getMdEqpCategory()+"'");
		}
		sb.append(" and p.datep >='"+bean.getDate_plan1()+"' and p.datep <= '"+bean.getDate_plan2()+"')");
		planDao.updateInfo(sb.toString(),null);
		sb.delete(0, sb.length());
		
		
		//删除一级数据
		
		sb.append("DELETE EQM_CHECK_PLAN where datep >= '"+bean.getDate_plan1()+"'");
		sb.append(" and datep <= '"+bean.getDate_plan2()+"'");
		if(StringUtil.notNull(bean.getShift())){
			sb.append(" and shift = "+ bean.getShift());
		}
		if(StringUtil.notNull(bean.getTeam())){
			sb.append(" and team= "+bean.getTeam());
		}
		if(StringUtil.notNull(bean.getMdEqpCategory())){
			sb.append(" and EQP_TYPE='"+bean.getMdEqpCategory()+"'");
		}
		planDao.updateInfo(sb.toString(),null);
		sb.delete(0, sb.length());
		
		
		
		//添加一级点检计划
		String shift="SHIFT";
		String team="TEAM";
		if(StringUtil.notNull(bean.getShift())){
			shift=bean.getShift();
		}
		if(StringUtil.notNull(bean.getTeam())){
			team=bean.getTeam();
		}
		sb.append("insert into EQM_CHECK_PLAN (id,SHIFT,TEAM,DATEP,EQP_TYPE,DEL,NAME,STATUS,dicid) ");
		sb.append("select newid(),"+shift+","+team+",DATE,'"+bean.getMdEqpCategory()+"',0,'','0','"+bean.getDicID()+"'");
		sb.append(" from SCH_CALENDAR where 1=1 ");
		sb.append(" and DATE >= '"+bean.getDate_plan1()+"'");
		sb.append(" and DATE <= '"+bean.getDate_plan2()+"'");
		sb.append(" and DEL=0");
		if(StringUtil.notNull(bean.getWorkshop())){
			sb.append(" AND WORKSHOP = '"+bean.getWorkshop()+"'");
		}
		if(StringUtil.notNull(bean.getShift())){
			sb.append(" and shift= "+bean.getShift());
		}
		if(StringUtil.notNull(bean.getTeam())){
			sb.append(" and team= "+bean.getTeam());
		}
		planDao.updateInfo(sb.toString(),null);
		sb.delete(0, sb.length());
		//生成二级点检计划部分
		
		//具体的各机台
		sb.append("SELECT me.id from MD_EQUIPMENT me LEFT JOIN MD_EQP_TYPE mt ON mt.id=me.eqp_type_id LEFT JOIN MD_EQP_CATEGORY mec ON mt.cid=mec.id where mec.id='"+bean.getMdEqpCategory()+"'");	
		List<?> lt=planDao.queryBySql(sb.toString());
		sb.delete(0, sb.length());
		//获取点检详细
		sb.append("SELECT st.DES,st.DJ_TYPE,st.DJ_METHOD_ID,st.name,st.id from SYS_EQP_TYPE st LEFT JOIN SYS_EQP_CATEGORY sc ON st.cid=sc.id where sc.id='"+bean.getDicID()+"'");
		List<?> lt2=planDao.queryBySql(sb.toString());
		sb.delete(0, sb.length());
		//遍历机台
		for (int i = 0; i < lt.size(); i++) {
			String eqpid=lt.get(i).toString();
			//遍历点检项目有死数据 写死的
			for (int j = 0; j < lt2.size(); j++) {
				Object[] o=(Object[]) lt2.get(j);
				sb.append("insert into EQM_CHECK_PLAN_PARAM(id,pid,stime,enable,status,eqp_id,check_type,check_position,check_standard,check_method,dicid) ");
				sb.append(" select newid(),id,getdate(),'0','0','"+eqpid+"'"+",'"+o[1].toString()+"','"+o[0].toString()+"','"+o[3].toString()+"','"+o[2].toString()+"','"+o[4].toString()+"'");
				sb.append(" from EQM_CHECK_PLAN where 1=1 ");
				sb.append(" and DATEP >= '"+bean.getDate_plan1()+"'");
				sb.append(" and DATEP <= '"+bean.getDate_plan2()+"'");
				sb.append(" and EQP_TYPE= '" +bean.getMdEqpCategory()+"'");
				planDao.updateInfo(sb.toString(), null);
				sb.delete(0, sb.length());
			}
		}
	}
	
	/**
	 * 查询
	 * 2015.9.16--张璐
	 */
	@Override
	public DataGrid queryEqmCheckPlan(EqmCheckPlanBean checkBean,PageParams pageParams) throws Exception {
		StringBuilder hql=new StringBuilder();
		hql.append("from EqmCheckPlan o where 1=1 and o.del=0 "); 
		if(StringUtil.notNull(checkBean.getMdEqpCategory())){
			hql.append(" and o.mdEqpCategory.id = '"+ checkBean.getMdEqpCategory() +"'");
		}
		if(StringUtil.notNull(checkBean.getDate_plan1())){
			hql.append(" and o.dateP >= '"+ checkBean.getDate_plan1() +"'");
		} 
		if(StringUtil.notNull(checkBean.getDate_plan2())){
			hql.append(" and o.dateP <= '"+ checkBean.getDate_plan2() +"'");
		}
		List<EqmCheckPlan> listBean = planDao.queryByPage(hql.toString(), pageParams);	
		List<EqmCheckPlanBean> list = new ArrayList<EqmCheckPlanBean>();
		for(EqmCheckPlan check : listBean){
			EqmCheckPlanBean bean =new EqmCheckPlanBean();
			bean.setId(check.getId());
			bean.setDateP(new SimpleDateFormat("yyyy-MM-dd").format(check.getDateP()));
			bean.setMdEqpCategory(check.getMdEqpCategory().getName());
			bean.setShift(check.getShift().getName());
			bean.setStatus(check.getStatus());
			bean.setTeam(check.getTeam().getName());
			list.add(bean);
		}
		hql.insert(0,"select count(*) ");
		long total = planDao.queryTotal(hql.toString());
		return new DataGrid(list,total);
	}
	
	/**
	 * 批量删除
	 * 2015.9.16--张璐
	 */
	@Override
	public void batchDeleteEqmCheckPlan(String ids) throws Exception {
		ids=ids.replaceAll(",", "','");
		StringBuffer sb=new StringBuffer("delete EQM_CHECK_PLAN where id in('");
		sb.append(ids);
		sb.append("')");
		planDao.updateInfo(sb.toString(),null);
		sb.delete(0, sb.length());
		sb.append("delete EQM_CHECK_PLAN_PARAM where pid in('");
		sb.append(ids);
		sb.append("')");
		paramDao.queryBySql(sb.toString());
	}

	@Override
	public DataGrid queryEqmCheckPlanParam(String pid) throws Exception {
//		String hql="from EqmCheckPlanParam e left join on  where e.pid.id= ?";
//		List<EqmCheckPlanParam> ls=paramDao.query(hql, pid);
		StringBuffer sb=new StringBuffer("SELECT p.status, p.check_type,p.check_position,p.check_standard,p.check_method,m.EQUIPMENT_NAME FROM EQM_CHECK_PLAN_PARAM p ");
		sb.append(" LEFT JOIN MD_EQUIPMENT m ON m.id =p.eqp_id where p.PID='"+pid+"'");
		List<Object[]> o=(List<Object[]>) paramDao.queryBySql(sb.toString());
		List<EqmCheckPalnParamBean> eqmsp=new ArrayList<EqmCheckPalnParamBean>();
		for (Object[] o2 : o) {
			EqmCheckPalnParamBean bean=new EqmCheckPalnParamBean();
			bean.setCheckMethod(o2[4].toString());
			bean.setCheckPosition(o2[2].toString());
			bean.setCheckStandard(o2[3].toString());
			bean.setCheckType(o2[1].toString());
			bean.setStatus(o2[0].toString());
			bean.setEqpName(o2[5].toString());
			eqmsp.add(bean);
		}
		//List<EqmCheckPalnParamBean> eqms=BeanConvertor.copyList(ls, EqmCheckPalnParamBean.class);
//		List<EqmCheckPalnParamBean> eqmsp=new ArrayList<EqmCheckPalnParamBean>();
//		for (EqmCheckPlanParam e : ls) {
//			EqmCheckPalnParamBean bean=new EqmCheckPalnParamBean();
//			bean.setCheckMethod(e.getCheckMethod());
//			bean.setCheckPosition(e.getCheckPosition());
//			bean.setCheckStandard(e.getCheckStandard());
//			bean.setCheckType(e.getCheckType());
//			bean.setStatus(e.getStatus());
//			bean.setEqpId(e.getEqpid());
//			bean.setDicid(e.getDicid());
//			eqmsp.add(bean);
//		}
		Long rows=(long) eqmsp.size();
		return new DataGrid(eqmsp,rows);
	}	
	
}
