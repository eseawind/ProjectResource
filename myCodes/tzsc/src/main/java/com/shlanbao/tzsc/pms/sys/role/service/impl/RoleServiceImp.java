package com.shlanbao.tzsc.pms.sys.role.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shlanbao.tzsc.base.dao.SysResourceDaoI;
import com.shlanbao.tzsc.base.dao.SysRoleDaoI;
import com.shlanbao.tzsc.base.dao.SysRoleResourceDaoI;
import com.shlanbao.tzsc.base.dao.SysUserDaoI;
import com.shlanbao.tzsc.base.dao.SysUserRoleDaoI;
import com.shlanbao.tzsc.base.mapping.SysResource;
import com.shlanbao.tzsc.base.mapping.SysRole;
import com.shlanbao.tzsc.base.mapping.SysRoleResource;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.mapping.SysUserRole;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;
import com.shlanbao.tzsc.pms.sys.role.service.RoleServiceI;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 角色业务实现类
 * @author Leejean
 * @create 2014年9月16日上午10:28:46
 */
@Service
public class RoleServiceImp extends BaseService implements RoleServiceI{
	@Autowired
	private SysRoleDaoI sysRoleDao;	
	@Autowired
	private SysResourceDaoI sysResourceDao;
	@Autowired
	private SysRoleResourceDaoI sysRoleResourceDao;
	@Autowired
	private SysUserDaoI sysUserDao;
	@Autowired
	private SysUserRoleDaoI sysUserRoleDao;
	@Override
	public List<RoleBean> getAllRoles(RoleBean roleBean) throws Exception {
		System.out.println(roleBean.toString());
		List<RoleBean> roleBeans = new ArrayList<RoleBean>();
		String name="";
		if(StringUtil.notNull(roleBean.getName())){
			name = roleBean.getName();
		}
		List<SysRole> roles=sysRoleDao.query("from SysRole o where o.del=0 and o.name like '%"+name+"%' order by o.seq asc");
		for (SysRole role : roles) {
			RoleBean bean=BeanConvertor.copyProperties(role, RoleBean.class);			
			roleBeans.add(bean);
		}
		return roleBeans;
	}
	@Override
	public void addRole(RoleBean roleBean) throws Exception {
		SysRole role=BeanConvertor.copyProperties(roleBean, SysRole.class);
		sysRoleDao.save(role);
	}
	@Override
	public void editRole(RoleBean roleBean) throws Exception {
		SysRole sysRole=sysRoleDao.findById(SysRole.class, roleBean.getId());
		BeanConvertor.copyProperties(roleBean, sysRole);
	}
	@Override
	public void deleteRole(String id) throws Exception {
		sysRoleDao.findById(SysRole.class, id).setDel(1L);
	}
	@Override
	public void batchDeleteRoles(String ids) throws Exception {
		for(String id: StringUtil.splitToStringList(ids, ",")){
			this.deleteRole(id);
		}
	}
	@Override
	public void assignResourceToRole(String id, String resids) throws Exception {
		//清除用户所有角色
		//批量添加
		sysRoleResourceDao.deleteByParams("delete from SysRoleResource o where o.sysRole.id = ?",id);
		for (String resid : StringUtil.splitToStringList(resids, ",")) {
			sysRoleResourceDao.save(new SysRoleResource(new SysRole(id), new SysResource(resid)));
		}
	}
	@Override
	public void batchAssignResourceToRoles(String rids, String resids)
			throws Exception {
		//清除用户所有角色
		//批量添加
		for (String rid : StringUtil.splitToStringList(rids, ",")) {			
			sysRoleResourceDao.deleteByParams("delete from SysRoleResource o where o.sysRole.id = ?",rid);
			for (String resid : StringUtil.splitToStringList(resids, ",")) {
				sysRoleResourceDao.save(new SysRoleResource(new SysRole(rid), new SysResource(resid)));
			}
		}
		
	}
	@Override
	public RoleBean getSysRoleById(String id) throws Exception {
		return BeanConvertor.copyProperties(sysRoleDao.findById(SysRole.class, id),RoleBean.class);
	}
	@Override
	public List<ResourceBean> getResourcesByRole(String id) throws Exception {
		//查询所有目录资源
		List<SysResource> menuResources=sysResourceDao.query("from SysResource o where o.del=0 and o.enable=1 and o.typ=1 order by o.seq asc");
		//查询所有功能资源
		List<SysResource> funcResources=sysResourceDao.query("from SysResource o where o.del=0 and o.enable=1 and o.typ=2 order by o.seq asc");
				
		//查询当前角色拥有目录资源
		List<SysResource> roleOwnMenuResources =  sysResourceDao.query(
		"select o.sysResource from SysRoleResource o where o.sysResource.typ=1 and o.sysRole.id = ?",id);
		//查询当前角色拥有目录资源
				List<SysResource> roleOwnFuncResources =  sysResourceDao.query(
				"select o.sysResource from SysRoleResource o where o.sysResource.typ=2 and o.sysRole.id = ?",id);
				
		List<ResourceBean> resourceBeans = new ArrayList<ResourceBean>();
		
		for (SysResource sysResource : menuResources) {
			
			ResourceBean resourceBean = BeanConvertor.copyProperties(sysResource, ResourceBean.class);
			
			if(sysResource.getSysResource()!=null){
				resourceBean.setPid(sysResource.getSysResource().getId());
				resourceBean.setPname(sysResource.getSysResource().getText());				
			}
			//判断当前角色是否拥有这个目录权限,如果有checked=true
			resourceBean.setChecked(isAssignedResource(roleOwnMenuResources,sysResource.getId()));
			//设置该目录下面的功能，并判断是否拥有该权限
			resourceBean.setFunctions(fmtFuncsToMenus(sysResource.getId(),funcResources,roleOwnFuncResources));
			
			resourceBeans.add(resourceBean);
		}
		//
		return resourceBeans;
	}
	
