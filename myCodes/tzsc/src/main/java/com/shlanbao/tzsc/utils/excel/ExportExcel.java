package com.shlanbao.tzsc.utils.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
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

import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;


public class ExportExcel {
	private short encoding = HSSFWorkbook.ENCODING_UTF_16;
	private int cellType = HSSFCell.CELL_TYPE_STRING;
	public ExportExcel() {
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)  throws IOException {
		ExportExcel ee = new ExportExcel(); 
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
			
			th.add("2,5,7,2,盘纸");
			th.add("2,8,10,2,甘油");
			th.add("2,11,13,2,丝数");
			
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

			//td 当前开始行,当前结束行,一共多少列
			int[] tdTables={4,11111,11};

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
	 * @param thTables	th表头表格 		EG:int[] thTables={1,3,13};当前开始行,当前结束行,一共多少列
	 * @param ths		th表格对应的内容  EG:th.add("1,1,1,3,跨行1");第1行;第1列开始 ~ 第1列结束  一共 跨3行
	 * @param tdTables	td表格		EG:int[] tdTables={4,(4+list条数-1),13}; 当前开始行,当前结束行,一共多少列
	 * @param tdVals	td表格对应的内容 EG:maps.put((4+i)+",1,1,1","项目1");//第N行;第H列开始 ~ 第H列结束 一共 跨S行
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
		int startLine=7;
		for(int i=0;i<100;i++){
			HSSFRow row = sheet.createRow(startLine++);
			try {
				for(int j=0;j<10;j++){
					HSSFCell cell=row.createCell((short) j);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue("测试");
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return workBook;
	}

	/**
	 * 报表导出excel格式
	 * @param thTables	th表头表格 		EG:int[] thTables={1,3,13};当前开始行,当前结束行,一共多少列
	 * @param ths		th表格对应的内容  EG:th.add("1,1,1,3,跨行1");第1行;第1列开始 ~ 第1列结束  一共 跨3行
	 * @param tdTables	td表格		EG:int[] tdTables={4,(4+list条数-1),13}; 当前开始行,当前结束行,一共多少列
	 * @param tdVals	td表格对应的内容 EG:maps.put((4+i)+",1,1,1","项目1");//第N行;第H列开始 ~ 第H列结束 一共 跨S行
	 * @return
	 */
	public HSSFWorkbook exportExcel(int[] thTables, List<String> ths,int startLine,String[] method,Class clazz,List<?> list) {
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
		
		for(Object o:list){
			HSSFRow row = sheet.createRow(startLine++);
			BigDecimal v=null;
			BigDecimal v2=null;
			Integer index=null;
			Object value=null;
			for(int i=0;i<method.length;i++){
				Method m;
				try {
					m = clazz.getDeclaredMethod(method[i]);
					Type rType=m.getGenericReturnType();
					if(rType==Double.TYPE || rType==Float.TYPE){
						value=m.invoke(o);
						if(value!=null){
							value=MathUtil.roundHalfUp((Double) value, 2);
						}else{
							value="0.0";
						}
					}else if(rType==Integer.TYPE || rType==Long.TYPE ||rType==Short.TYPE){
						value= m.invoke(o)!=null?m.invoke(o).toString():"0";
					}else{
						value= m.invoke(o)!=null?m.invoke(o).toString().trim():"";
						try {
							if(!value.equals("")){
								v=new BigDecimal(value.toString());
								Integer stat=v.compareTo(new BigDecimal(1000000000));
								if(stat>=1){
									//存在大数字原样输出
								}else{
									value=(v.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
									index=value.toString().indexOf('.');
									String d=value.toString().substring(index, value.toString().length());
									double ys=Double.parseDouble(d);
									if(ys==0){
										value=value.toString().substring(0, index);
									}
								}
							}
						} catch (Exception e) {
							//e.printStackTrace();
						}
					}
					HSSFCell cell=row.createCell((short) i);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(value.toString());
					if (tdStyle != null) {
						cell.setCellStyle(tdStyle);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
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
//		if(isBgGround){
//			style.setFillForegroundColor((short) 22);//背景颜色
//			style.setFillBackgroundColor((short) 22);//背景颜色
//			style.setFillPattern(HSSFCellStyle.SPARSE_DOTS);//背景颜色
//		}
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}

	/**
	 * 创建一行
	 * @param currentRow
	 * @param rowNum
	 * @param colNum
	 * @param style
	 * @param sheet
	 */
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
	/**
	 * 设置冻结列
	 * @param rowFrom
	 * @param colFrom
	 * @param rowTo
	 * @param colTo
	 * @param sheet
	 */
	public void mergedRegion(int rowFrom, int colFrom, int rowTo, int colTo,HSSFSheet sheet) {
		Region region = new Region(rowFrom, (short) colFrom, rowTo, (short) colTo);
		sheet.addMergedRegion(region);
	}
	/**
	 * 设置值
	 * @param rowIndex
	 * @param colIndex
	 * @param value
	 * @param style
	 * @param type
	 * @param sheet
	 */
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
	
	/**
	 * 写出
	 * @param workBook
	 * @param path
	 */
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
	/**
	 * 分sheet的excel导出
	 * @param thTables
	 * @param ths
	 * @param tdTables
	 * @param tdVals
	 * @return
	 */
	public HSSFWorkbook exportExcel2(int[] thTables, List<String> ths,
			int[] tdTables, List<List<Map<String,String>>> tdValss) {
		HSSFWorkbook workBook = new HSSFWorkbook();
		for (int k = 0; k < tdValss.size(); k++) {
		List<Map<String,String>> tdVals=tdValss.get(k);
		HSSFSheet sheet = workBook.createSheet();
		workBook.setSheetName(k, "sheet"+(k+1), encoding);
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
			}
		}
	}
		return workBook;
}
	
	
	
	/**
	 * 导出多表头excel
	 * TODO
	 * @param thTables
	 * @param th
	 * @param startLine
	 * @param method
	 * @param class1
	 * @param l1
	 * @param sheet
	 * @param tableStyle
	 * @param tdStyle
	 * @param idx 合并cell 从1开始 同行
	 * @param num  合并cell 同行
	 * @param idxRow 开始行  合并cell   同列
	 * @param eidx  结束行
	 * @param colNum 合并的cell列
	 * TRAVLER
	 * 2015年12月1日上午11:06:35
	 */
	public void exportExcelMoreTh(int[] thTables, List<String> ths,int startLine,String[] method,Class clazz,List<?> list,HSSFSheet sheet,HSSFCellStyle tableStyle,HSSFCellStyle tdStyle,Integer idx,Integer num,Integer idxRow,Integer eidx,Integer colNum) {
		this.createMoreTablesheet(thTables, ths, startLine, method, clazz, list, sheet, tableStyle, tdStyle,idx,num,idxRow,eidx,colNum);
		
	}
	/**
	 * 处理多表头sheet
	 * TODO
	 * @param thTables
	 * @param ths
	 * @param startLine
	 * @param method
	 * @param clazz
	 * @param list
	 * @param sheet
	 * @param tableStyle
	 * @param tdStyle
	 * @param idx 合并cell 从1开始 同行
	 * @param num  合并cell 同行
	 * @param idxRow 开始行  合并cell   同列
	 * @param eidx  结束行
	 * @param colNum 合并的cell列
	 * TRAVLER
	 * 2015年12月1日上午11:08:44
	 */
	private void createMoreTablesheet(int[] thTables, List<String> ths, int startLine, String[] method, Class clazz,
			List<?> list, HSSFSheet sheet, HSSFCellStyle tableStyle, HSSFCellStyle tdStyle, Integer idx, Integer num,Integer idxRow,Integer eidx,Integer colNum) {
		HSSFCell cell1=null;
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
						tdStyle,cellType, sheet);
			}
		}
		
		for(Object o:list){
			HSSFRow row = sheet.createRow(startLine++);
			for (short cellIndex = 0; cellIndex < thTables[2]; cellIndex++) {
				HSSFCell cell = row.createCell(cellIndex);
				cell.setEncoding(encoding);
				if (tdStyle != null) {
					cell.setCellStyle(tdStyle);
				}
				cell.setCellType(cellType);
				cell.setCellValue("");
			}
			if(idx!=null){
				mergedRegion(row.getRowNum(),
						idx-1,
						row.getRowNum(),
						num-1, sheet);// 合并单元格
			}
			BigDecimal v=null;
			Integer index=null;
			Object value=null;
			for(int i=0;i<method.length;i++){
				Method m;
				try {
					m = clazz.getDeclaredMethod(method[i]);
					Type rType=m.getGenericReturnType();
					if(rType==Double.TYPE || rType==Float.TYPE){
						value=m.invoke(o);
						if(value!=null){
							value=MathUtil.roundHalfUp((Double) value, 2);
						}else{
							value="0.0";
						}
					}else if(rType==Integer.TYPE || rType==Long.TYPE ||rType==Short.TYPE){
						value= m.invoke(o)!=null?m.invoke(o).toString():"0";
					}else{
						value= m.invoke(o)!=null?m.invoke(o).toString().trim():"";
						try {
							if(!value.equals("")){
								v=new BigDecimal(value.toString());
								Integer stat=v.compareTo(new BigDecimal(1000000000));
								if(stat>=1){
									//存在大数字原样输出
								}else{
									value=(v.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
									index=value.toString().indexOf('.');
									String d=value.toString().substring(index, value.toString().length());
									double ys=Double.parseDouble(d);
									if(ys==0){
										value=value.toString().substring(0, index);
									}
								}
							}
						} catch (Exception e) {
							//e.printStackTrace();
						}
					}
					//合并同列数据
					if(idxRow!=null&&i==colNum){
						mergedRegion(idxRow,
								i,
								eidx,
								i, sheet);// 合并单元格
					}
					cell1=row.getCell((short) i);
					cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell1.setCellValue(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		
	}
	
}

