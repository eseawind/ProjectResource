package com.shlanbao.tzsc.utils.tools;

import com.opensymphony.xwork2.ActionSupport;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;

public class TestAction extends ActionSupport{
    private static final long serialVersionUID = 9999999999L;
	public static  boolean exec(RepairResquestBean rb){
		try {
			
			/**
			 * url   推送到的jsp页面
			 * funcationName   web接收端js方法
			 * message   入参
			 * DwrPush.pushMsgToAnyJSP("/wjcy_tpyl!viewImgs.action", "text2",nu);
			 * */
			DwrPush.pushMsgToAnyJSP("/wct/eqm/send_news.jsp", "text2",rb);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}

}
