package com.shlanbao.tzsc.pms.equ.trouble.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmTroubleDaoI;
import com.shlanbao.tzsc.base.dao.EqmTroubleReDaoI;
import com.shlanbao.tzsc.base.mapping.EqmTrouble;
import com.shlanbao.tzsc.base.mapping.EqmTroubleInfo;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleInfoBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.TroubleBean;
import com.shlanbao.tzsc.pms.equ.trouble.service.TroubleServiceI;
import com.shlanbao.tzsc.pms.sch.workorder.beans.FaultWkBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class TroubleServiceImpl  extends BaseService implements TroubleServiceI {

	@Autowired
	private EqmTroubleDaoI troubleDao;
	@Autowired
	private EqmTroubleReDaoI eqmtroubleDaoI;
	@Override
	public DataGrid queryTrouble(TroubleBean bean, PageParams pageParams) throws Exception {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select t.ID,e.EQUIPMENT_NAME,t.TROUBLE_NAME,t.DESCRIPTION,shift.NAME as shift_name,t.SOURCE,CONVERT(varchar(32),t.CREATE_DATE,23) as cDate, ");  
		sqlBuffer.append("et.name as name1,maintain.CONTENTS as name2,et2.NAME as name3,usr.NAME as usrName ");
		sqlBuffer.append("from EQM_TROUBLE t ");
		sqlBuffer.append("left join MD_EQUIPMENT e on t.EQU_ID=e.ID ");
		sqlBuffer.append("left join MD_SHIFT shift on t.SHIFT_ID=shift.ID ");
		sqlBuffer.append("left join SYS_USER usr on usr.ID=t.CREATE_USER_ID ");
		sqlBuffer.append("left join SYS_EQP_TYPE et on et.ID= t.SOURCE_ID ");
		sqlBuffer.append("left join EQM_MAINTAIN maintain on maintain.ID= t.SOURCE_ID ");
		sqlBuffer.append("left join EQM_WHEEL_COVEL_PARAM wcp on wcp.ID= t.SOURCE_ID ");
		sqlBuffer.append("left join SYS_EQP_TYPE et2 on et2.ID=wcp.SET_ID ");
		sqlBuffer.append("where 1=1 ");
		if(StringUtil.notNull(bean.getStartDate())){
			sqlBuffer.append("and CONVERT(varchar(32),t.CREATE_DATE,23)>='"+bean.getStartDate()+"' "); 
		}
		if(StringUtil.notNull(bean.getEndDate())){
			sqlBuffer.append("and CONVERT(varchar(32),t.CREATE_DATE,23)<='"+bean.getEndDate()+"' "); 
		}
		if(StringUtil.notNull(bean.getEqu_id())){
			sqlBuffer.append("and t.EQU_ID='"+bean.getEqu_id()+"' ");
		}
		if(StringUtil.notNull(bean.getShift_id())){
			sqlBuffer.append("and t.SHIFT_ID='"+bean.getShift_id()+"' ");
		}
		sqlBuffer.append("order by t.CREATE_DATE desc ");
		List<TroubleBean> lists=new ArrayList<TroubleBean>();
		List<?> faultWkObj = troubleDao.queryBySql(sqlBuffer.toString());
		for(int i=(pageParams.getPage()-1)*pageParams.getRows();i<pageParams.getPage()*pageParams.getRows()&&i<faultWkObj.size();i++){
			Object o=faultWkObj.get(i);
			//设备名称，工单code,生产日期，班组名称，班次名称，生产牌号，故障名称，故障发生时长，故障发生次数，生产时长
			Object[] arr=(Object[]) o;
			TroubleBean b=new TroubleBean();
			b.setId(StringUtil.convertObjToString(arr[0]));
			b.setEqu_name(StringUtil.convertObjToString(arr[1]));
			b.setName(StringUtil.convertObjToString(arr[2]));
			b.setDes(StringUtil.convertObjToString(arr[3]));
			b.setShift_name(StringUtil.convertObjToString(arr[4]));
			b.setSource(StringUtil.convertObjToString(arr[5]));
			b.setCreate_date(StringUtil.convertObjToString(arr[6]));
			if(arr[7]!=null){
				b.setSource_name(StringUtil.convertObjToString(arr[7]));
			}
			if(arr[8]!=null){
				b.setSource_name(StringUtil.convertObjToString(arr[8]));
			}
			if(arr[9]!=null){
				b.setSource_name(StringUtil.convertObjToString(arr[9]));
			}
			b.setCreate_user_name(StringUtil.convertObjToString(arr[10]));
			lists.add(b);
		}
		return new DataGrid(lists,Long.parseLong(lists.size()+""));
	}

	@Override
	public DataGrid queryTrouble(EqmTroubleBean eqmtroubleBean, PageParams pageParams) throws Exception {
		/*StringBuilder hql=new StringBuilder();
		hql.append(" from EqmTrouble t where 1=1 and t.del='0' ");
		if(StringUtil.notNull(eqmtroubleBean.getMdWorkshopId()) )
			hql.append(" and t.mdEquipment.mdWorkshop.id='"+eqmtroubleBean.getMdWorkshopId()+"' ");
		if(StringUtil.notNull(eqmtroubleBean.getMdEqpCategoryId()))
			hql.append(" and t.mdEquipment.mdEqpType.mdEqpCategory.id='"+eqmtroubleBean.getMdEqpCategoryId()+"' ");
		if(StringUtil.notNull(eqmtroubleBean.getMdEqpTypeId()))
			hql.append(" and t.mdEquipment.mdEqpType.id='"+eqmtroubleBean.getMdEqpTypeId()+"' ");
		if(StringUtil.notNull(eqmtroubleBean.getMdEquipmentId()))
			hql.append(" and t.mdEquipment.id='"+eqmtroubleBean.getMdEquipmentId()+"' ");
		if(StringUtil.notNull(eqmtroubleBean.getMdShiftId()))
			hql.append(" and t.mdShift.id='"+eqmtroubleBean.getMdShiftId()+"' ");
		hql.append(StringUtil.fmtDateBetweenParams("t.fault_date", eqmtroubleBean.getRuntime(), eqmtroubleBean.getEndtime()));	
		List<EqmTrouble> troubles=eqmtroubleDaoI.queryByPage(hql.toString(), pageParams);
		List<EqmTroubleBean> eqmTroubleBeans=new ArrayList<EqmTroubleBean>();
		for (EqmTrouble eqmTrouble : troubles) {
			EqmTroubleBean bean=BeanConvertor.copyProperties(eqmTrouble, EqmTroubleBean.class);
			if(eqmTrouble.getMdShift()!=null){
				bean.setMdShiftId(eqmTrouble.getMdShift().getId());
				bean.setMdShiftName(eqmTrouble.getMdShift().getName());
			}
			if(eqmTrouble.getMdEquipment()!=null){
				bean.setMdEquipmentId(eqmTrouble.getMdEquipment().getId());
				bean.setMdEquipmentName(eqmTrouble.getMdEquipment().getEquipmentName());
			}
			bean.setFaultDate(DateUtil.formatDateToString(eqmTrouble.getFault_date(),"yyyy-MM-dd"));
			eqmTroubleBeans.add(bean);
		}
		long total=eqmtroubleDaoI.queryTotal("select count(*) "+hql.toString());
		return new DataGrid(eqmTroubleBeans, total);*/
		return null;
	}
	
	/**
	 * 张璐
	 * 设备信息表查询--测试用
	 * @return
	 */
	@Override
	public List<EqmTroubleInfoBean> queryTroubleInfo(EqmTroubleInfoBean troubleBean){
		/*StringBuffer sb=new StringBuffer();
		sb.append("SELECT et.id,et.md_eqp_type_id,et.trouble_part_description,et.part_code_description,et.trouble_phenomenon_description,et.trouble_reason_description,et.trouble_code,et.trouble_phenomenon,et.trouble_reason from EQM_TROUBLE_INFO et where 1=1 ");
		if(StringUtil.notNull(troubleBean.getTroubleName())){
			sb.append(" and et.trouble_reason_description='"+troubleBean.getTroubleName()+"'");
		}
		List<?> list = eqmtroubleDaoI.queryBySql(sb.toString());
		List<EqmTroubleInfoBean> listBean = new ArrayList<EqmTroubleInfoBean>();
		if(list.size()>0){
			for(Object b:list){
				Object[]temp = (Object[]) b;
				EqmTroubleInfoBean bean = new EqmTroubleInfoBean();
				bean.setId(temp[0].toString());
				if(null!=temp[1]){
					bean.setEqp_DES(temp[1].toString());
				}
				bean.setTrouble_part_description(temp[2].toString());
				bean.setPart_code_description(temp[3].toString());
				bean.setTrouble_phenomenon_description(temp[4].toString());
				bean.setTrouble_reason_description(temp[5].toString());
				bean.setTrouble_code(temp[6].toString());
				bean.setTrouble_phenomenon((int) temp[7]);
				bean.setTrouble_reason(temp[8].toString());
				listBean.add(bean);
			}
		}
		return listBean;*/
		
		StringBuffer sb=new StringBuffer(1000);
		sb.append("SELECT id,code,description,type,parent_id from EQM_TROUBLE_INFO  where 1=1 ");
		if(StringUtil.notNull(troubleBean.getParent_id())){
			sb.append(" and parent_id='"+troubleBean.getParent_id()+"'");
		}
		sb.append(" ORDER BY type");
		
		List<?> list = eqmtroubleDaoI.queryBySql(sb.toString());
		List<EqmTroubleInfoBean> listBean = new ArrayList<EqmTroubleInfoBean>();
		if(list.size()>0){
			for(Object b:list){
				Object[]temp = (Object[]) b;
				EqmTroubleInfoBean bean = new EqmTroubleInfoBean();
				bean.setId(temp[0].toString());
				bean.setCode(temp[1].toString());
				bean.setDescription(temp[2].toString());
				bean.setType(temp[3].toString());
				if(temp[4]!=null){
					bean.setParent_id(temp[4].toString());
				}
				listBean.add(bean);
			}
		}
		return listBean;
	}
	
	/**张璐2015-10-30
	 * 用于添加新的故障信息
	 * @param des--故障描述
	 * @param fiveCode--本级代码
	 * @param pid--父节点
	 */
	@Override
	public void addNewTrouble(String des,String fiveCode,String pid,String id){
		StringBuffer sb=new StringBuffer(1000);
		sb.append("INSERT into EQM_TROUBLE_INFO VALUES('"+id+"','"+fiveCode+"','"+des+"',5,'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"','"+pid+"')");
		eqmtroubleDaoI.updateBySql(sb.toString(), null);
	}
	
	/**
	 * 删除新添加的故障
	 * 张璐
	 */
	@Override
	public void deleteNewTrouble(String id){
		StringBuffer sb=new StringBuffer(1000);
		sb.append("DELETE from EQM_TROUBLE_INFO where id='"+id+"' ");
		eqmtroubleDaoI.updateBySql(sb.toString(), null);
	}
}
