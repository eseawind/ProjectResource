package com.shlanbao.tzsc.wct.isp.packer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.wct.isp.packer.beans.PackerData;
import com.shlanbao.tzsc.wct.isp.packer.service.PackerIspServiceI;
/**
 * 包装机实时监控
 * @author Leejean
 * @create 2014年12月31日上午9:26:28
 */
@Controller
@RequestMapping("/wct/isp/packer")
public class PackerIspController extends BaseController {
	@Autowired
	private PackerIspServiceI packerIspService;
	/**
	 * 初始化包装机基本信息
	 * @author Leejean
	 * @create 2014年12月31日上午8:41:58
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initPackerBaseInfo")
	public PackerData initPackerBaseInfo(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return packerIspService.initPackerBaseInfo(equipmentCode);
		}else{
			return new PackerData();
		}
	}
	
	/**
	 * 获取包装机实时数据
	 * @author Leejean
	 * @create 2014年12月31日上午8:42:07
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPackerData")
	public PackerData getPackerData(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return packerIspService.getPackerData(equipmentCode);
		}else{
			return new PackerData();
		}
	}
}
