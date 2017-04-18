package com.shlanbao.tzsc.wct.isp.boxer.service.impl;

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
import com.shlanbao.tzsc.wct.isp.boxer.beans.BoxerData;
import com.shlanbao.tzsc.wct.isp.boxer.service.BoxerIspServiceI;
@Service
public class BoxerIspServiceImpl implements BoxerIspServiceI {
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Override
	public BoxerData initBoxerBaseInfo(String equipmentCode) {
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
		BoxerData boxerData = new BoxerData();
		if(obj!=null){
			Object[] v = (Object[]) obj;
			//工单机台信息
			boxerData.setEquipmentEdcs(v[1].toString());
			boxerData.setWorkorderCode(v[2].toString());
			boxerData.setMatName(v[3].toString());
			boxerData.setPlanQty(Double.valueOf(v[4].toString())/5);
			boxerData.setUnit("箱");
			boxerData.setPlanStim(v[6].toString());
			boxerData.setPlanEtim(v[7].toString());
			boxerData.setBthCode(v[8].toString());
			boxerData.setStim(v[8].toString());
			
		}
		return boxerData;
		/*BoxerData boxerData = new BoxerData("1", 
				"YP18", "7000",
				"C22015010101#010", "白沙（精品）", 150.0,"箱",
				"2015-01-01 07:30:00", "2015-01-01 17:30:00", "C22015010101",
				"2015-01-01 07:35:10");
		return boxerData;*/
	}

	@Override
	public BoxerData getBoxerData(String equipmentCode) {
		Integer online=0; 
		Double qty=0d; 
		Integer speed=0;
		try {
			 int id = Integer.valueOf(equipmentCode);
			 EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
			 if(mainData!=null){
				List<CommonData> datas = mainData.getAllData();
				//网络
				online = mainData.isOnline()?1:0;
				//产量
				qty = MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "1")),2);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BoxerData(online, qty, 0d, 0d, 0, speed);
	}

}
