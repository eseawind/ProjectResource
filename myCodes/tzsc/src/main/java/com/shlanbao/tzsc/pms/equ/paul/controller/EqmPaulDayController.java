package com.shlanbao.tzsc.pms.equ.paul.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmPaulDay;
import com.shlanbao.tzsc.base.mapping.EqmProtectRecordBean;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.paul.beans.BatchBean;
import com.shlanbao.tzsc.pms.equ.paul.beans.EqmPaulDayBean;
import com.shlanbao.tzsc.pms.equ.paul.service.PaulDayServiceI;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCalendar;
/**
 * 
* @ClassName: EqmPaulDayController 
* @Description: 日保养 
* @author luo
* @date 2015年7月2日 上午11:17:57 
*
 */
@Controller
@RequestMapping("/pms/paul")
public class EqmPaulDayController extends BaseController{

	@Autowired
	public PaulDayServiceI paulDayServiceI;
	/**
	 * 
	* @Title: queryDataGrid 
	* @Description: 查询日保养
	* @param  bean
	* @param  pageParams
	* @return DataGrid  返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryDataGrid")
	public DataGrid queryDataGrid(EqmPaulDayBean bean,PageParams pageParams){
		return paulDayServiceI.queryList(bean, pageParams);
	}
	
	@RequestMapping("/gotoEdit")
	public String gotoEdit(HttpServletRequest request,String id){
		request.setAttribute("bean",paulDayServiceI.getBeanByIds(id));
		return "/pms/equ/paul/editPaul";
	}
	@RequestMapping("/gotoEdits")
	public String gotoEdits(HttpServletRequest request,String id){
		request.setAttribute("bean",paulDayServiceI.getBeanByIds(id));
		return "/pms/equ/paul/editPaul";
	}
	@RequestMapping("/gotoDetail")
	public String gotoDetail(HttpServletRequest request,String id){
		request.setAttribute("bean",paulDayServiceI.getBeanByIds(id));
		return "/pms/equ/paul/showDetail";
	}
	//批量添加日保
	@ResponseBody
	@RequestMapping("/batchAdd")
	public Json batchAdd(BatchBean batchBean,HttpServletRequest request){
		Json json=new Json();
		
		try{
			paulDayServiceI.batchAdd(batchBean);
			json.setMsg("添加成功");
			json.setSuccess(true);
		}catch(Exception e){
			log.error("批量添加日保养失败。", e);
			json.setMsg("添加失败");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/queryPaulCalendar")
	public List<EqmWheelCalendar> queryWCPlanCalendar(HttpServletRequest request) throws Exception{
		String date1=request.getParameter("start");
		String date2=request.getParameter("end");
		return paulDayServiceI.queryListCal(date1, date2);
	}
	
	@ResponseBody
	@RequestMapping("/addPaul")
	public Json addPaul(EqmPaulDayBean bean,HttpServletRequest request) throws Exception{
		Json json=new Json();
		try{
			paulDayServiceI.addPaulDayBean(bean);
			json.setMsg("添加成功");
			json.setSuccess(true);
		}catch(Exception e){
			log.error("日保养添加失败。", e);
			json.setMsg("添加失败");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/checkPaul")
	public Json checkPaul(HttpServletRequest request,String id) throws Exception{
		Json json=new Json();
		try{
			EqmPaulDay bean=paulDayServiceI.getBeanById(id);
			bean.setStatus(1);
			paulDayServiceI.updatePaulDay(bean);
			json.setMsg("审核成功");
			json.setSuccess(true);
		}catch(Exception e){
			log.error("日保养修改审核状态失败。", e);
			json.setMsg("审核失败");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/runPaul")
	public Json runPaul(HttpServletRequest request,String id) throws Exception{
		Json json=new Json();
		try{
			EqmPaulDay bean=paulDayServiceI.getBeanById(id);
			bean.setStatus(2);
			paulDayServiceI.updatePaulDay(bean);
			json.setMsg("运行成功");
			json.setSuccess(true);
		}catch(Exception e){
			log.error("日保养修改运行状态失败。", e);
			json.setMsg("运行失败");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/delPaul")
	public Json delPaul(HttpServletRequest request,String id) throws Exception{
		Json json=new Json();
		try{
			paulDayServiceI.delete(id);
//			EqmPaulDay bean=paulDayServiceI.getBeanById(id);
//			bean.setDel(1);
//			paulDayServiceI.updatePaulDay(bean);
			json.setMsg("删除成功");
			json.setSuccess(true);
		}catch(Exception e){
			log.error("日保养修改删除状态失败。", e);
			json.setMsg("删除失败");
			json.setSuccess(false);
		}
		return json;
	}
	//编辑
	@ResponseBody
	@RequestMapping("/editPaul")
	public Json editPaul(EqmPaulDayBean bean,HttpServletRequest request) throws Exception{
		Json json=new Json();
		try{
			paulDayServiceI.updatePaulDayBean(bean);
			json.setMsg("修改成功");
			json.setSuccess(true);
		}catch(Exception e){
			log.error("日保养修改失败。", e);
			json.setMsg("修改失败");
			json.setSuccess(false);
		}
		return json;
	}
	// 批量审核计划
	@ResponseBody
	@RequestMapping("/checkWork")
	public Json check(String ids){
		return checkWork(ids,1);
	}
	// 批量运行计划
	@ResponseBody
	@RequestMapping("/runWork")
	public Json run(String ids){
		return checkWork(ids,2);
	}
	
	//修改状态
	private Json checkWork(String ids,int status){
		Json json = new Json();
		try {
			paulDayServiceI.checkWork(ids,status);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			log.error("审核日保保养计划失败", e);
			json.setSuccess(false);
			json.setMsg("操作成功失败!");
		}
		return json;
	}
	
	
    /**
     *  功能说明：设备日保历史查询-查询
     *  
     *  @param pageParams 分页实体对象
     *  @param bean 数据实体对象
     *  @return
     *  @author wchuang
     *  @time 2015年7月13日15:37:54
     *  
     * */
	@ResponseBody
	@RequestMapping("/queryProtectRecordByList")
	public DataGrid queryProtectRecordByList(EqmProtectRecordBean bean,PageParams pageParams){
		return paulDayServiceI.queryProtectRecordByList(bean, pageParams);
	}
	/**
	 * 批量删除用户
	 */
	@ResponseBody
	@RequestMapping("/deletePaul")
	public Json batchDelete(String ids){
		Json json=new Json();
		try {
			paulDayServiceI.deletePaul(ids);
			json.setMsg("批量删除数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("批量删除数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
}
