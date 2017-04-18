package com.shlanbao.tzsc.utils.params;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.init.BaseParams;





/**
 * 数据字典导入固定参数
 * @author wanchanghuang
 * @create 2015年9月9日10:14:10
 */
public class SysEqpTypeBase {

	/**
	 * 点检角色（操作工，维修工，维修主管)
	 * value-sys_role表ID（角色ID）
	 */
	public static Map<String, String> getDJRoleAlls(){
		 Map<String, String> roleMap = new HashMap<String, String>();
		 roleMap.put("操作工点检","8af2d43f4d73d86d014d73df6da90000");
		 roleMap.put("机械维修工点检","402899894db72650014db78d4035004f");
		 roleMap.put("电气维修工点检", "8af2d49050d2002d0150da342dfb05b3");
		 roleMap.put("电气维修主管点检", "8af2d49050d2002d0150da35251d05b4");
		 roleMap.put("机械维修主管点检","402899894db72650014db78daf010050");
		return roleMap;
	}
	/**
	 * 获取数组格式的点检角色id 操作工点检、维修工点检、维修主管点检
	 * @return 工种角色id：0操作工、1机械维修工、2机械维修主管、3润滑工、4机械轮保工、5电气维修工、6电气维修主管、7电气轮保工
	 */
	
	public static String[] getArrayDJRoleAll(){
		return new String[]{"8af2d43f4d73d86d014d73df6da90000","402899894db72650014db78d4035004f","402899894db72650014db78daf010050","402899db4b399988014b399b33490000","8af2d43f4d73d86d014d73e1615a0001","8af2d49050d2002d0150da342dfb05b3","8af2d49050d2002d0150da35251d05b4","8af2d49050d2002d0150da33910005b2"};
	}
	/**
	 * 轮保角色（操作工，维修工，维修主管)
	 * value-sys_role表ID（角色ID）
	 * 注意：如果需要添加新角色，页面addMdType.jsp需要新增（目的页面显示角色名称）
	 *     queryTypeCategory.jsp
	 */
	public static Map<String, String> getLBRoleAlls(){
		 Map<String, String> roleMap = new HashMap<String, String>();
		 roleMap.put( "操作工项目","8af2d43f4d73d86d014d73df6da90000");
		 roleMap.put( "机械轮保工项目","8af2d43f4d73d86d014d73e1615a0001");
		 roleMap.put( "电气轮保工项目","8af2d49050d2002d0150da33910005b2");
		return roleMap;
	}
	
	/**
	 * 润滑部位
	 * 
	 */
	public static Map<String, String> getRHRoleAlls(){
		 Map<String, String> roleMap = new HashMap<String, String>();
		 roleMap.put( "VE-卷烟机","VE");
		 roleMap.put("SE-卷烟机","SE");
		 roleMap.put( "MAX-卷烟机","MAX");
		 roleMap.put( "HCF-卷烟机","HCF");
		 roleMap.put("ZF12B-卷烟机","ZF12B");
		 
		 roleMap.put( "YB25-包装机","YB25");
		 roleMap.put( "YB45-包装机","YB45");
		 roleMap.put( "YB55-包装机","YB55");
		 roleMap.put( "YB65-包装机","YB65");
		 roleMap.put("YB95-包装机","YB95");
		return roleMap;
	}
	/**
	 * 设备点检增加功能，部位下啦
	 * 
	 * type  卷-卷烟机
	 *     	    包-包装机
	 *       封-封箱机
	 *       发射-发射机
	 *       喂丝-喂丝机
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static Map<String, String> getDJBoxRoleAlls(String equipmentCode,String eqpTypeName) throws UnsupportedEncodingException{
		Map<String, String> roleMap = new HashMap<String, String>();
		int code=Integer.parseInt(equipmentCode);
		 if(code<=30){
			 roleMap.put( "VE-卷烟机","VE");
			 roleMap.put("SE-卷烟机","SE");
			 roleMap.put( "MAX-卷烟机","MAX");
			 roleMap.put( "HCF-卷烟机","HCF");
			 roleMap.put("ZF12B-卷烟机","ZF12B");
		 }else if(code>=31&&code<=60){
			 if(eqpTypeName.indexOf("25")>-1){
				 roleMap.put( "YB25-包装机","ZB25");
				}else{
				 roleMap.put( "YB45-包装机","ZB45");
				}
			 roleMap.put( "YB55-包装机","YB55");
			 roleMap.put( "YB65-包装机","YB65");
			 roleMap.put("YB95-包装机","YB95");
		 }else if(code>=61&&code<=70){
			 roleMap.put("YP11A-封箱机","YP11A"); 
		 }else if(code>=131&&code<=140){
			 roleMap.put("YP27B-发射机","YP27B");
		 }else if(code>=101&&code<=130){
			 roleMap.put("成型机","成型机");
		 }
		return roleMap;
	} 
	/**
	 * 数据字典润滑导入，润滑方式
	 * 
	 * */
	public static Map<String,String> getBaseDatas(){
		Map<String,String> map = new HashMap<String, String>();
		 List<Map<String,String>> list=  BaseParams.getListSysEqpType();
		 map=list.get(0);
		return map;
	}
	
