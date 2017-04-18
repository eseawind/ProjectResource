package com.lanbao.dws.controller.wct.eqpManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.common.init.BaseParams;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.combobox.MdEquipment;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.eqpManager.CosSparePartsBean;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.eqpManager.EqmFixRecBean;
import com.lanbao.dws.model.wct.eqpManager.EqmWheelCovelPlanBean;
import com.lanbao.dws.model.wct.eqpManager.EqmWheelCovelPlanParamBean;
import com.lanbao.dws.model.wct.eqpManager.SysUserBean;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.menu.WctMenu;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.eqpManager.IEqpManagerService;

/**
 * 设备检修
 *
 */
@Controller
@RequestMapping("wct/eqpManager/overhaul")
public class EqpManagerOverhaulController {
	@Autowired
	private IEqpManagerService eqpManagerService;

	@Autowired
	private InitComboboxData initComboboxData;
	
	/******************卷烟机检修*******************************************/
	
	/**
	 * 获取左侧菜单
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath( Model model,WctMenu menu){
		eqpManagerService.queryWctLeftMainMenu(model, menu);
		//返回主页面
		return menu.getMenu_url();
	}
	
	/**
	 * [功能说明]：设备检修-跳转进入页面
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/getResultPagePathForJx")
	public String getResultPagePathForJx( Model model,String url){
		return url;
	}
	
	
	/**
	 * [功能说明]：设备检修-卷烟机检修计划    
	 * @author wanchanghuang 
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerRollerPlan")
	public String gotoEqpManagerRollerPlan(HttpServletRequest request, Model model,String url,EqmWheelCovelPlanBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//验证用户是否登录，如果登录可以查询数据，没有登录跳转登录页面登录
		if( StringUtil.notNull(elbBean.getRoleId()) ){
			LoginBean loginInfo=(LoginBean) request.getSession().getAttribute("loginInfo");
			//初始化
	        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
	        bean.setType(Constants.gdType3);
	        //获得当前设备 
	        bean.setEqp_id(loginInfo.getRollerEquipmentId());
	        //班次id
	        bean.setShift_id(loginInfo.getShiftId());
			//查询保养工单
			PaginationResult<List<EqmWheelCovelPlanBean>> listpd=eqpManagerService.getResultPagePathForJxForRoler(pagination,bean);
			model.addAttribute("planList", listpd.getR());
			wctPage.setMaxCount(pagination.getTotalRows());
			//获得已登录的用户信息
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			model.addAttribute("elbBean", elbBean);
			model.addAttribute("wctPage", wctPage);
		}else{
			model.addAttribute("wctPage", null);
		}
		return url;
	}
	
	/**
	 * [功能说明]：设备检修-卷烟机检修项   
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerRollerParam")
	public String gotoEqpManagerRollerParam( Model model,String url,EqmWheelCovelPlanParamBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//得到role_id
		bean.setRole_id(elbBean.getRoleId());
		List<EqmWheelCovelPlanParamBean> list=eqpManagerService.getResultPagePathForJxForRolerParam(bean);
		model.addAttribute("planParamList", list);
		model.addAttribute("elbBean", elbBean);
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("eqm_wcp_id",bean.getEqm_wcp_id());
		return url;
	}
	
	/**
	 * [功能说明]：设备检修-更换备品备件页
	 * @author wanchanghuang 
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerRolerSpareParts")
	public String gotoEqpManagerRolerSpareParts(Model model,String url,EqmWheelCovelPlanParamBean bean,CosSparePartsBean cbean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//初始化
        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
        PaginationResult<List<CosSparePartsBean>> listpd=eqpManagerService.getResultPagePathForJxForRolerSpareParts(pagination,cbean);
		model.addAttribute("sparePartsList", listpd.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		//查询单个项
		//查询备品备件
        if(StringUtil.notNull(bean.getId())){
        	bean=eqpManagerService.queryEqmWheelCovelPlanParamBeanById(bean);
        }
		model.addAttribute("elbBean", elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId()));
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("paramBean", bean);
		model.addAttribute("cbean", cbean);
		return url;
	}
	
	/**
	 * [功能说明]：备品备件保存
	 *    1）修改备品备件库存数量-已使用数量
	 *    2)记录备品备件表 
	 *    3）修改详细维修项状态及备注状态；
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/saveRollerSparePartOrParam")
	public String saveRollerSparePartOrParam(HttpSession session,String str,EqmByLoginBean elbBean,CosSparePartsBean cosspb,
			EqmFixRecBean eqmfrb,EqmWheelCovelPlanParamBean eqmplanPb){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			LoginBean loginInfo=(LoginBean) session.getAttribute("loginInfo");
			elbBean.setShiftId(loginInfo.getShiftId());
			elbBean.setEqpId(loginInfo.getRollerEquipmentId());
			eqmfrb.setSource(Constants.gdType3);
			//修改备品备件数量 
			eqpManagerService.saveSparePartOrParam(str,cosspb,eqmfrb,eqmplanPb,elbBean);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	
	
	/**
	 * [功能说明]：检修项保存
	 *    1）修改检修计划状态
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/updateRollerPlan")
	public String updateRollerPlan(String chk_value,EqmByLoginBean elbBean,EqmWheelCovelPlanBean bean){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			//修改保养工单状态
			eqpManagerService.updatePlanOrParam(elbBean,bean,chk_value);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	
	/**
	 * 功能说明：检修内页登录
	 * @param userId：登录名   jtUserCode:设备电脑登录用户名
	 * @author wanchanghuang
	 * */
	@RequestMapping("/nyLogin")
	@ResponseBody
	public String nyLogin(HttpServletRequest request, Model model,EqmByLoginBean bean){
		EqmByLoginBean elbBean=null;
		try {
			if(StringUtils.isNotEmpty(bean.getJtUserCode())){ //验证查询条件是否为空
				elbBean = Constants.getEqmByLogin(bean.getJtUserCode(),bean.getUserId());
		        if(elbBean==null){ //验证快照是否为空
		        	SysUserBean subBean=eqpManagerService.querySysUserByLoginName(bean);
		        	Map<String,EqmByLoginBean> map= new HashMap<String, EqmByLoginBean>();
		        	if(subBean!=null){ //验证查询用户结果是否为空
		        		elbBean=new EqmByLoginBean();
		        		elbBean.setJtUserCode(bean.getJtUserCode());
			        	elbBean.setUserId(subBean.getId());
			        	elbBean.setUserName(subBean.getName());
			        	elbBean.setRoleId(subBean.getRoleId());
			        	elbBean.setRoleName(subBean.getRoleName());
			        	elbBean.setLoginName(subBean.getLoginName());
			        	//获得设备，班次---
			        	map.put(bean.getJtUserCode(), elbBean);
			        	BaseParams.setMapebb(map);
		        	}
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//session.setAttribute("elbBean", elbBean);
		Gson gson = new Gson(); 
        String json = gson.toJson(elbBean);
		return json;
	}
	
	
	
	
	

	/******************包装机检修*******************************************/
	
	
	/**
	 * [功能说明]：设备检修-包装机检修计划    
	 * @author wanchanghuang 
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerPackerPlan")
	public String gotoEqpManagerPackerPlan(HttpServletRequest request, Model model,String url,EqmWheelCovelPlanBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//验证用户是否登录，如果登录可以查询数据，没有登录跳转登录页面登录
		if( StringUtil.notNull(elbBean.getRoleId()) ){
			LoginBean loginInfo=(LoginBean) request.getSession().getAttribute("loginInfo");
			//初始化
	        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
	        bean.setType(Constants.gdType3);
	        //获得当前设备 
	        bean.setEqp_id(loginInfo.getPackerEquipmentId());
	        //班次id
	        bean.setShift_id(loginInfo.getShiftId());
			//查询保养工单
			PaginationResult<List<EqmWheelCovelPlanBean>> listpd=eqpManagerService.getResultPagePathForJxForRoler(pagination,bean);
			model.addAttribute("planList", listpd.getR());
			wctPage.setMaxCount(pagination.getTotalRows());
			//获得已登录的用户信息
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			model.addAttribute("elbBean", elbBean);
			model.addAttribute("wctPage", wctPage);
		}else{
			model.addAttribute("wctPage", null);
		}
		return url;
	}
	
	/**
	 * [功能说明]：设备检修-包装机检修项   
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerPackerParam")
	public String gotoEqpManagerPackerParam( Model model,String url,EqmWheelCovelPlanParamBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		List<EqmWheelCovelPlanParamBean> list=eqpManagerService.getResultPagePathForJxForRolerParam(bean);
		model.addAttribute("planParamList", list);
		model.addAttribute("elbBean", elbBean);
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("eqm_wcp_id",bean.getEqm_wcp_id());
		return url;
	}
	
	
	
	/**
	 * [功能说明]：设备检修-更换备品备件页(包装机)
	 * @author wanchanghuang 
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerPackerSpareParts")
	public String gotoEqpManagerPackerSpareParts(Model model,String url,EqmWheelCovelPlanParamBean bean,CosSparePartsBean cbean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//初始化
        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
        PaginationResult<List<CosSparePartsBean>> listpd=eqpManagerService.getResultPagePathForJxForRolerSpareParts(pagination,cbean);
		model.addAttribute("sparePartsList", listpd.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		//查询单个项
		//查询备品备件
        if(StringUtil.notNull(bean.getId())){
        	bean=eqpManagerService.queryEqmWheelCovelPlanParamBeanById(bean);
        }
		model.addAttribute("elbBean", elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId()));
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("paramBean", bean);
		model.addAttribute("cbean", cbean);
		return url;
	}
	
	/**
	 * [功能说明]：备品备件保存 -包装机保存
	 *    1）修改备品备件库存数量-已使用数量
	 *    2)记录备品备件表 
	 *    3）修改详细维修项状态及备注状态；
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/savePackerSparePartOrParam")
	public String savePackerSparePartOrParam(HttpSession session,String str,EqmByLoginBean elbBean,CosSparePartsBean cosspb,
			EqmFixRecBean eqmfrb,EqmWheelCovelPlanParamBean eqmplanPb){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			LoginBean loginInfo=(LoginBean) session.getAttribute("loginInfo");
			elbBean.setShiftId(loginInfo.getShiftId());
			elbBean.setEqpId(loginInfo.getPackerEquipmentId());
			eqmfrb.setSource(Constants.gdType3);
			//修改备品备件数量 
			eqpManagerService.saveSparePartOrParam(str,cosspb,eqmfrb,eqmplanPb,elbBean);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	
	
	/**
	 * [功能说明]：检修项保存 -包装机
	 *    1）修改检修计划状态
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/updatePackerPlan")
	public String updatePackerPlan(String chk_value,EqmByLoginBean elbBean,EqmWheelCovelPlanBean bean){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			//修改保养工单状态
			eqpManagerService.updatePlanOrParam(elbBean,bean,chk_value);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	
	
	
	
	
	
	/**************封箱机检修********************/
	/**
	 * [功能说明]：设备润滑-封箱机检修计划    
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerBoxerPlan")
	public String gotoEqpManagerBoxerPlan(HttpServletRequest request, Model model,String url,EqmWheelCovelPlanBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//验证用户是否登录，如果登录可以查询数据，没有登录跳转登录页面登录
		if( StringUtil.notNull(elbBean.getRoleId()) ){
			//初始化
	        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
	        bean.setType(Constants.gdType3);
			//查询保养工单
			PaginationResult<List<EqmWheelCovelPlanBean>> listpd=eqpManagerService.getResultPagePathForJxForRoler(pagination,bean);
			model.addAttribute("planList", listpd.getR());
			wctPage.setMaxCount(pagination.getTotalRows());
			//获得已登录的用户信息
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			model.addAttribute("elbBean", elbBean);
			model.addAttribute("wctPage", wctPage);
		}else{
			model.addAttribute("wctPage", null);
		}
		if(StringUtil.notEmpty(bean.getEqp_code())){
			String eqpName=getMdEquipmentByCode(bean.getEqp_code()).getEquipmentName();
			bean.setEqp_name(eqpName);
		}
		model.addAttribute("chooseParams", bean);
		return url;
	}
	
	/**
	 * [功能说明]：设备润滑-封箱机检修项   
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerBoxerParam")
	public String gotoEqpManagerBoxerParam( Model model,String url,EqmWheelCovelPlanParamBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		List<EqmWheelCovelPlanParamBean> list=eqpManagerService.getResultPagePathForJxForRolerParam(bean);
		model.addAttribute("planParamList", list);
		model.addAttribute("elbBean", elbBean);
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("eqm_wcp_id",bean.getEqm_wcp_id());
		model.addAttribute("chooseParams",bean);
		return url;
	}
	

	/**
	 * [功能说明]：封箱轮检修保存
	 *    1）修改轮保计划状态
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/updateBoxerPlan")
	public String updateBoxerPlan(String chk_value,EqmByLoginBean elbBean,EqmWheelCovelPlanBean bean){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			//修改润滑工单状态及润滑项状态
			eqpManagerService.updatePlanOrParam(elbBean,bean,chk_value);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	//根据设备code查找设备对象（该设备对象放置缓存中）
	public MdEquipment getMdEquipmentByCode(String code){
		List<MdEquipment> list = (List<MdEquipment>) initComboboxData.getComboboxDataByType(ComboboxType.ALLEQPS);
		for(MdEquipment eqm:list){
			if(code.equals(eqm.getEquipmentCode())){
				return eqm;
			}
		}
		return null;
	}
	
	
	
	/**************发射机检修********************/
	/**
	 * [功能说明]：发射机检修计划    
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerLauncherPlan")
	public String gotoEqpManagerLauncherPlan(HttpServletRequest request, Model model,String url,EqmWheelCovelPlanBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//验证用户是否登录，如果登录可以查询数据，没有登录跳转登录页面登录
		if( StringUtil.notNull(elbBean.getRoleId()) ){
			//初始化
	        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
	        bean.setType(Constants.gdType3);
			//查询保养工单
			PaginationResult<List<EqmWheelCovelPlanBean>> listpd=eqpManagerService.getResultPagePathForJxForRoler(pagination,bean);
			model.addAttribute("planList", listpd.getR());
			wctPage.setMaxCount(pagination.getTotalRows());
			//获得已登录的用户信息
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			model.addAttribute("elbBean", elbBean);
			model.addAttribute("wctPage", wctPage);
		}else{
			model.addAttribute("wctPage", null);
		}
		if(StringUtil.notEmpty(bean.getEqp_code())){
			String eqpName=getMdEquipmentByCode(bean.getEqp_code()).getEquipmentName();
			bean.setEqp_name(eqpName);
		}
		model.addAttribute("chooseParams", bean);
		return url;
	}
	
	/**
	 * [功能说明]：发射机检修项   
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerLauncherParam")
	public String gotoEqpManagerLauncherParam( Model model,String url,EqmWheelCovelPlanParamBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		List<EqmWheelCovelPlanParamBean> list=eqpManagerService.getResultPagePathForJxForRolerParam(bean);
		model.addAttribute("planParamList", list);
		model.addAttribute("elbBean", elbBean);
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("eqm_wcp_id",bean.getEqm_wcp_id());
		model.addAttribute("chooseParams",bean);
		return url;
	}
	

	/**
	 * [功能说明]：发射轮检修保存
	 *    1）修改计划状态
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/updateLauncherPlan")
	public String updateLauncherPlan(String chk_value,EqmByLoginBean elbBean,EqmWheelCovelPlanBean bean){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			//修改润滑工单状态及润滑项状态
			eqpManagerService.updatePlanOrParam(elbBean,bean,chk_value);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	
	
	/**************成型机检修********************/
	/**
	 * [功能说明]：成型机检修计划    
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerFilterPlan")
	public String gotoEqpManagerFilterPlan(HttpServletRequest request, Model model,String url,EqmWheelCovelPlanBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		//验证用户是否登录，如果登录可以查询数据，没有登录跳转登录页面登录
		if( StringUtil.notNull(elbBean.getRoleId()) ){
			//初始化
	        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
	        bean.setType(Constants.gdType3);
			//查询保养工单
			PaginationResult<List<EqmWheelCovelPlanBean>> listpd=eqpManagerService.getResultPagePathForJxForRoler(pagination,bean);
			model.addAttribute("planList", listpd.getR());
			wctPage.setMaxCount(pagination.getTotalRows());
			//获得已登录的用户信息
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			model.addAttribute("elbBean", elbBean);
			model.addAttribute("wctPage", wctPage);
		}else{
			model.addAttribute("wctPage", null);
		}
		if(StringUtil.notEmpty(bean.getEqp_code())){
			String eqpName=getMdEquipmentByCode(bean.getEqp_code()).getEquipmentName();
			bean.setEqp_name(eqpName);
		}
		model.addAttribute("chooseParams", bean);
		return url;
	}
	
	/**
	 * [功能说明]：成型机检修项   
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@RequestMapping("/gotoEqpManagerFilterParam")
	public String gotoEqpManagerFilterParam( Model model,String url,EqmWheelCovelPlanParamBean bean,
			EqmByLoginBean elbBean,WctPage wctPage){
		List<EqmWheelCovelPlanParamBean> list=eqpManagerService.getResultPagePathForJxForRolerParam(bean);
		model.addAttribute("planParamList", list);
		model.addAttribute("elbBean", elbBean);
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("eqm_wcp_id",bean.getEqm_wcp_id());
		model.addAttribute("chooseParams",bean);
		return url;
	}
	

	/**
	 * [功能说明]：成型轮检修保存
	 *    1）修改计划状态
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/updateFilterPlan")
	public String updateFilterPlan(String chk_value,EqmByLoginBean elbBean,EqmWheelCovelPlanBean bean){
		try {
			elbBean = Constants.getEqmByLogin(elbBean.getJtUserCode(),elbBean.getUserId());
			//修改润滑工单状态及润滑项状态
			eqpManagerService.updatePlanOrParam(elbBean,bean,chk_value);
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.RESULT_ERROR;
	}
	
}
