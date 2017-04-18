package com.lanbao.dws.service.init;

import java.util.List;

import org.springframework.ui.Model;

import com.lanbao.dws.model.combobox.MdFixCode;
/**
 * 初始化下拉数据
 */
public interface InitComboboxData {
	/**
	 * 初始化下拉数据
	 */
	void initCombobox();
	
	/**
	 * 根据type 获取指定的集合
	 * @param type
	 * @return
	 */
	List<?> getComboboxDataByType(String type);
	
	/**
	 * 向model中存放指定的bean集合。用于跳转到指定页面含有下拉框时使用
	 * @param model
	 * @param type
	 */
	void chooseSetComboboxModel(Model model,String ... type);
	/**
	* @author 作者 : 
	* @version 创建时间：2016年7月1日 下午9:08:03 
	* 功能说明 ：根据Upcode获取该节点下的对象集合并放到Model中
	 */
	void chooseFixCodeComboboxModel(Model model,String ... upCode);

	
	/**
	* @author 作者 : 
	* @version 创建时间：2016年7月1日 下午9:08:03 
	* 功能说明 ：初始化当前工单信息
	 * @throws Exception 
	 */
	void initWorkOrderInfo();

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月1日 下午3:03:28 
	* 功能说明 ：根据upcode获取对象集合
	 */
	List<MdFixCode> getFixCodeByUpcode(String upCode);
	/**
	 * <p>功能描述：根据设备code获取设备的工段</p>
	 *@param eqpCode
	 *@return
	 *shisihai
	 *2016上午9:26:37
	 */
	MdFixCode getEqpByEqpCode(String eqpCode);
	
}
