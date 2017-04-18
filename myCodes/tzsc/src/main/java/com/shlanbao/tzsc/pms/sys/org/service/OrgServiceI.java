package com.shlanbao.tzsc.pms.sys.org.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.pms.sys.org.beans.OrgBean;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
/**
 * 组织机构业务接口
 * @author Leejean
 * @create 2014年9月16日上午10:41:04
 */
public interface OrgServiceI{
	/**
	 * 获得所有未删除组织机构(del=0)
	 * @author Leejean
	 * @create 2014-8-16下午01:49:06
	 * @return
	 */
	List<OrgBean> getAllUnDelOrgs() throws Exception;
	/**
	 * 获得所有未删除,启用组织机构(del=0 && enable=1)
	 * @author Leejean
	 * @create 2014-8-16下午01:49:06
	 * @return
	 */
	List<OrgBean> getAllEnableOrgs() throws Exception;
	/**
	 * 获得所有启用的组织机构树
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @return
	 */
	List<Tree> getAllOrgsTree() throws Exception;
	/**
	 * 新增组织机构
	 * @author Leejean
	 * @create 2014年9月3日上午11:42:27
	 * @param orgBean
	 * @return 1：已经存在根节点,0:正常添加
	 * @throws Exception
	 */
	public Long addOrg(OrgBean orgBean) throws Exception;
	/**
	 * 删除组织机构
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:04
	 * @param id
	 * @throws Exception
	 */
	public void deleteOrg(String id) throws Exception;
	/**
	 * 批量删除组织机构
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:07
	 * @param ids
	 * @throws Exception
	 */
	public void batchDeleteOrg(String ids) throws Exception;
	/**
	 * 编辑组织机构
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:13
	 * @param OrgBean
	 * @throws Exception
	 */
	public void editOrg(OrgBean orgBean) throws Exception;
	/**
	 * 根据id获取组织机构
	 * @param id
	 * @return
	 */
	public OrgBean getOrgById(String id) throws Exception;
	/**
	 * 获得所有组织机构 (新增或修改组织机构时，选择父组织机构)del=0 enable=1
	 * @param id
	 * @return
	 */
	public List<Tree> getAllOrgsSelfNotIn(String id) throws Exception;
	/**
	 * 根据当前组织机构加载该组织机构拥有的资源列表
	 * @author Leejean
	 * @create 2014年8月25日下午9:40:23
	 * @param id 组织机构id
	 * @return 资源列表
	 */
	public List<ResourceBean> getResourcesByOrg(String id) throws Exception;
	/**
	 * 给组织机构配权限
	 * @author Leejean
	 * @create 2014-8-21上午09:04:05
	 * @param id 组织机构id
	 * @param ids 资源id串
	 * @return
	 */
	public void assignResourceToOrg(String id, String ids) throws Exception;
	/**
	 * 查询待分配的机构用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 组织机构id
	 * @return
	 */
	public List<UserBean> getOrgUnAssignedUsers(String oid,String name) throws Exception;
	/**
	 * 查询已分配的机构用户
	 * @author Leejean
	 * @create 2014年8月28日下午6:46:37
	 * @param oid
	 * @param pageParams
	 * @return
	 */
	public List<UserBean> getOrgAssignedUsers(String oid) throws Exception;
	/**
	 * 给机构分配用户
	 * @author Leejean
	 * @create 2014年8月29日上午11:38:43
	 * @param id 机构id
	 * @param ids 用户ids
	 * @return
	 */
	public void assignUsersToOrg(String id, String ids) throws Exception;
	////////////////////////
	/**
	 * 获得用户的机构
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @param id 用户id
	 * @return
	 */
	List<Tree> getOrgsByUser(String id) throws Exception;
	/**
	 * 给用户分配组织机构
	 * @author Leejean
	 * @create 2014-8-20上午09:27:20
	 * @param id 用户id
	 * @param ids 组织机构id
	 * @return
	 */
	void assignOrg(String id, String ids);
	/**
	 * 用户组织机构批量分配
	 * @author Leejean
	 * @create 2014-8-19下午07:22:22
	 * @param uids 用户ids
	 * @param oids 角色ids
	 * @return
	 */
	void batchAssignOrg(String uids, String oids);
}
