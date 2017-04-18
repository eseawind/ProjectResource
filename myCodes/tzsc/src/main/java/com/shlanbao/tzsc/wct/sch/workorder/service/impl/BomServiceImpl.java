package com.shlanbao.tzsc.wct.sch.workorder.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.base.dao.SchWorkorderBomDaoI;
import com.shlanbao.tzsc.base.mapping.SchWorkorderBom;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.workorder.beans.BomBean;
import com.shlanbao.tzsc.wct.sch.workorder.service.BomServiceI;
//@Service
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
				bean.setMat(schWorkorderBom.getMdMat().getSimpleName());
				bean.setQty(schWorkorderBom.getQty());
				bean.setUnit(schWorkorderBom.getMdUnit().getName());
				
				bomBeans.add(bean);
				
			}
			
			return new DataGrid(bomBeans, 0L);
			
		} catch (Exception e) {
			
			log.error("查询工单ID:["+workOrderId+"]的BOM异常", e);
			
		}
		
		return null;
	}
}
