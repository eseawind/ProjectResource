package com.shlanbao.tzsc.pms.cos.disabled.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosPartWeightDaoI;
import com.shlanbao.tzsc.base.mapping.CosPartWeight;
import com.shlanbao.tzsc.base.mapping.MdMat;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.disabled.bean.CosPartWeightBean;
import com.shlanbao.tzsc.pms.cos.disabled.service.CosPartWeightServiceI;
import com.shlanbao.tzsc.pms.md.mat.beans.MatBean;
import com.shlanbao.tzsc.pms.md.mat.service.MatServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;


/**
* @ClassName: CosIncompleteStandardServiceI 
* @Description: 烟支重量维护 实现类
* @author luoliang
* @date 2015年1月4日08:41:23
*
 */
@Service
public class CosPartWeightServiceImpl implements CosPartWeightServiceI{
	
	@Autowired
	private CosPartWeightDaoI cosPartWeight;
	
	@Autowired
	private MatServiceI matService;
	
	/**
	 * 查询Bean
	 */
	@Override
	public DataGrid queryCosPartWeight(CosPartWeightBean bean, PageParams pageParams) throws Exception {
		String hql = "from CosPartWeight o where 1=1 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getId())) params.append(" and o.id='" + bean.getId() + "'");
		if(StringUtil.notNull(bean.getPartNumber())) params.append(" and o.mdMat.id='" + bean.getPartNumber() + "'");
		List<CosPartWeight> cpw = cosPartWeight.queryByPage(hql.concat(params.toString()),pageParams);
		List<CosPartWeightBean> rows = new ArrayList<CosPartWeightBean>();
		for(CosPartWeight c:cpw){
			CosPartWeightBean weightBean = new CosPartWeightBean();
			weightBean.setPartName(c.getMdMat().getName());
			weightBean.setPartNumber(c.getMdMat().getId());
			weightBean.setId(c.getId());
			weightBean.setWeight(c.getWeight());
			rows.add(weightBean);
		}
		hql = "select count(*) from CosPartWeight o where 1=1  ";
		long total = cosPartWeight.queryTotal(hql.concat(params.toString()));
		
		return new DataGrid(rows,total);
	}
	
	/**
	 * 添加或修改Bean
	 */
	@Override
	public boolean addOrUpdateBean(CosPartWeight bean) throws Exception {
		if(bean.getWeight()<=0.0){
			throw new Exception("烟支单支重量不能为0!");
		}
		//add or update 
		if(!StringUtil.notNull(bean.getId())){
			bean.setId(null);
		}
		if(StringUtil.notNull(bean.getPartNumber())){
			MatBean mt=matService.getMatById(bean.getPartNumber());
			if(mt!=null){
				MdMat me=BeanConvertor.copyProperties(mt, MdMat.class);
				bean.setMdMat(me);
			}
		}
		
		if(bean.getMdMat()==null){
			throw new Exception("烟支牌号不能为空!");
		}
		
		return cosPartWeight.saveOrUpdate(bean);
	}
	
	/**
	 * 根据ID读取Bean
	 */
	public CosPartWeight getBeanById(String id) throws Exception{
		CosPartWeight c=cosPartWeight.findById(CosPartWeight.class, id);
		return c;
	}
	/**
	 * 根据ID读取Bean
	 */
	@Override
	public CosPartWeight getBeanByPartNumber(String id,String partNumber) throws Exception{
		StringBuffer hql =new StringBuffer();
		hql.append("from CosPartWeight o where 1=1 ");  
		if(StringUtil.notNull(id)) hql.append(" and o.id='" + id + "'");
		if(StringUtil.notNull(partNumber)) hql.append(" and o.mdMat.id='" + partNumber + "'");
		List<CosPartWeight> cpw = cosPartWeight.query(hql.toString());
		if(cpw.size()>0){
			return cpw.get(0);
		}
		return null;
	}
	
	/**
	 * 根据ID读取Bean
	 */
	@Override
	public List<CosPartWeight> getBeanByPartNumber(String partNumber) throws Exception{
		StringBuffer hql =new StringBuffer();
		hql.append("from CosPartWeight o where 1=1 ");  
		//if(StringUtil.notNull(id)) hql.append(" and o.id='" + id + "'");
		if(StringUtil.notNull(partNumber)) hql.append(" and o.mdMat.id='" + partNumber + "'");
		return cosPartWeight.query(hql.toString());
	}
}
