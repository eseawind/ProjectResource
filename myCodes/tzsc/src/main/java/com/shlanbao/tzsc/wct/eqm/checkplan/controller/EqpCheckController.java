package com.shlanbao.tzsc.wct.eqm.checkplan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmCheckcatParam;
import com.shlanbao.tzsc.base.mapping.EqmCullRecordBean;
import com.shlanbao.tzsc.base.mapping.EqmProtectRecord;
import com.shlanbao.tzsc.base.mapping.EqmSpotchRecode;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.paul.beans.EqmPaulDayBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmCheckParamBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmShiftDataBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckRecordBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.service.EqpCheckServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 
* @ClassName: EqpCheckController 
* @Description: 点检
*
 */
@Controller
@RequestMapping("/wct/eqm/checkplan")
public class EqpCheckController extends BaseController {
	
	@Autowired
	private EqpCheckServiceI eqpCheckService;
	protected Logger log = Logger.getLogger(this.getClass());
	/*
	 * 查询所有轮保
	 */
	@RequestMapping("getEqpList")
	@ResponseBody
	private DataGrid getAll(EqpCheckBean eqmWCPBean,int pageIndex ) throws Exception{
		return eqpCheckService.getEqpList(eqmWCPBean, pageIndex);
	}
	/**
	 * 查询计划
	 * @param bean
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("queryPlanList")
	@ResponseBody
	public DataGrid queryPlanList(EqmCheckParamBean bean,int pageIndex){
		return eqpCheckService.queryPlanList(bean,pageIndex);
	}
	
	@RequestMapping("queryParamList")
	@ResponseBody
	public DataGrid queryParamList(EqmCheckParamBean bean,int pageIndex){
		return eqpCheckService.queryParamList(bean,pageIndex);
	}
	
	@RequestMapping("updateStatus")
	@ResponseBody
	public boolean updateBean(String id,HttpSession session){
		EqmCheckcatParam bean=eqpCheckService.findById(id);
		bean.setActualTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
		bean.setEnable("1");
		LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");
		if(loginBean==null){
			return false;
		}
		bean.setUser(new SysUser(loginBean.getUserId()));
		return eqpCheckService.updateBean(bean);
	}
	
	@RequestMapping("queryParts")
	@ResponseBody
	public DataGrid queryParts(EqmCheckParamBean bean,PageParams pageParams){
		try {
			return eqpCheckService.queryParts(bean,pageParams);
		} catch (Exception e) {
			log.error("queryParts is error:"+e.getMessage());
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
				EqmCheckParamBean[] eqpBean = (EqmCheckParamBean[]) JSONUtil
						.JSONString2ObjectArray(eqpArrayList,EqmCheckParamBean.class);
				eqpCheckService.editBean(eqpBean,login);
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
			plan = eqpCheckService.updatePlanStatus(id,loginBean);
		} catch (Exception e) {
			plan = false;
		}
		return plan;
	}
	
	
	/**
	 * 功能说明：设备日保管理-查询
	 * @param bean
	 * @param pageIndex
	 * @return
	 * 最后修改人：2015.9.25-张璐，将状态给BEAN得isShow，以便页面判定是否显示保养,优化查询代码
	 * @author wchuang
	 * @time 2015年7月8日15:39:57
	 * 
	 */
	@RequestMapping("queryProtectList")
	@ResponseBody
	public DataGrid queryProtectList(HttpSession session,EqmPaulDayBean bean,int pageIndex){
		return eqpCheckService.queryProtectList(session,bean,pageIndex);
	}
	
	
	
	/**
	 * 功能说明：设备日保管理-操作添加
	 * @param id,uname,uId,szTime
	 * @return 
	 * 最后修改人：2015.9.25-张璐，实现将保养计划状态改变，将详细记录插入到历史表中
	 * @author wchuang
	 * @time 2015年7月8日15:39:57
	 * 
	 */
	@RequestMapping("/addEqpProtect")
	@ResponseBody
	public LoginBean addEqpProtect(HttpSession session,String id,String szTime,String id_check,String id_nocheck){
		//session.removeAttribute("loginWctEqmInfo");
		LoginBean bean = new LoginBean();
		try {
			//保存数据
		    EqmProtectRecord epr=new EqmProtectRecord();
			 //获得设备ID
		    LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		    	//epr.setStatus(1); //1-完成  0-未完成
		    	epr.setEqpId(loginBean.getEquipmentId());
				epr.setShift_id(loginBean.getShiftId());;
			    epr.setCreateUserId(loginBean.getUserId());
			    epr.setCreateTime(new Date());
			    epr.setEndUserId(loginBean.getUserId());
			    epr.setEndTime(new Date());
			    epr.setEndUserName(loginBean.getName());
			    epr.setPauldayId(id);
			    //保存日保历史数据
			    eqpCheckService.addEqpProtect(epr,id_check,id_nocheck);
		    //保存剔除记录
		    //addCullRecord(loginBean,szTime,id); 
			bean.setIsSuccess("true");
		} catch (Exception e) {
			bean.setIsSuccess("false");
		}
		return bean;
	}
	
