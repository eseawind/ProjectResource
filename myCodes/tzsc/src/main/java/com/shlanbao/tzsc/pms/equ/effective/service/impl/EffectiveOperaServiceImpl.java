package com.shlanbao.tzsc.pms.equ.effective.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmCullRecordDaoI;
import com.shlanbao.tzsc.base.dao.EqmParamDaoI;
import com.shlanbao.tzsc.base.dao.EqmWorkOrderTimeDaoI;
import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.PublicBean;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveGraphBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveOperaBean;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveRunTime;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveUtilizeTime;
import com.shlanbao.tzsc.pms.equ.effective.beans.EqmCullRecord;
import com.shlanbao.tzsc.pms.equ.effective.service.EffectiveOperaServiceI;
import com.shlanbao.tzsc.utils.excel.ExportExcel;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: EffectiveOperaServiceImpl 
* @Description:  
* @author luo
* @date 2015年3月9日 下午3:01:32 
*
 */
@Service
public class EffectiveOperaServiceImpl extends BaseService implements EffectiveOperaServiceI{
	
	
	private static String EXCLUDKEY="TCSJ";//剔除时间key
	@Autowired
	private BaseDaoI<Object> baseDao;
	@Autowired
	public  EqmParamDaoI eqmParamDaoI;
	@Autowired
	public  EqmWorkOrderTimeDaoI eqmWorkOrderTimeDaoI;
	@Autowired
	public  EqmCullRecordDaoI eqmCullRecordDao;
	
