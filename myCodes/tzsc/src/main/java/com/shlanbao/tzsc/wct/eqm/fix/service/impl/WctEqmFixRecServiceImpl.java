package com.shlanbao.tzsc.wct.eqm.fix.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmFixRecDaoI;
import com.shlanbao.tzsc.base.dao.SysServiceInfoDaoI;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.fix.beans.SysMaintenanceStaff;
import com.shlanbao.tzsc.wct.eqm.fix.beans.SysServiceInfo;
import com.shlanbao.tzsc.wct.eqm.fix.beans.WctEqmFixInfoBean;
import com.shlanbao.tzsc.wct.eqm.fix.beans.WctEqmFixRecBean;
import com.shlanbao.tzsc.wct.eqm.fix.service.WctEqmFixRecServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

@Service
public class WctEqmFixRecServiceImpl extends BaseService implements WctEqmFixRecServiceI{
	@Autowired
	protected EqmFixRecDaoI eqmFixDao;
	
	@Autowired
	protected SysServiceInfoDaoI sysSeviceInfo;
	
	@Autowired
	protected BaseDaoI<T> baseDao;

	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	
	@Override
	public void addFixRec(WctEqmFixRecBean eqmFixRecBean,LoginBean loginBean) throws Exception {
		
		//根据该项的主键ID+计划主键ID查询 
		StringBuffer buff = new StringBuffer();
		buff.append("from EqmFixRec rec ");
		List<Object> params = new ArrayList<Object>();
		buff.append("where rec.planId=? and rec.planParamId=? ");
		params.add(eqmFixRecBean.getPlanId());
		params.add(eqmFixRecBean.getPlanParamId());
		EqmFixRec queryBean = eqmFixDao.unique(EqmFixRec.class, buff.toString(), params);
		if(null!=queryBean&&!"".equals(queryBean)){//更新
			queryBean.setRemark(StringUtil.trim(eqmFixRecBean.getRemark()));
			//queryBean.setFixtype(eqmFixRecBean.getFixtype());
			if(null!=eqmFixRecBean.getShiftId()){
				//queryBean.setMdShift(new MdShift(eqmFixRecBean.getShiftId()));//计划班次
			}
		}else{
			//新增
			EqmFixRec fixRec  = new EqmFixRec();
			fixRec = BeanConvertor.copyProperties(eqmFixRecBean, EqmFixRec.class) ;
			fixRec.setId(UUID.randomUUID().toString());//ID
			//WCT维修呼叫新增 没有设备主键ID  只有设备CODE
			if(null!=eqmFixRecBean.getEquipId()||!"".equals(eqmFixRecBean.getEquipId())){
				fixRec.setEqp_id(new MdEquipment(eqmFixRecBean.getEquipId()));//设备 EQUIP_ID
			}
			fixRec.setRemark(StringUtil.trim(eqmFixRecBean.getRemark()));//REMARK 备注
			//fixRec.setFixtype(eqmFixRecBean.getFixtype());//FIXTYPE 类型
			fixRec.setMaintaiin_type(new Long(eqmFixRecBean.getFixtype()).intValue());
			//fixRec.setPlanId(eqmFixRecBean.getPlanId());//PLAN_ID 计划ID
			//fixRec.setPlanParamId(eqmFixRecBean.getPlanParamId());// PLAN_PARAM_ID 计划对应的子项
			fixRec.setAsk_date(new Date());//请求时间 RESTIM
			//fixRec.setSysUserByRequsr(new SysUser(loginBean.getUserId()));//RESUSR
			fixRec.setAsk_userId(loginBean.getUserId());
			fixRec.setStatus(0);
			// REQUSR 响应用户     REQTIM 响应时间    FIXTIM 修复时间   SHIFT 班次     SENDFLAG 是否反馈MES
			if(null!=eqmFixRecBean.getShiftId()){
				fixRec.setShift_id(new MdShift(eqmFixRecBean.getShiftId()));//计划班次
			}
			eqmFixDao.save(fixRec);
		}
	}
	
