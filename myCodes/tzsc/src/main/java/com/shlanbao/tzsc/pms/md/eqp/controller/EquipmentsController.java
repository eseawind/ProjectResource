package com.shlanbao.tzsc.pms.md.eqp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.spare.controller.InputFilesController;
import com.shlanbao.tzsc.pms.equ.overhaul.beans.EqmMaintainBean;
import com.shlanbao.tzsc.pms.md.eqp.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.md.eqp.service.EquipmentsServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;

/**
 * 设备主数据管理控制器
 * 
 * @author liuligong
 * @Time 2014/11/5 10:45
 */
@Controller
@RequestMapping("/pms/equc")
public class EquipmentsController extends BaseController {
	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private EquipmentsServiceI equipmentsService;

	@RequestMapping("/goToEqucMain")
	public String goToEqucMain(HttpServletRequest request) {
		return "/pms/equ/main/equAdd";
	}

	// 设备主数据新增
	@ResponseBody
	@RequestMapping("/addEqu")
	public Json addEqu(MdEquipment equBean) {
		Json json = new Json();
		try {
			equipmentsService.addEqu(equBean);
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
	@RequestMapping("/queryEqu")
	public DataGrid queryEqu(EquipmentsBean equBean, PageParams pageParams) {
		try {
			DataGrid gd = equipmentsService.queryEqu(equBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备主数据异常。", e);
		}
		return null;
	}

	@RequestMapping("/goToEquEdit")
	public String goToEquEdit(HttpServletRequest request, String id) {
		try {
			request.setAttribute("equBean", equipmentsService.getEquById(id));
		} catch (Exception e) {
			log.error("编辑设备主数据异常:", e);
		}
		return "/pms/equ/main/equEdit";
	}

	@ResponseBody
	@RequestMapping("/deleteEqu")
	public Json deleteEqu(String id) {
		Json json = new Json();
		try {
			equipmentsService.deleteEqu(id);
			json.setMsg("删除设备主数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除设备主数据失败!");
			json.setSuccess(false);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("/getAllEqpType")
	public List<Combobox> getAllEqpType(String id) {
		try {
			return equipmentsService.getAllEqpType(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/queryAllEquipments")
	public List<EquipmentsBean> queryAllEquipments() {
		try {
			return equipmentsService.queryAllEquipments();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * excel導出 2015.9.9--张璐
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/excelDeriveJuanBaoCP")
	public void excelDeriveJuanBaoCP(EquipmentsBean shopShiftBean,HttpServletResponse response) throws Exception {

		HSSFWorkbook wb = equipmentsService.ExportExcelJBPP2(shopShiftBean);
		String addtime = DateUtil.getNowDateTime("yyyyMMddHHmmSSS");
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ addtime + ".xls");
		ServletOutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();

	}

	@ResponseBody
	@RequestMapping("/inputExeclAndReadWrite")
	public Json inputExeclAndReadWrite(
			@RequestParam(value = "importFile", required = false) MultipartFile file,
			HttpServletRequest request) {
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
		String bm = fileName.substring((fileName.lastIndexOf(".") + 1),
				fileName.length());
		List<MdEquipment> list = null;
		InputFilesController fl = new InputFilesController();
		if ("xls".equals(bm)) {
			list = fl.readXls2003_4(url);
		} else if ("xlsx".equals(bm)) {
			list = fl.readXlsx2007_4(url);
		}
		if (list != null && list.size() > 0) {
				equipmentsService.inputExeclAndReadWrite(list);
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