	@Override
	public DataGrid queryList(EffectiveOperaBean seachBean,PageParams pageParams) {
		
		StringBuffer hql = genHql(seachBean,1);
		List<?> totalList=baseDao.queryBySql(hql.toString());
		long total =Long.parseLong(totalList.get(0).toString());
		//start -起始页       ednum -每页多少条
		Integer start=1;
		Integer ednum=1;
		Integer sp=pageParams.getPage();
		Integer rn=pageParams.getRows();
		start=(sp-1)*rn;
		ednum=sp*rn;
		hql = genHql(seachBean,2);
		//hql.append( " and t.rum BETWEEN "+start+" and "+ednum);
		hql.append(" ) eff where 1=1 ");
		hql.append( " and eff.rownum  > "+start+" and eff.rownum <= "+ednum);
		hql.append("  ORDER BY datef desc");
		List<?> list=baseDao.queryBySql(hql.toString());
		List<EffectiveOperaBean> returnList=new ArrayList<EffectiveOperaBean>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EffectiveOperaBean b=new EffectiveOperaBean();
			b.setArea(temp[1].toString());//chejian
			b.setEqpName(temp[2].toString());
			b.setEqpType(temp[3].toString());
			b.setDate(temp[4].toString());
			b.setShift(temp[5].toString());
			b.setTeam(temp[6].toString());
			b.setRunTime(Double.parseDouble(temp[7].toString()));
			b.setDownTime(Double.parseDouble(temp[8].toString()));
			b.setExcludTime(Double.parseDouble(temp[9].toString()));
			b.setRbTime(Double.parseDouble(temp[10].toString()));
			b.setTimeUnit(temp[11].toString());
			b.setQty(Double.parseDouble(temp[12].toString()));
			b.setDwUnit(temp[13].toString());
			b.setEqty(Double.parseDouble(temp[14].toString()));
			b.setEqtyUnit(temp[15].toString());
			double rTime=Double.parseDouble(temp[7].toString());
			double rbTime=Double.parseDouble(temp[10].toString());
			double ExcludTime=Double.parseDouble(temp[9].toString());
			double eatTime=SysEqpTypeBase.jcTime;
			double q1=(rTime-rbTime-ExcludTime-eatTime)/(60+0.0)*Double.parseDouble(temp[14].toString());
			double q2=Double.parseDouble(temp[12].toString());
			if(q1==0){
				b.setWorNum("0");
			}else{
				BigDecimal a1=new BigDecimal(q2);
				BigDecimal b1=new BigDecimal(q1);
				b.setWorNum(""+MathUtil.roundHalfUp((a1.divide(b1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2));
			}
//			b.setArea(temp[0].toString());
//			b.setEqpName(temp[1].toString());
//			b.setEqpType(temp[2].toString());
//			b.setDate(DateUtil.formatStrDate(temp[3].toString(),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"));
//			b.setTeam(temp[4].toString());
//			b.setShift(temp[5].toString());
//			b.setRunTime(Double.parseDouble(temp[6].toString()));
//			b.setDownTime(Double.parseDouble(temp[7].toString()));
//			b.setDwUnit(temp[8].toString());
//			b.setQty(Double.parseDouble(temp[9].toString()));
//			b.setQtyUnit(temp[10].toString());
//			b.setEqty(Double.parseDouble(temp[11].toString()));
//			b.setEqtyUnit(temp[12].toString());
//			b.setTimeUnit("分钟");
//			//实际开机运行时间
//			try {
//				b.setAllTime(Double.parseDouble(temp[13].toString()));
//			} catch (Exception e) {
//				b.setAllTime(0d);
//			}
//			//剔除时间
//			try {
//				b.setExcludTime(Double.parseDouble(temp[14].toString()));
//			} catch (Exception e) {
//				b.setExcludTime(0);
//			}
//			//生产率
//			try {
//				Double deltim=b.getExcludTime(); //剔除时间
//				Double alltim=b.getAllTime(); //实际工作时间
//				Double worNum=b.getQty(); //实际工作量
//				Double sepNum=b.getEqty(); //车速
//                //设备有效作业率
//				if(alltim-deltim<=0){
//					b.setWorNum("0%");
//				}else{
//					Integer vle=  (int) ((worNum /(  ( (alltim-deltim)/60 ) * sepNum ))*100);
//				    if(vle!=0){
//				    	b.setWorNum(vle.toString()+"%");
//				    }else{
//				    	b.setWorNum("0%");
//				    }
//				}
//			} catch (Exception e) {
//				b.setEffectiveOpera(0);
//			}
			returnList.add(b);
		}
		return new DataGrid(returnList,total);
	}
	
	/**
	 * type  1-查询总数   2-查询集合
	 * 
	 * */
	public StringBuffer genHql(EffectiveOperaBean seachBean,int type){
		StringBuffer b = new StringBuffer();
		if(type==1){
			b.append(" select count(*) ");
		}else{
			b.append("SELECT * from (SELECT ROW_NUMBER () OVER (ORDER BY t.code) AS rownum,t.workshop,t.name AS eqmName,t.eqp_type,t.datef,t.shiftName AS shift,t.team_name as team,t.gd_all_time,t.STOP_TIME,t.tctm_all_time,t.rb_all_time,t.TIME_UNIT,t.qty,t.qty_unit,t.yie_id,t.yield_unit,t.code,t.EQP_TYPE_ID ");
		}
		b.append(" FROM (SELECT DISTINCT(gd.shift),gd.shiftName,gd.TIME_UNIT,gd.qty_Unit as qty_unit,gd.YIELD_UNIT as yield_unit,gd.team_name, ");
		b.append(" gd.code,gd.EQP_TYPE_ID,gd.STOP_TIME,gd.eqp,gd.eqp_type as eqp_type, gd.workshop,gd.equipment_name AS name,gd.datef,isnull(gd.qty, 0) AS qty,");
		b.append(" gd.yie_id,isnull(gd.gd_all_time, 0) AS gd_all_time,isnull(tc.rb_all_time, 0) AS rb_all_time,isnull(gd.tc_all_time, 0) AS tctm_all_time");
		b.append(" FROM (SELECT a.shift,c.EQUIPMENT_CODE AS code,i.NAME as TIME_UNIT,j.NAME as qty_Unit,d.NAME AS shiftName,e.NAME as team_name,f.NAME as workshop,g.NAME as eqp_type, g.ID as EQP_TYPE_ID,a.team,a.eqp,a.mat,a. DATE AS datef,c.equipment_name,b.qty,b.bad_qty,b.stim,b.etim,b.run_time, b.STOP_TIME,b.stop_times,c.yie_id,c.YIELD_UNIT,datediff(MINUTE, b.stim, b.etim) AS gd_all_time,");
		b.append(" ( SELECT SUM (stop_time)FROM EQM_CULL_RECORD WHERE CONVERT (VARCHAR(100), st_date, 23) = CONVERT (VARCHAR(100), a. DATE, 23)AND eqp_id = a.eqp AND shift_id = a.shift GROUP BY CONVERT (VARCHAR(100), st_date, 23),eqp_id,shift_id) AS tc_all_time");
		b.append(" FROM SCH_WORKORDER a LEFT JOIN SCH_STAT_OUTPUT b ON a.id = b.oid LEFT JOIN MD_EQUIPMENT c ON c.id = a.eqp");
		b.append(" LEFT JOIN MD_SHIFT d ON a.shift = d.ID LEFT JOIN MD_TEAM e ON e.id=a.TEAM LEFT JOIN MD_WORKSHOP f ON f.id=c.WORK_SHOP LEFT JOIN MD_EQP_TYPE g ON g.id=c.EQP_TYPE_ID	LEFT JOIN MD_UNIT i on i.ID=b.TIME_UNIT");
		b.append(" LEFT JOIN MD_UNIT j ON j.id=b.UNIT WHERE a.del = 0 AND b.del = 0 AND b.etim IS NOT NULL AND sts = 4");
		if(StringUtil.notNull(seachBean.getEqpType())){
			b.append(" AND g.ID=='"+seachBean.getEqpType()+"' ");//设备类型
		}
		if(StringUtil.notNull(seachBean.getEqpName())){
			b.append(" AND c.EQUIPMENT_NAME like  '%"+seachBean.getEqpName()+"%' ");//设备名称
		}	
		//开始时间
		if(StringUtil.notNull(seachBean.getStartTime())){
			b.append(" AND a.[DATE]>='"+seachBean.getStartTime()+"' ");
		}
		//结束时间
		if(StringUtil.notNull(seachBean.getEndTime())){
			b.append(" and a.[DATE]<='"+seachBean.getEndTime()+"' ");
		}
		b.append(" ) gd ");
		b.append(" LEFT JOIN (SELECT shift,date_p AS datef,eqp_type,datediff(MINUTE, stim, etim) AS rb_all_time FROM EQM_PAULDAY WHERE del = 0 AND status = 3) tc ON tc.shift = gd.shift AND CONVERT (VARCHAR(32), tc.datef) = CONVERT (VARCHAR(32), gd.datef)) t  ");
		
//		if(type==1){
//			hql.append(" select count(*) ");
//		}else{
//			hql.append(" select t.area,t.eqpName,t.eqpType,t.btime,t.team,t.shift,t.runTime,t.downTime,t.timeUnit,t.qty,t.qtyUnit,t.yieId,t.yieldUnit, ");
//			hql.append(" t.all_time,t.stop_time ");
//		}
//		
//		hql.append(" from ( ");
//		hql.append(" SELECT ");
//		hql.append("   row_number() over(ORDER BY b.[date] DESC) as rum, ");
//		hql.append("   (  select sum(stop_time) from EQM_CULL_RECORD where 1=1  and CONVERT(varchar(100), st_date, 23) =  b.[date]  and  eqp_id=b.eqp and shift_id=b.shift  and  team_id=b.team  GROUP BY eqp_id,shift_id,team_id ) as stop_time, ");
//		hql.append("   (select name from md_workshop where id=c.work_shop ) as area, ");
//		hql.append("   (select EQUIPMENT_NAME from MD_EQUIPMENT where id=b.eqp) as eqpName, ");
//		hql.append("   d.name as eqpType, ");
//		hql.append("   d.id as eqpTypeId, ");
//		hql.append("   b.[date] as btime, ");
//		hql.append("   (select name from MD_TEAM where id=b.team) as team, ");
//		hql.append("   (select name from MD_SHIFT where id=b.shift) as shift, ");
//		hql.append("   a.run_time as runTime, ");
//		hql.append("   a.stop_time as downTime, ");
//		hql.append("   (select name from md_unit where id=a.unit) as timeUnit, ");
//		hql.append("   (a.qty-a.bad_qty) as qty, ");
//		hql.append("   (select name from md_unit where id=a.time_unit) as qtyUnit, ");
//		hql.append("   (select yie_id from MD_EQUIPMENT where id=b.eqp) as yieId, ");
//		hql.append("    c.yield_unit as yieldUnit, ");
//		hql.append("   ( datediff(minute, a.stim, a.etim)) as all_time ");
//		   //( (datediff(minute, a.stim, a.etim))/60)*(select yie_id from MD_EQUIPMENT where id=b.eqp)  as effectiveOpera
//		hql.append(" FROM ");
//		hql.append("	 SCH_STAT_OUTPUT a ");
//		hql.append(" LEFT JOIN SCH_WORKORDER b ON a.oid = b.id ");
//		hql.append(" left JOIN MD_EQUIPMENT c on b.EQP=c.id ");
//		hql.append(" left join MD_EQP_TYPE d on c.eqp_type_id=d.id ");
//		hql.append(" where a.del=0 ");
//        //设备机型
//		if(StringUtil.notNull(seachBean.getEqpType())){
//			hql.append(" and d.id ='"+seachBean.getEqpType()+"' ");
//		}
//		//设备名称
//		if(StringUtil.notNull(seachBean.getEqpName())){
//			hql.append("and c.equipment_name like '%"+seachBean.getEqpName()+"%' ");
//		}
//				
//		//开始时间
//		if(StringUtil.notNull(seachBean.getStartTime())){
//			hql.append("and CONVERT(varchar(100), b.[date], 23)  >='"+seachBean.getStartTime()+"' ");
//		}
//		//结束时间
//		if(StringUtil.notNull(seachBean.getEndTime())){
//			hql.append("and CONVERT(varchar(100), b.[date], 23) <='"+seachBean.getEndTime()+"' ");
//		}
//		hql.append(" ) t where 1=1  ");
//		
//		
//		
		return b;
	}
	/**
	 * shisihai 2015-10-16
	 */
	@Override
	public EffectiveGraphBean queryGraph(EffectiveOperaBean seachBean){
        //获得sql
		//StringBuffer hql = retHqlEuipment(seachBean);
		StringBuffer hql=this.genHql(seachBean,2);
		hql.append(" ) eff ");
		hql.append("  ORDER BY datef desc");
		
        //得到结果集
		List<?> list=baseDao.queryBySql(hql.toString());
		
		Comparator p= new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o1, Object[] o2) {
				if(o1[4]!=null&&o2[4]!=null){
					Date date1=DateUtil.formatStringToDate(o1[4].toString(), "yyyy-MM-dd");
					Date date2=DateUtil.formatStringToDate(o2[4].toString(), "yyyy-MM-dd");
					if(date1.getTime()>date2.getTime()){
						return 1;
					}
				}
				return -1;
			}
		};
		Collections.sort(list,p);
		//定义拼接结果集
		TreeMap<String,String> ht1=new TreeMap<String,String>();
		TreeMap<String,String> ht2=new TreeMap<String,String>();
		TreeMap<String,Object[]> ht3=new TreeMap<String,Object[]>();
		int xlength=0;
		int ylength=0;
		for(Object o:list){
			Object[] temp=(Object[])o;
			temp[4]=DateUtil.formatDateToString((Date)(temp[4]), "yyyy-MM-dd");
			try {
				//获取设备名称
				if(temp[2]==null){
					continue;
				}
				if(ht1.get(temp[2].toString()+temp[4].toString())==null){
					ht1.put(temp[2].toString()+temp[4], temp[2].toString()+temp[4]);
					xlength++;
				}
				//获取班组名称
				if(ht2.get(temp[5].toString())==null){
					ht2.put(temp[5].toString(), temp[5].toString());
					ylength++;
				}
				//
				if(ht3.get(temp[2].toString()+temp[5].toString()+temp[4])==null)
					ht3.put(temp[2].toString()+temp[4]+temp[5].toString(), temp);
				
			    }catch (Exception e) {
				e.printStackTrace();
			}
	     }
		Iterator<String> i1=ht1.keySet().iterator();
		Iterator<String> i2=ht2.keySet().iterator();
		//X轴值类型
		String[] xValue=new String[xlength];
		//Y轴值类型
		String[] yvalueType=new String[ylength];
		//Y轴值
		double[][] yValue=new double[ylength][xlength];
		int i=0;
		int j;
		while(i1.hasNext()){
			String key=i1.next();
			xValue[i]=ht1.get(key);//设备名+时间
			j=0;
			i2=ht2.keySet().iterator();
			while(i2.hasNext()){
				String i2Key=i2.next();
				yvalueType[j]=i2Key;
				Object o=ht3.get(ht1.get(key)+ht2.get(i2Key));
				if(o!=null){
					Object[] temp=(Object[])o;
					//计算有效作业率
					double rTime=Double.parseDouble(temp[7]==null?"0.0":temp[7].toString());//运行时间
					double rbTime=Double.parseDouble(temp[10]==null?"0.0":temp[10].toString());//日保时间
					double ExcludTime=Double.parseDouble(temp[9]==null?"0.0":temp[9].toString());//剔除时间
					double eatTime=SysEqpTypeBase.jcTime;//吃饭时间
					double q1=(rTime-rbTime-ExcludTime-eatTime)/(60+0.0)*Double.parseDouble(temp[14]==null?"0.0":temp[14].toString());
					double q2=Double.parseDouble(temp[12]==null?"0.0":temp[12].toString());
					if(q1==0){
						yValue[j][i]=0.0;
					}else{
						BigDecimal a1=new BigDecimal(q2);
						BigDecimal b1=new BigDecimal(q1);
						yValue[j][i]=(MathUtil.roundHalfUp((a1.divide(b1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2));
					}
//					double planValue=Double.parseDouble(temp[4].toString());
//					if(planValue>0){
//						double actualValue=Double.parseDouble(temp[3].toString());
//						yValue[j][i]=MathUtil.roundHalfUp(actualValue*100/planValue, 2);
//					}else{
//						yValue[j][i]=0.0;
//					}
				}else{
					yValue[j][i]=0.0;
				}
				j++;
			}
			i++;
		}
		EffectiveGraphBean bean=new EffectiveGraphBean();
		bean.setXvalue(xValue);
		bean.setYvalue(yValue);
		bean.setYvalueType(yvalueType);
		return bean;
	}
	//设备运行效率
	@Override
	public DataGrid queryRunTimeEffective(EffectiveRunTime seachBean,PageParams pageParams){
		StringBuffer sb=new StringBuffer();
//		sb.append("select count(0) ");
//		StringBuffer sb2=new StringBuffer();
//		StringBuffer hql = new StringBuffer();
//		sb2.append(" SELECT t.shop,t.eqp_name,t.eqp_type,CONVERT(VARCHAR(100),t.work_Date,23),t.shift,t.team,t.run_time,t.stop_time,t.tc_time,t.unit,t.qty,t.yie_id,t.rb_time");
//		hql.append(" FROM(	SELECT row_number () OVER (ORDER BY me.EQUIPMENT_CODE) AS rownumber,mw.NAME as shop,me.equipment_name AS eqp_name,met.NAME AS eqp_type,sw. DATE AS work_Date,ms.name AS shift,mt.name AS team,ISNULL(a.run_time, 0.0) AS run_time,a.stop_time AS stop_time,ISNULL(c.tc_time, 0.0) AS tc_time, mu.name as unit,ISNULL(a.qty, 0.0) AS qty,	b.yie_id,ISNULL(d.rb_time, 0.0) AS rb_time,me.equipment_code AS code");
//		hql.append(" FROM SCH_STAT_OUTPUT sso");
//		hql.append(" LEFT JOIN SCH_WORKORDER sw ON sso.oid = sw.id");
//		hql.append(" LEFT JOIN MD_EQUIPMENT me ON sw.eqp = me.ID");
//		hql.append(" LEFT JOIN MD_WORKSHOP mw ON me.WORK_SHOP = mw.ID");
//		hql.append(" LEFT JOIN MD_EQP_TYPE met ON me.EQP_TYPE_ID = met.ID");
//		hql.append(" LEFT JOIN MD_SHIFT ms ON sw.SHIFT = ms.ID");
//		hql.append(" LEFT JOIN MD_TEAM mt ON mt.id = sw.TEAM");
//		hql.append(" LEFT JOIN MD_UNIT mu ON sso.TIME_UNIT=mu.ID");
//		hql.append(" LEFT JOIN (SELECT SUM (ISNULL(DATEDIFF(MINUTE, s1.STIM, s1.ETIM),0.0)) AS run_time,SUM (ISNULL(s1.STOP_TIME, 0.0)) AS stop_time,SUM (ISNULL(s1.QTY, 0)) AS qty,s1.OID FROM 	SCH_STAT_OUTPUT s1 GROUP BY	s1.OID) a ON a.OID = sw.ID");
//		hql.append(" LEFT JOIN (SELECT yie_id,id FROM MD_EQUIPMENT) b ON b.id = sw.EQP");
//		hql.append(" LEFT JOIN (SELECT SUM (ISNULL(s2.stop_time, 0)) AS tc_time,	s2.eqp_id,CONVERT (VARCHAR(100), st_date, 23) AS c_date FROM EQM_CULL_RECORD s2 GROUP BY s2.eqp_id,s2.st_date,s2.shift_id) c ON c.eqp_id = me.ID");
//		hql.append(" AND c.c_date = CONVERT (VARCHAR(100), sw.[DATE], 23)");
//		hql.append(" LEFT JOIN (SELECT SUM (ISNULL(datediff(MINUTE, s3.STIM, s3.ETIM) + "+SysEqpTypeBase.jcTime+",0)) AS rb_time,s3.DATE_P AS dp,s3.EQP_TYPE FROM EQM_PAULDAY s3	WHERE s3.STATUS = 2 GROUP BY	s3.DATE_P,s3.EQP_TYPE,s3.SHIFT) d ON CONVERT (VARCHAR(100), sw.[DATE], 23) = CONVERT (VARCHAR(100), d.dp, 23)AND met.CID = d.EQP_TYPE");
//		hql.append(" WHERE 1=1 ");
//		//开始时间
//		if(StringUtil.notNull(seachBean.getStartTime())){
//			hql.append("and sw.[DATE] >='"+seachBean.getStartTime()+"' ");
//		}
//		//结束时间
//		if(StringUtil.notNull(seachBean.getEndTime())){
//				hql.append("and sw.[DATE] <='"+seachBean.getEndTime()+"' ");
//		}
//		hql.append(" GROUP BY me.equipment_name,a.qty,sw. DATE,b.yie_id,a.run_time,c.tc_time,	d.rb_time,me.equipment_code,mw.NAME,met.NAME,ms.name,mt.name,a.stop_time,mu.name");
//		hql.append(" ) t where 1=1 ");
//		//设备名称
//		if(StringUtil.notNull(seachBean.getEqpName())){
//			hql.append("and t.eqp_name like '%"+seachBean.getEqpName()+"%' ");
//		}
//		//设备机型
//		if(StringUtil.notNull(seachBean.getEqpType())){
//			hql.append("and t.eqp_type ='"+seachBean.getEqpType()+"' ");
//		}
//		String str=sb.append(hql).toString();
//		List<?> lisf=baseDao.queryBySql(str);
//		Integer l= (Integer) lisf.get(0);
//		hql.append(" and t.rownumber >= " +(pageParams.getPage()-1)*pageParams.getRows());
//		hql.append(" and  t.rownumber <= "+pageParams.getPage()*pageParams.getRows());
//		String sql2=sb2.append(hql).toString();
		
		sb=this.getSql(seachBean);
		List<Object[]> list=(List<Object[]>) baseDao.queryBySql(sb.toString());
		List<EffectiveRunTime> returnList=new ArrayList<EffectiveRunTime>();
		Object[] temp=null;
		EffectiveRunTime b=null;
		for (Object[] o : list) {
			temp=(Object[]) o;
			b=new EffectiveRunTime();
			try {
				if(temp[0]==null){
					continue;
				}
				b.setArea(temp[0].toString());
				b.setEqpName(temp[1].toString());
				b.setEqpType(temp[2].toString());
				//b.setDate(DateUtil.formatStrDate(temp[3].toString(),"yyyy-MM-dd","yyyy-MM-dd"));
				//b.setTeam(temp[4].toString());
				b.setShift(temp[3].toString());
				Double a=Double.parseDouble(temp[7].toString());//运行时间
				b.setRunTime(a);
				Double a2=Double.parseDouble(temp[8].toString());//剔除时间
				Double a3=Double.parseDouble(temp[5].toString());//采集产量
				Double a4=Double.parseDouble(temp[9].toString());//日保和吃饭时间
				Double a5=Double.parseDouble(temp[6].toString());//台时产量
				b.setDownTime(Double.parseDouble(temp[10].toString()));//停机时间
				double p_time=(a-a2-a4)/(60+0.0);
				if(p_time>0){
					b.setEffectiveRunTime(MathUtil.roundHalfUp(a3/(p_time*a5) * 100,2));
				}else{
					b.setEffectiveRunTime(0);//运行效率
				}
				b.setTimeUnit(temp[11].toString());
				b.setExcludTime(a2+a4);
			} catch (Exception e) {
				e.printStackTrace();
			}
			returnList.add(b);
		}
//		
		int s=returnList.size();
		return new DataGrid(returnList,(long) s);
	}
	
	//查询运行效率图表数据源
	public EffectiveGraphBean queryRunTimeEffectiveChart(EffectiveRunTime seachBean){
		StringBuffer hql = new StringBuffer();
		hql=this.getSql(seachBean);
//		hql.append("select ");
//		hql.append("o.schWorkorder.mdEquipment.equipmentName ");//设备名称0
//		hql.append(",o.schWorkorder.mdEquipment.mdEqpType.name ");//设备机型1
//		hql.append(",o.schWorkorder.mdTeam.name ");//班组2
//		hql.append(",sum(o.runTime) ");//运行时间3
//		hql.append(",sum(o.stopTime) ");//停机时间4
//		hql.append(",sum(o.runTime+o.stopTime) ");//运行总时间5
//		//运行时间/(运行时间+停机时间)×100%  
//		hql.append(" from SchStatOutput o ");
//		hql.append("where 1=1 and o.del=0 ");//卷烟机和包装机
//		//设备机型
//		if(StringUtil.notNull(seachBean.getEqpType())){
//			hql.append("and o.schWorkorder.mdEquipment.mdEqpType.id ='"+seachBean.getEqpType()+"' ");
//		}
//		//机组code,可多匹配
//		if(StringUtil.notNull(seachBean.getEqpId())){
//			if(StringUtil.notNull(parseString(seachBean.getEqpId()))){
//				hql.append("and o.schWorkorder.mdEquipment.id in ("+parseString(seachBean.getEqpId())+") ");
//			}
//		}
//		//设备名称
//		if(StringUtil.notNull(seachBean.getEqpName())){
//			hql.append("and o.schWorkorder.mdEquipment.equipmentName like '%"+seachBean.getEqpName()+"%' ");
//		}
//		//车间
//		if(StringUtil.notNull(seachBean.getArea())){
//			hql.append("and o.schWorkorder.mdEquipment.mdWorkshop.name ='"+seachBean.getArea()+"' ");
//		}
//		//开始时间
//		if(StringUtil.notNull(seachBean.getStartTime())){
//			hql.append("and o.schWorkorder.date >='"+seachBean.getStartTime()+"' ");
//		}
//		//结束时间
//		if(StringUtil.notNull(seachBean.getEndTime())){
//				hql.append("and o.schWorkorder.date <='"+seachBean.getEndTime()+"' ");
//		}
//		//班组
//		if(StringUtil.notNull(seachBean.getTeam())){
//			if(StringUtil.notNull(parseString(seachBean.getTeam()))){
//				hql.append("and o.schWorkorder.mdTeam.id in ("+parseString(seachBean.getTeam())+") ");
//			}
//		}
//		hql.append("group by ");
//		hql.append("o.schWorkorder.mdEquipment.equipmentName ");//设备名称2
//		hql.append(",o.schWorkorder.mdEquipment.mdEqpType.name ");//设备机型3
//		hql.append(",o.schWorkorder.mdTeam.name ");//班组5
//		
		List<Object> list=(List<Object>) baseDao.queryBySql(hql.toString());
		TreeMap<String,String> ht1=new TreeMap<String,String>();
		TreeMap<String,String> ht2=new TreeMap<String,String>();
		TreeMap<String,Object[]> ht3=new TreeMap<String,Object[]>();
		int xlength=0;
		int ylength=0;
		for(Object o:list){
			Object[] temp=(Object[])o;
			if(temp[0]==null){
				continue;
			}
			//获取设备名称
			if(ht1.get(temp[1].toString())==null){
				ht1.put(temp[1].toString(), temp[1].toString());
				xlength++;
			}
			//获取班次名称
			if(ht2.get(temp[3].toString())==null){
				ht2.put(temp[3].toString(), temp[3].toString());
				ylength++;
			}
			//
			if(ht3.get(temp[1].toString()+temp[3].toString())==null)
				ht3.put(temp[1].toString()+temp[3].toString(), temp);
			
		}
		Iterator<String> i1=ht1.keySet().iterator();
		Iterator<String> i2=ht2.keySet().iterator();
		//X轴值类型
		String[] xValue=new String[xlength];
		//Y轴值类型
		String[] yvalueType=new String[ylength];
		//Y轴值
		double[][] yValue=new double[ylength][xlength];
		int i=0;
		int j;
		//循环设备
		while(i1.hasNext()){
			String key=i1.next();
			xValue[i]=ht1.get(key);
			j=0;
			i2=ht2.keySet().iterator();
			//循环班组信息
			while(i2.hasNext()){
				String i2Key=i2.next();
				yvalueType[j]=i2Key;
				Object o=ht3.get(ht1.get(key)+ht2.get(i2Key));
				if(o!=null){
					Object[] temp=(Object[])o;
					//以防除数为0
					if(Double.parseDouble(temp[7].toString())>0){
						Double a=Double.parseDouble(temp[7].toString());//运行时间
						Double a2=Double.parseDouble(temp[8].toString());//剔除时间
						Double a3=Double.parseDouble(temp[5].toString());//采集产量
						Double a4=Double.parseDouble(temp[9].toString());//日保和吃饭时间
						Double a5=Double.parseDouble(temp[6].toString());//台时产量
						double p_time=(a-a2-a4)/(60+0.0);
						yValue[j][i]=MathUtil.roundHalfUp(a3/(p_time*a5) * 100,2);;
					}else{
						yValue[j][i]=0.0;
					}
				}else{
					yValue[j][i]=0.0;
				}
				j++;
			}
			i++;
		}
		EffectiveGraphBean bean=new EffectiveGraphBean();
		bean.setXvalue(xValue);
		bean.setYvalue(yValue);
		bean.setYvalueType(yvalueType);
		return bean;
	}
	
	//数据转换 1,2,3,4 转换成 '1','2','3','4'
	private String parseString(String value){
		String[] obj=value.split(",");
		String temp="";
		for(String o:obj){
			if(o.equals("")){continue;} 
			temp+=",'"+o+"'";
		}
		if(temp.length()<2){return "";}
		return temp.substring(1, temp.length());
	}

	//设备有效利用率查询方法
	@Override
	public DataGrid quertyUtilizeEffective(EffectiveUtilizeTime seachBean,PageParams pageParams) {
		String eqpName="";
		String stime="";
		String etime="";
		String team="";
		String eqpTypeId="";
		String eqpId="";
		Integer start=1;
		Integer ednum=1;
		//起始页-每页多少条
		if(pageParams.getPage()==1){// 1-10  10-20  20-30
			start=1;
			ednum=pageParams.getRows();
		}else{
			ednum=pageParams.getPage()*pageParams.getRows();
			start=ednum-pageParams.getRows();
		}
		//设备机型
		if(StringUtil.notNull(seachBean.getEqpType())){
			eqpTypeId=" and b.eqp_type_id ='"+seachBean.getEqpType()+"' ";
		}
		//机组code,可多匹配
		if(StringUtil.notNull(seachBean.getEqpId())){
			if(StringUtil.notNull(parseString(seachBean.getEqpId()))){
				eqpId=" and a.eqp in ("+parseString(seachBean.getEqpId())+") ";
			}
		}
		//设备名称
		if(StringUtil.notNull(seachBean.getEqpName())){
			eqpName=" and b.equipment_name like '%"+seachBean.getEqpName()+"%' ";
		}
		//开始时间
		if(StringUtil.notNull(seachBean.getStartTime())){
			stime=" and a.date >='"+seachBean.getStartTime()+"' ";
		}
		//结束时间
		if(StringUtil.notNull(seachBean.getEndTime())){
			etime=" and a.date  <='"+seachBean.getEndTime()+"' ";
		}
		//班组
		if(StringUtil.notNull(seachBean.getTeam())){
			if(StringUtil.notNull(parseString(seachBean.getTeam()))){
				team=" and a.team in ("+parseString(seachBean.getTeam())+") ";
			}
		}
		String colum="select" 
                +" (select name from MD_WORKSHOP where id=b.work_shop) as work_shop_name,"
				+" b.equipment_name ,"
				+" (select name from MD_EQP_TYPE where id=b.eqp_type_id) as eqp_type_name,"
				+" a.date,"
				+" (select name from md_shift where id=a.shift) as shift_name,"
				+" (select name from MD_TEAM where id=a.team) as team_name,"
				+" datediff(minute, a.stim, a.etim) as all_time,"
				+" c.run_time,"
				+" (c.run_time/datediff(minute, a.stim, a.etim))*100 as all_num,"
				+" row_number() over(ORDER BY a.date DESC) as rum"
				+" from  SCH_WORKORDER  a left join MD_EQUIPMENT b on a.eqp=b.id "
				+" left join SCH_STAT_OUTPUT c on a.id=c.oid"
				+" where 1=1 "+stime+etime+eqpName+team+eqpTypeId+eqpId+" and a.sts=4 and a.del=0";
                //+")t where 1=1 and t.rum  between "+start+" and "+ednum+"";
		
		String sqlCount="select count(*) from ("+colum+")t where 1=1";
		String sqllist="select * from ("+colum+")t where 1=1 and t.rum  between "+start+" and "+ednum+"";
		
		
		List<?> totalList=baseDao.queryBySql(sqlCount);
		long total =Long.parseLong(totalList.get(0).toString());
		List<?> list=baseDao.queryBySql(sqllist);
		List<EffectiveUtilizeTime> returnList=new ArrayList<EffectiveUtilizeTime>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EffectiveUtilizeTime b=new EffectiveUtilizeTime();
			b.setArea(temp[0].toString());
			b.setEqpName(temp[1].toString());
			b.setEqpType(temp[2].toString());
			b.setDate(DateUtil.formatStrDate(temp[3].toString(),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"));
			b.setTeam(temp[4].toString());
			b.setShift(temp[5].toString());
			b.setPlanTime(Double.parseDouble(temp[6].toString()));
			b.setRunSumTime(Double.parseDouble(temp[7].toString()));
			Double val=Double.parseDouble(temp[8].toString());
			double ret = convert(val);
			b.setEffectiveUtilize(ret);
			returnList.add(b);
		}
		return new DataGrid(returnList,total);
	}

	

	private double convert(Double val) {
		long ft = Math.round(val*100); //四舍五入
		double ret = ft/100.0; //注意:使用 100.0 而不是 100
		return ret;
	}

	@Override
	public EffectiveGraphBean quertyUtilizeEffectiveChart(EffectiveUtilizeTime seachBean) {
		String eqpName="";
		String stime="";
		String etime="";
		String team="";
		String eqpTypeId="";
		String eqpId="";
		//设备机型
		if(StringUtil.notNull(seachBean.getEqpType())){
			eqpTypeId=" and b.eqp_type_id ='"+seachBean.getEqpType()+"' ";
		}
		//机组code,可多匹配
		if(StringUtil.notNull(seachBean.getEqpId())){
			if(StringUtil.notNull(parseString(seachBean.getEqpId()))){
				eqpId=" and a.eqp in ("+parseString(seachBean.getEqpId())+") ";
			}
		}
		//设备名称
		if(StringUtil.notNull(seachBean.getEqpName())){
			eqpName=" and b.equipment_name like '%"+seachBean.getEqpName()+"%' ";
		}
		//开始时间
		if(StringUtil.notNull(seachBean.getStartTime())){
			stime=" and a.date >='"+seachBean.getStartTime()+"' ";
		}
		//结束时间
		if(StringUtil.notNull(seachBean.getEndTime())){
			etime=" and a.date  <='"+seachBean.getEndTime()+"' ";
		}
		//班组
		if(StringUtil.notNull(seachBean.getTeam())){
			if(StringUtil.notNull(parseString(seachBean.getTeam()))){
				team=" and a.team in ("+parseString(seachBean.getTeam())+") ";
			}
		}
		
		String sql=" select t.equipment_name,t.eqp_type_name,t.shift_name,sum(t.run_time)/sum(t.all_time) as all_num from ("
				+" select" 
				+"  b.equipment_name ,"
				+" (select name from MD_EQP_TYPE where id=b.eqp_type_id) as eqp_type_name,"
				+" (select name from md_shift where id=a.shift) as shift_name,"
				+" datediff(minute, a.stim, a.etim) as all_time,"
				+" c.run_time as run_time"
				+" from  SCH_WORKORDER  a left join MD_EQUIPMENT b on a.eqp=b.id "
				+" left join SCH_STAT_OUTPUT c on a.id=c.oid"
				+" where 1=1 "+stime+etime+eqpName+team+eqpTypeId+eqpId+" and a.sts=4 and a.del=0"
				+" )t where 1=1 group by t.equipment_name,t.eqp_type_name,t.shift_name";
		
		List<?> list=baseDao.queryBySql(sql);
		Hashtable<String,String> ht1=new Hashtable<String,String>();
		Hashtable<String,String> ht2=new Hashtable<String,String>();
		Hashtable<String,Object[]> ht3=new Hashtable<String,Object[]>();
		int xlength=0;
		int ylength=0;
		for(Object o:list){
			Object[] temp=(Object[])o;
			try {
				//获取设备名称
				if(ht1.get(temp[0].toString())==null){
					ht1.put(temp[0].toString(), temp[0].toString());
					xlength++;
				}
				//获取班组名称
				if(ht2.get(temp[2].toString())==null){
					ht2.put(temp[2].toString(), temp[2].toString());
					ylength++;
				}
				//
				if(ht3.get(temp[0].toString()+temp[2].toString())==null)
					ht3.put(temp[0].toString()+temp[2].toString(), temp);
			} catch (Exception e) {
				// TODO: handle exception
			}
		    }
		Iterator<String> i1=ht1.keySet().iterator();
		Iterator<String> i2=ht2.keySet().iterator();
		//X轴值类型
		String[] xValue=new String[xlength];
		//Y轴值类型
		String[] yvalueType=new String[ylength];
		//Y轴值
		double[][] yValue=new double[ylength][xlength];
		int i=0;
		int j;
		//循环设备
		while(i1.hasNext()){
			String key=i1.next();
			xValue[i]=ht1.get(key);
			j=0;
			i2=ht2.keySet().iterator();
			//循环班组信息
			while(i2.hasNext()){
				String i2Key=i2.next();
				yvalueType[j]=i2Key;
				Object o=ht3.get(ht1.get(key)+ht2.get(i2Key));
				if(o!=null){
					Object[] temp=(Object[])o;
					//以防除数为0
					Double valt=Double.parseDouble(temp[3].toString())*100;
					if(valt>0){
						yValue[j][i]=MathUtil.roundHalfUp(valt,2);
					}else{
						yValue[j][i]=0.0;
					}
				}else{
					yValue[j][i]=0.0;
				}
				j++;
			}
			i++;
		}
		EffectiveGraphBean bean=new EffectiveGraphBean();
		bean.setXvalue(xValue);
		bean.setYvalue(yValue);
		bean.setYvalueType(yvalueType);
		return bean;
	}

	/**
	 * 【功能说明】：获得PMS系统的设备有效作业率
	 * 
	 *  @time 2015年7月26日9:16:56
	 *  @author wchuang
	 * 
	 * */
	public StringBuffer retHqlEuipment(EffectiveOperaBean seachBean){
		StringBuffer b = new StringBuffer();
		b.append(" select k.eqpName,k.eqpType,k.team,sum( k.qty) as snum,sum(k.f) as enum from ( ");
		b.append(" 		 select t.eqpName,t.eqpType,t.team,t.qty,t.yieId, ( (t.all_time-t.stop_time)/60 )*t.yieId  as f  from ( ");
		b.append(" 		   SELECT ");
		b.append(" 		      case when ( (select sum(stop_time) from EQM_CULL_RECORD where 1=1  and CONVERT(varchar(100), st_date, 23) =  b.[date]  and  eqp_id=b.eqp and shift_id=b.shift  and  team_id=b.team  GROUP BY eqp_id,shift_id,team_id ) is null ) then 0.00 else ");
		b.append(" 		           (select sum(stop_time) from EQM_CULL_RECORD where 1=1  and CONVERT(varchar(100), st_date, 23) =  b.[date]  and  eqp_id=b.eqp and shift_id=b.shift  and  team_id=b.team  GROUP BY eqp_id,shift_id,team_id ) ");
		b.append(" 		      end as stop_time, ");
		b.append(" 		      (select EQUIPMENT_NAME from MD_EQUIPMENT where id=b.eqp) as eqpName, ");
		b.append(" 		      d.name as eqpType, ");
		b.append(" 		      d.id as eqpTypeId, ");
		b.append(" 		      b.[date], ");
		b.append(" 		      (select name from MD_TEAM where id=b.team) as team, ");
		b.append(" 		      (select name from MD_SHIFT where id=b.shift) as shift, ");
		b.append(" 		      a.stim,a.etim, (a.qty-a.bad_qty) as qty,  (select yie_id from MD_EQUIPMENT where id=b.eqp) as yieId, ");
		b.append(" 		      case  when ( ( datediff(minute, a.stim, a.etim)) IS  NULL) then 0.00 else ( datediff(minute, a.stim, a.etim)) end as all_time ");
		b.append(" 		   FROM ");
		b.append(" 			   SCH_STAT_OUTPUT a ");
		b.append(" 			 LEFT JOIN SCH_WORKORDER b ON a.oid = b.id ");
		b.append(" 			 left JOIN MD_EQUIPMENT c on b.EQP=c.id ");
		b.append(" 			 left join MD_EQP_TYPE d on c.eqp_type_id=d.id ");
		b.append(" 		   where a.del=0  ");
		//设备机型
		if(StringUtil.notNull(seachBean.getEqpType())){
			b.append(" and d.id ='"+seachBean.getEqpType()+"' ");
		}
		//设备名称
		if(StringUtil.notNull(seachBean.getEqpName())){
			b.append("and c.equipment_name like '%"+seachBean.getEqpName()+"%' ");
		}		
		//开始时间
		if(StringUtil.notNull(seachBean.getStartTime())){
			b.append("and CONVERT(varchar(100), b.[date], 23)  >='"+seachBean.getStartTime()+"' ");
		}
		//结束时间
		if(StringUtil.notNull(seachBean.getEndTime())){
			b.append("and CONVERT(varchar(100), b.[date], 23) <='"+seachBean.getEndTime()+"' ");
		}
		b.append(" 		 ) t where 1=1   ");
		b.append(" 		)k group by k.eqpName,k.eqpType,k.team ");
		return b;
	}
	
	/**
     *  功能说明：设备停机剔除时间管理-添加
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月21日16:18:46
     *  
     * */
	@Override
	public boolean addCullRecord(EqmCullRecordBean bean) {
		boolean falg=false;
		try {
			falg=eqmCullRecordDao.save(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return falg;
	}

	/**
	 *  【功能说明】：设备停机剔除时间管理-查询
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月22日10:36:42
     *  
	 * */ 
	@Override
	public DataGrid queryCullRecord(EqmCullRecordBean bean,
			PageParams pageParams) {
		String eqp_id="";
		String shift_id="";
		String st_date="";
		String ed_date="";
		String type_id="";
		//停机类型
		if(StringUtil.notNull(bean.getType_id())){
			type_id=" and type_id='"+bean.getType_id()+"'";
		}
		//设备ID
		if(StringUtil.notNull(bean.getEqp_id())){
			eqp_id=" and eqp_id='"+bean.getEqp_id()+"'";
		}
		//班次
        if(StringUtil.notNull(bean.getShift_id())){
			shift_id=" and shift_id="+bean.getShift_id();
		}
		//开始时间
        if(StringUtil.notNull(bean.getRuntime())){
			st_date=" and CONVERT (VARCHAR(32), st_date, 23) >='"+bean.getRuntime()+"'";
		}
        //结束时间
        if(StringUtil.notNull(bean.getEndtime())){
			ed_date=" and CONVERT (VARCHAR(32), st_date, 23) <='"+bean.getEndtime()+"'";
		}
		String clumn="( select ROW_NUMBER () OVER ( ORDER BY ed_date desc ) bh,id,(select equipment_name from MD_EQUIPMENT where id=eqp_id ) as eqp_name,(select name from  md_shift where id=shift_id) as shift_name,st_date,ed_date,remark,stop_time,"
				+"case type_id  when 1 then '固定停机' when 2 then '不可控停机' when 3 then '提前完工' end as type_id, type_name from EQM_CULL_RECORD  where 1=1 and del=1 "+type_id+eqp_id+shift_id+st_date+ed_date+" ) t ";
		
		Integer start=1;
		Integer ednum=1;
		//起始页-每页多少条
		if(pageParams.getPage()==1){// 1-10  10-20  20-30
			start=1;
			ednum=pageParams.getRows();
		}else{
			ednum=pageParams.getPage()*pageParams.getRows();
			start=ednum-pageParams.getRows();
		}
		String sqllit="select * from"+clumn+" where 1=1 and  t.bh BETWEEN "+start+" and "+ednum;
		String sqltol="select count(*) from" +clumn;
	    //总条数
    	List<?> tl= baseDao.queryBySql(sqltol);
		String t=tl.get(0).toString();
		long total=Long.parseLong(t);
		//对象集合
		List<?> list=baseDao.queryBySql(sqllit);
		List<EqmCullRecord> returnList=new ArrayList<EqmCullRecord>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EqmCullRecord b=new EqmCullRecord();
			try {
				b.setId(temp[1].toString());
				b.setEqp_name(temp[2].toString());
				b.setShift_name(temp[3].toString());
				b.setSt_date(temp[4].toString().substring(0,19));
				b.setEd_date(temp[5].toString().substring(0,19));
				b.setRemark(temp[6].toString());
				b.setStop_time(temp[7].toString());
				b.setType_id(temp[8].toString());
				b.setType_name(temp[9].toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			returnList.add(b);
		}
		return new DataGrid(returnList,total);
	}

	/**
	 * 【功能说明】：设备停机剔除时间管理-查询
	 *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月22日14:26:21
	 * 
	 * */
	@Override
	public boolean deleteCullRecordById(EqmCullRecordBean bean) {
		boolean flag=false;
		try {
			eqmCullRecordDao.deleteById(bean.getId(), EqmCullRecordBean.class);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public HSSFWorkbook ExportExcelJBEffic(EffectiveOperaBean bean) {
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 15 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,15,1,设备有效作业率");
			th.add("2,1,2,1,生产日期：");
			th.add("2,3,6,1," + bean.getStartTime()+ "   到     "
					+ bean.getEndTime());
			th.add("3,1,1,3,车间");
			th.add("3,2,2,3,设备名称");
			th.add("3,3,3,3,设备型号");
			th.add("3,4,4,3,生产日期");
			th.add("3,5,5,3,班次");
			th.add("3,6,6,3,班组");
			th.add("3,7,7,3,运行时间");

			// 物料消耗
			th.add("3,8,8,3,停机时间");

			th.add("3,9,9,3,剔除时间");
			th.add("3,10,10,3,时间单位");
			th.add("3,11,11,3,产量");

			th.add("3,12,12,3,产量单位");
			th.add("3,13,13,3,台时产量");
			th.add("3,14,14,3,单位");

			th.add("3,15,15,3,有效作业率");

			List<EffectiveOperaBean> ls = this.geteqpEfficList(bean);
//			Map<String, String> maps = new HashMap<String, String>();
//			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
//			if (ls!= null) {
//				for (int i = 0; i < ls.size(); i++) {
//					EffectiveOperaBean efficBean = (EffectiveOperaBean) ls.get(i);
//					maps.put((4 + i) + ",1,1,1",efficBean.getArea());
//					maps.put((4 + i) + ",2,2,1",efficBean.getEqpName());
//					maps.put((4 + i) + ",3,3,1",efficBean.getEqpType());
//					maps.put((4 + i) + ",4,4,1", efficBean.getDate());
//					maps.put((4 + i) + ",5,5,1", efficBean.getShift());
//					maps.put((4 + i) + ",6,6,1", efficBean.getTeam());
//					maps.put((4 + i) + ",7,7,1", ""+efficBean.getRunTime());
//					maps.put((4 + i) + ",8,8,1",""+ efficBean.getDownTime());
//					maps.put((4 + i) + ",9,9,1", ""+efficBean.getExcludTime());
//					maps.put((4 + i) + ",10,10,1", efficBean.getTimeUnit());
//					maps.put((4 + i) + ",11,11,1",""+ efficBean.getQty());
//					maps.put((4 + i) + ",12,12,1", efficBean.getDwUnit());
//					maps.put((4 + i) + ",13,13,1", ""+efficBean.getEqty());
//					maps.put((4 + i) + ",14,14,1", efficBean.getEqtyUnit());
//					maps.put((4 + i) + ",15,15,1", efficBean.getWorNum());
//					tdVals.add(maps);
//				}
//			}
			// td 当前开始行,当前结束行,一共多少列
//			int[] tdTables = { 4, (4 + tdVals.size() - 1), 15 };
//			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			
			String[] method=new String[]
					{"getArea","getEqpName","getEqpType","getDate",
					"getShift","getTeam","getRunTime","getDownTime",
					"getExcludTime","getTimeUnit","getQty","getDwUnit",
					"getEqty","getEqtyUnit","getWorNum"
					};
			//开始行
			int startLine=3;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,EffectiveOperaBean.class,ls);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	private List<EffectiveOperaBean> geteqpEfficList(EffectiveOperaBean bean){
		StringBuffer sql=this.genHql(bean, 2);
		sql.append(" ) eff ");
		sql.append("  ORDER BY datef desc");
		List<?> list=baseDao.queryBySql(sql.toString());
		List<EffectiveOperaBean> returnList=new ArrayList<EffectiveOperaBean>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EffectiveOperaBean b=new EffectiveOperaBean();
			b.setArea(temp[1].toString());//chejian
			b.setEqpName(temp[2].toString());
			b.setEqpType(temp[3].toString());
			b.setDate(temp[4].toString());
			b.setShift(temp[5].toString());
			b.setTeam(temp[6].toString());
			b.setRunTime(Double.parseDouble(temp[7].toString()));
			b.setDownTime(Double.parseDouble(temp[8].toString()));
			b.setExcludTime(Double.parseDouble(temp[9].toString()));
			b.setRbTime(Double.parseDouble(temp[10].toString()));
			b.setTimeUnit(temp[11].toString());
			b.setQty(Double.parseDouble(temp[12].toString()));
			b.setDwUnit(temp[13].toString());
			b.setEqty(Double.parseDouble(temp[14].toString()));
			b.setEqtyUnit(temp[15].toString());
			double rTime=Double.parseDouble(temp[7].toString());
			double rbTime=Double.parseDouble(temp[10].toString());
			double ExcludTime=Double.parseDouble(temp[9].toString());
			double eatTime=SysEqpTypeBase.jcTime;
			double q1=(rTime-rbTime-ExcludTime-eatTime)/(60+0.0)*Double.parseDouble(temp[14].toString());
			double q2=Double.parseDouble(temp[12].toString());//（实际运行时间-日保时间-剔除时间-吃饭时间）/60 * 台时产量
			if(q1==0){
				b.setWorNum("0");
			}else{
				BigDecimal a1=new BigDecimal(q2);
				BigDecimal b1=new BigDecimal(q1);
				b.setWorNum(""+MathUtil.roundHalfUp((a1.divide(b1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2));
			}
			returnList.add(b);
		}
		return returnList;
	}
	/**
	 * 导出设备运行效率
	 * shisihai
	 * 2015-9-28
	 */
	@Override
	public HSSFWorkbook deriveEqpRunEfficExcel(EffectiveRunTime bean) {
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 5 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,5,1,卷包设备运行效率月统计");
			th.add("2,1,2,2,生产日期：");
			th.add("2,3,5,2," + bean.getStartTime()+ "   到     "
					+ bean.getEndTime());
			th.add("3,1,1,3,机台");
			th.add("3,2,2,3,实际产量");
			th.add("3,3,3,3,实际工作时间");
			th.add("3,4,4,3,额定产能");
			th.add("3,5,5,3,运行效率");
			List<EffectiveOperaBean> Jyls = queryRunTimeEffective2(bean,"jy").getRows();
			List<EffectiveOperaBean> Bzls = queryRunTimeEffective2(bean,"bz").getRows();
			List<PublicBean> data=new ArrayList<PublicBean>();
			int jl=Jyls.size();
			int zl=Bzls.size();
			//卷烟机设备
			if (Jyls!= null) {
				PublicBean b=null;
				EffectiveOperaBean efficBean=null;
				for (int i = 0; i < jl; i++) {
					efficBean = (EffectiveOperaBean) Jyls.get(i);
					b=new PublicBean();
					b.setAttr1(efficBean.getEqpName());
					b.setAttr2(""+efficBean.getQty());
					//实际时间
					double a=(efficBean.getRunTime()-efficBean.getRbTime()-efficBean.getExcludTime())/(60+0.0);
					b.setAttr3(""+a);
					//额定产量
					double b0=((efficBean.getRunTime()-efficBean.getRbTime()-efficBean.getExcludTime())/(60+0.0)*efficBean.getEqty());
					b.setAttr4(""+b0);
					b.setAttr5(efficBean.getWorNum());
					data.add(b);
				}
			}
			String[] hj1=getHj(Jyls);
			PublicBean b=new PublicBean();
			b.setAttr1("合计");
		    b.setAttr2(hj1[0]);
		    b.setAttr3(hj1[1]);
		    b.setAttr4(hj1[2]);
		    b.setAttr5(hj1[3]);
			data.add(b);
			//包装机设备
			if (Bzls!= null) {
				EffectiveOperaBean efficBean=null;
				PublicBean b1=null;
				for (int i = 0; i < zl; i++) {
					efficBean = (EffectiveOperaBean) Bzls.get(i);
					b1=new PublicBean();
					b1.setAttr1(efficBean.getEqpName());
					b1.setAttr2(""+efficBean.getQty());
					//实际时间
					double a=(efficBean.getRunTime()-efficBean.getRbTime()-efficBean.getExcludTime())/(60+0.0);
					b1.setAttr3(""+a);
					//额定产量
					double b0=((efficBean.getRunTime()-efficBean.getRbTime()-efficBean.getExcludTime())/(60+0.0)*efficBean.getEqty());
					b1.setAttr4(""+b0);
					b1.setAttr5(""+efficBean.getWorNum());
					data.add(b1);
				}
			}
			String[] hj2=getHj(Bzls);
			
			PublicBean b3=new PublicBean();
			b3.setAttr1("合计");
		    b3.setAttr2(hj2[0]);
		    b3.setAttr3(hj2[1]);
		    b3.setAttr4(hj2[2]);
		    b3.setAttr5(hj2[3]);
			data.add(b3);
			// td 当前开始行,当前结束行,一共多少列
//			int[] tdTables = { 4, (4 + tdVals.size() +3), 5 };
//			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			
			String[] method=new String[]
			{"getAttr1","getAttr2","getAttr3","getAttr4","getAttr5"};
			//开始行
			int startLine=3;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,PublicBean.class,data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	};
	/**
	 * 设备月运行效率查询
	 * shisihai
	 * 2015-09-28
	 * @param seachBean
	 * @param code  机台code  30以下是卷烟机
	 * @return
	 */
	public DataGrid queryRunTimeEffective2(EffectiveRunTime seachBean,String code){
		
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT sour.code,sour.eqp_name AS eqp_name,ISNULL(SUM (sour.qty), 0.0) AS qty,sour.yie_id AS yid,ISNULL(SUM (sour.run_time), 0.0) AS run_time,ISNULL(SUM (sour.tc_time), 0.0) AS tc_time,ISNULL(SUM (sour.rb_time), 0.0) AS rb_time");
		hql.append(" FROM (SELECT me.equipment_name AS eqp_name,ISNULL(a.qty, 0.0)AS qty,sw. DATE AS work_Date,b.yie_id,ISNULL(a.run_time, 0.0) as run_time,ISNULL(c.tc_time, 0.0) as tc_time,ISNULL(d.rb_time, 0.0) as rb_time,me.equipment_code AS code ");
		hql.append("  FROM SCH_STAT_OUTPUT sso");
		hql.append("  LEFT JOIN SCH_WORKORDER sw ON sso.oid = sw.id ");
		hql.append(" LEFT JOIN MD_EQUIPMENT me ON sw.eqp = me.ID ");
		//设备名称
		if(StringUtil.notNull(seachBean.getEqpName())){
			hql.append(" and me.equipment_Name like '%"+seachBean.getEqpName()+"%' ");
		}
		hql.append(" 	LEFT JOIN MD_WORKSHOP mw ON me.WORK_SHOP = mw.ID ");
		hql.append(" LEFT JOIN MD_EQP_TYPE met ON me.EQP_TYPE_ID = met.ID");
		//设备机型
		if(StringUtil.notNull(seachBean.getEqpType())){
			hql.append(" and met.id ='"+seachBean.getEqpType()+"' ");
		}
		hql.append(" LEFT JOIN (SELECT SUM (ISNULL(DATEDIFF(MINUTE, s1.STIM, s1.ETIM), 0.0)) AS run_time,SUM (ISNULL(s1.QTY, 0)) AS qty,s1.OID FROM SCH_STAT_OUTPUT s1 GROUP BY s1.OID ) a ON a.OID = sw.ID ");
		hql.append(" LEFT JOIN (SELECT yie_id,id FROM MD_EQUIPMENT) b ON b.id = sw.EQP");
		hql.append(" LEFT JOIN (SELECT SUM (ISNULL(s2.stop_time, 0)) AS tc_time,s2.eqp_id,CONVERT (VARCHAR(100), st_date, 23) AS c_date FROM EQM_CULL_RECORD s2 GROUP BY s2.eqp_id,s2.st_date,s2.shift_id) c ON c.eqp_id = me.ID AND c.c_date = CONVERT (VARCHAR(100), sw.[DATE], 23) ");
		hql.append(" LEFT JOIN (SELECT SUM (ISNULL(datediff(MINUTE,s3.STIM,s3.ETIM)+"+SysEqpTypeBase.jcTime+",0)) AS rb_time,s3.DATE_P AS dp,s3.EQP_TYPE	FROM EQM_PAULDAY s3 where s3.STATUS=3 GROUP BY s3.DATE_P,s3.EQP_TYPE,s3.SHIFT ) d ON CONVERT (VARCHAR(100), sw.[DATE], 23) = CONVERT (VARCHAR(100), d.dp, 23) AND met.CID = d.EQP_TYPE");
		hql.append(" GROUP BY me.equipment_name ,a.qty ,sw.date ,b.yie_id,a.run_time,c.tc_time,d.rb_time,me.equipment_code ) sour ");
		hql.append(" where 1=1 and  ");
		//分机型查询
		if(StringUtil.notNull(code)){
			if(code.equals("jy")){
				hql.append(" sour.code <=30 ");
			}else{
				hql.append(" sour.code >=31 and sour.code <=60 ");
			}
		}
		//开始时间
		if(StringUtil.notNull(seachBean.getStartTime())){
			hql.append("and sour.work_date >='"+seachBean.getStartTime()+"' ");
		}
		//结束时间
		if(StringUtil.notNull(seachBean.getEndTime())){
				hql.append("and sour.work_date <='"+seachBean.getEndTime()+"' ");
		}
		hql.append("  GROUP BY eqp_name  ,sour.yie_id ,sour.code ");
		
		List<Object[]> list=(List<Object[]>) baseDao.queryBySql(hql.toString());
		long total =list.size();
		List<EffectiveOperaBean> returnList=new ArrayList<EffectiveOperaBean>();
		for(Object[] o:list){
			Object[] temp=o;
			EffectiveOperaBean b=new EffectiveOperaBean();
			b.setEqpName(temp[1].toString());//设备名称
			b.setQty(Double.parseDouble(temp[2].toString()));//总产量
			b.setEqty(Double.parseDouble(temp[3].toString()));//台时产量
			b.setRunTime(Double.parseDouble(temp[4].toString()));//运行时间
			b.setExcludTime(Double.parseDouble(temp[5].toString()));//剔除时间
			b.setRbTime(Double.parseDouble(temp[6].toString()));//日保时间
			double q1=(Double.parseDouble(temp[4].toString())-Double.parseDouble(temp[5].toString())-Double.parseDouble(temp[6].toString()))/(60+0.0)*Double.parseDouble(temp[3].toString());
			double q2=Double.parseDouble(temp[2].toString());
			if(q1==0){
				b.setWorNum("0");
			}else{
				BigDecimal a1=new BigDecimal(q2);
				BigDecimal b1=new BigDecimal(q1);
				b.setWorNum(""+MathUtil.roundHalfUp((a1.divide(b1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2));
			}
			returnList.add(b);
		}
		return new DataGrid(returnList,total);
	}
	/**
	 * 获取合计
	 * shisihai
	 * 2015-09-28
	 * @param ls
	 * @return qty,r_time,p_qty,r_effic
	 */
	public String[] getHj(List<EffectiveOperaBean> ls){
		double qty=0.0;
		double r_time=0.0;
		double p_qty=0.0;
		double r_effic=0.0;
		long size=ls.size();
		for (EffectiveOperaBean bean : ls) {
			qty+=bean.getQty();
			r_time+=(bean.getRunTime()-bean.getExcludTime()-bean.getRbTime())/(60+0.0);
			p_qty+=((bean.getRunTime()-bean.getExcludTime()-bean.getRbTime())/(60+0.0))*bean.getEqty();
		}
		if(p_qty==0){
			r_effic=0.0;
		}else{
			BigDecimal a1=new BigDecimal(p_qty);
			BigDecimal b1=new BigDecimal(qty);
			r_effic=MathUtil.roundHalfUp((b1.divide(a1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2);
		}
		return new String[]{""+MathUtil.roundHalfUp(qty,2),""+MathUtil.roundHalfUp(r_time,2),""+MathUtil.roundHalfUp(p_qty,2),""+r_effic};
	}
	
	private StringBuffer getSql(EffectiveRunTime seachBean){
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" sour.shopName,"//车间
				+ "sour.eqp_name AS eqp_name,"//机台名
				+ "sour.eqpType,"//设备型号
				+ "sour.shift,"//班次
				+ "sour.code,");//设备code
		sb.append(" ISNULL(SUM(sour.qty), 0.0) AS qty, ");//产量
		sb.append(" sour.yie_id AS yid,");//台时产量
		sb.append(" ISNULL(SUM(sour.run_time), 0.0) AS run_time, ");//运行时间
		sb.append(" ISNULL(SUM(sour.tc_time), 0.0) AS tc_time,");//剔除时间
		sb.append(" ISNULL(SUM(sour.rb_time), 0.0) AS rb_time,");//日保时间和吃饭时间
		sb.append(" ISNULL(SUM(sour.stims), 0.0) as stims, ");//停机时间
		sb.append(" sour.unit_name ");
		sb.append(" FROM ");
		sb.append(" (SELECT mw.NAME as shopName,ms.name as shift,met.name as eqpType,me.equipment_name AS eqp_name,ISNULL(a.qty, 0.0) AS qty,");
		sb.append(" sw. DATE AS work_Date,b.yie_id,ISNULL(a.run_time, 0.0) AS run_time,ISNULL(c.tc_time, 0.0) AS tc_time,ISNULL(d.rb_time, 0.0) AS rb_time,");
		sb.append(" me.equipment_code AS code,e.stims,met.id as type_id,mu.NAME as unit_name ");
		sb.append(" FROM ");
		sb.append(" SCH_STAT_OUTPUT sso ");
		sb.append(" LEFT JOIN SCH_WORKORDER sw ON sso.oid = sw.id and sso.etim is not null and sw.sts=4 ");
		sb.append(" LEFT JOIN MD_EQUIPMENT me ON sw.eqp = me.ID and me.del=0 ");
		sb.append(" LEFT JOIN MD_WORKSHOP mw ON me.WORK_SHOP = mw.ID ");
		sb.append(" LEFT JOIN MD_EQP_TYPE met ON me.EQP_TYPE_ID = met.ID and met.del=0 ");
		sb.append(" LEFT JOIN MD_SHIFT ms ON ms.ID=sw.SHIFT ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=sw.TEAM ");
		sb.append(" LEFT JOIN MD_UNIT mu ON sso.TIME_UNIT=mu.ID ");
		sb.append(" LEFT JOIN (SELECT SUM (ISNULL( DATEDIFF(MINUTE, s1.STIM, s1.ETIM),0.0)) AS run_time, ");
		sb.append(" SUM (ISNULL(s1.QTY, 0)) AS qty, ");
		sb.append(" s1.OID ");
		sb.append(" FROM SCH_STAT_OUTPUT s1 where s1.etim is not null ");
		sb.append(" GROUP BY s1.OID ) a ON a.OID = sw.ID and sw.sts=4 ");
		sb.append(" LEFT JOIN (SELECT yie_id,id FROM MD_EQUIPMENT ) b ON b.id = sw.EQP ");
		sb.append(" LEFT JOIN (SELECT SUM (ISNULL(s2.stop_time, 0)) AS tc_time, ");
		sb.append(" s2.eqp_id,"
				+ "CONVERT (VARCHAR(100), st_date, 23) AS c_date");
		sb.append(" FROM EQM_CULL_RECORD s2 ");
		sb.append(" GROUP BY s2.eqp_id,s2.st_date,s2.shift_id ");
		sb.append(" ) c ON c.eqp_id = me.ID ");
		sb.append(" AND c.c_date = CONVERT (VARCHAR(100), sw.[DATE], 23) ");
		sb.append(" LEFT JOIN (SELECT ");
		sb.append(" SUM (ISNULL(datediff(MINUTE, s3.STIM, s3.ETIM) + "+SysEqpTypeBase.jcTime+",0)) AS rb_time, ");
		sb.append(" s3.DATE_P AS dp,s3.EQP_TYPE,s3.shift ");
		sb.append(" FROM EQM_PAULDAY s3 ");
		sb.append(" WHERE s3.STATUS = 3 ");
		sb.append(" GROUP BY s3.DATE_P,s3.EQP_TYPE,s3.SHIFT) d  ");
		sb.append(" ON CONVERT (VARCHAR(100), sw.[DATE], 23) = CONVERT (VARCHAR(100), d.dp, 23) ");
		sb.append(" AND met.CID = d.EQP_TYPE  AND sw.SHIFT=d.shift ");
		sb.append(" LEFT JOIN (SELECT OID,ISNULL(STOP_TIME, 0) as stims FROM SCH_STAT_OUTPUT  ) e ON e.OID=sw.ID ");
		sb.append(" GROUP BY me.equipment_name,a.qty,sw. DATE,b.yie_id,a.run_time,c.tc_time,d.rb_time,me.equipment_code,e.stims,mw.NAME,ms.NAME,met.name,met.id,mu.NAME ");
		sb.append(" ) sour ");
		sb.append(" WHERE 1=1 ");
		//eqpType
		if(StringUtil.notNull(seachBean.getEqpType())){
			sb.append(" and sour.type_id = '"+seachBean.getEqpType()+"'");
		}
		//eqpName
		if(StringUtil.notNull(seachBean.getEqpName())){
			sb.append(" and sour.eqp_name like '%"+seachBean.getEqpName()+"%' ");
		}
		if(StringUtil.notNull(seachBean.getStartTime())){
			sb.append(" and sour.work_date >= '"+seachBean.getStartTime()+"'");
		}
		if(StringUtil.notNull(seachBean.getEndTime())){
			sb.append(" and sour.work_date <= '"+seachBean.getEndTime()+"'");
		}
		sb.append(" GROUP BY eqp_name,sour.yie_id,sour.code,sour.shopName,sour.shift,sour.eqpType,sour.type_id,sour.unit_name ");
		return sb;
		
	}
}
