package tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lanbao.dac.data.CommonData;

import sqlLiteBase.SysConstants;

public class ToolUtils {
	private static Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
		// 过滤掉不需要的字段
		@Override
		public boolean shouldSkipField(FieldAttributes arg0) {
			String fileName = arg0.getName();
			return fileName.equals("cat") || fileName.equals("pc") || fileName.equals("typ")
					|| fileName.equals("cacheVal") || fileName.equals("variable") || fileName.equals("position")
					|| fileName.equals("returnType") || fileName.equals("formula");
		}
		@Override
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}
	}).create();
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * <p>
	 * 功能描述：根据机台号获取数据表名称
	 * </p>
	 * 
	 * @param eqpCode
	 * @return shisihai 2016下午2:32:33
	 */
	public static String getTable(int eqpCode) {
		String tab = null;
		switch (dataType(eqpCode)) {
		case 1:
			tab = SysConstants.ROLLERTAB + eqpCode;
			break;
		case 2:
			tab = SysConstants.PACKAGETAB + eqpCode;
			break;
		case 3:
			tab = SysConstants.BOXERTAB + eqpCode;
			break;
		case 4:
			tab = SysConstants.STORAGERTAB + eqpCode;
			break;
		case 5:
			tab = SysConstants.FILTERTAB + eqpCode;
			break;
		case 6:
			tab = SysConstants.LAUNCHERTAB + eqpCode;
			break;
		case 7:
			tab = SysConstants.LIFTERTAB + eqpCode;
			break;
		case 8:
			tab = SysConstants.INSTALLTAB + eqpCode;
			break;
		case 9:
			tab = SysConstants.UNINSTALLTAB + eqpCode;
			break;
		case 10:
			tab = SysConstants.ROLLER_ZBTAB + eqpCode;
			break;
		case 11:
			tab = SysConstants.FL_TAB + eqpCode;
			break;
		default:
			break;
		}
		return tab;
	}

	public static int dataType(int eqpCode) {
		int result = 0;
		// 卷烟机
		if (eqpCode < 31) {
			return 1;
		}
		// 包装机
		else if (eqpCode > 30 && eqpCode < 61) {
			return 2;
		}
		// 封箱机
		else if (eqpCode > 60 && eqpCode < 71) {
			return 3;
		}
		// 储烟机
		else if (eqpCode > 70 && eqpCode < 101) {
			return 4;
		}
		// 成型机
		else if (eqpCode > 100 && eqpCode < 131) {
			return 5;
		}
		// 发射机
		else if (eqpCode > 130 && eqpCode < 141) {
			return 6;
		}
		// 提升机
		else if (eqpCode == 151) {
			return 7;
		}
		// 装盘机
		else if (eqpCode > 160 && eqpCode < 181) {
			return 8;
		}
		// 卸盘机
		else if (eqpCode > 160 && eqpCode < 181) {
			return 9;
		}
		// 卷烟机嘴棒接收机
		else if (eqpCode > 200 && eqpCode < 213) {
			return 10;
		}
		// 辅料数据
		else if (eqpCode >= 31000) {
			return 11;
		}
		return result;
	}

	/**
	 * <p>
	 * 功能描述：移除冗余的数据
	 * </p>
	 * 
	 * @param commonDatas
	 *            shisihai 2016上午11:56:21
	 */
	public static List<CommonData> removeData(List<CommonData> commonDatas, int eqpCode) {
		List<CommonData> newCommonDatas = new ArrayList<>();
		int[] points = dataPoints(eqpCode);
		if (points != null) {
			int dataNum = points.length;
			for (CommonData data : commonDatas) {
				for (int point : points) {
					if (Integer.valueOf(data.getId()) == point) {
						newCommonDatas.add(data);
						--dataNum;
						break;
					}
					continue;
				}

				if (dataNum == 0) {
					break;
				}
				continue;
			}
		}
		return newCommonDatas;
	}

	/**
	 * <p>
	 * 功能描述：根据设备code获取数据点位
	 * </p>
	 * 
	 * @param eqpCode
	 * @return shisihai 2016下午12:05:57
	 */
	public static int[] dataPoints(int eqpCode) {
		int[] points = null;
		switch (dataType(eqpCode)) {
		case 1:
			points = SysConstants.JYJPOINTS;
			break;
		case 2:
			points = SysConstants.BZJBASE;
			break;
		case 3:
			points = SysConstants.FXJ;
			break;
		case 4:
			points = SysConstants.CYJ;
			break;
		case 5:
			points = SysConstants.CXJ;
			break;
		case 6:
			points = SysConstants.FSJ;
			break;
		case 7:
			points = SysConstants.TSJ;
			break;
		case 8:
			points = SysConstants.ZPJ;
			break;
		case 9:
			points = SysConstants.XPJ;
			break;
		case 10:
			points = SysConstants.ZBJSJ;
			break;
		case 11:
			points = SysConstants.BZJFX;
			break;
		default:
			break;
		}
		return points;
	}

	public static String listToJsonStr(List<CommonData> obj) {
		return gson.toJson(obj);
	}

	public static String formatNowTime() {
		return format.format(new Date());
	}
}
