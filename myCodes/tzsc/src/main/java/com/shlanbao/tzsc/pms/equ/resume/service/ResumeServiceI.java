package com.shlanbao.tzsc.pms.equ.resume.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmResumeBean;

public interface ResumeServiceI {
	
	/**
	 * 删除
	 * 
	 */
	public void deleResumeById(String id) throws Exception;

	 
	public EqmResumeBean getResumeById(String id) throws Exception;
	
	
	/**
	 * 设备履历新增或编辑
	 * @param eqmResumeBean
	 * @param userId
	 * @throws Exception
	 */
	public void addOrEditResume(EqmResumeBean eqmResumeBean,String userId) throws Exception;
	
	/**
	 * 设备履历查询
	 * @param eqmResumeBean
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryResume(EqmResumeBean eqmResumeBean,PageParams pageParams) throws Exception;
}
