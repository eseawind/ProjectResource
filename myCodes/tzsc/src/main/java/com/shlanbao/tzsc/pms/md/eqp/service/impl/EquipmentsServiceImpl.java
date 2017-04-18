package com.shlanbao.tzsc.pms.md.eqp.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.EqmipmentsDaoI;
import com.shlanbao.tzsc.base.dao.MdEqpTypeDaoI;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.md.eqp.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.md.eqp.service.EquipmentsServiceI;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;
import com.shlanbao.tzsc.utils.excel.ExcelWriter;
import com.shlanbao.tzsc.utils.excel.ExportExcel;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 设备主数据实现类
 * @author liuligong 
 * @Time 2014/11/5 10:46
 */
@Service
public class EquipmentsServiceImpl extends BaseService implements EquipmentsServiceI{
	
	@Autowired
	private EqmipmentsDaoI equipmentsDao;
	
	@Autowired
	private MdEqpTypeDaoI mdEqpTypeDaoI;
	
	@Override
	public void addEqu(MdEquipment equBean) throws Exception {
		//MdEquipment equ = BeanConvertor.copyProperties(equBean, MdEquipment.class);
		if(StringUtil.notNull(equBean.getId())){
			equBean.setId(equBean.getId());
		}
		//if(StringUtil.notNull(equBean.getEquipmentCode())){
			equBean.setEquipmentCode(equBean.getEquipmentCode().trim());
		//}
		//if(StringUtil.notNull(equBean.getEquipmentName())){
			equBean.setEquipmentName(equBean.getEquipmentName().trim());
		//}
		//if(StringUtil.notNull(equBean.getEquipmentDesc())){
			equBean.setEquipmentDesc(equBean.getEquipmentDesc().trim());
		//}
		//if(StringUtil.notNull(equBean.getWorkCenter())){
			equBean.setWorkCenter(equBean.getWorkCenter().trim());
		//}
		//if(StringUtil.notNull(equBean.getEquipmentPosition())){
			equBean.setEquipmentPosition(equBean.getEquipmentPosition().trim());
		//}
		//if(StringUtil.notNull(equBean.getFixedAssetNum())){
			equBean.setFixedAssetNum(equBean.getFixedAssetNum().trim());
		//}
		//if(StringUtil.notNull(equBean.getManufacturingNum())){
			equBean.setManufacturingNum(equBean.getManufacturingNum().trim());
		//}
		//if(StringUtil.notNull(equBean.getApprovalNum())){
			equBean.setApprovalNum(equBean.getApprovalNum().trim());
		//}
		//if(StringUtil.notNull(equBean.getNavicertNum())){
			equBean.setNavicertNum(equBean.getNavicertNum().trim());
		//}
		//if(StringUtil.notNull(equBean.getUsingDepartment())){
			equBean.setUsingDepartment(equBean.getUsingDepartment().trim());
		//}
		equBean.setDel("0");
		equBean.setCreateTime(new java.util.Date());
		equipmentsDao.saveOrUpdate(equBean);
	}

	@Override
	public DataGrid queryEqu(EquipmentsBean equBean, PageParams pageParams) throws Exception {
		//hql 语句拼装
		String hql = "from MdEquipment o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(equBean.getEquipmentCode())){
			params.append(" and o.equipmentCode like '%"+ equBean.getEquipmentCode() +"%'");
		}
		
		if(StringUtil.notNull(equBean.getEquipmentName())){
			params.append(" and o.equipmentName like '%"+ equBean.getEquipmentName() +"%'");
		}
		//加入两个条件
		if(StringUtil.notNull(equBean.getWorkShopId())){
			params.append(" and o.mdWorkshop.id='"+ equBean.getWorkShopId() +"'");
		}
		
		if(StringUtil.notNull(equBean.getWorkShopName())){//1卷包车间2成型车间也是对象id
			params.append(" and o.mdWorkshop.id='"+equBean.getWorkShopName()+"'");
		}
		
		if(StringUtil.notNull(equBean.getEqpTypeName())){//传进来Id根据对象比较
			params.append(" and o.mdEqpType.id='"+equBean.getEqpTypeName()+"'");
		}
		
