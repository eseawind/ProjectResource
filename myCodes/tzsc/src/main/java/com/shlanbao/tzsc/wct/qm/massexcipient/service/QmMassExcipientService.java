package com.shlanbao.tzsc.wct.qm.massexcipient.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.qm.massexcipient.bean.QmMassExcipientBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 辅料、自检自控装置确认记录接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-27
 */
public interface QmMassExcipientService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmMassExcipientBean bean,PageParams pageParams) throws Exception;

	/**
	 * 首检记录 新增修改
	 * @param checkBean
	 * @param excipientBean	
	 * @param login		自检人登录信息	
	 * @throws Exception
	 */
	void editBean(QmMassExcipientBean[] checkBean,QmMassExcipientBean[] excipientBean,LoginBean login) throws Exception;
	/**
	 * 删除
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param id
	 */
	void deleteByIds(String id) throws Exception;
	
}