	private List<ResourceBean> fmtFuncsToMenus(String menuId,List<SysResource> funcResources,List<SysResource> roleOwnFuncResources) throws Exception{
		if(funcResources==null){
			return null;
		}
		List<ResourceBean> functions =new ArrayList<ResourceBean>();
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
	@Override
	public void assignUsersToRole(String id, String ids) throws Exception {
		//清除
		sysUserRoleDao.deleteByParams("delete from SysUserRole o where o.sysRole.id = ?",id);
		for (String uid : StringUtil.splitToStringList(ids, ",")) {
			sysUserRoleDao.save(new SysUserRole(new SysRole(id),new SysUser(uid)));
		}
	}
	@Override
	public List<UserBean> getRoleUnAssignedUsers(String oid, String name)
			throws Exception {
		if(name==null)name="";
		
		String hql= "from SysUser o where o.del=0 and o.enable=1 and (o.name like '%"+name+"%' or o.loginName like '%"+name+"%' or o.eno like '%"+name+"%' ) and o.id not in (select oo.sysUser.id from SysUserRole oo where oo.sysRole.id=?)";
		//获得所有用户
		List<SysUser> rows = sysUserDao.query(hql,oid);
		
		return BeanConvertor.copyList(rows,UserBean.class);
	}
	@Override
	public List<UserBean> getRoleAssignedUsers(String oid) throws Exception {
		String hql= "from SysUserRole o where o.sysRole.id=?";
		//获得所有用户
		List<SysUser> rows = sysUserDao.query("select o.sysUser ".concat(hql), oid);
		
		return BeanConvertor.copyList(rows,UserBean.class);
	}
	@Override
	public List<RoleBean> getRolesByUser(String id) throws Exception {
		//查询所有角色
		List<SysRole> sysRoles=sysRoleDao.query("from SysRole o where o.del=0 and o.enable=1");
		//查询当前用户拥有的角色
		List<SysRole> sysUserRoles =  sysRoleDao.query(
		"select o.sysRole from SysUserRole o where o.sysUser.id = ?",id);
		List<RoleBean> roleBeans = new ArrayList<RoleBean>();
		for (SysRole sysRole : sysRoles) {
			RoleBean roleBean = BeanConvertor.copyProperties(sysRole, RoleBean.class);
			roleBean.setChecked(isAssignedRole(sysUserRoles,sysRole.getId()));
			roleBeans.add(roleBean);
		}
		return roleBeans;
	}
	/**
	 * 是否拥有此角色
	 * @author Leejean
	 * @create 2014-8-19下午06:36:21
	 * @param sysUserRoles
	 * @param id
	 * @return
	 */
	private boolean isAssignedRole(List<SysRole> sysRoles, String id) {
		for (SysRole sysRole : sysRoles) {
			if(sysRole.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	@Override
	public void assignRole(String id, String ids) throws Exception {
		//清除用户所有角色
		sysUserRoleDao.deleteByParams("delete from SysUserRole o where o.sysUser.id = ?",id);
		//批量添加
		for (String rid : StringUtil.splitToStringList(ids, ",")) {			
			sysUserRoleDao.save(new SysUserRole(new SysRole(rid), new SysUser(id)));
		}
	}
	@Override
	public void batchAssignRole(String uids, String rids) throws Exception {		
		//清除用户所有角色
		//批量添加
		for (String uid : StringUtil.splitToStringList(uids, ",")) {			
			sysUserRoleDao.deleteByParams("delete from SysUserRole o where o.sysUser.id = ?",uid);
			for (String rid : StringUtil.splitToStringList(rids, ",")) {
				sysUserRoleDao.save(new SysUserRole(new SysRole(rid), new SysUser(uid)));
			}
		}
	}
}
