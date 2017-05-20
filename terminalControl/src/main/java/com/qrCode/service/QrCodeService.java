package com.qrCode.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baseCore.service.BaseService;
import com.commonUtil.StringUtils;

@Service
public class QrCodeService extends BaseService<QrCodeService> {
	public Map<String, Object> batchGenQrcode(Map<String, Object> rs) throws Exception {
		String qrCodeStr=StringUtils.toStr(rs.get("qrCodes"));
		String[] codes=qrCodeStr.split(",");
		List<Map<String,String>> codeList=new ArrayList<>();
		Map<String,String> qrCodeInfo=null;
		for (String str : codes) {
			qrCodeInfo=new HashMap<>();
			qrCodeInfo.put("qrCode", URLEncoder.encode(str, "UTF-8"));
			qrCodeInfo.put("remark", str);
			codeList.add(qrCodeInfo);
		}
		rs.put("qrCodeList", codeList);
		return rs;
	}

	@Override
	public List<Map<String, Object>> queryMapList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateEntity(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
