package com.lanbao.dws.service.wct.qm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.ibm.framework.dal.transaction.template.CallBackTemplate;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.JsonBean;
import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.qm.QMOutWandDelInfo;
import com.lanbao.dws.model.wct.qm.QMOutWandItem;
import com.lanbao.dws.model.wct.qm.QMOutWard;
import com.lanbao.dws.model.wct.qm.QMUser;
import com.lanbao.dws.model.wct.qm.QmAppearance;
import com.lanbao.dws.model.wct.qm.QmOutWandInfoBean;
import com.lanbao.dws.service.wct.qm.IAppearanceCheckService;
/**
 * 外观检测
 * @author shisihai
 */
@Service
public class AppearanceCheckServiceImpl implements IAppearanceCheckService{
	//private Logger log = Logger.getLogger(this.getClass());
	@Autowired
    IPaginationDalClient dalClient;
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月7日 上午10:38:09 
	* 功能说明 ：质量外观检测登陆
	 */
	@Override
	public String qmLogin(QMUser token) {
		Map<String,Object> params=new HashMap<>();
		params.put("token", token.getToken());
		List<QMUser> users=dalClient.queryForList("qmInfo.qmLogin", params, QMUser.class);
		Map<String,QMUser> filter=new HashMap<>();
		QMUser bean=null;
		for (QMUser qmUser : users) {
			//id为唯一
			bean=filter.get(qmUser.getId());
			//新数据
			if(bean==null){
				bean=new QMUser();
				filter.put(qmUser.getId(), bean);
			}
			//填充数据
			fillParams(bean,qmUser);
		}
		//取出数据
		for (Map.Entry<String, QMUser>  user: filter.entrySet()) {
			bean=user.getValue();
		}
		if(bean!=null){
			bean.setFlag(true);
		}else{
			bean=new QMUser();
			bean.setFlag(false);
		}
		//转json
		return GsonUtil.bean2Json(bean);
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月7日 上午10:28:09 
	* 功能说明 ：填充角色信息
	 */
	public void fillParams(QMUser bean,QMUser param){
		String oldRole="";
		if(StringUtil.notEmpty(bean.getrName())){
			oldRole=bean.getrName();
		}
		bean.setuName(param.getuName());
		bean.setId(param.getId());
		bean.setrName(oldRole+"   "+param.getrName());
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月8日 上午11:41:21 
	* 功能说明 ：保存巡检数据
	 */
	@Override
	public JsonBean saveQmAppearanceCheck(String jsonData,final String[] checkItemCodes,final Integer[] checkVals) {
		JsonBean json=new JsonBean();
		QmAppearance bean=GsonUtil.fromJson2Bean(jsonData, QmAppearance.class);
		//根据工单号和检测批次号查询是否已经存在，获取该巡检项id
		Map<String,Object> paramMap=new HashMap<>();
//		paramMap.put("oid", bean.getOid());
		paramMap.put("batchNo", bean.getBatchID());
		List<QMOutWard> resList=dalClient.queryForList("qmInfo.queryOutWandIsExists", paramMap, QMOutWard.class);
		if(resList!=null && resList.size()==1){
			//修改小项
			final QMOutWard qmOutWard=resList.get(0);
			final Map<String,Object> params=new HashMap<>();
			//事物处理（try catch 只能包围在事物模板外面，不可以包围在内部。否则失效）
			Integer res = 0;
			try {
				res = dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
					@Override
					public Integer invoke() {
						for (int i = 0; i < checkItemCodes.length; i++) {
							params.put("id", StringUtil.getUUID());
							params.put("item", checkItemCodes[i]);
							params.put("Qid", qmOutWard.getId());
							params.put("val", checkVals[i]);
							dalClient.execute("qmInfo.insertItemDetails", params);
						}
						return 1;
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(res==1){
				json.setFlag(true);
			}
		}else{
			json.setMsg("巡检项查询结果不唯一。关键字段    工单id，检测批次号");
		}
		return json;
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月8日 上午11:45:09 
	* 功能说明 ：查询巡检详细项目,用于选则
	 */
	@Override
	public PaginationResult<List<QMOutWandItem>>  queryQmOutWardItem(QMOutWandItem item,Pagination pagination){
		return dalClient.queryForList("qmInfo.qmOutWandItem", item,QMOutWandItem.class,pagination);
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月11日 下午3:14:14 
	* 功能说明 ：查询外观巡检项目
	 */
	@Override
	public PaginationResult<List<QMOutWard>> queryQmOutWardItem(QMOutWard item, Pagination pagination) {
		return dalClient.queryForList("qmInfo.queryQmOutWand", item,QMOutWard.class,pagination);
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月11日 下午4:44:33 
	* 功能说明 ：保存外观巡检主数据
	* 需要登陆之后获取当前设备、工单信息
	 */
	@Override
	public void saveQmOutWand(final QMOutWard item,HttpSession session,final String checkType) {
		final LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		String qmUserJson=(String) session.getAttribute("qmLogUser");
		final QMUser qmUser=GsonUtil.fromJson2Bean(qmUserJson, QMUser.class);
		int flag=1;
		try {
			flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
				@Override
				public Integer invoke() {
					String eqpCode=null;
					if(checkType.equals("1")){
						eqpCode=loginBean.getRollerEquipmentCode();
					}else if(checkType.equals("2")){
						eqpCode=loginBean.getPackerEquipmentCode();
					}else if(checkType.equals("3")){
						//第一个封箱机
						eqpCode=loginBean.getBoxerEquipmentCode0();
					}else if(checkType.equals("4")){
						//第二个封箱机
						eqpCode=loginBean.getBoxerEquipmentCode1();
					}else if(checkType.equals("5")){
						//第一个成型机
						eqpCode=loginBean.getFilterEquipmentCode0();
					}else if(checkType.equals("6")){
						//第二个成型机
						eqpCode=loginBean.getFilterEquipmentCode1();
					}
					
					
					
					Map<String,Object> map=new HashMap<>();
					map.put("eqpCode", eqpCode);
					item.setEqpCode(eqpCode);
					//查询当前机台运行的工单id
					Map<String,Object> result=dalClient.queryForMap("qmInfo.queryCurrentOid", map);
					if(result!=null){
						map.put("oid", result.get("oid"));
					}
					map.remove("eqpCode");
					map.put("id", StringUtil.getUUID());
					map.put("batchNo", genBatchNo(item));
					map.put("time", new Date());
					map.put("uid", qmUser.getId());
					dalClient.execute("qmInfo.insertOutWand", map);
					return 0;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月11日 下午4:49:47 
	* 功能说明 ：生成批次号
	*   每一次取样为一个取样批
	    yyyyMMdd HH MM KXX SSSS
	           年  月日 时 分 机台 流水号
	 */
	private String genBatchNo(QMOutWard item){
		String batchNo=DateUtil.formatDateToString(new Date(), "yyyyMMddHHmm");
		batchNo+=item.getEqpCode()+MathUtil.getRandomInt(100, 1000);
		return batchNo;
	}
	
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月12日 上午11:24:31 
	* 功能说明 ：根据检测批次号查询当前批次检测结果
	 */
	@Override
	public PaginationResult<List<QMOutWandDelInfo>> queryQmOutWardItemByBatch(QMOutWandDelInfo item, Pagination pagination) {
		return dalClient.queryForList("qmInfo.queryOutWandDelInfo", item,QMOutWandDelInfo.class,pagination);
	}
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月15日 上午9:30:44 
	* 功能说明 ：外观检测记录查询
	 */
	@Override
	public PaginationResult<List<QmOutWandInfoBean>> queryQmOutWandInfo(QmOutWandInfoBean param,Pagination pagination) {
		return dalClient.queryForList("qmInfo.queryOutWandInfo", param,QmOutWandInfoBean.class,pagination);
	}
}
