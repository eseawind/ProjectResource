package com.shlanbao.tzsc.pms.cos.param.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosMatParamDaoI;
import com.shlanbao.tzsc.base.dao.MdEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.CosMatParam;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.cos.param.beans.CosMatParamBean;
import com.shlanbao.tzsc.pms.cos.param.service.CosMatParamServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class CosMatParamServiceImpl extends BaseService implements CosMatParamServiceI{
	
	@Autowired
	private CosMatParamDaoI cosMatParamDao;

	@Autowired
	private MdEqpTypeDaoI mdEqpTypeDao;
	
	@Override
	public void addCosMat(CosMatParamBean bean) throws Exception {
		CosMatParam cosMatParam = new CosMatParam();
		if(StringUtil.notNull(bean.getId())){
			cosMatParam = cosMatParamDao.findById(CosMatParam.class, bean.getId());
			BeanConvertor.copyProperties(bean, cosMatParam);
		}else{
			BeanConvertor.copyProperties(bean, cosMatParam);
			cosMatParam.setMdEqpType(new MdEqpType(bean.getMdEqpTypeId()));
			cosMatParam.setMdMat(new MdMat(bean.getMdMatId()));
			cosMatParam.setId(null);
		}
		cosMatParam.setDel(0L);
		cosMatParamDao.saveOrUpdate(cosMatParam);
	}

	@Override
	public DataGrid queryCosMat(CosMatParamBean cosMatParamBean, PageParams pageParams) throws Exception {
		String hql = "from CosMatParam o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(!(StringUtil.notNull(cosMatParamBean.getMdEqpTypeId()) && "null".equals(cosMatParamBean.getMdEqpTypeId()))) params.append(" and o.mdEqpType.id='" + cosMatParamBean.getMdEqpTypeId() + "'");
		
		List<CosMatParam> cmp = cosMatParamDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<CosMatParamBean> beans = new ArrayList<CosMatParamBean>();
		for(CosMatParam c : cmp){
			CosMatParamBean bean = new CosMatParamBean();
			if(null != c.getMdMat()) bean.setMdMatName(c.getMdMat().getName());
			bean.setMdEqpName(c.getMdEqpType().getName());
			BeanConvertor.copyProperties(c, bean);
			beans.add(bean);
		}
		hql = "select count(*) from CosMatParam o where 1=1 and o.del=0 ";
		long total = mdEqpTypeDao.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(beans,total);
	}

	/**
	 * 辅料考核参数查询
	 */
	public DataGrid queryMdType(MdEqpTypeBean mdTypeBean, PageParams pageParams) throws Exception {
		String hql = "from MdEqpType o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(mdTypeBean.getName())) params.append(" and o.name like '%" + mdTypeBean.getName() +"%'");
		
		if(StringUtil.notNull(mdTypeBean.getCode())) params.append(" and o.code like '%" + mdTypeBean.getCode() +"%'");
		
		//if(StringUtil.notNull(mdTypeBean.getCategoryId())) params.append(" and o.mdEqpCategory.id='"+mdTypeBean.getCategoryId()+"'");
		List<MdEqpType> rows = mdEqpTypeDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<MdEqpTypeBean> beans = new ArrayList<MdEqpTypeBean>();
		for (MdEqpType mdEqpType : rows) {
			MdEqpTypeBean bean = new MdEqpTypeBean();
			BeanConvertor.copyProperties(mdEqpType, bean);
			if(mdEqpType.getMdEqpCategory() != null){
				bean.setCategoryId(mdEqpType.getMdEqpCategory().getId());
				bean.setCategoryName(mdEqpType.getMdEqpCategory().getName());
			}
			beans.add(bean);
		}
		
		List<MdEqpTypeBean> r = BeanConvertor.copyList(rows, MdEqpTypeBean.class);
	/*	for (int i = 0; i < rows.size(); i++) {
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
	public void editCosMat(CosMatParamBean cosMatParamBean) throws Exception {
		CosMatParam cosMatParam = cosMatParamDao.findById(CosMatParam.class, cosMatParamBean.getId());
		BeanConvertor.copyProperties(cosMatParamBean,cosMatParam);
	}

	@Override
	public CosMatParamBean getCosMatById(String id) throws Exception {
		CosMatParam cosMatParam = cosMatParamDao.findById(CosMatParam.class, id);
		
		CosMatParamBean bean = new CosMatParamBean();
		bean.setMdEqpName(cosMatParam.getMdEqpType().getName());
		bean.setMdEqpTypeId(cosMatParam.getMdEqpType().getId());
		bean.setMdMatName(cosMatParam.getMdMat().getName());
		bean.setMdMatId(cosMatParam.getMdMat().getId());

		BeanConvertor.copyProperties(cosMatParam,bean);
		return bean;
	}

	@Override
	public void deleteCosMat(String id) throws Exception {
		cosMatParamDao.findById(CosMatParam.class, id).setDel(1L);
	}
}
