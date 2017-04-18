package com.shlanbao.tzsc.pms.equ.param.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmParamDaoI;
import com.shlanbao.tzsc.base.mapping.EqmParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.param.service.EqmParamServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmParamServiceImpl implements EqmParamServiceI {

	@Autowired
	public  EqmParamDaoI eqmParamDaoI;
	
	@Override
	public EqmParam queryBeanById(String id) {
		return eqmParamDaoI.findById(EqmParam.class, id);
	}

	/**
	 * 查询
	 */
	@Override
	public List<EqmParam> queryBeanListByBean(EqmParam bean) {
		String hql="from EqmParam o where 1=1 ";
		StringBuffer params=new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append("and o.id='"+bean.getId()+"' ");
		if(StringUtil.notNull(bean.getCode())) params.append("and o.code='"+bean.getCode()+"' ");
		if(StringUtil.notNull(bean.getName())) params.append("and o.name='"+bean.getName()+"' ");
		return eqmParamDaoI.query(hql.concat(params.toString()));
	}

	/**
	 * 界面查询，返回DataGrid
	 */
	@Override
	public DataGrid queryBeanGridByBean(EqmParam bean,PageParams pageParams) {
		String hql="from EqmParam o where 1=1 ";
		StringBuffer params=new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append("and o.id='"+bean.getId()+"' ");
		if(StringUtil.notNull(bean.getCode())) params.append("and o.code='"+bean.getCode()+"' ");
		if(StringUtil.notNull(bean.getName())) params.append("and o.name like '%"+bean.getName()+"%' ");
		List<EqmParam> lists=eqmParamDaoI.queryByPage(hql.concat(params.toString()), pageParams);
		hql = "select count(*) from EqmParam o where 1=1  ";
		long total = eqmParamDaoI.queryTotal(hql.concat(params.toString()));
		return new DataGrid(lists,total);
	}

	/**
	 * 添加或修改
	 */
	@Override
	public boolean addOrUpdateBean(EqmParam bean) {
		return eqmParamDaoI.saveOrUpdate(bean);
	}
}
