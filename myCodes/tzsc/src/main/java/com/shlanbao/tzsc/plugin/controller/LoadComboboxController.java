package com.shlanbao.tzsc.plugin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.init.BaseParams;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;
import com.shlanbao.tzsc.utils.params.ComboboxType;
/**
 * 公共下拉框数据控制器
 * @author Leejean
 * @create 2014年11月18日上午9:11:28
 */
@Controller
@RequestMapping("/plugin/combobox")
public class LoadComboboxController extends BaseController {
	/**
	 * 加载下拉框
	 * @author Leejean
	 * @create 2014年11月27日上午10:38:43
	 * @param type 类型
	 * @param setAll 是否设置【全部】选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/load")
	public List<Combobox> loadShift(String type,boolean setAll){
		try {
			if(type.equalsIgnoreCase(ComboboxType.SHIFT)){				
				return BaseParams.getShiftCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.TEAM)){
				return BaseParams.getTeamCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.WORKSHOP)){
				return BaseParams.getWorkShopCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.MATPROD)){
				return BaseParams.getMatProdCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.MATGRP)){
				return BaseParams.getMatGrpCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.MATTYPE)){
				return BaseParams.getMatTypeCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.UNIT)){
				return BaseParams.getUnitCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.EQPCATEGORY)){
				return BaseParams.getEqpCategoryCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.EQPTYPE)){
				return BaseParams.getEqpTypesCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.ALLEQPS)){
				return BaseParams.getAllEqpsCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.ALLROLLERS)){
				return BaseParams.getAllRollersCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.ALLPACKERS)){
				return BaseParams.getAllPackersCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.ALLBOXERS)){
				return BaseParams.getAllBoxersCombobox(setAll);
			}
			if(type.equalsIgnoreCase(ComboboxType.ALLFILTERS)){
				return BaseParams.getAllFiltersCombobox(setAll);
			}
		} catch (Exception e) {
			log.error("获取"+type+"下拉框数据异常", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/loadMdMatParam")
	public List<MatParamBean> getAllMatParams(){
		try {	
			List<MatParamBean> lit= BaseParams.getAllMdMatParam();
			return lit;
		} catch (Exception e) {
			log.error("获取辅料数据异常", e);
		}
		return null;
	}
	
}
