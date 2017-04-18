package com.shlanbao.tzsc.pms.md.matparam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MdMatParamDaoI;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.MdMatParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;
import com.shlanbao.tzsc.pms.md.matparam.service.MatParamServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class MatParamServiceImpl extends BaseService implements MatParamServiceI{
	@Autowired
	private MdMatParamDaoI mdMatParamDao;
	@Override
	public void addMatParam(MatParamBean MatParamBean) throws Exception {
		
		MdMatParam o = new MdMatParam();
		
		BeanConvertor.copyProperties(MatParamBean, o);
		
		o.setMdMat(new MdMat(MatParamBean.getMat()));
		
		mdMatParamDao.save(o);
		
	}
	@Override
	public void editMatParam(MatParamBean MatParamBean) throws Exception {
		
		MdMatParam o = mdMatParamDao.findById(MdMatParam.class, MatParamBean.getId());
		
		BeanConvertor.copyProperties(MatParamBean, o);
		
		o.setMdMat(new MdMat(MatParamBean.getMat()));
	}
	@Override
	public void deleteMatParam(String id) throws Exception {
		
		mdMatParamDao.deleteById(id, MdMatParam.class);
	
	}
	@Override
	public DataGrid getAllMatParams(MatParamBean matParamBean) throws Exception {
		
		String hql = "from MdMatParam o where 1=1 ";
		
		if(StringUtil.notNull(matParamBean.getMatType())){
			
			hql = hql.concat(" and o.mdMat.mdMatType.id='"+matParamBean.getMatType()+"'");
		
		}
		//and o.name like '%"+MatParam.getName()+"%' order by o.seq asc";
		List<MatParamBean> list = new ArrayList<MatParamBean>();
		List<MdMatParam> ts=mdMatParamDao.query(hql);
		for (MdMatParam mdMatParam :ts) {
			MatParamBean bean = new MatParamBean();
			bean.setId(mdMatParam.getId());
			bean.setLength(mdMatParam.getLength());
			bean.setWidth(mdMatParam.getWidth());
			bean.setDensity(mdMatParam.getDensity());
			bean.setVal(mdMatParam.getVal());
			bean.setDes(mdMatParam.getDes());
			if(mdMatParam.getMdMat()!=null){
				bean.setMatName(mdMatParam.getMdMat().getId()); // 辅料ID
				bean.setMat(mdMatParam.getMdMat().getName());//辅料
				bean.setMatType(mdMatParam.getMdMat().getMdMatType().getId());
			}
			/*MatParamBean bean = BeanConvertor.copyProperties(mdMatParam, MatParamBean.class);
			if(mdMatParam.getMdMat()!=null){
				bean.setMatName(mdMatParam.getMdMat().getId());
				bean.setMat(mdMatParam.getMdMat().getName());//辅料
			}*/
			
			list.add(bean);
			
		}
		
		return new DataGrid(list, 0L);
	}
	@Override
	public MatParamBean getMatParamById(String id) throws Exception{
		
		MdMatParam MdMatParam = mdMatParamDao.findById(MdMatParam.class, id);
		
		MatParamBean bean = BeanConvertor.copyProperties(MdMatParam, MatParamBean.class);
		
		if(MdMatParam.getMdMat()!= null){
			
			bean.setMat(MdMatParam.getMdMat().getId());//辅料
			bean.setMatName(MdMatParam.getMdMat().getName());//辅料
			
		}
		
		return bean;
		
	}
}
