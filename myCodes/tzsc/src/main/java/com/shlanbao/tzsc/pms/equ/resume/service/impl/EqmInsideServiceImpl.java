package com.shlanbao.tzsc.pms.equ.resume.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmInsideDaoI;
import com.shlanbao.tzsc.base.mapping.EqmInside;
import com.shlanbao.tzsc.base.mapping.EqmResume;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmInsideBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmInsideServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmInsideServiceImpl extends BaseService implements EqmInsideServiceI {
	@Autowired
	private EqmInsideDaoI eqmInsideDao;

	@Override
	public EqmInsideBean getById(String id) throws Exception {
		EqmInside bean = eqmInsideDao.findById(EqmInside.class, id);
		EqmInsideBean lastBean = BeanConvertor.copyProperties(bean, EqmInsideBean.class);
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
	public void addOrEditBean(String eqmResumeId,EqmInsideBean[] beans,String userId) throws Exception {
		if(null!=beans){
			for(int i=0;i<beans.length;i++){
				EqmInsideBean bean = beans[i];
				if(null!=bean.getInsideId()&&!"".equals(bean.getInsideId())){//更新
					EqmInside updateBean = eqmInsideDao.findById(EqmInside.class, bean.getInsideId());
					SysUser modifyUserBean = new SysUser();
					modifyUserBean.setId(userId);
					updateBean.setSysUserByModifyUserid(modifyUserBean);
					updateBean.setInsideDate(StringUtil.trim(bean.getInsideDate()));
					updateBean.setVoucherType(StringUtil.trim(bean.getVoucherType()));
					updateBean.setVoucherNumber(StringUtil.trim(bean.getVoucherNumber()));
					updateBean.setReceiveDept(StringUtil.trim(bean.getReceiveDept()));
					updateBean.setDepositPlace(StringUtil.trim(bean.getDepositPlace()));
					updateBean.setRemarks(StringUtil.trim(bean.getRemarks()));
					updateBean.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				}else{//保存
					EqmInside eqmBean = BeanConvertor.copyProperties(bean, EqmInside.class);
					eqmBean.setInsideId(UUID.randomUUID().toString());//内部转移主键ID
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
					eqmBean.setResumeId(eqmResumeId);
					eqmInsideDao.save(eqmBean);
				}
			}
		}
	}
	@Override
	public DataGrid queryList(EqmInsideBean bean,PageParams pageParams) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from EqmInside o ");//内部转移
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
		List<EqmInsideBean> lastList= new ArrayList<EqmInsideBean>();
		try {
			/*total=eqmInsideDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			if(total>0){*/
				hql.append("order by o.modifyTime desc ");
				// pageParams.getPage(), pageParams.getRows()
				List<EqmInside> rows = eqmInsideDao.query(hql.toString(), params);
				for(EqmInside eqmBean:rows){
					EqmInsideBean oneBean = BeanConvertor.copyProperties(eqmBean, EqmInsideBean.class);
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
		long size = Long.parseLong(String.valueOf(lastList.size()));
		return new DataGrid(lastList,size);
	}

	@Override
	public void deleteById(String id) throws Exception {
		String[] args = id.split(",");
		if(null!=args&&args.length>0){
			for(int i=0;i<args.length;i++){
				EqmInside updateBean = eqmInsideDao.findById(EqmInside.class,args[i]);
				if(null!=updateBean&&!"".equals(updateBean)){
					updateBean.setDel("1");
				}
			}
		}
		
	}
}
