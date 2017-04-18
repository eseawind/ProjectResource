package com.shlanbao.tzsc.wct.qm.qmCheckRecord.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.qmCheckRecord.beans.qmCheckRecordBean;

/**
 * wct质检记录
 * @author shisihai
 *
 */
public interface QmCheckRecordWctService {
	/**
	 *说明： 查询质检信息概要
	 * @param bean
	 * @param pageIndex
	 * @return
	 * shisihai  
	 * 20152015年12月30日上午9:43:08
	 */
	public DataGrid getSummaryList(qmCheckRecordBean bean,int pageIndex);
	/**
	 *说明：查询质检信息详细
	 * @param bean
	 * @param pageIndex
	 * @return
	 * shisihai  
	 * 20152015年12月30日上午9:43:24
	 */
	public DataGrid getDetailedList(String  id);
}
