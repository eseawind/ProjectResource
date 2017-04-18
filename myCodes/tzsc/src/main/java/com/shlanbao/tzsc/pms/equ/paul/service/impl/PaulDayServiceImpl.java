package com.shlanbao.tzsc.pms.equ.paul.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmPaulDayDaoI;
import com.shlanbao.tzsc.base.mapping.EqmPaulDay;
import com.shlanbao.tzsc.base.mapping.EqmProtectRecordBean;
import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.paul.beans.BatchBean;
import com.shlanbao.tzsc.pms.equ.paul.beans.EqmPaulDayBean;
import com.shlanbao.tzsc.pms.equ.paul.service.PaulDayServiceI;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCalendar;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class PaulDayServiceImpl extends BaseService implements PaulDayServiceI {
	@Autowired
	private EqmPaulDayDaoI paulDayDao;
	@Autowired
	private BaseDaoI<Object> baseDao;
	/**
	 * 日保养查询
	 */
	@Override
	public DataGrid queryList(EqmPaulDayBean seachBean,PageParams pageParams) {
		StringBuffer hql=new StringBuffer();
		hql.append("select o.id,o.name, o.shift.id,");
		hql.append("o.shift.name,");
		hql.append("o.team.id,");
		hql.append("o.team.name,");
		hql.append("o.date_p,");
		hql.append("o.stim,");
		hql.append("o.etim,");
		hql.append("o.eqp_type.id,");
		hql.append("o.eqp_type.name,");
		hql.append("o.paul_id,");
		hql.append("o.del, ");
		hql.append("o.status ");
		hql.append("from EqmPaulDay o where o.del=0 ");
		if(StringUtil.notNull(seachBean.getId())){
			hql.append("and o.id='"+seachBean.getId()+"' ");
		}
		if(StringUtil.notNull(seachBean.getShiftId())){
			hql.append("and o.shift.id="+seachBean.getShiftId()+" ");
		}
		if(StringUtil.notNull(seachBean.getTeamId())){
			hql.append("and o.team.id="+seachBean.getTeamId()+" ");
		}
		if(StringUtil.notNull(seachBean.getEqp_type_id())){
			hql.append("and o.eqp_type.id='"+seachBean.getEqp_type_id()+"' ");
		}
		if(StringUtil.notNull(seachBean.getStim())){
			hql.append("and CONVERT (VARCHAR(32), o.stim, 23) >='"+seachBean.getStim()+"' ");
		}
		if(StringUtil.notNull(seachBean.getEtim())){
			hql.append("and CONVERT (VARCHAR(32), o.etim, 23) <='"+seachBean.getEtim()+"' ");
		}
		if(seachBean.getStatus()>-1){
			hql.append("and o.status='"+seachBean.getStatus()+"' ");
		}
		long total=baseDao.query(hql.toString()).size();
		hql.append("order by o.date_p,o.shift.code ");
		List<Object> list=baseDao.queryByPage(hql.toString(), pageParams);
		List<EqmPaulDayBean> returnList=new ArrayList<EqmPaulDayBean>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EqmPaulDayBean b=new EqmPaulDayBean();
			b.setId(temp[0].toString());
			b.setName(temp[1].toString());
			b.setShiftId(temp[2].toString());
			b.setShiftName(temp[3].toString());
			b.setTeamId(temp[4].toString());
			b.setTeamName(temp[5].toString());
			b.setDate_p(temp[6].toString());
			b.setStim(temp[7].toString());
			b.setEtim(temp[8].toString());
			b.setEqp_type_id(temp[9].toString());
			b.setEqp_type_name(temp[10].toString());
			b.setPaul_id(temp[11].toString());
			b.setDel(Integer.parseInt(temp[12].toString()));
			b.setStatus(Integer.parseInt(temp[13].toString()));
			returnList.add(b);
		}
		return new DataGrid(returnList,total);
	}
	//新增
	@Override
	public boolean addPaulDayBean(EqmPaulDayBean bean){
		EqmPaulDay eqm=new EqmPaulDay();
		eqm.setEqp_type(new MdEqpCategory(bean.getEqp_type_id()));
		eqm.setStim(DateUtil.formatStringToDate(bean.getStim()));
		eqm.setEtim(DateUtil.formatStringToDate(bean.getEtim()));
		eqm.setName(bean.getName());
		eqm.setPaul_id(bean.getPaul_id());
		eqm.setDate_p(DateUtil.formatStringToDate(bean.getStim(),"yyyy-MM-dd"));
		eqm.setTeam(new MdTeam(bean.getTeamId()));
		eqm.setShift(new MdShift(bean.getShiftId()));
		eqm.setDel(0);
		eqm.setStatus(0);
		return paulDayDao.saveOrUpdate(eqm);
	}
	
	//修改
	@Override
	public int updatePaulDayBean(EqmPaulDayBean bean){
		EqmPaulDay eqm=new EqmPaulDay();
		/*eqm.setEqp_type(new MdEqpCategory(bean.getEqp_type_id()));
		eqm.setName(bean.getName());
		eqm.setPaul_id(bean.getPaul_id());
		eqm.setTeam(new MdTeam(bean.getTeamId()));
		eqm.setShift(new MdShift(bean.getShiftId()));
		eqm.setDel(bean.getDel());
		eqm.setStatus(bean.getStatus());
		eqm.setId(bean.getId());
		eqm.setStim(DateUtil.formatStringToDate(bean.getStim()));
		eqm.setEtim(DateUtil.formatStringToDate(bean.getEtim()));
		eqm.setDate_p(DateUtil.formatStringToDate(bean.getDate_p(),"yyyy-MM-dd"));*/
		String sql = "update EQM_PAULDAY SET NAME='"+bean.getName()+"',DATE_P='"+bean.getDate_p()+"',STIM='"+bean.getStim()+"',ETIM='"+bean.getEtim()+"' where ID='"+bean.getId()+"'";
		return paulDayDao.updateBySql(sql, null);
		//return paulDayDao.saveOrUpdate(eqm);
	}
	//修改
	@Override
	public boolean updatePaulDay(EqmPaulDay bean){
		return paulDayDao.saveOrUpdate(bean);
	}
	//根据id查询
	@Override
	public EqmPaulDay getBeanById(String id){
		return paulDayDao.findById(EqmPaulDay.class, id);
	}
	
	//根据id查询
	@Override
	public void checkWork(String ids,int stats){
		for(String id:StringUtil.splitToStringList(ids, ",")){
			if(StringUtil.notNull(id)){
				EqmPaulDay bean=getBeanById(id);
				bean.setStatus(stats);
				paulDayDao.saveOrUpdate(bean);
			}
		}
	}
	//根据id查询
	@Override
	public boolean updateBean(EqmPaulDay bean){
		return paulDayDao.saveOrUpdate(bean);
	}
	
	//根据id查询
	@Override
	public EqmPaulDayBean getBeanByIds(String id){
		StringBuffer hql=new StringBuffer();
		hql.append("select o.id,o.name, o.shift.id,");
		hql.append("o.shift.name,");
		hql.append("o.team.id,");
		hql.append("o.team.name,");
		hql.append("o.date_p,");
		hql.append("o.stim,");
		hql.append("o.etim,");
		hql.append("o.eqp_type.id,");
		hql.append("o.eqp_type.name,");
		hql.append("o.paul_id,");
		hql.append("o.del, ");
		hql.append("o.status ");
		hql.append("from EqmPaulDay o where o.del=0 ");
		if(StringUtil.notNull(id)){
			hql.append("and o.id='"+id+"' ");
		}
		List<Object> list=baseDao.query(hql.toString());
		EqmPaulDayBean b=null;
		if(list.size()>0){
			b=new EqmPaulDayBean();
			Object o=list.get(0);
			Object[] temp=(Object[]) o;
			b.setId(temp[0].toString());
			b.setName(temp[1].toString());
			b.setShiftId(temp[2].toString());
			b.setShiftName(temp[3].toString());
			b.setTeamId(temp[4].toString());
			b.setTeamName(temp[5].toString());
			b.setDate_p(temp[6].toString());
			b.setStim(temp[7].toString());
			b.setEtim(temp[8].toString());
			b.setEqp_type_id(temp[9].toString());
			b.setEqp_type_name(temp[10].toString());
			b.setPaul_id(temp[11].toString());
			b.setDel(Integer.parseInt(temp[12].toString()));
			b.setStatus(Integer.parseInt(temp[13].toString()));
		}
		return b;
	}
	/**
	 * 日保日历
	 */
	@Override
	public List<EqmWheelCalendar> queryListCal(String date1,String date2) {
		StringBuffer hql=new StringBuffer();
		hql.append("select o.id,o.name, o.shift.id,");
		hql.append("o.shift.name,");
		hql.append("o.team.id,");
		hql.append("o.team.name,");
		hql.append("o.date_p,");
		hql.append("o.stim,");
		hql.append("o.etim,");
		hql.append("o.eqp_type.id,");
		hql.append("o.eqp_type.name,");
		hql.append("o.paul_id,");
		hql.append("o.del, ");
		hql.append("o.status ");
		hql.append("from EqmPaulDay o where o.del=0 ");
		if(StringUtil.notNull(date1)){
			hql.append(" and o.date_p >= '"+date1+"'");
		}
		if(StringUtil.notNull(date2)){
			hql.append(" and o.date_p <= '"+date2+"'");
		}
		hql.append("order by o.date_p,o.shift.code  ");
		List<Object> list=baseDao.query(hql.toString());
		List<EqmWheelCalendar> returnList=new ArrayList<EqmWheelCalendar>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EqmWheelCalendar b=new EqmWheelCalendar();
			b.setId(temp[0].toString());
			b.setName(temp[1].toString());
			b.setStart(temp[7].toString());
			b.setEnd(temp[8].toString());
			b.setTitle(temp[1].toString());
			// 0\新增 1、审核通过2、运行3、停止运行4、操作工操作完毕5、轮保工操作完毕6、操作工、轮保工操作完毕7、审核完毕8、计划完成
			if(Integer.parseInt(temp[13].toString())==8){
				b.setColor("GREEN");
			}else{
				b.setColor("Red");
			}
			returnList.add(b);
		}
		return returnList;
	}
	
	//批量添加日保养计划
	@Override
	public void batchAdd(BatchBean bean){
		int workshop=1;
		StringBuffer hql=new StringBuffer();
//		String date=bean.getDate();
//		int year=Integer.parseInt(date.substring(0, 4));
//		int month=Integer.parseInt(date.substring(5, 7));
		hql.append("delete from EQM_PAULDAY where 1=1 ");
//		hql.append("and DATEPART(YEAR,DATE_p)="+year+" ");
//		hql.append("and DATEPART(MONTH,DATE_p)="+month+" ");
		
		hql.append("and DATE_p >= '"+bean.getDate()+"'");
		hql.append(" and DATE_p <= '"+bean.getDate2()+"'");
		
		hql.append(" and eqp_type='"+bean.getEqp_type_id()+"' ");
		baseDao.updateInfo(hql.toString(),null);
		//早 不包含周一的早班
		hql=new StringBuffer();
		hql.append("insert into EQM_PAULDAY (ID,SHIFT,TEAM,DATE_P,STIM,ETIM,PAUL_ID,DEL,NAME,eqp_type,status) ");
		hql.append(" select newid(),SHIFT,TEAM,DATE,STIM,DATEADD(Minute, "+bean.getZao()+", STIM),");
		hql.append("'"+bean.getPaul_category()+"', ");
		hql.append("0,");
		hql.append("(select cate.name from MD_EQP_CATEGORY cate where cate.ID='"+bean.getEqp_type_id()+"' )+'日保', ");
		hql.append("'"+bean.getEqp_type_id()+"', ");
		hql.append("2");
		hql.append(" from SCH_CALENDAR where 1=1 ");
//		hql.append("DATEPART(YEAR,DATE)="+year+" ");
//		hql.append("and DATEPART(MONTH,DATE)="+month+" ");
		
		hql.append(" and DATE >= '"+bean.getDate()+"'");
		hql.append(" and DATE <= '"+bean.getDate2()+"'");
		
		hql.append(" and WORKSHOP= "+workshop+" and DEL=0 ");
		hql.append(" and SHIFT=1 ");
//		hql.append("and DATEPART(WEEKDAY,DATE)<>1 ");
		
		hql.append(" and DATEPART(WEEKDAY,DATE)<> "+bean.getZaoTD());//这里是判断特殊早班日
		
		baseDao.updateInfo(hql.toString(),null);
		hql=new StringBuffer();
		//周一早班轮保使用 zaoT时间
		hql.append("insert into EQM_PAULDAY (ID,SHIFT,TEAM,DATE_P,STIM,ETIM,PAUL_ID,DEL,NAME,eqp_type,status) ");
		hql.append("select newid(),SHIFT,TEAM,DATE,STIM,DATEADD(Minute, "+bean.getZaoT()+", STIM),");
//		hql.append("'"+bean.getPaul_category()+"', ");
		//如果有周就是周保养
		if(StringUtil.notNull(bean.getPaul_category2())){
			hql.append("'"+bean.getPaul_category2()+"', ");
		}else{
			hql.append("'"+bean.getPaul_category()+"', ");
		}
		hql.append("0,");
		hql.append("(select cate.name from MD_EQP_CATEGORY cate where cate.ID='"+bean.getEqp_type_id()+"' )+'日保', ");
		hql.append("'"+bean.getEqp_type_id()+"', ");
		hql.append("2");
		hql.append(" from SCH_CALENDAR where 1=1 ");
//		hql.append("DATEPART(YEAR,DATE)="+year+" ");
//		hql.append("and DATEPART(MONTH,DATE)="+month+" ");
		
		hql.append(" and DATE >= '"+bean.getDate()+"'");
		hql.append(" and DATE <= '"+bean.getDate2()+"'");
		
		hql.append(" and WORKSHOP="+workshop+" and DEL=0");
		hql.append(" and SHIFT=1 ");
//		hql.append("and DATEPART(WEEKDAY,DATE)=1 ");//特殊早班日时间
		
		hql.append(" and DATEPART(WEEKDAY,DATE) = "+bean.getZaoTD());
		
		baseDao.updateInfo(hql.toString(),null);
		
		//中  不包含周四的中班
		hql=new StringBuffer();
		hql.append("insert into EQM_PAULDAY (ID,SHIFT,TEAM,DATE_P,STIM,ETIM,PAUL_ID,DEL,NAME,eqp_type,status) ");
		hql.append("select newid(),SHIFT,TEAM,DATE,STIM,DATEADD(Minute, "+bean.getZhong()+", STIM),");
		hql.append("'"+bean.getPaul_category()+"', ");
		hql.append("0,");
		hql.append("(select cate.name from MD_EQP_CATEGORY cate where cate.ID='"+bean.getEqp_type_id()+"' )+'日保', ");
		hql.append("'"+bean.getEqp_type_id()+"', ");
		hql.append("2");
		hql.append(" from SCH_CALENDAR where 1=1 ");
//		hql.append("DATEPART(YEAR,DATE)="+year+" ");
//		hql.append("and DATEPART(MONTH,DATE)="+month+" ");
		
		hql.append(" and DATE >= '"+bean.getDate()+"'");
		hql.append(" and DATE <= '"+bean.getDate2()+"'");
		
		
		hql.append(" and WORKSHOP="+workshop+" and DEL=0");
		hql.append(" and SHIFT=2 ");
//		hql.append("and DATEPART(WEEKDAY,DATE)<>5 ");//特殊中班日
		
		hql.append(" and DATEPART(WEEKDAY,DATE)<> "+bean.getZhongTD());
		
		baseDao.updateInfo(hql.toString(),null);
		hql=new StringBuffer();
		//特殊中班
		hql.append("insert into EQM_PAULDAY (ID,SHIFT,TEAM,DATE_P,STIM,ETIM,PAUL_ID,DEL,NAME,eqp_type,status) ");
		hql.append("select newid(),SHIFT,TEAM,DATE,STIM,DATEADD(Minute, "+bean.getZhongT()+", STIM),");
		//hql.append("'"+bean.getPaul_category()+"', ");
		//如果有精细就是精细保养
		if(StringUtil.notNull(bean.getPaul_category3())){
			hql.append("'"+bean.getPaul_category3()+"', ");
		}else{
			hql.append("'"+bean.getPaul_category()+"', ");
		}
		hql.append("0,");
		hql.append("(select cate.name from MD_EQP_CATEGORY cate where cate.ID='"+bean.getEqp_type_id()+"' )+'日保', ");
		hql.append("'"+bean.getEqp_type_id()+"', ");
		hql.append("2");
		hql.append(" from SCH_CALENDAR where 1=1 ");
//		hql.append("DATEPART(YEAR,DATE)="+year+" ");
//		hql.append("and DATEPART(MONTH,DATE)="+month+" ");
		
		hql.append(" and DATE >= '"+bean.getDate()+"'");
		hql.append(" and DATE <= '"+bean.getDate2()+"'");
		
		
		hql.append(" and WORKSHOP="+workshop+" and DEL=0");
		hql.append(" and SHIFT=2 ");
//		hql.append("and DATEPART(WEEKDAY,DATE)=5 ");//特殊中班日时间
		
		hql.append(" and DATEPART(WEEKDAY,DATE) = "+bean.getZhongTD());
		
		
		baseDao.updateInfo(hql.toString(),null);
		
		//晚
		hql=new StringBuffer();
		hql.append("insert into EQM_PAULDAY (ID,SHIFT,TEAM,DATE_P,STIM,ETIM,PAUL_ID,DEL,NAME,eqp_type,status) ");
		hql.append("select newid(),SHIFT,TEAM,DATE,STIM,DATEADD(Minute, "+bean.getWan()+", STIM),");
		hql.append("'"+bean.getPaul_category()+"', ");
		hql.append("0,");
		hql.append("(select cate.name from MD_EQP_CATEGORY cate where cate.ID='"+bean.getEqp_type_id()+"' )+'日保', ");
		hql.append("'"+bean.getEqp_type_id()+"', ");
		hql.append("2");
		hql.append(" from SCH_CALENDAR where 1=1 ");
//		hql.append("DATEPART(YEAR,DATE)="+year+" ");
//		hql.append("and DATEPART(MONTH,DATE)="+month+" ");
		
		hql.append(" and DATE >= '"+bean.getDate()+"'");
		hql.append(" and DATE <= '"+bean.getDate2()+"'");
		
		
		
		hql.append(" and WORKSHOP="+workshop+" and DEL=0");
		hql.append(" and SHIFT=3 ");
		baseDao.updateInfo(hql.toString(),null);
	}
	
	
	/**
     *  功能说明：设备日保历史查询-查询
     *  
     *  @param pageParams 分页实体对象
     *  @param bean 数据实体对象
     *  @return
     *  @author 张璐
     *  @time 2015年9月29日
     *  
     * */
	@Override
	public DataGrid queryProtectRecordByList(EqmProtectRecordBean seachBean,
			PageParams pageParams) {
		String shif=""; //班组
		String tem=""; // 班次
		String eqpId=""; //设备类型ID
		Integer start=1;
		Integer ednum=10;
		//起始页-每页多少条
		/*if(pageParams.getPage()==1){// 1-10  10-20  20-30
			start=1;
			ednum=pageParams.getRows();
		}else{
			ednum=pageParams.getPage()*pageParams.getRows();
			start=ednum-pageParams.getRows();
		}*/
		
		int page=pageParams.getPage();
		int row=pageParams.getRows();
	    String sql=getSqlToStr(2,seachBean)+" AND bh >  "+(page-1)*row+" AND bh <="+page*row;
	    String sql2=getSqlToStr(1,seachBean);
	    //总条数
    	List<?> tl= baseDao.queryBySql(sql2);
    	
    	String t=tl.get(0).toString();
		long total=Long.parseLong(t);
		//对象集合
		List<?> list=baseDao.queryBySql(sql);
		List<EqmProtectRecordBean> returnList=new ArrayList<EqmProtectRecordBean>();
		if(list.size()>0){
			for(Object o:list){
				Object[] temp=(Object[]) o;
				EqmProtectRecordBean b=new EqmProtectRecordBean();
				try {
					b.setId(temp[1].toString());
					b.setEqu_name(temp[2].toString());
					if(null!=temp[3]&&!temp[3].equals("")){
						b.setEp_shift(temp[3].toString());
					}
					if(null!=temp[4]&&!temp[4].equals("")){
						b.setEqpTypeName(temp[4].toString());
					}
					if(null!=temp[5]&&!temp[5].equals("")){
						b.setEqpTypeDES(temp[5].toString());
					}
					b.setEp_stime(temp[6].toString());
					b.setEp_etime(temp[7].toString());
					b.setEpr_time(temp[8].toString());
					b.setEpr_username(temp[9].toString());
					b.setStatus(temp[10].toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				returnList.add(b);
			}
		}
		return new DataGrid(returnList,total);
	}
	
	
	/**
	 * 日保历史记录查询 sql拼接
	 * type 1- 查询对象  2-查询总条数
	 * @author 张璐
     * @time 2015年9月29日
	 * */
	public static String getSqlToStr(int type,EqmProtectRecordBean seachBean){
		StringBuffer sql=new StringBuffer();
		if(type==1){
			sql.append("select count(*) ");
		}else{
			sql.append("SELECT * ");
		}
		
		/*sql.append("FROM ( SELECT ROW_NUMBER () OVER ( ORDER BY tab.equ_name, tab.epr_time, tab.ep_shiftId ) bh, tab.* FROM ( ");
		sql.append("SELECT DISTINCT table_1.equ_name, table_1.ep_shift, table_1.ep_team, table_1.ep_stim AS ep_stime, table_1.ep_etim AS ep_etime, table_1.ep_name, table_1.epr_time, table_1.epr_username, table_1.ep_shiftId ");			
		sql.append("FROM SCH_WORKORDER wo LEFT JOIN ( SELECT ep.ID ep_id, shift.NAME ep_shift, shift.id ep_shiftId, team.NAME ep_team, ep.STIM ep_STIM, ep.ETIM ep_etim, ep.NAME ep_name, epr.create_time epr_time, ");			
		sql.append("sys_u.NAME epr_username, equ.ID equ_id, equ.EQUIPMENT_NAME equ_name, epr.id epr_id FROM EQM_PAULDAY ep LEFT JOIN MD_EQP_CATEGORY mec ON ep.EQP_TYPE = mec.ID LEFT JOIN MD_EQP_TYPE et ON et.CID = mec.ID LEFT JOIN MD_EQUIPMENT equ ON et.ID = equ.EQP_TYPE_ID ");					
		sql.append("LEFT JOIN EQM_PROTECT_RECORD epr ON equ.id = epr.eqp_id AND epr.paulday_id = ep.ID LEFT JOIN MD_SHIFT shift ON shift.ID = ep.SHIFT LEFT JOIN MD_TEAM team ON team.ID = ep.TEAM LEFT JOIN SYS_USER sys_u ON sys_u.ID = epr.create_user_id ");				
		*/
		sql.append(" from (");
		sql.append("SELECT ROW_NUMBER () OVER ( ORDER BY r.create_time DESC) bh, r.id,");
		sql.append("(SELECT EQUIPMENT_NAME from MD_EQUIPMENT e where e.id=r.eqp_id) as eqp_name,");
		sql.append("(SELECT NAME from MD_SHIFT s where s.id=r.shift_id) as shift_name,et.NAME,et.DES,");
		sql.append("(select stim from EQM_PAULDAY p where p.id=r.paulday_id) as stim,");
		sql.append("(select etim from EQM_PAULDAY p where p.id=r.paulday_id) as etim,");
		sql.append("r.create_time,");
		sql.append("(SELECT NAME from SYS_USER u where u.id=r.create_user_id)as create_name,");
		sql.append("r.status ");
		sql.append(" from EQM_PROTECT_RECORD r LEFT JOIN EQM_PAULDAY p ON r.paulday_id=p.id LEFT JOIN SYS_EQP_TYPE et ON r.paul_id = et.id WHERE 1 = 1 ");
		//班次
		if(StringUtil.notNull(seachBean.getEp_shift())){
			sql.append(" AND r.shift_id='"+seachBean.getEp_shift()+"'  ");
		}  
		/*//班组
		if(StringUtil.notNull(seachBean.getEp_team())){
			sql.append("AND team.id='"+seachBean.getEp_team()+"'  ");
		}*/
		//设备类型
		if(StringUtil.notNull(seachBean.getEqu_id())){
			sql.append(" AND r.eqp_id='"+seachBean.getEqu_id()+"'  ");
		}
		
		//时间
		if(StringUtil.notNull(seachBean.getStime())){
			sql.append(" AND CONVERT (VARCHAR(32), p.stim, 23) >= '"+seachBean.getStime()+"'  ");
		}  
		if(StringUtil.notNull(seachBean.getEtime())){
			sql.append(" AND CONVERT (VARCHAR(32), p.etim, 23) <= '"+seachBean.getEtime()+"' ");
		}
		
		//sql.append("AND equ.DEL = 0 ) table_1 ON wo.EQP = table_1.equ_id WHERE 1=1 and table_1.equ_name IS NOT NULL ");
		//时间
		/*if(StringUtil.notNull(seachBean.getStime())){
			sql.append("AND CONVERT (VARCHAR(32), wo. DATE, 23) >= '"+seachBean.getStime()+"'   ");
		}  
		if(StringUtil.notNull(seachBean.getEtime())){
			sql.append("AND CONVERT (VARCHAR(32), wo. DATE, 23) <= '"+seachBean.getEtime()+"'  ");
		}*/
		sql.append(" ) zl where 1=1");
		return sql.toString();
	}
	@Override
	public void delete(String id) throws Exception{
		paulDayDao.deleteById(id, EqmPaulDay.class);
		//paulDayDao.updateInfo(sql, obj);
	}
	@Override
	public void deletePaul(String ids) throws Exception {
		for (String id : StringUtil.splitToStringList(ids, ",")) {			
			this.delete(id);
		}
	}	
}
