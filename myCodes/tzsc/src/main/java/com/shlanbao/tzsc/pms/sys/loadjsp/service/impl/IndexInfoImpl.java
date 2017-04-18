package com.shlanbao.tzsc.pms.sys.loadjsp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgInfoDaoI;
import com.shlanbao.tzsc.base.mapping.MsgInfo;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sys.loadjsp.beans.IndexInfoBean;
import com.shlanbao.tzsc.pms.sys.loadjsp.service.IndexInfoServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
@Service
public class IndexInfoImpl extends BaseService implements IndexInfoServiceI {
	@Autowired
	private MsgInfoDaoI IndexInfodaoI; 
	@Override
	public List<IndexInfoBean> getList() {
		List<IndexInfoBean> lastList = new ArrayList<IndexInfoBean>();
		String hql="from MsgInfo where del='0' and flag='1' and time>=? order by time asc";
		List<Object> params = new ArrayList<Object>();
		params.add(DateUtil.strToDate(DateUtil.getNowDateTime("yyyy-MM-dd"), "yyyy-MM-dd"));
		
		List<MsgInfo> beans=IndexInfodaoI.query(hql, params);//.query(hql);
		if(beans!=null&&beans.size()>0){
			try {
				for(MsgInfo msgBean:beans){
					IndexInfoBean bean = BeanConvertor.copyProperties(msgBean, IndexInfoBean.class);
					lastList.add(bean);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lastList;
		}
		return lastList;
	}

}
