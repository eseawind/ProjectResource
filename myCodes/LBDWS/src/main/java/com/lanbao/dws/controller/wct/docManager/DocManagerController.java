package com.lanbao.dws.controller.wct.docManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.docManager.DocFile;
import com.lanbao.dws.service.wct.docManager.IDocFileService;


/**
 * 文档管理主控制层
 * @author shisihai
 */
@Controller
@RequestMapping("wct/docManager")
public class DocManagerController {
	@Autowired
	IDocFileService docFileService;
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月29日 上午10:09:07 
	* 功能说明 ：查询当前文档
	 */
	@RequestMapping("/getDocFiles")
	public String getDocFiles(Model model, String url, WctPage wctPage,DocFile docFile){
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		PaginationResult<List<DocFile>> result=docFileService.getDocFiles(pagination,docFile);
		 //设置总条数
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		model.addAttribute("dataList", result.getR());
		model.addAttribute("chooseParams", docFile);
		return url;
	}
}
