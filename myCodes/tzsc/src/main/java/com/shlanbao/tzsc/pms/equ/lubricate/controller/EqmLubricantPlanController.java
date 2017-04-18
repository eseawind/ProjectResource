package com.shlanbao.tzsc.pms.equ.lubricate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.EqmLubricant;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmBubricantfBean;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantPlanBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanParamServiceI;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanServiceI;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantServiceI;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpTypeServiceI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: EqmLubricantController 
* @Description: 设备润滑计划 
* @author luo
* @date 2015年7月8日 下午3:58:32 
*
 */
@Controller
@RequestMapping("/pms/lubrplan")
public class EqmLubricantPlanController {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EqmLubricantPlanServiceI eqmLubricantPlanServiceI;
	
	
	@Autowired
	public EqmLubricantServiceI lubricantServiceI;
	
	@Autowired
	public EqmLubricantPlanServiceI lubricantPlanService;
	
	@Autowired
	public EqmLubricantPlanParamServiceI lubricantPlanParamService;
	
	@Autowired
	public SysEqpTypeServiceI sysEqpTypeServiceImpl;
	
	
	@RequestMapping("/goToAddLubiPlan")
	public String goToAddLubiPlan()throws Exception{
		return "/pms/equ/lubriplan/addLubriPlan";
	}
	@RequestMapping("/goToEditLubiPlan")
	public String goToEditLubiPlan(String id,HttpServletRequest request)throws Exception{
		if(StringUtil.notNull(id)){
			EqmLubricantPlanBean bean=eqmLubricantPlanServiceI.getBeanByIds(id);
			request.setAttribute("bean", bean);
			return "/pms/equ/lubriplan/editLubriPlan";
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
	@RequestMapping("/addLubrplan")
	public Json addLubrplan(EqmLubricantPlanBean bean,HttpServletRequest request,HttpSession session)throws Exception{
		Json json = new Json();
		try {
			
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
	@RequestMapping("/editLubrplan")
	public Json editLubrplan(EqmLubricantPlanBean bean,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try {
			eqmLubricantPlanServiceI.updateBean(bean);
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
	@RequestMapping("/queryLubrplan")
	public DataGrid queryLubrplan(EqmLubricantPlanBean bean,PageParams pageParams){
		try {
			return eqmLubricantPlanServiceI.queryDataGrid(bean,pageParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 功能说明：设备润滑计划管理-添加
	 * @author wanchanghuang
	 * @time 2015年8月21日16:35:42
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/addEqmLubricatPlan")
	public Json addEqmLubricatPlan(EqmBubricantfBean bean){
		Json json = new Json();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			//日集合
			List<Date> dayDt=findDates(sdf.parse(bean.getDate_plan1()),sdf.parse( bean.getDate_plan2()));
			//月集合
			List<Date> periodDt=getMouthDay(bean.getDate_plan1(),bean.getDate_plan2());
			/*for(Date d: periodDt){
				System.out.println(sdf.format(d)+"  =================================");
			}*/
			//日润滑计划生成    天
			if(!"".equals(bean.getRuleId())){
				addDayLubricantPan(bean,dayDt); 
			}
			//周期性润滑-生成    周，月，年
			if(!"".equals(bean.getRuleIdf())){
				addPeriodLubricantPan(bean,dayDt,periodDt,sdf); //周期性润滑
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
	
	//周期性润滑-早，中，晚
	public void addPeriodLubricantPan(EqmBubricantfBean bean,List<Date> dayDt,List<Date> periodDt,SimpleDateFormat sdf) throws ParseException{
		SysEqpTypeBean sysEqpType=new SysEqpTypeBean();
		sysEqpType.setCategoryId(bean.getRuleIdf());
		//查询具体项周期值
		//List<SysEqpType> lists=sysEqpTypeServiceImpl.queryBean(sysEqpType);
		List<SysEqpTypeBean> lists=sysEqpTypeServiceImpl.querySysEqmTypeByCid(sysEqpType);
		if(lists.size()>0){
			for(SysEqpTypeBean s:lists){
				String unit_id=s.getUnit_id();
				if(SysEqpTypeBase.period_3.equals(unit_id)){ //月
						int lt=periodDt.size(); //日期数
						int f=2; //默认倍数
						int period=s.getPeriod(); //周期
						if(period==0){
							continue;
						}
						int dx=period;
						while(lt>=dx){
							Date d=periodDt.get(dx-1);
							System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(d)+"  ===月===需要添加的值");
							if(d!=null){
								String shift_id=bean.getShift_id();
								if(shift_id.equals(SysEqpTypeBase.shift_0)){
									//早
									bean.setShift_id(SysEqpTypeBase.shift_1);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
									//中
									bean.setShift_id(SysEqpTypeBase.shift_2);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
									//晚
									bean.setShift_id(SysEqpTypeBase.shift_3);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
								}else{
								   saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
								}
							}
							dx=f*period;
							f++;
						}
				}else if(SysEqpTypeBase.period_4.equals(unit_id)){ //年
						int lt=periodDt.size(); //日期数
						int f=2; //默认倍数
						int period=s.getPeriod()*12; //周期  12
						int dx=period;
						while(lt>=dx){
							Date d=periodDt.get(dx-1);
							//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(d)+"  ===年===需要添加的值");
							if(d!=null){
								String shift_id=bean.getShift_id();
								if(shift_id.equals(SysEqpTypeBase.shift_0)){
									//早
									bean.setShift_id(SysEqpTypeBase.shift_1);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
									//中
									bean.setShift_id(SysEqpTypeBase.shift_2);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
									//晚
									bean.setShift_id(SysEqpTypeBase.shift_3);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
								}else{
								   saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
								}
							}
							dx=f*period;
							f++;
						}
				}else if(SysEqpTypeBase.period_2.equals(unit_id)){ //周
						int lt=dayDt.size(); //日期数
						int f=2; //默认倍数
						int period=s.getPeriod()*7; //周期  7
						int dx=period;
						while(lt>=dx){
							Date d=dayDt.get(dx-1);
							System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(d)+"  ===周===需要添加的值");
							if(d!=null){
								String shift_id=bean.getShift_id();
								if(shift_id.equals(SysEqpTypeBase.shift_0)){
									//早
									bean.setShift_id(SysEqpTypeBase.shift_1);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
									//中
									bean.setShift_id(SysEqpTypeBase.shift_2);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
									//晚
									bean.setShift_id(SysEqpTypeBase.shift_3);
									saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
								}else{
								   saveEqmLubricantPlan(bean,s,sdf.format(d),unit_id);
								}
							}
							dx=f*period;
							f++;
						}
				}
			}
		}
	}
	
	//周期性润滑- 插入EQM_LUBRICAT_PLAN表  {d-日期   unit_id周期表示} 
	public void saveEqmLubricantPlan(EqmBubricantfBean bean,SysEqpTypeBean s,String date,String unit_id) throws ParseException{
		String shift_id=bean.getShift_id();
		EqmLubricantPlanParam param=new EqmLubricantPlanParam();
		EqmLubricantPlan epl=new EqmLubricantPlan();
		/**
         * 日期，时间，班次，设备，周期类型，查询该条数据是否已经生成，
         *    如果没有生成，插入后返回Id，再插入 EQM_LUBRICAT_PLAN_PARAM 表
         *    如果已生成，直接获取ID，插入 EQM_LUBRICAT_PLAN_PARAM 表
		 * 
		 */
		List<?> listMdEquipMent=eqmLubricantPlanServiceI.addEqmLubricatPlan(bean);
		for(Object o:listMdEquipMent){
			Object[] temp=(Object[]) o;
			String eqp_id= temp[0].toString();
			EqmBubricantfBean ebf=sysEqpTypeServiceImpl.queryEqmLubricantPlanByPar(bean,date,unit_id,shift_id,eqp_id);
			if(ebf!=null){
				//直接插入子表
				param.setCode(DateUtil.formatDateToString(new Date(), "ss")+Math.random());
				param.setName(s.getName());
				param.setDes(s.getDes());
				param.setOiltd(s.getOilId());
				param.setFill_quantity(s.getFillQuantity());
				param.setFill_unit(s.getFillUnitName());
				param.setPlan_id(ebf.getId());
				param.setMethods(s.getFashionName());
				SysEqpType setf=new SysEqpType();
				setf.setId(s.getId());
				param.setSysEqpType(setf);
				lubricantPlanParamService.savePlanParams(param);
				
			}else{
				MdEquipment met=new MdEquipment();
				met.setId(eqp_id);
				epl.setEqp(met);
				epl.setDate_p(new SimpleDateFormat("yyyy-MM-dd").parse(date));
				epl.setLub_id(unit_id);
				epl.setCode(DateUtil.formatDateToString(new Date(), "ss")+Math.random());
				epl.setShift_id(shift_id);
				epl.setRule_id(bean.getRuleIdf());
				
				EqmLubricantPlan plan=lubricantPlanService.saveBean(epl);
				param.setCode(DateUtil.formatDateToString(new Date(), "ss")+Math.random());
				param.setName(s.getName());
				param.setDes(s.getDes());
				param.setOiltd(s.getOilId());
				param.setFill_quantity(s.getFillQuantity());
				param.setFill_unit(s.getFillUnitName());
				param.setPlan_id(plan.getId());
				param.setMethods(s.getFashionName());
				SysEqpType setf=new SysEqpType();
				setf.setId(s.getId());
				param.setSysEqpType(setf);
				lubricantPlanParamService.savePlanParams(param);
			}
		}
		
		
		
	}
	
	//日润滑
	public void addDayLubricantPan(EqmBubricantfBean bean,List<Date> dateLists) throws ParseException{
		//获得选中的所有设备
		/**
		 * id:4028998449c0f9650149c0fc428b0005	
		 * eqp_type_id:4028998449bcb4b10149bcb558780002	
		 * equipment_cde:31	
		 * equipment_name:1号包装机
		 * 
		 * */
		List<?> listMdEquipMent=eqmLubricantPlanServiceI.addEqmLubricatPlan(bean);
		for(Date d :dateLists){
			for(Object o:listMdEquipMent){
				Object[] temp=(Object[]) o;
				EqmLubricantPlan plan=new EqmLubricantPlan();
				plan.setEqp(new MdEquipment(temp[0].toString()));
				plan.setDate_p(d);//计划开始日期
				plan.setCode(DateUtil.formatDateToString(new Date(), "yyyyMMdd")+"E"+temp[2].toString());
				plan.setLub_id("1");//表示日润滑，周润滑，月润滑，年润滑
				plan.setRule_id(bean.getRuleId());
				plan.setStatus(0);
				String shift_id=bean.getShift_id();
				if(shift_id.equals(SysEqpTypeBase.shift_0)){ // 0表示 全部   
					//早
					plan.setShift_id(SysEqpTypeBase.shift_1);
					plan=lubricantPlanService.saveBean(plan);
					addLubricantPlanParam(bean,plan);
					//中
					plan.setShift_id(SysEqpTypeBase.shift_2);
					plan=lubricantPlanService.saveBean(plan);
					addLubricantPlanParam(bean,plan);
					//晚
					plan.setShift_id(SysEqpTypeBase.shift_3);
					plan=lubricantPlanService.saveBean(plan);
					addLubricantPlanParam(bean,plan);
				}else{
					plan.setShift_id(shift_id);
					//保存至设备润滑计划
					plan=lubricantPlanService.saveBean(plan);
					addLubricantPlanParam(bean,plan);
				}
				
			}
		}
	}
	
	//日润滑-插入计划子表
	public void addLubricantPlanParam(EqmBubricantfBean bean,EqmLubricantPlan plan){
		//获取设备润滑规则详细项
		SysEqpTypeBean sysEqpType=new SysEqpTypeBean();
		sysEqpType.setCategoryId(bean.getRuleId());
		//根据润滑规则查找到具体的项
		List<SysEqpType> lists=sysEqpTypeServiceImpl.queryBean(sysEqpType);
		//获取刚创建的设备润滑计划ID
		String plan_id=plan.getId();
		for(SysEqpType sys:lists){
			EqmLubricantPlanParam param=new EqmLubricantPlanParam();
			param.setCode(sys.getCode());
			param.setDes(sys.getDes());
			//润滑剂单位
			if(sys.getFillUnitBean()!=null){
				param.setFill_unit(sys.getFillUnitBean().getName());
			}
			param.setFill_quantity(sys.getFillQuantity());
			//润滑剂名称
			if(sys.getOilIdBean()!=null){
				param.setOiltd(sys.getOilIdBean().getLubricantName());
			}
			param.setName(sys.getName());
			param.setPlan_id(plan_id);
			//润滑方式
			if(sys.getFashionBean()!=null){
				param.setMethods(sys.getFashionBean().getName());
			}
			param.setSysEqpType(new SysEqpType(sys.getId()));
			lubricantPlanParamService.savePlanParams(param);
		}
	}
		
	public void createLubriPlan(String lubri_id,String eqp_id,String eqp_code){
		//查询设备润滑周期表
		EqmLubricant lubri=lubricantServiceI.getBeanById(lubri_id);
		EqmLubricantPlan plan=new EqmLubricantPlan();
		plan.setEqp(new MdEquipment(eqp_id));
		plan.setDate_p(DateUtil.dateCals(new Date(),lubri.getCycle()));
		plan.setCode(DateUtil.formatDateToString(new Date(), "yyyyMMdd")+"E"+eqp_code);
		plan.setLub_id(lubri_id);
		plan.setRule_id(lubri.getRule_id());
		plan.setStatus(0);
		//保存至设备润滑计划
		plan=lubricantPlanService.saveBean(plan);
		//获取设备润滑规则详细项
		SysEqpTypeBean sysEqpType=new SysEqpTypeBean();
		sysEqpType.setCategoryId(lubri.getRule_id());
		//根据润滑规则查找到具体的项
		List<SysEqpType> lists=sysEqpTypeServiceImpl.queryBean(sysEqpType);
		//获取刚创建的设备润滑计划ID
		String plan_id=plan.getId();
		for(SysEqpType sys:lists){
			EqmLubricantPlanParam param=new EqmLubricantPlanParam();
			param.setCode(sys.getCode());
			param.setDes(sys.getDes());
			//润滑剂单位
			if(sys.getFillUnitBean()!=null){
				param.setFill_unit(sys.getFillUnitBean().getName());
			}
			param.setFill_quantity(sys.getFillQuantity());
			//润滑剂名称
			if(sys.getOilIdBean()!=null){
				param.setOiltd(sys.getOilIdBean().getLubricantName());
			}
			param.setName(sys.getName());
			param.setPlan_id(plan_id);
			//润滑方式
			if(sys.getFashionBean()!=null){
				param.setMethods(sys.getFashionBean().getName());
			}
			param.setSysEqpType(new SysEqpType(sys.getId()));
			lubricantPlanParamService.savePlanParams(param);
		}
	}
	
	//获得两时间段所有日期集合
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
	
	public static List<Date> getMouthDay(String date1,String date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> list= new ArrayList<Date>();
		try {
			Date d1= sdf.parse(date1);
			Date d2= sdf.parse(date2);
			Calendar dd= Calendar.getInstance();
			dd.setTime(d1);
			while(dd.getTime().before(d2)){
				Date str=dd.getTime();
				list.add(str);
				dd.add(Calendar.MONTH, 1);
			}
			Date str=dd.getTime();
			list.add(str);
			dd.add(Calendar.MONTH, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	
	/**
	 * 批量删除用户
	 */
	@ResponseBody
	@RequestMapping("/deleteCycle")
	public Json deleteCycle(String ids){
		Json json=new Json();
		try {
			lubricantPlanService.deleteCycle(ids);
			json.setMsg("批量删除数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("批量删除数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 返回日期列表
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static List<Date> getMonthSpace(String date1, String date2,int d)
			throws Exception {
			List<Date> dates=new ArrayList<Date>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
			int result = (c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12+(c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH));
			if(result==0){
				result=1;
			}
			for (int i = 0; i < result; i++) {
				c1.add(Calendar.MONTH, d);
				dates.add(c1.getTime());
			}
			return dates;
		}
	}

