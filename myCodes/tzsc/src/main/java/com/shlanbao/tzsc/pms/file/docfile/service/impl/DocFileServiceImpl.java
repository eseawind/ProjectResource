package com.shlanbao.tzsc.pms.file.docfile.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.shlanbao.tzsc.base.dao.DocFileDaoI;
import com.shlanbao.tzsc.base.mapping.DocFile;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.file.docfile.beans.DocFileBean;
import com.shlanbao.tzsc.pms.file.docfile.service.DocFileServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;

@Service
public class DocFileServiceImpl extends BaseService implements DocFileServiceI {
	@Autowired
	private DocFileDaoI fileDaoI;
	private File sourceFile;  //目标源文件
	private File pdfFile;     //PDF目标文件
	private File swfFile;     //swf目标文件
    private Runtime runtime;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	/**
	 *查询所有文件 
	 */
	@Override
	public List<DocFileBean> getDocFileAll(DocFileBean fileBean)
			throws Exception {
		List<DocFileBean> df = new ArrayList<DocFileBean>();

		StringBuilder hql = new StringBuilder();
		hql.append("from DocFile d  where 1=1 and d.attr=0 order by d.type desc");
		List<DocFile> docFiles = fileDaoI.query(hql.toString());
		if(docFiles!=null){
			for (DocFile docfile : docFiles) {
	
				DocFileBean dffile = new DocFileBean();
				dffile = BeanConvertor.copyProperties(docfile, DocFileBean.class);
				if (docfile.getDocFile() != null) {
					dffile.setPid(docfile.getDocFile().getId());
				}
				dffile.setSysUserName(docfile.getSysUser().getName());
				if (dffile.getType().equals("1")) {
					dffile.setIconCls("icon-hamburg-folder");
				}
				df.add(dffile);
			}
		}
		return df;
	}

	@Override
	public void deleteDocFile(String Id) {

		fileDaoI.deleteById(Id, DocFile.class);
	}

	/**
	 * 文件上传
	 * 修改：shisihai
	 */
	@Override
	public String uploadDocFile(String parentId, HttpServletRequest request,HttpSession session) throws Exception {
		request.setCharacterEncoding("utf-8");
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		String[] UrlfileNames=(String[]) request.getParameterValues("urlfileName")[0].split(",");
		String err = null;
		// 获取文件名
		String[] fileUrls = (String[]) request.getParameterValues("fileUrl")[0].split(",");
		String[] fileNames = (String[]) request.getParameterValues("fileName")[0].split(",");
		// 取到 pid
		DocFile dbdocFile = fileDaoI.findById(DocFile.class, parentId);
		String pid;
		if (dbdocFile.getType().equals("2")) {
			// 表示是文件
			pid = dbdocFile.getDocFile().getId();
		} else {
			// 文件夹
			pid = dbdocFile.getId();
		}
		for (int i = 0; i < fileUrls.length; i++) {
			if (this.DocFileByFileName(fileNames[i].substring(0,fileNames[i].lastIndexOf(".")))) {
				DocFile docFile = new DocFile();
				docFile.setDocFile(new DocFile(pid));
				docFile.setCreateTime(new Date());
				docFile.setFilename(fileNames[i].substring(0,fileNames[i].lastIndexOf(".")));
				docFile.setFileType(fileUrls[i].substring(fileUrls[i].lastIndexOf(".") + 1));
				docFile.setType("2");
				docFile.setUrl(fileUrls[i]);//修改后，
				//docFile.setUrl(bundle.getString("upload")+"/"+UrlfileNames[i]+"."+fileUrls[i].substring(fileUrls[i].lastIndexOf(".") + 1));
				docFile.setAttr("0");
				docFile.setSysUser(new SysUser(sessionInfo.getUser().getId()));
				fileDaoI.save(docFile);
			} else {
				err = "此文件已经存在！";
			}
		}
		return err;
	}
	/*public void init( HttpServletRequest request) {
		File uploadPath = new File(request.getServletContext().getRealPath(bundle.getString("upload")));
		File previewPDF = new File(request.getServletContext().getRealPath(bundle.getString("pdf")));
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
		if (!previewPDF.exists()) {
			previewPDF.mkdir();
		}	
	}*/
	/**
	 * file初始化
	 * @param sourceFilePath  源文件
	 * @param pdfPath pdf file
	 * @param swfPath swf file
	 */
	protected void initFilePath(String sourceFilePath,String pdfPath,String swfPath){
		//判断源文件是否为PDF文件
		if("pdf".equals(sourceFilePath.substring((sourceFilePath.lastIndexOf(".")+1)))){
			sourceFile = new File(sourceFilePath);
			pdfFile = new File(pdfPath);
			swfFile = new File(swfPath);
		}else{
			sourceFile = new File(sourceFilePath);
			pdfFile = new File(pdfPath);
			swfFile = new File(swfPath);
		}
		System.out.println("文件对象已生成，准备转换..");
	}
	/**
	 * 文件转换为swf格式
	 * @throws IOException
	 */
	protected void fileConvert() throws IOException{
		System.out.println(pdfFile.getName());
		if(sourceFile.exists()){
			if(!pdfFile.exists()){
				OpenOfficeConnection connection = null;
				try {
					connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
					converter.convert(sourceFile, pdfFile);//源文件转换为PDF
					pdfFile.createNewFile();
					connection.disconnect();
					System.out.println("转换为PDF文件:"+pdfFile.getPath());
				} catch (ConnectException e) {
					System.out.println("openOffice 服务未启动: ");
					e.printStackTrace();
					throw e;
				} catch (OpenOfficeException e){
					System.out.println("文件读取失败: ");
					e.printStackTrace();
					throw e;
				} catch (Exception e){
					System.out.println("未知异常:");
					e.printStackTrace();
				}finally{//add by luther.zhang 20150129防止报错后不释放链接
					if(connection!=null){
						connection.disconnect();
					}
				}
		} else {
			System.out.println("Pdf文件已经转换，无需再次转。");
		} 
		}else{
			System.out.println("要转换的源文件不存在， 可能是路径错误。");
		}
		
		runtime = Runtime.getRuntime();
		
		new Thread(){
			
			@Override
			public void run(){
				if(!swfFile.exists()){
					if(pdfFile.exists()){
						try {
							//System.out.println(ConfigParameter.psPath +
							//pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
							//Process p = runtime.exec(ConfigParameter.psPath + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");//单独的进程中执行指定的字符串命令
							//p.waitFor();//当前线程等待，如有必要，一直要等到由该 Process 对象表示的进程已经终止
							
							//runtime.exec(ConfigParameter.psPath + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");//单独的进程中执行指定的字符串命令
							String command=bundle.getString("exePath")+" -t \""+pdfFile.getPath()+"\" -o \""  
									+swfFile.getPath()+"\" -T 9";
							System.out.println(command);
							runtime.exec(command);//单独的进程中执行指定的字符串命令
							
							swfFile.createNewFile();
							
							System.out.println("swfFile 格式路径："+swfFile.getPath());
							
							System.out.println("swfFile 格式名称："+swfFile.getName());
							
						} catch (IOException e) {
							System.out.println("IOException:");
							e.printStackTrace();
						} /*catch (InterruptedException e) {
							System.out.println("InterruptedException:");
							e.printStackTrace();
						}*/
						
					} else {
						System.out.println("Pdf文件格式不存在，无法转换");
					}
				} else {
					System.out.println("已转换为SWF格式,无需再次转换!");
				}
			}
			
		}.start();
	}
	@Override
	public DocFile DocFileById(String Id) throws Exception {

		return fileDaoI.findById(DocFile.class, Id);
	}

