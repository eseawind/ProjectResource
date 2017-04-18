package com.shlanbao.tzsc.wct.isp.boxer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.wct.isp.boxer.beans.BoxerData;
import com.shlanbao.tzsc.wct.isp.boxer.service.BoxerIspServiceI;
/**
 * 封箱机实时监控
 * @author Leejean
 * @create 2014年12月31日上午9:26:28
 */
@Controller
@RequestMapping("/wct/isp/boxer")
public class BoxerIspController extends BaseController {
	@Autowired
	private BoxerIspServiceI boxerIspService;
	/**
	 * 初始封箱机机基本信息
	 * @author Leejean
	 * @create 2014年12月31日上午8:41:58
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initBoxerBaseInfo")
	public BoxerData initBoxerBaseInfo(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return boxerIspService.initBoxerBaseInfo(equipmentCode);
		}else{
			return new BoxerData();
		}
	}
	
	/**
	 * 获取封箱机实时数据
	 * @author Leejean
	 * @create 2014年12月31日上午8:42:07
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBoxerData")
	public BoxerData getBoxerData(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return boxerIspService.getBoxerData(equipmentCode);
		}else{
			return new BoxerData();
		}
	}
}
