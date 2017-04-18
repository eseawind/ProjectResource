package com.shlanbao.tzsc.init;

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
@WebServlet("/InitResourceServlet")
public class InitResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(this.getClass());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitResourceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		LoadComboboxServiceI loadComboboxService=context.getBean(LoadComboboxServiceI.class);
		loadComboboxService.initCombobox(ComboboxType.ALL);
		loadComboboxService.initAllRunnedWorkOrderCalcValues();//项目启动初始化卷包机组物料系数
		/*loadComboboxService.initSoffice();
		loadComboboxService.getAllMatParams();
		loadComboboxService.querySysEqpType();*/
		
	}

}
