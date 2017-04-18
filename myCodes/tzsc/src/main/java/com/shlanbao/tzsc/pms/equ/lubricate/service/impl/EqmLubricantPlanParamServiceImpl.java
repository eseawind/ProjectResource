package com.shlanbao.tzsc.pms.equ.lubricate.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantPlanParamDaoI;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantPlanParamBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanParamServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmLubricantPlanParamServiceImpl extends BaseService implements EqmLubricantPlanParamServiceI{

	@Autowired
	private EqmLubricantPlanParamDaoI lubricantPlanParamDao;
	@Autowired
	private BaseDaoI<Object> baseDao;
	
	@Override
	public boolean savePlanParam(EqmLubricantPlanParamBean bean) {
		
		return false;
	}

	//query to DataGrid
	@Override
	public DataGrid queryDataGrid(EqmLubricantPlanParamBean bean,PageParams pageParams) throws Exception {
		String hql="from EqmLubricantPlanParam o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getPlan_id())){
			params.append("and o.plan_id = '" + bean.getPlan_id() +"' ");
		}
		List<EqmLubricantPlanParam> rows = lubricantPlanParamDao.queryByPage(hql.concat(params.toString()),pageParams);
		hql = "select count(*) from EqmLubricantPlanParam o where 1=1 ";
		long total=lubricantPlanParamDao.queryTotal(hql.concat(params.toString()));
		List<EqmLubricantPlanParamBean> beans = new ArrayList<EqmLubricantPlanParamBean>();
		for(EqmLubricantPlanParam b:rows){
			EqmLubricantPlanParamBean plan=new EqmLubricantPlanParamBean();
			beanConvertor.copyProperties(b, plan);
			if(b.getEnd_user()!=null){
				plan.setEnd_user_name(b.getEnd_user().getName());
			}
			if(b.getEnd_time()!=null){
				plan.setEnd_times(DateUtil.formatDateToString(b.getEnd_time(), "yyyy-MM-dd"));
			}
			beans.add(plan);
		}
		return new DataGrid(beans,total);
	}
	@Override
	public DataGrid queryDataGridByPlanId(String id) throws Exception{
		String hql="from EqmLubricantPlanParam o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(id)){
			params.append("and o.plan_id = '" + id +"' ");
		}
		List<EqmLubricantPlanParam> rows = lubricantPlanParamDao.query(hql.concat(params.toString())+" order by o.code ");
		List<EqmLubricantPlanParamBean> beans = new ArrayList<EqmLubricantPlanParamBean>();
		for(EqmLubricantPlanParam b:rows){
			EqmLubricantPlanParamBean plan=new EqmLubricantPlanParamBean();
			beanConvertor.copyProperties(b, plan);
			if(b.getEnd_user()!=null){
				plan.setEnd_user_name(b.getEnd_user().getName());
			}
			if(b.getEnd_time()!=null){
				plan.setEnd_times(DateUtil.formatDateToString(b.getEnd_time(), "yyyy-MM-dd HH:mm:ss"));
			}
			beans.add(plan);
		}
		return new DataGrid(beans,1000L);
	}

	//save
	@Override
	public boolean savePlanParams(EqmLubricantPlanParam bean) {
		lubricantPlanParamDao.save(bean);
		return true;
	}

	//根据id查询ui bean
	@Override
	public EqmLubricantPlanParamBean getBeanByIds(String id) throws Exception {
		EqmLubricantPlanParam bean=getBeanById(id);
		EqmLubricantPlanParamBean b=new EqmLubricantPlanParamBean();
		beanConvertor.copyProperties(bean, b);
		if(bean.getEnd_user()!=null){
			b.setEnd_user_id(bean.getEnd_user().getId());
			b.setEnd_user_name(bean.getEnd_user().getName());
		}
		return b;
	}
	//根据ID查询实体bean
	public EqmLubricantPlanParam getBeanById(String id) {
		return lubricantPlanParamDao.findById(EqmLubricantPlanParam.class, id);
	}

	//修改
	@Override
	public boolean updateBean(EqmLubricantPlanParamBean bean) throws Exception {
		EqmLubricantPlanParam b=getBeanById(bean.getId());
		beanConvertor.copyProperties(bean, b);
		lubricantPlanParamDao.saveOrUpdate(b);
		return true;
	}

	
}