	/**
	 * 添加维修信息
	 * 
	 * 
	 */
	@Override
	public void addFix(WctEqmFixInfoBean fixInfoBean,String all_id,String all_num,String use_n,String name,String fixId)throws Exception{
		String[] spareId=all_id.split(",");
		String[] useNum=use_n.split(",");
		String[] allNum=all_num.split(",");
		List<EqmFixRec> eqmFixRecs=new ArrayList<EqmFixRec>();
		for (int i = 0; i < allNum.length; i++){
			int n=Integer.parseInt(useNum[i]);
			if(n==0){
					continue;
			}else{
				EqmFixRec fixRec  = new EqmFixRec();
				fixRec = BeanConvertor.copyProperties(fixInfoBean, EqmFixRec.class) ;
				fixRec.setId(UUID.randomUUID().toString());
				fixRec.setEqp_id(fixInfoBean.getEqpId());
				fixRec.setShift_id(fixInfoBean.getShiftId());
				fixRec.setMaintaiin_id(fixId);
				fixRec.setMaintaiin_name(name);
				fixRec.setMaintaiin_time(new Date());
				fixRec.setMaintaiin_type(fixInfoBean.getMaintaiinType());
				fixRec.setSpare_parts_id(spareId[i]);
				fixRec.setSpare_parts_num(Integer.parseInt(useNum[i]));
				fixRec.setStatus(fixInfoBean.getStatus());
				fixRec.setSource("3");
				fixRec.setRemark(fixInfoBean.getRemark());
				fixRec.setTrouble_id(fixInfoBean.getId());
				eqmFixRecs.add(fixRec);
			}
		}
		sysSeviceInfo.batchInsert(eqmFixRecs, EqmFixRec.class);
	}
	
	@Override
	public void updateFixRec(WctEqmFixRecBean eqmFixRecBean,LoginBean loginBean)throws Exception{
		EqmFixRec queryBean = eqmFixDao.findById(EqmFixRec.class, eqmFixRecBean.getWctEqmFixRecId());
		if(null!=queryBean&&!"".equals(queryBean)){
			queryBean.setMaintaiin_id(eqmFixRecBean.getSbUserId());//响应用户
			queryBean.setMaintaiin_time(new Date());//时间
			queryBean.setStatus(Integer.parseInt(eqmFixRecBean.getWxclState()));//状态
			queryBean.setRemark(eqmFixRecBean.getWxclRemark());//备注
		}
	}
	@Override
	public void updateFixRecFinsh(WctEqmFixRecBean eqmFixRecBean,LoginBean loginBean)throws Exception{
		EqmFixRec queryBean = eqmFixDao.findById(EqmFixRec.class, eqmFixRecBean.getWctEqmFixRecId());
		if(null!=queryBean&&!"".equals(queryBean)){
			queryBean.setMaintaiin_id(eqmFixRecBean.getSbUserId());//响应用户
			queryBean.setMaintaiin_time(new Date());//时间
			queryBean.setStatus(Integer.parseInt(eqmFixRecBean.getWxclState()));//状态
			queryBean.setRemark(eqmFixRecBean.getWxclRemark());//备注
		}
	}