		String param = params.toString();
		List<MdEquipment> rows = equipmentsDao.queryByPage(hql.concat(param), pageParams);
		List<EquipmentsBean> equBeans = new ArrayList<EquipmentsBean>();
		for(MdEquipment me : rows){
			String typeId = me.getMdEqpType().getId();
			String typeName = me.getMdEqpType().getName();
			EquipmentsBean eb = new EquipmentsBean();
			BeanConvertor.copyProperties(me, eb);
			if(me.getEnabled()!=null&&me.getEnabled().equals("0")){
					eb.setEnabled("禁用");
			}else {
				eb.setEnabled("启用");
			}
			if(me.getFixedAssetFlag()!=null&&me.getFixedAssetFlag().equals("0")){
				eb.setFixedAssetFlag("已入固");
			}else {
				eb.setFixedAssetFlag("未入固");
			}
			eb.setEqpTypeId(typeId);
			eb.setEqpTypeName(typeName);
			if(me.getMdWorkshop() != null){
				eb.setWorkShopId(me.getMdWorkshop().getId());
				eb.setWorkShopName(me.getMdWorkshop().getName());
			}
			equBeans.add(eb);
		}
		hql = "select count(*) from MdEquipment o where 1=1 and o.del=0 ";
		long total = equipmentsDao.queryTotal(hql.concat(param));
		return new DataGrid(equBeans,total);
	}

	@Override
	public EquipmentsBean getEquById(String id) throws Exception {
		MdEquipment equipments = equipmentsDao.findById(MdEquipment.class, id);
		EquipmentsBean equBean = BeanConvertor.copyProperties(equipments, EquipmentsBean.class);
		String typeId = equipments.getMdEqpType().getId();
		String typeName = equipments.getMdEqpType().getName();
		equBean.setEqpTypeId(typeId);
		equBean.setEqpTypeName(typeName);
		if(equipments.getMdWorkshop() != null){
			equBean.setWorkShopId(equipments.getMdWorkshop().getId());
			equBean.setWorkShopName(equipments.getMdWorkshop().getName());
		}
		
		if("0".equals(equBean.getFixedAssetFlag())) equBean.setFixedAssetFlag("已固入");
		
		if("1".equals(equBean.getFixedAssetFlag())) equBean.setFixedAssetFlag("未固入");
		
		return equBean;
		
	}

	@Override
	public void deleteEqu(String id) throws Exception {
		equipmentsDao.findById(MdEquipment.class, id).setDel("1");
	
	}

	@Override
	public List<Combobox> getAllEqpType(String id) throws Exception {
		List<MdEqpType> mdEqpType =  mdEqpTypeDaoI.query("from MdEqpType o where 1=1 and id<>? and o.del=0 and o.enable=1 order by o.id desc ", id);
		return BeanConvertor.copyList(mdEqpType,Combobox.class);
	}
	
	@Override
	public List<EquipmentsBean> queryAllEquipments() throws Exception {
		List<MdEquipment> equs =  equipmentsDao.query("from MdEquipment o  where o.del=0 and o.enabled=0 order by o.id desc");
		return BeanConvertor.copyList(equs,EquipmentsBean.class);
	}
	@Override
	public List<EquipmentsBean> queryAllEqpsForComboBox() {
		String hql = "from MdEquipment o  where o.del=0 and o.enabled=1 order by cast(o.equipmentCode as int) ";
		List<EquipmentsBean> equipmentsBeans = new ArrayList<EquipmentsBean>();
		for (MdEquipment  equipment : equipmentsDao.query(hql)) {
			EquipmentsBean e = new EquipmentsBean();
			e.setId(equipment.getId());
			e.setName(equipment.getEquipmentName());
			equipmentsBeans.add(e);
		}
		return equipmentsBeans;
	}
	@Override
	public List<EquipmentsBean> queryAllPackersForComboBox(int eType) {		
		try {
			if(eType==0){
				eType=2;
			}
			String hql = "from MdEquipment o where o.del=0 and o.mdEqpType.mdEqpCategory.code="+eType+" order by cast(o.equipmentCode as java.lang.Integer) asc ";
			List<MdEquipment> list = equipmentsDao.query(hql);
			List<EquipmentsBean> equipmentsBeans = new ArrayList<EquipmentsBean>();
			for (MdEquipment  equipment :list) {
				EquipmentsBean e = new EquipmentsBean();
				e.setId(equipment.getId());
				e.setName(equipment.getEquipmentName());
				equipmentsBeans.add(e);
			}
			return equipmentsBeans;
		} catch (Exception e) {
			//  TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能说明：初始化辅料
	 * 
	 * @author wanchanghuang
	 * @time 2015年8月10日17:08:59
	 * 
	 * */
	@Override
	public List<MatParamBean> getAllMdMatParamData() {
		String sql = "select mat,val from Md_Mat_Param ";
		List<MatParamBean> list = new ArrayList<MatParamBean>();
		List<?> ts=equipmentsDao.queryBySql(sql);
		for(Object o:ts){
			Object[] temp=(Object[]) o;
			MatParamBean b = new MatParamBean();
			try {
				b.setMat(temp[0].toString());
				b.setVal(Double.parseDouble(temp[1].toString()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			list.add(b);
		}
		return list;
	}

	
	@Override
	public List<Map<String, String>> querySysEqpType() {
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		String sql=" (select id,name from SYS_EQP_TYPE where del=0 and cid='8af2d43f4d938ab6014d948996e60003') "
		+" union all "
		+" (SELECT id,lubricant_name as name from EQM_LUBRICANT_MAINTAIN where del=0 ) "
		+" union all (select id,name from SYS_EQP_TYPE where del=0 and cid='8af2d43f4d938ab6014d94890f560002')";
		List<?> listt=equipmentsDao.queryBySql(sql);
		if(listt.size()>0){
			for(Object o:listt){
				Object[] temp=(Object[]) o;
			    map.put(temp[1].toString(), temp[0].toString());
			}
		}
		list.add(map);
		return list;
	}
	
	
	/**
	 * Excel导出
	 * 2015.9.9--张璐 
	 */
	@Override
	public HSSFWorkbook ExportExcelJBPP2(EquipmentsBean baoCJBean) throws Exception {
		Json json=new Json(); 
		HSSFWorkbook wb =null;
		ExportExcel ee = new ExportExcel(); 
		FileOutputStream fos;
		try {
			//th 当前开始行,当前结束行,一共多少列
			int[] thTables={1,2,18};
			List<String> th=new ArrayList<String>();
			//第1行;第1列开始 ~ 第18列结束 一共 跨到第1行
			th.add("1,1,18,1,设备主数据管理");
			th.add("2,1,1,1,设备编号");
			th.add("2,2,2,1,设备名称");
			th.add("2,3,3,1,设备描述");
			th.add("2,4,4,1,机型");
			th.add("2,5,5,1,车间");
			th.add("2,6,6,1,工序段");
			th.add("2,7,7,1,设备位置");
			th.add("2,8,8,1,额定车速");
			th.add("2,9,9,1,额定车速单位");
			th.add("2,10,10,1,台时产量");
			th.add("2,11,11,1,台时产量单位");
			th.add("2,12,12,1,是否启用");
			th.add("2,13,13,1,固定资产编号");
			th.add("2,14,14,1,出厂编号");
			th.add("2,15,15,1,批准文号");
			th.add("2,16,16,1,准运证编号");
			th.add("2,17,17,1,已入固");
			th.add("2,18,18,1,使用部门");
			PageParams pageParams = new PageParams();
			
			//获取List集合
			DataGrid dg=this.queryEqu(baoCJBean,pageParams);
			//方法集合，同表头一致
			String[] method=new String[]{"getEquipmentCode","getEquipmentName","getEquipmentDesc","getEqpTypeName","getWorkShopName","getWorkCenter","getEquipmentPosition","getRatedSpeed","getRateSpeedUnit","getYieId","getYieldUnit","getEnabled","getFixedAssetNum","getManufacturingNum","getApprovalNum","getNavicertNum","getFixedAssetFlag","getUsingDepartment"};
			//开始行
			int startLine=2;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,EquipmentsBean.class,dg.getRows());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

	@Override
	public void inputExeclAndReadWrite(List<MdEquipment> list) {
		for (int i = 0; i < list.size(); i++) {
			MdEquipment equBean=list.get(i);
			String sql="SELECT EQUIPMENT_CODE from MD_EQUIPMENT where del=0 and EQUIPMENT_CODE='"+equBean.getEquipmentCode()+"'";
			List l=equipmentsDao.queryBySql(sql);
			if (l!=null&&l.size()>0) {
				continue;
			}else{
//				if (StringUtil.notNull(equBean.getId())) {
//					equBean.setId(equBean.getId());
//				}
				equBean.setDel("0");
				equBean.setCreateTime(new java.util.Date());
				equipmentsDao.saveOrUpdate(equBean);
			}
		}
	}
	
}
