package com.shlanbao.tzsc.pms.md.workshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdWorkshopDaoI;
import com.shlanbao.tzsc.base.mapping.MdWorkshop;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.workshop.beans.WorkShopBean;
import com.shlanbao.tzsc.pms.md.workshop.service.WorkShopServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class WorkShopServiceImpl extends BaseService implements
		WorkShopServiceI {
	@Autowired
	private MdWorkshopDaoI mdWorkshopDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;
	@Override
	public List<WorkShopBean> queryAllWorkShopsForComboBox() throws Exception {		
			return BeanConvertor.copyList(mdWorkshopDao.query("from MdWorkshop o where o.del='0' and o.enable=1"), WorkShopBean.class);
	}
	@Override
	public void addWorkShop(WorkShopBean WorkShopBean) throws Exception {
		MdWorkshop o = new MdWorkshop();
		BeanConvertor.copyProperties(WorkShopBean, o);
		o.setDel("0");
		mdWorkshopDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.WORKSHOP);
	}
	@Override
	public void editWorkShop(WorkShopBean WorkShopBean) throws Exception {
		MdWorkshop o = mdWorkshopDao.findById(MdWorkshop.class, WorkShopBean.getId());
		BeanConvertor.copyProperties(WorkShopBean, o);
		loadComboboxService.initCombobox(ComboboxType.WORKSHOP);
	}
	@Override
	public void deleteWorkShop(String id) throws Exception {
		mdWorkshopDao.updateByParams("update MdWorkshop o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.WORKSHOP);
	}
	@Override
	public DataGrid getAllWorkShops(WorkShopBean workShopBean) throws Exception {
		String hql = "from MdWorkshop o where o.del='0' and o.name like '%"+workShopBean.getName()+"%' order by o.seq asc";
		return new DataGrid(BeanConvertor.copyList(mdWorkshopDao.query(hql), WorkShopBean.class), 0L);
	}
	@Override
	public WorkShopBean getWorkShopById(String id) throws Exception{
		return BeanConvertor.copyProperties(mdWorkshopDao.findById(MdWorkshop.class, id),WorkShopBean.class);
	}

}
