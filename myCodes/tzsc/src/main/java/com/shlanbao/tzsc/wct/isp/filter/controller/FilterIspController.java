package com.shlanbao.tzsc.wct.isp.filter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.wct.isp.filter.beans.FilterData;
import com.shlanbao.tzsc.wct.isp.filter.service.FilterIspServiceI;
/**
 * 成型机实时监控
 * @author Leejean
 * @create 2014年12月31日上午9:26:28
 */
@Controller
@RequestMapping("/wct/isp/filter")
public class FilterIspController extends BaseController {
	@Autowired
	private FilterIspServiceI filterIspService;
	/**
	 * 初始化包装机基本信息
	 * @author Leejean
	 * @create 2014年12月31日上午8:41:58
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initFilterBaseInfo")
	public FilterData initFilterBaseInfo(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return filterIspService.initFilterBaseInfo(equipmentCode);
		}else{
			return  new FilterData();
		}
	}
	
	/**
	 * 获取ZL22A型成型机实时数据
	 * @author Leejean
	 * @create 2014年12月31日上午8:42:07
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFilterData")
	public FilterData getFilterData(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return filterIspService.getFilterData(equipmentCode);	
		}else{
			return new FilterData();
		}
	}
	
	/**
	 * 获取ZL22C型成型机实时数据
	 * @author Leejean
	 * @create 2014年12月31日上午8:42:07
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFilterDataCX_FL")
	public FilterData getFilterDataCX_FL(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return filterIspService.getFilterDataCX_FL(equipmentCode);	
		}else{
			return new FilterData();
		}
	}
}