	@Override
	public DataGrid getFixRecList(WctEqmFixRecBean eqmFixRecBean, PageParams pageParams)throws Exception {
		StringBuffer  tables = new StringBuffer();
		tables.append(" from Eqm_Fix_Rec efr ");
		tables.append("left join EQM_WHEEL_COVEL_PLAN temp1 on efr.PLAN_ID=temp1.ID  ");      //计划
		tables.append("left join EQM_WHEEL_COVEL_PARAM temp2 on efr.PLAN_PARAM_ID=temp2.id "); //维护项
		tables.append("left join SYS_EQP_TYPE temp8 on temp2.SET_ID=temp8.id  ");
		tables.append("left join MD_EQUIPMENT temp3 on efr.EQUIP_ID=temp3.ID ");
		tables.append("left join MD_SHIFT temp4 on efr.SHIFT=temp4.ID ");
		tables.append("left join SYS_USER temp5 on efr.REQUSR=temp5.ID ");
		tables.append("left join SYS_USER temp6 on efr.RESUSR=temp6.ID ");
		tables.append("left join SYS_USER temp7 on efr.APPOINT_USR=temp7.ID ");
		tables.append("left join SYS_USER temp9 on efr.FIX_USR=temp9.ID ");
		
		StringBuffer  titles = new StringBuffer();
		titles.append("efr.ID,efr.PLAN_ID,temp1.PLAN_NAME,efr.EQUIP_ID,temp3.EQUIPMENT_CODE,temp3.EQUIPMENT_NAME,efr.SHIFT,temp4.NAME as SHIFT_NAME,");
		titles.append("efr.REQUSR,temp5.NAME as REQUSR_NAME,efr.REQTIM,");
		titles.append("efr.RESUSR,temp6.NAME as RESUSR_NAME,efr.RESTIM,");
		titles.append("efr.APPOINT_USR,temp7.NAME as APPOINT_USR_NAME,efr.APPOINT_TIME,");
		titles.append("efr.PLAN_PARAM_ID,temp2.SET_ID,temp8.NAME,efr.FIXTIM,efr.REMARK,efr.SENDFLAG,efr.FIXTYPE,efr.STATE,");
		titles.append("efr.FIX_USR,temp9.NAME as FIX_USR_NAME ");
		
		StringBuffer  whereSql = new StringBuffer();
			whereSql.append(" where efr.STATE in('1','2','3') ");
		if(null!=eqmFixRecBean.getEquipmentCode()&&!"".equals(eqmFixRecBean.getEquipmentCode())){
			whereSql.append(" and temp3.EQUIPMENT_CODE ='"+eqmFixRecBean.getEquipmentCode()+"' ");
		}
		
		String countSql="select count(efr.id) countLen "+tables.toString()+whereSql.toString();
		
		long total= 0;//总记录数
		List<?> list = eqmFixDao.queryBySql(countSql, null);//只会有一条记录
		if(null!=list&&list.size()>0){
			Object arr=(Object) list.get(0);//返回一个对象，而不是 一组数组
			total =Long.parseLong(ObjectUtils.toString(arr));
		}
		List<WctEqmFixRecBean> lastList = new ArrayList<WctEqmFixRecBean>();
		if(total>0){
			StringBuffer lastSql= new  StringBuffer();
			lastSql.append("select * from ( ");
			lastSql.append("	select row_number() over(ORDER BY efr.REQTIM DESC) as rownumber,");
			lastSql.append(titles.toString()).append(tables.toString()).append(whereSql.toString());
			lastSql.append(")tempLast where  rownumber>").append((pageParams.getPage()-1)*pageParams.getRows())
				   .append(" and rownumber<=").append(pageParams.getPage()*pageParams.getRows());//分页
			list = eqmFixDao.queryBySql(lastSql.toString(),null);
			if(null!=list&&list.size()>0){
				WctEqmFixRecBean fixRecBean = null;
				for(int i=0;i<list.size();i++){
					fixRecBean = new WctEqmFixRecBean();
					Object[] arr=(Object[]) list.get(i);
					fixRecBean.setId(ObjectUtils.toString(arr[1]));//主键ID
					fixRecBean.setPlanId(ObjectUtils.toString(arr[2]));//计划ID
					fixRecBean.setPlanName(ObjectUtils.toString(arr[3]));//计划名称
					fixRecBean.setEquipId(ObjectUtils.toString(arr[4]));//设备ID
					fixRecBean.setEquipmentCode(ObjectUtils.toString(arr[5]));
					fixRecBean.setEquipName(ObjectUtils.toString(arr[6]));
					fixRecBean.setShiftId(ObjectUtils.toString(arr[7]));//班组ID
					fixRecBean.setShiftName(ObjectUtils.toString(arr[8]));
					fixRecBean.setRequsrId(ObjectUtils.toString(arr[9]));//请求用户
					fixRecBean.setRequsrName(ObjectUtils.toString(arr[10]));
					fixRecBean.setReqtim(ObjectUtils.toString(arr[11]));
					fixRecBean.setResusrId(ObjectUtils.toString(arr[12]));//响应用户
					fixRecBean.setResusrName(ObjectUtils.toString(arr[13]));
					fixRecBean.setRestim(ObjectUtils.toString(arr[14]));
					fixRecBean.setAppointUsrId(ObjectUtils.toString(arr[15]));//指定人员ID
					fixRecBean.setAppointUsrName(ObjectUtils.toString(arr[16]));
					fixRecBean.setAppointTime(ObjectUtils.toString(arr[17]));
					fixRecBean.setPlanParamId(ObjectUtils.toString(arr[18]));//维护项ID
					fixRecBean.setSysEqpTypeId(ObjectUtils.toString(arr[19]));
					fixRecBean.setPlanParamName(ObjectUtils.toString(arr[20]));
					fixRecBean.setFixtim(ObjectUtils.toString(arr[21]));//修复时间
					fixRecBean.setRemark(ObjectUtils.toString(arr[22]));//备注
					String sendFlag= ObjectUtils.toString(arr[23]);
					Long  sendLong= Long.parseLong(sendFlag);
					fixRecBean.setSendflag(sendLong);//是否反馈MES
					String fixType= ObjectUtils.toString(arr[24]);
					Long  typeLong= Long.parseLong(fixType);
					fixRecBean.setFixtype(typeLong);//修复类型
					fixRecBean.setState(ObjectUtils.toString(arr[25]));
					fixRecBean.setFixUsrId(ObjectUtils.toString(arr[26]));//完成人员
					fixRecBean.setFixUsrName(ObjectUtils.toString(arr[27]));//完成人员
					lastList.add(fixRecBean);
				}
			}
		}
		return new DataGrid(lastList, total);
	}

