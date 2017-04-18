package com.shlanbao.tzsc.wct.ctm.machine.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.CosMatPriceMaintainDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.ctm.machine.beans.CostManager;
import com.shlanbao.tzsc.wct.ctm.machine.service.WctCostManagerServiceI;
import com.shlanbao.tzsc.wct.sch.stat.beans.EqpRuntime;
@Service
public class WctCostManagerServiceImpl extends BaseService implements WctCostManagerServiceI {
	@Autowired
	private BaseDaoI<Object> baseDao; 
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Autowired
	private CosMatPriceMaintainDaoI cosMatPriceMaintainDao;
	//辅料单价、奖罚单价信息
	private Hashtable<String ,String[]> ht;
	
	public Hashtable<String, String[]> getHt() throws Exception {
		if(ht!=null&&ht.size()>0){
			return ht;
		}else{
			return hqlAssembly();
		}
	}

	public void setHt(Hashtable<String, String[]> ht) {
		this.ht = ht; 
	}

	@Override
	public CostManager getDataSource(int ecode,String orderNumber){
		CostManager cost=new CostManager();
		NeedData needData=NeedData.getInstance();
		EquipmentData equipmentData=needData.getEquipmentData(ecode);
		if(equipmentData==null){
			return new CostManager();
		}
		Hashtable<String,String[]> h=getMaintainList(orderNumber);
		String qty=equipmentData.getVal("7");
		cost.setQty(GetValueUtil.getDoubleValue(qty));
		EquipmentData matData=needData.getEquipmentData(ecode*1000);
		//根据定义的设备号大小 取出对应的辅料内容
		if(ecode<30){
			//盘纸消耗量
			String f1=matData.getVal("2");
			cost.setJuanyanzhiIn(GetValueUtil.getDoubleValue(f1));
			cost.setJuanyanzhiD(getValueByMatType(h, "2", GetValueUtil.getDoubleValue(f1)));
			//水松纸消耗量
			String f2=matData.getVal("3");
			cost.setShuisongzhiIn(GetValueUtil.getDoubleValue(f2));
			cost.setShuisongzhiD(getValueByMatType(h, "3", GetValueUtil.getDoubleValue(f2)));
			//滤棒消耗量
			String f3=matData.getVal("4");
			cost.setLvbangIn(GetValueUtil.getDoubleValue(f3));
			cost.setLvbangD(getValueByMatType(h, "4", GetValueUtil.getDoubleValue(f3)));
		}else if(ecode<60){
		//小盒烟膜、条盒烟膜、小盒纸、条盒纸、内衬纸
		
			String f4=matData.getVal("5");
			cost.setXiaohemoIn(GetValueUtil.getDoubleValue(f4));
			cost.setXiaohemoD(getValueByMatType(h, "5", GetValueUtil.getDoubleValue(f4)));
			
			String f5=matData.getVal("6");
			cost.setTiaohemoIn(GetValueUtil.getDoubleValue(f5));
			cost.setTiaohemoD(getValueByMatType(h, "6", GetValueUtil.getDoubleValue(f5)));
			
			String f6=matData.getVal("7");
			cost.setXiaohezhiIn(GetValueUtil.getDoubleValue(f6));
			cost.setXiaohezhiD(getValueByMatType(h, "7", GetValueUtil.getDoubleValue(f6)));
			
			String f7=matData.getVal("8");
			cost.setTiaohezhiIn(GetValueUtil.getDoubleValue(f7));
			cost.setTiaohezhiD(getValueByMatType(h, "8", GetValueUtil.getDoubleValue(f7)));
			
			String f8=matData.getVal("9");
			cost.setNeichenzhiIn(GetValueUtil.getDoubleValue(f8));
			cost.setNeichenzhiD(getValueByMatType(h, "9", GetValueUtil.getDoubleValue(f8)));
		}else if(ecode>100&&ecode<130){
			//成型机标准单耗，盘纸，甘油，丝束
			String f9=matData.getVal("10");
			cost.setPanzhiIn(GetValueUtil.getDoubleValue(f9));
			cost.setPanzhiD(getValueByMatType(h, "10", GetValueUtil.getDoubleValue(f9)));
			
			String f10=matData.getVal("11");
			cost.setGanyouIn(GetValueUtil.getDoubleValue(f10));
			cost.setGanyouD(getValueByMatType(h, "11", GetValueUtil.getDoubleValue(f10)));
			
			String f11=matData.getVal("12");
			cost.setSishuIn(GetValueUtil.getDoubleValue(f10));
			cost.setSishuD(getValueByMatType(h, "12", GetValueUtil.getDoubleValue(f11)));
		}
		cost.setEqpCod(String.valueOf(ecode));
		return cost;
	}
	
