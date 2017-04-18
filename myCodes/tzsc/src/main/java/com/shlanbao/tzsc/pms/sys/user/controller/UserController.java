package com.shlanbao.tzsc.pms.sys.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.pms.sys.org.service.OrgServiceI;
import com.shlanbao.tzsc.pms.sys.resource.service.ResourceServiceI;
import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;
import com.shlanbao.tzsc.pms.sys.role.service.RoleServiceI;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
import com.shlanbao.tzsc.pms.sys.user.service.UserServiceI;
import com.shlanbao.tzsc.utils.tools.MD5Util;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
/**
 * 用户管理控制器
 * <li>@author Leejean
 * <li>@create 2014-6-27下午02:56:31
 */
@Controller
@RequestMapping("/pms/sysUser")
public class UserController extends BaseController{
	@Autowired
	private UserServiceI userService;
	@Autowired
	private OrgServiceI orgService;
	@Autowired
	private ResourceServiceI resourceService;
	@Autowired
	private RoleServiceI roleService;
	/**
	 * 首页
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		try {
			return "/pms/index";
		} catch (Exception e) {
			log.error(message, e);
		}
		return "/error/noResource";
	}
	/**
	 * 退出
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpSession session){
		try {
			session.invalidate();
			return "/pms/sys/user/login";
		} catch (Exception e) {
			log.error(message, e);
		}
		return "/error/noResource";
	}
	/**
	 * 用户登录
	 * @author Leejean
	 * @create 2014-6-27下午03:00:36
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Json login(UserBean userBean,HttpSession session,HttpServletRequest request){
		Json json=new Json();
		try {
			userBean=userService.login(userBean);
			if(userBean!=null){
				String uid=userBean.getId();
				json.setMsg("登陆成功!");
				json.setSuccess(true);
				
				SessionInfo sessionInfo=new SessionInfo();
				sessionInfo.setUser(userBean);
				sessionInfo.setIp(WebContextUtil.getRemoteIp(request));
				//得到用户的机构
				List<Tree> organizations=orgService.getOrgsByUser(userBean.getId());
				sessionInfo.setOrganizations(JSON.toJSONString(organizations));
				
				//得到用户的角色
				List<RoleBean> roles=roleService.getRolesByUser(uid);
				sessionInfo.setRoles(roles);
				
				//得到用户的权限(显示成树，拥有的权限才会checked=true)
				List<Tree> resources = resourceService.getResByUser(uid);
				sessionInfo.setResources(JSON.toJSONString(resources));
				
				Map<String,String> resourcesMap = new HashMap<String,String>();
				for (Tree tree : resources) {
					if(tree.isChecked()){
						resourcesMap.put(tree.getAttributes().toString(), tree.getText());
					}
				}
				sessionInfo.setResourcesMap(resourcesMap);
				
				WebContextUtil.saveObjToSession(session,"sessionInfo", sessionInfo,60*60*8);//保存8小时
			}else{
				json.setMsg("账号密码错误或该用户已被禁用,请重新登录!");
			}
		} catch (Exception e) {
			log.error("用户登录", e);
		}
		return json;
	}

	/**
	 * 锁定用户登录
	 * @author Leejean
	 * @create 2014-7-10下午01:47:16
	 */
	@ResponseBody
	@RequestMapping("/lockUser")
	public void lockUser(HttpSession session){
		try {
			SessionInfo sessionInfo=(SessionInfo)session.getAttribute("sessionInfo");
			sessionInfo.setIslock(true);
		} catch (Exception e) {
			log.error("锁定用户登录", e);
		}
	}
	/**
	 * 用户登录解锁
	 * @author Leejean
	 * @create 2014-7-10下午02:06:47
	 */
	@ResponseBody
	@RequestMapping("/breakLock")
	public Json breakLock(UserBean userBean,HttpSession session){
		Json json=new Json();
		try {
			SessionInfo sessionInfo=(SessionInfo)session.getAttribute("sessionInfo");
			if(sessionInfo.getUser().getPwd().equals(MD5Util.md5(userBean.getPwd()))){
				sessionInfo.setIslock(false);
				json.setMsg("解锁成功");
				json.setSuccess(true);
			}else{
				json.setMsg("密码错误");
			}
		} catch (Exception e) {
			log.error(message, e);
		}
		return json;
	}
	/**
	 * 修改密码
	 * @author Leejean
	 * @create 2014-7-10下午02:16:36
	 */
	@ResponseBody
	@RequestMapping("/editPwd")
	public Json editPwd(UserBean userBean,HttpSession session){
		Json json=new Json();
		try {
			SessionInfo sessionInfo=(SessionInfo)session.getAttribute("sessionInfo");
			UserBean user=sessionInfo.getUser();
			if(user.getPwd().equals(MD5Util.md5(userBean.getOldpwd()))){
				userService.editPwd(user.getId(),userBean.getPwd());
				json.setMsg("修改成功");
				user.setPwd(MD5Util.md5(userBean.getPwd()));
				json.setSuccess(true);
			}else{
				json.setMsg("输入的原始密码错误");
			}
		} catch (Exception e) {
			log.error("修改密码异常", e);
		}
		return json;
	}
	/**
	 * 查询系统用户
	 */
	@ResponseBody
	@RequestMapping("/getAllUser")
	public DataGrid getAllUser(UserBean userBean,PageParams pageParams){
		try {
			return userService.querySysUser(userBean,pageParams);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	@RequestMapping("/goToUserAddJsp")
	public String goToAddUserJsp(){
		return "/pms/sys/user/userAdd";
	}
	/**
	 * 新建用户
	 */
	@ResponseBody
	@RequestMapping("/addSysUser")
	public Json addSysUser(UserBean userBean){
		Json json=new Json();
		try {
			userService.addSysUser(userBean);
			json.setMsg("新增用户成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增用户失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 编辑用户
	 */
	@ResponseBody
	@RequestMapping("/editSysUser")
	public Json editSysUser(UserBean userBean){
		System.out.println(userBean.toString());
		Json json=new Json();
		try {
			userService.editSysUser(userBean);
			json.setMsg("编辑用户成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑用户失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 删除用户
	 */
	@ResponseBody
	@RequestMapping("/deleteSysUser")
	public Json deleteSysUser(String id){
		Json json=new Json();
		try {
			userService.deleteSysUser(id);
			json.setMsg("删除用户成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除用户失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 批量删除用户
	 */
	@ResponseBody
	@RequestMapping("/batchDeleteSysUser")
	public Json batchDeleteSysUser(String ids){
		Json json=new Json();
		try {
			userService.batchDeleteSysUser(ids);
			json.setMsg("批量删除用户成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("批量删除用户失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到用户编辑页面
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@RequestMapping("/goToUserEditJsp")
	public String goToUserEditJsp(String id,HttpServletRequest request){
		try {
			request.setAttribute("checkedUser", userService.getSysUserById(id));
		} catch (Exception e) {
			log.error("跳转到用户编辑页面失败", e);
		}
		return "/pms/sys/user/userEdit";
	}
	/**
	 * 重置密码
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @param id 用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetPwd")
	public Json resetPwd(String id){
		Json json=new Json();
		try {
			userService.editSysUser(new UserBean(id,MD5Util.md5("123456")));
			json.setMsg("重置密码为:123456,为了账户安全,请尽早修改密码!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("批量删除用户失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 重置密码
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @param id 用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/enableOrUnenableSysUser")
	public Json enableOrUnenableSysUser(UserBean userBean){
		Json json=new Json();
		try {
			userService.editSysUser(userBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/*****************用户-角色******************/
	/**
	 * 跳转到用户角色分配页面
	 * @author Leejean
	 * @create 2014-8-18下午09:51:14
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToUserAssignRoleJsp")
	public String goToUserAssignRoleJsp(String id,String name,HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
			request.setAttribute("checkedUserId", id);
			request.setAttribute("checkedUserName",name);
		} catch (Exception e) {
			log.error("跳转到用户角色分配页面失败", e);
		}
		return "/pms/sys/user/assignRole";
	}
	/**
	 * 给用户分配角色
	 * @author Leejean
	 * @create 2014-8-19下午03:28:55
	 * @param id 用户id
	 * @param ids 授权角色Ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignRole")
	public Json assignRole(String id,String ids){
		Json json=new Json();
		try {
			roleService.assignRole(id,ids);
			json.setMsg("角色分配成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("操作分配失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 获得用户角色
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @param id 用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRolesByUser")
	public List<RoleBean> getRolesByUser(String id){		
		try {
			return roleService.getRolesByUser(id);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/**
	 * 跳转到用户批量角色分配页面
	 * @author Leejean
	 * @create 2014-8-18下午09:51:14
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToUserBatchAssignRoleJsp")
	public String goToUserBatchAssignRoleJsp(String ids,String names,HttpServletRequest request){
		try {
			request.setAttribute("checkedUserIds", ids);
			request.setAttribute("checkedUserNames",names);
		} catch (Exception e) {
			log.error("跳转到用户角色分配页面失败", e);
		}
		return "/pms/sys/user/batchAssignRole";
	}
	/**
	 * 用户角色批量分配
	 * @author Leejean
	 * @create 2014-8-19下午07:22:22
	 * @param uids 用户ids
	 * @param rids 角色ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchAssignRole")
	public Json batchAssignRole(String uids,String rids){
		Json json=new Json();
		try {
			roleService.batchAssignRole(uids,rids);
			json.setMsg("用户角色批量分配成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("用户角色批量分配失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/*****************用户-机构******************/
	/**
	 * 跳转到用户组织机构分配页面
	 * @author Leejean
	 * @create 2014-8-18下午09:50:25
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToUserAssignOrgJsp")
	public String goToUserAssignOrgJsp(String id,HttpServletRequest request){
		try {
			request.setAttribute("checkedUserId", id);
		} catch (Exception e) {
			log.error("跳转到用户组织机构分配页面失败", e);
		}
		return "/pms/sys/user/assignOrg";
	}
	/**
	 * 获得用户的机构
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @param id 用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrgsByUser")
	public List<Tree> getOrgsByUser(String id){		
		try {
			return orgService.getOrgsByUser(id);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 给用户分配组织机构
	 * @author Leejean
	 * @create 2014-8-20上午09:27:20
	 * @param id 用户id
	 * @param ids 组织机构id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignOrg")
	public Json assignOrg(String id,String ids){				
		Json json=new Json();
		try {
			orgService.assignOrg(id,ids);
			json.setMsg("分配组织机构成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("分配组织机构失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到用户组织机构批量分配页面
	 * @author Leejean
	 * @create 2014-8-18下午09:50:25
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToUserBatchAssignOrgJsp")
	public String goToUserBatchAssignOrgJsp(String id,HttpServletRequest request){
		try {
			//request.setAttribute("checkedUserId", id);
		} catch (Exception e) {
			log.error("跳转到用户组织机构批量分配页面", e);
		}
		return "/pms/sys/user/batchAssignOrg";
	}
	/**
	 * 用户组织机构批量分配
	 * @author Leejean
	 * @create 2014-8-19下午07:22:22
	 * @param uids 用户ids
	 * @param oids 角色ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchAssignOrg")
	public Json batchAssignOrg(String uids,String oids){
		Json json=new Json();
		try {
			orgService.batchAssignOrg(uids,oids);
			json.setMsg("用户组织机构批量分配成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("用户组织机构分配失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
