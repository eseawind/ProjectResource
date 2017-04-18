package com.shlanbao.tzsc.pms.sys.log.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SysLogDaoI;
import com.shlanbao.tzsc.base.mapping.SysLog;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.log.beans.LogBean;
import com.shlanbao.tzsc.pms.sys.log.service.LogServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 操作日志业务实现类
 * @author Leejean
 * @create 2014年9月16日上午10:40:06
 */
@Service
public class LogServiceImpl extends BaseService implements LogServiceI {
	@Autowired
	private SysLogDaoI sysLogDao;
	private static List<LogBean> logbeans=new ArrayList<LogBean>();
	
	public static List<LogBean> getLogbeans() {
		return logbeans;
	}

	public static void setLogbeans(List<LogBean> logbeans) {
		LogServiceImpl.logbeans = logbeans;
	}

	@Override
	public void saveLog(LogBean logBean) {		
		try {
			SysLog log=BeanConvertor.copyProperties(logBean, SysLog.class);
					log.setDate(new Date());
			sysLogDao.save(log);
		} catch (Exception e) {
			log.error(message, e);
		}
	}

	@Override
	public DataGrid getAllLogs(LogBean logBean, PageParams pageParams)
			throws Exception {
		String hql="from SysLog o where o.del=0 ";
		if(StringUtil.notNull(logBean.getName())){			
			hql=hql.concat(" and o.name like '%"+logBean.getName()+"%'");
		}
		if(StringUtil.notNull(logBean.getOptname())){			
			hql=hql.concat(" and o.optname like '%"+logBean.getOptname()+"%'");
		}
		if(StringUtil.notNull(logBean.getSys())){			
			hql=hql.concat(" and o.sys like '%"+logBean.getSys()+"%'");
		}
		hql=hql.concat(StringUtil.fmtDateBetweenParams("o.date", logBean.getDate(), logBean.getDate2()));
		return new DataGrid(
				BeanConvertor.copyList(sysLogDao.queryByPage(hql, pageParams), 
						LogBean.class),sysLogDao.queryTotal("select count(1) ".concat(hql)));
	}

	@Override
	public void batchDeleteLogs(String ids) throws Exception {
		for(String id : StringUtil.splitToStringList(ids, ",")){
			this.deleteLog(id);
		}
	}

	@Override
	public void deleteLog(String id) throws Exception {
		sysLogDao.updateByParams("update SysLog o set o.del=1 where o.id=? ", id);
	}

}
