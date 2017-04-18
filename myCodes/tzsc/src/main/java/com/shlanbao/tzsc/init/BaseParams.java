package com.shlanbao.tzsc.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;

/**
 * 基础参数类
 * <li>@author Leejean
 * <li>@create 2014-7-5下午09:22:47
 * <li>存放项目运行过程中常用而不经常改变的基础参数
 * <li>如:班次,班组,机组信息等
 */
public class BaseParams {
	/**
	 * 班次
	 */
	private static List<Combobox> shiftCombobox = new ArrayList<Combobox>();
	/**
	 * 班组
	 */
	private static List<Combobox> teamCombobox = new ArrayList<Combobox>();
	/**
	 * 车间
	 */
	private static List<Combobox> workShopCombobox = new ArrayList<Combobox>();
	/**
	 * 成品
	 */
	private static List<Combobox> matProdCombobox = new ArrayList<Combobox>();

	/**
	 * 物料组
	 */
	private static List<Combobox> matGrpCombobox = new ArrayList<Combobox>();

	/**
	 * 物料类型
	 */
	private static List<Combobox> matTypeCombobox = new ArrayList<Combobox>();

	/**
	 * 计量单位
	 */
	private static List<Combobox> unitCombobox = new ArrayList<Combobox>();
	/**
	 * 设备类型
	 */
	private static List<Combobox> eqpCategoryCombobox = new ArrayList<Combobox>();
	/**
	 * 设备型号
	 */
	private static List<Combobox> eqpTypesCombobox = new ArrayList<Combobox>();
	/**
	 * 所有设备
	 */
	private static List<Combobox> allEqpsCombobox = new ArrayList<Combobox>();
	/**
	 * 所有包装机
	 */
	private static List<Combobox> allPackersCombobox = new ArrayList<Combobox>();
	/**
	 * 所有卷烟机
	 */
	private static List<Combobox> allRollersCombobox = new ArrayList<Combobox>();
	/**
	 * 所有封箱机
	 */
	private static List<Combobox> allBoxersCombobox = new ArrayList<Combobox>();
	/**
	 * 所有成型机
	 */
	private static List<Combobox> allFiltersCombobox = new ArrayList<Combobox>();
	
	/**
	 * 基础数据导入转换ID，name
	 * */
	private static List<Map<String,String>> listSysEqpType = new ArrayList<Map<String,String>>();
	
	
	/**
	 * 【功能说明】：辅料初始化 
	 * @author wanchanghuang
	 * @time 2015年8月10日16:06:47
	 * 
	 */
	private static List<MatParamBean> allMdMatParam = new ArrayList<MatParamBean>();
	

	public static List<Map<String, String>> getListSysEqpType() {
		return listSysEqpType;
	}

	public static void setListSysEqpType(List<Map<String, String>> listSysEqpType) {
		BaseParams.listSysEqpType = listSysEqpType;
	}

	public static List<MatParamBean> getAllMdMatParam() {
		return allMdMatParam;
	}

	public static void setAllMdMatParam(List<MatParamBean> allMdMatParam) {
		BaseParams.allMdMatParam = allMdMatParam;
	}

	public static List<Combobox> getAllFiltersCombobox(boolean setAll) {
		if(setAll){			
			return setAll(allFiltersCombobox);
		}
		return allFiltersCombobox;
	}

	public static void setAllFiltersCombobox(List<Combobox> allFiltersCombobox) {
		BaseParams.allFiltersCombobox = allFiltersCombobox;
	}

	public static List<Combobox> getEqpCategoryCombobox(boolean setAll) {
		if(setAll){			
			return setAll(eqpCategoryCombobox);
		}
		return eqpCategoryCombobox;
	}

	public static void setEqpCategoryCombobox(List<Combobox> eqpCategoryCombobox) {
		BaseParams.eqpCategoryCombobox = eqpCategoryCombobox;
	}

	public static List<Combobox> getAllEqpsCombobox(boolean setAll) {
		if(setAll){			
			return setAll(allEqpsCombobox);
		}
		return allEqpsCombobox;
	}

	public static void setAllEqpsCombobox(List<Combobox> allEqpsCombobox) {
		BaseParams.allEqpsCombobox = allEqpsCombobox;
	}

