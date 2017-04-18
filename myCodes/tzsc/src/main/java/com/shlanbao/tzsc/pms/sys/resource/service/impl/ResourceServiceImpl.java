package com.shlanbao.tzsc.pms.sys.resource.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SysFavoriteDaoI;
import com.shlanbao.tzsc.base.dao.SysResourceDaoI;
import com.shlanbao.tzsc.base.dao.SysRoleResourceDaoI;
import com.shlanbao.tzsc.base.mapping.SysFavorite;
import com.shlanbao.tzsc.base.mapping.SysResource;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.resource.beans.FavoriteBean;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.resource.service.ResourceServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 资源业务实现类
 * @author Leejean
 * @create 2014年9月16日上午10:42:37
 */
@Service
public class ResourceServiceImpl extends BaseService implements ResourceServiceI{
	@Autowired
	private SysResourceDaoI sysResourceDao;
	@Autowired
	private SysRoleResourceDaoI sysRoleResourceDao;
	@Autowired
	private SysFavoriteDaoI sysFavoriteDao;
	@Override
	public List<Tree> getUserMenus(String uid) throws Exception {
		Map<String,SysResource> map = new HashMap<String, SysResource>();
		
		String hql = "select o.sysResource from SysRoleResource o join o.sysRole.sysUserRoles u "
				   + "where o.sysRole.id in (select ur.sysRole.id from SysUserRole ur where ur.sysUser.id=?) "
				   + "and o.sysResource.typ=1 and o.sysResource.securityLevel <= u.sysUser.securityLevel and o.sysResource.del='0' and o.sysResource.enable='1' ";
		List<SysResource> sysResources = sysResourceDao.query(hql, uid);
		
		for (SysResource resource : sysResources) {
			map.put(resource.getId(), resource);
		}
		
		hql = "select o.sysResource from SysOrganizationResource o join o.sysOrganization.sysUserOrganizations u "
				+ "where o.sysOrganization.id in (select ur.sysOrganization.id from SysUserOrganization ur where ur.sysUser.id=?) "
				+ "and o.sysResource.typ=1 and o.sysResource.securityLevel <= u.sysUser.securityLevel and o.sysResource.del='0' and o.sysResource.enable='1' ";
		
		sysResources = sysResourceDao.query(hql, uid);
		
		for (SysResource resource : sysResources) {
			map.put(resource.getId(), resource);
		}
		
		
		List<Tree> trees = new ArrayList<Tree>();
		
		for (String key : map.keySet()) {
			SysResource resource = map.get(key);
			Tree tree = new Tree();
			tree.setId(resource.getId());
			if (resource.getSysResource() != null) {
				tree.setPid(resource.getSysResource().getId());
			}
			tree.setText(resource.getText());
			tree.setIconCls(resource.getIconCls());
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("href", resource.getUrl());
			tree.setAttributes(attr);
			tree.setSeq(resource.getSeq());
			trees.add(tree);
		}
		Collections.sort(trees);
		return trees;
	}

