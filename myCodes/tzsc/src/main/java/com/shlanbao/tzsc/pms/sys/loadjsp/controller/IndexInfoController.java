package com.shlanbao.tzsc.pms.sys.loadjsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.pms.sys.loadjsp.beans.IndexInfoBean;
import com.shlanbao.tzsc.pms.sys.loadjsp.beans.IndexfileBean;
import com.shlanbao.tzsc.pms.sys.loadjsp.beans.JuanBaoBean;
import com.shlanbao.tzsc.pms.sys.loadjsp.service.IndexFileServiceI;
import com.shlanbao.tzsc.pms.sys.loadjsp.service.IndexInfoServiceI;
import com.shlanbao.tzsc.pms.sys.loadjsp.service.JuanBaoServiceI;
@Controller
@RequestMapping("/pms/indexInfo")
public class IndexInfoController extends BaseController {
	@Autowired
	private IndexInfoServiceI IndexinfoServiceI;
	@Autowired
	private IndexFileServiceI IndexfileServiceI;
	@Autowired
	private JuanBaoServiceI juanbaoServiceI;
	@ResponseBody
	@RequestMapping("/getInfoAll")
	public List<IndexInfoBean> getInfoAll(){
	 return	IndexinfoServiceI.getList();
	}
	@ResponseBody
	@RequestMapping("/getIndexfileAll")
	public List<IndexfileBean> getIndexfileAll(){
		try {
			return IndexfileServiceI.getfileAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/getIndexQtyAll")
	public JuanBaoBean getIndexQtyAll(String type,int begin,int end){
		try {
			return juanbaoServiceI.getJuanBaoBean(type, begin, end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
