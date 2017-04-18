package com.shlanbao.tzsc.pms.md.mattype.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdMatGrpDaoI;
import com.shlanbao.tzsc.base.dao.MdMatTypeDaoI;
import com.shlanbao.tzsc.base.mapping.MdMatGrp;
import com.shlanbao.tzsc.base.mapping.MdMatType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.mattype.beans.MatTypeBean;
import com.shlanbao.tzsc.pms.md.mattype.service.MatTypeServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class MatTypeServiceImpl extends BaseService implements MatTypeServiceI {
	@Autowired
	private MdMatTypeDaoI mdMatTypeDao;
	@Autowired
	private LoadComboboxServiceI loadComboboxService;
	@Autowired
	private MdMatGrpDaoI mdMatGroupDao;
	@Override
	public List<MatTypeBean> queryAllMatTypesForComboBox() throws Exception {		
			return BeanConvertor.copyList(mdMatTypeDao.query("from MdMatType o where o.del='0' and o.enable=1 order by o.code asc"), MatTypeBean.class);
	}
	@Override
	public void addMatType(MatTypeBean matTypeBean) throws Exception {
		MdMatType o = new MdMatType();
		BeanConvertor.copyProperties(matTypeBean, o);
		if(StringUtil.notNull(matTypeBean.getMatGrp())){			
			o.setMdMatGrp(new MdMatGrp(matTypeBean.getMatGrp()));
		}
		o.setDel("0");
		mdMatTypeDao.save(o);
		loadComboboxService.initCombobox(ComboboxType.MATTYPE);
	}
	@Override
	public void editMatType(MatTypeBean matTypeBean) throws Exception {
		MdMatType o = mdMatTypeDao.findById(MdMatType.class, matTypeBean.getId());
		BeanConvertor.copyProperties(matTypeBean, o);
		MdMatGrp matGroup=mdMatGroupDao.findById(MdMatGrp.class, matTypeBean.getMatGrp());
		if(matGroup==null){
			matGroup=mdMatGroupDao.unique(MdMatGrp.class, "from MdMatGrp where name=?", matTypeBean.getMatGrp());
		}
		o.setMdMatGrp(matGroup);
		loadComboboxService.initCombobox(ComboboxType.MATTYPE);
	}
	@Override
	public void deleteMatType(String id) throws Exception {
		mdMatTypeDao.updateByParams("update MdMatType o set o.del='1' where o.id=?", id);
		loadComboboxService.initCombobox(ComboboxType.MATTYPE);
	}
	@Override
	public DataGrid getAllMatTypes(MatTypeBean matTypeBean) throws Exception {
		String hql = "from MdMatType o left join fetch o.mdMatGrp g where o.del='0' and o.name like '%"+matTypeBean.getName()+"%' order by o.code asc";
		List<MatTypeBean> list = new ArrayList<MatTypeBean>();
		for (MdMatType mdMatType : mdMatTypeDao.query(hql)) {
			
			MatTypeBean typeBean = BeanConvertor.copyProperties(mdMatType, MatTypeBean.class);
			if(mdMatType.getMdMatGrp()!=null){
				typeBean.setMatGrp(mdMatType.getMdMatGrp().getName());
			}
			list.add(typeBean);
		}
		return new DataGrid(list, 0L);
	}
	@Override
	public MatTypeBean getMatTypeById(String id) throws Exception{
		MdMatType mdMatType = mdMatTypeDao.findById(MdMatType.class, id);
		MatTypeBean matTypeBean = BeanConvertor.copyProperties(mdMatType,MatTypeBean.class);
		if(mdMatType.getMdMatGrp()!=null){
			matTypeBean.setMatGrp(mdMatType.getMdMatGrp().getName());
		}
		return matTypeBean;
		
	}
	@Override
	public DataGrid getAllTypesByGrp(String gid) throws Exception {
		String hql = "from MdMatType o left join fetch o.mdMatGrp g where o.del='0' and o.mdMatGrp.id = '"+gid+"' order by o.code asc";
		List<MatTypeBean> list = new ArrayList<MatTypeBean>();
		for (MdMatType mdMatType : mdMatTypeDao.query(hql)) {
			
			MatTypeBean typeBean = BeanConvertor.copyProperties(mdMatType, MatTypeBean.class);
			if(mdMatType.getMdMatGrp()!=null){
				typeBean.setMatGrp(mdMatType.getMdMatGrp().getName());
			}
			list.add(typeBean);
		}
		return new DataGrid(list, 0L);
	
	}
}
