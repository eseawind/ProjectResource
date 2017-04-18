package testjson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;


public class MSExcelDemo {
	private short encoding = HSSFWorkbook.ENCODING_UTF_16;
	private int cellType = HSSFCell.CELL_TYPE_STRING;
	public MSExcelDemo() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)  throws IOException {
		MSExcelDemo ee = new MSExcelDemo(); 
		FileOutputStream fos;
		try {
			//th 当前开始行,当前结束行,一共多少列
			int[] thTables={1,3,13};
			List<String> th=new ArrayList<String>();
			//第1行;第1列开始 ~ 第1列结束 一共 跨3行
			th.add("1,1,1,3,跨行1");
			th.add("1,2,2,3,跨行2");
			th.add("1,3,3,3,跨行3");
			th.add("1,4,4,3,回答\n问题\n情况4");//第0行第3列 开始;跨2行 到第3列结束
			th.add("1,5,13,1,合并列ALL");
			
			th.add("2,5,7,1,盘纸");
			th.add("2,8,10,1,甘油");
			th.add("2,11,13,1,丝数");
			
			th.add("3,5,5,1,数字1");
			th.add("3,6,6,1,数字2");
			th.add("3,7,7,1,数字3");
			th.add("3,8,8,1,数字1");
			th.add("3,9,9,1,数字2");
			th.add("3,10,10,1,数字3");
			th.add("3,11,11,1,数字1");
			th.add("3,12,12,1,数字2");
			th.add("3,13,13,1,数字3");
			
			List<LoginBean> queryList = new ArrayList<LoginBean>();
			for(int i=0;i<3;i++){
				LoginBean bean = new LoginBean();
				bean.setLoginName("张圣军"+i);
				bean.setEquipmentIp("127.0.0.1");
				queryList.add(bean);
			}
			Map<String,String> maps = new HashMap<String,String>();
			List<Map<String,String>> tdVals =new ArrayList<Map<String,String>>();
			for(int i=0;i<queryList.size();i++){
				LoginBean bean = queryList.get(i);
				maps.put((4+i)+",1,1,1",bean.getLoginName());//第4行;第1列开始 ~ 第1列结束 一共 跨1行
				maps.put((4+i)+",2,2,1",bean.getEquipmentIp());//第4行;第2列开始 ~ 第2列结束 一共 跨1行
				maps.put((4+i)+",3,3,1",i+"");
				maps.put((4+i)+",4,4,1",""+i);
				maps.put((4+i)+",5,5,1",bean.getEquipmentIp());
				maps.put((4+i)+",6,6,1",bean.getEquipmentIp());
				maps.put((4+i)+",7,7,1",bean.getEquipmentIp());
				maps.put((4+i)+",8,8,1",bean.getEquipmentIp());
				maps.put((4+i)+",9,9,1",bean.getEquipmentIp());
				maps.put((4+i)+",10,10,1",bean.getEquipmentIp());
				maps.put((4+i)+",11,11,1",bean.getEquipmentIp());
				maps.put((4+i)+",12,12,1","s");
				maps.put((4+i)+",13,13,1",bean.getEquipmentIp());
				tdVals.add(maps);
			}
			//合计
			maps.put(4+tdVals.size()+",1,4,1","合计");
			tdVals.add(maps);
			//td 当前开始行,当前结束行,一共多少列
			int[] tdTables={4,(4+tdVals.size()-1),13};
			
			
			HSSFWorkbook wb = ee.exportExcel(thTables,th,tdTables,tdVals);
			fos = new FileOutputStream("d:\\foo.xls");
			wb.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 报表导出excel格式
	 * @param thTables	th表头表格
	 * @param ths		th表格对应的内容
	 * @param tdTables	td表格
	 * @param tdVals	td表格对应的内容
	 * @return
	 */
	public HSSFWorkbook exportExcel(int[] thTables, List<String> ths,
			int[] tdTables, List<Map<String,String>> tdVals) {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		workBook.setSheetName(0, "temp1", encoding);
		sheet.setVerticallyCenter(true);
		sheet.setDefaultColumnWidth((short) 10);
		HSSFCellStyle tableStyle = getTableStyle(workBook,true);
		HSSFCellStyle tdStyle = getTableStyle(workBook,false);
		if(null!=thTables){//th表格
			//当前开始行,当前结束行,一共多少列
			createRow((thTables[0]-1), thTables[1],thTables[2],tableStyle,sheet);
		}
		if(null!=ths){//th跨行跨列,合并单元格
			for(String str:ths){
				String[] args = str.split(",");
				//第0行 第0列开始 ;跨2行 到 第0列结束
				mergedRegion(Integer.parseInt(args[0]) - 1,
						Integer.parseInt(args[1]) - 1,
						Integer.parseInt(args[3]) - 1,
						Integer.parseInt(args[2]) - 1, sheet);// 合并单元格
				setValue(Integer.parseInt(args[0]) - 1,
						Integer.parseInt(args[1]) - 1, 
						args[4], 
						tableStyle,cellType, sheet);
			}
		}
		if(null!=tdTables){//当前开始行,当前结束行,一共多少列
			createRow((tdTables[0]-1), tdTables[1],tdTables[2],tdStyle,sheet);
		}
		if(null!=tdVals){//td对应的值
			for(int i=0;i<tdVals.size();i++){
				Map<String,String > mapObj = tdVals.get(i);
				for (Map.Entry<String, String> entry : mapObj.entrySet()){
					 String key=entry.getKey();//次方法获取键值对的名称 
					 String value=entry.getValue();//次方法获取键值对的值 
					 String[] args = key.split(",");
					 mergedRegion(Integer.parseInt(args[0]) - 1,
								Integer.parseInt(args[1]) - 1,
								Integer.parseInt(args[3]) - 1,
								Integer.parseInt(args[2]) - 1, sheet);// 合并单元格
					 setValue(Integer.parseInt(args[0]) - 1,
								Integer.parseInt(args[1]) - 1, 
								value, 
								tdStyle,cellType, sheet);
					 
				}
				
				/*String[] args = String.valueOf(str[0]).split(",");
				//第0行 第0列开始 ;跨2行 到 第0列结束
				mergedRegion(Integer.parseInt(args[0]) - 1,
						Integer.parseInt(args[1]) - 1,
						Integer.parseInt(args[3]) - 1,
						Integer.parseInt(args[2]) - 1, sheet);// 合并单元格
				setValue(Integer.parseInt(args[0]) - 1,
						Integer.parseInt(args[1]) - 1, 
						String.valueOf(str[1]), 
						tableStyle,cellType, sheet);*/
			}
		}
		
		/*int beginRow = 0;//当前开始行
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
		setValue(2,7, "数字4", tableStyle, cellType,sheet);*/
			
		
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

