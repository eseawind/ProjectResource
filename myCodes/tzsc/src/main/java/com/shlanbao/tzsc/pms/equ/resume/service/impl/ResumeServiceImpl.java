package com.shlanbao.tzsc.pms.equ.resume.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmResumeDaoI;
import com.shlanbao.tzsc.base.mapping.EqmResume;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmResumeBean;
import com.shlanbao.tzsc.pms.equ.resume.service.ResumeServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 设备履历
 */
@Service
public class ResumeServiceImpl extends BaseService implements ResumeServiceI {
	@Autowired
	private EqmResumeDaoI eqmResumeDao;
	
	@Override
	public EqmResumeBean getResumeById(String id) throws Exception {
		EqmResume eqmResume = eqmResumeDao.findById(EqmResume.class, id);
		EqmResumeBean eqmResumeBean = BeanConvertor.copyProperties(eqmResume, EqmResumeBean.class);
		eqmResumeBean.setMdEquId(eqmResume.getMdEquipment().getId());
		eqmResumeBean.setMdEquName(eqmResume.getMdEquipment().getEquipmentName());
		eqmResumeBean.setMdEquType(eqmResume.getMdEquipment().getMdEqpType().getName());
		eqmResumeBean.setAddUserId(eqmResume.getSysUser().getId());
		eqmResumeBean.setAddUserName(eqmResume.getSysUser().getName());
		return eqmResumeBean;
	}

