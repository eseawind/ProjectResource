package com.lanbao.dws.common.data.refresh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.common.tools.ApplicationContextUtil;
import com.lanbao.dws.service.init.InitComboboxData;

/**
 * 缓存刷新
 * 
 * */
@Controller
@RequestMapping("/wct/refrush")
public class FLXSParamRefreshConttoller {
	
	/***
	 * 【共能说明】：刷新快照缓存数据
	 * @author wanchanghuang
	 * @date 2016年8月4日10:38:29
	 * @param  code 1:滚轴及辅料系数刷新     2:设备
	 * */
	@RequestMapping("/refreshEqpData")
	public String getResultPagePath(){
		try {
			InitComboboxData comboboxs=ApplicationContextUtil.getBean(InitComboboxData.class);
			comboboxs.initCombobox();
			comboboxs.initWorkOrderInfo();
			return Constants.RESULT_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.RESULT_ERROR;
		}
	}

}
