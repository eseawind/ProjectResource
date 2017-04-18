package com.lanbao.dws.controller.wct.eqpManager;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.JsonBean;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.eqpManager.CosSparePartsBean;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.eqpManager.EqpMainCallInfo;
import com.lanbao.dws.model.wct.eqpManager.RepairCallLogin;
import com.lanbao.dws.model.wct.eqpManager.RepairUserBean;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.service.wct.eqpManager.IEqpMainCallService;

/**
 * 维修呼叫
 * @author shisihai
 */
@Controller
@RequestMapping("/wct/eqpMainCall")
public class EqpMaintenanceCallController {
	@Autowired
	IEqpMainCallService eqpMainCallService;
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月19日 上午11:00:56 O
	* 功能说明 ：wct 查询维修呼叫记录
	 */
	@RequestMapping("/getEqpMainCallInfo")
	public String getHisQtyPagePath(Model model, String url, WctPage wctPage, EqpMainCallInfo param,HttpSession session) {
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		PaginationResult<List<EqpMainCallInfo>> datas=eqpMainCallService.queryEqpMainCallInfo(pagination, param,session);
		 //设置总条数
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("dataList",datas.getR());
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月19日 下午2:35:36 
	* 功能说明 ：查询机械工、或维修工
	 */
	@RequestMapping("/getMainUsers")
	public String getMainUsers(Model model,  WctPage wctPage,String url, RepairUserBean param,HttpSession session){
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		LoginBean loginbean=(LoginBean) session.getAttribute("loginInfo");
		param.setEqpTypeName(StringUtil.convertObjToString(loginbean.getLoginType()));
		param.setTeamId(loginbean.getTeamId());
		// 查询并填充数据集到model
		PaginationResult<List<RepairUserBean>> datas=eqpMainCallService.queryRepairUsers( param,pagination);
		//设置总行数
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("dataList",datas.getR());
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}

	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月19日 下午9:47:37 
	* 功能说明 ：修改维修工状态
	* 1.选择维修工，修改当前维修工状态
	* 2.新增维修请求记录
	* 3.跳转到维修呼叫主页面，刷新页面数据
	 */
	@RequestMapping("/editRepairUserStatus")
	public String editRepairUserStatus(String url, RepairUserBean param, HttpSession session){
		//1，选择维修工，修改当前维修工状态
		String repairCallLogin=(String) session.getAttribute("repairCallLogInUser");
		eqpMainCallService.updateRepairUserStatus(param,repairCallLogin,session);
		//重新查询记录
		return "redirect:/wct/eqpMainCall/getEqpMainCallInfo.htm?url="+url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月19日 下午10:35:35 
	* 功能说明 ：维修呼叫内登陆
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String repairCallLogin(Model model,RepairCallLogin token, HttpSession session){
		String json=eqpMainCallService.repairCallLogin(token);
		session.setAttribute("repairCallLogInUser", json);
		return json;
	}
	
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午1:43:53 
	* 功能说明 ：受理维修请求（修改受理）
	* 1.修改状态
	 */
	@RequestMapping("/acceptRepair")
	public String acceptRepair(String url, EqpMainCallInfo param){
		eqpMainCallService.acceptRepair(param);
		//重新查询记录
		return "redirect:/wct/eqpMainCall/getEqpMainCallInfo.htm?url="+url;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午2:13:45 
	* 功能说明 ：结束本次维修呼叫
	* ************************************************************
	* 2.更新受理时间和更新故障记录时间，并计算维修用时，单位分钟
	 */
	@RequestMapping("/finishRepair")
	public String finishRepair(String url, EqpMainCallInfo param){
		eqpMainCallService.finishRepair(param);
		//重新查询记录
		return "redirect:/wct/eqpMainCall/getEqpMainCallInfo.htm?url="+url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午3:22:38 
	* 功能说明 ：查询备品备件
	 */
	@RequestMapping("/querySparePart")
	public String querySparePart(Model model,  WctPage wctPage,String url, CosSparePartsBean param){
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		PaginationResult<List<CosSparePartsBean>> datas=eqpMainCallService.getSpareParts(pagination, param);
		//设置总行数
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("dataList",datas.getR());
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午3:45:53 
	* 功能说明 ：
	* 1.更新备品备件
	* 2.添加更换记录
	 */
	@RequestMapping("/saveSparePart")
	@ResponseBody
	public String saveSparePart(String url, String  sourceId, String str){
		 JsonBean json=new JsonBean();
		 int flag=eqpMainCallService.updateSparePartCount(str,sourceId);
		 if(flag==0){
			 json.setFlag(true);
		 }else{
			 json.setMsg("备品备件更换记录保存失败！");
		 }
		 return GsonUtil.bean2Json(json);
	}
	
	
	
	
    /***** 轮保，润滑，检修  维修呼叫“快捷请求”*****************************************************/
	
	/***
	 * 【共能说明】： 快捷跳转
	 * @param url 跳转页
	 * @AUTHOR wanchanbghuang
	 * @data 2016年9月22日7:23:00
	 * */
	@RequestMapping("/callOpenWindow")
	public String callOpenWindow(String url){
		
		return url;
	}
	
	/**
	 * 功能说明 ：查询机械工、或维修工
	* @author wanchanghuang
	* @version 2016年9月22日7:44:49
	 */
	@RequestMapping("/getOpenMainUsers")
	public String getOpenMainUsers(Model model,int eqpNum, String url, RepairUserBean param,EqmByLoginBean elbBean,HttpSession session){
			LoginBean loginbean=(LoginBean) session.getAttribute("loginInfo");
			//登陆类型0卷包机组    1 封箱机组     2成型机组    3发射机组
			//维修工类型  1 卷烟机   2 包装机   3成型机    4 封箱机  5 发射机
			param.setEqpTypeName(StringUtil.convertObjToString((loginbean.getLoginType())));
			param.setTeamId(loginbean.getTeamId());
			// 查询并填充数据集到model
			List<RepairUserBean> datas=eqpMainCallService.queryRepairUsersByTeamId( param);
			model.addAttribute("dataList",datas);
			// 回显条件
			model.addAttribute("chooseParams", param);
			//设置维修呼叫内登陆登陆信息
			//判断如果没有登陆信息，则从session中获取
			if(!StringUtil.notEmpty(elbBean.getJtUserCode())){
				String json=StringUtil.convertObjToString(session.getAttribute("repairCallLogInUser"));
				RepairCallLogin b=GsonUtil.fromJson2Bean(json, RepairCallLogin.class);
				elbBean.setUserName(b.getuName());
				elbBean.setUserId(b.getId());
			}
			model.addAttribute("elbBean",elbBean);
			//设置机台号（对于多机台一块屏）
			model.addAttribute("eqpNum",eqpNum);
		// 返回页面
		return url;
	}
	
	/**
	 * 功能说明：选择维修工，修改状态，且添加维修工记录
	 * @author wanchanghuang
	 * @date 2016年9月22日11:40:31
	 * */
	@RequestMapping("/editOpenRepairUserStatus")
	@ResponseBody
	public String editOpenRepairUserStatus(String url, EqmByLoginBean elbBean,RepairUserBean param,int eqpNum, HttpSession session){
		String ret="false";
		try {
			//1，选择维修工,修改当前维修工状态
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getLoginName());
			//兼容Session登陆
			if(elbBean==null){
				elbBean=new EqmByLoginBean();
				String json=StringUtil.convertObjToString(session.getAttribute("repairCallLogInUser"));
				RepairCallLogin b=GsonUtil.fromJson2Bean(json, RepairCallLogin.class);
				elbBean.setUserName(b.getuName());
				elbBean.setUserId(b.getId());
			}
			eqpMainCallService.updateOpenRepairUserStatus(param,elbBean,session,eqpNum);
			//重新查询记录
			ret="true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
