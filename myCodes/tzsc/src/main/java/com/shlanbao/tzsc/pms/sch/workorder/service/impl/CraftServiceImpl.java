package com.shlanbao.tzsc.pms.sch.workorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchWorkorderCraftDaoI;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.workorder.beans.CraftBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.CraftServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class CraftServiceImpl extends BaseService implements CraftServiceI{
	@Autowired
	private SchWorkorderCraftDaoI schWorkorderCraftDao;

	@Override
	public DataGrid queryCraftByWorkOrder(String workOrderId) {
		try {
			String hql = "from SchWorkorderCraft o where o.schWorkorder.id=?";
			return new DataGrid(BeanConvertor.copyList(schWorkorderCraftDao.query(hql, workOrderId),CraftBean.class), 0L);
		} catch (Exception e) {
			log.error("查询工单ID:["+workOrderId+"]的工艺参数异常", e);
		}
		return null;
	}
}
