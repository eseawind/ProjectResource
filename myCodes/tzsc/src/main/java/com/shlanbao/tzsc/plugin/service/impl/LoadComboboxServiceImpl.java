package com.shlanbao.tzsc.plugin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.init.BaseParams;
import com.shlanbao.tzsc.plugin.service.LoadComboboxServiceI;
import com.shlanbao.tzsc.pms.md.eqp.service.EquipmentsServiceI;
import com.shlanbao.tzsc.pms.md.eqpcategory.service.MdEqpCategoryServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.service.MdEqpTypeServiceI;
import com.shlanbao.tzsc.pms.md.mat.service.MatServiceI;
import com.shlanbao.tzsc.pms.md.matgrp.service.MatGrpServiceI;
import com.shlanbao.tzsc.pms.md.mattype.service.MatTypeServiceI;
import com.shlanbao.tzsc.pms.md.shift.service.ShiftServiceI;
import com.shlanbao.tzsc.pms.md.team.service.TeamServiceI;
import com.shlanbao.tzsc.pms.md.unit.service.UnitServiceI;
import com.shlanbao.tzsc.pms.md.workshop.service.WorkShopServiceI;
import com.shlanbao.tzsc.pms.sch.workorder.service.WorkOrderServiceI;
import com.shlanbao.tzsc.utils.params.ComboboxType;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class LoadComboboxServiceImpl extends BaseService implements LoadComboboxServiceI {
	@Autowired
	public ShiftServiceI shiftService;
	@Autowired
	public TeamServiceI teamService;
	@Autowired
	public WorkShopServiceI workShopService;
	@Autowired
	public MatServiceI matService;
	@Autowired
	public MatTypeServiceI matTypeService;
	@Autowired
	public MatGrpServiceI matGrpService;
	@Autowired
	public UnitServiceI unitService;
	@Autowired
	public EquipmentsServiceI equipmentsService;
	@Autowired
	public MdEqpTypeServiceI eqpTypeServiceI;
	@Autowired
	public MdEqpCategoryServiceI categoryServiceI;
	@Override
	public void initCombobox(String scope) {
				try {
					//班次下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.SHIFT)){
						BaseParams.setShiftCombobox(BeanConvertor.copyList(shiftService.queryAllShiftsForComboBox(), Combobox.class));
					}
					//班组下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.TEAM)){
						BaseParams.setTeamCombobox(BeanConvertor.copyList(teamService.queryAllTeamsForComboBox(), Combobox.class));
					}
					//车间下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.WORKSHOP)){
						BaseParams.setWorkShopCombobox(BeanConvertor.copyList(workShopService.queryAllWorkShopsForComboBox(), Combobox.class));
					}
					//产品下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.MATPROD)){
						BaseParams.setMatProdCombobox(BeanConvertor.copyList(matService.queryAllProdMat(), Combobox.class));
					}
					
					//物料组下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.MATGRP)){
						BaseParams.setMatGrpCombobox(BeanConvertor.copyList(matGrpService.queryAllMatGrpsForComboBox(), Combobox.class));
					}
					//物料类型下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.MATTYPE)){
						BaseParams.setMatTypeCombobox(BeanConvertor.copyList(matTypeService.queryAllMatTypesForComboBox(), Combobox.class));
					}
					//物料单位下拉框
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.UNIT)){
						BaseParams.setUnitCombobox(BeanConvertor.copyList(unitService.queryAllUnitsForComboBox(), Combobox.class));
					}
					//设备类型
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.EQPCATEGORY)){
						BaseParams.setEqpCategoryCombobox(BeanConvertor.copyList(categoryServiceI.queryMdCategory(), Combobox.class));
					}
					//设备型号
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.EQPTYPE)){
						BaseParams.setEqpTypesCombobox(BeanConvertor.copyList(eqpTypeServiceI.queryMdType(null), Combobox.class));
					}
					//所有设备
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.ALLEQPS)){
						BaseParams.setAllEqpsCombobox(BeanConvertor.copyList(equipmentsService.queryAllEqpsForComboBox(), Combobox.class));
					}
					//所有卷烟机
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.ALLROLLERS)){
						BaseParams.setAllRollersCombobox(BeanConvertor.copyList(equipmentsService.queryAllPackersForComboBox(1), Combobox.class));
					}
					//所有包装机
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.ALLPACKERS)){
						BaseParams.setAllPackersCombobox(BeanConvertor.copyList(equipmentsService.queryAllPackersForComboBox(2), Combobox.class));
					}
					//所有封箱机
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.ALLBOXERS)){
						BaseParams.setAllBoxersCombobox(BeanConvertor.copyList(equipmentsService.queryAllPackersForComboBox(3), Combobox.class));
					}
					//所有成型机
					if(scope.equalsIgnoreCase(ComboboxType.ALL)||scope.equalsIgnoreCase(ComboboxType.ALLFILTERS)){
						BaseParams.setAllFiltersCombobox(BeanConvertor.copyList(equipmentsService.queryAllPackersForComboBox(4), Combobox.class));
					}
				} catch (Exception e) {
					log.error("初始数据系统基础数据异常", e);
				}
	}
	
	/**
	 * 加载转换PDF 转换 SWF需要用到的进程
	 */
	public void initSoffice(){
		/*ResourceBundle bundle = ResourceBundle.getBundle("config");
		String officePath = bundle.getString("openOfficePath");
		String command = officePath
				+ "\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
		try {
			Process pro = Runtime.getRuntime().exec(command);// 单独的进程中执行指定的字符串命令
		} catch (IOException e) {
			log.error("初始进程", e);
		}*/
	}

	@Override
	public void getAllMatParams(){
		//保存缓存
		BaseParams.setAllMdMatParam(equipmentsService.getAllMdMatParamData());
	}

	/**
	 * {功能说明}: 润滑导入-基础数据转换
	 * 
	 * @author  wanchanghuang 
	 * @time 2015年9月9日13:58:04
	 * 
	 * */
	@Override
	public void querySysEqpType() {
		BaseParams.setListSysEqpType(equipmentsService.querySysEqpType());
	}
	
	@Override
	public void initAllRunnedWorkOrderCalcValues(){
		try {
			WorkOrderServiceI schWorkorderService=ApplicationContextUtil.getBean(WorkOrderServiceI.class);
			schWorkorderService.initAllRunnedWorkOrderCalcValues();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
