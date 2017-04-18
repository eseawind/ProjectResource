package com.shlanbao.tzsc.pms.equ.resume.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmScrapcelDaoI;
import com.shlanbao.tzsc.base.mapping.EqmScrapcel;
import com.shlanbao.tzsc.base.mapping.EqmResume;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmScrapcelBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmScrapcelServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class EqmScrapcelServiceImpl extends BaseService implements EqmScrapcelServiceI {
	@Autowired
	private EqmScrapcelDaoI eqmScrapcelDao;

	@Override
	public EqmScrapcelBean getById(String id) throws Exception {
		EqmScrapcel bean = eqmScrapcelDao.findById(EqmScrapcel.class, id);
		EqmScrapcelBean lastBean = BeanConvertor.copyProperties(bean, EqmScrapcelBean.class);
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
	public void addOrEditBean(String eqmResumeId,EqmScrapcelBean[] beans,String userId) throws Exception {
		if(null!=beans){
			for(int i=0;i<beans.length;i++){
				EqmScrapcelBean bean = beans[i];
				if(null!=bean.getClearId()&&!"".equals(bean.getClearId())){//更新
					EqmScrapcel updateBean = eqmScrapcelDao.findById(EqmScrapcel.class, bean.getClearId());
					SysUser modify = new SysUser();
					modify.setId(userId);
					updateBean.setSysUserByModifyUserid(modify);
					updateBean.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
					updateBean.setClearDate(StringUtil.trim(bean.getClearDate()));//报废清理日期	
					updateBean.setSurveyCode(StringUtil.trim(bean.getSurveyCode()));//凭证编号
					updateBean.setClearReason(StringUtil.trim(bean.getClearReason()));//报废清理原因
					updateBean.setCostPrice(StringUtil.trim(bean.getCostPrice()));//原价
					updateBean.setDeprMoney(StringUtil.trim(bean.getDeprMoney()));//已提折旧额
					updateBean.setChangeRevenue(StringUtil.trim(bean.getChangeRevenue()));//变价收入
					updateBean.setClearCost(StringUtil.trim(bean.getClearCost()));//清理费用
					updateBean.setCardDate(StringUtil.trim(bean.getCardDate()));//设卡日期
					updateBean.setLogoutDate(StringUtil.trim(bean.getLogoutDate()));//注销日期
				}else{//保存
					EqmScrapcel eqmBean = BeanConvertor.copyProperties(bean,EqmScrapcel.class);
					eqmBean.setClearId(UUID.randomUUID().toString());//主键ID
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
					eqmBean.setCardUser(userId);//卡片登记人
					eqmBean.setDel("0");
					eqmScrapcelDao.save(eqmBean);
				}
			}
		}
	}
	@Override
	public DataGrid queryList(EqmScrapcelBean bean,PageParams pageParams) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from EqmScrapcel o ");//内部转移
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
		List<EqmScrapcelBean> lastList= new ArrayList<EqmScrapcelBean>();
		try {
			/*total=eqmInsideDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			if(total>0){*/
				hql.append("order by o.modifyTime desc ");
				List<EqmScrapcel> rows = eqmScrapcelDao.query(hql.toString(), params);
				for(EqmScrapcel eqmBean:rows){
					EqmScrapcelBean oneBean = BeanConvertor.copyProperties(eqmBean, EqmScrapcelBean.class);
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
				EqmScrapcel updateBean = eqmScrapcelDao.findById(EqmScrapcel.class,args[i]);
				if(null!=updateBean&&!"".equals(updateBean)){
					updateBean.setDel("1");
				}
			}
		}
	}
}
