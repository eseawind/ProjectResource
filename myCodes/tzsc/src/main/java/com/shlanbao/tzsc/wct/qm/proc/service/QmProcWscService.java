package com.shlanbao.tzsc.wct.qm.proc.service;


import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.proc.beans.QmProcWctFileBean;
/**
 * 工艺规程业务接口
 * @author luther.zhang
 * @create 2015-02-13
 */
public interface QmProcWscService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmProcWctFileBean bean,int pageIndex) throws Exception;

}
