package com.shlanbao.tzsc.pms.md.shift.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdShiftDaoI;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.shift.beans.ShiftBean;
import com.shlanbao.tzsc.pms.md.shift.service.ShiftServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class ShiftServiceImpl extends BaseService implements ShiftServiceI {
	@Autowired
	private MdShiftDaoI mdShiftDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;
	@Override
	public List<ShiftBean> queryAllShiftsForComboBox() throws Exception {		
			return BeanConvertor.copyList(mdShiftDao.query("from MdShift o where o.del='0' and o.enable=1 order by o.seq asc"), ShiftBean.class);
	}
	@Override
	public void addShift(ShiftBean shiftBean) throws Exception {
		MdShift o = new MdShift();
		BeanConvertor.copyProperties(shiftBean, o);
		o.setDel(0L);
		mdShiftDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.SHIFT);
	}
	@Override
	public void editShift(ShiftBean shiftBean) throws Exception {
		MdShift o = mdShiftDao.findById(MdShift.class, shiftBean.getId());
		BeanConvertor.copyProperties(shiftBean, o);
		loadComboboxService.initCombobox(ComboboxType.SHIFT);
	}
	@Override
	public void deleteShift(String id) throws Exception {
		mdShiftDao.updateByParams("update MdShift o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.SHIFT);
	}
	@Override
	public DataGrid getAllShifts(ShiftBean shiftBean) throws Exception {
		String hql = "from MdShift o where o.del='0' and o.name like '%"+shiftBean.getName()+"%' order by o.seq asc";
		return new DataGrid(BeanConvertor.copyList(mdShiftDao.query(hql), ShiftBean.class), 0L);
	}
	@Override
	public ShiftBean getShiftById(String id) throws Exception{
		return BeanConvertor.copyProperties(mdShiftDao.findById(MdShift.class, id),ShiftBean.class);
	}
}
