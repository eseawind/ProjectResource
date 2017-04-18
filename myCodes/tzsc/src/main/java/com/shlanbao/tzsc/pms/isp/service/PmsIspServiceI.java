package com.shlanbao.tzsc.pms.isp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shlanbao.tzsc.pms.isp.beans.BoxerGroup;
import com.shlanbao.tzsc.pms.isp.beans.FilterData;
import com.shlanbao.tzsc.pms.isp.beans.FilterGroup;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerData;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerGroup;
import com.shlanbao.tzsc.pms.isp.beans.ShooterGroup;
import com.shlanbao.tzsc.pms.isp.beans.WorkorderInfoBean;

public interface PmsIspServiceI {
	/**
	 * 获得所有卷包机组数据
	 * @author Leejean
	 * @create 2015年1月16日上午9:07:02
	 * @return
	 */
	List<RollerPackerData> getAllRollerPackerDatas();
	/**
	 * 获得所有成型机数据
	 * @author Leejean
	 * @create 2015年1月27日上午8:57:51
	 * @return
	 */
	List<FilterData> getAllFilterDatas();
	/**
	 * 加载卷包机组工单信息（机组中的卷烟机，包装机工单的产量，牌号必定一致）
	 * @author Leejean
	 * @create 2015年1月16日上午11:17:45
	 * @return
	 */
	List<WorkorderInfoBean> initRollerPackerGroupWorkOrderInfo(Long type);
	/**
	 * 初始化卷包机组
	 * @author Leejean
	 * @create 2015年1月22日上午8:34:55
	 * @param request 请求
	 * @return 卷包机组信息集合
	 */
	List<RollerPackerGroup> initRollerPackerGroups(HttpServletRequest request);
	/**
	 * 初始化封箱机组
	 * @author Leejean
	 * @create 2015年1月22日上午8:34:55
	 * @param request 请求
	 * @return 卷包机组信息集合
	 */
	List<BoxerGroup> initBoxerGroups(HttpServletRequest request);
	/**
	 * 初始化发射机组
	 * @author Leejean
	 * @create 2015年1月22日上午8:34:55
	 * @param request 请求
	 * @return 卷包机组信息集合
	 */
	List<ShooterGroup> initShootererGroups(HttpServletRequest request);
	/**
	 * 初始化成型机机组
	 * @author Leejean
	 * @create 2015年1月22日上午8:34:55
	 * @param request 请求
	 * @return 卷包机组信息集合
	 */
	List<FilterGroup> initFilterGroups(HttpServletRequest request);
	
	Map<String,String> getAllWorkOrderByQty();

}
