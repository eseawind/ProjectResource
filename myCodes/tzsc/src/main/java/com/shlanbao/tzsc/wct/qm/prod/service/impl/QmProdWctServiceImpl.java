package com.shlanbao.tzsc.wct.qm.prod.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmProdFileDao;
import com.shlanbao.tzsc.base.mapping.QmProdFile;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.qm.prod.beans.QmProdFileBean;
import com.shlanbao.tzsc.wct.qm.prod.service.QmProdWctService;

/**
 * 产品规程业务实现类
 * 
 * @author luther.zhang
 * @create 2014-12-22
 */
@Service
public class QmProdWctServiceImpl extends BaseService implements
		QmProdWctService {
	@Autowired
	private QmProdFileDao qmProdFileDao;

	@Override
	public DataGrid queryList(QmProdFileBean query, int pageIndex) throws Exception {
		
		StringBuilder hql = new StringBuilder();
		hql.append("from QmProdFile qf "
				+ "left join fetch  qf.qmProdManage qm  "
				+ "left join fetch  qf.qmProdManage.mdMat mat  "
				+ "where qf.isDeleted=0 and qm.prodStop=0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notNull(query.getProdStatus())){//审核状态
			hql.append("and qm.prodStatus = ? ");
			params.add(query.getProdStatus());
		}
		if (StringUtil.notNull(query.getId())) {// 品名
			hql.append("and mat.id =? ");
			params.add(query.getId());
		}
		if (StringUtil.notNull(query.getFileName())) {// 文件名
			hql.append("and qf.fileName like ? ");
			params.add("%"+query.getFileName()+"%");
		}
		if (StringUtil.notNull(query.getProdNumber())) {// 编号
			hql.append("and qm.prodNumber like  ? ");
			params.add("%"+query.getProdNumber()+"%");
		}
		try {
			long total=qmProdFileDao.queryTotal("select count(*) "+hql.toString().replaceAll("fetch", ""),params);
			List<QmProdFileBean> list= new ArrayList<QmProdFileBean>();
			if(total>0){
				hql.append(" order by qf.createTime desc ");//排序
				List<QmProdFile> rows = qmProdFileDao.queryByPage(hql.toString(), pageIndex,10,params);
				
				for(QmProdFile file:rows){
					QmProdFileBean manageBean = BeanConvertor.copyProperties(file, QmProdFileBean.class);
					if(null!=file.getQmProdManage()&&null!=file.getQmProdManage().getMdMat()){//品名
						manageBean.setMdMatName(file.getQmProdManage().getMdMat().getName());
					}
					if(null!=file.getQmProdManage()){//编号
						manageBean.setProdNumber(file.getQmProdManage().getProdNumber());
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