	public void addCullRecord(LoginBean loginBean,String szTime,String id){
		try {
			EqmCullRecordBean eqb= new EqmCullRecordBean();
			 SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			 String sr[]=szTime.split(",");
			 Date st_date=formatter.parse(sr[0]);
			 Date ed_date=formatter.parse(sr[1]);
			 //起始，结束时间
		     eqb.setSt_date(st_date);
		     eqb.setEd_date(ed_date);
	         //日保总剔除时间/分钟
	         long diff = ed_date.getTime()-st_date.getTime(); 
	         int stop_time=(int) (diff/(60 * 1000));
			 eqb.setStop_time(stop_time);
			 eqb.setCid(id);
			 eqb.setTeam_id(loginBean.getTeamId());
			 eqb.setEqp_id(loginBean.getEquipmentId());
			 eqb.setShift_id(loginBean.getShiftId());
			 eqb.setCreate_time(new Date());
			 eqb.setCreate_user_id(loginBean.getUserId());
			 eqb.setCreate_user_name(loginBean.getName());
			 eqb.setUpdate_time(new Date());
			 eqb.setUpdate_user_id(loginBean.getUserId());
			 eqb.setUpdate_user_name(loginBean.getName());
			 eqb.setDel(1);
			 eqb.setType_id("4");
			 eqb.setType_name("设备日保时间");
			 eqb.setRemark("设备日保剔除时间");
			 eqpCheckService.addEqmCullRecord(eqb);
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	
	/**
	 * 功能描述：设备点检管理-查询
	 * @param session
	 * @param ecrbean
	 * @return
	 */
	
	@RequestMapping("/queryTayRecord")
	@ResponseBody
	public String queryTayRecord(HttpSession session,EqpCheckRecordBean ecrbean){
	    try {
	    	//获得设备ID
		    LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");//大登陆
	    	LoginBean loginBean2=(LoginBean)session.getAttribute("loginWctEqmInfo");//小登陆
	    	//获得角色ID
	    	if(loginBean2!=null){
	    		ecrbean.setRoles(loginBean2.getRoles());
	    	}else{
	    		ecrbean.setRoles(loginBean.getRoles());
	    	}
	    	//获得设备ID
	    	ecrbean.setEqp_id(loginBean.getEquipmentId());
	    	//获得班次
	    	ecrbean.setShift_id(loginBean.getShiftId());//班次
			List<EqpCheckRecordBean> list=eqpCheckService.queryTayRecord(ecrbean);
			String json=JSONArray.fromObject(list).toString();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 功能描述：设备点检管理- 点检审核
	 * 
	 * @author wchuang
	 * @time 2015年7月17日9:58:03
	 * @param ecrbean
	 * @return
	 * */
	@RequestMapping("/addEqmSpotchRecord")
	@ResponseBody
	public String addEqmSpotchRecord(HttpSession session,EqpCheckRecordBean ecrbean){
	    try {
	    	LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
	    	LoginBean loginBean2=(LoginBean)session.getAttribute("loginWctEqmInfo");//小登陆
	    	EqmSpotchRecode bean =new EqmSpotchRecode();
	    	if(loginBean2==null){
	    		bean.setCreateUserId(loginBean.getUserId());
		    	bean.setCreateUserName(loginBean.getName());
		    	bean.setUpdateUserId(loginBean.getUserId());
		    	bean.setUpdateUserName(loginBean.getName());
		    	ecrbean.setUserID(loginBean.getUserId());
	    	}else{
	    		bean.setCreateUserId(loginBean2.getUserId());
		    	bean.setCreateUserName(loginBean2.getName());
		    	bean.setUpdateUserId(loginBean2.getUserId());
		    	bean.setUpdateUserName(loginBean2.getName());
		    	ecrbean.setUserID(loginBean2.getUserId());
	    	}
	    	
		    //班次
	    	bean.setShiftName(loginBean.getShift());
	    	bean.setShiftId(new MdShift(loginBean.getShiftId()));
	    	ecrbean.setShift_id(loginBean.getShiftId());
		    //班组
	    	bean.setTeamName(loginBean.getTeam());
	    	bean.setTeamId(loginBean.getTeamId());
	    	ecrbean.setTeam_id(loginBean.getTeamId());
		    //二级数据字典ID
	    	bean.setSetId(ecrbean.getId());
		    //设备类型ID
	    	bean.setEqmId(new MdEquipment(loginBean.getEquipmentId()));
	    	ecrbean.setEqp_id(loginBean.getEquipmentId());
		    //设备名称
	    	bean.setEqpName(loginBean.getEquipmentName());
	    	
	    	
		    String equipmentTypeId=loginBean.getEquipmentTypeId();
	    	eqpCheckService.addEqmSpotchRecord(ecrbean,equipmentTypeId,bean);
	    	return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	
	/**
	 * [功能说明]：设备日保，点击日保养，跳转详细页面
	 * 
	 * @author wanchanghuang
	 * @time  2015年9月8日16:32:46
	 * 
	 * */
	@RequestMapping("gotoEqpProtectInfo")
	public String gotoEqpProtectInfo(String cid,String id,String szTime,HttpServletRequest request){
		request.setAttribute("cid", cid);
		request.setAttribute("id", id);
		request.setAttribute("szTime", szTime);
		return "/wct/eqm/eqp_protect_info";
	}
	
	
	/**
	 * [功能说明]：设备日保，点击日保养，跳转详细页面 -查询 
	 * 
	 * @author wanchanghuang
	 * @time  2015年9月8日16:32:46
	 * 
	 * */
	@RequestMapping("querySysEqpType")
	@ResponseBody
	public DataGrid querySysEqpType(HttpSession session,SysEqpTypeBean bean,int pageIndex){
		return eqpCheckService.querySysEqpType(session,bean,pageIndex);
	}
	
	/**
	 * [功能说明]：将备品备件记录插入EQM_FIX_REC表-记录表
	 * 
	 * @author wanchanghuang
	 * @time  2015年9月8日16:32:46
	 * 
	 * */
	@RequestMapping("addFixRec")
	@ResponseBody
	public void addFixRec(String all_id,String use_n,String all_num,String trouble_id,String type,int fix_type,HttpSession session){
		try {
			String user_id="";
			String user_name="";
			//获得设备ID
			 LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			//获得人员登录信息
			 LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");
			 if(loginBean2==null){
				 user_id= loginBean.getUserId(); 
				 user_name=loginBean.getName();
			 }else{
				 user_id=loginBean2.getUserId();
				 user_name=loginBean2.getName();
			 }
			 MdEquipment eqp_id = new MdEquipment();
			 MdShift shift_id = new MdShift();
			 eqp_id.setId(loginBean.getEquipmentId());
			 shift_id.setId(loginBean.getShiftId());
			 eqpCheckService.addFixRec(all_id,use_n,all_num,user_name,user_id,eqp_id,shift_id,trouble_id,type,fix_type);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 张璐--2015-10-10
	 * 功能：更换备品备件时，向故障表中添加故障信息
	 * @param session
	 * @param source_id
	 * @param all_id
	 * @param trouble_name
	 * @param description
	 * @return
	 */
	@RequestMapping("/addTrouble")
	public void addTrouble(HttpServletRequest request,HttpServletResponse response,HttpSession session,String source_id,String source,String areaV,String code){
	    try {
	    	//type:1-点检   2-维修呼叫   3-轮保   4-备品备件  5-检修 ,跳转页面用
	    	if(null!=areaV&&!"".equals(areaV)){
	    		areaV=areaV.substring(1, areaV.length()-1);
		    	areaV=areaV.replaceAll("\n\n", "；");
		    	code=code.substring(1, code.length()-1);
		    	code=code.replaceAll("\r\r", "；");
		    	List<EqmTroubleBean> list = new ArrayList<EqmTroubleBean>();
				EqmTroubleBean trouble=new EqmTroubleBean();
		    	//获得设备ID
				 LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
				//获得人员登录信息
				 LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");
				 if(loginBean2==null){
					 trouble.setCreate_user_id(loginBean.getUserId());
				 }else {
					 trouble.setCreate_user_id(loginBean2.getUserId());
				 }
				 trouble.setSource(source);//1-点检   2-维修呼叫   3-轮保   4-备品备件  5-检修   对应SOURCE_ID
				 trouble.setSource_id(source_id);
				 trouble.setDescription(areaV);
				 trouble.setTrouble_name(code);
				 trouble.setShift_id(loginBean.getShiftId());
				 trouble.setEqu_id(loginBean.getEquipmentId());
				 trouble.setCreate_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				 list.add(trouble);
				 eqpCheckService.addTrouble(list);
				 /*if(type==1){//1-点检   2-维修呼叫   3-轮保   4-备品备件  5-检修 
				    	//return "/wct/eqm/eqp_spotch_list"; //点检
				    }else if(type==2){
				    	//return "/wct/eqm/eqp_spotch_list";//维修呼叫
				    }else if(type==3){
				    	String url="/tzsc/wct/eqm/eqp_spotch_list.jsp";
				    	response.sendRedirect(url);
				    	request.getRequestDispatcher(url).forward(request, response);
				    	//return null;//轮保 
				    }else if(type==4){
				    	//return "/wct/eqm/eqp_spotch_list";//备品备件
				    }else if(type==5){
				    	//return "/wct/eqm/eqp_spotch_list";//检修 
				    }*/
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return null;
	}
	/**
	 * 查询故障表，用作故障下拉框
	 * 张璐
	 * 2015-10-10
	 * @return 
	 * @return
	 */
	@RequestMapping("/queryTroubleComb")
	@ResponseBody
	public  String queryTroubleComb(){
		List<?> list = eqpCheckService.queryTroubleComb();
		String json=JSONArray.fromObject(list).toString();
		return json;
	}
	
	
}
