package com.shlanbao.tzsc.wct.qm.prod.service;


import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.prod.beans.QmProdFileBean;

/**
 * 工艺规程业务接口
 * @author luther.zhang
 * @create 2014-12-31
 */
public interface QmProdWctService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmProdFileBean qmProdFileBean,int pageIndex) throws Exception;
	
}
