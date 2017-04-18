package com.lanbao.dws.controller.wct.pddisplay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.pddisplay.FLOfOrderResult;
import com.lanbao.dws.model.wct.pddisplay.OrderResultBean;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.pdDisplay.IOrderResultService;

/**
 * 生产实际控制层
 * 
 * @author shisihai
 */
@Controller
@RequestMapping("/wct/orderResult")
public class OrderResultController {
	@Autowired
	private InitComboboxData initComboboxData;
	@Autowired
	IOrderResultService orderResultService;

	@RequestMapping("/gotoWorkOrderResult")
	public String gotoWorkResult(Model model, String url, WctPage wctPage, OrderResultBean param) {
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		PaginationResult<List<OrderResultBean>> datas = orderResultService.queryWorkOrderByList(param, pagination);
		// 封装数据到model
		model.addAttribute("dataList", datas.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 填充下拉框数据
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM, ComboboxType.SHIFT,
				ComboboxType.EQPCATEGORY);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.ALLEQPS, ComboboxType.ROLLEREQPS,
				ComboboxType.PACKEREQPS, ComboboxType.FXEQPS, ComboboxType.FILTEREQPS);
		// 返回页面
		return url;
	}

	/**
	 * @author 作者 :
	 * @version 创建时间：2016年6月30日 下午4:00:21 功能说明 ：根据工单code获取该工单辅料信息
	 */
	@RequestMapping("/getOrderResultDetails")
	@ResponseBody
	public List<FLOfOrderResult> getOrderDetailByCode(Model model, String orderID) {
		return orderResultService.getOrderDetailById(model, orderID);
	}

}
