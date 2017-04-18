package com.shlanbao.tzsc.pms.sys.repairResquest.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.repairResquest.beans.RepairResquestBean;

public interface RepairResquestServiceI {
	
	/**
	 * 添加维修工
	 * @author 景孟博
	 * @param repairResquestuserBean
	 * @throws Exception
	 */
	public void addFixUser(RepairResquestBean repairResquestuserBean,MultipartFile file)throws Exception;
	
	
	/**
	 * 查询维修工信息
	 * @author 景孟博
	 * @param repairResquestuserBean
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryFixUser(RepairResquestBean repairResquestuserBean,PageParams pageParams)throws Exception;
	
	
	/**
	 * 更新维修工信息
	 * @author 景孟博
	 * @param bean
	 * @throws Exception
	 */
	public void updateFixUser(RepairResquestBean bean,MultipartFile file)throws Exception;
	
	
	/**
	 * 通过id查询维修工信息
	 * @author 景孟博
	 * @param id
	 * @param updateUsrId
	 * @param updateUserName
	 * @return
	 * @throws Exception
	 */
	public RepairResquestBean getFixUserById(String id, String updateUsrId, String updateUserName)throws Exception;
	
	
	/**
	 * 删除维修工信息
	 * @author 景孟博
	 * @param id
	 * @throws Exception
	 */
	public void deleteFixUser(String id)throws Exception;
	
	
	/**
	 * 批量删除维修工信息
	 * @author 景孟博
	 * @param ids
	 * @throws Exception
	 */
	public void batchDelete(String ids)throws Exception;
	/**
	 * 查询wct维修呼叫处于等待状态的请求信息，用于推送消息
	 * shisihai
	 */

	public List<RepairResquestBean> queryAskInfo();
	
	

}
