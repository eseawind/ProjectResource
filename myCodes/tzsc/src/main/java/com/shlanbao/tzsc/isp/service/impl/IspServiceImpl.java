package com.shlanbao.tzsc.isp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.DataSnapshot;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.isp.service.IspServiceI;
@Service
public class IspServiceImpl extends BaseService implements IspServiceI {
	@Override
	public EquipmentData get(String  code) {
		int id = Integer.valueOf(code);
		try {
			for(EquipmentData data : DataSnapshot.getInstance().getEqpData()){
				if (data.getEqp()==id){
					return data;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new EquipmentData(id,"无当前机台数据");
	}

	@Override
	public List<EquipmentData> getAll() {
		return DataSnapshot.getInstance().getEqpData();
	}

	@Override
	public List<EquipmentData> getAllFormatted() {
		return NeedData.getInstance().getEqpData();
	}

	@Override
	public EquipmentData getFormatted(String code) {
		int id = Integer.valueOf(code);
		try {
			for(EquipmentData data : NeedData.getInstance().getEqpData()){
				if (data.getEqp()==id){
					return data;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new EquipmentData(id,"无当前机台数据");

	}
}
