package com.shlanbao.tzsc.pms.cos.matassess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosMatAssessDaoI;
import com.shlanbao.tzsc.base.dao.MdEqpTypeDaoI;
import com.shlanbao.tzsc.base.dao.MdMatDaoI;
import com.shlanbao.tzsc.base.mapping.CosMatAssess;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.matassess.bean.CosMatAssessBean;
import com.shlanbao.tzsc.pms.cos.matassess.service.CosMatAssessServiceI;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: CosMatAssessServiceImpl 
* @Description: 辅料考核版本控制ServiceImpl 
* @author luoliang
* @date 2015-1-7 上午10:44:37 
*
 */
@Service
public class CosMatAssessServiceImpl implements CosMatAssessServiceI{

	private static final String STATUS1="新建";
	private static final String STATUS2="生效";
	private static final String STATUS3="归档";
	
	@Autowired
	private CosMatAssessDaoI cosMatAssessDao;
	@Autowired
	private MdMatDaoI mdMatDao;
	@Autowired
	private MdEqpTypeDaoI mdEqpTypeDao;
	
	//添加或修改Bean
	@Override
	public boolean addOrUpdateBean(CosMatAssessBean bean) throws Exception {
		CosMatAssess cosMatAssess=new CosMatAssess();
		if(!StringUtil.notNull(bean.getId())){
			bean.setId(null);
			cosMatAssess.setStatus(STATUS1);
			cosMatAssess.setStatusLasting(DateUtil.getNowDateTime(null));
		}else{
			cosMatAssess=getBeanByIds(bean.getId());
		}
		BeanConvertor.copyProperties(bean,cosMatAssess);
		if(StringUtil.notNull(bean.getMdMatId())){
			MdMat mat=mdMatDao.findById(MdMat.class, bean.getMdMatId());
			if(mat!=null){
				cosMatAssess.setMdMat(mat);
			}
		}else{
			throw new Exception();
		}
		if(StringUtil.notNull(bean.getEqpTypeID())){
			MdEqpType mdEqpType=mdEqpTypeDao.findById(MdEqpType.class, bean.getEqpTypeID());
			if(mdEqpType!=null){
				cosMatAssess.setEqpType(mdEqpType);
			}
		}
		cosMatAssessDao.saveOrUpdate(cosMatAssess);
		return true;
	}
	//根据ID获取mapping bean
	@Override
	public CosMatAssess getBeanByIds(String id) throws Exception {
		return cosMatAssessDao.findById(CosMatAssess.class, id);
	}
	//版本生效
	@Override
	public boolean editStatus(String id) throws Exception{
		CosMatAssess cosMatAssess=getBeanByIds(id);
		//查询当前 机型、牌号有没有生效的版本
		CosMatAssess bean=new CosMatAssess();
		bean.setStatus(STATUS2);
		bean.setEqpType(cosMatAssess.getEqpType());
		bean.setMdMat(cosMatAssess.getMdMat());
		List<CosMatAssess> oldBeans=queryBeans(bean,new PageParams());
		//如果存在已经生效的版本，则修改状态为生效状态，并且修改状态变化日期
		for(CosMatAssess c:oldBeans){
			c.setStatus(STATUS3);
			c.setStatusLasting(DateUtil.getNowDateTime(null));
			cosMatAssessDao.saveOrUpdate(c);
		}
		//生效当前
		cosMatAssess.setStatus(STATUS2);
		cosMatAssess.setStatusLasting(DateUtil.getNowDateTime(null));
		cosMatAssessDao.saveOrUpdate(cosMatAssess);
		return true;
	}
	
