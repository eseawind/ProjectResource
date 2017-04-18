package com.shlanbao.tzsc.pms.cos.maintain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosMatPriceMaintainDaoI;
import com.shlanbao.tzsc.base.dao.MdUnitDaoI;
import com.shlanbao.tzsc.base.mapping.CosMatPriceMaintain;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.cos.maintain.beans.MaintainBean;
import com.shlanbao.tzsc.pms.cos.maintain.beans.UnitBean;
import com.shlanbao.tzsc.pms.cos.maintain.service.MaintainServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class MaintainServiceImpl extends BaseService implements MaintainServiceI {
	@Autowired
	private MdUnitDaoI mdUnitDao;
	@Autowired
	private CosMatPriceMaintainDaoI cosMatPriceMaintainDao;
	
	@Override
	public DataGrid queryCosMaintain(MaintainBean maintainBean,PageParams pageParams) throws Exception {
		String hql = "from CosMatPriceMaintain o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		//辅料名称
		if(StringUtil.notNull(maintainBean.getMatName())) params.append(" and o.mdMat.name like '%"+maintainBean.getMatName()+"%'");
		//时间
		hql = hql.concat(StringUtil.fmtDateBetweenParams("o.takeeffectDate", maintainBean.getDate1(), maintainBean.getDate2()));
		
		List<CosMatPriceMaintain> cmp = cosMatPriceMaintainDao.queryByPage(hql.concat(params.toString()),pageParams);
		List<MaintainBean> beans = new ArrayList<MaintainBean>();
		for(CosMatPriceMaintain c : cmp){
			MaintainBean bean = new MaintainBean();
			if(null != c.getMdMat()) {//辅料
				bean.setMatId(c.getMdMat().getId());
				 bean.setMatName(c.getMdMat().getName());
			}
			if(null != c.getMdShift()) //班次
				bean.setShiftName(c.getMdShift().getName());
			
			if(null != c.getMdUnit())//单位
				bean.setUnitName(c.getMdUnit().getName());
			BeanConvertor.copyProperties(c, bean);
			beans.add(bean);
		}
		hql = "select count(*) from CosMatPriceMaintain o where 1=1 and o.del=0 ";
		
		long total = cosMatPriceMaintainDao.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(beans,total);
	}

	@Override
	public void addCosMaintain(MaintainBean maintainBean) throws Exception {
		CosMatPriceMaintain cosMatPriceMaintain = new CosMatPriceMaintain();
		BeanConvertor.copyProperties(maintainBean,cosMatPriceMaintain);
		cosMatPriceMaintain.setMdMat(new MdMat(maintainBean.getMatId()));
		if(!StringUtils.isEmpty(maintainBean.getShiftId())){
			cosMatPriceMaintain.setMdShift(new MdShift(maintainBean.getShiftId()));
		}
		cosMatPriceMaintain.setMdUnit(new MdUnit(maintainBean.getUnitId()));
		cosMatPriceMaintain.setDel(0L);
		cosMatPriceMaintainDao.save(cosMatPriceMaintain);
	}

	@Override
	public void editCosMaintain(MaintainBean maintainBean) throws Exception {
		CosMatPriceMaintain cosMatPriceMaintain = cosMatPriceMaintainDao.findById(CosMatPriceMaintain.class, maintainBean.getId());
		cosMatPriceMaintain.setMdUnit(new MdUnit(maintainBean.getUnitId()));
		cosMatPriceMaintain.setMdMat(new MdMat(maintainBean.getMatId()));
		cosMatPriceMaintain.setMdShift(new MdShift(maintainBean.getShiftId()));
		BeanConvertor.copyProperties(maintainBean,cosMatPriceMaintain);
	}

	@Override
	public void delCosMaintain(String id) throws Exception {
		cosMatPriceMaintainDao.findById(CosMatPriceMaintain.class, id).setDel(1L);
	}

	/**
	* @Title: getMaintainListByBean 
	* @Description: 根据Bean获取List 
	* @param  maintainBean
	* @return List<CosMatPriceMaintain>    返回类型 
	* @throws
	 */
	@Override
	public List<CosMatPriceMaintain> getMaintainListByBean(MaintainBean maintainBean){
		StringBuffer hql=new StringBuffer();
		hql.append("from CosMatPriceMaintain o where 1=1 and o.del=0 ");
		if(StringUtil.notNull(maintainBean.getMatName())){
			hql.append("and  o.mdMat.name='"+maintainBean.getMatName()+"' ");
		}
		return cosMatPriceMaintainDao.query(hql.toString());
	}
	
	@Override
	public MaintainBean getMaintainBean(String id) throws Exception {
		CosMatPriceMaintain cosMatPriceMaintain = cosMatPriceMaintainDao.findById(CosMatPriceMaintain.class, id);
		MaintainBean bean = new MaintainBean();
		if(null != cosMatPriceMaintain.getMdMat()) bean.setMatName(cosMatPriceMaintain.getMdMat().getName());
		
		if(null != cosMatPriceMaintain.getMdMat()) bean.setMatId(cosMatPriceMaintain.getMdMat().getId());
		
		if(null != cosMatPriceMaintain.getMdShift()) bean.setShiftId(cosMatPriceMaintain.getMdShift().getId());
		
		if(null != cosMatPriceMaintain.getMdUnit()) bean.setUnitId(cosMatPriceMaintain.getMdUnit().getId());
		
		if(null != cosMatPriceMaintain.getMdUnit()) bean.setUnitName(cosMatPriceMaintain.getMdUnit().getName());
		
		BeanConvertor.copyProperties(cosMatPriceMaintain,bean);
		return bean;
	}

	@Override
	public List<UnitBean> queryAllUtil() throws Exception {
		return BeanConvertor.copyList(mdUnitDao.query("from MdUnit o where o.del='0' and o.enable=1"), UnitBean.class);
	}
}
