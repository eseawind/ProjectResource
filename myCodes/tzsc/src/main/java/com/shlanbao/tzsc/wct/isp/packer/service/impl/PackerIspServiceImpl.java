package com.shlanbao.tzsc.wct.isp.packer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.isp.packer.beans.PackerData;
import com.shlanbao.tzsc.wct.isp.packer.service.PackerIspServiceI;

/**
 * 【功能说明】：dac采集 推送数据给服务器，服务器解析数据，
 *          然后存于缓存中，该类作用是可实时：获取缓存中的数据
 *  @author wchuang
 *  @time 2015年7月30日15:55:25
 *  
 *  //代码说明：
 *  EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);//主数据 是根据设备ID 来的。 卷烟机辅料数据和主数据在一起。
 *	EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);//辅料数据。 DAC组装数据的时候 设备id*1000所以，PMS这样取
 * 
 * */


@Service
public class PackerIspServiceImpl extends BaseService implements PackerIspServiceI {
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Override
	public PackerData initPackerBaseInfo(String equipmentCode) {
		//根据当前时间，判断工单日期
		
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
		PackerData packerData = new PackerData();
		if(obj!=null){
			Object[] v = (Object[]) obj;
			//工单机台信息
			packerData.setEquipmentEdcs(v[1].toString());
			packerData.setWorkorderCode(v[2].toString());
			packerData.setMatName(v[3].toString());
			packerData.setPlanQty(Double.valueOf(v[4].toString())/5);
			packerData.setUnit("箱");
			packerData.setPlanStim(v[6].toString());
			packerData.setPlanEtim(v[7].toString());
			packerData.setBthCode(v[8].toString());
			packerData.setStim(v[9].toString());
			//物料单耗信息
			String workOrderId = v[0].toString();
			/*hql = "select scs.val,scs.euval,swb.mdMat.id "//添加了辅料的id
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
			//5	            小盒烟膜
			std = schWorkorderDao.unique(hql, workOrderId,"5");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					packerData.setXiaohemoBzdh(Double.valueOf(stdres[0].toString()));
				}
				packerData.setXiaohemoEuval(Double.valueOf(stdres[1].toString()));
				packerData.setXiaohemoMatId(stdres[2].toString());
			}
			//6	            条盒烟膜
			std = schWorkorderDao.unique(hql, workOrderId,"6");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					packerData.setTiaohemoBzdh(Double.valueOf(stdres[0].toString()));
				}
				packerData.setTiaohemoEuval(Double.valueOf(stdres[1].toString()));
				packerData.setTiaohemoMatId(stdres[2].toString());
			}
			//7	            小盒纸
			std = schWorkorderDao.unique(hql, workOrderId,"7");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					packerData.setXiaohezhiBzdh(Double.valueOf(stdres[0].toString()));
				}
				packerData.setXiaohezhiEuval(Double.valueOf(stdres[1].toString()));
				packerData.setXiaohezhMatId(stdres[2].toString());
			}
			//8	            条盒纸
			std = schWorkorderDao.unique(hql, workOrderId,"8");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					packerData.setTiaohezhiBzdh(Double.valueOf(stdres[0].toString()));
				}
				packerData.setTiaohezhiEuval(Double.valueOf(stdres[1].toString()));
				packerData.setTiaohezhMatId(stdres[2].toString());
				
				
			}
			//9	            内衬纸
			std = schWorkorderDao.unique(hql, workOrderId,"9");
			if(std!=null){
				Object[] stdres = (Object[]) std;
				if(stdres[0]!=null){
					packerData.setNeichenzhiBzdh(Double.valueOf(stdres[0].toString()));
				}
				packerData.setNeichenzhiEuval(Double.valueOf(stdres[1].toString()));
				packerData.setNeichenzMatId(stdres[2].toString());
				
			}
			
			
		}
		/*
		 * 测试数据
		 * String equipmentType = "ZB45", 
				equipmentEdcs = "400", 
				workorderCode = "P22015010401#020", 
				matName = "白沙精品"; 
				
				Double planQty = 50D;
				
				String unit = "箱", 
				planStim = "2015-01-01 07:30:00", 
				planEtim = "2015-01-01 17:30:00", 
				bthCode = "P22015010401", 
				stim = "2015-01-01 07:30:00"; 
				
				Double  xiaohemoBzdh = 2.5D, 
						tiaohemoBzdh = 1.65, 
						xiaohezhiBzdh = 2500D, 
						tiaohezhiBzdh = 250D, 
						neichenzhiBzdh = 1.65, 
						xiaohemoEuval = 3.6, 
						tiaohemoEuval = 3.6,
						xiaohezhiEuval = 2550D, 
						tiaohezhiEuval = 260D, 
						neichenzhiEuval = 1.6;
				
				return new PackerData(equipmentCode, equipmentType, equipmentEdcs, workorderCode, matName, planQty, unit, planStim, planEtim, bthCode, stim, xiaohemoBzdh, tiaohemoBzdh, xiaohezhiBzdh, tiaohezhiBzdh, neichenzhiBzdh, xiaohemoEuval, tiaohemoEuval, xiaohezhiEuval, tiaohezhiEuval, neichenzhiEuval);
			*/
		return packerData;
	}

	@Override
	public PackerData getPackerData(String equipmentCode) {
		int id = Integer.valueOf(equipmentCode);
		
		EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
		EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);
		EquipmentData TSData = NeedData.getInstance().getEquipmentData(SysEqpTypeBase.TYTS_CODE);
		//网络
		Integer online = 0;
		//停机次数
		Integer stopTimes = 0;
		//产量
		Double qty = 0D;
		//剔除量
		double badQty=0D;
		//车速
		Integer speed = 0; 
		//运行时间
		Double runTime =0D; 
		//停机时间
		Double stopTime = 0D;
		//条烟提升机
		Double TSQty=0D;
		//消耗排名数据(小盒膜，条盒膜，小盒纸，条盒纸，内衬纸)
		Integer xiaohemoRank = 0; 
		Integer tiaohemoRank = 0;
		Integer xiaohezhiRank = 0; 
		Integer tiaohezhiRank = 0; 
		Integer neichenzhiRank = 0;
		//消耗数据(小盒膜，条盒膜，小盒纸，条盒纸，内衬纸)
		Double xiaohemoVal = 0D;
		Double tiaohemoVal = 0D;
		Double xiaohezhiVal = 0D; 
		Double tiaohezhiVal = 0D;
		Double neichenzhiVal = 0D; 
		
		if(mainData!=null){
			List<CommonData> datas = mainData.getAllData();
			//网络
			online = mainData.isOnline()?1:0;
			//车速
			speed = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "2"));
			//产量
			qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3"))/2500,2);
			//剔除量
			badQty=MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4"))/2500,2);
			//运行时间
			runTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5"))/60,2);
			//停机时间
			stopTime = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "6"))/60,2);
			//停机次数
			stopTimes = GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(datas, "7"));
		}
		if(flData!=null){
			List<CommonData> datas = flData.getAllData();
			neichenzhiVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1"));
			xiaohezhiVal =  GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "3"));
			xiaohemoVal =   GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "4"));
			tiaohezhiVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "5"));
			tiaohemoVal = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "6"));  
		}
		if(TSData!=null){
			if(StringUtil.isInteger(String.valueOf(id))){
				Integer tf=id-30;
				TSQty=GetValueUtil.getDoubleValue(TSData.getVal(tf.toString()));
			}
		}
		return new PackerData(online, qty,badQty, xiaohemoVal, tiaohemoVal, xiaohezhiVal, tiaohezhiVal, neichenzhiVal, xiaohemoRank, tiaohemoRank, xiaohezhiRank, tiaohezhiRank, neichenzhiRank, runTime, stopTime, stopTimes, speed,TSQty);
	
	}

}
