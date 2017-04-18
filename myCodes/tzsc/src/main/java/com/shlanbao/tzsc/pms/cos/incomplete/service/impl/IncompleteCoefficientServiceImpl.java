package com.shlanbao.tzsc.pms.cos.incomplete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.shlanbao.tzsc.base.dao.CosIncompleteCoefficientDaoI;
import com.shlanbao.tzsc.base.mapping.CosIncompleteCoefficient;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.incomplete.service.IncompleteCoefficientServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class IncompleteCoefficientServiceImpl implements IncompleteCoefficientServiceI {

	@Autowired
	public  CosIncompleteCoefficientDaoI incompleteCoefficientDao;
	
	@Override
	public CosIncompleteCoefficient queryBeanById(String id) {
		return incompleteCoefficientDao.findById(CosIncompleteCoefficient.class, id);
	}

	/**
	 * 查询
	 */
	@Override
	public List<CosIncompleteCoefficient> queryBeanListByBean(CosIncompleteCoefficient bean) {
		String hql="from CosIncompleteCoefficient o where 1=1 ";
		StringBuffer params=new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append("and o.id='"+bean.getId()+"' ");
		if(StringUtil.notNull(bean.getCode())) params.append("and o.code='"+bean.getCode()+"' ");
		if(StringUtil.notNull(bean.getName())) params.append("and o.name='"+bean.getName()+"' ");
		return incompleteCoefficientDao.query(hql.concat(params.toString()));
	}

	/**
	 * 界面查询，返回DataGrid
	 */
	@Override
	public DataGrid queryBeanGridByBean(CosIncompleteCoefficient bean,PageParams pageParams) {
		String hql="from CosIncompleteCoefficient o where 1=1 ";
		StringBuffer params=new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append("and o.id='"+bean.getId()+"' ");
		if(StringUtil.notNull(bean.getCode())) params.append("and o.code='"+bean.getCode()+"' ");
		if(StringUtil.notNull(bean.getName())) params.append("and o.name like '%"+bean.getName()+"%' ");
		List<CosIncompleteCoefficient> lists=incompleteCoefficientDao.queryByPage(hql.concat(params.toString()), pageParams);
		hql = "select count(*) from CosMatAssess o where 1=1  ";
		long total = incompleteCoefficientDao.queryTotal(hql.concat(params.toString()));
		return new DataGrid(lists,total);
	}

	/**
	 * 添加或修改
	 */
	@Override
	public boolean addOrUpdateBean(CosIncompleteCoefficient bean) {
		return incompleteCoefficientDao.saveOrUpdate(bean);
	}
}
