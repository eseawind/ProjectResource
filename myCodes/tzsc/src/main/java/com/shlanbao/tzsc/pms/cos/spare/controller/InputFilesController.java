package com.shlanbao.tzsc.pms.cos.spare.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdWorkshop;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;
import com.shlanbao.tzsc.pms.equ.overhaul.beans.EqmMaintainBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.StringUtil;

public class InputFilesController {
	public List<CosSparePartsBean> readXlsx2007(String url) {
		try {
			List<CosSparePartsBean> listAll = new ArrayList<CosSparePartsBean>();
			Map<String, String> unitMap=SysEqpTypeBase.getUnit();
			Map<String, String> eqpTypeMap=SysEqpTypeBase.getEqpTypeNameAndID();
			// String fileName = url;
			FileInputStream is = new FileInputStream(new File(url));
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet - 2007excel
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow == null) {
						continue;
					}
					CosSparePartsBean cspb = new CosSparePartsBean();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
						XSSFCell xssfCell = xssfRow.getCell(cellNum);
						if (xssfCell == null) {
							continue;
						}
						String vl = getValue2007(xssfCell);
						//型号（编码）
						if (cellNum == 0) {
							if(vl.trim().equals("")){
								continue;
							}
							cspb.setAttr1(vl.replace(".0", ""));
						} else if (cellNum == 1) {
							cspb.setPosition(vl);//存放位置
						} else if (cellNum == 2) {
							cspb.setType(vl);//物料号
						} else if (cellNum == 3) {
							cspb.setName(vl);//物料名称
						} else if (cellNum == 4) {
							cspb.setRemark(vl);//备注；物料组描述
						} else if (cellNum == 5) {
							cspb.setSell_num(vl);//寄售库存
						} else if (cellNum == 6) {
							cspb.setRandom_num(vl);//随机库存
						}else if(cellNum == 7){
							cspb.setAttr2(vl);//库存
						}else if (cellNum == 8) {
							try{
							cspb.setPrice(Double.parseDouble(vl));//单价
							}catch(Exception e){
								cspb.setPrice(0.0);
							};//物料号
						} else if (cellNum == 9) {
							cspb.setAmount(vl);//花费总金额
						} else if (cellNum == 10) {
							cspb.setEqpTypeId(eqpTypeMap.get(vl));//所属机型
						} else if (cellNum == 11) {
							cspb.setUnitId(unitMap.get(vl));//物料单位
						}else if(cellNum==12){
							cspb.setSEARCH_W(vl);//检索物料首字母
						}
					}
					listAll.add(cspb);
				}
			}
			return listAll;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
