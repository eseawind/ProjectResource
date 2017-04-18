package com.shlanbao.tzsc.wct.qm.massfrist.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.qm.massfrist.beans.QmMassFirstBean;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 首检记录控制器接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-16
 */
public interface QmMassFirstService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmMassFirstBean bean,PageParams pageParams) throws Exception;

	/**
	 * 首检记录 新增修改
	 * @param checkBean
	 * @param firstBean	
	 * @param login		自检人登录信息	
	 * @throws Exception
	 */
	void editBean(QmMassFirstBean[] checkBean,QmMassFirstBean[] firstBean,LoginBean login) throws Exception;
	/**
	 * 删除
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param id
	 */
	void deleteByIds(String id,LoginBean login) throws Exception;
	
	/**
	 * 包装机钢印查询和保存
	 * @param bean
	 * @return
	 */
	Json filledSteelSeal(QmMassFirstBean bean,LoginBean login);
	
	
}