	//根据ID获取bean
	@Override
	public CosMatAssessBean getBeanById(String id) throws Exception {
		if(!StringUtil.notNull(id)){
			return null;
		}
		CosMatAssess c = cosMatAssessDao.findById(CosMatAssess.class, id);
		CosMatAssessBean b=new CosMatAssessBean();
		BeanConvertor.copyProperties(c,b);
		b.setMdMatId(c.getMdMat().getId());
		b.setMdMatName(c.getMdMat().getName());
		b.setEqpTypeName(c.getEqpType().getName());
		b.setEqpTypeID(c.getEqpType().getId());
		b.setEqpTypeDes(c.getEqpType().getDes());
		return b;
	}
	//查询
	@Override
	public List<CosMatAssess> queryBeans(CosMatAssess bean,PageParams pageParams){
		String hql="from CosMatAssess o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(StringUtil.notNull(bean.getStatus())) params.append(" and o.status='" + bean.getStatus() + "'");
		if(bean.getEqpType()!=null) params.append(" and o.eqpType.id='" + bean.getEqpType().getId() + "'");
		if(bean.getMdMat()!=null) params.append(" and o.mdMat.id='" + bean.getMdMat().getId() + "'");
		return  cosMatAssessDao.queryByPage(hql.concat(params.toString()),pageParams);
	}
	//界面中翻页查询
	@Override
	public DataGrid queryBean(CosMatAssess bean, PageParams pageParams) throws Exception {
		String hql="from CosMatAssess o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(StringUtil.notNull(bean.getStatus())) params.append(" and o.status='" + bean.getStatus() + "'");
		if(bean.getEqpType()!=null&&StringUtil.notNull(bean.getEqpType().getId())) params.append(" and o.eqpType.id='" + bean.getEqpType().getId() + "'");
		if(bean.getMdMat()!=null&&StringUtil.notNull(bean.getMdMat().getId())) params.append(" and o.mdMat.id='" + bean.getMdMat().getId() + "'");
		List<CosMatAssess> oldbeans = cosMatAssessDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<CosMatAssessBean> beans=new ArrayList<CosMatAssessBean>();
		for(CosMatAssess c:oldbeans){
			CosMatAssessBean b=new CosMatAssessBean();
			BeanConvertor.copyProperties(c, b);
			b.setMdMatId(c.getMdMat().getId());
			b.setMdMatName(c.getMdMat().getName());
			b.setEqpTypeName(c.getEqpType().getName());
			b.setEqpTypeID(c.getEqpType().getId());
			b.setEqpTypeDes(c.getEqpType().getDes());
			beans.add(b);
		}
		hql = "select count(*) from CosMatAssess o where 1=1  ";
		long total = cosMatAssessDao.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(beans,total);
	}

	//界面中翻页查询
	@Override
	public DataGrid queryBeanByExtends(CosMatAssessBean bean, PageParams pageParams) throws Exception {
		String hql="from CosMatAssess o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())){
			params.append(" and o.id='" + bean.getId() + "'");
		}
		if(StringUtil.notNull(bean.getStatus())) {
			params.append(" and o.status='" + bean.getStatus() + "'");
		}
		if(StringUtil.notNull(bean.getEqpTypeID())) {
			params.append(" and o.eqpType.id='" + bean.getEqpTypeID() + "'");
		}
		if(StringUtil.notNull(bean.getMdMatId())) {
			params.append(" and o.mdMat.id='" + bean.getMdMatId() + "'");
		}
		List<CosMatAssess> oldbeans = cosMatAssessDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<CosMatAssessBean> beans=new ArrayList<CosMatAssessBean>();
		for(CosMatAssess c:oldbeans){
			CosMatAssessBean b=new CosMatAssessBean();
			BeanConvertor.copyProperties(c, b);
			b.setMdMatId(c.getMdMat().getId());
			b.setMdMatName(c.getMdMat().getName());
			b.setEqpTypeName(c.getEqpType().getName());
			b.setEqpTypeID(c.getEqpType().getId());
			b.setEqpTypeDes(c.getEqpType().getDes());
			beans.add(b);
		}
		hql = "select count(*) from CosMatAssess o where 1=1  ";
		long total = cosMatAssessDao.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(beans,total);
	}

	
	
	
}

