package com.shlanbao.tzsc.base.interceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.sys.log.beans.LogBean;
import com.shlanbao.tzsc.pms.sys.log.service.LogServiceI;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
/**
 * PMS权限和日志拦截器
 * @author Leejean
 * @create 2015年1月22日下午2:07:25
 */
public class SecurityAndLogInterceptor implements HandlerInterceptor {

	private Logger log = Logger.getLogger(SecurityAndLogInterceptor.class);
	
	public SecurityAndLogInterceptor() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	private LogServiceI logService;
	
	private String mappingURL;//利用正则映射到需要拦截的功能路径 add  del  edit 
	private String whiteList = "";
	private String blackList = "";
	private Long execStart =0L;
	private SessionInfo sessionInfo = null;
	private String optName = null;
	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}
	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}
    public void setMappingURL(String mappingURL) {  
           this.mappingURL = mappingURL;  
    } 
	/**
	 * 在业务处理器处理请求之前被调用
	 * 如果返回false
	 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true
	 *    执行下一个拦截器,直到所有的拦截器都执行完毕
	 *    再执行被拦截的Controller
	 *    然后进入拦截器链,
	 *    从最后一个拦截器往回执行所有的postHandle()
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String servletPath=request.getServletPath();
		execStart = System.currentTimeMillis();
		
		log.info("进入拦截器,当前请求路径:【"+servletPath+"】");
		
		if(servletPath.startsWith("/wct")||servletPath.startsWith("/plugin")){
			log.info("WCT||Plugin requests 无需权限过滤！");
			return true;
		}
		
		final Object obj = request.getSession().getAttribute("sessionInfo");
		sessionInfo  = (SessionInfo)obj ;	
		
		if(obj==null&&(!servletPath.contains("login.do"))){//session过期(登录不考虑)
			log.info("会话过期！ ");
			request.getRequestDispatcher("/error/noSession.jsp").forward(request, response);
			return false;
		}
		
		if (!Pattern.compile(whiteList).matcher(servletPath).find()) {//白名单  例如 查询用户登录后加载目录和收藏，不属于权限管控范围，加入白名单
			log.info("本路径已列入白名单！");
			return true;
		}
		
		if (!Pattern.compile(mappingURL).matcher(servletPath).find()) {
			// 如果要访问的资源是不需要验证的的url 除带add edit del 其他一律过
			log.info("当前路径无需拦截！");
			return true;
		}
		
		
		
		
		optName = sessionInfo.getResourcesMap().get(servletPath);
		if (optName==null) {// 如果当前用户没有访问此资源的权限
			log.info("当前用户没有权限访问本功能路径！");
			request.getRequestDispatcher("/error/noSecurity.jsp").forward(request, response);
			return false;
		}
		
		//日志保存
		this.saveLog(request, execStart);
		
		return true;
		
	}
	private void saveLog(HttpServletRequest request,
			Long execStart) throws Exception {
		//执行保存日志
		final LogServiceI logService= ApplicationContextUtil.getBean(LogServiceI.class);		
		String paramsStr = getRequestParams(request);
		long execEnd = System.currentTimeMillis();
		long ms = (execEnd-execStart);
		log.info("存储操作日志->执行耗时:"+ms+" ms");
		
        	logService.saveLog(new LogBean(
        			sessionInfo.getUser().getName(), 
        			sessionInfo.getIp(), 
        			"PMS", 
        			optName, 
        			"操作成功", 
        			paramsStr,
        			ms,
        			0L));
	}
	/**
	 * 获得请求参数
	 * @author Leejean
	 * @create 2014年9月4日下午1:17:45
	 * @param request
	 * @return
	 */
	private String getRequestParams(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParams = request.getParameterMap();
		if(requestParams==null){
			return null;
		}
		StringBuffer paramsStr= new StringBuffer();
		paramsStr.append("{");
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			String[] value = entry.getValue();
			if(value!=null&&value.length>0){
				paramsStr.append(entry.getKey()).append(":").append(Arrays.toString(value)).append(",");				
			}
		}
		paramsStr.append("}");
		return paramsStr.toString();
	}

	//在业务处理器处理请求执行完成后,生成视图之前执行的动作 
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用 
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
