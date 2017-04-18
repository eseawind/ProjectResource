package com.shlanbao.tzsc.wct.qm.self.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.self.beans.QmSelfCheckStripBean;
/**
 * 外观质量检验记录接口
 * @author luther.zhang
 * @create 2015-01-05
 */
public interface QmSelfWctService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmSelfCheckStripBean bean,int pageIndex) throws Exception;
}
