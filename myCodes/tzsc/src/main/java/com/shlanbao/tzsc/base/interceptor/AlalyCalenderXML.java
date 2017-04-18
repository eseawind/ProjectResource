package com.shlanbao.tzsc.base.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.mapping.MdWorkshop;
import com.shlanbao.tzsc.base.mapping.SchCalendar;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.DateUtil;

/**
 * 解析MES关于工厂日历xml数据
 * @author TRAVLER
 *
 */
public class AlalyCalenderXML {
	private static AlalyCalenderXML calender=null;
	
	private AlalyCalenderXML() {
		
	}
	
	public static AlalyCalenderXML getInstance(){
		if(calender==null){
			calender=new AlalyCalenderXML();
		}
		 return calender;
	}
	
	/**
	 * 解析保存xml
	 * @param root
	 * @param fileName
	 * @return
	 * TRAVLER
	 * 20152015年12月24日上午10:02:38
	 */
	public boolean handleQCXML(Element root,String fileName){
		boolean flag=false;
		
		return flag;
	}
	
	/**
	 * 解析工厂日历xml，并封装成map
	 * @param root
	 * @param fileName 文件名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean alalyCalenderXML(Element root,String fileName) throws Exception{
		MdWorkshop workshop=new MdWorkshop();
		MdTeam team=null;
		MdShift shift=null;
		SchCalendar calender=null;
		
		Date sday=null;//开始排班日
		Date eday=null;//最后排班日
		
		String shiftId1=null;//早班
		String shiftName1=null;
		String shiftId2=null;//中班
		String shiftName2=null;
		String shiftId3=null;//晚班
		String shiftName3=null;
		
		String date=null;//工作日
		String shiftStime1=null;//早班开始时间
		String shiftEtime1=null;//早班结束时间
		
		String shiftStime2=null;//中班开始时间
		String shiftEtime2=null;//中班结束时间
		
		String shiftStime3=null;//晚班开始时间
		String shiftEtime3=null;//晚班结束时间
		
		Element ele=null;//中转引用
		String val=null;//中转引用
		Element teamId;//
		Element teamName;
		Element date0;
		List<SchCalendar> calenders=new ArrayList<SchCalendar>();
		//1.解析数据*********************start************************/
		try {
			for (Iterator i= root.elementIterator();i.hasNext();) {
				ele=(Element) i.next();
				val=ele.getName();
				if(val.equals("segmentItem")){
					for (Iterator segmentItem= ele.elementIterator();segmentItem.hasNext();){
						ele=(Element) segmentItem.next();
						val=ele.getName();
						if(val.equals("work_shop")){
							workshop.setId(getNodeStringVal(ele));
						}else if(val.equals("shiftId1")){
							shiftId1=transShift(getNodeStringVal(ele));
						}else if(val.equals("shiftName1")){
							shiftName1=getNodeStringVal(ele);
						}else if(val.equals("stime1")){
							shiftStime1=getNodeStringVal(ele);
						}else if(val.equals("etime1")){
							shiftEtime1=getNodeStringVal(ele);
						}else if(val.equals("shiftId2")){
							shiftId2=transShift(getNodeStringVal(ele));
						}else if(val.equals("shiftName2")){
							shiftName2=getNodeStringVal(ele);
						}else if(val.equals("stime2")){
							shiftStime2=getNodeStringVal(ele);
						}else if(val.equals("etime2")){
							shiftEtime2=getNodeStringVal(ele);
						}else if(val.equals("shiftId3")){
							shiftId3=transShift(getNodeStringVal(ele));
						}else if(val.equals("shiftName3")){
							shiftName3=getNodeStringVal(ele);
						}else if(val.equals("stime3")){
							shiftStime3=getNodeStringVal(ele);
						}else if(val.equals("etime3")){
							shiftEtime3=getNodeStringVal(ele);
						}else if(val.equals("bomInfo")){
							List<?> ls=ele.elements("segmentInfo");
							int size=ls.size();
							//具体的排班
							for (int j=0;j<size;j++){
								ele=(Element) ls.get(j);
								date0=ele.element("date");
								date=getNodeStringVal(date0);
								if(j==0){
									sday=DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
								}
								if(j==size-1){
									eday=DateUtil.strToDate(date+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
								}
								//夜班结束时间天数加一
								/***************第一个*******************/
								String shiftStime = date+" "+shiftStime1;
								String shiftEtime=null;
								if(shiftId1.equals("3")){
									shiftEtime = DateUtil.dateAdd("d",1,DateUtil.strToDate(date, "yyyy-MM-dd"),"yyyy-MM-dd")+" "+shiftEtime1;
								}else{
									shiftEtime = date+" "+shiftEtime1;
								}
								//排班信息 第一个
								teamId=ele.element("teamId1");
								team=new MdTeam(getNodeStringVal(teamId));
								teamName=ele.element("teamName1");
								team.setName(getNodeStringVal(teamName));
								shift=new MdShift(shiftId1);
								shift.setName(shiftName1);
								calender=new SchCalendar(team, shift, workshop, DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil.strToDate(shiftStime, "yyyy-MM-dd HH:mm"), DateUtil.strToDate(shiftEtime, "yyyy-MM-dd HH:mm"), 0L);
								calenders.add(calender);
								
								/***************end*******************/
								
								
								/***************第二个*******************/
								shiftStime = date+" "+shiftStime2;
								if(shiftId2.equals("3")){
									shiftEtime = DateUtil.dateAdd("d",1,DateUtil.strToDate(date, "yyyy-MM-dd"),"yyyy-MM-dd")+" "+shiftEtime2;
								}else{
									shiftEtime = date+" "+shiftEtime2;
								}
								//第二个
								teamId=ele.element("teamId2");
								team=new MdTeam(getNodeStringVal(teamId));;
								teamName=ele.element("teamName2");
								team.setName(getNodeStringVal(teamName));
								shift=new MdShift(shiftId2);;
								shift.setName(shiftName2);
								calender=new SchCalendar(team, shift, workshop, DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil.strToDate(shiftStime, "yyyy-MM-dd HH:mm"), DateUtil.strToDate(shiftEtime, "yyyy-MM-dd HH:mm"), 0L);
								calenders.add(calender);
								
								/***************end*******************/
								
								/***************第三个*******************/
								shiftStime = date+" "+shiftStime3;
								if(shiftId3.equals("3")){
									shiftEtime = DateUtil.dateAdd("d",1,DateUtil.strToDate(date, "yyyy-MM-dd"),"yyyy-MM-dd")+" "+shiftEtime3;
								}else{
									shiftEtime = date+" "+shiftEtime3;
								}
								//第三个
								teamId=ele.element("teamId3");
								team=new MdTeam(getNodeStringVal(teamId));
								teamName=ele.element("teamName3");
								team.setName(getNodeStringVal(teamName));
								shift=new MdShift(shiftId3);;
								shift.setName(shiftName3);
								calender=new SchCalendar(team, shift, workshop, DateUtil.strToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil.strToDate(shiftStime, "yyyy-MM-dd HH:mm"), DateUtil.strToDate(shiftEtime, "yyyy-MM-dd HH:mm"), 0L);
								calenders.add(calender);
								/***************end******************/
							}
						}
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		/**********************end*******************/
		//2.先删除原有的日历
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE SCH_CALENDAR where [DATE]>=? AND [DATE]<=? AND WORKSHOP=? ");
		List<Object> params = new ArrayList<Object>();
		params.add(sday);
		params.add(eday);
		params.add(workshop.getId());
		SchCalendarDaoI dao=ApplicationContextUtil.getBean(SchCalendarDaoI.class);
		dao.updateBySql(sql.toString(),params);
		//3.插入新的日历数据
		return dao.batchInsertAndReturn(calenders, SchCalendar.class);
	}
	
	
	
	/**
	 * 获取节点的String 值
	 *  TODO
	 *  @param e
	 *  @return
	 *  TRAVLER
	 *2015年12月14日下午3:54:36
	 */
	private String getNodeStringVal(Element e){
		String r=null;
		if(null!=e){
			r=e.getTextTrim();
		}
		return r;
	}
	
	/**
	 * 这个不是滕州班次，
	 * 滕州班次与数据库一致
	 * @param st
	 * @return
	 * TRAVLER
	 * 20152015年12月24日上午9:17:50
	 */
	private String transShift(String st){
		String shift="";
		if(st.equals("1")){
			shift="3";//夜班
		}else if(st.equals("2")){
			shift="1";//白班
		}else if(st.equals("3")){
			shift="2";//中班
		}
		return shift;
	}
	
}
