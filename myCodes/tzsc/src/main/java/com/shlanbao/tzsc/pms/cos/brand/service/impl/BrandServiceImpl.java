package com.shlanbao.tzsc.pms.cos.brand.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.CosIncompleteCoefficientDaoI;
import com.shlanbao.tzsc.base.dao.CosIncompleteStandardDaoI;
import com.shlanbao.tzsc.base.dao.CosPartWeightDaoI;
import com.shlanbao.tzsc.base.dao.EqmipmentsDaoI;
import com.shlanbao.tzsc.base.dao.MdMatTypeDaoI;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.cos.brand.beans.BrandBean;
import com.shlanbao.tzsc.pms.cos.brand.beans.CostsExamine;
import com.shlanbao.tzsc.pms.cos.brand.beans.SeachBean;
import com.shlanbao.tzsc.pms.cos.brand.service.BrandServiceI;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerGroup;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.SystemConstant;
/**
 * @ClassName: BrandServiceImpl 
 * @Description: 成本考核Service实现类
 * @author luo
 * @date 2015年1月21日 下午3:21:17 
 *
 */
@Service
public class BrandServiceImpl extends BaseService implements BrandServiceI {

	//滤棒，卷烟纸，接装纸 物料类型ID
	public static final String LB="4";
	public static final String JYZ="2";
	public static final String JZZ="3";
	//包装机：小盒商标纸，条盒商标纸，小盒烟膜，条盒烟膜，内衬纸；  物料类型ID
	public static final String XHSBZ="7";
	public static final String THSBZ="8";
	public static final String XHYM="5";
	public static final String THYM="6";
	public static final String NCZ="9";
	//成型：盘纸
	public static final String PZ="13";
	
	public static final boolean isCalculate=false;//是否计算消耗为0的奖罚金额
	
	@Autowired
	private BaseDaoI<Object> baseDao;
	
	@Autowired
	private MdMatTypeDaoI mdMatTypeDao; 
	
	@Autowired
	private EqmipmentsDaoI equipmentsDao;
	
	@Autowired
	private CosIncompleteStandardDaoI cosIncompleteStandardDao;
	
	@Autowired
	private CosPartWeightDaoI cosPartWeightDao;
	
	@Autowired
	private CosIncompleteCoefficientDaoI incompleteCoefficientDao;
	
	//残烟丝计算系数
	private Hashtable<String,Double> incompleteCoefficient;
	
	public Hashtable<String, Double> getIncompleteCoefficient() {
		if(incompleteCoefficient==null||incompleteCoefficient.keySet().size()<1){
			return incompleteCoefficientDao.queryModulusHT();
		}
		return incompleteCoefficient;
	}

	//成型机综合成本管理查询
	@Override
	public DataGrid queryBrand_chengxing(boolean isShift,boolean isWorkorder,SeachBean seachBean, PageParams pageParams) throws Exception {
		//获取产量和消耗量
		List<Object[]> list = getProduceByTime(isShift,isWorkorder,seachBean);
		//获取辅料的上下限值、标准值、单价、考核单价信息
		Hashtable<String,String[]> ht=hqlAssembly();
		//根据产量、消耗和标准获取BrandBean
		List<BrandBean> beans = getBradBeansByEqpType(list,ht);
		//BrandBean转CostsExamine
		List<CostsExamine> lists=convertBean(beans);
		//return DataGrid
		return new DataGrid(lists,Long.valueOf(lists.size()));
	}
	
