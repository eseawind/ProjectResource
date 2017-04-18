package com.shlanbao.tzsc.utils.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
/**
 * 文件操作类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午05:06:23
 */
public class FileOptionsUtil {
	private static Logger log=Logger.getLogger(FileOptionsUtil.class);
	/**
	 * 获取文件的扩展名获得     ->.jpg
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public static String getFileName(String fileName) {
		int pos = fileName.lastIndexOf('.');
		if (pos == -1) {
			return "";
		} else {
			return fileName.substring(0,pos);
		}
	}
	
	/**
	 * @param url       写入路径
     * @param content   写入内容
     * @param encoder   写入编码
     * @param append    false：覆盖原有文件。ture：在原有文件后追加内容。 
	 */
	public static boolean writeFile(String url, String content, String encoder, boolean append){
		File file = new File(url);
		String parent = file.getParent();
		File dir = new File(parent);
		if (!dir.exists()) dir.mkdirs();    //如果目录和文件不存在，创建目录和文件
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(url, append);
			fos.write(content.getBytes(encoder));
			fos.close();
			return true;
		} catch (Exception e) {
			log.error("写入文件失败", e);
			return false;
		}
	}
	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean upload(File upload, String fileName,
			String parentMenu) {
		
			if (upload == null) {
				return false;
			}
			// 3、保存上传的文件到upload目录下
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				File fileRootDir = new File(parentMenu);
				if (!fileRootDir.exists())
					fileRootDir.mkdirs();
				// 2、目录
				fis = new FileInputStream(upload);
				fos = new FileOutputStream(new File(fileRootDir, fileName));
				// 批量，每次传输2K
				byte[] b = new byte[2 * 1024];
				int len = fis.read(b);
				while (len > 0) {
					fos.write(b, 0, len);
					len = fis.read(b);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("上传失败", e);
			} finally {
				try {
					// 4、关闭流
					if (fos != null) {
						fos.flush();
						fos.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("关闭流失败", e);
				}
			}
			return true;
	}

	/**
	 * 文件下载
	 * 
	 * @param filePath
	 *            下载文件的完整路径
	 * @param response
	 *            响应对象
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static boolean download(String filePath,
			HttpServletResponse response) {
		
		    // 将服务器上的文件写入本地文件
			OutputStream outputStream = null;
			// 读取服务器上的文件信息
			InputStream inputStream = null;
			// 文件名,输出到用户的下载对话框
			String fileName = null;
			try {
			if (filePath.lastIndexOf("/") != -1) {
				// 从文件完整路径中提取文件名,转码是防止中文不能正确显示
				fileName = new String(filePath.substring(
						filePath.lastIndexOf("/") + 1).getBytes("utf-8"),
						"ISO-8859-1");
			} else if (filePath.lastIndexOf("\\") != -1) {
				fileName = new String(filePath.substring(
						filePath.lastIndexOf("\\") + 1).getBytes("utf-8"),
						"ISO-8859-1");
			}
				inputStream = new FileInputStream(filePath);
				response.setContentType("text/plain");
				response.setHeader("Location", fileName);
				response.setHeader("Content-Disposition",
						"attachment;filename=" + fileName);
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
	
	
	/**
	 * 文件下载
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @param response 响应对象
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean download2(String filePath,String fileName,
			HttpServletResponse response) {
		
		    // 将服务器上的文件写入本地文件
			OutputStream outputStream = null;
			// 读取服务器上的文件信息
			InputStream inputStream = null;
			// 文件名,输出到用户的下载对话框
			//String fileName = null;
			try {
				inputStream = new FileInputStream(filePath);
				response.setContentType("text/plain");
				response.setHeader("Location", fileName);
				response.setHeader("Content-Disposition",
						"attachment;filename=" + fileName);
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
	/**
	 * 读取普通文本文档中的内容
	 * 
	 * @param sPath
	 *            文件路径
	 * @return 字符串 .txt .java .xml
	 */
	public static String readTXT(String sPath) {
		StringBuffer sb = new StringBuffer();
		try {
			String encoding = "utf-8";
			File file = new File(sPath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt + "\n");
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return sb != null ? sb.toString() : "";
	}
	public static boolean deleteFileurl(String filePath) {  
		try {
			File file = new File(filePath); 
			if(file.exists()){
				file.delete();
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		return true;
	} 
}
