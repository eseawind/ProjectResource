package com.shlanbao.tzsc.pms.sys.msgqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.msgqueue.beans.MessageQueueBean;
import com.shlanbao.tzsc.pms.sys.msgqueue.service.MessageQueueServiceI;
/**
 * 接口日志
 * @author Leejean
 * @create 2015年1月22日下午3:45:16
 */
@Controller
@RequestMapping("/pms/msgqueue")
public class MessageQueueController extends BaseController {
	@Autowired
	private MessageQueueServiceI messageQueueService;
	/**
	 * 查询接口日志
	 * @author Leejean
	 * @create 2014年9月4日上午11:42:38
	 * @param logBean 查询条件
	 * @param pageParams 分页参数
	 * @return
	 */
	@RequestMapping("/getAllMessageQueues")
	@ResponseBody
	public DataGrid getAllMessageQueues(MessageQueueBean messageQueueBean,PageParams pageParams){
		try {
			return messageQueueService.getAllMessageQueues(messageQueueBean,pageParams);
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
	@RequestMapping("/deleteMessageQueue")
	@ResponseBody
	public Json deleteMessageQueue(String id){
		Json json=new Json();
		try {
			messageQueueService.deleteMessageQueue(id);
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
	@RequestMapping("/batchDeleteMessageQueues")
	@ResponseBody
	public Json batchDeleteMessageQueues(String ids){
		Json json=new Json();
		try {
			messageQueueService.batchDeleteMessageQueues(ids);
			json.setMsg("删除成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除成功");
			log.error(message, e);
		}
		return json;
	}
}
