package com.lanbao.dws.service.init.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.lanbao.dws.common.data.dac.handler.DataHandler;
import com.lanbao.dws.common.data.dac.handler.FilterCalcValue;
import com.lanbao.dws.common.data.dac.handler.PackerCalcValue;
import com.lanbao.dws.common.data.dac.handler.RollerCalcValue;
import com.lanbao.dws.common.init.BaseParams;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.combobox.MdEquipment;
import com.lanbao.dws.model.combobox.MdFixCode;
import com.lanbao.dws.model.combobox.MdMat;
import com.lanbao.dws.model.combobox.MdMatGrp;
import com.lanbao.dws.model.wct.pddisplay.WorkOrderBean;
import com.lanbao.dws.service.init.InitComboboxData;
/**
 * 初始化基础数据，用于填充下拉框数据
 * @author shisihai
 *
 */
@Service
public class InitComboboxDataImpl implements InitComboboxData{
	@Autowired
    IPaginationDalClient dalClient;
	@Override
	public void initCombobox() {
		//所有设备
		BaseParams.setEqps(dalClient.queryForList("initCombobox.queryEqps", null, MdEquipment.class));
		//综合基础数据
		BaseParams.setFixCode(dalClient.queryForList("initCombobox.queryFixCode", null, MdFixCode.class));
		//牌号
		BaseParams.setMat(dalClient.queryForList("initCombobox.queryMat", null, MdMat.class));
		//物料组
		BaseParams.setMatGroup(dalClient.queryForList("initCombobox.queryMatGroup", null, MdMatGrp.class));
	}

	/**
	 * 根据type返回相应的对象集合
	 */
	@Override
	public List<?> getComboboxDataByType(String type) {
		switch (type) {
		case ComboboxType.ALLEQPS:
			return BaseParams.getEqps();
		case ComboboxType.ROLLEREQPS:
			return BaseParams.getRollerEqps();
		case ComboboxType.PACKEREQPS:
			return BaseParams.getPackerEqps();
		case ComboboxType.FILTEREQPS:
			return BaseParams.getFiltereqps();
		case ComboboxType.FXEQPS:
			return BaseParams.getFXeqps();
		case ComboboxType.FIXCODE:
			return BaseParams.getFixCode();
		case ComboboxType.MAT:
			return BaseParams.getMat();
		case ComboboxType.MATGRP:
			return BaseParams.getMatGroup();
		default:
			return null;
		}
	}
	
