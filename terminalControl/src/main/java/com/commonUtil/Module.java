package com.commonUtil;

public enum Module {
	/**
	 * 主模块。默认模块
	 */
	M;
	public static String getVal(Module grad){
		String reS=null;
		switch (grad){
			case M:
				reS="M";
				break;
			default: reS="M";
		}
		return reS;
	}
}
