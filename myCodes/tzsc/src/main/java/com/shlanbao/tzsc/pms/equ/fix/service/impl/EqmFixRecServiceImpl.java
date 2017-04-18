package com.shlanbao.tzsc.pms.equ.fix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmFixRecDaoI;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.fix.beans.EqmFixRecBean;
import com.shlanbao.tzsc.pms.equ.fix.service.EqmFixRecServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;

@Service
public class EqmFixRecServiceImpl extends BaseService implements EqmFixRecServiceI{
	@Autowired
	protected EqmFixRecDaoI eqmFixDao;

	@Override
	public void addFixRec(EqmFixRecBean eqmFixRecBean) throws Exception {
		if(StringUtil.notNull(eqmFixRecBean.getId())){
			String hql = "from EqmFixRec  where id = ? ";
			List<Object> params = new ArrayList<Object>();
			params.add(eqmFixRecBean.getId());
			EqmFixRec fixRec = eqmFixDao.unique(EqmFixRec.class, hql, params);
			if(null!=fixRec){
				fixRec.setEqp_id(new MdEquipment(eqmFixRecBean.getEquipId()));//设备
				fixRec.setShift_id(new MdShift(eqmFixRecBean.getShiftId()));//班次
				//fixRec.setFixtype(eqmFixRecBean.getFixtype());//维修类型
				fixRec.setMaintaiin_type(new Long(eqmFixRecBean.getFixtype()).intValue());
				fixRec.setRemark(eqmFixRecBean.getRemark());//备注	
				if(null!=eqmFixRecBean.getAppointUsrId()&&!"".equals(eqmFixRecBean.getAppointUsrId())){
					fixRec.setMaintaiin_id(eqmFixRecBean.getAppointUsrId());//维修人
					fixRec.setStatus(2);//状态 新增是 0 或 空  , 运行是 1 ,完成 2 
					fixRec.setCreate_user_time(new Date());//创建时间
				}
			}
		}else{
			//新增
			EqmFixRec fixRec = BeanConvertor.copyProperties(eqmFixRecBean, EqmFixRec.class ) ;
			fixRec.setAsk_date(new Date());//请求时间
			fixRec.setEqp_id(new MdEquipment(eqmFixRecBean.getEquipId()));//设备
			SessionInfo sessionInfo = (SessionInfo) WebContextUtil.getObjectFromSession();//设置请求用户
			fixRec.setAsk_userId(sessionInfo.getUser().getId());
			
			fixRec.setShift_id(new MdShift(eqmFixRecBean.getShiftId()));//班次
			//fixRec.setShift_id(eqmFixRecBean.getShiftId());
			//fixRec.setId(null);
			fixRec.setStatus(0);//状态 新增是 0 或 空  , 运行是 1 ,完成 2 
			if(null!=eqmFixRecBean.getAppointUsrId()&&!"".equals(eqmFixRecBean.getAppointUsrId())){
				fixRec.setMaintaiin_id(eqmFixRecBean.getAppointUsrId());
				fixRec.setMaintaiin_time(new Date());
			}
			eqmFixDao.save(fixRec);
		}
		//eqmFixDao.saveOrUpdate(fixRec);
	}

	@Override
	
