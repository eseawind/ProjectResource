package com.qrCode.service;

import java.util.ArrayList;
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
		List<String> codeList=new ArrayList<>();
		for (String str : codes) {
			str=new String(str.getBytes("utf-8"),"utf-8");
			codeList.add(str);
		}
		rs.put("qrCodes",new String(qrCodeStr.getBytes("utf-8"),"utf-8"));
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
