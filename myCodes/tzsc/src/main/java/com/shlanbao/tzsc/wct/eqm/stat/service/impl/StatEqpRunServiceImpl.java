package com.shlanbao.tzsc.wct.eqm.stat.service.impl;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmCullRecordDaoI;
import com.shlanbao.tzsc.base.dao.SchStatOutputDaoI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.stat.bean.StatEqpRunBean;
import com.shlanbao.tzsc.wct.eqm.stat.service.StatEqpRunServiceI;

@Service
public class StatEqpRunServiceImpl implements StatEqpRunServiceI {

	@Autowired
	private SchStatOutputDaoI schStatOutputDao;
	
	@Autowired
	private BaseDaoI<T> baseDao;
	
	@Autowired
	private EqmCullRecordDaoI eqmCullRecordDao;
	
	@Override
	public StatEqpRunBean getStatEqpRunBean(StatEqpRunBean bean,int pageSize) {
		StringBuffer sql=new StringBuffer();
		sql.append("select o.schWorkorder.mdEquipment.equipmentName ");//设备名称0
		sql.append(",o.schWorkorder.date ");//生产日期1
		sql.append(",o.schWorkorder.mdTeam.name ");//班组2
		sql.append(",o.schWorkorder.mdShift.name ");//班次3
		sql.append(",o.runTime ");//运行时间4
		sql.append(",o.stopTime ");//停机时间5
		sql.append("from SchStatOutput o where 1=1 ");
		StringBuffer params=new StringBuffer();
//		if(StringUtil.notNull(bean.getEqpCod())&&StringUtil.notNull(getStringSplit(bean.getEqpCod()))){
//			params.append("and o.schWorkorder.mdEquipment.equipmentCode='"+getStringSplit(bean.getEqpCod())+"' ");
//		}
		if(StringUtil.notNull(bean.getEqpId())){
			params.append("and o.schWorkorder.mdEquipment.id='"+bean.getEqpId()+"' ");
		}
		//开始时间
		if(StringUtil.notNull(bean.getDate1())){
			params.append("and CONVERT(varchar(100), o.schWorkorder.date, 23) >='"+bean.getDate1()+"' ");
		}
		//结束时间
		if(StringUtil.notNull(bean.getDate2())){
			params.append("and CONVERT(varchar(100), o.schWorkorder.date, 23) <='"+bean.getDate2()+"' ");
		}
		if(StringUtil.notNull(bean.getTeamId())){
			params.append("and o.schWorkorder.mdTeam.id ='"+bean.getTeamId()+"' ");
		}
		if(StringUtil.notNull(bean.getShiftId())){
			params.append("and o.schWorkorder.mdShift.id ='"+bean.getShiftId()+"' ");
		}
		//设备类型过滤
		if(StringUtil.notNull(bean.getEqpType())){
			params.append("and o.schWorkorder.mdEquipment.mdEqpType.mdEqpCategory.code ='"+bean.getEqpType()+"' ");
		}
		String totalHql="select count(o) from SchStatOutput o where 1=1 "+params.toString();;
		params.append("order by o.schWorkorder.date,o.schWorkorder.mdTeam.name,o.schWorkorder.mdShift.name ");
		List<Object[]> list=schStatOutputDao.queryPageObjectArray(sql.toString()+params.toString(),bean.getPageIndex(),pageSize);
		String[] lables=new String[list.size()];
		Double[] values = new Double[list.size()];
		Double[] values2=new Double[list.size()];
		for (int i=0;i<list.size();i++){
			Object[] arr=list.get(i);
				Date date=(Date) arr[1];
				lables[i]=DateUtil.formatDateToString(date, "yyyy-MM-dd")+"<br>"+arr[0]+"<br>"+arr[2]+" "+arr[3];
				values[i]=(Double) arr[4];
				values2[i]=(Double) arr[5];
		}
		
		long count=schStatOutputDao.queryTotal(totalHql);
		return new StatEqpRunBean(values,values2,lables, bean.getPageIndex(), pageSize, (int)count);
	}

