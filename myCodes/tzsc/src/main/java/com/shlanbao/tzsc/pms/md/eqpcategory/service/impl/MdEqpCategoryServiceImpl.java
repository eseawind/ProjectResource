package com.shlanbao.tzsc.pms.md.eqpcategory.service.impl;

import java.beans.beancontext.BeanContext;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.dao.MdEqpCategoryDaoI;
import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.eqpcategory.beans.MdEqpCategoryBean;
import com.shlanbao.tzsc.pms.md.eqpcategory.service.MdEqpCategoryServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.thoughtworks.xstream.converters.basic.DateConverter;

@Service
public class MdEqpCategoryServiceImpl extends BaseService implements MdEqpCategoryServiceI {
	@Autowired
	protected MdEqpCategoryDaoI mdEqpCategoryDao;
	@Autowired
	private LoadComboboxServiceI  comboboxServiceI;
	
	@Override
	public void addMdCategory(MdEqpCategory mdEqpCategory) throws Exception {
		MdEqpCategory categroy = null;		
			categroy = mdEqpCategoryDao.findById(MdEqpCategory.class, mdEqpCategory.getId());	
			categroy = mdEqpCategory;
			categroy.setDel("0");
		mdEqpCategoryDao.saveOrUpdate(categroy);
		comboboxServiceI.initCombobox(ComboboxType.EQPCATEGORY);
	}

	@Override
	public MdEqpCategory getMdCategoryById(String id) throws Exception {
		return mdEqpCategoryDao.findById(MdEqpCategory.class, id);
	}

	@Override
	public DataGrid queryMdCategory(MdEqpCategory mdEqpCategory,PageParams pageParams) throws Exception {
		String hql = "from MdEqpCategory o where o.del=0";
		StringBuffer params = new StringBuffer();
		//设置查询参数
		if(StringUtil.notNull(mdEqpCategory.getCode())) params.append(" and o.code like '%" + mdEqpCategory.getCode() +"%'");
		if(StringUtil.notNull(mdEqpCategory.getName())) params.append(" and o.name like '%" + mdEqpCategory.getName() +"%'");
		//获取类型列表
		List<MdEqpCategory> categories = mdEqpCategoryDao.queryByPage(hql.concat(params.toString()), pageParams);
		//转换类型列表
		List<MdEqpCategoryBean> rows = beanConvertor.copyList(categories, MdEqpCategoryBean.class);
		//查询总数
		hql = "select count(*)" + hql;
		long total = mdEqpCategoryDao.queryTotal(hql.concat(params.toString()));
		return new DataGrid(rows, total);
	}

	@Override
	public void deleteMdCategory(String id) throws Exception {
		mdEqpCategoryDao.findById(MdEqpCategory.class, id).setDel("1");
		comboboxServiceI.initCombobox(ComboboxType.EQPCATEGORY);
	}

	@Override
	public List<MdEqpCategoryBean> queryMdCategory() throws Exception {
		String hql = "from MdEqpCategory o where 1=1 and o.enable=1 and o.del=0";
		List<MdEqpCategoryBean> mdeqpcategropbean=beanConvertor.copyList(mdEqpCategoryDao.query(hql, new Object[]{}), MdEqpCategoryBean.class);
		return mdeqpcategropbean;
	}

	@Override
	public void updateCategory(MdEqpCategoryBean mdEqpCategoryBean) throws Exception {
		mdEqpCategoryBean.setDel("0");
		mdEqpCategoryDao.saveOrUpdate(BeanConvertor.copyProperties(mdEqpCategoryBean, MdEqpCategory.class));
		comboboxServiceI.initCombobox(ComboboxType.EQPCATEGORY);
	}
}
