package com.shlanbao.tzsc.pms.sys.role.service;

import java.util.List;

import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
/**
 * 角色
 * <li>@author Leejean
 * <li>@create 2014-8-18下午10:59:40
 */
public interface RoleServiceI {
	/**
	 * 获得所有角色
	 * @author Leejean
	 * @create 2014-8-18下午10:59:21
	 * @return
	 * @throws Exception
	 */
	public List<RoleBean> getAllRoles(RoleBean roleBean) throws Exception;	
	/**
	 * 新增角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param roleBean
	 * @return
	 */
	public void addRole(RoleBean roleBean) throws Exception;
	/**
	 * 编辑角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param roleBean
	 * @return
	 */
	public void editRole(RoleBean roleBean) throws Exception;
	/**
	 * 删除角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param roleBean
	 * @return
	 */
	public void deleteRole(String id) throws Exception;
	/**
	 * 批量删除角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param ids ids串
	 * @return
	 */
	public void batchDeleteRoles(String ids) throws Exception;
	/**
	 * 给角色分配权限
	 * @author Leejean
	 * @create 2014-8-21上午09:04:05
	 * @param id 角色id
	 * @param ids 资源id串
	 * @return
	 */
	public void assignResourceToRole(String id, String ids) throws Exception;
	/**
	 * 批量给角色分配权限
	 * @author Leejean
	 * @create 2014-8-21上午09:04:05
	 * @param id 角色id串
	 * @param rids 资源id串
	 * @return
	 */
	public void batchAssignResourceToRoles(String rids, String resids) throws Exception;
	/**
	 * 根据角色id得到角色
	 * @author Leejean
	 * @create 2014-8-21上午10:34:22
	 * @param id 角色id
	 * @return
	 */
	public RoleBean getSysRoleById(String id) throws Exception;
	/**
	 * 根据角色获得该角色拥有的资源
	 * @author Leejean
	 * @create 2014年8月25日下午9:25:17
	 * @param id 角色id
	 * @return 资源列表
	 */
	public List<ResourceBean> getResourcesByRole(String id) throws Exception;
	/**
	 * 给角色分配用户
	 * @author Leejean
	 * @create 2014年8月29日上午11:38:43
	 * @param id 角色id
	 * @param ids 用户ids
	 * @return
	 */
	public void assignUsersToRole(String id, String ids) throws Exception;
	/**
	 * 查询待分配的角色用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 角色id
	 * @return
	 */
	public List<UserBean> getRoleUnAssignedUsers(String oid, String name) throws Exception;
	/**
	 * 查询已分配的角色用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 角色id
	 * @return
	 */
	public List<UserBean> getRoleAssignedUsers(String oid) throws Exception;
	//////////
	/**
	 * 获得用户角色
	 * @author Leejean
	 * @create 2014-8-19上午09:34:00
	 * @param id 用户id
	 * @return
	 */
	List<RoleBean> getRolesByUser(String id) throws Exception;
	/**
	 * 给用户分配角色
	 * @author Leejean
	 * @create 2014-8-19下午03:40:28
	 * @param id 用户id
	 * @param ids 角色id
	 */
	void assignRole(String id, String ids) throws Exception;
	/**
	 * 用户角色批量分配
	 * @author Leejean
	 * @create 2014-8-19下午07:22:22
	 * @param uids 用户ids
	 * @param rids 角色ids
	 * @return
	 */
	void batchAssignRole(String uids, String rids) throws Exception;
}
