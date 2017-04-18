package com.shlanbao.tzsc.wct.eqm.overhaul.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.overhaul.bean.EqpOverhaulBean;
import com.shlanbao.tzsc.wct.eqm.overhaul.service.EqpOverhaulServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

/**
 * 
 * @ClassName: EqmMaintainController 
 * @Description: 设备检修项目维护 
 * @author luo
 * @date 2015年2月5日 上午11:39:49 
 *
 */
@Controller
@RequestMapping("/wct/overhaul")
public class EqpOverhaulController extends BaseController{
	@Autowired
	protected EqpOverhaulServiceI eqpOverhaulServiceI;
	
	@ResponseBody
	@RequestMapping("/queryOverhaul")
	public DataGrid queryOverhaul(HttpSession session,EqpOverhaulBean eqmMaintain,int pageIndex)throws Exception{
		 LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");//大登陆
		 eqmMaintain.setEqp_id(loginBean.getEquipmentId());
		try {
			PageParams pageParams=new PageParams();
			pageParams.setPage(pageIndex);
			pageParams.setRows(10);
			return eqpOverhaulServiceI.queryEqmMaintain(eqmMaintain, pageParams);
		} catch (Exception e) {
			log.error("查询设备检修项目数据异常。", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/updateStatus")
	public Json updateStatus(String id,String area3,String area6,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try{
			LoginBean loginBean=(LoginBean)request.getSession().getAttribute("loginInfo");
			if(loginBean==null){
				json.setMsg("用户没有登录!");
				json.setSuccess(false);
				return json;
			}
			eqpOverhaulServiceI.updateStatus(id,area3,area6,loginBean.getUserId());
		}catch(Exception e){
			json.setMsg("操作失败!");
			json.setSuccess(false);
			log.error("查询设备检修项目数据异常。", e);
			return json;
		}
		json.setMsg("操作成功!");
		json.setSuccess(true);
		return json;
	}
	
	/**张璐2015.10.30
	 * 用于添加故障后，跳转回来，按钮才变成已完成
	 * @param id
	 * @param uid
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateStatusTrouble")
	public Json updateStatusTrouble(String id,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try{
			LoginBean loginBean=(LoginBean)request.getSession().getAttribute("loginInfo");
			LoginBean loginBean2 = (LoginBean) request.getSession().getAttribute("loginWctEqmInfo");//小登陆
			String userId;
			if(loginBean2==null){
				userId=loginBean.getUserId();
			}else{
				userId=loginBean2.getUserId();
			}
			if(loginBean==null){
				json.setMsg("用户没有登录!");
				json.setSuccess(false);
				return json;
			}
			eqpOverhaulServiceI.updateStatusTrouble(id,userId);
		}catch(Exception e){
			json.setMsg("操作失败!");
			json.setSuccess(false);
			log.error("查询设备检修项目数据异常。", e);
			return json;
		}
		json.setMsg("操作成功!");
		json.setSuccess(true);
		return json;
	}
	
	
	/**
	 * 更换备件记录
	 * @param all_id
	 * @param use_n
	 * @param all_num
	 * @param request
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping("/insertHisSParts")
	public Json insertHistoryOfSparePart(String chooseid,String placeJ,String contentsJ,String date_plansJ,String blame_usr_nameJ,String noteJ,String eqp_idJ,String blame_usr_idJ,String real_usr_nameJ,String all_id,String use_n,String all_num,HttpSession session,HttpServletRequest request){
		Json json=new Json();
		try {
			 LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");//大登陆
			 LoginBean loginBean2 = (LoginBean) session.getAttribute("loginWctEqmInfo");//小登陆
			if(loginBean==null){
				json.setMsg("用户没有登录!");
				json.setSuccess(false);
				return json;
			}
			String[] id=all_id.split(",");//备件ids
			String[] useNum=use_n.split(",");//备件使用数量
			List<EqmFixRec> eqmFixRecs=new ArrayList<EqmFixRec>();
			for (int i = 0; i < useNum.length; i++) {
				int n=Integer.parseInt(useNum[i]);
				if(n==0){
					continue;
				}
				//换备件的记录
				else{
					EqmFixRec e=new EqmFixRec();
					//班次id
					if(loginBean!=null&&StringUtil.notNull(loginBean.getShift())){
						e.setShift_id(new MdShift(loginBean.getShiftId()));
					}
					e.setTrouble_id(chooseid);//设备故障表id
					e.setSource("5");//5代表检修  以后维护到map   写死的
					e.setStatus(1);//完成状态
					MdEquipment eqp_id = new MdEquipment();
					eqp_id.setId(loginBean.getEquipmentId());
					e.setEqp_id(eqp_id);//设备id
					//e.setRemark(contentsJ);//检修内容
					e.setMaintaiin_time(new Date());//维修完成时间
					e.setSpare_parts_id(id[i]);//备件id
					e.setSpare_parts_num(n);//使用数量
					if(loginBean2!=null){
						e.setMaintaiin_name(loginBean2.getName());//实际维修人
						e.setMaintaiin_id(loginBean2.getUserId());//维修人id
					}else{
						e.setMaintaiin_name(loginBean.getName());//实际维修人
						e.setMaintaiin_id(loginBean.getUserId());//维修人id
					}
					eqmFixRecs.add(e);
				}
			}
			eqpOverhaulServiceI.addBatch(eqmFixRecs);
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
			log.error("查询设备检修更换备件记录数据异常。", e);
			return json;
		}
		return json;
	}
	
	/**
	 * 张璐-2015.11.1
	 * 用于查询检修数据，方便修改
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryJX")
	public EqpOverhaulBean queryJX(String id,HttpServletRequest request)throws Exception{
		try{
			EqpOverhaulBean bean = eqpOverhaulServiceI.queryJX(id);
			return bean;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
