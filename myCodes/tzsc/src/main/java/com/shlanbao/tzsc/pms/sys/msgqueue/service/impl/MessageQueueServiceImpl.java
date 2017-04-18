package com.shlanbao.tzsc.pms.sys.msgqueue.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SysMessageQueueDaoI;
import com.shlanbao.tzsc.base.mapping.SysMessageQueue;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.msgqueue.beans.MessageQueueBean;
import com.shlanbao.tzsc.pms.sys.msgqueue.service.MessageQueueServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 消息队列
 * @author Leejean
 * @create 2014年12月5日上午11:23:51
 */
@Service
public class MessageQueueServiceImpl extends BaseService implements
		MessageQueueServiceI {
	@Autowired
	private SysMessageQueueDaoI sysMessageQueueDao;

	@Override
	public void saveMessageQueue(MessageQueueBean messageQueueBean)
			throws Exception {
		sysMessageQueueDao.save(new SysMessageQueue(messageQueueBean.getMsgType(),
				messageQueueBean.getContent(), 
				messageQueueBean.getSysSend(), 
				messageQueueBean.getSysReceive(),
				new Date(), 
				0L));
	}

	@Override
	public DataGrid getAllMessageQueues(MessageQueueBean messageQueueBean,
			PageParams pageParams) throws Exception {
		String hql="from SysMessageQueue o where o.del='0' ";
		if(messageQueueBean.getMsgType()!=null){			
			hql=hql.concat(" and o.msgType = "+messageQueueBean.getMsgType());
		}
		if(messageQueueBean.getFlag()!=null){
			hql=hql.concat(" and o.flag = "+messageQueueBean.getFlag());
		}
		if(messageQueueBean.getSysSend()!=null){			
			hql=hql.concat(" and o.sysSend = "+messageQueueBean.getSysSend());
		}
		if(messageQueueBean.getSysReceive()!=null){			
			hql=hql.concat(" and o.sysReceive = "+messageQueueBean.getSysReceive());
		}
		if(StringUtil.notNull(messageQueueBean.getDes())){			
			hql=hql.concat(" and o.des like '%"+messageQueueBean.getDes()+"%'");
		}
		if(StringUtil.notNull(messageQueueBean.getDate_())){			
			hql=hql.concat(" and convert(varchar,o.date_,120) like '"+messageQueueBean.getDate_()+"%'");
		}
		if(messageQueueBean.getContent()!=null){
			hql=hql.concat(" and o.content like '%"+messageQueueBean.getContent()+"%'");
		}
		return new DataGrid(
				BeanConvertor.copyList(sysMessageQueueDao.queryByPage(hql+" order by o.date_ desc", pageParams), 
						MessageQueueBean.class),sysMessageQueueDao.queryTotal("select count(1) ".concat(hql)));
	
	}

	@Override
	public void batchDeleteMessageQueues(String ids) throws Exception {
		for (String id : StringUtil.splitToStringList(ids, ",")) {
			this.deleteMessageQueue(id);
		}
	}

	@Override
	public void deleteMessageQueue(String id) throws Exception {
		sysMessageQueueDao.updateByParams("update SysMessageQueue o set o.del='1' where o.id=?", id);
	}
}
