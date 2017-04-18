package com.lanbao.dws.controller.wct.pddisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.pddisplay.CalendarBean;
import com.lanbao.dws.model.wct.pddisplay.PdDisplayBean;
import com.lanbao.dws.model.wct.pddisplay.WorkOrderBean;
import com.lanbao.dws.service.wct.pdDisplay.IPdDisplayService;

/**
 * [功能说明]：WCT-生产调度
 *              生产工单、卷包排班
 * @date 2016年6月28日21:23:08
 * @author wanchanghuang
 * 
 * */
@Controller
@RequestMapping("/wct/pddisplay")
public class PdDisplayController {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	IPdDisplayService ipdDisplaySevice;
	
	/**
	 * [功能说明]：生产工单-卷烟机跳转查询
	 * @param url-返回相应页面路径
	 * */
	@RequestMapping("/gotoWorkDisplayRoller")
	public String getResultPagePathRoller(HttpSession session, Model model,String url,WctPage wctPage,PdDisplayBean pbean){
		LoginBean loginInfo=(LoginBean) session.getAttribute("loginInfo");
		pbean.setEqp_id(loginInfo.getRollerEquipmentId());
		//初始化
        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		//查询工单
		PaginationResult<List<PdDisplayBean>> listpd=ipdDisplaySevice.queryWorkOrderByList(pagination,pbean);
		model.addAttribute("pdList", listpd.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		//返回主页面
		return url;
	}
	/**
	 * [功能说明]：生产工单-包装机跳转查询
	 * @param url-返回相应页面路径
	 * */
	@RequestMapping("/gotoWorkDisplayPacker")
	public String getResultPagePathPacker(HttpSession session, Model model,String url,WctPage wctPage,PdDisplayBean pbean){
		LoginBean loginInfo=(LoginBean) session.getAttribute("loginInfo");
		pbean.setEqp_id(loginInfo.getPackerEquipmentId());
		//初始化
        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		//查询工单
		PaginationResult<List<PdDisplayBean>> listpd=ipdDisplaySevice.queryWorkOrderByList(pagination,pbean);
		model.addAttribute("pdList", listpd.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		//返回主页面
		return url;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月2日 上午11:15:07 
	* 功能说明 ：获取封箱机工单（多个封箱机id）
	 */
	@RequestMapping("/gotoWorkDisplayBoxer")
	public String getResultPagePathBoxer(HttpSession session, Model model,String url,WctPage wctPage,PdDisplayBean pbean){
		LoginBean loginInfo=(LoginBean) session.getAttribute("loginInfo");
		Map<String,Object> map=new HashMap<>();
		List<String> param=new ArrayList<>();
		param.add(loginInfo.getBoxerEquipmentId0());
		param.add(loginInfo.getBoxerEquipmentId1());
		map.put("eqp_id", param);
		//设备id可能是多个
		//初始化
        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		//查询工单
		PaginationResult<List<PdDisplayBean>> listpd=ipdDisplaySevice.queryOtherWorkOrderByList(pagination,map);
		model.addAttribute("pdList", listpd.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		//返回主页面
		return url;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月2日 上午11:17:00 
	* 功能说明 ：获取成型机工单(多个设备id)
	 */
	@RequestMapping("/gotoWorkDisplayFilter")
	public String getResultPagePathFilter(HttpSession session, Model model,String url,WctPage wctPage,PdDisplayBean pbean){
		LoginBean loginInfo=(LoginBean) session.getAttribute("loginInfo");
		//设备id可能是多个
		Map<String,Object> map=new HashMap<>();
		List<String> param=new ArrayList<>();
		param.add(loginInfo.getFilterEquipmentId0());
		param.add(loginInfo.getFilterEquipmentId1());
		map.put("eqp_id", param);
		//初始化
        Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		//查询工单
		PaginationResult<List<PdDisplayBean>> listpd=ipdDisplaySevice.queryOtherWorkOrderByList(pagination,map);
		model.addAttribute("pdList", listpd.getR());
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		//返回主页面
		return url;
	}
	
	/**
	 * [工能说明]：卷包车间排班-跳转进入页面
	 * @author wanchanghuang
	 * @date 2016年6月29日20:58:51
	 * @param url：返回页面链接
	 * */
	@RequestMapping("/calendar")
	public String queryCalendar( Model model,String url){
		//返回主页面
		return url;
	}

	/**
	 * [工能说明]：卷包车间排班-查询排班信息
	 * @author wanchanghuang
	 * @date 2016年6月29日20:58:51
	 * @param url：返回页面链接
	 * */
	@ResponseBody
	@RequestMapping("/getCurMonthCalendars")
	public String getCurMonthCalendars(CalendarBean bean){
		try {
			if(StringUtils.isNotEmpty(bean.getDateTime())){
				bean.setDateTime(bean.getDateTime().substring(0, 7));
			}
			List<CalendarBean> list=ipdDisplaySevice.getCurMonthCalendars(bean);
			Gson gson = new Gson(); 
	        String json = gson.toJson(list);
	        return json;
		} catch (Exception e) {
			log.error("查询车间排班:"+bean.getDate_()+" : "+bean.getWorkshop()+"异常", e);
		}
		return null;
	} 
	
	
	/**
	 * [功能说明]：卷烟机-启动运行状态（WCT系统）
	 *    1)修改工单状态    2）保存辅料消耗系数数据  3)将状态反馈MES
	 * @author wanchanghuang
	 * @date 2016年7月25日14:10:27
	 * 
	 * */
	@RequestMapping("/rollerRunWorkOrder")
	public String rollerRunWorkOrder(HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS2);//运行状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathRoller(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * [功能说明]：卷烟机-启动完成状态（WCT系统）
	 *    1)修改工单状态    2）保存辅料消耗系数数据   3)将状态反馈MES
	 * @author wanchanghuang
	 * @date 2016年7月25日14:10:27
	 * 
	 * */
	@RequestMapping("/rollerEndWorkOrder")
	public String rollerEndWorkOrder(HttpSession session,Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathRoller(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 卷烟机换牌
	 * @param request
	 * @param session
	 * @param model
	 * @param wctPage
	 * @param pbean
	 * @param bean
	 * @param url
	 * @return
	 */
	@RequestMapping("/rollerChangeWorkOrder")
	public String rollerChangeWorkOrder(HttpServletRequest request,HttpSession session,Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			//向DAC发送换牌信息
			ipdDisplaySevice.sendMsgToDAC(request, bean);
			getResultPagePathRoller(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * [功能说明]：包装机-启动运行状态（WCT系统）
	 *    1)修改工单状态    2）保存辅料消耗系数数据  3)将状态反馈MES
	 * @author wanchanghuang
	 * @date 2016年7月25日14:10:27
	 * 
	 * */
	@RequestMapping("/packRunWorkOrder")
	public String packRunWorkOrder(HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS2);//运行状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathPacker(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * [功能说明]：包装机-启动完成状态（WCT系统）
	 *    1)修改工单状态    2）保存辅料消耗系数数据   3)将状态反馈MES
	 * @author wanchanghuang
	 * @date 2016年7月25日14:10:27
	 * 
	 * */
	@RequestMapping("/packEndWorkOrder")
	public String packEndWorkOrder(HttpSession session,Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathPacker(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 包装机换牌
	 * @param request
	 * @param session
	 * @param model
	 * @param wctPage
	 * @param pbean
	 * @param bean
	 * @param url
	 * @return
	 */
	@RequestMapping("/packChangeWorkOrder")
	public String packChangeWorkOrder(HttpServletRequest request,HttpSession session,Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			ipdDisplaySevice.sendMsgToDAC(request, bean);
			getResultPagePathPacker(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月5日 下午1:03:02 
	* 功能说明 ：启动封箱机工单
	 */
	@RequestMapping("/boxerRunWorkOrder")
	public String boxerRunWorkOrder(HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS2);//运行状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathBoxer(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月5日 下午1:11:16 
	* 功能说明 ：完成封箱机工单
	 */
	@RequestMapping("/boxerFinishWorkOrder")
	public String boxerFinishWorkOrder(HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathBoxer(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 封箱机班内换牌
	 * @param request
	 * @param session
	 * @param model
	 * @param wctPage
	 * @param pbean
	 * @param bean
	 * @param url
	 * @return
	 */
	@RequestMapping("/boxerChangeWorkOrder")
	public String boxerChangeWorkOrder(HttpServletRequest request,HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			ipdDisplaySevice.sendMsgToDAC(request, bean);
			getResultPagePathBoxer(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月5日 下午1:03:02 
	* 功能说明 ：启动成型机工单
	 */
	@RequestMapping("/filterRunWorkOrder")
	public String filterRunWorkOrder(HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS2);//运行状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathFilter(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年8月5日 下午1:11:16 
	* 功能说明 ：完成成型机工单
	 */
	@RequestMapping("/filterFinishWorkOrder")
	public String filterFinishWorkOrder(HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			getResultPagePathFilter(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 成型机换牌
	 * @param request
	 * @param session
	 * @param model
	 * @param wctPage
	 * @param pbean
	 * @param bean
	 * @param url
	 * @return
	 */
	@RequestMapping("/filterChangeWorkOrder")
	public String filterChangeWorkOrder(HttpServletRequest request,HttpSession session, Model model,WctPage wctPage,PdDisplayBean pbean,WorkOrderBean bean,String url){
		try {
			bean.setSts(Constants.WORKORDERSTATUS4);//完成状态
			ipdDisplaySevice.updateWorkOrder(bean);
			ipdDisplaySevice.sendMsgToDAC(request, bean);
			getResultPagePathFilter(session,model, url, new WctPage(), new PdDisplayBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 系统管理，获取当前几台的设备保养工单等信息。
	 * @param request
	 * @param session
	 * @param model
	 * @param wctPage
	 * @param pbean
	 * @param bean
	 * @param url
	 * @return
	 */
	@RequestMapping("/getEqpWorkInfo")
	public String getEqpWorkInfo(HttpServletRequest request,HttpSession session, Model model,WctPage wctPage,String url){
		return url;
	}
	
	
}
