package com.shlanbao.tzsc.pms.sys.msgqueue.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.msgqueue.beans.MessageQueueBean;

public interface MessageQueueServiceI {
	/**
	 * 保存接口日志
	 * @author Leejean
	 * @create 2014年9月4日上午11:43:59
	 * @param logBean
	 */
	public void saveMessageQueue(MessageQueueBean messageQueueBean) throws Exception;
	/**
	 * 查询接口日志
	 * @author Leejean
	 * @create 2014年9月4日上午11:44:09
	 * @param MessageQueueBean
	 * @param pageParams
	 * @return
	 */
	public DataGrid getAllMessageQueues(MessageQueueBean messageQueueBean, PageParams pageParams)throws Exception;
	/**
	 * 批量删除接口日志
	 * @author Leejean
	 * @create 2014年9月16日下午12:59:30
	 * @param ids
	 */
	public void batchDeleteMessageQueues(String ids) throws Exception;;
	/**
	 * 删除接口日志
	 * @author Leejean
	 * @create 2014年9月16日下午12:59:42
	 * @param id
	 */
	public void deleteMessageQueue(String id) throws Exception;;
}
