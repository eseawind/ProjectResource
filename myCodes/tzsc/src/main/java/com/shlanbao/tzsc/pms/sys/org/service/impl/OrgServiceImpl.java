package com.shlanbao.tzsc.pms.sys.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shlanbao.tzsc.base.dao.SysOrganizationDaoI;
import com.shlanbao.tzsc.base.dao.SysOrganizationResourceDaoI;
import com.shlanbao.tzsc.base.dao.SysResourceDaoI;
import com.shlanbao.tzsc.base.dao.SysUserDaoI;
import com.shlanbao.tzsc.base.dao.SysUserOrganizationDaoI;
import com.shlanbao.tzsc.base.mapping.SysOrganization;
import com.shlanbao.tzsc.base.mapping.SysOrganizationResource;
import com.shlanbao.tzsc.base.mapping.SysResource;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.mapping.SysUserOrganization;
import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.org.beans.OrgBean;
import com.shlanbao.tzsc.pms.sys.org.service.OrgServiceI;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 组织机构业务实现类
 * @author Leejean
 * @create 2014年9月16日上午10:41:43
 */
@Service
public class OrgServiceImpl extends BaseService implements OrgServiceI {
	@Autowired
	private SysOrganizationDaoI sysOrganizationDao;
	@Autowired
	private SysOrganizationResourceDaoI sysOrganizationResourceDao;	
	@Autowired
	private SysResourceDaoI sysResourceDao;	
	@Autowired
	private SysUserDaoI sysUserDao;
	@Autowired
	private SysUserOrganizationDaoI sysUserOrganizationDao;
	@Override
	public List<OrgBean> getAllUnDelOrgs() throws Exception {
		String hql="from SysOrganization o where o.del=0 order by o.seq asc";
		List<SysOrganization> sysOrganizations=sysOrganizationDao.query(hql);
		List<OrgBean> orgBeans= new ArrayList<OrgBean>();
		for (SysOrganization sysOrganization : sysOrganizations) {
			OrgBean orgBean=BeanConvertor.copyProperties(sysOrganization, OrgBean.class);
			if(sysOrganization.getSysOrganization()!=null){
				orgBean.setPid(sysOrganization.getSysOrganization().getId());
				orgBean.setPname(sysOrganization.getSysOrganization().getName());
			}
			orgBeans.add(orgBean);
		}
		return orgBeans;
	}
	@Override
	public List<OrgBean> getAllEnableOrgs() throws Exception {
		String hql="from SysOrganization o where o.del=0 and enable=1 order by o.seq asc";
		List<SysOrganization> sysOrganizations=sysOrganizationDao.query(hql);
		List<OrgBean> orgBeans= new ArrayList<OrgBean>();
		for (SysOrganization sysOrganization : sysOrganizations) {
			OrgBean orgBean=BeanConvertor.copyProperties(sysOrganization, OrgBean.class);
			if(sysOrganization.getSysOrganization()!=null){
				orgBean.setPid(sysOrganization.getSysOrganization().getId());
				orgBean.setPname(sysOrganization.getSysOrganization().getName());
			}
			orgBeans.add(orgBean);
		}
		return orgBeans;
	}
	@Override
	public List<Tree> getAllOrgsSelfNotIn(String id) throws Exception {
		String hql="from SysOrganization o where o.del=0 and o.enable=1 and o.id<>? order by o.seq asc";
		List<SysOrganization> sysOrganizations=sysOrganizationDao.query(hql,id);
		List<Tree> trees= new ArrayList<Tree>();
		for (SysOrganization sysOrganization : sysOrganizations) {
			Tree tree= new Tree();
			tree.setIconCls(sysOrganization.getIconCls());
			tree.setId(sysOrganization.getId());
			tree.setText(sysOrganization.getName());
			if(sysOrganization.getSysOrganization()!=null){
				tree.setPid(sysOrganization.getSysOrganization().getId());
				/*orgBean.setPname(sysOrganization.getSysOrganization().getName());*/
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public List<Tree> getAllOrgsTree() {
		String hql="from SysOrganization o where o.del=0 and o.enable=1 order by o.seq asc";
		List<SysOrganization> sysOrganizations=sysOrganizationDao.query(hql);
		List<Tree> trees = new ArrayList<Tree>();
		for (SysOrganization sysOrganization : sysOrganizations) {
			Tree tree = new Tree();
			tree.setText(sysOrganization.getName());
			tree.setIconCls(sysOrganization.getIconCls());
			tree.setId(sysOrganization.getId());
			if(sysOrganization.getSysOrganization()!=null){
				tree.setPid(sysOrganization.getSysOrganization().getId());
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public Long addOrg(OrgBean OrgBean) throws Exception {
		if(!StringUtil.notNull(OrgBean.getPid())&&sysOrganizationDao.unique("from SysOrganization o where o.del=0 and o.enable=1 and o.sysOrganization=null")
				!=null){
			//查询到未禁用，未删除，父级别为空的机构即为根机构
			return 1L;
		}
		SysOrganization sysOrganization=BeanConvertor.copyProperties(OrgBean, SysOrganization.class);
		if(StringUtil.notNull(OrgBean.getPid())){
			sysOrganization.setSysOrganization(new SysOrganization(OrgBean.getPid()));
		}
		sysOrganizationDao.save(sysOrganization);
		return 0L;
	}

	@Override
	public void deleteOrg(String id) throws Exception {
		sysOrganizationDao.findById(SysOrganization.class, id).setDel(1L);
	}

	@Override
	public void batchDeleteOrg(String ids) throws Exception {
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			this.deleteOrg(id);
		}
	}

	@Override
	public void editOrg(OrgBean OrgBean) throws Exception {
		SysOrganization sysOrganization=sysOrganizationDao.findById(SysOrganization.class, OrgBean.getId());
		BeanConvertor.copyProperties(OrgBean, sysOrganization);
		if(StringUtil.notNull(OrgBean.getPid())){
			sysOrganization.setSysOrganization(new SysOrganization(OrgBean.getPid()));
		}
	}

	@Override
	public OrgBean getOrgById(String id) throws Exception {
		SysOrganization sysOrganization = sysOrganizationDao.findById(SysOrganization.class, id);
		OrgBean orgBean=BeanConvertor.copyProperties(sysOrganization,OrgBean.class);
		if(sysOrganization.getSysOrganization()!=null){
			orgBean.setPid(sysOrganization.getSysOrganization().getId());
			orgBean.setPname(sysOrganization.getSysOrganization().getName());
		}
		return orgBean;
	}
	@Override
	public void assignResourceToOrg(String id, String ids) throws Exception {
		//清除
		sysOrganizationResourceDao.deleteByParams("delete from SysOrganizationResource o where o.sysOrganization.id = ?",id);
		for (String resid : StringUtil.splitToStringList(ids, ",")) {
			sysOrganizationResourceDao.save(new SysOrganizationResource(new SysResource(resid),new SysOrganization(id)));
		}
	}


	@Override
	public List<ResourceBean> getResourcesByOrg(String id) throws Exception {
		//查询所有目录资源
		List<SysResource> menuResources = sysResourceDao.query("from SysResource o where o.del=0 and o.enable=1 and o.typ=1 order by o.seq asc");
		//查询所有功能资源
		List<SysResource> funcResources = sysResourceDao.query("from SysResource o where o.del=0 and o.enable=1 and o.typ=2 order by o.seq asc");
				
		//查询当前机构拥有目录资源
		List<SysResource> orgOwnMenuResources = sysResourceDao.query(
		"select o.sysResource from SysOrganizationResource o where o.sysResource.typ=1 and o.sysOrganization.id = ?",id);
		//查询当前机构拥有目录资源
				List<SysResource> orgOwnFuncResources = sysResourceDao.query(
				"select o.sysResource from SysOrganizationResource o where o.sysResource.typ=2 and o.sysOrganization.id = ?",id);
				
		List<ResourceBean> resourceBeans = new ArrayList<ResourceBean>();
		
		for (SysResource sysResource : menuResources) {
			
			ResourceBean resourceBean = BeanConvertor.copyProperties(sysResource, ResourceBean.class);
			
			if(sysResource.getSysResource() != null){
				resourceBean.setPid(sysResource.getSysResource().getId());
				resourceBean.setPname(sysResource.getSysResource().getText());				
			}
			//判断当前机构是否拥有这个目录权限,如果有checked=true
			resourceBean.setChecked(isAssignedResource(orgOwnMenuResources,sysResource.getId()));
			//设置该目录下面的功能，并判断是否拥有该权限
			resourceBean.setFunctions(fmtFuncsToMenus(sysResource.getId(),funcResources,orgOwnFuncResources));
			
			resourceBeans.add(resourceBean);
		}
		//
		return resourceBeans;
	}
	
	private List<ResourceBean> fmtFuncsToMenus(String menuId,List<SysResource> funcResources,List<SysResource> roleOwnFuncResources) throws Exception{
		if(funcResources == null){
			return null;
		}
		List<ResourceBean> functions = new ArrayList<ResourceBean>();
		for(SysResource func:funcResources){
			if(func.getSysResource().getId().equals(menuId)){//该功能属于这个模块
				ResourceBean funcBean = BeanConvertor.copyProperties(func, ResourceBean.class);				
				//判断当前角色是否拥有这个功能权限
				funcBean.setChecked(isOwnFunc(roleOwnFuncResources,func.getId()));
				functions.add(funcBean);
			}
		}
		
		return functions;
	}
	/**
	 * 判断角色是否拥有该功能权限
	 * @author Leejean
	 * @create 2014年8月26日上午9:44:25
	 * @param roleOwnFuncResources 用户拥有的功能列表
	 * @param id 功能id
	 * @return
	 */
	private boolean isOwnFunc(List<SysResource> roleOwnFuncResources, String id) {
		for (SysResource sysResource : roleOwnFuncResources) {
			if(sysResource.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断角色是否拥有此目录权限
	 * @author Leejean
	 * @create 2014年8月26日上午9:40:16
	 * @param sysResources 目录s
	 * @param id 
	 * @return
	 */
	private boolean isAssignedResource(List<SysResource> roleOwnMenuResources, String id) {
		for (SysResource sysResource : roleOwnMenuResources) {
			if(sysResource.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	/*@Override
	public List<UserBean> getOrgUsers(String id) throws Exception {		
		//获得所有用户
		List<SysUser> allUsers = sysUserDao.query("from SysUser o where o.del=0 and o.enable=1 ");
		//获得属于该机构的用户
		List<SysUser> assignedUsers =sysUserDao.query("select o.sysUser from SysUserOrganization o where o.sysOrganization.id=? ",id);
		
		List<UserBean> userBeans = new ArrayList<UserBean>();
		
		for (SysUser sysUser : allUsers) {
			
			UserBean userBean = new UserBean();
			String uid=sysUser.getId();
			userBean.setId(id);
			userBean.setGender(sysUser.getGender());
			userBean.setEno(sysUser.getEno());
			userBean.setName(sysUser.getName());
			
			userBean.setAssigned(userIsAssigned(assignedUsers,uid));
			
			userBeans.add(userBean);
			
		}
		
		return userBeans;
	}*/
	@Override
	public List<UserBean> getOrgUnAssignedUsers(String oid,String name) throws Exception {
		if(name==null)name="";
		
		String hql= "from SysUser o where o.del=0 and o.enable=1 and (o.name like '%"+name+"%' or o.loginName like '%"+name+"%' or o.eno like '%"+name+"%' ) and o.id not in (select oo.sysUser.id from SysUserOrganization oo where oo.sysOrganization.id=?)";
		//获得所有用户
		List<SysUser> rows = sysUserDao.query(hql,oid);
		
		return BeanConvertor.copyList(rows,UserBean.class);
	}
	/**
	 * 辨别用户是否属于某个机构
	 * @author Leejean
	 * @create 2014年8月28日下午6:47:48
	 * @param assignedUsers 
	 * @param id 用户id
	 * @return
	 */
	/*private boolean userIsAssigned(List<SysUser> assignedUsers, String id) {
		for (SysUser sysUser : assignedUsers) {
			if(sysUser.getId().equals(id)){
				return true;
			}
		}
		return false;
	}*/

	@Override
	public List<UserBean> getOrgAssignedUsers(String oid) throws Exception {		
		String hql= "from SysUserOrganization o where o.sysOrganization.id=?";
		//获得所有用户
		List<SysUser> rows = sysUserDao.query("select o.sysUser ".concat(hql), oid);
		
		return BeanConvertor.copyList(rows,UserBean.class);
	}

	@Override
	public void assignUsersToOrg(String id, String ids) throws Exception {
		//清除
		sysUserOrganizationDao.deleteByParams("delete from SysUserOrganization o where o.sysOrganization.id = ?",id);
		for (String uid : StringUtil.splitToStringList(ids, ",")) {
			sysUserOrganizationDao.save(new SysUserOrganization(new SysUser(uid),new SysOrganization(id)));
		}
	}
	@Override
	public List<Tree> getOrgsByUser(String id) throws Exception {
		//查询所有组织机构
		List<SysOrganization> sysOrganizations=
			sysOrganizationDao.query("from SysOrganization o where o.del=0 and o.enable=1 order by o.seq asc");
		//查询当前用户拥有的组织机构
		List<SysOrganization> organizations =  
			sysOrganizationDao.query("select o.sysOrganization from  SysUserOrganization o where o.sysUser.id = ?",id);
		List<Tree> trees = new ArrayList<Tree>();
		for (SysOrganization organization : sysOrganizations) {
			Tree tree = new Tree();
			tree.setText(organization.getName());
			tree.setId(organization.getId());
			tree.setIconCls(organization.getIconCls());
			tree.setChecked(isAssignedOrg(organizations,organization.getId()));
			if(organization.getSysOrganization()!=null){
				tree.setPid(organization.getSysOrganization().getId());
			}
			trees.add(tree);
		}
		return trees;
	}
	/**
	 * 是否拥有此组织机构
	 * @author Leejean
	 * @create 2014-8-19下午06:36:21
	 * @param sysUserRoles
	 * @param id
	 * @return
	 */
	private boolean isAssignedOrg(List<SysOrganization> organizations, String id) {
		for (SysOrganization organization : organizations) {
			if(organization.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	@Override
	public void assignOrg(String id, String ids) {
		//清除用户所有组织机构
		sysUserOrganizationDao.deleteByParams("delete from SysUserOrganization o where o.sysUser.id = ?",id);
		//批量添加
		for (String oid : StringUtil.splitToStringList(ids, ",")) {			
			sysUserOrganizationDao.save(new SysUserOrganization(new SysUser(id),new SysOrganization(oid)));
		}
	}
	@Override
	public void batchAssignOrg(String uids, String oids) {
		//清除用户所有组织机构
		//批量添加
		for (String uid : StringUtil.splitToStringList(uids, ",")) {
			sysUserOrganizationDao.deleteByParams("delete from SysUserOrganization o where o.sysUser.id = ?",uid);
			for (String oid : StringUtil.splitToStringList(oids, ",")) {
				sysUserOrganizationDao.save(new SysUserOrganization(new SysUser(uid),new SysOrganization(oid)));
			}
		}
	}
}