	@Override
	public void addOrEditResume(EqmResumeBean eqmResumeBean,String userId) throws Exception {
		if(null!=eqmResumeBean.getId()&&!"".equals(eqmResumeBean.getId())){//更新
			EqmResume updateBean = eqmResumeDao.findById(EqmResume.class, eqmResumeBean.getId());//获取原有数据
			updateBean.setMdEquipment(new MdEquipment(StringUtil.trim(eqmResumeBean.getMdEquType())));//设备ID
			updateBean.setModifyTime(StringUtil.trim(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")));//修改时间
			updateBean.setModifySysUser(new SysUser(userId));//修改人
			updateBean.setManufactureDate(DateUtil.strToDate(StringUtil.trim(eqmResumeBean.getManufactureDate()), "yyyy-MM-dd HH:mm:ss"));//设备出厂日期
			updateBean.setPurchaseDate(DateUtil.strToDate(StringUtil.trim(eqmResumeBean.getPurchaseDate()), "yyyy-MM-dd HH:mm:ss"));//设备购置日期
			updateBean.setMaintenanceDate(DateUtil.strToDate(StringUtil.trim(eqmResumeBean.getMaintenanceDate()), "yyyy-MM-dd HH:mm:ss"));//保养日期
			updateBean.setMaintenanceContent(StringUtil.trim(eqmResumeBean.getMaintainContent()));
			updateBean.setMaintenanceType(StringUtil.trim(eqmResumeBean.getMaintenanceType()));//保养类型
			updateBean.setMaintenancePerson(StringUtil.trim(eqmResumeBean.getMaintenancePerson()));//保养人
			updateBean.setMaintainDate(DateUtil.strToDate(StringUtil.trim(eqmResumeBean.getMaintainDate()), "yyyy-MM-dd HH:mm:ss")); //维修日期
			updateBean.setMaintainContent(StringUtil.trim(eqmResumeBean.getMaintainContent()));//维修内容
			updateBean.setMaintainType(StringUtil.trim(eqmResumeBean.getMaintainType()));//维修类型
			updateBean.setMaintainPerson(StringUtil.trim(eqmResumeBean.getMaintainPerson()));//维修人
			updateBean.setFactoryName(StringUtil.trim(eqmResumeBean.getFactoryName()));//制造厂名	
			updateBean.setCompany(StringUtil.trim(eqmResumeBean.getCompany()));//承建单位
			updateBean.setBuildDate(StringUtil.trim(eqmResumeBean.getBuildDate()));//建造年份	
			updateBean.setCheckDate(StringUtil.trim(eqmResumeBean.getCheckDate()));//验收日期		
			updateBean.setUsingDate(StringUtil.trim(eqmResumeBean.getUsingDate()));//开始使用日期	
			updateBean.setVoucherCode(StringUtil.trim(eqmResumeBean.getVoucherCode()));//交接凭证编号	
			updateBean.setCallSource(StringUtil.trim(eqmResumeBean.getCallSource()));//调入来源	
			updateBean.setMoneySource(StringUtil.trim(eqmResumeBean.getMoneySource()));//资金来源	
			updateBean.setHasUsingYear(StringUtil.trim(eqmResumeBean.getHasUsingYear()));//调入时已使用年限	
			updateBean.setHasDepr(StringUtil.trim(eqmResumeBean.getHasDepr()));//调入时已提折旧
			updateBean.setResumeType(StringUtil.trim(eqmResumeBean.getResumeType()));//类     别	
			updateBean.setResumeName(StringUtil.trim(eqmResumeBean.getResumeName()));//名     称	
			updateBean.setResumeModel(StringUtil.trim(eqmResumeBean.getResumeModel()));//牌号、型号、规格或结构、层数建筑面积	
			updateBean.setPropertyCode(StringUtil.trim(eqmResumeBean.getPropertyCode()));//财产编号		
			updateBean.setSkillDataCode(StringUtil.trim(eqmResumeBean.getSkillDataCode()));//技术资料编号
			updateBean.setCostPrice(StringUtil.trim(eqmResumeBean.getCostPrice()));//原价	
			updateBean.setInstallPrice(StringUtil.trim(eqmResumeBean.getInstallPrice()));//其中：安装成本	
			updateBean.setPredUsingYear(StringUtil.trim(eqmResumeBean.getPredUsingYear()));//预计使用年限	
			updateBean.setPredResidual(StringUtil.trim(eqmResumeBean.getPredResidual()));//预计残值	
			updateBean.setPredClearMoney(StringUtil.trim(eqmResumeBean.getPredClearMoney()));//预计清理费用	
			updateBean.setYearDeprRate(StringUtil.trim(eqmResumeBean.getYearDeprRate()));//年折旧率	
			updateBean.setYearFixRate(StringUtil.trim(eqmResumeBean.getYearFixRate()));//年大修理提存率	
			updateBean.setMonthDeprMoney(StringUtil.trim(eqmResumeBean.getMonthDeprMoney()));//月折旧额	
			updateBean.setMonthFixMoney(StringUtil.trim(eqmResumeBean.getMonthFixMoney()));//月大修理提存额	
			//updateBean.setDel;//是否删除
			//private SysUser sysUser;//创建人
			//private Date createDate;//创建日期
			//private String attr1;//备用字段1
			//private String attr2;
			//private String attr3;
			//private String attr4;
		}else{
			EqmResume eqmResume = BeanConvertor.copyProperties(eqmResumeBean, EqmResume.class);
			//设备ID赋值
			eqmResume.setMdEquipment(new MdEquipment(eqmResumeBean.getMdEquType()));
			eqmResume.setSysUser(new SysUser(userId));
			eqmResume.setCreateDate(new Date());
			eqmResume.setDel("0");
			eqmResume.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			eqmResume.setModifySysUser(new SysUser(userId));
			eqmResumeDao.save(eqmResume);
		}
		/*if(!StringUtil.notNull(eqmResumeBean.getId())){
			eqmResume.setId(null);
		}
		//设备ID赋值
		eqmResume.setMdEquipment(new MdEquipment(eqmResumeBean.getMdEquType()));
		eqmResume.setSysUser(new SysUser(userId));
		eqmResume.setCreateDate(new Date());
		eqmResume.setDel("0");
		
		eqmResume.setModifyTime(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
		eqmResume.setModifySysUser(new SysUser(userId));
		
		eqmResumeDao.saveOrUpdate(eqmResume);*/
	}


	@Override
	public DataGrid queryResume(EqmResumeBean eqmResumeBean,PageParams pageParams) throws Exception {
		String hql = "from EqmResume o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		//hql=hql.concat(StringUtil.fmtDateBetweenParams("o.date", logBean.getDate(), logBean.getDate2()));
		if(StringUtil.notNull(eqmResumeBean.getMdEquName())){
			params.append(" and o.mdEquipment.equipmentName like '%"+ eqmResumeBean.getMdEquName() +"%'");
		}
		if(StringUtil.notNull(eqmResumeBean.getMdEquType())){
			params.append(" and o.mdEquipment.mdEqpType like '%"+ eqmResumeBean.getMdEquType() +"%'");
		}
		String param = params.toString();
		List<EqmResume> eqmResumes = eqmResumeDao.queryByPage(hql.concat(param), pageParams);
		List<EqmResumeBean> list = new ArrayList<EqmResumeBean>();
		for(EqmResume er : eqmResumes){
			EqmResumeBean eb = new EqmResumeBean();
			BeanConvertor.copyProperties(er, eb);
			eb.setMdEquName(er.getMdEquipment().getEquipmentName());
			eb.setMdEquType(er.getMdEquipment().getMdEqpType().getName());
			list.add(eb);
		}
		hql = "select count(*) from EqmResume o where 1=1 and o.del=0 ";
		long total = eqmResumeDao.queryTotal(hql.concat(param));
		return new DataGrid(list,total);
	}

	@Override
	public void deleResumeById(String id) throws Exception {
		Integer str=eqmResumeDao.deleteByParams("update EqmResume  set del='1' where id=?", id);
		
	}
	
}