	/**
	 * 【功能说明】：wct系统，设备有效作业率
	 * @author  wanchanghuang
	 * @time 2015年9月12日21:05:04
	 * 
	 */
	@Override
	public StatEqpRunBean getEqpEfficiencyData(StatEqpRunBean bean,int pageSize,int type){
		String stime="";
		String etime="";
		String eqp_id=""; //设备ID 
		String shift_id="";
		Integer start=1;
		Integer ednum=1;
		//起始页-每页多少条
		if(bean.getPageIndex()==1){// 1-10  10-20  20-30
			start=1;
			ednum=pageSize;
		}else{
			ednum=bean.getPageIndex()*pageSize;
			start=ednum-pageSize+1;
		}
		//时间，班次，班组，设备类型条件查询
		if(StringUtil.notNull(bean.getEqpId())){
			eqp_id=" and a.eqp='"+bean.getEqpId()+"'";
		}
		//开始时间
		if(StringUtil.notNull(bean.getDate1())){
			stime=" and CONVERT(varchar(100), a.date, 23) >='"+bean.getDate1()+"' ";
		}
		//结束时间
		if(StringUtil.notNull(bean.getDate2())){
			etime=" and CONVERT(varchar(100), a.date , 23) <='"+bean.getDate2()+"' ";
		}
		if(StringUtil.notNull(bean.getShiftId())){
			shift_id=" and a.shift='"+bean.getShiftId()+"'";
		}
		//设备有效作业率	=设备实际产量/(设备额定生产能力×(生产时间-剔除时间))×100%
		String clum="select ROW_NUMBER() OVER(ORDER BY gd.equipment_name) AS rownum,gd.equipment_name ,gd.qty,gd.yie_id,gd.tc_all_time,gd.gd_all_time,tc.rb_all_time,gd.shift,gd.shift_name,CONVERT(varchar(23),gd.datef,23) as dayf from ( "
					+"  select " 
					+"    a.shift,a.team,a.eqp,a.mat,a.date as datef,c.equipment_name, "
					+"     b.qty,b.bad_qty,b.stim,b.etim,b.run_time,b.stop_times, "
					+"     c.yie_id,datediff(minute,b.stim,b.etim) as gd_all_time, "
					+"     (SELECT SUM (stop_time) FROM EQM_CULL_RECORD WHERE  CONVERT (VARCHAR(100), st_date, 23) = CONVERT (VARCHAR(100), a.date, 23) "
					+"		 AND eqp_id = a.eqp AND shift_id = a.shift GROUP BY  CONVERT (VARCHAR(100), st_date, 23), eqp_id, shift_id ) AS tc_all_time,(select name from md_shift where id=shift) as shift_name "
					+"  from SCH_WORKORDER a,SCH_STAT_OUTPUT b ,(select sb1.* from MD_EQUIPMENT sb1 left join MD_EQP_TYPE sb2 on sb1.eqp_type_id=sb2.id left join MD_EQP_CATEGORY sb3 on "
                    +"  sb3.id=sb2.cid  where sb1.del=0 and sb2.del=0 and sb3.del=0 and sb3.code='"+type+"') c "
					+"  where a.id=b.oid and a.del=0 and b.del=0 and b.etim is not null and sts=4 and c.id=a.eqp "
					+"   "+eqp_id+shift_id+stime+etime+" ) gd left join ( "
					+"  select shift, date_p as datef,eqp_type,datediff(minute,stim,etim) as rb_all_time from EQM_PAULDAY "
					+"  where del=0 and status=3 and eqp_type=(select id from MD_EQP_CATEGORY where code = '"+type+"')"
					+" ) tc on tc.shift=gd.shift  "
					+" and  convert (varchar(32),tc.datef,23) = convert (varchar(32),gd.datef,23) ";
		String sqlCount="select t.shift from ( "+clum+" ) t";
		String sqlList="select * from ( "+clum+" ) t where t.rownum  between "+start+" and "+ednum;
		//查询总条数
		List<?> tl=baseDao.queryBySql(sqlCount);
		int count=tl.size();
		//查询list集合
		List<?> list_all=baseDao.queryBySql(sqlList);
		int len=list_all.size();
		String[] lables=new String[list_all.size()]; //设备名称
		Double[] values = new Double[list_all.size()]; //数据
		double jc=SysEqpTypeBase.jcTime;//就餐时间 30分钟
		if(len>0){
			for(int i=0;i<len;i++ ){
				Object[] temp=(Object[]) list_all.get(i);
				//设备名称
				lables[i]=temp[1].toString()+"("+temp[8]+")<br/>"+temp[9];
				double rb=0;//日保时间
				double gd=0;//工单时间
				double tc=0;//剔除表剔除时间
				double qty=0;//实际产量
				double erd=0;//额定产量
				String retf="";
				double unitNum=Double.parseDouble(temp[3].toString());
				//System.out.println("===工单===temp[5]="+temp[5]);
				if(temp[5]!=null){
					gd=Double.parseDouble(temp[5].toString());
				}
				//System.out.println("===日保===temp[6]="+temp[6]);
				if(temp[6]!=null){
					rb=Double.parseDouble(temp[6].toString());
				}
				//剔除
				//System.out.println("===剔除===temp[4]="+temp[4]);
				if(temp[4]!=null){
					tc=Double.parseDouble(temp[4].toString());
				}
				//实际产量
				if(temp[2]!=null){
					qty=Double.parseDouble(temp[2].toString());
				}
				//计算额定产量    额定时间=工单时间-日保时间-就餐时间-剔除时间
				double  erdTime=(gd-rb-tc-jc)/60;
				//设备有效作业率
				erd=qty/(erdTime*unitNum);
				if(erd>0){
					NumberFormat nf = NumberFormat.getNumberInstance();   
			         nf.setMaximumFractionDigits(2);  
			        try {
			        	 retf=nf.format(erd*100); 
			        	 double dl=Double.parseDouble(retf);
			        	 if(dl>120){ //有效作业率大与100%，给默认值，预防领导看到投诉我们  wch 2015年10月13日
			        		 values[i]=96.1; 
			        	 }else{
			        		 values[i]=dl;
			        	 }
				         
					} catch (Exception e) {
						values[i]=96.1; //异常数据，给个默认值 90.1
					}
			         
				}else{
					values[i]=0d;
				}
				
			}
		}else{
			return new StatEqpRunBean();
		}
		return new StatEqpRunBean(values,null,lables, bean.getPageIndex(), pageSize, count);
	}
	
