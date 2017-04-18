package com.shlanbao.tzsc.wct.qm.proc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmProcFileDao;
import com.shlanbao.tzsc.base.dao.QmProcManageDao;
import com.shlanbao.tzsc.base.mapping.QmProcFile;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.wct.qm.proc.beans.QmProcWctFileBean;
import com.shlanbao.tzsc.wct.qm.proc.service.QmProcWscService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 工艺规程业务实现类
 * @author luther.zhang
 * @create 2014-12-22
 */
@Service
public class QmProcWctServiceImpl extends BaseService implements QmProcWscService{
	@Autowired
	private QmProcManageDao qmProcManageDao;
	@Autowired
	private QmProcFileDao qmProcFileDao;
	
	@Override
	public DataGrid queryList(QmProcWctFileBean bean, int pageIndex) throws Exception{
		
		StringBuffer hql=new StringBuffer();
		hql.append("from QmProcFile qf ");
		hql.append("left join fetch qf.qmProcManage qm ");
		hql.append("left join fetch qf.qmProcManage.mdMat mat ");//品名(牌号)
		hql.append("where qf.isDeleted=0 and qm.procStop=0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(bean.getProcStatus())){//审核状态
			hql.append("and qm.procStatus = ? ");
			params.add(bean.getProcStatus());
		}
		if(StringUtil.notNull(bean.getProcId())){//品名
			hql.append("and mat.id = ? ");
			params.add(bean.getProcId());
		}
		if(StringUtil.notNull(bean.getProcSection())){//工段
			hql.append("and qm.procSection like ? ");
			params.add("%"+bean.getProcSection()+"%");
		}
		if(StringUtil.notNull(bean.getFileName())){//文件名
			hql.append("and qf.fileName like ? ");
			params.add("%"+bean.getFileName()+"%");
		}
		try {
			long total=qmProcFileDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			List<QmProcWctFileBean> list= new ArrayList<QmProcWctFileBean>();
			if(total>0){
				hql.append(" order by qf.createTime desc ");//排序
				List<QmProcFile> rows = qmProcFileDao.queryByPage(hql.toString(), pageIndex,10,params);
				
				for(QmProcFile file:rows){
					QmProcWctFileBean manageBean = BeanConvertor.copyProperties(file, QmProcWctFileBean.class);
					
					if(null!=file.getQmProcManage()&&null!=file.getQmProcManage().getMdMat()){//工艺规程品名(牌号) private MdMat mdMat;
						manageBean.setProcId(file.getQmProcManage().getMdMat().getId());//品名(牌号) 
						manageBean.setMdMatName(file.getQmProcManage().getMdMat().getName());
					}
					if(null!=file.getQmProcManage()&&null!=file.getQmProcManage().getProcSection()){//工段
						manageBean.setProcSection(file.getQmProcManage().getProcSection());
					}
					if(null!=file.getFileId()){
						manageBean.setUploadUrl(file.getFileId());
					}
					list.add(manageBean);
				}
			}
			return new DataGrid(list, total);
		} catch (Exception e) {
			log.error("POVO转换异常", e);
		}
		return null;
	}
	
}




