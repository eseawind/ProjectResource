package com.shlanbao.tzsc.pms.sys.loadjsp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.dao.impl.SysOrganizationResourceDaoImpl;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.pms.sys.loadjsp.beans.JuanBaoBean;
import com.shlanbao.tzsc.pms.sys.loadjsp.service.JuanBaoServiceI;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;


@Service
public class JuanBaoServiceImpl extends BaseService implements JuanBaoServiceI {
	
	public JuanBaoBean getJuanBaoBean(String type,int index,int end){
		JuanBaoBean queryBean = null;
		String[] lables=new String[(end-index)+1];
		Double[] values1 = new Double[(end-index)+1];
		Double[] values2 = new Double[(end-index)+1];
		int s=0;
		if(index>30){
			s=30;
		}else if(index>60){
			s=60;
		}
		for (int i=index;i<(end+1);i++){
			//如果未选定了机台，则X轴机台号，Y轴质量指标合格率平均值
			String mon = (i)+"号机台";
			lables[i-1-s]=mon+"<br>";
			
			int id = (i);
			EquipmentData mainData = NeedData.getInstance().getEquipmentData(id);
			//EquipmentData flData = NeedData.getInstance().getEquipmentData(id*1000);	
			List<CommonData> datas = null;
			double qty = 0;
			if(null!=mainData){
				datas=mainData.getAllData();
				qty = GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(datas, "7"));
			}
			String value1 =qty+"";
			//Double dv1 =Double.parseDouble(value1)/100;
			values1[i-1-s]=Double.parseDouble(value1);//MathUtil.roundHalfUp(dv1, 2);
			
		}
		
		queryBean = new JuanBaoBean(lables,type,values1,values2);
		
		return queryBean;
	}
	
	
}
