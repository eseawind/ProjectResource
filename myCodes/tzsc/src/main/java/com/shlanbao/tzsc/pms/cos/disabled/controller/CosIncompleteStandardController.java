package com.shlanbao.tzsc.pms.cos.disabled.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.CosIncompleteStandard;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.disabled.service.CosIncompleteStandardServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
* @ClassName: CosIncompleteStandardServiceI 
* @Description: 机台对应班次储烟量维护 
* @author luoliang
* @date 2015年1月5日11:11:44
*
 */
@Controller
@RequestMapping("/pms/disabled")
public class CosIncompleteStandardController extends BaseController {
	@Autowired
	private CosIncompleteStandardServiceI cosIncompleteStandardService;
	
	//跳转到添加界面
	@RequestMapping("/gotoDis")
	public String gotoDis(){
		return "/pms/cos/disabled/addOrUpdateDis";
	}
	
	@RequestMapping("/gotoEditDis")
	public String gotoEditDis(HttpServletRequest request,String id){
		try {
			request.setAttribute("disBean",cosIncompleteStandardService.getBeanById(id));
			request.setAttribute("id","i");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改残烟丝配置信息时读取信息异常。", e);
		}
		return "/pms/cos/disabled/addOrUpdateDis";
	}
	
	@ResponseBody
	@RequestMapping("/queryDis")
	public DataGrid queryDis(CosIncompleteStandard bean,PageParams pageParams){
		try {
			DataGrid gd = cosIncompleteStandardService.queryCosDisabled(bean, pageParams);
			System.out.println(gd.toString());
			return gd;
		} catch (Exception e) {
			log.error("查询残烟丝配置信息异常。", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public Json addOrUpdate(CosIncompleteStandard bean){
		Json json = new Json();
		try {
			boolean isNotNull=false;
			//当前牌号单支重量是否存在验证
			if(StringUtil.notNull(bean.getId())){
				String id=bean.getId();
				bean.setId(null);
				List<CosIncompleteStandard> b=cosIncompleteStandardService.queryCosDisabled(bean);
				for(CosIncompleteStandard c:b){
					if(!c.getId().equals(id)){
						isNotNull=true;
						break;
					}
				}
				bean.setId(id);
			}else{
				List<CosIncompleteStandard> b=cosIncompleteStandardService.queryCosDisabled(bean);
				if(b!=null&&b.size()>0){
					isNotNull=true;
				}
			}
			if(isNotNull){
				json.setMsg("此牌号的单支烟支重量信息已存在!");
				json.setSuccess(false);
				return json;
			}
			if(cosIncompleteStandardService.addOrUpdateBean(bean)){
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
