package com.lanbao.dws.service.wct.pdStat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.ibm.framework.uts.util.StringUtils;
import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.pdStat.HisQtyStat;
import com.lanbao.dws.model.wct.pdStat.RealTimeFLConsume;
import com.lanbao.dws.model.wct.pdStat.RealTimeFLConsumeShowBean;
import com.lanbao.dws.model.wct.pdStat.RealTimeQty;
import com.lanbao.dws.service.wct.pdStat.IPdStatService;

@Service
public class PdStatServiceImpl implements IPdStatService {
	@Autowired
	IPaginationDalClient dalClient;
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月4日 下午3:04:15 
	* 功能说明 ：查询辅料实时消耗信息
	 */
	@Override
	public PaginationResult<List<RealTimeFLConsumeShowBean>> getRealTimeFLConsumeInfo(RealTimeFLConsume param,
			Pagination pagination) {
		//因为卷烟包装辅料个数不同，按显示4条完整数据，则卷烟机需12条，包装机20条
		String orderType="1";
		if(StringUtils.isEmpty(param.getOrderType())){
			param.setOrderType(orderType);
		}else{
			orderType=param.getOrderType();
		}
		//设置查询每页数据大小
		if(orderType.equals("1")){
			//卷烟机查询12条，实际4条数据
			pagination.setPagesize(12);
		}else if(orderType.equals("2")){
			//包装机查询20条，实际4条
			pagination.setPagesize(20);
		}
		
		PaginationResult<List<RealTimeFLConsume>> datas=dalClient.queryForList("pdStat.queryRealConsumeInfo", param, RealTimeFLConsume.class, pagination);
	
		List<RealTimeFLConsume> listData=datas.getR();
		Map<String,RealTimeFLConsumeShowBean> beansMap=new HashMap<>();
		RealTimeFLConsumeShowBean bean=null;
		boolean flag=false;
		for (RealTimeFLConsume realTimeFLConsume : listData) {
			//使用设备code+班次、班组为key，存放bean
			String key=realTimeFLConsume.getEqpCode()+realTimeFLConsume.getTeamName()+realTimeFLConsume.getShiftName();
			bean=beansMap.get(key);
			//新的数据bean
			if(bean==null){
				bean=new RealTimeFLConsumeShowBean();
				beansMap.put(key, bean);
				flag=true;
			}
			setFLData(bean,realTimeFLConsume,orderType,flag);
		}
		//将map中的数据转成list
		List<RealTimeFLConsumeShowBean> list=new ArrayList<>();
		for (Map.Entry<String, RealTimeFLConsumeShowBean> entry : beansMap.entrySet()) {
			list.add(entry.getValue());
		}
		//重新设置结果页数
		int rows=pagination.getTotalRows();
		if(orderType.equals("1")){
			pagination.setTotalRows(rows%3==0?rows/3:rows/3+1);
		}else if(orderType.equals("2")){
			pagination.setTotalRows(rows%5==0?rows/5:rows/5+1);
		}
		PaginationResult<List<RealTimeFLConsumeShowBean>> finalData=new PaginationResult<>(list,pagination);
		return finalData;
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月5日 下午4:35:40 
	* 功能说明 ：查询历史消耗
	 */
	@Override
	public PaginationResult<List<RealTimeFLConsumeShowBean>> getHisFLConsumeInfo(RealTimeFLConsume param,
			Pagination pagination) {
		//因为卷烟包装辅料个数不同，按显示3条完整数据，则卷烟机需9条，包装机15条
				String orderType="1";
				if(StringUtils.isEmpty(param.getEqpCategory())){
					param.setEqpCategory(orderType);
				}else{
					orderType=param.getEqpCategory();
				}
				//设置查询每页数据大小
				if(orderType.equals("1")){
					//卷烟机查询9条，实际三条数据
					pagination.setPagesize(9);
				}else if(orderType.equals("2")){
					//包装机查询15条，实际3条
					pagination.setPagesize(15);
				}
				
				PaginationResult<List<RealTimeFLConsume>> datas=dalClient.queryForList("pdStat.queryHisConsumeInfo", param, RealTimeFLConsume.class, pagination);
			
				List<RealTimeFLConsume> listData=datas.getR();
				Map<String,RealTimeFLConsumeShowBean> beansMap=new HashMap<>();
				RealTimeFLConsumeShowBean bean=null;
				boolean flag=false;
				for (RealTimeFLConsume realTimeFLConsume : listData) {
					//使用设备code+班次、班组+牌号+日期 为key，存放bean
					String key=realTimeFLConsume.getEqpCode()+realTimeFLConsume.getTeamName()+realTimeFLConsume.getShiftName()+realTimeFLConsume.getMat()+realTimeFLConsume.getpDate();
					bean=beansMap.get(key);
					//新的数据bean
					if(bean==null){
						bean=new RealTimeFLConsumeShowBean();
						beansMap.put(key, bean);
						flag=true;
					}
					setFLData(bean,realTimeFLConsume,orderType,flag);
				}
				//将map中的数据转成list
				List<RealTimeFLConsumeShowBean> list=new ArrayList<>();
				for (Map.Entry<String, RealTimeFLConsumeShowBean> entry : beansMap.entrySet()) {
					list.add(entry.getValue());
				}
				//重新设置结果页数
				int rows=pagination.getTotalRows();
				if(orderType.equals("1")){
					pagination.setTotalRows(rows%3==0?rows/3:rows/3+1);
				}else if(orderType.equals("2")){
					pagination.setTotalRows(rows%5==0?rows/5:rows/5+1);
				}
				PaginationResult<List<RealTimeFLConsumeShowBean>> finalData=new PaginationResult<>(list,pagination);
				return finalData;
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月6日 上午10:00:35 
	* 功能说明 ：查询卷包实时产量（初始化数据）
	 */
	@Override
	public PaginationResult<List<RealTimeQty>> getRealTimeQty(RealTimeQty param, Pagination pagination) {
		//默认选择卷烟机数据
		String orderType="1";
		if(StringUtils.isEmpty(param.getOrderType())){
			param.setOrderType(orderType);
		}else{
			orderType=param.getOrderType();
		}
		return dalClient.queryForList("pdStat.queryRealTimeQty", param, RealTimeQty.class, pagination);
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月6日 下午2:21:24 
	* 功能说明 ：卷包机实时剔除产量查询（初始化）
	 */
	@Override
	public PaginationResult<List<RealTimeQty>> getRealTimeBadQty(RealTimeQty param, Pagination pagination) {
		//默认选择卷烟机数据
		String orderType="1";
		if(StringUtils.isEmpty(param.getOrderType())){
			param.setOrderType(orderType);
		}else{
			orderType=param.getOrderType();
		}
		return dalClient.queryForList("pdStat.queryRealTimeBadQty", param, RealTimeQty.class, pagination);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月6日 下午3:15:14 
	* 功能说明 ：查询历史产量信息
	 */
	@Override
	public void getHisQtyStst(HisQtyStat param, Pagination pagination,Model model) {
			PaginationResult<List<HisQtyStat>> datas=dalClient.queryForList("pdStat.queryHisQty", param, HisQtyStat.class, pagination);
			String[] xDatabeans=new String[]{"","","","",""};
			Double[] yPDatas=new Double[]{0.0,0.0,0.0,0.0,0.0};//计划产量
			Double[] yRDatas=new Double[]{0.0,0.0,0.0,0.0,0.0};//实际产量
			StringBuffer xDatabean=new StringBuffer();
			List<HisQtyStat> databeans=datas.getR();
			HisQtyStat bean=null;
			for (int i = 0; i < databeans.size(); i++) {
				bean=databeans.get(i);
				//x轴显示的文本  时间  机台班次班组
				xDatabean.append(bean.getpDate())
				.append("。")//换行
				.append(bean.getEqpName())
				.append("。")//换行
				.append(bean.getTeamName())
				.append(bean.getShiftName());
				xDatabeans[i]=xDatabean.toString();
				yPDatas[i]=StringUtil.converObj2Double(bean.getPqty());
				yRDatas[i]=StringUtil.converObj2Double(bean.getRealQty());
				xDatabean.setLength(0);
			}
			HisQtyStat dataBean=new HisQtyStat();
			dataBean.setxText(xDatabeans);
			dataBean.setyPData(yPDatas);
			dataBean.setyRData(yRDatas);
			model.addAttribute("beanData", dataBean);
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月6日 下午9:35:06 
	* 功能说明 ：查询历史剔除
	 */
	@Override
	public void getHisBadQtyStst(HisQtyStat param, Pagination pagination, Model model) {
		PaginationResult<List<HisQtyStat>> datas=dalClient.queryForList("pdStat.queryHisBadQty", param, HisQtyStat.class, pagination);
		String[] xDatabeans=new String[]{"","","","",""};
		Double[] yRDatas=new Double[]{0.0,0.0,0.0,0.0,0.0};//实际产量
		Double[] yBDatas=new Double[]{0.0,0.0,0.0,0.0,0.0};//剔除产量
		StringBuffer xDatabean=new StringBuffer();
		List<HisQtyStat> databeans=datas.getR();
		HisQtyStat bean=null;
		for (int i = 0; i < databeans.size(); i++) {
			bean=databeans.get(i);
			//x轴显示的文本  时间  机台班次班组
			xDatabean.append(bean.getpDate())
			.append("。")//换行
			.append(bean.getEqpName())
			.append("。")//换行
			.append(bean.getTeamName())
			.append(bean.getShiftName());
			xDatabeans[i]=xDatabean.toString();
			yRDatas[i]=StringUtil.converObj2Double(bean.getRealQty());
			yBDatas[i]=StringUtil.converObj2Double(bean.getRealBadQty());
			xDatabean.setLength(0);
		}
		HisQtyStat dataBean=new HisQtyStat();
		dataBean.setxText(xDatabeans);
		dataBean.setyBData(yBDatas);
		dataBean.setyRData(yRDatas);
		model.addAttribute("beanData", dataBean);
		
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 下午5:07:02 
	* 功能说明 ：封装辅料消耗数据
	 */
	private void setFLData(RealTimeFLConsumeShowBean bean,RealTimeFLConsume param,String orderType,boolean isNew){
		//辅料类型
		String matType=param.getMatType();
		
		if(isNew){
			bean.setEqpName(param.getEqpName());
			bean.setEqpCode(param.getEqpCode());
			bean.setTeamName(param.getTeamName());
			bean.setShiftName(param.getShiftName());
			bean.setQty(param.getNowQty());
			bean.setUnitName(param.getUnitName());
			bean.setMatName(param.getMat());
		}
		
		if(orderType.equals("1")){
			//卷烟机
			//卷烟纸
			switch(matType){
				//卷烟纸
				case "2":
					bean.setPzNowVal(param.getRealQty());
					bean.setPzStd(param.getStdQty());
					bean.setPzVal(param.getPercent());
					break;
				//滤棒
				case "4":
					//滤棒单位为万支
					bean.setLbNowVal(param.getRealQty());
					bean.setLbRealVal(getPercent(param));
					bean.setLbStd(param.getStdQty());
					break;
				//水松纸
				case "3":
					bean.setSszNowVal(param.getRealQty());
					bean.setSszStd(param.getStdQty());
					bean.setSszVal(param.getPercent());break;
			}
			
		}else if(orderType.equals("2")){
			//包装机
			switch(matType){
			//条盒纸
			case "8":
				bean.setThzNowVal(param.getRealQty());
				bean.setThzStd(param.getStdQty());
				bean.setThzVal(param.getPercent());
				break;
			//小盒纸
			case "7":
				bean.setXhzNowVal(param.getRealQty());
				bean.setXhzStd(param.getStdQty());
				bean.setXhzVal(param.getPercent());
				break;
			//条盒烟膜
			case "6":
				bean.setThmNowVal(param.getRealQty());
				bean.setThmStd(param.getStdQty());
				bean.setThmVal(param.getPercent());
				break;
			//内衬纸
			case "9":
				bean.setNczNowVal(param.getRealQty());
				bean.setNczStd(param.getStdQty());
				bean.setNczVal(param.getPercent());
				break;
			//小盒烟膜
			case "5":
				bean.setXhmNowVal(param.getRealQty());
				bean.setXhmStd(param.getStdQty());
				bean.setXhmVal(param.getPercent());
				break;
			}
		}
	}

	private  String getPercent(RealTimeFLConsume param) {
		Double p=0.0;
		if(param.getNowQty()!=null && param.getNowQty()>0 && param.getStdQty()!=null && param.getStdQty()>0){
			p=MathUtil.roundHalfUp(param.getRealQty()/param.getNowQty()/param.getStdQty(), 2)*0.8;
		}
		p=p>1?1:p;
		return p*100+"%";
	}
}