/**
 * shisihai 2015-10-19
 * @param xssfCell
 * @return
 */
	@SuppressWarnings("static-access")
	private String getValue2007(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			String value="";
			// 纯数字
  			BigDecimal big=new BigDecimal(xssfCell.getNumericCellValue());
  			value = big.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
  			//解决1234.0  去掉后面的.0
  			if(null!=value&&!"".equals(value.trim())){
  				  String[] item = value.split("[.]");
  				  if(1<item.length&&"00".equals(item[1])){
  				  value=item[0];
  			}
  			 }
			return value;
//			Double itf = xssfCell.getNumericCellValue();
//			return String.valueOf(itf.toString());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}

	// 循环解析 - 2003excel
	public List<CosSparePartsBean> readXls2003(String url) {
		List<CosSparePartsBean> listAll = new ArrayList<CosSparePartsBean>();
		Map<String, String> unitMap=SysEqpTypeBase.getUnit();
		Map<String, String> eqpTypeMap=SysEqpTypeBase.getEqpTypeNameAndID();
		try {
			InputStream is = new FileInputStream(url);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					CosSparePartsBean cspb = new CosSparePartsBean();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell((short) cellNum);
						if (hssfCell == null) {
							continue;
						}
						String vl = getValue2003(hssfCell);
						//型号（编码）
						if (cellNum == 0) {
							if(vl.trim().equals("")){
								continue;
							}
							cspb.setAttr1(vl.replace(".0", ""));
						} else if (cellNum == 1) {
							cspb.setPosition(vl);//存放位置
						} else if (cellNum == 2) {
							cspb.setType(vl);//物料号
						} else if (cellNum == 3) {
							cspb.setName(vl);//物料名称
						} else if (cellNum == 4) {
							cspb.setRemark(vl);//备注；物料组描述
						} else if (cellNum == 5) {
							cspb.setSell_num(vl);//寄售库存
						} else if (cellNum == 6) {
							cspb.setRandom_num(vl);//随机库存
						}else if(cellNum == 7){
							cspb.setAttr2(vl);//库存
						}else if (cellNum == 8) {
							try{
							cspb.setPrice(Double.parseDouble(vl));//单价
							}catch(Exception e){
								cspb.setPrice(0.0);
							};//物料号
						} else if (cellNum == 9) {
							cspb.setAmount(vl);//花费总金额
						} else if (cellNum == 10) {
							cspb.setEqpTypeId(eqpTypeMap.get(vl));//所属机型
						} else if (cellNum == 11) {
							cspb.setUnitId(unitMap.get(vl));//物料单位
						}else if(cellNum==12){
							cspb.setSEARCH_W(vl);
						}
					}
					listAll.add(cspb);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAll;
	}

	/**
	 * 数据字典批量导入excel，润滑、点检、日保、轮保
	 * shisihai
	 * @param url
	 * @return 1 点检 , 2轮保 , 3润滑, 4日保
	 * @throws Exception 
	 */
	public List<SysEqpTypeBean> readXls2003_2(String url, String type) throws Exception  {
		List<SysEqpTypeBean> listAll = new ArrayList<SysEqpTypeBean>();
		//点检类型map
		Map<String,String> DJmap=SysEqpTypeBase.getDJRoleAlls();
		//轮保类型map
		Map<String,String> LBmap=SysEqpTypeBase.getLBRoleAlls();
		// 润滑部位
		Map<String, String> rb = SysEqpTypeBase.getRHRoleAlls();
		// id综合
		Map<String, String> ids = SysEqpTypeBase.getBaseDatas();
		//rh zhouqi
		Map<String,String>  rhIds=SysEqpTypeBase.getRh2();
			InputStream is = new FileInputStream(url);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					SysEqpTypeBean etb = new SysEqpTypeBean();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell((short) cellNum);
						if (hssfCell == null) {
							continue;
						}
						String vl = getValue2003(hssfCell);
						// 点检 点检4列数据
						if (type.equals("1")) {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							} else if (cellNum == 2) {
								etb.setDjType(DJmap.get(vl));
							} else if (cellNum == 3) {
								etb.setDjMethodId(vl);
							}
						}
						// 2轮保 轮保3列
						else if (type.equals("2")) {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							} else if (cellNum == 2) {
								etb.setLxType(LBmap.get(vl));
							}
						}
						// 3润滑 润滑10列
						else if (type.equals("3")) {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							}
							// 点数
							else if (cellNum == 2) {
								etb.setNumber(vl);
							}
							// 润滑油 id
							else if (cellNum == 3) {
								etb.setOilId(ids.get(vl));
							}
							// 加注量
							else if (cellNum == 4) {
								etb.setFillQuantity(vl);
							}
							// 加注单位 id
							else if (cellNum == 5) {
								etb.setCategoryId(ids.get(vl));
							}
							// 方式
							else if (cellNum == 6) {
								etb.setFashion(ids.get(vl));
							}
							// 润滑部位ok
							else if (cellNum == 7) {
								if(rb.get(vl)!=null){
									etb.setRhPart(rb.get(vl));
								}else{
									etb.setRhPart(vl);
								}
//								etb.setRhPart(rb.get(vl));
							}
							// 润滑周期ok
							else if (cellNum == 8) {
								try {
									etb.setPeriod(Integer.valueOf(vl.trim()
											.substring(0, vl.indexOf("."))));
								} catch (Exception e) {
									etb.setPeriod(0);
								}
							}
							// 周期单位
							else if (cellNum == 9) {
								etb.setPeriodUnit2(rhIds.get(vl));
							}
						}
						// 4日保 日保2列
						else {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							}
						}
					}
					listAll.add(etb);
				}

			}
		return listAll;
	}

	/**
	 * 数据字典批量导入excel，润滑、点检、日保、轮保
	 * shisihai
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public List<SysEqpTypeBean> readXlsx2007_2(String url, String type) throws Exception {
			List<SysEqpTypeBean> listAll = new ArrayList<SysEqpTypeBean>();
			//点检类型map
			Map<String,String> DJmap=SysEqpTypeBase.getDJRoleAlls();
			//轮保类型map
			Map<String,String> LBmap=SysEqpTypeBase.getLBRoleAlls();
			// 润滑部位
			Map<String, String> rb = SysEqpTypeBase.getRHRoleAlls();
			// id综合
			Map<String, String> ids = SysEqpTypeBase.getBaseDatas();
			//rh zhouqi
			Map<String,String> rhIds=SysEqpTypeBase.getRh2();
			// String fileName = url;
			FileInputStream is = new FileInputStream(new File(url));
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet - 2007excel
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow == null) {
						continue;
					}
					SysEqpTypeBean etb = new SysEqpTypeBean();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
						XSSFCell xssfCell = xssfRow.getCell(cellNum);
						if (xssfCell == null) {
							continue;
						}
						String vl = getValue2007(xssfCell);
						// //点检 点检4列数据
						if (type.equals("1")) {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							} else if (cellNum == 2) {
								etb.setDjType(DJmap.get(vl));
							} else if (cellNum == 3) {
								etb.setDjMethodId(vl);
							}
						}
						// 2轮保 轮保3列
						else if (type.equals("2")) {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							} else if (cellNum == 2) {
								etb.setLxType(LBmap.get(vl));
							}
						}
						// 3润滑 润滑10列
						else if (type.equals("3")) {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							}
							// 点数
							else if (cellNum == 2) {
								etb.setNumber(vl);
							}
							// 润滑油 id
							else if (cellNum == 3) {
								etb.setOilId(ids.get(vl));
							}
							// 加注量
							else if (cellNum == 4) {
								etb.setFillQuantity(vl);
							}
							// 加注单位 id
							else if (cellNum == 5) {
								etb.setCategoryId(ids.get(vl));
							}
							// 方式
							else if (cellNum == 6) {
								etb.setFashion(ids.get(vl));
							}
							// 润滑部位ok
							else if (cellNum == 7) {
								if(rb.get(vl)!=null){
									etb.setRhPart(rb.get(vl));
								}else{
									etb.setRhPart(vl);
								}
							}
							// 润滑周期ok
							else if (cellNum == 8) {
								try {
									etb.setPeriod(Integer.valueOf(vl.trim()
											.substring(0, vl.indexOf("."))));
								} catch (Exception e) {
									etb.setPeriod(0);
								}
							}
							// 周期单位
							else if (cellNum == 9) {
								etb.setPeriodUnit2(rhIds.get(vl));
							}
						}
						// 4日保 日保2列
						else {
							if (cellNum == 0) {
								etb.setName(vl);
							} else if (cellNum == 1) {
								etb.setDes(vl);
							}
						}
					}
					listAll.add(etb);
				}
			}
			return listAll;
	}
/**
 * shisihai 2015-10-19
 * @param hssfCell
 * @return
 */
	@SuppressWarnings("static-access")
	private String getValue2003(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			String value="";
			if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
			    //如果是date类型则 ，获取该cell的date值
  				Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
  				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    value = format.format(date);;
  			}else {// 纯数字
  				BigDecimal big=new BigDecimal(hssfCell.getNumericCellValue());
  				value = big.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
  				//解决1234.0  去掉后面的.0
  				if(null!=value&&!"".equals(value.trim())){
  				     String[] item = value.split("[.]");
  				     if(1<item.length&&"000".equals(item[1])){
  				    	 value=item[0];
  				     }
  				}
  			}
			return value;
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 设备检修excel导入xls2003
	 * 
	 * @param url
	 * @return
	 * @throws FileNotFoundException 
	 */
	public List<EqmMaintainBean> readXls2003_3(String url) throws Exception {

		List<EqmMaintainBean> listAll = new ArrayList<EqmMaintainBean>();

		// 设备name和id综合
		Map<String, String> eqpMap = SysEqpTypeBase.getAllEqpNameAndID();
			InputStream is = new FileInputStream(url);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					EqmMaintainBean etb = new EqmMaintainBean();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell((short) cellNum);
						if (hssfCell == null) {
							continue;
						}
						String vl = getValue2003(hssfCell);
						// 设备名 需要转化成设备id
						if (cellNum == 0) {
							if (vl == "" || vl.equals("null")) {
								return listAll;
							}
							etb.setEqp_id(eqpMap.get(vl));
						}
						// 部位
						else if (cellNum == 1) {
							etb.setPlace(vl);
						}
						// 检修内容
						else if (cellNum == 2) {
							etb.setContents(vl);
						}
						// 计划执行日期
						else if (cellNum == 3) {
							etb.setDate_plans(getDate2003(hssfCell));
						}
						// 计划负责人 需要转化成id
						else if (cellNum == 4) {
							etb.setBlame_usr_id(vl);
						}
						// 解决措施
						else if (cellNum == 5) {
							etb.setSolution(vl);
						}
						// 实际完成人 转id 可能有多个
						else if (cellNum == 6) {
							etb.setReal_usr_id(vl);
						}
						// 实际完成日期
						else if (cellNum == 7) {
							etb.setReal_times(getDate2003(hssfCell));
						}
						// 检修注意事项
						else if (cellNum == 8) {
							etb.setNote(vl);
						}
						// 复查情况
						else if (cellNum == 9) {
							etb.setReview(vl);
						}
						//后期跟进 （ 可能没有）
						else if(cellNum==10){
							etb.setFollowup(vl);
						}
						//备注（可能没有）
						else if(cellNum==11){
							etb.setRemark(vl);
						}
					}
					listAll.add(etb);
				}
			}
		return listAll;
	}

	/**
	 * 设备检修excel导入xls2007
	 * 
	 * @param url
	 * @return
	 * @throws FileNotFoundException 
	 */
	public List<EqmMaintainBean> readXlsx2007_3(String url) throws Exception {
			// 设备name和id综合
			Map<String, String> eqpMap = SysEqpTypeBase.getAllEqpNameAndID();
			List<EqmMaintainBean> listAll = new ArrayList<EqmMaintainBean>();
			FileInputStream is = new FileInputStream(new File(url));
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet - 2007excel
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow == null) {
						continue;
					}
					EqmMaintainBean etb = new EqmMaintainBean();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
						XSSFCell xssfCell = xssfRow.getCell(cellNum);
						if (xssfCell == null) {
							continue;
						}
						String vl = getValue2007(xssfCell);
						// 设备名 需要转化成设备id
						if (cellNum == 0) {
							if (vl == "" || vl.equals("null")) {
								return listAll;
							}
							etb.setEqp_id(eqpMap.get(vl));
						}
						// 部位
						else if (cellNum == 1) {
							etb.setPlace(vl);
						}
						// 检修内容
						else if (cellNum == 2) {
							etb.setContents(vl);
						}
						// 计划执行日期
						else if (cellNum == 3) {
							etb.setDate_plans(getDate2007(xssfCell));
						}
						// 计划负责人 需要转化成id
						else if (cellNum == 4) {
							etb.setBlame_usr_id(vl);
						}
						// 解决措施
						else if (cellNum == 5) {
							etb.setSolution(vl);
						}
						// 实际完成人 转id
						else if (cellNum == 6) {
							etb.setReal_usr_id(vl);
						}
						// 实际完成日期
						else if (cellNum == 7) {
							etb.setReal_times(getDate2007(xssfCell));
						}
						// 检修注意事项
						else if (cellNum == 8) {
							etb.setNote(vl);
						}
						// 复查情况
						else if (cellNum == 9) {
							etb.setReview(vl);
						}	
						//后期跟进
						else if(cellNum==10){
							etb.setFollowup(vl);
						}
						//备注
						else if(cellNum==11){
							etb.setRemark(vl);
						}
					}
					listAll.add(etb);
				}
			}
			return listAll;
	}

	public String getDate2003(HSSFCell hssfCell) throws Exception{
			String vl = getValue2003(hssfCell);
			if(vl.trim().equals("")||vl==null){
				return "";
			}else{
				try {
					Date d = hssfCell.getDateCellValue();
					if(d==null){
						return "";
					}
					return new SimpleDateFormat("yyyy-MM-dd").format(d);
				} catch (Exception e) {
					return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy/MM/dd").parse(vl));
				}
			}
	}

	public String getDate2007(XSSFCell xssfCell) throws Exception {
		
		String vl = getValue2007(xssfCell);
		if(vl.trim().equals("")||vl==null){
			return "";
		}else{
			try {
				Date d = xssfCell.getDateCellValue();
				if(d==null){
					return "";
				}
				return new SimpleDateFormat("yyyy-MM-dd").format(d);
			} catch (Exception e) {
				return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy/MM/dd").parse(vl));
			}
		}
		
	}

	/**
	 * 设备主数据导入excel
	 * 
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public List<MdEquipment> readXls2003_4(String url) throws Exception {
		List<MdEquipment> listAll = new ArrayList<MdEquipment>();
		// 设备型号
		Map<String, String> eqpmap = SysEqpTypeBase.getEqpTypeNameAndID();
		// 车间
		Map<String, String> wsmap = SysEqpTypeBase.getworkshopNameAndID();
		// 启用状态
		Map<String, String> eqpSta = SysEqpTypeBase.eqpStat();
		// 固入状态
		Map<String, String> eqpIpS = SysEqpTypeBase.eqpPutStat();
			InputStream is = new FileInputStream(url);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					MdEquipment etb = new MdEquipment();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell((short) cellNum);
						if (hssfCell == null) {
							continue;
						}
						String vl = getValue2003(hssfCell).trim();
						// 设备编号
						if (cellNum == 0) {
							if (vl == "" || vl.equals("null")) {
								return listAll;
							}
							
							try {
								etb.setEquipmentCode(vl.substring(0,
										vl.indexOf('.')));
							} catch (Exception e) {
								etb.setEquipmentCode(vl);
							}
						}
						// 设备名
						else if (cellNum == 1) {
							etb.setEquipmentName(vl);
						}
						// 设备描述
						else if (cellNum == 2) {
							etb.setEquipmentDesc(vl);
						}
						// 机型
						else if (cellNum == 3) {
							if (StringUtil.notNull(vl)) {
								MdEqpType mt = new MdEqpType(eqpmap.get(vl));
								etb.setMdEqpType(mt);
							}
						}
						// 车间
						else if (cellNum == 4) {
							if (StringUtil.notNull(vl)) {
								MdWorkshop ms = new MdWorkshop();
								ms.setId(wsmap.get(vl));
								etb.setMdWorkshop(ms);
							}
						}
						// 工序段
						else if (cellNum == 5) {
							etb.setWorkCenter(vl);
						}
						// 设备位置
						else if (cellNum == 6) {
							etb.setEquipmentPosition(vl);
						}
						// 额定车速
						else if (cellNum == 7) {
							etb.setRatedSpeed(Double.parseDouble(vl));
						}
						// 额定车速单位
						else if (cellNum == 8) {
							etb.setRateSpeedUnit(vl);
						}
						// 台时产量
						else if (cellNum == 9) {
							etb.setYieId(Double.parseDouble(vl));
						}
						// 台时产量单位
						else if (cellNum == 10) {
							etb.setYieldUnit(vl);
						}
						// 是否启用
						else if (cellNum == 11) {
							etb.setEnabled(eqpSta.get(vl));
						}
						// 固定资产编号
						else if (cellNum == 12) {
							etb.setFixedAssetNum(vl);
						}
						// 出厂编号
						else if (cellNum == 13) {
							etb.setManufacturingNum(vl);
						}
						// 批准文号
						else if (cellNum == 14) {
							etb.setApprovalNum(vl);
						}
						// 准运证编号
						else if (cellNum == 15) {
							etb.setNavicertNum(vl);
						}
						// 已固入
						else if (cellNum == 16) {
							etb.setFixedAssetFlag(eqpIpS.get(vl));
						}
						// 使用部门
						else if (cellNum == 17) {
							etb.setUsingDepartment(vl);
						}

					}
					listAll.add(etb);
				}
			}
		
		return listAll;
	}

	public List<MdEquipment> readXlsx2007_4(String url) throws Exception {

			// 设备型号
			Map<String, String> eqpmap = SysEqpTypeBase.getEqpTypeNameAndID();
			// 车间
			Map<String, String> wsmap = SysEqpTypeBase.getworkshopNameAndID();
			// 启用状态
			Map<String, String> eqpSta = SysEqpTypeBase.eqpStat();
			// 固入状态
			Map<String, String> eqpIpS = SysEqpTypeBase.eqpPutStat();
			List<MdEquipment> listAll = new ArrayList<MdEquipment>();
			// String fileName = url;
			// XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileName);
			FileInputStream is = new FileInputStream(new File(url));
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			is.close();
			// 循环工作表Sheet - 2007excel
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow == null) {
						continue;
					}
					MdEquipment etb = new MdEquipment();
					// 循环列Cell
					for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
						XSSFCell xssfCell = xssfRow.getCell(cellNum);
						if (xssfCell == null) {
							continue;
						}
						String vl = getValue2007(xssfCell);
						// 设备名 需要转化成设备id
						// 设备编号
						if (cellNum == 0) {
							if (vl == "" || vl.equals("null")) {
								return listAll;
							}
							try {
								etb.setEquipmentCode(vl.substring(0,
										vl.indexOf('.')));
							} catch (Exception e) {
								etb.setEquipmentCode(vl);
							}
						}
						// 设备名
						else if (cellNum == 1) {
							etb.setEquipmentName(vl);
						}
						// 设备描述
						else if (cellNum == 2) {
							etb.setEquipmentDesc(vl);
						}
						// 机型
						else if (cellNum == 3) {
							if (StringUtil.notNull(vl)) {
								MdEqpType mt = new MdEqpType(eqpmap.get(vl));
								etb.setMdEqpType(mt);
							}
						}
						// 车间
						else if (cellNum == 4) {
							if (StringUtil.notNull(vl)) {
								MdWorkshop ms = new MdWorkshop();
								ms.setId(wsmap.get(vl));
								etb.setMdWorkshop(ms);
							}
						}
						// 工序段
						else if (cellNum == 5) {
							etb.setWorkCenter(vl);
						}
						// 设备位置
						else if (cellNum == 6) {
							etb.setEquipmentPosition(vl);
						}
						// 额定车速
						else if (cellNum == 7) {
							etb.setRatedSpeed(Double.parseDouble(vl));
						}
						// 额定车速单位
						else if (cellNum == 8) {
							etb.setRateSpeedUnit(vl);
						}
						// 台时产量
						else if (cellNum == 9) {
							etb.setYieId(Double.parseDouble(vl));
						}
						// 台时产量单位
						else if (cellNum == 10) {
							etb.setYieldUnit(vl);
						}
						// 是否启用
						else if (cellNum == 11) {
							etb.setEnabled(eqpSta.get(vl));
						}
						// 固定资产编号
						else if (cellNum == 12) {
							etb.setFixedAssetNum(vl);
						}
						// 出厂编号
						else if (cellNum == 13) {
							etb.setManufacturingNum(vl);
						}
						// 批准文号
						else if (cellNum == 14) {
							etb.setApprovalNum(vl);
						}
						// 准运证编号
						else if (cellNum == 15) {
							etb.setNavicertNum(vl);
						}
						// 已固入
						else if (cellNum == 16) {
							etb.setFixedAssetFlag(eqpIpS.get(vl));
						}
						// 使用部门
						else if (cellNum == 17) {
							etb.setUsingDepartment(vl);
						}
					}
					listAll.add(etb);
				}
			}
			return listAll;

	}
}
