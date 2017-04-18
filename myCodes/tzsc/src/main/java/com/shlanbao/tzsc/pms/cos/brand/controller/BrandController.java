package com.shlanbao.tzsc.pms.cos.brand.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.brand.beans.SeachBean;
import com.shlanbao.tzsc.pms.cos.brand.service.BrandServiceI;
import com.shlanbao.tzsc.pms.isp.beans.RollerPackerGroup;
import com.shlanbao.tzsc.pms.isp.service.PmsIspServiceI;
import com.shlanbao.tzsc.utils.tools.SystemConstant;
/**
 * @ClassName: BrandController 
 * @Description: 成本管理 
 * @author luo
 * @date 2015年1月21日 下午2:55:34 
 *
 */
@Controller
@RequestMapping("/pms/barand")
public class BrandController extends BaseController {
	
	@Autowired
	private BrandServiceI brandService;
	
	@Autowired
	private PmsIspServiceI pmsIspService;
	
	/**
	* @Title: queryBrand_juanbao 
	* @Description: 查询卷包机组综合成本统计
	* @param  seachBean
	* @param  pageParams
	* @param  request
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryBrand_juanbao")
	public DataGrid queryBrand_juanbao(SeachBean seachBean,PageParams pageParams,HttpServletRequest request){
		try {
			seachBean.setWorkshop(SystemConstant.PAGEAING_AREA);
			List<RollerPackerGroup> rollerPackerGroup=pmsIspService.initRollerPackerGroups(request);
			DataGrid gd = brandService.queryBrandCos_juanbao(false,false,seachBean, pageParams,rollerPackerGroup);
			return gd;
		} catch (Exception e) {
			log.error("牌号统计异常", e);
		}
		return null;
	}
	/**
	* @Title: queryBrand_chengxing 
	* @Description: 成型车间综合成本考核查询
	* @param  seachBean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryBrand_chengxing")
	public DataGrid queryBrand_chengxing(SeachBean seachBean,PageParams pageParams){
		try {
			seachBean.setWorkshop(SystemConstant.FILTER_AREA);
			DataGrid gd = brandService.queryBrand_chengxing(false,false,seachBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("牌号统计异常", e);
		}
		return null;
	}
	
	/**
	* @Title: queryBrand_juanjie 
	* @Description: 卷接机成本管理考核统计
	* @param  seachBean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryBrand_juanjie")
	public DataGrid queryBrand_juanjie(SeachBean seachBean,PageParams pageParams){
		try {
			seachBean.setWorkshop(SystemConstant.PAGEAING_AREA);
			seachBean.seteType(SystemConstant.WORKORDER_TYPE_JJ);
			DataGrid gd = brandService.queryBrand_chengxing(false,false,seachBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("牌号统计异常", e);
		}
		return null;
	}
	
	/**
	* @Title: queryBrand_juanjie 
	* @Description: 包装机成本管理考核统计
	* @param  seachBean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryBrand_baozhuang")
	public DataGrid queryBrand_baozhuang(SeachBean seachBean,PageParams pageParams){
		try {
			seachBean.setWorkshop(SystemConstant.PAGEAING_AREA);
			seachBean.seteType(SystemConstant.WORKORDER_TYPE_BZ);
			DataGrid gd = brandService.queryBrand_chengxing(false,false,seachBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("牌号统计异常", e);
		}
		return null;
	}
	
	/**
	* @Title: queryBrand_juanbao 
	* @Description: 查询卷包机组综合成本统计并且统计残烟丝量
	* @param  seachBean
	* @param  pageParams
	* @param  request
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryBrandAndDis_juanbao")
	public DataGrid queryBrandAndDis_juanbao(SeachBean seachBean,PageParams pageParams,HttpServletRequest request){
		try {
			seachBean.setWorkshop(SystemConstant.PAGEAING_AREA);
			List<RollerPackerGroup> rollerPackerGroup=pmsIspService.initRollerPackerGroups(request);
			DataGrid gd = brandService.queryBrandCos_juanbao(true,false,seachBean, pageParams,rollerPackerGroup);
			return gd;
		} catch (Exception e) {
			log.error("卷包机组成本统计异常", e);
		}
		return null;
	}
	
	/**
	* @Title: queryBrand_juanbao 
	* @Description: 查询成型机组综合成本统计并且统计残烟丝量
	* @param  seachBean
	* @param  pageParams
	* @param  request
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryBrandAndDis_chengxing")
	public DataGrid queryBrandAndDis_chengxing(SeachBean seachBean,PageParams pageParams,HttpServletRequest request){
		try {
			seachBean.setWorkshop(SystemConstant.PAGEAING_AREA);
			List<RollerPackerGroup> rollerPackerGroup=pmsIspService.initRollerPackerGroups(request);
			DataGrid gd = brandService.queryBrandCos_juanbao(true,false,seachBean, pageParams,rollerPackerGroup);
			return gd;
		} catch (Exception e) {
			log.error("牌号统计异常", e);
		}
		return null;
	}
}
