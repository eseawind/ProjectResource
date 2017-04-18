package com.shlanbao.tzsc.wct.msg.warn.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.MsgInfoDaoI;
import com.shlanbao.tzsc.base.mapping.MsgConWarn;
import com.shlanbao.tzsc.base.mapping.MsgInfo;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.msg.warn.beans.ConWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.MsgInfoBean;
import com.shlanbao.tzsc.wct.msg.warn.service.WctMsgInfoServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class WctMsgInfoServiceImpl extends BaseService implements WctMsgInfoServiceI {
	@Autowired
	private MsgInfoDaoI msgInfoDaoI;
	@Override
	public List<MsgInfoBean> getAll() {//查询大于昨天的所有通知
		/*String sql="select t.* from (select  id,title,content,ROW_NUMBER () OVER ( ORDER BY time desc ) f from MSG_INFO where del='0' and flag='3'  ) t where 1=1 and t.f between  1 and 4";*/
		String sql="select t.* from (select  id,title,content,ROW_NUMBER () OVER ( ORDER BY time desc ) f from MSG_INFO where del='0' ) t where 1=1 and t.f between  1 and 4";
		List<Object[]> objs=(List<Object[]>) msgInfoDaoI.queryBySql(sql);
		List<MsgInfoBean> msgInfos=new ArrayList<MsgInfoBean>();
		if(objs!=null){		
			for (Object[] obj : objs) {
				MsgInfoBean infoBean=new MsgInfoBean();
				infoBean.setId(obj[0].toString());
				infoBean.setTitle(obj[1].toString());
				infoBean.setContent(obj[2].toString());
				msgInfos.add(infoBean);
			}
		}
		return msgInfos;
	}
	@Override
	public DataGrid getInfoList(MsgInfoBean infoBean, int pageIndex,
			HttpSession session) throws Exception {
		LoginBean  loginbean=  (LoginBean) session.getAttribute("loginInfo");
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  String str = dateFormat.format(new Date());
		String hql="from MsgInfo m where 1=1 and m.del='0' and m.flag='3'";	
		if(!StringUtil.notNull(infoBean.getStim()))
			infoBean.setStim(str);
		if(!StringUtil.notNull(infoBean.getEtim())){
			infoBean.setEtim(str+" 23:59:59");
		}else{
			infoBean.setEtim(infoBean.getEtim()+" 23:59:59");			
		}
		hql+=StringUtil.fmtDateBetweenParams("m.time", infoBean.getStim(), infoBean.getEtim());
		List<MsgInfo> msgInfos=msgInfoDaoI.queryByPage(hql, pageIndex, 10);
		List<MsgInfoBean> infoBeans=new ArrayList<MsgInfoBean>();
		if(msgInfos!=null){
			for (MsgInfo inf : msgInfos) {
				MsgInfoBean warnBean=BeanConvertor.copyProperties(inf, MsgInfoBean.class);
				if(inf.getSysUserByIssuer()!=null){
					warnBean.setIssuerId(inf.getSysUserByIssuer().getId());
					warnBean.setIssuerName(inf.getSysUserByIssuer().getName());
				}
				infoBeans.add(warnBean);
			}
		}
		long t=msgInfoDaoI.queryTotal("select count(*) "+ hql);
		return new DataGrid(infoBeans, t);
	
	}
	@Override
	public void updateInfoFlagAndFour(String id) {
		msgInfoDaoI.findById(MsgInfo.class, id).setFlag(4L);;
		
	}

}
