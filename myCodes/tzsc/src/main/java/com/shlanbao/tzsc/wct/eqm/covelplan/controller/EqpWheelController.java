package com.shlanbao.tzsc.wct.eqm.covelplan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.eqm.covelplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.wct.eqm.covelplan.beans.EqpWCPBean;
import com.shlanbao.tzsc.wct.eqm.covelplan.service.EqpWheelServiceI;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 
* @ClassName: EqpWheelController 
* @Description: 设备保养
* @author luo
* @date 2015年3月17日 下午4:07:11 
*
 */
@Controller
@RequestMapping("/wct/eqm/covelplan")
public class EqpWheelController extends BaseController {
	
	@Autowired
	private EqpWheelServiceI eqpWheelServiceI;
	protected Logger log = Logger.getLogger(this.getClass());
	/*
	 * 查询所有轮保
	 */
	@RequestMapping("getEqpList")
	@ResponseBody
	private DataGrid getAll(EqpWCPBean eqmWCPBean,int pageIndex ) throws Exception{
		return eqpWheelServiceI.getEqpList(eqmWCPBean, pageIndex);
	}
	/**
	 * 查询计划
	 * @param bean
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("queryPlanList")
	@ResponseBody
	public DataGrid queryPlanList(EqmWheelCovelParamBean bean,int pageIndex){
		return eqpWheelServiceI.queryPlanList(bean,pageIndex);
	}
	@RequestMapping("queryParamList")
	@ResponseBody
	public DataGrid queryParamList(EqmWheelCovelParamBean bean,int pageIndex){
		return eqpWheelServiceI.queryParamList(bean,pageIndex);
	}
	
	@RequestMapping("updateStatus")
	@ResponseBody
	public boolean updateBean(String id,HttpSession session){
		EqmWheelCovelParam bean=eqpWheelServiceI.findById(id);
		bean.setActualTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
		bean.setEnable("1");
		LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");
		if(loginBean==null){
			return false;
		}
		bean.setUser(new SysUser(loginBean.getUserId()));
		return eqpWheelServiceI.updateBean(bean);
	}
	
	@RequestMapping("queryWheelParts")
	@ResponseBody
	public DataGrid queryWheelParts(EqmWheelCovelParamBean bean,PageParams pageParams){
		try {
			return eqpWheelServiceI.queryWheelParts(bean,pageParams);
		} catch (Exception e) {
			log.error("queryWheelParts is error:"+e.getMessage());
		}
		return null;
	}
	/**
	 * 针对 设备做检查
	 * @param eqpArray
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editBean")
	public Json editBean(String eqpArray, HttpServletRequest request) {
		Json json=new Json();
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(request.getSession(),"loginWctEqmInfo");//设备管理员登录
		try {
			String eqpArrayList = request.getParameter("eqpArray");
			if(null!=eqpArrayList){
				// 填充BEAN
				EqmWheelCovelParamBean[] eqpBean = (EqmWheelCovelParamBean[]) JSONUtil
						.JSONString2ObjectArray(eqpArrayList,EqmWheelCovelParamBean.class);
				eqpWheelServiceI.editBean(eqpBean,login);
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("保存失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("updatePlanStatus")
	@ResponseBody
	public boolean updatePlanStatus(String id,HttpSession session){
		boolean plan = false;
		//EqmWheelCovelPlan
		LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");//设备管理员
		try {
			plan = eqpWheelServiceI.updatePlanStatus(id,loginBean);
		} catch (Exception e) {
			plan = false;
		}
		/*EqmWheelCovelParam bean=eqpWheelServiceI.findById(id);
		bean.setActualTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
		bean.setEnable("1");
		LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");
		if(loginBean==null){
			return false;
		}
		bean.setUser(new SysUser(loginBean.getUserId()));*/
		return plan;
	}
	
	/**
	 * 修改维修类型和备注
	 * @param session
	 */
	@RequestMapping("updateCovelParam")
	@ResponseBody
	public void updateCovelParam(String paramId,int fixType,String remarks,String planId){
		try {
		eqpWheelServiceI.updateCovelParam(paramId, fixType, remarks,planId);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于WCT轮保查询，用作修改备注
	 * 张璐-2015.11.2
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryLB")
	public EqmWheelCovelParamBean queryLB(String id){
		EqmWheelCovelParamBean bean = eqpWheelServiceI.queryLB(id);
		return bean;
	}
	
}
