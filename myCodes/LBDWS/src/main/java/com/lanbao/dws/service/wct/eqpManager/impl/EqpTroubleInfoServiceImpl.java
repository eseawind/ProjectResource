package com.lanbao.dws.service.wct.eqpManager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.support.PaginationDalClient;
import com.ibm.framework.dal.transaction.template.CallBackTemplate;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.common.tools.GsonUtil;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.eqpManager.EqmTroubleInfoBean;
import com.lanbao.dws.model.wct.eqpManager.EqpMainCallInfo;
import com.lanbao.dws.service.wct.eqpManager.IEqpTroubleInfoService;

@Service
public class EqpTroubleInfoServiceImpl implements IEqpTroubleInfoService {

	@Autowired
	PaginationDalClient dalClient;
	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月20日 下午7:14:46 
	* 功能说明 ：故障信息查询
	 */
	@Override
	public  List<EqmTroubleInfoBean> queryTroubleInfo(EqmTroubleInfoBean troubleBean) {
		return dalClient.queryForList("eqpTrouble.queryTrouble", troubleBean,EqmTroubleInfoBean.class);
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月21日 上午9:12:15 
	* 功能说明 ：将故障树list转html
	 */
	@Override
	public String getTroubleTreeHtml(EqmTroubleInfoBean troubleBean){
		List<EqmTroubleInfoBean> list=queryTroubleInfo(troubleBean);
		StringBuffer htmlBuffer=new StringBuffer();
		if(list.size()>0){
			for(int i1=0;i1<list.size();i1++){
				int type1=list.get(i1).getNodeType();
				String description1=list.get(i1).getNodeDesc();
				String id1=list.get(i1).getId();
				if(type1==1){
					htmlBuffer.append("<div class='tree ce_ceng_close' style='margin-top:10px' onclick=addNewCode('"+2+"','"+id1+"')><a href='javascript:void(0);'>"+description1+"</a></div> ");
					htmlBuffer.append("<ul class='node' ><li>");
				for(int i2=0;i2<list.size();i2++){
					int type2=list.get(i2).getNodeType();
					String description2=list.get(i2).getNodeDesc();
					String parent_id2=list.get(i2).getNodePid();
					String id2=list.get(i2).getId();
					if(type2==2 &&parent_id2.equals(id1)){
						htmlBuffer.append("<div class='tree ce_ceng_close' style='margin-top:10px' onclick=addNewCode('"+3+"','"+id2+"');><a href='javascript:void(0);'>"+description2+"</a></div>");
						htmlBuffer.append("<ul class='node' ><li>");
						for(int i3=0;i3<list.size();i3++){
							int type3=list.get(i3).getNodeType();
							String description3=list.get(i3).getNodeDesc();
							String parent_id3=list.get(i3).getNodePid();
							String id3=list.get(i3).getId();
							String code3=list.get(i3).getNodeCode();
							if(type3==3&&parent_id3.equals(id2)){
								htmlBuffer.append("<div class='tree'  style='margin-top:10px' onclick=addNewCode('"+4+"','"+id3+"');><a href='javascript:void(0);'>"+description3+"</a></div>");
								htmlBuffer.append("<ul class='node' ><li>");
								for(int i4=0;i4<list.size();i4++){
									int type4=list.get(i4).getNodeType();
									String description4=list.get(i4).getNodeDesc();
									String parent_id4=list.get(i4).getNodePid();
									String id4=list.get(i4).getId();
									String code4=list.get(i4).getNodeCode();
									if(type4==4&&parent_id4.equals(id3)){
										htmlBuffer.append("<div class='tree'  style='margin-top:10px' onclick=addNewCode('"+5+"','"+id4+"');><a href='javascript:void(0);'>"+description4+"</a></div>");
										htmlBuffer.append("<ul class='node' ><li>");
										for(int i5=0;i5<list.size();i5++){
											int type5=list.get(i5).getNodeType();
											String description5=list.get(i5).getNodeDesc();
											String parent_id5=list.get(i5).getNodePid();
											String code5=list.get(i5).getNodeCode();
											String id5=list.get(i5).getId();
											if(type5==5&&parent_id5.equals(id4)){
												htmlBuffer.append("<div class='tree'  onclick=save('"+code3+'-'+code4+'-'+code5+"','"+description5+"','"+id5+"'); style='margin-top:10px; width:210px;'><a href='javascript:void(0);'>"+description5+"</a></div>");
											}
										}htmlBuffer.append("</li></ul>");
									}
								}htmlBuffer.append("</li></ul>");
							}
						}htmlBuffer.append("</li></ul>");
					}
				}htmlBuffer.append("</li></ul>");
				
			  }
			}
		}
		return htmlBuffer.toString();
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月21日 上午9:23:14 
	* 功能说明 ：保存节点数据（第五级）
	 */
	@Override
	public String addNewTrouble(EqmTroubleInfoBean troubleBean) {
		String des = troubleBean.getNodeDesc();
		String pid=troubleBean.getNodePid();
		if(StringUtil.notNull(pid)&&StringUtil.notNull(des)){
			List<EqmTroubleInfoBean> list = queryTroubleInfo(troubleBean);
			if(list.size()>0){
				int fcode=list.size()+1;
				String fiveCode=""+fcode;
				String id=StringUtil.getUUID();
				troubleBean.setId(id);
				troubleBean.setNodeCode(fiveCode);//节点code
				troubleBean.setNodeType(troubleBean.getNodeType());
				dalClient.execute("eqpTrouble.saveNode", troubleBean);
				return GsonUtil.bean2Json(troubleBean);
			}
		}
		return null;
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月21日 上午10:45:59 
	* 功能说明 ：点击重置时，删除已经添加的故障
	 */
	@Override
	public void deleteTroubles(String ids) {
		String[] id=ids.split(",");
		int length=id.length;
		HashMap<String,Object>[] params=new HashMap[length];
		HashMap<String,Object> param=null;
		for (int i=0;i<length;i++) {
			param=new HashMap<>();
			param.put("ids", id[i]);
			params[i]=param;
		}
		dalClient.batchUpdate("eqpTrouble.deleteTroubles", params);
	}

	/**
	* @author 作者 : shisihai 
	* @version 创建时间：2016年7月22日 上午9:25:38 
	* 功能说明 ：保存故障记录
	* 1.根据来源查询班次、机台相关信息
	* 2.保存故障记录
	 */
	@Override
	public int addEqmTrouble(final String troubles,final String sourceId) {
		int flag=1;
		try {
			flag=dalClient.getTransactionTemplate().execute(new CallBackTemplate<Integer>() {
				@Override
				public Integer invoke() {
					HashMap<String, Object> params=new HashMap<>();
					params.put("id", sourceId);
					EqpMainCallInfo eqpCallInfo=dalClient.queryForObject("eqpMainCall.mainCallInfo", params,EqpMainCallInfo.class);
					if(eqpCallInfo==null){
						return 1;
					}
					params.remove("id");
					String shiftId=eqpCallInfo.getShiftId();//班次id
					String shiftName=eqpCallInfo.getShiftName();
					String teamId=eqpCallInfo.getTeamId();//班组
					String teamName=eqpCallInfo.getTeamName();
					String eqpId=eqpCallInfo.getEqpId();//设备id
					String eqpName=eqpCallInfo.getEqpName();
					String repairNo=StringUtil.getUUID();//维修流水号
					String repairDate=DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");//维修日期
					Integer status=0;//处理状态  0默认  1完成   2上抛给高级维修工  4已反馈MES
					String repairUserId=eqpCallInfo.getDesignatedId();//维修人
					String repairUserName=eqpCallInfo.getDesignatedName();
					String mes_id=eqpCallInfo.getMes_id();
					int repair_type=3;// 1计划检修  2巡检维修  3故障维修 0其他维修
					Date create_time=new Date(); //创建时间
					String shutdown_type=eqpCallInfo.getTypeName();//维修类型	
					//1机械  2电气  3网络 0未知
					if(!shutdown_type.equals("1") && !shutdown_type.equals("2")){
						shutdown_type="0";
					}
					
					/*没有字段*/
					String comments="5";//评分(对维修结果打分)
					params.put("shiftId",shiftId);
					params.put("shiftName",shiftName);
					params.put("teamId",teamId);
					params.put("teamName",teamName);
					params.put("eqpId",eqpId);
					params.put("eqpName",eqpName);
					params.put("repairNo",repairNo);
					params.put("repairDate",repairDate);
					params.put("status",status);
					params.put("repairUserId",repairUserId);
					params.put("repairUserName",repairUserName);
					params.put("repair_type",repair_type);
					params.put("create_user_id",repairUserId);
					params.put("create_user_name",repairUserName);
					params.put("create_time",create_time);
					params.put("shutDownType", Integer.valueOf(shutdown_type));
					params.put("sourceId", sourceId);//维修呼叫id
					params.put("mes_id",mes_id);//维保计划id
					params.put("comments",comments);//评分(对维修结果打分)
					//id#code#des
					String[] troubleBean=troubles.split(",");
					String[] beanFiled=null;
					//保存故障信息
					for (String str : troubleBean) {
						beanFiled=str.split("#");
						params.put("id", StringUtil.getUUID());
						params.put("tCode", beanFiled[1]);
						params.put("solution", beanFiled[2]);//修改为处理措施 5
						
						//查询故障原因
						params.put("touId", beanFiled[0]);
						String[] info=queryPTroubInfo(params);
						params.put("fault_reason",info[0]);//故障原因 4
						
						//查询故障现象
						params.put("touId", info[1]);
						params.put("type", 3);
						info=queryPTroubInfo(params);
						params.put("shutdown_name",info[0]);//故障现象  3
						
						//查询故障类型
						params.put("touId", info[1]);
						params.put("type", 2);
						info=queryPTroubInfo(params);
						params.put("tDesc",info[0]);//故障类型2
						
						dalClient.execute("eqpMainCall.saveEqpTrouble", params);
					}
					return 0;
				}

				private String[] queryPTroubInfo(HashMap<String, Object> params) {
					String[] str=new String[2];
					List<Map<String,Object>> datas=dalClient.queryForList("eqpMainCall.queryUpTroubleInfo", params);
					for (Map<String, Object> map : datas) {
						str[0]=StringUtil.convertObjToString(map.get("descInfo"));
						str[1]=StringUtil.convertObjToString(map.get("parentId"));
					}
					return str;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
