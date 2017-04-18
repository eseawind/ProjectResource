package com.shlanbao.tzsc.pms.sys.datadict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.SysEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.CosSpareParts;
import com.shlanbao.tzsc.base.mapping.EqmLubricantMaintain;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.SysEqpCategory;
import com.shlanbao.tzsc.base.mapping.SysEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmBubricantfBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpTypeServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqpCheckRecordBean;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanBeans;

/**
 * 数据字典实现类
 */
@Service
public class SysEqpTypeServiceImpl extends BaseService implements
		SysEqpTypeServiceI {
	@Autowired
	private SysEqpTypeDaoI sysEqpTypeDao;
	@Autowired
	private BaseDaoI<T> baseDao;
	@Autowired
	private LoadComboboxServiceI comboboxServiceI;

	// 更新 图片设置的点信息
	@Override
	public void addSetUpImages(String id, String imagePointMsg, String filePath)
			throws Exception {
		if (null != id && !"".equals(id)) {
			SysEqpType mdEqpType = sysEqpTypeDao.findById(SysEqpType.class, id);
			if (null != mdEqpType) {
				imagePointMsg = imagePointMsg.replaceAll("\"", "\'");// 双引号替换成单引号
				imagePointMsg = imagePointMsg.replaceAll(" ", "");
				mdEqpType.setSetImagePoint(imagePointMsg);
				if (null != filePath && !"".equals(filePath)) {
					mdEqpType.setFilePath(filePath);
				} else {
					if (null != mdEqpType.getSysEqpCategory()) {
						/*
						 * if(null!=mdEqpType.getSysEqpCategory().getUploadBean()
						 * ){
						 * mdEqpType.setFilePath(mdEqpType.getSysEqpCategory()
						 * .getUploadBean().getFileId()); }
						 */
					}
				}
			}
		}
	}

	@Override
	public void addMdType(SysEqpTypeBean mdEqpTypeBean) throws Exception {
		SysEqpType mdEqpType = new SysEqpType();
		if (StringUtil.notNull(mdEqpTypeBean.getId())) {
			mdEqpType = sysEqpTypeDao.findById(SysEqpType.class,
					mdEqpTypeBean.getId());
			BeanConvertor.copyProperties(mdEqpTypeBean, mdEqpType);
		} else {// 保存
			mdEqpType = BeanConvertor.copyProperties(mdEqpTypeBean,
					SysEqpType.class);
			mdEqpType.setId(UUID.randomUUID().toString());
		}
		if (StringUtil.notNull(mdEqpTypeBean.getCategoryId())) {
			mdEqpType.setSysEqpCategory(new SysEqpCategory(mdEqpTypeBean
					.getCategoryId()));
		}
		if (StringUtil.notNull(mdEqpTypeBean.getOilId())) {// 润 滑 油（脂）ID
			EqmLubricantMaintain bean = new EqmLubricantMaintain();
			bean.setId(mdEqpTypeBean.getOilId());
			mdEqpType.setOilIdBean(bean);
		}
		if (StringUtil.notNull(mdEqpTypeBean.getFillUnit())) {// 加注量单位ID
			SysEqpType bean = new SysEqpType();
			bean.setId(mdEqpTypeBean.getFillUnit());
			mdEqpType.setFillUnitBean(bean);
		}
		if (StringUtil.notNull(mdEqpTypeBean.getFashion())) {// 方式ID
			SysEqpType bean = new SysEqpType();
			bean.setId(mdEqpTypeBean.getFashion());
			mdEqpType.setFashionBean(bean);
		}
		mdEqpType.setDel("0");
		if (StringUtil.notNull(mdEqpTypeBean.getId())) {
			// 更新不用写
		} else {
			sysEqpTypeDao.save(mdEqpType);
		}
		// comboboxServiceI.initCombobox(ComboboxType.EQPTYPE);
	}

	@Override
	public SysEqpTypeBean getTypeById(String id) throws Exception {
		SysEqpType mdEqpType = sysEqpTypeDao.findById(SysEqpType.class, id);
		SysEqpTypeBean bean = BeanConvertor.copyProperties(mdEqpType,
				SysEqpTypeBean.class);
		if (null != mdEqpType.getSysEqpCategory()) {
			bean.setCategoryId(mdEqpType.getSysEqpCategory().getId());
			bean.setCategoryName(mdEqpType.getSysEqpCategory().getName());
		}
		if (null != mdEqpType.getOilIdBean()) {// 润滑油
			bean.setOilId(mdEqpType.getOilIdBean().getId());
			bean.setOilName(mdEqpType.getOilIdBean().getLubricantName());
		}
		if (null != mdEqpType.getFillUnitBean()) {// 单位
			bean.setFillUnit(mdEqpType.getFillUnitBean().getId());
			bean.setFillUnitName(mdEqpType.getFillUnitBean().getName());
		}
		if (null != mdEqpType.getFashionBean()) {// 方式
			bean.setFashion(mdEqpType.getFashionBean().getId());
			bean.setFashionName(mdEqpType.getFashionBean().getName());
		}
		return bean;
	}

	@Override
	public DataGrid queryMdType(SysEqpTypeBean mdTypeBean, PageParams pageParams)
			throws Exception {
		String hql = "from SysEqpType o ";
		hql += " left join fetch  o.sysEqpCategory sys1  ";//
		hql += " left join fetch  o.oilIdBean sys2  "; // 润滑油
		hql += " left join fetch  o.fillUnitBean sys3  "; // 单位
		hql += " left join fetch  o.fashionBean sys4  "; // 方式
		hql += " where o.del=0  ";
		StringBuffer params = new StringBuffer();
		if (StringUtil.notNull(mdTypeBean.getName())) {
			params.append(" and o.name like '%" + mdTypeBean.getName() + "%'");
		}
		if (StringUtil.notNull(mdTypeBean.getCode())) {
			params.append(" and o.code like '%" + mdTypeBean.getCode() + "%'");
		}
		if (StringUtil.notNull(mdTypeBean.getCategoryId())) {
			params.append(" and o.sysEqpCategory.id='"
					+ mdTypeBean.getCategoryId() + "'");
		}
		if (StringUtil.notNull(mdTypeBean.getId())) {
			params.append(" and o.id='" + mdTypeBean.getId() + "'");
		}
		List<SysEqpType> rows = sysEqpTypeDao.queryByPage(
				hql.concat(params.toString()), pageParams);
		List<SysEqpTypeBean> beans = new ArrayList<SysEqpTypeBean>();
		for (SysEqpType mdEqpType : rows) {
			try {
				SysEqpTypeBean bean = new SysEqpTypeBean();
				beanConvertor.copyProperties(mdEqpType, bean);
				if (null != mdEqpType.getSysEqpCategory()) {
					bean.setCategoryId(mdEqpType.getSysEqpCategory().getId());
					bean.setCategoryName(mdEqpType.getSysEqpCategory().getName());
				}
				if (null != mdEqpType.getOilIdBean()) {// 润滑油
					bean.setOilId(mdEqpType.getOilIdBean().getId());
					bean.setOilName(mdEqpType.getOilIdBean().getLubricantName());
				}
				if (null != mdEqpType.getFillUnitBean()) {// 单位
					bean.setFillUnit(mdEqpType.getFillUnitBean().getId());
					bean.setFillUnitName(mdEqpType.getFillUnitBean().getName());
				}
				if (null != mdEqpType.getFashionBean()) {// 方式
					bean.setFashion(mdEqpType.getFashionBean().getId());
					bean.setFashionName(mdEqpType.getFashionBean().getName());
				}
				beans.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		hql = "select count(*) from SysEqpType o where o.del=0 ";
		long total = sysEqpTypeDao.queryTotal(hql.concat(params.toString()));
		return new DataGrid(beans, total);
	}

	@Override
	public void deleteMdType(String id) throws Exception {
		// sysEqpTypeDao.findById(SysEqpType.class, id).setDel("1");
		sysEqpTypeDao.deleteById(id, SysEqpType.class);
		// comboboxServiceI.initCombobox(ComboboxType.EQPTYPE);
	}

	@Override
	public List<SysEqpTypeBean> queryMdType() throws Exception {
		// String hql = "from SysEqpType o where 1=1 and o.del=0";
		String hql = "from SysEqpType o ";
		hql += " left join fetch  o.sysEqpCategory sys1  ";//
		hql += " left join fetch  o.oilIdBean sys2  "; // 润滑油
		hql += " left join fetch  o.fillUnitBean sys3  "; // 单位
		hql += " left join fetch  o.fashionBean sys4  "; // 方式
		hql += " where o.del=0  ";
		List<SysEqpType> eqpTypes = sysEqpTypeDao.query(hql, new Object[] {});
		List<SysEqpTypeBean> beans = new ArrayList<SysEqpTypeBean>();
		for (SysEqpType mdEqpType : eqpTypes) {
			SysEqpTypeBean bean = new SysEqpTypeBean();
			beanConvertor.copyProperties(mdEqpType, bean);
			if (null != mdEqpType.getSysEqpCategory()) {
				bean.setCategoryId(mdEqpType.getSysEqpCategory().getId());
				bean.setCategoryName(mdEqpType.getSysEqpCategory().getName());
			}
			if (null != mdEqpType.getOilIdBean()) {// 润滑油
				bean.setOilId(mdEqpType.getOilIdBean().getId());
				bean.setOilName(mdEqpType.getOilIdBean().getLubricantName());
			}
			if (null != mdEqpType.getFillUnitBean()) {// 单位
				bean.setFillUnit(mdEqpType.getFillUnitBean().getId());
				bean.setFillUnitName(mdEqpType.getFillUnitBean().getName());
			}
			if (null != mdEqpType.getFashionBean()) {// 方式
				bean.setFashion(mdEqpType.getFashionBean().getId());
				bean.setFashionName(mdEqpType.getFashionBean().getName());
			}
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public List<SysEqpTypeBean> queryMdEqpTypeByCategory(String[] categoryIds)
			throws Exception {
		// String hql = "from SysEqpType o where 1=1 and o.del=0 ";
		String hql = "from SysEqpType o ";
		hql += " left join fetch  o.sysEqpCategory sys1  ";//
		hql += " left join fetch  o.oilIdBean sys2  "; // 润滑油
		hql += " left join fetch  o.fillUnitBean sys3  "; // 单位
		hql += " left join fetch  o.fashionBean sys4  "; // 方式
		hql += " where o.del=0  ";
		hql += " and o.sysEqpCategory.id in ("
				+ StringUtil.arrayToStringBySqlin(categoryIds) + ") ";
		List<SysEqpType> eqpTypes = sysEqpTypeDao.query(hql);
		List<SysEqpTypeBean> beans = new ArrayList<SysEqpTypeBean>();
		for (SysEqpType mdEqpType : eqpTypes) {
			SysEqpTypeBean bean = new SysEqpTypeBean();
			beanConvertor.copyProperties(mdEqpType, bean);
			if (null != mdEqpType.getSysEqpCategory()) {
				bean.setCategoryId(mdEqpType.getSysEqpCategory().getId());
				bean.setCategoryName(mdEqpType.getSysEqpCategory().getName());
			}
			if (null != mdEqpType.getOilIdBean()) {// 润滑油
				bean.setOilId(mdEqpType.getOilIdBean().getId());
				bean.setOilName(mdEqpType.getOilIdBean().getLubricantName());
			}
			if (null != mdEqpType.getFillUnitBean()) {// 单位
				bean.setFillUnit(mdEqpType.getFillUnitBean().getId());
				bean.setFillUnitName(mdEqpType.getFillUnitBean().getName());
			}
			if (null != mdEqpType.getFashionBean()) {// 方式
				bean.setFashion(mdEqpType.getFashionBean().getId());
				bean.setFashionName(mdEqpType.getFashionBean().getName());
			}
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public List<SysEqpType> queryBean(SysEqpTypeBean mdTypeBean) {
		String hql = "from SysEqpType o ";
		hql += " left join fetch  o.sysEqpCategory sys1  ";//
		hql += " left join fetch  o.oilIdBean sys2  "; // 润滑油
		hql += " left join fetch  o.fillUnitBean sys3  "; // 单位
		hql += " left join fetch  o.fashionBean sys4  "; // 方式
		hql += " where o.del=0  ";
		StringBuffer params = new StringBuffer();
		if (StringUtil.notNull(mdTypeBean.getName())) {
			params.append(" and o.name like '%" + mdTypeBean.getName() + "%'");
		}
		if (StringUtil.notNull(mdTypeBean.getCode())) {
			params.append(" and o.code like '%" + mdTypeBean.getCode() + "%'");
		}
		if (StringUtil.notNull(mdTypeBean.getCategoryId())) {
			params.append(" and o.sysEqpCategory.id='"
					+ mdTypeBean.getCategoryId() + "'");
		}
		if (StringUtil.notNull(mdTypeBean.getId())) {
			params.append(" and o.id='" + mdTypeBean.getId() + "'");
		}
		List<SysEqpType> rows = sysEqpTypeDao.query(hql.concat(params
				.toString()));
		return rows;
	}

	@Override
	public void inputExeclAndReadWrite(List<SysEqpTypeBean> lt,
			SysEqpTypeBean etb) throws Exception {
		for (int i = 0; i < lt.size(); i++) {
			SysEqpTypeBean etp = lt.get(i);
			if(etp.getName().trim().equals("")||etp.getDes().trim().equals("")){
				continue;
			}
			SysEqpType sysEqpType = new SysEqpType();
			SysEqpCategory c = new SysEqpCategory();
			c.setId(etb.getId());
			sysEqpType.setSysEqpCategory(c);
			sysEqpType.setDel("0");
			sysEqpType.setEnable(1L);
			sysEqpType.setCode(UUID.randomUUID().toString());
			// 判断是否已经存在
			String sql = "select cid from SYS_EQP_TYPE where cid='"
					+ etb.getId() + "' and name='" + etp.getName() + "'";
			List list = sysEqpTypeDao.queryBySql(sql);
			if (list != null && list.size() > 0) {
				continue;
			} else {
				beanConvertor.copyProperties(etp, sysEqpType);
				// 润滑油
				if (StringUtil.notNull(etp.getOilId())) {
					EqmLubricantMaintain e = new EqmLubricantMaintain();
					e.setId(etp.getOilId());
					sysEqpType.setOilIdBean(e);
				}
				// 润滑油加注单位
				if (StringUtil.notNull(etp.getCategoryId())) {
					SysEqpType e = new SysEqpType();
					e.setId(etp.getCategoryId());
					sysEqpType.setFillUnitBean(e);
				}
				// 润滑方式
				if (StringUtil.notNull(etp.getFashion())) {
					SysEqpType e = new SysEqpType();
					e.setId(etp.getFashion());
					sysEqpType.setFashionBean(e);
				}
				if(StringUtil.notNull(etp.getPeriodUnit2())){
					sysEqpType.setUnit_id(etp.getPeriodUnit2());
				}
				sysEqpTypeDao.save(sysEqpType);
			}
		}

	}

	/**
	 * 功能说明：通过设备Id，日期时间，班次，润滑类型查找表eqm_lubricat_plan表
	 * @author wanchanghuang
	 * @time 2015年9月17日16:24:37
	 * 
	 * */
	@Override
	public EqmBubricantfBean queryEqmLubricantPlanByPar(
			EqmBubricantfBean bean,String date,String unit_id,String shift_id,String eqp_id) {
		String sql="select id,lub_id,shift_id from EQM_LUBRICAT_PLAN where  shift_id='"+shift_id+"' and CONVERT(varchar(50),date_p,23)='"+date+"' and lub_id='"+unit_id+"' and eqp_id='"+eqp_id+"' ";
		List<?> list=baseDao.queryBySql(sql);
		 EqmBubricantfBean b=new EqmBubricantfBean();
		 if(list.size()>0){
			Object[] temp=(Object[])list.get(0);
			try {
				b.setId(temp[0].toString());
				if(temp[1]!=null){
					b.setLub_id(Integer.parseInt(temp[1].toString()));
				}
				b.setShift_id(temp[2].toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
		 }else{
			 b=null;
		 }
		return b;
	}

	 /**
     * [功能说明]：根据sys_eqp_type表cid查询集合
     * @time 2015年9月24日9:19:51
     * @author wanchanghuang	
     * 
     * */
	@Override
	public List<SysEqpTypeBean> querySysEqmTypeByCid(SysEqpTypeBean sysEqpType) {
		List<SysEqpTypeBean> listSete=new ArrayList<SysEqpTypeBean>();
		String sql="SELECT a.id, a.name, a.des, (select lubricant_name from EQM_LUBRICANT_MAINTAIN where id=a.oilid) as oilid, a.fill_quantity, (select name from SYS_EQP_TYPE where id=a.fill_unit ) as fill_unit_name, (select name from SYS_EQP_TYPE where id=a.fashion  ) as fashion_name, a.period, a.unit_id FROM SYS_EQP_TYPE a WHERE a.del = 0 AND cid = '"+sysEqpType.getCategoryId()+"'";
		List<?> list=baseDao.queryBySql(sql);
		if(list.size()>0){
			for(Object o:list){
				SysEqpTypeBean st=new SysEqpTypeBean();
				Object[] temp=(Object[]) o;
				st.setId(temp[0].toString());
				st.setName(temp[1].toString());
				if(temp[2]!=null){
					st.setDes(temp[2].toString());
				}
				if(temp[3]!=null){
					st.setOilId(temp[3].toString());
				}
				if(temp[4]!=null){
					st.setFillQuantity(temp[4].toString());
				}
				if(temp[5]!=null){
					st.setFillUnitName(temp[5].toString());
				}
				if(temp[6]!=null){
					st.setFashionName(temp[6].toString());
				}
				if(temp[7]!=null){
					st.setPeriod(Integer.parseInt(temp[7].toString()));
				}
				if(temp[8]!=null){
					st.setUnit_id(temp[8].toString());
				}
				
				listSete.add(st);
			}
		}
		return listSete;
	}

}