	/**
	 * 文件下载
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @param response
	 *            响应对象
	 * @return
	 */
	public boolean download(String filePath, String fileName,
			HttpServletResponse response) {

		// 将服务器上的文件写入本地文件
		OutputStream outputStream = null;
		// 读取服务器上的文件信息
		InputStream inputStream = null;
		// 文件名,输出到用户的下载对话框
		// String fileName = null;
		try {

			inputStream = new FileInputStream(filePath);
			response.setContentType("text/plain");
			response.setHeader("Location", fileName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}

		} catch (Exception e) {
			log.error("下载失败", e);
			return false;
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("关闭流失败", e);
			}
		}
		return true;
	}

	@Override
	public String deleteFile(String id) throws Exception {
		// 判断有没有子节点
		DocFile docFile = fileDaoI.findById(DocFile.class, id);
		Set<DocFile> docFiles = docFile.getDocFiles();
		String err = null;
		int str=0;
		for (DocFile docFile2 : docFiles) {
			if(docFile2.getAttr().equals("0")){
				str++;
			}
		}
		if (str > 0) {
			err = "请先删除文件夹里的文件";
		} else {
			docFile.setAttr("1");
		}
		return err;
	}
/**
 * 添加文件夹
 * 
 */
	@Override
	public String saveFile(String parentId, String filename,
			HttpServletRequest request, HttpSession session) throws Exception {
		request.setCharacterEncoding("utf-8");
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		String err = null;
		if (this.DocFileByFileName(filename)&& StringUtil.notNull(filename)) {
			String pid;
			String url;
			// 取到 pid
			if(StringUtil.notNull(parentId)){
				DocFile dbdocFile = fileDaoI.findById(DocFile.class, parentId);
			
				if (dbdocFile.getType().equals("2")) {
					// 表示是文件
					pid = dbdocFile.getDocFile().getId();
					url = dbdocFile.getDocFile().getUrl();
				} else {
					// 文件夹
					pid = dbdocFile.getId();
					url = dbdocFile.getUrl();
				}
			}else{
				pid=null;
				url ="upload";
				
			}
			System.out.println(request.getServletContext()
					.getRealPath("upload"));

			DocFile docFile = new DocFile();
			if(StringUtil.notNull(pid)){
			docFile.setDocFile(new DocFile(pid));
			}else{
				docFile.setDocFile(null);
			}
			// docFile.setCreateTime(new Date());
			docFile.setFilename(filename);
			docFile.setFileType(null);
			docFile.setType("1");
			docFile.setUrl(url);
			docFile.setAttr("0");
			docFile.setSysUser(new SysUser(sessionInfo.getUser().getId()));
			fileDaoI.save(docFile);
			
		} else {
			err = "此文件已经存在、或者不能为空";

		}
		return err;
	}

	@Override
	public boolean DocFileByFileName(String fileName) throws Exception {
		List<DocFile> docFiles = fileDaoI.query(
				"from DocFile d where d.filename=?  and d.attr='0'", fileName);
		if (docFiles.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String updatefile(DocFileBean docFileBean, HttpSession session) {
		String err = null;
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		if (sessionInfo.getUser() == null) {
			err = "请登录后修改";
		} else {
		DocFile file=	fileDaoI.findById(DocFile.class, docFileBean.getId());
			file.setFilename(docFileBean.getFilename());
			file.setSysUser( new SysUser(sessionInfo.getUser().getId()));
		}
		return err;
	}
	

	
}
