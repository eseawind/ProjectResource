package com.shlanbao.tzsc.pms.sys.resource.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.resource.service.ResourceServiceI;
/**
 * 资源管理控制器
 * <li>@author Leejean
 * <li>@create 2014-7-12上午10:03:38
 */
@Controller
@RequestMapping("/pms/sysRes")
public class ResourceController extends BaseController {
	@Autowired
	private ResourceServiceI resourceService;
	/**
	 * 查询用户拥有权限菜单
	 * @author Leejean
	 * @create 2014-7-7下午09:56:08
	 */
	@ResponseBody
	@RequestMapping("/getUserMenus")
	public List<Tree> getUserMenus(HttpSession session){
		try {
			SessionInfo sessionInfo=(SessionInfo) session.getAttribute("sessionInfo");
			return resourceService.getUserMenus(sessionInfo.getUser().getId());
		} catch (Exception e) {
			log.error("查询用户拥有权限菜单", e);
		}
		return null;
	}
	/**
	 * 查询用户收藏夹
	 * @author Leejean
	 * @create 2014-7-7下午10:04:56
	 */
	@ResponseBody
	@RequestMapping("/getUserFavorites")
	public List<Tree> getUsersFavorites(HttpSession session){
		try {
			SessionInfo sessionInfo=(SessionInfo) session.getAttribute("sessionInfo");
			String id= sessionInfo.getUser().getId();
			return resourceService.getUserFavorites(id);
		} catch (Exception e) {
			log.error("查询用户收藏夹", e);
		}
		return null;
	}
	/**
	 * 查询所有资源(菜单,功能)
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllResources")
	public List<ResourceBean> getAllResources(){
		try {
			return resourceService.queryAllResources();
		} catch (Exception e) {
			log.error("查询所有资源(菜单,功能)", e);
		}
		return null;
	}
	/**
	 * 查询所有菜单(不包含自身)用于修改资源时，选择父级资源
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllMenuSelfNotIn")
	public List<ResourceBean> getAllMenuSelfNotIn(String id){
		try {
			return resourceService.getAllMenuSelfNotIn(id);
		} catch (Exception e) {
			log.error("查询所有资源(菜单,功能)", e);
		}
		return null;
	}
	/**
	 * 跳转到新增资源页面
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@RequestMapping("/goToResourceAddJsp")
	public String goToResourceAddJsp(){
		return "/pms/sys/resource/resourceAdd";
	}
	/**
	 * 新增资源
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addResource")
	public Json addResource(ResourceBean resourceBean){
		Json json=new Json();
		try {
			resourceService.addResource(resourceBean);
			json.setMsg("新增资源成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增资源失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到编辑资源页面
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@RequestMapping("/goToResourceEditJsp")
	public String goToResourceEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("checkedRes", resourceService.getResourceById(id));
			return "/pms/sys/resource/resourceEdit";
		} catch (Exception e) {
			log.error("加载资源异常", e);
		}
		return null;
	}
	/**
	 * 编辑资源
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editResource")
	public Json editResource(ResourceBean resourceBean){
		Json json=new Json();
		try {
			resourceService.editResource(resourceBean);
			json.setMsg("编辑资源成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑资源失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 删除资源
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteResource")
	public Json deleteResource(String id){
		Json json=new Json();
		try {
			resourceService.deleteResource(id);
			json.setMsg("删除资源成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除资源失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
