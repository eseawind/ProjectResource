package com.shlanbao.tzsc.pms.sys.resource.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.pms.sys.resource.beans.FavoriteBean;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
/**
 * 资源业务类
 * @author Leejean
 * @create 2014年9月16日上午10:42:20
 */
public interface ResourceServiceI {
	/**
	 * 加载用户拥有的菜单
	 * @author Leejean
	 * @create 2014年8月26日上午11:12:25
	 * @param uid 用户id
	 * @return 用户拥有的菜单
	 * @throws Exception
	 */
	public List<Tree> getUserMenus(String uid) throws Exception;
	/**
	 * 查询所有资源(菜单,功能)
	 * @author Leejean
	 * @create 2014-8-14下午07:05:23
	 * @return 资源集合
	 * @throws Exception
	 */
	public List<ResourceBean> queryAllResources() throws Exception;
	/**
	 * 根据id获取资源
	 * @author Leejean
	 * @create 2014-8-15上午08:54:10
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ResourceBean getResourceById(String id) throws Exception;
	/**
	 * 查询用户收藏夹
	 * @author Leejean
	 * @create 2014-8-21下午04:07:28
	 * @param id 用户id
	 * @return
	 */
	public List<Tree> getUserFavorites(String id);
	/**
	 * 新增资源
	 * @author Leejean
	 * @create 2014年8月22日下午7:20:59
	 * @param resourceBean
	 * @throws Exception
	 */
	public void addResource(ResourceBean resourceBean) throws Exception;
	/**
	 * 删除资源
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:04
	 * @param id
	 * @throws Exception
	 */
	public void deleteResource(String id) throws Exception;
	/**
	 * 批量删除资源
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:07
	 * @param ids
	 * @throws Exception
	 */
	public void batchDeleteResource(String ids) throws Exception;
	/**
	 * 编辑资源
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:13
	 * @param resourceBean
	 * @throws Exception
	 */
	public void editResource(ResourceBean resourceBean) throws Exception;
	/**
	 * 新增目录收藏
	 * @author Leejean
	 * @create 2014年8月22日下午7:20:59
	 * @param resourceBean
	 * @throws Exception
	 */
	public void addFavorite(FavoriteBean favoriteBean) throws Exception;
	/**
	 * 删除目录收藏
	 * @author Leejean
	 * @create 2014年8月22日下午7:21:04
	 * @param id
	 * @throws Exception
	 */
	public void deleteFavorite(FavoriteBean favoriteBean) throws Exception;
	/**
	 * 查询所有菜单(不包含自身)用于新增或修改资源时，选择父级资源
	 * @author Leejean
	 * @create 2014年9月3日下午4:11:28
	 * @param id 编辑对象
	 * @return
	 */
	public List<ResourceBean> getAllMenuSelfNotIn(String id) throws Exception;
	
	/////////////////////
	/**
	 * 获得用户可访问资源
	 * @author Leejean
	 * @create 2014年9月1日下午4:49:02
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	List<Tree> getResByUser(String uid);
}

















