package com.shlanbao.tzsc.pms.sys.datadict.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.DocFilemanage;
import com.shlanbao.tzsc.base.mapping.SysEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpCategoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public interface SysEqpCategoryServiceI {
	/**
	 * 添加设备类型
	 * @param mdEqpCategory
	 * @throws Exception
	 */
	public void addMdCategory(SysEqpCategory mdEqpCategory)throws Exception;
	/**
	 * 修改设备类型
	 * @param mdEqpCategory
	 * @throws Exception
	 */
	
	public void updateCategory(SysEqpCategoryBean mdEqpCategoryBean)throws Exception;
	
	/**
	 * 查询设备类型实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysEqpCategory getMdCategoryById(String id)throws Exception;
	
	/**
	 * 查询设备类型列表
	 * @param mdEqpCategory
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryMdCategory(SysEqpCategory mdEqpCategory,PageParams pageParams)throws Exception;
	
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
	public List<SysEqpCategoryBean> queryMdCategory()throws Exception;
	
	/**
	 * 上传图片
	 * @param id		主键ID
	 * @param session
	 * @param request
	 * @param fmBean	保存对象
	 * @throws Exception
	 */
	public DocFilemanage fileUpdate(String id,HttpSession session, HttpServletRequest request,
			SysEqpCategoryBean fmBean) throws Exception;
	/**
	 * 更新主键外键ID
	 * @param file
	 * @throws Exception
	 */
	public int updateCategory(DocFilemanage file)throws Exception;
	
}
