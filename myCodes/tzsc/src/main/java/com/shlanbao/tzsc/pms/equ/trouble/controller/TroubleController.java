package com.shlanbao.tzsc.pms.equ.trouble.controller;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleInfoBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.TroubleBean;
import com.shlanbao.tzsc.pms.equ.trouble.service.TroubleServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Controller
@RequestMapping("/pms/trouble")
public class TroubleController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private TroubleServiceI troubleService;
	
	@ResponseBody
	@RequestMapping("/queryTrouble")
	public DataGrid queryTrouble(TroubleBean troubleBean,PageParams pageParams){
		try {
			DataGrid gd = troubleService.queryTrouble(troubleBean, pageParams);
			return gd;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	   
	@ResponseBody
	@RequestMapping("/queryList")
	public DataGrid queryList(EqmTroubleBean eqmtroubleBean,PageParams pageParams){
		
		try {
			DataGrid gd = troubleService.queryTrouble(eqmtroubleBean, pageParams);
			return gd;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 张璐
	 * 用于WCT故障信息树状结构查询
	 * @param troubleBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTroubleInfo")
	public String queryTroubleInfo(EqmTroubleInfoBean troubleBean){
			List<EqmTroubleInfoBean> list =  troubleService.queryTroubleInfo(troubleBean);
			StringBuffer htmlBuffer=new StringBuffer();
			if(list.size()>0){
				for(int i1=0;i1<list.size();i1++){
					String type1=list.get(i1).getType();
					String description1=list.get(i1).getDescription();
					String id1=list.get(i1).getId();
					String code1=list.get(i1).getCode();
					if(type1.equals("1")){
						htmlBuffer.append("<div class='tree ce_ceng_close' style='margin-top:10px'><a href='javascript:void(0);'>"+description1+"</a></div> ");
						htmlBuffer.append("<ul class='node' ><li>");
					for(int i2=0;i2<list.size();i2++){
						String type2=list.get(i2).getType();
						String description2=list.get(i2).getDescription();
						String parent_id2=list.get(i2).getParent_id();
						String id2=list.get(i2).getId();
						String code2=list.get(i2).getCode();
						if(type2.equals("2")&&parent_id2.equals(id1)){
							htmlBuffer.append("<div class='tree ce_ceng_close' style='margin-top:10px'><a href='javascript:void(0);'>"+description2+"</a></div>");
							htmlBuffer.append("<ul class='node' ><li>");
							for(int i3=0;i3<list.size();i3++){
								String type3=list.get(i3).getType();
								String description3=list.get(i3).getDescription();
								String parent_id3=list.get(i3).getParent_id();
								String id3=list.get(i3).getId();
								String code3=list.get(i3).getCode();
								if(type3.equals("3")&&parent_id3.equals(id2)){
									htmlBuffer.append("<div class='tree'  style='margin-top:10px'><a href='javascript:void(0);'>"+description3+"</a></div>");
									htmlBuffer.append("<ul class='node' ><li>");
									for(int i4=0;i4<list.size();i4++){
										String type4=list.get(i4).getType();
										String description4=list.get(i4).getDescription();
										String parent_id4=list.get(i4).getParent_id();
										String id4=list.get(i4).getId();
										String code4=list.get(i4).getCode();
										if(type4.equals("4")&&parent_id4.equals(id3)){
											htmlBuffer.append("<div class='tree'  style='margin-top:10px' onclick=addNewCode('"+code3+"','"+code4+"','"+id4+"');><a href='javascript:void(0);'>"+description4+"</a></div>");
											htmlBuffer.append("<ul class='node' ><li>");
											for(int i5=0;i5<list.size();i5++){
												String type5=list.get(i5).getType();
												String description5=list.get(i5).getDescription();
												String parent_id5=list.get(i5).getParent_id();
												String id5=list.get(i5).getId();
												String code5=list.get(i5).getCode();
												if(type5.equals("5")&&parent_id5.equals(id4)){
													htmlBuffer.append("<div class='tree'  onclick=save('"+code3+'-'+code4+'-'+code5+"','"+description5+"'); style='margin-top:10px; width:210px;'><a href='javascript:void(0);'>"+description5+"</a></div>");
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
		/*Map<String, List<String>> map1=new HashMap<String,List<String>>();//一级标题键，值为二级标题的键，以下以此类推
		Map<String, List<String>> map2=new HashMap<String,List<String>>();
		Map<String, List<String>> map3=new HashMap<String,List<String>>();
		Map<String, List<String>> map4=new HashMap<String,List<String>>();
		
		List<EqmTroubleInfoBean> list =  troubleService.queryTroubleInfo(troubleBean);
		for(EqmTroubleInfoBean bean:list){
			String eqpDes=bean.getEqp_DES();//1
			String trouble2=bean.getTrouble_part_description();
			String trouble3=bean.getPart_code_description();
			String trouble4=bean.getTrouble_phenomenon_description();
			String trouble5=bean.getTrouble_reason_description();
			String code=bean.getTrouble_code()+"-"+bean.getTrouble_phenomenon()+"-"+bean.getTrouble_reason();
			String fourCode=bean.getTrouble_code()+"~"+bean.getTrouble_phenomenon();
			if(map1.get(eqpDes)!=null){
				List<String> tempList=map1.get(eqpDes);
				if(!tempList.contains(trouble2)){
					tempList.add(trouble2);
					map1.put(eqpDes, tempList);
				}
			}else{
				List<String> tempList=new ArrayList<String>();
				tempList.add(trouble2);
				map1.put(eqpDes, tempList);
			}
			
			 
			if(map2.get(trouble2)!=null){
				List<String> tempList=map2.get(trouble2);
				if(!tempList.contains(trouble3)){
					tempList.add(trouble3);
					map2.put(trouble2, tempList);
				}
			}else{
				List<String> tempList=new ArrayList<String>();
				tempList.add(trouble3);
				map2.put(trouble2, tempList);
			}
			
			if(map3.get(trouble3)!=null){
				List<String> tempList=map3.get(trouble3);
				if(!tempList.contains(trouble4)){
					tempList.add(trouble4);
					map3.put(trouble3, tempList);
				}
			}else{
				List<String> tempList=new ArrayList<String>();
				tempList.add(trouble4);
				map3.put(trouble3,tempList);
			}
			
			if(map4.get(trouble4)!=null){
				List<String> tempList=map4.get(trouble4);
				if(!tempList.contains(trouble5)){
					tempList.add(code+":1"+trouble5);
					map4.put(trouble4, tempList);
				}
			}else{
				List<String> tempList=new ArrayList<String>();
				tempList.add(code+":1"+trouble5);
				map4.put(trouble4, tempList);
			}
		}
		int j=1;
		StringBuffer htmlBuffer=new StringBuffer();
		
		for (Entry<String, List<String>> entry : map1.entrySet()) {
			String trouble1=entry.getKey();
			List<String> trouble2=entry.getValue();
			htmlBuffer.append("<div class='tree ce_ceng_close' style='margin-top:10px'><a href='javascript:void(0);'>"+trouble1+"</a></div> ");
			htmlBuffer.append("<ul class='node' ><li>");
			for(String temp2:trouble2){//循环第二层Key
				htmlBuffer.append("<div class='tree ce_ceng_close' style='margin-top:10px'><a href='javascript:void(0);'>"+temp2+"</a></div>");
				if(map2.get(temp2)!=null){
					List<String> trouble3=map2.get(temp2);
					htmlBuffer.append("<ul class='node' ><li>");
					for(String temp3:trouble3){//第三次Key
						htmlBuffer.append("<div class='tree' id='tem3"+j+"' style='margin-top:10px'><a href='javascript:void(0);'>"+temp3+"</a></div>");
						if(map3.get(temp3)!=null){
							List<String> trouble4=map3.get(temp3);
							htmlBuffer.append("<ul class='node'> <li>");
							for(String temp4:trouble4){
								htmlBuffer.append("<div class='tree'  style='margin-top:10px' onclick=fourCode('"+j+"','"+temp4+"')><a href='javascript:void(0);'>"+temp4+"</a></div>");
								if(map4.get(temp4)!=null){
									List<String> touble5=map4.get(temp4);
									htmlBuffer.append("<ul class='node'><li>");
									for(String temp5:touble5){
										String[] t=new String[2];
										t=temp5.split(":1");
										htmlBuffer.append("<div class='tree'  onclick=save('"+t[0]+"','"+t[1]+"'); style='margin-top:10px;'><a href='javascript:void(0);'>"+t[1]+"</a></div>");
									}htmlBuffer.append("</li></ul>");//4	
								}
								
							}htmlBuffer.append("</li></ul>");//4
						}
						j++;
					}htmlBuffer.append("</li></ul>");//3
				}
				
				
			}htmlBuffer.append("</li></ul>");//2
			//j++;
			
		}*/
		
		
	}
	
	
	/**张璐2015-10-30
	 * 用于添加新的故障信息
	 */
	@ResponseBody
	@RequestMapping("/addNewTrouble")
	public String[] addNewTrouble(EqmTroubleInfoBean troubleBean){
		String des = troubleBean.getDescription();
		String pid=troubleBean.getParent_id();
		String[] codeId=new String[50];
		if(StringUtil.notNull(pid)&&StringUtil.notNull(des)){
			List<EqmTroubleInfoBean> list =  troubleService.queryTroubleInfo(troubleBean);
			if(list.size()>0){
				int fcode=list.size()+1;
				String fiveCode=""+fcode;
				String id=UUID.randomUUID().toString();
				troubleService.addNewTrouble(des, fiveCode, pid,id);
				codeId[0]=fiveCode;
				codeId[1]=id;
				return codeId;
			}
		}
		return null;
	}
	

	@ResponseBody
	@RequestMapping("/deleteNewTrouble")
	public void deleteNewTrouble(String id){
		String ids[]=id.split(",");
		for(int i=0;i<ids.length;i++){
			troubleService.deleteNewTrouble(ids[i]);
		}
		
	}
}
