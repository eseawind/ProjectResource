package com.shlanbao.tzsc.pms.cos.disabled.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosIncompleteStandardDaoI;
import com.shlanbao.tzsc.base.mapping.CosIncompleteStandard;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.disabled.bean.CosIncStandardBean;
import com.shlanbao.tzsc.pms.cos.disabled.service.CosIncompleteStandardServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;
import com.shlanbao.tzsc.pms.md.eqptype.service.MdEqpTypeServiceI;
import com.shlanbao.tzsc.pms.md.shift.beans.ShiftBean;
import com.shlanbao.tzsc.pms.md.shift.service.ShiftServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;


/**
* @ClassName: CosIncompleteStandardServiceI 
* @Description: 机台对应班次储烟量维护 实现类
* @author luoliang
* @date 2015年1月4日08:41:23
*
 */
@Service
public class CosIncompleteStandardServiceImpl implements CosIncompleteStandardServiceI{
	
	@Autowired
	private CosIncompleteStandardDaoI cosIncompleteStandardDaoImpl;
	@Autowired
	private ShiftServiceI shiftServiceI;
	@Autowired
	private MdEqpTypeServiceI mdEqpTypeService;
	
	/**
	 * 查询Bean
	 */
	@Override
	public DataGrid queryCosDisabled(CosIncompleteStandard bean,PageParams pageParams) throws Exception {
		String hql = "from CosIncompleteStandard o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(StringUtil.notNull(bean.getShift())) params.append(" and o.mdShift.id='" + bean.getShift() + "'");
		if(StringUtil.notNull(bean.getModelType()))	params.append(" and o.mdEqupType.id='" + bean.getModelType() + "'");
		List<CosIncompleteStandard> cips = cosIncompleteStandardDaoImpl.queryByPage(hql.concat(params.toString()),pageParams);
		List<CosIncStandardBean> cbeans=new ArrayList<CosIncStandardBean>();
		for(CosIncompleteStandard c:cips){
			CosIncStandardBean cis=new CosIncStandardBean();
			cis.setShiftName(c.getMdShift().getName());
			cis.setModelTypeName(c.getMdEqupType().getName());
			cis.setId(c.getId());
			cis.setModelType(c.getModelType());
			cis.setShift(c.getShift());
			cis.setStorage_smoke(c.getStorage_smoke());
			cbeans.add(cis);
		}
		hql = "select count(*) from CosIncompleteStandard o where 1=1  ";
		long total = cosIncompleteStandardDaoImpl.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(cbeans,total);
	}
	
	/**
	 * 添加或修改Bean
	 */
	@Override
	public boolean addOrUpdateBean(CosIncompleteStandard bean) throws Exception {
		if(!StringUtil.notNull(bean.getId())){
			bean.setId(null);
		}
		if(StringUtil.notNull(bean.getModelType())){
			MdEqpTypeBean mt=mdEqpTypeService.getTypeById(bean.getModelType());
			if(mt!=null){
				MdEqpType me=BeanConvertor.copyProperties(mt, MdEqpType.class);
				bean.setMdEqupType(me);
			}
		}
		if(StringUtil.notNull(bean.getShift())){
			//bean.setMdShift();
			ShiftBean s= shiftServiceI.getShiftById(bean.getShift());
			if(s!=null){
				MdShift mdShift=BeanConvertor.copyProperties(s, MdShift.class);
				bean.setMdShift(mdShift);
			}
		}
		
		
		return cosIncompleteStandardDaoImpl.saveOrUpdate(bean);
	}
	
	/**
	 * 根据ID读取Bean
	 */
	public CosIncStandardBean getBeanById(String id) throws Exception{
		CosIncompleteStandard c=cosIncompleteStandardDaoImpl.findById(CosIncompleteStandard.class, id);
		if(c!=null){
			CosIncStandardBean cis=BeanConvertor.copyProperties(c, CosIncStandardBean.class);
			cis.setShiftName(c.getMdShift().getName());
			cis.setModelTypeName(c.getMdEqupType().getName());
			cis.setModelType(c.getMdEqupType().getId());
			cis.setShift(c.getMdShift().getId());
			return cis;
		}
		return null;
	}

	/**
	 * 查询Bean
	 */
	@Override
	public List<CosIncompleteStandard> queryCosDisabled(CosIncompleteStandard bean) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from CosIncompleteStandard o where 1=1 ");
		if(StringUtil.notNull(bean.getId())) hql.append(" and o.id='" + bean.getId() + "'");
		if(StringUtil.notNull(bean.getShift())) hql.append(" and o.mdShift.id='" + bean.getShift() + "'");
		if(StringUtil.notNull(bean.getModelType()))	hql.append(" and o.mdEqupType.id='" + bean.getModelType() + "'");
		return cosIncompleteStandardDaoImpl.query(hql.toString());
	}
}