	/**
	 * 获取设备的id和名字
	 * @return
	 */
	public static Map<String,String> getAllEqpNameAndID(){
		Map<String,String> map = new HashMap<String, String>();
		List<Combobox> box=BaseParams.getAllEqpsCombobox(false);
		for (int i = 0; i < box.size(); i++) {
			Combobox b=new Combobox();
			b=box.get(i);
			map.put(b.getName(), b.getId());
		}
		return map;
	}
	
	/**
	 * 获取机型和名字
	 * @return
	 */
	public static Map<String,String> getEqpTypeNameAndID(){
		Map<String,String> map = new HashMap<String, String>();
		List<Combobox> box=BaseParams.getEqpTypesCombobox(false);
		for (int i = 0; i < box.size(); i++) {
			Combobox b=new Combobox();
			b=box.get(i);
			map.put(b.getName(), b.getId());
		}
		return map;
	}
	
	/**
	 * 获取车间的name和id
	 * @return
	 */
	public static Map<String,String> getworkshopNameAndID(){
		Map<String,String> map = new HashMap<String, String>();
		List<Combobox> box=BaseParams.getWorkShopCombobox(false);
		for (int i = 0; i < box.size(); i++) {
			Combobox b=new Combobox();
			b=box.get(i);
			map.put(b.getName(), b.getId());
		}
		return map;
	}
	public static Map<String,String> geteqpCategoryFromEqpType(){
		List<Combobox> box=BaseParams.getEqpCategoryCombobox(false);
		Map<String,String> map = new HashMap<String, String>();
		for (int i = 0; i < box.size(); i++) {
			Combobox b=new Combobox();
			b=box.get(i);
			map.put(b.getName(), b.getId());
		}
		return map;
	}
	
	/**
	 * 获取单位名和id
	 * @return
	 */
	public static  Map<String,String> getUnit(){
		Map<String, String>  map=new HashMap<String, String>();
		 List<Combobox> ls=BaseParams.getUnitCombobox(false);
		 for (int i = 0; i < ls.size(); i++) {
			map.put(ls.get(i).getName(), ls.get(i).getId());
		}
		return map;
	}
	/**
	 * 设备启用禁用
	 */
	public static Map<String,String> eqpStat(){
		Map<String,String> map=new HashMap<String, String>();
		map.put("启用", "1");
		map.put("禁用", "0");
		return map;
	}
	/**
	 * 设备固入状态
	 */
	public static Map<String,String> eqpPutStat(){
		Map<String,String> map=new HashMap<String, String>();
		map.put("未入固", "1");
		map.put("已入固", "0");
		return map;
	}
	
	
	/**
	 * wct设备有效作业率
	 * jcTIme -就餐时间    wct就餐时间固定30分钟
	 * 
	 * */
	public static Double jcTime=30d;
	
