package com.shlanbao.tzsc.pms.equ.runtime.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.mapping.SchStatOutput;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveGraphBean;
import com.shlanbao.tzsc.pms.equ.runtime.beans.EqpRunTimeBean;
import com.shlanbao.tzsc.pms.equ.runtime.service.EqpRunTimeServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
 * @ClassName: EqpRunTimeServiceImpl 
 * @Description: 设备运行统计service实现类 
 * @author luo
 * @date 2015年3月5日 下午3:44:56 
 *
 */
@Service
public class EqpRunTimeServiceImpl extends BaseService implements EqpRunTimeServiceI{

	@Autowired
	private BaseDaoI<Object> baseDao;
	
	/*@Autowired
	private BaseDaoI<SchStatOutput> baseDaoI;*/
	/**
	 * 设备运行统计查询方法
	 */
	@Override
	public DataGrid queryEqpRunTimeByBean(EqpRunTimeBean bean, PageParams pageParams) {
		String hql = "from SchStatOutput o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getEqpName())){
			params.append(" and o.schWorkorder.mdEquipment.equipmentName like '%"+ bean.getEqpName() +"%'");
		}
		if(StringUtil.notNull(bean.getEqpType())){
			params.append(" and o.schWorkorder.mdEquipment.mdEqpType.id='"+ bean.getEqpType() +"'");
		}
		if(StringUtil.notNull(bean.getTeamName())){
			params.append(" and o.schWorkorder.mdTeam.id='"+ bean.getTeamName() +"'");
		}
		if(StringUtil.notNull(bean.getShiftName())){
			params.append(" and o.schWorkorder.mdShift.id='"+ bean.getShiftName() +"'");
		}
		if(bean.getRunDate()!=null&&StringUtil.notNull(bean.getRunDate().toString().trim())){ 
			params.append(" and CONVERT(varchar(100),o.schWorkorder.date, 23)>='"+ bean.getRunDate() +"'");
		}
		if(bean.getRunDate2()!=null&&StringUtil.notNull(bean.getRunDate2().toString().trim())){
			params.append(" and CONVERT(varchar(100),o.schWorkorder.date, 23)<='"+ bean.getRunDate2() +"'");
		}
		String param = params.toString();
		/*hql = hql.concat(StringUtil.fmtDateBetweenParams("o.schWorkorder.stim", bean.getRunDate(), bean.getRunDate2()));*/
		List<Object> eqmTrouble = baseDao.queryByPage(hql.concat(param).concat(" order by o.schWorkorder.date, o.schWorkorder.mdEquipment.equipmentName, o.schWorkorder.mdShift.id"), pageParams);
		List<EqpRunTimeBean> list = new ArrayList<EqpRunTimeBean>();
		for(Object o : eqmTrouble){
			SchStatOutput et = (SchStatOutput) o;
			EqpRunTimeBean tb = new EqpRunTimeBean();
			tb.setEqpName(et.getSchWorkorder().getMdEquipment().getEquipmentName());
			tb.setEqpType(et.getSchWorkorder().getMdEquipment().getMdEqpType().getName());
			tb.setShiftName(et.getSchWorkorder().getMdShift().getName());
			tb.setTeamName(et.getSchWorkorder().getMdTeam().getName());
			tb.setRunTime(et.getRunTime());
			tb.setTime(DateUtil.formatDateToString(et.getSchWorkorder().getDate(),"yyyy-MM-dd"));
			list.add(tb);
		}
		hql = "select count(*) from SchStatOutput o where 1=1 and o.del=0 ";
		long total = baseDao.queryTotal(hql.concat(param));
		return new DataGrid(list,total);
	}
	
	//查询运行效率图表数据源
	public EffectiveGraphBean queryEqpRunTimeChart(EqpRunTimeBean bean, PageParams pageParams){
		
		String hql = "select o.schWorkorder.date"
				+ ",o.schWorkorder.mdEquipment.equipmentName"
				+ ",o.schWorkorder.mdShift.name"
				+ ",o.schWorkorder.mdTeam.name"
				+ ",o.runTime  from SchStatOutput o where 1=1 and o.del=0 ";
		StringBuffer params = new StringBuffer();
		if(StringUtil.notNull(bean.getEqpName())){
			params.append(" and o.schWorkorder.mdEquipment.equipmentName like '%"+ bean.getEqpName() +"%'");
		}
		if(StringUtil.notNull(bean.getEqpType())){
			params.append(" and o.schWorkorder.mdEquipment.mdEqpType.id='"+ bean.getEqpType() +"'");
		}
		if(StringUtil.notNull(bean.getTeamName())){
			params.append(" and o.schWorkorder.mdTeam.id='"+ bean.getTeamName() +"'");
		}
		if(StringUtil.notNull(bean.getShiftName())){
			params.append(" and o.schWorkorder.mdShift.id='"+ bean.getShiftName() +"'");
		}
		if(bean.getRunDate()!=null&&StringUtil.notNull(bean.getRunDate().toString().trim())){ 
			params.append(" and CONVERT(varchar(100),o.schWorkorder.date, 23)>='"+ bean.getRunDate() +"'");
		}
		if(bean.getRunDate2()!=null&&StringUtil.notNull(bean.getRunDate2().toString().trim())){
			params.append(" and CONVERT(varchar(100),o.schWorkorder.date, 23)<='"+ bean.getRunDate2() +"'");
		}
		String param = params.toString();
		
		List<Object> list = baseDao.queryByPage(hql.concat(param).concat(" order by o.schWorkorder.date, o.schWorkorder.mdEquipment.equipmentName, o.schWorkorder.mdShift.id"), pageParams);
		Hashtable<String,String> ht1=new Hashtable<String,String>();
		Hashtable<String,String> ht2=new Hashtable<String,String>();
		Hashtable<String,Object[]> ht3=new Hashtable<String,Object[]>();
		int xlength=0;
		int ylength=0;
		List<String> sortList=new ArrayList<String>();
		for(Object o:list){
			Object[] temp=(Object[])o;
			String date=temp[0].toString();
			date=date.substring(5,10);
			//获取日期+设备名称
			if(ht1.get(temp[1].toString()+date)==null){
				//DateUtil.formatDateToString((Date)temp[0],"yyyy-MM-dd");
				ht1.put(temp[1].toString()+date, temp[1].toString()+date);
				sortList.add(temp[1].toString()+date);
				xlength++;
			}
			//获取班次名称
			if(ht2.get(temp[2].toString())==null){
				ht2.put(temp[2].toString(), temp[2].toString());
				ylength++;
			}
			//
			if(ht3.get(temp[1].toString()+date+temp[2].toString())==null){
				ht3.put(temp[1].toString()+date+temp[2].toString(), temp);
			}
			
		}
		//Iterator<String> i1=ht1.keySet().iterator();
		Iterator<String> i2=ht2.keySet().iterator();
		//X轴值类型
		String[] xValue=new String[xlength];
		//Y轴值类型
		String[] yvalueType=new String[ylength];
		//Y轴值
		double[][] yValue=new double[ylength][xlength];
		int i=0;
		int j;
		//循环设备
		for(int index=0;index<sortList.size();index++){
			String key=sortList.get(index);
			xValue[i]=ht1.get(key);
			j=0;
			i2=ht2.keySet().iterator();
			//循环班组信息
			while(i2.hasNext()){
				String i2Key=i2.next();
				yvalueType[j]=i2Key;
				Object o=ht3.get(ht1.get(key)+ht2.get(i2Key));
				if(o!=null){
					Object[] temp=(Object[])o;
					yValue[j][i]=Double.parseDouble(temp[4].toString());
				}else{
					yValue[j][i]=0.0;
				}
				j++;
			}
			i++;
		}
		EffectiveGraphBean beans=new EffectiveGraphBean();
		beans.setXvalue(xValue);
		beans.setYvalue(yValue);
		beans.setYvalueType(yvalueType);
		return beans;
	} 

}
