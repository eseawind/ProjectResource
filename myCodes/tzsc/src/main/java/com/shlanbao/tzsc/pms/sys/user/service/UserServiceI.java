package com.shlanbao.tzsc.pms.sys.user.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
/**
 * 用户管理业务接口
 * @author Leejean
 * @create 2014年9月16日上午10:43:55
 */
public interface UserServiceI {
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	UserBean login(UserBean user) throws Exception;
	/**
	 * 查询系统用户
	 */
	DataGrid querySysUser(UserBean userBean,PageParams pageParams) throws Exception;
	/**
	 * 修改密码
	 * @author Leejean
	 * @create 2014-7-10下午02:23:56
	 * @param id 用户id
	 * @param pwd 新密码
	 */
	void editPwd(String id, String pwd) throws Exception;
	/**
	 * 新建用户
	 * @author Leejean
	 * @create 2014-8-18上午11:18:29
	 * @param userBean
	 * @return
	 */
	void addSysUser(UserBean userBean) throws Exception;
	/**
	 * 修改用户
	 * @author Leejean
	 * @create 2014-8-18下午05:45:18
	 * @param userBean
	 */
	void editSysUser(UserBean userBean) throws Exception;
	/**
	 * 删除用户
	 * @author Leejean
	 * @create 2014-8-18下午05:45:22
	 * @param id
	 */
	void deleteSysUser(String id) throws Exception;
	/**
	 * 批量删除用户
	 * @author Leejean
	 * @create 2014-8-18下午07:35:41
	 * @param ids
	 */
	void batchDeleteSysUser(String ids) throws Exception;
	/**
	 * 根据用户id获取用户对象
	 * @author Leejean
	 * @create 2014-8-18下午08:02:10
	 * @param id
	 * @return
	 */
	UserBean getSysUserById(String id) throws Exception;
	/**
	 * 批量添加
	 * @author Leejean
	 * @create 2014年9月29日下午4:20:05
	 * @param users
	 * @throws Exception
	 */
	void batchInsert(List<UserBean> users) throws Exception;
}
