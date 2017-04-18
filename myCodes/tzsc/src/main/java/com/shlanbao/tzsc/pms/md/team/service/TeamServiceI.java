package com.shlanbao.tzsc.pms.md.team.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.team.beans.TeamBean;

public interface TeamServiceI {
	/**
	 * 查询所有班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<TeamBean> queryAllTeamsForComboBox() throws Exception ;
	/**
	 * 查询所有班次
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllTeams(TeamBean teamBean) throws Exception;
	/**
	 * 新增班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param TeamBean
	 * @throws Exception
	 */
	public void addTeam(TeamBean teamBean) throws Exception;
	/**
	 * 编辑班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param TeamBean
	 * @throws Exception
	 */
	public void editTeam(TeamBean teamBean) throws Exception;
	/**
	 * 删除班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteTeam(String id) throws Exception;
	/**
	 * 工具ID获取班次
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public TeamBean getTeamById(String id) throws Exception;
}