	public static List<Combobox> getAllPackersCombobox(boolean setAll) {
		if(setAll){			
			return setAll(allPackersCombobox);
		}
		return allPackersCombobox;
	}

	public static void setAllPackersCombobox(List<Combobox> allPackersCombobox) {
		BaseParams.allPackersCombobox = allPackersCombobox;
	}

	public static List<Combobox> getAllRollersCombobox(boolean setAll) {
		if(setAll){			
			return setAll(allRollersCombobox);
		}
		return allRollersCombobox;
	}

	public static void setAllRollersCombobox(List<Combobox> allRollersCombobox) {
		BaseParams.allRollersCombobox = allRollersCombobox;
	}

	public static List<Combobox> getAllBoxersCombobox(boolean setAll) {
		if(setAll){			
			return setAll(allBoxersCombobox);
		}
		return allBoxersCombobox;
	}

	public static void setAllBoxersCombobox(List<Combobox> allBoxersCombobox) {
		BaseParams.allBoxersCombobox = allBoxersCombobox;
	}

	public static List<Combobox> getEqpTypesCombobox(boolean setAll) {
		if(setAll){			
			return setAll(eqpTypesCombobox);
		}
		return eqpTypesCombobox;
	}

	public static List<Combobox> getUnitCombobox(boolean setAll) {
		if(setAll){			
			return setAll(unitCombobox);
		}
		return unitCombobox;
	}

	public static void setUnitCombobox(List<Combobox> unitCombobox) {
		BaseParams.unitCombobox = unitCombobox;
	}

	public static List<Combobox> getShiftCombobox(boolean setAll) {
		if(setAll){			
			return setAll(shiftCombobox);
		}
		return shiftCombobox;
	}

	public static void setShiftCombobox(List<Combobox> shiftCombobox) {
		BaseParams.shiftCombobox = shiftCombobox;
	}

	public static List<Combobox> getTeamCombobox(boolean setAll) {
		if(setAll){			
			return setAll(teamCombobox);
		}
		return teamCombobox;
	}

	public static void setTeamCombobox(List<Combobox> teamCombobox) {
		BaseParams.teamCombobox = teamCombobox;
	}

	public static List<Combobox> getWorkShopCombobox(boolean setAll) {
		if(setAll){			
			return setAll(workShopCombobox);
		}
		return workShopCombobox;
	}

	public static void setWorkShopCombobox(List<Combobox> workShopCombobox) {
		BaseParams.workShopCombobox = workShopCombobox;
	}

	public static List<Combobox> getEqpTypesComboboxCombobox(boolean setAll) {
		if(setAll){			
			return setAll(eqpTypesCombobox);
		}
		return eqpTypesCombobox;
	}

	public static void setEqpTypesCombobox(List<Combobox> eqpTypesCombobox) {
		BaseParams.eqpTypesCombobox = eqpTypesCombobox;
	}

	public static List<Combobox> getMatProdCombobox(boolean setAll) {
		if(setAll){			
			return setAll(matProdCombobox);
		}
		return matProdCombobox;
	}

	public static void setMatProdCombobox(List<Combobox> matProdCombobox) {
		BaseParams.matProdCombobox = matProdCombobox;
	}

	public static List<Combobox> getMatGrpCombobox(boolean setAll) {
		if(setAll){			
			return setAll(matGrpCombobox);
		}
		return matGrpCombobox;
	}

	public static void setMatGrpCombobox(List<Combobox> matGrpCombobox) {
		BaseParams.matGrpCombobox = matGrpCombobox;
	}

	public static List<Combobox> getMatTypeCombobox(boolean setAll) {
		if(setAll){			
			return setAll(matTypeCombobox);
		}
		return matTypeCombobox;
	}

	public static void setMatTypeCombobox(List<Combobox> matTypeCombobox) {
		BaseParams.matTypeCombobox = matTypeCombobox;
	}

	/**
	 * 添加[全部]选项
	 * @author Leejean
	 * @create 2014年12月2日上午8:45:53
	 * @param sourceList
	 * @return
	 */
	private static List<Combobox> setAll(List<Combobox> sourceList) {
		List<Combobox> cb = new ArrayList<Combobox>(sourceList);
		cb.add(0, new Combobox("", "全部"));
		return cb;
	}

	
}
