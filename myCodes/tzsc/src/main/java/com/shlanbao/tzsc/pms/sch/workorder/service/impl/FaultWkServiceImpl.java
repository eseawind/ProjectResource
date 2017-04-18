package com.shlanbao.tzsc.pms.sch.workorder.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.workorder.beans.FaultWkBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.FaultWkServiceI;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: FaultWkServiceI 
* @Description: 设备运行故障信息 
* @author luo 
* @date 2015年10月10日 上午10:05:39 
*
 */
@Service
public class FaultWkServiceImpl extends BaseService implements FaultWkServiceI{
	@Autowired
	private BaseDaoI<Object> baseDao;

	@Override
	public DataGrid queryFaultWkGrid(FaultWkBean bean,PageParams pageParams) {
		String sql=getFaultWkSql(bean);
		List<FaultWkBean> faultWkList=new ArrayList<FaultWkBean>();
		List<?> faultWkObj = baseDao.queryBySql(sql);
		for(int i=(pageParams.getPage()-1)*pageParams.getRows();i<pageParams.getPage()*pageParams.getRows()&&i<faultWkObj.size();i++){
			Object o=faultWkObj.get(i);
			//设备名称，工单code,生产日期，班组名称，班次名称，生产牌号，故障名称，故障发生时长，故障发生次数，生产时长
			Object[] arr=(Object[]) o;
			FaultWkBean b=new FaultWkBean();
			b.setEqp_name(StringUtil.convertObjToString(arr[0]));
			b.setWk_code(StringUtil.convertObjToString(arr[1]));
			b.setDate(StringUtil.convertObjToString(arr[2]));
			b.setTeam_name(StringUtil.convertObjToString(arr[3]));
			b.setShift_name(StringUtil.convertObjToString(arr[4]));
			b.setMat_name(StringUtil.convertObjToString(arr[5]));
			b.setFault_name(StringUtil.convertObjToString(arr[6]));
			String time=StringUtil.convertObjToString(arr[7]);
			if(StringUtil.isDouble(time)){
				double tim=MathUtil.roundHalfUp(Double.parseDouble(time), 2);
				b.setTime(String.valueOf(tim));
			}else{
				b.setTime(StringUtil.convertObjToString(arr[7]));
			}
			
			b.setTimes(StringUtil.convertObjToString(arr[8]));
			b.setRuntime(StringUtil.convertObjToString(arr[9]));
			faultWkList.add(b);
		}
		return new DataGrid(faultWkList,Long.parseLong(faultWkObj.size()+""));
	}
	@Override
	public List<FaultWkBean> exportFaultWkList(FaultWkBean bean){
		String sql=getFaultWkSql(bean);
		List<FaultWkBean> faultWkList=new ArrayList<FaultWkBean>();
		List<?> faultWkObj = baseDao.queryBySql(sql);
		for(Object o:faultWkObj){
			//设备名称，工单code,生产日期，班组名称，班次名称，生产牌号，故障名称，故障发生时长，故障发生次数，生产时长
			Object[] arr=(Object[]) o;
			FaultWkBean b=new FaultWkBean();
			b.setEqp_name(StringUtil.convertObjToString(arr[0]));
			b.setWk_code(StringUtil.convertObjToString(arr[1]));
			b.setDate(StringUtil.convertObjToString(arr[2]));
			b.setTeam_name(StringUtil.convertObjToString(arr[3]));
			b.setShift_name(StringUtil.convertObjToString(arr[4]));
			b.setMat_name(StringUtil.convertObjToString(arr[5]));
			b.setFault_name(StringUtil.convertObjToString(arr[6]));
			b.setTime(StringUtil.convertObjToString(arr[7]));
			b.setTimes(StringUtil.convertObjToString(arr[8]));
			b.setRuntime(StringUtil.convertObjToString(arr[9]));
			faultWkList.add(b);
		}
		return faultWkList;
	}
	
	
	private String getFaultWkSql(FaultWkBean bean){
		StringBuffer sqlBuffer=new StringBuffer();
		//设备名称，工单code,生产日期，班组名称，班次名称，生产牌号，故障名称，故障发生时长，故障发生次数，生产时长
		sqlBuffer.append("select e.EQUIPMENT_NAME,wo.CODE,CONVERT(varchar(32),wo.DATE,23) as date, ");
		sqlBuffer.append("team.NAME as teamName, ");
		sqlBuffer.append("shift.NAME as shiftName,mat.NAME as matName,fault.NAME as faultName, ");
		sqlBuffer.append("fault.TIME,fault.TIMES, ");
		sqlBuffer.append("sso.RUN_TIME   ");
		sqlBuffer.append("from SCH_WORKORDER wo left join SCH_STAT_OUTPUT sso on wo.ID=sso.OID  ");
		sqlBuffer.append("left join SCH_STAT_FAULT fault on sso.ID=fault.OID ");
		sqlBuffer.append("left join MD_EQUIPMENT e on wo.EQP=e.ID   ");
		sqlBuffer.append("left join MD_EQP_TYPE etype on e.EQP_TYPE_ID=etype.ID ");
		sqlBuffer.append("left join MD_EQP_CATEGORY cate on cate.ID=etype.CID ");
		sqlBuffer.append("left join MD_TEAM team on wo.TEAM=team.ID  ");
		sqlBuffer.append("left join MD_SHIFT shift on wo.SHIFT=shift.ID   ");
		sqlBuffer.append("left join MD_MAT mat on wo.MAT=mat.ID  ");
		sqlBuffer.append("where fault.NAME is not null  ");
		if(StringUtil.notNull(bean.getWorkShop_id())){
			sqlBuffer.append("and e.WORK_SHOP='"+bean.getWorkShop_id()+"' ");
		}
		if(StringUtil.notNull(bean.getEqp_type_cate_id())){
			sqlBuffer.append("and cate.ID='"+bean.getEqp_type_cate_id()+"' ");
		}
		if(StringUtil.notNull(bean.getStartDate())){
			sqlBuffer.append("and CONVERT(varchar(32),wo.DATE,23)>='"+bean.getStartDate()+"' "); 
		}
		if(StringUtil.notNull(bean.getEndDate())){
			sqlBuffer.append("and CONVERT(varchar(32),wo.DATE,23)<='"+bean.getEndDate()+"' "); 
		}
		if(StringUtil.notNull(bean.getEqp_type_id())){
			sqlBuffer.append("and e.EQP_TYPE_ID='"+bean.getEqp_type_id()+"' ");
		}
		if(StringUtil.notNull(bean.getEqp_id())){
			sqlBuffer.append("and wo.EQP='"+bean.getEqp_id()+"' ");
		}
		if(StringUtil.notNull(bean.getShift_id())){
			sqlBuffer.append("and wo.SHIFT='"+bean.getShift_id()+"' "); 
		}
		sqlBuffer.append("order by wo.CODE,CONVERT(varchar(32),wo.DATE,23),fault.TIME desc "); 
		return sqlBuffer.toString();
	}
}
