package com.shlanbao.tzsc.pms.sys.repairResquest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;
import com.shlanbao.tzsc.pms.sys.repairResquest.service.RepairResquestServiceI;

	@Controller
	@RequestMapping("/pms/fixUser")
	public class RepairResquestController extends BaseController{
		@Autowired
		protected RepairResquestServiceI repairResquestService;
		
		
		@RequestMapping("/goToAddFixUserJsp")
		public String goToAddFixUserJsp(){
			return "/pms/sys/repairResquest/fixUserAdd";
		}
		@RequestMapping("/goToUpdateFixUserJsp")
		public String goToUpdateFixUserJsp(RepairResquestBean repairResquestuserBean,PageParams pageParams,HttpServletRequest request){
			try{
			request.setAttribute("fixUser", repairResquestService.getFixUserById(repairResquestuserBean.getId(),repairResquestuserBean.getUpdateUserId(),repairResquestuserBean.getUpdateUserName()));
			}catch (Exception e){
				log.error(message, e);
			}
			return "/pms/sys/repairResquest/fixUserUpdate";
		}
		
		
	/**
	 * 添加维人员
	 * @param repairResquestuserBean
	 * @param name
	 * @param id
	 * @return
	 * @author 景孟博
	 */
	@ResponseBody
	@RequestMapping("/addFixUser")
	public Json addFixUser(@RequestParam(value = "file", required = false) MultipartFile file,RepairResquestBean repairResquestuserBean,HttpServletRequest request){
		Json json = new Json();
		try{
		repairResquestService.addFixUser(repairResquestuserBean,file);
		json.setMsg("操作成功!");
		json.setSuccess(true);
	} catch (Exception e) {
		json.setMsg("操作失败!");
		json.setSuccess(false);
		log.error(message, e);
	}
		return json;
		}

	/**
	 * 查询维修人员
	 * @param repairResquestuserBean
	 * @param pageParams
	 * @return
	 * @author 景孟博
	 */
	@ResponseBody
	@RequestMapping("/queryFixUser")
	public DataGrid queryFixUser(RepairResquestBean repairResquestuserBean,PageParams pageParams){
		try{
		return repairResquestService.queryFixUser(repairResquestuserBean,pageParams);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 修改维修工信息
	 * @param bean
	 * @author 景孟博
	 */
	@ResponseBody
	@RequestMapping("/updateFixUser")
	public Json updateFixUser(@RequestParam(value = "file", required = false) MultipartFile file,RepairResquestBean bean){
		Json json = new Json();
		try{
		repairResquestService.updateFixUser(bean,file);
		json.setMsg("操作成功!");
		json.setSuccess(true);
	} catch (Exception e) {
		json.setMsg("操作失败!");
		json.setSuccess(false);
		log.error(message,e);
	}
		return json;
	}
	
	/**
	 * @author 景孟博
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFixUser")
	public Json deleteFixUser(String id){
		Json json = new Json();
		try{
			repairResquestService.deleteFixUser(id);
			json.setMsg("操作成功！");
			json.setSuccess(true);
		}catch (Exception e){
			json.setMsg("操作失败！");
			json.setSuccess(false);
			log.error(message, e);
		}
		return json;
	}
	
	
	/**
	 * 
	 * @author 景孟博
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Json batchDelete(String ids){
		Json json = new Json();
		try{
			repairResquestService.batchDelete(ids);
			json.setMsg("操作成功！");
			json.setSuccess(true);
		}catch (Exception e){
			json.setMsg("操作失败！");
			json.setSuccess(false);
			log.error(message,e);
		}
		return json;
	}
	/**
	 *查询wct维修呼叫等待状态的信息
	 *shisihai
	 *2015-12-07
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryAskInfo")
	public List<RepairResquestBean> queryAskInfo(){
		return repairResquestService.queryAskInfo();
	}
	}