	@Override
	public List<ResourceBean> queryAllResources() throws Exception {
		List<SysResource> resources=sysResourceDao.query("from SysResource o where o.del=0 order by o.seq asc");
		List<ResourceBean> resourceBeans= new ArrayList<ResourceBean>();
		for (SysResource sysResource : resources) {
			ResourceBean resourceBean=BeanConvertor.copyProperties(sysResource, ResourceBean.class);
			try {
				if(sysResource.getSysResource()!=null){
					resourceBean.setPid(sysResource.getSysResource().getId());
					resourceBean.setPname(sysResource.getSysResource().getText());				
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
			
			resourceBeans.add(resourceBean);
		}
		return resourceBeans;
	}
	@Override
	public ResourceBean getResourceById(String id) throws Exception {
		SysResource sysResource = sysResourceDao.findById(SysResource.class,id);
		ResourceBean resourceBean=BeanConvertor.copyProperties(sysResource,ResourceBean.class);
		if(sysResource.getSysResource()!=null){
			resourceBean.setPid(sysResource.getSysResource().getId());
			resourceBean.setPname(sysResource.getSysResource().getText());				
		}
		return resourceBean;
	}
	@Override
	public List<Tree> getUserFavorites(String id) {
		List<Tree> lt = new ArrayList<Tree>();
		List<SysFavorite> sysFavorites=
			sysFavoriteDao.query(
					"from SysFavorite o join fetch o.sysResource res where  res.del=0 and res.enable=1 and o.sysUser.id= ? ",id);
		for (SysFavorite sysFavorite : sysFavorites) {
			Tree tree = new Tree();
			SysResource sysResource=sysFavorite.getSysResource();
			tree.setText(sysResource.getText());
			tree.setIconCls(sysResource.getIconCls());
			tree.setId(sysResource.getId());
			if(sysResource.getSysResource()!=null){
				tree.setPid(sysResource.getSysResource().getId());
			}
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("href", sysResource.getUrl());
			tree.setAttributes(attr);
			lt.add(tree);
		}
		return lt;
	}
	@Override
	public void addResource(ResourceBean resourceBean) throws Exception {
		SysResource sysResource=BeanConvertor.copyProperties(resourceBean, SysResource.class);
		if(StringUtil.notNull(resourceBean.getPid())){
			sysResource.setSysResource(new SysResource(resourceBean.getPid()));
		}
		sysResourceDao.save(sysResource);			
	}
	@Override
	public void deleteResource(String id) throws Exception {
		sysResourceDao.findById(SysResource.class, id).setDel(1L);
	}
	@Override
	public void batchDeleteResource(String ids) throws Exception {		
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			this.deleteResource(id);
		}
	}
	@Override
	public void editResource(ResourceBean resourceBean) throws Exception {
		SysResource sysResource=sysResourceDao.findById(SysResource.class, resourceBean.getId());
		if(StringUtil.notNull(resourceBean.getPid())){
			sysResource.setSysResource(new SysResource(resourceBean.getPid()));
		}
		BeanConvertor.copyProperties(resourceBean,sysResource);
	}
	@Override
	public void addFavorite(FavoriteBean favoriteBean) throws Exception {
		sysFavoriteDao.save(new SysFavorite(new SysResource(favoriteBean.getRid()), new SysUser(favoriteBean.getUid())));
	}
	@Override
	public void deleteFavorite(FavoriteBean favoriteBean) throws Exception {
		sysFavoriteDao.deleteByParams(
				"delete from SysFavorite o where o.sysUser.id=? and o.sysResource.id=?",
				favoriteBean.getUid(),
				favoriteBean.getRid());
	}
	@Override
	public List<ResourceBean> getAllMenuSelfNotIn(String id) throws Exception {
		List<SysResource> resources=sysResourceDao.query("from SysResource o where o.typ=1 and o.del=0 and o.id<>? order by o.seq asc",id);
		List<ResourceBean> resourceBeans= new ArrayList<ResourceBean>();
		for (SysResource sysResource : resources) {
			ResourceBean resourceBean=BeanConvertor.copyProperties(sysResource, ResourceBean.class);
			if(sysResource.getSysResource()!=null){
				resourceBean.setPid(sysResource.getSysResource().getId());
				resourceBean.setPname(sysResource.getSysResource().getText());				
			}
			resourceBeans.add(resourceBean);
		}
		return resourceBeans;
	}
	@Override
	public List<Tree> getResByUser(String uid) {

		List<Tree> trees = new ArrayList<Tree>();
		Map<String,SysResource> map = new HashMap<String, SysResource>();
		
		String hql = "select o.sysResource from SysRoleResource o join o.sysRole.sysUserRoles u "
				   + "where o.sysRole.id in (select ur.sysRole.id from SysUserRole ur where ur.sysUser.id=?) and o.sysResource.securityLevel <= u.sysUser.securityLevel";
		List<SysResource> sysResources = sysResourceDao.query(hql, uid);
		
		for (SysResource resource : sysResources) {
			map.put(resource.getId(), resource);
		}
		
		hql = "select o.sysResource from SysOrganizationResource o join o.sysOrganization.sysUserOrganizations u "
				+ "where o.sysOrganization.id in (select ur.sysOrganization.id from SysUserOrganization ur where ur.sysUser.id=?) and o.sysResource.securityLevel <= u.sysUser.securityLevel";
		
		sysResources = sysResourceDao.query(hql, uid);
		
		for (SysResource resource : sysResources) {
			map.put(resource.getId(), resource);
		}
		
		//所有权限
		List<SysResource> allResources = sysResourceDao.query("from SysResource o where o.del=0 and o.enable=1");
		
		for (SysResource resource : allResources) {
			Tree tree = new Tree();
			tree.setId(resource.getId());
			if (resource.getSysResource() != null) {
				tree.setPid(resource.getSysResource().getId());
			}
			tree.setText(resource.getText());
			tree.setIconCls(resource.getIconCls());
			if(resource.getTyp()==2){//只有功能才加checked属性,因为父节点的任意一个子节点选中了	，父节点即为实心点选中			
				tree.setChecked(map.containsKey(resource.getId()));//存在checked=true
			}
			if(resource.getTyp()==1){//菜单时，
				tree.setChecked(map.containsKey(resource.getId())&&exsitSubs(map,resource.getId())==0);//存在checked=true
			}
			if(resource.getSysResource()!=null&&resource.getTyp()==1){//除根节点外菜单默认关闭
				tree.setState("closed");
			}
			tree.setAttributes(resource.getUrl());
			trees.add(tree);
		}
		return trees;
	}
	private int exsitSubs(Map<String, SysResource> map, String id) {
		int flag = 0;
		for (String key : map.keySet()) {
			SysResource resource = map.get(key);
			String sid = resource.getId();
			if((!sid.equals(id))
					&&(resource.getSysResource()!=null)
					&&resource.getSysResource().getId().equals(id)){
				flag += 1;
			}
		}
		return flag;
	}
	/**
	 * 是否拥有此资源
	 * @author Leejean
	 * @create 2014-8-19下午06:36:21
	 * @param sysUserRoles
	 * @param id
	 * @return
	 */
	/*private boolean isAssignedRes(List<SysResource> sysResources, String id) {
		for (SysResource sysResource : sysResources) {
			if(sysResource.getId().equals(id)){
				return true;
			}
		}
		return false;
	}*/

}
