package com.shlanbao.tzsc.pms.equ.resume.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmStopDaoI;
import com.shlanbao.tzsc.base.mapping.EqmStop;
import com.shlanbao.tzsc.base.mapping.EqmResume;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmStopBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmStopServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmStopServiceImpl extends BaseService implements EqmStopServiceI {
	@Autowired
	private EqmStopDaoI eqmStopDao;

	@Override
	public EqmStopBean getById(String id) throws Exception {
		EqmStop bean = eqmStopDao.findById(EqmStop.class, id);
		EqmStopBean lastBean = BeanConvertor.copyProperties(bean, EqmStopBean.class);
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
	public void addOrEditBean(String eqmResumeId,EqmStopBean[] beans,String userId) throws Exception {
		if(null!=beans){
			for(int i=0;i<beans.length;i++){
				EqmStopBean bean = beans[i];
				if(null!=bean.getStopId()&&!"".equals(bean.getStopId())){//更新
					EqmStop updateBean = eqmStopDao.findById(EqmStop.class, bean.getStopId());
					SysUser modify = new SysUser();
					modify.setId(userId);
					updateBean.setSysUserByModifyUserid(modify);
					updateBean.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					updateBean.setStopTime(StringUtil.trim(bean.getStopTime()));
					updateBean.setStopType(StringUtil.trim(bean.getStopType()));
					updateBean.setStopNumber(StringUtil.trim(bean.getStopNumber()));
					updateBean.setStopReason(StringUtil.trim(bean.getStopReason()));
					updateBean.setEnableTime(StringUtil.trim(bean.getEnableTime()));
					updateBean.setEnableType(StringUtil.trim(bean.getEnableType()));
					updateBean.setEnableNumber(StringUtil.trim(bean.getEnableNumber()));
					updateBean.setEnableReason(StringUtil.trim(bean.getEnableReason()));
				}else{//保存
					EqmStop eqmBean = BeanConvertor.copyProperties(bean,EqmStop.class);
					eqmBean.setStopId(UUID.randomUUID().toString());//主键ID
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
					eqmStopDao.save(eqmBean);
				}
			}
		}
	}
	@Override
	public DataGrid queryList(EqmStopBean bean,PageParams pageParams) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from EqmStop o ");//内部转移
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
		List<EqmStopBean> lastList= new ArrayList<EqmStopBean>();
		try {
			/*total=eqmInsideDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			if(total>0){*/
				hql.append("order by o.modifyTime desc ");
				List<EqmStop> rows = eqmStopDao.query(hql.toString(), params);
				for(EqmStop eqmBean:rows){
					EqmStopBean oneBean = BeanConvertor.copyProperties(eqmBean, EqmStopBean.class);
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
				EqmStop updateBean = eqmStopDao.findById(EqmStop.class,args[i]);
				if(null!=updateBean&&!"".equals(updateBean)){
					updateBean.setDel("1");
				}
			}
		}
	}
}

