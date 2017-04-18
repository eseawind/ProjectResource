package com.shlanbao.tzsc.pms.equ.lubricate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.EqmLubricantDaoI;
import com.shlanbao.tzsc.base.mapping.EqmLubricant;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class EqmLubricantServiceImpl extends BaseService implements EqmLubricantServiceI {
	@Autowired
	private EqmLubricantDaoI lubricantDao;
	@Autowired
	private BaseDaoI<Object> baseDao;
	//新增
	@Override
	public boolean addBean(EqmLubricantBean bean) {
		EqmLubricant b=new EqmLubricant();
		b.setCreate_time(new Date());
		b.setCreate_usr(new SysUser(bean.getCreate_uId()));
		b.setCycle(bean.getCycle());
		b.setEqp_type(new MdEqpType(bean.getEqp_typeId()));
		/*b.setLast_time(null);
		b.setNext_time(null);*/
		b.setName(bean.getName());
		b.setStatus(0);
		b.setRule_id(bean.getRuleId());
		b.setRule_name(bean.getRuleName());
		b.setEqp_category(new MdEqpCategory(bean.getEqp_category()));
		lubricantDao.save(b);
		return true;
	}
	
	//根据Id获取对象
	@Override
	public EqmLubricant getBeanById(String id){
		return lubricantDao.findById(EqmLubricant.class, id);
	}
	//根据Id获取对象
	@Override
	public EqmLubricantBean getBeanByIds(String id){
		StringBuffer hql=new StringBuffer();
		hql.append("select o.id,o.name,");
		hql.append("o.eqp_type.id,");
		hql.append("o.eqp_type.name,");
		hql.append("o.create_usr.id ,");
		hql.append("o.create_usr.name ,");
		hql.append("o.cycle,");//周期
		hql.append("o.last_time,");
		hql.append("o.next_time,");
		hql.append("o.create_time,");
		hql.append("o.rule_id,");
		hql.append("o.rule_name,");
		hql.append("o.status, ");
		hql.append("o.eqp_category.id ");
		hql.append("from EqmLubricant o where 1=1 ");
		if(StringUtil.notNull(id)){
			hql.append("and o.id='"+id+"' ");
		}
		List<Object> list=baseDao.query(hql.toString());
		Object[] temp=(Object[]) list.get(0);
		EqmLubricantBean b=new EqmLubricantBean();
		b.setId(temp[0].toString());
		b.setName(temp[1].toString());
		b.setEqp_typeId(temp[2].toString());
		b.setEqp_typeName(temp[3].toString());
		b.setCreate_uId(temp[4].toString());
		b.setCreate_uName(temp[5].toString());
		b.setCycle(Integer.parseInt(temp[6].toString()));
		if(temp[7]!=null)b.setLast_time(temp[7].toString());
		if(temp[8]!=null)b.setNext_time(temp[8].toString());
		if(temp[9]!=null)b.setCreate_time(temp[9].toString());
		b.setRuleId(temp[10].toString());
		if(temp[11]!=null)
			b.setRuleName(temp[11].toString());
		b.setStatus(Integer.parseInt(temp[12].toString()));
		b.setEqp_category(temp[13].toString());
		return b;
	}
	//修改
	@Override
	public boolean updateBean(EqmLubricantBean bean) {
		EqmLubricant b=getBeanById(bean.getId());
		/*b.setCreate_time(DateUtil.formatStringToDate(bean.getCreate_time()));
		b.setCreate_usr(new SysUser(bean.getCreate_uId()));*/
		b.setCycle(bean.getCycle());
		b.setEqp_type(new MdEqpType(bean.getEqp_typeId()));
		/*b.setLast_time(b.getLast_time());
		b.setNext_time(b.getNext_time());*/
		b.setName(bean.getName());
		//b.setStatus(bean.getStatus());
		b.setEqp_category(new MdEqpCategory(bean.getEqp_category()));
		lubricantDao.saveOrUpdate(b);
		return true;
	}

	//查询
	@Override
	public DataGrid searchBean(EqmLubricantBean seachBean,PageParams pageParams) {
		StringBuffer hql=new StringBuffer();
		hql.append("select o.id,o.name,");
		hql.append("o.eqp_type.id,");
		hql.append("o.eqp_type.name,");
		hql.append("o.create_usr.id ,");
		hql.append("o.create_usr.name ,");
		hql.append("o.cycle,");//周期
		hql.append("o.last_time,");
		hql.append("o.next_time,");
		hql.append("o.create_time,");
		hql.append("o.rule_id,");
		hql.append("o.rule_name,");
		hql.append("o.status, ");
		hql.append("o.eqp_category.id ");
		hql.append("from EqmLubricant o where 1=1 ");
		if(StringUtil.notNull(seachBean.getId())){
			hql.append("and o.id='"+seachBean.getId()+"' ");
		}
		
		if(StringUtil.notNull(seachBean.getEqp_typeId())){
			hql.append("and o.eqp_type.id='"+seachBean.getEqp_typeId()+"' ");
		}
		
		if(seachBean.getStatus()>-1){
			hql.append("and o.status='"+seachBean.getStatus()+"' ");
		}
		long total=baseDao.query(hql.toString()).size();
		//hql.append("order by o.date_p,o.shift.code ");
		List<Object> list=baseDao.queryByPage(hql.toString(), pageParams);
		List<EqmLubricantBean> returnList=new ArrayList<EqmLubricantBean>();
		for(Object o:list){
			Object[] temp=(Object[]) o;
			EqmLubricantBean b=new EqmLubricantBean();
			b.setId(temp[0].toString());
			b.setName(temp[1].toString());
			b.setEqp_typeId(temp[2].toString());
			b.setEqp_typeName(temp[3].toString());
			b.setCreate_uId(temp[4].toString());
			b.setCreate_uName(temp[5].toString());
			b.setCycle(Integer.parseInt(temp[6].toString()));
			if(temp[7]!=null)b.setLast_time(temp[7].toString());
			if(temp[8]!=null)b.setNext_time(temp[8].toString());
			if(temp[9]!=null)b.setCreate_time(temp[9].toString());
			b.setRuleId(temp[10].toString());
			if(temp[11]!=null)
				b.setRuleName(temp[11].toString());
			b.setStatus(Integer.parseInt(temp[12].toString()));
			b.setEqp_category(temp[13].toString());
			returnList.add(b);
		}
		return new DataGrid(returnList,total);
	}
	//生产润滑计划
	public void createLubricantPlan(String lubriId,String eqpId){
		EqmLubricant  lubri=getBeanById(lubriId);
		EqmLubricantPlan plan=new EqmLubricantPlan();
		plan.setDate_p(new Date());
		plan.setEqp(new MdEquipment(eqpId));
		plan.setLub_id(lubri.getRule_id());
		plan.setStatus(0);
		
	}
	@Override
	public List<?> getListBySql(String sql){
		return baseDao.queryBySql(sql);
	}

}
