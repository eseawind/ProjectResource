package com.shlanbao.tzsc.pms.md.eqptype.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;
import com.shlanbao.tzsc.pms.md.eqptype.service.MdEqpTypeServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 设备类型与型号实现类
 * @author liuligong 
 * @Time 2014/11/5 10:46
 */
@Service
public class MdEqpTypeServiceImpl extends BaseService implements MdEqpTypeServiceI {
	@Autowired
	private MdEqpTypeDaoI mdEqpTypeDao;
	@Autowired
	private LoadComboboxServiceI  comboboxServiceI;
	
	@Override
	public void addMdType(MdEqpTypeBean mdEqpTypeBean) throws Exception {
		MdEqpType mdEqpType = BeanConvertor.copyProperties(mdEqpTypeBean, MdEqpType.class);
		if(StringUtil.notNull(mdEqpTypeBean.getId())){
			mdEqpType.setId(mdEqpTypeBean.getId());
		}else{
			mdEqpType.setId(null);
		}
		if(StringUtil.notNull(mdEqpTypeBean.getCategoryId())){
			mdEqpType.setMdEqpCategory(new MdEqpCategory(mdEqpTypeBean.getCategoryId()));
		}
		mdEqpType.setDel("0");
		mdEqpTypeDao.saveOrUpdate(mdEqpType);
		comboboxServiceI.initCombobox(ComboboxType.EQPTYPE);
	}

	@Override
	public MdEqpTypeBean getTypeById(String id) throws Exception {
		MdEqpType mdEqpType = mdEqpTypeDao.findById(MdEqpType.class, id);
		MdEqpTypeBean mdEqpTypeBean = BeanConvertor.copyProperties(mdEqpType, MdEqpTypeBean.class);
	/*	if("0".equals(mdEqpType.getEnable())) mdEqpTypeBean.setEnable("已启用");
		
		if("1".equals(mdEqpType.getEnable())) mdEqpTypeBean.setEnable("未启用");*/
		if(mdEqpType.getMdEqpCategory() != null){
			mdEqpTypeBean.setCategoryId(mdEqpType.getMdEqpCategory().getId());
			mdEqpTypeBean.setCategoryName(mdEqpType.getMdEqpCategory().getName());
		}
		return mdEqpTypeBean;
	}

	@Override
	public DataGrid queryMdType(MdEqpTypeBean mdTypeBean, PageParams pageParams) throws Exception {
		String hql = "from MdEqpType o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(mdTypeBean.getName())) params.append(" and o.name like '%" + mdTypeBean.getName() +"%'");
		
		if(StringUtil.notNull(mdTypeBean.getCode())) params.append(" and o.code like '%" + mdTypeBean.getCode() +"%'");
		
		if(StringUtil.notNull(mdTypeBean.getCategoryId())) params.append(" and o.mdEqpCategory.id='"+mdTypeBean.getCategoryId()+"'");
		List<MdEqpType> rows = mdEqpTypeDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<MdEqpTypeBean> beans = new ArrayList<MdEqpTypeBean>();
		for (MdEqpType mdEqpType : rows) {
			MdEqpTypeBean bean = new MdEqpTypeBean();
			beanConvertor.copyProperties(mdEqpType, bean);
			if(mdEqpType.getMdEqpCategory() != null){
				bean.setCategoryId(mdEqpType.getMdEqpCategory().getId());
				bean.setCategoryName(mdEqpType.getMdEqpCategory().getName());
			}
			beans.add(bean);
		}
		
		/*
		List<MdEqpTypeBean> r = BeanConvertor.copyList(rows, MdEqpTypeBean.class);
		for (int i = 0; i < rows.size(); i++) {
			MdEqpTypeBean metb = (MdEqpTypeBean)rows.get(i);
			if("0".equals(metb.getEnable())) {
				metb.setEnableStr("已启用");
				break;
			}
			
			if("1".equals(metb.getEnable())){
				metb.setEnableStr("未启用");
				break;
			}
		}*/
		hql = "select count(*) from MdEqpType o where 1=1 and o.del=0 ";
		long total = mdEqpTypeDao.queryTotal(hql.concat(params.toString()));
		return new DataGrid(beans,total);
	}

	@Override
	public void deleteMdType(String id) throws Exception {
		mdEqpTypeDao.findById(MdEqpType.class, id).setDel("1");
		comboboxServiceI.initCombobox(ComboboxType.EQPTYPE);
	}

	@Override
	public List<MdEqpTypeBean> queryMdType(String categoryId) throws Exception {
		String hql = "from MdEqpType o where 1=1 and o.del=0";
		if(StringUtil.notNull(categoryId)){
			hql+=" and o.mdEqpCategory.id='"+categoryId+"' ";
		}
		List<MdEqpType> eqpTypes = mdEqpTypeDao.query(hql, new Object[]{});
		List<MdEqpTypeBean> beans = new ArrayList<MdEqpTypeBean>();
		for (MdEqpType mdEqpType : eqpTypes) {
			MdEqpTypeBean bean = new MdEqpTypeBean();
			beanConvertor.copyProperties(mdEqpType, bean);
			if(mdEqpType.getMdEqpCategory() != null){
				bean.setCategoryId(mdEqpType.getMdEqpCategory().getId());
				bean.setCategoryName(mdEqpType.getMdEqpCategory().getName());
			}
			beans.add(bean);
		}
		return beans;
	}
	@Override
	public List<MdEqpTypeBean> queryMdEqpTypeByCategory(String[] categoryIds)throws Exception{
		String hql = "from MdEqpType o where 1=1 and o.del=0 ";
		hql+=" and o.mdEqpCategory.id in ("+StringUtil.arrayToStringBySqlin(categoryIds)+") ";
		List<MdEqpType> eqpTypes = mdEqpTypeDao.query(hql);
		List<MdEqpTypeBean> beans = new ArrayList<MdEqpTypeBean>();
		for (MdEqpType mdEqpType : eqpTypes) {
			MdEqpTypeBean bean = new MdEqpTypeBean();
			beanConvertor.copyProperties(mdEqpType, bean);
			if(mdEqpType.getMdEqpCategory() != null){
				bean.setCategoryId(mdEqpType.getMdEqpCategory().getId());
				bean.setCategoryName(mdEqpType.getMdEqpCategory().getName());
			}
			beans.add(bean);
		}
		return beans;
	}
}
