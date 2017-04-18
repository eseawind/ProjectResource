package com.shlanbao.tzsc.wct.eqm.fix.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.TestAction;
import com.shlanbao.tzsc.wct.eqm.fix.beans.SysMaintenanceStaff;
import com.shlanbao.tzsc.wct.eqm.fix.beans.SysServiceInfo;
import com.shlanbao.tzsc.wct.eqm.fix.beans.WctEqmFixInfoBean;
import com.shlanbao.tzsc.wct.eqm.fix.beans.WctEqmFixRecBean;
import com.shlanbao.tzsc.wct.eqm.fix.service.WctEqmFixRecServiceI;
import com.shlanbao.tzsc.wct.eqm.overhaul.service.EqpOverhaulServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 设备维修记录
 *
 */
@Controller
@RequestMapping("/wct/eqm/fixrec")
public class WctEqmFixRecController extends BaseController{
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected WctEqmFixRecServiceI wctEqmFixRecService;
	@Autowired
	protected EqpOverhaulServiceI eqpOverhaulServiceI;
	
	@ResponseBody
	@RequestMapping("/addFixRec")
	public Json addFixRec(WctEqmFixRecBean eqmFixRecBean,
			HttpServletRequest request, HttpSession session) {
		Json json = new Json();
		try {
			LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");//设备管理员
			wctEqmFixRecService.addFixRec(eqmFixRecBean,loginBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	//查询
	@RequestMapping("getFixRecList")
	@ResponseBody
	public DataGrid getFixRecList(WctEqmFixRecBean bean,PageParams pageParams){
		try {
			return wctEqmFixRecService.getFixRecList(bean,pageParams);
		} catch (Exception e) {
			log.error("WctEqmFixRecController->queryList is error:"+e.getMessage());
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/updateFixRec")
	public Json updateFixRec(WctEqmFixRecBean eqmFixRecBean,HttpServletRequest request, HttpSession session) {
		Json json = new Json();
		try {
			LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");//设备管理员
			wctEqmFixRecService.updateFixRec(eqmFixRecBean,loginBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/updateFixRecFinsh")
	public Json updateFixRecFinsh(WctEqmFixRecBean eqmFixRecBean,HttpServletRequest request, HttpSession session) {
		Json json = new Json();
		try {
			LoginBean loginBean=(LoginBean)session.getAttribute("loginWctEqmInfo");//设备管理员
			wctEqmFixRecService.updateFixRecFinsh(eqmFixRecBean,loginBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * [功能说明]：维修请求-进入页面
	 * type -null  初始查询，
	 * /wct/eqm/repairResquest.jsp
	 * */
	@RequestMapping("/sysStaffPage")
	public String sysStaffPage(HttpServletRequest request,HttpSession session,SysServiceInfo info) {
		LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		info.setEqp_id(loginBean.getEquipmentId());
		List<SysServiceInfo> list = wctEqmFixRecService.querySysServiceInfoAll(info);
		request.setAttribute("listInfo", list);
		request.setAttribute("info", info);
	   return "/wct/eqm/repairResquest";
	}
	/**
	 * 操作工跳转的页面，点检查看故障跳转的页面
	 * @param request
	 * @param session
	 * @param info
	 * @return
	 */
	@RequestMapping("/sysStaffPageCZG")
	public String sysStaffPagCZGe(HttpServletRequest request,HttpSession session,SysServiceInfo info) {
		LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		info.setEqp_id(loginBean.getEquipmentId());
		List<SysServiceInfo> list = wctEqmFixRecService.querySysServiceInfoAll(info);
		request.setAttribute("listInfoCZG", list);
		request.setAttribute("infoCZG", info);
	   return "/wct/eqm/repairResquestCZG";
	}
	/**
	 * [功能说明]：维修请求-呼叫点餐查询页
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * 
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	@RequestMapping("/queryStaffAll")
	@ResponseBody
	public String queryStaffAll(SysMaintenanceStaff ssf, HttpServletRequest request,HttpSession session) {
		Json json = new Json();
		try {
			//获得设备ID
		    LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		    ssf.setShift_id(loginBean.getShiftId());
		    ssf.setTeam_id(loginBean.getTeamId());
		    List<SysMaintenanceStaff> list=	wctEqmFixRecService.queryStaffAll(ssf);
		    String jsonf=JSONArray.fromObject(list).toString();
		    return jsonf;
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return null;
	}
	
	/**
	 * [功能说明]：维修请求-添加呼叫记录
	 * 
	 * @author  wanchanghuang
	 * @time 2015年8月24日15:49:07
	 * 
	 * type_id  1-机械工   2-电气工
	 * 
	 * */
	@RequestMapping("/addServiceInfo")
	@ResponseBody
	public Json addServiceInfo(SysMaintenanceStaff ssf, HttpServletRequest request,HttpSession session) {
		Json json = new Json();
		SysServiceInfo ssInfo = new SysServiceInfo();
		try {
			//获得设备ID
		    LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		    LoginBean loginBean2=(LoginBean)session.getAttribute("loginWctEqmInfo");
		    ssf.setShift_id(loginBean.getShiftId());
		    ssInfo.setShift_id(loginBean.getShiftId());
		    ssf.setTeam_id(loginBean.getTeamId());
		    ssInfo.setTeam_id(loginBean.getTeamId());
		    ssInfo.setStatus("0"); //0-空闲
		    
		    if(loginBean2!=null){
		    	ssInfo.setCreate_user_name(loginBean2.getName());
		    	ssInfo.setCreate_user_id(loginBean2.getUserId());
		    }else{
		    	ssInfo.setCreate_user_name(loginBean.getName());
		    	ssInfo.setCreate_user_id(loginBean.getUserId());
		    }
		    ssInfo.setCreate_user_time(new Date());
		    ssInfo.setTeam_id(loginBean.getTeamId());
		    ssInfo.setEqp_id(loginBean.getEquipmentId());
		    ssInfo.setEqp_name(loginBean.getEquipmentName());
		    //得到指定维修工信息
		    List<SysMaintenanceStaff> list=	wctEqmFixRecService.queryStaffAll(ssf);
		    if(list.size()>0){
		    	SysMaintenanceStaff smsf=list.get(0);
		    	ssInfo.setDesignated_person_id(smsf.getId());
		    	ssInfo.setDesignated_person_name(smsf.getUser_name());
		    	//ssInfo.setDesignated_person_time(new Date());
		    	ssInfo.setType_name(smsf.getType_name());
		    }
		    //获得工单ID
		    List<SchWorkorder> listSchWork=wctEqmFixRecService.queryWorkOrder(ssInfo); 
		    String id=listSchWork.get(0).getId();
		    ssInfo.setWorkorder_id(id);
		    ssInfo.setDel("0");
		    //保存添加,返回该条数据信息
		    String idf=wctEqmFixRecService.saveServcieInfo(ssInfo);
		    RepairResquestBean rb=  wctEqmFixRecService.queryServiceInfoById(idf);
		    //调用推送
		    TestAction.exec(rb);
		    json.setMsg("呼叫成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	
	/**
	 * @author 景孟博
	 * @time 2015年9月29日
	 * @param fixInfoBean
	 * @param all_id
	 * @param use_n
	 * @param all_num
	 * @param session
	 */
	@RequestMapping("/addFix")
	@ResponseBody
	public void addFix(WctEqmFixInfoBean fixInfoBean,String all_id,String use_n,String all_num,HttpSession session){
		try {
			 LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			 LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");
			 if(loginBean2!=null){
			wctEqmFixRecService.addFix(fixInfoBean,all_id,all_num,use_n,loginBean2.getName(),loginBean2.getUserId());
			 }else{
				 wctEqmFixRecService.addFix(fixInfoBean,all_id,all_num,use_n,loginBean.getName(),loginBean.getUserId()); 
			 }
		}catch (Exception e){
			
		}
	}
	
	/**
	 * 维修请求模块受理人改变维修状态
	 * @author 景孟博
	 * @time 2015年9月25日
	 */
	@RequestMapping("/updateSlServiceInfo")
	@ResponseBody
	public void updateSlServiceInfo(String id,String status,String designated_person_id,String designated_person_name,HttpSession session){
		LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");
		if(loginBean2!=null){
		wctEqmFixRecService.updateSlServiceInfo(id,status,designated_person_id,designated_person_name,loginBean2.getName());
		}else{
			wctEqmFixRecService.updateSlServiceInfo(id,status,designated_person_id,designated_person_name,loginBean.getName());
		}
	}
	
	/**
	 * 根据id查询维修人信息
	 * @author 景孟博
	 * @time 2015年9月25日
	 * @param id
	 */
	@RequestMapping("/querySlServiceInfoById")
	@ResponseBody
	public SysServiceInfo querySlServiceInfoById(String id){
		return wctEqmFixRecService.querySlServiceInfoById(id);
	}
	
	/**
	 * 修改维修工工作状态
	 * @author 景孟博
	 * @param status
	 * @param designated_person_id
	 */
	@RequestMapping("/updateStaff")
	@ResponseBody
	public void updateStaff(String status,String designated_person_id){
		try {
		wctEqmFixRecService.updateStaff(status, designated_person_id);
		}catch (Exception e){
			log.error(message, e);
		}
	}
	
	
	/**
	 * 张璐-2015.11.6
	 * WCT手动添加备品备件
	 * @param id
	 * @param trouble_name
	 * @param description
	 * @param useNum
	 * @param type
	 * @param session
	 */
	@RequestMapping("/addNewFix")
	@ResponseBody
	public void addNewFix(String id,String fix_name,String fix_code,String useNum,String type,HttpSession session){
		try {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");
			useNum=useNum.substring(1);
			String[] num=useNum.split(",");
			String[]ids=wctEqmFixRecService.addNewFix(fix_name, fix_code);
			List<EqmFixRec> eqmFixRecs=new ArrayList<EqmFixRec>();
			for(int i=0;i<num.length;i++){
				EqmFixRec e=new EqmFixRec();
				//班次id
				if(loginBean!=null&&StringUtil.notNull(loginBean.getShift())){
					e.setShift_id(new MdShift(loginBean.getShiftId()));
				}
				e.setTrouble_id(id);//设备故障表id
				e.setSource(type);//
				e.setStatus(1);//完成状态
				MdEquipment eqp_id = new MdEquipment();
				eqp_id.setId(loginBean.getEquipmentId());
				e.setEqp_id(eqp_id);//设备id
				//e.setRemark(contentsJ);//检修内容
				e.setMaintaiin_time(new Date());//维修完成时间
				e.setSpare_parts_id(ids[i]);
				e.setSpare_parts_num(Integer.parseInt(num[i]));//使用数量
				if(loginBean2!=null){
					e.setMaintaiin_name(loginBean2.getName());//实际维修人
					e.setMaintaiin_id(loginBean2.getUserId());//维修人id
				}else{
					e.setMaintaiin_name(loginBean.getName());//实际维修人
					e.setMaintaiin_id(loginBean.getUserId());//维修人id
				}
				e.setRemark("1");
				eqmFixRecs.add(e);
			}
			eqpOverhaulServiceI.addBatch(eqmFixRecs);
		}catch (Exception e){
			log.error(message, e);
		}
	}
	/**
	 * [功能说明]：WCT维修呼叫-查看呼叫历史记录故障信息
	 * @author jingmengbo
	 * @crateTime 2015年11月13日17:55:48
	 */
	@RequestMapping("/querySpareTrouble")
	@ResponseBody
	public List<SysServiceInfo> querySpareTrouble(HttpServletRequest request,String id){
		return wctEqmFixRecService.querySpareTrouble(id);
		
	}
}
