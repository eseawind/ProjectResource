package com.shlanbao.tzsc.pms.md.eqpparam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdEquipmentParamDaoI;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdEquipmentParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.md.eqpparam.beans.EquipmentParamBean;
import com.shlanbao.tzsc.pms.md.eqpparam.service.EquipmentParamServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class EquipmentParamServiceImpl extends BaseService implements EquipmentParamServiceI{
	@Autowired
	private MdEquipmentParamDaoI mdEquipmentParamDao;
	@Override
	public void addEquipmentParam(EquipmentParamBean equipmentParamBean) throws Exception {
		
		MdEquipmentParam o = new MdEquipmentParam();
		
		BeanConvertor.copyProperties(equipmentParamBean, o);
		
		o.setMdEquipment(new MdEquipment(equipmentParamBean.getEquipment()));
		
		mdEquipmentParamDao.save(o);
		
	}
	@Override
	public void editEquipmentParam(EquipmentParamBean equipmentParamBean) throws Exception {
		
		MdEquipmentParam o = mdEquipmentParamDao.findById(MdEquipmentParam.class, equipmentParamBean.getId());
		
		BeanConvertor.copyProperties(equipmentParamBean, o);
		
		o.setMdEquipment(new MdEquipment(equipmentParamBean.getEquipment()));
	}
	@Override
	public void deleteEquipmentParam(String id) throws Exception {
		
		mdEquipmentParamDao.deleteById(id, MdEquipmentParam.class);
	
	}
	@Override
	public DataGrid getAllEquipmentParams(EquipmentParamBean equipmentParamBean) throws Exception {
		
		String hql = "from MdEquipmentParam o left join fetch o.mdEquipment me where 1=1";
		
		if(StringUtil.notNull(equipmentParamBean.getWorkshop())){
			
			hql = hql.concat(" and me.mdWorkshop.id='"+equipmentParamBean.getWorkshop()+"'");
		
		}
		
		//and o.name like '%"+EquipmentParam.getName()+"%' order by o.seq asc";
		List<EquipmentParamBean> list = new ArrayList<EquipmentParamBean>();
		
		for (MdEquipmentParam mdEquipmentParam :mdEquipmentParamDao.query(hql)) {
			
			EquipmentParamBean bean = BeanConvertor.copyProperties(mdEquipmentParam, EquipmentParamBean.class);
			
			if(mdEquipmentParam.getMdEquipment()!= null){
				
				bean.setEquipment(mdEquipmentParam.getMdEquipment().getEquipmentName());//设备
				
			}
			
			list.add(bean);
			
		}
		
		return new DataGrid(list, 0L);
	}
	@Override
	public EquipmentParamBean getEquipmentParamById(String id) throws Exception{
		
		MdEquipmentParam mdEquipmentParam = mdEquipmentParamDao.findById(MdEquipmentParam.class, id);
		
		EquipmentParamBean bean = BeanConvertor.copyProperties(mdEquipmentParam, EquipmentParamBean.class);
		
		if(mdEquipmentParam.getMdEquipment()!= null){
			
			bean.setEquipment(mdEquipmentParam.getMdEquipment().getId());//设备
			bean.setEquipmentName(mdEquipmentParam.getMdEquipment().getEquipmentName());//设备
			
		}
		
		return bean;
		
	}
}
