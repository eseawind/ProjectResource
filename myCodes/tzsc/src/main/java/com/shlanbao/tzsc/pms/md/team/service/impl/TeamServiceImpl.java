package com.shlanbao.tzsc.pms.md.team.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdTeamDaoI;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.team.beans.TeamBean;
import com.shlanbao.tzsc.pms.md.team.service.TeamServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class TeamServiceImpl extends BaseService implements TeamServiceI {
	@Autowired
	private MdTeamDaoI mdTeamDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;
	@Override
	public List<TeamBean> queryAllTeamsForComboBox() throws Exception {		
			return BeanConvertor.copyList(mdTeamDao.query("from MdTeam o where o.del='0' and o.enable=1 order by o.seq asc"), TeamBean.class);
	}
	@Override
	public void addTeam(TeamBean teamBean) throws Exception {
		MdTeam o = new MdTeam();
		BeanConvertor.copyProperties(teamBean, o);
		o.setDel("0");
		mdTeamDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.TEAM);
	}
	@Override
	public void editTeam(TeamBean teamBean) throws Exception {
		MdTeam o = mdTeamDao.findById(MdTeam.class, teamBean.getId());
		BeanConvertor.copyProperties(teamBean, o);
		loadComboboxService.initCombobox(ComboboxType.TEAM);
	}
	@Override
	public void deleteTeam(String id) throws Exception {
		mdTeamDao.updateByParams("update MdTeam o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.TEAM);
	}
	@Override
	public DataGrid getAllTeams(TeamBean teamBean) throws Exception {
		String hql = "from MdTeam o where o.del='0' and o.name like '%"+teamBean.getName()+"%' order by o.seq asc";
		return new DataGrid(BeanConvertor.copyList(mdTeamDao.query(hql), TeamBean.class), 0L);
	}
	@Override
	public TeamBean getTeamById(String id) throws Exception{
		return BeanConvertor.copyProperties(mdTeamDao.findById(MdTeam.class, id),TeamBean.class);
	}
}
