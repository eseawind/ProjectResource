package com.shlanbao.tzsc.base.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.shlanbao.tzsc.base.dao.QualityCheckMesDataDaoI;
import com.shlanbao.tzsc.base.dao.QualityCheckMesDataParamsDaoI;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfoParams;
import com.shlanbao.tzsc.utils.tools.ApplicationContextUtil;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MESConvertToJB;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 解析MES关于质检xml数据
 * @author TRAVLER
 *
 */
public class AlalyQualityXML {
	private static AlalyQualityXML alalyQualityXML=null;
	
	private AlalyQualityXML() {
		
	}
	
	public static AlalyQualityXML getInstance(){
		if(alalyQualityXML==null){
			alalyQualityXML=new AlalyQualityXML();
		}
		 return alalyQualityXML;
	}
	
	/**
	 * 解析保存xml
	 * @param root
	 * @param fileName
	 * @return
	 * TRAVLER
	 * 20152015年12月24日上午10:02:38
	 */
	public boolean handleQCXML(Document doc){
		boolean flag=false;
		/*HashMap<String, List<?>> map=null;
		Element node=doc.getRootElement();
		Element fnode= node.element("messageCode");
		try {
			map = alalyQZXML(root,fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		flag=saveQZ(map);*/
		//return flag;
	    return false;
	}
	
