package com.traveler.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SysCache {
	/**
	 * 系统菜单缓存
	 */
	public static List<Map<String,Object>> SYS_MENU=new ArrayList<>();
	/**
	 * 菜单权限缓存
	 */
	public static List<Map<String,Object>> SYS_MENU_PREMISSION=new ArrayList<>();
	/**
	 * 系统角色操作权限缓存
	 */
	public static List<Map<String,Object>> SYS_DO_PERMISSION=new ArrayList<>();
}
