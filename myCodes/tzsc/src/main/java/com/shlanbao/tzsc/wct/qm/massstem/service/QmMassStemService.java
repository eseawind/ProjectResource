package com.shlanbao.tzsc.wct.qm.massstem.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.qm.massstem.beans.QmMassStemBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 丝含梗接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-28
 */
public interface QmMassStemService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmMassStemBean bean,PageParams pageParams) throws Exception;

	/**
	 * 首检记录 新增修改
	 * @param checkBean
	 * @param stemBean	
	 * @param login		自检人登录信息	
	 * @throws Exception
	 */
	void editBean(QmMassStemBean[] checkBean,QmMassStemBean[] stemBean,LoginBean login) throws Exception;
	/**
	 * 删除
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param id
	 */
	void deleteByIds(String id) throws Exception;
	
}
