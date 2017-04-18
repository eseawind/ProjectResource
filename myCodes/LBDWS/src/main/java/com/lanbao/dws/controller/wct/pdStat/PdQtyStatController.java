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
import com.lanbao.dws.model.wct.pdStat.HisQtyStat;
import com.lanbao.dws.model.wct.pdStat.RealTimeQty;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.pdStat.IPdStatService;

/**
 * 生产统计模块--》产量相关
 * @author shisihai
 *
 */
@Controller
@RequestMapping("/wct/pdQty")
public class PdQtyStatController {
	
	@Autowired
	IPdStatService pdStatService;
	@Autowired
	InitComboboxData initComboboxData;
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 上午9:06:16 
	* 功能说明 ：卷包机实时产量查询初始化数据
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath(Model model, String url, WctPage wctPage, RealTimeQty param) {
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		 PaginationResult<List<RealTimeQty>> datas =pdStatService.getRealTimeQty(param, pagination);
		// 封装数据到model
		 if(param.getOrderType().equals("1")){
			 model.addAttribute("dataList", datas.getR());
		 }else if(param.getOrderType().equals("2")){
			 model.addAttribute("packerList", datas.getR());
		 }
		 //设置总条数
		 wctPage.setMaxCount(pagination.getTotalRows());
		 model.addAttribute("wctPage", wctPage);
		// 回显条件
		 model.addAttribute("chooseParams", param);
		// 返回页面
		 return url;
	}
	
	
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 上午9:10:40 
	* 功能说明 ：卷包烟机实时剔除初始化数据查询
	 */
	@RequestMapping("/getRealTimeBadQtyPagePath")
	public String getRealTimeBadQtyPagePath(Model model, String url, WctPage wctPage, RealTimeQty param) {
			// 初始化分页
			Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
			// 查询
			 PaginationResult<List<RealTimeQty>> datas =pdStatService.getRealTimeBadQty(param, pagination);
			// 封装数据到model
			 if(param.getOrderType().equals("1")){
				 model.addAttribute("dataList", datas.getR());
			 }else if(param.getOrderType().equals("2")){
				 model.addAttribute("packerList", datas.getR());
			 }
			 //设置总条数
			 wctPage.setMaxCount(pagination.getTotalRows());
			 model.addAttribute("wctPage", wctPage);
			// 回显条件
			 model.addAttribute("chooseParams", param);
			// 返回页面
			 return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 上午9:11:38 
	* 功能说明 ：卷包烟机历史产量查询   柱状图
	 */
	@RequestMapping("/getHisQtyPagePath")
	public String getHisQtyPagePath(Model model, String url, WctPage wctPage, HisQtyStat param) {
		//加载下拉框数据
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT,ComboboxType.ALLEQPS);
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM,ComboboxType.SHIFT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		pdStatService.getHisQtyStst(param, pagination,model);
		 //设置总条数
		 wctPage.setMaxCount(pagination.getTotalRows());
		 model.addAttribute("wctPage", wctPage);
		// 回显条件
		 model.addAttribute("chooseParams", param);
		// 返回页面
		 return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月6日 上午9:13:25 
	* 功能说明 ：卷包机历史剔除查询    柱状图
	 */
	@RequestMapping("/getHisBadQtyPagePath")
	public String getHisBadQtyPagePath(Model model, String url, WctPage wctPage, HisQtyStat param) {
		//加载下拉框数据
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT,ComboboxType.ALLEQPS);
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM,ComboboxType.SHIFT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		pdStatService.getHisBadQtyStst(param, pagination,model);
		 //设置总条数
		 wctPage.setMaxCount(pagination.getTotalRows());
		 model.addAttribute("wctPage", wctPage);
		// 回显条件
		 model.addAttribute("chooseParams", param);
		// 返回页面
		 return url;
	}
}
