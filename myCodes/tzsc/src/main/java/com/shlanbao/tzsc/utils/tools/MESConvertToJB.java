package com.shlanbao.tzsc.utils.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.base.dao.MdEquipmentParamDaoI;
import com.shlanbao.tzsc.base.dao.MdUnitDaoI;
import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;

public class MESConvertToJB {
	/**
	 *说明：通过mes系统的单位mes_code 返回 数采系统 md_unit表中的 id
	 * @param mesMatCode
	 * shisihai 
	 * @return   返回 数采系统 md_unit表中的 id  没有对应结果返回 null
	 * 20162016年2月24日上午11:00:56
	 */
	public static String MES2JBMatUnitConvert(String mesMatCode){
		String id=null;
		MdUnitDaoI dao=ApplicationContextUtil.getBean(MdUnitDaoI.class);
		String sql="SELECT id from MD_UNIT where mes_code='"+mesMatCode+"'";
		List<?> ls=dao.queryBySqlExpand(sql);
		if(ls!=null && ls.size()>0){
			id=StringUtil.convertObjToString(ls.get(0));
		}else{
			id="-1";
		}
		return id;
	}
	
	
	/**
	 *说明：通过mes系统的单位mes_code 返回 数采系统 md_unit表中的 id
	 * @param mesMatCode -物料号
	 * wanchanghuang
	 * @return   返回 数采系统 md_unit表中的 id  没有对应结果返回 null
	 * 20162016年2月24日上午11:00:56
	 */
	public static String MES2JBMatConvert(String mesMatCode){
		String id=null;
		MdUnitDaoI dao=ApplicationContextUtil.getBean(MdUnitDaoI.class);
		String sql="SELECT id from MD_MAT where code='"+mesMatCode+"'";
		List<?> ls=dao.queryBySqlExpand(sql);
		if(ls!=null && ls.size()>0){
			id=StringUtil.convertObjToString(ls.get(0));
		}else{
			id="-1";
		}
		return id;
	}
	
	
	public static Map<String,String> MES2JBMatConvert2(String mesMatCode){
		Map<String,String> map=new HashMap<String, String>();
		MdUnitDaoI dao=ApplicationContextUtil.getBean(MdUnitDaoI.class);
		String sql="SELECT id,(select code from MD_MAT_TYPE where id=tid ) as mtid from MD_MAT where code='"+mesMatCode+"'";
		List<?> ls=dao.queryBySqlExpand(sql);
		if(ls!=null && ls.size()>0){
			for(Object o:ls){
				Object[] temp=(Object[]) o;
				map.put("id",temp[0].toString());
				map.put("tid",temp[1].toString());
			}
		}else{
			return null;
		}
		return map;
	}
	
