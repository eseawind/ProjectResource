package com.shlanbao.tzsc.plugin.service;


/**
 * 加载下拉框
 * @author Leejean
 * @create 2014年11月28日上午9:13:10
 */
public interface LoadComboboxServiceI {
	/**
	 * 加载combobox
	 * @author Leejean
	 * @create 2014年11月28日上午9:10:30
	 * @param scope  ALL:初始化系统时默认加载所有，当某主数据业务变更时，指定scope单独加载
	 * scope定义参考 ComboboxType 成员变量定义
	 */
	public void initCombobox(String scope);
	/**
	 * 加载转换SWF需要用到的进程
	 */
	public void initSoffice();
	
	/**
	 * 加载辅料数据
	 * 
	 */
	public void getAllMatParams();
	
	/**
	 * 数据初始化导入转换数据
	 * 
	 * */
	public void querySysEqpType();
	
	public void initAllRunnedWorkOrderCalcValues();

}
