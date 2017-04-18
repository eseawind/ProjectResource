package com.shlanbao.tzsc.utils.tools;

import net.sf.json.JSONArray;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContextFactory;

import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;


public class DwrPush {
	
	/**
	 * 推送信息到任意页面
	 * 
	 * @param url
	 *            除去项目根路径外的action路径名，以"/"开头
	 * @param functionName
	 *            前台页面JS函数名
	 * @param message
	 *            需要推送的信息
	 * @author  pushMsgToAnyJSP(String url, final String functionName,final Object... message)
	 * */
	public static void pushMsgToAnyJSP(String url, final String functionName,final RepairResquestBean rb) {
		Browser.withPage(ServerContextFactory.get().getContextPath() + url,new Runnable() {
					public void run() {
						//将对象转转成字符串
						JSONArray json=new JSONArray().fromObject(rb);
					    String jsonstr = json.toString();
						ScriptSessions.addFunctionCall(functionName, jsonstr);
					}
				});
	}


}
