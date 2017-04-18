package com.shlanbao.tzsc.pms.msg.qm.service;

import java.util.Date;
import java.util.List;

import com.shlanbao.tzsc.base.mapping.MsgQmWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.msg.qm.beans.MsgQmWarnBean;

/**
 * 质量告警服务接口
 * @author yangbo
 *
 */
public interface MsgQmWarnServiceI {
	/**
	 * 添加质量告警数据
	 * @param msgQmWarn
	 * @throws Exception
	 */
	public void addMsgQmWarn(MsgQmWarn msgQmWarn)throws Exception;
	
	/**
	 * 通过ID获取质量告警数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MsgQmWarn getMsgQmWarnById(String id)throws Exception;
	
	/**
	 * 通过ID获取质量告警的BEAN 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MsgQmWarnBean getMsgQmWarnBeanById(String id)throws Exception;
	
	/**
	 * 分页查询质量告警数据
	 * @param msgQmWarn 多条件查询
	 * @param pageParams 分页的参数
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryMsgQmWarn(MsgQmWarnBean msgQmWarn,PageParams pageParams)throws Exception;
	
	/**
	 * 通过读取状态获取质量告警信息
	 * @return
	 * @throws Exception
	 */
	public List<MsgQmWarnBean> getMsgQmWarns(Long sts)throws Exception;
	
	/**
	 * 通过ID删除质量告警信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteMsgQmWarn(String id)throws Exception;
}