	/**
	 * 解析质检xml，并封装成map
	 * @param root
	 * @param fileName 文件名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String,List<?>> alalyQZXML(Element root,String fileName) throws Exception{
		HashMap<String,List<?>> map=new HashMap<String,List<?>>();
		List<QualityCheckInfoParams> ps=new ArrayList<QualityCheckInfoParams>();
		List<QualityCheckInfo> info=new ArrayList<QualityCheckInfo>();
		QualityCheckInfo qzbean = null;
		QualityCheckInfoParams params = null;
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element e = (Element) i.next();
			String pNs = e.getName();//获取节点名
			// 遍历SegmentItem
			if (pNs.equals("segmentItem")) {
				qzbean = new QualityCheckInfo();
				qzbean.setFileInfo(fileName);
				for (Iterator i3 = e.elementIterator(); i3.hasNext();) {
						Element e4 = (Element) i3.next();
						String tagName = e4.getName();
						if (tagName.equals("sc")) {
							qzbean.setSc(getNodeStringVal(e4));
						} else if (tagName.equals("st")) {
							qzbean.setSt(getNodeStringVal(e4));
						} else if (tagName.equals("checkType")) {
							qzbean.setCheckType(getNodeStringVal(e4));
						} else if (tagName.equals("sc_score")) {
							qzbean.setScScore(getNodeDoubleVal(e4));
						} else if (tagName.equals("st_version")) {
							qzbean.setStVersion(getNodeStringVal(e4));
						} else if (tagName.equals("description")) {
							qzbean.setMatDes(getNodeStringVal(e4));
						} else if (tagName.equals("creation_date")) {
							//xml生成日期  需要转化
							qzbean.setCreateDate(DateUtil.formatStringToDate(getNodeStringVal(e4), "yyyy-MM-dd HH:mm:ss"));
						} else if (tagName.equals("sampleInfo")) {
							// 解析班次班组牌号等信息
							for (Iterator i4 = e4.elementIterator(); i4.hasNext();) {
								Element e5 =(Element) i4.next();
								Element e6 = e5.element("value");// 值
								String val = getNodeStringVal(e6);
								Element e7 = e5.element("code");// code值
								String code = getNodeStringVal(e7);
								Element e8 = e5.element("ii");// 值的类型
								String vp = getNodeStringVal(e8);
								if (vp.equals("ii_brand")) {
									// 牌号
									qzbean.setMatName(val);
								} else if (vp.equals("ii_lot")) {
									// 生产批次
									qzbean.setIiLot(val);
								} else if (vp.equals("ii_team")) {
									// 班组
									qzbean.setTeamId(code);
									qzbean.setTeamName(val);
								} else if (vp.equals("ii_shift")) {
									// 班次   需要转换
									qzbean.setShiftId(transShift(code));
									qzbean.setShiftName(val);
								} else if (vp.equals("ii_chk")) {
									// 检验员
									qzbean.setIiChk(val);
								} else if (vp.equals("ii_adt")) {
									// 审核人
									qzbean.setIiAdk(val);
								} else if (vp.equals("ii_remark")) {
									// 备注
									qzbean.setRemark(val);
								} else if (vp.equals("ii_coil_tz")) {
									// 卷烟机
									qzbean.setRollerCode(transEqpCode(code));
									qzbean.setRollerName(val);
								} else if (vp.equals("ii_pack_jb_tz")) {
									// 包装机
									qzbean.setPackerCode(transEqpCode(code));
									qzbean.setPackerName(val);
								} else if (vp.equals("ii_sealer_tz")) {
									// 封箱机
									qzbean.setSealerCode(transEqpCode(code));
									qzbean.setSealerName(val);
								} else if (vp.equals("ii_order")) {
									// 订单号
									qzbean.setOrderNum(val);
								} else if (vp.equals("ii_samsequence")) {
									// 取样序号
									qzbean.setSamSequence(val);
								} else if (vp.equals("ii_season_cpsh_tz")) {
									// 季度
									qzbean.setSeasonCpsh(val);
								} else if (vp.equals("ii_chk_time_cpsh")) {
									// 检验日期
									qzbean.setCheckTime(DateUtil.formatStringToDate(val, "yyyy-MM-dd HH:mm:ss"));
								} else if (vp.equals("ii_shift_tz")) {
									// 滕州班次
									qzbean.setShiftTz(val);
									qzbean.setShiftTzCode(code);
								}
							}
							
						} else if (tagName.equals("sampleResults")) {
							// 质检详细信息
							for (Iterator i5 = e4.elementIterator(); i5.hasNext();) {
								params =new QualityCheckInfoParams();
								Element e9 = (Element) i5.next();
								Element pg = e9.element("pg");
								String pgv = this.getNodeStringVal(pg);
								params.setPgDes(pgv);
								Element pa = e9.element("pa");
								String pav = this.getNodeStringVal(pa);
								params.setPaDes(pav);
								Element value_f = e9.element("value_f");
								String vf = this.getNodeStringVal(value_f);
								params.setValueF(vf);
								Element sc_deduct = e9.element("sc_deduct");
								Double scv=this.getNodeDoubleVal(sc_deduct);
								params.setScDeduct(scv);
								Element unit = e9.element("unit");
								String uv = this.getNodeStringVal(unit);
								params.setUnit(uv);
								Element code = e9.element("code");
								String cv =this.getNodeStringVal(code);
								params.setPaTypeCode(cv);//abc类别,当cell有节点时说明是物理指标
								List<?> cls = e9.elements("cell");
								if (cls != null && cls.size() > 0) {
									// 物理指标
									for (int k = 0; k < cls.size(); k++) {
										Element ce = (Element) cls.get(k);
										Element id = ce.element("id");
										String t = this.getNodeStringVal(id);
										Element val = ce.element("value");
										String va = this.getNodeStringVal(val);
										if (t.equals("c_mean")) {
											// 均值
											params.setcMean(va);
										} else if (t.equals("c_sd")) {
											// 标偏
											params.setcSd(va);
										} else if (t.equals("c_min")) {
											// 最小
											params.setcMin(va);
										} else if (t.equals("c_max")) {
											// 最大
											params.setcMax(va);
										} else if (t.equals("c_cv")) {
											// 变异系数
											params.setcCv(va);
										} else if (t.equals("c_result")) {
											// 不合格支数
											params.setcResult(va);
										}
									}

								}
								ps.add(params);
							}
						}
				}

			}
		}
		info.add(qzbean);
		map.put("QZParams", ps);
		map.put("QZInfo", info);
		return map;
	}
	
	
	/**
	 * 保存质检数据到数据库
	 * 保存有异常，返回false
	 *  TODO
	 *  @param map
	 *  TRAVLER
	 *2015年12月14日下午4:10:27
	 */
	@SuppressWarnings("unchecked")
	public boolean saveQZ(HashMap<String,List<?>> map) {
		boolean flag=false;
		QualityCheckInfo info=(QualityCheckInfo) map.get("QZInfo").get(0);//质检概要信息
		List<QualityCheckInfoParams> pls= (List<QualityCheckInfoParams>) map.get("QZParams");//该质检的详细信息
		QualityCheckMesDataDaoI infoDao=ApplicationContextUtil.getBean(QualityCheckMesDataDaoI.class);
		QualityCheckMesDataParamsDaoI InfoParamsDao=ApplicationContextUtil.getBean(QualityCheckMesDataParamsDaoI.class);
		QualityCheckInfo info2=null;
		try {
			//保存质检概述
			info2 = infoDao.saveAndReturn(info);
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		if(info2!=null){
			for (QualityCheckInfoParams qualityCheckInfoParams : pls) {
				qualityCheckInfoParams.setqId(info2.getId());
			}
			//保存质检详细
			flag=InfoParamsDao.batchInsertAndReturn(pls, QualityCheckInfoParams.class);
		}
		return flag;
	}
	
	/**
	 * 获取节点的String 值
	 *  TODO
	 *  @param e
	 *  @return
	 *  TRAVLER
	 *2015年12月14日下午3:54:36
	 */
	private String getNodeStringVal(Element e){
		String r=null;
		if(null!=e){
			r=e.getTextTrim();
		}
		return r;
	}
	/**
	 * 获取节点的double值
	 *  TODO
	 *  @param e
	 *  @return
	 *  TRAVLER
	 *2015年12月14日下午3:54:18
	 */
	private Double getNodeDoubleVal(Element e){
		Double r=0.0;
		if(null!=e){
			String r2=e.getTextTrim();
			if(StringUtil.isDouble(r2)){
				r=Double.parseDouble(r2);
			}
		}
		return r;
	}
	
	/**
	 * 这个不是滕州班次，
	 * 滕州班次与数据库一致
	 * @param st
	 * @return
	 * TRAVLER
	 * 20152015年12月24日上午9:17:50
	 */
	private String transShift(String st){
		String shift="";
		if(st.equals("1")){
			shift="3";
		}else if(st.equals("2")){
			shift="1";
		}else if(st.equals("3")){
			shift="2";
		}
		return shift;
	}
	
	/**
	 * 转换设备code
	 * @param code
	 * 1#到12#卷烟机            编码分别2101 --- 2112   数据库  1----12            计算：-2100
	 * 
	 * 1#到12#包装机            编码分别2201 --- 2212    数据库 31----42         计算：-2170
	 * 
	 * 1#到3#封箱机               编码分别2301 --- 2303     数据库  61----64       计算：-2240
	 * 
	 * @return
	 * TRAVLER
	 * 20152015年12月24日上午9:22:35
	 */
	private String transEqpCode(String code) throws Exception{
		return MESConvertToJB.transEqpCode(code);
	}
	
}
