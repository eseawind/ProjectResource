package com.shlanbao.tzsc.pms.sys.org.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.Tree;
import com.shlanbao.tzsc.pms.sys.org.beans.OrgBean;
import com.shlanbao.tzsc.pms.sys.org.service.OrgServiceI;
import com.shlanbao.tzsc.pms.sys.resource.beans.ResourceBean;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
/**
 * 组织机构控制器
 * @author Leejean
 * @create 2014年9月16日上午10:40:37
 */
@Controller
@RequestMapping("/pms/sysOrg")
public class OrgController extends BaseController {
	@Autowired
	private OrgServiceI orgService;
	/**
	 * 获得所有组织机构 del=0 enable:-1即为忽略enable条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllOrgs")
	public List<OrgBean> getAllOrgs(){
		try {
			return orgService.getAllUnDelOrgs();
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/getAllOrgsHtml")
	public Json getAllOrgsHtml(){
		Json json = new Json();
		try {
			json.setObj(OrgHtmlUtil.getHtml(orgService.getAllEnableOrgs()));
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 获得所有组织机构 (新增或修改组织机构时，选择父组织机构)del=0 enable=1
	 * @param id 编辑对象id 自己不可以为自己的父id 应过滤
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllOrgsSelfNotIn")
	public List<Tree> getAllOrgsSelfNotIn(String id){
		try {
			return orgService.getAllOrgsSelfNotIn(id);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/**
	 * 获得所有启用的组织机构树
	 * @author Leejean
	 * @create 2014-8-18下午08:36:38
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllOrgsTree")
	public List<Tree> getAllOrgsTree(){		
		try {
			return orgService.getAllOrgsTree();
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/**
	 * 跳转跳转到新增组织机构
	 * @param orgBean
	 * @return
	 */
	@RequestMapping("/goToOrgAddJsp")
	public String goToOrgAddJsp(){		
		return "/pms/sys/organization/orgAdd";
	}
	/**
	 * 跳转跳转到编辑组织机构
	 * @param orgBean
	 * @return
	 */
	@RequestMapping("/goToOrgEditJsp")
	public String goToOrgEditJsp(String id,HttpServletRequest request){		
		try {
			request.setAttribute("checkedOrg", orgService.getOrgById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/sys/organization/orgEdit";
	}
	/**
	 * 添加组织机构
	 * @param orgBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrg")
	public Json addOrg(OrgBean orgBean){
		Json json = new  Json();
		try {
			infoId = orgService.addOrg(orgBean);
			if(infoId==1){
				json.setMsg("添加失败,已经存在根级机构");
			}else{				
				json.setMsg("添加组织机构成功!");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			log.error("添加组织机构失败", e);
			json.setMsg("添加组织机构失败!");
			json.setSuccess(true);
		}
		return json;
	}
	/**
	 * 编辑组织机构
	 * @param orgBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editOrg")
	public Json editOrg(OrgBean orgBean){
		Json json = new  Json();
		try {
			orgService.editOrg(orgBean);
			json.setMsg("编辑组织机构成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("编辑组织机构失败", e);
			json.setMsg("编辑组织机构失败!");
			json.setSuccess(true);
		}
		return json;
	}
	/**
	 * 删除组织机构
	 * @param orgBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteOrg")
	public Json deleteOrg(String id){
		Json json = new  Json();
		try {
			orgService.deleteOrg(id);
			json.setMsg("删除组织机构成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("删除组织机构失败", e);
			json.setMsg("删除组织机构失败!");
			json.setSuccess(true);
		}
		return json;
	}
	/**
	 * 跳转到分配权限页面
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 组织机构id
	 * @return
	 */
	@RequestMapping("/goToAssignResourceJsp")
	public String goToAssignResourceJsp(HttpServletRequest request,String id){
		request.setAttribute("checkedOrgId",id);
		return "/pms/sys/organization/assignResource";
	}
	/**
	 * 根据当前组织机构加载该组织机构拥有的资源列表
	 * @author Leejean
	 * @create 2014年8月25日下午9:40:23
	 * @param id 组织机构id
	 * @return 资源列表
	 */
	@RequestMapping("/getResourcesByOrg")
	@ResponseBody
	public List<ResourceBean> getResourcesByOrg(String id){
		try {
			return orgService.getResourcesByOrg(id);
		} catch (Exception e) {
			log.error("根据当前组织机构加载该组织机构拥有的资源列表失败", e);
		}
		return null;
	}
	/**
	 * 给组织机构配权限
	 * @author Leejean
	 * @create 2014-8-21上午09:04:05
	 * @param id 组织机构id
	 * @param ids 资源id串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignResourceToOrg")
	public Json assignResourceToOrg(String id,String ids){
		Json json = new Json();
		try {
			orgService.assignResourceToOrg(id,ids);
			json.setSuccess(true);
			json.setMsg("给组织机构配权限成功!");
		} catch (Exception e) {
			log.error("给组织机构配权限异常", e);
			json.setSuccess(false);
			json.setMsg("给组织机构配权限失败!");
		}
		return json;
	}
	/**
	 * 跳转到给机构分配用户界面
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 组织机构id
	 * @return
	 */
	@RequestMapping("/goToAssignUserJsp")
	public String goToAssignUserJsp(HttpServletRequest request,String id){
		request.setAttribute("checkedOrgId",id);
		return "/pms/sys/organization/assignUser";
	}
	/**
	 * 给机构分配用户
	 * @author Leejean
	 * @create 2014年8月29日上午11:38:43
	 * @param id 机构id
	 * @param ids 用户ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignUsersToOrg")
	public Json assignUsersToOrg(String id,String ids){
		Json json = new Json();
		try {
			orgService.assignUsersToOrg(id,ids);
			json.setSuccess(true);
			json.setMsg("给机构分配用户成功!");
		} catch (Exception e) {
			log.error("给机构分配用户异常", e);
			json.setMsg("给机构分配用户失败!");
		}
		return json;
	}
	/**
	 * 查询待分配的机构用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 组织机构id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrgUnAssignedUsers")
	public List<UserBean> getOrgUnAssignedUsers(String oid,String name,PageParams pageParams){
		try {
			return orgService.getOrgUnAssignedUsers(oid,name);
		} catch (Exception e) {
			log.error("获得该机构用户分配情况异常", e);
		}
		return null;
	}
	/**
	 * 查询已分配的机构用户
	 * @author Leejean
	 * @create 2014年8月25日下午9:41:58
	 * @param id 组织机构id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrgAssignedUsers")
	public List<UserBean> getOrgAssignedUsers(String oid,UserBean userBean){
		try {
			return orgService.getOrgAssignedUsers(oid);
		} catch (Exception e) {
			log.error("获得该机构用户分配情况异常", e);
		}
		return null;
	}
	
}