	public EqmFixRecBean getFixRecById(String id) throws Exception {
		EqmFixRec fixRec=  eqmFixDao.findById(EqmFixRec.class,id);
		EqmFixRecBean recBean=BeanConvertor.copyProperties(fixRec, EqmFixRecBean.class);
		MdEquipment mdEquipment=fixRec.getEqp_id();
		recBean.setEquipId(mdEquipment.getId());
		recBean.setEquipName(mdEquipment.getEquipmentName());
		MdShift shift=fixRec.getShift_id();
		recBean.setShiftId(shift.getId());
		recBean.setShiftName(shift.getName());
		if(null!=fixRec.getMaintaiin_id()){
//			recBean.setAppointUsrId(fixRec.getAppointUsr().getId());
//			recBean.setAppointUsrName(fixRec.getAppointUsr().getName());
			recBean.setAppointUsrId(fixRec.getMaintaiin_id());
			recBean.setAppointUsrName(fixRec.getMaintaiin_name());
		}
		 return recBean;
	}
/**
 * 备件跟换记录查询
 * 2015-09-30
 * shisihai
 */
	@Override
	public DataGrid queryFixRec(EqmFixRecBean eqmFixRecBean, PageParams pageParams)throws Exception {
		int page=pageParams.getPage();
		int row=pageParams.getRows();
		StringBuffer count=this.getSql(eqmFixRecBean, 0);
		List<?> l=eqmFixDao.queryBySql(count.toString());
		Integer leng=(Integer) l.get(0);
		
		StringBuffer sb=this.getSql(eqmFixRecBean, 1);
		sb.append(" and t.rownumber > " +(page-1)*row);
		sb.append(" and t.rownumber <= " +page*row);
		List<Object[]> ls=(List<Object[]>) eqmFixDao.queryBySql(sb.toString());
		List<EqmFixRecBean> beans=new ArrayList<EqmFixRecBean>();
		EqmFixRecBean bean =null;
		for (Object[] o : ls) {
			try {
				bean=new EqmFixRecBean();
				bean.setId(o[0].toString());
				bean.setEquipName(o[1].toString());
				bean.setShiftName(o[2].toString());
				bean.setBjName(o[3].toString());
				bean.setBjNum(o[4].toString());
				bean.setFixtim(o[5].toString());
				bean.setGhName(o[6].toString());
				bean.setFixtype(Long.parseLong(o[7].toString()));//更换备件的来源 1-点检   2-维修呼叫   3-轮保   4-备品备件 5、检修
				try{
				bean.setIsR(o[8].toString());
				}catch(Exception e){
					bean.setIsR("0");//没有反馈
				}
				bean.setBjType(o[12].toString());
				beans.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
//		String hql = "from EqmFixRec o where 1=1";
//		String params = "";
//		//班次
//		if(StringUtil.notNull(eqmFixRecBean.getShiftId())){
//			params += " and o.shift_id.id='" +eqmFixRecBean.getShiftId()+ "'";
//		}
//		//设备id
//		if(StringUtil.notNull(eqmFixRecBean.getEquipId())){
//			params += " and o.eqp_id=" +eqmFixRecBean.getEquipId()+ "%'";
//		}
//		//维修类型 0  电器和 机械 1
//		if(eqmFixRecBean.getFixtype()!=null){
//			params += " and o.maintaiin_type='" +eqmFixRecBean.getFixtype()+ "'";
//		}
//		//完成时间1
//		if(StringUtil.notNull(eqmFixRecBean.getReqtim())){
//			params += " and convert(varchar(100),o.maintaiin_time,23) >='" +eqmFixRecBean.getReqtim()+ "'";
//		}
//		//完成时间2
//		if(StringUtil.notNull(eqmFixRecBean.getFixtim())){
//			params += " and convert(varchar(100),o.maintaiin_time,23) <= '" +eqmFixRecBean.getFixtim()+ "'";
//		}
//		//开始查询数据
//		List<EqmFixRec> eqmFixRecs = eqmFixDao.queryByPage(hql.concat(params), pageParams);
//		List<EqmFixRecBean> beans = new ArrayList<EqmFixRecBean>();
//		for (EqmFixRec fixRec : eqmFixRecs) {
//			EqmFixRecBean bean = new EqmFixRecBean();
//			beanConvertor.copyProperties(fixRec, bean);
//			if(null!=fixRec.getRepair_name()){
//				bean.setAppointUsrName(fixRec.getRepair_name());
//			}
//			if(fixRec.getAsk_userId() != null){
//				bean.setRequsrId(fixRec.getAsk_userId());
//			}
//			//响应用户
//			if(fixRec.getMaintaiin_id() != null){
//				bean.setResusrId(fixRec.getMaintaiin_id());
//				bean.setResusrName(fixRec.getMaintaiin_name());
//			}
//			if(fixRec.getEqp_id() != null){
//				bean.setEquipId(fixRec.getEqp_id().getId());
//				bean.setEquipName(fixRec.getEqp_id().getEquipmentName());
//			}
//			if(fixRec.getShift_id() != null){
//				bean.setShiftId(fixRec.getShift_id().getId());
//				bean.setShiftName(fixRec.getShift_id().getName());
//			}
//			beans.add(bean);
//		}
//		hql = "select count(*) " + hql;
//		long total = eqmFixDao.queryTotal(hql.concat(params));
		return new DataGrid(beans, (long)leng);
	}

	@Override
	public void deleteFixRec(String id) throws Exception {
		eqmFixDao.deleteById(id, EqmFixRec.class);
	}
/**
 * 
 * @param eqmFixRecBean
 * @param type 1 查信息  其余查条数
 * @return
 */
	private StringBuffer getSql(EqmFixRecBean eqmFixRecBean,int type){
		StringBuffer sb=null;
		if(type==1){
			sb=new StringBuffer(" SELECT * from ");
		}else{
			 sb=new StringBuffer(" select count(0) from ");
		}
		sb.append("(SELECT row_number () OVER (ORDER BY a.maintaiin_time ) AS rownumber ,b.EQUIPMENT_NAME as name1,d.NAME as name2,e.NAME as name3,a.spare_parts_num,CONVERT(VARCHAR(100),a.maintaiin_time,20) as maintaiin_time,c.NAME,a.source,a.is_send,b.ID as eqp_id,d.ID as shift_id,a.maintaiin_type as maintaiin_type, e.type as cos_type  from EQM_FIX_REC a ");
		sb.append(" LEFT JOIN MD_EQUIPMENT b ON a.eqp_id=b.ID ");
		
		
		sb.append(" LEFT JOIN SYS_USER c ON a.maintaiin_id=c.ID");
		sb.append(" LEFT JOIN MD_SHIFT d ON a.shift_id=d.ID");
		
		sb.append(" LEFT JOIN COS_SPARE_PARTS e ON e.ID=a.spare_parts_id  where 1=1 ");
		//完成时间1
		if(StringUtil.notNull(eqmFixRecBean.getReqtim())){
			sb.append(" and convert(varchar(100),a.maintaiin_time,23) >='" +eqmFixRecBean.getReqtim()+ "'");
		}
		//完成时间2
		if(StringUtil.notNull(eqmFixRecBean.getRestim())){
			sb.append(" and convert(varchar(100),a.maintaiin_time,23) <= '" +eqmFixRecBean.getRestim()+ "'");
		}
		sb.append(" ) t where 1=1 ");
		//设备id
		if(StringUtil.notNull(eqmFixRecBean.getEquipId())){
			sb.append( " and t.eqp_id= '" +eqmFixRecBean.getEquipId()+ "'");
		}
		//班次
		if(StringUtil.notNull(eqmFixRecBean.getShiftId())){
			sb.append(" and t.shift_id='" +eqmFixRecBean.getShiftId()+ "'");
		}
		//维修类型 0  电器和 机械 1
		if(eqmFixRecBean.getFixtype()!=null){
			sb.append( " and t.maintaiin_type=" +eqmFixRecBean.getFixtype());
		}
		
		return sb;
	}
	
}