	/**
	 * 设备轮完成保状态，目前全部完成为8
	 */
	public static int Wctype=8;
	
	/**
	 * 班次  1-早  2-中  3-晚   0-全部
	 * 
	 * */
	public static String shift_0="0";
	public static String shift_1="1";
	public static String shift_2="2";
	public static String shift_3="3";
	/**
	 * 班次  1-早  2-中  3-晚   0-全部
	 * 取到具体的班次
	 * 张璐
	 * 
	 * */
	public static String[] getShift(){
		return new String[]{"0","1","2","3"};
	}
	/**
	 * 周期  1-日  2-周  3-月   4-年
	 * 
	 * */
	public static String period_1="1";
	public static String period_2="2";
	public static String period_3="3";
	public static String period_4="4";
	/**
	 * 周期  1-日  2-周  3-月   4-年
	 * @return
	 */
	public static Map<String,String> getRh2(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("日", "1");
		map.put("周", "2");
		map.put("月", "3");
		map.put("年", "4");
		return map;
	}
	
	
	/**
	 * 共鞥：用于判定WCT设备点检中维修主管信息状态
	 * 0-未完成，1-通过，2-有故障
	 */
	public static String che_WX0="0";
	public static String che_WX1="1";
	public static String che_WX2="2";
	/**
	 * 条烟提升机code
	 */
	public static Integer TYTS_CODE=151;
	/**
	 * 张璐
	 * excel导出设置行数和页数
	 * 生产统计中，导出暂设置为1W条
	 */
	public static int page=1;
	public static int rows=10000;
	
	
	/**
	 * 【功能描述】：通过当前时间，
	 *           返回数组 String['班次'，日期] 
	 * @author wanchanghuagn
	 * @createTime 2015年11月9日10:13:35
	 * */
	public static String[] getShiftAndDate(String dateTime){
		
		return null;
	}
	/**
	 * 包装机设备类型code
	 */
	public static int packerEqpTypeCode=2;
	/**
	 * 卷烟机设备类型code
	 */
	public static int rollerEqpTypeCode=1;
	/**
	 * 封箱机设备类型code
	 */
	public static int sealerEqpTypeCode=3;
	/**
	 * 成型机设备类型code
	 */
	public static int filterEqpTypeCode=4;
	/**
	 * 发射机设备类型code
	 */
	public static int launchEqpTypeCode=5;
	/**
	 * 装盘机设备类型code
	 */
	public static int snatchEqpTypeCode=6;
	/**
	 * 卸盘机设备类型code
	 */
	public static int unsnatchEqpTypeCode=7;
	
	/**
	 * 盘纸规格  交接班使用
	 */
	public static Double pz_gg=5000D;
	
	
	
	
	/*************START****************mes  xml相关字段*********************************/
	/**
	 * mes下发的质检xml判别字段
	 */
	public static String QC="MES_JB_CHECK";
	/**
	 * mes下发的工厂日历xml判别字段
	 */
	public static String SC="MES_JB_SHC";
	/**
	 * mes工单撤销xml判别字段
	 */
	public static String WR="MES_JB_WORCANCEL";
	
	/**
	 * mes工单反馈xml判别字段
	 */
	public static String WB="MES_JB_BACKNS";
	
	/**
	 * mes下发的工单xml判别字段
	 */
	public static String WO="MES_JB_WOR";
	
	/**
	 * mes下发的工单信号xml判别字段
	 */
	public static String WS="MES_JB_WOSIGNAL";
	
	
	/**
	 * 工单反馈xml存放位置
	 */
	public static String OFBT="C:\\";
	/**
	 * 工单信号反馈存放位置
	 */
	public static String OSTS="C:\\";
/**********************************END********************************/	
}
