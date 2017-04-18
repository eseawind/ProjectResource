package com.shlanbao.tzsc.utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.shlanbao.tzsc.utils.tools.MathUtil;


public class ExcelWriter{

	// 设置cell编码解决中文高位字节截断
	private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;
	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";
	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"
	private OutputStream out = null;
	private HSSFWorkbook workbook = new HSSFWorkbook();
	private HSSFSheet sheet = workbook.createSheet();
	private HSSFRow row = null;
	private HSSFCellStyle style=null;
	

	public ExcelWriter() {
	}

	//初始化excel
	public ExcelWriter(OutputStream out) {
		this.out = out;
		//this.workbook = new HSSFWorkbook();
		//this.sheet = workbook.createSheet();
		
		//表格样式
		this.style=workbook.createCellStyle();
		
		//边框
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);   
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); 
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 指定单元格居中对齐   
		
		//设置字体
		HSSFFont f = workbook.createFont();  
		f.setFontHeightInPoints((short)12);  
		f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
		style.setFont(f); 
		
	
		//设置列宽  
		sheet.setColumnWidth((short)0, (short)4000);  
		sheet.setColumnWidth((short)1, (short)3000);  
		sheet.setColumnWidth((short)2, (short)6000);  
		sheet.setColumnWidth((short)3, (short)6000);
		sheet.setColumnWidth((short)4, (short)6000);  
		sheet.setColumnWidth((short)5, (short)6000);  
		//sheet.setColumnWidth((short)5, (short)6000); 
		
		// 表头表格样式
		HSSFCellStyle style1=workbook.createCellStyle();  
		style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);   
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 指定单元格居中对齐
		HSSFFont f1 = workbook.createFont();  
		f1.setFontHeightInPoints((short)6);  
		f1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
		style1.setFont(f1);// 设置字体	
	}

	
	//导出Excel文件
	public void export() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ", e);
		}
	}

	//增加一行index表示行号
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	
	//获取单元格的值index列好
	public String getCell(int index) {
		HSSFCell cell = this.row.getCell((short) index);
		String strExcelCell = "";
		if (cell != null) { // add this condition
			// judge
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				strExcelCell = "FORMULA ";
				break;
			case HSSFCell.CELL_TYPE_NUMERIC: {
				strExcelCell = String.valueOf(cell.getNumericCellValue());
			}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				strExcelCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strExcelCell = "";
				break;
			default:
				strExcelCell = "";
				break;
			}
		}
		return strExcelCell;
	}
	/**
     * 设置单元格 index列号   value单元格填充值
     * 日期类型
     * 
     * */
	public void setDate(int index,Date value){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
        
		HSSFCell cell = this.row.createCell((short) index);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 	
		if(value != null){
			cell.setCellValue(simpleDateFormat.format(value));
		}else{
			cell.setCellValue("");
		}
		cell.setCellStyle(cellStyle); 
	}
    /**
     * 设置单元格 index列号   value单元格填充值
     * int类型
     * */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellType(HSSFCellStyle.ALIGN_LEFT);
		cell.setCellValue(value);
	}
	/**
     * 设置单元格 index列号   value单元格填充值
     * double类型
     * 
     * */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}
	/**
     * 设置单元格 index列号   value单元格填充值
     * String类型
     * 
     * */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 		
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(XLS_ENCODING);
		cell.setCellValue(value);
	}

	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellValue(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	/**
     * 第一列新样式标题
     * 
     * */
	//设置单元格 index列号  value单元格填充值
	public void setCellTitle(int index, String value) {
		sheet.addMergedRegion(new Region(0,(short)0,0,(short)5));
		HSSFCell cell = this.row.createCell((short) index);
		 //生成一个字体
        HSSFFont font1=workbook.createFont();
        font1.setColor(HSSFColor.BLACK.index);
        font1.setFontHeightInPoints((short)12);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
        //把字体应用到当前的样式
		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		cellStyle.setFont(font1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
     * 第一列新值
     * 不能合并单元格
     * 
     * */
	//设置单元格 index列号  value单元格填充值
	public void setCellFirst(int index, String value) {
		//sheet.addMergedRegion(new Region(rowcoo,(short)cumcoo,rowspan,(short)cumspan));
		HSSFCell cell = this.row.createCell((short) index);
		 //生成一个字体
        HSSFFont font1=workbook.createFont();
        font1.setColor(HSSFColor.BLACK.index);
        font1.setFontHeightInPoints((short)10);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
      //把字体应用到当前的样式
		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		cellStyle.setFont(font1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}
	
	//循环遍历封装值
	public void ExcelWriter(List<Object[]> dg,String np){
		File f = new File(np);
		ExcelWriter e = new ExcelWriter();
		try {
			e = new ExcelWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        int y=0;
        e.createRow(0);
        e.setCellTitle(0,"设备有效作业率" );
        e.createRow(1);
        e.setCellFirst(0,"设备名称" );
		e.setCellFirst(1, "班次");
		e.setCellFirst(2, "总时间");
		e.setCellFirst(3,"实际产量");
		e.setCellFirst(4, "车间实际产量");
		e.setCellFirst(5,"有效作业率" );
		//e.setCellFirst(6, "实际有效作业率");
		
		for(int i=0;i<dg.size();i++){
			Object[] w=(Object[])dg.get(i);
			e.createRow(i+2);
			y++;
			if(y>0){
				y--;
				try {
					e.setCell(y,   w[0].toString());
					e.setCell(1,   w[2].toString());
					e.setCell(y+2, w[4].toString());
					e.setCell(y+3, w[5].toString());
					e.setCell(y+4, w[3].toString());
					e.setCell(y+5, this.getEfic(w[3].toString(), w[4].toString()));
					//e.setCell(y+6, "");
				} catch (Exception e1) {
					// TODO: handle exception
				}
			}
		}

		try {
			e.export();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	
	private String getEfic(String qty,String r_qty){
		if(Float.parseFloat(r_qty)==0){
			return  "0";
		}else{
			BigDecimal a1=new BigDecimal(Double.parseDouble(qty));
			BigDecimal b1=new BigDecimal(Double.parseDouble(r_qty));
			Double st=MathUtil.roundHalfUp((a1.divide(b1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2);
			if(st>120){
				st=96.1; //有效作业率大于100，给出默认值90.1，防止数据错误，导致误差太大(测试数据，误差大正常)；wch 2015年10月13日
			}
			if(st==0){
				return "0";
			}
			return st.toString();
		}
	}
	
	
	
	/**
	 * 张璐-2015.10.14
	 * 设置列，带合并单元格的
	 * @param index
	 * @param value
	 * @param rowcoo:行坐标
	 * @param cumcoo:列坐标
	 * @param rowspan:跨行
	 * @param cumspan:跨列
	 */
	//设置单元格 index列号  value单元格填充值
	public void setCellFirstSpan(int index, String value,int rowcoo,int cumcoo,int rowspan,int cumspan) {
		sheet.addMergedRegion(new Region(rowcoo,(short)cumcoo,rowspan,(short)cumspan));
		HSSFCell cell = this.row.createCell((short) index);
		 //生成一个字体
        HSSFFont font1=workbook.createFont();
        font1.setColor(HSSFColor.BLACK.index);
        font1.setFontHeightInPoints((short)10);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
      //把字体应用到当前的样式
		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		cellStyle.setFont(font1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

}
