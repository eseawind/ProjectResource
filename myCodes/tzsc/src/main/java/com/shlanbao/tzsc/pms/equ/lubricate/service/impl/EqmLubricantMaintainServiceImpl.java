package com.shlanbao.tzsc.pms.equ.lubricate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmLubricantMaintainDaoI;
import com.shlanbao.tzsc.base.dao.SysEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.EqmLubricantMaintain;
import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EquLubricantMaintainBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantMaintainServiceI;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;

@Service
public class EqmLubricantMaintainServiceImpl extends BaseService implements EqmLubricantMaintainServiceI {
	@Autowired
	protected EqmLubricantMaintainDaoI lubriCateDao;
	@Autowired
	private SysEqpTypeDaoI sysEqpTypeDao;

	@Override
	public void addLubricate(EqmLubricantMaintain equLubricantMaintain) throws Exception {
		EqmLubricantMaintain equLubricant = new EqmLubricantMaintain();
		//判断是否存在ID 如果存在则更新数据，如果不存在则添加
		if(StringUtil.notNull(equLubricantMaintain.getId())){
			equLubricantMaintain.setDel("0");
			//设置创建用户
			if(equLubricantMaintain.getSysUser()==null){
				SessionInfo sessionInfo = (SessionInfo) WebContextUtil.getObjectFromSession();
				equLubricantMaintain.setSysUser(new SysUser(sessionInfo.getUser().getId()));
			}
			equLubricant=equLubricantMaintain;
		}else{
			if(!StringUtil.notNull(equLubricantMaintain.getDel()))
				equLubricantMaintain.setDel("0");
			//设置创建用户
			if(equLubricantMaintain.getSysUser()==null){
				SessionInfo sessionInfo = (SessionInfo) WebContextUtil.getObjectFromSession();
				equLubricantMaintain.setSysUser(new SysUser(sessionInfo.getUser().getId()));
			}
			//创建时间
			if(equLubricantMaintain.getCreateDate() == null){
				equLubricantMaintain.setCreateDate(new Date());
			}
			equLubricant = equLubricantMaintain;
		}
		lubriCateDao.saveOrUpdate(equLubricant);
	}

	@Override
	public DataGrid queryLubricate(EqmLubricantMaintain equLubricantMaintain, PageParams pageParams)throws Exception {
		String hql="from EqmLubricantMaintain o where 1=1 and o.del=0";//1 deleted data is not visible
		String params="";
		if(StringUtil.notNull(equLubricantMaintain.getLubricantCode())){
			params+=" and (o.lubricantCode like '%"+equLubricantMaintain.getLubricantCode()+"%')";
		}
		if(StringUtil.notNull(equLubricantMaintain.getLubricantName())){
			params+=" and o.lubricantName like '%"+equLubricantMaintain.getLubricantName()+"%'";
		}
		List<EqmLubricantMaintain> rows=lubriCateDao.queryByPage(hql.concat(params), pageParams);
		hql="select count(*) " + hql;
		try {
			List<EquLubricantMaintainBean> equLubricantMaintainBeans = new ArrayList<EquLubricantMaintainBean>();
			for (EqmLubricantMaintain eqmLubricantMaintain : rows) {
				EquLubricantMaintainBean bean = new EquLubricantMaintainBean();
				beanConvertor.copyProperties(eqmLubricantMaintain, bean);
				if(null!=eqmLubricantMaintain.getSysUser()){
					bean.setCreateId(eqmLubricantMaintain.getSysUser().getId());
					bean.setCreateName(eqmLubricantMaintain.getSysUser().getName());
				}
				equLubricantMaintainBeans.add(bean);
			}
			//equLubricantMaintainBeans = BeanConvertor.copyList(rows, EquLubricantMaintainBean.class);
			long total=lubriCateDao.queryTotal(hql.concat(params));
			return new DataGrid(equLubricantMaintainBeans, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}

	@Override
	public EqmLubricantMaintain getLubricateById(String id) throws Exception {
		return lubriCateDao.findById(EqmLubricantMaintain.class, id);
	}

	@Override
	public void deleteLuricateById(String id) throws Exception {
		//lubriCateDao.deleteById(id, EqmLubricantMaintain.class);
		EqmLubricantMaintain eqmLubricantMaintain = lubriCateDao.findById(EqmLubricantMaintain.class, id);
		eqmLubricantMaintain.setDel("1");
	}

	@Override
	public List<EquLubricantMaintainBean> queryAllLubricant(String type) throws Exception {
		String hql="from EqmLubricantMaintain o where 1=1 and o.del=0";
		List<EqmLubricantMaintain> eqmLubricantMaintains = lubriCateDao.query(hql, new Object[]{});
		List<EquLubricantMaintainBean> lastList= beanConvertor.copyList(eqmLubricantMaintains, EquLubricantMaintainBean.class);
		if("all".equals(type)){
			EquLubricantMaintainBean bean = new EquLubricantMaintainBean();
			bean.setId("");
			bean.setLubricantCode("");
			bean.setLubricantName("请选择");
			lastList.add(0, bean);
		}
		return lastList;
	}
	/**
	 * 获取所有的润滑剂
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public List<EquLubricantMaintainBean> queryListById(String key,String type) throws Exception {
		String hql="from SysEqpType o where o.del=0 and o.sysEqpCategory.id=? ";
		List<SysEqpType> eqpList = sysEqpTypeDao.query(hql, new Object[]{key});
		List<EquLubricantMaintainBean> lastBean = new ArrayList<EquLubricantMaintainBean>();
		if(null!=eqpList&&eqpList.size()>0){
			for(int i=0;i<eqpList.size();i++){
				SysEqpType oneBean = eqpList.get(i);
				EquLubricantMaintainBean bean = new EquLubricantMaintainBean();
				bean.setId(oneBean.getId());
				bean.setLubricantCode(oneBean.getCode());
				bean.setLubricantName(oneBean.getName());
				lastBean.add(bean);
			}
		}
		if("all".equals(type)){
			EquLubricantMaintainBean bean = new EquLubricantMaintainBean();
			bean.setId("");
			bean.setLubricantCode("");
			bean.setLubricantName("请选择");
			lastBean.add(0, bean);
		}
		return lastBean;
	}
	
	
}
