package com.shlanbao.tzsc.pms.msg.info.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.MsgInfo;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.msg.info.beans.MsgInfoBean;
import com.shlanbao.tzsc.pms.msg.info.beans.MsgOperatorBean;

/**
 * 机台通知信息维护服务接口
 * @author yangbo 
 *
 */
public interface MsgInfoServiceI {
	/**
	 * 保存机台通知信息
	 * @param msgInfo
	 * @throws Exception
	 */
	public Json addMsgInfo(MsgInfo msgInfo)throws Exception;
	
	/**
	 * 审批保存
	 * @param msgInfo
	 * @throws Exception
	 */
	public void saveMsgInfo(MsgInfo msgInfo)throws Exception;
	
	/**
	 * 通过ID查询机台通知信息
	 * @param id
	 * @return 机台通知信息实体
	 * @throws Exception
	 */
	public MsgInfoBean getMsgInfoById(String id)throws Exception;
	
	/**
	 * 通过ID查询bean
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MsgInfoBean getMsgInfoBeanById(String id)throws Exception;
	
	/**
	 * 分页带参数查询通知信息
	 * @param msgInfo
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryMsgInfo(MsgInfoBean msgInfoBean,PageParams pageParams)throws Exception;
	
	/**
	 * 根据ID删除通知信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteMsgInfoById(String id)throws Exception;
	
	/**
	 * 获取当前用户对消息的操作
	 * @return
	 * @throws Exception
	 */
	public MsgOperatorBean getCurrUserOperator()throws Exception;
	
	/**
	 * 获取已发送的信息
	 * @return
	 * @throws Exception
	 */
	public List<MsgInfoBean> getMsgInfo()throws Exception;
	
	/**
	 * 根据ID获取审批意见列表
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<MsgInfoBean> getMsgInfoApprove(String id)throws Exception;
}
