package com.shlanbao.tzsc.pms.equ.resume.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmInsideBean;

public interface EqmInsideServiceI {
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(String id) throws Exception;
	/**
	 * 根据主键ID查询信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EqmInsideBean getById(String id) throws Exception;
	/**
	 * 添加 或 修改
	 * @param beans
	 * @param userId
	 * @throws Exception
	 */
	public void addOrEditBean(String eqmResumeId,EqmInsideBean[] beans,String userId) throws Exception;
	/**
	 * 分页查询
	 * @param bean
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryList(EqmInsideBean bean,PageParams pageParams) throws Exception;
}