	/**
	* @author 作者 : 
	* @version 创建时间：2016年7月1日 下午9:03:10 
	* 功能说明 ：根据Upcode 获取该节点下的对象
	 */
	public void chooseFixCodeComboboxModel(Model model,String ... upCode){
		List<MdFixCode> fixCodes=BaseParams.getFixCode();
		List<MdFixCode> combobox=null;
		for (String ucode : upCode) {
			combobox=new ArrayList<>();
			for (MdFixCode mdFixCode : fixCodes) {
				if(mdFixCode.getUpcode().equals(ucode)){
					combobox.add(mdFixCode);
				}
			}
			model.addAttribute(ucode, combobox);
		}
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午3:02:54 
	* 功能说明 ：根据upcode获取mdFixCode对象集合
	 */
	@Override
	public List<MdFixCode> getFixCodeByUpcode(String upCode){
		List<MdFixCode> fixCodes=BaseParams.getFixCode();
		List<MdFixCode> persionFixCode=new ArrayList<>();
		for (MdFixCode mdFixCode : fixCodes) {
			if(mdFixCode.getUpcode().equals(upCode)){
				persionFixCode.add(mdFixCode);
			}
		}
		return persionFixCode;
	}
	
	
	/**
	 * 向model中存放指定的bean集合
	 * @param model
	 * @param type
	 */
	@Override
	public void chooseSetComboboxModel(Model model,String ... type){
		for (String str : type) {
			model.addAttribute(str,getComboboxDataByType(str));
		}
	}

    /**
     * 说明：
     *    1）通过当前时间，找到当班所有工单
     *    2）通过工单type 1卷烟机   2包装机 4   查询对应的辅料
     *    3）2个for循环，得到相应的值
     *    4）封装map<eqpcode,object>
     *    4)将map保存在相应的快照内，方便dac辅料转换；
     * @throws Exception 
     * 
     * */
	@Override
	public void initWorkOrderInfo(){
		try {
			WorkOrderBean wob=new WorkOrderBean();
			wob.setFdate(new Date());
			//查询当前班次所有工单
			List<WorkOrderBean> list=dalClient.queryForList("initCombobox.querySchWorkOrder", wob, WorkOrderBean.class);
			List<WorkOrderBean> listfl=null;
			List<WorkOrderBean> listGzxs=null;
			List<WorkOrderBean> listFilterGzxs=null;
			for(WorkOrderBean w:list){
				if("1".equals(w.getType().toString())){ 
					//查询当前工单的辅料系数  、设备类型、辅料类型
					listfl=dalClient.queryForList("initCombobox.queryMdMatParam", w, WorkOrderBean.class);
					//查询设备滚轴系数
					listGzxs=dalClient.queryForList("initCombobox.queryMdEquipmentParam", w, WorkOrderBean.class);
					Object calcValue = null;
			    	Double shuisongzhiValue = null; //水松纸系数
			    	Double shuisongzhiAxisValue = null;//水松纸滚轴
			    	Double juanyanzhiAxisValue = null;//卷烟纸滚轴
					if( listfl!=null && listfl.size()>0 ){
				    	for(WorkOrderBean fl:listfl){ 
					    	if("3".equals( fl.getMatTypeCode() )){
					    		shuisongzhiValue=fl.getFlxsVal();
					    	}
					    }
				    }
					if( listGzxs!=null && listGzxs.size()>0 ){
				    	for(WorkOrderBean gzxs:listGzxs){ 
				    		juanyanzhiAxisValue = gzxs.getAxlePz();
							shuisongzhiAxisValue = gzxs.getAxleSsz();
					    }
				    }
					
					//判断系数是否配置
					if(juanyanzhiAxisValue==null){
						throw new Exception(w.getId()+"卷烟纸滚轴系数未配置");
					}
					if(shuisongzhiAxisValue==null){
						throw new Exception(w.getId()+"水松纸滚轴系数未配置");
					}
					calcValue = new RollerCalcValue(shuisongzhiValue, shuisongzhiAxisValue, juanyanzhiAxisValue);
					DataHandler.setCalcValueIntoMap(w.getEqpcode().toString(), calcValue);
					
				}
				
				if("2".equals(w.getType().toString())){ //包装机
					listfl=dalClient.queryForList("initCombobox.queryMdMatParam", w, WorkOrderBean.class);
				    if( listfl!=null && listfl.size()>0 ){
				    	Object calcValue = null;
				    	Double xiaohemoValue = null; //小盒膜系数
				    	Double tiaohemoValue = null; //条盒膜系数
				    	Double neichenzhiValue = null;//内衬纸系数
				    	for(WorkOrderBean fl:listfl){ //7-小盒纸  8-条盒纸  5小盒膜 6条盒膜9内衬纸  
					    	if("5".equals( fl.getMatTypeCode() )){
					    		xiaohemoValue=fl.getFlxsVal();
					    	}else if("6".equals( fl.getMatTypeCode() )){
					    		tiaohemoValue=fl.getFlxsVal();
					    	}else if("9".equals( fl.getMatTypeCode() )){
					    		neichenzhiValue=fl.getFlxsVal();
					    	}
					    }
				    	//判断是否配置完整
						if(xiaohemoValue==null){
							throw new Exception("工单ID:"+w.getId()+" 小盒膜辅料计数参数未配置");
						}
						if(tiaohemoValue==null){
							throw new Exception("工单ID:"+w.getId()+" 条盒膜辅料计数参数未配置");
						}
						if(neichenzhiValue==null){
							throw new Exception("工单ID:"+w.getId()+" 内衬纸辅料计数参数未配置");
						}
				    	calcValue = new PackerCalcValue(xiaohemoValue, tiaohemoValue, neichenzhiValue);
						DataHandler.setCalcValueIntoMap(w.getEqpcode().toString(), calcValue);
				    }
				}
				
				//成型机
				if("4".equals(w.getType().toString())){ 
					Double panzhiAxisValue = null;
					Object calcValue = null;
					listFilterGzxs=dalClient.queryForList("initCombobox.queryMdEquipmentParam", w, WorkOrderBean.class);
					if(listFilterGzxs!=null && listFilterGzxs.size()>0){
						WorkOrderBean wbt=listFilterGzxs.get(0);
						panzhiAxisValue = Double.valueOf(wbt.getAxlePz());
					}
					if(panzhiAxisValue==null){
						throw new Exception(w.getId()+"成型机滚轴系数未配置");
					}
					calcValue = new FilterCalcValue(panzhiAxisValue);
					DataHandler.setCalcValueIntoMap(w.getEqpcode().toString(), calcValue);
					
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>功能描述：根据设备code获取设备的工段</p>
	 *@see com.lanbao.dws.service.init.InitComboboxData#getEqpByEqpCode(java.lang.String)
	 *shisihai
	 *2016上午9:30:49
	 */
	@Override
	public MdFixCode getEqpByEqpCode(String eqpCode) {
		List<MdFixCode> ls=getFixCodeByUpcode(ComboboxType.WORKCENTEREQP);
		MdFixCode eqp=null;
		long eqpCodeNum=StringUtil.converObj2Long(eqpCode);
		String code=null;
		for (MdFixCode mdFixCode : ls) {
			code=mdFixCode.getCode();
			if(StringUtil.notEmpty(code) && StringUtil.converObj2Long(code)==eqpCodeNum){
				eqp=mdFixCode;
				break;
			}else{
				eqp=new MdFixCode();
			}
		}
		return eqp;
	}
	
}
