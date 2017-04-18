package com.lanbao.dws.common.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * WEB网络相关工具类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:14:15
 */
public class WebContextUtil {
	public final static String SESSION_INFO = "sessionInfo";
	/**
	 * 获得HttpServletResponse
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }	
	/**
	 * 设置cookie
	 * 
	 * @param response
	 *            Response对象
	 * @param cookName
	 *            名称
	 * @param cookValue
	 *            值
	 * @param cookTime
	 *            时间
	 */
	public static void setCookie(HttpServletResponse response, String cookName,
			String cookValue, int cookTime) {
		// 创建cookie
		Cookie cookie = new Cookie(cookName, cookValue);
		// 设置cookie路径
		cookie.setPath("/");
		// 设置cookie时间
		if (cookTime > -1) {
			cookie.setMaxAge(cookTime);
		}
		// 添加cookie
		response.addCookie(cookie);
	}
	/**
	 * 获取指定名称的Cookie值
	 * 
	 * @param request
	 * @param cookName
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) {
		// 保存cookie值
		String cookieValue = null;
		// 获取cookie值
		if (StringUtil.notNull(cookieName)) {
			Cookie cookies[] = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				// 循环获取cookie
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(cookieName)) {
						// 找到所需cookie跳出循环
						cookieValue = cookie.getValue();
						break;
					}
				}
			}
		}
		return cookieValue;
	}
	/**
	 * 清除所有的cookie
	 * 
	 * @param request
	 * @param response
	 */
	public static void clearAllCookie(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookies[] = request.getCookies();
		if (cookies.length > 0) {
			for (Cookie cookie : cookies) {
				Cookie ck = new Cookie(cookie.getName(), null);
				ck.setMaxAge(0);
				ck.setPath("/");
				response.addCookie(ck);
			}
		}
	}
	public static String getRemoteIp(HttpServletRequest request){
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				StringBuilder IFCONFIG=new StringBuilder();
			    try {
			        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			            NetworkInterface intf = en.nextElement();
			            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
			                InetAddress inetAddress = enumIpAddr.nextElement();
			                if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
			                	IFCONFIG.append(inetAddress.getHostAddress().toString()+",");
			                }

			            }
			        }
			    } catch (SocketException ex) {
			    }
			    ipAddress = IFCONFIG.toString();
			    if(ipAddress.length()>0){
			    	ipAddress =ipAddress.substring(0,(ipAddress.length()-1));
			    }else{
			    	// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (Exception e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
			    }
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割

		if (ipAddress != null && ipAddress.length() > 15) { 
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	/**
	 * 保存对象到session
	 * @author Leejean
	 * @create 2014年8月13日上午10:46:11
	 * @param session session
	 * @param key 键
	 * @param value 值
	 * @param lifeTime 生命周期 毫秒ms
	 */
	public static void saveObjToSession(HttpSession session,String key,Object value,int lifeTime){
		if(session.getAttribute(key)!=null){
			session.setAttribute(key, null);
		}
		session.setAttribute(key, value);
		session.setMaxInactiveInterval(lifeTime);
	}
	/**
	 * 获得session中的值
	 * @param key 键
	 * @return
	 */
	public static Object getSessionValue(HttpSession session,String key){
		return session.getAttribute(key);
	}
	/**
	 * 获得虚拟路径
	 * @param root
	 * @param request
	 * @return
	 */
	public static String getVirtualPath(String root,HttpServletRequest request) {
		ServletContext application = request.getServletContext();
		return application.getRealPath(root);
	}
	
	/***---新增方法 2014年11月14日---***/
	/**
	 * 获取request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request;
	}
	
	/**
	 * 获取session
	 * @return
	 */
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 获取sessionId
	 * @param session
	 * @return
	 */
	public static String getSessionId(){
		return getRequest().getSession().getId();
	}
	
	/**
	 * 从session中获取用户
	 * @return
	 */
	public static Object getObjectFromSession(){
		return getSession().getAttribute(SESSION_INFO) == null ? null : getSession().getAttribute(SESSION_INFO);
	}
	
	/**
	 * 从session中获取用户
	 * @return
	 */
	public static Object getObjectFromSession(HttpSession session){
		return session.getAttribute(SESSION_INFO) == null ? null : session.getAttribute(SESSION_INFO);
	}
}
