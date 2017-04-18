package com.shlanbao.tzsc.wct.eqm.shiftGl.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmShiftDataBean;
import com.shlanbao.tzsc.wct.eqm.checkplan.service.EqpCheckServiceI;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;
import com.shlanbao.tzsc.wct.eqm.shiftGl.service.StatShiftFLInfoService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 
* @ClassName: EqpCheckController 
* @Description: WCT生产调度-交接班维护
*
 */
@Controller
@RequestMapping("/wct/eqm/shiftGl")
public class StatShiftFLInfoController {
	@Autowired
	private EqpCheckServiceI eqpCheckService;
	
	@Autowired
	private StatShiftFLInfoService statShiftFLInfoServce;
	
	
	/**
	 * [功能描述]：wct交接班维护-主页面查询-封箱机
	 *               产量，设备有效作业率
	 * @createTime 2015年11月2日11:32:54
	 * @author wanchanghuang
	 * */
	@RequestMapping("/matRequestBoxByQty")
	@ResponseBody
	public  String matRequestBoxByQty(HttpSession session,EqmShiftDataBean eqmsb){
		Map<String, String> map=new HashMap<String, String>();
		try {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			//当前时间
			eqmsb.setNowTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
			//班次,根据当班班次，查询上班数据
			Integer shift=Integer.parseInt(loginBean.getShiftId());
			eqmsb.setShift(shift);
			//设备
			eqmsb.setEqp_id(loginBean.getEquipmentId());
			eqmsb.setEqp_code(loginBean.getEquipmentCode());
			//车间
			eqmsb.setShop_code(loginBean.getWorkshopCode());
			//查询，封装数据
			map=statShiftFLInfoServce.queryEqmShiftDatasBox(eqmsb);
			//json返回
			String json=JSONArray.fromObject(map).toString();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * [功能描述]：wct交接班维护-包装机辅料保存-封箱机
	 *      yf_xp[] -箱皮   yf_jd[] -胶带
	 * @createTime 2015年11月13日16:52:19
	 * @author wanchanghuang
	 * */
	@RequestMapping("/saveShiftBox")
	@ResponseBody
	public  String saveShiftBox(HttpSession session,String yf_xpf, String yf_jdf){
		try {
			String[] yf_xp=yf_xpf.split(",");    
			String[] yf_jd=yf_jdf.split(","); 
			statShiftFLInfoServce.saveShiftBox(session,yf_xp,yf_jd);
			return "true";
		} catch (Exception e){
			e.printStackTrace();
		}
		return "false";
	}
	
	
	
	/**
	 * [功能描述]：wct交接班维护-主页面查询-成型机
	 *               产量，设备有效作业率
	 * @createTime 2015年11月2日11:32:54
	 * @author wanchanghuang
	 * */
	@RequestMapping("/matRequestFilterByQty")
	@ResponseBody
	public  String matRequestFilterByQty(HttpSession session,EqmShiftDataBean eqmsb){
		Map<String, String> map=new HashMap<String, String>();
		try {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			//当前时间
			eqmsb.setNowTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
			//班次,根据当班班次，查询上班数据
			Integer shift=Integer.parseInt(loginBean.getShiftId());
			eqmsb.setShift(shift);
			//设备
			eqmsb.setEqp_id(loginBean.getEquipmentId());
			eqmsb.setEqp_code(loginBean.getEquipmentCode());
			//车间
			eqmsb.setShop_code(loginBean.getWorkshopCode());
			//查询，封装数据
			map=statShiftFLInfoServce.queryEqmShiftDatasFilter(eqmsb);
			//json返回
			String json=JSONArray.fromObject(map).toString();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * [功能描述]：wct交接班维护-包装机辅料保存-成型机
	 *      zl_pz[] -盘纸   zl_ss[] -丝素
	 * @createTime 2015年11月13日16:52:19
	 * @author wanchanghuang
	 * */
	@RequestMapping("/saveShiftFilter")
	@ResponseBody
	public  String saveShiftFilter(HttpSession session,String zl_pzf, String zl_ssf){
		try {
			String[] zl_pz=zl_pzf.split(",");    
			String[] zl_ss=zl_ssf.split(","); 
			statShiftFLInfoServce.saveShiftFilter(session,zl_pz,zl_ss);
			return "true";
		} catch (Exception e){
			e.printStackTrace();
		}
		return "false";
	}
	
	
	
	
	/**
	 * [功能描述]：wct交接班维护-主页面查询-包装机
	 *               产量，设备有效作业率
	 * @createTime 2015年11月2日11:32:54
	 * @author wanchanghuang
	 * */
	@RequestMapping("/matRequestPackerByQty")
	@ResponseBody
	public  String matRequestPackerByQty(HttpSession session,EqmShiftDataBean eqmsb){
		Map<String, String> map=new HashMap<String, String>();
		try {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			//当前时间
			eqmsb.setNowTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
			//班次,根据当班班次，查询上班数据
			Integer shift=Integer.parseInt(loginBean.getShiftId());
			eqmsb.setShift(shift);
			//设备
			eqmsb.setEqp_id(loginBean.getEquipmentId());
			eqmsb.setEqp_code(loginBean.getEquipmentCode());
			//车间
			eqmsb.setShop_code(loginBean.getWorkshopCode());
			//查询，封装数据
			map=statShiftFLInfoServce.queryEqmShiftDatasPacker(eqmsb);
			//json返回
			String json=JSONArray.fromObject(map).toString();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * [功能描述]：wct交接班维护-包装机辅料保存-包装机
	 *      zb_lbz[]- 铝箔纸       zb_xhz[]- 条盒纸      zb_xhm[] - 条盒膜
	 *      zb_tmz[]- 条盒纸       zb_thm[]-条盒膜        zb_kz[] - 卡纸
	 *      zb_fq[] - 封签          zb_lx1[] - 拉线1  zb_lx2[] -拉线2  
	 * @createTime 2015年11月13日12:37:36
	 * @author wanchanghuang
	 * */
	@RequestMapping("/saveShiftPacker")
	@ResponseBody
	public  String saveShiftPacker(HttpSession session,String zb_lbz, String zb_xhz, String zb_xhm,String zb_thz, String zb_thm, String zb_kz,  String zb_fq, String zb_lx1, String zb_lx2){
		try {
			String[] zb_lbz1=zb_lbz.split(",");    
			String[] zb_xhz1=zb_xhz.split(",");    
			String[] zb_xhm1=zb_xhm.split(",");
			String[] zb_thz1=zb_thz.split(",");   
			String[] zb_thm1=zb_thm.split(",");   
			String[] zb_kz1=zb_kz.split(",");
			String[] zb_fq1=zb_fq.split(",");     
			String[] zb_lx11=zb_lx1.split(",");
			String[] zb_lx21=zb_lx2.split(",");
			statShiftFLInfoServce.saveShiftPacker(session,zb_lbz1,zb_xhz1,zb_xhm1,zb_thz1,zb_thm1,zb_kz1,zb_fq1,zb_lx11,zb_lx21);
			return "true";
		} catch (Exception e){
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	 * [功能描述]：wct交接班维护-主页面查询-卷烟机
	 *               产量，设备有效作业率
	 * @createTime 2015年11月2日11:32:54
	 * @author wanchanghuang
	 * */
	@RequestMapping("/matRequestByQty")
	@ResponseBody
	public  String matRequestByQty(HttpSession session,EqmShiftDataBean eqmsb){
		Map<String, String> map=new HashMap<String, String>();
		try {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			//当前时间
			eqmsb.setNowTime(DateUtil.getNowDateTime("yyyy-MM-dd"));
			//班次,根据当班班次，查询上班数据
			Integer shift=Integer.parseInt(loginBean.getShiftId());
			eqmsb.setShift(shift);
			//设备
			eqmsb.setEqp_id(loginBean.getEquipmentId());
			eqmsb.setEqp_code(loginBean.getEquipmentCode());
			//车间
			eqmsb.setShop_code(loginBean.getWorkshopCode());
			//查询，封装数据
			map=statShiftFLInfoServce.queryEqmShiftDatas(eqmsb);
			//json返回
			String json=JSONArray.fromObject(map).toString();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * [功能描述]：wct交接班维护-卷烟机辅料保存-卷烟机
	 *      zj_pz[]   盘纸
	 *      zj_ssz[]  水松纸
	 *      zj_lb[]   滤棒
	 * @createTime 2015年11月6日16:22:06
	 * @author wanchanghuang
	 * */
	@RequestMapping("/saveShiftRoller")
	@ResponseBody
	public  String saveShiftRoller(HttpSession session,String zj_pzf, String zj_sszf, String zj_lbf,String zj_szyf){
		try {
			//LoginBean loginBean = (LoginBean) session.getAttribute("loginWctEqmInfo");
			String[] zj_pz1=zj_pzf.split(",");
			String[] zj_ssz1=zj_sszf.split(",");
			String[] zj_lb1=zj_lbf.split(",");
			String[] zj_szy1=zj_szyf.split(",");
			statShiftFLInfoServce.saveShiftRoller(session,zj_pz1,zj_ssz1,zj_lb1,zj_szy1);
			return "true";
		} catch (Exception e){
			e.printStackTrace();
		}
		return "false";
	}
	/**
	 * 保存物料呼叫信息
	 * shisihai
	 * @param session
	 * @param zj_pzf
	 * @param zj_sszf
	 * @param zj_lbf
	 * @return
	 */
	@RequestMapping("/saveCallMatter")
	@ResponseBody
	public  Json saveCallMatter(HttpSession session,StatShiftFLInfo bean){
		Json json=new Json();
		boolean flag=false;
		try {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
			LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");
			flag=statShiftFLInfoServce.saveCallMatter(loginBean,loginBean2, bean);
			json.setMsg("要料信息已保存");
		} catch (Exception e){
			json.setMsg("要料信息出错！");
			e.printStackTrace();
		}
		json.setSuccess(flag);
		return json;
	}
 
	
}
