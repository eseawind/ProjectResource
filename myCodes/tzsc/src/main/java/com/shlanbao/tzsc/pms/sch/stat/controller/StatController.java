package com.shlanbao.tzsc.pms.sch.stat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.mat.beans.MatUnitBean;
import com.shlanbao.tzsc.pms.md.unit.beans.UnitBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.pms.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.pms.sch.stat.service.StatServiceI;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
/**
 * 生产 统计
 * @author Leejean
 * @create 2014年11月25日下午1:31:32
 */
import com.shlanbao.tzsc.utils.tools.DateUtil;
@Controller
@RequestMapping("/pms/stat")
public class StatController extends BaseController {
	@Autowired
	private StatServiceI statService;
	@Autowired
	private WorkOrderServiceI workOrderService;
	/**
	 * 查询所有产出数据
	 * @author Leejean
	 * @create 2014年12月3日上午9:00:25
	 * @param outputBean 查询条件
	 * @param pageParams 参数
	 * @return datagrid
	 */
	@RequestMapping("/getAllOutputs")
	@ResponseBody
	public DataGrid getAllOutputs(OutputBean outputBean,PageParams pageParams){
		return statService.getAllOutputs(outputBean, pageParams);		
	}
	/**
	 * 查询成型生产实绩
	 * @author 张璐
	 * @create 2015年10月12日
	 * @param outputBean 查询条件
	 * @param pageParams 参数
	 * @return datagrid
	 */
	@RequestMapping("/getAllForming")
	@ResponseBody
	public DataGrid getAllForming(OutputBean outputBean,PageParams pageParams){
		return statService.getAllForming(outputBean, pageParams);		
	}
	/**
	 * 根据产出获得消耗集合
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:13
	 * @param id 产出ID
	 * @return datagrid
	 */
	@RequestMapping("/getAllInputsByOutput")
	@ResponseBody
	public DataGrid getAllInputsByOutput(String id){
		try {
			return statService.getAllInputsByOutput(id);
		} catch (Exception e) {
			super.setMessage("根据产出ID:"+id+"查询消耗数据");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 删除产出数据
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:17
	 * @param id ID
	 * @return
	 */
	@RequestMapping("/deleteOutput")
	@ResponseBody	
	public Json deleteOutput(String id){
		Json json=new Json();
		try {
			statService.deleteOutput(id);
			json.setMsg("删除产出数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			super.setMessage("删除产出数据失败!");
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 删除消耗数据
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:21
	 * @param id ID
	 * @return
	 */
	@RequestMapping("/deleteInput")
	@ResponseBody	
	public Json deleteInput(String id){
		Json json=new Json();
		try {
			statService.deleteInput(id);
			json.setMsg("删除消耗数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			super.setMessage("删除消耗数据失败!");
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 跳转到产出新增页面
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:24
	 * @param request
	 * @param workshop 车间代码
	 * @return 
	 */
	@RequestMapping("/goToOutputAddJsp")
	public String goToOutputAddJsp(HttpServletRequest request,String workshop){
		request.setAttribute("workshop", workshop);
		return "/pms/sch/stat/outputAdd";
	}
	/**
	 * 张璐
	 * 成型生产实绩添加页面跳转
	 * @param request
	 * @param workshop
	 * @return
	 */
	@RequestMapping("/goToOutputAddJspCX")
	public String goToOutputAddJspCX(HttpServletRequest request,String workshop){
		request.setAttribute("workshop", workshop);
		return "/pms/sch/stat/outputAddCX";
	}
	/**
	 * 新增产出数据
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:27
	 * @param outputBean
	 * @return
	 */
	@RequestMapping("/addOutput")
	@ResponseBody	
	public Json addOutput(OutputBean outputBean){
		Json json=new Json();
		try {
			int result = statService.addOutput(outputBean,1);
			if(result==0){
				json.setMsg("已经存在本工单产出信息!");
				return json;
			}
			json.setMsg("添加产出数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			super.message = "添加产出数据失败!";
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 跳转到产出数据编辑页面
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:32
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/goToOutputEditJsp")
	public String goToOutputEditJsp(HttpServletRequest request,String id){
		request.setAttribute("output", statService.getOutputById(id));
		return "/pms/sch/stat/outputEdit";
	}
	/**
	 * 编辑产出
	 */
	@RequestMapping("/editOutput")
	@ResponseBody	
	public Json editOutput(OutputBean outputBean){
		Json json=new Json();
		try {
			statService.editOutput(outputBean);
			json.setMsg("编辑消耗数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			super.message = "编辑消耗数据失败!";
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 跳转到消耗数据新增页面
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:35
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goToInputAddJsp")
	public String goToInputAddJsp(HttpServletRequest request,OutputBean outputBean) throws Exception{
		request.setAttribute("outputId", outputBean.getId());		
		request.setAttribute("workorder",workOrderService.getWorkOrderById(outputBean.getWorkorder()));
		System.out.println("工单编号为："+outputBean.getWorkorder());
		//根据产出ID得到工单，根据工单得到物料清单bom集合，供页面选择辅料新增。
		request.setAttribute("boms",JSON.toJSONString(statService.getBomsWorkorderId(outputBean.getWorkorder())));
		return "/pms/sch/stat/inputAdd";
	}
	/**
	 * 根据工单和物料，获取该获取在bom中的单位
	 * @author Leejean
	 * @create 2014年12月4日上午11:53:39
	 * @param workorder
	 * @param matId
	 * @return
	 */
	@RequestMapping("/getUnitByMatId")
	@ResponseBody
	public UnitBean getUnitByMatId(String workorder,String matId){
		return statService.getUnitByMatId(workorder,matId);
	}
	@ResponseBody
	@RequestMapping("/getunitbyworkorder")
	public MatUnitBean getunitbyworkorderid(String workorder,String matId) throws Exception{
		return statService.getUnitByworkorder(workorder, matId);
	}
	/**
	 * 新增消耗数据
	 * @author Leejean
	 * @create 2014年12月4日下午2:50:36
	 * @param inputBean
	 * @return
	 */
	@RequestMapping("/addInput")
	@ResponseBody	
	public Json addInput(InputBean inputBean){
		Json json=new Json();
		try {
			statService.addInput(inputBean);
			json.setMsg("新增消耗数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			super.message = "新增消耗数据失败!";
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 跳转到消耗数据编辑页面
	 * @author Leejean
	 * @create 2014年12月3日上午9:01:39
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goToInputEditJsp")
	public String goToInputEditJsp(HttpServletRequest request,String iid,OutputBean outputBean) throws Exception{
		
		request.setAttribute("outputId", outputBean.getId());
		
		request.setAttribute("workorder",workOrderService.getWorkOrderById(outputBean.getWorkorder()));
		
		request.setAttribute("input", statService.getInputById(iid));
		
		return "/pms/sch/stat/inputEdit";
	}
	/**
	 * 编辑消耗
	 */
	@RequestMapping("/editInput")
	@ResponseBody	
	public Json editInput(InputBean inputBean){
		Json json=new Json();
		try {
			statService.editInput(inputBean);
			json.setMsg("编辑消耗数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			super.message = "编辑消耗数据失败!";
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 反馈数据到MES系统
	 * @author Leejean
	 * @create 2014年12月4日下午2:53:48
	 * @param idOrIds
	 * @return
	 */
	@RequestMapping("/sendDataToMES")
	@ResponseBody
	public Json sendDataToMES(String idOrIds,String feedbackUser){
		Json json=new Json();
		try {
			int result = statService.sendDataToMES(idOrIds, feedbackUser);
			if(result>0){
				json.setMsg("反馈成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("反馈失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			super.message = "反馈失败!请稍后再试！";
			log.error(super.message, e);
			json.setMsg(super.message);
		}
		return json;
	}
	/**
	 * 导出PMS卷包产量信息
	 * TODO
	 * @param date
	 * @param date2
	 * @param response
	 * @throws Exception
	 * TRAVLER
	 * 2015年12月2日下午2:47:53
	 */
	@ResponseBody
	@RequestMapping("/exportQtyInfo")
	public void exportQtyInfo(String date,String date2,String team,String shift,String equipment,String eqpType,HttpServletResponse response) throws Exception{		
		HSSFWorkbook wb =statService.exportQtyInfo(date,date2,shift,team,equipment,eqpType);
		String addtime=DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment; filename="+addtime+".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}
	
	
	@ResponseBody
	@RequestMapping("/geteqpment")
	public List<MdEquipment> geteqpment(int eqptypeid) throws Exception{
		List<MdEquipment> eqpments=new ArrayList<MdEquipment>();
		eqpments=statService.queryEqpMents(eqptypeid);
		return eqpments;
	}
	
	
}
