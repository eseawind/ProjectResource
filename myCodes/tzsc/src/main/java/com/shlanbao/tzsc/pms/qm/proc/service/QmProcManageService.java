package com.shlanbao.tzsc.pms.qm.proc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.proc.beans.QmProcFileBean;
import com.shlanbao.tzsc.pms.qm.proc.beans.QmProcManageBean;
/**
 * 工艺规程业务接口
 * @author luther.zhang
 * @create 2014-12-25
 */
public interface QmProcManageService {
	/**
	 * 查询
	 */
	DataGrid queryList(QmProcManageBean bean,PageParams pageParams) throws Exception;
	/**
	 * 根据ID查询
	 */
	DataGrid queryListById(String id,PageParams pageParams) throws Exception;
	/**
	 * 新建
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param bean
	 * @return
	 */
	String addBean(QmProcManageBean bean) throws Exception;
	/**
	 * 修改
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param bean
	 */
	void editBean(QmProcManageBean bean) throws Exception;
	/**
	 * 删除
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param id
	 */
	void deleteBean(String id) throws Exception;
	/**
	 * 批量删除
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param ids
	 */
	void batchDeleteByIds(String ids) throws Exception;
	/**
	 * 根据id获取对象
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param id
	 * @return
	 */
	QmProcManageBean getBeanById(String id) throws Exception;
	/**
	 * 批量添加
	 * @author luther.zhang
	 * @create 2014-12-25
	 * @param beans
	 * @throws Exception
	 */
	void batchInsert(List<QmProcManageBean> beans) throws Exception;
	
	/**
	 * 文件修改，上传
	 * @param session
	 * @param request
	 * @param fmBean
	 * @throws Exception
	 */
	void fileUpdate(String procManageId,HttpSession session, HttpServletRequest request,
			QmProcFileBean fmBean) throws Exception;
	
	/**
	 * 审核
	 * @author luther.zhang
	 * @create 2014-12-30
	 * @param bean
	 */
	void editReview(String ids,String status,HttpSession session) throws Exception;
	/**
	 * 下发
	 * @author luther.zhang
	 * @create 2014-12-30
	 * @param bean
	 */
	void editSend(String ids,String status,HttpSession session) throws Exception;
	/**
	 * 批量删除
	 * @author luther.zhang
	 * @create 2014-12-30
	 * @param ids
	 */
	void deleteFiles(String ids,HttpSession session) throws Exception;

}
