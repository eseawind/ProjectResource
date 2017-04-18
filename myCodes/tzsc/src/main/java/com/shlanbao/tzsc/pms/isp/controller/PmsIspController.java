package com.shlanbao.tzsc.pms.isp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.pms.isp.beans.BoxerGroup;
import com.shlanbao.tzsc.pms.isp.beans.FilterData;
import com.shlanbao.tzsc.pms.isp.beans.FilterGroup;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerData;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerGroup;
import com.shlanbao.tzsc.pms.isp.beans.ShooterGroup;
import com.shlanbao.tzsc.pms.isp.beans.WorkorderInfoBean;
import com.shlanbao.tzsc.pms.isp.service.PmsIspServiceI;
/**
 * PMS实时监控监控控制器
 * @author Leejean
 * @create 2015年1月22日下午3:03:24
 */
@Controller
@RequestMapping("/pms/isp")
public class PmsIspController extends BaseController{
	@Autowired
	private PmsIspServiceI pmsIspService;
	/**
	 * 初始化卷包机组
	 * @author Leejean
	 * @create 2015年1月15日上午9:50:17
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initRollerPackerGroups")
	public List<RollerPackerGroup> initRollerPackerGroups(HttpServletRequest request){
		return pmsIspService.initRollerPackerGroups(request);
	}
	/**
	 * 
	 * @author Leejean
	 * @create 2015年1月26日上午11:41:07
	 * @param type 1：卷烟机工单2：包装机工单3:封箱机工单4:成型机工
	 *  卷烟机和包装机为一个整体机组，该机组使用卷烟机工单作为显示
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initWorkOrderInfo")
	public List<WorkorderInfoBean> initWorkOrderInfo(Long type){
		return pmsIspService.initRollerPackerGroupWorkOrderInfo(type);
	}
	/**
	 * 初始化封箱机
	 * @author Leejean
	 * @create 2015年1月15日上午9:50:17
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initBoxerGroups")
	public List<BoxerGroup> initBoxerGroups(HttpServletRequest request){
		return pmsIspService.initBoxerGroups(request);
	}
	/**
	 * 初始化发射机
	 * @author Leejean
	 * @create 2015年1月15日上午9:50:17
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initShootererGroups")
	public List<ShooterGroup> initShootererGroups(HttpServletRequest request){
		return pmsIspService.initShootererGroups(request);
	}
	/**
	 * 初始化成型机
	 * @author Leejean
	 * @create 2015年1月15日上午9:50:17
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initFilterGroups")
	public List<FilterGroup> initFilterGroups(HttpServletRequest request){
		return pmsIspService.initFilterGroups(request);
	}
	/**
	 * 获得所有卷包机组数据
	 * @author Leejean
	 * @create 2015年1月16日上午9:05:57
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllRollerPackerDatas")
	public List<RollerPackerData> getAllRollerPackerDatas(){
		return pmsIspService.getAllRollerPackerDatas();
	}
	
	/**
	 * 获得当天当班所有已运行的工单计划总产量，计划结束时间
	 * @author wanchanghuang
	 * @create 2016年12月15日13:22:56
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllWorkOrderByQty")
	public Map<String, String> getAllWorkOrderByQty(){
		return pmsIspService.getAllWorkOrderByQty();
	}
	
	
	/**
	 * 获得所有成型机组数据
	 * @author Leejean
	 * @create 2015年1月16日上午9:05:57
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllFilterDatas")
	public List<FilterData> getAllFilterDatas(){
		return pmsIspService.getAllFilterDatas();
	}
	
}
