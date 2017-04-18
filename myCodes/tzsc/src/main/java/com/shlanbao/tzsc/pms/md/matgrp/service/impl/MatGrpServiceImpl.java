package com.shlanbao.tzsc.pms.md.matgrp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdMatGrpDaoI;
import com.shlanbao.tzsc.base.mapping.MdMatGrp;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.matgrp.beans.MatGrpBean;
import com.shlanbao.tzsc.pms.md.matgrp.service.MatGrpServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class MatGrpServiceImpl extends BaseService implements MatGrpServiceI {
	@Autowired
	private MdMatGrpDaoI mdMatGrpDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;
	@Override
	public List<MatGrpBean> queryAllMatGrpsForComboBox() throws Exception {		
			return BeanConvertor.copyList(mdMatGrpDao.query("from MdMatGrp o where o.del='0' and o.enable=1 order by o.code asc"), MatGrpBean.class);
	}
	@Override
	public void addMatGrp(MatGrpBean matGrpBean) throws Exception {
		MdMatGrp o = new MdMatGrp();
		BeanConvertor.copyProperties(matGrpBean, o);
		o.setDel("0");
		mdMatGrpDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.MATGRP);
	}
	@Override
	public void editMatGrp(MatGrpBean matGrpBean) throws Exception {
		MdMatGrp o = mdMatGrpDao.findById(MdMatGrp.class, matGrpBean.getId());
		BeanConvertor.copyProperties(matGrpBean, o);
		loadComboboxService.initCombobox(ComboboxType.MATGRP);
	}
	@Override
	public void deleteMatGrp(String id) throws Exception {
		mdMatGrpDao.updateByParams("update MdMatGrp o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.MATGRP);
	}
	@Override
	public DataGrid getAllMatGrps(MatGrpBean matGrpBean) throws Exception {
		String hql = "from MdMatGrp o where o.del='0' and o.name like '%"+matGrpBean.getName()+"%' order by o.id asc";
		return new DataGrid(BeanConvertor.copyList(mdMatGrpDao.query(hql), MatGrpBean.class), 0L);
	}
	@Override
	public MatGrpBean getMatGrpById(String id) throws Exception{
		return BeanConvertor.copyProperties(mdMatGrpDao.findById(MdMatGrp.class, id),MatGrpBean.class);
	}
}