	/**
	 * [功能说明]：维修请求-呼叫点餐查询页
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * @修改人：景孟博
	 * @修改时间：2015年10月13日
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	@Override
	public List<SysMaintenanceStaff> queryStaffAll(SysMaintenanceStaff ssf) {
		List<SysMaintenanceStaff> ssfList=new ArrayList<SysMaintenanceStaff>();
		String type_id="";
		if(StringUtil.notNull(ssf.getType_id())){
			type_id=" and type_id='"+ssf.getType_id()+"'";
		}
		if(StringUtil.notNull(ssf.getId())){
			String sql="select id,shift_id,user_name,remark,status,type_name,path,eqp_type from sys_maintenance_staff where 1=1 "+type_id+" and team_id='"+ssf.getTeam_id()+"'and id='"+ssf.getId()+"'" ;
			List<?> list=baseDao.queryBySql(sql);
			if(list.size()>0){
				for(Object o:list){
					Object[] temp=(Object[]) o;
					SysMaintenanceStaff sf=new SysMaintenanceStaff();
					try {
						if(temp[0]!=null){
							sf.setId((temp[0].toString()));
						}
						if(temp[1]!=null){
							sf.setShift_id(temp[1].toString());					
						}
						if(temp[2]!=null){
							sf.setUser_name(temp[2].toString());
						}
						if(temp[3]!=null){
							sf.setRemark(temp[3].toString());
						}
						if(temp[4]!=null){
							sf.setStatus(temp[4].toString());
						}
						if(temp[5]!=null){
							sf.setType_name(temp[5].toString());				
						}
						if(temp[6]!=null){
							String path_img=bundle.getString("service_url") + bundle.getString("upload_img")+"\\"+temp[6].toString();
							sf.setPath(path_img);
						}
						if(temp[7]!=null){
							sf.setEqp_type(temp[7].toString());
						}
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					ssfList.add(sf);
				}
			}
		}else{
		String sql="select id,shift_id,user_name,remark,status,type_name,path,eqp_type from sys_maintenance_staff where 1=1 "+type_id+" and team_id='"+ssf.getTeam_id()+"'" ;
		List<?> list=baseDao.queryBySql(sql);
		if(list.size()>0){
			for(Object o:list){
				Object[] temp=(Object[]) o;
				SysMaintenanceStaff sf=new SysMaintenanceStaff();
				try {
					if(temp[0]!=null){
						sf.setId((temp[0].toString()));
					}
					if(temp[1]!=null){
						sf.setShift_id(temp[1].toString());					
					}
					if(temp[2]!=null){
						sf.setUser_name(temp[2].toString());
					}
					if(temp[3]!=null){
						sf.setRemark(temp[3].toString());
					}
					if(temp[4]!=null){
						sf.setStatus(temp[4].toString());
					}
					if(temp[5]!=null){
						sf.setType_name(temp[5].toString());				
					}
					if(temp[6]!=null){
						String path_img=bundle.getString("service_url") + bundle.getString("upload_img")+"\\"+temp[6].toString();
						sf.setPath(path_img);
					}
					if(temp[7]!=null){
						sf.setEqp_type(temp[7].toString());
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				ssfList.add(sf);
			}
		}
		}
		
		return ssfList;
	}
	
	/**
	 * [功能说明]：维修请求-添加呼叫记录
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * 
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	@Override
	public String saveServcieInfo(SysServiceInfo ssInfo) {
		try {
			//sysSeviceInfo.save(ssInfo);
			String id=sysSeviceInfo.saveBackKey(ssInfo);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//查询工单
	@Override
	public List<SchWorkorder> queryWorkOrder(SysServiceInfo ssInfo) {
		List<SchWorkorder> ssfList=new ArrayList<SchWorkorder>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dt=sdf.format(new Date());
		String sql="select id,code from SCH_WORKORDER where date>='"+dt+"' and date <='"+dt+"' and shift='"+ssInfo.getShift_id()+"' and eqp='"+ssInfo.getEqp_id()+"'";
		List<?> list=baseDao.queryBySql(sql);
		if(list.size()>0){
			Object[] temp=(Object[])list.get(0);
			SchWorkorder sf=new SchWorkorder();
			try {
				sf.setId((temp[0].toString()));
			} catch (Exception e) {
				sf.setId("0");
			}
			ssfList.add(sf);
		}
		return ssfList;
	}

	/**
	 * [功能说明]：维修请求-查询
	 * 
	 * @author  wanchanghuang
	 * @time 2015年9月4日14:14:25
	 * 修改人：景孟博
	 * 修改时间：2015年10月29日
	 * */
	@Override
	public List<SysServiceInfo> querySysServiceInfoAll(SysServiceInfo info) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime="";
		String etime="";
		String eqp_id="";
		if(StringUtil.notNull(info.getStime())){
			stime=" and CONVERT(varchar(100), create_user_time, 23) >='"+info.getStime()+"' ";
		}
        if(StringUtil.notNull(info.getEtime())){
			etime=" and CONVERT(varchar(100), create_user_time, 23) <='"+info.getEtime()+"' ";
		}
        if(StringUtil.notNull(info.getEqp_id())){
        	eqp_id=" and eqp_id='"+info.getEqp_id()+"' ";
		}
        if( !StringUtil.notNull(info.getStime()) && !StringUtil.notNull(info.getEtime()) ){
        	SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
        	String tm=sdfs.format(new Date());
        	stime=" and CONVERT(varchar(100), create_user_time, 23) >='"+tm+"' ";
        	etime=" and CONVERT(varchar(100), create_user_time, 23) <='"+tm+"' ";
        }
		List<SysServiceInfo> listInfo=new ArrayList<SysServiceInfo>();
		
