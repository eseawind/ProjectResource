package com.shlanbao.tzsc.pms.md.team.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.team.beans.TeamBean;
import com.shlanbao.tzsc.pms.md.team.service.TeamServiceI;
/**
 * 班组
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/team")
public class TeamController extends BaseController {
	@Autowired
	private TeamServiceI teamService;
	/**
	 * 查询所有班组
	 */
	@ResponseBody
	@RequestMapping("/getAllTeams")
	public DataGrid getAllTeams(TeamBean teamBean){
		try {
			return teamService.getAllTeams(teamBean);
		} catch (Exception e) {
			log.error("查询所有班组异常", e);
		}
		return null;
	}
	/**
	 * 跳转到班组新增页面
	 */
	@RequestMapping("/goToTeamAddJsp")
	public String goToTeamAddJsp(String id){
		return "/pms/md/team/teamAdd";
	}
	/**
	 * 新增班组
	 */
	@ResponseBody
	@RequestMapping("/addTeam")
	public Json addTeam(TeamBean teamBean){
		Json json=new Json();
		try {
			teamService.addTeam(teamBean);
			json.setMsg("新增班组成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增班组失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到班组编辑页面
	 */
	@RequestMapping("/goToTeamEditJsp")
	public String goToTeamEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("team", teamService.getTeamById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的班组失败", e);
		}
		return "/pms/md/team/teamEdit";
	}
	/**
	 * 编辑班组
	 */
	@ResponseBody
	@RequestMapping("/editTeam")
	public Json editTeam(TeamBean teamBean){
		Json json=new Json();
		try {
			teamService.editTeam(teamBean);
			json.setMsg("编辑班组成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑班组失败!");
		}
		return json;
	}
	/**
	 * 删除班组
	 */
	@ResponseBody
	@RequestMapping("/deleteTeam")
	public Json deleteTeam(String id){
		Json json=new Json();
		try {
			teamService.deleteTeam(id);
			json.setMsg("删除班组成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除班组失败!");
		}
		return json;
	}
}
