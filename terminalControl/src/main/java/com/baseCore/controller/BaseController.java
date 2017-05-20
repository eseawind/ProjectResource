package com.baseCore.controller;

import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commonUtil.LoggerBean;
import com.commonUtil.LoggerGrade;
import com.commonUtil.StringUtils;

public class BaseController<T> {
	protected   Logger loger = LoggerFactory.getLogger(getType());
	@SuppressWarnings("unchecked")
	private Class<T> getType() {  
		Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }
	/**
	 * 操作结果标志
	 */
	protected static final String SUCCESS="success";
	/**
	 * 操作结果反馈
	 */
	protected static final String MSG="msg";
	protected static final String ERRMSG="err_msg";
	/**
	 * 新增操作
	 */
	protected static final String ADD="0";
	/**
	 * 删除操作
	 */
	protected static final String DELETE="1";
	/**
	 * 更新操作
	 */
	protected static final String UPDATE="2";
	/**
	 * 查询操作
	 */
	protected static final String QUERY="3";
	/**
	 * <p>功能描述：保存系统日志</p>
	 *@param request
	 *@param loggerGrade  日志等级
	 *@param operatation  操作
	 *@param content      内容
	 *@param remark       备注
	 *作者：SShi11
	 *日期：Apr 15, 2017 2:12:26 PM
	 */
	public void saveLog(HttpServletRequest request,LoggerGrade loggerGrade,String operatation,String content,String remark){
		String user=StringUtils.toStr(request.getSession().getAttribute("loginUserName"));
		LoggerBean loggerBean=new LoggerBean(getIP(request), user, operatation, loggerGrade, content, remark);
		loggerBean.saveLog(loggerBean);
	}
		
	
	/**
	 * <p>功能描述：获取客户端IP地址</p>
	 *@param request
	 *@return
	 *作者：SShi11
	 *日期：Apr 15, 2017 2:05:06 PM
	 */
	public String getIP(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For");
		if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个IP值，第一个IP才是真实IP
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		return request.getRemoteAddr();
	}
}
