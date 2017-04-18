package com.shlanbao.tzsc.pms.sys.scj.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.sys.scj.service.FivoServiceI;
@Controller
@RequestMapping("/pms/sys/scj")
public class FivoController  extends BaseController{
	@Autowired
	private FivoServiceI fivoService;
	@RequestMapping("/saveFivo")
	@ResponseBody
	public Json saveFivo(HttpSession session,String rid){
		SessionInfo userlogin=(SessionInfo) session.getAttribute("sessionInfo");
		//判断是否已添加
		Json json=new Json();
			boolean bl= fivoService.saveFaverite(userlogin.getUser().getId(),rid);
			if(bl){
				json.setMsg("收藏成功");
				json.setSuccess(true);
			}else{
				json.setMsg("收藏失败");
				json.setSuccess(false);
			}
		return json;
	}
	@RequestMapping("/deleteFivo")
	@ResponseBody
	public Json deleteFavoirte(HttpSession session,String rid){
		SessionInfo userlogin=(SessionInfo) session.getAttribute("sessionInfo");
		Json json=new Json();
		boolean bl= fivoService.deleteFaverite(rid,userlogin.getUser().getId());
		if(bl){
			json.setMsg("取消收藏成功");
			json.setSuccess(true);
		}else{
			json.setMsg("取消收藏失败");
			json.setSuccess(false);
		}
		return json;
	}
}
