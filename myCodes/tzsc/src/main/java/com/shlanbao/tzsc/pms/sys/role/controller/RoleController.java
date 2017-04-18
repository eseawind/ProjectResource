package com.shlanbao.tzsc.pms.sys.role.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;
import com.shlanbao.tzsc.pms.sys.role.service.RoleServiceI;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
/**
 * 角色管理控制器
 * <li>@author Leejean
 * <li>@create 2014-7-12上午10:03:27
 */
@Controller
@RequestMapping("/pms/sysRole")
public class RoleController extends BaseController {
	@Autowired
	private RoleServiceI roleService;
	/**
	 * 查询所有角色
	 * @author Leejean
	 * @create 2014-7-12上午10:03:08
	 */
	@ResponseBody
	@RequestMapping("/getAllRoles")
	public List<RoleBean> getAllRoles(RoleBean roleBean){
		try {
			return roleService.getAllRoles(roleBean);
		} catch (Exception e) {
			log.error("查询角色异常", e);
		}
		return null;
	}
	/**
	 * 调转到角色新增页面
	 * @author Leejean
	 * @create 2014-8-21上午10:40:00
	 * @return
	 */
	@RequestMapping("/goToRoleAddJsp")
	public String goToRoleAddJsp(){
		try {
			return "/pms/sys/role/roleAdd";
		} catch (Exception e) {
			log.error("调转到角色新增页面", e);
			e.printStackTrace();
		}
		return "/error/500";
	}
	/**
	 * 新增角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param roleBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRole")
	public Json addRole(RoleBean roleBean){
		Json json = new Json();
		try {
			roleService.addRole(roleBean);
			json.setSuccess(true);
			json.setMsg("新增角色成功!");
		} catch (Exception e) {
			log.error("新增角色异常", e);
			json.setSuccess(false);
			json.setMsg("新增角色失败!");
		}
		return json;
	}
	/**
	 * 调转到角色编辑页面
	 * @author Leejean
	 * @create 2014-8-21上午10:38:51
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToRoleEditJsp")
	public String goToRoleEditJsp(String id,HttpServletRequest request){
		try {
			request.setAttribute("checkedRole", roleService.getSysRoleById(id));
			return "/pms/sys/role/roleEdit";
		} catch (Exception e) {
			log.error("调转到角色编辑页面失败", e);
			e.printStackTrace();
		}
		return "/error/500";
	}
	/**
	 * 编辑角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param roleBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editRole")
	public Json editRole(RoleBean roleBean){
		Json json = new Json();
		try {
			roleService.editRole(roleBean);
			json.setSuccess(true);
			json.setMsg("编辑角色成功!");
		} catch (Exception e) {
			log.error("编辑角色异常", e);
			json.setSuccess(false);
			json.setMsg("编辑角色失败!");
		}
		return json;
	}
	/**
	 * 删除角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param roleBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteRole")
	public Json deleteRole(String id){
		Json json = new Json();
		try {
			roleService.deleteRole(id);
			json.setSuccess(true);
			json.setMsg("删除角色成功!");
		} catch (Exception e) {
			log.error("删除角色异常", e);
			json.setSuccess(false);
			json.setMsg("删除角色失败!");
		}
		return json;
	}
	/**
	 * 批量删除角色
	 * @author Leejean
	 * @create 2014-8-21上午08:44:45
	 * @param ids ids串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchDeleteRoles")
	public Json batchDeleteRoles(String ids){
		Json json = new Json();
		try {
			roleService.batchDeleteRoles(ids);
			json.setSuccess(true);
			json.setMsg("批量删除角色成功!");
		} catch (Exception e) {
			log.error("批量删除角色异常", e);
			json.setSuccess(false);
			json.setMsg("批量删除角色失败!");
		}
		return json;
	}
	/**
	 * 跳转到分配权限页面
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 角色id
	 * @return
	 */
	@RequestMapping("/goToAssignResourceJsp")
	public String goToAssignResourceJsp(HttpServletRequest request,String id){
		request.setAttribute("checkedRoleId",id);
		return "/pms/sys/role/assignResource";
	}
	/**
	 * 根据当前角色加载该角色拥有的资源列表
	 * @author Leejean
	 * @create 2014年8月25日下午9:40:23
	 * @param id 角色id
	 * @return 资源列表
	 */
	@RequestMapping("/getResourcesByRole")
	@ResponseBody
	public List<ResourceBean> getResourcesByRole(String id){
		try {
			return roleService.getResourcesByRole(id);
		} catch (Exception e) {
			log.error("根据当前角色加载该角色拥有的资源列表失败", e);
		}
		return null;
	}
	@RequestMapping("/goToBatchAssignResourceJsp")
	public String goToBatchAssignResourceJsp(){
		return "/pms/sys/role/batchAssignResource";
	}
	/**
	 * 给角色分配权限
	 * @author Leejean
	 * @create 2014-8-21上午09:04:05
	 * @param id 角色id
	 * @param ids 资源id串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignResourceToRole")
	public Json assignResourceToRole(String id,String ids){
		Json json = new Json();
		try {
			roleService.assignResourceToRole(id,ids);
			json.setSuccess(true);
			json.setMsg("给角色分配权限成功!");
		} catch (Exception e) {
			log.error("给角色分配权限异常", e);
			json.setSuccess(false);
			json.setMsg("给角色分配权限失败!");
		}
		return json;
	}
	/**
	 * 批量给角色分配权限
	 * @author Leejean
	 * @create 2014-8-21上午09:04:05
	 * @param id 角色id串
	 * @param rids 资源id串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchAssignResourceToRoles")
	public Json batchAssignResourceToRoles(String rids,String resids){
		Json json = new Json();
		try {
			roleService.batchAssignResourceToRoles(rids,resids);
			json.setSuccess(true);
			json.setMsg("批量给角色分配权限成功!");
		} catch (Exception e) {
			log.error("批量给角色分配权限异常", e);
			json.setSuccess(false);
			json.setMsg("批量给角色分配权限失败!");
		}
		return json;
	}
	/**
	 * 跳转到给角色分配用户界面
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 角色id
	 * @return
	 */
	@RequestMapping("/goToAssignUserJsp")
	public String goToAssignUserJsp(HttpServletRequest request,String id){
		request.setAttribute("checkedRoleId",id);
		return "/pms/sys/role/assignUser";
	}
	/**
	 * 给角色分配用户
	 * @author Leejean
	 * @create 2014年8月29日上午11:38:43
	 * @param id 角色id
	 * @param ids 用户ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignUsersToRole")
	public Json assignUsersToRole(String id,String ids){
		Json json = new Json();
		try {
			roleService.assignUsersToRole(id,ids);
			json.setSuccess(true);
			json.setMsg("给角色分配用户成功!");
		} catch (Exception e) {
			log.error("给角色分配用户异常", e);
			json.setMsg("给角色分配用户失败!");
		}
		return json;
	}
	/**
	 * 查询待分配的角色用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 角色id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleUnAssignedUsers")
	public List<UserBean> getRoleUnAssignedUsers(String oid,String name,PageParams pageParams){
		try {
			return roleService.getRoleUnAssignedUsers(oid,name);
		} catch (Exception e) {
			log.error("获得该角色用户分配情况异常", e);
		}
		return null;
	}
	/**
	 * 查询已分配的角色用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 角色id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleAssignedUsers")
	public List<UserBean> getRoleAssignedUsers(String oid,UserBean userBean){
		try {
			return roleService.getRoleAssignedUsers(oid);
		} catch (Exception e) {
			log.error("获得该角色用户分配情况异常", e);
		}
		return null;
	}
}