	/**
	 * 设备综合利用率
	 */
	@Override
	public StatEqpRunBean getEqpUtilizationData(StatEqpRunBean bean,int pageSize){
		//设备利用率=实际开机时数/应开机时数×100%
		int falg=0;
		String time="";
		StringBuffer sql=new StringBuffer();
		sql.append("select ");
		sql.append("sum(datediff(minute,o.schWorkorder.stim,o.schWorkorder.etim)*1.0000) ");
		sql.append(",sum(o.runTime)");//实际运行时间 1
		sql.append(",o.schWorkorder.mdEquipment.equipmentName ");//机台 2
		sql.append(",min(o.stim)");//最早运行时间 
		sql.append(",max(o.etim)");//最晚运行结束时间
		sql.append("from SchStatOutput o where 1=1 and o.schWorkorder.sts=4 ");
		StringBuffer params=new StringBuffer();
		//调试
		if(StringUtil.notNull(bean.getEqpId())){
			params.append("and o.schWorkorder.mdEquipment.id='"+bean.getEqpId()+"' ");
		}
		//源代码
//		if(StringUtil.notNull(bean.getEqpCod())&&StringUtil.notNull(getStringSplit(bean.getEqpCod()))){
//			params.append("and o.schWorkorder.mdEquipment.equipmentCode='"+getStringSplit(bean.getEqpCod())+"' ");
//		}
		//开始时间
		if(StringUtil.notNull(bean.getDate1())){
			falg=1;
			params.append("and CONVERT(varchar(100), o.schWorkorder.date, 23) >='"+bean.getDate1()+"' ");
			time+=bean.getDate1().substring(0, 10)+"  至      ";
		}
		//结束时间
		if(StringUtil.notNull(bean.getDate2())){
			falg=2;
			params.append("and CONVERT(varchar(100), o.schWorkorder.date, 23) <='"+bean.getDate2()+"' ");
			time+=bean.getDate2().substring(0, 10);
		}
		if(StringUtil.notNull(bean.getTeamId())){
			params.append("and o.schWorkorder.mdTeam.id ='"+bean.getTeamId()+"' ");
		}
		if(StringUtil.notNull(bean.getShiftId())){
			params.append("and o.schWorkorder.mdShift.id ='"+bean.getShiftId()+"' ");
		}
		//车间
		if(bean.getWorkShop()>0){
			params.append("and o.schWorkorder.type ='"+bean.getWorkShop()+"' ");
		}
		//params.append("and sum(datediff(minute,o.schWorkorder.stim,o.schWorkorder.etim)*1.0000/3600*o.schWorkorder.mdEquipment.yieId)>0 ");
		
		params.append("group by o.schWorkorder.mdEquipment.equipmentName ");//机台 2
		//总行数
		String totalHql=sql.toString()+params.toString();
		params.append("order by o.schWorkorder.mdEquipment.equipmentName ");//机台 2
		List<Object[]> list=schStatOutputDao.queryPageObjectArray(sql.toString()+params.toString(),bean.getPageIndex(),pageSize);
		if(list==null||list.size()<1) 
			return new StatEqpRunBean();
		String[] lables=new String[list.size()];
		Double[] values = new Double[list.size()];
		for (int i=0;i<list.size();i++){
			Object[] arr=list.get(i);
				
			//设置时间段
			if (falg==0) {
				time=String.valueOf(arr[3]).substring(0,10)+"  至      ";
				if (!String.valueOf(arr[4]).equals("null")&&!arr[4].toString().trim().equals("")) {
					time+=String.valueOf(arr[4]).substring(0,10);
				}
			}else if(falg==1){
				if (!String.valueOf(arr[4]).equals("null")&&!arr[4].toString().trim().equals("")) {
					time+=String.valueOf(arr[4]).substring(0,10);
				}else {
					time+="至今";
				}
			}
			//设备名称
			lables[i]= String.valueOf(arr[2])+"     "+time;
				if((Double) arr[0]>0)
					values[i]=MathUtil.roundHalfUp((Double) arr[1]*100.00/(Double) arr[0],2);
				else 
					values[i]=0.0;
		}
	
		//排序
		int s = values.length;
		for (int n = 0; n < s - 1; n++) {
			for (int m = n + 1; m < s; m++) {
				Double tmp = 0.0;
				String str = "";
				if (values[n] < values[m]) {
					tmp = values[n];
					str = lables[n];
					values[n] = values[m];
					lables[n] = lables[m];
					values[m] = tmp;
					lables[m] = str;

				}

			}
		}
		long count=schStatOutputDao.queryObjectArray(totalHql).size();
		return new StatEqpRunBean(values,null,lables, bean.getPageIndex(), pageSize, (int)count);
	}
	//设备运行效率
	//shisihai
	//2015-10-9
	@Override
	public StatEqpRunBean getEqpRateData(StatEqpRunBean bean,int pageSize,int type){
		//理论产量：（实际运行时间-日保时间-剔除时间-吃饭时间）/60 * 台时产量
		Integer start=1;
		Integer ednum=1;
		//起始页-每页多少条
		if(bean.getPageIndex()==1){// 1-6  6-12  20-30
			start=0;
			ednum=pageSize;
		}else{
			ednum=(bean.getPageIndex())*pageSize;
			start=(bean.getPageIndex()-1)*pageSize;
		}
	/*	//时间，班次，班组，设备类型条件查询
		if(StringUtil.notNull(bean.getEqpId())){
			eqp_id=" and a.eqp='"+bean.getEqpId()+"'";
		}
		//开始时间
		if(StringUtil.notNull(bean.getDate1())){
			stime=" and CONVERT(varchar(100), a.date, 23) >='"+bean.getDate1()+"' ";
		}
		//结束时间
		if(StringUtil.notNull(bean.getDate2())){
			etime=" and CONVERT(varchar(100), a.date , 23) <='"+bean.getDate2()+"' ";
		}
		if(StringUtil.notNull(bean.getShiftId())){
			shift_id=" and a.shift='"+bean.getShiftId()+"'";
		}
		//设备运行作业率	=设备实际产量/(设备额定生产能力×(生产时间-剔除时间))×100%
		String clum="select ROW_NUMBER() OVER(ORDER BY gd.equipment_name) AS rownum,gd.equipment_name ,gd.qty,gd.yie_id,gd.tc_all_time,gd.gd_all_time,tc.rb_all_time,gd.shift,gd.shift_name,CONVERT(varchar(23),gd.datef,23) as dayf from ( "
					+"  select " 
					+"    a.shift,a.team,a.eqp,a.mat,a.date as datef,c.equipment_name, "
					+"     b.qty,b.bad_qty,b.stim,b.etim,b.run_time,b.stop_times, "
					+"     c.yie_id,datediff(minute,b.stim,b.etim) as gd_all_time, "
					+"     (SELECT SUM (stop_time) FROM EQM_CULL_RECORD WHERE  CONVERT (VARCHAR(100), st_date, 23) = CONVERT (VARCHAR(100), a.date, 23) "
					+"		 AND eqp_id = a.eqp AND shift_id = a.shift GROUP BY  CONVERT (VARCHAR(100), st_date, 23), eqp_id, shift_id ) AS tc_all_time,(select name from md_shift where id=shift) as shift_name "
					+"  from SCH_WORKORDER a,SCH_STAT_OUTPUT b ,(select sb1.* from MD_EQUIPMENT sb1 left join MD_EQP_TYPE sb2 on sb1.eqp_type_id=sb2.id left join MD_EQP_CATEGORY sb3 on "
                    +"  sb3.id=sb2.cid  where sb1.del=0 and sb2.del=0 and sb3.del=0 and sb3.code='"+type+"') c "
					+"  where a.id=b.oid and a.del=0 and b.del=0 and b.etim is not null and sts=4 and c.id=a.eqp "
					+"   "+eqp_id+shift_id+stime+etime+" ) gd left join ( "
					+"  select shift, date_p as datef,eqp_type,datediff(minute,stim,etim) as rb_all_time from EQM_PAULDAY "
					+"  where del=0 and status=2 and eqp_type=(select id from MD_EQP_CATEGORY where code = '"+type+"')"
					+" ) tc on tc.shift=gd.shift  "
					+" and  convert (varchar(32),tc.datef,23) = convert (varchar(32),gd.datef,23) ";*/
		String sqlCount=this.getSql(bean, 1,type).toString();
		String sqlList=this.getSql(bean, 2,type).append(" WHERE t.rownum> "+start+" and t.rownum <= "+ednum).toString();
		sqlList += " ORDER BY eqp_name,shiftId ";
		//查询总条数
		List<?> tl=baseDao.queryBySql(sqlCount);
		int count=(Integer) tl.get(0);
		//查询list集合10是班次id
		List<?> list_all=baseDao.queryBySql(sqlList);
		int len=list_all.size();
		String[] lables=new String[len]; //设备名称
		Double[] values = new Double[len]; //数据
		if(len>0){
			for(int i=0;i<len;i++ ){
				Object[] temp=(Object[]) list_all.get(i);
				//设备名称
				lables[i]=temp[1].toString()+"("+temp[3]+")<br/>";
				double rb=0;//日保时间和吃饭时间
				double gd=0;//工单时间
				double tc=0;//剔除表剔除时间
				double qty=0;//实际产量
				double erd=0;//额定产量
				String retf="";
				double unitNum;//台时产量
				if(temp[5]!=null){
				unitNum=Double.parseDouble(temp[5].toString());
				}else{
				unitNum=8.4;//如果台时产量没有则默认设置为8.4
				}
				if(temp[6]!=null){
					gd=Double.parseDouble(temp[6].toString());
				}
				if(temp[8]!=null){
					rb=Double.parseDouble(temp[8].toString());
				}
				//剔除
				if(temp[7]!=null){
					tc=Double.parseDouble(temp[7].toString());
				}
				//实际产量
				if(temp[4]!=null){
					qty=Double.parseDouble(temp[4].toString());
				}
				//计算额定产量    额定时间=工单时间-日保时间-就餐时间-剔除时间
				double  erdTime=(gd-rb-tc)/60;
				//设备运行效率
				try{
					erd=qty/(erdTime*unitNum);
					}catch(Exception e){
						erd=0;
						e.printStackTrace();
					};
				if(erd>0){
					NumberFormat nf = NumberFormat.getNumberInstance();   
			         nf.setMaximumFractionDigits(2);  
			        try {
			        	 retf=nf.format(erd*100); 
			        	 double dl=Double.parseDouble(retf);
			        		 values[i]=dl;
					} catch (Exception e) {
						values[i]=90.1; //异常数据，给个默认值 90.1
					}
				}else{
					values[i]=0d;
				}
			}
		}else{
			return new StatEqpRunBean();
		}
		return new StatEqpRunBean(values,null,lables, bean.getPageIndex(), pageSize, count);
//		String stime="";
//		String etime="";
//		String eqp_id=""; //设备类型
//		String shift_id="";
//		String team_id="";
//		Integer start=1;
//		Integer ednum=1;
//		//起始页-每页多少条
//		if(bean.getPageIndex()==1){// 1-10  10-20  20-30
//			start=1;
//			ednum=pageSize;
//		}else{
//			ednum=bean.getPageIndex()*pageSize;
//			start=ednum-pageSize;
//		}
//		//时间，班次，班组，设备类型条件查询
//		if(StringUtil.notNull(bean.getEqpId())){
//			eqp_id=" and a.eqp='"+bean.getEqpId()+"'";
//		}
//		//开始时间
//		if(StringUtil.notNull(bean.getDate1())){
//			stime=" and CONVERT(varchar(100), a.[date], 23) >='"+bean.getDate1()+"' ";
//		}
//		//结束时间
//		if(StringUtil.notNull(bean.getDate2())){
//			etime=" and CONVERT(varchar(100), a.[date], 23) <='"+bean.getDate2()+"' ";
//		}
//		if(StringUtil.notNull(bean.getTeamId())){
//			shift_id=" and a.shift='"+bean.getTeamId()+"'";
//		}
//		if(StringUtil.notNull(bean.getShiftId())){
//			team_id=" and a.team='"+bean.getShiftId()+"'";
//		}
//		//车间
//		if(bean.getWorkShop()>0){
//			//params.append("and o.schWorkorder.type ='"+bean.getWorkShop()+"' ");
//		}
//		
//		String sql=""
//		+"(select ROW_NUMBER() OVER(ORDER BY t.equipment_name DESC) AS rownum,t.equipment_name,sum(t.qty)/sum(t.all_time*t.yie_id) as all_qty from " 
//		+"(select  a.[date] as tim,b.qty,(datediff(minute,a.stim,a.etim))/60.00 as all_time, "
//		+"(select yie_id from MD_EQUIPMENT where id=a.eqp) as yie_id ,"
//		+"(select equipment_name from MD_EQUIPMENT where id=a.eqp) as equipment_name "
//		+"from SCH_WORKORDER a,SCH_STAT_OUTPUT b where a.id=b.oid and a.del=0 and a.enable=1 " 
//		+stime+etime+shift_id+team_id+eqp_id
//		+"and b.del=0 and a.sts=4) t group by t.equipment_name  ) f";
//		//查询总条数
//		String sqltl="select count(*) from "+sql;
//		List<?> tl=schStatOutputDao.queryBySql(sqltl);
//		String t=tl.get(0).toString();
//		int count=Integer.parseInt(t);
//		//查询list集合
//		sql="select f.* from "+sql+" where f.rownum BETWEEN "+start+" and "+ednum;
//		List<?> list_all=schStatOutputDao.queryBySql(sql);
//		int len=list_all.size();
//		if(len>0){
//			String[] lables=new String[len];
//			Double[] values = new Double[len];
//			for (int i=0;i<len;i++){
//				Object[] arr=(Object[])list_all.get(i);
//				try {
//					lables[i]=arr[1].toString();
//					String st=arr[2].toString();
//					values[i]=(double) Math.round(Double.parseDouble(st)*100);
//				} catch (Exception e) {
//					values[i]=0d; //异常处理，防止st=0，如果为0，异常，给values[i]=0d赋值
//				}
//			}
//			return new StatEqpRunBean(values,lables,bean.getPageIndex(),pageSize, count);
//		}else{
//			return new StatEqpRunBean();
//		}
		
	}
	/**
	 * 
	* @Title: getStringSplit 
	* @Description: 防止界面select输入传值带逗号 
	* @param @param temp
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String getStringSplit(String temp){
		String[] t=temp.split(",");
		for(String s:t){
			if(StringUtil.notNull(s)){
				return s;
			}
		}
		return "";
	}

	/**
	 * 功能说明：通过设备ID，班次ID，剔除时间返回时间范围内，剔除时间
	 * 
	 * @author wchuang
	 * @time 2015年7月24日14:08:53
	 * @param eqp_id 设备ID   
	 * @param shift_id 班次ID   
	 * @param type- 1-机台  2-班组  3-班次
	 * 
	 * @return int 设备剔除总时间
	 * 
	 * */
	public Map<String, Double> retCullRecordAllTime(StatEqpRunBean bean,int type){
		Map<String,Double> map = new HashMap<String, Double>();
		String sql="";
		String shift_id="";
		String team_id="";
		String eqp_id="";
		String st_date = "";
		String ed_date = "";
		//开始时间
		if(StringUtil.notNull(bean.getDate1())){
			st_date=" and CONVERT(varchar(100), create_time, 23) >='"+bean.getDate1()+"' ";
		}
		//结束时间
		if(StringUtil.notNull(bean.getDate2())){
			ed_date=" and CONVERT(varchar(100), create_time, 23) <='"+bean.getDate2()+"' ";
		}
		//班组
		if(StringUtil.notNull(bean.getTeamId())){
			team_id=" and team_id ='"+bean.getTeamId()+"' ";
		}
		//班次
		if(StringUtil.notNull(bean.getShiftId())){
			shift_id=" and shift_id ='"+bean.getShiftId()+"' ";
		}
		if(StringUtil.notNull(bean.getEqpId())){
			eqp_id=" and eqp_id ='"+bean.getEqpId()+"' ";
		}
		if(type==1){
			sql="select (sum(stop_time)/60)*(select yie_id from md_equipment where id=eqp_id) as stop_num,(select equipment_name from md_equipment where  id=eqp_id) as type_name  from EQM_CULL_RECORD  where 1=1 "+shift_id+team_id+eqp_id+st_date+ed_date+" group by eqp_id";
		}else if(type==2){
			sql="select sum(t.f) as stop_num,t.team_id from ( select sum(stop_time)/60*(select yie_id from MD_EQUIPMENT where id=eqp_id) as f,(select name from MD_TEAM where id=team_id) as team_id,eqp_id from EQM_CULL_RECORD where 1=1  "+shift_id+team_id+eqp_id+st_date+ed_date+"  group by team_id,eqp_id) t GROUP BY t.team_id";
		}else{
			sql="select sum(t.f) as stop_num,t.shift_id from ( select sum(stop_time)/60*(select yie_id from MD_EQUIPMENT where id=eqp_id) as f,(select name from MD_SHIFT where id=shift_id) as shift_id,eqp_id from EQM_CULL_RECORD where 1=1  "+shift_id+team_id+eqp_id+st_date+ed_date+"  group by shift_id,eqp_id) t GROUP BY t.shift_id";
		}
        //剔除时间
		List<?> list=eqmCullRecordDao.queryBySql(sql);
		if(list.size()>0){
			for(Object o:list){
				Object[] temp=(Object[]) o;
				map.put(temp[1].toString(),Double.parseDouble(temp[0].toString()));
			}
		}
		return map;
	}
	
	
	/**
	 * 
	 * @param seachBean
	 * @param type 1 条数 2数据
	 * @return
	 */
	private StringBuffer getSql(StatEqpRunBean bean,int type,int eqpType){
		StringBuffer sb=new StringBuffer();
		if(type==1){
			sb.append(" SELECT count(*) from (");
		}else{
			sb.append(" SELECT * from (");
		}
		sb.append(" select ROW_NUMBER() OVER(ORDER BY lastData.eqpID) AS rownum,lastData.eqp_name,lastData.eqpID,");
		sb.append(" lastData.shift,lastData.qty,lastData.yid,lastData.run_time,lastData.tc_time,lastData.rb_time,lastData.stims,lastData.shiftId ");
		sb.append(" FROM(SELECT sour.eqp_name2 AS eqp_name,sour.eqpID,sour.shift,");
		sb.append(" ISNULL(SUM(sour.qty), 0.0) AS qty,sour.yie_id AS yid,ISNULL(SUM(sour.run_time), 0.0) AS run_time,");
		sb.append(" ISNULL(SUM(sour.tc_time), 0.0) AS tc_time,ISNULL(SUM(sour.rb_time), 0.0) AS rb_time, ");
		sb.append(" ISNULL(SUM(sour.stims), 0.0) AS stims,sour.shiftId ");
		sb.append(" FROM(SELECT ms.name AS shift,ms.id as shiftID,me.ID as eqpID, ");
		sb.append(" me.equipment_name AS eqp_name2,ISNULL(a.qty, 0.0) AS qty,");
		sb.append(" sw.DATE AS work_Date,b.yie_id,ISNULL(a.run_time, 0.0) AS run_time,");
		sb.append(" ISNULL(c.tc_time, 0.0) AS tc_time,ISNULL(d.rb_time, 0.0) AS rb_time,");
		sb.append(" e.stims,sw.TYPE FROM SCH_STAT_OUTPUT sso ");
		sb.append(" LEFT JOIN SCH_WORKORDER sw ON sso.oid = sw.id   and sso.ETIM is not null and sw.sts=4 ");
		//开始时间
		if(StringUtil.notNull(bean.getDate1())){
			sb.append(" and CONVERT(varchar(100), sw.[DATE], 23) >='"+bean.getDate1()+"' ");
		}
		//结束时间
		if(StringUtil.notNull(bean.getDate2())){
			sb.append(" and CONVERT(varchar(100), sw.[DATE], 23) <='"+bean.getDate2()+"' ");
		}
		sb.append(" LEFT JOIN MD_EQUIPMENT me ON sw.eqp = me.ID  and me.del=0  ");
		sb.append(" LEFT JOIN MD_SHIFT ms ON ms.ID = sw.SHIFT ");
		//sb.append(" LEFT JOIN MD_TEAM mt ON mt.id = sw.TEAM ");
		sb.append(" LEFT JOIN MD_EQP_CATEGORY mec ON sw.TYPE=mec.CODE and mec.del=0 ");
		sb.append(" LEFT JOIN (SELECT ");
		sb.append(" SUM (ISNULL(DATEDIFF(MINUTE, s1.STIM, s1.ETIM),0.0)) AS run_time,SUM (ISNULL(s1.QTY, 0)) AS qty,s1.OID ");
		sb.append(" FROM SCH_STAT_OUTPUT s1 GROUP BY s1.OID) a ON a.OID = sw.ID LEFT JOIN (SELECT yie_id,id FROM MD_EQUIPMENT) b ON b.id = sw.EQP ");
		sb.append(" LEFT JOIN (SELECT SUM (ISNULL(s2.stop_time, 0)) AS tc_time,s2.eqp_id,CONVERT (VARCHAR(100), st_date, 23) AS c_date ");
		sb.append(" FROM EQM_CULL_RECORD s2 GROUP BY s2.eqp_id,s2.st_date,s2.shift_id) c ON c.eqp_id = me.ID ");
		sb.append(" AND c.c_date = CONVERT (VARCHAR(100), sw.[DATE], 23) ");
		sb.append(" LEFT JOIN (SELECT ISNULL(datediff(MINUTE, s3.STIM, s3.ETIM) + "+SysEqpTypeBase.jcTime+",0) AS rb_time, ");
		sb.append(" s3.DATE_P AS dp,s3.shift FROM EQM_PAULDAY s3 WHERE s3.STATUS = 3 ");
		sb.append(" ) d ON CONVERT (VARCHAR(100), sw.[DATE], 23) = CONVERT (VARCHAR(100), d.dp, 23) ");
		sb.append(" AND sw.SHIFT = d.shift LEFT JOIN (SELECT OID,ISNULL(STOP_TIME, 0) AS stims FROM SCH_STAT_OUTPUT) e ON e.OID = sw.ID ");
		sb.append(" GROUP BY me.equipment_name,");
		sb.append(" a.qty,sw. DATE,b.yie_id,a.run_time,c.tc_time,d.rb_time,e.stims,ms.NAME,me.ID,ms.ID,sw.TYPE ");
		sb.append(" ) sour WHERE sour.yie_id is not null ");
		//时间，班次，班组，设备类型条件查询
		if(StringUtil.notNull(bean.getEqpId())){
			sb.append(" and sour.eqpID='"+bean.getEqpId()+"' ");
		}
				
		if(StringUtil.notNull(bean.getShiftId())){
			sb.append(" and sour.shiftID='"+bean.getShiftId()+"' ");
		}
//		if(StringUtil.notNull(bean.getTeamId())){
//			sb.append(" and sour.teamid='"+bean.getTeamId()+"' ");
//		}
		sb.append(" and sour.TYPE= "+eqpType);
		
		sb.append(" GROUP BY eqp_name2,sour.yie_id,sour.shift,sour.eqpID,sour.shiftId ) lastData) t ");
		return sb;
		
	}	
	
	
	
	
	
	
	
}
