package com.shlanbao.tzsc.pms.sch.workorder.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchWorkorderBomDaoI;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.mapping.SchWorkorderBom;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.workorder.beans.BomBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.BomServiceI;
@Service
public class BomServiceImpl extends BaseService implements BomServiceI{
	@Autowired
	private SchWorkorderBomDaoI schWorkorderBomDao;

	@Override
	public DataGrid queryBomByWorkOrder(String workOrderId) {
		
		try {
			String hql = "from SchWorkorderBom o join fetch o.mdMat m join fetch o.mdUnit u where o.schWorkorder.id=?";
			
			List<SchWorkorderBom> schWorkorderBoms = schWorkorderBomDao.query(hql, workOrderId);
			
			List<BomBean> bomBeans = new ArrayList<BomBean>();
			
			
			for (SchWorkorderBom schWorkorderBom : schWorkorderBoms) {
				BomBean bean = new BomBean();	
				bean.setId(schWorkorderBom.getId());
				bean.setMat(schWorkorderBom.getMdMat().getSimpleName());
				bean.setQty(schWorkorderBom.getQty());
				bean.setUnit(schWorkorderBom.getMdUnit().getName());
				bean.setTypeName(schWorkorderBom.getMdMat().getMdMatType().getName());
				bomBeans.add(bean);
				
			}
			
			return new DataGrid(bomBeans, 0L);
			
		} catch (Exception e) {
			
			log.error("查询工单ID:["+workOrderId+"]的BOM异常", e);
			
		}
		
		return null;
	}
	@Override
	public void addBom(BomBean bomBean) {
		SchWorkorderBom o = new SchWorkorderBom();
		o.setMdMat(new MdMat(bomBean.getMat()));
		o.setQty(bomBean.getQty());
		o.setMdUnit(new MdUnit(bomBean.getUnit()));
		o.setSchWorkorder(new SchWorkorder(bomBean.getOrderId()));
		schWorkorderBomDao.save(o);
	}
	@Override
	public void editBom(BomBean bomBean) {
		SchWorkorderBom o = schWorkorderBomDao.findById(SchWorkorderBom.class, bomBean.getId());
		o.setMdMat(new MdMat(bomBean.getMat()));
		o.setQty(bomBean.getQty());
		o.setMdUnit(new MdUnit(bomBean.getUnit()));
		//o.setSchWorkorder(new SchWorkorder(bomBean.getOrderId()));
	}
	@Override
	public void deleteBom(String id) {
		schWorkorderBomDao.deleteById(id, SchWorkorderBom.class);
	}
	@Override
	public BomBean getBomById(String id) {
		SchWorkorderBom schWorkorderBom = schWorkorderBomDao.findById(SchWorkorderBom.class, id);
		BomBean bean = new BomBean();	
		bean.setId(schWorkorderBom.getId());
		bean.setMat(schWorkorderBom.getMdMat().getSimpleName());
		bean.setMatId(schWorkorderBom.getMdMat().getId());
		bean.setQty(schWorkorderBom.getQty());
		bean.setUnit(schWorkorderBom.getMdUnit().getName());
		bean.setUnitId(schWorkorderBom.getMdUnit().getId());
		return bean;
	}
}
