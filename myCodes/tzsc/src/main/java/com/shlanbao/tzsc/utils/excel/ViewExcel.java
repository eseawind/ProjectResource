package com.shlanbao.tzsc.utils.excel;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
* @ClassName: ViewExcel 
* @Description: Spring MVC Excel导出通用类，无合并单元格 
* @author luo 
* @date 2015年10月13日 上午9:50:52 
 */
public class ViewExcel extends AbstractExcelView{
	
	@Override
	public void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,HttpServletRequest request, HttpServletResponse response)   
            throws Exception {  
    	//获取名称
    	String excelName = model.get("name")!=null?model.get("name").toString():"work";
    	excelName+=".xls";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(excelName, "UTF-8"));
		List list = (List) model.get("list");
		// 产生Excel表头
		HSSFSheet sheet = workbook.createSheet("work");
		
		//创建一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		//设置边框样式
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//设置边框颜色
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		HSSFRow header = sheet.createRow(0); // 第0行
		header.setHeight((short)512);   
		//获取列宽
		if(model.get("lineWidth")!=null){
			Short[] title=(Short[])model.get("lineWidth");
			// 设置每一列的列宽
			for(int i=0;i<title.length;i++){
				sheet.setColumnWidth((short)i,(short)(title[i]*256));  
			}
		}
		
		//获取标题
		if(model.get("title")!=null){
			String[] title=(String[])model.get("title");
			// 产生标题列
			for(int i=0;i<title.length;i++){
				//sheet.autoSizeColumn((short)0);
				HSSFCell cell=header.createCell((short) i);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(title[i]);
				cell.setCellStyle(style);
			}
		}
		int rowNum = 1;
		if(model.get("class")!=null){
			Class c=(Class)model.get("class");
			String[] method=model.get("method")!=null?(String[])model.get("method"):null;
			if(method==null){
				throw new Exception("导出"+excelName+"报表时,未找到集合方法集合");
			}else{
				for(Object o:list){
					HSSFRow row = sheet.createRow(rowNum++);
					for(int i=0;i<method.length;i++){
						Method m=c.getDeclaredMethod(method[i]);
						String value=m.invoke(o)!=null?m.invoke(o).toString():"";
						HSSFCell cell=row.createCell((short) i);
						//设置为中文
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(value);
						cell.setCellStyle(style);
					}
				}
			}
		
		}
		
    }
	
}
