package testjson;

import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;


public class MSExcelManager {
	private short encoding = HSSFWorkbook.ENCODING_UTF_16;
	private int cellType = HSSFCell.CELL_TYPE_STRING;
	public MSExcelManager() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		MSExcelManager msExcel = new MSExcelManager();

		String path = "d:\\"+ new Date().getTime() + ".xls";

		msExcel.write(msExcel.exportExcel(), path);
	}




	/**
	 * 报表导出excel格式
	 * 
	 * @param vistorList
	 * @param expertList
	 * @return
	 */
	public HSSFWorkbook exportExcel() {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		workBook.setSheetName(0, "temp1", encoding);
		sheet.setVerticallyCenter(true);
		sheet.setDefaultColumnWidth((short) 10);
		HSSFCellStyle tableStyle = getTableStyle(workBook,true);
		int beginRow = 0;//当前开始行
		int endRow = 3;	 //当前结束行
		int sumCol = 13; //一共多少列
		//当前开始行,当前结束行,一共多少列
		createRow(beginRow, endRow,sumCol,tableStyle,sheet);
		//第0行第0列开始 ;跨2行 到 第0列结束
		mergedRegion(0, 0, 2, 0,sheet);//合并单元格
		setValue(0, 0, "回答1", tableStyle, cellType,sheet);
		//第0行第1列开始 ;跨2行 到第1列结束
		mergedRegion(0, 1, 2, 1,sheet);//合并单元格
		setValue(0, 1, "回答2", tableStyle, cellType,sheet);
		//第0行第2列开始 ;跨2行 到第2列结束
		mergedRegion(0, 2, 2, 2,sheet);//合并单元格
		setValue(0, 2, "回答3", tableStyle, cellType,sheet);
		//第0行第3列 开始;跨2行 到第3列结束
		mergedRegion(0, 3, 2, 3,sheet);//合并单元格
		setValue(0, 3, "回答\n问题\n情况4\r\n", tableStyle, cellType,sheet);
		//第0行第4列 开始;跨0行 到第4列结束
		mergedRegion(0, 4, 0,12,sheet);//合并单元格
		setValue(0,4, "合并列ALL", tableStyle, cellType,sheet);
		
		//第1行第4列开始 ;跨0行 到第6列结束
		mergedRegion(1, 4, 0,6,sheet);//合并单元格
		setValue(1,4, "盘纸", tableStyle, cellType,sheet);
		
		//第1行第7列开始 ;跨0行 到第9列结束
		mergedRegion(1, 7, 0,9,sheet);//合并单元格
		setValue(1,7, "甘油", tableStyle, cellType,sheet);
		
		//第1行第10列开始 ;跨0行 到第9列结束
		mergedRegion(1, 10, 0,12,sheet);//合并单元格
		setValue(1,10, "丝数", tableStyle, cellType,sheet);
		
		//第2行第4列开始 ;跨0行 到第4列结束
		mergedRegion(2, 4, 0,4,sheet);//合并单元格
		setValue(2,4, "数字1", tableStyle, cellType,sheet);
		
		//第2行第5列开始 ;跨0行 到第5列结束
		mergedRegion(2, 5, 0,5,sheet);//合并单元格
		setValue(2,5, "数字2", tableStyle, cellType,sheet);
		
		//第2行第6列开始 ;跨0行 到第6列结束
		mergedRegion(2, 6, 0,6,sheet);//合并单元格
		setValue(2,6, "数字3", tableStyle, cellType,sheet);
		
		//第2行第7列开始 ;跨0行 到第7列结束
		mergedRegion(2, 7, 0,7,sheet);//合并单元格
		setValue(2,7, "数字4", tableStyle, cellType,sheet);
			
		return workBook;
	}

	/**
	 * 表格样式
	 * 
	 * @return
	 */
	public HSSFCellStyle getTableStyle(HSSFWorkbook workBook,boolean isBgGround) {
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上线居中
		if(isBgGround){
			style.setFillForegroundColor((short) 22);//背景颜色
			style.setFillBackgroundColor((short) 22);//背景颜色
			style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);//背景颜色
		}
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}

	
	public void createRow(int currentRow, int rowNum, int colNum,
			HSSFCellStyle style,HSSFSheet sheet) {
		for (int rowIndex = currentRow; rowIndex < rowNum; rowIndex++) {
			HSSFRow row = sheet.createRow(rowIndex);
			for (short cellIndex = 0; cellIndex < colNum; cellIndex++) {
				HSSFCell cell = row.createCell(cellIndex);
				cell.setEncoding(encoding);
				if (style != null) {
					cell.setCellStyle(style);
				}
				cell.setCellType(cellType);
				cell.setCellValue("");
			}
		}
	}
	public void mergedRegion(int rowFrom, int colFrom, int rowTo, int colTo,HSSFSheet sheet) {
		Region region = new Region(rowFrom, (short) colFrom, rowTo, (short) colTo);
		sheet.addMergedRegion(region);
	}
	public void setValue(int rowIndex, int colIndex, String value,
			HSSFCellStyle style, int type,HSSFSheet sheet) {
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell((short) colIndex);
		cell.setCellType(type);
		if (style != null) {
			cell.setCellStyle(style);
		}
		cell.setCellValue(value);
	}
	public void write(HSSFWorkbook workBook, String path) {

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			workBook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

