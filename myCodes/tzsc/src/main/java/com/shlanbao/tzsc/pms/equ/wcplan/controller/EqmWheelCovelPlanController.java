package com.shlanbao.tzsc.pms.equ.wcplan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmBubricantfBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanServiceI;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.service.EqmPlanServiceI;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.BatchWCPlan;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCalendar;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelPlanBean;
import com.shlanbao.tzsc.pms.equ.wcplan.service.EqmWheelCovelPlanServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;

@Controller
@RequestMapping("/pms/wcp")
public class EqmWheelCovelPlanController {
	@Autowired
	private EqmWheelCovelPlanServiceI eqmWheelCovelPlanService;
	
	@Autowired
	private EqmLubricantPlanServiceI eqmLubricantPlanServiceI;
	
	@Autowired
	private EqmPlanServiceI eqmPlanService;
	
	
	//private  static RoleIdBean roleIdBean = ;

	@RequestMapping("/goToAddWCPlan")
	public String goToAddWCPlan(HttpServletRequest request,String id){
		//取得一个轮保的计划编号
		long randNo= System.currentTimeMillis();
		request.setAttribute("randNo", randNo);
		return "/pms/equ/wcplan/addWCPlan";
	}
	
	@RequestMapping("/goToQueryWCPlan")
	public String goToQueryWCPlan(HttpServletRequest request,String id){
		return "/pms/equ/wcplan/queryWCPlan";
	}
	@ResponseBody
	@RequestMapping("/auditing")
	public Json auditing(String id,String status){
		Json json = new Json();
		try {
			eqmWheelCovelPlanService.updateWCPlanStatus(id, status);
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
	@RequestMapping("/queryWCPlan")
	public DataGrid queryWCPlan(EqmWheelCovelPlanBean wcpBean,PageParams pageParams,HttpServletRequest request){
		try {
			DataGrid gd = eqmWheelCovelPlanService.queryWCPlan(wcpBean,pageParams);
			return gd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/addWCPlan")
	public Json addWCPlan(EqmWheelCovelPlanBean wcpBean,HttpSession session){
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		try {
			eqmWheelCovelPlanService.addWCPlan(wcpBean, sessionInfo.getUser().getId());
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	
	@RequestMapping("/goToEditWCPlan")
	public String goToEditWCPlan(HttpServletRequest request,String id){
		try {
			request.setAttribute("wcpBean",eqmWheelCovelPlanService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/wcplan/editWCPlan";
	}
	
	@RequestMapping("/goToCheckWCPlan")
	public String goToCheckWCPlan(HttpServletRequest request,String id){
		try {
			request.setAttribute("wcpBean",eqmWheelCovelPlanService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/wcplan/checkWCPlan";
	}
	
	@ResponseBody
	@RequestMapping("/editWCPlan")
	public Json editWCPlan(EqmWheelCovelPlanBean wcpBean){
		Json json = new Json();
		try {
			eqmWheelCovelPlanService.editWCPlan(wcpBean);
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
	@RequestMapping("/deleteWCPlan")
	public Json deleteWCPlan(String id){
		Json json = new Json();
		try {
			eqmWheelCovelPlanService.deleteWCPlan(id);
			json.setMsg("删除设备主数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("删除设备主数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/queryWCPlanCalendar")
	public List<EqmWheelCalendar> queryWCPlanCalendar(HttpServletRequest request) throws Exception{
		String date1=request.getParameter("start");
		String date2=request.getParameter("end");
		return eqmWheelCovelPlanService.queryWCPlanCalendar(date1, date2);
	}
	
	
	
	/**
	 * 设备轮保-批量添加跳转
	 * 
	 * */
	@RequestMapping("/goToAddLubiPlan")
	public String goToAddLubiPlan()throws Exception{
		return "/pms/equ/wcplan/add_wcplan";
	}
	
	/**
	 * 功能说明：设备润滑计划管理-添加
	 * @author wanchanghuang
	 * @time 2015年8月21日16:35:42
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/addEqmLubricatPlan")
	public Json addEqmLubricatPlan(BatchWCPlan b,HttpSession session){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		 Json json = new Json();
		 try {
			//获得两时间段的所有日期
			List<Date> dateLists=findDates(sdf.parse(b.getDate_plan1()),sdf.parse( b.getDate_plan2()));
			//获得选中的所有设备
			EqmBubricantfBean bf=new EqmBubricantfBean();
			bf.setEqp_typeId(b.getEqp_typeId());
			List<?> listMdEquipMent=eqmLubricantPlanServiceI.addEqmLubricatPlan(bf);
			EquipmentsBean equBean = new EquipmentsBean();
			equBean.setQueryEqpTypeId(b.getEqp_category());
			equBean.setQueryType("lb");
			List<?> tf= eqmPlanService.queryEqpTypeChild(b);
			Object[] t=(Object[]) tf.get(0);
			
			Map<String,String> map =eqmPlanService.querySysEqpType(b);
			
			for(Date d :dateLists){
				for(Object o:listMdEquipMent){
					Object[] temp=(Object[]) o;
					EqmWheelCovelPlan ep = new EqmWheelCovelPlan();
					String planNo=DateUtil.datetoStr(new Date(),"yyyyMMddHHmmss.SSSZ")+"LB";
					ep.setPlanNo(planNo);
					ep.setPlanName(temp[3].toString()+"轮保");
					SysUser addUser = new SysUser();
					addUser.setId(sessionInfo.getUser().getId());
					ep.setSysUserByCreateId(addUser);//创建人
					//EQU_ID  //设备主数据
					MdEquipment mdEquipment = new MdEquipment();//设备
					mdEquipment.setId(temp[0].toString());//设备ID,设备主数据中的ID
					ep.setMdEquipment(mdEquipment);
					//ep.setEqmWheelCovelPlan(null);  //
					ep.setScheduleDate(d);
					ep.setScheduleEndDate(d);
					ep.setMaintenanceLength(b.getEquipmentMinute());
					ep.setMaintenanceContent("");
					//班次
					MdShift mf=new MdShift();
					mf.setId(b.getShiftId());
				    ep.setMdShift(mf);
					ep.setPlanner("1");
					ep.setMaintenanceType("lb");
					ep.setWheelCoverType("0");
					ep.setWheelParts(t[0].toString());  //轮保部位
					ep.setDel("0");
					ep.setCreateDate(new Date());
					ep.setMdMatId(b.getMatId());
					String idf=eqmPlanService.saveWcPlanf(ep);
					for (Map.Entry<String, String> entry : map.entrySet()) {  
						String value=entry.getValue();
						String key=entry.getKey();
						if(value.equals(b.getRuleId())){
							EqmWheelCovelParam ecpb=new EqmWheelCovelParam();
							EqmWheelCovelPlan tfe=new EqmWheelCovelPlan();
							tfe.setId(idf);
							ecpb.setPid(tfe);
							ecpb.setPlanTime(sdf.format(d));
							ecpb.setEnable("0");
							ecpb.setSetId(key);
							//保存
							eqmPlanService.saveWcParam(ecpb);
						}
					  
					}  
				}
				
			}
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	//获得两时间段所有时间集合
		public static List<Date> findDates(Date dBegin, Date dEnd) {  
			System.out.println(dBegin+"     "+dEnd);
	        List<Date> lDate = new ArrayList<Date>();  
	        lDate.add(dBegin);  
	        Calendar calBegin = Calendar.getInstance();  
	        // 使用给定的 Date 设置此 Calendar 的时间    
	        calBegin.setTime(dBegin);  
	        Calendar calEnd = Calendar.getInstance();  
	        // 使用给定的 Date 设置此 Calendar 的时间    
	        calEnd.setTime(dEnd);  
	        // 测试此日期是否在指定日期之后    
	        while (dEnd.after(calBegin.getTime())) {  
	            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量    
	            calBegin.add(Calendar.DAY_OF_MONTH, 1);  
	            lDate.add(calBegin.getTime());  
	        }  
	        return lDate;  
	    }  
}
