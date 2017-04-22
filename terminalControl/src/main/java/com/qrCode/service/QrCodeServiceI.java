package com.qrCode.service;

import java.util.Map;

import com.baseCore.service.BaseServiceI;

public interface QrCodeServiceI extends BaseServiceI {
	/**
	 * <p>功能描述：根据输入的数据批量生成二维码</p>
	 *@param rs
	 *@return
	 *@throws Exception
	 *作者：SShi11
	 *日期：Apr 22, 2017 3:43:40 PM
	 */
	Map<String, Object> batchGenQrcode(Map<String, Object> rs) throws Exception;

}
