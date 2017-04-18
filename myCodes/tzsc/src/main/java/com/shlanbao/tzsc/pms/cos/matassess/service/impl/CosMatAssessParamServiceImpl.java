package com.shlanbao.tzsc.pms.cos.matassess.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosMatAssessDaoI;
import com.shlanbao.tzsc.base.dao.CosMatAssessParamDaoI;
import com.shlanbao.tzsc.base.dao.MdMatDaoI;
import com.shlanbao.tzsc.base.dao.SchConStdDaoI;
import com.shlanbao.tzsc.base.mapping.CosMatAssess;
import com.shlanbao.tzsc.base.mapping.CosMatAssessParam;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.SchConStd;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.matassess.bean.CosMatAssessParamBean;
import com.shlanbao.tzsc.pms.cos.matassess.service.CosMatAssessParamServiceI;
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
public class CosMatAssessParamServiceImpl implements CosMatAssessParamServiceI{

	
	@Autowired
	private CosMatAssessParamDaoI cosMatAssessParamDao;
	@Autowired
	private CosMatAssessDaoI cosMatAssessDao;
	@Autowired
	private SchConStdDaoI schConStdDaol;
	@Autowired
	private MdMatDaoI mdMatDao;
	
	//添加或修改Bean
	@Override
	public boolean addOrUpdateBean(CosMatAssessParamBean bean) throws Exception {
		CosMatAssessParam cosMatAssessParam=new CosMatAssessParam();
		if(!StringUtil.notNull(bean.getId())){
			bean.setId(null);
		}else{
			cosMatAssessParam=getBeanByIds(bean.getId());
		}
		cosMatAssessParam.setVal(bean.getVal());
		cosMatAssessParam.setRemark(bean.getRemark());
		cosMatAssessParam.setUnitprice(bean.getUnitprice());
		cosMatAssessParam.setUval(bean.getUval());
		cosMatAssessParam.setLval(bean.getLval());
		if(StringUtil.notNull(bean.getMatId())){
			MdMat m=mdMatDao.findById(MdMat.class, bean.getMatId());
			if(m!=null){
				cosMatAssessParam.setMat(m);
			}
		}
		if(StringUtil.notNull(bean.getCid())){
			CosMatAssess c=cosMatAssessDao.findById(CosMatAssess.class, bean.getCid());
			if(c!=null){
				cosMatAssessParam.setCid(c);
			}
		}
		if(StringUtil.notNull(bean.getStdID())){
			SchConStd s=schConStdDaol.findById(SchConStd.class, bean.getStdID());
			if(s!=null){
				cosMatAssessParam.setStdID(s);
			}
		}
		cosMatAssessParamDao.saveOrUpdate(cosMatAssessParam);
		return true;
	}
	//根据ID获取mapping bean
	@Override
	public CosMatAssessParam getBeanByIds(String id) throws Exception {
		return cosMatAssessParamDao.findById(CosMatAssessParam.class, id);
	}
	
	//根据ID获取bean
	@Override
	public CosMatAssessParamBean getBeanById(String id) throws Exception {
		CosMatAssessParam c = cosMatAssessParamDao.findById(CosMatAssessParam.class, id);
		CosMatAssessParamBean b=new CosMatAssessParamBean();
		b.setVal(c.getVal());
		b.setRemark(c.getRemark());
		b.setUnitprice(c.getUnitprice());
		b.setUval(c.getUval());
		b.setLval(c.getLval());
		b.setCid(c.getCid().getId());
		b.setId(c.getId());
		b.setMatId(c.getMat().getId());
		b.setMatName(c.getMat().getName());
		b.setStdID(c.getStdID().getId());
		return b;
	}
	//查询
	@Override
	public List<CosMatAssessParam> queryBeans(CosMatAssessParam bean,PageParams pageParams){
		String hql="from CosMatAssessParam o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(bean.getCid()!=null&&StringUtil.notNull(bean.getCid().getId())) params.append(" and o.cid='" + bean.getCid().getId() + "'");
		if(StringUtil.notNull(bean.getMat().getId())) params.append(" and o.mdmat.id='"+bean.getMat().getId()+"'");
		return  cosMatAssessParamDao.queryByPage(hql.concat(params.toString()),pageParams);
	}
	
	//查询
	@Override
	public List<CosMatAssessParam> queryBeansByExpand(CosMatAssessParamBean bean,PageParams pageParams){
		String hql="from CosMatAssessParam o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(StringUtil.notNull(bean.getCid())) params.append(" and o.cid='" + bean.getCid() + "'");
		if(StringUtil.notNull(bean.getMatId())) params.append(" and o.mat='"+bean.getMatId()+"'");
		return  cosMatAssessParamDao.queryByPage(hql.concat(params.toString()),pageParams);
	}
	
	//界面中翻页查询
	@Override
	public DataGrid queryBean(CosMatAssessParam bean, PageParams pageParams) throws Exception {
		String hql="from CosMatAssessParam o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(bean.getCid()!=null&&StringUtil.notNull(bean.getCid().getId())) params.append(" and o.cid='" + bean.getCid().getId() + "'");
		List<CosMatAssessParam> oldbeans = cosMatAssessParamDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<CosMatAssessParamBean> beans=new ArrayList<CosMatAssessParamBean>();
		for(CosMatAssessParam c:oldbeans){
			CosMatAssessParamBean b=new CosMatAssessParamBean();
			b.setLval(c.getLval());
			b.setRemark(c.getRemark());
			b.setUnitprice(c.getUnitprice());
			b.setUval(c.getUval());
			b.setVal(c.getVal());
			b.setCid(c.getCid().getId());
			b.setId(c.getId());
			b.setMatId(c.getMat().getId());
			b.setMatName(c.getMat().getName());
			b.setStdID(c.getStdID().getId());
			beans.add(b);
		}
		hql = "select count(*) from CosMatAssessParam o where 1=1  ";
		long total = cosMatAssessParamDao.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(beans,total);
	}
	
}

