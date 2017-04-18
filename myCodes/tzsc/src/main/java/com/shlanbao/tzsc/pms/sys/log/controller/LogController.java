package com.shlanbao.tzsc.pms.sys.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.log.beans.LogBean;
import com.shlanbao.tzsc.pms.sys.log.service.LogServiceI;
/**
 * 系统日志控制器
 * @author Leejean
 * @create 2014年9月4日上午11:39:26
 */
@Controller
@RequestMapping("/pms/log")
public class LogController extends BaseController {
	@Autowired
	private LogServiceI logService;
	/**
	 * 查询系统日志
	 * @author Leejean
	 * @create 2014年9月4日上午11:42:38
	 * @param logBean 查询条件
	 * @param pageParams 分页参数
	 * @return
	 */
	@RequestMapping("/getAllLogs")
	@ResponseBody
	public DataGrid getAllLogs(LogBean logBean,PageParams pageParams){
		try {
			return logService.getAllLogs(logBean,pageParams);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/**
	 * 删除
	 * @author Leejean
	 * @create 2014年9月5日上午10:02:21
	 * @return
	 */
	@RequestMapping("/deleteLog")
	@ResponseBody
	public Json deleteLog(String id){
		Json json=new Json();
		try {
			logService.deleteLog(id);
			json.setMsg("删除成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除成功");
			log.error(message, e);
		}
		return json;
	}
	/**
	 * 批量删除
	 * @author Leejean
	 * @create 2014年9月5日上午10:02:26
	 * @return
	 */
	@RequestMapping("/batchDeleteLogs")
	@ResponseBody
	public Json batchDeleteLogs(String ids){
		Json json=new Json();
		try {
			logService.batchDeleteLogs(ids);
			json.setMsg("删除成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除成功");
			log.error(message, e);
		}
		return json;
	}
}
