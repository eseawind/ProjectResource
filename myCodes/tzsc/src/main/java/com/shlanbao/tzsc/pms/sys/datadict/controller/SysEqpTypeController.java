package com.shlanbao.tzsc.pms.sys.datadict.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.mapping.SysEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;
import com.shlanbao.tzsc.pms.cos.spare.controller.InputFilesController;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpCategoryServiceI;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpCategoryBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpTypeServiceI;
import com.shlanbao.tzsc.utils.tools.SystemConstant;

/**
 * 数据字典维护
 */
@Controller
@RequestMapping("/pms/sys/eqptype")
public class SysEqpTypeController {
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SysEqpTypeServiceI mdEqpTypeService;
	@Autowired
	private SysEqpCategoryServiceI mdEqpCategoryService;

	@RequestMapping("/goToMdTypeJsp")
	public String goToMdType(HttpServletRequest request, String id) {
		if (null != id) {
			try {
				request.setAttribute("mdEqpTypeBean",
						mdEqpTypeService.getTypeById(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "/pms/sys/datadict/addMdType";
	}

	@RequestMapping("/AddMdTypeJsp")
	public String goToMdType(String cateid, HttpServletRequest request)
			throws Exception {
		SysEqpCategory category = mdEqpCategoryService
				.getMdCategoryById(cateid);
		SysEqpTypeBean eqpTypeBean = new SysEqpTypeBean();
		eqpTypeBean.setCategoryId(category.getId());
		eqpTypeBean.setCategoryName(category.getName());
		request.setAttribute("mdEqpTypeBean", eqpTypeBean);
		return "/pms/sys/datadict/addMdType";
	}

	// 设备类型新增
	@ResponseBody
	@RequestMapping("/addMdType")
	public Json addMdType(SysEqpTypeBean mdEqpTypeBean) {
		Json json = new Json();
		try {
			mdEqpTypeService.addMdType(mdEqpTypeBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("/queryMdType")
	public DataGrid queryMdType(SysEqpTypeBean mdTypeBean, PageParams pageParams) {
		try {
			DataGrid gd = mdEqpTypeService.queryMdType(mdTypeBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询异常。", e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/queryMdEqpType")
	public List<SysEqpTypeBean> queryMdEqpType() {
		try {
			return mdEqpTypeService.queryMdType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/deleteMdType")
	public Json deleteMdType(String id) {
		Json json = new Json();
		try {
			mdEqpTypeService.deleteMdType(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("删除数据异常。", e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}

	// 获取卷烟机、包装机和成型机的机型
	@ResponseBody
	@RequestMapping("/queryMdEqpTypeByCategory")
	public List<SysEqpTypeBean> queryMdEqpTypeByCategory() {
		try {
			String[] categorys = new String[3];
			categorys[0] = SystemConstant.CATEGORY_JY_ID;
			categorys[1] = SystemConstant.CATEGORY_BZ_ID;
			categorys[2] = SystemConstant.CATEGORY_CX_ID;
			return mdEqpTypeService.queryMdEqpTypeByCategory(categorys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/gotoHotspotJsp")
	public String gotoHotspot(SysEqpTypeBean bean, HttpServletRequest request) {
		try {
			// 需要查询 不然有乱码
			SysEqpTypeBean queryBean = mdEqpTypeService.getTypeById(bean
					.getTwoRowId());
			queryBean.setUploadUrl(bean.getUploadUrl());// 图片地址
			queryBean.setTwoRowId(bean.getTwoRowId());// 子节点 主键ID
			queryBean.setContextPath(bean.getContextPath());// 项目路径
			queryBean.setContextDes(queryBean.getSetImagePoint());// 内容
			request.setAttribute("mdEqpTypeBean", queryBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/sys/datadict/imagesHotspot/hotspot";
	}

	/**
	 * 图片设置
	 * 
	 * @param id
	 *            主键ID
	 * @param result
	 *            设置的内容
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setUpImage")
	public Json setUpImage(String id, String result, String filePath,
			HttpSession session, HttpServletRequest request) {
		Json json = new Json();
		try {
			// setImagePoint
			mdEqpTypeService.addSetUpImages(id, result, filePath);
			json.setMsg("设置成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("设置异常", e);
			json.setMsg("设置失败！");
			json.setSuccess(false);
		}
		return json;
	}

	/**
	 * 【功能说明】：数据字典批量导入功能- 添加解析excel
	 * 
	 * @time 2015年8月2日14:50:16
	 * @param id
	 * @param request
	 * @return path -页面路径
	 * 
	 * */
	@RequestMapping("/inputExeclAndReadWrite")
	@ResponseBody
	public Json inputExeclAndReadWrite(
			@RequestParam(value = "importFile", required = false) MultipartFile file,
			HttpServletRequest request, String type, SysEqpTypeBean etb) {
		Json json = new Json();
		try {
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			String fileName = file.getOriginalFilename();
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			file.transferTo(targetFile);
			String url = path + "/" + fileName;
			
			String bm=null;
			try {
				bm = fileName.substring((fileName.lastIndexOf(".") + 1),
						fileName.length());
			} catch (Exception e) {
				json.setMsg("文件后缀错误！导入失败");
				json.setSuccess(false);
				return json;
			}
			List<SysEqpTypeBean> list = null;
			InputFilesController fl = new InputFilesController();
			if ("xls".equals(bm)) {
				list = fl.readXls2003_2(url, type);
			} else if ("xlsx".equals(bm)) {
				list = fl.readXlsx2007_2(url, type);
			}
			if (list != null && list.size() > 0) {
				mdEqpTypeService.inputExeclAndReadWrite(list, etb);
				json.setMsg("导入成功！");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			json.setMsg("导入失败！");
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}
}