		String sql=" select id,shift_id,(select name from MD_SHIFT where id=shift_id) as shift_name,"
		 +" eqp_id,eqp_name,create_user_time,create_user_id,create_user_name,designated_person_id,"
		 +" designated_person_name,designated_person_time,type_name,status,remark,update_user_name,(SELECT type_id from sys_maintenance_staff where user_name=designated_person_name) as roleId"
		 +" from sys_service_info where del='0'"+stime+etime+eqp_id+" order by create_user_time desc";
		List<?> list=baseDao.queryBySql(sql);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				SysServiceInfo ssf=new SysServiceInfo();
				try {
					Object[] temp=(Object[])list.get(i);
					ssf.setId(temp[0].toString());
					ssf.setShift_id(temp[1].toString());
					ssf.setShift_name(temp[2].toString());
					ssf.setEqp_id(temp[3].toString());
					ssf.setEqp_name(temp[4].toString());
					ssf.setCreate_user_time(sdf.parse(temp[5].toString()));
					ssf.setCreate_user_id(temp[6].toString());
					ssf.setCreate_user_name(temp[7].toString());
					
					ssf.setDesignated_person_id(StringUtil.convertObjToString(temp[8]));
					ssf.setDesignated_person_name(StringUtil.convertObjToString(temp[9]));
					if(temp[10]!=null){
						ssf.setDesignated_person_time(sdf.parse(temp[10].toString()));
					}else{
						ssf.setDesignated_person_time(null);
					}
					ssf.setType_name(StringUtil.convertObjToString(temp[11]));
					ssf.setStatus(StringUtil.convertObjToString(temp[12]));
					if(temp[13]!=null){
						ssf.setRemark(temp[13].toString());
					}else{
						ssf.setRemark("");
					}
					if(temp[14]!=null){
						ssf.setUpdate_user_name(temp[14].toString());
					}else{
						ssf.setUpdate_user_name(null);
					}
					if(temp[15]!=null){
						ssf.setRoletype(temp[15].toString());
					}else{
						ssf.setRoletype("");
					}
					
				} catch (Exception e) {
					System.out.println("**********维修呼叫查询异常错误！************");
					e.printStackTrace();
				}
				listInfo.add(ssf);
			}
		}
		return listInfo;
	}

	/**
	 *功能： 受理人改变维修请求表维修状态
	 * @author 景孟博
	 * @time 2015年9月25日
	 */
	@Override
	public void updateSlServiceInfo(String id,String status,String designated_person_id,String designated_person_name,String name){
		StringBuffer wx = new StringBuffer();
		if(status.equals("0")){
			String sql = "UPDATE sys_service_info SET status = '1',designated_person_time=getdate(),update_user_name='"+name+"' WHERE id='"+id+"'";
			//status 0等待中，1维修中，2完成
			sysSeviceInfo.updateBySql(sql, null);
		}else if(status.equals("1")){
			wx.append("UPDATE sys_service_info SET status = '2',update_user_time=getdate(),");
			wx.append("update_user_id='"+designated_person_id+"',update_user_name='"+name+"'");
			wx.append("WHERE id='"+id+"'");
			//String sql = "UPDATE sys_service_info SET status = '2',update_user_time=getdate() WHERE id='"+id+"'";
			//status 0等待中，1维修中，2完成
			sysSeviceInfo.updateBySql(wx.toString(), null);
		}
		
	}
	
	/**
	 * 功能：根据id查询维修人信息
	 * @author 景孟博
	 * @time 2015年9月25日
	 * @param id
	 */
	@Override
	public 	SysServiceInfo querySlServiceInfoById(String id){
		String sql = "SELECT status,designated_person_time FROM sys_service_info WHERE id = '"+id+"'";
		//status 0等待中，1维修中，2完成
		List lt = sysSeviceInfo.queryBySql(sql);
		Object[] o=(Object[]) lt.get(0);
		SysServiceInfo info=new SysServiceInfo();
		for (int i = 0; i < o.length; i++) {
			info.setStatus(o[0].toString());
			/*try {
				info.setDesignated_person_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(o[1].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
		}
		return info;
	}
	
	/**
	 * 修改维修工信息表工作状态
	 * @author 景孟博
	 * @param status
	 * @param designated_person_id
	 */
	@Override
	public void updateStaff(String status,String designated_person_id)throws Exception{
		if(status.equals("0")){
		String sql = "update sys_maintenance_staff set status = '1' where id = '"+designated_person_id+"'";
		sysSeviceInfo.updateBySql(sql, null);
		}else if((status.equals("1"))){
			String sql2 = "update sys_maintenance_staff set status = '0' where id = '"+designated_person_id+"'";
			sysSeviceInfo.updateBySql(sql2, null);
		}
	} 
	
	/**
	 * 张璐-2015.11.6
	 * WCT手动添加备品备件
	 * @param spare_name
	 * @param spare_code
	 * @throws Exception
	 */
	@Override
	public String[] addNewFix(String spare_name,String spare_code)throws Exception{
		spare_name=spare_name.substring(1);
		spare_code=spare_code.substring(1);
		spare_name=spare_name.trim();
		spare_code=spare_code.trim();
		String[] fixName=spare_name.split(",");//备品备件名称
		String[] fixCode=spare_code.split(",");//备品备件型号
		String[] ids= new String[1000];
		for(int i=0;i<fixCode.length;i++){
			String a=UUID.randomUUID().toString();
			StringBuffer sb=new StringBuffer(1000);
			sb.append("insert into COS_SPARE_PARTS (id,name,type,del) VALUES ('"+a+"','"+fixName[i]+"','"+fixCode[i]+"',1)");
			sysSeviceInfo.updateBySql(sb.toString(), null);
			ids[i]=a;
		}
		return ids;
	}
	
	/**
	 * 张璐
	 * 查询备品备件的名称和型号
	 */
	@Override
	public String[] querySpareName(String[] id,String[] unum){
		List<?> list = new ArrayList<>();
		String [] o = new String[100];
		for(int i=0;i<id.length;i++){
			if(!unum[i].equals(0)){
				String sql="SELECT NAME,TYPE FROM COS_SPARE_PARTS where id='"+id[i]+"'";
				list = sysSeviceInfo.queryBySql(sql);
				o[i]=list.get(i).toString();
			}
			return o;
		}
		return null;
	}
	
	/**
	 * 查询备品备件名称和使用数量及故障名称
	 * @author 景孟博
	 * @time 2015年11月11日
	 * 
	 */
	@Override
	public List<SysServiceInfo> querySpareTrouble(String id){
		List<SysServiceInfo> listInfo=new ArrayList<SysServiceInfo>();
		StringBuffer sql=new StringBuffer(1000);
		sql.append( " (select (select name From COS_SPARE_PARTS where id=spare_parts_id ) as name ,maintaiin_time,'1',spare_parts_num from EQM_FIX_REC  where trouble_id='"+id+"') ");
		sql.append(" UNION ");
		sql.append(" (select DESCRIPTION as name , create_date as maintaiin_time,'2','0' from EQM_TROUBLE where SOURCE_ID='"+id+"'  ) ");
		List<?> list=baseDao.queryBySql(sql.toString());
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				SysServiceInfo ssf=new SysServiceInfo();
				try{
					Object[] temp=(Object[])list.get(i);
					if(temp[0]!=null){
					  ssf.setSpare_prats_name(temp[0].toString());
					}
					if(temp[1]!=null){
					  ssf.setStime(DateUtil.formatStrDate(temp[1].toString(), "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"));
					}
					if(temp[2]!=null){
					  ssf.setType_name(temp[2].toString());
					}
					if(temp[3]!=null){
				      ssf.setSpare_parts_num(temp[3].toString());
					}
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				listInfo.add(ssf);
			}
		}
		return listInfo;
	}

	@Override
	public RepairResquestBean queryServiceInfoById(String idf) {
		try {
		//查询id
		StringBuffer sb = new StringBuffer(1000);
		sb.append(" select eqp_name,(select name from MD_SHIFT where id=shift_id) as shift_name,(select name from MD_TEAM where id=team_id) as team_name, ");
		sb.append("create_user_name,designated_person_name,type_name,CONVERT(varchar(21),create_user_time,20) as create_time ");
		sb.append("from sys_service_info where id='"+idf+"' ");
		List<?> list=sysSeviceInfo.queryBySql(sb.toString());
		RepairResquestBean rb=null;
		if(list.size()>0 && list!=null){
				Object[] temp=(Object[]) list.get(0);
				rb = new RepairResquestBean();
				rb.setEqpName(StringUtil.convertObjToString(temp[0]));
				rb.setShiftName(StringUtil.convertObjToString(temp[1]));
				rb.setTeamName(StringUtil.convertObjToString(temp[2]));
				rb.setCreateUserName(StringUtil.convertObjToString(temp[3]));
				rb.setUserName(StringUtil.convertObjToString(temp[4]));
				rb.setTypeName(StringUtil.convertObjToString(temp[5]));
				rb.setCreateUserTime(StringUtil.convertObjToString(temp[6]));
		}
		return rb;
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
	}
}
