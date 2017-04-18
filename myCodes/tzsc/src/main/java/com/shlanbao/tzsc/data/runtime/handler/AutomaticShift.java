package com.shlanbao.tzsc.data.runtime.handler;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.base.mapping.ChangeShiftDatas;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;

/**
 * 【功能描述】： 定时器启动自动换班逻辑
 * 
 * @createTime 2015年10月20日16:33:52
 * @author wanchanghuang
 */
public class AutomaticShift{
	@Autowired
	private WorkOrderServiceI workOrderService;
	private static Logger log = Logger.getLogger(AutomaticShift.class);
	public AutomaticShift() {}
	private static AutomaticShift instance = null;
	
	public static AutomaticShift getInstance(){
		if (instance == null){
			instance = new AutomaticShift();
		}
		return instance;
	}
	
	//定时器启动换班方法
	public void updateShiftData(){
		try {
			workOrderService.updateShiftData();
		} catch (Exception e) {
			System.out.println("定时器自动换班错误！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询上一班次采集数据详细
	 * @createTime 2015年10月26日08:35:09
	 * @author wanchanghuang
	 * @return 
	 * */
	public Map<Integer,List<ChangeShiftDatas>> getMapChangeShiftDates(ChangeShiftDatas csd){
		if(null==workOrderService){
			workOrderService = ApplicationContextUtil.getBean(WorkOrderServiceI.class);	
		}
		Map<Integer,List<ChangeShiftDatas>> list = workOrderService.getMapChangeShiftDates(csd);
		NeedData.getInstance().setMapShiftDatas(list); //全局赋值
		return list;
	}
	
}



