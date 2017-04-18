package com.shlanbao.tzsc.pms.equ.lubricate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: EqmLubricantController 
* @Description: 设备润滑周期设置
* @author luo
* @date 2015年7月8日 下午3:58:32 
*
 */
@Controller
@RequestMapping("/pms/lubrCycle")
public class EqmLubricantController {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EqmLubricantServiceI eqmLubricantServiceI;
	
	@RequestMapping("/goToAddLubiCycle")
	public String goToAddLubiPlan()throws Exception{
		return "/pms/equ/lubriplan/addLubriCycle";
	}
	@RequestMapping("/goToEditLubiCycle")
	public String goToEditLubiPlan(String id,HttpServletRequest request)throws Exception{
		if(StringUtil.notNull(id)){
			EqmLubricantBean bean=eqmLubricantServiceI.getBeanByIds(id);
			request.setAttribute("bean", bean);
			return "/pms/equ/lubriplan/editLubriCycle";
		}else{
			return "";
		}
	}
	
	/**
	 * 设备润滑计划新增
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addLubrCycle")
	public Json addLubrplan(EqmLubricantBean bean,HttpServletRequest request,HttpSession session)throws Exception{
		Json json = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
			bean.setCreate_uId(sessionInfo.getUser().getId());
			request.getSession().getAttribute("");
			eqmLubricantServiceI.addBean(bean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 设备润滑计划修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/editLubrCycle")
	public Json editLubrplan(EqmLubricantBean bean,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try {
			eqmLubricantServiceI.updateBean(bean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/queryLubrCycle")
	public DataGrid queryLubrCycle(EqmLubricantBean bean,PageParams pageParams){
		return eqmLubricantServiceI.searchBean(bean,pageParams);
	}
	
}
