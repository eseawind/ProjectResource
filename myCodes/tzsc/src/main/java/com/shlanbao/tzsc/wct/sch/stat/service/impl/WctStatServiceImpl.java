package com.shlanbao.tzsc.wct.sch.stat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.dao.MdUnitDaoI;
import com.shlanbao.tzsc.base.dao.SchStatInputDaoI;
import com.shlanbao.tzsc.base.dao.SchStatOutputDaoI;
import com.shlanbao.tzsc.base.dao.SchWorkorderDaoI;
import com.shlanbao.tzsc.base.mapping.SchStatInput;
import com.shlanbao.tzsc.base.mapping.SchStatOutput;
import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageView;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.data.runtime.handler.NeedData;
import com.shlanbao.tzsc.pms.sch.statjbcj.beans.MatterInfo;
import com.shlanbao.tzsc.utils.params.EquipmentTypeDefinition;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.GetValueUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.isp.boxer.service.BoxerIspServiceI;
import com.shlanbao.tzsc.wct.isp.filter.beans.FilterData;
import com.shlanbao.tzsc.wct.isp.filter.service.FilterIspServiceI;
import com.shlanbao.tzsc.wct.isp.packer.beans.PackerData;
import com.shlanbao.tzsc.wct.isp.packer.service.PackerIspServiceI;
import com.shlanbao.tzsc.wct.isp.roller.beans.RollerData;
import com.shlanbao.tzsc.wct.isp.roller.service.RollerIspServiceI;
import com.shlanbao.tzsc.wct.sch.stat.beans.EqpRuntime;
import com.shlanbao.tzsc.wct.sch.stat.beans.HisOutBean;
import com.shlanbao.tzsc.wct.sch.stat.beans.InputBean;
import com.shlanbao.tzsc.wct.sch.stat.beans.InputPageModel;
import com.shlanbao.tzsc.wct.sch.stat.beans.OutputBean;
import com.shlanbao.tzsc.wct.sch.stat.service.WctStatServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class WctStatServiceImpl extends BaseService implements WctStatServiceI {
	@Autowired
	private SchStatInputDaoI schStatInputDao;
	@Autowired
	private SchStatOutputDaoI schStatOutputDao;
	@Autowired
	private MdUnitDaoI mdUnitDao;
	@Autowired
	private SchWorkorderDaoI schWorkorderDao;
	@Autowired
	private RollerIspServiceI rollerIspService;
	@Autowired
	private PackerIspServiceI packerIspService;
	@Autowired
	private BoxerIspServiceI boxerIspService;
	@Autowired
	private FilterIspServiceI filterIspService;
	@Override
	public PageView<OutputBean> getAllOutputs(String equipmentCode,
			int curpage, int pagesize,String shiftId,String teamId,String date1,String date2) {
		// equipmentCode = "32";//设备号 测试2号包装机用
		StringBuffer hql =new StringBuffer( "from SchStatOutput o "
				+ "left join fetch o.schWorkorder sw "
				+ "left join fetch sw.mdEquipment me "
				+ "left join fetch sw.mdMat mm "
				+ "left join fetch sw.mdTeam mt "
				+ "left join fetch sw.mdShift ms "
				+ "left join fetch o.mdUnit mu "
				+ "left join fetch o.timeUnit tu "
				+ "where o.del='0' and me.equipmentCode='" + equipmentCode
				+ "'");
		if(StringUtil.notNull(shiftId)){
			hql.append(" and ms.id='"+shiftId+"'");
		}
		if(StringUtil.notNull(teamId)){
			hql.append(" and mt.id='"+teamId+"'");
		}
		if(StringUtil.notNull(date1)){
			hql.append(" and sw.date >='"+date1+"'");
		}
		if(StringUtil.notNull(date2)){
			hql.append(" and sw.date <='"+date2+"'");
		}
		String orderByTemp = " order by sw.date desc ";
		List<SchStatOutput> rows = schStatOutputDao.queryByPage(
				(hql + orderByTemp), curpage, pagesize);

		List<OutputBean> outputBeans = new ArrayList<OutputBean>();

		OutputBean bean = null;
		try {

			for (SchStatOutput output : rows) {

				bean = BeanConvertor.copyProperties(output, OutputBean.class);

				SchWorkorder workorder = output.getSchWorkorder();

				bean.setShift(workorder.getMdShift().getName());

				bean.setTeam(workorder.getMdTeam().getName());

				bean.setDate(DateUtil.formatDateToString(workorder.getDate(),
						"yyyy-MM-dd"));

				// bean.setEquipment(workorder.getMdEquipment().getEquipmentName());

				bean.setWorkorder(workorder.getCode());

				bean.setMat(workorder.getMdMat().getName());

				bean.setUnit(output.getMdUnit().getName());

				// bean.setUnit(output.getTimeUnit().getName());

				outputBeans.add(bean);

				bean = null;
			}

			hql =new StringBuffer( "select count(o) ".concat(hql.toString().replace("fetch", "")));

			long total = schStatOutputDao.queryTotal(hql.toString());

			return new PageView<OutputBean>(outputBeans, (int) total, pagesize,
					curpage);

		} catch (Exception e) {

			log.error("POVO转换异常", e);

		}

		return null;
	}

	@Override
	public List<InputBean> getAllInputsByOutput(String id) throws Exception {
		List<SchStatInput> list = schStatInputDao
				.query("from SchStatInput o left join fetch o.mdMat mm left join fetch o.mdUnit mu where o.schStatOutput.id=?",
						id);

		List<InputBean> rows = new ArrayList<InputBean>();

		for (SchStatInput schStatInput : list) {
			InputBean bean = new InputBean();
			bean.setQty(schStatInput.getQty());
			bean.setOrignalData(schStatInput.getQty());
			bean.setMat(schStatInput.getMdMat().getName());
			bean.setUnit(schStatInput.getMdUnit().getName());
			rows.add(bean);
		}

		return rows;
	}

	//生产车间工单设备名称，生产车间工单日期，生产车间班次名称，生产车间班组名称，工单计划产量，实际产量，剔除量，单位名称，
	@Override
	public HisOutBean getHisOutData(HisOutBean hisOutBean, int pageIndex,
			int pageSize) {
		String hql = "select " + "o.schWorkorder.mdEquipment.equipmentName, "
				+ "o.schWorkorder.date, " + "o.schWorkorder.mdShift.name, "
				+ "o.schWorkorder.mdTeam.name, " + "o.schWorkorder.qty, "
				+ "o.qty, " + "o.badQty, " + "o.mdUnit.name "
				+ "from SchStatOutput o where 1=1";
		String params = "";
		if (hisOutBean.getEqpCod() != null) {
			params += " and o.schWorkorder.mdEquipment.equipmentCode="
					+ hisOutBean.getEqpCod() + "";
		} else {
			params += " and o.schWorkorder.mdEquipment.equipmentCode=" + -1
					+ "";//
		}

		String dateS = hisOutBean.getDate1();
		String dateE = hisOutBean.getDate2();
		if (StringUtil.notNull(dateS) || StringUtil.notNull(dateE)) {
			if (!StringUtil.notNull(dateS)) {
				dateS = "1970-01-01";
			}
			if (!StringUtil.notNull(dateE)) {
				dateE = "2049-12-29";
			}
			params += " and o.schWorkorder.date between '" + dateS + "' and '"
					+ dateE + "' ";
		}

		if (StringUtil.notNull(hisOutBean.getShiftId())) {
			params += " and o.schWorkorder.mdShift.id='"
					+ hisOutBean.getShiftId() + "'";
		}
		if (StringUtil.notNull(hisOutBean.getTeamId())) {
			params += " and o.schWorkorder.mdTeam.id='"
					+ hisOutBean.getTeamId() + "'";
		}

		String TotalHql = "select count(o) from SchStatOutput o where 1=1 "
				+ params;

		if (hisOutBean.getOrderFlag() != null) {
			Integer orderFlag = hisOutBean.getOrderFlag();
			// 时间排序
			if (orderFlag == 1) {
				params += " order by o.schWorkorder.date asc ";
			}
			if (orderFlag == -1) {
				params += " order by o.schWorkorder.date desc ";
			}
			// 班次
			if (orderFlag == 2) {
				params += " order by o.schWorkorder.mdShift.code asc ";
			}
			if (orderFlag == -2) {
				params += " order by o.schWorkorder.mdShift.code desc ";
			}
			// 班组
			if (orderFlag == 3) {
				params += " order by o.schWorkorder.mdTeam.code asc";
			}
			if (orderFlag == -3) {
				params += " order by o.schWorkorder.mdTeam.code desc ";
			}
			// 产量
			if (orderFlag == 4) {
				params += " order by o.qty asc";
			}
			if (orderFlag == -4) {
				params += " order by o.qty desc ";
			}
			// 剔除
			if (orderFlag == 5) {
				params += " order by o.badQty asc";
			}
			if (orderFlag == -5) {
				params += " order by o.badQty desc ";
			}
		} else {
			params += " order by o.schWorkorder.date desc ";
		}
		List<Object[]> list = schStatOutputDao.queryPageObjectArray(hql
				+ params, pageIndex, pageSize);

		String[] lables = new String[list.size()];
		Double[] values = new Double[list.size()];
		Double[] values2 = new Double[list.size()];
		Double[] values3 = new Double[list.size()];
		String unit = "";
		for (int i = 0; i < list.size(); i++) {
			Object[] arr = list.get(i);
			// 日期，班次，班组，计划产量，产量，产量单位
			Date date = (Date) arr[1];
			lables[i] = DateUtil.formatDateToString(date, "yyyy-MM-dd")
					+ "<br>" + arr[0] + "<br>" + arr[2] + " " + arr[3];
			values[i] = (Double) arr[4];
			values2[i] = (Double) arr[5];
			values3[i] = (Double) arr[6];
			unit = (String) arr[7];
		}

		long count = schStatOutputDao.queryTotal(TotalHql);
		HisOutBean bean = new HisOutBean(values, values2, values3, lables,
				pageIndex, pageSize, (int) count);
		bean.setUnit(unit);
		return bean;
	}
	
	@Override
	public HisOutBean getHisTotal(HisOutBean hisOutBean, int pageindex, int pagesize) {
		String sql=" select SUM(a.qty) as standQty,SUM(b.qty) as realQty,(select name from MD_UNIT where ID=a.unit) as unitName from SCH_WORKORDER a,SCH_STAT_OUTPUT b where a.ID=b.OID ";
		
		String params="";
		if(hisOutBean.getEqpCod()!=null){
			params+=" and a.EQP='"+hisOutBean.getEqpCod()+"' ";
		}
		
		String dateS = hisOutBean.getDate1();
		String dateE = hisOutBean.getDate2();
		//开始时间
		if (StringUtil.notNull(dateS)) {
			params += " and a.DATE >= '" + dateS + "' ";
		}
		//结束时间
		if (StringUtil.notNull(dateE)) {
			params += " and a.DATE <= '"  + dateE + "' ";
		}
		
		if (StringUtil.notNull(hisOutBean.getShiftId())) {
			params += " and a.SHIFT='"+ hisOutBean.getShiftId() + "'";
		}
		if (StringUtil.notNull(hisOutBean.getTeamId())) {
			params += " and a.TEAM='"+ hisOutBean.getTeamId() + "'";
		}
		List<?> list = schStatOutputDao.queryBySql(sql+params+" group by a.UNIT");
		
		if(list!=null && list.size()>0){
			String[] lables = new String[list.size()];
			Double[] values = new Double[list.size()];
			Double[] values2 = new Double[list.size()];
			String unit = "";
			for (int i = 0; i < list.size(); i++) {
				Object[] arr = (Object[]) list.get(i);
				// 日期，班次，班组，计划产量，产量，产量单位
				lables[i] = dateS+"  ~  "+dateE;
				if(arr[0]!=null){
					values[i] =MathUtil.roundHalfUp(Double.parseDouble(arr[0].toString() ), 2);
				}
				if(arr[1]!=null){
					values2[i] =MathUtil.roundHalfUp(Double.parseDouble(arr[1].toString() ), 2);
				}
				unit =(String) arr[2];
			}

			HisOutBean bean = new HisOutBean(values, values2, lables,
					0, 0,0);
			bean.setUnit(unit);
			return bean;
		}else{
			return new HisOutBean();
		}
		
	}

	@Override
	public List<EqpRuntime> initOutDataPage(HttpSession session,Long type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datef = sdf.format(new Date());
		//LoginBean loginBean = (LoginBean) WebContextUtil.getSession().getAttribute("loginInfo"); 
		LoginBean loginBean = (LoginBean) session.getAttribute("loginInfo");
		String shift_id = loginBean.getShiftId();
		StringBuffer ssf=new StringBuffer(1000);
		ssf.append(" from SchWorkorder o where o.enable=1 and o.del=0  and o.type='"+type+"' and o.sts=2 and o.mdShift.id='"+ shift_id+"' ");
		ssf.append(" AND '"+datef+"'  between o.stim and o.etim  order by o.mdEquipment.equipmentCode asc ,type " );
		// 主要是获取当前运行的工单班次标准，设备，牌号信息
		List<SchWorkorder> orders = schWorkorderDao.query(ssf.toString());
		List<EqpRuntime> list = new ArrayList<EqpRuntime>();
		for (SchWorkorder schWorkorder : orders) {
				EqpRuntime eqpRuntime = new EqpRuntime(schWorkorder.getMdEquipment().getEquipmentCode(), 
						schWorkorder.getMdEquipment().getEquipmentName(), 
						schWorkorder.getMdMat().getSimpleName(), 
						DateUtil.formatDateToString(schWorkorder.getDate(), "yyyy-MM-dd"), 
						schWorkorder.getMdShift().getName(),
						schWorkorder.getMdTeam().getName(), 
						schWorkorder.getQty(),
						schWorkorder.getMdUnit().getName()
						);
				eqpRuntime.setCurOut(0D);
				eqpRuntime.setBadQty(0D);
				list.add(eqpRuntime);
		}
		return list;

	}

	/**
	 * 根据设备查询 运行的工单
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	public EqpRuntime initOutDataByEqp(EqpRuntime bean) {
		// 主要是获取当前运行的工单班次标准，设备，牌号信息
		List<SchWorkorder> orders = schWorkorderDao
				.query("from SchWorkorder o where o.enable=1 and o.del=0 and o.mdEquipment.equipmentCode=? and o.sts=2 and shift='"+bean.getShiftID()+"'  and o.date='"+MESConvertToJB.rutDateStr()+"' ",
						bean.getEqpCod());

		List<EqpRuntime> list = new ArrayList<EqpRuntime>();

		for (SchWorkorder schWorkorder : orders) {
			EqpRuntime eqpRuntime = new EqpRuntime(schWorkorder
					.getMdEquipment().getEquipmentCode(), schWorkorder
					.getMdEquipment().getEquipmentName(), schWorkorder
					.getMdMat().getSimpleName(), DateUtil.formatDateToString(
					schWorkorder.getDate(), "yyyy-MM-dd"), schWorkorder
					.getMdShift().getName(),
					schWorkorder.getMdTeam().getName(), schWorkorder.getQty(),
					schWorkorder.getMdUnit().getName(), schWorkorder.getCode(),
					schWorkorder.getId());
			eqpRuntime.setCurOut(0D);
			eqpRuntime.setBadQty(0D);
			eqpRuntime.setMatId(schWorkorder.getMdMat().getId());
			eqpRuntime.setShiftID(schWorkorder.getMdShift().getId());
			eqpRuntime.setEqpId(schWorkorder.getMdEquipment().getId());
			list.add(eqpRuntime);
		}
		EqpRuntime queryBean = null;
		try {
			queryBean = list.get(0);
		} catch (Exception e) {

		}
		return queryBean;
	}
/**
 * shisihai
 * 2015-9-13
 */
	@Override
	public List<InputPageModel> initEqpInputInfos(Long type) {
		// 设备code、设备名称、成品名、时间、班次、班组、辅料名、辅料code、辅料单耗标准、成品单位
//		String hql = "select "
//				+ "o.mdEquipment.equipmentCode,"// 0
//				+ "o.mdEquipment.equipmentName,"// 1
//				+ "o.mdMat.simpleName,"// 2
//				+ "o.date,"// 3
//				+ "o.mdShift.name,"// 4
//				+ "o.mdTeam.name,"// 5
//				+ "b.mdMat.name,"// 6
//				+ "b.mdMat.mdMatType.code,"// 7
//				+ "s.val, "// 8
//				+ "o.mdMat.mdUnit.name "// 9
//				+ "from SchWorkorder o,SchWorkorderBom b,SchConStd s where o.type=? and o.sts = 2 and b.schWorkorder.id=o.id "
//				+ "and (s.del=0 and s.mdMatByProd.id=o.mdMat.id and s.mdMatByMat.id=b.mdMat.id)"
//				+ " order by o.mdEquipment.equipmentCode asc";
		Double price=0.0;
		StringBuffer sb=new StringBuffer();
		//sb.append(" SELECT me.EQUIPMENT_CODE as eqpCode, me.EQUIPMENT_NAME as eqpName,md.name as name,swo.date,ms.name as shift,mt.name as team,mts.name as mtName,mts.tid as matType,mts.val as danhao,mu.name as untiName,mts.mat_price as price ");
		sb.append(" SELECT me.EQUIPMENT_CODE as eqpCode, me.EQUIPMENT_NAME as eqpName,md.name as name,swo.date,ms.name as shift,mt.name as team,mts.name as mtName,mts.tid as matType,mts.STANDARD_VAL as danhao,mu.name as untiName,mts.mat_price as price ");
		sb.append(" from SCH_WORKORDER swo LEFT JOIN MD_EQUIPMENT me ON me.ID=swo.eqp ");
		sb.append(" LEFT JOIN MD_MAT md ON md.ID=swo.mat ");
		sb.append(" LEFT JOIN MD_SHIFT ms ON ms.ID=swo.shift ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.ID=swo.team ");
		sb.append(" LEFT JOIN MD_UNIT mu ON swo.unit=mu.ID ");
		sb.append(" LEFT JOIN (SELECT c.oid,a.name,a.STANDARD_VAL,a.TID,b.mat_price,d.val from MD_MAT a LEFT JOIN COS_MAT_PRICE_MAINTAIN b ON a.id=b.mat_id LEFT JOIN SCH_WORKORDER_BOM c ON a.id=c.mat LEFT JOIN SCH_CON_STD d ON d.mat=a.id where d.del=0 ) mts ON mts.oid=swo.id ");
		sb.append(" where swo.sts=2 AND swo.type=? ORDER BY me.EQUIPMENT_CODE");
		//List<Object[]> objects = schWorkorderDao.queryObjectArray(hql, type);
		List<Object[]> objects = (List<Object[]>) schWorkorderDao.queryBySql(sb.toString(), type);

		Map<String, InputPageModel> map = new HashMap<String, InputPageModel>();
		InputPageModel inputPageModel = null;
		for (Object[] obj : objects) {
			String key = obj[0].toString();// 设备编码作为KEY
			if (map.containsKey(key)) {// 如果存在，取出赋值
				inputPageModel = map.get(key);
			} else {
				inputPageModel = new InputPageModel();
				map.put(obj[0].toString(), inputPageModel);
			}
			inputPageModel.setEqpCod(key);
			inputPageModel.setEqpName(obj[1].toString());
			inputPageModel.setMat(obj[2].toString());
			inputPageModel.setDate(DateUtil.formatDateToString((Date) obj[3],
					"yyyy-MM-dd"));
			inputPageModel.setShift(obj[4].toString());
			inputPageModel.setTeam(obj[5].toString());
			inputPageModel.setUnit(obj[9].toString());
			inputPageModel.setQty(0D);
			if(obj[10]!=null){
				price=Double.parseDouble(obj[10].toString());
			}
			this.setMatInfos(inputPageModel, obj[7].toString(),
					Double.parseDouble(obj[8].toString()),price);

		}
		// 转存list
		List<InputPageModel> list = new ArrayList<InputPageModel>();
		for (String key : map.keySet()) {
			list.add(map.get(key));
		}
		Collections.sort(list);// 按设备Code排序
		return list;
	}

	/**
	 * 辅料单耗信息赋值
	 * 
	 * @author Leejean
	 * @create 2014年12月23日下午3:10:40
	 * @param eqpRuntime
	 * @param string
	 * @param parseDouble
	 */
	private void setMatInfos(InputPageModel inputPageModel, String matTypeCode,
			Double val,Double price) {
		/*
		 * 辅料类型Code定义 1 1 成品烟
		 * 
		 * 2 2 卷烟纸 3 3 水松纸 4 4 滤棒 5 5 小盒烟膜 6 6 条盒烟膜 7 7 小盒纸 8 8 条盒纸 9 9 内衬纸 10
		 * 10 封签纸 11 11 甘油 12 12 丝束 13 13 滤棒盘纸 14 14 成品滤棒
		 */
		switch (matTypeCode) {
		// 卷烟机部分
		case "2":
			inputPageModel.setJuanyanzhiBzdh(val);
			inputPageModel.setJuanyanzhidj(price);
			break;
		case "3":
			inputPageModel.setShuisongzhiBzdh(val);
			inputPageModel.setShuisongzhidj(price);
			break;
		case "4":
			inputPageModel.setLvbangBzdh(val);
			inputPageModel.setLvbangdj(price);
			break;
		// 包装机部分
		case "5":
			inputPageModel.setXiaohemoBzdh(val);
			inputPageModel.setXiaohemodj(price);
			break;
		case "6":
			inputPageModel.setTiaohemoBzdh(val);
			inputPageModel.setTiaohemodj(price);
			break;
		case "7":
			inputPageModel.setXiaohezhiBzdh(val);
			inputPageModel.setXiaohezhidj(price);
			break;
		case "8":
			inputPageModel.setTiaohezhiBzdh(val);
			inputPageModel.setTiaohezhidj(price);
			break;
		case "9":
			inputPageModel.setNeichenzhiBzdh(val);
			inputPageModel.setNeichenzhidj(price);
			break;
		case "11":
			inputPageModel.setGanyouBzdh(val);
			break;
		case "12":
			inputPageModel.setSishuBzdh(val);
			break;
		case "13":
			inputPageModel.setPanzhiBzdh(val);
			inputPageModel.setJuanyanzhidj(price);
			break;
		default:
			break;
		}
	}

	@Override
	public List<EqpRuntime> getRollerOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getRoller(),
				"roller");
	}

	@Override
	public List<EqpRuntime> getPackerOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getPacker(),
				"packer");
	}

	@Override
	public List<EqpRuntime> getBoxerOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getBoxer(),
				"boxer");
	}

	@Override
	public List<EqpRuntime> getFilterOutData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getFilter(),
				"filter");
	}

	@Override
	public List<EqpRuntime> getRollerBadData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getRoller(),
				"roller");
	}

	@Override
	public List<EqpRuntime> getPackerBadData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getPacker(),
				"packer");
	}

	@Override
	public List<EqpRuntime> getFilterBadData() {
		return this.getOutAndBadQtyData(EquipmentTypeDefinition.getFilter(),
				"filter");
	}

	/**
	 * outDataPonit，badQtyDataPonit ->产量、剔除的点位参考点表
	 * 
	 * @return
	 */
	private List<EqpRuntime> getOutAndBadQtyData(List<String> equipentTypes,
			String flag) {
		try {
			List<EqpRuntime> list = new ArrayList<EqpRuntime>();
			List<EquipmentData> allData = NeedData.getInstance().getEqpData();

			for (EquipmentData equipmentData : allData) {

				String type = equipmentData.getType().toUpperCase();

				if (type.indexOf("_FL") > 0) {
					continue;
				}// 辅料数据跳过

				List<CommonData> datas = equipmentData.getAllData();

				String eqpCod = String.valueOf(equipmentData.getEqp());

				if (flag.equals("roller") && equipentTypes.contains(type)) {
					String outDataPonit = "7";
					String badQtyDataPonit = "11";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				} else if (flag.equals("packer") && equipentTypes.contains(type)) {
					String outDataPonit = "3";
					String badQtyDataPonit = "4";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				} else if (flag.equals("boxer") && equipentTypes.contains(type)) {
					String outDataPonit = "53";
					String badQtyDataPonit = "-1";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				} else if (flag.equals("filter") && equipentTypes.contains(type)) {
					String outDataPonit = "1";
					String badQtyDataPonit = "2";
					getCurOutAndBadQtyData(list, datas, eqpCod, outDataPonit,
							badQtyDataPonit);
				}

			}

			return list;
		} catch (Exception e) {
			System.out.println("获取辅料错误>>>>");
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * 获取产量或剔除法方
	 * 
	 * @param list
	 * @param datas
	 * @param eqpCod
	 * @param outDataPonit
	 * @param badQtyDataPonit
	 *            部分设备剔除量是没有的 所以这里可以传递-1作为点位，则程序会给剔除量默认值为0。
	 */
	private void getCurOutAndBadQtyData(List<EqpRuntime> list,
			List<CommonData> datas, String eqpCod, String outDataPonit,
			String badQtyDataPonit) {
		Double curOut = GetValueUtil.getDoubleValue(NeedData.getInstance()
				.getDataValue(datas, outDataPonit));
		// 千字换成箱
		Double badQty = 0d;
		if (!badQtyDataPonit.equals("-1")) {
			String t = NeedData.getInstance().getDataValue(datas,
					badQtyDataPonit);
			badQty = GetValueUtil.getDoubleValue(t);
		}
		list.add(new EqpRuntime(eqpCod, curOut, badQty));
	}

	@Override
	public List<InputPageModel> getPackerInputData() {
		List<InputPageModel> list = new ArrayList<InputPageModel>();
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		for (EquipmentData equipmentData : allData) {
			String equipmentCode = String.valueOf(equipmentData.getEqp());
			if (EquipmentTypeDefinition.getPacker().contains(
					equipmentData.getType().toUpperCase())) {
				PackerData packerData = packerIspService
						.getPackerData(equipmentCode);
				list.add(new InputPageModel(equipmentCode, packerData.getQty(),
						packerData.getXiaohemoVal(), packerData
								.getTiaohemoVal(),
						packerData.getXiaohezhiVal(), packerData
								.getTiaohezhiVal(), packerData
								.getNeichenzhiVal()));
			}
		}
		return list;
	}

	@Override
	public List<InputPageModel> getRollerInputData() {
		List<InputPageModel> list = new ArrayList<InputPageModel>();
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		if(allData.size()>0 && allData!=null){
			for (EquipmentData equipmentData : allData) {
				try {
					String equipmentCode = String.valueOf(equipmentData.getEqp());
					if (EquipmentTypeDefinition.getRoller().contains(
							equipmentData.getType().toUpperCase())) {
						RollerData rollerData = rollerIspService
								.getRollerData(equipmentCode);
						list.add(new InputPageModel(equipmentCode, rollerData
								.getShuisongzhiVal(), rollerData.getJuanyanzhiVal(),
								rollerData.getLvbangVal(), rollerData.getQty()));
					}
				} catch (Exception e) {}
			}
		}
		return list;
	}

	@Override
	public List<InputPageModel> getFilterInputData() {
		List<InputPageModel> list = new ArrayList<InputPageModel>();
		List<EquipmentData> allData = NeedData.getInstance().getEqpData();
		for (EquipmentData equipmentData : allData) {
			String equipmentCode = String.valueOf(equipmentData.getEqp());
			if (EquipmentTypeDefinition.getFilter().contains(
					equipmentData.getType().toUpperCase())) {
				FilterData filterData = filterIspService
						.getFilterData(equipmentCode);
				list.add(new InputPageModel(equipmentCode, filterData
						.getPanzhiVal(), 0D, 0D, filterData.getQty(), null));
			}
		}
		return list;
	}


	// 历史消耗
	public  DataGrid initEqpInputInfos2(Long type, HisOutBean his,
			int pageSize) {
		// 1卷烟机 2包装机3封箱机4成型机
		//设置工单类型
		StringBuffer sql2=getSql(type, his, pageSize, 2);//获取总条数
		List<Integer> l=(List<Integer>) schWorkorderDao.queryBySql(sql2.toString());
		long total=(long)l.get(0);
		pageSize=15;
		if (StringUtil.notNull(his.getEqpCod())) {
			int eqpCode = Integer.parseInt(his.getEqpCod());
			if (eqpCode >= 1 && eqpCode <= 30) {
				type = 1L;
				total=total/3;
			}else if(eqpCode >= 31 && eqpCode <= 60){
				type=2L;
				pageSize=25;
				total=total/5;
			}else if(eqpCode >= 61 && eqpCode <= 70){
				type=3L;
			}else if(eqpCode >= 101 && eqpCode <= 130){
				type=4L;
			}else{
				type = 1L;
			}
		}
//辅料消耗量 * 系数  tab0.qty*(CASE  WHEN mmp.val is NULL THEN 1 ELSE mmp.val END) n9  现在已乘
		StringBuffer sql = getSql(type, his, pageSize,1);
		// 分页
		sql.append(" where  rowNum > " + (his.getPageIndex() - 1) * pageSize
				+ " and rowNum <= " + (his.getPageIndex() * pageSize));
		sql.append(" ORDER BY tab2.[DATE] desc ");
		List<Object[]> objs = (List<Object[]>) schWorkorderDao.queryBySql(sql.toString());
		Map<String, InputPageModel> map = new LinkedHashMap<String, InputPageModel>();
		InputPageModel pageModel = null;
		// 以工单为key
		for (Object[] obj : objs) {
			String key = obj[1].toString();
			if (map.containsKey(key)) {
				pageModel = map.get(key);
			} else {
				pageModel = new InputPageModel();
				map.put(key, pageModel);
			}
			pageModel.setEqpCod(obj[1].toString());// 里面放的是工单id
			pageModel.setDate(DateUtil.formatDateToString((Date) obj[2],
					"yyyy-MM-dd"));
			pageModel.setEqpName(obj[3].toString());
			pageModel.setShift(obj[4].toString());
			pageModel.setTeam(obj[5].toString());
			pageModel.setMat(obj[6].toString());
			pageModel.setQty(Double.parseDouble(obj[7].toString()));
			pageModel.setUnit(obj[8].toString());
			this.setMatInfos2(pageModel, obj[10].toString(),
					Double.parseDouble(obj[11].toString()),
					Double.parseDouble(obj[12]==null?"0":obj[12].toString()),obj[14].toString());
		}

		List<InputPageModel> list = new ArrayList<InputPageModel>();
		for (String key : map.keySet()) {
			list.add(map.get(key));
		}
		return new DataGrid(list, total);
	}

	

	/**
	 * 
	 * @param inputPageModel
	 * @param matTypeCode
	 * @param bzval标准单耗
	 * @param Val
	 *            实际单耗
	 * @param matId
	 *            辅料id
	 */
	private void setMatInfos2(InputPageModel inputPageModel,
			String matTypeCode, Double bzval, Double val, String matId) {
		/*
		 * 辅料类型Code定义 1 1 成品烟
		 * 
		 * 2 2 卷烟纸 3 3 水松纸 4 4 滤棒 5 5 小盒烟膜 6 6 条盒烟膜 7 7 小盒纸 8 8 条盒纸 9 9 内衬纸 10
		 * 10 封签纸 11 11 甘油 12 12 丝束 13 13 滤棒盘纸 14 14 成品滤棒
		 */
		switch (matTypeCode) {
		// 卷烟机部分
		case "2":
			inputPageModel.setJuanyanzhiBzdh(bzval);
			inputPageModel.setJuanyanzhiIn(val);
			inputPageModel.setJuanyanzhiid(matId);
			break;
		case "3":
			inputPageModel.setShuisongzhiBzdh(bzval);
			inputPageModel.setShuisongzhiIn(val);
			inputPageModel.setShuisongzhiid(matId);
			break;
		case "4":
			inputPageModel.setLvbangBzdh(bzval);
			inputPageModel.setLvbangIn(val);
			inputPageModel.setLvbangid(matId);
			break;
		// 包装机部分
		case "5":
			inputPageModel.setXiaohemoBzdh(bzval);
			inputPageModel.setXiaohemoIn(val);
			inputPageModel.setXiaohemoid(matId);
			break;
		case "6":
			inputPageModel.setTiaohemoBzdh(bzval);
			inputPageModel.setTiaohemoIn(val);
			inputPageModel.setTiaohemoid(matId);
			break;
		case "7":
			inputPageModel.setXiaohezhiBzdh(bzval);
			inputPageModel.setXiaohezhiIn(val);
			inputPageModel.setXiaohezhiid(matId);
			break;
		case "8":
			inputPageModel.setTiaohezhiBzdh(bzval);
			inputPageModel.setTiaohezhiIn(val);
			inputPageModel.setTiaohezhiid(matId);
			break;
		case "9":
			inputPageModel.setNeichenzhiBzdh(bzval);
			inputPageModel.setNeichenzhiIn(val);
			inputPageModel.setNeichenzhiid(matId);
			break;
		case "11":
			inputPageModel.setGanyouBzdh(bzval);
			inputPageModel.setGanyouIn(val);
			break;
		case "12":
			inputPageModel.setSishuBzdh(bzval);
			inputPageModel.setSishuIn(val);
			break;
		case "13":
			inputPageModel.setPanzhiBzdh(bzval);
			inputPageModel.setPanzhiIn(val);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @param type
	 * @param his
	 * @param pageSize
	 * @param sqlType 1获取详细  其他获取总条数
	 * @return
	 */
	private StringBuffer getSql(Long type, HisOutBean his, int pageSize,int sqlType) {
		StringBuffer sql = new StringBuffer();
		if(sqlType==1){
			sql.append("SELECT * ");
		}else{
			sql.append(" select count(0) ");
		}
		sql.append(" from ( SELECT ROW_NUMBER() OVER(ORDER BY tab1.oid1) as rowNum,tab1.oid1,tab1.[DATE],tab1.n0,tab1.n1,tab1.n2,tab1.n3,tab1.n4,tab1.n5,tab1.n6,tab1.n7,tab1.n8,tab1.n9,tab1.n10,tab1.n11 from ");
		sql.append("(SELECT DISTINCT(swo.id) oid1,swo.[DATE],eqp.EQUIPMENT_NAME n0,ms.NAME n1,mt.NAME n2,mm.NAME n3,sso.qty n4,mu.NAME n5,tab0.name1 n6,tab0.CODE n7,scs.val n8,tab0.qty n9,tab0.name2 n10,tab0.mid n11 FROM ");
		sql.append("SCH_WORKORDER swo LEFT JOIN MD_EQUIPMENT eqp ON swo.EQP = eqp.ID ");
		sql.append(" LEFT JOIN MD_SHIFT ms ON swo.SHIFT=ms.ID ");
		sql.append(" LEFT JOIN MD_TEAM mt ON swo.TEAM=mt.ID ");
		sql.append(" LEFT JOIN MD_MAT mm ON swo.MAT=mm.ID ");
		sql.append(" LEFT JOIN MD_UNIT mu on swo.UNIT=mu.ID ");
		sql.append(" LEFT JOIN SCH_STAT_OUTPUT sso on sso.OID=swo.ID and sso.QTY>0 ");
		sql.append(" LEFT JOIN SCH_STAT_INPUT ssi ON ssi.OUT_ID=sso.ID ");
		sql.append(" LEFT JOIN(SELECT mm.NAME name1,ssi.QTY qty,mu.NAME name2,so.ID id,mm.ID mid,mmt.CODE from SCH_STAT_INPUT ssi LEFT JOIN MD_MAT mm on ssi.MAT = mm.ID LEFT JOIN MD_UNIT mu ON ssi.UNIT = mu.ID LEFT JOIN ");
		sql.append(" SCH_STAT_OUTPUT sso ON ssi.OUT_ID = sso.ID LEFT JOIN SCH_WORKORDER so on sso.OID = so.ID LEFT JOIN MD_MAT_TYPE mmt ON mm.TID=mmt.ID) tab0 ON swo.id=tab0.id ");
		sql.append(" LEFT JOIN SCH_CON_STD scs ON scs.MAT=tab0.mid and scs.PROD=swo.mat LEFT JOIN MD_MAT_PARAM mmp ON mmp.MAT=tab0.mid WHERE swo.STS > 2 AND sso.DEL=0 and scs.DEL=0 AND swo.TYPE= "+type);
		// 条件过滤
		if (StringUtil.notNull(his.getDate1())) {
			sql.append(" and swo.[DATE] >= '" + his.getDate1() + "'");
		}
		if (StringUtil.notNull(his.getDate1())) {
			sql.append(" and swo.[DATE] <= '" + his.getDate2() + "'");
		}
		// 班次班组
		if (StringUtil.notNull(his.getShiftId())) {
			sql.append(" and ms.CODE = " + his.getShiftId());
		}
		if (StringUtil.notNull(his.getTeamId())) {
			sql.append(" and mt.CODE = " + his.getTeamId());
		}

		sql.append(" ) tab1 ) tab2 ");
		return sql;
	}
	/**
	* WCT查询机台产耗信息
	*TRAVLER
	2015年11月27日下午3:24:02
	*
	*/
	@Override
	public List<MatterInfo> SearchEqpDailyInfo(String time, String teamId) {
		//数据集合，用于保存MatterInfo对象
		List<MatterInfo> data=new ArrayList<MatterInfo>();
		//1.查询卷烟机产耗信息
		String sql1=this.getRolerQtySql(time, teamId);
		List<?> rl=schStatOutputDao.queryBySql(sql1);
		//1.1封装卷烟机成bean,并添加到List中
		this.setRollerBeanValue(rl,data);
		
		
		//2.查询包装机产耗信息
		String sql2=this.getPackerQtySql(time, teamId);
		List<?> pl=schStatOutputDao.queryBySql(sql2);
		//2.1封装包装机Bean，并添加到List中
		this.setPackerBeanValue(pl,data);
		
		//3.将卷烟机和包装机匹配成组    对集合排序    按机台号
		this.reSetBeanCouple(data);
		return data;
	}
	/**
	 * 将卷烟机和包装机匹配成组
	 * TODO
	 * @param data
	 * TRAVLER
	 * 2015年11月27日下午4:44:43
	 */
	private void reSetBeanCouple(List<MatterInfo> data) {
		List<MatterInfo> mData=new ArrayList<MatterInfo>();//中间数据
		List<MatterInfo> fData=new ArrayList<MatterInfo>();//最后的数据
		MatterInfo b1=null;
		MatterInfo b2=null;
		Integer code1=null;
		Integer code2=null;
		String mat1=null;
		String mat2=null;
		String team1=null;
		String team2=null;
		for (int i = 0; i <data.size(); i++) {
			b1=data.get(i);
			for (int j = 0; j < data.size(); j++) {
				b2=data.get(j);
				code1=b1.getEqpCode();
				code2=b2.getEqpCode();
				//1 code要相匹配
				if(code1 != (code2-30)){
					continue;
				}
				//2班组要相同
				team1=b1.getTeam_Name();
				team2=b2.getTeam_Name();
				if(!team1.equals(team2)){
					continue;
				}
				//3 牌号要相同
				mat1=b1.getMat_name();
				mat2=b2.getMat_name();
				if(mat1.equals(mat2)){
					//匹配的组,最后移除
					mData.add(b1);
					mData.add(b2);
					try {
						BeanConvertor.copyProperties(b1, b2);
					} catch (Exception e) {
						e.printStackTrace();
					}
					fData.add(b2);//最后整合的数据
				}else{
					//同机组但是不同牌号的特殊情况，牌号为两者连接
					//匹配的组,最后移除
					mData.add(b1);
					mData.add(b2);
					try {
						BeanConvertor.copyProperties(b1, b2);
						b2.setMat_name(mat1+mat2);
					} catch (Exception e) {
						e.printStackTrace();
					}
					fData.add(b2);//最后整合的数据
				}
			}
		}
		
		data.removeAll(mData);//移除已经匹配的对象
		//重新设置Code
		for (MatterInfo b3 : data) {
			Integer c=null;
			if(b3.getEqpCode()>30){
				c=b3.getEqpCode()-30;
				b3.setEqpCode(c);
			}
		}
		data.addAll(fData);//添加匹配的数据
		
		//填充空数据凑足12个对象
		List<MatterInfo> m=new ArrayList<MatterInfo>(11);
		MatterInfo b4=null;
		for (int i = 1; i < 13; i++) {
			if(i==10){
				continue;
			}
			b4=new MatterInfo();
			b4.setEqpCode(i);
			m.add(b4);
		}
		
		
		//重新设置List数据
		Integer mcode=null;
		Integer dcode=null;
		for(int i=0;i<m.size();i++){
			mcode=m.get(i).getEqpCode();
			for(int j=0;j<data.size();j++){
				dcode=data.get(j).getEqpCode();
				if(mcode==dcode){
					m.set(i, data.get(j));
				}
			}
		}
		//按code排序
		Comparator<MatterInfo> p=new Comparator<MatterInfo>() {
			@Override
			public int compare(MatterInfo o1, MatterInfo o2) {
				return Integer.compare(o1.getEqpCode(), o2.getEqpCode());
			}
		};
		
		
		data.clear();
		//m.sort(p);
		int size=m.size();
		MatterInfo[] bs2=new MatterInfo[size];
		for (int i = 0; i < size; i++) {
			bs2[i]=m.get(i);
		}
		Arrays.sort(bs2,p);
		m.clear();
		for (MatterInfo matterInfo : bs2) {
			m.add(matterInfo);
		}
		data.addAll(m);
		
		
	}

	/**
	 * 封装包装机Bean，并添加到List中
	 * TODO
	 * @param pl
	 * @param data
	 * TRAVLER
	 * 2015年11月27日下午4:42:10
	 */
	private void setPackerBeanValue(List<?> pl, List<MatterInfo> data) {
		if(null!=pl&&pl.size()>0){
			Object[] o=null;
			MatterInfo bean=null;
			for (int i = 0; i < pl.size(); i++) {
				bean=new MatterInfo();
				o=(Object[]) pl.get(i);
				bean.setEqpCode(this.convObjToInt(o[0]));
				bean.setMat_name(StringUtil.convertObjToString(o[1]));
				bean.setTeam_Name(StringUtil.convertObjToString(o[2]));
				Double qty=this.convObjToDou(o[3]);
				bean.setBzQty(qty);
				Double xhz=this.convObjToDou(o[4]);
				Double thz=this.convObjToDou(o[5]);
				Double lbz=this.convObjToDou(o[6]);
				bean.setXhzDH(this.calcuDH(qty, xhz));
				bean.setThzDH(this.calcuDH(qty, thz));
				bean.setLbzDH(this.calcuDH(qty, lbz));
				data.add(bean);
			}
		}
		
	}

	/**
	 * 封装卷烟机成bean,并添加到List中
	 * TODO
	 * @param rl
	 * @param data
	 * TRAVLER
	 * 2015年11月27日下午4:22:58
	 */
	private void setRollerBeanValue(List<?> rl, List<MatterInfo> data) {
		if(null!=rl&&rl.size()>0){
			Object[] o=null;
			MatterInfo bean=null;
			for (int i = 0; i < rl.size(); i++) {
				bean=new MatterInfo();
				o=(Object[]) rl.get(i);
				bean.setEqpCode(this.convObjToInt(o[0]));
				bean.setMat_name(StringUtil.convertObjToString(o[1]));
				bean.setTeam_Name(StringUtil.convertObjToString(o[2]));
				Double qty=this.convObjToDou(o[3]);
				bean.setJuQty(qty);
				Double lb=this.convObjToDou(o[4]);
				Double pz=this.convObjToDou(o[5]);
				Double ssz=this.convObjToDou(o[6]);
				bean.setLbDH(this.calcuDH(qty, lb));
				bean.setPzDH(this.calcuDH(qty, pz));
				bean.setSszDH(this.calcuDH(qty, ssz));
				data.add(bean);
			}
		}
	}
	/**
	 * Obj 转Integer
	 * TODO
	 * @param o
	 * @return
	 * TRAVLER
	 * 2015年11月27日下午4:30:24
	 */
	private Integer convObjToInt(Object o){
		Integer v=0;
		if(null!=o){
			v=Integer.valueOf(o.toString());
		}
		return v;
	}
	/**
	 * Obj 转Double
	 * TODO
	 * @param o
	 * @return
	 * TRAVLER
	 * 2015年11月27日下午4:30:24
	 */
	private Double convObjToDou(Object o){
		Double v=0.0;
		if(null!=o){
			v=Double.valueOf(o.toString());
		}
		return v;
	}
	
	/**
	 * 计算单耗
	 * TODO
	 * @param qty 产量
	 * @param fl  辅料
	 * @return
	 * TRAVLER
	 * 2015年11月27日下午4:37:26
	 */
	private Double calcuDH(Double qty,Double fl){
		Double v=0.0;
		if(qty!=0){
			v=fl/qty;
			v=MathUtil.roundHalfUp(v, 2);
		}
		return v;
	}
	
	/**
	 * 获取查询卷烟机台工段日报信息
	 * TODO
	 * @param time
	 * @param teamId
	 * @return
	 * TRAVLER
	 * 2015年11月27日下午3:28:47
	 */
	private String getRolerQtySql(String time,String teamId){
		StringBuffer sb=new StringBuffer(1000);
		sb.append(" SELECT qtyTab.eqp_code,qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.lb,flTab.pz,flTab.ssz from  ");
		sb.append(" (SELECT f1.team1 as team,f1.eqp_id as eqpId,f1.mat1 as matId,SUM(f1.pz1+f2.pz1-f3.pz1) as pz,SUM (f1.ssz1+f2.ssz1-f3.ssz1) as ssz,SUM(f1.lb1+f2.lb1-f3.lb1) as lb from ");
		sb.append(" (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1,fl_1.eqp_id as eqp_id, SUM(fl_1.pz)as pz1,SUM(fl_1.ssz) as ssz1,SUM(fl_1.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=1 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) ='"+time+"' ");
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ,fl_1.eqp_id) f1, ");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,fl_2.eqp_id as eqp_id,SUM(fl_2.pz)as pz1,SUM(fl_2.ssz) as ssz1,SUM(fl_2.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where mec_id=1 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) ='"+time+"' ");
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ,fl_2.eqp_id) f2,");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,fl_3.eqp_id as eqp_id,SUM(fl_3.pz)as pz1,SUM(fl_3.ssz) as ssz1,SUM(fl_3.lb) as lb1 from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zj_pz, 0)*ISNULL(pz_gg, 5000) as pz ,ISNULL(zj_ssz, 0) as ssz,ISNULL(zj_lb, 0) as lb from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=1 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) ='"+time+"' ");
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ,fl_3.eqp_id) f3 ");
		sb.append(" where f1.eqp_id=f2.eqp_id AND f2.eqp_id=f3.eqp_id AND f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
		sb.append(" GROUP BY f1.team1,f1.eqp_id,f1.mat1) flTab ");
		sb.append(" LEFT JOIN (SELECT t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id  from ( ");
		sb.append(" SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.TEAM as team_id,mt.NAME as team,mep.EQUIPMENT_CODE as eqp_code,swo.EQP as eqp_id,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
		sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.ID=swo.EQP ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=1 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) ='"+time+"' ) t0 ");
		sb.append(" GROUP BY t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,t0.team_id,t0.mat_id ) qtyTab ");
		sb.append(" ON flTab.eqpId=qtyTab.eqp_id AND flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
		sb.append(" WHERE qtyTab.team_id='"+teamId+"' ");//班组
		sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team ");
		return sb.toString();
	}
	
	/**
	 * 获取查询包装机工段日报信息
	 * TODO
	 * @param time
	 * @param teamId
	 * @return
	 * TRAVLER
	 * 2015年11月27日下午3:29:05
	 */
	private String getPackerQtySql(String time,String teamId){
		StringBuffer sb=new StringBuffer(1000);
		sb.append(" SELECT qtyTab.eqp_code,qtyTab.mat_name,qtyTab.team,qtyTab.qty,flTab.xhz,flTab.thz,flTab.lbz from  ");
		sb.append(" (SELECT f1.team1 as team,f1.eqp_id as eqpId,f1.mat1 as matId,SUM(f1.xhz+f2.xhz-f3.xhz) as xhz,SUM(f1.thz+f2.thz-f3.thz) as thz,SUM(f1.lbz+f2.lbz-f3.lbz) as lbz from  ");
		sb.append(" (SELECT fl_1.team_id as team1,fl_1.mat_id as mat1,fl_1.eqp_id as eqp_id, SUM(fl_1.xhz) as xhz,SUM(fl_1.thz) as thz, SUM(fl_1.lbz) as lbz from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz from SCH_STAT_SHIFT_FLINFO ");
		sb.append(" where  mec_id=2 AND fl_type=1 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) ='"+time+"'");
		sb.append(" ) fl_1 GROUP BY fl_1.team_id ,fl_1.mat_id ,fl_1.eqp_id) f1,");
		sb.append(" (SELECT fl_2.team_id as team1,fl_2.mat_id as mat1,fl_2.eqp_id as eqp_id, SUM(fl_2.xhz) as xhz,SUM(fl_2.thz) as thz, SUM(fl_2.lbz) as lbz from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where mec_id=2 AND fl_type=2 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) ='"+time+"'");
		sb.append(" ) fl_2 GROUP BY fl_2.team_id ,fl_2.mat_id ,fl_2.eqp_id) f2,");
		sb.append(" (SELECT fl_3.team_id as team1,fl_3.mat_id as mat1,fl_3.eqp_id as eqp_id,SUM(fl_3.xhz) as xhz,SUM(fl_3.thz) as thz, SUM(fl_3.lbz) as lbz from ( ");
		sb.append(" SELECT oid,team_id,mat_id,eqp_id,ISNULL(zb_xhz, 0) as xhz ,ISNULL(zb_thz, 0) as thz,ISNULL(zb_lbz, 0) as lbz from SCH_STAT_SHIFT_FLINFO  ");
		sb.append(" where  mec_id=2 AND fl_type=3 AND (SELECT CONVERT(VARCHAR(23),swo.[DATE],23) from SCH_WORKORDER swo where swo.id=oid) ='"+time+"'");
		sb.append(" ) fl_3 GROUP BY fl_3.team_id ,fl_3.mat_id ,fl_3.eqp_id) f3 ");
		sb.append(" where f1.eqp_id=f2.eqp_id AND f2.eqp_id=f3.eqp_id AND f1.team1=f2.team1 AND f2.team1=f3.team1 AND f1.mat1=f2.mat1 AND f2.mat1=f3.mat1 ");
		sb.append(" GROUP BY f1.team1,f1.eqp_id,f1.mat1) flTab ");
		sb.append(" LEFT JOIN  ");
		sb.append(" (SELECT t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,SUM(t0.qty) as qty,t0.team_id ,t0.mat_id  from  ");
		sb.append(" ( SELECT mm.NAME as mat_name,swo.MAT as mat_id,swo.TEAM as team_id,mt.NAME as team,mep.EQUIPMENT_CODE as eqp_code,swo.EQP as eqp_id,ISNULL(sso.QTY , 0) as qty ");
		sb.append(" from SCH_WORKORDER swo ");
		sb.append(" LEFT JOIN SCH_STAT_OUTPUT sso ON sso.OID=swo.ID ");
		sb.append(" LEFT JOIN MD_MAT mm ON mm.id=swo.MAT ");
		sb.append(" LEFT JOIN MD_EQUIPMENT mep ON mep.ID=swo.EQP ");
		sb.append(" LEFT JOIN MD_TEAM mt ON mt.id=swo.TEAM ");
		sb.append(" WHERE swo.TYPE=2 AND swo.STS=4 AND CONVERT(VARCHAR(23),swo.[DATE],23) ='"+time+"'");
		sb.append(" ) t0 GROUP BY t0.mat_name,t0.team,t0.eqp_code,t0.eqp_id,t0.team_id,t0.mat_id ) qtyTab ");
		sb.append(" ON flTab.eqpId=qtyTab.eqp_id AND flTab.matId=qtyTab.mat_id AND flTab.team=qtyTab.team_id ");
		sb.append(" WHERE qtyTab.team_id= '"+teamId+"'");
		sb.append(" ORDER BY qtyTab.mat_name,qtyTab.team ");
		return sb.toString();
	}

	

}
