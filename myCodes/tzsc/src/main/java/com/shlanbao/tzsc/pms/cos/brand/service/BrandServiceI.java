package com.shlanbao.tzsc.pms.cos.brand.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.brand.beans.SeachBean;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerGroup;


public interface BrandServiceI {

	/**
	* @Title: queryBrand_chengxing 
	* @Description: 成型成本考核统计
	* @param  isShift 是否显示班次
	* @param  isWorkorder 是否显示工单
	* @param  seachBean 查询条件
	* @param  pageParams 分页条件
	* @return DataGrid   返回类型 
	* @throws Exception  设定文件 
	 */
	public DataGrid queryBrand_chengxing(boolean isShift,boolean isWorkorder,SeachBean seachBean, PageParams pageParams) throws Exception;
	/**
	* @Title: queryBrandCos_juanbao 
	* @Description: 卷包成本考核统计
	* @param  isDisabled 是否统计残烟量
	* @param  isWorkorder 是否查询工单
	* @param  seachBean 查询条件
	* @param  pageParams 分页条件
	* @param  rollerPackerGroup 机组对应关系
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryBrandCos_juanbao(boolean isDisabled,boolean isWorkorder,SeachBean seachBean, PageParams pageParams,List<RollerPackerGroup> rollerPackerGroup) throws Exception;
	
}
