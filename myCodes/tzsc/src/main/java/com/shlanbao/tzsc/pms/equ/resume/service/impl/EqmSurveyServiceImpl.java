package com.shlanbao.tzsc.pms.equ.resume.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmSurveyDaoI;
import com.shlanbao.tzsc.base.mapping.EqmSurvey;
import com.shlanbao.tzsc.base.mapping.EqmResume;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmSurveyBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmSurveyServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmSurveyServiceImpl extends BaseService implements EqmSurveyServiceI {
	@Autowired
	private EqmSurveyDaoI eqmSurveyDao;

	@Override
	public EqmSurveyBean getById(String id) throws Exception {
		EqmSurvey bean = eqmSurveyDao.findById(EqmSurvey.class, id);
		EqmSurveyBean lastBean = BeanConvertor.copyProperties(bean, EqmSurveyBean.class);
		if(null!=bean.getEqmResume()&&!"".equals(bean.getEqmResume())){
			lastBean.setEqmResumeId(bean.getEqmResume().getId());//设备履历ID
			lastBean.setEqmResumeName(bean.getEqmResume().getResumeName());
		}
		if(null!=bean.getSysUserByAddUserid()&&!"".equals(bean.getSysUserByAddUserid())){
			lastBean.setAddUserid(bean.getSysUserByAddUserid().getId());
			lastBean.setAddUserName(bean.getSysUserByAddUserid().getName());
		}
		if(null!=bean.getSysUserByModifyUserid()&&!"".equals(bean.getSysUserByModifyUserid())){
			lastBean.setModifyUserid(bean.getSysUserByModifyUserid().getId());
			lastBean.setModifyUserName(bean.getSysUserByModifyUserid().getName());
		}
		return lastBean;
	}

	@Override
	public void addOrEditBean(String eqmResumeId,EqmSurveyBean[] beans,String userId) throws Exception {
		if(null!=beans){
			for(int i=0;i<beans.length;i++){
				EqmSurveyBean bean = beans[i];
				if(null!=bean.getSurveyId()&&!"".equals(bean.getSurveyId())){//更新
					EqmSurvey updateBean = eqmSurveyDao.findById(EqmSurvey.class, bean.getSurveyId());
					SysUser modify = new SysUser();
					modify.setId(userId);
					updateBean.setSysUserByModifyUserid(modify);
					updateBean.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					updateBean.setSurveyDate(StringUtil.trim(bean.getSurveyDate()));
					updateBean.setSurveyCode(StringUtil.trim(bean.getSurveyCode()));
					updateBean.setSurveyWay(StringUtil.trim(bean.getSurveyWay()));
					updateBean.setSurveyCompany(StringUtil.trim(bean.getSurveyCompany()));
					updateBean.setCostPrice(StringUtil.trim(bean.getCostPrice()));
					updateBean.setDeprMoney(StringUtil.trim(bean.getDeprMoney()));
					updateBean.setAllotMoney(StringUtil.trim(bean.getAllotMoney()));
					updateBean.setAllotRevenue(StringUtil.trim(bean.getAllotRevenue()));
					updateBean.setRemarks(StringUtil.trim(bean.getRemarks()));
				}else{//保存
					EqmSurvey eqmBean = BeanConvertor.copyProperties(bean,EqmSurvey.class);
					eqmBean.setSurveyId(UUID.randomUUID().toString());//主键ID
					EqmResume eqmResume = new EqmResume();
					eqmResume.setId(eqmResumeId);
					eqmBean.setEqmResume(eqmResume);//设备履历ID
					SysUser modify = new SysUser();
					modify.setId(userId);
					eqmBean.setSysUserByModifyUserid(modify);
					eqmBean.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					SysUser insert = new SysUser();
					insert.setId(userId);
					eqmBean.setSysUserByAddUserid(insert);
					eqmBean.setAddTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					eqmBean.setDel("0");
					eqmSurveyDao.save(eqmBean);
				}
			}
		}
	}
	@Override
	public DataGrid queryList(EqmSurveyBean bean,PageParams pageParams) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from EqmSurvey o ");//内部转移
		hql.append("left join fetch o.eqmResume resume ");//设备履历
		hql.append("left join fetch o.sysUserByAddUserid addUser ");//用户信息
		hql.append("left join fetch o.sysUserByModifyUserid modifyUser ");//用户信息
		hql.append(" where o.del=0 ");//单位信息
		
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getEqmResumeId())){
			hql.append(" and o.eqmResume.id=? ");
			params.add(bean.getEqmResumeId());
		}
		//long total = 0;
		List<EqmSurveyBean> lastList= new ArrayList<EqmSurveyBean>();
		try {
			/*total=eqmInsideDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			if(total>0){*/
				hql.append("order by o.modifyTime desc ");
				List<EqmSurvey> rows = eqmSurveyDao.query(hql.toString(), params);
				for(EqmSurvey eqmBean:rows){
					EqmSurveyBean oneBean = BeanConvertor.copyProperties(eqmBean, EqmSurveyBean.class);
					if(null!=eqmBean.getEqmResume()){
						oneBean.setEqmResumeId(eqmBean.getEqmResume().getId());
						oneBean.setEqmResumeName(eqmBean.getEqmResume().getResumeName());
					}
					if(null!=eqmBean.getSysUserByAddUserid()){
						oneBean.setAddUserid(eqmBean.getSysUserByAddUserid().getId());
						oneBean.setAddUserName(eqmBean.getSysUserByAddUserid().getName());
					}
					if(null!=eqmBean.getSysUserByModifyUserid()){
						oneBean.setModifyUserid(eqmBean.getSysUserByModifyUserid().getId());
						oneBean.setModifyUserName(eqmBean.getSysUserByModifyUserid().getName());
					}
					lastList.add(oneBean);
				}
			//}
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		long total = Long.parseLong(String.valueOf(lastList.size()));
		return new DataGrid(lastList,total);
	}

	@Override
	public void deleteById(String id) throws Exception {
		String[] args = id.split(",");
		if(null!=args&&args.length>0){
			for(int i=0;i<args.length;i++){
				EqmSurvey updateBean = eqmSurveyDao.findById(EqmSurvey.class,args[i]);
				if(null!=updateBean&&!"".equals(updateBean)){
					updateBean.setDel("1");
				}
			}
		}
	}
}
