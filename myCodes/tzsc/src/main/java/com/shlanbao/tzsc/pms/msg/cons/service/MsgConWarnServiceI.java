package com.shlanbao.tzsc.pms.msg.cons.service;

import java.util.Date;
import java.util.List;

import com.shlanbao.tzsc.base.mapping.MsgConWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.msg.cons.beans.MsgConWarnBean;

/**
 * 单耗告警服务接口
 * @author yangbo
 *
 */
public interface MsgConWarnServiceI {
	/**
	 * 添加或者修改单耗告警信息
	 * @param msgConWarn
	 * @throws Exception
	 */
	public void addMsgConWarn(MsgConWarn msgConWarn)throws Exception;
	
	/**
	 * 分页带条件查询单耗告警列表
	 * @param msgConWarn
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryMsgConWarn(MsgConWarnBean msgConWarn,PageParams pageParams,Date startTime,Date endTime)throws Exception;
	
	/**
	 * 通过读取状态获取单耗告警列表
	 * @param sts
	 * @return
	 * @throws Exception
	 */
	public List<MsgConWarnBean> getMsgConWarns(long sts)throws Exception;
	
	/**
	 * 通过ID获取单耗告警记录实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MsgConWarn getMsgConWarnById(String id)throws Exception;
	
	/**
	 * 通过ID获取单耗告警记录BEAN
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MsgConWarnBean getMsgConWarnBeanById(String id)throws Exception;
	
	/**
	 * 通过ID删除单耗告警记录
	 * @param id
	 * @throws Exception
	 */
	public void deleteMsgConWarnById(String id)throws Exception;
}
