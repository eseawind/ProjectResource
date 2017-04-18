package com.lanbao.dws.service.wct.eqpManager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.model.wct.eqpManager.EqpOperatingEfficBean;
import com.lanbao.dws.service.wct.eqpManager.IEqpEfficService;

@Service
public class EqpEfficServiceImpl implements IEqpEfficService {
	@Autowired
	IPaginationDalClient dalClient;
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月14日 上午11:08:42 
	* 功能说明 ：查询设备运行效率
	 */
	@Override
	public void queryEqpOperatingEffic(Pagination pagination,
			EqpOperatingEfficBean bean, Model model) {
		PaginationResult<List<EqpOperatingEfficBean>> result=dalClient.queryForList("eqpEffic.eqpOreratingEffic",bean,EqpOperatingEfficBean.class, pagination);
		List<EqpOperatingEfficBean> beans=result.getR();
		EqpOperatingEfficBean efficBean=new EqpOperatingEfficBean();
		//x轴数据
		String[] xTextArray=efficBean.getxText();
		//y轴数据
		Float[] yDataArray=efficBean.getyVal();
		
		EqpOperatingEfficBean eqpOperatingEfficBean=null;
		StringBuffer xText=new StringBuffer();
		for (int i=0;i<beans.size();i++) {
			eqpOperatingEfficBean=beans.get(i);
			//运行效率
			yDataArray[i]=calcEqpOretatingEffic(eqpOperatingEfficBean.getpRuntime(),eqpOperatingEfficBean.getRunTime());
			xText.append(eqpOperatingEfficBean.getEqpName());//设备名称
			xText.append("。");//换行
			xText.append(eqpOperatingEfficBean.getEqpCaregoryName());
			//x轴文字显示
			xTextArray[i]=xText.toString();
			xText.setLength(0);
		}
		model.addAttribute("beanData", efficBean);
	}

	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月14日 上午11:31:53 
	* 功能说明 ：计算设备运行效率，如果有异常数据默认为99.9
	* @param	pStime	计划开始时间
	* @param	pEtime	计划结束时间
	* @param	runTime	实际运行时长
	 */
	private Float calcEqpOretatingEffic(Float pRuntime,Float runTime){
		Float result=9.9F;
		if(pRuntime>0){
			result=(float) (MathUtil.roundHalfUp(Double.parseDouble(runTime.toString())/pRuntime, 2) * 100);
		}
		return result;
	}

	/**
	 * <p>功能描述设备有效作业率：</p>
	 *@see com.lanbao.dws.service.wct.eqpManager.IEqpEfficService#queryEffectiveOperatingEffic(com.ibm.framework.dal.pagination.Pagination, com.lanbao.dws.model.wct.eqpManager.EqpOperatingEfficBean, org.springframework.ui.Model)
	 *shisihai
	 *2016上午11:06:09
	 */
	@Override
	public void queryEffectiveOperatingEffic(Pagination pagination, EqpOperatingEfficBean bean, Model model) {
		PaginationResult<List<EqpOperatingEfficBean>> result=dalClient.queryForList("eqpEffic.effectiveOperatingEffic",bean,EqpOperatingEfficBean.class, pagination);
		List<EqpOperatingEfficBean> beans=result.getR();
		EqpOperatingEfficBean efficBean=new EqpOperatingEfficBean();
		//x轴数据
		String[] xTextArray=efficBean.getxText();
		//y轴数据
		Float[] yDataArray=efficBean.getyVal();
		
		EqpOperatingEfficBean eqpOperatingEfficBean=null;
		StringBuffer xText=new StringBuffer();
		for (int i=0;i<beans.size();i++) {
			eqpOperatingEfficBean=beans.get(i);
			//设备有效作业率
			yDataArray[i]=(float) calcEqpOretatingEffic(eqpOperatingEfficBean);
			xText.append(eqpOperatingEfficBean.getEqpName());//设备名称
			xText.append("。");//换行
			xText.append(eqpOperatingEfficBean.getTeamName());
			xText.append(eqpOperatingEfficBean.getShiftName());
			//x轴文字显示
			xTextArray[i]=xText.toString();
			xText.setLength(0);
		}
		model.addAttribute("beanData", efficBean);
		
	}
	
	private double calcEqpOretatingEffic(EqpOperatingEfficBean eqpOperatingEfficBean){
		double result=0F;
		double qty=eqpOperatingEfficBean.getrQty();//实际产量
		double wbTime=eqpOperatingEfficBean.getpMaintenanceTime();//维保时长
		double pRuntime=eqpOperatingEfficBean.getpRuntime();//计划开机时长
		double yid=eqpOperatingEfficBean.getYie();//额定产能
		double kickOutTime=eqpOperatingEfficBean.getKickOutTime();//剔除时间（包括吃饭）
		double flag=(pRuntime-wbTime-kickOutTime)*yid;
		if(flag>0){
			result=(float) (qty/flag);
		}
		result=MathUtil.roundHalfUp(result, 2);
		return result;
	}
}
