package com.shlanbao.tzsc.isp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.DataHandler;
import com.shlanbao.tzsc.data.runtime.handler.DbOutputOrInputInfos;
import com.shlanbao.tzsc.isp.service.IspServiceI;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
import com.shlanbao.tzsc.pms.sys.user.service.UserServiceI;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
/**
 * ISP系统实时监控controller
 * @author Leejean
 * @create 2015年1月6日下午12:05:04
 */
@Controller
@RequestMapping("/isp")
public class IspController extends BaseController{
	@Autowired
	private IspServiceI ispService;
	@Autowired
	private UserServiceI userService;
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
				//String uid=userBean.getId();
				json.setMsg("登陆成功!");
				json.setSuccess(true);
				
				SessionInfo sessionInfo=new SessionInfo();
				sessionInfo.setUser(userBean);
				sessionInfo.setIp(WebContextUtil.getRemoteIp(request));
				/*//得到用户的机构
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
				*/
				WebContextUtil.saveObjToSession(session,"ispSessionInfo", sessionInfo,60*60*8);//保存8小时
			}else{
				json.setMsg("账号密码错误或该用户已被禁用,请重新登录!");
			}
		} catch (Exception e) {
			log.error("用户登录", e);
		}
		return json;
	}
	//*******************以下法方为/isp/runtimeData.jsp，仅供开发或测试人员分析数据时调用************//
	/**
	 * 根据设备code获取设备实时源数据
	 * @author Leejean
	 * @create 2015年1月12日上午10:23:20
	 * @param code
	 * @return
	 */
	@RequestMapping("/get")
	@ResponseBody
	public EquipmentData get(String code){
		try {
			return ispService.get(code);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new EquipmentData();
	}
	/**
	 * 获取所有设备实时源数据
	 * @author Leejean
	 * @create 2015年1月12日上午10:23:20
	 * @return
	 */
	@RequestMapping("/getAll")
	@ResponseBody
	public List<EquipmentData> getAll(){
		try {
			return ispService.getAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 根据设备code获取设备实时格式化后的数据
	 * @author Leejean
	 * @create 2015年1月12日上午10:23:20
	 * @param code
	 * @return
	 */
	@RequestMapping("/getFormatted")
	@ResponseBody
	public EquipmentData getFormatted(String code){
		try {
			return ispService.getFormatted(code);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new EquipmentData();
	}
	/**
	 * 获取所有设备实时格式化后的数据
	 * @author Leejean
	 * @create 2015年1月12日上午10:23:20
	 * @param code
	 * @return
	 */
	@RequestMapping("/getAllFormatted")
	@ResponseBody
	public List<EquipmentData> getAllFormatted(){
		try {
			return ispService.getAllFormatted();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 获取已经实例化的计算参数
	 * @author Leejean
	 * @create 2015年1月12日上午10:25:16
	 * @return
	 */
	@RequestMapping("/getAllCalcValues")
	@ResponseBody
	public Map<String, Object> getAllCalcValues(){
		try {
			return DataHandler.getCalcValues();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 产出实绩和消耗实绩信息
	 * @author Leejean
	 * @create 2015年1月27日下午4:41:57
	 * @return
	 */
	@RequestMapping("/getDbOutputOrInputInfos")
	@ResponseBody
	public Map<String, DbOutputOrInputInfos>  getDbOutputOrInputInfos(){
		try {
			return DataHandler.getDbOutputOrInputInfos();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
