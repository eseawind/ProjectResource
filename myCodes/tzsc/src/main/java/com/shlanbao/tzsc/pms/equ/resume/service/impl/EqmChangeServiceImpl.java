package com.shlanbao.tzsc.pms.equ.resume.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmChangeDaoI;
import com.shlanbao.tzsc.base.mapping.EqmChange;
import com.shlanbao.tzsc.base.mapping.EqmResume;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmChangeBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmChangeServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmChangeServiceImpl extends BaseService implements EqmChangeServiceI {
	@Autowired
	private EqmChangeDaoI eqmChangeDao;

	@Override
	public EqmChangeBean getById(String id) throws Exception {
		EqmChange bean = eqmChangeDao.findById(EqmChange.class, id);
		EqmChangeBean lastBean = BeanConvertor.copyProperties(bean, EqmChangeBean.class);
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
	public void addOrEditBean(String eqmResumeId,EqmChangeBean[] beans,String userId) throws Exception {
		if(null!=beans){
			for(int i=0;i<beans.length;i++){
				EqmChangeBean bean = beans[i];
				if(null!=bean.getChangeId()&&!"".equals(bean.getChangeId())){//更新
					EqmChange updateBean = eqmChangeDao.findById(EqmChange.class, bean.getChangeId());
					SysUser modify = new SysUser();
					modify.setId(userId);
					updateBean.setSysUserByModifyUserid(modify);
					updateBean.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					updateBean.setChangeDate(StringUtil.trim(bean.getChangeDate()));//年月日	
					updateBean.setVoucherType(StringUtil.trim(bean.getVoucherType()));//凭证类
					updateBean.setVoucherNumber(StringUtil.trim(bean.getVoucherNumber()));//凭证号
					updateBean.setRaiseMoney(StringUtil.trim(bean.getRaiseMoney()));//增加
					updateBean.setReduceMoney(StringUtil.trim(bean.getReduceMoney()));//减少
					updateBean.setChangeCostprice(StringUtil.trim(bean.getChangeCostprice()));//变动后原价
					updateBean.setRemarks(StringUtil.trim(bean.getRemarks()));//备注
				}else{//保存
					EqmChange eqmBean = BeanConvertor.copyProperties(bean,EqmChange.class);
					eqmBean.setChangeId(UUID.randomUUID().toString());//原价变动主键ID
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
					eqmChangeDao.save(eqmBean);
				}
			}
		}
	}
	@Override
	public DataGrid queryList(EqmChangeBean bean,PageParams pageParams) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from EqmChange o ");//内部转移
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
		List<EqmChangeBean> lastList= new ArrayList<EqmChangeBean>();
		try {
			/*total=eqmInsideDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			if(total>0){*/
				hql.append("order by o.modifyTime desc ");
				List<EqmChange> rows = eqmChangeDao.query(hql.toString(), params);
				for(EqmChange eqmBean:rows){
					EqmChangeBean oneBean = BeanConvertor.copyProperties(eqmBean, EqmChangeBean.class);
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
				EqmChange updateBean = eqmChangeDao.findById(EqmChange.class,args[i]);
				if(null!=updateBean&&!"".equals(updateBean)){
					updateBean.setDel("1");
				}
			}
		}
	}
}
