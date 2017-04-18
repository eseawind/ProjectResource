package com.shlanbao.tzsc.utils.thread;

import java.util.List;





//import com.lanbao.comm.modbus.tcp.slave.DataPostListener;
import com.lanbao.dac.data.CommonData;
//import com.lanbao.dac.data.DataSnapshot;
//import com.lanbao.dac.data.EquipmentData;
import com.serotonin.modbus4j.BasicProcessImage;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.shlanbao.tzsc.data.modbus.tcp.slave.DataPostListener;
import com.shlanbao.tzsc.data.runtime.bean.DataSnapshot;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;

/////////////////////////////////////////////////////////////////
/////////  Modbus Data UPDATE process	 
/////////  The following code is integrated from GZDAMS by tuxiao
/////////  Date:  2016-09-09
//////////////////////////////////////////////////////////////////
public class WriteModbusRegister implements Runnable {

	private static WriteModbusRegister instance;

	private WriteModbusRegister() {

	}

	public static WriteModbusRegister getInstance() {
		if (instance == null) {
			instance = new WriteModbusRegister();
		}
		return instance;
	}

	@Override
	@SuppressWarnings("static-access")
	public synchronized void run() {
		// 以下三个变量声明以及后续与这两个变量相关的丑陋代码，传染自DAC，深恶痛绝之！！！ --howie
		boolean isFuliao; // eqpId*1000,特殊处理辅料
		boolean isJieshouji; // eqpId*1000+1，特殊处理提升机
		int dataId; // 特殊处理以上两个变态
		//
		List<EquipmentData> list = null;
		try {
			list = DataSnapshot.getInstance().getEqpData();
			if (list == null) {
			    System.out.println("[info]WriteModbusRegister 从数据快照中未获取到设备数据");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		for (EquipmentData eqpData : list) {
			isFuliao = false;
			isJieshouji = false;
			BasicProcessImage image = null;
			int eqpId = eqpData.getEqp();
			if (eqpId > 999) {
				isFuliao = eqpId % 1000 == 0 ? true : false;
				isJieshouji = !isFuliao;
				eqpId = Math.round(eqpId / 1000);
			}
			try {
				DataPostListener.getInstance();
				image = (BasicProcessImage) DataPostListener
						.getSlaveSet().getProcessImage(eqpId);
				//System.out.println("获取设备ID："+eqpData.getEqp()+"的数据");
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			if (image == null) {
				System.out.println("未初始化的设备ID："+eqpData.getEqp());
				continue;
			}

			List<CommonData> dataList = eqpData.getAllData();
			for (CommonData data : dataList) {
				if (data.getReturnType() != null && data.getVal() != null) {
					dataId = Integer.parseInt(data.getId());
					if (isFuliao) {
						dataId += 999;
					}
					if (isJieshouji) {
						dataId += 1999;
					}
					try {
						if (data.getReturnType().equalsIgnoreCase("int")
								|| data.getReturnType()
										.equalsIgnoreCase("dint")) {
							int val = 0;
							try {
								val = Integer.parseInt(data.getVal());
							} catch (NumberFormatException nfe) {
								val = 0;
							}
							image.setRegister(RegisterRange.INPUT_REGISTER, 
											  dataId*2, 
											  DataType.FOUR_BYTE_INT_SIGNED_SWAPPED, 
											  val);
							//Following is the old code.
							//image.setInputRegister(dataId,DataType.TWO_BYTE_INT_UNSIGNED, (short) val);
						} else if (data.getReturnType().equalsIgnoreCase(
								"float")) {
							float val = 0.00f;
							try {
								val = Float.parseFloat(data.getVal());
							} catch (NumberFormatException nfe) {
								val = 0.00f;
							}
							image.setRegister(RegisterRange.HOLDING_REGISTER, 
									  dataId*2, 
									  DataType.FOUR_BYTE_FLOAT_SWAPPED, 
									  val);							
							//Following is the old code.
							//image.setHoldingRegister(dataId * 2,DataType.FOUR_BYTE_FLOAT_SWAPPED, val);
						} else if (data.getReturnType()
								.equalsIgnoreCase("bool")) {
							image.setInput(dataId,Boolean.parseBoolean(data.getVal()));
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
	}

}
