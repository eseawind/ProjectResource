package com.shlanbao.tzsc.pms.md.unit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdUnitDaoI;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.unit.beans.UnitBean;
import com.shlanbao.tzsc.pms.md.unit.service.UnitServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class UnitServiceImpl extends BaseService implements
		UnitServiceI {
	@Autowired
	private MdUnitDaoI mdUnitDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;
	@Override
	public List<UnitBean> queryAllUnitsForComboBox() {		
		try {
			return BeanConvertor.copyList(mdUnitDao.query("from MdUnit o where o.del='0' and o.enable=1 ORDER BY code asc"), UnitBean.class);
		} catch (Exception e) {
			//  TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void addUnit(UnitBean unitBean) throws Exception {
		MdUnit o = new MdUnit();
		BeanConvertor.copyProperties(unitBean, o);
		o.setDel("0");
		mdUnitDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.UNIT);
	}
	@Override
	public void editUnit(UnitBean unitBean) throws Exception {
		MdUnit o = mdUnitDao.findById(MdUnit.class, unitBean.getId());
		BeanConvertor.copyProperties(unitBean, o);
		loadComboboxService.initCombobox(ComboboxType.UNIT);
	}
	@Override
	public void deleteUnit(String id) throws Exception {
		mdUnitDao.updateByParams("update MdUnit o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.UNIT);
	}
	@Override
	public DataGrid getAllUnits(UnitBean unitBean) throws Exception {
		String hql = "from MdUnit o where o.del='0' and o.name like '%"+unitBean.getName()+"%' order by o.id asc";
		return new DataGrid(BeanConvertor.copyList(mdUnitDao.query(hql), UnitBean.class), 0L);
	}
	@Override
	public UnitBean getUnitById(String id) throws Exception{
		return BeanConvertor.copyProperties(mdUnitDao.findById(MdUnit.class, id),UnitBean.class);
	}

}