	/**
	* @Title: getBradBeansByEqpType 
	* @Description: 根据产量、消耗量和标准获取BrandBean集合 
	* @param  list 产量和消耗量
	* @param  ht   标准  key=牌号+机型+辅料  value=String[]
	* @return List<BrandBean>    返回类型 
	* @throws
	 */
	public List<BrandBean> getBradBeansByEqpType(List<Object[]> list,Hashtable<String,String[]> ht){
		List<BrandBean> beans = new ArrayList<BrandBean>();
		for (Object[] objects : list) {
			BrandBean bb=new BrandBean();
			//牌号+机型+辅料
			String key=String.valueOf(objects[0])+String.valueOf(objects[2])+String.valueOf(objects[7]);
			//获取标准
			String[] standard=ht.get(key);
			//object 转 string
			String[] temp = new String[objects.length];
			for(int i=0;i<objects.length;i++){
				temp[i]=String.valueOf(objects[i]);
			}
			// 牌号 0、设备名称1、设备机型2、工单号3、批次号4、工单日期5、总产量6、辅料的名称7、消耗量8
			//辅料单位9、工单类型10、班组11、辅料类型12、牌号单位13、辅料类型ID14
			bb.setEqdType(temp[1]);
			bb.setMatName(temp[0]);
			bb.setBatch(temp[4]);
			bb.setQty(pasetDouble(temp[6]));
			bb.setFmatName(temp[7]);
			bb.setFqty(pasetDouble(temp[8]));
			bb.setFunit(temp[9]);
			bb.setTeam(temp[11]);
			bb.setTeamName(temp[11]);
			if(StringUtil.notNull(temp[5])&&temp[5].length()>12){
				bb.setTime(temp[5].substring(0, 11));
			}
			bb.setWorkOrderId(temp[3]);
			bb.setFmatType(temp[12]);
			bb.setFmatTypeID(temp[14]);
			bb.setEqdID(temp[15]);
			bb.setEqdCode(temp[16]);
			bb.setShiftId(temp[17]);
			bb.setShiftName(temp[18]);
			bb.setExcluding(pasetDouble(temp[19]));
			Hashtable<String,String[]> h=new Hashtable<String,String[]>();
			//辅料类型
			String k=temp[12];
			//单耗/单位/标准单耗/单位/消耗量/单价/总成本/单箱成本/奖罚单价/奖罚金额
			String[] t=new String[10];
			if(bb.getQty()>0)
				t[0]=String.valueOf(MathUtil.roundHalfUp((bb.getFqty()*1.00)/bb.getQty(),2));
			t[1]=temp[9]+"/"+temp[13];
			if(standard!=null){
				t[2]=standard[8];
				t[3]=standard[11];
				t[5]=standard[6];
				t[6]=String.valueOf(pasetDouble(standard[6])*bb.getFqty());
				if(bb.getQty()>0)
					t[7]=String.valueOf(MathUtil.roundHalfUp(bb.getFqty()*pasetDouble(standard[6])*1.00/bb.getQty(),2));
				t[8]=standard[7];
				//辅料奖罚金额=(定额单耗*产量-消耗量)*奖罚金额
				
				if(isCalculate||bb.getFqty()>0){
					t[9]=String.valueOf(MathUtil.roundHalfUp((pasetDouble(standard[8])*bb.getQty()-bb.getFqty())*pasetDouble(standard[7]),2));
				}
			}
			t[4]=String.valueOf(bb.getFqty());
			h.put(k,t);
			bb.setHt(h);
			beans.add(bb);
		}
		return beans;
	}
	//卷包机组
	@Override
	public DataGrid queryBrandCos_juanbao(boolean isDisabled,boolean isWorkorder,SeachBean seachBean, PageParams pageParams,List<RollerPackerGroup> rollerPackerGroup) throws Exception {
		//获取设备集合
		List<MdEquipment> equipments = equipmentsDao.query("from MdEquipment o where o.mdWorkshop.id='1'");
		Hashtable<String,String> equipmentHT=new Hashtable<String,String>();
		for(MdEquipment e:equipments){
			equipmentHT.put(e.getEquipmentCode(),e.getEquipmentName());
		}
		//卷接机数据集合
		seachBean.seteType("1");
		DataGrid dg=queryBrand_chengxing(true,isWorkorder,seachBean,new PageParams());
		List<CostsExamine> costs1=dg.getRows();
		//包装机数据集合
		seachBean.seteType("2");
		DataGrid dg2=queryBrand_chengxing(true,isWorkorder,seachBean,new PageParams());
		List<CostsExamine> costs2=dg2.getRows();
		//bean 转 Hashtable 
		Hashtable<String,CostsExamine> ht=new Hashtable<String,CostsExamine>();
		for(CostsExamine c:costs2){
			//key=设备+牌号+班组+班次
			String key=c.getEqdType()+c.getMatName()+c.getTeam()+c.getShiftName();
			ht.put(key,c);
		}
		//key 卷接机 code value RollerPackerGroup
		Hashtable<String,RollerPackerGroup> roolerHT=new Hashtable<String,RollerPackerGroup>();
		//卷接机和包装机的对应关系
		for(RollerPackerGroup roller: rollerPackerGroup){
			roolerHT.put(roller.getrCode(), roller);
		}
		List<CostsExamine> returnBean=new ArrayList<CostsExamine>();
		for(CostsExamine c:costs1){
			//根据卷接机设备code获取包装机设备code
			RollerPackerGroup pCode=roolerHT.get(c.getEqdCode());
			String key="";
			if(pCode!=null){
				//根据设备code查找设备名称
				String equName=equipmentHT.get(pCode.getpCode());
				c.setEqdType(pCode.getGroupName());
				if(equName==null){
					log.error("根据卷接机设备Code"+c.getEqdID()+"/"+c.getEqdType()+",获取包装机Code异常,group.xml配置信息错误。");
				}
				key+=equName;
			}else{
				log.error("根据卷接机设备Code"+c.getEqdID()+"/"+c.getEqdType()+",不能获取包装机Code,group.xml配置信息错误。");
			}
			key+=c.getMatName()+c.getTeam()+c.getShiftName();
			//根据key值取出 包装机的数据
			CostsExamine bean=ht.get(key);
			if(bean!=null){
				//把包装机的数据bean保存到卷烟机bean
				c=saveToBean(isDisabled,c,bean);
			}else{
				c.setJqty(c.getQty());//卷烟机产量
				c.setJunit(c.getUnit());//卷烟机产量单位
				//获取不到包装机时，机组产量为0
				c.setQty(0);//包装机产量
				c.setUnit("");//包装机单位
			}
			returnBean.add(c);
		}
		return new DataGrid(returnBean,Long.parseLong(returnBean.size()+""));
	}
	/**
	 * @description 查询产量和消耗
	 * @param bean
	 * @param isWorkorder 是否显示工单
	 * @param isShift 是否显示班次
	 * @return
	 */
	private List<Object[]> getProduceByTime(boolean isShift,boolean isWorkorder,SeachBean seachBean){
		//
		StringBuffer hql = new StringBuffer();
		hql.append("select o.schStatOutput.schWorkorder.mdMat.name,");// 牌号 0
		hql.append(" o.schStatOutput.schWorkorder.mdEquipment.equipmentName,");//设备名称1
		hql.append(" o.schStatOutput.schWorkorder.mdEquipment.mdEqpType.name,");//设备机型2
		if(isWorkorder){
			hql.append(" o.schStatOutput.schWorkorder.code,");//工单号3
			hql.append(" o.schStatOutput.schWorkorder.bth,");//批次号4
			hql.append(" o.schStatOutput.schWorkorder.date,");//工单日期5
		}else{
			hql.append(" '工单',");//工单号3
			hql.append(" '批次',");//批次号4
			hql.append(" '工单日期',");//工单日期5
		}
		hql.append(" sum(o.schStatOutput.qty-o.schStatOutput.badQty), ");   //总产量6
		hql.append(" o.mdMat.name, ");   //辅料的名称7
		hql.append(" sum(o.qty), ");   //消耗量8
		hql.append(" o.mdUnit.name ");   //单位9
		hql.append(",o.schStatOutput.schWorkorder.type ");//工单类型  1卷烟机工单、2包装机工单、3封箱机工单、4成型机工单10
		hql.append(",o.schStatOutput.schWorkorder.mdTeam.name ");//班组11
		hql.append(",o.mdMat.mdMatType.name ");   //辅料类型12
		hql.append(",o.schStatOutput.schWorkorder.mdUnit.name ");   //牌号单位13
		hql.append(",o.mdMat.mdMatType.id ");   //辅料类型ID 14
		hql.append(",o.schStatOutput.schWorkorder.mdEquipment.id ");//设备ID15
		hql.append(",o.schStatOutput.schWorkorder.mdEquipment.equipmentCode ");//设备codeID16
		if(isShift){
			hql.append(",o.schStatOutput.schWorkorder.mdShift.id ");//班次ID 17
			hql.append(",o.schStatOutput.schWorkorder.mdShift.name ");//班次名称 18
		}else{
			hql.append(",'班次ID' ");//班次ID 
			hql.append(",'班次名称' ");//班次名称
		}
		hql.append(",sum(o.schStatOutput.badQty) ");//剔除量19
		hql.append(" from SchStatInput o ");
		hql.append("where 1=1 and o.schStatOutput.del=0 ");//卷烟机和包装机
		if(StringUtil.notNull(seachBean.getMdMatId())){
			hql.append("and o.schStatOutput.schWorkorder.mdMat.id = '"+seachBean.getMdMatId()+"' ");
		}
		//机组code,可多匹配
		if(StringUtil.notNull(seachBean.geteName())){
			if(StringUtil.notNull(parseString(seachBean.geteName()))){
				hql.append("and o.schStatOutput.schWorkorder.mdEquipment.id in ("+parseString(seachBean.geteName())+") ");
			}
		}
		//车间
		if(StringUtil.notNull(seachBean.getWorkshop())){
			hql.append("and o.schStatOutput.schWorkorder.mdEquipment.mdWorkshop ='"+seachBean.getWorkshop()+"' ");
		}
		//开始时间
		if(StringUtil.notNull(seachBean.getStartTime())){
			hql.append("and o.schStatOutput.schWorkorder.date >'"+seachBean.getStartTime()+"' ");
		}
		//结束时间
		if(StringUtil.notNull(seachBean.getEndTime())){
				hql.append("and o.schStatOutput.schWorkorder.date <'"+seachBean.getEndTime()+"' ");
		}
		//班组
		if(StringUtil.notNull(seachBean.getTeam())){
			if(StringUtil.notNull(parseString(seachBean.getTeam()))){
				hql.append("and o.schStatOutput.schWorkorder.mdTeam.id in ("+parseString(seachBean.getTeam())+") ");
			}
		}
		//卷接工单、包装工单、成型工单
		if(StringUtil.notNull(seachBean.geteType())){
			hql.append("and o.schStatOutput.schWorkorder.type = '"+seachBean.geteType()+"' ");
		}
		//hql.append("order by o.schStatOutput.schWorkorder.mdEquipment.mdEqpType.name,o.schStatOutput.schWorkorder.mdMat.name ");
		hql.append("group by o.schStatOutput.schWorkorder.mdMat.name ");
		if(isWorkorder){
			hql.append(",o.schStatOutput.schWorkorder.code ");//工单号3
			hql.append(",o.schStatOutput.schWorkorder.bth ");//批次号4
			hql.append(",o.schStatOutput.schWorkorder.date ");//工单日期5
		}
		if(isShift){
			hql.append(",o.schStatOutput.schWorkorder.mdShift.id ");//班次ID 17
			hql.append(",o.schStatOutput.schWorkorder.mdShift.name ");//班次名称 18
		}
		hql.append(",o.mdMat.name,o.mdUnit.name,o.schStatOutput.schWorkorder.type ");
		hql.append(",o.schStatOutput.schWorkorder.mdEquipment.equipmentName,o.schStatOutput.schWorkorder.mdEquipment.mdEqpType.name ");
		hql.append(",o.schStatOutput.schWorkorder.mdTeam.name,o.mdMat.mdMatType.name,o.schStatOutput.schWorkorder.mdUnit.name ");
		hql.append(",o.mdMat.mdMatType.id,o.schStatOutput.schWorkorder.mdEquipment.id ");
		hql.append(",o.schStatOutput.schWorkorder.mdEquipment.equipmentCode ");
		return baseDao.queryObjectArray(String.valueOf(hql));
	}
	//Object to double
	private double pasetDouble(Object value){
		try{
			return Double.parseDouble(value+"");
		}catch(Exception e){
			return 0.0;
		}
	}
	//数据转换 1,2,3,4 转换成 '1','2','3','4'
	private String parseString(String value){
		String[] obj=value.split(",");
		String temp="";
		for(String o:obj){
			if(o.equals("")){continue;} 
			temp+=",'"+o+"'";
		}
		if(temp.length()<2){return "";}
		return temp.substring(1, temp.length());
	}
	
