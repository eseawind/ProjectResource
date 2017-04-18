package com.shlanbao.tzsc.pms.cos.disabled.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.CosPartWeight;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.disabled.bean.CosPartWeightBean;
import com.shlanbao.tzsc.pms.cos.disabled.service.CosPartWeightServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: CosPartWeightController 
* @Description: 烟支重量维护
* @author luoliang
* @date 2015-1-5 下午02:53:20 
*
 */
@Controller
@RequestMapping("/pms/partWeight")
public class CosPartWeightController extends BaseController {
	@Autowired
	private CosPartWeightServiceI cosPartWeightService;
	
	//跳转到添加界面
	@RequestMapping("/gotoPartWeight")
	public String gotoDis(){
		return "/pms/cos/disabled/addOrUpdatePartWidth";
	}
	
	@RequestMapping("/gotoEditPartWeight")
	public String gotoEditPartWeight(HttpServletRequest request,String id){
		try {
			request.setAttribute("bean",cosPartWeightService.getBeanById(id));
			request.setAttribute("id","i");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改烟支重量配置信息时读取信息异常。", e);
		}
		return "/pms/cos/disabled/addOrUpdatePartWidth";
	}
	
	@ResponseBody
	@RequestMapping("/queryPartWeight")
	public DataGrid queryPartWeight(CosPartWeightBean bean,PageParams pageParams){
		try {
			DataGrid gd = cosPartWeightService.queryCosPartWeight(bean, pageParams);
			System.out.println(gd.toString());
			return gd;
		} catch (Exception e) {
			log.error("查询烟支重量配置信息异常。", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/addOrUpdatePartWeight")
	public Json addOrUpdatePartWeight(CosPartWeight bean){
		Json json = new Json();
		try {
			boolean isNotNull=false;
			//当前牌号单支重量是否存在验证
			if(StringUtil.notNull(bean.getId())){
				List<CosPartWeight> b=cosPartWeightService.getBeanByPartNumber(bean.getPartNumber());
				for(CosPartWeight c:b){
					if(!c.getId().equals(bean.getId())){
						isNotNull=true;
						break;
					}
				}
			}else{
				CosPartWeight b=cosPartWeightService.getBeanByPartNumber(null,bean.getPartNumber());
				if(b!=null){
					isNotNull=true;
				}
			}
			if(isNotNull){
				json.setMsg("此牌号的单支烟支重量信息已存在!");
				json.setSuccess(false);
				return json;
			}
			if(cosPartWeightService.addOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("操作失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加或修改残烟丝配置信息异常。", e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
}
