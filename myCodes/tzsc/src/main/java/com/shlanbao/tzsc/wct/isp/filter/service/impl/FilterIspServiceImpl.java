package com.shlanbao.tzsc.wct.isp.filter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.isp.filter.beans.FilterData;
import com.shlanbao.tzsc.wct.isp.filter.service.FilterIspServiceI;
@Service
public class FilterIspServiceImpl implements FilterIspServiceI {
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Override
	public FilterData initFilterBaseInfo(String equipmentCode) {
		String hql = "select "
				+ "sw.id,"//0
				+ "sw.mdEquipment.ratedSpeed,"//1
				+ "sw.code,"//2
				+ "sw.mdMat.simpleName,"//3
				+ "sw.qty,"//4
				+ "sw.mdUnit.name,"//5
				+ "sw.stim,"//6
				+ "sw.etim,"//7
				+ "sw.bth,"//8
				+ "sw.realStim "//9
				+ "from SchWorkorder sw "
				+ "where sw.mdEquipment.equipmentCode=? "
				+ "and sw.date='"+MESConvertToJB.rutDateStr()+"' "
				+ "and sw.sts=2";
		Object obj = schWorkorderDao.unique(hql, equipmentCode);
		FilterData filterData = new FilterData();
		if(obj!=null){
			Object[] v = (Object[]) obj;
			//工单机台信息
			filterData.setEquipmentEdcs(v[1].toString());
			filterData.setWorkorderCode(v[2].toString());
			filterData.setMatName(v[3].toString());
			filterData.setPlanQty(Double.valueOf(v[4].toString())/5);
			filterData.setUnit("箱");
			filterData.setPlanStim(v[6].toString());
			filterData.setPlanEtim(v[7].toString());
			filterData.setBthCode(v[8].toString());
			filterData.setStim(v[8].toString());
			//物料单耗信息
			String workOrderId = v[0].toString();
			/*hql = "select scs.val,scs.euval "
					+ "from SchWorkorderBom swb,SchConStd scs "
					+ "where swb.schWorkorder.id=? "
					+ "and (scs.mdMatByMat.id = swb.mdMat.id "
					+ "and swb.schWorkorder.mdMat.id = scs.mdMatByProd.id "
					+ " and scs.oid=? "
					+ "and swb.mdMat.mdMatType.code=?)";*/
			hql = "select scs.mdMatByMat.standardVal ,scs.euval,scs.mdMatByMat.id "
					+ " from SchConStd scs where "
					+ " scs.oid=? "
					+ " and scs.mdMatByMat.mdMatType.id=? ";
			Object std =null;
			//13	            滤棒盘纸
			std = schWorkorderDao.unique(hql, workOrderId,"304");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				filterData.setPanzhiBzdh(Double.valueOf(stdres[0].toString()));
				filterData.setPanzhiEuval(Double.valueOf(stdres[1].toString()));
			}
		}
		return filterData;
		/*String  equipmentType="ZL26C", 
				equipmentEdcs="7000", 
				workorderCode="F22015010101#010", 
				matName="自产双喜108mm滤棒"; 
				
				Double planQty=120D;
				
				String unit="万支", 
					planStim = "2015-01-01 07:30:00", 
					planEtim = "2015-01-01 17:30:00", 
					bthCode = "P22015010401", 
					stim = "2015-01-01 07:30:00"; 
				
				
				Double panzhiBzdh=2.5, 
						panzhiEuval=1.65;
				
				return new FilterData(equipmentCode, equipmentType, equipmentEdcs, workorderCode, matName, planQty, unit, planStim, planEtim, bthCode, stim, panzhiBzdh, panzhiEuval);*/
	}

	@Override
	public FilterData getFilterData(String equipmentCode) {
		int id=Integer.valueOf(equipmentCode);
		EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
		EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);
		EquipmentData TSData = NeedData.getInstance().getEquipmentData(SysEqpTypeBase.TYTS_CODE);
		//网络
		Integer runStatus = 0;
		//计划产量
		Double qty = 0D;
		//盘纸消耗
		Double panzhiVal = 0D;
		//盘纸消耗排名
		Integer panzhiRank = 0;
		//机器运行时间
		Double runTime = 0D;
		//机器停机时间
		Double stopTime = 0D;
		//机器停机次数
		Integer stopTimes = 0;
		//机器车速
		Integer speed = 0;
		if(mainData!=null){
			List<CommonData> datas = mainData.getAllData();
			//网络
			runStatus = mainData.isOnline()?1:0;
			//车速
			speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "11"));
			//产量
			qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1")), 2);
			//运行时间
			runTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7")), 2);
			//停机时间
			stopTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8")), 2);
			//停机次数
			stopTimes = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, ""));
			//盘纸消耗
			panzhiVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5"));
		}
		return new FilterData(runStatus, qty, panzhiVal, panzhiRank, runTime, stopTime, stopTimes, speed);
	}

	@Override
	public FilterData getFilterDataCX_FL(String equipmentCode) {
		int id=Integer.valueOf(equipmentCode);
		EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
		EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);
		//网络
		Integer runStatus = 0;
		//计划产量
		Double qty = 0D;
		//盘纸消耗
		Double panzhiVal = 0D;
		//盘纸消耗排名
		Integer panzhiRank = 0;
		//机器运行时间
		Double runTime = 0D;
		//机器停机时间
		Double stopTime = 0D;
		//机器停机次数
		Integer stopTimes = 0;
		//机器车速
		Integer speed = 0;
		if(mainData!=null){
			List<CommonData> datas = mainData.getAllData();
			//网络
			runStatus = mainData.isOnline()?1:0;
			//车速
			speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "1"));
			//运行时间
			runTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7")), 2);
			//停机时间
			stopTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8")), 2);
			//停机次数
			stopTimes = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, ""));
		}
		if(flData!=null){
			List<CommonData> datas=flData.getAllData();
			//盘纸消耗
			panzhiVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1"));
			//产量
			qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2")), 2);
		}
		return new FilterData(runStatus, qty, panzhiVal, panzhiRank, runTime, stopTime, stopTimes, speed);
	}

}
