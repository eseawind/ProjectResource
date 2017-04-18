package com.shlanbao.tzsc.wct.isp.roller.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.shlanbao.tzsc.wct.isp.roller.beans.RollerData;
import com.shlanbao.tzsc.wct.isp.roller.service.RollerIspServiceI;
@Service
public class RollerIspServiceImpl implements RollerIspServiceI {
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Override
	public RollerData initRollerBaseInfo(String equipmentCode) {
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
				+ "sw.realStim,"//9
				+ "sw.mdTeam.name,"//10
				+ "sw.mdShift.name,"//11
				+ "sw.mdEquipment.mdEqpType.name "//12
				+ "from SchWorkorder sw "
				+ "where sw.mdEquipment.equipmentCode=? "
				+ "and sw.date='"+MESConvertToJB.rutDateStr()+"' "
				+ "and sw.sts=2";
		Object obj = schWorkorderDao.unique(hql, equipmentCode);
		RollerData rollerData = new RollerData();
		if(obj!=null){
			Object[] v = (Object[]) obj;
			//工单机台信息
			rollerData.setEquipmentEdcs(v[1].toString());
			rollerData.setWorkorderCode(v[2].toString());
			rollerData.setMatName(v[3].toString());
			rollerData.setPlanQty(Double.valueOf(v[4].toString())/5);
			rollerData.setUnit("箱");
			rollerData.setPlanStim(v[6].toString());
			rollerData.setPlanEtim(v[7].toString());
			rollerData.setBthCode(v[8].toString());
			if(v[9]!=null){
				rollerData.setStim(v[9].toString());
			}
			rollerData.setTeamName(v[10].toString());
			rollerData.setShiftName(v[11].toString());
			rollerData.setEquipmentType((v[12].toString()));
			//物料单耗信息
			String workOrderId = v[0].toString();
			//卷烟纸2 这里辅料的code写死了，如果清空过辅料类型表，数据查询将会出错
			hql = "select scs.mdMatByMat.standardVal ,scs.euval,scs.mdMatByMat.id "
					+ " from SchConStd scs where "
					+ " scs.oid=? "
					+ " and scs.mdMatByMat.mdMatType.id=? ";
					/*+ "and (scs.mdMatByMat.id = swb.mdMat.id "
					+ "and swb.schWorkorder.mdMat.id = scs.mdMatByProd.id "
					+ " and scs.oid=? "
					+ "and swb.mdMat.mdMatType.code=?)";*/
			Object std =null;
			std = schWorkorderDao.unique(hql, workOrderId,"2");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					rollerData.setJuanyanzhiBzdh(Double.valueOf(stdres[0].toString()));
				}
				rollerData.setJuanyanzhiEuval(Double.valueOf(stdres[1].toString()));
				rollerData.setJuanyanzhiMatId(stdres[2].toString());
			}
			//水松纸3
			std = schWorkorderDao.unique(hql, workOrderId,"3");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					rollerData.setShuisongzhiBzdh(Double.valueOf(stdres[0].toString()));
				}
				rollerData.setShuisongzhiEuval(Double.valueOf(stdres[1].toString()));
				rollerData.setShuisongzhiMatId(stdres[2].toString());
			}
			//滤棒4
			std = schWorkorderDao.unique(hql, workOrderId,"4");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					rollerData.setLvbangBzdh(Double.valueOf(stdres[0].toString()));
				}
				rollerData.setLvbangEuval(Double.valueOf(stdres[1].toString()));
				rollerData.setLvbangMatId(stdres[2].toString());
			}
			
		}
		return rollerData;
	}

	@Override
	public RollerData getRollerData(String equipmentCode) {
		int id = Integer.valueOf(equipmentCode);
		
		EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
		EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);
		
		Double qty = 0D; 
		//剔除量
		double badQty=0D;
		
		Double runTime = 0D; 
		Double stopTime = 0D;
		Integer stopTimes = 0; 
		Integer speed = 0;
		Integer online = 0;
		//消耗
		Double lvbangVal = 0D; 
		Double juanyanzhiVal = 0D;
		Double shuisongzhiVal = 0D; 
		//单耗排名
		Integer lvbangRank = 0; 
		Integer juanyanzhiRank = 0;
		Integer shuisongzhiRank = 0;
		
		if(mainData!=null){
			List<CommonData> datas = mainData.getAllData();
			//网络
			online = mainData.isOnline()?1:0;
			//车速
			speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "15"));
			//产量
			qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7"))/50,2);
			//剔除
			badQty= MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "11"))/50000,4);
			//运行时间
			runTime = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "16"));
			//停机时间
			try {
				stopTime = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "18"));
			} catch (Exception e) {
				e.printStackTrace();
				//打印日志
				System.out.println("\n\n\n停机时间异常！>>>>"+NeedData.getInstance().getDataValue(datas, "18")+">>>"+mainData.getEqp()+">>>"+mainData.getName()+">>>"+mainData.getType()+">>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
			
			//停机次数
			stopTimes = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "19"));
			//add by luhter.zhang 20150413
			lvbangVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "8"));
			juanyanzhiVal =  GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "21"));
			shuisongzhiVal =   GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "22"));
			//end
		}
		if(flData!=null){
			List<CommonData> datas = flData.getAllData();
			lvbangVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4"));
			juanyanzhiVal =  GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "2"));
			shuisongzhiVal =   GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3"));
		}
		
		return new RollerData(qty,badQty, lvbangVal, juanyanzhiVal,
				shuisongzhiVal, lvbangRank, juanyanzhiRank,
				shuisongzhiRank, runTime, stopTime,
				stopTimes, speed,online);
	}

}
