package com.shlanbao.tzsc.wct.sch.calendar.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.dao.EqmCullRecordDaoI;
import com.shlanbao.tzsc.base.dao.MdManualShiftDaoI;
import com.shlanbao.tzsc.base.dao.MdManualShiftLogDaoI;
import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.mapping.MdManualShift;
import com.shlanbao.tzsc.base.mapping.MdManualShiftLog;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.mapping.MdWorkshop;
import com.shlanbao.tzsc.base.mapping.SchCalendar;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.DataSnapshot;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataEquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataEquipmentDataBean;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.utils.params.EquipmentTypeDefinition;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.sch.calendar.beans.WctCalendarBean;
import com.shlanbao.tzsc.wct.sch.calendar.service.WctCalendarServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class WctCalendarServiceImpl extends BaseService implements WctCalendarServiceI{
	@Autowired
	private SchCalendarDaoI schCalendarDao;
	@Autowired
	private MdManualShiftLogDaoI mdManualShiftLogDao;
	@Autowired
	private MdManualShiftDaoI mdManualShiftDao;
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Autowired
	private EqmCullRecordDaoI eqmCullRecordDao;
	
	@Override
	public List<WctCalendarBean> getCurMonthCalendars(String date, String workshop)
			throws Exception {
				 String hql = "from SchCalendar o left join fetch o.mdTeam t left join fetch o.mdShift s where 1=1 and CONVERT(varchar(7), o.date, 126)='"+date.substring(0, 7)+"' and o.mdWorkshop.code='"+workshop+"' order by o.mdShift.code asc";
				List<SchCalendar> list=schCalendarDao.query(hql);
				List<WctCalendarBean> calendarBeans=new ArrayList<WctCalendarBean>();
				WctCalendarBean calendarBean = null;
				for (SchCalendar schCalendar : list) {
					calendarBean = BeanConvertor.copyProperties(schCalendar, WctCalendarBean.class);
					calendarBean.setShift(schCalendar.getMdShift().getName());
					calendarBean.setTeam(schCalendar.getMdTeam().getName());
					calendarBeans.add(calendarBean);
					calendarBean = null;
				}
				return calendarBeans;
	}
	/**
	 * 保存排班信息
	 * @author luther.zhang
	 * @create 20150331
	 * @param workshop 车间
	 * @return
	 * @throws Exception
	 */
	public void saveCurMonthCalendars(String workshop) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append("delete from SchCalendar s  ");
		List<Object> params = new ArrayList<Object>();
		hql.append("where s.date>=? and s.mdWorkshop.id=? ");
		String nowDay = DateUtil.getNowDateTime("yyyy-MM-dd")+" 00:00:00";
		Date dateStr = DateUtil.strToDate(nowDay,"yyyy-MM-dd HH:mm:ss");
		params.add(dateStr);
		params.add(workshop);
		schCalendarDao.deleteByParams(hql.toString(),params);
		
		for(int j=0;j<7;j++){
			//j=0;j=1;j=2
			//int j=0;
			for(int i=0;i<3;i++){
				SchCalendar sch = new SchCalendar();
				MdWorkshop mdWorkshop = new MdWorkshop();mdWorkshop.setId(workshop);//1.卷包车间 2.成型车间
				sch.setId(UUID.randomUUID().toString());
				sch.setMdWorkshop(mdWorkshop);
				if(i==0){
					String begin = DateUtil.dateAdd("d",j,new Date(),"yyyy-MM-dd");
					sch.setDate(DateUtil.strToDate(begin+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					sch.setStim(DateUtil.strToDate(begin+" 07:00:00", "yyyy-MM-dd HH:mm:ss"));
					sch.setEtim(DateUtil.strToDate(begin+" 15:00:00", "yyyy-MM-dd HH:mm:ss"));
					MdShift mdShift = new MdShift();
					MdTeam team =new MdTeam();
					/*Calendar c = Calendar.getInstance();		
					c.setTime(new Date());		
					int dsWeek = c.get(Calendar.WEEK_OF_MONTH);*/
					mdShift.setId("1");mdShift.setName("早班");
					team.setId("2");team.setName("乙");
					sch.setMdTeam(team);
					sch.setMdShift(mdShift);
				}else if(i==1){
					String begin = DateUtil.dateAdd("d",j,new Date(),"yyyy-MM-dd");
					sch.setDate(DateUtil.strToDate(begin+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					sch.setStim(DateUtil.strToDate(begin+" 15:00:00", "yyyy-MM-dd HH:mm:ss"));
					sch.setEtim(DateUtil.strToDate(begin+" 23:00:00", "yyyy-MM-dd HH:mm:ss"));
					MdShift mdShift = new MdShift();mdShift.setId("2");mdShift.setName("中班");
					MdTeam team =new MdTeam();team.setId("3");team.setName("丙");
					sch.setMdTeam(team);
					sch.setMdShift(mdShift);
				}else if(i==2){
					String begin = DateUtil.dateAdd("d",j,new Date(),"yyyy-MM-dd");
					sch.setDate(DateUtil.strToDate(begin+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					
					sch.setStim(DateUtil.strToDate(begin+" 23:00:00", "yyyy-MM-dd HH:mm:ss"));
					begin = DateUtil.dateAdd("d",(j+1),new Date(),"yyyy-MM-dd");
					sch.setEtim(DateUtil.strToDate(begin+" 07:00:00", "yyyy-MM-dd HH:mm:ss"));
					MdShift mdShift = new MdShift();mdShift.setId("3");mdShift.setName("晚班");
					MdTeam team =new MdTeam();team.setId("1");team.setName("甲");
					sch.setMdTeam(team);
					sch.setMdShift(mdShift);
				}
				sch.setDel(0L);
				schCalendarDao.save(sch);
			}
			//Thread.sleep(1);
		}
		
	}
	
	/**
	 * 保存手动换班信息
	 * @author luther.zhang
	 * @create 20150331
	 * @param eqpId 设备号
	 * @return
	 * @throws Exception
	 */
	public void saveManualShift(String type, String eqpId, String workShop,
			String proWorkId, LoginBean login, HttpServletRequest request)
			throws Exception {
		String shiftCode = request.getParameter("shiftCode");//班次 早中晚
		String teamCode = request.getParameter("teamCode");//班组	甲乙丙
		this.editProdWork(eqpId, workShop, login.getUserId(), proWorkId,
				teamCode, shiftCode, type, login.getLoginName(),
				login.getWorkshopCode());
	}
	
	/**
	 * 
	 * @param eqpId				设备ID
	 * @param workShop			工单运行车间
	 * @param userId			修改人
	 * @param proWorkId			工单主键
	 * @param teamCode			班组
	 * @param shiftCode			班次
	 * @param type				B是  新增工单但未运行状态， 运行工单 S 或 结束工单  E
	 * @param loginName			登录人姓名
	 * @param loginWorkshopCode	登录人工作车间
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void editProdWork(String eqpId, String workShop, String userId,
			String proWorkId, String teamCode, String shiftCode, String type,
			String loginName, String loginWorkshopCode) throws Exception{
		//根据设备ID查询 保存最后一次 记录
		String hql=" from MdManualShift where eqpid=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(eqpId);
		List<MdManualShift> queryList=mdManualShiftDao.query(hql, params);
		//记录上一班的 产量信息 放到数据库中
		if(null!=queryList&&queryList.size()>0){//表示查询到数据了
			MdManualShift query=queryList.get(0);
			query.setEqpid(eqpId);//设备code
			query.setWorkShop(workShop);//车间
			query.setPmsprocess(-1);//预留字段
			query.setDasprocess(-1);
			query.setModifyTime(new Date());//修改时间
			query.setModifyUserId(userId);//修改人
			query.setProWorkId(proWorkId);//工单主键ID
			query.setTeamCode(teamCode);//班组
			query.setShiftCode(shiftCode);//班次
			query.setNote("运行工单");//备注
			query.setState("S");//工单运行状态:运行中
			//工单启动、结束 清空前一个变量
			DataEquipmentDataBean changeBeans = DataEquipmentData.allMap.get(eqpId);
			if(null!=changeBeans){
				changeBeans.setPlcBeforCl("");//PLC上一次产量
				changeBeans.setPlcEqpData(null);//PLC上一条记录
				changeBeans.setHbCount(0);//本班自动换班标识
			}
			//end
			EquipmentData eqpData = DataSnapshot.getInstance().getEquipmentData(Integer.parseInt(eqpId));//获取 内存快照里的 当前值
			String str ="";
			String jtType ="";//表示是 卷烟机  还是 卷包机
			if(null!=eqpData&&!"A-S".equals(type)){//自动开启工单  记录信息为空即可
				List<CommonData> datas = eqpData.getAllData(); //工单开启时 初始化数据
				int cl =   0;
				//卷烟机产量 和 包装机产量
				if(EquipmentTypeDefinition.getRoller().contains(eqpData.getType())){//当前值 卷烟机产量
					cl = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "7"));
				}else if(EquipmentTypeDefinition.getPacker().contains(eqpData.getType())){//当前值  包装机 产量
					cl = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "3"));
				}else if(EquipmentTypeDefinition.getFilter().contains(eqpData.getType())){//当前值 成型机  产量	
				}
				if(cl==0){
					query.setPmvMsg("");
					query.setPmvTime("");
					query.setPmv1Msg("");
					query.setPmv1Time("");
				}else{
					query.setPmv1Msg("");
					query.setPmv1Time("");
					query.setPmv2Msg("");
					query.setPmv2Time("");
				}
				jtType = eqpData.getType();//设备型号
				eqpData.setName(loginName);//用户名
				eqpData.setTeamCode(teamCode);//班组
				eqpData.setShiftCode(shiftCode);//班次
				eqpData.setWorkshopCode(loginWorkshopCode);//车间
				eqpData.setUserId(userId);//用户主键ID
				//java Bean 转json
				String[] tes ={""};
				JsonConfig jsonConfig = JSONUtil.kickProperty(tes,EquipmentData.class);
				Map out = new HashMap();
				out.put("root",eqpData);
				str = JSONObject.fromObject(out, jsonConfig).toString();
			}
			//end
			query.setWorkType(jtType);
			query.setPmvStartMsg(str);//开始采集的值
			query.setPmvStartTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			//辅料数据保存 add by luther.zhang 20150715
			int flEqpId = Integer.parseInt(eqpId)*1000;
			EquipmentData flEqpData = DataSnapshot.getInstance().getEquipmentData(flEqpId);
			String flStr ="";
			if(null!=flEqpData&&!"A-S".equals(type)){//自动开启工单  记录信息为空即可
				String[] tes ={""};
				JsonConfig jsonConfig = JSONUtil.kickProperty(tes,EquipmentData.class);
				Map out = new HashMap();
				out.put("root",flEqpData);
				flStr = JSONObject.fromObject(out, jsonConfig).toString();
			}
			query.setPmvStartFlMsg(flStr);//工单开始的辅料值
			//end
			if("end".equals(type)){
				query.setDasprocess(0);//WCT结束工单赋值为0,DAC运行后赋值为1,PMS运行后赋值为2
				query.setShifttime(new Date());
				query.setNote("手动换牌/换班");//备注
				query.setPmvMsg(str);
				query.setPmvFlMsg(flStr);//add by luther.zhang 20150715
				query.setPmvTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				query.setState("E");//工单运行状态:结束
			}else if("A-S".equals(type)){//表示自动运行工单模式
				query.setDasprocess(0);//WCT结束工单赋值为0,DAC运行后赋值为1,PMS运行后赋值为2
				query.setShifttime(new Date());
				query.setNote("自动开启工单");//备注
				query.setPmvMsg(str);
				query.setPmvFlMsg(flStr);//add by luther.zhang 20150715
				query.setPmvTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				query.setState("S");//工单运行状态:结束
			}else if("A-E".equals(type)){//工单是提前 结束的 	包装机需要清零
				query.setDasprocess(0);//WCT结束工单赋值为0,DAC运行后赋值为1,PMS运行后赋值为2
				query.setShifttime(new Date());
				query.setNote("自动换牌结束");//备注
				query.setPmvMsg(str);
				query.setPmvFlMsg(flStr);//add by luther.zhang 20150715
				query.setPmvTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				query.setState("E");//工单运行状态:结束
			}
			//运行中的工单 add by luther.zhang 20150721
			if("S".equals(query.getState())){
				DataEquipmentData.thisBcMap.put(String.valueOf(eqpId),query);
			}else{
				DataEquipmentData.thisBcMap.put(String.valueOf(eqpId),null);
			}
			//end
		}else{//新增保存最后一条数据工单
			MdManualShift shift = new MdManualShift();
			shift.setEqpid(eqpId);//设备code
			shift.setWorkShop(workShop);//车间
			shift.setPmsprocess(-1);//预留字段
			shift.setDasprocess(1);//第一次开启 不需要 DAC配合处理数据
			shift.setAddTime(new Date());//新增时间
			shift.setModifyTime(new Date());//修改时间
			shift.setAddUserId(userId);//新增人
			shift.setModifyUserId(userId);//修改人
			shift.setProWorkId(proWorkId);//工单主键ID
			shift.setTeamCode(teamCode);//班组
			shift.setShiftCode(shiftCode);//班次
			shift.setNote("运行工单");//不管手动 自动工单 第一次 都是 运行状态
			shift.setState("S");//工单运行状态:运行中
			//最初 最原始数据
			EquipmentData eqpData = DataSnapshot.getInstance().getEquipmentData(Integer.parseInt(eqpId));
			String str ="";
			String jtType ="";//表示是 卷烟机  还是 卷包机
			if(null!=eqpData&&!"A-S".equals(type)){//自动开启工单  记录信息为空即可
				jtType = eqpData.getType();//设备型号
				eqpData.setName(loginName);//用户名
				eqpData.setTeamCode(teamCode);//班组
				eqpData.setShiftCode(shiftCode);//班次
				eqpData.setWorkshopCode(loginWorkshopCode);//车间
				eqpData.setUserId(userId);//用户主键ID
				//java Bean 转json
				String[] tes ={""};
				JsonConfig jsonConfig = JSONUtil.kickProperty(tes,EquipmentData.class);
				Map out = new HashMap();
				out.put("root",eqpData);
				str = JSONObject.fromObject(out, jsonConfig).toString();
			}
			shift.setWorkType(jtType);
			shift.setPmvStartMsg(str);//开始采集的值
			shift.setPmvStartTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			//辅料数据保存 add by luther.zhang 20150715
			int flEqpId = Integer.parseInt(eqpId)*1000;
			EquipmentData flEqpData = DataSnapshot.getInstance().getEquipmentData(flEqpId);
			String flStr ="";
			if(null!=flEqpData&&!"A-S".equals(type)){//自动开启工单  记录信息为空即可
				String[] tes ={""};
				JsonConfig jsonConfig = JSONUtil.kickProperty(tes,EquipmentData.class);
				Map out = new HashMap();
				out.put("root",flEqpData);
				flStr = JSONObject.fromObject(out, jsonConfig).toString();
			}
			shift.setPmvStartFlMsg(flStr);//工单开始的辅料值
			//end
			if("end".equals(type)){//手动换班 ,需要DAC 0 
				shift.setDasprocess(0);//WCT结束工单赋值为0,DAC运行后赋值为1,PMS运行后赋值为2
				shift.setShifttime(new Date());
				shift.setNote("手动换牌/换班");//备注
				shift.setPmvMsg(str);
				shift.setPmvFlMsg(flStr);//add by luther.zhang 20150715
				shift.setPmvTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				shift.setState("E");//工单运行状态:结束
			}else if("A-S".equals(type)){//表示自动运行工单模式
				shift.setNote("自动换牌");//备注
				shift.setState("S");//工单运行状态:运行中
				shift.setDasprocess(1);//自动开启的工单 都是第一次
			}else if("A-E".equals(type)){//表示自动运行工单模式
				shift.setNote("自动换牌结束");//备注
				shift.setState("E");//工单运行状态:运行中
				shift.setDasprocess(1);//自动开启的工单 都是第一次
			}
			mdManualShiftDao.save(shift);
			
			//运行中的工单 add by luther.zhang 20150721
			if("S".equals(shift.getState())){
				DataEquipmentData.thisBcMap.put(String.valueOf(eqpId),shift);
			}else{
				DataEquipmentData.thisBcMap.put(String.valueOf(eqpId),null);
			}
			//end
		}
		MdManualShiftLog log = new MdManualShiftLog();
		log.setEqpid(eqpId);//设备code
		log.setWorkShop(workShop);//车间
		log.setPmsprocess(-1);//预留字段
		log.setDasprocess(0);//WCT结束工单赋值为0,DAC运行后赋值为1,PMS运行后赋值为2
		log.setShifttime(new Date());
		log.setNote("手动换牌/换班");//备注
		log.setAddTime(new Date());//新增时间
		log.setModifyTime(new Date());//修改时间
		log.setAddUserId(userId);//新增人
		log.setModifyUserId(userId);//修改人
		log.setProWorkId(proWorkId);//工单主键ID
		if("end".equals(type)){
			mdManualShiftLogDao.save(log);
		}
	}
	
	/**
	 *  功能说明：判断是否提前完工，如果提前完工，
	 *         向 eqm_cull_record表添加提前完成多少时间，及相关信息记录
	 *  @author wchuang
	 *  @time 2015年7月22日17:36:56
	 *  
	 *  @param proWorkId 工单主键ID
	 * 
	 * */
	public void saveCullRecordData(String proWorkId){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String sql="select real_stim,getdate() as real_etime from SCH_WORKORDER where id='"+proWorkId+"'";
			//String sql="select real_stim,real_etim from SCH_WORKORDER where id='0038b692-77ed-4e65-8a3e-87b8b93b9e56'";
			//通过主键，查询该条记录
			List<?> wchd=schWorkorderDao.queryBySql(sql);
			Object[] temp=(Object[]) wchd.get(0);
			Date st=df.parse(temp[0].toString()); //实际开始时间
			Date et=df.parse(temp[1].toString()); //实际结束时间
			//获得实际完成时间
			long edate=et.getTime();
			//获得实际开始时间
			long sdate=st.getTime();
			int res=(int) ((edate-sdate)/(1000*60));
			if(res<8*60){ //8小时=8*60分钟
				int stop_time=8*60-res;
				//添加剔除记录
				EqmCullRecordBean eqb=new EqmCullRecordBean();
				//获得登录信息
				LoginBean loginBean = (LoginBean) WebContextUtil.getSession().getAttribute("loginInfo");
				//用户基本信息
				eqb.setCreate_time(new Date());
				eqb.setUpdate_time(new Date());
				eqb.setUpdate_user_id(loginBean.getUserId());
				eqb.setUpdate_user_name(loginBean.getName());
				eqb.setCreate_user_id(loginBean.getUserId());
				eqb.setCreate_user_name(loginBean.getName());
				//获得设备ID
				eqb.setEqp_id(loginBean.getEquipmentId());
				eqb.setTeam_id(loginBean.getTeamId());
				//获得外键关联Id
				eqb.setCid(proWorkId);
				//获得提前完成的剔除时间
				eqb.setStop_time(stop_time);
				//班次
				eqb.setShift_id(loginBean.getShiftId());
				eqb.setType_id("3");
				eqb.setType_name("生产提前完成时间");
				eqb.setDel(1);
				eqb.setRemark("生产提前完成时间");
				//开始时间  结束时间
				eqb.setSt_date(st);
				eqb.setEd_date(et);
				eqmCullRecordDao.save(eqb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("剔除时间插入错误！");
		}
	}

	
	
}
