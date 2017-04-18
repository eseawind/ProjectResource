package com.shlanbao.tzsc.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Session过滤器
 * 用于过滤需要拦截的JSP文件
 * <li>@author Leejean
 * <li>@create 2014-7-10下午12:30:08
 */
public class SessionFilter implements Filter {

	private static final Logger logger = Logger.getLogger(SessionFilter.class);


	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String servletPath = request.getServletPath();
		
			logger.info("Into SessionFilter ->The url is [" + servletPath + "]");
			
			if (servletPath.toUpperCase().equals("/PMS/")||servletPath.toUpperCase().equals("/PMS")){
				request.getRequestDispatcher("/pms/sys/user/login.jsp").forward(request, response);
				return;
			}
			if (servletPath.toUpperCase().equals("/WCT/")||servletPath.toUpperCase().equals("/WCT")){
				request.getRequestDispatcher("/wct/sys/login.jsp").forward(request, response);
				return;
			}
			if (servletPath.toUpperCase().equals("/ISP/")||servletPath.toUpperCase().equals("/ISP")){
				request.getRequestDispatcher("/isp/login.jsp").forward(request, response);
				return;
			}
			
			if(servletPath.startsWith("/pms")
					&&request.getSession().getAttribute("sessionInfo") == null
					&&!servletPath.trim().contains("/pms/sys/user/login.jsp")
					&&!servletPath.trim().contains("/error/noSession.jsp")){
					logger.info("Into SessionFilter ->PMS 登录超时");
					request.setAttribute("loginUrl", "/pms");
					request.getRequestDispatcher("/error/noSessionAutoRedirect.jsp").forward(request, response);
					return;
			}
			if(servletPath.startsWith("/wct")
					&&request.getSession().getAttribute("loginInfo") == null
					&&!servletPath.trim().contains("/wct/sys/login.jsp")
					&&!servletPath.trim().contains("/error/noSession.jsp")){
				 	logger.info("Into SessionFilter ->WCT 登录超时");
				 	request.setAttribute("loginUrl", "/wct");
					request.getRequestDispatcher("/error/noSessionAutoRedirect.jsp").forward(request, response);
					return;
			}
			if(servletPath.startsWith("/isp")
					&&request.getSession().getAttribute("ispSessionInfo") == null
					&&!servletPath.trim().contains("/isp/login.jsp")
					&&!servletPath.trim().contains("/error/noSession.jsp")){
					logger.info("Into SessionFilter ->ISP 登录超时");
					request.setAttribute("loginUrl", "/isp");
					request.getRequestDispatcher("/error/noSessionAutoRedirect.jsp").forward(request, response);
					return;
			}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {
		
	}
}
