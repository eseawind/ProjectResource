package com.lanbao.dws.controller.initCombobox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanbao.dws.model.combobox.MdFixCode;
import com.lanbao.dws.service.init.InitComboboxData;
/**
 * 初始化下拉框数据测试类（实际已经在每次页面跳转时，将下拉数据放在了Model中）
 */
@Controller
@RequestMapping("/wct/initCombobox")
public class InitComboboxController {

	@Autowired
	private InitComboboxData initComboboxData;
	
	/**
	 * 根据请求类型，返回对应的对象集合
	 * @param type
	 * @return
	 */
	@RequestMapping("/getCombobox")
	@ResponseBody
	public List<?> getComboboxData(String type) {
		return initComboboxData.getComboboxDataByType(type);
	}
	
	/**
	 * <p>功能描述：根据设备的code获取设备的工段</p>
	 *@param eqpCode
	 *@return
	 *shisihai
	 *2016上午9:31:33
	 */
	@RequestMapping("/getEqpByCode")
	@ResponseBody
	public MdFixCode getEqpByCode(String eqpCode) {
		return initComboboxData.getEqpByEqpCode(eqpCode);
	}
	
}
