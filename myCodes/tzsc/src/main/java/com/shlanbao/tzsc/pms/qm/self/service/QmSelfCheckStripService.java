package com.shlanbao.tzsc.pms.qm.self.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.self.beans.QmSelfCheckStripBean;
/**
 * 外观质量检验记录接口
 * @author luther.zhang
 * @create 2015-01-05
 */
public interface QmSelfCheckStripService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmSelfCheckStripBean bean,PageParams pageParams) throws Exception;
}
