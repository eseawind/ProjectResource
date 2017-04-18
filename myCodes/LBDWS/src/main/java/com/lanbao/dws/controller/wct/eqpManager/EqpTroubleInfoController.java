package com.lanbao.dws.controller.wct.eqpManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.JsonBean;
import com.lanbao.dws.model.wct.eqpManager.EqmTroubleInfoBean;
import com.lanbao.dws.service.wct.eqpManager.IEqpTroubleInfoService;

@Controller
@RequestMapping("/wct/trouble")
public class EqpTroubleInfoController {
	@Autowired
	IEqpTroubleInfoService eqpTroubleInfoService;
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月20日 下午7:20:38 
	* 功能说明 ：查询故障数数据
	 */
	@RequestMapping("/queryTroubleInfo")
	public String queryTroubleInfo(EqmTroubleInfoBean troubleBean,String url,Model model){
		String treeStr=eqpTroubleInfoService.getTroubleTreeHtml(troubleBean);
		model.addAttribute("troubleTree", treeStr);
		model.addAttribute("sourceId", troubleBean.getSourceId());
		return  url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月21日 上午9:13:18 
	* 功能说明 ：添加故障描述（只有第5级节点数据可以添加）
	 */
	@ResponseBody
	@RequestMapping("/addNewTrouble")
	public String addNewTrouble(EqmTroubleInfoBean troubleBean){
		return eqpTroubleInfoService.addNewTrouble(troubleBean);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月21日 上午10:29:58 
	* 功能说明 ：删除故障（第五级）
	 */
	@RequestMapping("/deleteNewTrouble")
	public void deleteTroubles(String ids){
		eqpTroubleInfoService.deleteTroubles(ids);
	}
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月22日 上午9:22:14 
	* 功能说明 ：保存故障记录    EQM_TROUBLE
	 */
	@ResponseBody
	@RequestMapping("/addEqmTrouble")
	public String addEqmTrouble(String troubles,String sourceId){
		JsonBean json=new JsonBean();
		int flag=eqpTroubleInfoService.addEqmTrouble(troubles,sourceId);
		if(flag==0){
			json.setFlag(true);
		}else{
			json.setMsg("保存故障记录失败！");
		}
		return GsonUtil.bean2Json(json);
	}
}