	public double getValueByMatType(Hashtable<String,String[]> h,String type,double consumed){
		String[] o=h.get(type);
		if(o!=null&&o.length>0){
			return GetValueUtil.getDoubleValue(o[1]);
		}
		return 0;
	}
	
	@Override
	public CostManager initEqpInputInfos(String ecode) throws Exception {
				String hql="select "
						+ "o.mdEquipment.equipmentCode,"//0
						+ "o.mdEquipment.equipmentName,"//1
						+ "o.mdMat.simpleName,"//2
						+ "o.date,"//3
						+ "o.mdShift.name,"//4
						+ "o.mdTeam.name,"//5
						+ "b.mdMat.name,"//6
						+ "b.mdMat.mdMatType.code,"//7
						+ "s.val, "//8
						+ "o.mdMat.mdUnit.name, "//9
						+ "o.mdEquipment.mdEqpType.id "//10
						+ "from SchWorkorder o,SchWorkorderBom b,SchConStd s where o.mdEquipment.equipmentCode=? and o.sts=2 and b.schWorkorder.id=o.id "
						+ "and (s.del=0 and s.mdMatByProd.id=o.mdMat.id and s.mdMatByMat.id=b.mdMat.id and o.id=s.oid )"
						+ " order by o.mdEquipment.equipmentCode asc";
				List<Object[]> objects = baseDao.queryObjectArray(hql, ecode);
				
				Map<String,CostManager> map = new HashMap<String,CostManager>();
				CostManager costManager = null;
				for (Object[] obj : objects) {
					String key =obj[0].toString();//设备编码作为KEY
					if(map.containsKey(key)){//如果存在，取出赋值
						costManager = map.get(key);
					}else{
						costManager = new CostManager();				
						map.put(obj[0].toString(), costManager);
					}
					costManager.setEqpCod(key);
					costManager.setEqpName(obj[1].toString());
					costManager.setMat(obj[2].toString());
					costManager.setDate(DateUtil.formatDateToString((Date)obj[3], "yyyy-MM-dd"));
					costManager.setShift(obj[4].toString());
					costManager.setTeam(obj[5].toString());
					costManager.setUnit(obj[9].toString());
					costManager.setQty(0D);
					costManager.setEqpType(obj[10].toString());
					this.setMatInfos(costManager,obj[7].toString(),Double.parseDouble(obj[8].toString()));
					
				}
				//转存list
				//List<CostManager> list = new ArrayList<CostManager>();
				for(String key:map.keySet()){
					return map.get(key);
				}
				//Collections.sort(list);//按设备Code排序
				return new CostManager();
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
		List<Object[]> list = baseDao.queryObjectArray(hql.toString());
		if(list.size()<1){
			return new Hashtable<String,String[]>();
		}
		Hashtable<String ,String[]> ht=new Hashtable<String,String[]>();
		for(Object[] objects : list){
			String[] temp = new String[objects.length];
			for(int i=0;i<temp.length;i++){
				temp[i]=String.valueOf(objects[i]);
			}
			//key=牌号+机型ID
			ht.put(temp[0]+temp[1], temp);
		}
		return ht;
	}
	/**
	 * 查询当前设备运行的工单
	 */
	@Override
	public List<EqpRuntime> initOutDataPage(String ecode) {
		if(StringUtil.notNull(ecode)){
			log.error("查询当前设备运行的工单 code为null");
		}
		//主要是获取当前运行的工单班次标准，设备，牌号信息
		List<SchWorkorder> orders = schWorkorderDao.query("from SchWorkorder o where o.enable=1 and o.del=0 and o.mdEquipment.equipmentCode='"+ecode+"' and o.sts=2  " );
		
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		
		for (SchWorkorder schWorkorder : orders) {
			EqpRuntime eqpRuntime = new EqpRuntime(
					schWorkorder.getMdEquipment().getEquipmentCode(), 
					schWorkorder.getMdEquipment().getEquipmentName(), 
					schWorkorder.getMdMat().getSimpleName(), 
					DateUtil.formatDateToString(schWorkorder.getDate(), "yyyy-MM-dd"), 
					schWorkorder.getMdShift().getName(),
					schWorkorder.getMdTeam().getName(),
					schWorkorder.getQty(), 
					schWorkorder.getMdUnit().getName());
			eqpRuntime.setCurOut(0D);
			eqpRuntime.setBadQty(0D);
			eqpRuntime.setOrderNumber(schWorkorder.getCode());
			list.add(eqpRuntime);
		}
		
		return list;
		
	}
	
