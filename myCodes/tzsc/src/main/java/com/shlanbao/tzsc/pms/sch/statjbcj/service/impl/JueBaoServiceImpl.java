package com.shlanbao.tzsc.pms.sch.statjbcj.service.impl;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.BaseDaoI;
import com.shlanbao.tzsc.base.dao.QmChangeShiftInfoDaoI;
import com.shlanbao.tzsc.base.dao.SchStatInputDaoI;
import com.shlanbao.tzsc.base.dao.SchStatOutputDaoI;
import com.shlanbao.tzsc.base.dao.StatShiftFLInfoDaoI;
import com.shlanbao.tzsc.base.mapping.QmChangeShiftInfo;
import com.shlanbao.tzsc.base.mapping.SchStatInput;
import com.shlanbao.tzsc.base.mapping.SchStatOutput;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.ChangeShiftData;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.JuanBaoCJBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.MatterInfo;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.ShiftCheckBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.WorkShopShiftBean;
import com.shlanbao.tzsc.pms.sch.statjbcj.service.JueBaoSerivceI;
import com.shlanbao.tzsc.utils.excel.ExcelWriter;
import com.shlanbao.tzsc.utils.excel.ExportExcel;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;

@Service
public class JueBaoServiceImpl extends BaseService implements JueBaoSerivceI {
	@Autowired
	private BaseDaoI<Object> basedao;
	@Autowired
	private SchStatOutputDaoI JueBaoServiceImpl;
	@Autowired
	private SchStatInputDaoI inputDaoI;
	@Autowired
	private StatShiftFLInfoDaoI statShiftFLInfoDaoI;
	@Autowired
	private SchStatOutputDaoI schStatOutputDao;
	@Autowired
	private QmChangeShiftInfoDaoI qmchangshiftinfodao;

	/**
	 * 成型车间产品生产统计 此功能隐藏
	 */
	@Override
	public DataGrid getChengXingChanPinList(WorkShopShiftBean shopShiftBean)
			throws Exception {
		// 查出所有····产量 剔除 单位 运行时间 停机时间 停机次数 时间单位 工单编号 工单类型 牌号名称
		String temp = "select o.schStatOutput.qty, o.schStatOutput.badQty,o.schStatOutput.mdUnit.name,o.schStatOutput.runTime,o.schStatOutput.stopTime, o.schStatOutput.stopTimes, o.schStatOutput.timeUnit.name,o.schStatOutput.schWorkorder.id, o.schStatOutput.schWorkorder.type,o.schStatOutput.schWorkorder.mdMat.name,"
				// 辅料编号 辅料名称 辅料单位
				+ " o.mdMat.mdMatType.id,o.mdMat.mdMatType.name,o.qty,o.mdUnit.name from SchStatInput o where o.schStatOutput.schWorkorder.mdEquipment.mdWorkshop.id='2' and o.schStatOutput.del='0'";// 卷包车间
		if (StringUtil.notNull(shopShiftBean.getMdTeamId())) {// 班组（甲乙丙）
			temp += " and o.schStatOutput.schWorkorder.mdTeam.id='"
					+ shopShiftBean.getMdTeamId() + "' ";
		}

		if (StringUtil.notNull(shopShiftBean.getMdShiftId())) {// 班次（早中晚）
			temp += " and o.schStatOutput.schWorkorder.mdShift.id='"
					+ shopShiftBean.getMdShiftId() + "'";
		}
		temp += StringUtil.fmtDateBetweenParams(
				"o.schStatOutput.schWorkorder.date", shopShiftBean.getStim(),
				shopShiftBean.getEtim());

		List<Object[]> tempList = JueBaoServiceImpl.queryObjectArray(temp);

		Hashtable<String, WorkShopShiftBean> ht = new Hashtable<String, WorkShopShiftBean>();
		if (tempList != null) {
			for (Object[] o : tempList) {

				String orderId = o[7].toString();// 根据key取到对象再对对象进行赋值，把工单编号做为key
				if (ht.get(orderId) != null) {
					WorkShopShiftBean bean = ht.get(orderId);
					bean = ObjectToBean1(bean, o);
					ht.put(orderId, bean);
				} else {

					WorkShopShiftBean bean = new WorkShopShiftBean();
					bean = ObjectToBean1(bean, o);
					ht.put(orderId, bean);
				}

			}
		}

		Hashtable<String, WorkShopShiftBean> ht1 = new Hashtable<String, WorkShopShiftBean>();// 进行同牌号汇总

		Iterator i = ht.keySet().iterator();
		while (i.hasNext()) {

			WorkShopShiftBean bean1 = ht.get(i.next());
			if (ht1.get(bean1.getMdMatName()) != null) {
				WorkShopShiftBean bean = ht1.get(bean1.getMdMatName());
				bean = huizong1(bean, bean1);
				ht1.put(bean1.getMdMatName(), bean);
			} else {
				WorkShopShiftBean bean = new WorkShopShiftBean();
				bean = huizong1(bean, bean1);
				ht1.put(bean1.getMdMatName(), bean);
			}
		}

		List<WorkShopShiftBean> shopBeans = new ArrayList<WorkShopShiftBean>();
		Iterator j = ht1.keySet().iterator();// 进行迭代
		while (j.hasNext()) {// 如果下个元素还有继续迭代
			shopBeans.add(ht1.get(j.next()));// 根据key取value
		}

		return new DataGrid(shopBeans, 100L);
	}

	private WorkShopShiftBean huizong1(WorkShopShiftBean bean,
			WorkShopShiftBean bean1) {
		bean.setMdMatName(bean1.getMdMatName());
		if (bean1.getMdUnitName() != null)
			bean.setMdUnitName(bean1.getMdUnitName());
		if (bean1.getMdUnitName1() != null)
			bean.setMdUnitName1(bean1.getMdUnitName1());
		if (bean1.getMdUnitName2() != null)
			bean.setMdUnitName2(bean1.getMdUnitName2());
		if (bean1.getMdUnitName3() != null)
			bean.setMdUnitName3(bean1.getMdUnitName3());

		bean.setQty(bean.getQty() + bean1.getQty());
		bean.setBadQty(bean.getBadQty() + bean1.getBadQty());
		// bean.setQtyBaoZhuang(bean.getQtyBaoZhuang()+bean1.getQtyBaoZhuang());
		// bean.setBadQtyBaoZhuang(bean.getBadQtyBaoZhuang()+bean1.getBadQtyBaoZhuang());
		// bean.setRunTime(bean.getRunTime()+bean1.getRunTime());
		// bean.setStopTime(bean.getStopTime()+bean1.getStopTime());
		bean.setQty1(bean.getQty1() + bean1.getQty1());
		bean.setQty2(bean.getQty2() + bean1.getQty2());
		bean.setQty3(bean.getQty3() + bean1.getQty3());
		// 成型机
		if (bean.getQty() != null && bean.getQty() > 0
				&& bean.getQty1() != null && bean.getQty1() > 0) {
			bean.setDanhao1(MathUtil.roundHalfUp(
					bean.getQty1() / bean.getQty(), 2));
		} else {
			bean.setDanhao1(0.0);
		}
		if (bean.getQty() != null && bean.getQty() > 0
				&& bean.getQty2() != null && bean.getQty2() > 0) {
			bean.setDanhao2(MathUtil.roundHalfUp(
					bean.getQty2() / bean.getQty(), 2));
		} else {
			bean.setDanhao2(0.0);
		}
		if (bean.getQty() != null && bean.getQty() > 0
				&& bean.getQty3() != null && bean.getQty3() > 0) {
			bean.setDanhao3(MathUtil.roundHalfUp(
					bean.getQty3() / bean.getQty(), 2));
		} else {
			bean.setDanhao3(0.0);
		}

		return bean;

	}

	private WorkShopShiftBean ObjectToBean1(WorkShopShiftBean shopBean1,
			Object[] o) {
		if (o != null) {
			String matTypeId = o[10].toString();
			// if(o[8].toString().equals("4")){//成型机
			if (null != o[0].toString() && !"".equals(o[0].toString())) {
				shopBean1.setQty(Double.valueOf(o[0].toString()));// 产量
			} else {
				o[0] = 0;
			}
			if (null != o[1].toString() && !"".equals(o[0].toString())) {
				shopBean1.setBadQty(Double.valueOf(o[1].toString()));// 剔除
			} else {

				shopBean1.setBadQty(0.0);// 剔除
			}

			// }
			shopBean1.setMdUnitName(o[2].toString());// 单位
			// shopBean1.setRunTime(Double.valueOf(o[3].toString()));//运行时间
			// shopBean1.setStopTime(Double.valueOf(o[4].toString()));//停机时间
			// shopBean1.setStopTimes(Long.valueOf((o[5].toString())));//停机次数
			// shopBean1.setTimeUnitName(o[6].toString());//时间单位
			shopBean1.setWorkorderId(o[7].toString());// 工单编号
			shopBean1.setWorkorderType(o[8].toString());// 工单类型
			shopBean1.setMdMatName(o[9].toString());// 产品名称
			if (matTypeId.equals("2")) {// 盘纸
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty1(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName1(o[13].toString());// 单位
				if (null != shopBean1.getQty() && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao1(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao1(0.0);
				}
			} else if (matTypeId.equals("11")) {// 甘油
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty2(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName2(o[13].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao2(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao2(0.0);
				}
			} else if (matTypeId.equals("12")) {// 丝束
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty3(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName3(o[13].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao3(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao3(0.0);
				}
			}
			return shopBean1;
		} else {
			return null;
		}
	}

	/**
	 * 卷包车间产品生产统计 主键是执行工单时间+D+工单号忽略机台类型
	 */

	@Override
	public DataGrid getJuanBaoChanPinList(WorkShopShiftBean shopShiftBean)
			throws Exception {
		// 查出所有···· 产量 剔除 单位 运行时间 停机时间 停机次数 时间单位 工单输入编号 工单类型 牌号名称
		String temp = "select o.schStatOutput.qty, o.schStatOutput.badQty,o.schStatOutput.mdUnit.name,o.schStatOutput.runTime,o.schStatOutput.stopTime, o.schStatOutput.stopTimes, o.schStatOutput.timeUnit.name,o.schStatOutput.schWorkorder.code, o.schStatOutput.schWorkorder.type,o.schStatOutput.schWorkorder.mdMat.name,"
				// 辅料编号 辅料名称 辅料单位
				+ " o.mdMat.mdMatType.id,o.mdMat.mdMatType.name,o.qty,o.mdUnit.name,o.schStatOutput.schWorkorder.stim,o.schStatOutput.schWorkorder.mdMat.id from SchStatInput o where o.schStatOutput.schWorkorder.mdEquipment.mdWorkshop.id='1' and o.schStatOutput.del='0'";// 卷包车间
		if (StringUtil.notNull(shopShiftBean.getMdTeamId())) {// 班组（甲乙丙）
			temp += " and o.schStatOutput.schWorkorder.mdTeam.id='"
					+ shopShiftBean.getMdTeamId() + "' ";
		}

		if (StringUtil.notNull(shopShiftBean.getMdShiftId())) {// 班次（早中晚）
			temp += " and o.schStatOutput.schWorkorder.mdShift.id='"
					+ shopShiftBean.getMdShiftId() + "'";
		}
		if (StringUtil.notNull(shopShiftBean.getMdMatId())) {// 品牌
			temp += " and o.schStatOutput.schWorkorder.mdMat.id='"
					+ shopShiftBean.getMdMatId() + "' ";
		}
		temp += StringUtil.fmtDateBetweenParams(
				"o.schStatOutput.schWorkorder.date", shopShiftBean.getStim(),
				shopShiftBean.getEtim());
		List<Object[]> tempList = JueBaoServiceImpl.queryObjectArray(temp);

		Hashtable<String, WorkShopShiftBean> ht = new Hashtable<String, WorkShopShiftBean>();
		if (tempList != null) {
			for (Object[] o : tempList) {
				String Dte = o[14].toString();
				String Cde = o[7].toString();
				String key = Dte.substring(0, 13) + "D"
						+ Cde.substring(0, Cde.length() - 3)
						+ Cde.substring(Cde.length() - 2, Cde.length());
				if (ht.get(key) != null) {
					WorkShopShiftBean bean = ht.get(key);
					bean = ObjectToBean(bean, o);
					ht.put(key, bean);
				} else {

					WorkShopShiftBean bean = new WorkShopShiftBean();
					bean = ObjectToBean(bean, o);
					ht.put(key, bean);
				}

			}
		}

		Hashtable<String, WorkShopShiftBean> ht1 = new Hashtable<String, WorkShopShiftBean>();// 进行同牌号汇总

		Iterator i = ht.keySet().iterator();
		while (i.hasNext()) {

			WorkShopShiftBean bean1 = ht.get(i.next());
			if (ht1.get(bean1.getMdMatName()) != null) {
				WorkShopShiftBean bean = ht1.get(bean1.getMdMatName());
				bean = huizong(bean, bean1);
				ht1.put(bean1.getMdMatName(), bean);
			} else {
				WorkShopShiftBean bean = new WorkShopShiftBean();
				bean = huizong(bean, bean1);
				ht1.put(bean1.getMdMatName(), bean);
			}
		}

		List<WorkShopShiftBean> shopBeans = new ArrayList<WorkShopShiftBean>();
		Iterator j = ht1.keySet().iterator();// 进行迭代
		while (j.hasNext()) {// 如果下个元素还有继续迭代
			shopBeans.add(ht1.get(j.next()));// 根据key取value
		}

		return new DataGrid(shopBeans, 100L);
	}

	private WorkShopShiftBean huizong(WorkShopShiftBean bean,
			WorkShopShiftBean bean1) {
		bean.setMdMatName(bean1.getMdMatName());
		if (bean1.getMdUnitName() != null)
			bean.setMdUnitName(bean1.getMdUnitName());
		if (bean1.getMdUnitNameBad() != null)
			bean.setMdUnitNameBad(bean1.getMdUnitNameBad());
		if (bean1.getMdUnitName1() != null)
			bean.setMdUnitName1(bean1.getMdUnitName1());
		if (bean1.getMdUnitName2() != null)
			bean.setMdUnitName2(bean1.getMdUnitName2());
		if (bean1.getMdUnitName3() != null)
			bean.setMdUnitName3(bean1.getMdUnitName3());
		if (bean1.getMdUnitName4() != null)
			bean.setMdUnitName4(bean1.getMdUnitName4());
		if (bean1.getMdUnitName5() != null)
			bean.setMdUnitName5(bean1.getMdUnitName5());
		if (bean1.getMdUnitName6() != null)
			bean.setMdUnitName6(bean1.getMdUnitName6());
		if (bean1.getMdUnitName7() != null)
			bean.setMdUnitName7(bean1.getMdUnitName7());
		if (bean1.getMdUnitName8() != null)
			bean.setMdUnitName8(bean1.getMdUnitName8());

		bean.setQty(bean.getQty() + bean1.getQty());
		bean.setBadQty(bean.getBadQty() + bean1.getBadQty());
		bean.setQtyBaoZhuang(bean.getQtyBaoZhuang() + bean1.getQtyBaoZhuang());
		bean.setBadQtyBaoZhuang(bean.getBadQtyBaoZhuang()
				+ bean1.getBadQtyBaoZhuang());
		bean.setRunTime(bean.getRunTime() + bean1.getRunTime());
		bean.setStopTime(bean.getStopTime() + bean1.getStopTime());
		bean.setQty1(bean.getQty1() + bean1.getQty1());
		bean.setQty2(bean.getQty2() + bean1.getQty2());
		bean.setQty3(bean.getQty3() + bean1.getQty3());
		bean.setQty4(bean.getQty4() + bean1.getQty4());
		bean.setQty5(bean.getQty5() + bean1.getQty5());
		bean.setQty6(bean.getQty6() + bean1.getQty6());
		bean.setQty7(bean.getQty7() + bean1.getQty7());
		bean.setQty8(bean.getQty8() + bean1.getQty8());
		if (bean.getQty() != null && bean.getQty() > 0
				&& bean.getQty1() != null && bean.getQty1() > 0) {
			bean.setDanhao1(MathUtil.roundHalfUp(
					bean.getQty1() / bean.getQty(), 2));
		} else {
			bean.setDanhao1(0.0);
		}
		if (bean.getQty() != null && bean.getQty() > 0
				&& bean.getQty2() != null && bean.getQty2() > 0) {
			bean.setDanhao2(MathUtil.roundHalfUp(
					bean.getQty2() / bean.getQty(), 2));
		} else {
			bean.setDanhao2(0.0);
		}
		if (bean.getQty() != null && bean.getQty() > 0
				&& bean.getQty3() != null && bean.getQty3() > 0) {
			bean.setDanhao3(MathUtil.roundHalfUp(
					bean.getQty3() / bean.getQty(), 2));
		} else {
			bean.setDanhao3(0.0);
		}

		// 包装机
		if (bean.getQtyBaoZhuang() != null && bean.getQtyBaoZhuang() > 0
				&& bean.getQty4() != null && bean.getQty4() > 0) {
			bean.setDanhao4(MathUtil.roundHalfUp(
					bean.getQty4() / bean.getQtyBaoZhuang(), 2));
		} else {
			bean.setDanhao4(0.0);
		}
		if (bean.getQtyBaoZhuang() != null && bean.getQtyBaoZhuang() > 0
				&& bean.getQty5() != null && bean.getQty5() > 0) {
			bean.setDanhao5(MathUtil.roundHalfUp(
					bean.getQty5() / bean.getQtyBaoZhuang(), 2));
		} else {
			bean.setDanhao5(0.0);
		}
		if (bean.getQtyBaoZhuang() != null && bean.getQtyBaoZhuang() > 0
				&& bean.getQty6() != null && bean.getQty6() > 0) {
			bean.setDanhao6(MathUtil.roundHalfUp(
					bean.getQty6() / bean.getQtyBaoZhuang(), 2));
		} else {
			bean.setDanhao6(0.0);
		}
		if (bean.getQtyBaoZhuang() != null && bean.getQtyBaoZhuang() > 0
				&& bean.getQty7() != null && bean.getQty7() > 0) {
			bean.setDanhao7(MathUtil.roundHalfUp(
					bean.getQty7() / bean.getQtyBaoZhuang(), 2));
		} else {
			bean.setDanhao7(0.0);
		}
		if (bean.getQtyBaoZhuang() != null && bean.getQtyBaoZhuang() > 0
				&& bean.getQty8() != null && bean.getQty8() > 0) {
			bean.setDanhao8(MathUtil.roundHalfUp(
					bean.getQty8() / bean.getQtyBaoZhuang(), 2));
		} else {
			bean.setDanhao8(0.0);
		}

		return bean;

	}

	private WorkShopShiftBean ObjectToBean(WorkShopShiftBean shopBean1,
			Object[] o) {
		if (o != null) {
			String matTypeId = o[10].toString();
			if (o[8].toString().equals("1")) {// 卷烟机
				if (null != o[0].toString() && !"".equals(o[0].toString())) {
					shopBean1.setQty(Double.valueOf(o[0].toString()));// 产量
				} else {
					o[0] = 0;
				}
				if (null != o[1].toString() && !"".equals(o[0].toString())) {
					shopBean1.setBadQty(Double.valueOf(o[1].toString()));// 剔除
				} else {

					shopBean1.setBadQty(0.0);// 剔除
				}
				shopBean1.setMdUnitName(o[2].toString());// 单位

			} else if (o[8].toString().equals("2")) {// 包装机
				if (null != o[0].toString() && !"".equals(o[0].toString())) {
					shopBean1.setQtyBaoZhuang(Double.valueOf(o[0].toString()));// 产量
				} else {
					o[0] = 0;
				}
				if (null != o[1].toString() && !"".equals(o[0].toString())) {
					shopBean1
							.setBadQtyBaoZhuang(Double.valueOf(o[1].toString()));// 剔除
				} else {

					shopBean1.setBadQtyBaoZhuang(0.0);// 剔除
				}
				shopBean1.setMdUnitNameBad(o[2].toString());// 单位

			}

			shopBean1.setRunTime(Double.valueOf(o[3].toString()));// 运行时间
			shopBean1.setStopTime(Double.valueOf(o[4].toString()));// 停机时间
			shopBean1.setStopTimes(Long.valueOf((o[5].toString())));// 停机次数
			shopBean1.setTimeUnitName(o[6].toString());// 时间单位
			shopBean1.setWorkorderId(o[7].toString());// 工单编号
			shopBean1.setWorkorderType(o[8].toString());// 工单类型
			shopBean1.setMdMatName(o[9].toString());// 产品名称
			if (matTypeId.equals("4")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty1(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName1(o[13].toString());// 单位
				if (null != shopBean1.getQty() && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao1(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao1(0.0);
				}
			}
			// 这里是待测试点 ？（不知原因为何是13）
			else if (matTypeId.equals("13")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty2(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName2(o[13].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao2(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao2(0.0);
				}
			}
			// 盘纸
			else if (matTypeId.equals("2")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty2(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName2(o[13].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao2(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao2(0.0);
				}
			} else if (matTypeId.equals("3")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty3(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName3(o[13].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao3(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao3(0.0);
				}
			} else if (matTypeId.equals("7")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty4(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName4(o[13].toString());// 单位
				if (shopBean1.getBadQtyBaoZhuang() != null
						&& shopBean1.getBadQtyBaoZhuang() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao4(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getBadQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao4(0.0);
				}
			} else if (matTypeId.equals("5")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty5(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName5(o[13].toString());// 单位
				if (shopBean1.getBadQtyBaoZhuang() != null
						&& shopBean1.getBadQtyBaoZhuang() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao5(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getBadQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao5(0.0);
				}
			} else if (matTypeId.equals("9")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty6(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName6(o[13].toString());// 单位
				if (shopBean1.getBadQtyBaoZhuang() != null
						&& shopBean1.getBadQtyBaoZhuang() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao6(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getBadQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao6(0.0);
				}
			} else if (matTypeId.equals("8")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty7(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName7(o[13].toString());// 单位
				if (shopBean1.getBadQtyBaoZhuang() != null
						&& shopBean1.getBadQtyBaoZhuang() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao7(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getBadQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao7(0.0);
				}
			} else if (matTypeId.equals("6")) {
				if (null != o[12].toString() && !"".equals(o[12].toString())) {
					o[12] = o[12].toString();
				} else {
					o[12] = 0;
				}
				shopBean1.setQty8(Double.valueOf(o[12].toString()));// 单耗
				shopBean1.setMdUnitName8(o[13].toString());// 单位
				if (shopBean1.getBadQtyBaoZhuang() != null
						&& shopBean1.getBadQtyBaoZhuang() != 0
						&& Double.valueOf(o[12].toString()) != 0) {
					shopBean1.setDanhao8(MathUtil.roundHalfUp(
							Double.valueOf(o[12].toString())
									/ shopBean1.getBadQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao8(0.0);
				}
			}

			return shopBean1;
		} else {
			return null;
		}
	}

	/**
	 * 卷烟综合生产统计
	 * 
	 */

	@Override
	public DataGrid getJueYanList(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		List<JuanBaoCJBean> baoCJBeans = new ArrayList<JuanBaoCJBean>();

		String hql = " from SchStatOutput s  left join fetch s.schWorkorder sw  left join fetch sw.mdEquipment me "
				+ " left join fetch me.mdEqpType vv"
				+ " left join fetch sw.mdUnit mu  left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch"
				+ " sw.mdMat mm   where 1=1 and s.del='0' and vv.mdEqpCategory.id='4028998349c6880e0149c688e9fb0001' and sw.type='1'";// 4028998349c6880e0149c688e9fb0001卷烟机
		if (type == 1) {
			hql += " and me.mdWorkshop.id='1' ";
		} else if (type == 2) {
			// 是2说明是机组
		}
		if (StringUtil.notNull(baoCJBean.getMdEquipmentId())) {
			hql += " and me.id='" + baoCJBean.getMdEquipmentId() + "'";
		}
		hql += StringUtil.fmtDateBetweenParams("sw.date", baoCJBean.getStim(),
				baoCJBean.getEtim());

		List<SchStatOutput> outputs = JueBaoServiceImpl.query(hql);
		double chanliang = 0, tichu = 0, xiaohao1 = 0, xiaohao2 = 0, xiaohao3 = 0, yunxingshijian = 0, tingjishijian = 0, danhao1 = 0, danhao2 = 0, danhao3 = 0;
		long tingjicishu = 0;
		if (outputs != null) {
			for (SchStatOutput schStatOutput : outputs) {
				JuanBaoCJBean schStat = null;
				try {
					chanliang += schStatOutput.getQty();// 产量
					tichu += schStatOutput.getBadQty();// 剔除
					yunxingshijian += schStatOutput.getRunTime();// 运行时间
					tingjishijian += schStatOutput.getStopTime();// 停机时间
					tingjicishu += schStatOutput.getStopTimes();// 停机时次数
					schStat = BeanConvertor.copyProperties(schStatOutput,
							JuanBaoCJBean.class);
					schStat.setEquipmentType(schStatOutput.getSchWorkorder()
							.getMdEquipment().getMdEqpType().getName());
					schStat.setEquipmentNameCode(schStatOutput
							.getSchWorkorder().getMdEquipment()
							.getEquipmentCode());
					schStat.setEquipmentName(schStatOutput.getSchWorkorder()
							.getMdEquipment().getEquipmentName());
					schStat.setMdEquipmentId(schStatOutput.getSchWorkorder()
							.getMdEquipment().getId());
					schStat.setMdMatId(schStatOutput.getSchWorkorder()
							.getMdMat().getId());
					schStat.setMdMatName(schStatOutput.getSchWorkorder()
							.getMdMat().getName());
					schStat.setMdShiftId(schStatOutput.getSchWorkorder()
							.getMdShift().getId());
					schStat.setMdShiftName(schStatOutput.getSchWorkorder()
							.getMdShift().getName());
					schStat.setMdTeamId(schStatOutput.getSchWorkorder()
							.getMdTeam().getId());
					schStat.setMdTeamName(schStatOutput.getSchWorkorder()
							.getMdTeam().getName());
					schStat.setTimeUnitId(schStatOutput.getTimeUnit().getId());
					schStat.setTimeUnitName(schStatOutput.getTimeUnit()
							.getName());
					schStat.setDate(schStatOutput.getSchWorkorder().getDate()
							.toString());
					schStat.setMdUnitName(schStatOutput.getMdUnit().getName());

					if (schStatOutput.getIsFeedback().equals("1")) {
						schStat.setIsFeedback01("已反馈");
					} else {
						schStat.setIsFeedback01("未反馈");
					}
					List<SchStatInput> inputs = inputDaoI
							.query(" from SchStatInput s  left join fetch s.mdMat mm left join fetch mm.mdMatType  left join fetch s.mdUnit mu where s.schStatOutput.id='"
									+ schStat.getId() + "'");
					if (inputs.size() > 0) {
						for (SchStatInput schStatInput : inputs) {
							if (schStatInput.getMdMat().getMdMatType().getId()
									.equals("4")) {// 滤棒
								schStat.setMdUnitName1(schStatInput.getMdUnit()
										.getName());
								schStat.setQty1(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao1(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao1 += schStatInput.getQty();
								} else {
									schStat.setDanhao1(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("2")) {// 盘纸
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao2(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao2 += schStatInput.getQty();
								} else {
									schStat.setDanhao2(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("13")) {// 盘纸
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao2(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao2 += schStatInput.getQty();
								} else {
									schStat.setDanhao2(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("3")) {// 水松纸
								schStat.setMdUnitName3(schStatInput.getMdUnit()
										.getName());
								schStat.setQty3(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao3(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao3 += schStatInput.getQty();
								} else {
									schStat.setDanhao3(0.0);
								}

							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				baoCJBeans.add(schStat);
			}
			if (chanliang != 0 && xiaohao1 != 0) {
				danhao1 = MathUtil.roundHalfUp(xiaohao1 / chanliang, 2);
			} else {
				danhao1 = 0.0;
			}
			if (chanliang != 0 && xiaohao2 != 0) {
				danhao2 = MathUtil.roundHalfUp(xiaohao2 / chanliang, 2);
			} else {
				danhao2 = 0.0;
			}
			if (chanliang != 0 && xiaohao3 != 0) {
				danhao3 = MathUtil.roundHalfUp(xiaohao3 / chanliang, 2);
			} else {
				danhao3 = 0.0;
			}

			JuanBaoCJBean juebao = new JuanBaoCJBean();
			juebao.setEquipmentName("合计");
			juebao.setQty(chanliang);
			juebao.setBadQty(tichu);
			juebao.setDanhao1(danhao1);
			juebao.setDanhao2(danhao2);
			juebao.setDanhao3(danhao3);
			juebao.setQty1(xiaohao1);
			juebao.setQty2(xiaohao2);
			juebao.setQty3(xiaohao3);
			juebao.setRunTime(yunxingshijian);
			juebao.setStopTime(tingjishijian);
			juebao.setStopTimes(tingjicishu);
			if (baoCJBeans.size() > 0) {
				baoCJBeans.add(juebao);
			}
		}
		return new DataGrid(baoCJBeans, 100L);
	}

	/**
	 * 成型综合生产统计
	 * 
	 */
	@Override
	public DataGrid getChengXingList(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		List<JuanBaoCJBean> baoCJBeans = new ArrayList<JuanBaoCJBean>();
		String hql = " from SchStatOutput s  left join fetch s.schWorkorder sw  left join fetch sw.mdEquipment m "
				+ " left join fetch m.mdEqpType vv"
				+ " left join fetch sw.mdUnit mu  left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch"
				+ " sw.mdMat mm   where 1=1 and s.del='0' and vv.mdEqpCategory.id='4028998349c6880e0149c688e9fb0003' and sw.type='4' ";//
		if (type == 1) {
			hql += " and m.mdWorkshop.id='2' ";
		} else if (type == 2) {
			// 是2说明是机组
		}
		if (StringUtil.notNull(baoCJBean.getMdEquipmentId())) {
			hql += " and m.id='" + baoCJBean.getMdEquipmentId() + "'";
		}
		hql += StringUtil.fmtDateBetweenParams("sw.date", baoCJBean.getStim(),
				baoCJBean.getEtim());

		List<SchStatOutput> outputs = JueBaoServiceImpl.query(hql);
		double chanliang = 0, tichu = 0, xiaohao1 = 0, xiaohao2 = 0, xiaohao3 = 0, yunxingshijian = 0, tingjishijian = 0, danhao1 = 0, danhao2 = 0, danhao3 = 0;
		long tingjicishu = 0;
		if (outputs != null) {
			for (SchStatOutput schStatOutput : outputs) {
				JuanBaoCJBean schStat = null;
				try {
					chanliang += schStatOutput.getQty();// 产量
					tichu += schStatOutput.getBadQty();// 剔除
					yunxingshijian += schStatOutput.getRunTime();// 运行时间
					tingjishijian += schStatOutput.getStopTime();// 停机时间
					tingjicishu += schStatOutput.getStopTimes();// 停机时次数
					schStat = BeanConvertor.copyProperties(schStatOutput,
							JuanBaoCJBean.class);
					schStat.setEquipmentType(schStatOutput.getSchWorkorder()
							.getMdEquipment().getMdEqpType().getName());
					schStat.setEquipmentNameCode(schStatOutput
							.getSchWorkorder().getMdEquipment()
							.getEquipmentCode());
					schStat.setEquipmentName(schStatOutput.getSchWorkorder()
							.getMdEquipment().getEquipmentName());
					schStat.setMdEquipmentId(schStatOutput.getSchWorkorder()
							.getMdEquipment().getId());
					schStat.setMdMatId(schStatOutput.getSchWorkorder()
							.getMdMat().getId());
					schStat.setMdMatName(schStatOutput.getSchWorkorder()
							.getMdMat().getName());
					schStat.setMdShiftId(schStatOutput.getSchWorkorder()
							.getMdShift().getId());
					schStat.setMdShiftName(schStatOutput.getSchWorkorder()
							.getMdShift().getName());
					schStat.setMdTeamId(schStatOutput.getSchWorkorder()
							.getMdTeam().getId());
					schStat.setMdTeamName(schStatOutput.getSchWorkorder()
							.getMdTeam().getName());
					schStat.setTimeUnitId(schStatOutput.getTimeUnit().getId());
					schStat.setTimeUnitName(schStatOutput.getTimeUnit()
							.getName());
					schStat.setDate(schStatOutput.getSchWorkorder().getDate()
							.toString());
					schStat.setMdUnitName(schStatOutput.getMdUnit().getName());

					if (schStatOutput.getIsFeedback().equals("1")) {
						schStat.setIsFeedback01("已反馈");
					} else {
						schStat.setIsFeedback01("未反馈");
					}
					List<SchStatInput> inputs = inputDaoI
							.query(" from SchStatInput s  left join fetch s.mdMat mm left join fetch mm.mdMatType  left join fetch s.mdUnit mu where s.schStatOutput.id='"
									+ schStat.getId() + "'");
					if (inputs.size() > 0) {
						for (SchStatInput schStatInput : inputs) {
							if (schStatInput.getMdMat().getMdMatType().getId()
									.equals("2")) {// 盘纸
								schStat.setMdUnitName1(schStatInput.getMdUnit()
										.getName());
								schStat.setQty1(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao1(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao1 += schStatInput.getQty();
								} else {
									schStat.setDanhao1(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("11")) {// 甘油
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao2(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao2 += schStatInput.getQty();
								} else {
									schStat.setDanhao2(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("12")) {// 丝束
								schStat.setMdUnitName3(schStatInput.getMdUnit()
										.getName());
								schStat.setQty3(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao3(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao3 += schStatInput.getQty();
								} else {
									schStat.setDanhao3(0.0);
								}

							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				baoCJBeans.add(schStat);
			}

			if (chanliang != 0 && xiaohao1 != 0) {
				danhao1 = MathUtil.roundHalfUp(xiaohao1 / chanliang, 2);
			} else {
				danhao1 = 0.0;
			}
			if (chanliang != 0 && xiaohao2 != 0) {
				danhao2 = MathUtil.roundHalfUp(xiaohao2 / chanliang, 2);
			} else {
				danhao2 = 0.0;
			}
			if (chanliang != 0 && xiaohao3 != 0) {
				danhao3 = MathUtil.roundHalfUp(xiaohao3 / chanliang, 2);
			} else {
				danhao3 = 0.0;
			}

			JuanBaoCJBean juebao = new JuanBaoCJBean();
			juebao.setEquipmentName("合计");
			juebao.setQty(chanliang);
			juebao.setBadQty(tichu);
			juebao.setDanhao1(danhao1);
			juebao.setDanhao2(danhao2);
			juebao.setDanhao3(danhao3);
			juebao.setQty1(xiaohao1);
			juebao.setQty2(xiaohao2);
			juebao.setQty3(xiaohao3);
			juebao.setRunTime(yunxingshijian);
			juebao.setStopTime(tingjishijian);
			juebao.setStopTimes(tingjicishu);
			if (baoCJBeans.size() > 0) {
				baoCJBeans.add(juebao);
			}
		}
		return new DataGrid(baoCJBeans, 100L);
	}

	/**
	 * 包装综合生产统计
	 * 
	 */
	@Override
	public DataGrid getBaoZhuangList(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		List<JuanBaoCJBean> baoCJBeans = new ArrayList<JuanBaoCJBean>();
		String hql = " from SchStatOutput s  left join fetch s.schWorkorder sw  left join fetch sw.mdEquipment me "
				+ " left join fetch me.mdEqpType vv"
				+ " left join fetch sw.mdUnit mu  left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch"
				+ " sw.mdMat mm   where 1=1 and s.del='0' and vv.mdEqpCategory.id='4028998349c6880e0149c688e9fb0000' and sw.type='2'";
		if (type == 1) {
			hql += " and me.mdWorkshop.id='1' ";
		} else if (type == 2) {
			// 是2说明是机组
		}

		if (StringUtil.notNull(baoCJBean.getMdEquipmentId())) {
			hql += " and me.id='" + baoCJBean.getMdEquipmentId() + "'";
		}
		hql += StringUtil.fmtDateBetweenParams("sw.date", baoCJBean.getStim(),
				baoCJBean.getEtim());

		List<SchStatOutput> outputs = JueBaoServiceImpl.query(hql);
		double chanliang = 0, tichu = 0, xiaohao1 = 0, xiaohao2 = 0, xiaohao3 = 0, xiaohao4 = 0, xiaohao5 = 0, yunxingshijian = 0, tingjishijian = 0, danhao1 = 0, danhao2 = 0, danhao3 = 0, danhao4 = 0, danhao5 = 0;
		long tingjicishu = 0;
		if (outputs != null) {
			for (SchStatOutput schStatOutput : outputs) {
				JuanBaoCJBean schStat = null;
				try {
					chanliang += schStatOutput.getQty();// 产量
					tichu += schStatOutput.getBadQty();// 剔除
					yunxingshijian += schStatOutput.getRunTime();// 运行时间
					tingjishijian += schStatOutput.getStopTime();// 停机时间
					tingjicishu += schStatOutput.getStopTimes();// 停机时次数
					schStat = BeanConvertor.copyProperties(schStatOutput,
							JuanBaoCJBean.class);
					schStat.setEquipmentType(schStatOutput.getSchWorkorder()
							.getMdEquipment().getMdEqpType().getName());
					schStat.setEquipmentNameCode(schStatOutput
							.getSchWorkorder().getMdEquipment()
							.getEquipmentCode());
					schStat.setEquipmentName(schStatOutput.getSchWorkorder()
							.getMdEquipment().getEquipmentName());
					schStat.setMdEquipmentId(schStatOutput.getSchWorkorder()
							.getMdEquipment().getId());
					schStat.setMdMatId(schStatOutput.getSchWorkorder()
							.getMdMat().getId());
					schStat.setMdMatName(schStatOutput.getSchWorkorder()
							.getMdMat().getName());
					schStat.setMdShiftId(schStatOutput.getSchWorkorder()
							.getMdShift().getId());
					schStat.setMdShiftName(schStatOutput.getSchWorkorder()
							.getMdShift().getName());
					schStat.setMdTeamId(schStatOutput.getSchWorkorder()
							.getMdTeam().getId());
					schStat.setMdTeamName(schStatOutput.getSchWorkorder()
							.getMdTeam().getName());
					schStat.setTimeUnitId(schStatOutput.getTimeUnit().getId());
					schStat.setTimeUnitName(schStatOutput.getTimeUnit()
							.getName());
					schStat.setDate(schStatOutput.getSchWorkorder().getDate()
							.toString());
					schStat.setMdUnitName(schStatOutput.getMdUnit().getName());

					if (schStatOutput.getIsFeedback().equals("1")) {
						schStat.setIsFeedback01("已反馈");
					} else {
						schStat.setIsFeedback01("未反馈");
					}
					List<SchStatInput> inputs = inputDaoI
							.query(" from SchStatInput s  left join fetch s.mdMat mm left join fetch mm.mdMatType  left join fetch s.mdUnit mu where s.schStatOutput.id='"
									+ schStat.getId() + "'");
					if (inputs.size() > 0) {
						for (SchStatInput schStatInput : inputs) {
							if (schStatInput.getMdMat().getMdMatType().getId()
									.equals("7")) {// 小盒
								schStat.setMdUnitName1(schStatInput.getMdUnit()
										.getName());
								schStat.setQty1(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao1(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao1 += schStatInput.getQty();
								} else {
									schStat.setDanhao1(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("5")) {// 小盒透明纸
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao2(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao2 += schStatInput.getQty();
								} else {
									schStat.setDanhao2(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("9")) {// 内衬纸
								schStat.setMdUnitName3(schStatInput.getMdUnit()
										.getName());
								schStat.setQty3(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao3(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao3 += schStatInput.getQty();
								} else {
									schStat.setDanhao3(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("8")) {// 条盒
								schStat.setMdUnitName4(schStatInput.getMdUnit()
										.getName());
								schStat.setQty4(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao4(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao4 += schStatInput.getQty();
								} else {
									schStat.setDanhao4(0.0);
								}

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("6")) {// 条盒透明纸
								schStat.setMdUnitName5(schStatInput.getMdUnit()
										.getName());
								schStat.setQty5(schStatInput.getQty());
								if (schStatInput.getQty() != 0
										&& schStat.getQty() != 0) {
									schStat.setDanhao5(MathUtil.roundHalfUp(
											schStatInput.getQty()
													/ schStat.getQty(), 2));
									xiaohao5 += schStatInput.getQty();
								} else {
									schStat.setDanhao5(0.0);
								}

							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				baoCJBeans.add(schStat);
			}
			if (chanliang != 0 && xiaohao1 != 0) {
				danhao1 = MathUtil.roundHalfUp(xiaohao1 / chanliang, 2);
			} else {
				danhao1 = 0.0;
			}
			if (chanliang != 0 && xiaohao2 != 0) {
				danhao2 = MathUtil.roundHalfUp(xiaohao2 / chanliang, 2);
			} else {
				danhao2 = 0.0;
			}
			if (chanliang != 0 && xiaohao3 != 0) {
				danhao3 = MathUtil.roundHalfUp(xiaohao3 / chanliang, 2);
			} else {
				danhao3 = 0.0;
			}
			if (chanliang != 0 && xiaohao4 != 0) {
				danhao4 = MathUtil.roundHalfUp(xiaohao4 / chanliang, 2);
			} else {
				danhao4 = 0.0;
			}
			if (chanliang != 0 && xiaohao5 != 0) {
				danhao5 = MathUtil.roundHalfUp(xiaohao5 / chanliang, 2);
			} else {
				danhao5 = 0.0;
			}
			JuanBaoCJBean juebao = new JuanBaoCJBean();
			juebao.setEquipmentName("合计");
			juebao.setQty(chanliang);
			juebao.setBadQty(tichu);
			juebao.setDanhao1(danhao1);
			juebao.setDanhao2(danhao2);
			juebao.setDanhao3(danhao3);
			juebao.setDanhao4(danhao4);
			juebao.setDanhao5(danhao5);
			juebao.setQty1(xiaohao1);
			juebao.setQty2(xiaohao2);
			juebao.setQty3(xiaohao3);
			juebao.setQty4(xiaohao4);
			juebao.setQty5(xiaohao5);
			juebao.setRunTime(yunxingshijian);
			juebao.setStopTime(tingjishijian);
			juebao.setStopTimes(tingjicishu);
			if (baoCJBeans.size() > 0) {
				baoCJBeans.add(juebao);
			}
		}
		return new DataGrid(baoCJBeans, 100L);
	}

	/**
	 * 卷包班组生产统计
	 */
	@Override
	public DataGrid getJuanBaoBanZuList(WorkShopShiftBean shopShiftBean)
			throws Exception {
		List<WorkShopShiftBean> baoCJBeans = new ArrayList<WorkShopShiftBean>();
		String hql = " from SchStatOutput s  left join fetch s.schWorkorder sw  left join fetch sw.mdEquipment me "
				+ " left join fetch me.mdEqpType vv"
				+ " left join fetch sw.mdUnit mu  left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch"
				+ " sw.mdMat mm   where 1=1 and s.del='0' and me.mdWorkshop.id='1' "; //

		if (StringUtil.notNull(shopShiftBean.getMdTeamId())) {// 班组（甲乙丙）
			hql += " and mt.id='" + shopShiftBean.getMdTeamId() + "' ";
		}

		if (StringUtil.notNull(shopShiftBean.getMdShiftId())) {// 班次（早中晚）
			hql += " and ms.id='" + shopShiftBean.getMdShiftId() + "'";
		}
		hql += StringUtil.fmtDateBetweenParams("sw.date",
				shopShiftBean.getStim(), shopShiftBean.getEtim());

		List<SchStatOutput> outputs = JueBaoServiceImpl.query(hql);
		if (outputs != null) {
			for (SchStatOutput schStatOutput : outputs) {
				WorkShopShiftBean schStat = null;
				try {

					schStat = BeanConvertor.copyProperties(schStatOutput,
							WorkShopShiftBean.class);
					schStat.setEquipmentType(schStatOutput.getSchWorkorder()
							.getMdEquipment().getMdEqpType().getName());
					schStat.setEquipmentNameCode(schStatOutput
							.getSchWorkorder().getMdEquipment()
							.getEquipmentCode());
					schStat.setEquipmentName(schStatOutput.getSchWorkorder()
							.getMdEquipment().getEquipmentName());
					schStat.setMdEquipmentId(schStatOutput.getSchWorkorder()
							.getMdEquipment().getId());
					schStat.setMdMatId(schStatOutput.getSchWorkorder()
							.getMdMat().getId());
					schStat.setMdMatName(schStatOutput.getSchWorkorder()
							.getMdMat().getName());
					schStat.setMdShiftId(schStatOutput.getSchWorkorder()
							.getMdShift().getId());
					schStat.setMdShiftName(schStatOutput.getSchWorkorder()
							.getMdShift().getName());
					schStat.setMdTeamId(schStatOutput.getSchWorkorder()
							.getMdTeam().getId());
					schStat.setMdTeamName(schStatOutput.getSchWorkorder()
							.getMdTeam().getName());
					schStat.setTimeUnitId(schStatOutput.getTimeUnit().getId());
					schStat.setTimeUnitName(schStatOutput.getTimeUnit()
							.getName());
					schStat.setDate(schStatOutput.getSchWorkorder().getDate()
							.toString());
					schStat.setMdUnitName(schStatOutput.getMdUnit().getName());
					schStat.setOrderType(schStatOutput.getSchWorkorder()
							.getType());
					if (schStatOutput.getSchWorkorder().getType().equals(1L)) {// 根据卷烟车间查询1是卷烟机工单
						schStat.setQty(schStatOutput.getQty());
						schStat.setBadQty(schStatOutput.getBadQty());
						schStat.setMdUnitName(schStatOutput.getMdUnit()
								.getName());
					} else if (schStatOutput.getSchWorkorder().getType()
							.equals(2L)) {// 2是包装机工单
						schStat.setQtyBaoZhuang(schStatOutput.getQty());
						schStat.setBadQtyBaoZhuang(schStatOutput.getBadQty());
						schStat.setMdUnitNameBad(schStatOutput.getMdUnit()
								.getName());
					}

					if (schStatOutput.getIsFeedback().equals("1")) {
						schStat.setIsFeedback01("已反馈");
					} else {
						schStat.setIsFeedback01("未反馈");
					}
					List<SchStatInput> inputs = inputDaoI
							.query(" from SchStatInput s  left join fetch s.mdMat mm left join fetch mm.mdMatType  left join fetch s.mdUnit mu where s.schStatOutput.id='"
									+ schStat.getId() + "'");
					if (inputs.size() > 0) {
						for (SchStatInput schStatInput : inputs) {
							if (schStatInput.getMdMat().getMdMatType().getId()
									.equals("4")) {// 滤棒
								schStat.setMdUnitName1(schStatInput.getMdUnit()
										.getName());
								schStat.setQty1(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("13")) {// 滤棒盘纸
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("2")) {// 卷烟纸
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("3")) {// 水松纸
								schStat.setMdUnitName3(schStatInput.getMdUnit()
										.getName());
								schStat.setQty3(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("7")) {// 小条盒
								schStat.setMdUnitName4(schStatInput.getMdUnit()
										.getName());
								schStat.setQty4(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("5")) {// 小透明纸
								schStat.setMdUnitName5(schStatInput.getMdUnit()
										.getName());
								schStat.setQty5(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("9")) {// 内衬纸
								schStat.setMdUnitName6(schStatInput.getMdUnit()
										.getName());
								schStat.setQty6(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("8")) {// 条盒
								schStat.setMdUnitName7(schStatInput.getMdUnit()
										.getName());
								schStat.setQty7(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("6")) {// 大透明纸
								schStat.setMdUnitName8(schStatInput.getMdUnit()
										.getName());
								schStat.setQty8(schStatInput.getQty());

							}
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				baoCJBeans.add(schStat);
			}
		}

		double chanliang = 0, tichu = 0, bzchanliang = 0, bztichu = 0, yunxingshijian = 0, tingjishijian = 0, xiaohao1 = 0, xiaohao2 = 0, xiaohao3 = 0, xiaohao4 = 0, xiaohao5 = 0, xiaohao6 = 0, xiaohao7 = 0, xiaohao8 = 0;
		long tingjicishu = 0;
		double chanliangA = 0, tichuA = 0, bzchanliangA = 0, bztichuA = 0, yunxingshijianA = 0, tingjishijianA = 0, xiaohao1A = 0, xiaohao2A = 0, xiaohao3A = 0, xiaohao4A = 0, xiaohao5A = 0, xiaohao6A = 0, xiaohao7A = 0, xiaohao8A = 0;
		long tingjicishuA = 0;
		double chanliangB = 0, tichuB = 0, bzchanliangB = 0, bztichuB = 0, yunxingshijianB = 0, tingjishijianB = 0, xiaohao1B = 0, xiaohao2B = 0, xiaohao3B = 0, xiaohao4B = 0, xiaohao5B = 0, xiaohao6B = 0, xiaohao7B = 0, xiaohao8B = 0;
		long tingjicishuB = 0;

		List<WorkShopShiftBean> returnData = new ArrayList<WorkShopShiftBean>();
		if (baoCJBeans != null) {
			WorkShopShiftBean beanA = new WorkShopShiftBean();
			WorkShopShiftBean beanB = new WorkShopShiftBean();
			WorkShopShiftBean beanC = new WorkShopShiftBean();
			for (WorkShopShiftBean workShopShiftBean : baoCJBeans) {// 对所有的甲乙丙丁进行统计
				if (workShopShiftBean.getMdTeamId().equals("1")) {// 甲班

					beanA.setMdTeamName("甲班");
					if (workShopShiftBean.getQty1() == null
							|| workShopShiftBean.getQty1().equals(equals(""))) {
						xiaohao1A += 0.0;
					} else {
						xiaohao1A += workShopShiftBean.getQty1();
					}
					if (workShopShiftBean.getQty2() == null
							|| workShopShiftBean.getQty2().equals(equals(""))) {
						xiaohao2A += 0.0;
					} else {
						xiaohao2A += workShopShiftBean.getQty2();
					}
					if (workShopShiftBean.getQty3() == null
							|| workShopShiftBean.getQty3().equals(equals(""))) {
						xiaohao3A += 0.0;
					} else {
						xiaohao3A += workShopShiftBean.getQty3();
					}
					if (workShopShiftBean.getQty4() == null
							|| workShopShiftBean.getQty4().equals(equals(""))) {
						xiaohao4A += 0.0;
					} else {
						xiaohao4A += workShopShiftBean.getQty4();
					}
					if (workShopShiftBean.getQty5() == null
							|| workShopShiftBean.getQty5().equals(equals(""))) {
						xiaohao5A += 0.0;
					} else {
						xiaohao5A += workShopShiftBean.getQty5();
					}
					if (workShopShiftBean.getQty6() == null
							|| workShopShiftBean.getQty6().equals(equals(""))) {
						xiaohao6A += 0.0;
					} else {
						xiaohao6A += workShopShiftBean.getQty6();
					}
					if (workShopShiftBean.getQty7() == null
							|| workShopShiftBean.getQty7().equals(equals(""))) {
						xiaohao7A += 0.0;
					} else {
						xiaohao7A += workShopShiftBean.getQty7();
					}
					if (workShopShiftBean.getQty8() == null
							|| workShopShiftBean.getQty8().equals(equals(""))) {
						xiaohao8A += 0.0;
					} else {
						xiaohao8A += workShopShiftBean.getQty8();
					}

					beanA.setQty1(xiaohao1A);
					beanA.setQty2(xiaohao2A);
					beanA.setQty3(xiaohao3A);
					beanA.setQty4(xiaohao4A);
					beanA.setQty5(xiaohao5A);
					beanA.setQty6(xiaohao6A);
					beanA.setQty7(xiaohao7A);
					beanA.setQty8(xiaohao8A);

					chanliangA += workShopShiftBean.getQty();
					tichuA += workShopShiftBean.getBadQty();
					bzchanliangA += workShopShiftBean.getQtyBaoZhuang();
					bztichuA += workShopShiftBean.getBadQtyBaoZhuang();
					yunxingshijianA += workShopShiftBean.getRunTime();
					tingjishijianA += workShopShiftBean.getStopTime();
					tingjicishuA += workShopShiftBean.getStopTimes();
					if (xiaohao1A != 0 && chanliangA != 0) {
						beanA.setDanhao1(MathUtil.roundHalfUp(xiaohao1A
								/ chanliangA, 2));
					} else {
						beanA.setDanhao1(0.0);
					}
					if (xiaohao2A != 0 && chanliangA != 0) {
						beanA.setDanhao2(MathUtil.roundHalfUp(xiaohao2A
								/ chanliangA, 2));
					} else {
						beanA.setDanhao2(0.0);
					}
					if (xiaohao3A != 0 && chanliangA != 0) {
						beanA.setDanhao3(MathUtil.roundHalfUp(xiaohao3A
								/ chanliangA, 2));
					} else {
						beanA.setDanhao3(0.0);
					}
					if (xiaohao4A != 0 && bzchanliangA != 0) {
						beanA.setDanhao4(MathUtil.roundHalfUp(xiaohao4A
								/ bzchanliangA, 2));
					} else {
						beanA.setDanhao4(0.0);
					}
					if (xiaohao5A != 0 && bzchanliangA != 0) {
						beanA.setDanhao5(MathUtil.roundHalfUp(xiaohao5A
								/ bzchanliangA, 2));
					} else {
						beanA.setDanhao5(0.0);
					}
					if (xiaohao6A != 0 && bzchanliangA != 0) {
						beanA.setDanhao6(MathUtil.roundHalfUp(xiaohao6A
								/ bzchanliangA, 2));
					} else {
						beanA.setDanhao6(0.0);
					}
					if (xiaohao7A != 0 && bzchanliangA != 0) {
						beanA.setDanhao7(MathUtil.roundHalfUp(xiaohao7A
								/ bzchanliangA, 2));
					} else {
						beanA.setDanhao7(0.0);
					}
					if (xiaohao8A != 0 && bzchanliangA != 0) {
						beanA.setDanhao8(MathUtil.roundHalfUp(xiaohao8A
								/ bzchanliangA, 2));
					} else {
						beanA.setDanhao8(0.0);
					}
					if (workShopShiftBean.getMdUnitName() != null
							&& workShopShiftBean.getOrderType().equals(1L))
						beanA.setMdUnitName(workShopShiftBean.getMdUnitName());
					if (workShopShiftBean.getMdUnitNameBad() != null
							&& workShopShiftBean.getOrderType().equals(2L))
						beanA.setMdUnitNameBad(workShopShiftBean
								.getMdUnitNameBad());
					if (workShopShiftBean.getMdUnitName1() != null) {
						beanA.setMdUnitName1(workShopShiftBean.getMdUnitName1());
					}
					if (workShopShiftBean.getMdUnitName2() != null) {
						beanA.setMdUnitName2(workShopShiftBean.getMdUnitName2());
					}
					if (workShopShiftBean.getMdUnitName3() != null) {
						beanA.setMdUnitName3(workShopShiftBean.getMdUnitName3());
					}
					if (workShopShiftBean.getMdUnitName4() != null) {
						beanA.setMdUnitName4(workShopShiftBean.getMdUnitName4());
					}
					if (workShopShiftBean.getMdUnitName5() != null) {
						beanA.setMdUnitName5(workShopShiftBean.getMdUnitName5());
					}
					if (workShopShiftBean.getMdUnitName6() != null) {
						beanA.setMdUnitName6(workShopShiftBean.getMdUnitName6());
					}
					if (workShopShiftBean.getMdUnitName7() != null) {
						beanA.setMdUnitName7(workShopShiftBean.getMdUnitName7());
					}
					if (workShopShiftBean.getMdUnitName8() != null) {
						beanA.setMdUnitName8(workShopShiftBean.getMdUnitName8());
					}

					beanA.setQty(chanliangA);
					beanA.setBadQty(tichuA);
					beanA.setRunTime(yunxingshijianA);
					beanA.setStopTime(tingjishijianA);
					beanA.setStopTimes(tingjicishuA);
					beanA.setQtyBaoZhuang(bzchanliangA);
					beanA.setBadQtyBaoZhuang(bztichuA);
					if (workShopShiftBean.getTimeUnitName() != null) {
						beanA.setTimeUnitName(workShopShiftBean
								.getTimeUnitName());
					}

				} else if (workShopShiftBean.getMdTeamId().equals("2")) {// 乙班

					beanB.setMdTeamName("乙班");
					if (workShopShiftBean.getQty1() == null
							|| workShopShiftBean.getQty1().equals(equals(""))) {
						xiaohao1B += 0.0;
					} else {
						xiaohao1B += workShopShiftBean.getQty1();
					}
					if (workShopShiftBean.getQty2() == null
							|| workShopShiftBean.getQty2().equals(equals(""))) {
						xiaohao2B += 0.0;
					} else {
						xiaohao2B += workShopShiftBean.getQty2();
					}
					if (workShopShiftBean.getQty3() == null
							|| workShopShiftBean.getQty3().equals(equals(""))) {
						xiaohao3B += 0.0;
					} else {
						xiaohao3B += workShopShiftBean.getQty3();
					}
					if (workShopShiftBean.getQty4() == null
							|| workShopShiftBean.getQty4().equals(equals(""))) {
						xiaohao4B += 0.0;
					} else {
						xiaohao4B += workShopShiftBean.getQty4();
					}
					if (workShopShiftBean.getQty5() == null
							|| workShopShiftBean.getQty5().equals(equals(""))) {
						xiaohao5B += 0.0;
					} else {
						xiaohao5B += workShopShiftBean.getQty5();
					}
					if (workShopShiftBean.getQty6() == null
							|| workShopShiftBean.getQty6().equals(equals(""))) {
						xiaohao6B += 0.0;
					} else {
						xiaohao6B += workShopShiftBean.getQty6();
					}
					if (workShopShiftBean.getQty7() == null
							|| workShopShiftBean.getQty7().equals(equals(""))) {
						xiaohao7B += 0.0;
					} else {
						xiaohao7B += workShopShiftBean.getQty7();
					}
					if (workShopShiftBean.getQty8() == null
							|| workShopShiftBean.getQty8().equals(equals(""))) {
						xiaohao8B += 0.0;
					} else {
						xiaohao8B += workShopShiftBean.getQty8();
					}
					beanB.setQty1(xiaohao1B);
					beanB.setQty2(xiaohao2B);
					beanB.setQty3(xiaohao3B);
					beanB.setQty4(xiaohao4B);
					beanB.setQty5(xiaohao5B);
					beanB.setQty6(xiaohao6B);
					beanB.setQty7(xiaohao7B);
					beanB.setQty8(xiaohao8B);

					chanliangB += workShopShiftBean.getQty();
					tichuB += workShopShiftBean.getBadQty();
					bzchanliangB += workShopShiftBean.getQtyBaoZhuang();
					bztichuB += workShopShiftBean.getBadQtyBaoZhuang();
					yunxingshijianB += workShopShiftBean.getRunTime();
					tingjishijianB += workShopShiftBean.getStopTime();
					tingjicishuB += workShopShiftBean.getStopTimes();
					if (xiaohao1B != 0 && chanliangB != 0) {
						beanB.setDanhao1(MathUtil.roundHalfUp(xiaohao1B
								/ chanliangB, 2));
					} else {
						beanB.setDanhao1(0.0);
					}
					if (xiaohao2B != 0 && chanliangB != 0) {
						beanB.setDanhao2(MathUtil.roundHalfUp(xiaohao2B
								/ chanliangB, 2));
					} else {
						beanB.setDanhao2(0.0);
					}
					if (xiaohao3B != 0 && chanliangB != 0) {
						beanB.setDanhao3(MathUtil.roundHalfUp(xiaohao3B
								/ chanliangB, 2));
					} else {
						beanB.setDanhao3(0.0);
					}
					if (xiaohao4B != 0 && bzchanliangB != 0) {
						beanB.setDanhao4(MathUtil.roundHalfUp(xiaohao4B
								/ bzchanliangB, 2));
					} else {
						beanB.setDanhao4(0.0);
					}
					if (xiaohao5B != 0 && bzchanliangB != 0) {
						beanB.setDanhao5(MathUtil.roundHalfUp(xiaohao5B
								/ bzchanliangB, 2));
					} else {
						beanB.setDanhao5(0.0);
					}
					if (xiaohao6B != 0 && bzchanliangB != 0) {
						beanB.setDanhao6(MathUtil.roundHalfUp(xiaohao6B
								/ bzchanliangB, 2));
					} else {
						beanB.setDanhao6(0.0);
					}
					if (xiaohao7B != 0 && bzchanliangB != 0) {
						beanB.setDanhao7(MathUtil.roundHalfUp(xiaohao7B
								/ bzchanliangB, 2));
					} else {
						beanB.setDanhao7(0.0);
					}
					if (xiaohao8B != 0 && bzchanliangB != 0) {
						beanB.setDanhao8(MathUtil.roundHalfUp(xiaohao8B
								/ bzchanliangB, 2));
					} else {
						beanB.setDanhao8(0.0);
					}
					if (workShopShiftBean.getMdUnitName() != null
							&& workShopShiftBean.getOrderType().equals(1L))
						beanB.setMdUnitName(workShopShiftBean.getMdUnitName());
					if (workShopShiftBean.getMdUnitNameBad() != null
							&& workShopShiftBean.getOrderType().equals(2L))
						beanB.setMdUnitNameBad(workShopShiftBean
								.getMdUnitNameBad());

					if (StringUtil.notNull(workShopShiftBean.getMdUnitName1())) {
						beanB.setMdUnitName1(workShopShiftBean.getMdUnitName1());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName2())) {
						beanB.setMdUnitName2(workShopShiftBean.getMdUnitName2());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName3())) {
						beanB.setMdUnitName3(workShopShiftBean.getMdUnitName3());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName4())) {
						beanB.setMdUnitName4(workShopShiftBean.getMdUnitName4());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName5())) {
						beanB.setMdUnitName5(workShopShiftBean.getMdUnitName5());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName6())) {
						beanB.setMdUnitName6(workShopShiftBean.getMdUnitName6());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName7())) {
						beanB.setMdUnitName7(workShopShiftBean.getMdUnitName7());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName8())) {
						beanB.setMdUnitName8(workShopShiftBean.getMdUnitName8());
					}

					beanB.setQty(chanliangB);
					beanB.setBadQty(tichuB);
					beanB.setRunTime(yunxingshijianB);
					beanB.setStopTime(tingjishijianB);
					beanB.setStopTimes(tingjicishuB);
					beanB.setQtyBaoZhuang(bzchanliangB);
					beanB.setBadQtyBaoZhuang(bztichuB);
					if (workShopShiftBean.getTimeUnitName() != null) {
						beanB.setTimeUnitName(workShopShiftBean
								.getTimeUnitName());
					}

				} else if (workShopShiftBean.getMdTeamId().equals("3")) {// 丙班

					beanC.setMdTeamName("丙班");
					if (workShopShiftBean.getQty1() == null
							|| workShopShiftBean.getQty1().equals(equals(""))) {
						xiaohao1 += 0.0;
					} else {
						xiaohao1 += workShopShiftBean.getQty1();
					}
					if (workShopShiftBean.getQty2() == null
							|| workShopShiftBean.getQty2().equals(equals(""))) {
						xiaohao2 += 0.0;
					} else {
						xiaohao2 += workShopShiftBean.getQty2();
					}
					if (workShopShiftBean.getQty3() == null
							|| workShopShiftBean.getQty3().equals(equals(""))) {
						xiaohao3 += 0.0;
					} else {
						xiaohao3 += workShopShiftBean.getQty3();
					}
					if (workShopShiftBean.getQty4() == null
							|| workShopShiftBean.getQty4().equals(equals(""))) {
						xiaohao4 += 0.0;
					} else {
						xiaohao4 += workShopShiftBean.getQty4();
					}
					if (workShopShiftBean.getQty5() == null
							|| workShopShiftBean.getQty5().equals(equals(""))) {
						xiaohao5 += 0.0;
					} else {
						xiaohao5 += workShopShiftBean.getQty5();
					}
					if (workShopShiftBean.getQty6() == null
							|| workShopShiftBean.getQty6().equals(equals(""))) {
						xiaohao6 += 0.0;
					} else {
						xiaohao6 += workShopShiftBean.getQty6();
					}
					if (workShopShiftBean.getQty7() == null
							|| workShopShiftBean.getQty7().equals(equals(""))) {
						xiaohao7 += 0.0;
					} else {
						xiaohao7 += workShopShiftBean.getQty7();
					}
					if (workShopShiftBean.getQty8() == null
							|| workShopShiftBean.getQty8().equals(equals(""))) {
						xiaohao8 += 0.0;
					} else {
						xiaohao8 += workShopShiftBean.getQty8();
					}

					beanC.setQty1(xiaohao1);
					beanC.setQty2(xiaohao2);
					beanC.setQty3(xiaohao3);
					beanC.setQty4(xiaohao4);
					beanC.setQty5(xiaohao5);
					beanC.setQty6(xiaohao6);
					beanC.setQty7(xiaohao7);
					beanC.setQty8(xiaohao8);

					chanliang += workShopShiftBean.getQty();
					tichu += workShopShiftBean.getBadQty();
					bzchanliang += workShopShiftBean.getQtyBaoZhuang();
					bztichu += workShopShiftBean.getBadQtyBaoZhuang();
					yunxingshijian += workShopShiftBean.getRunTime();
					tingjishijian += workShopShiftBean.getStopTime();
					tingjicishu += workShopShiftBean.getStopTimes();
					if (xiaohao1 != 0 && chanliang != 0) {
						beanC.setDanhao1(MathUtil.roundHalfUp(xiaohao1
								/ chanliang, 2));
					} else {
						beanC.setDanhao1(0.0);
					}
					if (xiaohao2 != 0 && chanliang != 0) {
						beanC.setDanhao2(MathUtil.roundHalfUp(xiaohao2
								/ chanliang, 2));
					} else {
						beanC.setDanhao2(0.0);
					}
					if (xiaohao3 != 0 && chanliang != 0) {
						beanC.setDanhao3(MathUtil.roundHalfUp(xiaohao3
								/ chanliang, 2));
					} else {
						beanC.setDanhao3(0.0);
					}
					if (xiaohao4 != 0 && bzchanliang != 0) {
						beanC.setDanhao4(MathUtil.roundHalfUp(xiaohao4
								/ bzchanliang, 2));
					} else {
						beanC.setDanhao4(0.0);
					}
					if (xiaohao5 != 0 && bzchanliang != 0) {
						beanC.setDanhao5(MathUtil.roundHalfUp(xiaohao5
								/ bzchanliang, 2));
					} else {
						beanC.setDanhao5(0.0);
					}
					if (xiaohao6 != 0 && bzchanliang != 0) {
						beanC.setDanhao6(MathUtil.roundHalfUp(xiaohao6
								/ bzchanliang, 2));
					} else {
						beanC.setDanhao6(0.0);
					}
					if (xiaohao7 != 0 && bzchanliang != 0) {
						beanC.setDanhao7(MathUtil.roundHalfUp(xiaohao7
								/ bzchanliang, 2));
					} else {
						beanC.setDanhao7(0.0);
					}
					if (xiaohao8 != 0 && bzchanliang != 0) {
						beanC.setDanhao8(MathUtil.roundHalfUp(xiaohao8
								/ bzchanliang, 2));
					} else {
						beanC.setDanhao8(0.0);
					}
					if (workShopShiftBean.getMdUnitName() != null
							&& workShopShiftBean.getOrderType().equals(1L))
						beanC.setMdUnitName(workShopShiftBean.getMdUnitName());
					if (workShopShiftBean.getMdUnitNameBad() != null
							&& workShopShiftBean.getOrderType().equals(2L))
						beanC.setMdUnitNameBad(workShopShiftBean
								.getMdUnitNameBad());
					if (workShopShiftBean.getMdUnitName1() != null) {
						beanC.setMdUnitName1(workShopShiftBean.getMdUnitName1());
					}

					if (workShopShiftBean.getMdUnitName2() != null) {
						beanC.setMdUnitName2(workShopShiftBean.getMdUnitName2());
					}

					if (workShopShiftBean.getMdUnitName3() != null) {
						beanC.setMdUnitName3(workShopShiftBean.getMdUnitName3());
					}

					if (workShopShiftBean.getMdUnitName4() != null) {
						beanC.setMdUnitName4(workShopShiftBean.getMdUnitName4());
					}

					if (workShopShiftBean.getMdUnitName5() != null) {
						beanC.setMdUnitName5(workShopShiftBean.getMdUnitName5());
					}
					if (workShopShiftBean.getMdUnitName6() != null) {
						beanC.setMdUnitName6(workShopShiftBean.getMdUnitName6());
					}
					if (workShopShiftBean.getMdUnitName7() != null) {
						beanC.setMdUnitName7(workShopShiftBean.getMdUnitName7());
					}
					if (workShopShiftBean.getMdUnitName8() != null) {
						beanC.setMdUnitName8(workShopShiftBean.getMdUnitName8());
					}

					beanC.setQty(chanliang);
					beanC.setBadQty(tichu);
					beanC.setRunTime(yunxingshijian);
					beanC.setStopTime(tingjishijian);
					beanC.setStopTimes(tingjicishu);
					beanC.setQtyBaoZhuang(bzchanliang);
					beanC.setBadQtyBaoZhuang(bztichu);
					if (workShopShiftBean.getTimeUnitName() != null) {
						beanC.setTimeUnitName(workShopShiftBean
								.getTimeUnitName());
					}

				}

			}
			if (beanA != null && beanA.getMdTeamName() != null) {
				returnData.add(beanA);
			}

			if (beanB != null && beanB.getMdTeamName() != null) {
				returnData.add(beanB);
			}
			if (beanC != null && beanC.getMdTeamName() != null) {
				returnData.add(beanC);
			}

		}
		return new DataGrid(returnData, 100L);
	}

	/**
	 * 成型班组生产统计
	 */
	@Override
	public DataGrid getChengXingBanZuList(WorkShopShiftBean shopShiftBean)

	throws Exception {
		List<WorkShopShiftBean> baoCJBeans = new ArrayList<WorkShopShiftBean>();
		String hql = " from SchStatOutput s  left join fetch s.schWorkorder sw  left join fetch sw.mdEquipment me "
				+ " left join fetch me.mdEqpType vv"
				+ " left join fetch sw.mdUnit mu  left join fetch sw.mdTeam mt left join fetch sw.mdShift ms left join fetch"
				+ " sw.mdMat mm   where 1=1 and s.del='0' and me.mdWorkshop.id='2' "; // 2成型车间

		if (StringUtil.notNull(shopShiftBean.getMdTeamId())) {// 班组（甲乙丙）
			hql += " and mt.id='" + shopShiftBean.getMdTeamId() + "' ";
		}

		if (StringUtil.notNull(shopShiftBean.getMdShiftId())) {// 班次（早中晚）
			hql += " and ms.id='" + shopShiftBean.getMdShiftId() + "'";
		}
		hql += StringUtil.fmtDateBetweenParams("sw.date",
				shopShiftBean.getStim(), shopShiftBean.getEtim());

		List<SchStatOutput> outputs = JueBaoServiceImpl.query(hql);
		if (outputs != null) {
			for (SchStatOutput schStatOutput : outputs) {
				WorkShopShiftBean schStat = null;
				try {

					schStat = BeanConvertor.copyProperties(schStatOutput,
							WorkShopShiftBean.class);
					if (schStatOutput.getSchWorkorder().getMdEquipment()
							.getMdEqpType().getName() != null)
						schStat.setEquipmentType(schStatOutput
								.getSchWorkorder().getMdEquipment()
								.getMdEqpType().getName());
					if (schStatOutput.getSchWorkorder().getMdEquipment()
							.getEquipmentCode() != null)
						schStat.setEquipmentNameCode(schStatOutput
								.getSchWorkorder().getMdEquipment()
								.getEquipmentCode());
					if (schStatOutput.getSchWorkorder().getMdEquipment()
							.getEquipmentName() != null)
						schStat.setEquipmentName(schStatOutput
								.getSchWorkorder().getMdEquipment()
								.getEquipmentName());
					if (schStatOutput.getSchWorkorder().getMdEquipment()
							.getId() != null)
						schStat.setMdEquipmentId(schStatOutput
								.getSchWorkorder().getMdEquipment().getId());
					if (schStatOutput.getSchWorkorder().getMdMat().getId() != null)
						schStat.setMdMatId(schStatOutput.getSchWorkorder()
								.getMdMat().getId());
					if (schStatOutput.getSchWorkorder().getMdMat().getName() != null)
						schStat.setMdMatName(schStatOutput.getSchWorkorder()
								.getMdMat().getName());
					if (schStatOutput.getSchWorkorder().getMdShift().getId() != null)
						schStat.setMdShiftId(schStatOutput.getSchWorkorder()
								.getMdShift().getId());
					if (schStatOutput.getSchWorkorder().getMdShift().getName() != null)
						schStat.setMdShiftName(schStatOutput.getSchWorkorder()
								.getMdShift().getName());
					if (schStatOutput.getSchWorkorder().getMdTeam().getId() != null)
						schStat.setMdTeamId(schStatOutput.getSchWorkorder()
								.getMdTeam().getId());
					if (schStatOutput.getSchWorkorder().getMdTeam().getName() != null)
						schStat.setMdTeamName(schStatOutput.getSchWorkorder()
								.getMdTeam().getName());
					if (schStatOutput.getTimeUnit().getId() != null)
						schStat.setTimeUnitId(schStatOutput.getTimeUnit()
								.getId());
					if (schStatOutput.getTimeUnit().getName() != null)
						schStat.setTimeUnitName(schStatOutput.getTimeUnit()
								.getName());
					if (schStatOutput.getSchWorkorder().getDate().toString() != null)
						schStat.setDate(schStatOutput.getSchWorkorder()
								.getDate().toString());
					if (schStatOutput.getMdUnit() != null)
						schStat.setMdUnitName(schStatOutput.getMdUnit()
								.getName());

					if (schStatOutput.getIsFeedback().equals("1")) {
						schStat.setIsFeedback01("已反馈");
					} else {
						schStat.setIsFeedback01("未反馈");
					}
					List<SchStatInput> inputs = inputDaoI
							.query(" from SchStatInput s  left join fetch s.mdMat mm left join fetch mm.mdMatType  left join fetch s.mdUnit mu where s.schStatOutput.id='"
									+ schStat.getId() + "'");
					if (inputs.size() > 0) {
						for (SchStatInput schStatInput : inputs) {
							if (schStatInput.getMdMat().getMdMatType().getId()
									.equals("2")) {// 盘纸
								schStat.setMdUnitName1(schStatInput.getMdUnit()
										.getName());
								schStat.setQty1(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("11")) {// 甘油
								schStat.setMdUnitName2(schStatInput.getMdUnit()
										.getName());
								schStat.setQty2(schStatInput.getQty());

							} else if (schStatInput.getMdMat().getMdMatType()
									.getId().equals("12")) {// 丝束
								schStat.setMdUnitName3(schStatInput.getMdUnit()
										.getName());
								schStat.setQty3(schStatInput.getQty());

							}
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				baoCJBeans.add(schStat);
			}
		}

		double chanliang = 0, tichu = 0, yunxingshijian = 0, tingjishijian = 0, xiaohao1 = 0, xiaohao2 = 0, xiaohao3 = 0;
		long tingjicishu = 0;
		double chanliangA = 0, tichuA = 0, yunxingshijianA = 0, tingjishijianA = 0, xiaohao1A = 0, xiaohao2A = 0, xiaohao3A = 0;
		long tingjicishuA = 0;
		double chanliangB = 0, tichuB = 0, yunxingshijianB = 0, tingjishijianB = 0, xiaohao1B = 0, xiaohao2B = 0, xiaohao3B = 0;
		long tingjicishuB = 0;
		List<WorkShopShiftBean> returnData = new ArrayList<WorkShopShiftBean>();
		if (baoCJBeans != null) {
			WorkShopShiftBean beanA = new WorkShopShiftBean();
			WorkShopShiftBean beanB = new WorkShopShiftBean();
			WorkShopShiftBean beanC = new WorkShopShiftBean();
			for (WorkShopShiftBean workShopShiftBean : baoCJBeans) {// 对所有的甲乙丙丁进行统计
				if (workShopShiftBean.getMdTeamId().equals("1")) {// 甲班

					beanA.setMdTeamName("甲班");
					if (workShopShiftBean.getQty1() == null
							|| workShopShiftBean.getQty1().equals(equals(""))) {
						xiaohao1A += 0.0;
					} else {
						xiaohao1A += workShopShiftBean.getQty1();
					}
					if (workShopShiftBean.getQty2() == null
							|| workShopShiftBean.getQty2().equals(equals(""))) {
						xiaohao2A += 0.0;
					} else {
						xiaohao2A += workShopShiftBean.getQty2();
					}
					if (workShopShiftBean.getQty3() == null
							|| workShopShiftBean.getQty3().equals(equals(""))) {
						xiaohao3A += 0.0;
					} else {
						xiaohao3A += workShopShiftBean.getQty3();
					}
					beanA.setQty1(xiaohao1A);
					beanA.setQty2(xiaohao2A);
					beanA.setQty3(xiaohao3A);

					chanliangA += workShopShiftBean.getQty();
					tichuA += workShopShiftBean.getBadQty();
					yunxingshijianA += workShopShiftBean.getRunTime();
					tingjishijianA += workShopShiftBean.getStopTime();
					tingjicishuA += workShopShiftBean.getStopTimes();
					if (xiaohao1A != 0 && chanliangA != 0) {
						beanA.setDanhao1(MathUtil.roundHalfUp(xiaohao1A
								/ chanliangA, 2));
					} else {
						beanA.setDanhao1(0.0);
					}
					if (xiaohao2A != 0 && chanliangA != 0) {
						beanA.setDanhao2(MathUtil.roundHalfUp(xiaohao2A
								/ chanliangA, 2));
					} else {
						beanA.setDanhao2(0.0);
					}
					if (xiaohao3A != 0 && chanliangA != 0) {
						beanA.setDanhao3(MathUtil.roundHalfUp(xiaohao3A
								/ chanliangA, 2));
					} else {
						beanA.setDanhao3(0.0);
					}

					if (workShopShiftBean.getMdUnitName1() != null) {
						beanA.setMdUnitName1(workShopShiftBean.getMdUnitName1());
					}
					if (workShopShiftBean.getMdUnitName2() != null) {
						beanA.setMdUnitName2(workShopShiftBean.getMdUnitName2());
					}
					if (workShopShiftBean.getMdUnitName3() != null) {
						beanA.setMdUnitName3(workShopShiftBean.getMdUnitName3());
					}

					beanA.setQty(chanliangA);
					beanA.setBadQty(tichuA);
					beanA.setRunTime(yunxingshijianA);
					beanA.setStopTime(tingjishijianA);
					beanA.setStopTimes(tingjicishuA);

					if (workShopShiftBean.getTimeUnitName() != null)
						beanA.setTimeUnitName(workShopShiftBean
								.getTimeUnitName());
					if (workShopShiftBean.getMdUnitName() != null)
						beanA.setMdUnitName(workShopShiftBean.getMdUnitName());

				} else if (workShopShiftBean.getMdTeamId().equals("2")) {// 乙班

					beanB.setMdTeamName("乙班");
					if (workShopShiftBean.getQty1() == null
							|| workShopShiftBean.getQty1().equals(equals(""))) {
						xiaohao1B += 0.0;
					} else {
						xiaohao1B += workShopShiftBean.getQty1();
					}
					if (workShopShiftBean.getQty2() == null
							|| workShopShiftBean.getQty2().equals(equals(""))) {
						xiaohao2B += 0.0;
					} else {
						xiaohao2B += workShopShiftBean.getQty2();
					}
					if (workShopShiftBean.getQty3() == null
							|| workShopShiftBean.getQty3().equals(equals(""))) {
						xiaohao3B += 0.0;
					} else {
						xiaohao3B += workShopShiftBean.getQty3();
					}
					beanB.setQty1(xiaohao1B);
					beanB.setQty2(xiaohao2B);
					beanB.setQty3(xiaohao3B);

					chanliangB += workShopShiftBean.getQty();
					tichuB += workShopShiftBean.getBadQty();
					yunxingshijianB += workShopShiftBean.getRunTime();
					tingjishijianB += workShopShiftBean.getStopTime();
					tingjicishuB += workShopShiftBean.getStopTimes();
					if (xiaohao1B != 0 && chanliangB != 0) {
						beanB.setDanhao1(MathUtil.roundHalfUp(xiaohao1B
								/ chanliangB, 2));
					} else {
						beanB.setDanhao1(0.0);
					}
					if (xiaohao2B != 0 && chanliangB != 0) {
						beanB.setDanhao2(MathUtil.roundHalfUp(xiaohao2B
								/ chanliangB, 2));
					} else {
						beanB.setDanhao2(0.0);
					}
					if (xiaohao3B != 0 && chanliangB != 0) {
						beanB.setDanhao3(MathUtil.roundHalfUp(xiaohao3B
								/ chanliangB, 2));
					} else {
						beanB.setDanhao3(0.0);
					}

					if (StringUtil.notNull(workShopShiftBean.getMdUnitName1())) {
						beanB.setMdUnitName1(workShopShiftBean.getMdUnitName1());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName2())) {
						beanB.setMdUnitName2(workShopShiftBean.getMdUnitName2());
					}
					if (StringUtil.notNull(workShopShiftBean.getMdUnitName3())) {
						beanB.setMdUnitName3(workShopShiftBean.getMdUnitName3());
					}

					beanB.setQty(chanliangB);
					beanB.setBadQty(tichuB);
					beanB.setRunTime(yunxingshijianB);
					beanB.setStopTime(tingjishijianB);
					beanB.setStopTimes(tingjicishuB);
					if (workShopShiftBean.getTimeUnitName() != null)
						beanB.setTimeUnitName(workShopShiftBean
								.getTimeUnitName());
					if (workShopShiftBean.getMdUnitName() != null)
						beanB.setMdUnitName(workShopShiftBean.getMdUnitName());
				} else if (workShopShiftBean.getMdTeamId().equals("3")) {// 丙班

					beanC.setMdTeamName("丙班");
					if (workShopShiftBean.getQty1() == null
							|| workShopShiftBean.getQty1().equals(equals(""))) {
						xiaohao1 += 0.0;
					} else {
						xiaohao1 += workShopShiftBean.getQty1();
					}
					if (workShopShiftBean.getQty2() == null
							|| workShopShiftBean.getQty2().equals(equals(""))) {
						xiaohao2 += 0.0;
					} else {
						xiaohao2 += workShopShiftBean.getQty2();
					}
					if (workShopShiftBean.getQty3() == null
							|| workShopShiftBean.getQty3().equals(equals(""))) {
						xiaohao3 += 0.0;
					} else {
						xiaohao3 += workShopShiftBean.getQty3();
					}

					beanC.setQty1(xiaohao1);
					beanC.setQty2(xiaohao2);
					beanC.setQty3(xiaohao3);

					chanliang += workShopShiftBean.getQty();
					tichu += workShopShiftBean.getBadQty();
					yunxingshijian += workShopShiftBean.getRunTime();
					tingjishijian += workShopShiftBean.getStopTime();
					tingjicishu += workShopShiftBean.getStopTimes();
					if (xiaohao1 != 0 && chanliang != 0) {
						beanC.setDanhao1(MathUtil.roundHalfUp(xiaohao1
								/ chanliang, 2));
					} else {
						beanC.setDanhao1(0.0);
					}
					if (xiaohao2 != 0 && chanliang != 0) {
						beanC.setDanhao2(MathUtil.roundHalfUp(xiaohao2
								/ chanliang, 2));
					} else {
						beanC.setDanhao2(0.0);
					}
					if (xiaohao3 != 0 && chanliang != 0) {
						beanC.setDanhao3(MathUtil.roundHalfUp(xiaohao3
								/ chanliang, 2));
					} else {
						beanC.setDanhao3(0.0);
					}

					if (workShopShiftBean.getMdUnitName1() != null) {
						beanC.setMdUnitName1(workShopShiftBean.getMdUnitName1());
					}

					if (workShopShiftBean.getMdUnitName2() != null) {
						beanC.setMdUnitName2(workShopShiftBean.getMdUnitName2());
					}

					if (workShopShiftBean.getMdUnitName3() != null) {
						beanC.setMdUnitName3(workShopShiftBean.getMdUnitName3());
					}

					beanC.setQty(chanliang);
					beanC.setBadQty(tichu);
					beanC.setRunTime(yunxingshijian);
					beanC.setStopTime(tingjishijian);
					beanC.setStopTimes(tingjicishu);
					if (workShopShiftBean.getTimeUnitName() != null)
						beanC.setTimeUnitName(workShopShiftBean
								.getTimeUnitName());
					if (workShopShiftBean.getMdUnitName() != null)
						beanC.setMdUnitName(workShopShiftBean.getMdUnitName());
				}

			}
			if (beanA != null && beanA.getMdTeamName() != null) {
				returnData.add(beanA);
			}

			if (beanB != null && beanB.getMdTeamName() != null) {
				returnData.add(beanB);
			}
			if (beanC != null && beanC.getMdTeamName() != null) {
				returnData.add(beanC);
			}

		}
		return new DataGrid(returnData, 100L);
	}

	/**
	 * 卷包车间生产统计 取到时间D+工单号
	 */
	// *************************************************************
	@Override
	public DataGrid getJuanYanList(WorkShopShiftBean shopShiftBean)
			throws Exception {
		String temp = "select o.QTY as qty1,"// 产量
				+ "o.BAD_QTY,"// 剔除
				+ "u.NAME as name1,"// 单位
				+ "w.CODE,"// 工单输入的 工单号
				+ "w.TYPE,"// 工单类型
				+ "tab0.name0 as name2,"// 牌号名称
				+ "t.NAME as name3,"// 班组
				+ "s.NAME as name4,"// 班次
				+ "ty.code as code1,"// 辅料编号
				+ "ty.NAME as name5,"// 辅料名称
				+ "i.QTY as qty2,"// 辅料消耗
				+ "ut.NAME as name6,"// 辅料单位
				+ "w.STIM,"// 工单开启时间
				+ "e.EQUIPMENT_NAME "// 设备名称
				+ "from SCH_STAT_INPUT  i,SCH_STAT_OUTPUT o,MD_UNIT u,SCH_WORKORDER w,MD_MAT m,MD_TEAM t, "
				+ "MD_SHIFT s,MD_MAT_TYPE ty,MD_UNIT ut,MD_EQUIPMENT e,MD_WORKSHOP ws, (SELECT m.name name0,o.ID id0 from MD_MAT m,SCH_WORKORDER o where m.id=o.MAT) tab0 "
				+ "where i.OUT_ID=o.ID and o.UNIT=u.ID and o.OID=w.ID and i.MAT=m.id "
				+ "and w.TEAM=t.ID and w.SHIFT=s.ID and m.TID=ty.ID and i.UNIT=ut.ID and w.EQP=e.ID and e.WORK_SHOP=ws.ID "
				+ " and tab0.id0=w.ID " + "and ws.ID='1' and o.DEL='0' ";
		//String sql = "select*from SCH_STAT_INPUT";

		if (StringUtil.notNull(shopShiftBean.getMdTeamId())) {// 班组（甲乙丙）
			temp += " and t.id='" + shopShiftBean.getMdTeamId() + "' ";
		}

		if (StringUtil.notNull(shopShiftBean.getMdShiftId())) {// 班次（早中晚）
			temp += " and s.id='" + shopShiftBean.getMdShiftId() + "'";
		}
		
		if (StringUtil.notNull(shopShiftBean.getMdMatId())) {// 牌号
			temp += " and w.mat='" + shopShiftBean.getMdMatId() + "'";
		}
		
		if (StringUtil.notNull(shopShiftBean.getEquipmentType())) {// 设备型号
			String cid="";
			if("1".equals(shopShiftBean.getEquipmentType()) ){
				cid="4028998349c6880e0149c688e9fb0001"; //卷烟机
			}else if("2".equals(shopShiftBean.getEquipmentType()) ){
				cid="4028998349c6880e0149c688e9fb0000"; //包装机
			}
			temp += " and w.eqp in (select id from MD_EQUIPMENT where EQP_TYPE_ID in ( select id From MD_EQP_TYPE  where cid='"+cid+"' ))";
		}else{
			temp += " and w.eqp in (select id from MD_EQUIPMENT where EQP_TYPE_ID in ( select id From MD_EQP_TYPE  where cid in ('4028998349c6880e0149c688e9fb0001','4028998349c6880e0149c688e9fb0000') ))";
		}
		
		if (StringUtil.notNull(shopShiftBean.getMdEquipmentId())) {// 设备ID
			temp += " and w.eqp='" + shopShiftBean.getMdEquipmentId() + "'";
		}
		
		temp += StringUtil.fmtDateBetweenParams("w.date",shopShiftBean.getStim(), shopShiftBean.getEtim());

		List<Object[]> tempList = (List<Object[]>) JueBaoServiceImpl.queryBySql(temp);

		Hashtable<String, WorkShopShiftBean> ht = new Hashtable<String, WorkShopShiftBean>();
		if (tempList != null) {
			for (Object[] o : tempList) {
				String Dte = o[12].toString();// 开始时间
				String Cde = o[3].toString(); // 工单号
				String key = Dte.substring(0, 13) + "D"
						+ Cde.substring(0, Cde.length() - 3)
						+ Cde.substring(Cde.length() - 2, Cde.length());
				if (ht.get(key) != null) {
					WorkShopShiftBean bean = ht.get(key);
					bean = ObjectToBeanJuanBao(bean, o);
					ht.put(key, bean);
				} else {
					WorkShopShiftBean bean = new WorkShopShiftBean();
					bean = ObjectToBeanJuanBao(bean, o);
					ht.put(key, bean);
				}
			}

			List<WorkShopShiftBean> shopBeans = new ArrayList<WorkShopShiftBean>();
			Iterator j = ht.keySet().iterator();// 进行迭代
			while (j.hasNext()) {// 如果下个元素还有继续迭代
				shopBeans.add(ht.get(j.next()));// 根据key取value
			}
			WorkShopShiftBean wss=heji(shopBeans);
			shopBeans.add(wss);
			return new DataGrid(shopBeans, 100L);
		}

		return null;
	}

	private WorkShopShiftBean heji(List<WorkShopShiftBean> shopBeans) {
		WorkShopShiftBean bean = new WorkShopShiftBean();
		bean.setMdMatName("合计");
		double chanliang = 0.0, tichu = 0.0, bzchanliang = 0.0, bztichu = 0.0, a1 = 0.0, a2 = 0.0, a3 = 0.0, a4 = 0.0, a5 = 0.0, a6 = 0.0, a7 = 0.0, a8 = 0.0, b1 = 0.0, b2 = 0.0, b3 = 0.0, b4 = 0.0, b5 = 0.0, b6 = 0.0, b7 = 0.0, b8 = 0.0;
		for (WorkShopShiftBean workShopShiftBean : shopBeans) {
			chanliang += workShopShiftBean.getQty();
			tichu += workShopShiftBean.getBadQty();
			bzchanliang += workShopShiftBean.getQtyBaoZhuang();
			bztichu += workShopShiftBean.getBadQtyBaoZhuang();
			a1 += workShopShiftBean.getQty1();
			a2 += workShopShiftBean.getQty2();
			a3 += workShopShiftBean.getQty3();
			a4 += workShopShiftBean.getQty4();
			a5 += workShopShiftBean.getQty5();
			a6 += workShopShiftBean.getQty6();
			a7 += workShopShiftBean.getQty7();
			a8 += workShopShiftBean.getQty8();
		}
		if (chanliang > 0 && a1 > 0)
			b1 = MathUtil.roundHalfUp(a1 / chanliang, 2);
		if (chanliang > 0 && a2 > 0)
			b2 = MathUtil.roundHalfUp(a2 / chanliang, 2);
		if (chanliang > 0 && a3 > 0)
			b3 = MathUtil.roundHalfUp(a3 / chanliang, 2);
		if (bzchanliang > 0 && a4 > 0)
			b4 = MathUtil.roundHalfUp(a4 / bzchanliang, 2);
		if (bzchanliang > 0 && a5 > 0)
			b5 = MathUtil.roundHalfUp(a5 / bzchanliang, 2);
		if (bzchanliang > 0 && a6 > 0)
			b6 = MathUtil.roundHalfUp(a6 / bzchanliang, 2);
		if (bzchanliang > 0 && a7 > 0)
			b7 = MathUtil.roundHalfUp(a7 / bzchanliang, 2);
		if (bzchanliang > 0 && a8 > 0)
			b8 = MathUtil.roundHalfUp(a8 / bzchanliang, 2);
		bean.setQty(chanliang);
		bean.setBadQty(tichu);
		bean.setQtyBaoZhuang(bzchanliang);
		bean.setBadQtyBaoZhuang(bztichu);
		bean.setQty1(a1);
		bean.setQty2(a2);
		bean.setQty3(a3);
		bean.setQty4(a4);
		bean.setQty5(a5);
		bean.setQty6(a6);
		bean.setQty7(a7);
		bean.setQty8(a8);
		bean.setDanhao1(b1);
		bean.setDanhao2(b2);
		bean.setDanhao3(b3);
		bean.setDanhao4(b4);
		bean.setDanhao5(b5);
		bean.setDanhao6(b6);
		bean.setDanhao7(b7);
		bean.setDanhao8(b8);
		return bean;
	}

	private WorkShopShiftBean ObjectToBeanJuanBao(WorkShopShiftBean shopBean1,
			Object[] o) {
		if (o != null) {
			String matTypeId = o[8].toString();// 辅料编号 o[4]工单类型
			if (o[4].toString().equals("1")) {// 卷烟机
				if (null != o[0].toString() && !"".equals(o[0].toString())) {
					// //单位转化成万支
					// shopBean1.setQty(Double.valueOf(o[0].toString())*5);//产量
					shopBean1.setQty(Double.valueOf(o[0].toString()));// 产量
				} else {
					o[0] = 0;
				}
				if (null != o[1].toString() && !"".equals(o[0].toString())) {
					// //如果单位是箱转换成支
					// if (StringUtil.trim(o[2].toString()).equals("箱")) {
					// shopBean1.setBadQty(Double.valueOf(o[1].toString())*50000);//剔除
					// }else {
					// }
					shopBean1.setBadQty(Double.valueOf(o[1].toString()));// 剔除
				} else {
					shopBean1.setBadQty(0.0);// 剔除
				}
				// //单位改为万支
				// if (StringUtil.trim(o[2].toString()).equals("箱")) {
				// o[2]="万支";
				// }
				shopBean1.setMdUnitName(o[2].toString());// 单位

			} else if (o[4].toString().equals("2")) {// 包装机
				if (null != o[0].toString() && !"".equals(o[0].toString())) {
					shopBean1.setQtyBaoZhuang(Double.valueOf(o[0].toString()));// 产量
				} else {
					o[0] = 0;
				}
				if (null != o[1].toString() && !"".equals(o[0].toString())) {
					shopBean1
							.setBadQtyBaoZhuang(Double.valueOf(o[1].toString()));// 剔除
				} else {

					shopBean1.setBadQtyBaoZhuang(0.0);// 剔除
				}
				shopBean1.setMdUnitNameBad(o[2].toString());// 单位

			}
			shopBean1.setDate(o[12].toString());
			shopBean1.setMdTeamName(o[6].toString());// 班组
			shopBean1.setMdShiftName(o[7].toString());// 班次

			shopBean1.setWorkorderId(o[3].toString());// 工单编号
			shopBean1.setWorkorderType(o[4].toString());// 工单类型
			shopBean1.setMdMatName(o[5].toString());// 产品名称
			shopBean1.setEquipmentName(o[13].toString());// 设备名字
			// 辅料是滤棒
			if (matTypeId.equals("4")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty1(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName1(o[11].toString());// 单位
				if (null != shopBean1.getQty() && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao1(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao1(0.0);
				}
			}
			// 卷烟纸
			else if (matTypeId.equals("2")) {

				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}

				shopBean1.setQty2(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName2(o[11].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao2(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao2(0.0);
				}

			}
			// 滤棒盘纸
			else if (matTypeId.equals("13")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}

				shopBean1.setQty2(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName2(o[11].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao2(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao2(0.0);
				}
			}
			// 水松纸
			else if (matTypeId.equals("3")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty3(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName3(o[11].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao3(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao3(0.0);
				}
			}
			// 小盒纸
			else if (matTypeId.equals("7")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty4(Double.valueOf(o[10].toString()));// 消耗
				shopBean1.setMdUnitName4(o[11].toString());// 单位
				if (shopBean1.getQtyBaoZhuang() != null
						&& shopBean1.getQtyBaoZhuang() != 0
						&& Double.valueOf(o[10].toString()) != 0) {

					shopBean1.setDanhao4(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ (shopBean1.getQtyBaoZhuang()), 2));
				} else {
					shopBean1.setDanhao4(0.0);
				}
			}
			// 小盒烟膜
			else if (matTypeId.equals("5")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty5(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName5(o[11].toString());// 单位
				if (shopBean1.getQtyBaoZhuang() != null
						&& shopBean1.getQtyBaoZhuang() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao5(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao5(0.0);
				}
			}
			// 内衬纸
			else if (matTypeId.equals("9")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty6(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName6(o[11].toString());// 单位
				if (shopBean1.getQtyBaoZhuang() != null
						&& shopBean1.getQtyBaoZhuang() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao6(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao6(0.0);
				}
			}
			// 条盒纸
			else if (matTypeId.equals("8")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty7(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName7(o[11].toString());// 单位
				if (shopBean1.getQtyBaoZhuang() != null
						&& shopBean1.getQtyBaoZhuang() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao7(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao7(0.0);
				}
			}
			// 条盒烟膜
			else if (matTypeId.equals("6")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty8(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName8(o[11].toString());// 单位
				if (shopBean1.getQtyBaoZhuang() != null
						&& shopBean1.getQtyBaoZhuang() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao8(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQtyBaoZhuang(), 2));
				} else {
					shopBean1.setDanhao8(0.0);
				}
			}

			return shopBean1;
		} else {
			return null;
		}
	}

	/**
	 * 成型车间生产统计
	 */

	@Override
	public DataGrid getChengXingList(WorkShopShiftBean shopShiftBean)
			throws Exception {
		// 查出所有····产量 剔除 单位 工单编号 工单类型 牌号名称 班组 班次
		String temp = "select o.schStatOutput.qty, o.schStatOutput.badQty,o.schStatOutput.mdUnit.name,o.schStatOutput.schWorkorder.id, o.schStatOutput.schWorkorder.type,o.schStatOutput.schWorkorder.mdMat.name,o.schStatOutput.schWorkorder.mdTeam.name,o.schStatOutput.schWorkorder.mdShift.name,"
				// 辅料编号 辅料名称 辅料单位
				+ " o.mdMat.mdMatType.id,o.mdMat.mdMatType.name,o.qty,o.mdUnit.name from SchStatInput o where o.schStatOutput.schWorkorder.type='4' and o.schStatOutput.del='0'";// 卷包车间
		if (StringUtil.notNull(shopShiftBean.getMdTeamId())) {// 班组（甲乙丙）
			temp += " and o.schStatOutput.schWorkorder.mdTeam.id='"+ shopShiftBean.getMdTeamId() + "' ";
		}

		if (StringUtil.notNull(shopShiftBean.getMdShiftId())) {// 班次（早中晚）
			temp += " and o.schStatOutput.schWorkorder.mdShift.id='"+ shopShiftBean.getMdShiftId() + "'";
		}
		
		if (StringUtil.notNull(shopShiftBean.getMdMatId())) {// 牌号
			temp += " and o.schStatOutput.schWorkorder.mdMat.id='" + shopShiftBean.getMdMatId() + "'";
		}
		
		/*if (StringUtil.notNull(shopShiftBean.getMdEquipmentId())) {// 设备ID
			temp += " and o.schStatOutput.schWorkorder.MdEquipment.id='" + shopShiftBean.getMdEquipmentId() + "'";
		}*/
		
		temp += StringUtil.fmtDateBetweenParams("o.schStatOutput.schWorkorder.date", shopShiftBean.getStim(),shopShiftBean.getEtim());

		List<Object[]> tempList = JueBaoServiceImpl.queryObjectArray(temp);

		Hashtable<String, WorkShopShiftBean> ht = new Hashtable<String, WorkShopShiftBean>();
		if (tempList != null) {
			for (Object[] o : tempList) {

				String orderId = o[3].toString();// 根据key取到对象再对对象进行赋值，把工单编号做为key
				if (ht.get(orderId) != null) {
					WorkShopShiftBean bean = ht.get(orderId);
					bean = ObjectToBeanChengXing(bean, o);
					ht.put(orderId, bean);
				} else {

					WorkShopShiftBean bean = new WorkShopShiftBean();
					bean = ObjectToBeanChengXing(bean, o);
					ht.put(orderId, bean);
				}
			}

			List<WorkShopShiftBean> shopBeans = new ArrayList<WorkShopShiftBean>();
			Iterator j = ht.keySet().iterator();// 进行迭代
			while (j.hasNext()) {// 如果下个元素还有继续迭代
				shopBeans.add(ht.get(j.next()));// 根据key取value
			}
			shopBeans.add(heji(shopBeans));

			return new DataGrid(shopBeans, 100L);

		}

		return null;
	}

	private WorkShopShiftBean ObjectToBeanChengXing(
			WorkShopShiftBean shopBean1, Object[] o) {
		if (o != null) {
			String matTypeId = o[8].toString();
			// if(o[4].toString().equals("4")){//成型机
			if (null != o[0].toString() && !"".equals(o[0].toString())) {
				shopBean1.setQty(Double.valueOf(o[0].toString()));// 产量
			} else {
				o[0] = 0;
			}
			if (null != o[1].toString() && !"".equals(o[0].toString())) {
				shopBean1.setBadQtyBaoZhuang(Double.valueOf(o[1].toString()));// 剔除
			} else {

				shopBean1.setBadQty(0.0);// 剔除
			}
			shopBean1.setMdUnitName(o[2].toString());// 单位

			// }
			shopBean1.setMdTeamName(o[6].toString());// 班组
			shopBean1.setMdShiftName(o[7].toString());// 班次

			shopBean1.setWorkorderId(o[3].toString());// 工单编号
			shopBean1.setWorkorderType(o[4].toString());// 工单类型
			shopBean1.setMdMatName(o[5].toString());// 产品名称
			if (matTypeId.equals("2")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty1(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName1(o[11].toString());// 单位
				if (null != shopBean1.getQty() && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao1(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao1(0.0);
				}
			} else if (matTypeId.equals("11")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty2(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName2(o[11].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao2(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao2(0.0);
				}
			} else if (matTypeId.equals("12")) {
				if (null != o[10].toString() && !"".equals(o[10].toString())) {
					o[10] = o[10].toString();
				} else {
					o[10] = 0;
				}
				shopBean1.setQty3(Double.valueOf(o[10].toString()));// 单耗
				shopBean1.setMdUnitName3(o[11].toString());// 单位
				if (shopBean1.getQty() != null && shopBean1.getQty() != 0
						&& Double.valueOf(o[10].toString()) != 0) {
					shopBean1.setDanhao3(MathUtil.roundHalfUp(
							Double.valueOf(o[10].toString())
									/ shopBean1.getQty(), 2));
				} else {
					shopBean1.setDanhao3(0.0);
				}
			}

			return shopBean1;
		} else {
			return null;
		}
	}

	

	/***
	 * excel导出卷烟车间（修改后的代码）
	 */
	@Override
	public HSSFWorkbook ExportExcelJB2(WorkShopShiftBean baoCJBean)
			throws Exception {
		// TODO Auto-generated method stub
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 5, 13 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			// 测试添加时间
			th.add("1,1,13,1,卷包车间生产统计");
			th.add("2,1,2,2,生产时间：");
			th.add("2,3,6,2," + baoCJBean.getStim() + "   到     "
					+ baoCJBean.getEtim());
			th.add("3,1,1,4,设备");
			th.add("3,2,2,4,牌号");
			th.add("3,3,3,4,班次");
			th.add("3,4,4,4,班组");

			th.add("3,5,7,3,卷烟机");
			th.add("3,8,13,3,包装机");

			th.add("4,5,5,4,产量(箱)");
			th.add("4,6,6,4,盘纸(米)");
			th.add("4,7,7,4,水松纸(KG)");

			th.add("4,8,8,4,产量(箱)");
			th.add("4,9,9,4,铝箔纸(KG)");
			th.add("4,10,10,4,商标纸(张)");
			th.add("4,11,11,4,小透(KG)");
			th.add("4,12,12,4,条盒纸(张)");
			th.add("4,13,13,4,条透(KG)");

			DataGrid dg = this.getJuanYanList(baoCJBean);

//			Map<String, String> maps = new HashMap<String, String>();
//			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
//			if (dg.getRows() != null) {
//				for (int i = 0; i < dg.getRows().size(); i++) {
//					WorkShopShiftBean workShopbean = (WorkShopShiftBean) dg
//							.getRows().get(i);
//					maps.put((5 + i) + ",1,1,1",
//							workShopbean.getEquipmentName());// 第5行;第1列开始 ~
//																// 第1列结束 一共 跨1行
//					maps.put((5 + i) + ",2,2,1", workShopbean.getMdMatName());// 第5行;第2列开始
//																				// ~
//																				// 第2列结束
//																				// 一共
//																				// 跨1行
//					maps.put((5 + i) + ",3,3,1", workShopbean.getMdShiftName());
//					maps.put((5 + i) + ",4,4,1", workShopbean.getMdTeamName());
//
//					// 卷烟机
//					maps.put((5 + i) + ",5,5,1", workShopbean.getQty()
//							.toString());// 产量
//					maps.put((5 + i) + ",6,6,1", workShopbean.getQty1()
//							.toString());// 盘纸
//					maps.put((5 + i) + ",7,7,1", workShopbean.getQty3()
//							.toString());// 水松纸
//
//					// 包装机
//					maps.put((5 + i) + ",8,8,1", workShopbean.getQtyBaoZhuang()
//							.toString());// 产量
//					maps.put((5 + i) + ",9,9,1", workShopbean.getQty6()
//							.toString());// 铝箔纸(KG)
//					maps.put((5 + i) + ",10,10,1", workShopbean.getQty4()
//							.toString());// 商标纸(张)
//					maps.put((5 + i) + ",11,11,1", workShopbean.getQty5()
//							.toString());// 小透(KG)
//					maps.put((5 + i) + ",12,12,1", workShopbean.getQty7()
//							.toString());// 条盒纸(张)
//					maps.put((5 + i) + ",13,13,1", workShopbean.getQty8()
//							.toString());// 条透(KG)
//
//					tdVals.add(maps);
//				}
//			}
			// td 当前开始行,当前结束行,一共多少列
//			int[] tdTables = { 5, (5 + tdVals.size() - 1), 13 };
//			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			
			String[] method=new String[]{"getEquipmentName","getMdMatName","getMdShiftName","getMdTeamName","getQty","getQty1","getQty3","getQtyBaoZhuang","getQty6","getQty4","getQty5","getQty7","getQty8"};
			//开始行
			int startLine=4;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,WorkShopShiftBean.class,dg.getRows());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

	/***
	 * excel導出卷烟车间
	 */

	@Override
	public HSSFWorkbook ExportExcelJB(WorkShopShiftBean baoCJBean)
			throws Exception {
		// TODO Auto-generated method stub
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 34 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,1,3,班次");
			th.add("1,2,2,3,班组	");
			th.add("1,3,3,3,产品");
			th.add("1,4,4,3,工单开启时间");
			th.add("1,5,16,1,卷烟机组");
			th.add("1,17,34,1,包装机组");

			th.add("2,5,5,3,实际产量");
			th.add("2,6,6,3,产量单位");
			th.add("2,7,7,3,剔除");

			th.add("2,8,10,2,滤棒");
			th.add("2,12,13,2,滤棒盘纸 ");
			th.add("2,14,16,2,水松纸");

			th.add("2,17,17,3,实际产量");
			th.add("2,18,18,3,产量单位");
			th.add("2,19,19,3,剔除");

			th.add("2,20,20,2,小盒");
			th.add("2,23,25,2, 小透明纸 ");
			th.add("2,26,27,2,内衬纸");
			th.add("2,28,31,2,条盒");
			th.add("2,32,34,2,大透明纸");

			th.add("3,8,8,1,消耗");
			th.add("3,9,9,1,单位");
			th.add("3,10,10,1,单耗");
			th.add("3,11,11,1,消耗");
			th.add("3,12,12,1,单位");
			th.add("3,13,13,1,单耗");
			th.add("3,14,14,1,消耗");
			th.add("3,15,15,1,单位");
			th.add("3,16,16,1,单耗");

			th.add("3,20,20,1,消耗");
			th.add("3,21,21,1,单位");
			th.add("3,22,22,1,单耗");
			th.add("3,23,23,1,消耗");
			th.add("3,24,24,1,单位");
			th.add("3,25,25,1,单耗");
			th.add("3,26,26,1,消耗");
			th.add("3,27,27,1,单位");
			th.add("3,28,28,1,单耗");
			th.add("3,29,29,1,消耗");
			th.add("3,30,30,1,单位");
			th.add("3,31,31,1,单耗");
			th.add("3,32,32,1,消耗");
			th.add("3,33,33,1,单位");
			th.add("3,34,34,1,单耗");

			DataGrid dg = this.getJuanYanList(baoCJBean);

			Map<String, String> maps = new HashMap<String, String>();
			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
			if (dg.getRows() != null) {
				for (int i = 0; i < dg.getRows().size(); i++) {
					WorkShopShiftBean workShopbean = (WorkShopShiftBean) dg
							.getRows().get(i);
					maps.put((4 + i) + ",1,1,1", workShopbean.getMdShiftName());// 第4行;第1列开始
																				// ~
																				// 第1列结束
																				// 一共
																				// 跨1行
					maps.put((4 + i) + ",2,2,1", workShopbean.getMdTeamName());// 第4行;第2列开始
																				// ~
																				// 第2列结束
																				// 一共
																				// 跨1行
					maps.put((4 + i) + ",3,3,1", workShopbean.getMdMatName());
					maps.put((4 + i) + ",4,4,1", workShopbean.getDate());

					maps.put((4 + i) + ",5,5,1", workShopbean.getQty()
							.toString());
					maps.put((4 + i) + ",6,6,1", workShopbean.getMdUnitName());
					maps.put((4 + i) + ",7,7,1", workShopbean.getBadQty()
							.toString());

					maps.put((4 + i) + ",8,8,1", workShopbean.getQty1()
							.toString());
					maps.put((4 + i) + ",9,9,1", workShopbean.getMdUnitName1());
					maps.put((4 + i) + ",10,10,1", workShopbean.getDanhao1()
							.toString());

					maps.put((4 + i) + ",11,11,1", workShopbean.getQty2()
							.toString());
					maps.put((4 + i) + ",12,12,1",
							workShopbean.getMdUnitName2());
					maps.put((4 + i) + ",13,13,1", workShopbean.getDanhao2()
							.toString());

					maps.put((4 + i) + ",14,14,1", workShopbean.getQty3()
							.toString());
					maps.put((4 + i) + ",15,15,1",
							workShopbean.getMdUnitName3());
					maps.put((4 + i) + ",16,16,1", workShopbean.getDanhao3()
							.toString());

					maps.put((4 + i) + ",17,17,1", workShopbean
							.getQtyBaoZhuang().toString());
					maps.put((4 + i) + ",18,18,1",
							workShopbean.getMdUnitNameBad());
					maps.put((4 + i) + ",19,19,1", workShopbean
							.getBadQtyBaoZhuang().toString());

					maps.put((4 + i) + ",20,20,1", workShopbean.getQty4()
							.toString());
					maps.put((4 + i) + ",21,21,1",
							workShopbean.getMdUnitName4());
					maps.put((4 + i) + ",22,22,1", workShopbean.getDanhao4()
							.toString());

					maps.put((4 + i) + ",23,23,1", workShopbean.getQty5()
							.toString());
					maps.put((4 + i) + ",24,24,1",
							workShopbean.getMdUnitName5());
					maps.put((4 + i) + ",25,25,1", workShopbean.getDanhao5()
							.toString());

					maps.put((4 + i) + ",26,26,1", workShopbean.getQty6()
							.toString());
					maps.put((4 + i) + ",27,27,1",
							workShopbean.getMdUnitName6());
					maps.put((4 + i) + ",28,28,1", workShopbean.getDanhao6()
							.toString());

					maps.put((4 + i) + ",29,29,1", workShopbean.getQty7()
							.toString());
					maps.put((4 + i) + ",30,30,1",
							workShopbean.getMdUnitName7());
					maps.put((4 + i) + ",31,31,1", workShopbean.getDanhao7()
							.toString());

					maps.put((4 + i) + ",32,32,1", workShopbean.getQty8()
							.toString());
					maps.put((4 + i) + ",33,33,1",
							workShopbean.getMdUnitName8());
					maps.put((4 + i) + ",34,34,1", workShopbean.getDanhao8()
							.toString());
					tdVals.add(maps);
				}
			}
			// td 当前开始行,当前结束行,一共多少列
			int[] tdTables = { 4, (4 + tdVals.size() - 1), 34 };

			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			/*
			 * fos = new FileOutputStream("d:\\卷包车间"+new
			 * Date().getTime()+".xls"); wb.write(fos); fos.flush();
			 * fos.close(); json.setSuccess(true);
			 * json.setMsg("导出Execl成功在D盘根目录下");
			 */
		} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}

	/**
	 * ExportExcel導出卷烟班组
	 */
	@Override
	public HSSFWorkbook ExportExcelJBBZ2(WorkShopShiftBean baoCJBean)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 6, 21 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行

			th.add("1,1,21,1,卷包班组生产统计");
			th.add("2,1,2,2,生产日期：");
			th.add("2,3,6,2," + baoCJBean.getStim() + "   到     "
					+ baoCJBean.getEtim());

			th.add("3,1,1,5,班组");
			th.add("3,2,9,3,卷烟机组");
			th.add("3,10,21,3,包装机组");

			th.add("4,2,2,5,实际产量(箱)");
			th.add("4,3,3,5,剔除(箱)");

			th.add("4,4,5,4,滤棒");
			th.add("5,4,4,5,消耗(支)");
			th.add("5,5,5,5,单耗(支/箱)");

			th.add("4,6,7,4,滤棒盘纸");
			th.add("5,6,6,5,消耗(米)");
			th.add("5,7,7,5,单耗(米/箱)");

			th.add("4,8,9,4,水松纸");
			th.add("5,8,8,5,消耗(KG)");
			th.add("5,9,9,5,单耗(KG/箱)");

			th.add("4,10,10,5,实际产量(箱)");
			th.add("4,11,11,5,剔除(箱)");

			th.add("4,12,13,4,小盒纸");
			th.add("5,12,12,5,消耗(张)");
			th.add("5,13,13,5,单耗(张/箱)");

			th.add("4,14,15,4,小透明纸");
			th.add("5,14,14,5,消耗(KG)");
			th.add("5,15,15,5,单耗(KG/箱)");

			th.add("4,16,17,4,内衬纸");
			th.add("5,16,16,5,消耗(KG)");
			th.add("5,17,17,5,单耗(KG/箱)");

			th.add("4,18,19,4,条盒纸");
			th.add("5,18,18,5,消耗(KG)");
			th.add("5,19,19,5,单耗(KG/箱)");

			th.add("4,20,21,4,内衬纸");
			th.add("5,20,20,5,消耗(KG)");
			th.add("5,21,21,5,单耗(KG/箱)");

			DataGrid dg = this.getJuanBaoBanZuList(baoCJBean);
//			Map<String, String> maps = new HashMap<String, String>();
//			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
//			if (dg.getRows() != null) {
//				for (int i = 0; i < dg.getRows().size(); i++) {
//					WorkShopShiftBean workShopbean = (WorkShopShiftBean) dg
//							.getRows().get(i);
//
//					maps.put((6 + i) + ",1,1,1", workShopbean.getMdTeamName());// 第4行;第2列开始
//																				// ~
//																				// 第2列结束
//																				// 一共
//																				// 跨1行
//					maps.put((6 + i) + ",2,2,1", workShopbean.getQty()
//							.toString());// 实际产量
//					maps.put((6 + i) + ",3,3,1", workShopbean.getBadQty()
//							.toString());// 剔除
//
//					maps.put((6 + i) + ",4,4,1", workShopbean.getQty1()
//							.toString());// 滤棒消耗
//					maps.put((6 + i) + ",5,5,1", workShopbean.getDanhao1()
//							.toString());//
//
//					maps.put((6 + i) + ",6,6,1", workShopbean.getQty2()
//							.toString());// 滤棒盘纸消耗
//					maps.put((6 + i) + ",7,7,1", workShopbean.getDanhao2()
//							.toString());//
//
//					maps.put((6 + i) + ",8,8,1", workShopbean.getQty3()
//							.toString());// 水松纸消耗
//					maps.put((6 + i) + ",9,9,1", workShopbean.getDanhao3()
//							.toString());//
//
//					maps.put((6 + i) + ",10,10,1", workShopbean
//							.getQtyBaoZhuang().toString());// 包装机产量
//					maps.put((6 + i) + ",11,11,1", workShopbean
//							.getBadQtyBaoZhuang().toString());// 剔除
//
//					maps.put((6 + i) + ",12,12,1", workShopbean.getQty4()
//							.toString());// 小盒纸消耗
//					maps.put((6 + i) + ",13,13,1", workShopbean.getDanhao4()
//							.toString());//
//
//					maps.put((6 + i) + ",14,14,1", workShopbean.getQty5()
//							.toString());// 小透明纸消耗
//					maps.put((6 + i) + ",15,15,1", workShopbean.getDanhao5()
//							.toString());//
//
//					maps.put((6 + i) + ",16,16,1", workShopbean.getQty6()
//							.toString());// 内衬纸消耗
//					maps.put((6 + i) + ",17,17,1", workShopbean.getDanhao6()
//							.toString());//
//
//					maps.put((6 + i) + ",18,18,1", workShopbean.getQty7()
//							.toString());// 条盒纸消耗
//					maps.put((6 + i) + ",19,19,1", workShopbean.getDanhao7()
//							.toString());//
//
//					maps.put((6 + i) + ",20,20,1", workShopbean.getQty8()
//							.toString());// 大透明纸消耗
//					maps.put((6 + i) + ",21,21,1", workShopbean.getDanhao8()
//							.toString());//
//
//					tdVals.add(maps);
//				}
//			}
			// td 当前开始行,当前结束行,一共多少列
//			int[] tdTables = { 6, (6 + tdVals.size() - 1), 21 };
//
//			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			
			String[] method=new String[]
            {"getMdTeamName","getQty","getBadQty","getQty1",
			 "getDanhao1","getQty2","getDanhao2","getQty3",
			 "getQtyBaoZhuang","getBadQtyBaoZhuang","getDanhao3","getQty4",
			 "getDanhao4","getQty5","getDanhao5","getQty6",
			 "getDanhao6","getQty7","getDanhao7","getQty8",
			 "getDanhao8"
			 };
			//开始行
			int startLine=5;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,WorkShopShiftBean.class,dg.getRows());	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

	/**
	 * ExportExcel導出卷烟班组
	 */
	@Override
	public HSSFWorkbook ExportExcelJBBZ(WorkShopShiftBean baoCJBean)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 31 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,1,3,班组");

			th.add("1,2,13,1,卷烟机组");
			th.add("1,14,31,1,包装机组");

			th.add("2,2,2,3,实际产量");
			th.add("2,3,3,3,产量单位");
			th.add("2,4,4,3,剔除");

			th.add("2,5,7,2,滤棒");
			th.add("2,8,10,2,滤棒盘纸 ");
			th.add("2,11,13,2,水松纸");

			th.add("2,14,14,3,实际产量");
			th.add("2,15,15,3,产量单位");
			th.add("2,16,16,3,剔除");

			th.add("2,17,19,2,小盒");
			th.add("2,20,22,2, 小透明纸 ");
			th.add("2,23,25,2,内衬纸");
			th.add("2,26,28,2,条盒");
			th.add("2,29,31,2,大透明纸");

			th.add("3,5,5,3,消耗");
			th.add("3,6,6,3,单位");
			th.add("3,7,7,3,单耗");
			th.add("3,8,8,3,消耗");
			th.add("3,9,9,3,单位");
			th.add("3,10,10,3,单耗");
			th.add("3,11,11,3,消耗");
			th.add("3,12,12,3,单位");
			th.add("3,13,13,3,单耗");

			th.add("3,17,17,3,消耗");
			th.add("3,18,18,3,单位");
			th.add("3,19,19,3,单耗");
			th.add("3,20,20,3,消耗");
			th.add("3,21,21,3,单位");
			th.add("3,22,22,3,单耗");
			th.add("3,23,23,3,消耗");
			th.add("3,24,24,3,单位");
			th.add("3,25,25,3,单耗");
			th.add("3,26,26,3,消耗");
			th.add("3,27,27,3,单位");
			th.add("3,28,28,3,单耗");
			th.add("3,29,29,3,消耗");
			th.add("3,30,30,3,单位");
			th.add("3,31,31,3,单耗");

			DataGrid dg = this.getJuanBaoBanZuList(baoCJBean);

			Map<String, String> maps = new HashMap<String, String>();
			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
			if (dg.getRows() != null) {
				for (int i = 0; i < dg.getRows().size(); i++) {
					WorkShopShiftBean workShopbean = (WorkShopShiftBean) dg
							.getRows().get(i);

					maps.put((4 + i) + ",1,1,1", workShopbean.getMdTeamName());// 第4行;第2列开始
																				// ~
																				// 第2列结束
																				// 一共
																				// 跨1行

					maps.put((4 + i) + ",2,2,1", workShopbean.getQty()
							.toString());
					maps.put((4 + i) + ",3,3,1", workShopbean.getMdUnitName());
					maps.put((4 + i) + ",4,4,1", workShopbean.getBadQty()
							.toString());

					maps.put((4 + i) + ",5,5,1", workShopbean.getQty1()
							.toString());
					maps.put((4 + i) + ",6,6,1", workShopbean.getMdUnitName1());
					maps.put((4 + i) + ",7,7,1", workShopbean.getDanhao1()
							.toString());

					maps.put((4 + i) + ",8,8,1", workShopbean.getQty2()
							.toString());
					maps.put((4 + i) + ",9,9,1", workShopbean.getMdUnitName2());
					maps.put((4 + i) + ",10,10,1", workShopbean.getDanhao2()
							.toString());

					maps.put((4 + i) + ",11,11,1", workShopbean.getQty3()
							.toString());
					maps.put((4 + i) + ",12,12,1",
							workShopbean.getMdUnitName3());
					maps.put((4 + i) + ",13,13,1", workShopbean.getDanhao3()
							.toString());

					maps.put((4 + i) + ",14,14,1", workShopbean
							.getQtyBaoZhuang().toString());
					maps.put((4 + i) + ",15,15,1",
							workShopbean.getMdUnitNameBad());
					maps.put((4 + i) + ",16,16,1", workShopbean
							.getBadQtyBaoZhuang().toString());

					maps.put((4 + i) + ",17,17,1", workShopbean.getQty4()
							.toString());
					maps.put((4 + i) + ",18,18,1",
							workShopbean.getMdUnitName4());
					maps.put((4 + i) + ",19,19,1", workShopbean.getDanhao4()
							.toString());

					maps.put((4 + i) + ",20,20,1", workShopbean.getQty5()
							.toString());
					maps.put((4 + i) + ",21,21,1",
							workShopbean.getMdUnitName5());
					maps.put((4 + i) + ",22,22,1", workShopbean.getDanhao5()
							.toString());

					maps.put((4 + i) + ",23,23,1", workShopbean.getQty6()
							.toString());
					maps.put((4 + i) + ",24,24,1",
							workShopbean.getMdUnitName6());
					maps.put((4 + i) + ",25,25,1", workShopbean.getDanhao6()
							.toString());

					maps.put((4 + i) + ",26,26,1", workShopbean.getQty7()
							.toString());
					maps.put((4 + i) + ",27,27,1",
							workShopbean.getMdUnitName7());
					maps.put((4 + i) + ",28,28,1", workShopbean.getDanhao7()
							.toString());

					maps.put((4 + i) + ",29,29,1", workShopbean.getQty8()
							.toString());
					maps.put((4 + i) + ",30,30,1",
							workShopbean.getMdUnitName8());
					maps.put((4 + i) + ",31,31,1", workShopbean.getDanhao8()
							.toString());
					tdVals.add(maps);
				}
			}
			// td 当前开始行,当前结束行,一共多少列
			int[] tdTables = { 4, (4 + tdVals.size() - 1), 31 };

			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			/*
			 * fos = new FileOutputStream("d:\\卷包班组"+new
			 * Date().getTime()+".xls"); wb.write(fos); fos.flush();
			 * fos.close(); json.setSuccess(true);
			 * json.setMsg("导出Execl成功在D盘根目录下");
			 */
		} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}

	/**
	 * ExportExcel導出卷烟品牌
	 */
	@Override
	public HSSFWorkbook ExportExcelJBPP(WorkShopShiftBean baoCJBean)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 31 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,1,3,班组");

			th.add("1,2,13,1,卷烟机组");
			th.add("1,14,31,1,包装机组");

			th.add("2,2,2,3,实际产量");
			th.add("2,3,3,3,产量单位");
			th.add("2,4,4,3,剔除");

			th.add("2,5,7,2,滤棒");
			th.add("2,8,10,2,滤棒盘纸 ");
			th.add("2,11,13,2,水松纸");

			th.add("2,14,14,3,实际产量");
			th.add("2,15,15,3,产量单位");
			th.add("2,16,16,3,剔除");

			th.add("2,17,19,2,小盒");
			th.add("2,20,22,2, 小透明纸 ");
			th.add("2,23,25,2,内衬纸");
			th.add("2,26,28,2,条盒");
			th.add("2,29,31,2,大透明纸");

			th.add("3,5,5,3,消耗");
			th.add("3,6,6,3,单位");
			th.add("3,7,7,3,单耗");
			th.add("3,8,8,3,消耗");
			th.add("3,9,9,3,单位");
			th.add("3,10,10,3,单耗");
			th.add("3,11,11,3,消耗");
			th.add("3,12,12,3,单位");
			th.add("3,13,13,3,单耗");

			th.add("3,17,17,3,消耗");
			th.add("3,18,18,3,单位");
			th.add("3,19,19,3,单耗");
			th.add("3,20,20,3,消耗");
			th.add("3,21,21,3,单位");
			th.add("3,22,22,3,单耗");
			th.add("3,23,23,3,消耗");
			th.add("3,24,24,3,单位");
			th.add("3,25,25,3,单耗");
			th.add("3,26,26,3,消耗");
			th.add("3,27,27,3,单位");
			th.add("3,28,28,3,单耗");
			th.add("3,29,29,3,消耗");
			th.add("3,30,30,3,单位");
			th.add("3,31,31,3,单耗");

			DataGrid dg = this.getJuanBaoChanPinList(baoCJBean);

			Map<String, String> maps = new HashMap<String, String>();
			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
			if (dg.getRows() != null) {
				for (int i = 0; i < dg.getRows().size(); i++) {
					WorkShopShiftBean workShopbean = (WorkShopShiftBean) dg
							.getRows().get(i);

					maps.put((4 + i) + ",1,1,1", workShopbean.getMdMatName());// 第4行;第2列开始
																				// ~
																				// 第2列结束
																				// 一共
																				// 跨1行

					maps.put((4 + i) + ",2,2,1", workShopbean.getQty()
							.toString());
					maps.put((4 + i) + ",3,3,1", workShopbean.getMdUnitName());
					maps.put((4 + i) + ",4,4,1", workShopbean.getBadQty()
							.toString());

					maps.put((4 + i) + ",5,5,1", workShopbean.getQty1()
							.toString());
					maps.put((4 + i) + ",6,6,1", workShopbean.getMdUnitName1());
					maps.put((4 + i) + ",7,7,1", workShopbean.getDanhao1()
							.toString());

					maps.put((4 + i) + ",8,8,1", workShopbean.getQty2()
							.toString());
					maps.put((4 + i) + ",9,9,1", workShopbean.getMdUnitName2());
					maps.put((4 + i) + ",10,10,1", workShopbean.getDanhao2()
							.toString());

					maps.put((4 + i) + ",11,11,1", workShopbean.getQty3()
							.toString());
					maps.put((4 + i) + ",12,12,1",
							workShopbean.getMdUnitName3());
					maps.put((4 + i) + ",13,13,1", workShopbean.getDanhao3()
							.toString());

					maps.put((4 + i) + ",14,14,1", workShopbean
							.getQtyBaoZhuang().toString());
					maps.put((4 + i) + ",15,15,1",
							workShopbean.getMdUnitNameBad());
					maps.put((4 + i) + ",16,16,1", workShopbean
							.getBadQtyBaoZhuang().toString());

					maps.put((4 + i) + ",17,17,1", workShopbean.getQty4()
							.toString());
					maps.put((4 + i) + ",18,18,1",
							workShopbean.getMdUnitName4());
					maps.put((4 + i) + ",19,19,1", workShopbean.getDanhao4()
							.toString());

					maps.put((4 + i) + ",20,20,1", workShopbean.getQty5()
							.toString());
					maps.put((4 + i) + ",21,21,1",
							workShopbean.getMdUnitName5());
					maps.put((4 + i) + ",22,22,1", workShopbean.getDanhao5()
							.toString());

					maps.put((4 + i) + ",23,23,1", workShopbean.getQty6()
							.toString());
					maps.put((4 + i) + ",24,24,1",
							workShopbean.getMdUnitName6());
					maps.put((4 + i) + ",25,25,1", workShopbean.getDanhao6()
							.toString());

					maps.put((4 + i) + ",26,26,1", workShopbean.getQty7()
							.toString());
					maps.put((4 + i) + ",27,27,1",
							workShopbean.getMdUnitName7());
					maps.put((4 + i) + ",28,28,1", workShopbean.getDanhao7()
							.toString());

					maps.put((4 + i) + ",29,29,1", workShopbean.getQty8()
							.toString());
					maps.put((4 + i) + ",30,30,1",
							workShopbean.getMdUnitName8());
					maps.put((4 + i) + ",31,31,1", workShopbean.getDanhao8()
							.toString());
					tdVals.add(maps);
				}
			}
			// td 当前开始行,当前结束行,一共多少列
			int[] tdTables = { 4, (4 + tdVals.size() - 1), 31 };

			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			/*
			 * fos = new FileOutputStream("d:\\卷包产品"+new
			 * Date().getTime()+".xls"); wb.write(fos); fos.flush();
			 * fos.close(); json.setSuccess(true);
			 * json.setMsg("导出Execl成功在D盘根目录下");
			 */} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}

	

	/**
	 * ExportExcel導出卷烟机组
	 */
	@Override
	public HSSFWorkbook ExportExcelJBJZ(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		// Json json=new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 27 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,1,3,设备编号");
			th.add("1,2,2,3,设备名称");
			th.add("1,3,3,3,设备型号");
			th.add("1,4,4,3,班组");
			th.add("1,5,5,3,牌号");
			th.add("1,6,6,3,产量");
			th.add("1,7,7,3,单位");
			th.add("1,8,8,3,剔除	");

			th.add("1,9,17,1,辅料消耗");

			th.add("1,18,18,3,生产日期");

			th.add("1,19,27,1,运行统计");

			th.add("2,9,11,2,滤棒");
			th.add("2,12,14,2,盘纸");
			th.add("2,15,17,2,水松纸	");

			th.add("2,19,19,3,运行时间");
			th.add("2,20,20,3,停机时间");
			th.add("2,21,21,3,单位");

			th.add("2,22,22,3,停机次数");
			th.add("2,23,23,3,数据最后接收");
			th.add("2,24,24,3,数据最后编辑");

			th.add("2,25,25,3,是否反馈");
			th.add("2,26,26,3, 反馈时间 ");
			th.add("2,27,27,3,反馈人");

			th.add("3,9,9,3,消耗");
			th.add("3,10,10,3,单位");
			th.add("3,11,11,3,单耗");
			th.add("3,12,12,3,消耗");
			th.add("3,13,13,3,单位");
			th.add("3,14,14,3,单耗");
			th.add("3,15,15,3,消耗");
			th.add("3,16,16,3,单位");
			th.add("3,17,17,3,单耗");

			DataGrid dg = this.getJueYanList(baoCJBean, type);

			Map<String, String> maps = new HashMap<String, String>();
			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
			if (dg.getRows() != null) {
				for (int i = 0; i < dg.getRows().size(); i++) {
					JuanBaoCJBean workShopbean = (JuanBaoCJBean) dg.getRows()
							.get(i);

					maps.put((4 + i) + ",1,1,1",
							workShopbean.getEquipmentNameCode());// 第4行;第2列开始 ~
																	// 第2列结束 一共
																	// 跨1行
					maps.put((4 + i) + ",2,2,1",
							workShopbean.getEquipmentName());
					maps.put((4 + i) + ",3,3,1",
							workShopbean.getEquipmentType());
					maps.put((4 + i) + ",4,4,1", workShopbean.getMdTeamName());
					maps.put((4 + i) + ",5,5,1", workShopbean.getMdMatName());
					maps.put((4 + i) + ",6,6,1", workShopbean.getQty()
							.toString());
					maps.put((4 + i) + ",7,7,1", workShopbean.getMdUnitName());
					maps.put((4 + i) + ",8,8,1", workShopbean.getBadQty()
							.toString());

					maps.put((4 + i) + ",9,9,1", workShopbean.getQty1()
							.toString());
					maps.put((4 + i) + ",10,10,1",
							workShopbean.getMdUnitName1());
					maps.put((4 + i) + ",11,11,1", workShopbean.getDanhao1()
							.toString());

					maps.put((4 + i) + ",12,12,1", workShopbean.getQty2()
							.toString());
					maps.put((4 + i) + ",13,13,1",
							workShopbean.getMdUnitName2());
					maps.put((4 + i) + ",14,14,1", workShopbean.getDanhao2()
							.toString());

					maps.put((4 + i) + ",15,15,1", workShopbean.getQty3()
							.toString());
					maps.put((4 + i) + ",16,16,1",
							workShopbean.getMdUnitName3());
					maps.put((4 + i) + ",17,17,1", workShopbean.getDanhao3()
							.toString());

					maps.put((4 + i) + ",18,18,1", workShopbean.getDate());
					maps.put((4 + i) + ",19,19,1", workShopbean.getRunTime()
							.toString());
					maps.put((4 + i) + ",20,20,1", workShopbean.getStopTime()
							.toString());

					maps.put((4 + i) + ",21,21,1",
							workShopbean.getTimeUnitName());
					maps.put((4 + i) + ",22,22,1", workShopbean.getStopTimes()
							.toString());
					maps.put((4 + i) + ",23,23,1",
							workShopbean.getLastRecvTime());

					maps.put((4 + i) + ",24,24,1",
							workShopbean.getLastUpdateTime());
					maps.put((4 + i) + ",25,25,1",
							workShopbean.getIsFeedback01());
					maps.put((4 + i) + ",26,26,1",
							workShopbean.getFeedbackTime());

					maps.put((4 + i) + ",27,27,1",
							workShopbean.getFeedbackUser());

					tdVals.add(maps);
				}
			}
			// td 当前开始行,当前结束行,一共多少列
			int[] tdTables = { 4, (4 + tdVals.size() - 1), 27 };

			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			/*
			 * fos = new FileOutputStream("d:\\卷烟机组"+new
			 * Date().getTime()+".xls"); wb.write(fos); fos.flush();
			 * fos.close();
			 */

			/*
			 * json.setSuccess(true); json.setMsg("导出Execl成功在D盘根目录下");
			 */
		} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}

	/**
	 * 导出卷烟机组（修改后的代码） shisihai 2015-10-14
	 */
	public HSSFWorkbook ExportExcelJBJZ2(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 6, 21 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,21,1,卷烟机组数据统计");
			th.add("2,1,2,2,生产日期：");
			th.add("2,3,6,2," + baoCJBean.getStim() + "   到     "
					+ baoCJBean.getEtim());
			th.add("3,1,1,5,设备编号");
			th.add("3,2,2,5,设备名称");
			th.add("3,3,3,5,设备型号");
			th.add("3,4,4,5,班组");
			th.add("3,5,5,5,牌号");
			th.add("3,6,6,5,产量(箱)");
			th.add("3,7,7,5,剔除(箱)");

			// 物料消耗
			th.add("3,8,13,3,物料消耗");

			th.add("4,8,9,4,滤棒");
			th.add("5,8,8,5,消耗(支)");
			th.add("5,9,9,5,单耗(支/箱)");

			th.add("4,10,11,4,盘纸");
			th.add("5,10,10,5,消耗(米)");
			th.add("5,11,11,5,单耗(米/箱)");

			th.add("4,12,13,4,水松纸");
			th.add("5,12,12,5,消耗(KG)");
			th.add("5,13,13,5,单耗(KG/箱)");

			// 生产日期

			th.add("3,14,14,5,生产日期");

			// 运行统计
			th.add("3,15,21,3,运行统计");
			th.add("4,15,15,5,运行时间(分钟)");
			th.add("4,16,16,5,停机时间(分钟)");
			th.add("4,17,17,5,停机次数");
			th.add("4,18,18,5,最后编辑时间");
			th.add("4,19,19,5,是否反馈");
			th.add("4,20,20,5,反馈时间");
			th.add("4,21,21,5,反馈人");

			DataGrid dg = this.getJueYanList(baoCJBean, type);
//
//			Map<String, String> maps = new HashMap<String, String>();
//			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
//			if (dg.getRows() != null) {
//				for (int i = 0; i < dg.getRows().size(); i++) {
//					JuanBaoCJBean workShopbean = (JuanBaoCJBean) dg.getRows()
//							.get(i);
//
//					maps.put((6 + i) + ",1,1,1",
//							workShopbean.getEquipmentNameCode());// 第4行;第2列开始 ~
//																	// 第2列结束 一共
//																	// 跨1行
//					maps.put((6 + i) + ",2,2,1",
//							workShopbean.getEquipmentName());
//					maps.put((6 + i) + ",3,3,1",
//							workShopbean.getEquipmentType());
//					maps.put((6 + i) + ",4,4,1", workShopbean.getMdTeamName());
//					maps.put((6 + i) + ",5,5,1", workShopbean.getMdMatName());
//					maps.put((6 + i) + ",6,6,1", workShopbean.getQty()
//							.toString());
//					maps.put((6 + i) + ",7,7,1", workShopbean.getBadQty()
//							.toString());
//
//					maps.put((6 + i) + ",8,8,1", workShopbean.getQty1()
//							.toString());
//					maps.put((6 + i) + ",9,9,1", workShopbean.getDanhao1()
//							.toString());
//
//					maps.put((6 + i) + ",10,10,1", workShopbean.getQty2()
//							.toString());
//					maps.put((6 + i) + ",11,11,1", workShopbean.getDanhao2()
//							.toString());
//
//					maps.put((6 + i) + ",12,12,1", workShopbean.getQty3()
//							.toString());
//					maps.put((6 + i) + ",13,13,1", workShopbean.getDanhao3()
//							.toString());
//
//					maps.put((6 + i) + ",14,14,1", workShopbean.getDate());
//					maps.put((6 + i) + ",15,15,1", workShopbean.getRunTime()
//							.toString());
//					maps.put((6 + i) + ",16,16,1", workShopbean.getStopTime()
//							.toString());
//
//					maps.put((6 + i) + ",17,17,1", workShopbean.getStopTimes()
//							.toString());
//					// maps.put((6+i)+",18,18,1",workShopbean.getLastRecvTime());
//
//					maps.put((6 + i) + ",18,18,1",
//							workShopbean.getLastUpdateTime());
//					maps.put((6 + i) + ",19,19,1",
//							workShopbean.getIsFeedback01());
//					maps.put((6 + i) + ",20,20,1",
//							workShopbean.getFeedbackTime());
//
//					maps.put((6 + i) + ",21,21,1",
//							workShopbean.getFeedbackUser());
//
//					tdVals.add(maps);
//				}
//			}
//			// td 当前开始行,当前结束行,一共多少列
//			int[] tdTables = { 6, (6 + tdVals.size() - 1), 21 };
//
//			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			String[] method=new String[]
					{"getEquipmentNameCode","getEquipmentName","getEquipmentType","getMdTeamName",
					"getMdMatName","getQty","getBadQty","getQty1",
					"getDanhao1","getQty2","getDanhao2","getQty3",
					"getDanhao3","getDate","getRunTime","getStopTime",
					"getStopTimes","getLastUpdateTime","getIsFeedback01","getFeedbackTime",
					"getFeedbackUser"
					};
			//开始行
			int startLine=5;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,JuanBaoCJBean.class,dg.getRows());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

	/**
	 * ExportExcel導出包装机组(修改后的代码)  shisihai 2015-10-14
	 */
	public HSSFWorkbook ExportExcelBZJZ2(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 5, 25 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,25,1,包装机组生产统计");
			th.add("2,1,2,2,生产日期：");
			th.add("2,3,6,2," + baoCJBean.getStim() + "   到     "
					+ baoCJBean.getEtim());
			th.add("3,1,1,5,设备编号");
			th.add("3,2,2,5,设备名称");
			th.add("3,3,3,5,设备型号");
			th.add("3,4,4,5,班组");
			th.add("3,5,5,5,牌号");
			th.add("3,6,6,5,产量(箱)");
			th.add("3,7,7,5,剔除(箱)");

			// 辅料消耗
			th.add("3,8,17,3,辅料消耗");
			// 小盒
			th.add("4,8,9,4,小盒纸");
			th.add("5,8,8,5,消耗(张)");
			th.add("5,9,9,5,单耗(张/箱)");
			// 小透明
			th.add("4,10,11,4,小透明纸");
			th.add("5,10,10,5,消耗(张)");
			th.add("5,11,11,5,单耗(张/箱)");
			// 内衬
			th.add("4,12,13,4,内衬纸");
			th.add("5,12,12,5,消耗(张)");
			th.add("5,13,13,5,单耗(张/箱)");
			// 条盒
			th.add("4,14,15,4,条盒纸");
			th.add("5,14,14,5,消耗(张)");
			th.add("5,15,15,5,单耗(张/箱)");
			// 大透明
			th.add("4,16,17,4,大透明纸");
			th.add("5,16,16,5,消耗(张)");
			th.add("5,17,17,5,单耗(张/箱)");

			// 生产日期
			th.add("3,18,18,5,生产日期");

			// 运行统计
			th.add("3,19,25,3,运行统计");
			th.add("4,19,19,5,运行时间");
			th.add("4,20,20,5,停机时间");
			th.add("4,21,21,5,停机次数");
			th.add("4,22,22,5,数据最后编辑时间");
			th.add("4,23,23,5,是否反馈");
			th.add("4,24,24,5,反馈时间");
			th.add("4,25,25,5,反馈人");

			DataGrid dg = this.getBaoZhuangList(baoCJBean, type);

//			Map<String, String> maps = new HashMap<String, String>();
//			// maps.put("2,3,6,1","生产时间："+
//			// baoCJBean.getStim()+"   到     "+baoCJBean.getEtim());
//			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
//			if (dg.getRows() != null) {
//				for (int i = 0; i < dg.getRows().size(); i++) {
//					JuanBaoCJBean workShopbean = (JuanBaoCJBean) dg.getRows()
//							.get(i);
//
//					maps.put((6 + i) + ",1,1,1",
//							workShopbean.getEquipmentNameCode());// 第4行;第2列开始 ~
//																	// 第2列结束 一共
//																	// 跨1行
//					maps.put((6 + i) + ",2,2,1",
//							workShopbean.getEquipmentName());
//					maps.put((6 + i) + ",3,3,1",
//							workShopbean.getEquipmentType());
//					maps.put((6 + i) + ",4,4,1", workShopbean.getMdTeamName());
//					maps.put((6 + i) + ",5,5,1", workShopbean.getMdMatName());
//					maps.put((6 + i) + ",6,6,1", workShopbean.getQty()
//							.toString());
//					maps.put((6 + i) + ",7,7,1", workShopbean.getBadQty()
//							.toString());
//
//					maps.put((6 + i) + ",8,8,1", workShopbean.getQty1()
//							.toString());
//					maps.put((6 + i) + ",9,9,1", workShopbean.getDanhao1()
//							.toString());
//
//					maps.put((6 + i) + ",10,10,1", workShopbean.getQty2()
//							.toString());
//					maps.put((6 + i) + ",11,11,1", workShopbean.getDanhao2()
//							.toString());
//
//					maps.put((6 + i) + ",12,12,1", workShopbean.getQty3()
//							.toString());
//					maps.put((6 + i) + ",13,13,1", workShopbean.getDanhao3()
//							.toString());
//
//					maps.put((6 + i) + ",14,14,1", workShopbean.getQty4()
//							.toString());
//					maps.put((6 + i) + ",15,15,1", workShopbean.getDanhao4()
//							.toString());
//
//					maps.put((6 + i) + ",16,16,1", workShopbean.getQty5()
//							.toString());
//					maps.put((6 + i) + ",17,17,1", workShopbean.getDanhao5()
//							.toString());
//
//					maps.put((6 + i) + ",18,18,1", workShopbean.getDate());
//					maps.put((6 + i) + ",19,19,1", workShopbean.getRunTime()
//							.toString());
//					maps.put((6 + i) + ",20,20,1", workShopbean.getStopTime()
//							.toString());
//
//					maps.put((6 + i) + ",21,21,1", workShopbean.getStopTimes()
//							.toString());
//
//					maps.put((6 + i) + ",22,22,1",
//							workShopbean.getLastUpdateTime());
//					maps.put((6 + i) + ",23,23,1",
//							workShopbean.getIsFeedback01());
//					maps.put((6 + i) + ",24,24,1",
//							workShopbean.getFeedbackTime());
//
//					maps.put((6 + i) + ",25,25,1",
//							workShopbean.getFeedbackUser());
//
//					tdVals.add(maps);
//				}
//			}
			// td 当前开始行,当前结束行,一共多少列
//			int[] tdTables = { 6, (6 + tdVals.size() - 1), 25 };
			String[] method=new String[]
					{"getEquipmentNameCode","getEquipmentName","getEquipmentType","getMdTeamName",
					 "getMdMatName","getQty","getBadQty","getQty1","getDanhao1",
					 "getQty2","getDanhao2","getQty3","getDanhao3",
					 "getQty4","getDanhao4","getQty5","getDanhao5",
					"getDate","getRunTime","getStopTime","getStopTimes", 
					"getLastUpdateTime","getIsFeedback01","getFeedbackTime","getFeedbackUser"
					
					};
			//开始行
			int startLine=5;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,JuanBaoCJBean.class,dg.getRows());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

	/**
	 * ExportExcel導出包装机组
	 */
	@Override
	public HSSFWorkbook ExportExcelBZJZ(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 33 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,1,3,设备编号");
			th.add("1,2,2,3,设备名称");
			th.add("1,3,3,3,设备型号");
			th.add("1,4,4,3,班组");
			th.add("1,5,5,3,牌号");
			th.add("1,6,6,3,产量");
			th.add("1,7,7,3,单位");
			th.add("1,8,8,3,剔除	");

			th.add("1,9,23,1,辅料消耗");

			th.add("1,24,24,3,生产日期");

			th.add("1,25,33,1,运行统计");

			th.add("2,9,11,2,小盒");
			th.add("2,12,14,2,小透明纸");
			th.add("2,15,17,2,内衬纸	");
			th.add("2,18,20,2,条盒");
			th.add("2,21,23,2,大透明纸");

			th.add("2,25,25,3,运行时间");
			th.add("2,26,26,3,停机时间");
			th.add("2,27,27,3,单位");

			th.add("2,28,28,3,停机次数");
			th.add("2,29,29,3,数据最后接收");
			th.add("2,30,30,3,数据最后编辑");

			th.add("2,31,31,3,是否反馈");
			th.add("2,32,32,3, 反馈时间 ");
			th.add("2,33,33,3,反馈人");

			th.add("3,9,9,3,消耗");
			th.add("3,10,10,3,单位");
			th.add("3,11,11,3,单耗");
			th.add("3,12,12,3,消耗");
			th.add("3,13,13,3,单位");
			th.add("3,14,14,3,单耗");
			th.add("3,15,15,3,消耗");
			th.add("3,16,16,3,单位");
			th.add("3,17,17,3,单耗");
			th.add("3,18,18,3,消耗");
			th.add("3,19,19,3,单位");
			th.add("3,20,20,3,单耗");
			th.add("3,21,21,3,消耗");
			th.add("3,22,22,3,单位");
			th.add("3,23,23,3,单耗");

			DataGrid dg = this.getBaoZhuangList(baoCJBean, type);

			Map<String, String> maps = new HashMap<String, String>();
			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
			if (dg.getRows() != null) {
				for (int i = 0; i < dg.getRows().size(); i++) {
					JuanBaoCJBean workShopbean = (JuanBaoCJBean) dg.getRows()
							.get(i);

					maps.put((4 + i) + ",1,1,1",
							workShopbean.getEquipmentNameCode());// 第4行;第2列开始 ~
																	// 第2列结束 一共
																	// 跨1行
					maps.put((4 + i) + ",2,2,1",
							workShopbean.getEquipmentName());
					maps.put((4 + i) + ",3,3,1",
							workShopbean.getEquipmentType());
					maps.put((4 + i) + ",4,4,1", workShopbean.getMdTeamName());
					maps.put((4 + i) + ",5,5,1", workShopbean.getMdMatName());
					maps.put((4 + i) + ",6,6,1", workShopbean.getQty()
							.toString());
					maps.put((4 + i) + ",7,7,1", workShopbean.getMdUnitName());
					maps.put((4 + i) + ",8,8,1", workShopbean.getBadQty()
							.toString());

					maps.put((4 + i) + ",9,9,1", workShopbean.getQty1()
							.toString());
					maps.put((4 + i) + ",10,10,1",
							workShopbean.getMdUnitName1());
					maps.put((4 + i) + ",11,11,1", workShopbean.getDanhao1()
							.toString());

					maps.put((4 + i) + ",12,12,1", workShopbean.getQty2()
							.toString());
					maps.put((4 + i) + ",13,13,1",
							workShopbean.getMdUnitName2());
					maps.put((4 + i) + ",14,14,1", workShopbean.getDanhao2()
							.toString());

					maps.put((4 + i) + ",15,15,1", workShopbean.getQty3()
							.toString());
					maps.put((4 + i) + ",16,16,1",
							workShopbean.getMdUnitName3());
					maps.put((4 + i) + ",17,17,1", workShopbean.getDanhao3()
							.toString());

					maps.put((4 + i) + ",18,18,1", workShopbean.getQty4()
							.toString());
					maps.put((4 + i) + ",19,19,1",
							workShopbean.getMdUnitName4());
					maps.put((4 + i) + ",20,20,1", workShopbean.getDanhao4()
							.toString());

					maps.put((4 + i) + ",21,21,1", workShopbean.getQty5()
							.toString());
					maps.put((4 + i) + ",22,22,1",
							workShopbean.getMdUnitName5());
					maps.put((4 + i) + ",23,23,1", workShopbean.getDanhao5()
							.toString());

					maps.put((4 + i) + ",24,24,1", workShopbean.getDate());
					maps.put((4 + i) + ",25,25,1", workShopbean.getRunTime()
							.toString());
					maps.put((4 + i) + ",26,26,1", workShopbean.getStopTime()
							.toString());

					maps.put((4 + i) + ",27,27,1",
							workShopbean.getTimeUnitName());
					maps.put((4 + i) + ",28,28,1", workShopbean.getStopTimes()
							.toString());
					maps.put((4 + i) + ",29,29,1",
							workShopbean.getLastRecvTime());

					maps.put((4 + i) + ",30,30,1",
							workShopbean.getLastUpdateTime());
					maps.put((4 + i) + ",31,31,1",
							workShopbean.getIsFeedback01());
					maps.put((4 + i) + ",32,32,1",
							workShopbean.getFeedbackTime());

					maps.put((4 + i) + ",33,33,1",
							workShopbean.getFeedbackUser());

					tdVals.add(maps);
				}
			}
			// td 当前开始行,当前结束行,一共多少列
			int[] tdTables = { 4, (4 + tdVals.size() - 1), 33 };

			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
			/*
			 * fos = new FileOutputStream("d:\\卷包机组"+new
			 * Date().getTime()+".xls"); wb.write(fos); fos.flush();
			 * fos.close(); json.setSuccess(true);
			 * json.setMsg("导出Execl成功在D盘根目录下");
			 */
		} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}

	/***
	 * excel导出卷烟车间有效作业率
	 */
	@Override
	public void ExportExcelJBEffic(WorkShopShiftBean baoCJBean,String newName)
			throws Exception {
		List<Object[]> dg = this.getJuanYanListEffic(baoCJBean);
		new ExcelWriter().ExcelWriter(dg,newName);
		
		/*int jyNum=0;
		int bzNum=0;
		HSSFWorkbook wb = null;
		List<Double> d=new ArrayList<Double>();
		ExportExcel ee = new ExportExcel();
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 2, 14 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			// 测试添加时间
			th.add("1,1,1,1,设备名称");
			th.add("1,2,2,1,班次");
			th.add("1,3,3,1,实际产量");
			th.add("1,4,4,1,车间实际产量");

			th.add("1,5,5,1,有效作业率");
			th.add("1,6,6,1,实际有效作业率");

			th.add("1,7,7,1, ");
			th.add("1,8,8,1, ");
			
			th.add("1,9,9,1,设备名称");
			th.add("1,10,10,1,班次");
			th.add("1,11,11,1,实际产量");
			th.add("1,12,12,1,车间实际产量");
			th.add("1,13,13,1,有效作业率");
			th.add("1,14,14,1,实际有效作业率");

			List<Object[]> dg = this.getJuanYanListEffic(baoCJBean);

			Map<String, String> maps = new HashMap<String, String>();
			List<Map<String, String>> tdVals = new ArrayList<Map<String, String>>();
			if(dg!=null&&dg.size()>0){
				for (int i = 0; i <dg.size(); i++) {
					Object[] o=dg.get(i);
					if(o==null||dg.get(i)[0].toString().trim().equals("")){
						continue;
					}
					int code=Integer.parseInt(o[1].toString());
					if(code>=0 && code <=30){
						jyNum++;
					}else{
						bzNum++;
					}
				}
			}
			Object[] jyo=null;
			Object[] bzo=null;
			if (dg!= null&&dg.size()>0) {
				d=hejiEffic(dg, jyNum, bzNum);
				boolean flag=false;
				if(jyNum>bzNum){
					flag=true;
				}
				for (int i = 0; i < dg.size(); i++) {
						int bzn=jyNum+i;
						if(dg.get(i)==null||(bzn>dg.size())){
							continue;
						}
						//卷烟机
					   if(i<jyNum&&jyNum!=0){
						   jyo=dg.get(i);
						   maps.put((2 + i) + ",1,1,1",jyo[0].toString());
						   maps.put((2 + i) + ",2,2,1", jyo[2].toString());
						   maps.put((2 + i) + ",3,3,1", jyo[3].toString());
						   maps.put((2 + i) + ",4,4,1", "");//车间实际产量
						   maps.put((2 + i) + ",5,5,1", this.getEfic(jyo[3].toString(), jyo[4].toString()));//有效作业率（计算）
						   maps.put((2 + i) + ",6,6,1", "");//实际有效作业率
						   maps.put((2 + i) + ",7,7,1", "");//空格
						   maps.put((2 + i) + ",8,8,1", "");//空格
					  // }else{
						   //maps.put((2 + i) + ",1,1,1","");
						   maps.put((2 + i) + ",2,2,1", "");
						   maps.put((2 + i) + ",3,3,1", "");
						   maps.put((2 + i) + ",4,4,1", "");//车间实际产量
						   maps.put((2 + i) + ",5,5,1", "");//有效作业率（计算）
						   maps.put((2 + i) + ",6,6,1", "");//实际有效作业率
						   maps.put((2 + i) + ",7,7,1", "");//空格
						   maps.put((2 + i) + ",8,8,1", "");//空格
					  // }
					  //包装机
					    if(bzn<dg.size()&&bzNum!=0){
						   if(flag&&bzn==dg.size()){
								maps.put((2 + i) + ",9,9,1","" );
								maps.put((2 +i) + ",10,10,1","");
								maps.put((2 + i) + ",11,11,1","");
								maps.put((2 + i) + ",12,12,1", "");//车间实际产量
								maps.put((2 +i) + ",13,13,1", "");//有效作业率（计算）
								maps.put((2 + i) + ",14,14,1", "");//实际有效作业率 
						   }else{
								bzo=dg.get(bzn);
								maps.put((2 + i) + ",9,9,1",bzo[0].toString() );
								maps.put((2 +i) + ",10,10,1", bzo[2].toString());
								maps.put((2 + i) + ",11,11,1",bzo[3].toString());
								maps.put((2 + i) + ",12,12,1", "");//车间实际产量
								maps.put((2 +i) + ",13,13,1", this.getEfic(bzo[3].toString(), bzo[4].toString()));//有效作业率（计算）
								maps.put((2 + i) + ",14,14,1", "");//实际有效作业率
						   }
					   }else{
						   	maps.put((2 + i) + ",9,9,1","" );
							maps.put((2 +i) + ",10,10,1","");
							maps.put((2 + i) + ",11,11,1","");
							maps.put((2 + i) + ",12,12,1", "");//车间实际产量
							maps.put((2 +i) + ",13,13,1", "");//有效作业率（计算）
							maps.put((2 + i) + ",14,14,1", "");//实际有效作业率 
					   }
					   
					tdVals.add(maps);
				}
				if(dg!= null&&dg.size()>0){
					int hjR=0;
					if(jyNum>=bzNum){
						hjR=jyNum+2;
					}else{
						hjR=bzNum+2;
					}
					maps.put(hjR + ",1,2,1","合计：" );
					maps.put(hjR + ",3,3,1", d.get(0).toString());
					maps.put(hjR + ",4,4,1","");
					maps.put(hjR + ",5,5,1", d.get(1).toString());//车间实际产量
					maps.put(hjR + ",6,6,1", "");
					maps.put(hjR + ",7,7,1", "");
					maps.put(hjR + ",8,8,1", "");
					maps.put(hjR + ",9,9,1","");
					maps.put(hjR + ",10,10,1", "");
					maps.put(hjR + ",11,11,1",d.get(2).toString());
					maps.put(hjR+ ",12,12,1", "");
					maps.put(hjR + ",13,13,1",d.get(3).toString() );
					maps.put(hjR + ",14,14,1", "");
					tdVals.add(maps);
				}
			}
			// td 当前开始行,当前结束行,一共多少列
			int[] tdTables = { 2, (2 + tdVals.size() - 1), 14 };
//			wb=ee.exportExcel2(thTables, th, tdTables, ls);
			wb = ee.exportExcel(thTables, th, tdTables, tdVals);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;*/
	}
	private List<Object[]> getJuanYanListEffic(WorkShopShiftBean shopShiftBean){
		StringBuffer temp = new StringBuffer();
		temp.append("SELECT t.name as eqmName,t.code,t.shiftName as shift,t.qty as qty,(t.gd_all_time-t.rb_all_time-t.tctm_all_time-"+SysEqpTypeBase.jcTime+")/(60+0.0)*t.yie_id as effTime,t.datef");
		temp.append(" FROM(SELECT DISTINCT(gd.shift),gd.shiftName,gd.code,gd.eqp,gd.equipment_name AS name,gd.datef,isnull(gd.qty,0) as qty,gd.yie_id,isnull(gd.gd_all_time,0) as gd_all_time,isnull(tc.rb_all_time,0) as rb_all_time,isnull(gd.tc_all_time,0) AS tctm_all_time");
		temp.append(" FROM(SELECT a.shift,c.EQUIPMENT_CODE as code,d.NAME as shiftName,a.team,a.eqp,a.mat,a. DATE AS datef,c.equipment_name,b.qty,b.bad_qty,b.stim,b.etim,b.run_time,b.stop_times,c.yie_id,datediff(MINUTE, b.stim, b.etim)AS gd_all_time,");
		temp.append(" (SELECT SUM (stop_time) FROM EQM_CULL_RECORD WHERE CONVERT (VARCHAR(100), st_date, 23) = CONVERT (VARCHAR(100), a. DATE, 23) AND eqp_id = a.eqp AND shift_id = a.shift GROUP BY CONVERT (VARCHAR(100), st_date, 23),eqp_id,shift_id) AS tc_all_time");
		temp.append(" FROM SCH_WORKORDER a LEFT JOIN SCH_STAT_OUTPUT b on a.id = b.oid  LEFT JOIN MD_EQUIPMENT c  ON c.id = a.eqp LEFT JOIN MD_SHIFT d on a.shift = d.ID WHERE  a.del = 0 AND b.del = 0 AND b.etim IS NOT NULL AND sts = 4 ) gd");
		temp.append(" LEFT JOIN (SELECT shift,date_p AS datef,eqp_type,datediff(MINUTE, stim, etim) AS rb_all_time FROM EQM_PAULDAY WHERE del = 0 AND status = 2) tc ON tc.shift = gd.shift");
		temp.append(" AND CONVERT (VARCHAR(32), tc.datef) = CONVERT (VARCHAR(32), gd.datef)) t ");
		temp.append(" where 1=1 ");
		
		if (StringUtil.notNull(shopShiftBean.getStim())) {
			temp.append(" and datef >='" + shopShiftBean.getStim() + "' ");
		}

		if (StringUtil.notNull(shopShiftBean.getEtim())) {
			temp.append( " and datef <='" + shopShiftBean.getEtim() + "'");
		}
		 temp.append(" ORDER BY t.datef,t.name");
		 List<Object[]> o=(List<Object[]>) JueBaoServiceImpl.queryBySql(temp.toString());
		return o;
	}
	
	private String getEfic(String qty,String r_qty){
		if(Float.parseFloat(r_qty)==0){
			return  "0";
		}else{
			BigDecimal a1=new BigDecimal(Double.parseDouble(qty));
			BigDecimal b1=new BigDecimal(Double.parseDouble(r_qty));
			return ""+MathUtil.roundHalfUp((a1.divide(b1,4, BigDecimal.ROUND_HALF_EVEN).doubleValue()*100), 2);
		}
	}
	
	/**
	 * 获取合计
	 * @param dg
	 * @return
	 */
	private List<Double> hejiEffic(List<Object[]> dg,int jyNum,int bzNum) {
		List<Double> d=new ArrayList<Double>();
		double chanliang = 0.0, effic = 0.0,chanliang2 = 0.0, effic2 = 0.0;
		//卷烟机合计
		for (int i = 0; i <jyNum; i++) {
			Object[] o=dg.get(i);
			chanliang += Double.parseDouble(o[3].toString());
			effic+=Double.parseDouble(this.getEfic(o[3].toString(), o[4].toString()));
		}
		//包装机合计
		for (int j = jyNum; j<dg.size(); j++) {
			Object[] o=dg.get(j);
			chanliang2 += Double.parseDouble(o[3].toString());
			effic2+=Double.parseDouble(this.getEfic(o[3].toString(), o[4].toString()));
		}
		
		
		if(dg!=null&&dg.size()>0){
			d.add(MathUtil.roundHalfUp(chanliang,2));
			if(jyNum==0){
				d.add(0.0);
			}else{
				d.add(MathUtil.roundHalfUp(effic/jyNum, 2));
			}
			d.add(MathUtil.roundHalfUp(chanliang2, 2));
			if(bzNum==0){
				d.add(0.0);
			}else{
				d.add(MathUtil.roundHalfUp(effic2/bzNum, 2));
			}
		}
		return d;
	}
	
	/***
	 * 张璐-2015.10.14
	 * excel導出成型班組
	 */
	@Override
	public HSSFWorkbook ExportExcelCXBZ(WorkShopShiftBean shopShiftBean)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 13 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨3行

			th.add("1,1,4,1," + shopShiftBean.getStim() + "   到      "
					+ shopShiftBean.getEtim());
			th.add("2,1,1,3,班组");
			th.add("2,2,2,3,实际产量");
			th.add("2,3,3,3,产量单位");
			th.add("2,4,4,3,剔除");// 第0行第3列 开始;跨2行 到第3列结束
			th.add("1,5,13,1,成型机");

			th.add("2,5,7,2,盘纸");
			th.add("2,8,10,2,甘油");
			th.add("2,11,13,2,丝束");

			th.add("3,5,5,3,消耗");
			th.add("3,6,6,3,单位");
			th.add("3,7,7,3,单耗");
			th.add("3,8,8,3,消耗");
			th.add("3,9,9,3,单位");
			th.add("3,10,10,3,单耗");
			th.add("3,11,11,3,消耗");
			th.add("3,12,12,3,单位");
			th.add("3,13,13,3,单耗");
			//获取List集合
			DataGrid dg = this.getChengXingBanZuList(shopShiftBean);
			//方法集合，同表头一致
			String[] method=new String[]{"getMdTeamName","getQty","getMdUnitName","getBadQty","getQty1","getMdUnitName1","getDanhao1","getQty2","getMdUnitName2","getDanhao2","getQty3","getMdUnitName3","getDanhao3"};
			//开始行
			int startLine=3;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,WorkShopShiftBean.class,dg.getRows());
		} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}




/***
	 * 张璐：2015-10-14
	 * excel導出成型产品
	 */
	@Override
	public HSSFWorkbook ExportExcelCXPP(WorkShopShiftBean shopShiftBean)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 13 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,3,1," + shopShiftBean.getStim() + "   到      "
					+ shopShiftBean.getEtim());
			th.add("2,1,1,3,产品");
			th.add("1,4,13,1,成型机组");
			th.add("2,2,2,3,产量");
			th.add("2,3,3,3,单位");
			th.add("2,4,4,3,剔除");// 第0行第3列 开始;跨2行 到第3列结束

			th.add("2,5,7,2,盘纸");
			th.add("2,8,10,2,甘油");
			th.add("2,11,13,2,丝束");

			th.add("3,5,5,3,消耗");
			th.add("3,6,6,3,单位");
			th.add("3,7,7,3,单耗");
			th.add("3,8,8,3,消耗");
			th.add("3,9,9,3,单位");
			th.add("3,10,10,3,单耗");
			th.add("3,11,11,3,消耗");
			th.add("3,12,12,3,单位");
			th.add("3,13,13,3,单耗");
			//获取List集合
			DataGrid dg = this.getChengXingChanPinList(shopShiftBean);
			//方法集合，同表头一致
			String[] method=new String[]{"getMdMatName","getQty","getMdUnitName","getBadQty","getQty1","getMdUnitName1","getDanhao1","getQty2","getMdUnitName2","getDanhao2","getQty3","getMdUnitName3","getDanhao3"};
			//开始行
			int startLine=3;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,WorkShopShiftBean.class,dg.getRows());
		} catch (Exception e) {
			/*
			 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
			 */
			e.printStackTrace();
		}

		return wb;
	}

/***
	 * 张璐：2015-10-14
	 * excel導出成型车间
	 */
	@Override
	public HSSFWorkbook ExportExcelCX(WorkShopShiftBean shopShiftBean)throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 15 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,3,1," + shopShiftBean.getStim() + "   到      "
					+ shopShiftBean.getEtim()); 
			th.add("2,1,1,3,班次");
			th.add("2,2,2,3,班组");
			th.add("2,3,3,3,产品");
			th.add("1,4,15,1,成型机组");// 第0行第3列 开始;跨2行 到第3列结束
			th.add("2,4,4,3,实际产量");
			th.add("2,5,5,3,产量单位");
			th.add("2,6,6,3,剔除");

			th.add("2,7,9,2,盘纸");
			th.add("2,10,12,2,甘油");
			th.add("2,13,15,2,丝束");

			th.add("3,7,7,3,消耗");
			th.add("3,8,8,3,单位");
			th.add("3,9,9,3,单耗");
			th.add("3,10,10,3,消耗");
			th.add("3,11,11,3,单位");
			th.add("3,12,12,3,单耗");
			th.add("3,13,13,3,消耗");
			th.add("3,14,14,3,单位");
			th.add("3,15,15,3,单耗");
			//获取List集合
			DataGrid dg = this.getChengXingList(shopShiftBean);
			//方法集合，同表头一致
			String[] method=new String[]{"getMdShiftName","getMdTeamName","getMdMatName","getQty","getMdUnitName","getBadQtyBaoZhuang","getQty1","getMdUnitName1","getDanhao1","getQty2","getMdUnitName2","getDanhao2","getQty3","getMdUnitName3","getDanhao3"};
			//开始行
			int startLine=3;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,WorkShopShiftBean.class,dg.getRows());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}


/***
	 * 张璐
	 * 2015-10-14
	 * excel導出成型机组
	 */
	@Override
	public HSSFWorkbook ExportExcelCXJZ(JuanBaoCJBean baoCJBean, int type)
			throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 3, 27 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,8,1," + baoCJBean.getStim() + "   到      "
					+ baoCJBean.getEtim()); 
			th.add("2,1,1,3,设备编号");
			th.add("2,2,2,3,设备名称");
			th.add("2,3,3,3,设备型号");
			th.add("2,4,4,3,班组");// 第0行第3列 开始;跨2行 到第3列结束
			th.add("2,5,5,3,产品");
			th.add("2,6,6,3,产量");
			th.add("2,7,7,3,单位");
			th.add("2,8,8,3,剔除");

			th.add("1,9,17,1,辅料消耗");
			th.add("1,18,18,3,生产日期");
			th.add("1,19,27,1,运行统计");

			th.add("2,9,11,2,盘纸");
			th.add("2,12,14,2,甘油");

			th.add("2,15,17,2,丝束");

			th.add("2,19,19,3,运行时间");
			th.add("2,20,20,3,停机时间 ");
			th.add("2,21,21,3,单位");
			th.add("2,22,22,3,停机次数");
			th.add("2,23,23,3,数据最后接收");
			th.add("2,24,24,3,数据最后编辑");
			th.add("2,25,25,3,是否反馈");
			th.add("2,26,26,3,反馈时间");
			th.add("2,27,27,3,反馈人");

			th.add("3,9,9,3,消耗");
			th.add("3,10,10,3,单位");
			th.add("3,11,11,3,单耗");
			th.add("3,12,12,3,消耗");
			th.add("3,13,13,3,单位");
			th.add("3,14,14,3,单耗");
			th.add("3,15,15,3,消耗");
			th.add("3,16,16,3,单位");
			th.add("3,17,17,3,单耗");
			//获取List集合
			DataGrid dg = this.getChengXingList(baoCJBean, type);
			//方法集合，同表头一致
			String[] method=new String[]{"getEquipmentNameCode","getEquipmentName","getEquipmentType","getMdTeamName","getMdMatName","getQty","getMdUnitName","getBadQty","getQty1","getMdUnitName1","getDanhao1","getQty2","getMdUnitName2","getDanhao2","getQty3","getMdUnitName3","getDanhao3","getDate","getRunTime","getStopTime","getTimeUnitName","getStopTimes","getLastRecvTime","getLastUpdateTime","getIsFeedback01","getFeedbackTime","getFeedbackUser"};
			//开始行
			int startLine=3;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,JuanBaoCJBean.class,dg.getRows());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

/**张璐-2015.10.14
	 * ExportExcel导出卷烟品牌（修改后的代码）
	 */
	@Override
	public HSSFWorkbook ExportExcelJBPP2(WorkShopShiftBean baoCJBean)throws Exception {
		Json json = new Json();
		HSSFWorkbook wb = null;
		ExportExcel ee = new ExportExcel();
		FileOutputStream fos;
		try {
			// th 当前开始行,当前结束行,一共多少列
			int[] thTables = { 1, 6, 21 };
			List<String> th = new ArrayList<String>();
			// 第1行;第1列开始 ~ 第1列结束 一共 跨到第3行
			th.add("1,1,21,1,卷包品牌生产统计");
			th.add("2,1,2,2,生产日期");
			th.add("2,2,5,2," + baoCJBean.getStim() + "   到      "
					+ baoCJBean.getEtim());

			th.add("3,1,1,5,产品");

			th.add("3,2,9,3,卷烟机组");
			th.add("4,2,2,5,产量(箱)");
			th.add("4,3,3,5,剔除(箱)");

			th.add("4,4,5,4,滤棒");
			th.add("5,4,4,5,消耗(支)");
			th.add("5,5,5,5,单耗(支/箱)");

			th.add("4,6,7,4,滤棒盘纸");
			th.add("5,6,6,5,消耗(米)");
			th.add("5,7,7,5,单耗(米/箱)");

			th.add("4,8,9,4,水松纸");
			th.add("5,8,8,5,消耗(KG)");
			th.add("5,9,9,5,单耗(KG/箱)");

			th.add("3,10,21,3,包装机组");
			th.add("4,10,10,5,产量(箱)");
			th.add("4,11,11,5,剔除(箱)");

			th.add("4,12,13,4,小盒");
			th.add("5,12,12,5,消耗(张)");
			th.add("5,13,13,5,单耗(张/箱)");

			th.add("4,14,15,4,小透明纸");
			th.add("5,14,14,5,消耗(KG)");
			th.add("5,15,15,5,单耗(KG/箱)");

			th.add("4,16,17,4,内衬纸");
			th.add("5,16,16,5,消耗(KG)");
			th.add("5,17,17,5,单耗(KG/箱)");

			th.add("4,18,19,4,条盒");
			th.add("5,18,18,5,消耗(张)");
			th.add("5,19,19,5,单耗(张/箱)");

			th.add("4,20,21,4,大透明纸");
			th.add("5,20,20,5,消耗(KG)");
			th.add("5,21,21,5,单耗(KG/箱)");
			//获取List集合
			DataGrid dg = this.getJuanBaoChanPinList(baoCJBean);
			//方法集合，同表头一致
			String[] method=new String[]{"getMdMatName","getQty","getBadQty","getQty1","getDanhao1","getQty2","getDanhao2","getQty3","getDanhao3","getQtyBaoZhuang","getBadQtyBaoZhuang","getQty4","getDanhao4","getQty5","getDanhao5","getQty6","getDanhao6","getQty7","getDanhao7","getQty8","getDanhao8"};
			//开始行
			int startLine=5;
			//WorkShopShiftBean.class List集合中对应的实体类
			wb = ee.exportExcel(thTables, th, startLine, method,WorkShopShiftBean.class,dg.getRows());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}
/**
 * PMS查询交接班辅料/产量信息                                               更新时上下班次工单id可能为空
 * shisihai
 */
@SuppressWarnings("static-access")
@Override
public DataGrid queryChangeShiftMatterInfo(String stim,String etim, String mdTeamId, String mdShiftId) {
	//inputDaoI
	List<MatterInfo> jyData=new ArrayList<MatterInfo>();//暂时存放卷烟机数据
	List<MatterInfo> bzData=new ArrayList<MatterInfo>();//暂时存放包装机数据
	List<MatterInfo> allData=new ArrayList<MatterInfo>();//卷烟机/包装机数据
	List<MatterInfo> jzData=new ArrayList<MatterInfo>();//存放整合后的机组数据
	MatterInfo matterInfo=null;
	MatterInfo m=null;
	StringBuffer sb=new StringBuffer();
	Object[] o=null;
	//查询卷烟机数据的sql
	getSearchRollerSql(stim, etim,mdTeamId, mdShiftId, sb);
	List<?> jyObj=inputDaoI.queryBySql(sb.toString());
	//封装卷烟机数据
	if(null!=jyObj&&jyObj.size()>0){
		//填充bean数据并添加到list中
		this.packingRollerBean(jyData, jyObj);
	}
	sb.setLength(0);
	//查询包装机数据语句
	getPacksearchSql(stim, etim,mdTeamId, mdShiftId, sb);
	List<?> bzObj=inputDaoI.queryBySql(sb.toString());
	if(null!=bzObj&&bzObj.size()>0){
		//封装数据并添加到list中
		this.packingPackerBean(bzData, o, bzObj);
	}
	
	if(jyData.size()>0){
		allData.addAll(jyData);//添加卷烟机数据
	}
	if(bzData.size()>0){
		allData.addAll(bzData);//添加包装机数据
	}
	
	//用以保存匹配成功的数据的index，之后需要从allData中移除
	List<Integer> indexs=new ArrayList<Integer>();
	//将卷烟机和对应的包装机组合成一个对象
	for (int i = 0; i < allData.size(); i++) {
		//先以卷烟机匹配包装机，匹配成功之后添加到jzData中
		matterInfo=allData.get(i);
		for (int j=0;j<allData.size();j++) {
			m=allData.get(j);
			if(this.isCoupleEqp(m, matterInfo)){
				//匹配成功
				//添加匹配成功index
				indexs.add(i);
				indexs.add(j);
				MatterInfo m2=new MatterInfo();
				//重新设置开机人员
				sb.setLength(0);
				sb.append(m.getHandover_user_name()+" "+matterInfo.getHandover_user_name());
				String name=sb.toString();
				//设置吹车的残烟量
				Double cc_qty=this.calcuCC(m.getQt_cc(), matterInfo.getQt_cc());
				//copy整合数据
				try {
					beanConvertor.copyProperties(m, m2);//卷烟机数据copy
					beanConvertor.copyProperties(matterInfo, m2);//包装机数据copy
					m2.setHandover_user_name(name);
					m2.setQt_cc(cc_qty);
					if(null==m2.getPa_gg()||m2.getPa_gg()==0){
						m2.setPa_gg(SysEqpTypeBase.pz_gg);
					}
					jzData.add(m2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				continue;
			}
		}
	}
	//去除已经匹配的数据
	List<MatterInfo> rData=new ArrayList<MatterInfo>();
	Integer index=null;
	for (int i = 0; i < indexs.size(); i++) {
		index=indexs.get(i);
		rData.add(allData.get(index));
	}
	allData.removeAll(rData);
	
	//添加没有匹配成一组的数据
	if(allData.size()>0){
		jzData.addAll(allData);
	}
	//排序 按日期和工段排序 时间倒序
	Comparator<MatterInfo> p=new Comparator<MatterInfo>() {
		@Override
		public int compare(MatterInfo o1, MatterInfo o2) {
			int f=1;
			try {
				
				Long n1=DateUtil.strToDate(o1.getOrderDate(), "yyyy-MM-dd").getTime();
				Long n2=DateUtil.strToDate(o2.getOrderDate(), "yyyy-MM-dd").getTime();
				if(n1-n2>0){
					f=-1;
				}else if(n1-n2==0){
					f=0;
				}
				if(f==0){
					f=-o1.getTeam_Name().compareTo(o2.getTeam_Name());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return f;
		}
	};
	int size=jzData.size();
	MatterInfo[] mbs=new MatterInfo[size];
	for (int i = 0; i < mbs.length; i++) {
		mbs[i]=jzData.get(i);
	}
	Arrays.sort(mbs, p);
	jzData.clear();
	for (MatterInfo matterInfo2 : mbs) {
		jzData.add(matterInfo2);
	}
	return new DataGrid(jzData, 500L);
}
/**
 * 获取查询卷烟机的数据的sql 残烟量放在虚退表里  吹车残烟放在卷烟机实领表
 * shisihai
 * @param stim
 * @param etim
 * @param mdTeamId
 * @param mdShiftId
 * @param sb
 */
private void getSearchRollerSql(String stim, String etim,String mdTeamId, String mdShiftId, StringBuffer sb) {
	sb.append("SELECT met.EQUIPMENT_CODE,mt.name,CONVERT(VARCHAR(23),swo.[DATE],23)as sdate,yl.yl_user as handUser,met.EQUIPMENT_NAME,yl.yl_mat_name,sso.QTY, ");
	sb.append(" xl.xl_id,xl.xl_lb,xl.xl_zj_pz,xl.xl_zj_ssz,");
	sb.append(" yl.yl_id,yl.yl_lb,yl.yl_pz,yl.yl_ssz,");
	sb.append(" xt.xt_id,xt.xt_lb,xt.xt_pz,xt.xt_ssz, ");
	sb.append(" swo.TEAM,swo.SHIFT, ");
	sb.append(" yl.sl_jy, ");//卷烟机残烟
	sb.append(" yl.yl_cc, ");//吹车残烟
	sb.append(" yl.pz_gg ");//盘纸规格
	sb.append(" from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
	sb.append(" LEFT JOIN MD_TEAM mt ON swo.TEAM=mt.ID ");
	sb.append(" LEFT JOIN MD_EQUIPMENT met ON swo.EQP=met.ID ");
	sb.append(" LEFT JOIN (SELECT id as xl_id,oid as xl_oid,mec_id as xl_mec_id,zj_pz as xl_zj_pz,zj_ssz as xl_zj_ssz,zj_lb as xl_lb from SCH_STAT_SHIFT_FLINFO  where fl_type=2  ) xl ON xl.xl_oid=swo.ID ");//虚领
	sb.append(" LEFT JOIN (SELECT id as yl_id,oid as yl_oid,mec_id as yl_mec_id,mat_name as yl_mat_name,zj_pz as yl_pz,zj_ssz as yl_ssz,zj_lb as yl_lb,receive_user_name as yl_user,qt_cc as yl_cc,qt_jy as sl_jy,pz_gg from SCH_STAT_SHIFT_FLINFO  where fl_type=1  ) yl ON yl.yl_oid=swo.ID ");//实际领取
	sb.append(" LEFT JOIN (SELECT id as xt_id,oid as xt_oid,mec_id as xt_mec_id,zj_pz as xt_pz,zj_ssz as xt_ssz,zj_lb as xt_lb from SCH_STAT_SHIFT_FLINFO  where fl_type=3  ) xt ON xt.xt_oid=swo.ID ");//虚退
	sb.append(" WHERE  yl.yl_mec_id=1 and xl.xl_mec_id=1 AND xt.xt_mec_id=1 and swo.sts=4 ");
	if(StringUtil.notNull(stim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) >= '"+stim+"'");
	}
	if(StringUtil.notNull(etim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) <= '"+etim+"'");
	}
	if(StringUtil.notNull(mdTeamId)){
		sb.append(" AND swo.TEAM='"+mdTeamId+"'");
	}
	if(StringUtil.notNull(mdShiftId)){
		sb.append(" AND swo.SHIFT='"+mdShiftId+"'");
	}
	sb.append(" ORDER BY swo.[DATE],TEAM,EQUIPMENT_CODE ASC ");
}
/**
 *获取包装机数据sql语句
 *shisihai
 * @param stim
 * @param etim
 * @param mdTeamId
 * @param mdShiftId
 * @param sb
 */
private void getPacksearchSql(String stim,String etim, String mdTeamId, String mdShiftId, StringBuffer sb) {
	sb.append(" SELECT met.EQUIPMENT_CODE,CONVERT (VARCHAR(23), swo.[DATE], 23) AS sdate,yl.receive_user_name AS handUser,sso.QTY, ");
	sb.append(" xl.id AS lx_id,xl.zb_xhz,xl.zb_thz,xl.zb_lbz,xl.zb_xhm,xl.zb_thm,xl.zb_kz,xl.zb_fq,xl.zb_lx1,xl.zb_lx2, ");
	sb.append(" yl.id AS yl_id,yl.zb_xhz as yl_xhz,yl.zb_thz as yl_thz,yl.zb_lbz as yl_lbz,yl.zb_xhm as yl_xhm,yl.zb_thm as yl_thm,yl.zb_kz as yl_kz,yl.zb_fq as yl_fq,yl.zb_lx1 as yl_lx1,yl.zb_lx2 as yl_lx2,");
	sb.append(" xt.id AS xt_id,xt.zb_xhz as xt_xhz,xt.zb_thz as xt_thz,xt.zb_lbz as xt_lbz,xt.zb_xhm as xt_xhm,xt.zb_thm as xt_thm,xt.zb_kz as xt_kz,xt.zb_fq as xt_fq,xt.zb_lx1 as xt_lx1,xt.zb_lx2 as xt_xl2, ");
	sb.append(" swo.TEAM,swo.SHIFT, ");
	sb.append(" yl.qt_bz,");//包装机残烟
	sb.append(" yl.qt_cc, ");//吹车残烟
	sb.append(" mt.name,yl.yl_mat_name,met.EQUIPMENT_NAME ");
	sb.append(" from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
	sb.append(" LEFT JOIN MD_TEAM mt ON swo.TEAM=mt.ID ");
	sb.append(" LEFT JOIN MD_EQUIPMENT met ON swo.EQP=met.ID ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,zb_xhz,zb_thz,zb_lbz,zb_xhm,zb_thm,zb_kz,zb_fq,zb_lx1,zb_lx2 from SCH_STAT_SHIFT_FLINFO  where fl_type=2  ) xl ON xl.oid=swo.ID ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,zb_xhz,zb_thz,zb_lbz,zb_xhm,zb_thm,zb_kz,zb_fq,zb_lx1,zb_lx2,receive_user_name,qt_cc,mat_name as yl_mat_name,qt_bz,pz_gg  from SCH_STAT_SHIFT_FLINFO  where fl_type=1  ) yl ON yl.oid=swo.ID ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,zb_xhz,zb_thz,zb_lbz,zb_xhm,zb_thm,zb_kz,zb_fq,zb_lx1,zb_lx2 from SCH_STAT_SHIFT_FLINFO  where fl_type=3  ) xt ON xt.oid=swo.ID ");
	sb.append(" WHERE  yl.mec_id=2 and xl.mec_id=2 AND xt.mec_id=2 and swo.sts=4  ");
	if(StringUtil.notNull(stim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) >= '"+stim+"'");
	}
	if(StringUtil.notNull(etim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) <= '"+etim+"'");
	}
	if(StringUtil.notNull(mdTeamId)){
		sb.append(" AND swo.TEAM='"+mdTeamId+"'");
	}
	if(StringUtil.notNull(mdShiftId)){
		sb.append(" AND swo.SHIFT='"+mdShiftId+"'");
	}
	sb.append(" ORDER BY swo.[DATE],TEAM,EQUIPMENT_CODE ASC ");
}
/**
 * 封装包装机数据并添加到list中
 * @param bzData
 * @param o
 * @param bzObj
 */
private void packingPackerBean(List<MatterInfo> bzData, Object[] o, List<?> bzObj) {
	MatterInfo bean2=null;
	for (Object obj : bzObj) {
		o=(Object[]) obj;
		bean2=new MatterInfo();
		Integer eqpCode=Integer.parseInt(StringUtil.convertObjToString(o[0]));
		bean2.setEqpCode(eqpCode);//设备code
		bean2.setOrderDate(StringUtil.convertObjToString(o[1]));//工单开始时间
		bean2.setHandover_user_name(StringUtil.convertObjToString(o[2]));//交班人
		bean2.setBzQty(this.convertToDouble(o[3]));//产量
		bean2.setTeamId(StringUtil.convertObjToString(o[34]));//班组
		bean2.setShiftId(StringUtil.convertObjToString(o[35]));//班次
		bean2.setQt_bz(this.convertToDouble(o[36]));//残烟
		bean2.setQt_cc(this.convertToDouble(o[37]));//吹车残烟
		bean2.setTeam_Name(StringUtil.convertObjToString(o[38]));//班组
		bean2.setMat_name(StringUtil.convertObjToString(o[39]));//牌号
		bean2.setEqpName(this.reSetEqpName(o[40]));//设置机台名
		//虚领
		Long xl_id=Long.parseLong(StringUtil.convertObjToString(o[4]));
		bean2.setXl_bz_id(xl_id);//表id
		bean2.setXl_zb_xhz(this.convertToDouble(o[5]));//小盒纸
		bean2.setXl_zb_thz(this.convertToDouble(o[6]));//条盒纸
		bean2.setXl_zb_lbz(this.convertToDouble(o[7]));//铝箔纸
		bean2.setXl_zb_xhm(this.convertToDouble(o[8]));//小盒膜
		bean2.setXl_zb_thm(this.convertToDouble(o[9]));//条盒膜
		bean2.setXl_zb_kz(this.convertToDouble(o[10]));//卡纸
		bean2.setXl_zb_fq(this.convertToDouble(o[11]));//封签
		bean2.setXl_zb_lx1(this.convertToDouble(o[12]));//拉线1
		bean2.setXl_zb_lx2(this.convertToDouble(o[13]));//拉线2
		//实领
		Long sl_id=Long.parseLong(StringUtil.convertObjToString(o[14]));
		bean2.setSl_bz_id(sl_id);//表id
		bean2.setSl_zb_xhz(this.convertToDouble(o[15]));//小盒纸
		bean2.setSl_zb_thz(this.convertToDouble(o[16]));//条盒纸
		bean2.setSl_zb_lbz(this.convertToDouble(o[17]));//铝箔纸
		bean2.setSl_zb_xhm(this.convertToDouble(o[18]));//小盒膜
		bean2.setSl_zb_thm(this.convertToDouble(o[19]));//条盒膜
		bean2.setSl_zb_kz(this.convertToDouble(o[20]));//卡纸
		bean2.setSl_zb_fq(this.convertToDouble(o[21]));//封签
		bean2.setSl_zb_lx1(this.convertToDouble(o[22]));//拉线1
		bean2.setSl_zb_lx2(this.convertToDouble(o[23]));//拉线2
		//虚退
		Long xt_id=Long.parseLong(StringUtil.convertObjToString(o[24]));
		bean2.setXt_bz_id(xt_id);//表id
		bean2.setXt_zb_xhz(this.convertToDouble(o[25]));//小盒纸
		bean2.setXt_zb_thz(this.convertToDouble(o[26]));//条盒纸
		bean2.setXt_zb_lbz(this.convertToDouble(o[27]));//铝箔纸
		bean2.setXt_zb_xhm(this.convertToDouble(o[28]));//小盒膜
		bean2.setXt_zb_thm(this.convertToDouble(o[29]));//条盒膜
		bean2.setXt_zb_kz(this.convertToDouble(o[30]));//卡纸
		bean2.setXt_zb_fq(this.convertToDouble(o[31]));//封签
		bean2.setXt_zb_lx1(this.convertToDouble(o[32]));//拉线1
		bean2.setXt_zb_lx2(this.convertToDouble(o[33]));//拉线2
		//实用
		bean2.setSy_zb_xhz(this.calcuMatter(bean2.getXl_zb_xhz(), bean2.getXt_zb_xhz(), bean2.getSl_zb_xhz()));//小盒纸
		bean2.setSy_zb_thz(this.calcuMatter(bean2.getXl_zb_thz(), bean2.getXt_zb_thz(), bean2.getSl_zb_thz()));//条盒纸
		bean2.setSy_zb_lbz(this.calcuMatter(bean2.getXl_zb_lbz(), bean2.getXt_zb_lbz(), bean2.getSl_zb_lbz()));//铝箔纸
		bean2.setSy_zb_xhm(this.calcuMatter(bean2.getXl_zb_xhm(), bean2.getXt_zb_xhm(), bean2.getSl_zb_xhm()));//小盒膜
		bean2.setSy_zb_thm(this.calcuMatter(bean2.getXl_zb_thm(), bean2.getXt_zb_thm(), bean2.getSl_zb_thm()));//条盒膜
		bean2.setSy_zb_kz(this.calcuMatter(bean2.getXl_zb_kz(), bean2.getXt_zb_kz(), bean2.getSl_zb_kz()));//卡纸
		bean2.setSy_zb_fq(this.calcuMatter(bean2.getXl_zb_fq(), bean2.getXt_zb_fq(), bean2.getSl_zb_fq()));//封签
		bean2.setSy_zb_lx1(this.calcuMatter(bean2.getXl_zb_lx1(),bean2.getXt_zb_lx1(),bean2.getSl_zb_lx1()));//拉线1
		bean2.setSy_zb_lx2(this.calcuMatter(bean2.getXl_zb_lx2(),bean2.getXt_zb_lx2(),bean2.getSl_zb_lx2()));//拉线2
		bzData.add(bean2);
		
	}
}
/**
 * 封装卷烟机辅料交接班数据
 * @param jyData
 * @param jyObj
 */
private void packingRollerBean(List<MatterInfo> jyData, List<?> jyObj) {
	Object[] o;
	MatterInfo bean;
	for (Object obj : jyObj) {
		o=(Object[]) obj;
		bean=new MatterInfo();
		Integer eqpCode=Integer.parseInt(StringUtil.convertObjToString(o[0]));
		bean.setEqpCode(eqpCode);//机台code
		if(this.convertToDouble(o[23])==0){
			bean.setPa_gg(SysEqpTypeBase.pz_gg);
		}else{
			bean.setPa_gg(this.convertToDouble(o[23]));//设置盘纸规格  默认5000
		}
		bean.setTeam_Name(StringUtil.convertObjToString(o[1]));//班组
		bean.setOrderDate(StringUtil.convertObjToString(o[2]));//工单开始日期
		bean.setHandover_user_name(StringUtil.convertObjToString(o[3]));//交班人 要和包装机加
		bean.setEqpName(this.reSetEqpName(o[4]));//机台名  需要最后处理
		bean.setMat_name(StringUtil.convertObjToString(o[5]));//牌号
		bean.setJuQty(this.convertToDouble(o[6]));//卷烟机产量
		bean.setTeamId(StringUtil.convertObjToString(o[19]));//班组
		bean.setShiftId(StringUtil.convertObjToString(o[20]));//班次
		bean.setQt_jy(this.convertToDouble(o[21]));//残烟
		bean.setQt_cc(this.convertToDouble(o[22]));//吹车残烟
		//虚领辅料数据    
		Long xl_id=Long.parseLong(StringUtil.convertObjToString(o[7]));
		bean.setLx_jy_id(xl_id);//虚领数据  id
		bean.setXl_zj_lb(this.convertToDouble(o[8]));//滤棒
		bean.setXl_zj_pz(this.convertToDouble(o[9]));//盘纸
		bean.setXl_zj_ssz(this.convertToDouble(o[10]));//水松纸
		//实领辅料数据
		Long yl=Long.parseLong(StringUtil.convertObjToString(o[11]));
		bean.setSl_jy_id(yl);;//数据  id
		bean.setSl_zj_lb(this.convertToDouble(o[12]));//滤棒
		bean.setSl_zj_pz(this.convertToDouble(o[13]));//盘纸
		bean.setSl_zj_ssz(this.convertToDouble(o[14]));//水松纸
		
		//虚退辅料数据
		Long xt=Long.parseLong(StringUtil.convertObjToString(o[15]));
		bean.setXt_jy_id(xt);;//数据  id
		bean.setXt_zj_lb(this.convertToDouble(o[16]));//滤棒
		bean.setXt_zj_pz(this.convertToDouble(o[17]));//盘纸
		bean.setXt_zj_ssz(this.convertToDouble(o[18]));//水松纸
		//实用 辅料数据
		bean.setSy_zj_lb(this.calcuMatter(bean.getXl_zj_lb(), bean.getXt_zj_lb(), bean.getSl_zj_lb()));//滤棒
		bean.setSy_zj_pz(this.calcuMatter(bean.getXl_zj_pz(), bean.getXt_zj_pz(), bean.getSl_zj_pz()));//盘纸
		bean.setSy_zj_ssz(this.calcuMatter(bean.getXl_zj_ssz(), bean.getXt_zj_ssz(), bean.getSl_zj_ssz()));//水松纸
		jyData.add(bean);
	}
}
/**
 * 将object 转成Double
 * shisihai
 * @param o
 * @return
 */
private Double convertToDouble(Object o){
	Double result=0.0;
	String attr=StringUtil.convertObjToString(o);
	if(StringUtil.isDouble(attr)){
		result=Double.parseDouble(attr);
		result=MathUtil.roundHalfUp(result, 3);
	}
	return result;
}
/**
 * 计算实际用料
 * shisihai
 * @param xl
 * @param xt
 * @param sl
 * @return
 */
private Double calcuMatter(Double xl,Double xt,Double sl){
	return xl+sl-xt;
}
/**
 * 判断机组是否为一组（一个卷烟机和一个包装机）
 * @param m1 卷烟机
 * @param m2 包装机
 * @return
 */
private boolean isCoupleEqp(MatterInfo m1,MatterInfo m2){
	boolean flag=false;
	Integer code1=m1.getEqpCode();
	Integer code2=m2.getEqpCode();
	if(code1==code2||(code1+30)!=code2){
		return false;
	}
	String date1=m1.getOrderDate();
	String date2=m2.getOrderDate();
	String shift1=m1.getShiftId();
	String shift2=m2.getShiftId();
	String team1=m1.getTeamId();
	String team2=m2.getTeamId();
	if(date1.equals(date2)&&shift1.equals(shift2)&&team1.equals(team2)){
		flag=true;
	}
	return flag;
}
/**
 * 计算吹车的残烟量
 * @param m1
 * @param m2
 * @return
 */
private Double calcuCC(Double m1,Double m2){
	Double result=0.0;
	if(m1==null){
		m1=0.0;
	}
	if(m2==null){
		m2=0.0;
	}
	if(m1>0&&m2>0){
		result=m1;
	}else if(m1==0&&m2>0){
		result=m2;
	}else if(m1>0&&m2==0){
		result=m1;
	}
	
	return result;
}
/**
 * PMS保存交接班数据修改
 * shisihai
 */
@Override
public void saveMatterInfoChange(MatterInfo bean) {
	//更新卷烟机数据
	//卷烟机虚领
	if(bean.getLx_jy_id()!=null){
		StatShiftFLInfo rxl=statShiftFLInfoDaoI.findById(bean.getLx_jy_id());
		//更新虚领表数据，如果实领表里的uoid不是null，说明有上一班工单，需要更新上一班次的虚退
		if(null!=rxl){
			this.updateRollerXL(bean, rxl);
		}
			
	}
	
	
	//卷烟机实领
	if(bean.getSl_jy_id()!=null){
		StatShiftFLInfo rsl=statShiftFLInfoDaoI.findById(bean.getSl_jy_id());
		//更新实领数据，如果产量有更新，更新SchStatoutput数据，实领里面更新残烟数据
		if(rsl!=null){
			this.updateRollerSl(bean, rsl);
		}
	}
	
	
	//卷烟机虚退
	if(bean.getXt_jy_id()!=null){
		StatShiftFLInfo rxt=statShiftFLInfoDaoI.findById(bean.getXt_jy_id());
		//更新虚退表数据，如果实领表里的doid不是null，说明有下一班工单，需要更新下一班次的虚领
		if(null!=rxt){
			this.updateRollerXt(bean, rxt);
		}
	}
	
	
	
	
	//更新包装机数据
	
	
	//包装机虚领
	if(bean.getXl_bz_id()!=null){
		StatShiftFLInfo pxl=statShiftFLInfoDaoI.findById(bean.getXl_bz_id());
		//更新虚退表数据，如果实领表里的doid不是null，说明有下一班工单，需要更新下一班次的虚领
		if(null!=pxl){
			this.updatePackerXl(bean,pxl);
		}
	}
	
	
	//包装机实领
	if(bean.getSl_bz_id()!=null){
		StatShiftFLInfo psl=statShiftFLInfoDaoI.findById(bean.getSl_bz_id());
		//更新实领数据，如果产量有更新，更新SchStatoutput数据，实领里面更新残烟数据
		if(null!=psl){
			this.updatePacker(bean,psl);
		}
	}
	
	
	//包装机虚退
	if(bean.getXt_bz_id()!=null){
		StatShiftFLInfo pxt=statShiftFLInfoDaoI.findById(bean.getXt_bz_id());
		//更新虚退表数据，如果实领表里的doid不是null，说明有下一班工单，需要更新下一班次的虚领
		if(null!=pxt){
			this.updatePackerxt(bean,pxt);
		}
	}
}

/**
 * 更新包装机虚退数据
 * @param bean
 * @param pxt
 */
private void updatePackerxt(MatterInfo bean, StatShiftFLInfo pxt) {
	StatShiftFLInfo bean2=null;
	
	//非正常换班，没有下班oid，查找下班oid
		if(null==pxt.getDoid()){
			String doid=getRoundOrder(1,pxt.getOid(),pxt.getEqp_id());
			if(doid!=null){
				pxt.setDoid(doid);
			}
		}
	
	
	//如果存在下班次工单id，则更新下班次虚领数据
	if(StringUtil.notNull(pxt.getDoid())){
		bean2=this.getBeanByOidAndType(pxt.getDoid(), 2, 1);
	}
	//小盒纸
	if(bean.getXt_zb_xhz()!=null){
		pxt.setZb_xhz(bean.getXt_zb_xhz());
		if(bean2!=null){
			bean2.setZb_xhz(bean.getXt_zb_xhz());
		}
	}
	
	//条盒纸
	
	if(bean.getXt_zb_thz()!=null){
		pxt.setZb_thz(bean.getXt_zb_thz());
		if(bean2!=null){
			bean2.setZb_thz(bean.getXt_zb_thz());
		}
	}
	
	//铝箔纸
	if(bean.getXt_zb_lbz()!=null){
		pxt.setZb_lbz(bean.getXt_zb_lbz());
		if(bean2!=null){
			bean2.setZb_lbz(bean.getXt_zb_lbz());
		}
	}
	
	//小透明纸
	if(bean.getXt_zb_xhm()!=null){
		pxt.setZb_xhm(bean.getXt_zb_xhm());
		if(bean2!=null){
			bean2.setZb_xhm(bean.getXt_zb_xhm());
		}
	}
	//大透明
	if(bean.getXt_zb_thm()!=null){
		pxt.setZb_thm(bean.getXt_zb_thm());
		if(bean2!=null){
			bean2.setZb_thm(bean.getXt_zb_thm());
		}
	}
	
	//卡纸
	if(bean.getXt_zb_kz()!=null){
		pxt.setZb_kz(bean.getXt_zb_kz());
		if(bean2!=null){
			bean2.setZb_kz(bean.getXt_zb_kz());
		}
	}
	//封签
	if(bean.getXt_zb_fq()!=null){
		pxt.setZb_fq(bean.getXt_zb_fq());
		if(bean2!=null){
			bean2.setZb_fq(bean.getXt_zb_fq());
		}
	}
	
	//拉线1
	if(bean.getXt_zb_lx1()!=null){
		pxt.setZb_lx1(bean.getXt_zb_lx1());
		if(bean2!=null){
			bean2.setZb_lx1(bean.getXt_zb_lx1());
		}
	}
	
	//拉线2
		if(bean.getXt_zb_lx2()!=null){
			pxt.setZb_lx2(bean.getXt_zb_lx2());
			if(bean2!=null){
				bean2.setZb_lx2(bean.getXt_zb_lx2());
			}
		}
		
	if(null!=bean2){
		statShiftFLInfoDaoI.update(bean2);
	}
	statShiftFLInfoDaoI.update(pxt);
	
}
/**
 * 更新包装机实领数据
 * @param bean
 * @param psl
 */
private void updatePacker(MatterInfo bean, StatShiftFLInfo psl) {
	//残烟
	if(bean.getQt_bz()!=null){
		psl.setQt_bz(bean.getQt_bz());
	}
	//吹车
	if(bean.getQt_cc()!=null){
		psl.setQt_cc(bean.getQt_cc());
	}
	//产量
	if(bean.getBzQty()!=null){
		if(StringUtil.notNull(psl.getOid())){
			String hql="FROM SchStatOutput o where o.schWorkorder.id=?";
			SchStatOutput b=schStatOutputDao.unique(SchStatOutput.class, hql, psl.getOid());
			if(b!=null){
				b.setDACqty(b.getQty());
				b.setQty(bean.getBzQty());
				schStatOutputDao.update(b);
			}
		}
	}
	//小盒纸
	if(bean.getSl_zb_xhz()!=null){
		psl.setZb_xhz(bean.getSl_zb_xhz());
	}
	//条盒纸
	if(bean.getSl_zb_thz()!=null){
		psl.setZb_thz(bean.getSl_zb_thz());
	}
	//铝箔纸
	if(bean.getSl_zb_lbz()!=null){
		psl.setZb_lbz(bean.getSl_zb_lbz());
	}
	//小透明
	if(bean.getSl_zb_xhm()!=null){
		psl.setZb_xhm(bean.getSl_zb_xhm());
	}
	//大透明纸
	if(bean.getSl_zb_thm()!=null){
		psl.setZb_thm(bean.getSl_zb_thm());
	}
	//卡纸
	if(bean.getSl_zb_kz()!=null){
		psl.setZb_kz(bean.getSl_zb_kz());
	}
	//封签
	if(bean.getSl_zb_fq()!=null){
		psl.setZb_fq(bean.getSl_zb_fq());
	}
	//拉线1
	if(bean.getSl_zb_lx1()!=null){
		psl.setZb_lx1(bean.getSl_zb_lx1());
		
	}
	//拉线2
	if(bean.getSl_zb_lx2()!=null){
		psl.setZb_lx2(bean.getSl_zb_lx2());
	}
	statShiftFLInfoDaoI.update(psl);
}
/**
 * 更新包装机虚领数据
 * @param bean
 * @param pxl
 */
private void updatePackerXl(MatterInfo bean, StatShiftFLInfo pxl) {
	//需要修改上班次的虚退
	StatShiftFLInfo b2=null;
	
	//非正常换班，没有上班oid，查找上班oid
		if(null==pxl.getUoid()){
			String uoid=getRoundOrder(2,pxl.getOid(),pxl.getEqp_id());
			if(uoid!=null){
				pxl.setUoid(uoid);
			}
		}
	
	
	if(StringUtil.notNull(pxl.getUoid())){
		b2=this.getBeanByOidAndType(pxl.getUoid(), 3, 2);
	}
	//小盒纸
	if(bean.getXl_zb_xhz()!=null){
		pxl.setZb_xhz(bean.getXl_zb_xhz());
		if(b2!=null){
			b2.setZb_xhz(bean.getXl_zb_xhz());
		}
	}
	//条盒纸
	if(bean.getXl_zb_thz()!=null){
		pxl.setZb_thz(bean.getXl_zb_thz());
		if(b2!=null){
			b2.setZb_thz(bean.getXl_zb_thz());
		}
	}
	
	//铝箔纸
	if(bean.getXl_zb_lbz()!=null){
		pxl.setZb_lbz(bean.getXl_zb_lbz());
		if(b2!=null){
			b2.setZb_lbz(bean.getXl_zb_lbz());
		}
	}
	//小透明
	if(bean.getXl_zb_xhm()!=null){
		pxl.setZb_xhm(bean.getXl_zb_xhm());
		if(b2!=null){
			b2.setZb_xhm(bean.getXl_zb_xhm());
		}
	}
	
	//大透明
	if(bean.getXl_zb_thm()!=null){
		pxl.setZb_thm(bean.getXl_zb_thm());
		if(b2!=null){
			b2.setZb_thm(bean.getXl_zb_thm());
		}
	}
	
	//卡纸
	if(bean.getXl_zb_kz()!=null){
		pxl.setZb_kz(bean.getXl_zb_kz());	
		if(b2!=null){
			b2.setZb_kz(bean.getXl_zb_kz());
		}
	}
	
	
	//封签
	if(bean.getXl_zb_fq()!=null){
		pxl.setZb_fq(bean.getXl_zb_fq());
		if(b2!=null){
			b2.setZb_fq(bean.getXl_zb_fq());
		}
	}
	
	//拉线1
	if(bean.getXl_zb_lx1()!=null){
		pxl.setZb_lx1(bean.getXl_zb_lx1());
		if(b2!=null){
			b2.setZb_lx1(bean.getXl_zb_lx1());
		}
	}
	
	//拉线2
	if(bean.getXl_zb_lx2()!=null){
		pxl.setZb_lx2(bean.getXl_zb_lx2());
		if(b2!=null){
			b2.setZb_lx2(bean.getXl_zb_lx2());
		}
	}
}

/**
 * 更新卷烟机虚领数据
 * @param bean
 * @param rxl
 */
private void updateRollerXL(MatterInfo bean, StatShiftFLInfo rxl) {
	StatShiftFLInfo bean2=null;
	//如果有上班工单辅料可以更新，获取上班的辅料信息
	
	//非正常换班，没有上班oid，查找上班oid
	if(null==rxl.getUoid()){
		String uoid=getRoundOrder(2,rxl.getOid(),rxl.getEqp_id());
		if(uoid!=null){
			rxl.setUoid(uoid);
		}
	}
	//正常交接班有上班oid
	if(StringUtil.notNull(rxl.getUoid())){
		bean2=this.getBeanByOidAndType(rxl.getUoid(), 3, 2);
	}
	//滤棒
	if(bean.getXl_zj_lb()!=null){
		rxl.setZj_lb(bean.getXl_zj_lb());
		//上班次信息更新
		if(bean2!=null){
			bean2.setZj_lb(bean.getXl_zj_lb());
		}
	}
	//盘纸
	if(bean.getXl_zj_pz()!=null){
		rxl.setZj_pz(bean.getXl_zj_pz());
		//上班次信息更新
		if(bean2!=null){
			bean2.setZj_pz(bean.getXl_zj_pz());
		}
	}
	//水松纸
	if(bean.getXl_zj_ssz()!=null){
		rxl.setZj_ssz(bean.getXl_zj_ssz());
		//上班次信息更新
		if(bean2!=null){
			bean2.setZj_ssz(bean.getXl_zj_ssz());
		}
	}
	statShiftFLInfoDaoI.update(rxl);
	if(bean2!=null){
		statShiftFLInfoDaoI.update(bean2);
	}
}
/**
 * 根据工单id和辅料确认类型 获取StatShiftFLInfo，用于更新上下班次确认信息
 * @param oid
 * @param type   1-实领辅料   2-虚领辅料  3-虚退辅料   0-没有交接班
 * @param beanType 获取的bean类型，1下一班次   2上一班次
 * @return
 */
private StatShiftFLInfo getBeanByOidAndType(String oid,Integer type,Integer beanType){
	String hql=null;
	if(beanType==2){
		 hql="FROM StatShiftFLInfo o where o.oid=? and o.fl_type=? ";
	}else if(beanType==1){
		hql="FROM StatShiftFLInfo o where o.oid=? and o.fl_type=? ";
	}
	StatShiftFLInfo bean=(StatShiftFLInfo) statShiftFLInfoDaoI.unique(hql, oid,type);
	return bean;
}

/**
 * 更新卷烟机虚退的信息
 * @param bean
 * @param rxl
 */
private void updateRollerXt(MatterInfo bean, StatShiftFLInfo rxt){
	StatShiftFLInfo bean2=null;
	//非正常换班，没有下班oid，查找下班oid
		if(null==rxt.getDoid()){
			String doid=getRoundOrder(1,rxt.getOid(),rxt.getEqp_id());
			if(doid!=null){
				rxt.setDoid(doid);
			}
		}
	
	//如果有下班工单辅料可以更新，获取下班的辅料信息
	if(StringUtil.notNull(rxt.getDoid())){
		bean2=this.getBeanByOidAndType(rxt.getDoid(), 2, 1);
	}
	//滤棒
	if(bean.getXt_zj_lb()!=null){
		rxt.setZj_lb(bean.getXt_zj_lb());
		//下班次信息更新
		if(bean2!=null){
			bean2.setZj_lb(bean.getXt_zj_lb());
		}
	}
	//盘纸
	if(bean.getXt_zj_pz()!=null){
		rxt.setZj_pz(bean.getXt_zj_pz());
		//下班次信息更新
		if(bean2!=null){
			bean2.setZj_pz(bean.getXt_zj_pz());
		}
	}
	//水松纸
	if(bean.getXt_zj_ssz()!=null){
		rxt.setZj_ssz(bean.getXt_zj_ssz());
		//下班次信息更新
		if(bean2!=null){
			bean2.setZj_ssz(bean.getXt_zj_ssz());
		}
	}
	statShiftFLInfoDaoI.update(rxt);
	statShiftFLInfoDaoI.update(bean2);
}
/**
 * 更新卷烟机实领数据
 * @param bean
 * @param rsl
 */
private void updateRollerSl(MatterInfo bean, StatShiftFLInfo rsl) {
	//卷烟机残烟
	if(bean.getQt_jy()!=null){
		rsl.setQt_jy(bean.getQt_jy());
	}
	//吹车残烟
	if(bean.getQt_cc()!=null){
		rsl.setQt_cc(bean.getQt_cc());
	}
	//滤棒
	if(bean.getSl_zj_lb()!=null){
		rsl.setZj_lb(bean.getSl_zj_lb());
	}
	//盘纸
	if(bean.getSl_zj_pz()!=null){
		rsl.setZj_pz(bean.getSl_zj_pz());
	}
	//盘纸规格
	if(bean.getPa_gg()!=null){
		rsl.setPz_gg(bean.getPa_gg());
	}
	//水松纸
	if(bean.getSl_zj_ssz()!=null){
		rsl.setZj_ssz(bean.getSl_zj_ssz());
	}
	
	
	//更新工单产出数据
	if(bean.getJuQty()!=null){
		if(StringUtil.notNull(rsl.getOid())){
			String hql="FROM SchStatOutput o where o.schWorkorder.id=?";
			SchStatOutput bean2	= schStatOutputDao.unique(SchStatOutput.class, hql, rsl.getOid());
			if(bean2!=null){
				bean2.setDACqty(bean2.getQty());
				bean2.setQty(bean.getJuQty());
				schStatOutputDao.update(bean2);
			}
		}
	}
	statShiftFLInfoDaoI.update(rsl);
}
/**
 * 卷包车间产耗统计导出
 * 张璐：2015.11.16
 * @param matterInfoBean
 * @param response
 * @throws Exception
 */
@Override
public HSSFWorkbook excelHandAndReceiveTeam(String stim,String etim,String mdTeamId,String mdShiftId)throws Exception {
	HSSFWorkbook wb = null;
	ExportExcel ee = new ExportExcel();
	try {
		// th 当前开始行,当前结束行,一共多少列
		int[] thTables = { 1, 4, 59 };
		List<String> th = new ArrayList<String>();
		// 第1行;第1列开始 ~ 第59列结束  第一行结束

		th.add("1,1,59,1,卷包车间产耗统计");
		th.add("2,1,1,4,工段");// 第2行;第1列开始 ~ 第1列结束  第4行结束
		th.add("2,2,2,4,日期");
		th.add("2,3,3,4,定编机组");
		th.add("2,4,4,4,实开机组");
		th.add("2,5,5,4,牌号");
		th.add("2,6,7,2,产量");//产量
		th.add("3,6,6,4,卷烟(箱)");
		th.add("3,7,7,4,包装(箱)");
		th.add("2,8,10,2,残烟");//残烟 
		th.add("3,8,8,4,卷烟(KG)");
		th.add("3,9,9,4,包装(箱)");
		th.add("3,10,10,4,吹车(KG)");
		th.add("2,11,11,4,卷烟纸规格(米/PAN)");
		th.add("2,12,23,2,虚领");//虚领
		th.add("3,12,14,3,卷烟机用料");//二级标题
		th.add("4,12,12,4,滤棒(支)");
		th.add("4,13,13,4,卷烟纸(PAN) ");
		th.add("4,14,14,4,水松纸(KG)");
		th.add("3,15,23,3,包装机用料");//二级标题
		th.add("4,15,15,4,商标纸(万张)");
		th.add("4,16,16,4,条盒(万张)");
		th.add("4,17,17,4,铝箔纸(KG)");
		th.add("4,18,18,4,小透明(KG)");
		th.add("4,19,19,4,条透明(KG)");
		th.add("4,20,20,4,卡纸(KG)");
		th.add("4,21,21,4,封签(万张)");
		th.add("4,22,22,4,拉线1(KG)");
		th.add("4,23,23,4,拉线2(KG)");
		
		th.add("2,24,35,2,实领");//实领
		th.add("3,24,26,3,卷烟机用料");//二级标题
		th.add("4,24,24,4,滤棒(支)");
		th.add("4,25,25,4,卷烟纸(PAN) ");
		th.add("4,26,26,4,水松纸(KG)");
		th.add("3,27,35,3,包装机用料");//二级标题
		th.add("4,27,27,4,商标纸(万张)");
		th.add("4,28,28,4,条盒(万张)");
		th.add("4,29,29,4,铝箔纸(KG)");
		th.add("4,30,30,4,小透明(KG)");
		th.add("4,31,31,4,条透明(KG)");
		th.add("4,32,32,4,卡纸(KG)");
		th.add("4,33,33,4,封签(万张)");
		th.add("4,34,34,4,拉线1(KG)");
		th.add("4,35,35,4,拉线2(KG)");
		
		th.add("2,36,47,2,实领");//虚退
		th.add("3,36,38,3,卷烟机用料");//二级标题
		th.add("4,36,36,4,滤棒(支)");
		th.add("4,37,37,4,卷烟纸(PAN) ");
		th.add("4,38,38,4,水松纸(KG)");
		th.add("3,39,47,3,包装机用料");//二级标题
		th.add("4,39,39,4,商标纸(万张)");
		th.add("4,40,40,4,条盒(万张)");
		th.add("4,41,41,4,铝箔纸(KG)");
		th.add("4,42,42,4,小透明(KG)");
		th.add("4,43,43,4,条透明(KG)");
		th.add("4,44,44,4,卡纸(KG)");
		th.add("4,45,45,4,封签(万张)");
		th.add("4,46,46,4,拉线1(KG)");
		th.add("4,47,47,4,拉线2(KG)");
		
		th.add("2,48,59,2,用料");//用料
		th.add("3,48,50,3,卷烟机用料");//二级标题
		th.add("4,48,48,4,滤棒(支)");
		th.add("4,49,49,4,卷烟纸(PAN) ");
		th.add("4,50,50,4,水松纸(KG)");
		th.add("3,51,59,3,包装机用料");//二级标题
		th.add("4,51,51,4,商标纸(万张)");
		th.add("4,52,52,4,条盒(万张)");
		th.add("4,53,53,4,铝箔纸(KG)");
		th.add("4,54,54,4,小透明(KG)");
		th.add("4,55,55,4,条透明(KG)");
		th.add("4,56,56,4,卡纸(KG)");
		th.add("4,57,57,4,封签(万张)");
		th.add("4,58,58,4,拉线1(KG)");
		th.add("4,59,59,4,拉线2(KG)");
		//获取List集合
		DataGrid dg = this.queryChangeShiftMatterInfo(stim,etim,mdTeamId,mdShiftId);
		//方法集合，同表头一致
		String[] method=new String[]{"getTeam_Name","getOrderDate","getHandover_user_name","getEqpName","getMat_name",
									"getJuQty","getBzQty","getQt_jy","getQt_bz","getQt_cc","getPa_gg",
									"getXl_zj_lb","getXl_zj_pz","getXl_zj_ssz","getXl_zb_xhz","getXl_zb_thz","getXl_zb_lbz","getXl_zb_xhm","getXl_zb_thm","getXl_zb_kz","getXl_zb_fq","getXl_zb_lx1","getXl_zb_lx2",//虚领
									"getSl_zj_lb","getSl_zj_pz","getSl_zj_ssz","getSl_zb_xhz","getSl_zb_thz","getSl_zb_lbz","getSl_zb_xhm","getSl_zb_thm","getSl_zb_kz","getSl_zb_fq","getSl_zb_lx1","getSl_zb_lx2",//实领
									"getXt_zj_lb","getXt_zj_pz","getXt_zj_ssz","getXt_zb_xhz","getXt_zb_thz","getXt_zb_lbz","getXt_zb_xhm","getXt_zb_thm","getXt_zb_kz","getXt_zb_fq","getXt_zb_lx1","getXt_zb_lx2",//虚退
									"getSy_zj_lb","getSy_zj_pz","getSy_zj_ssz","getSy_zb_xhz","getSy_zb_thz","getSy_zb_lbz","getSy_zb_xhm","getSy_zb_thm","getSy_zb_kz","getSy_zb_fq","getSy_zb_lx1","getSy_zb_lx2",//实用
									};
		//开始行
		int startLine=4;
		//WorkShopShiftBean.class List集合中对应的实体类
		wb = ee.exportExcel(thTables, th, startLine, method,MatterInfo.class,dg.getRows());
	} catch (Exception e) {
		/*
		 * json.setSuccess(false); json.setMsg("导出Execl失败！！！");
		 */
		e.printStackTrace();
	}

	return wb;
}
/**
 * 查询交接班其他料（挂靠在封箱机工单上）
 * shisihai
 */
@Override
public DataGrid queryChangeShiftOtherMatterInfo(String stim,String etim, String mdTeamId, String mdShiftId) {
	//查询sql
	String hql=this.getQueryOtherMatterInfo(stim,etim, mdTeamId, mdShiftId);
	List<?> ls=statShiftFLInfoDaoI.queryBySql(hql);
	//封装bean
	List<MatterInfo> dataList=this.setOtherMattherInfoBean(ls);
	return new DataGrid(dataList, 100L);
}

/**
 * 查询交接班其他料
 * zhanglu:2015.11.16
 * @param stim
 * @param mdTeamId
 * @param mdShiftId
 * @param response
 * @throws Exception
 */
@Override
public HSSFWorkbook otherMatterHandAndReceiveInfo(String stim,String etim,String mdTeamId,String mdShiftId)throws Exception {
	HSSFWorkbook wb = null;
	ExportExcel ee = new ExportExcel();
	try {
		// th 当前开始行,当前结束行,一共多少列
		int[] thTables = {1,3,11};
		List<String> th = new ArrayList<String>();
		// 第1行;第1列开始 ~ 第59列结束  第一行结束

		th.add("1,1,11,1,交接班其他料");// 第2行;第1列开始 ~ 第1列结束  第4行结束
		th.add("2,1,1,3,工段");
		th.add("2,2,2,3,日期");
		th.add("2,3,3,3,牌号");
		th.add("2,4,6,2,烟箱 ");
		th.add("3,4,4,3,虚领 ");
		th.add("3,5,5,3,实领");
		th.add("3,6,6,3,虚退");
		th.add("2,7,7,2,胶带 ");
		th.add("3,7,7,3,实领 ");
		th.add("2,8,8,2,接嘴胶 ");
		th.add("3,8,8,3,实领 ");
		th.add("2,9,9,2,搭扣胶 ");
		th.add("3,9,9,3,实领 ");
		th.add("2,10,10,2,包装胶 ");
		th.add("3,10,10,3,实领 ");
		th.add("2,11,11,2,烟箱 ");
		th.add("3,11,11,3,用料");
		//获取List集合
		DataGrid dg = this.queryChangeShiftOtherMatterInfo(stim,etim,mdTeamId,mdShiftId);
		//方法集合，同表头一致
		String[] method=new String[]{"getTeam_Name","getOrderDate","getMat_name","getXl_yf_xp","getSl_yf_xp","getXt_yf_xp","getXl_yf_jd","getOth_jzj","getOth_dkj","getOth_bzj","getSy_yf_xp"};
		//开始行
		int startLine=3;
		wb = ee.exportExcel(thTables, th, startLine, method,MatterInfo.class,dg.getRows());
	} catch (Exception e) {
		e.printStackTrace();
	}
	return wb;
}
/**
 * 其他辅料数据（挂在封箱机工单上）
 * @param stim
 * @param mdTeamId
 * @param mdShiftId
 * @return
 */
private String getQueryOtherMatterInfo(String stim,String etim, String mdTeamId, String mdShiftId){
	StringBuffer sb=new StringBuffer();
	sb.append("SELECT mt.NAME as team_name,CONVERT(VARCHAR(23),swo.[DATE],23) as sdate,mm.NAME as mat_name,");
	sb.append(" xl.yf_xp as xl_xp,xl.id as xl_id,");
	sb.append(" sl.yf_xp as sl_xp,sl.yf_jd as sl_jd,sl.oth_jzj,sl.oth_dkj,sl.oth_bzj,sl.id as sl_id,");
	sb.append(" xt.yf_xp as xt_xp,xt.id as xt_id ");
	sb.append(" from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
	sb.append(" LEFT JOIN MD_MAT mm ON mm.ID=swo.MAT ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,yf_xp from SCH_STAT_SHIFT_FLINFO WHERE fl_type=2 ) xl ON xl.oid=swo.ID ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,yf_xp,yf_jd,oth_jzj,oth_dkj,oth_bzj from SCH_STAT_SHIFT_FLINFO WHERE fl_type=1 ) sl ON sl.oid=swo.ID ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,yf_xp from SCH_STAT_SHIFT_FLINFO WHERE fl_type=3 ) xt ON xt.oid=swo.ID ");
	sb.append(" where  xl.mec_id=3 AND sl.mec_id=3 AND xt.mec_id=3 and swo.sts=4 ");
	if(StringUtil.notNull(stim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) >= '"+stim+"'");
	}
	if(StringUtil.notNull(etim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) <= '"+etim+"'");
	}
	if(StringUtil.notNull(mdTeamId)){
		sb.append(" AND swo.TEAM='"+mdTeamId+"'");
	}
	if(StringUtil.notNull(mdShiftId)){
		sb.append(" AND swo.SHIFT='"+mdShiftId+"'");
	}
	sb.append(" ORDER BY swo.[DATE],swo.TEAM asc ");
	return sb.toString();
}
/**
 * 封装其他料bean
 * shisihai
 * @param ls
 * @return
 */
@SuppressWarnings("rawtypes")
private List<MatterInfo> setOtherMattherInfoBean(List ls){
	List<MatterInfo> beans=new ArrayList<MatterInfo>();
	Object[] o=null;
	MatterInfo b=null;
	if(ls!=null){
		for (int i = 0; i < ls.size(); i++) {
			o=(Object[]) ls.get(i);
			b=new MatterInfo();
			//设置班组
			b.setMat_name(StringUtil.convertObjToString(o[0]));
			//时间
			b.setOrderDate(StringUtil.convertObjToString(o[1]));
			//牌号
			b.setMat_name(StringUtil.convertObjToString(o[2]));
			//虚领箱皮
			b.setXl_yf_xp(this.convertToDouble(o[3]));
			Long xl_id=Long.parseLong(StringUtil.convertObjToString(o[4]));
			b.setXl_yf_id(xl_id);
			//实领箱皮
			b.setSl_yf_xp(this.convertToDouble(o[5]));
			//实领胶带
			b.setSl_yf_jd(this.convertToDouble(o[6]));
			//接嘴胶
			b.setOth_jzj(this.convertToDouble(o[7]));
			//搭口胶
			b.setOth_dkj(this.convertToDouble(o[8]));
			//包装胶
			b.setOth_bzj(this.convertToDouble(o[9]));
			Long sl_id=Long.parseLong(StringUtil.convertObjToString(o[10]));
			b.setSl_yf_id(sl_id);
			//虚退
			//id
			Long xt_id=Long.parseLong(StringUtil.convertObjToString(o[12]));
			b.setXt_yf_id(xt_id);
			//虚退箱皮
			b.setXt_yf_xp(this.convertToDouble(o[11]));
			//实用
			b.setSy_yf_xp(this.calcuMatter(b.getXl_yf_xp(), b.getXt_yf_xp(), b.getSl_yf_xp()));
			beans.add(b);
		}
	}
	return beans;
}




/**
 * 根据机台名设置机台编号
 * shisihai
 * @param eqpName
 * @return
 */
private String reSetEqpName(Object eqpName){
	String name="";
	if(eqpName!=null){
		name=eqpName.toString();
		Integer index=name.indexOf("号");
		if(index!=-1){
			name=name.substring(0, index);
		}
	}
	return name;
}
/**
 * 其他料数据修改（绑定在封箱机工单上）
 * shisihai
 * @param bean
 */
@Override
public void saveOtherMatterInfoChange(MatterInfo bean) {
	
	//虚领数据修改,如果有上班次工单，则修改上班次虚退
	if(bean.getXl_yf_id()!=null){
		StatShiftFLInfo xl=statShiftFLInfoDaoI.findById(bean.getXl_yf_id());
		this.updateXLSealer(xl,bean);
	}
	//实领数据修改
	if(bean.getSl_yf_id()!=null){
		StatShiftFLInfo sl=statShiftFLInfoDaoI.findById(bean.getSl_yf_id());
		this.updateSLSealer(sl,bean);
	}
	//虚退数据修改，有过有下班次工单，修改下班次虚领
	if(bean.getXt_yf_id()!=null){
		StatShiftFLInfo xt=statShiftFLInfoDaoI.findById(bean.getXt_yf_id());
		this.updateXTSealer(xt,bean);
	}
}
/**
 * 更新其他辅料 虚退
 * shisihai
 * @param xt
 * @param bean
 */
private void updateXTSealer(StatShiftFLInfo xt, MatterInfo bean) {
	StatShiftFLInfo b2=null;
	//非正常换班，没有下班oid，查找下班oid
	if(null==xt.getDoid()){
		String doid=getRoundOrder(1,xt.getOid(),xt.getEqp_id());
		if(doid!=null){
			xt.setDoid(doid);
		}
	}
	if(StringUtil.notNull(xt.getDoid())){
		b2=this.getBeanByOidAndType(xt.getDoid(), 2, 1);
	}
	//箱皮
	if(bean.getXt_yf_xp()!=null){
		xt.setYf_xp(bean.getXt_yf_xp());
		if(b2!=null){
			b2.setYf_xp(bean.getXt_yf_xp());
		}
	}
	if(b2!=null){
		statShiftFLInfoDaoI.update(b2);
	}
	statShiftFLInfoDaoI.update(xt);
	
}

/**
 * 更新其他辅料数据 实领 包含其他胶和胶带的更新，都放在实领里面
 * shisihai
 * @param sl
 * @param bean
 */
private void updateSLSealer(StatShiftFLInfo sl, MatterInfo bean) {
	//箱皮
	if(bean.getSl_yf_xp()!=null){
		sl.setYf_xp(bean.getSl_yf_xp());
	}
	//胶带
	if(bean.getSl_yf_jd()!=null){
		sl.setYf_jd(bean.getSl_yf_jd());
	}
	//接嘴胶
	if(bean.getOth_jzj()!=null){
		sl.setOth_jzj(bean.getOth_jzj());
	}
	//搭口胶
	if(bean.getOth_dkj()!=null){
		sl.setOth_dkj(bean.getOth_dkj());
	}
	//包装胶
	if(bean.getOth_bzj()!=null){
		sl.setOth_bzj(bean.getOth_bzj());
	}
	statShiftFLInfoDaoI.update(sl);
}

/**
 * 更新其他物料信息  虚领
 * @param xl
 * @param bean
 */
private void updateXLSealer(StatShiftFLInfo xl, MatterInfo bean) {
	StatShiftFLInfo b2=null;
	//非正常换班，没有上班oid，查找上班oid
	if(null==xl.getUoid()){
		String uoid=getRoundOrder(2,xl.getOid(),xl.getEqp_id());
		if(uoid!=null){
			xl.setUoid(uoid);
		}
	}
	
	if(StringUtil.notNull(xl.getUoid())){
		b2=this.getBeanByOidAndType(xl.getUoid(), 3, 2);
	}
	//箱皮
	if(bean.getXl_yf_xp()!=null){
		xl.setYf_xp(bean.getXl_yf_xp());
		if(b2!=null){
			b2.setYf_xp(bean.getXl_yf_xp());
		}
	}
	if(b2!=null){
		statShiftFLInfoDaoI.update(b2);
	}
	statShiftFLInfoDaoI.update(xl);
}
/**
 * 成型机交接班数据修改
 * shisihai
 */
@Override
public void saveFilterMatterInfoChange(MatterInfo bean) {
	//虚领更新
	if(bean.getXl_zl_id()!=null){
		StatShiftFLInfo xl=statShiftFLInfoDaoI.findById(bean.getXl_zl_id());
		this.updateFilterXL(xl,bean);
	}
	//虚退更新
	if(bean.getXt_zl_id()!=null){
		StatShiftFLInfo xt=statShiftFLInfoDaoI.findById(bean.getXt_zl_id());
		this.updateFilterXT(xt,bean);
	}
	//实领更新
	if(bean.getSl_zl_id()!=null){
		StatShiftFLInfo sl=statShiftFLInfoDaoI.findById(bean.getSl_zl_id());
	    this.updateFilterSL(sl,bean);
	}
}
/**
 * 
 * TODO更新成型机交接班数据
 * @param sl
 * @param bean
 * TRAVLER
 * 2015年11月19日下午2:28:09
 */
private void updateFilterSL(StatShiftFLInfo sl, MatterInfo bean) {
	
	//产量更新
	if(bean.getFilterQty()!=null){
		if(StringUtil.notNull( sl.getOid())){
			String hql="FROM SchStatOutput o where o.schWorkorder.id=?";
			SchStatOutput b2=schStatOutputDao.unique(SchStatOutput.class, hql,sl.getOid());
			if(b2!=null){
				b2.setDACqty(b2.getQty());
				b2.setQty(bean.getFilterQty());
				schStatOutputDao.update(b2);
			}
		}
	}
	//盘纸
	if(bean.getSl_zl_pz()!=null){
		sl.setZl_pz(bean.getSl_zl_pz());
	}
	//丝束
	if(bean.getSl_zl_ss()!=null){
		sl.setZl_ss(bean.getSl_zl_ss());
	}
	//甘油
	if(bean.getSl_zl_gy()!=null){
		sl.setZl_gy(bean.getSl_zl_gy());
	}
	//搭口胶
	if(bean.getSl_zl_dkj()!=null){
		sl.setZl_dkj(bean.getSl_zl_dkj());
	}
	//热融胶
	if(bean.getSl_zl_rrj()!=null){
		sl.setZl_rrj(bean.getSl_zl_rrj());
	}
	statShiftFLInfoDaoI.update(sl);
}

/**
 * 更新成型机虚退数据
 * shisihai
 * @param xt
 * @param bean
 */
private void updateFilterXT(StatShiftFLInfo xt, MatterInfo bean) {
	StatShiftFLInfo b2=null;
	//非正常换班，没有下班oid，查找下班oid
	if(null==xt.getDoid()){
		String doid=getRoundOrder(1,xt.getOid(),xt.getEqp_id());
		if(doid!=null){
			xt.setDoid(doid);
		}
	}
	
	//可能需要更新下班次的虚领
	if(StringUtil.notNull(xt.getDoid())){
		b2=this.getBeanByOidAndType(xt.getDoid(), 2, 1);
	}
	//盘纸
	if(bean.getXt_zl_pz()!=null){
		xt.setZl_pz(bean.getXt_zl_pz());
		if(b2!=null){
			b2.setZl_pz(bean.getXt_zl_pz());
		}
	}
	//丝束
	if(bean.getXt_zl_ss()!=null){
		xt.setZl_ss(bean.getXt_zl_ss());
		if(b2!=null){
			b2.setZl_ss(bean.getXt_zl_ss());
		}
	}
	statShiftFLInfoDaoI.update(xt);
	if(b2!=null){
		statShiftFLInfoDaoI.update(b2);
	}
}

/**
 * 更新成型机虚领数据
 * shisihai
 * @param xl
 * @param bean
 */
private void updateFilterXL(StatShiftFLInfo xl, MatterInfo bean) {
	StatShiftFLInfo b2=null;
	//非正常换班，没有上班oid，查找上班oid
		if(null==xl.getUoid()){
			String uoid=getRoundOrder(2,xl.getOid(),xl.getEqp_id());
			if(uoid!=null){
				xl.setUoid(uoid);
			}
		}
	//可能需要更新上班次的虚退
	if(StringUtil.notNull(xl.getUoid())){
		b2=this.getBeanByOidAndType(xl.getUoid(), 3, 2);
	}
	//盘纸
	if(bean.getXl_zl_pz()!=null){
		xl.setZl_pz(bean.getXl_zl_pz());
		if(b2!=null){
			b2.setZl_pz(bean.getXl_zl_pz());
		}
	}
	//丝束
	if(bean.getXl_zl_ss()!=null){
		xl.setZl_ss(bean.getXl_zl_ss());
		if(b2!=null){
			b2.setZl_ss(bean.getXl_zl_ss());
		}
	}
	statShiftFLInfoDaoI.update(xl);
	if(b2!=null){
		statShiftFLInfoDaoI.update(b2);
	}
}

/**
 * 成型机交接班数据查询
 * shisihai
 */
@Override
public DataGrid queryFilterMatterInfo(String stim, String etim, String mdTeamId, String mdShiftId) {
	String hql=this.getQueryFilterMatter(stim,etim,mdTeamId,mdShiftId);
	List<?> ls=statShiftFLInfoDaoI.queryBySql(hql);
	//封装数据
	List<MatterInfo> beans=this.packFilterBean(ls);
	return new DataGrid(beans, 100L);
}
/**
 * 查询成型机交接班数据sql
 * @return
 */
private String getQueryFilterMatter(String stim, String etim, String mdTeamId, String mdShiftId) {
	StringBuffer sb=new StringBuffer();
	sb.append(" SELECT mt.NAME as team,CONVERT(VARCHAR(23),swo.[DATE],23) as sdate,me.EQUIPMENT_NAME,sl.mat_name,sso.QTY,");
	sb.append(" xl.id as xl_id,xl.zl_pz as xl_pz,xl.zl_ss as xl_ss,");
	sb.append(" sl.id as sl_id,sl.zl_pz as sl_pz,sl.zl_ss as sl_ss,sl.zl_gy as sl_gy,sl.zl_dkj as sl_dkj,sl.zl_rrj as sl_rrz,");
	sb.append(" xt.id as xt_id,xt.zl_pz as xt_pz,xt.zl_ss as xt_ss ");
	sb.append(" from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON swo.id=sso.OID ");
	sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
	sb.append(" LEFT JOIN MD_EQUIPMENT me ON me.id=swo.EQP ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,zl_pz,zl_ss from SCH_STAT_SHIFT_FLINFO where  fl_type=2  ) xl ON xl.oid=swo.ID ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,mat_name,zl_pz,zl_ss,zl_gy,zl_dkj,zl_rrj from SCH_STAT_SHIFT_FLINFO where fl_type=1 ) sl on sl.oid=swo.id ");
	sb.append(" LEFT JOIN (SELECT id,oid,mec_id,zl_pz,zl_ss from SCH_STAT_SHIFT_FLINFO where fl_type=3  ) xt ON xt.oid=swo.ID ");
	sb.append(" where  xl.mec_id=4 AND sl.mec_id=4 AND xt.mec_id=4 and swo.sts=4 ");
	if(StringUtil.notNull(stim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) >= '"+stim+"'");
	}
	if(StringUtil.notNull(etim)){
		sb.append(" AND CONVERT(VARCHAR(23),swo.[DATE],23) <= '"+etim+"'");
	}
	if(StringUtil.notNull(mdTeamId)){
		sb.append(" AND swo.TEAM='"+mdTeamId+"'");
	}
	if(StringUtil.notNull(mdShiftId)){
		sb.append(" AND swo.SHIFT='"+mdShiftId+"'");
	}
	sb.append(" ORDER BY swo.[DATE],swo.TEAM asc ");
	return sb.toString();
}

/*
 * 封装成型机数据
 */
private List<MatterInfo> packFilterBean(List<?> ls) {
	List<MatterInfo> beans=new ArrayList<MatterInfo>();
	MatterInfo b=null;
	Object[] o=null;
	if(ls!=null){
    	for (int i = 0; i < ls.size(); i++) {
			o=(Object[]) ls.get(i);
			b=new MatterInfo();
			//班次
			b.setTeam_Name(StringUtil.convertObjToString(o[0]));
			//时间
			b.setOrderDate(StringUtil.convertObjToString(o[1]));
			//设备名
			b.setEqpName(StringUtil.convertObjToString(o[2]));
			//牌名
			b.setMat_name(StringUtil.convertObjToString(o[3]));
			//产量
			b.setFilterQty(this.convertToDouble(o[4]));
			//虚领id
			Long xl_id=Long.parseLong(StringUtil.convertObjToString(o[5]));
			b.setXl_zl_id(xl_id);
			//虚领盘纸
			b.setXl_zl_pz(this.convertToDouble(o[6]));
			//丝束
			b.setXl_zl_ss(this.convertToDouble(o[7]));
			
			//实领
			Long sl_id=Long.parseLong(StringUtil.convertObjToString(o[8]));
			b.setSl_zl_id(sl_id);
			//盘纸
			b.setSl_zl_pz(this.convertToDouble(o[9]));
			//丝束
			b.setSl_zl_ss(this.convertToDouble(o[10]));
			//甘油
			b.setSl_zl_gy(this.convertToDouble(o[11]));
			//搭口胶带
			b.setSl_zl_dkj(this.convertToDouble(o[12]));
			//热融胶
			b.setSl_zl_rrj(this.convertToDouble(o[13]));
			//虚退
			Long xt_id=Long.parseLong(StringUtil.convertObjToString(o[14]));
			b.setXt_zl_id(xt_id);
			//盘纸
			b.setXt_zl_pz(this.convertToDouble(o[15]));
			//丝束
			b.setXt_zl_ss(this.convertToDouble(o[16]));
			//实用
			//盘纸
			b.setSy_zl_pz(this.calcuMatter(b.getXl_zl_pz(), b.getXt_zl_pz(), b.getSl_zl_pz()));
			//丝束
			b.setSy_zl_ss(this.calcuMatter(b.getXl_zl_ss(), b.getXt_zl_ss(), b.getSl_zl_ss()));
			beans.add(b);
		}
    }
	return beans;
}

/**
 * 成型机交接班数据导出
 */
@Override
public HSSFWorkbook filterMatterInfo(String stim, String etim, String mdTeamId, String mdShiftId) {
	HSSFWorkbook wb = null;
	ExportExcel ee = new ExportExcel();
	try {
		// th 当前开始行,当前结束行,  从1开始计算行  一共多少列 从0开始计算总列数
		int[] thTables = {1,3,15};
		List<String> th = new ArrayList<String>();

		th.add("1,1,16,1,成型机交接班物料信息");//开始行 开始列  结束列  结束行
		th.add("2,1,1,3,工段");
		th.add("2,2,2,3,日期");
		th.add("2,3,3,3,机台");
		th.add("2,4,4,3,产量");
		th.add("2,5,8,2,盘纸");
		th.add("3,5,5,3,虚领");
		th.add("3,6,6,3,实领");
		th.add("3,7,7,3,虚退");
		th.add("3,8,8,3,用料");
		th.add("2,9,12,2,丝束");
		th.add("3,9,9,3,虚领");
		th.add("3,10,10,3,实领");
		th.add("3,11,11,3,虚退");
		th.add("3,12,12,3,用料");
		th.add("2,13,13,2,甘油");
		th.add("3,13,13,3,实领");
		th.add("2,14,14,2,搭口胶");
		th.add("3,14,14,3,实领");
		th.add("2,15,15,2,热融胶");
		th.add("3,15,15,3,实领");
		//获取List集合
		DataGrid dg = this.queryFilterMatterInfo(stim,etim,mdTeamId,mdShiftId);
		//方法集合，同表头一致
		String[] method=new String[]{"getTeam_Name","getOrderDate","getEqpName","getFilterQty"
									 ,"getXl_zl_pz","getSl_zl_pz","getXt_zl_pz","getSy_zl_pz",
									 "getXl_zl_ss","getSl_zl_ss","getXt_zl_ss","getSy_zl_ss"
									 ,"getSl_zl_gy","getSl_zl_dkj","getSl_zl_rrj"};
		//开始行
		int startLine=3;
		wb = ee.exportExcel(thTables, th, startLine, method,MatterInfo.class,dg.getRows());
	} catch (Exception e) {
		e.printStackTrace();
	}
	return wb;
}
/**
 * 查询交接班产量信息
*TRAVLER
2015年11月23日下午7:01:21
*
*/
@Override
public ChangeShiftData queryQtyDataOfChangeShift(String time) {
	ChangeShiftData data=new ChangeShiftData();
	//查询卷烟机牌号分组产量
	List<?> l1=statShiftFLInfoDaoI.queryBySql(this.getSearchByMatSql(time,1));
	//封装数据
	this.setRollerQtyByMatBean(data, l1, 1);
	//查询包装机牌号分组产量信息
	List<?> l2=statShiftFLInfoDaoI.queryBySql(this.getSearchByMatSql(time,2));
	//封装数据
	this.setRollerQtyByMatBean(data, l2, 2);
	
	//查询卷烟机产量按机台分组
	List<?> l3=statShiftFLInfoDaoI.queryBySql(this.getsqlAboutRollerQtyByeqp(time));
	//封装数据
	this.setQtyByRollerEqpBean(data,l3);
	
	//查询包装机产量按机台分组
	List<?> l4=statShiftFLInfoDaoI.queryBySql(this.getsqlPackerAboutQtyByEqp(time));
	//封装数据
	this.setQtyByPackerEqpBean(data, l4);
	
	//查询总产量
	List<?> l5=statShiftFLInfoDaoI.queryBySql(this.getsqlAllQty(time));
	//封装数据
	this.setSumQty(data, l5);
	
	
	return data;
}
/**
 * 查询总产量信息（牌号分组）
 * TODO
 * @param date
 * @param type 1 卷烟机  2包装机
 * @return
 * TRAVLER
 * 2015年11月23日下午7:50:57
 */
private String getSearchByMatSql(String date,int type){
  StringBuffer sb=new StringBuffer();
  sb.append(" SELECT T0.mat,SUM(T0.qty) as q1,SUM(T0.szy) as q2,T0.TEAM  ");
  sb.append(" from  ");
  sb.append(" (SELECT swo.TEAM,ISNULL(sso.QTY, 0) as qty,ISNULL(ssf.zj_szy, 0) as szy, mm.NAME as mat from SCH_WORKORDER swo ");
  sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
  sb.append(" LEFT JOIN MD_MAT mm ON mm.ID=swo.MAT ");
  sb.append(" LEFT JOIN SCH_STAT_SHIFT_FLINFO ssf ON ssf.oid=swo.ID ");
  sb.append(" WHERE  ");
  sb.append("  ssf.fl_type=1 AND ssf.mec_id="+type);
  sb.append(" AND swo.STS=4 AND swo.TYPE="+type+" AND CONVERT(VARCHAR(23),swo.[DATE],23)='"+date+"'");
  sb.append(" ) T0 GROUP BY T0.mat,T0.TEAM");
  return sb.toString();
}

/**
 * 封装产量综合信息
 * TODO
 * @param data
 * @param ls
 * @param type 1卷烟机 2包装机
 * TRAVLER
 * 2015年11月23日下午7:57:07
 */
private void setRollerQtyByMatBean(ChangeShiftData data,List<?> ls,int type){
	if(ls!=null&&ls.size()>0){
		Object[] o=null;
		if(type==1){
			for (int i = 0; i < ls.size(); i++) {
				o=(Object[]) ls.get(i);
				Integer team=this.getInteger(o[3]);
				//转换散支烟成箱
				Double szyQty=this.convertToDouble(o[2]);
				szyQty=MathUtil.roundHalfUp(szyQty*0.0865, 2);
				o[2]=szyQty;
				if(team==1){
					data.getJRsumQty().add(o);
				}else if(team==2){
					data.getYRsumQty().add(o);
				}else if(team==3){
					data.getBRsumQty().add(o);
				}
			}
			//添加合计
			cacuSum(data.getJRsumQty());
			cacuSum(data.getYRsumQty());
			cacuSum(data.getBRsumQty());
			
			
			
		}else if(type==2){
			for (int i = 0; i < ls.size(); i++) {
				o=(Object[]) ls.get(i);
				Integer team=this.getInteger(o[3]);
				if(team==1){
					data.getJPsumQty().add(o);
				}else if(team==2){
					data.getYPsumQty().add(o);
				}else if(team==3){
					data.getBPsumQty().add(o);
				}
			}
			//添加合计
			cacuSum(data.getJPsumQty());
			cacuSum(data.getYPsumQty());
			cacuSum(data.getBPsumQty());
			
		}
		
	}
}
/**
 * 查询卷烟机交接班产量详细信息（按机台分组） 必须已完成交接班
 * TODO
 * @param time
 * @param type
 * @return
 * TRAVLER
 * 2015年11月23日下午9:12:11
 */
private String getsqlAboutRollerQtyByeqp(String time){
	StringBuffer sb=new StringBuffer();
	sb.append(" SELECT t.code1,t.id1,t.q1*50,t.q1,t.q2,t.team from ( ");
	sb.append(" SELECT mep.EQUIPMENT_CODE as code1,swo.ID as id1,ISNULL(sso.QTY, 0) as q1,ISNULL(ssf.zj_szy, 0) as q2,swo.TEAM as team from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
	sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.id=swo.EQP ");
	sb.append(" LEFT JOIN SCH_STAT_SHIFT_FLINFO ssf ON ssf.oid=swo.ID ");
	sb.append(" where swo.TYPE=1 AND ssf.mec_id=1 ");
	sb.append("  AND ssf.fl_type=1  AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23)='"+time+"'");
	sb.append(" ) t GROUP BY t.id1,t.q1,t.code1,t.q2,t.team ORDER BY t.code1 ASC ");
	return sb.toString();
}
/**
 * 封装卷烟机交接班产量详细（按机台）
 * TODO
 * @param data
 * @param ls
 * TRAVLER
 * 2015年11月23日下午9:23:31
 */
private void setQtyByRollerEqpBean(ChangeShiftData data,List<?> ls){
	if(ls!=null&&ls.size()>0){
		Object[] o=null;
		for (int i = 0; i < ls.size(); i++) {
			o=(Object[]) ls.get(i);
			Integer team=this.getInteger(o[5]);
			if(team==1){
				data.getJRdata().add(o);
			}else if(team==2){
				data.getYRdata().add(o);
			}else if(team==3){
				data.getBRdata().add(o);
			}
		}
		this.fillObject(data.getJRdata(), 1, 1);
		this.fillObject(data.getYRdata(), 2, 1);
		this.fillObject(data.getBRdata(), 3, 1);
	}
}
/**
 * 包装机交接班产量详细
 * TODO DACQty是修改之前的数据，最初是null，修改产量时会把之前的产量存放在DACQTY中 必须已完成交接班
 * @param time
 * @return
 * TRAVLER
 * 2015年11月23日下午10:09:05
 */
private String getsqlPackerAboutQtyByEqp(String time){
	StringBuffer sb=new StringBuffer();
	sb.append(" SELECT t.c1,t.id1,");
	sb.append(" CASE t.q1 WHEN 0 THEN t.q2*250 ELSE t.q1*250 END as q1,");//调整前
	sb.append(" CASE t.q1 WHEN 0 THEN 0 ELSE (t.q2*250-t.q1*250) END as q2,");//差值
	sb.append(" t.q2*250 as q3,t.q2 as q4,t.team ");
	sb.append(" from  ");
	sb.append(" (SELECT swo.id as id1,ISNULL(sso.DACQTY, 0) as q1,ISNULL(sso.QTY, 0) as q2,mep.EQUIPMENT_CODE as c1,swo.TEAM as team from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
	sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.id=swo.EQP ");
	sb.append(" left join SCH_STAT_SHIFT_FLINFO ssf on ssf.oid = swo.id ");//工单已经完成
	sb.append(" where ssf.fl_type=1 and swo.TYPE=2 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23)='"+time+"' ) t");
	sb.append(" GROUP BY t.id1,t.q1,t.q2,t.c1,t.team ORDER BY t.c1 ASC");
	return sb.toString();
}
/**
 * 封装包装机产量详细信息
 * TODO
 * @param data
 * @param ls
 * TRAVLER
 * 2015年11月23日下午10:11:29
 */
private void setQtyByPackerEqpBean(ChangeShiftData data,List<?> ls){
	if(ls!=null&&ls.size()>0){
		Object[] o=null;
		for (int i = 0; i < ls.size(); i++) {
			o=(Object[]) ls.get(i);
			Integer team=this.getInteger(o[6]);
			Integer code=this.getInteger(o[0]);
			//设置从新机台号
			o[0]=code-30;
			if(team==1){
				data.getJPdata().add(o);
			}else if(team==2){
				data.getYPdata().add(o);
			}else if(team==3){
				data.getBPdata().add(o);
			}
		}
		this.fillObject(data.getJPdata(), 1,2);
		this.fillObject(data.getYPdata(), 2,2);
		this.fillObject(data.getBPdata(), 3,2);
	}
}
/**
 * 给ls填充空数据
 * TODO
 * @param ls
 * @param team 班组
 * @param type 1卷烟机  2包装机
 * TRAVLER
 * 2015年11月24日上午8:45:38
 */
private void fillObject(List<Object[]> ls,Integer team,Integer type){
	if(ls.size()>0){
		Integer c1=null;
		Integer c2=null;
		//创建11个object数组放到List中，遍历集合
		List<Object[]> o1=new ArrayList<Object[]>();
		Object[] o2=null;
		for (int i = 1; i < 13; i++) {
			if(i==10){
				continue;
			}
			if(type==2){
				o2=new Object[]{i,0,0,0,0,0,team};
			}else if(type==1){
				o2=new Object[]{i,0,0,0,0,team};
			}
			o1.add(o2);
		}
		//填充数据（缺少的数据要补充默认值）
		for(int j=0;j<ls.size();j++){
			o2=(Object[]) ls.get(j);
			c1=this.getInteger(o2[0]);
			Object[] o3=null;
			for(int k=0;k<o1.size();k++){
				o3=o1.get(k);
				c2=this.getInteger(o3[0]);
				if(c1==c2){
					o1.set(k, o2);
				}
			}
		}
		//重新给集合赋值
		ls.clear();
		for (Object[] obj : o1) {
			obj[0]=obj[0]+"#";
		}
		ls.addAll(o1);
	}
}


/**
 * 获取交接班总产量
 * TODO
 * @param time
 * @return
 * TRAVLER
 * 2015年11月23日下午10:38:24 交接班完成
 */
private String getsqlAllQty(String time){
	StringBuffer sb=new StringBuffer();
	sb.append(" SELECT SUM(t.q),t.type from ( ");
	sb.append(" SELECT ISNULL(sso.QTY, 0) as q,swo.type from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.oid=swo.ID ");
	sb.append(" LEFT JOIN SCH_STAT_SHIFT_FLINFO ssf ON ssf.oid= swo.id ");
	sb.append(" where ssf.fl_type = 1  and CONVERT(VARCHAR(23),swo.[DATE],23)='"+time+"' ");
	sb.append(" AND swo.STS=4 AND (swo.TYPE=1 OR swo.TYPE=2) ) t GROUP BY t.type ORDER BY t.type ASC");
	return sb.toString();
}
/**
 * 封装交接班总产量
 * TODO
 * @param data
 * @param ls
 * TRAVLER
 * 2015年11月23日下午10:42:23
 */
private void setSumQty(ChangeShiftData data,List<?> ls){
	if(ls!=null&&ls.size()>0){
		Object[] o=null;
		o=(Object[]) ls.get(0);
		//卷烟机产量
		if(this.getInteger(o[1])==1){
			data.setrSum(this.convertToDouble(o[0]));
		}
		//包装机产量
		if(ls.size()>1 && this.getInteger(o[1])==2){
			o=(Object[]) ls.get(1);
			data.setpSum(this.convertToDouble(o[0]));
		}
	}
}
/**
 * Object转int
 * TODO
 * @param o
 * @return
 * TRAVLER
 * 2015年11月23日下午8:02:35
 */
private Integer getInteger(Object o){
	Integer i=0;
	if(null!=o){
		if(StringUtil.isInteger(o.toString())){
			i=Integer.parseInt(o.toString());
		}
	}
	return i;
}
/**
*TRAVLER
2015年11月20日下午2:40:03
* 修改交接班产量数据
*/
@Override
public void updateQtyInfo(ChangeShiftData[] firstBean,ChangeShiftData[] packer,ChangeShiftData[] filter) {
	//查询产量信息hql
	String hql="FROM SchStatOutput o where o.schWorkorder.id=?";
	SchStatOutput outBean=null;
	//卷烟机数据修改
	if(firstBean!=null&&firstBean.length>0){
		for (ChangeShiftData bean : firstBean) {
			String oid=bean.getOid();//工单id
			Double rQty=bean.getRollerQty();//修改后的产量（千支）
			Double szyQty=bean.getSzyQty();//修改后的散支烟（单位是盒）*0.0865  换算成箱 散支烟数据放在实领类别里面
			//修改产量
			outBean=schStatOutputDao.unique(SchStatOutput.class, hql, oid);
			if(outBean!=null){
				if(rQty!=null){
					outBean.setDACqty(outBean.getQty());
					rQty=MathUtil.roundHalfUp(rQty/50, 2);
					outBean.setQty(rQty);
				}
				schStatOutputDao.update(outBean);
			}
			//修改散支烟
			String sql=" UPDATE SCH_STAT_SHIFT_FLINFO SET zj_szy= "+szyQty+" where oid= '"+oid+"'";
			inputDaoI.updateBySql(sql, null);
		}
	}
	//包装机数据修改
	if(packer!=null&&packer.length>0){
		for (ChangeShiftData bean : filter) {
			String oid=bean.getOid();//工单id
			outBean=schStatOutputDao.unique(SchStatOutput.class, hql, oid);
			if(outBean!=null){
				Double diff=bean.getDiff();//调整数
				if(diff!=null){
					diff=MathUtil.roundHalfUp(diff/250, 2);
				}
				Double qty=outBean.getQty();
				outBean.setDACqty(qty);
				outBean.setQty(qty+diff);
				schStatOutputDao.update(outBean);
			}
			
		}
	}
	//成型机产量数据修改  需要确定第三列的数据，也可以最后做，发射机的数据是没有的
	if(filter!=null&&filter.length>0){
		for (ChangeShiftData bean : filter) {
			
		}
		
	}
}
/**
 * 交接班添加合计
 * TODO
 * @param ls
 * TRAVLER
 * 2015年11月20日下午6:37:41
 */
private void cacuSum(List<Object[]> ls){
	if(ls!=null&&ls.size()>0){
		Double qtyHj=0.0;
		Double syQty=0.0;
		for (Object[] o : ls) {
			qtyHj +=  Double.parseDouble(o[1].toString());//产量合计
			syQty += Double.parseDouble(o[2].toString());//散烟合计
		}
		Object[] o2=new Object[]{"合计",MathUtil.roundHalfUp(qtyHj, 2),MathUtil.roundHalfUp(syQty, 2)};
		ls.add(o2);
	}
}
/**
*TRAVLER
2015年11月21日下午9:07:57
*查询月绩效产耗信息
*/
@Override
public DataGrid queryMonthQtyData(String stim, String etime) {
	List<?> rs=null;//卷烟机查询
	List<?> ps=null;//包装机数据
	List<MatterInfo> bs=new ArrayList<MatterInfo>();
	String sql1=getRollerMonthSql(stim,etime);
	String sql2=getPackerMonthSql(stim,etime);
	rs=statShiftFLInfoDaoI.queryBySql(sql1);
	ps=statShiftFLInfoDaoI.queryBySql(sql2);
	//对查询结果进行封装成bean
	this.monthRollerSetParams(rs,bs);
	this.monthPackerSetParams(ps,bs);
	//配对数据  暂时不做(吹车数据处理：  包装机和卷烟机之中哪个有数据就取哪个，注意不是两者之和)
	
	//当正式登陆WCT之后，可以根据登陆人的所属组号匹配组合包装机和卷烟机数据到一条数据
	
	
	
	
	
	//排序  按牌号/班组
	Comparator<MatterInfo> p=new Comparator<MatterInfo>() {
		@Override
		public int compare(MatterInfo o1, MatterInfo o2) {
			int f=-o1.getMat_name().compareTo(o2.getMat_name());
			if(f==0){
				f=o1.getTeam_Name().compareTo(o2.getTeam_Name());
			}
			return -f;
		}
	};
	//bs.sort(p);
	int size=bs.size();
	MatterInfo[] bs2=new MatterInfo[size];
	for (int i = 0; i < size; i++) {
		bs2[i]=bs.get(i);
	}
	Arrays.sort(bs2,p);
	bs.clear();
	for (MatterInfo matterInfo : bs2) {
		bs.add(matterInfo);
	}
	
	//挑拣数据，为每个牌号和班组最后添加一个合计
	//1.将数据按牌号/班组分成list
	List<List<MatterInfo>> mls=this.addSumGroupByMT(bs);
	//2.对每个list中添加一个带有合计的对象
	for (List<MatterInfo> list : mls) {
		this.addSumObj(list);
	}
	//3.将数据整合到一个List中
	bs.clear();
	for (List<MatterInfo> list : mls) {
		bs.addAll(list);
	}
	return new DataGrid(bs,500L);
}
/**
 * 向集合中添加一个合计对象
 * TODO
 * @param list
 * TRAVLER
 * 2015年12月2日上午10:48:33
 */
private void addSumObj(List<MatterInfo> list) {
	
	Double zjQty=0.0;//卷烟产量和
	Double zbQty=0.0;
	Double zjC=0.0;
	Double bzC=0.0;
	Double cc=0.0;
	Double lb=0.0;
	Double pz=0.0;
	Double ssz=0.0;
	Double xhz=0.0;
	Double thz=0.0;
	Double lbz=0.0;
	Double xhm=0.0;
	Double thm=0.0;
	Double kz=0.0;
	Double fq=0.0;
	Double lx1=0.0;
	Double lx2=0.0;
	
	//单耗和
	Double zjCDH=0.0;
	Double bzCDH=0.0;
	Double ccDH=0.0;
	Double lbDH=0.0;
	Double pzDH=0.0;
	Double sszDH=0.0;
	Double xhzDH=0.0;
	Double thzDH=0.0;
	Double lbzDH=0.0;
	Double xhmDH=0.0;
	Double thmDH=0.0;
	Double kzDH=0.0;
	Double fqDH=0.0;
	Double lx1DH=0.0;
	Double lx2DH=0.0;
	
	for (MatterInfo b : list) {
		zjQty += this.convertToDouble(b.getJuQty());
		zbQty += this.convertToDouble(b.getBzQty());
		zjC   += this.convertToDouble(b.getQt_jy());
		bzC   += this.convertToDouble(b.getQt_bz());
		cc    += this.convertToDouble(b.getQt_cc());
		lb    += this.convertToDouble(b.getSy_zj_lb());
		pz    += this.convertToDouble(b.getSy_zj_pz());
		ssz   += this.convertToDouble(b.getSy_zj_ssz());
		xhz   += this.convertToDouble(b.getSy_zb_xhz());
		thz   += this.convertToDouble(b.getSy_zb_thz());
		lbz   += this.convertToDouble(b.getSy_zb_lbz());
		xhm   += this.convertToDouble(b.getSy_zb_xhm());
		thm   += this.convertToDouble(b.getSy_zb_thm());
		kz    += this.convertToDouble(b.getSy_zb_kz());
		fq    += this.convertToDouble(b.getSy_zb_fq());
		lx1   += this.convertToDouble(b.getSy_zb_lx1());
		lx2   += this.convertToDouble(b.getSy_zb_lx2());
		
		//单耗
		zjCDH += this.convertToDouble(b.getZjDH());
		bzCDH += this.convertToDouble(b.getZbDH());
		ccDH  += this.convertToDouble(b.getCcDH());
		lbDH  += this.convertToDouble(b.getLbDH());
		pzDH  += this.convertToDouble(b.getPzDH());
		sszDH += this.convertToDouble(b.getSszDH());
		xhzDH += this.convertToDouble(b.getXhzDH());
		thzDH += this.convertToDouble(b.getThzDH());
		lbzDH += this.convertToDouble(b.getLbzDH());
		xhmDH += this.convertToDouble(b.getXhmDH());
		thmDH += this.convertToDouble(b.getThmDH());
		kzDH  += this.convertToDouble(b.getKzDH());
		fqDH  += this.convertToDouble(b.getFqDH());
		lx1DH += this.convertToDouble(b.getLx1DH());
		lx2DH += this.convertToDouble(b.getLx2DH());
	}
	MatterInfo bean=new MatterInfo();
	bean.setUserEqpGroup("合计");
	bean.setJuQty(zjQty);
	bean.setBzQty(zbQty);
	bean.setQt_jy(zjC);
	bean.setQt_bz(bzC);
	bean.setQt_cc(cc);
	bean.setSy_zj_lb(lb);
	bean.setSy_zj_pz(pz);
	bean.setSy_zj_ssz(ssz);
	bean.setSy_zb_xhz(xhz);
	bean.setSy_zb_thz(thz);
	bean.setSy_zb_lbz(lbz);
	bean.setSy_zb_xhm(xhm);
	bean.setSy_zb_thm(thm);
	bean.setSy_zb_kz(kz);
	bean.setSy_zb_fq(fq);
	bean.setSy_zb_lx1(lx1);
	bean.setSy_zb_lx2(lx2);
	//单耗和
	bean.setZjDH(MathUtil.roundHalfUp(zjCDH, 2));
	bean.setZbDH(MathUtil.roundHalfUp(bzCDH, 2));
	bean.setCcDH(MathUtil.roundHalfUp(ccDH, 2));
	bean.setLbDH(MathUtil.roundHalfUp(lbDH, 2));
	bean.setPzDH(MathUtil.roundHalfUp(pzDH, 2));
	bean.setSszDH(MathUtil.roundHalfUp(sszDH, 2));
	bean.setXhzDH(MathUtil.roundHalfUp(xhzDH, 2));
	bean.setThzDH(MathUtil.roundHalfUp(thzDH, 2));
	bean.setLbzDH(MathUtil.roundHalfUp(lbzDH, 2));
	bean.setXhmDH(MathUtil.roundHalfUp(xhmDH, 2));
	bean.setThmDH(MathUtil.roundHalfUp(thmDH, 2));
	bean.setKzDH(MathUtil.roundHalfUp(kzDH, 2));
	bean.setFqDH(MathUtil.roundHalfUp(fqDH, 2));
	bean.setLx1DH(MathUtil.roundHalfUp(lx1DH, 2));
	bean.setLx2DH(MathUtil.roundHalfUp(lx2DH, 2));
	list.add(bean);
}

/**
 * 为每个牌号和班组最后添加一个合计
 * TODO
 * @param bs
 * TRAVLER
 * 2015年12月2日上午9:43:10
 */
private List<List<MatterInfo>>  addSumGroupByMT(List<MatterInfo> bs) {
	List<List<MatterInfo>> mls=new ArrayList<List<MatterInfo>>();
	List<MatterInfo> mls2=new ArrayList<MatterInfo>(bs);//复制一份数据
	String mat=null;
	String team=null;
	List<MatterInfo> ls=null;//存放按牌号/班组分好的数据
	MatterInfo bean=null;
	MatterInfo bean2=null;
	int lnum=bs.size();
	for (int i = 0; i < lnum; i++) {
			if(!(bs.size()>0)){
				continue;
			}
			bean=bs.get(0);
			ls=new ArrayList<MatterInfo>();
			mat=bean.getMat_name();
			team=bean.getTeam_Name();
			for (int j = 0; j < mls2.size(); j++) {
				bean2=mls2.get(j);
				if(bean2.getMat_name().equals(mat) && bean2.getTeam_Name().equals(team)){
					ls.add(bean2);
				}
			}
			bs.removeAll(ls);//去掉已经匹配的数据
			mls.add(ls);
		}
		return mls;
	}
	

/**
 * 封装包装机月报表bean
 * TODO
 * @param rs
 * @param bs
 * TRAVLER
 * 2015年12月1日下午4:29:41
 */
private void monthPackerSetParams(List<?> ps, List<MatterInfo> bs) {
	MatterInfo b=null;
	Object[] o=null;
	if(ps!=null&&ps.size()>0){
		for (int i = 0; i < ps.size(); i++) {
			o=(Object[]) ps.get(i);
			b=new MatterInfo();
			b.setUserEqpGroup(StringUtil.convertObjToString(o[0]));
			b.setMat_name(StringUtil.convertObjToString(o[2]));
			b.setTeam_Name(StringUtil.convertObjToString(o[3]));
			
			b.setBzQty(this.convertToDouble(o[4]));
			b.setQt_bz(this.convertToDouble(o[5]));
			b.setQt_cc(this.convertToDouble(o[6]));
			b.setSy_zb_xhz(this.convertToDouble(o[7]));
			b.setSy_zb_thz(this.convertToDouble(o[8]));
			b.setSy_zb_lbz(this.convertToDouble(o[9]));
			b.setSy_zb_xhm(this.convertToDouble(o[10]));
			b.setSy_zb_thm(this.convertToDouble(o[11]));
			b.setSy_zb_kz(this.convertToDouble(o[12]));
			b.setSy_zb_fq(this.convertToDouble(o[13]));
			b.setSy_zb_lx1(this.convertToDouble(o[14]));
			b.setSy_zb_lx2(this.convertToDouble(o[15]));
			
			
			b.setZbDH(this.caultDH(o[4], o[5]));
			b.setXhzDH(this.caultDH(o[4], o[7]));
			b.setThzDH(this.caultDH(o[4], o[8]));
			b.setLbzDH(this.caultDH(o[4], o[9]));
			b.setXhmDH(this.caultDH(o[4], o[10]));
			b.setThmDH(this.caultDH(o[4], o[11]));
			b.setKzDH(this.caultDH(o[4], o[12]));
			b.setFqDH(this.caultDH(o[4], o[13]));
			b.setLx1DH(this.caultDH(o[4], o[14]));
			b.setLx2DH(this.caultDH(o[4], o[15]));
			bs.add(b);
		}
		
	}
	
	
}

/**
 * 封装月报表卷烟机bean
 * TODO
 * @param rs
 * @param bs
 * TRAVLER
 * 2015年12月1日下午4:29:05
 */
private void monthRollerSetParams(List<?> rs, List<MatterInfo> bs) {
	MatterInfo bean=null;
	Object[] o=null;
	if(rs!=null&&rs.size()>0){
		for (int i = 0; i < rs.size(); i++) {
			bean=new MatterInfo();
			o=(Object[]) rs.get(i);
			bean.setUserEqpGroup(StringUtil.convertObjToString(o[0]));
			bean.setMat_name(StringUtil.convertObjToString(o[2]));
			bean.setTeam_Name(StringUtil.convertObjToString(o[3]));
			bean.setJuQty(this.convertToDouble(o[4]));
			bean.setQt_jy(this.convertToDouble(o[5]));
			bean.setQt_cc(this.convertToDouble(o[6]));
			bean.setSy_zj_lb(this.convertToDouble(o[7]));
			bean.setSy_zj_pz(this.convertToDouble(o[8]));
			bean.setSy_zj_ssz(this.convertToDouble(o[9]));
			bean.setZjDH(this.caultDH(o[4], o[5]));
			bean.setLbDH(this.caultDH(o[4], o[7]));
			bean.setPzDH(this.caultDH(o[4], o[8]));
			bean.setSszDH(this.caultDH(o[4], o[9]));
			bs.add(bean);
		}
	}
}

/**
 * 获取卷烟机做日报表的时候使用（本日止累计）统计的是月初到当前日的综合
 * TODO
 * @param stim
 * @param etime
 * @param type 1为查总和  2为当天 3查牌号
 * 1.查机台总和：
 *    按机台和班组   					 1去掉牌号
 * 2.查当天
 *    按/机台/牌号/班组
 * 3.查牌号和
 * 	    按牌号/班组 					 3 去掉机台
 * @return
 * TRAVLER
 * 2015年11月21日下午9:11:08
 */
private String getRollerDaySql(String stim, String etim,Integer type){
	StringBuffer sb=new StringBuffer();
	String info=null;
	if(type==1||type==3){
		etim=this.getAddDate(etim, 1);
		info="BETWEEN '"+stim+"' AND '"+etim+"' ";
	}else if(type==2){
		info=" = '"+etim+"' ";
	}
	if(type==1){//去掉牌号
		sb.append(" SELECT qtyTab.eqp_code,qtyTab.team,qtyTab.qty,flTab.qt_zj,flTab.cc,flTab.lb,flTab.pz,flTab.ssz ");
		sb.append(" from (SELECT f1.team1 as team,f1.eqp_id as eqpId,SUM(f1.qt_cc) as cc,SUM(f1.qt_zj0) as qt_zj,SUM(f1.pz1+f2.pz1-f3.pz1) as pz,SUM (f1.ssz1+f2.ssz1-f3.ssz1) as ssz,SUM(f1.lb1+f2.lb1-f3.lb1) as lb ");
		sb.append(" from (SELECT fl_1.team_id as team1,fl_1.eqp_id as eqp_id, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_zj0) as qt_zj0,SUM(fl_1.pz)as pz1,SUM(fl_1.ssz) as ssz1,SUM(fl_1.lb) as lb1 ");
		sb.append(" from ( SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_jy, 0) as qt_zj0,team_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=1 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.eqp_id) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.eqp_id as eqp_id,SUM(fl_2.pz)as pz1,SUM(fl_2.ssz) as ssz1,SUM(fl_2.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=1 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.eqp_id) f2, ");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.eqp_id as eqp_id,SUM(fl_3.pz)as pz1,SUM(fl_3.ssz) as ssz1,SUM(fl_3.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=1 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.eqp_id) f3 ");
		sb.append(" where f1.eqp_id=f2.eqp_id AND f2.eqp_id=f3.eqp_id AND f1.team1=f2.team1 AND f2.team1=f3.team1  ");
		sb.append(" GROUP BY f1.team1,f1.eqp_id ) flTab ");
		sb.append(" LEFT JOIN  ");
		//产量信息
		sb.append(" (SELECT t0.team,t0.eqp_code,t0.eqp_id,SUM(t0.qty) as qty,t0.team_id   from ( ");
		sb.append(" SELECT swo.TEAM as team_id,mt.NAME as team,mep.EQUIPMENT_CODE as eqp_code,swo.EQP as eqp_id,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.ID=swo.EQP ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=1 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) "+info+" ) t0");
		sb.append(" GROUP BY t0.team,t0.eqp_code,t0.eqp_id,t0.team_id ) qtyTab ");
		sb.append(" ON flTab.eqpId=qtyTab.eqp_id AND flTab.team=qtyTab.team_id ");
		sb.append(" ORDER BY qtyTab.team");
	}else if(type==3){//去掉机台
		sb.append(" SELECT qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.qt_zj,flTab.cc,flTab.lb,flTab.pz,flTab.ssz ");
		sb.append(" from (SELECT f1.team1 as team,f1.mat1 as matId,SUM(f1.qt_cc) as cc,SUM(f1.qt_zj0) as qt_zj,SUM(f1.pz1+f2.pz1-f3.pz1) as pz,SUM (f1.ssz1+f2.ssz1-f3.ssz1) as ssz,SUM(f1.lb1+f2.lb1-f3.lb1) as lb ");
		sb.append(" from (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_zj0) as qt_zj0,SUM(fl_1.pz)as pz1,SUM(fl_1.ssz) as ssz1,SUM(fl_1.lb) as lb1 ");
		sb.append(" from ( SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_jy, 0) as qt_zj0,team_id,mat_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=1 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,SUM(fl_2.pz)as pz1,SUM(fl_2.ssz) as ssz1,SUM(fl_2.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=1 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ) f2, ");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,SUM(fl_3.pz)as pz1,SUM(fl_3.ssz) as ssz1,SUM(fl_3.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where mec_id=1 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ) f3 ");
		sb.append(" where  f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
		sb.append(" GROUP BY f1.team1,f1.mat1 ) flTab ");
		sb.append(" LEFT JOIN  ");
		//产量信息
		sb.append(" (SELECT t0.mat_name,t0.team,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id  from ( ");
		sb.append(" SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.TEAM as team_id,mt.NAME as team,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=1 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) "+info+" ) t0");
		sb.append(" GROUP BY t0.mat_name,t0.team,t0.team_id,t0.mat_id ) qtyTab ");
		sb.append(" ON flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
		sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team");
	}else{
		sb.append(" SELECT qtyTab.eqp_code,qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.qt_zj,flTab.cc,flTab.lb,flTab.pz,flTab.ssz ");
		sb.append(" from (SELECT f1.team1 as team,f1.eqp_id as eqpId,f1.mat1 as matId,SUM(f1.qt_cc) as cc,SUM(f1.qt_zj0) as qt_zj,SUM(f1.pz1+f2.pz1-f3.pz1) as pz,SUM (f1.ssz1+f2.ssz1-f3.ssz1) as ssz,SUM(f1.lb1+f2.lb1-f3.lb1) as lb ");
		sb.append(" from (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1,fl_1.eqp_id as eqp_id, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_zj0) as qt_zj0,SUM(fl_1.pz)as pz1,SUM(fl_1.ssz) as ssz1,SUM(fl_1.lb) as lb1 ");
		sb.append(" from ( SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_jy, 0) as qt_zj0,team_id,mat_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=1 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ,fl_1.eqp_id) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,fl_2.eqp_id as eqp_id,SUM(fl_2.pz)as pz1,SUM(fl_2.ssz) as ssz1,SUM(fl_2.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=1 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ,fl_2.eqp_id) f2, ");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,fl_3.eqp_id as eqp_id,SUM(fl_3.pz)as pz1,SUM(fl_3.ssz) as ssz1,SUM(fl_3.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where mec_id=1 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ,fl_3.eqp_id) f3 ");
		sb.append(" where f1.eqp_id=f2.eqp_id AND f2.eqp_id=f3.eqp_id AND f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
		sb.append(" GROUP BY f1.team1,f1.eqp_id,f1.mat1) flTab ");
		sb.append(" LEFT JOIN  ");
		//产量信息
		sb.append(" (SELECT t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id  from ( ");
		sb.append(" SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.TEAM as team_id,mt.NAME as team,mep.EQUIPMENT_CODE as eqp_code,swo.EQP as eqp_id,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
		sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.ID=swo.EQP ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=1 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) "+info+" ) t0");
		sb.append(" GROUP BY t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,t0.team_id,t0.mat_id ) qtyTab ");
		sb.append(" ON flTab.eqpId=qtyTab.eqp_id AND flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
		sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team");
	}
	
	return sb.toString();
}
/**
 * 做日报表的时候使用（本日止累计）计算sql，统计的是月初到当前日的综合
 *  获取包装机日报sql BETWEEN '"+stim+"' AND '"+etim+"' "
 * TODO
 * @param stim
 * @param etim
 * @param type 1查总和   2查当天，3查牌号
 * 1.查机台总和：
 *    按机台和班组                                                        1去掉牌号
 * 2.查当天
 *    按班组/机台/牌号
 * 3.查牌号和
 * 	    按牌号/班组						 3.去掉机台
 * 
 * @return
 * TRAVLER
 * 2015年11月20日下午10:56:17
 */
private String getPackerDaySql(String stim, String etim,Integer type){
	StringBuffer sb=new StringBuffer();
	String info=null;
	if(type==1||type==3){
		etim=this.getAddDate(etim, 1);
		info="BETWEEN '"+stim+"' AND '"+etim+"' ";
	}else if(type==2){
		info=" = '"+etim+"' ";
	}
	
	if(type==1){//去掉牌号
		sb.append(" SELECT qtyTab.eqp_code,qtyTab.team,qtyTab.qty,flTab.qt_bz,flTab.cc,flTab.xhz,flTab.thz,flTab.lbz,flTab.xhm,flTab.thm,flTab.kz,flTab.fq,flTab.lx1,flTab.lx2 from  ");
		sb.append(" (SELECT f1.team1 as team,f1.eqp_id as eqpId,SUM(f1.qt_cc) as cc,SUM(f1.qt_bz) as qt_bz,SUM(f1.xhz+f2.xhz-f3.xhz) as xhz,SUM (f1.xhm+f2.xhm-f3.xhm) as xhm,SUM(f1.thz+f2.thz-f3.thz) as thz,SUM(f1.thm+f2.thm-f3.thm) as thm ,SUM(f1.lbz+f2.lbz-f3.lbz) as lbz,SUM(f1.fq+f2.fq-f3.fq) as fq,SUM(f1.kz+f2.kz-f3.kz) as kz,SUM(f1.lx1+f2.lx1-f3.lx1) as lx1,SUM(f1.lx2+f2.lx2-f3.lx2) as lx2 from  ");
		sb.append(" (SELECT fl_1.team_id as team1,fl_1.eqp_id as eqp_id, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_bz0) as qt_bz,SUM(fl_1.xhz) as xhz,SUM(fl_1.xhm) as xhm,SUM(fl_1.thz) as thz,SUM(fl_1.thm) as thm, SUM(fl_1.lbz) as lbz,SUM(fl_1.kz) as kz,SUM(fl_1.fq) as fq,SUM(fl_1.lx1) as lx1,SUM(fl_1.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid)  "+info);
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.eqp_id) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.eqp_id as eqp_id, SUM(fl_2.qt_cc0) as qt_cc,SUM(fl_2.qt_bz0) as qt_bz,SUM(fl_2.xhz) as xhz,SUM(fl_2.xhm) as xhm,SUM(fl_2.thz) as thz,SUM(fl_2.thm) as thm, SUM(fl_2.lbz) as lbz,SUM(fl_2.kz) as kz,SUM(fl_2.fq) as fq,SUM(fl_2.lx1) as lx1,SUM(fl_2.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.eqp_id) f2, ");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.eqp_id as eqp_id, SUM(fl_3.qt_cc0) as qt_cc,SUM(fl_3.qt_bz0) as qt_bz,SUM(fl_3.xhz) as xhz,SUM(fl_3.xhm) as xhm,SUM(fl_3.thz) as thz,SUM(fl_3.thm) as thm, SUM(fl_3.lbz) as lbz,SUM(fl_3.kz) as kz,SUM(fl_3.fq) as fq,SUM(fl_3.lx1) as lx1,SUM(fl_3.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=2 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.eqp_id) f3 ");
		sb.append(" where f1.eqp_id=f2.eqp_id AND f2.eqp_id=f3.eqp_id AND f1.team1=f2.team1 AND f2.team1=f3.team1  ");
		sb.append(" GROUP BY f1.team1,f1.eqp_id ) flTab ");
		sb.append(" LEFT JOIN ");
		//产量信息
		sb.append(" (SELECT t0.team,t0.eqp_code,t0.eqp_id,SUM(t0.qty) as qty,t0.team_id   from (");
		sb.append(" SELECT swo.TEAM as team_id,mt.NAME as team,mep.EQUIPMENT_CODE as eqp_code,swo.EQP as eqp_id,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.ID=swo.EQP ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=2 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) "+info+" ) t0 ");
		sb.append(" GROUP BY t0.team,t0.eqp_code,t0.eqp_id,t0.team_id ) qtyTab ");
		sb.append(" ON flTab.eqpId=qtyTab.eqp_id  AND flTab.team=qtyTab.team_id ");
		sb.append(" ORDER BY qtyTab.team ");
	}else if(type==3){//去掉机台
		sb.append(" SELECT qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.qt_bz,flTab.cc,flTab.xhz,flTab.thz,flTab.lbz,flTab.xhm,flTab.thm,flTab.kz,flTab.fq,flTab.lx1,flTab.lx2 from  ");
		sb.append(" (SELECT f1.team1 as team,f1.mat1 as matId,SUM(f1.qt_cc) as cc,SUM(f1.qt_bz) as qt_bz,SUM(f1.xhz+f2.xhz-f3.xhz) as xhz,SUM (f1.xhm+f2.xhm-f3.xhm) as xhm,SUM(f1.thz+f2.thz-f3.thz) as thz,SUM(f1.thm+f2.thm-f3.thm) as thm ,SUM(f1.lbz+f2.lbz-f3.lbz) as lbz,SUM(f1.fq+f2.fq-f3.fq) as fq,SUM(f1.kz+f2.kz-f3.kz) as kz,SUM(f1.lx1+f2.lx1-f3.lx1) as lx1,SUM(f1.lx2+f2.lx2-f3.lx2) as lx2 from  ");
		sb.append(" (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_bz0) as qt_bz,SUM(fl_1.xhz) as xhz,SUM(fl_1.xhm) as xhm,SUM(fl_1.thz) as thz,SUM(fl_1.thm) as thm, SUM(fl_1.lbz) as lbz,SUM(fl_1.kz) as kz,SUM(fl_1.fq) as fq,SUM(fl_1.lx1) as lx1,SUM(fl_1.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid)  "+info);
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1, SUM(fl_2.qt_cc0) as qt_cc,SUM(fl_2.qt_bz0) as qt_bz,SUM(fl_2.xhz) as xhz,SUM(fl_2.xhm) as xhm,SUM(fl_2.thz) as thz,SUM(fl_2.thm) as thm, SUM(fl_2.lbz) as lbz,SUM(fl_2.kz) as kz,SUM(fl_2.fq) as fq,SUM(fl_2.lx1) as lx1,SUM(fl_2.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ) f2, ");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1, SUM(fl_3.qt_cc0) as qt_cc,SUM(fl_3.qt_bz0) as qt_bz,SUM(fl_3.xhz) as xhz,SUM(fl_3.xhm) as xhm,SUM(fl_3.thz) as thz,SUM(fl_3.thm) as thm, SUM(fl_3.lbz) as lbz,SUM(fl_3.kz) as kz,SUM(fl_3.fq) as fq,SUM(fl_3.lx1) as lx1,SUM(fl_3.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=2 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ) f3 ");
		sb.append(" where  f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
		sb.append(" GROUP BY f1.team1,f1.mat1) flTab ");
		sb.append(" LEFT JOIN ");
		//产量信息
		sb.append(" (SELECT t0.mat_name,t0.team,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id  from (");
		sb.append(" SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.TEAM as team_id,mt.NAME as team,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=2 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) "+info+" ) t0 ");
		sb.append(" GROUP BY t0.mat_name,t0.team,t0.team_id,t0.mat_id ) qtyTab ");
		sb.append(" ON  flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
		sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team ");
	}else{
		sb.append(" SELECT qtyTab.eqp_code,qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.qt_bz,flTab.cc,flTab.xhz,flTab.thz,flTab.lbz,flTab.xhm,flTab.thm,flTab.kz,flTab.fq,flTab.lx1,flTab.lx2 from  ");
		sb.append(" (SELECT f1.team1 as team,f1.eqp_id as eqpId,f1.mat1 as matId,SUM(f1.qt_cc) as cc,SUM(f1.qt_bz) as qt_bz,SUM(f1.xhz+f2.xhz-f3.xhz) as xhz,SUM (f1.xhm+f2.xhm-f3.xhm) as xhm,SUM(f1.thz+f2.thz-f3.thz) as thz,SUM(f1.thm+f2.thm-f3.thm) as thm ,SUM(f1.lbz+f2.lbz-f3.lbz) as lbz,SUM(f1.fq+f2.fq-f3.fq) as fq,SUM(f1.kz+f2.kz-f3.kz) as kz,SUM(f1.lx1+f2.lx1-f3.lx1) as lx1,SUM(f1.lx2+f2.lx2-f3.lx2) as lx2 from  ");
		sb.append(" (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1,fl_1.eqp_id as eqp_id, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_bz0) as qt_bz,SUM(fl_1.xhz) as xhz,SUM(fl_1.xhm) as xhm,SUM(fl_1.thz) as thz,SUM(fl_1.thm) as thm, SUM(fl_1.lbz) as lbz,SUM(fl_1.kz) as kz,SUM(fl_1.fq) as fq,SUM(fl_1.lx1) as lx1,SUM(fl_1.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid)  "+info);
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ,fl_1.eqp_id) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,fl_2.eqp_id as eqp_id, SUM(fl_2.qt_cc0) as qt_cc,SUM(fl_2.qt_bz0) as qt_bz,SUM(fl_2.xhz) as xhz,SUM(fl_2.xhm) as xhm,SUM(fl_2.thz) as thz,SUM(fl_2.thm) as thm, SUM(fl_2.lbz) as lbz,SUM(fl_2.kz) as kz,SUM(fl_2.fq) as fq,SUM(fl_2.lx1) as lx1,SUM(fl_2.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ,fl_2.eqp_id) f2, ");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,fl_3.eqp_id as eqp_id, SUM(fl_3.qt_cc0) as qt_cc,SUM(fl_3.qt_bz0) as qt_bz,SUM(fl_3.xhz) as xhz,SUM(fl_3.xhm) as xhm,SUM(fl_3.thz) as thz,SUM(fl_3.thm) as thm, SUM(fl_3.lbz) as lbz,SUM(fl_3.kz) as kz,SUM(fl_3.fq) as fq,SUM(fl_3.lx1) as lx1,SUM(fl_3.lx2) as lx2 from ( ");
		sb.append(" SELECT oid,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=2 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) "+info);
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ,fl_3.eqp_id) f3 ");
		sb.append(" where f1.eqp_id=f2.eqp_id AND f2.eqp_id=f3.eqp_id AND f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
		sb.append(" GROUP BY f1.team1,f1.eqp_id,f1.mat1) flTab ");
		sb.append(" LEFT JOIN ");
		//产量信息
		sb.append(" (SELECT t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id  from (");
		sb.append(" SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.TEAM as team_id,mt.NAME as team,mep.EQUIPMENT_CODE as eqp_code,swo.EQP as eqp_id,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
		sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.ID=swo.EQP ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=2 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) "+info+" ) t0 ");
		sb.append(" GROUP BY t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,t0.team_id,t0.mat_id ) qtyTab ");
		sb.append(" ON flTab.eqpId=qtyTab.eqp_id AND flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
		sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team ");
	}
	return sb.toString();
}

/**
 * 获取卷烟机查询月绩效sql
 * TODO
 * @param stim
 * @param etim
 * @return
 * TRAVLER
 * 2015年11月20日下午9:58:18
 */
private String getRollerMonthSql(String stim,String etim){
	StringBuffer sb=new StringBuffer();
	etim=this.getAddDate(etim, 1);
	sb.append(" SELECT qtyTab.u_id,qtyTab.u_name,qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.qt_zj,flTab.cc,flTab.lb,flTab.pz,flTab.ssz from ");
	sb.append(" (SELECT f1.team1 as team,f1.u_id as u_id,f1.mat1 as matId,SUM(f1.qt_cc) as cc,SUM(f1.qt_zj0) as qt_zj,SUM(f1.pz1+f2.pz1-f3.pz1) as pz,SUM (f1.ssz1+f2.ssz1-f3.ssz1) as ssz,SUM(f1.lb1+f2.lb1-f3.lb1) as lb from  ");
	sb.append(" (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1,fl_1.u_id as u_id, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_zj0) as qt_zj0,SUM(fl_1.pz)as pz1,SUM(fl_1.ssz) as ssz1,SUM(fl_1.lb) as lb1 from ( ");
	sb.append(" SELECT oid,handover_user_id as u_id,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_jy, 0) as qt_zj0,team_id,mat_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
	sb.append(" where  mec_id=1 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ,fl_1.u_id) f1,");
	sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,fl_2.u_id as u_id,SUM(fl_2.pz)as pz1,SUM(fl_2.ssz) as ssz1,SUM(fl_2.lb) as lb1 from ( ");
	sb.append(" SELECT oid,handover_user_id as u_id,team_id,mat_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
	sb.append(" where  mec_id=1 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ,fl_2.u_id) f2,");
	sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,fl_3.u_id as u_id,SUM(fl_3.pz)as pz1,SUM(fl_3.ssz) as ssz1,SUM(fl_3.lb) as lb1 from ( ");
	sb.append(" SELECT oid,handover_user_id as u_id,team_id,mat_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
	sb.append(" where  mec_id=1 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ,fl_3.u_id) f3 ");
	sb.append(" where f1.u_id=f2.u_id AND f2.u_id=f3.u_id AND f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
	sb.append(" GROUP BY f1.team1,f1.u_id,f1.mat1) flTab ");
	sb.append(" LEFT JOIN ");
	sb.append(" (SELECT t0.mat_name,t0.team,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id,t0.u_id as u_id,t0.u_name as u_name  from ");
	sb.append(" (SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.LOGIN_NAME as u_name,swo.LOGIN_USER as u_id,swo.TEAM as team_id,mt.NAME as team,ISNULL(sso.QTY , 0) as qty ");
	sb.append(" from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
	sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
	sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
	sb.append(" WHERE swo.TYPE=1 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) t0 GROUP BY t0.mat_name,t0.team,t0.team_id,t0.mat_id,t0.u_id,t0.u_name ) qtyTab ");
	sb.append(" ON flTab.u_id=qtyTab.u_id AND flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
	sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team ");
	return sb.toString();
}
/**
 * 获取包装机月绩效sql
 * TODO
 * @param stim
 * @param etim
 * @return
 * TRAVLER
 * 2015年11月20日下午9:58:51
 */
private String getPackerMonthSql(String stim,String etim){
	StringBuffer sb=new StringBuffer();
	etim=this.getAddDate(etim, 1);
	sb.append(" SELECT qtyTab.u_id,qtyTab.u_name,qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.qt_bz,flTab.cc,flTab.xhz,flTab.thz,flTab.lbz,flTab.xhm,flTab.thm,flTab.kz,flTab.fq,flTab.lx1,flTab.lx2 from  ");
	sb.append(" (SELECT f1.team1 as team,f1.u_id as u_id,f1.mat1 as matId,SUM(f1.qt_cc) as cc,SUM(f1.qt_bz0) as qt_bz,SUM(f1.xhz+f2.xhz-f3.xhz) as xhz,SUM (f1.xhm+f2.xhm-f3.xhm) as xhm,SUM(f1.thz+f2.thz-f3.thz) as thz,SUM(f1.thm+f2.thm-f3.thm) as thm ,SUM(f1.lbz+f2.lbz-f3.lbz) as lbz,SUM(f1.fq+f2.fq-f3.fq) as fq,SUM(f1.kz+f2.kz-f3.kz) as kz,SUM(f1.lx1+f2.lx1-f3.lx1) as lx1,SUM(f1.lx2+f2.lx2-f3.lx2) as lx2  from ");
	sb.append(" (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1,fl_1.u_id as u_id, SUM(fl_1.qt_cc0) as qt_cc,SUM(fl_1.qt_bz0) as qt_bz0,SUM(fl_1.xhz) as xhz,SUM(fl_1.xhm) as xhm,SUM(fl_1.thz) as thz,SUM(fl_1.thm) as thm, SUM(fl_1.lbz) as lbz,SUM(fl_1.kz) as kz,SUM(fl_1.fq) as fq,SUM(fl_1.lx1) as lx1,SUM(fl_1.lx2) as lx2 from ( ");
	sb.append(" SELECT oid,handover_user_id as u_id,ISNULL(qt_cc, 0) as qt_cc0,ISNULL(qt_bz, 0) as qt_bz0,team_id,mat_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO  ");
	sb.append(" where  mec_id=2 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ,fl_1.u_id) f1,");
	sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,fl_2.u_id as u_id,SUM(fl_2.xhz) as xhz,SUM(fl_2.xhm) as xhm,SUM(fl_2.thz) as thz,SUM(fl_2.thm) as thm, SUM(fl_2.lbz) as lbz,SUM(fl_2.kz) as kz,SUM(fl_2.fq) as fq,SUM(fl_2.lx1) as lx1,SUM(fl_2.lx2) as lx2 from ( ");
	sb.append(" SELECT oid,handover_user_id as u_id,team_id,mat_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO  ");
	sb.append(" where  mec_id=2 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ,fl_2.u_id) f2,");
	sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,fl_3.u_id as u_id,SUM(fl_3.xhz) as xhz,SUM(fl_3.xhm) as xhm,SUM(fl_3.thz) as thz,SUM(fl_3.thm) as thm, SUM(fl_3.lbz) as lbz,SUM(fl_3.kz) as kz,SUM(fl_3.fq) as fq,SUM(fl_3.lx1) as lx1,SUM(fl_3.lx2) as lx2 from ( ");
	sb.append(" SELECT oid,handover_user_id as u_id,team_id,mat_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz,ISNULL(zb_xhm, 0) as xhm,ISNULL(zb_thm, 0) as thm,ISNULL(zb_kz, 0) as kz,ISNULL(zb_fq, 0) as fq,ISNULL(zb_lx1, 0) as lx1,ISNULL(zb_lx2, 0) as lx2 from SCH_STAT_SHIFT_FLINFO  ");
	sb.append(" where  mec_id=2 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ,fl_3.u_id) f3 ");
	sb.append(" where f1.u_id=f2.u_id AND f2.u_id=f3.u_id AND f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
	sb.append(" GROUP BY f1.team1,f1.u_id,f1.mat1) flTab ");
	sb.append(" LEFT JOIN (SELECT t0.mat_name,t0.team,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id,t0.u_id as u_id,t0.u_name as u_name  from ");
	sb.append(" (SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.LOGIN_NAME as u_name,swo.LOGIN_USER as u_id,swo.TEAM as team_id,mt.NAME as team,ISNULL(sso.QTY , 0) as qty ");
	sb.append(" from SCH_WORKORDER swo ");
	sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
	sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
	sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
	sb.append(" WHERE swo.TYPE=1 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) BETWEEN '"+stim+"' AND '"+etim+"' ");
	sb.append(" ) t0 GROUP BY t0.mat_name,t0.team,t0.team_id,t0.mat_id,t0.u_id,t0.u_name ) qtyTab ");
	sb.append(" ON flTab.u_id=qtyTab.u_id AND flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
	sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team ");
	return sb.toString();
}

/**
 * 时间处理  时间添加天数
 * TODO
 * @param date
 * @return
 * TRAVLER
 * 2015年11月20日下午11:14:09
 */
private  String getAddDate(String date,int diff){
	if(date!=null){
		Date d=DateUtil.formatStringToDate(date, "yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		int currDay = gc.get(Calendar.DATE);
		gc.set(Calendar.DATE, (currDay+diff));
		SimpleDateFormat fo=new SimpleDateFormat("yyyy-MM-dd");
		return fo.format(gc.getTime());
	}
	return null;
}
/**
 * 查询工段产耗信息
 * 整合数据的思路：
 * 1.在MatterInfo 对象里已经有9个List对应EXCL中的9组数据
 * 2.查询出的结果为6组，对6组数据挑出班组放到对应的list中，暂时是只有单个机台数据，最后对List处理配对
*TRAVLER
2015年11月30日上午8:28:57
*
*/
@Override
public MatterInfo queryDayQtyData(String stim, String etim) {
	MatterInfo m=new MatterInfo();
	//1.将查询出来的数据都封装MatterInfo成对象，放到9个list中
	//卷烟机数据
	//按机台/牌号/班组
	String sql1=this.getRollerDaySql(stim, etim, 2);
	List<?> ls1=statShiftFLInfoDaoI.queryBySql(sql1);
	this.setRollerParamBean(ls1,m,2);
	//按机台和班组
	String sql2=this.getRollerDaySql(stim, etim, 1);
	List<?> ls2=statShiftFLInfoDaoI.queryBySql(sql2);
	this.setRollerParamBean(ls2,m,1);
	//按牌号和班组
	String sql3=this.getRollerDaySql(stim, etim, 3);
	List<?> ls3=statShiftFLInfoDaoI.queryBySql(sql3);
	this.setRollerParamBean(ls3,m,3);
	
	
	//包装机
	//按班组/机台/牌号
	String sql4=this.getPackerDaySql(stim, etim, 2);
	List<?> ls4=statShiftFLInfoDaoI.queryBySql(sql4);
	this.setPackParamBean(ls4,m,2);
	//机台班组
	String sql5=this.getPackerDaySql(stim, etim, 1);
	List<?> ls5=statShiftFLInfoDaoI.queryBySql(sql5);
	this.setPackParamBean(ls5,m,1);
	//牌号和班组
	String sql6=this.getPackerDaySql(stim, etim, 3);
	List<?> ls6=statShiftFLInfoDaoI.queryBySql(sql6);
	this.setPackParamBean(ls6,m,3);
	
	
	//2.对MatterInfo中的9个list处理，配对组合成最后的数据输出
	//2.1对按机台/班组/牌号分组的集合处理配对
	this.handerListData(m.getL1());
	this.handerListData(m.getL4());
	this.handerListData(m.getL7());
	//2.2对按机台/班组分组的list处理配对
	this.handListTeamAndEqpData(m.getL2());
	this.handListTeamAndEqpData(m.getL5());
	this.handListTeamAndEqpData(m.getL8());
	//2.3对按班组/牌号分组的list配对
	this.handListTeamAndMatData(m.getL3());
	this.handListTeamAndMatData(m.getL6());
	this.handListTeamAndMatData(m.getL9());
	return m;
}
/**
 * 组合按班组和牌号分组的数据
 * TODO
 * @param l3
 * TRAVLER
 * 2015年11月30日下午2:43:50
 */
private void handListTeamAndMatData(List<MatterInfo> ls) {
	//规则  牌号相同
	String mat1=null;
	String mat2=null;
	List<MatterInfo> dl=new ArrayList<MatterInfo>();
	List<MatterInfo> fl=new ArrayList<MatterInfo>();
	List<String> mat=new ArrayList<String>();//用于去重复
	MatterInfo b1 =null;
	MatterInfo b2=null;
	for (int i=0;i<ls.size();i++) {
		b1=ls.get(i);
		for (int j=0;j<ls.size();j++) {
			b2=ls.get(j);
			mat1=b1.getMat_name();
			mat2=b2.getMat_name();
			if(mat.contains(mat1)){
				continue;
			}
			if(mat1.equals(mat2) && i!=j){
				try {
					mat.add(mat1);
					BeanConvertor.copyProperties(b1, b2);
					Double cc=this.calcuCC(b1.getQt_cc(), b2.getQt_cc());
					b2.setQt_cc(cc);
					if(b1.getBzQty()!=null&&b1.getBzQty()!=0){
						b2.setCcDH(this.caultDH(b1.getBzQty(), cc));
					}else{
						b2.setCcDH(this.caultDH(b2.getBzQty(), cc));
					}
					dl.add(b1);
					dl.add(b2);
					fl.add(b2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	ls.removeAll(dl);
	ls.addAll(fl);
	//去除重复数据
	
}

/**
 * 处理MatterInfo中的list，按机台和班组分组的数据组合成最终数据
 * TODO
 * @param l2
 * TRAVLER
 * 2015年11月30日下午2:28:12
 */
private void handListTeamAndEqpData(List<MatterInfo> l2) {
	//1.匹配规则是  机台code和班组符合
	String team=null;
	Integer code1=null;
	Integer code2=null;
	List<MatterInfo> l0=new ArrayList<MatterInfo>();//匹配的数据，最后需要移除
	List<MatterInfo> l3=new ArrayList<MatterInfo>();//匹配的最后数据
	if(l2.size()>0){
		team=l2.get(0).getTeam_Name();
	}
	for (MatterInfo b1 : l2) {
		for (MatterInfo b2 : l2) {
			code1=b1.getEqpCode();
			code2=b2.getEqpCode();
			if(code1==(code2-30)){
				try {
					BeanConvertor.copyProperties(b1, b2);
					//设置吹车数据
					Double cc=this.calcuCC(b1.getQt_cc(), b2.getQt_cc());
					b2.setQt_cc(cc);
					b2.setCcDH(this.caultDH(b2.getBzQty(), cc));
					b2.setEqpCode(b1.getEqpCode());
					l3.add(b2);
					l0.add(b1);
					l0.add(b2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	//移除已经匹配的数据
	l2.removeAll(l0);
	//添加匹配后的数据
	l2.addAll(l3);
	//最后需要创建空对象，补全页面显示
	List<MatterInfo> l=new ArrayList<MatterInfo>();
	MatterInfo b0=null;
	for(int i=1;i<13;i++){
		if(i==10){
			continue;
		}
		b0=new MatterInfo();
		b0.setEqpCode(i);
		b0.setTeam_Name(team);
		l.add(b0);
	}
	//对数据进行空数据填充
	Integer c1=null;
	Integer c2=null;
	for (int i = 0; i <l.size(); i++) {
		for (int j = 0; j < l2.size(); j++) {
			c1=l.get(i).getEqpCode();
			c2=l2.get(j).getEqpCode();
			if(c1==c2){
				l.set(i, l2.get(j));
			}
		}
	}
	l2.clear();
	l2.addAll(l);
}

/**
 * 处理MatterInfo中的list，组合成最后数据，吹车单耗是吹车残烟/包装机产量
 * TODO
 * @param m
 * TRAVLER
 * 2015年11月30日下午1:54:26
 */
private void handerListData(List<MatterInfo> ls) {
	//1.处理按班组/机台/牌号分组的数据（组合成卷烟机/包装机一组   其中吹车的数据在这里处理,）
	//匹配code/班组/牌号
	List<MatterInfo> l1=ls;
	List<MatterInfo> l2=new ArrayList<MatterInfo>();//匹配的数据，最后需要移除
	List<MatterInfo> l3=new ArrayList<MatterInfo>();//匹配的最后数据
	Integer code1=null;
	Integer code2=null;
	String mat1=null;
	String mat2=null;
	for (MatterInfo b1 : l1) {
		for (MatterInfo b2 : l1) {
			code1=b1.getEqpCode();
			code2=b2.getEqpCode();
			if(code1!=(code2-30)){
				continue;
			}
			mat1=b1.getMat_name();
			mat2=b2.getMat_name();
			if(mat1.equals(mat2)){
				try {
					BeanConvertor.copyProperties(b1, b2);
					//设置吹车数据
					Double cc=this.calcuCC(b1.getQt_cc(), b2.getQt_cc());
					b2.setQt_cc(cc);
					b2.setCcDH(this.caultDH(b2.getBzQty(), cc));
					b2.setEqpCode(b1.getEqpCode());
					l2.add(b1);
					l2.add(b2);
					l3.add(b2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//同组设备不同牌号的特殊情况，牌号是两者连接
				try {
					BeanConvertor.copyProperties(b1, b2);
					//设置吹车数据
					Double cc=this.calcuCC(b1.getQt_cc(), b2.getQt_cc());
					b2.setQt_cc(cc);
					b2.setCcDH(this.caultDH(b2.getBzQty(), cc));
					b2.setEqpCode(b1.getEqpCode());
					b2.setMat_name(mat1+mat2);
					l2.add(b1);
					l2.add(b2);
					l3.add(b2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	//移除已经匹配的数据
	l1.removeAll(l2);
	//添加匹配的数据
	l1.addAll(l3);
	//处理机组
	for (MatterInfo bean : l1) {
		if(bean.getEqpCode()>30){
			bean.setEqpCode(bean.getEqpCode()-30);
		}
	}
	
	
}

/**
 * 封装包装机数据
 * TODO 分班组到对应的list,对吹车的单耗没有处理，放在数据整合部分处理
 * @param ls4
 * @param m
 * @param type 封装卷烟机数据方式
 * 1.查机台总和：
 *    按机台和班组                                                        1去掉牌号
 * 2.查当天
 *    按班组/机台/牌号
 * 3.查牌号和
 * 	    按牌号/班组						 3.去掉机台
 * TRAVLER
 * 2015年11月30日上午10:42:56
 */
@SuppressWarnings("null")
private void setPackParamBean(List<?> ls, MatterInfo m, int type) {
	if(null!=ls&&ls.size()>0){
		Object[] o=null;
		//按机台/班组/牌号
		List<MatterInfo> l1=m.getL1();//甲
		List<MatterInfo> l2=m.getL4();//乙
		List<MatterInfo> l3=m.getL7();//丙
		//按机台班组
		List<MatterInfo> l4=m.getL2();//甲
		List<MatterInfo> l5=m.getL5();//乙
		List<MatterInfo> l6=m.getL8();//丙
		
		
		//按牌号班组
		List<MatterInfo> l7=m.getL3();//甲
		List<MatterInfo> l8=m.getL6();//乙
		List<MatterInfo> l9=m.getL9();//丙
		
		for (Object obj : ls) {
			o=(Object[]) obj;
			if(type==2){
				this.setParams4(o, l1, l2, l3);
			}else if(type==1){
				this.setParams5(o, l4, l5, l6);
			}else if(type==3){
				this.setParams6(o, l7, l8, l9);
			}
			
		}
	}
	
}
/**
 * 封装包装机数据   按牌号/班组
 * TODO
 * @param o
 * @param l4
 * @param l5
 * @param l6
 * TRAVLER
 * 2015年11月30日上午11:13:44
 */
private void setParams6(Object[] o, List<MatterInfo> l7, List<MatterInfo> l8, List<MatterInfo> l9) {
	
	MatterInfo b=new MatterInfo();
	String team=StringUtil.convertObjToString(o[1]).trim();
	b.setMat_name(StringUtil.convertObjToString(o[0]));
	b.setTeam_Name(team);
	
	b.setBzQty(this.convertToDouble(o[2]));
	b.setQt_bz(this.convertToDouble(o[3]));
	b.setQt_cc(this.convertToDouble(o[4]));
	b.setSy_zb_xhz(this.convertToDouble(o[5]));
	b.setSy_zb_thz(this.convertToDouble(o[6]));
	b.setSy_zb_lbz(this.convertToDouble(o[7]));
	b.setSy_zb_xhm(this.convertToDouble(o[8]));
	b.setSy_zb_thm(this.convertToDouble(o[9]));
	b.setSy_zb_kz(this.convertToDouble(o[10]));
	b.setSy_zb_fq(this.convertToDouble(o[11]));
	b.setSy_zb_lx1(this.convertToDouble(o[12]));
	b.setSy_zb_lx2(this.convertToDouble(o[13]));
	
	
	b.setZbDH(this.caultDH(o[2], o[3]));
	b.setXhzDH(this.caultDH(o[2], o[5]));
	b.setThzDH(this.caultDH(o[2], o[6]));
	b.setLbzDH(this.caultDH(o[2], o[7]));
	b.setXhmDH(this.caultDH(o[2], o[8]));
	b.setThmDH(this.caultDH(o[2], o[9]));
	b.setKzDH(this.caultDH(o[2], o[10]));
	b.setFqDH(this.caultDH(o[2], o[11]));
	b.setLx1DH(this.caultDH(o[2], o[12]));
	b.setLx2DH(this.caultDH(o[2], o[13]));
	
	
	if(team.equals("甲班")){
		l7.add(b);
	}else if(team.equals("乙班")){
		l8.add(b);
	}else if(team.equals("丙班")){
		l9.add(b);
	}
	
	
}

/**
 * 封装包装机数据   按班组/机台
 * TODO
 * @param o
 * @param l4
 * @param l5
 * @param l6
 * TRAVLER
 * 2015年11月30日上午11:13:44
 */
private void setParams5(Object[] o, List<MatterInfo> l4, List<MatterInfo> l5, List<MatterInfo> l6) {
	MatterInfo b=new MatterInfo();
	String team=StringUtil.convertObjToString(o[1]).trim();
	b.setEqpCode(this.getInteger(o[0]));
	b.setTeam_Name(team);
	b.setBzQty(this.convertToDouble(o[2]));
	b.setQt_bz(this.convertToDouble(o[3]));
	b.setQt_cc(this.convertToDouble(o[4]));
	b.setSy_zb_xhz(this.convertToDouble(o[5]));
	b.setSy_zb_thz(this.convertToDouble(o[6]));
	b.setSy_zb_lbz(this.convertToDouble(o[7]));
	b.setSy_zb_xhm(this.convertToDouble(o[8]));
	b.setSy_zb_thm(this.convertToDouble(o[9]));
	b.setSy_zb_kz(this.convertToDouble(o[10]));
	b.setSy_zb_fq(this.convertToDouble(o[11]));
	b.setSy_zb_lx1(this.convertToDouble(o[12]));
	b.setSy_zb_lx2(this.convertToDouble(o[13]));
	
	
	b.setZbDH(this.caultDH(o[2], o[3]));
	b.setXhzDH(this.caultDH(o[2], o[5]));
	b.setThzDH(this.caultDH(o[2], o[6]));
	b.setLbzDH(this.caultDH(o[2], o[7]));
	b.setXhmDH(this.caultDH(o[2], o[8]));
	b.setThmDH(this.caultDH(o[2], o[9]));
	b.setKzDH(this.caultDH(o[2], o[10]));
	b.setFqDH(this.caultDH(o[2], o[11]));
	b.setLx1DH(this.caultDH(o[2], o[12]));
	b.setLx2DH(this.caultDH(o[2], o[13]));
	
	
	if(team.equals("甲班")){
		l4.add(b);
	}else if(team.equals("乙班")){
		l5.add(b);
	}else if(team.equals("丙班")){
		l6.add(b);
	}
	
	
}

/**
 * 
 * TODO        对包装机数据封装，分班组到对应的list,对吹车的单耗没有处理，放在数据整合部分处理
 * @param ls1
 * @param m
 * @param type 封装包装机数据方式
 *
 *    按班组/机台/牌号
 * TRAVLER
 * 2015年11月30日上午9:43:16
 */
private void setParams4(Object[] o, List<MatterInfo> l1, List<MatterInfo> l2, List<MatterInfo> l3) {
	MatterInfo b=new MatterInfo();
	String team=StringUtil.convertObjToString(o[2]).trim();
	b.setEqpCode(this.getInteger(o[0]));
	b.setMat_name(StringUtil.convertObjToString(o[1]));
	b.setTeam_Name(team);
	b.setBzQty(this.convertToDouble(o[3]));
	b.setQt_bz(this.convertToDouble(o[4]));
	b.setQt_cc(this.convertToDouble(o[5]));
	b.setSy_zb_xhz(this.convertToDouble(o[6]));
	b.setSy_zb_thz(this.convertToDouble(o[7]));
	b.setSy_zb_lbz(this.convertToDouble(o[8]));
	b.setSy_zb_xhm(this.convertToDouble(o[9]));
	b.setSy_zb_thm(this.convertToDouble(o[10]));
	b.setSy_zb_kz(this.convertToDouble(o[11]));
	b.setSy_zb_fq(this.convertToDouble(o[12]));
	b.setSy_zb_lx1(this.convertToDouble(o[13]));
	b.setSy_zb_lx2(this.convertToDouble(o[14]));
	
	
	b.setZbDH(this.caultDH(o[3], o[4]));
	b.setXhzDH(this.caultDH(o[3], o[6]));
	b.setThzDH(this.caultDH(o[3], o[7]));
	b.setLbzDH(this.caultDH(o[3], o[8]));
	b.setXhmDH(this.caultDH(o[3], o[9]));
	b.setThmDH(this.caultDH(o[3], o[10]));
	b.setKzDH(this.caultDH(o[3], o[11]));
	b.setFqDH(this.caultDH(o[3], o[12]));
	b.setLx1DH(this.caultDH(o[3], o[13]));
	b.setLx2DH(this.caultDH(o[3], o[14]));
	
	
	if(team.equals("甲班")){
		l1.add(b);
	}else if(team.equals("乙班")){
		l2.add(b);
	}else if(team.equals("丙班")){
		l3.add(b);
	}
	
	
}

/**
 * 
 * TODO        对卷烟机数据封装，分班组到对应的list,对吹车的单耗没有处理，放在数据整合部分处理
 * @param ls1
 * @param m
 * @param type 封装卷烟机数据方式
 * 1.查机台总和：
 *    按机台和班组                                                        1去掉牌号
 * 2.查当天
 *    按班组/机台/牌号
 * 3.查牌号和
 * 	    按牌号/班组						 3.去掉机台
 * TRAVLER
 * 2015年11月30日上午9:43:16
 */
@SuppressWarnings("null")
private void setRollerParamBean(List<?> ls, MatterInfo m,Integer type) {
	if(null!=ls&&ls.size()>0){
		Object[] o=null;
		//按机台/班组/牌号
		List<MatterInfo> l1=m.getL1();//甲
		List<MatterInfo> l2=m.getL4();//乙
		List<MatterInfo> l3=m.getL7();//丙
		//按机台班组
		List<MatterInfo> l4=m.getL2();//甲
		List<MatterInfo> l5=m.getL5();//乙
		List<MatterInfo> l6=m.getL8();//丙
		
		
		//按牌号班组
		List<MatterInfo> l7=m.getL3();//甲
		List<MatterInfo> l8=m.getL6();//乙
		List<MatterInfo> l9=m.getL9();//丙
		
		for (Object obj : ls) {
			o=(Object[]) obj;
			if(type==2&&o.length>0){
				this.setParams1(o, l1, l2, l3);
			}else if(type==1&&o.length>0){
				this.setParams2(o, l4, l5, l6);
			}else if(type==3&&o.length>0){
				this.setParams3(o, l7, l8, l9);
			}
			
		}
		
		
		
	}
}
/**
 * 卷烟机 按牌号封装Bean
 * TODO
 * @param o
 * @param l7
 * @param l8
 * @param l9
 * TRAVLER
 * 2015年11月30日上午10:37:11
 */
private void setParams3(Object[] o, List<MatterInfo> l7, List<MatterInfo> l8, List<MatterInfo> l9) {
	MatterInfo b;
	String team;
	team=StringUtil.convertObjToString(o[1]).trim();
	b=new MatterInfo();
	b.setTeam_Name(team);
	b.setMat_name(StringUtil.convertObjToString(o[0]));
	b.setJuQty(this.convertToDouble(o[2]));
	b.setQt_jy(this.convertToDouble(o[3]));
	b.setQt_cc(this.convertToDouble(o[4]));
	b.setSy_zj_lb(this.convertToDouble(o[5]));
	b.setSy_zj_pz(this.convertToDouble(o[6]));
	b.setSy_zj_ssz(this.convertToDouble(o[7]));
	
	b.setZjDH(this.caultDH(o[2], o[3]));
	b.setLbDH(this.caultDH(o[2], o[5]));
	b.setPzDH(this.caultDH(o[2], o[6]));
	b.setSszDH(this.caultDH(o[2], o[7]));
	if(team.equals("甲班")){
		l7.add(b);
	}else if(team.equals("乙班")){
		l8.add(b);
	}else if(team.equals("丙班")){
		l9.add(b);
	}
}
/**
 * 卷烟机按班组/机台分组封装数据
 * TODO
 * @param o
 * @param l4
 * @param l5
 * @param l6
 * TRAVLER
 * 2015年11月30日上午10:31:01
 */
private void setParams2(Object[] o, List<MatterInfo> l4, List<MatterInfo> l5, List<MatterInfo> l6) {
	MatterInfo b;
	String team;
	team=StringUtil.convertObjToString(o[1]).trim();
	b=new MatterInfo();
	b.setTeam_Name(team);
	b.setEqpCode(this.getInteger(o[0]));
	b.setJuQty(this.convertToDouble(o[2]));
	b.setQt_jy(this.convertToDouble(o[3]));
	b.setQt_cc(this.convertToDouble(o[4]));
	b.setSy_zj_lb(this.convertToDouble(o[5]));
	b.setSy_zj_pz(this.convertToDouble(o[6]));
	b.setSy_zj_ssz(this.convertToDouble(o[7]));
	
	b.setZjDH(this.caultDH(o[2], o[3]));
	b.setLbDH(this.caultDH(o[2], o[5]));
	b.setPzDH(this.caultDH(o[2], o[6]));
	b.setSszDH(this.caultDH(o[2], o[7]));
	if(team.equals("甲班")){
		l4.add(b);
	}else if(team.equals("乙班")){
		l5.add(b);
	}else if(team.equals("丙班")){
		l6.add(b);
	}
}
/**
 * 包装机按班组/牌号/机台封装数据
 * TODO
 * @param o
 * @param l1
 * @param l2
 * @param l3
 * TRAVLER
 * 2015年11月30日上午10:21:05
 */
private void setParams1(Object[] o, List<MatterInfo> l1, List<MatterInfo> l2, List<MatterInfo> l3) {
	MatterInfo b;
	String team;
	team=StringUtil.convertObjToString(o[2]).trim();
	b=new MatterInfo();
	b.setEqpCode(this.getInteger(o[0]));
	b.setMat_name(StringUtil.convertObjToString(o[1]));
	b.setTeam_Name(StringUtil.convertObjToString(o[2]));
	b.setJuQty(this.convertToDouble(o[3]));
	b.setQt_jy(this.convertToDouble(o[4]));
	b.setQt_cc(this.convertToDouble(o[5]));
	b.setSy_zj_lb(this.convertToDouble(o[6]));
	b.setSy_zj_pz(this.convertToDouble(o[7]));
	b.setSy_zj_ssz(this.convertToDouble(o[8]));
	
	b.setZjDH(this.caultDH(o[3], o[4]));
	b.setLbDH(this.caultDH(o[3], o[6]));
	b.setPzDH(this.caultDH(o[3], o[7]));
	b.setSszDH(this.caultDH(o[3], o[8]));
	if(team.equals("甲班")){
		l1.add(b);
		
	}else if(team.equals("乙班")){
		l2.add(b);
		
	}else if(team.equals("丙班")){
		
		l3.add(b);
	}
}
/**
 * 计算单耗
 * TODO
 * @param qty
 * @param p
 * @return
 * TRAVLER
 * 2015年11月30日上午9:55:17
 */
private Double caultDH(Object qty,Object p){
	Double r=0.0;
	if(null!=qty&&p!=null){
		Double q1=Double.valueOf(qty.toString());
		Double q2=Double.valueOf(p.toString());
		if(q1!=0){
			r=q2/q1;
		}
	}
	return MathUtil.roundHalfUp(r, 2);
}
/**
 * PMS导出工段日报表
 * * TODO
 * @param qty
 * @param p
 * @return
 * TRAVLER
 * 2015年12月01日上午7:55:17
 */
@Override
public HSSFWorkbook exportDayQtyData(String stim, String etime) {
	HSSFWorkbook workBook = null;
	MatterInfo bean=this.queryDayQtyData(stim, etime);
	ExportExcel ee = new ExportExcel();
	workBook = new HSSFWorkbook();
	HSSFSheet sheet = workBook.createSheet();
	workBook.setSheetName(0, "temp1", HSSFWorkbook.ENCODING_UTF_16);
	sheet.setVerticallyCenter(true);
	sheet.setDefaultColumnWidth((short) 10);
	sheet.setDefaultRowHeight((short)15);
	HSSFCellStyle tableStyle = ee.getTableStyle(workBook,true);
	HSSFCellStyle tdStyle = ee.getTableStyle(workBook,false);
	tdStyle.setWrapText(false);
	try {
		// th 当前开始行,当前结束行,  从1开始计算行  一共多少列 从0开始计算总列数
		int[] thTables = {1,3,35};
		List<String> th = new ArrayList<String>();
		int sb1=1;
		int eb1=3;
		String[] method = exportTab1(th,sb1,eb1);
		//第一个表格
		int startLine=3;
		int b2=bean.getL1().size();
		th.add("1,1,2,1,日期:");
		th.add("1,3,5,1,"+stim+" 至  "+etime);
		ee.exportExcelMoreTh(thTables, th, startLine, method,MatterInfo.class,bean.getL1(), sheet, tableStyle, tdStyle,null,null,startLine,startLine+b2-1,0);
		//第二个表格
		int sb2=b2+eb1+1;//开始行15
		int eb2=sb2+1;//结束行16
		int[] tab2={sb2,eb2,35};
		th.clear();
		String[] method2 = exportTab2(th, sb2, eb2);
		ee.exportExcelMoreTh(tab2, th, eb2, method2,MatterInfo.class,bean.getL2(), sheet, tableStyle, tdStyle,2,3,eb2,eb2+bean.getL2().size()-1,0);
		//第三个表格
		int sb3=eb2+12;//开始行28
		int eb3=eb2+12;//结束行28
		int[] tab3={sb3,eb3,35};
		th.clear();
		String[] method3 = exportTab3(th, sb3, eb3);
		ee.exportExcelMoreTh(tab3, th, eb3, method3,MatterInfo.class,bean.getL3(), sheet, tableStyle, tdStyle,2,3,eb3,eb3+(bean.getL3().size()-1),0);
		//第四个表格
		int sb4=eb3+bean.getL3().size()+1+2;//班组之间空两行
		int eb4=sb4+2;
		int[] tab4={sb4,eb4,35};
		th.clear();
		String[] method4 = exportTab1(th, sb4, eb4);
		ee.exportExcelMoreTh(tab4, th, eb4, method4,MatterInfo.class,bean.getL4(), sheet, tableStyle, tdStyle,null,null,eb4,eb4+bean.getL4().size()-1,0);
		//第五个表格
		int sb5=eb4+bean.getL4().size()+1;
		int eb5=sb5+1;
		int[] tab5={sb5,eb5,35};
		th.clear();
		String[] method5 = exportTab2(th, sb5, eb5);
		ee.exportExcelMoreTh(tab5, th, eb5, method5,MatterInfo.class,bean.getL5(), sheet, tableStyle, tdStyle,2,3,eb5,eb5+bean.getL5().size()-1,0);
		//第六个表格
		int sb6=eb5+12;//开始行28
		int eb6=eb5+12;//结束行28
		int[] tab6={sb6,eb6,35};
		th.clear();
		String[] method6 = exportTab3(th, sb6, eb6);
		ee.exportExcelMoreTh(tab6, th, eb6, method6,MatterInfo.class,bean.getL6(), sheet, tableStyle, tdStyle,2,3,eb6,eb6+bean.getL6().size()-1,0);
	
		//第七个表格
		int sb7=eb6+bean.getL6().size()+1+2;//班组之间空两行
		int eb7=sb7+2;
		int[] tab7={sb7,eb7,35};
		th.clear();
		String[] method7 = exportTab1(th, sb7, eb7);
		ee.exportExcelMoreTh(tab7, th, eb7, method7,MatterInfo.class,bean.getL7(), sheet, tableStyle, tdStyle,null,null,eb7,eb7+bean.getL7().size()-1,0);
		//第八个表格
		int sb8=eb7+bean.getL7().size()+1;
		int eb8=sb8+1;
		int[] tab8={sb8,eb8,35};
		th.clear();
		String[] method8 = exportTab2(th, sb8, eb8);
		ee.exportExcelMoreTh(tab8, th, eb8, method8,MatterInfo.class,bean.getL8(), sheet, tableStyle, tdStyle,2,3,eb8,eb8+bean.getL8().size()-1,0);
		//第九个表格
		int sb9=eb8+12;//开始行28
		int eb9=eb8+12;//结束行28
		int[] tab9={sb9,eb9,35};
		th.clear();
		String[] method9 = exportTab3(th, sb9, eb9);
		ee.exportExcelMoreTh(tab9, th, eb9, method9,MatterInfo.class,bean.getL9(), sheet, tableStyle, tdStyle,2,3,eb9,eb9+bean.getL9().size()-1,0);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return workBook;
}
/**
 * 工段日报第三个table导出格式
 * TODO
 * @param th
 * @param sb3
 * @param eb3
 * @return
 * TRAVLER
 * 2015年12月1日下午1:44:42
 */
private String[] exportTab3(List<String> th, int sb3, int eb3) {
	th.add(sb3+",1,1,"+eb3+", ");
	th.add(sb3+",2,3,"+eb3+", ");
	th.add(sb3+",4,5,"+eb3+", ");
	th.add(sb3+",4,4,"+eb3+", ");
	th.add(sb3+",5,5,"+eb3+", ");
	th.add(sb3+",6,8,"+eb3+", ");
	th.add(sb3+",6,6,"+eb3+", ");
	th.add(sb3+",7,7,"+eb3+", ");
	th.add(sb3+",8,8,"+eb3+", ");
	th.add(sb3+",9,11,"+eb3+", ");
	th.add(sb3+",9,9,"+eb3+", ");
	th.add(sb3+",10,10,"+eb3+", ");
	th.add(sb3+",11,11,"+eb3+", ");
	th.add(sb3+",12,20,"+eb3+", ");
	th.add(sb3+",12,12,"+eb3+", ");
	th.add(sb3+",13,13,"+eb3+", ");
	th.add(sb3+",14,14,"+eb3+", ");
	th.add(sb3+",15,15,"+eb3+", ");
	th.add(sb3+",16,16,"+eb3+", ");
	th.add(sb3+",17,17,"+eb3+", ");
	th.add(sb3+",18,18,"+eb3+", ");
	th.add(sb3+",19,19,"+eb3+", ");
	th.add(sb3+",20,20,"+eb3+", ");
	th.add(sb3+",21,23,"+eb3+", ");
	th.add(sb3+",21,21,"+eb3+", ");
	th.add(sb3+",22,22,"+eb3+", ");
	th.add(sb3+",23,23,"+eb3+", ");
	th.add(sb3+",24,26,"+eb3+", ");
	th.add(sb3+",24,24,"+eb3+", ");
	th.add(sb3+",25,25,"+eb3+", ");
	th.add(sb3+",26,26,"+eb3+", ");
	th.add(sb3+",27,35,"+eb3+", ");
	th.add(sb3+",27,27,"+eb3+", ");
	th.add(sb3+",28,28,"+eb3+", ");
	th.add(sb3+",29,29,"+eb3+", ");
	th.add(sb3+",30,30,"+eb3+", ");
	th.add(sb3+",31,31,"+eb3+", ");
	th.add(sb3+",32,32,"+eb3+", ");
	th.add(sb3+",33,33,"+eb3+", ");
	th.add(sb3+",34,34,"+eb3+", ");
	th.add(sb3+",35,35,"+eb3+", ");
	String[] method3={
			"getTeam_Name","getMat_name",
			"getJuQty","getJuQty" ,"getBzQty","getQt_jy","getQt_bz","getQt_cc",
			"getSy_zj_lb","getSy_zj_pz","getSy_zj_ssz",
			"getSy_zb_xhz","getSy_zb_thz","getSy_zb_lbz","getSy_zb_xhm","getSy_zb_thm","getSy_zb_kz","getSy_zb_fq","getSy_zb_lx1","getSy_zb_lx2",
			"getZjDH","getZbDH","getCcDH",
			"getLbDH","getPzDH","getSszDH",
			"getXhzDH","getThzDH","getLbzDH","getXhmDH","getThmDH","getKzDH","getFqDH","getLx1DH","getLx2DH",
	};
	return method3;
}
/**
 * 工段信息第二个table导出格式
 * TODO
 * @param th
 * @param sb2
 * @param eb2
 * @return
 * TRAVLER
 * 2015年12月1日下午1:43:45
 */
private String[] exportTab2(List<String> th, int sb2, int eb2) {
	th.add(sb2+",1,1,"+eb2+",班组");
	th.add(sb2+",2,3,"+eb2+",实开机组");
	th.add(sb2+",4,5,"+(eb2-1)+",产量");
	th.add(sb2+1+",4,4,"+eb2+",卷烟");
	th.add(sb2+1+",5,5,"+eb2+",包装");
	th.add(sb2+",6,8,"+(eb2-1)+",残烟量");
	th.add(sb2+1+",6,6,"+eb2+",卷烟");
	th.add(sb2+1+",7,7,"+eb2+",包装");
	th.add(sb2+1+",8,8,"+eb2+",吹车");
	th.add(sb2+",9,11,"+(eb2-1)+",卷烟机用料");
	th.add(sb2+1+",9,9,"+eb2+",滤棒");
	th.add(sb2+1+",10,10,"+eb2+",卷烟纸");
	th.add(sb2+1+",11,11,"+eb2+",水松纸");
	th.add(sb2+",12,20,"+(eb2-1)+",包装机用料");
	th.add(sb2+1+",12,12,"+eb2+",商标纸");
	th.add(sb2+1+",13,13,"+eb2+",条盒纸");
	th.add(sb2+1+",14,14,"+eb2+",铝箔纸");
	th.add(sb2+1+",15,15,"+eb2+",小透明");
	th.add(sb2+1+",16,16,"+eb2+",条透明");
	th.add(sb2+1+",17,17,"+eb2+",卡纸");
	th.add(sb2+1+",18,18,"+eb2+",封签");
	th.add(sb2+1+",19,19,"+eb2+",拉线1");
	th.add(sb2+1+",20,20,"+eb2+",拉线2");
	th.add(sb2+",21,23,"+(eb2-1)+",单箱残烟量");
	th.add(sb2+1+",21,21,"+eb2+",卷烟");
	th.add(sb2+1+",22,22,"+eb2+",包装");
	th.add(sb2+1+",23,23,"+eb2+",吹车");
	th.add(sb2+",24,26,"+(eb2-1)+",卷烟机单箱用料");
	th.add(sb2+1+",24,24,"+eb2+",滤棒");
	th.add(sb2+1+",25,25,"+eb2+",卷烟纸");
	th.add(sb2+1+",26,26,"+eb2+",水松纸");
	th.add(sb2+",27,35,"+(eb2-1)+",包装机单箱用料");
	th.add(sb2+1+",27,27,"+eb2+",商标纸");
	th.add(sb2+1+",28,28,"+eb2+",条盒纸");
	th.add(sb2+1+",29,29,"+eb2+",铝箔纸");
	th.add(sb2+1+",30,30,"+eb2+",小透明");
	th.add(sb2+1+",31,31,"+eb2+",条透明");
	th.add(sb2+1+",32,32,"+eb2+",卡纸");
	th.add(sb2+1+",33,33,"+eb2+",封签");
	th.add(sb2+1+",34,34,"+eb2+",拉线1");
	th.add(sb2+1+",35,35,"+eb2+",拉线2");
	String[] method2={
			"getTeam_Name","getEqpCode",
			"getJuQty","getJuQty" ,"getBzQty","getQt_jy","getQt_bz","getQt_cc",
			"getSy_zj_lb","getSy_zj_pz","getSy_zj_ssz",
			"getSy_zb_xhz","getSy_zb_thz","getSy_zb_lbz","getSy_zb_xhm","getSy_zb_thm","getSy_zb_kz","getSy_zb_fq","getSy_zb_lx1","getSy_zb_lx2",
			"getZjDH","getZbDH","getCcDH",
			"getLbDH","getPzDH","getSszDH",
			"getXhzDH","getThzDH","getLbzDH","getXhmDH","getThmDH","getKzDH","getFqDH","getLx1DH","getLx2DH",
	};
	return method2;
}
/**
 * 工段信息第一个table导出格式
 * TODO
 * @param th
 * @return
 * TRAVLER
 * 2015年12月1日下午1:42:12
 */
private String[] exportTab1(List<String> th,int sb1,int eb1) {//1,3
	th.add(sb1+",6,20,"+(eb1-2)+",用料");//开始行 开始列  结束列  结束行
	th.add(sb1+",21,35,"+(eb1-2)+",单耗");
	th.add((sb1+1)+",1,1,"+eb1+",工段");
	th.add((sb1+1)+",2,2,"+eb1+",实开机组");
	th.add((sb1+1)+",3,3,"+eb1+",牌号");
	th.add((sb1+1)+",4,5,"+(eb1-1)+",产量");
	th.add((sb1+2)+",4,4,"+eb1+",包装");
	th.add((sb1+2)+",5,5,"+eb1+",卷烟");
	th.add((sb1+1)+",6,8,"+(eb1-1)+",残烟量");
	th.add((sb1+2)+",6,6,"+eb1+",卷烟");
	th.add((sb1+2)+",7,7,"+eb1+",包装");
	th.add((sb1+2)+",8,8,"+eb1+",吹车");
	th.add((sb1+1)+",9,11,"+(eb1-1)+",卷烟机用料");
	th.add((sb1+2)+",9,9,"+eb1+",滤棒");
	th.add((sb1+2)+",10,10,"+eb1+",卷烟纸");
	th.add((sb1+2)+",11,11,"+eb1+",水松纸");
	th.add((sb1+1)+",12,20,"+(eb1-1)+",包装机用料");
	th.add((sb1+2)+",12,12,"+eb1+",商标纸");
	th.add((sb1+2)+",13,13,"+eb1+",条盒");
	th.add((sb1+2)+",14,14,"+eb1+",铝箔纸");
	th.add((sb1+2)+",15,15,"+eb1+",小透明");
	th.add((sb1+2)+",16,16,"+eb1+",条透明");
	th.add((sb1+2)+",17,17,"+eb1+",卡纸");
	th.add((sb1+2)+",18,18,"+eb1+",封签");
	th.add((sb1+2)+",19,19,"+eb1+",拉线1");
	th.add((sb1+2)+",20,20,"+eb1+",拉线2");
	th.add((sb1+1)+",21,23,"+(eb1-1)+",单箱残烟量");
	th.add((sb1+2)+",21,21,"+eb1+",卷烟");
	th.add((sb1+2)+",22,22,"+eb1+",包装");
	th.add((sb1+2)+",23,23,"+eb1+",吹车");
	th.add((sb1+1)+",24,26,"+(eb1-1)+",卷烟机单箱用料");
	th.add((sb1+2)+",24,24,"+eb1+",滤棒");
	th.add((sb1+2)+",25,25,"+eb1+",卷烟纸");
	th.add((sb1+2)+",26,26,"+eb1+",水松纸");
	th.add((sb1+1)+",27,35,"+(eb1-1)+",包装机单箱用料");
	th.add((sb1+2)+",27,27,"+eb1+",商标纸");
	th.add((sb1+2)+",28,28,"+eb1+",条盒纸");
	th.add((sb1+2)+",29,29,"+eb1+",铝箔纸");
	th.add((sb1+2)+",30,30,"+eb1+",小透明");
	th.add((sb1+2)+",31,31,"+eb1+",条透明");
	th.add((sb1+2)+",32,32,"+eb1+",卡纸");
	th.add((sb1+2)+",33,33,"+eb1+",封签");
	th.add((sb1+2)+",34,34,"+eb1+",拉线1");
	th.add((sb1+2)+",35,35,"+eb1+",拉线2");
	//方法集合，同表头一致
	String[] method=new String[]{"getTeam_Name","getEqpCode","getMat_name",
								"getJuQty" ,"getBzQty","getQt_jy","getQt_bz","getQt_cc",
								 "getSy_zj_lb","getSy_zj_pz","getSy_zj_ssz",
								 "getSy_zb_xhz","getSy_zb_thz","getSy_zb_lbz","getSy_zb_xhm","getSy_zb_thm","getSy_zb_kz","getSy_zb_fq","getSy_zb_lx1","getSy_zb_lx2",
								 "getZjDH","getZbDH","getCcDH",
								 "getLbDH","getPzDH","getSszDH",
								 "getXhzDH","getThzDH","getLbzDH","getXhmDH","getThmDH","getKzDH","getFqDH","getLx1DH","getLx2DH",
	};
	return method;
}

@Override
public HSSFWorkbook exportMonthQtyData(String stim, String etime) {
	HSSFWorkbook workBook = null;
	ExportExcel ee = new ExportExcel();
	workBook = new HSSFWorkbook();
	HSSFSheet sheet = workBook.createSheet();
	workBook.setSheetName(0, "temp1", HSSFWorkbook.ENCODING_UTF_16);
	sheet.setVerticallyCenter(true);
	sheet.setDefaultColumnWidth((short) 10);
	sheet.setDefaultRowHeight((short)15);
	HSSFCellStyle tableStyle = ee.getTableStyle(workBook,true);
	HSSFCellStyle tdStyle = ee.getTableStyle(workBook,false);
	tdStyle.setWrapText(false);
	try {
		// th 当前开始行,当前结束行,一共多少列
		int[] thTables = { 1, 3, 37 };
		List<String> th = new ArrayList<String>();
		// 第1行;第1列开始 ~ 第59列结束  第一行结束

		th.add("1,1,1,3,牌号");
		th.add("1,2,2,3,工段");// 第2行;第1列开始 ~ 第1列结束  第4行结束
		th.add("1,3,3,3,定编机组");
		th.add("1,4,5,2,产量");
		th.add("3,4,4,3,卷烟");
		th.add("3,5,5,3,包装");
		
		th.add("1,6,21,1,用料");
		th.add("2,6,8,2,残烟量");
		th.add("3,6,6,3,卷烟");
		th.add("3,7,7,3,包装");
		th.add("3,8,8,3,吹车");
		
		th.add("2,9,11,2,卷烟机");
		th.add("3,9,9,3,滤棒");
		th.add("3,10,10,3,卷烟纸");
		th.add("3,11,11,3,水松纸");
		
		th.add("2,12,21,2,包装机");
		th.add("3,12,12,3,商标纸");
		th.add("3,13,13,3,条盒纸");
		th.add("3,14,14,3,铝箔纸");
		th.add("3,15,15,3,小透明");
		th.add("3,16,16,3,条透明");
		th.add("3,17,17,3,卡纸");
		th.add("3,18,18,3,封签");
		th.add("3,19,19,3,拉线1");
		th.add("3,20,20,3,拉线2");
		th.add("3,21,21,3,备用");
		//单耗
		th.add("1,22,37,1,单耗");
		th.add("2,22,24,2,残烟量");
		th.add("3,22,22,3,卷烟");
		th.add("3,23,23,3,包装");
		th.add("3,24,24,3,吹车");
		
		th.add("2,25,27,2,卷烟机");
		th.add("3,25,25,3,滤棒");
		th.add("3,26,26,3,卷烟纸");
		th.add("3,27,27,3,水松纸");
		
		th.add("2,28,37,2,包装机");
		th.add("3,28,28,3,商标纸");
		th.add("3,29,29,3,条盒纸");
		th.add("3,30,30,3,铝箔纸");
		th.add("3,31,31,3,小透明");
		th.add("3,32,32,3,条透明");
		th.add("3,33,33,3,卡纸");
		th.add("3,34,34,3,封签");
		th.add("3,35,35,3,拉线1");
		th.add("3,36,36,3,拉线2");
		th.add("3,37,37,3,备用");
		//获取List集合
		DataGrid dg = this.queryMonthQtyData(stim, etime);
		//方法集合，同表头一致getEqpCode是备用列，实际 什么都没有
		String[] method=new String[]{"getMat_name","getTeam_Name","getUserEqpGroup",
									 "getJuQty" ,"getBzQty","getQt_jy","getQt_bz","getQt_cc",
									 "getSy_zj_lb","getSy_zj_pz","getSy_zj_ssz",
									 "getSy_zb_xhz","getSy_zb_thz","getSy_zb_lbz","getSy_zb_xhm","getSy_zb_thm","getSy_zb_kz","getSy_zb_fq","getSy_zb_lx1","getSy_zb_lx2","getEqpCode",
									 "getZjDH","getZbDH","getCcDH",
									 "getLbDH","getPzDH","getSszDH",
									 "getXhzDH","getThzDH","getLbzDH","getXhmDH","getThmDH","getKzDH","getFqDH","getLx1DH","getLx2DH","getEqpCode"
				};
		//开始行,0开始
		int startLine=3;  
		ee.exportExcelMoreTh(thTables, th, startLine, method, MatterInfo.class, dg.getRows(), sheet, tableStyle, tdStyle, null, null, null, null, null);
	} catch (Exception e) {
		e.printStackTrace();
	}

	return workBook;
	
}

/**
 * 获取相邻的工单  上下工单必须是已经完成状态
 *	条件是同一个机台和班次
 *  TODO
 *  @param type 1 表示下一个工单， 2表示上一个工单
 *  @return
 *  TRAVLER
 *2015年12月15日下午3:20:03
 */
private String getRoundOrder(int type,String oid,String eqpId){
	//查询上一班次的工单id
	String attr1="a";
	String attr2="b";
	String attr3="-";
	if(type==1){
		attr1="b";
		attr2="a";
		attr3="";
	}
	StringBuffer sb=new StringBuffer();
	sb.append("SELECT id from SCH_WORKORDER where sts=4 and STIM=(");
	sb.append(" SELECT DATEADD(HOUR,");
	sb.append(attr3);
	sb.append("(SELECT min(tab0.date2) from (");
	sb.append(" SELECT DATEDIFF(HOUR,"+attr1+".STIM, "+attr2+".STIM) as date2 from SCH_WORKORDER a LEFT JOIN SCH_WORKORDER b ON a.EQP=b.EQP ");
	sb.append(" WHERE b.id='"+oid+"' AND  a.MAT=b.MAT ");
	sb.append(" ) tab0 WHERE tab0.date2>0), ");
	sb.append(" STIM) from SCH_WORKORDER where id='"+oid+"'");
	sb.append(" )");
	sb.append(" AND EQP='"+eqpId+"'");
	List<?> ls=statShiftFLInfoDaoI.queryBySql(sb.toString());
	if(null!=ls && ls.size()>0){
		String oid2=ls.get(0).toString();
		return oid2;
	}
	return null;
}

@Override
public DataGrid queryShiftCheck(ShiftCheckBean shiftcheckbean, PageParams pageParams) {
	String sql="";
	String sqltotal="";
	String shiftid="";
	String teamid="";
	String date="";
	String date2="";
	String type="";
	if(StringUtil.notNull(shiftcheckbean.getDate())){
		date = " and  convert( varchar(10),create_time,23) >='"+shiftcheckbean.getDate()+"'";
	}
	if(StringUtil.notNull(shiftcheckbean.getDate2())){
		date2 = " and convert( varchar(10),create_time,23) <='"+shiftcheckbean.getDate2()+"'";
	}
	if(StringUtil.notNull(shiftcheckbean.getShiftId())){
		shiftid = " and shift_id='"+shiftcheckbean.getShiftId()+"'";
	}
	if(StringUtil.notNull(shiftcheckbean.getTeamId())){
		teamid = " and team_id='"+shiftcheckbean.getTeamId()+"'";
	}
	if(StringUtil.notNull(shiftcheckbean.getEqptype())){
		type = " and type='"+shiftcheckbean.getEqptype()+"'"; 
	}
	String column="(SELECT ROW_NUMBER () OVER (ORDER BY PLAN_START_TIME DESC) AS rnum,ACTUAL_START_TIME,(select EQUIPMENT_NAME from MD_EQUIPMENT where MES_EQPCODE=device_id) as equipment_name,lot_id,(select code from SCH_WORKORDER WHERE  del=0 and code=entry_id) as workorderid,(select name from md_mat where code=out_material_id) as name,(select qty From SCH_STAT_OUTPUT where oid=(select id from SCH_WORKORDER where 1=1 "+type+" and code=entry_id and del=0  ) ) as qty,shift_name,team_name,create_time FROM QM_CHANGSHIFT where 1=1 "+shiftid+teamid+date+date2+" ) t";
	int startnum=1;
	int endnum=1;
	if(pageParams.getPage()==1){
		startnum=1;
		endnum=pageParams.getRows();
	}else{
		startnum=pageParams.getRows()*(pageParams.getPage()-1)+1;
		endnum=pageParams.getPage()*pageParams.getRows();
	}
	sql="select * from "+column+" where 1=1 and  t.rnum BETWEEN "+startnum+" and "+endnum;
	List<?> shiftcheckbeans=basedao.queryBySql(sql);
	sqltotal="select count(*) from "+column;
	List<?> tl=basedao.queryBySql(sqltotal);
	String t=tl.get(0).toString();
	long total=Long.parseLong(t);
	List<ShiftCheckBean> shiftbeans=new ArrayList<ShiftCheckBean>();
	for(Object b:shiftcheckbeans){
		Object[] temp=(Object[])b;
		ShiftCheckBean shiftckbean=new ShiftCheckBean();
		try{
			shiftckbean.setId(StringUtil.convertObjToString(temp[1]));
			shiftckbean.setEqpname(StringUtil.convertObjToString(temp[2]));
			shiftckbean.setLotid(StringUtil.convertObjToString(temp[3]));
			shiftckbean.setEntryid(StringUtil.convertObjToString(temp[4]));
			shiftckbean.setOutmaterial(StringUtil.convertObjToString(temp[5]));
			if(StringUtil.convertObjToString(temp[6])=="" || "".equals(StringUtil.convertObjToString(temp[6]))){
				shiftckbean.setPlanqty(0d);
			}else{
				shiftckbean.setPlanqty(Double.parseDouble(StringUtil.convertObjToString(temp[6])));
			}
			
			shiftckbean.setShiftname(StringUtil.convertObjToString(temp[7]));
			shiftckbean.setTeamname(StringUtil.convertObjToString(temp[8]));
			shiftckbean.setCreatetime(StringUtil.convertObjToString(temp[9]));
		}catch(Exception e){
			e.printStackTrace();
		}
		shiftbeans.add(shiftckbean);	
	}
	return new DataGrid(shiftbeans,total);
}

@Override
public DataGrid queryShiftCheckInfo(String id) {
	String sql="select qc.MATERIAL_CLASS,qc.material_name,qc.qty,mu.name,qc.type from QM_CHANGSHIFT_INFO qc,MD_UNIT mu where 1=1 and qc.uom=mu.mes_code and (qc.type='1' or qc.type='2' or qc.type='3') and qc.qm_csid='"+id+"'";
	List<?> unknowsObjs=basedao.queryBySql(sql);
	List<QmChangeShiftInfo> qmchangshiftinfos=new ArrayList<QmChangeShiftInfo>();
	for(Object b:unknowsObjs){
		Object[] temp=(Object[])b;
		QmChangeShiftInfo qmchangshiftinfo=new QmChangeShiftInfo();
		try{
			qmchangshiftinfo.setMaterialClass(StringUtil.convertObjToString(temp[0]));
			qmchangshiftinfo.setMaterialName(StringUtil.convertObjToString(temp[1]));
			qmchangshiftinfo.setQty(Float.parseFloat(temp[2]!=null?StringUtil.convertObjToString(temp[2]):""));
			qmchangshiftinfo.setUom(StringUtil.convertObjToString(temp[3]));
			qmchangshiftinfo.setType(StringUtil.convertObjToString(temp[4]!=null?StringUtil.convertObjToString(temp[4]):""));
		}catch(Exception e){
			e.printStackTrace();
		}
		qmchangshiftinfos.add(qmchangshiftinfo);
	}
	return new DataGrid(qmchangshiftinfos,0L);
}

@Override
public boolean addchangshiftinfo(QmChangeShiftInfo qmchangshiftinfo) {
	boolean flag=false;
	try{
		qmchangshiftinfodao.save(qmchangshiftinfo);
		flag=true;
	}catch(Exception e){
		e.printStackTrace();
	}
	return flag;
}

}
