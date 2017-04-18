package com.lanbao.dws.controller.wct.qm;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.tools.AjaxModelData;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.JsonBean;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.qm.CraftRules;
import com.lanbao.dws.model.wct.qm.QMOnlineCheck;
import com.lanbao.dws.model.wct.qm.QMOutWandDelInfo;
import com.lanbao.dws.model.wct.qm.QMOutWandItem;
import com.lanbao.dws.model.wct.qm.QMOutWard;
import com.lanbao.dws.model.wct.qm.QMUser;
import com.lanbao.dws.model.wct.qm.QmOutWandInfoBean;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.qm.IAppearanceCheckService;
import com.lanbao.dws.service.wct.qm.ICraftRulesService;
import com.lanbao.dws.service.wct.qm.IOnlineQmCheckService;

/**
 * 质量管理主控制层
 * 
 * @author shisihai
 *
 */
@Controller
@RequestMapping("wct/qm")
public class QmController {
	@Autowired
	private InitComboboxData initComboboxData;
	@Autowired
	IOnlineQmCheckService onlineQmCheckService;
	@Autowired
	ICraftRulesService craftRulesService;
	@Autowired
	IAppearanceCheckService appearanceCheckService;
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 上午10:30:55 
	* 功能说明 ：综合物理质量检测
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath(Model model, String url, WctPage wctPage, QMOnlineCheck param ,HttpSession session) {
		//根据登陆的设备code区分卷包、封箱、成型首页
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		List<String> eqpCodes=param.getEqpCodes();
		//登陆类型	0卷包机组    1 封箱机组     2成型机组    3发射机组
	    int logType=loginBean.getLoginType();
	    //首次默认查询当前登陆机台信息
	    if(param.getOrderNumber()==null || "".equals(param.getOrderNumber())){
				if(logType==0){
					eqpCodes.add(loginBean.getRollerEquipmentId());
					eqpCodes.add(loginBean.getPackerEquipmentId());
				}else if(logType==1){
					eqpCodes.add(loginBean.getBoxerEquipmentId0());
					eqpCodes.add(loginBean.getBoxerEquipmentId1());
				}else if(logType==2){
					eqpCodes.add(loginBean.getFilterEquipmentId0());
					eqpCodes.add(loginBean.getFilterEquipmentId1());
				}else if(logType==3){
					eqpCodes.add(loginBean.getLaunchEquipmentId0());
					eqpCodes.add(loginBean.getLaunchEquipmentId1());
					eqpCodes.add(loginBean.getLaunchEquipmentId2());
				}
	        }else{
	        	eqpCodes.add(param.getOrderNumber());
	        }
		param.setEqpCodes(eqpCodes);
		// 向该页面填充下拉数据
		// 填充下拉框数据
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM, ComboboxType.SHIFT);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT,ComboboxType.ALLEQPS);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		 PaginationResult<List<QMOnlineCheck>> datas =onlineQmCheckService.queryOnlineCheck(param, pagination);
//		 System.out.println("pagination.getTotalRows()==="+pagination.getTotalRows()+" / pagination.getPageCount()==="+pagination.getPageCount()
//		          +" / pagination.getPagesize()==="+pagination.getPagesize() +" / pagination.getCurrentPage()==="+pagination.getCurrentPage()
//		          +" / pagination.getFirstRowIndex()==="+pagination.getFirstRowIndex()+"========");
		// 封装数据到model
		 model.addAttribute("dataList", datas.getR());
		 wctPage.setMaxCount(pagination.getTotalRows());
		 model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月4日 上午11:05:46 
	* 功能说明 ： 工艺规程
	 */
	@RequestMapping("/getCraftRulesPagePath")
	public String getCraftRulesPagePath(Model model, String url, WctPage wctPage, CraftRules param) {
		// 向该页面填充下拉数据
		// 填充下拉框数据
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM, ComboboxType.SHIFT);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		 PaginationResult<List<CraftRules>> datas =craftRulesService.queryCraftRules(param, pagination);
		// 封装数据到model
		 model.addAttribute("dataList", datas.getR());
		 wctPage.setMaxCount(pagination.getTotalRows());
		 model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月7日 上午9:46:58 
	* 功能说明 ：物理外观检测
	 */
	@RequestMapping("/getAppearancePagePath")
	public String getAppearancePagePath(Model model, String url, WctPage wctPage, QMOutWard param) {
		// 向该页面填充下拉数据
		// 填充下拉框数据
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM,ComboboxType.SHIFT,ComboboxType.QMOUTWAND);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		PaginationResult<List<QMOutWard>> datas =appearanceCheckService.queryQmOutWardItem(param, pagination);
		// 封装数据到model
		model.addAttribute("dataList", datas.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月7日 上午10:42:37 
	* 功能说明 ：质量外观检测 	 内登陆
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String qmLogin(Model model,QMUser token, HttpSession session){
		String json=appearanceCheckService.qmLogin(token);
		session.setAttribute("qmLogUser", json);
		return json;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月8日 上午11:34:35 
	* 功能说明 ：保存外观巡检项
	 */
	@RequestMapping("/saveAppearances")
	@ResponseBody
	public String qmAppearanceSave(Model model,String jsonData,String url,String[] checkItemCodes,Integer[] checkVals){
			JsonBean json=  appearanceCheckService.saveQmAppearanceCheck(jsonData, checkItemCodes, checkVals);
			if(json.isFlag()){
				json.setMsg("保存成功！");
			}else{
				json.setMsg("保存失败！");
			}
			return GsonUtil.bean2Json(json);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月8日 上午10:23:53 
	* 功能说明 ：查询外观缺陷定义（选择外观缺陷项）
	 */
	@RequestMapping("/queryQmOutWardItem")
	@ResponseBody
	public String queryQmOutWardItem(Model model,WctPage wctPage,QMOutWandItem item){
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		PaginationResult<List<QMOutWandItem>> data=appearanceCheckService.queryQmOutWardItem(item, pagination);
		 //设置总条数
		wctPage.setMaxCount(pagination.getTotalRows());
		//将数据存到json中
		AjaxModelData modelData=new AjaxModelData();
		wctPage.setMaxPage(wctPage.getMaxPage());
		modelData.setDatas(data.getR());
		modelData.setWctPage(wctPage);
		modelData.setChooseParam(item);
		return GsonUtil.bean2Json(modelData);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月11日 下午4:19:45 
	* 功能说明 ：新建巡检大项
	 */
	@RequestMapping("/saveQmOutWand")
	public String saveQmOutWand(Model model, String url, WctPage wctPage, QMOutWard param,HttpSession session,String checkType){
		appearanceCheckService.saveQmOutWand(param,session,checkType);
		//返回查询结果
		return getAppearancePagePath(model,url,wctPage,param);
	}
	
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月12日 上午11:56:28 
	* 功能说明 ：查询外观巡检详细信息
	 */
	@RequestMapping("/queryQmOutWardDelInfo")
	public String queryQmOutWardDelInfo(Model model,String url,WctPage wctPage,QMOutWandDelInfo item){
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		PaginationResult<List<QMOutWandDelInfo>> data=appearanceCheckService.queryQmOutWardItemByBatch(item, pagination);
		// 封装数据到model
		model.addAttribute("dataList", data.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", item);
		// 返回页面
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月15日 上午9:21:43 
	* 功能说明 ：物理外观检测记录查询
	 */
	@RequestMapping("/getQmOutWandInfo")
	public String getQmOutWandInfo(Model model, String url, WctPage wctPage, QmOutWandInfoBean param) {
		// 向该页面填充下拉数据
		// 填充下拉框数据
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.TEAM, ComboboxType.SHIFT);
		initComboboxData.chooseSetComboboxModel(model, ComboboxType.MAT,ComboboxType.ALLEQPS);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询
		 PaginationResult<List<QmOutWandInfoBean>> datas =appearanceCheckService.queryQmOutWandInfo(param, pagination);
		// 封装数据到model
		 model.addAttribute("dataList", datas.getR());
		 wctPage.setMaxCount(pagination.getTotalRows());
		 model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
}
