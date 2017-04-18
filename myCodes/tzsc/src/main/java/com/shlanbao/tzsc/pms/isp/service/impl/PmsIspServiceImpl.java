package com.shlanbao.tzsc.pms.isp.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.pms.isp.beans.BoxerGroup;
import com.shlanbao.tzsc.pms.isp.beans.FilterData;
import com.shlanbao.tzsc.pms.isp.beans.FilterGroup;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerData;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerGroup;
import com.shlanbao.tzsc.pms.isp.beans.ShooterGroup;
import com.shlanbao.tzsc.pms.isp.beans.WorkorderInfoBean;
import com.shlanbao.tzsc.pms.isp.service.PmsIspServiceI;
import com.shlanbao.tzsc.utils.params.EquipmentTypeDefinition;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.utils.tools.XmlUtil;
@Service
public class PmsIspServiceImpl extends BaseService implements PmsIspServiceI {
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Override
	public List<RollerPackerData> getAllRollerPackerDatas() {
		
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		
		List<RollerPackerData> rollerPackerDatas = new ArrayList<RollerPackerData>();
		
		for (EquipmentData equipmentData : allData) {
			String type = equipmentData.getType();
			RollerPackerData  data= null;
			if(EquipmentTypeDefinition.getRoller().contains(type)){//卷烟机
				data = new RollerPackerData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "7")));
				data.setSpeed(GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "15")));
			}
			if(EquipmentTypeDefinition.getPacker().contains(type)){//包装机
				data = new RollerPackerData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "3")));
				data.setSpeed(GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "2")));
			}
			if(EquipmentTypeDefinition.getConveyorScrew().contains(type)){//成品库
				data = new RollerPackerData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty( MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "25"))/5,2));           
				data.setSpeed(1);
			}
			
			if(EquipmentTypeDefinition.getBoxer().contains(type)){//装箱机
				data = new RollerPackerData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty( MathUtil.roundHalfUp(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "1"))/5,2));           
				data.setSpeed(1);
			}
			
			if(data!=null){
				rollerPackerDatas.add(data);
			}
		}
		return rollerPackerDatas;
	}
	
	@Override
	public List<FilterData> getAllFilterDatas() {
		//01	产量
		//02	总废品
		//07	运行时间
		//08	停机时间
		//11	当前车速
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		List<FilterData> filterDatas = new ArrayList<FilterData>();
		for (EquipmentData equipmentData : allData) {
			String type = equipmentData.getType();
			FilterData  data= null;
			if(EquipmentTypeDefinition.getFilter().contains(type)){//卷烟机
				data = new FilterData();
				data.setOnline(equipmentData.isOnline());
				data.setCode(String.valueOf(equipmentData.getEqp()));
				data.setQty(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "1")));
				data.setSpeed(GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "11")));
				data.setRunTime(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "7")));
				data.setStopTime(GetValueUtil.getDoubleValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "8")));
				//成型机停机次数有没有?
				//data.setStopTimes(GetValueUtil.getIntegerValue(NeedData.getInstance().getDataValue(equipmentData.getAllData(), "")));
			}
			if(data!=null){
				filterDatas.add(data);
			}
		}
		return filterDatas;
	}
	@Override
	public List<WorkorderInfoBean> initRollerPackerGroupWorkOrderInfo(Long type) {
		//查询运行的卷烟机工单的设备号，班组，牌号，计划产量
		String hql = "select o.mdEquipment.equipmentCode,o.mdTeam.name,o.mdMat.simpleName,o.qty,o.mdShift.name from SchWorkorder o where o.type=? and o.sts=2 and o.enable=1 and o.del=0";
		List<Object[]> objList = schWorkorderDao.queryObjectArray(hql,type);
		List<WorkorderInfoBean> workorderInfoBeans = new ArrayList<WorkorderInfoBean>();
		for (Object[] objects : objList) {
			WorkorderInfoBean workorderInfoBean = new WorkorderInfoBean();
			workorderInfoBean.setCode(objects[0].toString());
			workorderInfoBean.setTeamName(objects[1].toString());
			workorderInfoBean.setMatName(objects[2].toString());
			workorderInfoBean.setQty(Double.valueOf(objects[3].toString()));
			workorderInfoBean.setShiftName(objects[4].toString());
			workorderInfoBeans.add(workorderInfoBean);
		}
		return workorderInfoBeans;
	}

	@Override
	public List<RollerPackerGroup> initRollerPackerGroups(
			HttpServletRequest request) {
		String xmlPath = WebContextUtil.getVirtualPath("xmlcfg", request)+File.separator+"group.xml";
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "group");
		List<RollerPackerGroup> rollerPackerGroups = new ArrayList<RollerPackerGroup>(); 
		String type = "roller-packer";
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XmlUtil.getValueByNodeName(node, "group-type").equals(type)){
				 String id = XmlUtil.getValueByNodeName(node, "group-id");
				 //机组类型
				 String groupType = XmlUtil.getValueByNodeName(node, "group-type");
				 //机组名称
				 String groupName = XmlUtil.getValueByNodeName(node, "group-name");						 
				 //卷烟机
				 Node roller = XmlUtil.getNodeByNodeName(node, "roller");
				 String rCode = XmlUtil.getValueByNodeName(roller, "code");
				 String rName = XmlUtil.getValueByNodeName(roller, "name");
				 String tType = XmlUtil.getValueByNodeName(roller, "type");
				 //包装机
				 Node packer = XmlUtil.getNodeByNodeName(node, "packer");
				 String pCode = XmlUtil.getValueByNodeName(packer, "code");
				 String pName = XmlUtil.getValueByNodeName(packer, "name");
				 String pType = XmlUtil.getValueByNodeName(packer, "type");
				 rollerPackerGroups.add(new RollerPackerGroup(id, groupType, groupName, rCode, rName, tType, pCode, pName, pType));								 
			}
			
		}
		return rollerPackerGroups;
	}

	@Override
	public List<BoxerGroup> initBoxerGroups(HttpServletRequest request) {
		String xmlPath = WebContextUtil.getVirtualPath("xmlcfg", request)+File.separator+"group.xml";
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "group");
		List<BoxerGroup> boxerGroups = new ArrayList<BoxerGroup>(); 
		String gtype = "boxer";
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XmlUtil.getValueByNodeName(node, "group-type").equals(gtype)){
				 String id = XmlUtil.getValueByNodeName(node, "group-id");
				 //机组类型
				 String groupType = XmlUtil.getValueByNodeName(node, "group-type");
				 //机组名称
				 String name = XmlUtil.getValueByNodeName(node, "group-name");	
				 
				 String code = XmlUtil.getValueByNodeName(node, "code");	
				 
				 String type = XmlUtil.getValueByNodeName(node, "type");						 
				
				 boxerGroups.add(new BoxerGroup(id, groupType, name, code, type));								 
			}
			
		}
		return boxerGroups;
	}

	@Override
	public List<ShooterGroup> initShootererGroups(HttpServletRequest request) {
		String xmlPath = WebContextUtil.getVirtualPath("xmlcfg", request)+File.separator+"group.xml";
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "group");
		List<ShooterGroup> shooterGroups = new ArrayList<ShooterGroup>(); 
		String gtype = "shooter";
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XmlUtil.getValueByNodeName(node, "group-type").equals(gtype)){
				 String id = XmlUtil.getValueByNodeName(node, "group-id");
				 //机组类型
				 String groupType = XmlUtil.getValueByNodeName(node, "group-type");
				 //机组名称
				 String name = XmlUtil.getValueByNodeName(node, "group-name");	
				 
				 String code = XmlUtil.getValueByNodeName(node, "code");	
				 
				 String type = XmlUtil.getValueByNodeName(node, "type");						 
				
				 shooterGroups.add(new ShooterGroup(id, groupType, name, code, type));									 
			}
			
		}
	return shooterGroups;
	}

	@Override
	public List<FilterGroup> initFilterGroups(HttpServletRequest request) {
		String xmlPath = WebContextUtil.getVirtualPath("xmlcfg", request)+File.separator+"group.xml";
		NodeList nodeList = XmlUtil.getRootNodes(xmlPath, "group");
		List<FilterGroup> filterGroups = new ArrayList<FilterGroup>(); 
		String gtype = "filter";
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XmlUtil.getValueByNodeName(node, "group-type").equals(gtype)){
				 String id = XmlUtil.getValueByNodeName(node, "group-id");
				 //机组类型
				 String groupType = XmlUtil.getValueByNodeName(node, "group-type");
				 //机组名称
				 String name = XmlUtil.getValueByNodeName(node, "name");	
				 
				 String code = XmlUtil.getValueByNodeName(node, "code");	
				 
				 String type = XmlUtil.getValueByNodeName(node, "type");						 
				
				 filterGroups.add(new FilterGroup(id, groupType, name, code, type));	
			}
			
		}
		return filterGroups;
	}

	
	/**
	 * 获得当天当班所有已运行的工单计划总产量，计划结束时间
	 * @author wanchanghuang
	 * @create 2016年12月15日13:22:56
	 * @return
	 */
	@Override
	public Map<String, String> getAllWorkOrderByQty() {
		Map<String ,String> map=new HashMap<String, String>();
		try {
			StringBuffer sb=new StringBuffer(100);
			//查询工厂日历
			sb.append("select ID,SHIFT,TEAM,DATE,STIM,ETIM,WORKSHOP from Sch_Calendar  WITH (NOLOCK)  ");
			sb.append("where (? between STIM and ETIM) and del=0 ");
			String beginDay = DateUtil.dateAdd("d",0,new Date(),"yyyy-MM-dd HH:mm:ss");//当前换班时间
			List<Object> params = new ArrayList<Object>();
			params.add(beginDay);
			List<?>  list= schWorkorderDao.queryBySql(sb.toString(), params);
			String shift="0";
			String etime="";
			String totalQty="0";
			if(list.size()>0){
				Object[] arr0=(Object[]) list.get(0);
				shift=arr0[1].toString();
				etime=arr0[5].toString();
			}
			map.put("shift", shift);
			map.put("etime", etime);
			sb.setLength(0);
			sb.append("select sum(qty) as t,shift from SCH_WORKORDER  where date='"+etime.substring(0, 10)+"' and shift='"+shift+"' and type in (1,2) and sts=2 group by shift");
			List<?> list2= schWorkorderDao.queryBySql(sb.toString());
			if(!list2.isEmpty()){
				Object[] temp=(Object[]) list2.get(0);
				totalQty=temp[0].toString();
				totalQty=MathUtil.roundHalfUp(Double.parseDouble(totalQty)/5,2).toString();
			}	
			map.put("qty",totalQty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