	/**
	 * 转换设备code 用于mes设备code转换    传入mes数据库设备code 得到 数采数据库设备code
	 * @param code   mes数据库设备code
	 * 1#到12#卷烟机            编码分别2101 --- 2112   数据库  1----12            计算：-2100
	 * 
	 * 1#到12#包装机            编码分别2201 --- 2212    数据库 31----42         计算：-2170
	 * 
	 * 1#到3#封箱机               编码分别2301 --- 2303     数据库  61----64       计算：-2240
	 * 
	 * 成型机                               猜测是2401开始
	 * @return code不为数字返回"" code <= 0 会抛异常
	 * TRAVLER
	 * 20152015年12月24日上午9:22:35
	 */
	public static String transEqpCode(String code) throws Exception{
		String eqpCode="";
		if(StringUtil.isInteger(code)){
			Integer code0=Integer.valueOf(code);
			//卷烟机
			if(code0 >= 2101 && code0 <= 2112 ){
				code0 -=2100;
			}
			//包装机
			else if(code0 >= 2201 && code0 <= 2212 ){
				code0 -=2170;
			}
			//封箱机
			else if(code0 >= 2301 && code0 <= 2303 ){
				code0 -=2240;
			}
			if(code0 <= 0){
				code0=code0/0;//抛出异常
			}
			eqpCode += code0;
		}
		return eqpCode;
	}
	/**
	 *说明：根据传入 MES的eqpcode 得到 数采的设备id
	 * @return 无结果和异常都返回null
	 * shisihai  
	 * 20162016年2月25日上午9:43:12
	 */
	public static String convertMESEqpCode2JBId(String mesEqpCode){
		String eqpId=null;
		try {
			/*String eqpCode=transEqpCode(mesEqpCode);*/
			MdEquipmentParamDaoI dao=ApplicationContextUtil.getBean(MdEquipmentParamDaoI.class);
			String sql="SELECT ID from MD_EQUIPMENT where MES_EQPCODE='"+mesEqpCode+"'";
			List<?> ls=dao.queryBySqlExpand(sql);
			if(null!=ls && ls.size()>0){
				eqpId=StringUtil.convertObjToString(ls.get(0));
			}else{
				eqpId="-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return eqpId;
		}
		return eqpId;
	}
	
	/**
	 *说明：MES和数采 设备code相互转换      MES成型机的code和数采成型机code没有给出相对应的关系，故当key为“”时结果不确定获取的是哪一个成型机
	 * @param type 1表示mescode转数采code    2表示数采code转mescode
	 * @return  根据type 1返回 mescode为key，数采设备code为value的map    tyep=2 与type=1相反
	 * shisihai  
	 * 
	 * 
	 * 1#到12#卷烟机            编码分别2101 --- 2112   数据库  1----12            计算：-2100
	 * 
	 * 1#到12#包装机            编码分别2201 --- 2212    数据库 31----42         计算：-2170
	 * 
	 * 1#到3#封箱机               编码分别2301 --- 2303     数据库  61----64       计算：-2240
	 * 
	 * 成型机                               猜测是2401开始
	 * 
	 * 
	 * 
	 * 20162016年2月26日上午10:12:52
	 */
	public static Map<String,String> convertMESOrJBEqpCode(Integer type){
		Map<String,String> data=new HashMap<String,String>();
		MdEquipmentParamDaoI dao=ApplicationContextUtil.getBean(MdEquipmentParamDaoI.class);
		String sql="SELECT EQUIPMENT_CODE,MES_EQPCODE from MD_EQUIPMENT WHERE DEL=0 AND ENABLED=1";
		List<?> ls=dao.queryBySqlExpand(sql);
		Object[] obj=null;
		if(type==1){
			for (Object object : ls) {
				obj=(Object[]) object;
				data.put(StringUtil.convertObjToString(obj[1]), StringUtil.convertObjToString(obj[0]));
			}
		}else if(type==2){
			for (Object object : ls) {
				obj=(Object[]) object;
				data.put(StringUtil.convertObjToString(obj[0]), StringUtil.convertObjToString(obj[1]));
			}
		}
		return data;
	}
	
	/**
	 *说明：MES 和    数采 班次转换
	 * @param code shift
	 * @param type 1 MES转数采     2 数采转MES
	 * @return
	 * shisihai  数采（ 1-早  2-中  3-晚）     MES（2- 白班 3-中班  1-夜班）
	 * 20162016年2月29日下午2:54:29
	 */
	public static String convertShift(String code,Integer type){
		String jBCode="";
		
		if(type==1){
			if(code.equals("1")){
				jBCode="3";
			}else if(code.equals("2")){
				jBCode="1";
			}else if(code.equals("3")){
				jBCode="2";
			}
		}else if(type==2){
			if(code.equals("1")){
				jBCode="2";
			}else if(code.equals("2")){
				jBCode="3";
			}else if(code.equals("3")){
				jBCode="1";
			}
		}
		return jBCode;
	}


	public static String convertMESTeamCode2JBId(String dt,String shiftId) {
		String id=null;
		SchCalendarDaoI dao=ApplicationContextUtil.getBean(SchCalendarDaoI.class);
		String sql="select team from SCH_CALENDAR where date='"+dt+"' and shift='"+shiftId+"' and del=0 ";
		List<?> ls=dao.queryBySqlExpand(sql);
		if(ls!=null && ls.size()>0){
			id=StringUtil.convertObjToString(ls.get(0));
		}else{
			id="-1";
		}
		return id;
	}
	
	
	
	/**
	* @Title: 返回字符串日期
	* @Description: 
	* @param  params
	* @return String   返回类型 
	* @throws
	 */
	private static String date="";
	static SchCalendarDaoI dao=null;//ApplicationContextUtil.getBean(SchCalendarDaoI.class);
	public static String rutDateStr(){
		try {
			if(!date.equals(DateUtil.getNowDateTime("yyyy-MM-dd"))){
				if(dao==null){
					dao=ApplicationContextUtil.getBean(SchCalendarDaoI.class);
				}
				StringBuffer sb=new StringBuffer(100);
				//查询工厂日历
				sb.append("select ID,SHIFT,TEAM,convert(varchar(20),date,23) as date,stim,ETIM,WORKSHOP from Sch_Calendar  WITH (NOLOCK)  ");
				sb.append("where ('"+  DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")+"' between STIM and ETIM) and del=0 ");
				List<?>  list= dao.queryBySqlExpand(sb.toString());
				if(!list.isEmpty()){
					Object[] arr0=(Object[]) list.get(0);
					date=arr0[3].toString();
				}
			}
			return date;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return DateUtil.getNowDateTime("yyyy-MM-dd");
	}
	
	
}
