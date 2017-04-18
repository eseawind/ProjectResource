package com.lanbao.dws.common.data.realTimeData;

import org.apache.log4j.Logger;

import com.lanbao.dws.common.tools.ApplicationContextUtil;
import com.lanbao.dws.service.wct.pdDisplay.IPdDisplayService;

/**
 * [说明]保存快照信息
 * 
 * */
public class SaveLastSnapshotDataContorller {
	
	private Logger log = Logger.getLogger(this.getClass());
	private static IPdDisplayService ipdDisplaySevice=null;
	
	
	public void saveLastSnapshotDatas(String chgShift, int equipmentID){
		try {
			if(ipdDisplaySevice==null){
				ipdDisplaySevice=ApplicationContextUtil.getBean(IPdDisplayService.class);
			}
			ipdDisplaySevice.saveLastSnapshotDatas(chgShift,equipmentID);
		} catch (Exception e) {
			log.info("DAC发送换班指令，数采处理逻辑错误！");
			e.printStackTrace();
		}
	}
	
	
}
