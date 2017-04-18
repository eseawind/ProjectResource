package com.lanbao.dws.controller.wct.pdStat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.pdStat.RealTimeFLConsume;
import com.lanbao.dws.model.wct.pdStat.RealTimeFLConsumeShowBean;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.pdStat.IPdStatService;

/**
 * 生产统计辅料消耗
 * @author shisihai
 *
 */
@Controller
@RequestMapping("/wct/fLConsume")
public class PdFLConsumeController {
	@Autowired
	IPdStatService pdStatService;
	@Autowired
	InitComboboxData initComboboxData;
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 上午10:31:27 
	* 功能说明 ：生产统计辅料实时消耗
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath(Model model, String url, WctPage wctPage, RealTimeFLConsume param) {
		// 向该页面填充下拉数据
//		// 填充下拉框数据
//		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM, ComboboxType.SHIFT,ComboboxType.EQPCATEGORY);
//		initComboboxData.chooseSetComboboxModel(model, ComboboxType.ALLEQPS);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		 PaginationResult<List<RealTimeFLConsumeShowBean>> datas =pdStatService.getRealTimeFLConsumeInfo(param, pagination);
		// 封装数据到model
		 if(param.getOrderType().equals("1")){
			 model.addAttribute("dataList", datas.getR());
		 }else if(param.getOrderType().equals("2")){
			 model.addAttribute("packerList", datas.getR());
		 }
		 //设置最大
		 wctPage.setMaxCount(pagination.getTotalRows());
		 //每页显示4条
		 model.addAttribute("wctPage", wctPage);
		 //设置总页数
		 model.addAttribute("maxpage", wctPage.getMaxCount()%4==0?wctPage.getMaxCount()/4:wctPage.getMaxCount()/4+1);
		 // 回显条件
		 model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月5日 下午4:32:10 
	* 功能说明 ：历史消耗
	 */
	@RequestMapping("/getHisSchStatInput")
	public String getResultHisPagePath(Model model, String url, WctPage wctPage, RealTimeFLConsume param) {
		// 向该页面填充下拉数据
//		// 填充下拉框数据
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM, ComboboxType.SHIFT,ComboboxType.EQPCATEGORY);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.ALLEQPS,ComboboxType.MAT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		 PaginationResult<List<RealTimeFLConsumeShowBean>> datas =pdStatService.getHisFLConsumeInfo(param, pagination);
		// 封装数据到model
		 if(param.getEqpCategory().equals("1")){
			 model.addAttribute("dataList", datas.getR());
		 }else if(param.getEqpCategory().equals("2")){
			 model.addAttribute("packerList", datas.getR());
		 }
		 //设置最大
		 wctPage.setMaxCount(pagination.getTotalRows());
		 //每页显示3条
		 model.addAttribute("wctPage", wctPage);
		 //设置总页数
		 model.addAttribute("maxpage", wctPage.getMaxCount()%3==0?wctPage.getMaxCount()/3:wctPage.getMaxCount()/3+1);
		 // 回显条件
		 model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	
	
}
