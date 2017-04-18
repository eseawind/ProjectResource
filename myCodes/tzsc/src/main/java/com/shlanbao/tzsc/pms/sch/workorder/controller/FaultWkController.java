package com.shlanbao.tzsc.pms.sch.workorder.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.workorder.beans.FaultWkBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.FaultWkServiceI;
import com.shlanbao.tzsc.utils.excel.ViewExcel;
/**
 * 
* @ClassName: FaultWkController 
* @Description: 设备故障历史记录
* @author luo 
* @date 2015年10月12日 上午8:38:33 
*
 */
@Controller
@RequestMapping("/pms/faultWk")
public class FaultWkController extends BaseController{

	@Autowired
	private FaultWkServiceI faultWkService;
	/**
	* @Title: getFaultWkGrid 
	* @Description: 故障历史记录查询
	* @param bean 条件实体
	* @param pageParams 翻页实体
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getFaultWkGrid")
	public DataGrid getFaultWkGrid(FaultWkBean bean,PageParams pageParams){
		try {
			return faultWkService.queryFaultWkGrid(bean, pageParams);
		} catch (Exception e) {
			log.error("获取工单机台故障异常", e);
		}
		return null;
	}
	/**
	* @Title: viewExcel 
	* @Description: 故障历史记录Excel导出 
	* @param bean 查询条件
	* @param request
	* @param response
	* @return ModelAndView 返回类型 
	* @throws
	 */
	@RequestMapping("/faultWkExcel")
	public ModelAndView viewExcel(FaultWkBean bean,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();		
		//Excel 列表数据源
		model.put("list", faultWkService.exportFaultWkList(bean));	
		model.put("name", "故障历史记录");
		//Excel标题
		model.put("title", new String[]{"机台","班次","故障日期","故障名称","停机时间(分)","停机次数(次)","工单号","运行时长(分)"});
		//Excel标题对应的类的方法名
		model.put("method", new String[]{"getEqp_name","getTeam_name","getDate","getFault_name","getTime","getTimes","getWk_code","getRuntime"});
		//数据源中的Bean
		model.put("class", FaultWkBean.class);
		//设置列宽
		model.put("lineWidth", new Short[]{10,10,10,12,10,10,19,12});
		return new ModelAndView(new ViewExcel(), model);
	}
	
	
}
