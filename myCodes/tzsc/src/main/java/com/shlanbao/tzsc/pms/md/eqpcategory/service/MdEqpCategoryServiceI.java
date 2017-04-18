package com.shlanbao.tzsc.pms.md.eqpcategory.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqpcategory.beans.MdEqpCategoryBean;


public interface MdEqpCategoryServiceI {
	/**
	 * 添加设备类型
	 * @param mdEqpCategory
	 * @throws Exception
	 */
	public void addMdCategory(MdEqpCategory mdEqpCategory)throws Exception;
	/**
	 * 修改设备类型
	 * @param mdEqpCategory
	 * @throws Exception
	 */
	
	public void updateCategory(MdEqpCategoryBean mdEqpCategoryBean)throws Exception;
	
	/**
	 * 查询设备类型实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MdEqpCategory getMdCategoryById(String id)throws Exception;
	
	/**
	 * 查询设备类型列表
	 * @param mdEqpCategory
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryMdCategory(MdEqpCategory mdEqpCategory,PageParams pageParams)throws Exception;
	
	/**
	 * 删除设备类型
	 * @param id
	 * @throws Exception
	 */
	public void deleteMdCategory(String id)throws Exception;
	
	/**
	 * 查询设备类型列表
	 * @return
	 * @throws Exception
	 */
	public List<MdEqpCategoryBean> queryMdCategory()throws Exception;
	
	
	
}
