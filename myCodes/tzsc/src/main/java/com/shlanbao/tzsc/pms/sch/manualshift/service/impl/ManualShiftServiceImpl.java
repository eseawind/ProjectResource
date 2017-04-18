package com.shlanbao.tzsc.pms.sch.manualshift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdManualShiftDaoI;
import com.shlanbao.tzsc.base.mapping.MdManualShift;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.manualshift.service.ManualShiftServiceI;

@Service
public class ManualShiftServiceImpl extends BaseService implements ManualShiftServiceI{
	@Autowired
	private MdManualShiftDaoI mdManualShiftDao;
	/**
	 * 查询当前运行工单 确定当前班次
	 * @throws Exception
	 */
	@Override
	public MdManualShift getManualShift(String eqpId,String state) throws Exception{
		    
		StringBuffer sql = new StringBuffer();
		sql.append(" select ID,EQPID,DASPROCESS,PMSPROCESS,NOTE,SHIFTTIME,WORK_SHOP,ADD_USER_ID,ADD_TIME,");//8个
		sql.append(" MODIFY_USER_ID,MODIFY_TIME,PRO_WORK_ID,PMV_MSG,PMV1_MSG,PMV2_MSG,TEAM_CODE,SHIFT_CODE, ");//9个
		sql.append(" PMV_START_MSG,PMV_TIME,PMV1_TIME,PMV2_TIME,PMV_START_TIME,WORK_TYPE, ");//6个
		sql.append(" PMV_START_FL_MSG,PMV_FL_MSG,PMV1_FL_MSG,PMV2_FL_MSG ");//4个
		sql.append(" from MD_MANUAL_SHIFT WITH (NOLOCK) where EQPID =? ");
		List<Object> params = new ArrayList<Object>();
		params.add(eqpId);
		if(null!=state&&!"".equals(state)){
			sql.append(" and STATE=? ");
			params.add(state);
		}
		List<?> list = mdManualShiftDao.queryBySql(sql.toString(), params);
		MdManualShift shift = null;
		if(null!=list&&list.size()>0){
			shift = new MdManualShift();
			Object[] arr=(Object[]) list.get(0);
			shift.setId(Integer.parseInt(ObjectUtils.toString(arr[0])));
			shift.setEqpid(ObjectUtils.toString(arr[1]));
			shift.setDasprocess(Integer.parseInt(ObjectUtils.toString(arr[2])));
			shift.setPmsprocess(Integer.parseInt(ObjectUtils.toString(arr[3])));
			shift.setNote(ObjectUtils.toString(arr[4]));
			shift.setWorkShop(ObjectUtils.toString(arr[6]));
			shift.setAddUserId(ObjectUtils.toString(arr[7]));
			shift.setModifyUserId(ObjectUtils.toString(arr[9]));
			shift.setProWorkId(ObjectUtils.toString(arr[11]));
			shift.setPmvMsg(ObjectUtils.toString(arr[12]));
			shift.setPmv1Msg(ObjectUtils.toString(arr[13]));
			shift.setPmv2Msg(ObjectUtils.toString(arr[14]));
			shift.setTeamCode(ObjectUtils.toString(arr[15]));
			shift.setShiftCode(ObjectUtils.toString(arr[16]));
			shift.setPmvStartMsg(ObjectUtils.toString(arr[17]));
			shift.setPmvTime(ObjectUtils.toString(arr[18]));
			shift.setPmv1Time(ObjectUtils.toString(arr[19]));
			shift.setPmv2Time(ObjectUtils.toString(arr[20]));
			shift.setPmvStartTime(ObjectUtils.toString(arr[21]));
			shift.setWorkType(ObjectUtils.toString(arr[22]));
			shift.setPmvStartFlMsg(ObjectUtils.toString(arr[23]));
			shift.setPmvFlMsg(ObjectUtils.toString(arr[24]));
			shift.setPmv1FlMsg(ObjectUtils.toString(arr[25]));
			shift.setPmv2FlMsg(ObjectUtils.toString(arr[26]));
		}
		return shift;
	}
	/**
	 * 根据sql语句更新
	 */
	public void updateInfo(String sql,List<Object> obj) throws Exception{
		mdManualShiftDao.updateInfo(sql,obj);
	}

}
