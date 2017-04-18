package com.shlanbao.tzsc.pms.md.mat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdMatDaoI;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdMatType;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.mat.beans.MatBean;
import com.shlanbao.tzsc.pms.md.mat.service.MatServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class MatServiceImpl extends BaseService implements MatServiceI{
	@Autowired
	private MdMatDaoI mdMatDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;

	@Override
	public List<MatBean> queryAllMatsForComboBox() throws Exception {
		String hql = "from MdMat o where o.mdMatType.code=?" ;
		//mdMatType.code=1 成品  2辅料
		return BeanConvertor.copyList(mdMatDao.query(hql,"1"), MatBean.class);
	}
	@Override
	public void addMat(MatBean matBean) throws Exception {
		MdMat o = new MdMat();
		BeanConvertor.copyProperties(matBean, o);
		if(StringUtil.notNull(matBean.getUnit())){
			o.setMdUnit(new MdUnit(matBean.getUnit()));
		}
		if(StringUtil.notNull(matBean.getMatType())){
			o.setMdMatType(new MdMatType(matBean.getMatType()));
		}
		o.setLastUpdateTime(new Date());
		o.setDel("0");
		mdMatDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.MATPROD);
	}
	@Override
	public void editMat(MatBean matBean) throws Exception {
		MdMat o = mdMatDao.findById(MdMat.class, matBean.getId());

		BeanConvertor.copyProperties(matBean, o);
		o.setLastUpdateTime(new Date());
		
		if(StringUtil.notNull(matBean.getUnit())){
			o.setMdUnit(new MdUnit(matBean.getUnit()));
		}
		
		if(StringUtil.notNull(matBean.getMatType())){
			o.setMdMatType(new MdMatType(matBean.getMatType()));
		}
		loadComboboxService.initCombobox(ComboboxType.MATPROD);
	}
	@Override
	public void deleteMat(String id) throws Exception {
		mdMatDao.updateByParams("update MdMat o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.MATPROD);
	}
	@Override
	public DataGrid getAllMats(MatBean matBean,PageParams pageParams) throws Exception {
		
		String hql = "from MdMat o left join fetch o.mdMatType t left join fetch o.mdUnit u where o.del='0'";
		
		if(StringUtil.notNull(matBean.getMatType())){
			hql = hql.concat(" and o.mdMatType.id = "+matBean.getMatType());
		}

		if(matBean.getEnable() != null){
			hql = hql.concat(" and o.enable = "+matBean.getEnable());
		}
		
		if(StringUtil.notNull(matBean.getCode())){
			hql = hql.concat(" and o.code like '%"+matBean.getCode()+"%'");
		}
		
		if(StringUtil.notNull(matBean.getName())){
			hql = hql.concat(" and o.name like '%"+matBean.getName()+"%'");
		}
		List<MdMat> mdMats = mdMatDao.queryByPage(hql+" order by o.lastUpdateTime desc ", pageParams);
		
		List<MatBean> list = new ArrayList<MatBean>();
		
		for (MdMat mdMat : mdMats) {
			MatBean bean = BeanConvertor.copyProperties(mdMat, MatBean.class);
			if(mdMat.getMdUnit()!=null){
				bean.setUnit(mdMat.getMdUnit().getName());
				bean.setUnitId(mdMat.getMdUnit().getId());
			}
			if(mdMat.getMdMatType()!=null){
				bean.setMatType(mdMat.getMdMatType().getName());
			}
			
			list.add(bean);
		}
		
		hql= "select count(o) ".concat(hql.replace("fetch", ""));
		
		Long count = mdMatDao.queryTotal(hql);
		
		return new DataGrid(list, count);
	}
	@Override
	public MatBean getMatById(String id) throws Exception{
		
		MdMat mdMat = mdMatDao.findById(MdMat.class, id);
		
		MatBean bean = BeanConvertor.copyProperties(mdMat,MatBean.class);
		
		if(mdMat.getMdUnit()!=null){
			bean.setUnit(mdMat.getMdUnit().getId());
		}
		
		if(mdMat.getMdMatType()!=null){
			bean.setMatType(mdMat.getMdMatType().getId());
		}
		
		return bean;
	}
	@Override
	public List<MatBean> queryAllProdMat() throws Exception {
		List<MatBean> list = new ArrayList<MatBean>();
		String hql = "from MdMat o where o.del='0' and o.mdMatType.mdMatGrp.code='1'";
		for (MdMat mdMat : mdMatDao.query(hql)) {
			MatBean matBean = new MatBean(mdMat.getId(), mdMat.getName());
			list.add(matBean);
		}
		return list;
	}
}
