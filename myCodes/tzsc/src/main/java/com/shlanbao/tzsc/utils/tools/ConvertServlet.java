package com.shlanbao.tzsc.utils.tools;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;










import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 
 * @author liuligong
 * @create 2014-8-14下午07:03:44
 * @return
 */
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private File sourceFile;  //目标源文件
    private File pdfFile;     //PDF目标文件
    private File swfFile;     //swf目标文件
    private Runtime runtime;
    private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
    protected Logger log = Logger.getLogger(this.getClass());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConvertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		File uploadPath = new File(bundle.getString("save_url")+bundle.getString("upload"));
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,HttpSession session, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileId = request.getParameter("fileId");
		String path=bundle.getString("service_url")+fileId;
		session.setAttribute("service_urlf", path);
		response.sendRedirect(request.getContextPath()+"/pms/FileManage/flexpaper/readFile.jsp");
	}
	
	
}