	/**
	 * 
	* @Title: getMaintainList 
	* @Description:根据工单获取辅料类型的价格
	* @param @param orderNumber
	* @param @return    设定文件 
	* @return Hashtable<String,String[]>    返回类型 
	* @throws
	 */
	public Hashtable<String,String[]> getMaintainList(String orderNumber){
		if(StringUtil.notNull(orderNumber)){
			log.error(this.getClass().getName()+"根据工单获取辅料价格失败！");
			return new Hashtable<String,String[]>();
		}
		StringBuffer hql=new StringBuffer();
		hql.append("select o.code ");//工单号0
		hql.append(",o.mdEquipment.equipmentCode ");//设备code 1
		hql.append(",b.mdMat.name ");//辅料名称 2
		hql.append(",b.mdMat.mdMatType.code ");//辅料类型 3
		hql.append(",c.matPrice ");//辅料价格 4
		hql.append("from SchWorkorder o,SchWorkorderBom b,CosMatPriceMaintain c ");
		hql.append("where o.code=b.schWorkorder.code and b.mdMat.name=c.mdMat.name ");
		hql.append("and  o.code='"+orderNumber+"' ");
		List<Object[]> list = baseDao.queryObjectArray(hql.toString());
		Hashtable<String,String[]> h=new Hashtable<String,String[]>();
		for(Object[] o:list){
			String key=o[3].toString();
			String[] value={o[4].toString()};
			h.put(key, value);
		}
		return h;
	}
	
	/**
	 * 辅料单耗信息及物料成本赋值
	 * @author luo
	 * @create 2015年1月28日09:55:20
	 * @param costManager
	 * @param matTypeCode
	 * @param val
	 * @throws Exception 
	 */
	private void setMatInfos(CostManager costManager, String matTypeCode,
			Double val) throws Exception {
		String htKey=costManager.getMat()+costManager.getEqpType();
		String[] temp=getHt().get(htKey);
		double unitPrice=0.0;//单价
		if(temp!=null&&temp.length>0&&StringUtil.notNull(temp[6])){
			unitPrice=Double.parseDouble(temp[6]);
		}
		/*  辅料类型Code定义
			1	1	成品烟
			
			2	2	卷烟纸
			3	3	水松纸
			4	4	滤棒
			5	5	小盒烟膜
			6	6	条盒烟膜
			7	7	小盒纸
			8	8	条盒纸
			9	9	内衬纸
			10	10	封签纸
			11	11	甘油
			12	12	丝束
			13	13	滤棒盘纸
			14	14	成品滤棒
		 * */
		switch (matTypeCode) {
		//卷烟机部分
		case "2":
			costManager.setJuanyanzhiBzdh(val);
			break;
		case "3":
			costManager.setShuisongzhiBzdh(val);
			break;
		case "4":
			costManager.setLvbangBzdh(val);
			break;
		//包装机部分
		case "5":
			costManager.setXiaohemoBzdh(val);
			break;
		case "6":
			costManager.setTiaohemoBzdh(val);
			break;
		case "7":
			costManager.setXiaohezhiBzdh(val);
			break;
		case "8":
			costManager.setTiaohezhiBzdh(val);
			break;
		case "9":
			costManager.setNeichenzhiBzdh(val);
			break;
		case "11":
			costManager.setGanyouBzdh(val);
			break;
		case "12":
			costManager.setSishuBzdh(val);
			break;
		case "13":
			costManager.setPanzhiBzdh(val);
			break;
		default:
			break;
		}
	}
	
}
