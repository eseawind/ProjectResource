package com.shlanbao.tzsc.utils.tools;

import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;

/**
 * 服务器启动时，访问数据库，加载所有实例化信息到静态类中
 * <li>@author Leejean
 * <li>@create 2014-7-5下午09:18:48
 */
@WebServlet("/InitRunServlet")
public class InitRunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(this.getClass());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitRunServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			// 检查 接收卷包机数据socket是否保持连接, 如果中断则要重新连接.
			Runnable rcvSocketThread = new Runnable() {
				@Override
				public void run() {
					System.out.println("vvvvvvvvvvvvvvvvvv");
					Thread.yield();
				}
			};
			ThreadManager.getInstance().addSchedule(rcvSocketThread,
					2, 3,TimeUnit.SECONDS);
		} catch (Exception e) {
			log.info("卷烟机socket状态检查线程异常");
		}
		
	}

}
