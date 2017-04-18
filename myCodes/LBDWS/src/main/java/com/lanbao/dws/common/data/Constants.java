package com.lanbao.dws.common.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.lanbao.dws.common.init.BaseParams;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;

/*
 * Copyright (C), 2014-2015, 
 * FileName: CccSgmConstants.java
 * Author:  万昌煌
 * Date:     2014年4月3日 下午13:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class Constants {
	
	
	
	/* 0-默认  1-通过  3-后期处理    -完成情况*/
	 public static final Integer STATUS1 = 1;
	 public static final Integer STATUS2 = 2;
	 public static final Integer STATUS3 = 3;

	/**
     *  提交返回提示：
     *  
     *   1-成功 
     *   2-失败 
     */
    public static final String RESULT_SUCESS = "1";
    public static final String RESULT_ERROR = "2";
    
    
    /**
     * 简介 
     * 
     * 1-起始页
     * 5-每页5条 
     * 
     * */
    public static final Integer ABOUT_INDEX = 1;
    public static final Integer ABOUT_SIZE = 5;
    
    /**
     * md_eqp_category表 code
     * 1-卷烟机
     * 2-包装机
     * 3-装封箱机
     * 4-成型机
     * 5-发射机
     * 6-装盘机
     * 7-卸盘机
     * 
     * **/
    public static final String MEC_CODE1="1";
    public static final String MEC_CODE2="2";
    public static final String MEC_CODE3="3";
    public static final String MEC_CODE4="4";
    
    /**
     * [功能说明]：工单保养计划
     * @param type; //类型     1. 轮保 2 润滑 3 停产检修   4-维修呼叫
     * @author wanchanghuang
     * 
     * */
    public static final String gdType1="1";
    public static final String gdType2="2";
    public static final String gdType3="3";
    public static final String gdType4="4";
    
    /**
     * [功能说明]：WCT系统手动运行，完成工单状态
     * @param type; //类型    5:终止  4：完成  2：运行 
     * @author wanchanghuang
     * 
     * */
    public static final Integer WORKORDERSTATUS2=2;
    public static final Integer WORKORDERSTATUS4=4;
    public static final Integer WORKORDERSTATUS5=5;
    
    
    
    
    /**
     * [说明]:获得WCT系统内页(设备保养)登录信息
     * @param jtUserCode:机台系统登录用户名
     * 
     * */
    public static EqmByLoginBean getEqmByLogin(String jtUserCode,String loginName){
    	EqmByLoginBean eqlBean=BaseParams.getMapebb().get(jtUserCode); //通过电脑登录名能找到之前登录信息
    	if(eqlBean!=null){ 
    		if( loginName==null || loginName.equals(eqlBean.getLoginName()) ){ //现有登录信息与之前对比，如果不能匹配，删除之前通过本电脑登录的信息
    			return eqlBean;
    		}else{
    			//清除Map
    			BaseParams.getMapebb().remove(jtUserCode);
    		}
    	}
    	return null;
    }
    
    /** DAC换班信号
	 * #91-早班  92-中班   93-晚班   
	 * 
	 * eqpType  1卷包    2成型
	 * */
	public static Map<String,String> getShiftId(Map<String,String> map,String chgShift,int eqpType){
		if("91".equals(chgShift)){
			map.put("shift", "1");
		}else if("92".equals(chgShift)){
			map.put("shift", "2");
		}else if("93".equals(chgShift)){
			map.put("shift", "3");
		}
		map.put("eshift",chgShift);
		if(eqpType==1){
			//卷包
			BaseParams.setDacMap(map);
		}else if(eqpType==2){
			//成型
			BaseParams.setFilterDacMap(map);
		}
		return map;
	}
	/**
	 * 存放监听到的DAC  Session
	 */
	public static Map<String,IoSession> dacMap= new HashMap<>();
	
    public static final int unitMins=6;//分钟id，之后通过md_fix——code中获取
}
