package com.shlanbao.tzsc.wct.eqm.lube.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantPlanParamDaoI;
import com.shlanbao.tzsc.base.dao.SysEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanParamBeans;
import com.shlanbao.tzsc.wct.eqm.lube.service.EqmLubricantPlanParamsServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

@Service
public class EqmLubricantPlanParamsServiceImpl extends BaseService implements EqmLubricantPlanParamsServiceI{

	@Autowired
	private EqmLubricantPlanParamDaoI lubricantPlanParamDao;
	@Autowired
	private SysEqpTypeDaoI partDao;
	@Autowired
	private BaseDaoI<Object> baseDao;
	
	@Override
	public boolean savePlanParam(EqmLubricantPlanParamBeans bean) {
		
		return false;
	}

	//query to DataGrid 
	@Override
	public DataGrid queryDataGrid(EqmLubricantPlanParamBeans bean,PageParams pageParams) throws Exception {
		String hql="from EqmLubricantPlanParam o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getPlan_id())){
			params.append("and o.plan_id = '" + bean.getPlan_id() +"' ");
		}
		List<EqmLubricantPlanParam> rows = lubricantPlanParamDao.queryByPage(hql.concat(params.toString()),pageParams);
		hql = "select count(*) from EqmLubricantPlanParam o where 1=1 ";
		long total=lubricantPlanParamDao.queryTotal(hql.concat(params.toString()));
		List<EqmLubricantPlanParamBeans> beans = new ArrayList<EqmLubricantPlanParamBeans>();
		for(EqmLubricantPlanParam b:rows){
			EqmLubricantPlanParamBeans plan=new EqmLubricantPlanParamBeans();
			beanConvertor.copyProperties(b, plan);
			if(b.getEnd_user()!=null){
				plan.setEnd_user_name(b.getEnd_user().getName());
			}
			beans.add(plan);
		}
		return new DataGrid(beans,total);
	}
	@Override
	public DataGrid queryDataGridByPlanId(String id,String type) throws Exception{
		String hql="from EqmLubricantPlanParam o left join fetch o.sysEqpType s where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(id)){
			params.append("and o.plan_id = '" + id +"' ");
		}
		if(null!=type&&!"".equals(type)){
			params.append("and s.rhPart = '" + type +"' ");
		}
		
		List<EqmLubricantPlanParam> rows = lubricantPlanParamDao.query(hql.concat(params.toString())+" order by o.code ");
		List<EqmLubricantPlanParamBeans> beans = new ArrayList<EqmLubricantPlanParamBeans>();
		for(EqmLubricantPlanParam b:rows){
			EqmLubricantPlanParamBeans plan=new EqmLubricantPlanParamBeans();
			beanConvertor.copyProperties(b, plan);
			if(null!=b.getSysEqpType()){
				plan.setUploadUrl(b.getSysEqpType().getFilePath());//设置图片路径
				plan.setSetImagePoint(b.getSysEqpType().getSetImagePoint());//设置图片热点代码
				plan.setRhPart(b.getSysEqpType().getRhPart());//设置部位
			}
			if(null!=b.getEnd_user()){
				plan.setEnd_user_name(b.getEnd_user().getName());
			}
			
			beans.add(plan);
		}
		return new DataGrid(beans,1000L);
	}
	
	
	@Override
	public EqmLubricantPlanParamBeans getTypeById(String id) throws Exception {
		EqmLubricantPlanParam mdEqpType = lubricantPlanParamDao.findById(EqmLubricantPlanParam.class, id);
		EqmLubricantPlanParamBeans bean = BeanConvertor.copyProperties(mdEqpType, EqmLubricantPlanParamBeans.class);
		if(null!=mdEqpType.getSysEqpType()){
			bean.setSetImagePoint(mdEqpType.getSysEqpType().getSetImagePoint());
			bean.setUploadUrl(mdEqpType.getSysEqpType().getFilePath());
		}
		return bean;
	}
	
	/**
	 * piontInfo 查询
	 */
	@Override
	public EqmLubricantPlanParamBeans piontInfo(String id) throws Exception {
		EqmLubricantPlanParam pointinfo = lubricantPlanParamDao.findById(EqmLubricantPlanParam.class, id);
		EqmLubricantPlanParamBeans bean = BeanConvertor.copyProperties(pointinfo, EqmLubricantPlanParamBeans.class);
		if(null!=pointinfo.getSysEqpType()){
			bean.setOiltd(pointinfo.getOiltd());//润滑介质
			bean.setEnd_user_name(pointinfo.getEnd_user().getName());//润滑人
			bean.setEnd_time(pointinfo.getEnd_time());//润滑时间
		}
		return bean;
	}


	//save
	@Override
	public boolean savePlanParams(EqmLubricantPlanParam bean) {
		lubricantPlanParamDao.save(bean);
		return true;
	}

	//根据id查询ui bean
	@Override
	public EqmLubricantPlanParamBeans getBeanByIds(String id) throws Exception {
		EqmLubricantPlanParam bean=getBeanById(id);
		EqmLubricantPlanParamBeans b=new EqmLubricantPlanParamBeans();
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
	public boolean updateBean(EqmLubricantPlanParamBeans bean) throws Exception {
		EqmLubricantPlanParam b=getBeanById(bean.getId());
		beanConvertor.copyProperties(bean, b);
		lubricantPlanParamDao.saveOrUpdate(b);
		return true;
	}

	//WCT设备润滑保存完成人，完成日期
	@Override
	public boolean lubricantParamEnd(EqmLubricantPlanParamBeans[] beans,LoginBean login) {
		String user_id=login.getUserId();
		for(EqmLubricantPlanParamBeans bean:beans){
			EqmLubricantPlanParam b=this.getBeanById(bean.getId());
			if(b.getEnd_user()==null){
				b.setEnd_time(new Date());
				b.setEnd_user(new SysUser(user_id));
				lubricantPlanParamDao.saveOrUpdate(b);
			}
		}
		return true;
	}

	
	//润滑ZF部位查询
	@Override
	public DataGrid findZF(String rhPart) throws Exception {
		String hql="from SysEqpType s where 1=1";
		StringBuffer params = new StringBuffer(hql);
		//濮撳悕妯＄硦鏌ヨ
		if(StringUtil.notNull(rhPart)){
			params.append(" and s.rhPart = '" + rhPart +"' ");
		}
		
		List<SysEqpType> rows = partDao.query(params.toString());
		List<EqmLubricantPlanParamBeans> beans = new ArrayList<EqmLubricantPlanParamBeans>();
		for(SysEqpType b:rows){
			EqmLubricantPlanParamBeans plan=new EqmLubricantPlanParamBeans();
			beanConvertor.copyProperties(b, plan);
			if(null!=b.getRhPart()){
				plan.setRhPart(b.getRhPart());//查询润滑部位
			}
			beans.add(plan);
		}
		
		return new DataGrid(beans,1000L);
	}

	
}
