package com.shlanbao.tzsc.pms.cos.spare.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.CosSparePartsCopDaoI;
import com.shlanbao.tzsc.base.dao.CosSparePartsDaoI;
import com.shlanbao.tzsc.base.mapping.CosSpareParts;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdUnit;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;
import com.shlanbao.tzsc.pms.cos.spare.service.CosSparePartsServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Service
public class CosSparePartsServiceImpl extends BaseService implements
		CosSparePartsServiceI {

	@Autowired
	public CosSparePartsDaoI cosSparePartsDaoI;

	@Autowired
	public CosSparePartsCopDaoI cosSparePartsCop;

	// 添加或修改
	@Override
	public boolean saveOrUpdateBean(CosSparePartsBean bean) throws Exception {
		CosSpareParts spare = null;
		if (StringUtil.notNull(bean.getId()))
			spare = getMappingBeanById(bean.getId());
		else
			spare = new CosSpareParts();
		spare.setAttr1(bean.getAttr1());
		spare.setAttr2(bean.getAttr2());
		spare.setName(bean.getName());
		spare.setPrice(bean.getPrice());
		spare.setRemark(bean.getRemark());
		spare.setType(bean.getType());
		if (StringUtil.notNull(bean.getUnitId())) {
			MdUnit unit = new MdUnit();
			unit.setId(bean.getUnitId());
			spare.setUnit(unit);
		}
		if (StringUtil.notNull(bean.getEqpTypeId())) {
			MdEqpType eqpType = new MdEqpType();
			eqpType.setId(bean.getEqpTypeId());
			spare.setEqpType(eqpType);
		}
		return cosSparePartsDaoI.saveOrUpdate(spare);
	}

	// 查询list
	@Override
	public List<CosSparePartsBean> queryListByBeans(CosSparePartsBean bean)
			throws Exception {
		return queryListByBeans(bean, null, 0, false);
	}

	// WCT备品备件查询
	@Override
	public DataGrid queryGridByBean(CosSparePartsBean bean,
			PageParams pageParams) throws Exception {
		long length = queryTotal();
		// long length=15;
		List<CosSparePartsBean> beans = queryListByBeans(bean, pageParams,
				length, true);
		return new DataGrid(beans, length);
	}
	public boolean deleteById(String id){
		CosSpareParts b=cosSparePartsDaoI.findById(CosSpareParts.class, id);
		b.setDel("1");
		return true;
	}
	// 根据id查询
	@Override
	public CosSparePartsBean getBeanById(String id) throws Exception {
		CosSpareParts spare = cosSparePartsDaoI.findById(CosSpareParts.class,
				id);
		CosSparePartsBean b = new CosSparePartsBean();
		BeanConvertor.copyProperties(spare, b);
		try {
		} catch (Exception e) {
			
		}
		try {
			b.setEqpTypeId(spare.getEqpType().getId());
			b.setEqpTypeCode(spare.getEqpType().getCode());
			b.setEqpTypeName(spare.getEqpType().getName());
		} catch (Exception e) {
			b.setEqpTypeId(null);
			b.setEqpTypeId(null);
			b.setEqpTypeCode(null);
		}
		try {
			b.setUnitId(spare.getUnit().getId());
			b.setUnitName(spare.getUnit().getName());
		} catch (Exception e) {
			b.setUnitId(null);
			b.setUnitName(null);
		}
		return b;
	}

	// 根据id查询mapping类
	public CosSpareParts getMappingBeanById(String id) throws Exception {
		return cosSparePartsDaoI.findById(CosSpareParts.class, id);
	}

	/**
	 * WCT备品备件更换框查询
	 * 
	 * @param bean
	 * @param pageParams
	 * @param length
	 * @param isNameFuzzy
	 * @return
	 * @throws Exception
	 */
	public List<CosSparePartsBean> queryListByBeans(CosSparePartsBean bean,
			PageParams pageParams, long length, boolean isNameFuzzy)
			throws Exception {

		String hql = "from CosSpareParts o where 1=1 and del= '0' ";
		String param = "";
		if (StringUtil.notNull(bean.getId()))
			param += "and o.id='" + bean.getId() + "' ";
		if (StringUtil.notNull(bean.getEqpTypeId()))
			param += "and o.eqpType.id='" + bean.getEqpTypeId() + "' ";
		if (isNameFuzzy && StringUtil.notNull(bean.getName()))
			param += "and o.name like '%" + bean.getName().trim() + "%' ";
		else if (StringUtil.notNull(bean.getName()))
			param += "and o.name='" + bean.getName().trim() + "' ";
		// star-数量
		if (StringUtil.notNull(bean.getAttr2()))
			param += "and o.attr2='" + bean.getAttr2() + "' ";
		// end
		if (StringUtil.notNull(bean.getAttr1()))
			param += "and o.attr1='" + bean.getAttr1() + "' ";
		if (StringUtil.notNull(bean.getEqpTypeName()))
			param += "and o.eqpType.name='" + bean.getEqpTypeName() + "' ";
		if (StringUtil.notNull(bean.getUnitId()))
			param += "and o.unit.id='" + bean.getUnitId() + "' ";
		if (StringUtil.notNull(bean.getType())) {
			param += "and o.type like'%" + bean.getType().trim() + "%'";
		}
		List<CosSpareParts> list = null;
//		if (pageParams != null) {
		list=cosSparePartsDaoI.query(hql.concat(param));
//			list = cosSparePartsDaoI.queryByPage(hql.concat(param), pageParams);
//		} else {
//			list = cosSparePartsDaoI.queryByPage(hql.concat(param), pageParams);
//			length = cosSparePartsDaoI.queryTotal(hql.concat(param));
//			list = cosSparePartsDaoI.query(hql);
//		}
		List<CosSparePartsBean> beans = new ArrayList<CosSparePartsBean>();
		if (list.size() > 0) {
			for (CosSpareParts spare : list) {
				CosSparePartsBean b = new CosSparePartsBean();
				BeanConvertor.copyProperties(spare, b);
				if (spare.getEqpType() != null
						&& !spare.getEqpType().equals("")) {
					b.setEqpTypeId(spare.getEqpType().getId());
					b.setEqpTypeCode(spare.getEqpType().getCode());
					b.setEqpTypeName(spare.getEqpType().getName());
				}
				if (spare.getUnit() != null && !spare.getUnit().equals("")) {
					b.setUnitId(spare.getUnit().getId());
					b.setUnitName(spare.getUnit().getName());
				}
				b.setNumber(spare.getAttr2());
				b.setType(spare.getType());
				b.setId(spare.getId());
				beans.add(b);
			}
		}
		return beans;
	}

	// PMS备品备件查询
	@Override
	public DataGrid queryGridByBeanPMS(CosSparePartsBean bean,
			PageParams pageParams) throws Exception {
		long length = queryTotal();
		// long length=15;
		List<CosSparePartsBean> beans = queryListByBeansPMS(bean, pageParams,
				length, true);
		return new DataGrid(beans, length);
	}

	/**
	 * PMS系统备品备件查询 张璐
	 */
	public List<CosSparePartsBean> queryListByBeansPMS(CosSparePartsBean bean,
			PageParams pageParams, long length, boolean isNameFuzzy)
			throws Exception {
		String hql = "from CosSpareParts o where 1=1 and del='0' ";
		String param = "";
		if (StringUtil.notNull(bean.getId()))
			param += "and o.id='" + bean.getId() + "' ";
		if (StringUtil.notNull(bean.getEqpTypeId()))
			param += "and o.eqpType.id='" + bean.getEqpTypeId() + "' ";
		if (isNameFuzzy && StringUtil.notNull(bean.getName()))
			param += "and o.name like '%" + bean.getName().trim() + "%' ";
		else if (StringUtil.notNull(bean.getName()))
			param += "and o.name='" + bean.getName().trim() + "' ";
		// star-数量
		if (StringUtil.notNull(bean.getAttr2()))
			param += "and o.attr2='" + bean.getAttr2() + "' ";
		// end
		if (StringUtil.notNull(bean.getAttr1()))
			param += "and o.attr1='" + bean.getAttr1() + "' ";
		if (StringUtil.notNull(bean.getEqpTypeName()))
			param += "and o.eqpType.name='" + bean.getEqpTypeName() + "' ";
		if (StringUtil.notNull(bean.getUnitId()))
			param += "and o.unit.id='" + bean.getUnitId() + "' ";
		if (StringUtil.notNull(bean.getType())) {
			param += "and o.type like'%" + bean.getType().trim() + "%'";
		}
		List<CosSpareParts> list = null;
		if (pageParams != null) {
			list = cosSparePartsDaoI.queryByPage(hql.concat(param), pageParams);
		} else {
			length = cosSparePartsDaoI.queryTotal(hql.concat(param));
			list = cosSparePartsDaoI.query(hql);
		}
		List<CosSparePartsBean> beans = new ArrayList<CosSparePartsBean>();
		if (list.size() > 0) {
			for (CosSpareParts spare : list) {
				CosSparePartsBean b = new CosSparePartsBean();
				BeanConvertor.copyProperties(spare, b);
				if (spare.getEqpType() != null
						&& !spare.getEqpType().equals("")) {
					b.setEqpTypeId(spare.getEqpType().getId());
					b.setEqpTypeCode(spare.getEqpType().getCode());
					b.setEqpTypeName(spare.getEqpType().getName());
				}
				if (spare.getUnit() != null && !spare.getUnit().equals("")) {
					b.setUnitId(spare.getUnit().getId());
					b.setUnitName(spare.getUnit().getName());
				}
				b.setNumber(spare.getAttr2());
				b.setType(spare.getType());
				b.setId(spare.getId());
				beans.add(b);
			}
		}
		return beans;
	}

	private long queryTotal() throws Exception {
		String sql = "select count(0) from CosSpareParts where del='0' ";
		return cosSparePartsDaoI.queryTotal(sql);
	}

	@Override
	public void deleteBean(String id) {
		cosSparePartsDaoI.deleteById(id, CosSpareParts.class);
	}

	/**
	 * 【功能说明】：备品备件导入Excel，批量保存
	 * shisihai
	 * 
	 * */
	@Override
	public boolean batchInputExeclAndReadWrite(List<CosSparePartsBean> lt,
			CosSparePartsBean cspb) throws Exception {
		StringBuffer sb = null;
		CosSpareParts p=null;
		StringBuffer sb2=null;
		for (int i = 0; i < lt.size(); i++) {
			CosSparePartsBean csp = lt.get(i);
			sb2=new StringBuffer();
			sb = new StringBuffer();
			if (csp == null || csp.getName().trim().equals("")) {
				continue;
			}
			// 判断是否已经存在
			 sb2.append(" from CosSpareParts o where 1=1 and del='0' ");
			 if(csp.getAttr1()!=null){
				 sb2.append(" and o.attr1='"+ csp.getAttr1()+"'");
			 }
			 if(csp.getType()!=null){
				 sb2.append(" and o.type='"+csp.getType()+"'");
			 }
			 if(csp.getEqpTypeId()!=null){
				 sb2.append(" and o.eqp_type='"+csp.getEqpTypeId()+"'");
			 }
			 if(csp.getName()!=null){
				 String name=csp.getName();
				 if(name.contains("'")){
					 name=name.replace("'", "");
				 };
				 sb2.append(" and o.name='"+name+"'");
			 }
			List<CosSpareParts> list = cosSparePartsDaoI.query(sb2.toString());
			sb2=null;
			if (list != null && list.size() > 0) {
				p=list.get(0);//数据库已存在
				int num=0;//用于接收库存的整数
				double num4=0.0;//用于接收库存的小数
				
				int num2=0;//用于接收导入的整数
				double num3=0.0;//用于接收导入的小数
				
				
				//库存信息
				if(p.getAttr2()!=null){
					try {
						num=Integer.parseInt(p.getAttr2());
					} catch (Exception e) {
						try{
							num4=Double.parseDouble(p.getAttr2());
							num4=MathUtil.roundHalfUp(num4, 2);
						}catch(Exception e2){
							e2.printStackTrace();
						}
					}
				}
				
				//需要累加的信息
				if(csp.getAttr2()!=null){//现在需要导入的数量
					String numS=csp.getAttr2();
					try {
						num2=Integer.parseInt(numS);
					} catch (Exception e) {
						try{
						num3=Double.parseDouble(numS);
						num3=MathUtil.roundHalfUp(num3, 2);
						}catch(Exception e2 ){
							e2.printStackTrace();
						}
						
					}
				}
				//如果导入累加的数量为0，继续下一个	
			if(num2==0&&num3==0){
				continue;
			}
			String sql=null;
			if(num3==0){
				if(num4==0){
					sql="UPDATE COS_SPARE_PARTS SET ATTR2 = "+(num+num2)+" where id='"+p.getId()+"'";
				}else{
					sql="UPDATE COS_SPARE_PARTS SET ATTR2 = "+(num4+num2)+" where id='"+p.getId()+"'";
				}
			}else{
				if(num4==0){
					sql="UPDATE COS_SPARE_PARTS SET ATTR2 = "+(num+num3)+" where id='"+p.getId()+"'";
				}else{
					sql="UPDATE COS_SPARE_PARTS SET ATTR2 = "+(num4+num3)+" where id='"+p.getId()+"'";
				}
			}
			cosSparePartsDaoI.updateBySql(sql.toString(), null);
			} else {
				sb.append(" INSERT into COS_SPARE_PARTS(ID,NAME,EQP_TYPE,REMARK,PRICE,UNIT,ATTR1,ATTR2,TYPE,SEARCH_W,position,sell_num,random_num,amount,DEL) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				List<Object> params = new ArrayList<Object>();
				params.add(UUID.randomUUID().toString());
				//去掉特殊的字符
				String name=csp.getName();
				 if(name.contains("'")){
					 name=name.replace("'", "");
				 };
				params.add(name);
				params.add(csp.getEqpTypeId());
				params.add(csp.getRemark());
				params.add(csp.getPrice());
				params.add(csp.getUnitId());
				params.add(csp.getAttr1());
				params.add(csp.getAttr2());
				params.add(csp.getType());
				params.add(csp.getSEARCH_W());
				params.add(csp.getPosition());
				params.add(csp.getSell_num());
				params.add(csp.getRandom_num());
				params.add(csp.getAmount());
				params.add("0");
				cosSparePartsDaoI.updateBySql(sb.toString(), params);
			}
		}
		return false;
	}

	/**
	 * 写死的
	 * 
	 * @return
	 */
	public Map<String, String> retUnit() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("箱", "1");
		map.put("万支", "10");
		map.put("千克", "2");
		map.put("张", "3");
		map.put("米", "4");
		map.put("件", "5");
		map.put("分钟", "6");
		map.put("支", "7");
		map.put("包", "8");
		map.put("条", "9");

		return map;
	}
}