	/**
	 * 卷烟机辅料 单耗上下限值、单价、奖罚单价
	 * @param workOrderId 工单id
	 * @param pageParams
	 * @return
	 * @throws Exception 
	 * 1、牌号名称
	 * 2、机型ID
	 * 3、设备名称
	 * 4、机型
	 * 5、辅料名称
	 * 6、辅料类型
	 * 7、辅料单价
	 * 8、奖罚单价
	 * 9、辅料单耗标准值
	 * 10、辅料单耗上限
	 * 11、辅料单耗下限
	 * 12、单耗单位
	 */
	private Hashtable<String ,String[]> hqlAssembly() throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append("select cosp.cid.mdMat.name ");//牌号名称0
		hql.append(",cosp.cid.eqpType.id ");//机型ID1
		hql.append(",eq.equipmentName ");//设备名称2
		hql.append(",cosp.cid.eqpType.name ");//机型3
		hql.append(",cosp.mat.name ");//辅料名称4
		hql.append(",cosp.mat.mdMatType.code ");//辅料类型5
		hql.append(",mpm.matPrice ");//辅料单价6
		hql.append(",cosp.unitprice ");//奖罚单价7
		hql.append(",cosp.val ");//辅料单耗标准值8
		hql.append(",cosp.uval ");//辅料单耗上限9
		hql.append(",cosp.lval ");//辅料单耗下限10
		hql.append(",cosp.stdID.des ");//单耗单位11
		hql.append("from CosMatAssessParam cosp ");
		hql.append(", MdEquipment eq ");
		hql.append(", CosMatPriceMaintain mpm ");
		hql.append("where cosp.cid.eqpType.id =eq.mdEqpType.id ");
		hql.append("and mpm.mdMat.id=cosp.mat.id ");
		hql.append("and cosp.cid.status='生效' ");
		List<Object[]> list = baseDao.queryObjectArray(String.valueOf(hql));
		Hashtable<String ,String[]> ht=new Hashtable<String,String[]>();
		for(Object[] objects : list){
			String[] temp = new String[objects.length];
			for(int i=0;i<temp.length;i++){
				temp[i]=String.valueOf(objects[i]);
			}
			//key=牌号+机型+辅料
			ht.put(temp[0]+temp[3]+temp[4], temp);
		}
		return ht;
	}
	//bean转换
	public List<CostsExamine> convertBean(List<BrandBean> list){
		//key牌号 value Bean
		Hashtable<String,CostsExamine> h=new Hashtable<String,CostsExamine>();
		
		for(BrandBean bb:list){
			CostsExamine costs=new CostsExamine();
			//工单+批次+牌号+机组+班组+班次
			String key=bb.getWorkOrderId()+bb.getBatch()+bb.getMatName()+bb.getEqdType()+bb.getTeamName()+bb.getShiftName();
			if(h.get(key)!=null){
				costs=h.get(key);
			}else{
				costs.setEqdID(bb.getEqdID());
				costs.setEqdType(bb.getEqdType());
				costs.setMatName(bb.getMatName());
				costs.setQty(bb.getQty());
				costs.setUnit(bb.getUnit());
				costs.setTeam(bb.getTeam());
				costs.setTeamName(bb.getTeamName());
				costs.setTime(bb.getTime());
				costs.setWorkOrderId(bb.getWorkOrderId());
				costs.setBatch(bb.getBatch());
				costs.setEqdCode(bb.getEqdCode());
				costs.setShiftID(bb.getShiftId());
				costs.setShiftName(bb.getShiftName());
				costs.setExcluding_B(bb.getExcluding());
			}
			Hashtable<String,String[]> ht=bb.getHt();
			//根据辅料类型 获取单耗成本信息
			String[] standard=ht.get(bb.getFmatType());
			if(standard==null){
				h.put(bb.getWorkOrderId()+bb.getBatch(),costs);
				continue;
			}		
			//单耗/单位/标准单耗/单位/消耗量/单价/总成本
			if(bb.getFmatTypeID().equals(LB)){
				costs.setConsumption1(pasetDouble(standard[0]));
				costs.setUnit1(standard[1]);
				costs.setConsumStand1(pasetDouble(standard[2]));
				costs.setCunit1(standard[3]);
				costs.setConsumQty1(pasetDouble(standard[4]));
				costs.setUnitPrice1(pasetDouble(standard[5]));
				costs.setCountPrice1(pasetDouble(standard[6]));
				costs.setSingleCost1(pasetDouble(standard[7]));
				costs.setAward1(pasetDouble(standard[8]));
				costs.setAwardCount1(pasetDouble(standard[9]));
				costs.setFname1(bb.getFmatName());
				costs.setFunit1(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(JYZ)){
				costs.setConsumption2(pasetDouble(standard[0]));
				costs.setUnit2(standard[1]);
				costs.setConsumStand2(pasetDouble(standard[2]));
				costs.setCunit2(standard[3]);
				costs.setConsumQty2(pasetDouble(standard[4]));
				costs.setUnitPrice2(pasetDouble(standard[5]));
				costs.setCountPrice2(pasetDouble(standard[6]));
				costs.setSingleCost2(pasetDouble(standard[7]));
				costs.setAward2(pasetDouble(standard[8]));
				costs.setAwardCount2(pasetDouble(standard[9]));
				costs.setFname2(bb.getFmatName());
				costs.setFunit2(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(JZZ)){
				costs.setConsumption3(pasetDouble(standard[0]));
				costs.setUnit3(standard[1]);
				costs.setConsumStand3(pasetDouble(standard[2]));
				costs.setCunit3(standard[3]);
				costs.setConsumQty3(pasetDouble(standard[4]));
				costs.setUnitPrice3(pasetDouble(standard[5]));
				costs.setCountPrice3(pasetDouble(standard[6]));
				costs.setSingleCost3(pasetDouble(standard[7]));
				costs.setAward3(pasetDouble(standard[8]));
				costs.setAwardCount3(pasetDouble(standard[9]));
				costs.setFname3(bb.getFmatName());
				costs.setFunit3(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(XHSBZ)){
				costs.setConsumption4(pasetDouble(standard[0]));
				costs.setUnit4(standard[1]);
				costs.setConsumStand4(pasetDouble(standard[2]));
				costs.setCunit4(standard[3]);
				costs.setConsumQty4(pasetDouble(standard[4]));
				costs.setUnitPrice4(pasetDouble(standard[5]));
				costs.setCountPrice4(pasetDouble(standard[6]));
				costs.setSingleCost4(pasetDouble(standard[7]));
				costs.setAward4(pasetDouble(standard[8]));
				costs.setAwardCount4(pasetDouble(standard[9]));
				costs.setFname4(bb.getFmatName());
				costs.setFunit4(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(THSBZ)){
				costs.setConsumption5(pasetDouble(standard[0]));
				costs.setUnit5(standard[1]);
				costs.setConsumStand5(pasetDouble(standard[2]));
				costs.setCunit5(standard[3]);
				costs.setConsumQty5(pasetDouble(standard[4]));
				costs.setUnitPrice5(pasetDouble(standard[5]));
				costs.setCountPrice5(pasetDouble(standard[6]));
				costs.setSingleCost5(pasetDouble(standard[7]));
				costs.setAward5(pasetDouble(standard[8]));
				costs.setAwardCount5(pasetDouble(standard[9]));
				costs.setFname5(bb.getFmatName());
				costs.setFunit5(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(XHYM)){
				costs.setConsumption6(pasetDouble(standard[0]));
				costs.setUnit6(standard[1]);
				costs.setConsumStand6(pasetDouble(standard[2]));
				costs.setCunit6(standard[3]);
				costs.setConsumQty6(pasetDouble(standard[4]));
				costs.setUnitPrice6(pasetDouble(standard[5]));
				costs.setCountPrice6(pasetDouble(standard[6]));
				costs.setSingleCost6(pasetDouble(standard[7]));
				costs.setAward6(pasetDouble(standard[8]));
				costs.setAwardCount6(pasetDouble(standard[9]));
				costs.setFname6(bb.getFmatName());
				costs.setFunit6(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(THYM)){
				costs.setConsumption7(pasetDouble(standard[0]));
				costs.setUnit7(standard[1]);
				costs.setConsumStand7(pasetDouble(standard[2]));
				costs.setCunit7(standard[3]);
				costs.setConsumQty7(pasetDouble(standard[4]));
				costs.setUnitPrice7(pasetDouble(standard[5]));
				costs.setCountPrice7(pasetDouble(standard[6]));
				costs.setSingleCost7(pasetDouble(standard[7]));
				costs.setAward7(pasetDouble(standard[8]));
				costs.setAwardCount7(pasetDouble(standard[9]));
				costs.setFname7(bb.getFmatName());
				costs.setFunit7(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(NCZ)){
				costs.setConsumption8(pasetDouble(standard[0]));
				costs.setUnit8(standard[1]);
				costs.setConsumStand8(pasetDouble(standard[2]));
				costs.setCunit8(standard[3]);
				costs.setConsumQty8(pasetDouble(standard[4]));
				costs.setUnitPrice8(pasetDouble(standard[5]));
				costs.setCountPrice8(pasetDouble(standard[6]));
				costs.setSingleCost8(pasetDouble(standard[7]));
				costs.setAward8(pasetDouble(standard[8]));
				costs.setAwardCount8(pasetDouble(standard[9]));
				costs.setFname8(bb.getFmatName());
				costs.setFunit8(bb.getFunit());
			}
			if(bb.getFmatTypeID().equals(PZ)){
				costs.setConsumption9(pasetDouble(standard[0]));
				costs.setUnit9(standard[1]);
				costs.setConsumStand9(pasetDouble(standard[2]));
				costs.setCunit9(standard[3]);
				costs.setConsumQty9(pasetDouble(standard[4]));
				costs.setUnitPrice9(pasetDouble(standard[5]));
				costs.setCountPrice9(pasetDouble(standard[6]));
				costs.setSingleCost9(pasetDouble(standard[7]));
				costs.setAward9(pasetDouble(standard[8]));
				costs.setAwardCount9(pasetDouble(standard[9]));
				costs.setFname9(bb.getFmatName());
				costs.setFunit9(bb.getFunit());
			}
			h.put(key,costs);
		}
		List<CostsExamine> costsList=new ArrayList<CostsExamine>();
		for(Iterator itr = h.keySet().iterator(); itr.hasNext();){
			String key = (String) itr.next();
			CostsExamine value = h.get(key);
			if(value!=null){
				costsList.add(value);
			}
		}
		return costsList;
	}
	/**
	* @Title: saveToBean 
	* @Description: costs2部分熟悉熟悉添加到costs中
	* @param  isDisabled 是否统计残烟量
	* @param  costs output
	* @param  costs2 input
	* @return CostsExamine    返回类型 
	* @throws
	 */
	private CostsExamine saveToBean(boolean isDisabled,CostsExamine costs,CostsExamine costs2){
		
		costs.setExcluding_J(costs.getExcluding_B());
		costs.setExcluding_B(costs2.getExcluding_B());
		costs.setJqty(costs.getQty());//卷烟机产量
		costs.setJunit(costs.getUnit());//卷烟机产量单位
		costs.setQty(costs2.getQty());//包装机产量
		costs.setUnit(costs2.getUnit());//包装机单位
		if(isDisabled){
			if(StringUtil.notNull(costs.getMatName())){
				//根据牌号获取单支重量
				double weightMat=cosPartWeightDao.queryWeightByMatCode(costs.getMatName());
				if(weightMat==0.0){
					log.error("成本考核统计时，获取牌号“"+costs.getMatName()+"”烟支重量失败，请查找烟支重量设置界面确认。");
				}else{
					//根据班次ID和设备ID获取储烟量， 单位：支
					double f=cosIncompleteStandardDao.queryBeanByShiftAndEqpType(costs.getShiftID(), costs.getEqdID());
					if(f==0.0){
						log.error("成本考核统计时，根据班次“"+costs.getShiftID()+"”，设备ID“"+costs.getEqdID()+"”获取储烟量失败，请查找机台储烟量设置界面确认。");
					}
					//残烟量=卷烟机烟支总数 +综合残烟量-包装机产量-包装机剔除产量   ，单位：支
					double disabled=costs.getJqty()*SystemConstant.BOX_CIGARETTE_NUMBER+f-costs.getQty()*SystemConstant.BOX_CIGARETTE_NUMBER;
					//残烟量 公斤=支*单支重量
					disabled=disabled*weightMat;
					costs.setDisabled(disabled);
					//机台残烟率=（卷烟机烟支总数+综合残烟量-包装机产量）*烟支重量/包装机产量 
					//2015年1月22日09:01:36 此处按照设计文档来，但是 公斤除以箱 ，还需与客户确认或者现场进行调整
					if(costs.getQty()!=0.0){
						double disabledRate = MathUtil.roundHalfUp(disabled/costs.getQty(),2);
						costs.setDisabledRate(disabledRate);
						//残烟丝考核金额
						Hashtable<String,Double> ht=getIncompleteCoefficient();
						double rated=ht.get(SystemConstant.RATED_SINGLE_DISABLED_CODE);
						double award=ht.get(SystemConstant.RATED_SINGLE_DISABLED_AWARD_CODE);
						double punish=ht.get(SystemConstant.RATED_SINGLE_DISABLED_PUNISH_CODE);
						if(rated==0.0){
							log.error("根据编码“"+SystemConstant.RATED_SINGLE_DISABLED_CODE+"”获取残烟丝考核标准失败。");
						}
						if(award==0.0){
							log.error("根据编码“"+SystemConstant.RATED_SINGLE_DISABLED_AWARD_CODE+"”获取残烟丝考核奖励金额失败。");
						}
						if(punish==0.0){
							log.error("根据编码“"+SystemConstant.RATED_SINGLE_DISABLED_PUNISH_CODE+"”获取残烟丝考核处罚金额失败。");
						}
						//单箱残烟标准
						costs.setDisabledStandard(rated);
						//残烟一公斤奖励标准
						costs.setRewardStandard(award);
						//残烟一公斤处罚标准
						costs.setPunishStandard(punish);
						if(costs.getQty()!=0){
							//单箱残烟量=残烟量/产量
							costs.setSingleDisabled(MathUtil.roundHalfUp(disabled/costs.getQty(),2));
						}
						//如果单箱残烟量小于单箱残烟量标准值，则奖励，否则处罚。
						if(rated-costs.getSingleDisabled()>0){
							costs.setDisabledCheck(MathUtil.roundHalfUp((rated-costs.getSingleDisabled())*costs.getQty()*award,2));
						}else{
							costs.setDisabledCheck(MathUtil.roundHalfUp((rated-costs.getSingleDisabled())*costs.getQty()*punish,2));
						}
						//卷接机残烟率=（卷烟机烟支总数+综合残烟量-包装机产量-包装机剔除量）*烟支重量 /包装机产量
						costs.setDisabledRate_J(costs.getDisabledCheck());
						//包装机残烟率=包装机剔除量*烟支重量/包装机产量
						costs.setDisabledRate_B(MathUtil.roundHalfUp(costs.getExcluding_B()*weightMat/costs.getQty(),2));
						
					}else{
						log.error("成本考核中，产量为0。");
						costs.setDisabledRate(0.0);
					}
				}
			}else{
				log.error("成本考核统计时，牌号名称不存在。");
			}
		}
		
		costs.setConsumption4(costs2.getConsumption4());
		costs.setUnit4(costs2.getUnit4());
		costs.setConsumStand4(costs2.getConsumStand4());
		costs.setCunit4(costs2.getCunit4());
		costs.setConsumQty4(costs2.getConsumQty4());
		costs.setUnitPrice4(costs2.getUnitPrice4());
		costs.setCountPrice4(costs2.getCountPrice4());
		costs.setSingleCost4(costs2.getSingleCost4());
		costs.setAward4(costs2.getAward4());
		costs.setAwardCount4(costs2.getAwardCount4());
		costs.setFname4(costs2.getFname4());
		costs.setFunit4(costs2.getFunit4());
	
		costs.setConsumption5(costs2.getConsumption5());
		costs.setUnit5(costs2.getUnit5());
		costs.setConsumStand5(costs2.getConsumStand5());
		costs.setCunit5(costs2.getCunit5());
		costs.setConsumQty5(costs2.getConsumQty5());
		costs.setUnitPrice5(costs2.getUnitPrice5());
		costs.setCountPrice5(costs2.getCountPrice5());
		costs.setSingleCost5(costs2.getSingleCost5());
		costs.setAward5(costs2.getAward5());
		costs.setAwardCount5(costs2.getAwardCount5());
		costs.setFname5(costs2.getFname5());
		costs.setFunit5(costs2.getFunit5());
	
		costs.setConsumption6(costs2.getConsumption6());
		costs.setUnit6(costs2.getUnit6());
		costs.setConsumStand6(costs2.getConsumStand6());
		costs.setCunit6(costs2.getCunit6());
		costs.setConsumQty6(costs2.getConsumQty6());
		costs.setUnitPrice6(costs2.getUnitPrice6());
		costs.setCountPrice6(costs2.getCountPrice6());
		costs.setSingleCost6(costs2.getSingleCost6());
		costs.setAward6(costs2.getAward6());
		costs.setAwardCount6(costs2.getAwardCount6());
		costs.setFname6(costs2.getFname6());
		costs.setFunit6(costs2.getFunit6());
	
		costs.setConsumption7(costs2.getConsumption7());
		costs.setUnit7(costs2.getUnit7());
		costs.setConsumStand7(costs2.getConsumStand7());
		costs.setCunit7(costs2.getCunit7());
		costs.setConsumQty7(costs2.getConsumQty7());
		costs.setUnitPrice7(costs2.getUnitPrice7());
		costs.setCountPrice7(costs2.getCountPrice7());
		costs.setSingleCost7(costs2.getSingleCost7());
		costs.setAward7(costs2.getAward7());
		costs.setAwardCount7(costs2.getAwardCount7());
		costs.setFname7(costs2.getFname7());
		costs.setFunit7(costs2.getFunit7());
	
		costs.setConsumption8(costs2.getConsumption8());
		costs.setUnit8(costs2.getUnit8());
		costs.setConsumStand8(costs2.getConsumStand8());
		costs.setCunit8(costs2.getCunit8());
		costs.setConsumQty8(costs2.getConsumQty8());
		costs.setUnitPrice8(costs2.getUnitPrice8());
		costs.setCountPrice8(costs2.getCountPrice8());
		costs.setSingleCost8(costs2.getSingleCost8());
		costs.setAward8(costs2.getAward8());
		costs.setAwardCount8(costs2.getAwardCount8());
		costs.setFname8(costs2.getFname8());
		costs.setFunit8(costs2.getFunit8());
		
		return costs;
	}
}
