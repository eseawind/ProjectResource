package com.shlanbao.tzsc.pms.sch.constd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchConStdDaoI;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.mapping.SchConStd;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;
import com.shlanbao.tzsc.pms.sch.constd.service.ConStdServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
@Service
public class ConStdServiceImpl extends BaseService implements ConStdServiceI{
	@Autowired
	private SchConStdDaoI schConStdDao;
	@Override
	public void addConStd(ConStdBean conStdBean) throws Exception {
		SchConStd o = new SchConStd();
		BeanConvertor.copyProperties(conStdBean, o);
		o.setMdMatByMat(new MdMat(conStdBean.getMat()));
		o.setMdMatByProd(new MdMat(conStdBean.getMatProd()));
		o.setDel(0L);
		schConStdDao.save(o);
	}
	@Override
	public void editConStd(ConStdBean conStdBean) throws Exception {
		SchConStd o = schConStdDao.findById(SchConStd.class, conStdBean.getId());
		BeanConvertor.copyProperties(conStdBean, o);
		o.setMdMatByMat(new MdMat(conStdBean.getMat()));
		o.setMdMatByProd(new MdMat(conStdBean.getMatProd()));
	}
	@Override
	public void deleteConStd(String id) throws Exception {
		schConStdDao.updateByParams("update SchConStd o set o.del='1' where o.id=?", id);
	}
	
	@Override
	public DataGrid getConStds(ConStdBean conStdBean,PageParams pageParams) throws Exception {
		String hql = "from SchConStd o where o.del='0' ";
		String params="";
		if(StringUtil.notNull(conStdBean.getMatProd())){
			params = params.concat(" and o.mdMatByProd.id='"+conStdBean.getMatProd()+"'");
		}
		if(StringUtil.notNull(conStdBean.getMat())){
			params = params.concat(" and o.mat.id='"+conStdBean.getMat()+"'");
		}
		//and o.name like '%"+ConStd.getName()+"%' order by o.seq asc";
		List<SchConStd> SchConStds=schConStdDao.queryByPage(hql.concat(params), pageParams);
		
		List<ConStdBean> list = new ArrayList<ConStdBean>();//schConStdDao		
		ConStdBean bean=null;
		try{
		for(SchConStd schconstd:SchConStds){
			bean = BeanConvertor.copyProperties(schconstd, ConStdBean.class);
			try{
				bean.setId(schconstd.getId());
				bean.setMatProd(schconstd.getMdMatByProd().getName());
				bean.setMatName(schconstd.getMdMatByMat().getName());
				bean.setMat(schconstd.getMdMatByMat().getId());
				bean.setVal(schconstd.getVal());
				bean.setUval(schconstd.getUval());
				bean.setLval(schconstd.getLval());
				bean.setEuval(schconstd.getEuval());
				bean.setElval(schconstd.getElval());
				bean.setDes(schconstd.getDes());
			}catch(Exception e){
				e.printStackTrace();
			}
			list.add(bean);
			bean=null;
		}
		/*return new DataGrid(list, 0L);*/
		hql = "select count(o) ".concat(hql.replace("fetch", ""));
		long total=schConStdDao.queryTotal(hql.concat(params));
		return new DataGrid(list,total);
		}catch(Exception e){
			log.error("POVO转换异常", e);
		}
		return null;
	}
	
	
	@Override
	public ConStdBean getConStdById(String id) throws Exception{
		
		SchConStd schConStd = schConStdDao.findById(SchConStd.class, id);
		
		ConStdBean bean = BeanConvertor.copyProperties(schConStd, ConStdBean.class);
		
		if(schConStd.getMdMatByMat()!=null && schConStd.getMdMatByProd()!=null){
			
			bean.setMat(schConStd.getMdMatByMat().getId());//辅料
			bean.setMatName(schConStd.getMdMatByMat().getName());//辅料名称
			
			bean.setMatProd(schConStd.getMdMatByProd().getId());//产品
			
		}
		
		return bean;
		
	}
	
	@Override
	public DataGrid getAllConStds(ConStdBean conStdBean) throws Exception {
		String hql = "from SchConStd o where o.del='0' ";
		if(StringUtil.notNull(conStdBean.getMatProd())){
			hql = hql.concat(" and o.mdMatByProd.id='"+conStdBean.getMatProd()+"'");
		}
		if(StringUtil.notNull(conStdBean.getMat())){
			hql = hql.concat(" and o.mat.id='"+conStdBean.getMat()+"'");
		}
		//and o.name like '%"+ConStd.getName()+"%' order by o.seq asc";
		List<ConStdBean> list = new ArrayList<ConStdBean>();
		
		for (SchConStd schConStd :schConStdDao.query(hql)) {
			
			ConStdBean bean = BeanConvertor.copyProperties(schConStd, ConStdBean.class);
			
			if(schConStd.getMdMatByMat()!=null){
				try{
					bean.setMat(schConStd.getMdMatByMat().getId());//辅料
					bean.setMatName(schConStd.getMdMatByMat().getName());
					bean.setMatUnitId(schConStd.getMdMatByMat().getMdUnit().getId());//辅料单位
					bean.setMatUnitName(schConStd.getMdMatByMat().getMdUnit().getName());
					bean.setMatCount("0");//辅料量
				}catch(Exception e){
					log.error("ConStdServiceImpl->getAllConStds is error :"+e.getMessage());
				}
			}
			if(schConStd.getMdMatByProd()!=null){	
				bean.setMatProd(schConStd.getMdMatByProd().getName());//产品
				bean.setMatProdCode(schConStd.getMdMatByProd().getCode());//产品
			}
			
			list.add(bean);
		}
		return new DataGrid(list, 0L);
	}
	
}
