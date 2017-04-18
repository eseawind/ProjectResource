package com.shlanbao.tzsc.wct.eqm.shiftGl.service.impl;


import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.StatShiftFLInfoDaoI;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmShiftDataBean;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;
import com.shlanbao.tzsc.wct.eqm.shiftGl.service.StatShiftFLInfoService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
@Service
public class StatShiftFLInfoServiceImpl implements StatShiftFLInfoService{

	@Autowired 
	private StatShiftFLInfoDaoI  statShiftFLInfoDao;
	

	/**
	 * 【功能说明】：设备故障，设备有效作业率，设备产量 
	 *              卷烟机，包装机，成型机，封箱机共用调用方法
	 * @author wch
	 * @createTime 2015年11月13日13:13:20	 
	 * */
	public Map<String, String> getShiftDataMap(EqmShiftDataBean eqmsb,Map<String, String> map,String shift_id,String oid_date,StringBuffer sql1){
		sql1.append(" select c.EQUIPMENT_NAME,datediff( MINUTE, b.stim, GETDATE() ) AS gd_time, ");
		sql1.append(" (SELECT datediff(MINUTE, stim, etim) FROM EQM_PAULDAY WHERE del = 0 AND status = 3 ");
		sql1.append(" AND shift = '"+shift_id+"' AND CONVERT (VARCHAR(50), date_p, 23) = '"+oid_date.substring(0,10)+"' ");
		sql1.append(" AND eqp_type = ( SELECT 	id 	FROM 	MD_EQP_CATEGORY WHERE code = '"+eqmsb.getShop_code()+"' ) ) as rb_time , "); 
		sql1.append(" (select stop_time from EQM_CULL_RECORD where eqp_id=a.eqp and shift_id=a.shift ) as tc_time, ");  
		sql1.append(" b.qty,c.YIE_ID,a.id  from SCH_WORKORDER a,SCH_STAT_OUTPUT b,MD_EQUIPMENT c ");	
		sql1.append(" where a.id = b.oid and a.eqp = c.id AND b.del = 0  ");
		sql1.append(" and CONVERT(varchar(50),a.date,23)= '"+oid_date.substring(0,10)+"' and a.del=0 and b.del=0 ");
		sql1.append(" and a.shift='"+shift_id+"' and a.EQP='"+eqmsb.getEqp_id()+"' ");
		
		//产量，有效作业率
		List<?> list1=statShiftFLInfoDao.queryBySql(sql1.toString());
		if(list1.size()>0 && list1!=null){
			Object[] temp=(Object[]) list1.get(0);
			Double qty=0d; //产量
			Double gd=0d; //工单
			Double rb=0d;//日保
			Double tc=0d;//固定剔除
			Double tscl=0d; //台时产量
			double erd=0;//额定产量
			String retf="";
			Double jc=SysEqpTypeBase.jcTime;
			try {
				if(temp[4]!=null){
					qty=Double.parseDouble(temp[4].toString());
					map.put("qty", qty.toString()); //产量
				}
				if(temp[1]!=null){
					gd=Double.parseDouble(temp[1].toString());	
				}
				if(temp[2]!=null){
					rb=Double.parseDouble(temp[2].toString());	
				}
				if(temp[3]!=null){
					tc=Double.parseDouble(temp[3].toString());	
				}
				if(temp[5]!=null){
					tscl=Double.parseDouble(temp[5].toString());	
				}
				double  erdTime=(gd-rb-tc-jc)/60;
				//设备有效作业率
				erd=qty/(erdTime*tscl);
				if(erd>0){
					 NumberFormat nf = NumberFormat.getNumberInstance();   
			         nf.setMaximumFractionDigits(2);  
		        	 retf=nf.format(erd*100); 
		        	 Double dl=Double.parseDouble(retf);
		        	 if(dl>120){ //有效作业率大与100%，给默认值，预防领导看到投诉我们  wch 2015年10月13日
		        		 map.put("zyl", "0");  //有效作业率
		        	 }else{
		        		 map.put("zyl", dl.toString());//有效作业率
		        	 } 
				}else{
					 map.put("zyl", "0");//有效作业率
				}
			} catch (Exception e) {
				 map.put("zyl", "0");//有效作业率
				 map.put("qty", "0"); //产量
				 e.printStackTrace();
			}
		}else{
			 map.put("zyl", "0");//有效作业率
			 map.put("qty", "0"); //产量
		}
		
		//故障
		sql1.setLength(0);
		sql1.append(" select description,source From EQM_TROUBLE where shift_id='"+shift_id+"' and convert(varchar(50),create_date,23)='"+oid_date.substring(0,10)+"'  and equ_id='"+eqmsb.getEqp_id()+"' ");
		List<?> list2=statShiftFLInfoDao.queryBySql(sql1.toString());
		if(list2.size()>0 && list2!=null){
			StringBuffer sbf=new StringBuffer(1000);
			for(int i=0;i<list2.size();i++){
				Object[] temp=(Object[]) list2.get(i);
				sbf.append((i+1)+") ");
				if(temp[0]!=null){
					sbf.append(temp[0].toString()+"；");	
				}
				sbf.append("<br/>");
			}
			map.put("equ", sbf.toString());
		}else{
			map.put("equ", "<span style='color:red;'>未查询到故障维修记录！</span>");
		}
		return  map;
	}
	
	
	/**
	 * [功能描述]：wct交接班维护-包装机辅料保存-封箱机
	 *      yf_xp[] -箱皮   yf_jd[] -胶带
	 * @createTime 2015年11月13日16:52:19
	 * @author wanchanghuang
	 * */
	@Override
	public void saveShiftBox(HttpSession session, String[] yf_xp, String[] yf_jd) {
		LoginBean loginWctInfoBean = (LoginBean) session.getAttribute("loginWctEqmInfo");
		   LoginBean loginInfoBean = (LoginBean) session.getAttribute("loginInfo");
	       StatShiftFLInfo ssf=new StatShiftFLInfo();
	       ssf.setMec_id(SysEqpTypeBase.sealerEqpTypeCode+""); //封箱机
	       ssf.setEqp_id(loginInfoBean.getEquipmentId());//班次
	       String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
		   //查询当前工单信息
		   List<?> nList=getShiftAndDate(today,loginInfoBean.getEquipmentId());
		   //班次
		   String shift_id="";
		   //品牌ID
		   String mat_id="";
		   //品牌名称
		   String mat_name="";
		   //工单日期
		   String oid_date="";
		   if(nList.size()>0 && nList!=null){
			   Object[] temp=(Object[]) nList.get(0);
			   ssf.setTeam_id(temp[2].toString()); //班组
			   shift_id=temp[1].toString(); //班次
			   ssf.setOid(temp[0].toString()); //当前工单
			   mat_id=temp[4].toString();//品牌ID
			   mat_name=temp[5].toString();//品牌name
			   oid_date=temp[6].toString();//工单日期
		   }
		   ssf.setShift_id(shift_id);
		   ssf.setMat_id(mat_id);
		   ssf.setMat_name(mat_name);
	       ssf.setHandover_user_id(loginWctInfoBean.getUserId());
	       ssf.setHandover_user_name(loginWctInfoBean.getName());
	       ssf.setCreate_time(new Date());
	       ssf.setUpdate_time(new Date());
	       ssf.setUpdate_user_id(loginWctInfoBean.getUserId());    
	       ssf.setZc_type(0);
	       ssf.setFl_type(1); //辅料类型  1-实领  2-虚领  3-虚退
	       ssf.setStatus(1); //1-通过   0-默认
	       ssf.setDel(0);
	       //实领
	       ssf.setYf_xp(Double.parseDouble(yf_xp[1]));
	       ssf.setYf_jd(Double.parseDouble(yf_jd[1]));
	       
	       String doid=""; //下一班的工单ID
	       String uoid=""; //上一班的工单ID
	       /**
	        * 【功能描述】：下一班次数据处理
	        *          (下一班的)虚领=(当前班次的)虚退
	        *          (下一班的)品牌=(当前班次的)品牌
	        * */
	       String dshift_id="";//下一班班次
	       String dteam_id="";//下一班班组
	       String dmat_id="";//下一班品牌ID
	       String umat_id="";//上一班品牌ID
	       //查询下一班工单信息
		   List<?> dList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,1);
		   if(dList.size()>0 && dList!=null){
			   Object[] temp=(Object[]) dList.get(0);
			   doid=temp[0].toString(); //当前工单
			   dmat_id=temp[4].toString();//品牌ID 
			   dteam_id=temp[2].toString(); //班组
			   dshift_id=temp[1].toString(); //班次
		   }
		   //判断当前品牌=下一班品牌
		   if(mat_id.equals(dmat_id)){
			   ssf.setDoid(doid);
		   }
		   //查询上一班工单信息
		   List<?> uList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,2);
		   if(uList.size()>0 && uList!=null){
			   Object[] temp=(Object[]) uList.get(0);
			   uoid=temp[0].toString(); //当前工单
			   umat_id=temp[4].toString();//品牌ID
		   }
		   //判断当前品牌=上一班品牌
		   if(mat_id.equals(umat_id)){
			   ssf.setUoid(uoid);
		   }
		   //实领-保存
		   saveSlFLData(ssf);
		   
		   //虚领
		   try {
			   StatShiftFLInfo ssf2=new StatShiftFLInfo();
			   BeanConvertor.copyProperties(ssf, ssf2); //第一个对象拷贝到第一个对象
		       ssf2.setYf_xp(Double.parseDouble(yf_xp[0]));
		       ssf2.setYf_jd(Double.parseDouble(yf_jd[0]));
		       ssf2.setFl_type(2); //辅料类型  1-实领  2-虚领  3-虚退
		       ssf2.setCreate_time(new Date());
		       ssf2.setUpdate_time(new Date());
		       //虚领-保存
		       saveSlFLData(ssf2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  
		   //本班虚退
		   try {
		       StatShiftFLInfo ssf3=new StatShiftFLInfo();
			   BeanConvertor.copyProperties(ssf, ssf3); //第一个对象拷贝到第一个对象
			   
		       ssf3.setYf_xp(Double.parseDouble(yf_xp[2]));
		       ssf3.setYf_jd(Double.parseDouble(yf_jd[2]));

	           ssf3.setFl_type(3); //辅料类型  1-实领  2-虚领  3-虚退
	           ssf3.setCreate_time(new Date());
	           ssf3.setUpdate_time(new Date());
	           //虚退-保存
	           statShiftFLInfoDao.saveBackKey(ssf3);
		    } catch (Exception e) {
		    	e.printStackTrace();
			}
		   
		   //如果品牌相等，添加下一班虚领
	       if(mat_id.equals(dmat_id)){ //判断虚退也就是下一班虚领，品牌相等才能虚退
	    	   try {
	    	       StatShiftFLInfo ssf4=new StatShiftFLInfo();
	    		   BeanConvertor.copyProperties(ssf, ssf4); //第一个对象拷贝到第一个对象

			       ssf4.setYf_xp(Double.parseDouble(yf_xp[2]));
			       ssf4.setYf_jd(Double.parseDouble(yf_jd[2]));

	               ssf4.setFl_type(2); //本班虚退   = 下一班虚领
	               ssf4.setStatus(0);//下一班虚领默认状态  0-默认
	               ssf4.setCreate_time(new Date());
	               ssf4.setUpdate_time(new Date());
	               //工单基本信息
	               ssf4.setShift_id(dshift_id);
	               ssf4.setTeam_id(dteam_id);
	               ssf4.setOid(ssf.getDoid());
	               ssf4.setUoid(ssf.getOid());
	               //清空DOID
	               ssf4.setDoid("");
	               //下一班虚领-保存
	               statShiftFLInfoDao.saveBackKey(ssf4);
	    	    } catch (Exception e) {
	    			e.printStackTrace();
	    		}
		   }
		
	}

	/**
	 * [功能描述]：wct交接班维护-主页面查询-封箱机
	 *               产量，设备有效作业率
	 * @createTime 2015年11月2日11:32:54
	 * @author wanchanghuang
	 * */
	@Override
	public Map<String, String> queryEqmShiftDatasBox(EqmShiftDataBean eqmsb) {
		StringBuffer sql1=new StringBuffer(1000);//有效作业率,产量,故障
		Map<String, String> map=new HashMap<String, String>();
	   //查询当前工单信息
	   String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
	   //查询当前工单信息
	   List<?> nList=getShiftAndDate(today,eqmsb.getEqp_id());
	   String oid="";//工单ID
	   String shift_id=""; //班次
	   String oid_date=""; //工单日期
	   if(nList.size()>0 && nList!=null){
		   Object[] temp=(Object[]) nList.get(0);
		   shift_id=temp[1].toString(); //班次
		   oid_date=temp[6].toString();//工单日期
		   oid=temp[0].toString();//工单ID 
	   }
	   map=getShiftDataMap(eqmsb,map, shift_id, oid_date,sql1);
		
		/**
		 * 查询：实领，虚领数据
		 * */
		//清空
		sql1.setLength(0);
		sql1.append("  select yf_xp,yf_jd,status,fl_type,zc_type From SCH_STAT_SHIFT_FLINFO where mec_id='"+SysEqpTypeBase.sealerEqpTypeCode+"' and oid='"+oid+"' and fl_type in ('1','2','3') ORDER BY fl_type ");
		List<?> listSlorXL=statShiftFLInfoDao.queryBySql(sql1.toString());
		//定义实领，虚领数组,虚退
		String zj_sl="";
		String zj_xl="";
		String zj_xt="";
		if(listSlorXL.size()>0 && listSlorXL!=null){
			for(int i=0;i<listSlorXL.size();i++){
				Object[] temp=(Object[]) listSlorXL.get(i);
				Integer fl_type=Integer.parseInt(temp[3].toString());
				if(fl_type==1){//实领
					 zj_sl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString();
				}else if(fl_type==2){//虚领
					 zj_xl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString();
				}else if(fl_type==3){//虚退
					zj_xt=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString();
				}
			}
	    }
		map.put("zj_sl", zj_sl);
		map.put("zj_xl", zj_xl);
		map.put("zj_xt", zj_xt);
		return map;
	}
	
	
	
	
	
		
	/**
	 * [功能描述]：wct交接班维护-包装机辅料保存-成型机
	 *      zl_pz[] -盘纸   zl_ss[] -丝素
	 * @createTime 2015年11月13日16:52:19
	 * @author wanchanghuang
	 * */
	@Override
	public void saveShiftFilter(HttpSession session, String[] zl_pz,String[] zl_ss) {
		 LoginBean loginWctInfoBean = (LoginBean) session.getAttribute("loginWctEqmInfo");
		   LoginBean loginInfoBean = (LoginBean) session.getAttribute("loginInfo");
	       StatShiftFLInfo ssf=new StatShiftFLInfo();
	       ssf.setMec_id(SysEqpTypeBase.filterEqpTypeCode+""); //卷烟机
	       ssf.setEqp_id(loginInfoBean.getEquipmentId());//班次
	       String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
		   //查询当前工单信息
		   List<?> nList=getShiftAndDate(today,loginInfoBean.getEquipmentId());
		   //班次
		   String shift_id="";
		   //品牌ID
		   String mat_id="";
		   //品牌名称
		   String mat_name="";
		   //工单日期
		   String oid_date="";
		   if(nList.size()>0 && nList!=null){
			   Object[] temp=(Object[]) nList.get(0);
			   ssf.setTeam_id(temp[2].toString()); //班组
			   shift_id=temp[1].toString(); //班次
			   ssf.setOid(temp[0].toString()); //当前工单
			   mat_id=temp[4].toString();//品牌ID
			   mat_name=temp[5].toString();//品牌name
			   oid_date=temp[6].toString();//工单日期
		   }
		   ssf.setShift_id(shift_id);
		   ssf.setMat_id(mat_id);
		   ssf.setMat_name(mat_name);
	       ssf.setHandover_user_id(loginWctInfoBean.getUserId());
	       ssf.setHandover_user_name(loginWctInfoBean.getName());
	       ssf.setCreate_time(new Date());
	       ssf.setUpdate_time(new Date());
	       ssf.setUpdate_user_id(loginWctInfoBean.getUserId());    
	       ssf.setZc_type(0);
	       ssf.setFl_type(1); //辅料类型  1-实领  2-虚领  3-虚退
	       ssf.setStatus(1); //1-通过   0-默认
	       ssf.setDel(0);
	       //实领
	       ssf.setZl_pz(Double.parseDouble(zl_pz[1]));
	       ssf.setZl_ss(Double.parseDouble(zl_ss[1]));
	       
	       String doid=""; //下一班的工单ID
	       String uoid=""; //上一班的工单ID
	       /**
	        * 【功能描述】：下一班次数据处理
	        *          (下一班的)虚领=(当前班次的)虚退
	        *          (下一班的)品牌=(当前班次的)品牌
	        * */
	       String dshift_id="";//下一班班次
	       String dteam_id="";//下一班班组
	       String dmat_id="";//下一班品牌ID
	       String umat_id="";//上一班品牌ID
	       //查询下一班工单信息
		   List<?> dList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,1);
		   if(dList.size()>0 && dList!=null){
			   Object[] temp=(Object[]) dList.get(0);
			   doid=temp[0].toString(); //当前工单
			   dmat_id=temp[4].toString();//品牌ID 
			   dteam_id=temp[2].toString(); //班组
			   dshift_id=temp[1].toString(); //班次
		   }
		   //判断当前品牌=下一班品牌
		   if(mat_id.equals(dmat_id)){
			   ssf.setDoid(doid);
		   }
		   //查询上一班工单信息
		   List<?> uList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,2);
		   if(uList.size()>0 && uList!=null){
			   Object[] temp=(Object[]) uList.get(0);
			   uoid=temp[0].toString(); //当前工单
			   umat_id=temp[4].toString();//品牌ID
		   }
		   //判断当前品牌=上一班品牌
		   if(mat_id.equals(umat_id)){
			   ssf.setUoid(uoid);
		   }
		   //实领-保存
		   saveSlFLData(ssf);
		   
		   //虚领
		   try {
			   StatShiftFLInfo ssf2=new StatShiftFLInfo();
			   BeanConvertor.copyProperties(ssf, ssf2); //第一个对象拷贝到第一个对象
			   ssf2.setZl_pz(Double.parseDouble(zl_pz[0]));
		       ssf2.setZl_ss(Double.parseDouble(zl_ss[0]));
		       ssf2.setFl_type(2); //辅料类型  1-实领  2-虚领  3-虚退
		       ssf2.setCreate_time(new Date());
		       ssf2.setUpdate_time(new Date());
		       //虚领-保存
		       saveSlFLData(ssf2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  
		   //本班虚退
		   try {
		       StatShiftFLInfo ssf3=new StatShiftFLInfo();
			   BeanConvertor.copyProperties(ssf, ssf3); //第一个对象拷贝到第一个对象
			   ssf3.setZl_pz(Double.parseDouble(zl_pz[2]));
		       ssf3.setZl_ss(Double.parseDouble(zl_ss[2]));

	           ssf3.setFl_type(3); //辅料类型  1-实领  2-虚领  3-虚退
	           ssf3.setCreate_time(new Date());
	           ssf3.setUpdate_time(new Date());
	           //虚退-保存
	           statShiftFLInfoDao.saveBackKey(ssf3);
		    } catch (Exception e) {
		    	e.printStackTrace();
			}
		   
		   //如果品牌相等，添加下一班虚领
	       if(mat_id.equals(dmat_id)){ //判断虚退也就是下一班虚领，品牌相等才能虚退
	    	   try {
	    	       StatShiftFLInfo ssf4=new StatShiftFLInfo();
	    		   BeanConvertor.copyProperties(ssf, ssf4); //第一个对象拷贝到第一个对象
	    		   
	    		   ssf4.setZl_pz(Double.parseDouble(zl_pz[2]));
			       ssf4.setZl_ss(Double.parseDouble(zl_ss[2]));

	               ssf4.setFl_type(2); //本班虚退   = 下一班虚领
	               ssf4.setStatus(0);//下一班虚领默认状态  0-默认
	               ssf4.setCreate_time(new Date());
	               ssf4.setUpdate_time(new Date());
	               //工单基本信息
	               ssf4.setShift_id(dshift_id);
	               ssf4.setTeam_id(dteam_id);
	               ssf4.setOid(ssf.getDoid());
	               ssf4.setUoid(ssf.getOid());
	               //清空DOID
	               ssf4.setDoid("");
	               //下一班虚领-保存
	               statShiftFLInfoDao.saveBackKey(ssf4);
	    	    } catch (Exception e) {
	    			e.printStackTrace();
	    		}
		   }
		
	}
	
	/**
	 * 【功能说明】：查询产量，有效作业率，故障  - 成型机
	 * @author wch
	 * @createTime 2015年11月13日17:41:34
	 * 
	 * */
	@Override
	public Map<String, String> queryEqmShiftDatasFilter(EqmShiftDataBean eqmsb) {
		StringBuffer sql1=new StringBuffer(1000);//有效作业率,产量,故障
		Map<String, String> map=new HashMap<String, String>();
	   //查询当前工单信息
	   String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
	   //查询当前工单信息
	   List<?> nList=getShiftAndDate(today,eqmsb.getEqp_id());
	   String oid="";//工单ID
	   String shift_id=""; //班次
	   String oid_date=""; //工单日期
	   if(nList.size()>0 && nList!=null){
		   Object[] temp=(Object[]) nList.get(0);
		   shift_id=temp[1].toString(); //班次
		   oid_date=temp[6].toString();//工单日期
		   oid=temp[0].toString();//工单ID 
	   }
	   map=getShiftDataMap(eqmsb,map, shift_id, oid_date,sql1);
		
		/**
		 * 查询：实领，虚领数据
		 * */
		//清空
		sql1.setLength(0);
		sql1.append("  select zl_pz,zl_ss,status,fl_type,zc_type From SCH_STAT_SHIFT_FLINFO where mec_id='"+SysEqpTypeBase.filterEqpTypeCode+"' and oid='"+oid+"' and fl_type in ('1','2','3') ORDER BY fl_type ");
		List<?> listSlorXL=statShiftFLInfoDao.queryBySql(sql1.toString());
		//定义实领，虚领数组,虚退
		String zj_sl="";
		String zj_xl="";
		String zj_xt="";
		if(listSlorXL.size()>0 && listSlorXL!=null){
			for(int i=0;i<listSlorXL.size();i++){
				Object[] temp=(Object[]) listSlorXL.get(i);
				Integer fl_type=Integer.parseInt(temp[3].toString());
				if(fl_type==1){//实领
					 zj_sl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString();
				}else if(fl_type==2){//虚领
					 zj_xl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString();
				}else if(fl_type==3){//虚退
					zj_xt=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString();
				}
			}
	    }
		map.put("zj_sl", zj_sl);
		map.put("zj_xl", zj_xl);
		map.put("zj_xt", zj_xt);
		return map;
	}
	
	
	/**
	 * [功能描述]：wct交接班维护-包装机辅料保存-包装机
	 *      zb_lbz[]- 铝箔纸       zb_xhz[]- 条盒纸      zb_xhm[] - 条盒膜
	 *      zb_tmz[]- 条盒纸       zb_thm[]-条盒膜        zb_kz[] - 卡纸
	 *      zb_fq[] - 封签          zb_lx1[] - 拉线1  zb_lx2[] -拉线2  
	 * @createTime 2015年11月13日12:37:36
	 * @author wanchanghuang
	 * */
	@Override
	public void saveShiftPacker(HttpSession session, String[] zb_lbz,
			String[] zb_xhz, String[] zb_xhm, String[] zb_thz,
			String[] zb_thm, String[] zb_kz, String[] zb_fq,
			String[] zb_lx1, String[] zb_lx2) {
		 LoginBean loginWctInfoBean = (LoginBean) session.getAttribute("loginWctEqmInfo");
		   LoginBean loginInfoBean = (LoginBean) session.getAttribute("loginInfo");
	       StatShiftFLInfo ssf=new StatShiftFLInfo();
	       ssf.setMec_id(SysEqpTypeBase.packerEqpTypeCode+""); //卷烟机
	       ssf.setEqp_id(loginInfoBean.getEquipmentId());//班次
	       String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
		   //查询当前工单信息
		   List<?> nList=getShiftAndDate(today,loginInfoBean.getEquipmentId());
		   //班次
		   String shift_id="";
		   //品牌ID
		   String mat_id="";
		   //品牌名称
		   String mat_name="";
		   //工单日期
		   String oid_date="";
		   if(nList.size()>0 && nList!=null){
			   Object[] temp=(Object[]) nList.get(0);
			   ssf.setTeam_id(temp[2].toString()); //班组
			   shift_id=temp[1].toString(); //班次
			   ssf.setOid(temp[0].toString()); //当前工单
			   mat_id=temp[4].toString();//品牌ID
			   mat_name=temp[5].toString();//品牌name
			   oid_date=temp[6].toString();//工单日期
		   }
		   ssf.setShift_id(shift_id);
		   ssf.setMat_id(mat_id);
		   ssf.setMat_name(mat_name);
	       ssf.setHandover_user_id(loginWctInfoBean.getUserId());
	       ssf.setHandover_user_name(loginWctInfoBean.getName());
	       ssf.setCreate_time(new Date());
	       ssf.setUpdate_time(new Date());
	       ssf.setUpdate_user_id(loginWctInfoBean.getUserId());    
	       ssf.setZc_type(0);
	       ssf.setFl_type(1); //辅料类型  1-实领  2-虚领  3-虚退
	       ssf.setStatus(1); //1-通过   0-默认
	       ssf.setDel(0);
	       //实领
	       ssf.setZb_lbz(Double.parseDouble(zb_lbz[1]));
	       ssf.setZb_xhz(Double.parseDouble(zb_xhz[1]));
	       ssf.setZb_xhm(Double.parseDouble(zb_xhm[1]));
	       ssf.setZb_thz(Double.parseDouble(zb_thz[1]));
	       ssf.setZb_thm(Double.parseDouble(zb_thm[1]));
	       ssf.setZb_kz(Double.parseDouble(zb_kz[1]));
	       ssf.setZb_fq(Double.parseDouble(zb_fq[1]));
	       ssf.setZb_lx1(Double.parseDouble(zb_lx1[1]));
	       ssf.setZb_lx2(Double.parseDouble(zb_lx2[1]));
	       String doid=""; //下一班的工单ID
	       String uoid=""; //上一班的工单ID
	       /**
	        * 【功能描述】：下一班次数据处理
	        *          (下一班的)虚领=(当前班次的)虚退
	        *          (下一班的)品牌=(当前班次的)品牌
	        * */
	       String dshift_id="";//下一班班次
	       String dteam_id="";//下一班班组
	       String dmat_id="";//下一班品牌ID
	       String umat_id="";//上一班品牌ID
	       //查询下一班工单信息
		   List<?> dList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,1);
		   if(dList.size()>0 && dList!=null){
			   Object[] temp=(Object[]) dList.get(0);
			   doid=temp[0].toString(); //当前工单
			   dmat_id=temp[4].toString();//品牌ID 
			   dteam_id=temp[2].toString(); //班组
			   dshift_id=temp[1].toString(); //班次
		   }
		   //判断当前品牌=下一班品牌
		   if(mat_id.equals(dmat_id)){
			   ssf.setDoid(doid);
		   }
		   //查询上一班工单信息
		   List<?> uList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,2);
		   if(uList.size()>0 && uList!=null){
			   Object[] temp=(Object[]) uList.get(0);
			   uoid=temp[0].toString(); //当前工单
			   umat_id=temp[4].toString();//品牌ID
		   }
		   //判断当前品牌=上一班品牌
		   if(mat_id.equals(umat_id)){
			   ssf.setUoid(uoid);
		   }
		   //实领-保存
		   saveSlFLData(ssf);
		   
		   //虚领
		   try {
			   StatShiftFLInfo ssf2=new StatShiftFLInfo();
			   BeanConvertor.copyProperties(ssf, ssf2); //第一个对象拷贝到第一个对象
			   ssf2.setZb_lbz(Double.parseDouble(zb_lbz[0]));
    	       ssf2.setZb_xhz(Double.parseDouble(zb_xhz[0]));
    	       ssf2.setZb_xhm(Double.parseDouble(zb_xhm[0]));
    	       ssf2.setZb_thz(Double.parseDouble(zb_thz[0]));
    	       ssf2.setZb_thm(Double.parseDouble(zb_thm[0]));
    	       ssf2.setZb_kz(Double.parseDouble(zb_kz[0]));
    	       ssf2.setZb_fq(Double.parseDouble(zb_fq[0]));
    	       ssf2.setZb_lx1(Double.parseDouble(zb_lx1[0]));
    	       ssf2.setZb_lx2(Double.parseDouble(zb_lx2[0]));
		       ssf2.setFl_type(2); //辅料类型  1-实领  2-虚领  3-虚退
		       ssf2.setCreate_time(new Date());
		       ssf2.setUpdate_time(new Date());
		       //虚领-保存
		       saveSlFLData(ssf2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  
		   //本班虚退
		   try {
		       StatShiftFLInfo ssf3=new StatShiftFLInfo();
			   BeanConvertor.copyProperties(ssf, ssf3); //第一个对象拷贝到第一个对象
			   ssf3.setZb_lbz(Double.parseDouble(zb_lbz[2]));
    	       ssf3.setZb_xhz(Double.parseDouble(zb_xhz[2]));
    	       ssf3.setZb_xhm(Double.parseDouble(zb_xhm[2]));
    	       ssf3.setZb_thz(Double.parseDouble(zb_thz[2]));
    	       ssf3.setZb_thm(Double.parseDouble(zb_thm[2]));
    	       ssf3.setZb_kz(Double.parseDouble(zb_kz[2]));
    	       ssf3.setZb_fq(Double.parseDouble(zb_fq[2]));
    	       ssf3.setZb_lx1(Double.parseDouble(zb_lx1[2]));
    	       ssf3.setZb_lx2(Double.parseDouble(zb_lx2[2]));
	           ssf3.setFl_type(3); //辅料类型  1-实领  2-虚领  3-虚退
	           ssf3.setCreate_time(new Date());
	           ssf3.setUpdate_time(new Date());
	           //虚退-保存
	           statShiftFLInfoDao.saveBackKey(ssf3);
		    } catch (Exception e) {
		    	e.printStackTrace();
			}
		   
		   //如果品牌相等，添加下一班虚领
	       if(mat_id.equals(dmat_id)){ //判断虚退也就是下一班虚领，品牌相等才能虚退
	    	   try {
	    	       StatShiftFLInfo ssf4=new StatShiftFLInfo();
	    		   BeanConvertor.copyProperties(ssf, ssf4); //第一个对象拷贝到第一个对象
	    		   ssf4.setZb_lbz(Double.parseDouble(zb_lbz[2]));
	    	       ssf4.setZb_xhz(Double.parseDouble(zb_xhz[2]));
	    	       ssf4.setZb_xhm(Double.parseDouble(zb_xhm[2]));
	    	       ssf4.setZb_thz(Double.parseDouble(zb_thz[2]));
	    	       ssf4.setZb_thm(Double.parseDouble(zb_thm[2]));
	    	       ssf4.setZb_kz(Double.parseDouble(zb_kz[2]));
	    	       ssf4.setZb_fq(Double.parseDouble(zb_fq[2]));
	    	       ssf4.setZb_lx1(Double.parseDouble(zb_lx1[2]));
	    	       ssf4.setZb_lx2(Double.parseDouble(zb_lx2[2]));
	               ssf4.setFl_type(2); //本班虚退   = 下一班虚领
	               ssf4.setStatus(0);//下一班虚领默认状态  0-默认
	               ssf4.setCreate_time(new Date());
	               ssf4.setUpdate_time(new Date());
	               //工单基本信息
	               ssf4.setShift_id(dshift_id);
	               ssf4.setTeam_id(dteam_id);
	               ssf4.setOid(ssf.getDoid());
	               ssf4.setUoid(ssf.getOid());
	               //清空DOID
	               ssf4.setDoid("");
	               //下一班虚领-保存
	               statShiftFLInfoDao.saveBackKey(ssf4);
	    	    } catch (Exception e) {
	    			e.printStackTrace();
	    		}
		   }
	}
	
	
	/**
	 * 【功能说明】：查询产量，有效作业率，故障	-包装机
	 * @author wch
	 * @createTime 2015年11月13日13:22:07
	 * 
	 * */
	@Override
	public Map<String, String> queryEqmShiftDatasPacker(EqmShiftDataBean eqmsb) {
		StringBuffer sql1=new StringBuffer(1000);//有效作业率,产量,故障
		Map<String, String> map=new HashMap<String, String>();
	   //查询当前工单信息
	   String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
	   //查询当前工单信息
	   List<?> nList=getShiftAndDate(today,eqmsb.getEqp_id());
	   String oid="";//工单ID
	   String shift_id=""; //班次
	   String oid_date=""; //工单日期
	   if(nList.size()>0 && nList!=null){
		   Object[] temp=(Object[]) nList.get(0);
		   shift_id=temp[1].toString(); //班次
		   oid_date=temp[6].toString();//工单日期
		   oid=temp[0].toString();//工单ID 
	   }
	   map=getShiftDataMap(eqmsb,map, shift_id, oid_date,sql1);
		
		/**
		 * 查询：实领，虚领数据
		 * */
		//清空
		sql1.setLength(0);
		sql1.append("  select zb_lbz,zb_xhz,zb_xhm,zb_thz,zb_thm,zb_kz,zb_fq,zb_lx1,zb_lx2,status,fl_type,zc_type From SCH_STAT_SHIFT_FLINFO where mec_id='"+SysEqpTypeBase.packerEqpTypeCode+"' and oid='"+oid+"' and fl_type in ('1','2','3') ORDER BY fl_type ");
		List<?> listSlorXL=statShiftFLInfoDao.queryBySql(sql1.toString());
		//定义实领，虚领数组,虚退
		String zj_sl="";
		String zj_xl="";
		String zj_xt="";
		if(listSlorXL.size()>0 && listSlorXL!=null){
			for(int i=0;i<listSlorXL.size();i++){
				Object[] temp=(Object[]) listSlorXL.get(i);
				Integer fl_type=Integer.parseInt(temp[10].toString());
				if(fl_type==1){//实领
					 zj_sl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString()+","+temp[3].toString()+","+temp[4].toString()+","+temp[5].toString()+","+temp[6].toString()+","+temp[7].toString()+","+temp[8].toString()+","+temp[9].toString()+","+temp[10].toString();
				}else if(fl_type==2){//虚领
					 zj_xl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString()+","+temp[3].toString()+","+temp[4].toString()+","+temp[5].toString()+","+temp[6].toString()+","+temp[7].toString()+","+temp[8].toString()+","+temp[9].toString()+","+temp[10].toString();
				}else if(fl_type==3){//虚退
					zj_xt=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString()+","+temp[3].toString()+","+temp[4].toString()+","+temp[5].toString()+","+temp[6].toString()+","+temp[7].toString()+","+temp[8].toString()+","+temp[9].toString()+","+temp[10].toString();
				}
			}
	    }
		map.put("zj_sl", zj_sl);
		map.put("zj_xl", zj_xl);
		map.put("zj_xt", zj_xt);
		return map;
	}
	
	
	
	/**
	 * 【功能说明】：查询产量，有效作业率，故障	-卷烟机
	 * @author wch
	 * @createTime 2015年11月2日16:21:43
	 * 
	 * */
	@Override
	public Map<String, String> queryEqmShiftDatas(EqmShiftDataBean eqmsb) {
		StringBuffer sql1=new StringBuffer(1000);//有效作业率,产量,故障
		Map<String, String> map=new HashMap<String, String>();
	   //查询当前工单信息
	   String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
	   //查询当前工单信息
	   List<?> nList=getShiftAndDate(today,eqmsb.getEqp_id());
	   String oid="";//工单ID
	   String shift_id=""; //班次
	   String oid_date=""; //工单日期
	   if(nList.size()>0 && nList!=null){
		   Object[] temp=(Object[]) nList.get(0);
		   shift_id=temp[1].toString(); //班次
		   oid_date=temp[6].toString();//工单日期
		   oid=temp[0].toString();//工单ID 
	   }
	   map=getShiftDataMap(eqmsb,map, shift_id, oid_date,sql1);
		
		/**
		 * 查询：实领，虚领数据
		 * */
		//清空
		sql1.setLength(0);
		sql1.append("  select zj_pz,zj_ssz,zj_lb,status,fl_type,zc_type,zj_szy From SCH_STAT_SHIFT_FLINFO where mec_id='"+SysEqpTypeBase.rollerEqpTypeCode+"' and oid='"+oid+"' and fl_type in ('1','2','3') ORDER BY fl_type ");
		List<?> listSlorXL=statShiftFLInfoDao.queryBySql(sql1.toString());
		//定义实领，虚领数组,虚退
		String zj_sl="";
		String zj_xl="";
		String zj_xt="";
		if(listSlorXL.size()>0 && listSlorXL!=null){
			for(int i=0;i<listSlorXL.size();i++){
				Object[] temp=(Object[]) listSlorXL.get(i);
				Integer fl_type=Integer.parseInt(temp[4].toString());
				if(fl_type==1){//实领
					 zj_sl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString()+","+temp[3].toString()+","+temp[6].toString();
				}else if(fl_type==2){//虚领
					 zj_xl=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString()+","+temp[3].toString()+","+temp[6].toString();
				}else if(fl_type==3){
					 zj_xt=temp[0].toString()+","+temp[1].toString()+","+temp[2].toString()+","+temp[3].toString()+","+temp[6].toString();
				}
			}
	    }
		map.put("zj_sl", zj_sl);
		map.put("zj_xl", zj_xl);
		map.put("zj_xt", zj_xt);
		return map;
	}
	
	

	/**
	 * [功能描述]：wct交接班维护-卷烟机辅料保存 -卷烟机
	 *      zj_pz[]   盘纸
	 *      zj_ssz[]  水松纸
	 *      zj_lb[]   滤棒
	 * @createTime 2015年11月6日16:22:06
	 * @author wanchanghuang
	 * */
	@Override
	public boolean saveShiftRoller(HttpSession session,String[] zj_pz, String[] zj_ssz, String[] zj_lb,String[] zj_szy) {
	   LoginBean loginWctInfoBean = (LoginBean) session.getAttribute("loginWctEqmInfo");
	   LoginBean loginInfoBean = (LoginBean) session.getAttribute("loginInfo");
       StatShiftFLInfo ssf=new StatShiftFLInfo();
       ssf.setMec_id(SysEqpTypeBase.rollerEqpTypeCode+""); //卷烟机
       ssf.setEqp_id(loginInfoBean.getEquipmentId());//班次
       String today=DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
	   //查询当前工单信息
	   List<?> nList=getShiftAndDate(today,loginInfoBean.getEquipmentId());
	   //班次
	   String shift_id="";
	   //品牌ID
	   String mat_id="";
	   //品牌名称
	   String mat_name="";
	   //工单日期
	   String oid_date="";
	   if(nList.size()>0 && nList!=null){
		   Object[] temp=(Object[]) nList.get(0);
		   ssf.setTeam_id(temp[2].toString()); //班组
		   shift_id=temp[1].toString(); //班次
		   ssf.setOid(temp[0].toString()); //当前工单
		   mat_id=temp[4].toString();//品牌ID
		   mat_name=temp[5].toString();//品牌name
		   oid_date=temp[6].toString();//工单日期
	   }
	   ssf.setShift_id(shift_id);
	   ssf.setMat_id(mat_id);
	   ssf.setMat_name(mat_name);
       ssf.setHandover_user_id(loginWctInfoBean.getUserId());
       ssf.setHandover_user_name(loginWctInfoBean.getName());
       ssf.setCreate_time(new Date());
       ssf.setUpdate_time(new Date());
       ssf.setUpdate_user_id(loginWctInfoBean.getUserId());    
       ssf.setZc_type(0);
       ssf.setFl_type(1); //辅料类型  1-实领  2-虚领  3-虚退
       ssf.setStatus(1); //1-通过   0-默认
       ssf.setDel(0);
       //实领
       ssf.setZj_pz(Double.parseDouble(zj_pz[1]));
       ssf.setZj_ssz(Double.parseDouble(zj_ssz[1]));
       ssf.setZj_lb(Double.parseDouble(zj_lb[1]));
       ssf.setZj_szy(Double.parseDouble(zj_szy[1]));
       String doid=""; //下一班的工单ID
       String uoid=""; //上一班的工单ID
       /**
        * 【功能描述】：下一班次数据处理
        *          (下一班的)虚领=(当前班次的)虚退
        *          (下一班的)品牌=(当前班次的)品牌
        * */
       String dshift_id="";//下一班班次
       String dteam_id="";//下一班班组
       String dmat_id="";//下一班品牌ID
       String umat_id="";//上一班品牌ID
       //查询下一班工单信息
	   List<?> dList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,1);
	   if(dList.size()>0 && dList!=null){
		   Object[] temp=(Object[]) dList.get(0);
		   doid=temp[0].toString(); //当前工单
		   dmat_id=temp[4].toString();//品牌ID 
		   dteam_id=temp[2].toString(); //班组
		   dshift_id=temp[1].toString(); //班次
	   }
	   //判断当前品牌=下一班品牌
	   if(mat_id.equals(dmat_id)){
		   ssf.setDoid(doid);
	   }
	   //查询上一班工单信息
	   List<?> uList=getDownShiftData(oid_date.substring(0,10),loginInfoBean.getEquipmentId() , shift_id,2);
	   if(uList.size()>0 && uList!=null){
		   Object[] temp=(Object[]) uList.get(0);
		   uoid=temp[0].toString(); //当前工单
		   umat_id=temp[4].toString();//品牌ID
	   }
	   //判断当前品牌=上一班品牌
	   if(mat_id.equals(umat_id)){
		   ssf.setUoid(uoid);
	   }
	   //实领-保存
	   saveSlFLData(ssf);
	   
	   //虚领
	   try {
		   StatShiftFLInfo ssf2=new StatShiftFLInfo();
		   BeanConvertor.copyProperties(ssf, ssf2); //第一个对象拷贝到第一个对象
		   ssf2.setZj_pz(Double.parseDouble(zj_pz[0]));
	       ssf2.setZj_ssz(Double.parseDouble(zj_ssz[0]));
	       ssf2.setZj_lb(Double.parseDouble(zj_lb[0]));
	       ssf2.setZj_szy(Double.parseDouble(zj_szy[0]));
	       ssf2.setFl_type(2); //辅料类型  1-实领  2-虚领  3-虚退
	       ssf2.setCreate_time(new Date());
	       ssf2.setUpdate_time(new Date());
	       //虚领-保存
	       saveSlFLData(ssf2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	   //本班虚退
	   try {
	       StatShiftFLInfo ssf3=new StatShiftFLInfo();
		   BeanConvertor.copyProperties(ssf, ssf3); //第一个对象拷贝到第一个对象
	       ssf3.setZj_pz(Double.parseDouble(zj_pz[2]));
           ssf3.setZj_ssz(Double.parseDouble(zj_ssz[2]));
           ssf3.setZj_lb(Double.parseDouble(zj_lb[2]));
           ssf3.setZj_szy(Double.parseDouble(zj_szy[2]));
           ssf3.setFl_type(3); //辅料类型  1-实领  2-虚领  3-虚退
           ssf3.setCreate_time(new Date());
           ssf3.setUpdate_time(new Date());
           //虚退-保存
           statShiftFLInfoDao.saveBackKey(ssf3);
	    } catch (Exception e) {
	    	e.printStackTrace();
		}
	   
	   //如果品牌相等，添加下一班虚领
       if(mat_id.equals(dmat_id)){ //判断虚退也就是下一班虚领，品牌相等才能虚退
    	   try {
    	       StatShiftFLInfo ssf4=new StatShiftFLInfo();
    		   BeanConvertor.copyProperties(ssf, ssf4); //第一个对象拷贝到第一个对象
    	       ssf4.setZj_pz(Double.parseDouble(zj_pz[2]));
               ssf4.setZj_ssz(Double.parseDouble(zj_ssz[2]));
               ssf4.setZj_lb(Double.parseDouble(zj_lb[2]));
               ssf4.setZj_szy(Double.parseDouble(zj_szy[2]));
               ssf4.setFl_type(2); //本班虚退   = 下一班虚领
               ssf4.setStatus(0);//下一班虚领默认状态  0-默认
               ssf4.setCreate_time(new Date());
               ssf4.setUpdate_time(new Date());
               //工单基本信息
               ssf4.setShift_id(dshift_id);
               ssf4.setTeam_id(dteam_id);
               ssf4.setOid(ssf.getDoid());
               ssf4.setUoid(ssf.getOid());
               //清空DOID
               ssf4.setDoid("");
               //下一班虚领-保存
               statShiftFLInfoDao.saveBackKey(ssf4);
    	    } catch (Exception e) {
    			e.printStackTrace();
    		}
	   }
	   return false;
	}
	

	/**
	 * 【功能说明】：交接班-实领 -卷烟机
	 * @author  wch
	 * @createTime  2015年11月9日15:51:25
	 * */
	public void saveSlFLData(StatShiftFLInfo ssf){
		StringBuffer hql = new StringBuffer(1000);
		hql.append("select id,del from SCH_STAT_SHIFT_FLINFO where oid='"+ssf.getOid()+"' and eqp_id='"+ssf.getEqp_id()+"'  and shift_id='"+ssf.getShift_id()+"' and  team_id='"+ssf.getTeam_id()+"' and mec_id='"+ssf.getMec_id()+"' and fl_type='"+ssf.getFl_type()+"'");
		List<?> listSL=statShiftFLInfoDao.queryBySql(hql.toString());
		if(listSL.size()>0 && listSL!=null){ //虚领，实领修改
			//判断上一班，下一班工单号是否存在，如果不存在默认值：空
			if(!StringUtil.notNull(ssf.getUoid())){
				ssf.setUoid("");
			}
            if(!StringUtil.notNull(ssf.getDoid())){
				ssf.setDoid("");
			}
			
			Object[] temp=(Object[]) listSL.get(0);
			hql.setLength(0);//清空StringBuffer
			if("1".equals(ssf.getMec_id()) ){
				//1-卷烟机
				hql.append(" update SCH_STAT_SHIFT_FLINFO set status='1', doid='"+ssf.getDoid()+"',uoid='"+ssf.getUoid()+"',update_time=GETDATE(),zj_pz='"+ssf.getZj_pz()+"',zj_ssz='"+ssf.getZj_ssz()+"',zj_lb='"+ssf.getZj_lb()+"',zj_szy='"+ssf.getZj_szy()+"',");
				hql.append(" update_user_id='"+ssf.getUpdate_user_id()+"' ,handover_user_id='"+ssf.getHandover_user_id()+"',handover_user_name='"+ssf.getHandover_user_name()+"', receive_user_id='"+ssf.getHandover_user_id()+"',receive_user_name='"+ssf.getHandover_user_name()+"' where id='"+Long.parseLong(temp[0].toString())+"' ");
			}else if("2".equals(ssf.getMec_id())){
				//2-包装机
				hql.append(" update SCH_STAT_SHIFT_FLINFO set status='1', doid='"+ssf.getDoid()+"',uoid='"+ssf.getUoid()+"',update_time=GETDATE(),zb_lbz='"+ssf.getZb_lbz()+"',zb_thz='"+ssf.getZb_thz()+"',zb_thm='"+ssf.getZb_thm()+"', zb_xhz='"+ssf.getZb_xhz()+"',zb_xhm='"+ssf.getZb_xhm()+"',zb_kz='"+ssf.getZb_kz()+"',zb_fq='"+ssf.getZb_fq()+"',zb_lx1='"+ssf.getZb_lx1()+"',zb_lx2='"+ssf.getZb_lx2()+"' ,  ");
				hql.append(" update_user_id='"+ssf.getUpdate_user_id()+"' ,handover_user_id='"+ssf.getHandover_user_id()+"',handover_user_name='"+ssf.getHandover_user_name()+"', receive_user_id='"+ssf.getHandover_user_id()+"',receive_user_name='"+ssf.getHandover_user_name()+"' where id='"+Long.parseLong(temp[0].toString())+"' ");
			}else if("3".equals(ssf.getMec_id())){
				//3-成型机
				hql.append(" update SCH_STAT_SHIFT_FLINFO set status='1', doid='"+ssf.getDoid()+"',uoid='"+ssf.getUoid()+"',update_time=GETDATE(),zl_pz='"+ssf.getZl_pz()+"',zl_ss='"+ssf.getZl_ss()+"' ,  ");
				hql.append(" update_user_id='"+ssf.getUpdate_user_id()+"' ,handover_user_id='"+ssf.getHandover_user_id()+"',handover_user_name='"+ssf.getHandover_user_name()+"', receive_user_id='"+ssf.getHandover_user_id()+"',receive_user_name='"+ssf.getHandover_user_name()+"' where id='"+Long.parseLong(temp[0].toString())+"' ");
			}else if("4".equals(ssf.getMec_id())){
				//4-封箱机
				hql.append(" update SCH_STAT_SHIFT_FLINFO set status='1', doid='"+ssf.getDoid()+"',uoid='"+ssf.getUoid()+"',update_time=GETDATE(),yf_xp='"+ssf.getYf_xp()+"',yf_jd='"+ssf.getYf_jd()+"' ,  ");
				hql.append(" update_user_id='"+ssf.getUpdate_user_id()+"' ,handover_user_id='"+ssf.getHandover_user_id()+"',handover_user_name='"+ssf.getHandover_user_name()+"', receive_user_id='"+ssf.getHandover_user_id()+"',receive_user_name='"+ssf.getHandover_user_name()+"' where id='"+Long.parseLong(temp[0].toString())+"' ");
			}
			//执行语句
			statShiftFLInfoDao.updateInfo(hql.toString(), null);
		}else{ //虚领，实领添加
			statShiftFLInfoDao.saveBackKey(ssf);
		}
	}
	
	/**
	 * 【功能描述】：根据shift_id-班次, eqp_id-设备ID 
	 *                     得到下一班工单和上一班工单公共方法 -卷烟机
	 * @author wch
	 * @createTime 2015年11月9日14:53:55
	 * */
	public List<?> getDownShiftData(String today,String eqp_id,String shift_id,int type){
		StringBuffer sbf = new StringBuffer(1000);
		Integer shift=0;
		if(type==1){
			//根据班次，得到下一班次
			if("1".equals(shift_id)){
				shift=2; //早-》中
			}else if("2".equals(shift_id)){
				shift=3; //中——》晚
			}else if("3".equals(shift_id)){
				shift=1; //晚-》第二天早班
				today=DateUtil.dateFormatCal(today, 1);
			}
		}else if(type==2) {
			//根据班次，得到上一班次
			if("1".equals(shift_id)){
				shift=3; //早-》昨天晚上晚
				today=DateUtil.dateFormatCal(today, -1);
			}else if("2".equals(shift_id)){
				shift=1; //中——》早
			}else if("3".equals(shift_id)){
				shift=2; //晚-》中
			}
		}
        sbf.append(" select id,shift,team,eqp,mat,(select name from MD_MAT where id=mat) as mat_name,ep_name From SCH_WORKORDER  where convert(varchar(50),date,23)='"+today+"' ");
        sbf.append(" and shift='"+shift+"' and eqp='"+eqp_id+"' ");
        List<?> olist=statShiftFLInfoDao.queryBySql(sbf.toString());
		return olist;
	}
	
	/**
	 * 【功能描述】：通过：dateTime-日期时间   eqp_id:设备ID -卷烟机
                                                 返回当前工单信息
	 * @author wch
	 * @createTime 2015年11月9日10:07:49
	 * */
	public List<?> getShiftAndDate(String date,String eqp_id){
		StringBuffer sbf = new StringBuffer(1000);
		Integer shift_id=getShiftAndDate(date);
		if(shift_id==3){
			date=DateUtil.getYesterdayf();
		}
        sbf.append(" select id,shift,team,eqp,mat,(select name from MD_MAT where id=mat) as mat_name,date,ep_name From SCH_WORKORDER  where convert(varchar(50),date,23)='"+date.substring(0,10)+"' ");
        sbf.append(" and shift='"+shift_id+"'  and eqp='"+eqp_id+"' ");
        List<?> olist=statShiftFLInfoDao.queryBySql(sbf.toString());
		return olist;
	}
	
	/**
	 * 【功能描述】：通过当前时间，
	 *           返回数组-'班次'  -卷烟机
	 * @author wanchanghuang
	 * @createTime 2015年11月9日10:13:35
	 * */
	public Integer getShiftAndDate(String dateTime){ // 2015-11-09 01:23:00
		try {
			Integer shift_id=0;
			String dateToDay=dateTime.substring(0,10);  // 2015-11-09
			//早班   
			String gStart=dateToDay+" 09:00:00"; // 2015-11-09 09:00:00
			String gEnd=dateToDay+" 17:00:00";   // 2015-11-09 17:00:00
			//中班  
			String zStart="";
			String zEnd="";
			Integer sf=Integer.parseInt(dateTime.substring(11,13));
			//通过截取小时，判断是否跨天
			if(0>=sf && sf<2){
				zStart=dateToDay+" 00:00:00"; // 2015-11-09 00:00:00
				zEnd=dateToDay+" 02:00:00";   // 2015-11-09 02:00:00
			}else{
				zStart=dateToDay+" 17:00:00"; // 2015-11-09 17:00:00
				zEnd=dateToDay+" 23:59:59";   // 2015-11-09 23:59:59
			}
			//晚班
			String wStart=dateToDay+" 02:00:00"; // 2015-11-09 02:00:00
			String wEnd=dateToDay+" 09:00:00";   // 2015-11-09 09:00:00
			//转换成long类型比较大小
			long sdTime = DateUtil.getTime(dateTime,"yyyy-MM-dd HH:mm:ss");
			long gStartTime=DateUtil.getTime(gStart,"yyyy-MM-dd HH:mm:ss");
			long gEndTime=DateUtil.getTime(gEnd,"yyyy-MM-dd HH:mm:ss");
			long zStartTime=DateUtil.getTime(zStart,"yyyy-MM-dd HH:mm:ss");
			long zEndTime=DateUtil.getTime(zEnd,"yyyy-MM-dd HH:mm:ss");
			long wStartTime=DateUtil.getTime(wStart,"yyyy-MM-dd HH:mm:ss");
			long wEndTime=DateUtil.getTime(wEnd,"yyyy-MM-dd HH:mm:ss");
			if(sdTime>=gStartTime && sdTime<=gEndTime){ //早班
				shift_id=1;
			}else if(sdTime>=zStartTime && sdTime<=zEndTime){//中班
				shift_id=2;
			}else if(sdTime>=wStartTime && sdTime<=wEndTime){ //晚班
				shift_id=3;
			}
			return shift_id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 交接班保存WCT物料呼叫信息
	 * shisihai
	 */
	@Override
	public boolean saveCallMatter(LoginBean loginBean,LoginBean loginBean2,StatShiftFLInfo bean) {
		//获取工单信息
		List<?> ls=this.getWorkOrderInfo(loginBean.getEquipmentId());
		Object[] obj=null;
		boolean flag=false;
		if(null!=ls&&ls.size()>0){
			obj=(Object[]) ls.get(0);
		}else{
			return false;
		}
		StringBuffer sb=null;
		//根据设备code判断机型
		Integer eqpCode=Integer.parseInt(loginBean.getEquipmentCode());
		//先判断是否已经有过要料纪录
		sb=new StringBuffer();
		sb.append("select id from SCH_STAT_SHIFT_FLINFO where fl_type=1 and oid= ? ");
		List<?> l=statShiftFLInfoDao.queryBySql(sb.toString(), StringUtil.convertObjToString(obj[0]));
		if(eqpCode<31){//卷烟机
				//更新操作
				if(null!=l&&l.size()>0){
					StatShiftFLInfo b =statShiftFLInfoDao.findById(Long.valueOf(l.get(0).toString()));
					this.updateRollerData(loginBean, loginBean2, bean, b);
					flag=true;
				}else{
				//新增   
					StatShiftFLInfo b = insertInfo(loginBean, loginBean2, bean,SysEqpTypeBase.rollerEqpTypeCode, obj);
					statShiftFLInfoDao.save(b);
					flag=true;
				}
		}else if(eqpCode>30&&eqpCode<61){//包装机
				//更新要料数据
				if(null!=l&&l.size()>0){
					StatShiftFLInfo b =statShiftFLInfoDao.findById(Long.valueOf(l.get(0).toString()));
					this.updatePackData(loginBean, loginBean2, bean, b);
					flag=true;
				}else{
					//新增
					StatShiftFLInfo b = insertInfo(loginBean, loginBean2, bean,SysEqpTypeBase.packerEqpTypeCode, obj);
					statShiftFLInfoDao.save(b);
					flag=true;
				}
			
		}else if(eqpCode>60&&eqpCode<71){//封箱机
				//更新要料数据
				if(null!=l&&l.size()>0){
					StatShiftFLInfo b =statShiftFLInfoDao.findById(Long.valueOf(l.get(0).toString()));
					this.updateSealerData(loginBean, loginBean2, bean, b);
					flag=true;					
				}else{
					//新增
					StatShiftFLInfo b = insertInfo(loginBean, loginBean2, bean,SysEqpTypeBase.sealerEqpTypeCode, obj);
					statShiftFLInfoDao.save(b);
					flag=true;
				}
			
		}else if(eqpCode>100&&eqpCode<131){//成型机
				//更新要料数据
				if(null!=l&&l.size()>0){
					StatShiftFLInfo b =statShiftFLInfoDao.findById(Long.valueOf(l.get(0).toString()));
					this.updateFilterData(loginBean, loginBean2, bean, b);
					flag=true;
				}else{
					//新增
					StatShiftFLInfo b = insertInfo(loginBean, loginBean2, bean,SysEqpTypeBase.filterEqpTypeCode, obj);
					statShiftFLInfoDao.save(b);
					flag=true;
				}
		}
		return flag;
	}

	/**
	 * 更新成型机交接班数据
	 * shisihai
	 * @param loginBean
	 * @param loginBean2
	 * @param bean
	 * @param b
	 */
	private void updateFilterData(LoginBean loginBean, LoginBean loginBean2, StatShiftFLInfo bean, StatShiftFLInfo b) {
		//更新人id
		if(null!=loginBean&&loginBean.getUserId()!=null){
			b.setUpdate_user_id(loginBean.getUserId());
			b.setReceive_user_name(loginBean.getLoginName());
		}else if(null!=loginBean2&&loginBean2.getUserId()!=null){
			b.setUpdate_user_id(loginBean2.getUserId());
			b.setReceive_user_name(loginBean2.getLoginName());
		}
		//更新时间
		b.setUpdate_time(new Date());
		//盘纸
		Double zl_pz=this.updateDoubleData(bean.getZl_pz(), b.getZl_pz());
		b.setZl_pz(zl_pz);
		//丝束
		Double zl_ss=this.updateDoubleData(bean.getZl_ss(), b.getZl_ss());
		b.setZl_ss(zl_ss);
		//甘油
		Double zl_gy=this.updateDoubleData(bean.getZl_gy(),b.getZl_gy());
		b.setZl_gy(zl_gy);
		//热融胶
		Double zl_rrj=this.updateDoubleData(bean.getZl_rrj(), b.getZl_rrj());
		b.setZl_rrj(zl_rrj);
		//搭口胶
		Double zl_dkj=this.updateDoubleData(bean.getZl_dkj(), b.getZl_dkj());
		b.setZl_dkj(zl_dkj);
		statShiftFLInfoDao.saveOrUpdate(b);
	}

/**
 * 更新封箱机交接班数据
 * shisihai
 * @param loginBean
 * @param loginBean2
 * @param bean
 * @param b
 */
	private void updateSealerData(LoginBean loginBean, LoginBean loginBean2, StatShiftFLInfo bean, StatShiftFLInfo b) {
		//更新人id
		if(null!=loginBean&&loginBean.getUserId()!=null){
			b.setUpdate_user_id(loginBean.getUserId());
			b.setReceive_user_name(loginBean.getLoginName());
		}else if(null!=loginBean2&&loginBean2.getUserId()!=null){
			b.setUpdate_user_id(loginBean2.getUserId());
			b.setReceive_user_name(loginBean2.getLoginName());
		}
		//更新时间
		b.setUpdate_time(new Date());
		//箱皮
		Double yf_xp=this.updateDoubleData(bean.getYf_xp(), b.getYf_xp());
		b.setYf_xp(yf_xp);
		
		//胶带
		Double yf_jd=this.updateDoubleData(bean.getYf_jd(), b.getYf_jd());
		b.setYf_jd(yf_jd);
		statShiftFLInfoDao.saveOrUpdate(b);
	}

/**
 * 更新包装机交接班数据
 * shisihai
 * @param loginBean
 * @param loginBean2
 * @param bean
 * @param b
 */
	private void updatePackData(LoginBean loginBean, LoginBean loginBean2, StatShiftFLInfo bean, StatShiftFLInfo b) {
		//更新人id
		if(null!=loginBean&&loginBean.getUserId()!=null){
			b.setUpdate_user_id(loginBean.getUserId());
			b.setReceive_user_name(loginBean.getLoginName());
		}else if(null!=loginBean2&&loginBean2.getUserId()!=null){
			b.setUpdate_user_id(loginBean2.getUserId());
			b.setReceive_user_name(loginBean2.getLoginName());
		}
		//更新时间
		b.setUpdate_time(new Date());
		//铝箔纸
		Double lbz=this.updateDoubleData(bean.getZb_lbz(), b.getZb_lbz());
		b.setZb_lbz(lbz);
		//小盒纸
		Double Zb_xhz=this.updateDoubleData(bean.getZb_xhz(), b.getZb_xhz());
		b.setZb_xhz(Zb_xhz);
		//小盒膜
		Double xhm=this.updateDoubleData(bean.getZb_xhm(), b.getZb_xhm());
		b.setZb_xhm(xhm);
		//条盒纸
		Double zb_thz=this.updateDoubleData(bean.getZb_thz(), b.getZb_thz());
		b.setZb_thz(zb_thz);
		//条盒膜
		Double zb_thm=this.updateDoubleData(bean.getZb_thm(), b.getZb_thm());
		b.setZb_thm(zb_thm);
		//卡纸
		Double kz=this.updateDoubleData(bean.getZb_kz(), b.getZb_kz());
		b.setZb_kz(kz);
		//封签
		Double fq=this.updateDoubleData(bean.getZb_fq(), b.getZb_fq());
		b.setZb_fq(fq);
		//拉线1
		Double lx1=this.updateDoubleData(bean.getZb_lx1(), b.getZb_lx1());
		b.setZb_lx1(lx1);
		//拉线2
		Double lx2=this.updateDoubleData(bean.getZb_lx2(), b.getZb_lx2());
		b.setZb_lx2(lx2);
		statShiftFLInfoDao.saveOrUpdate(b);
	}

/**
 * 更新卷烟机交接班数据
 * shisihia
 * @param loginBean
 * @param loginBean2
 * @param bean
 * @param b
 */
	private void updateRollerData(LoginBean loginBean, LoginBean loginBean2, StatShiftFLInfo bean, StatShiftFLInfo b) {
		//更新人id
		if(null!=loginBean&&loginBean.getUserId()!=null){
			b.setUpdate_user_id(loginBean.getUserId());
			b.setReceive_user_name(loginBean.getLoginName());
		}else if(null!=loginBean2&&loginBean2.getUserId()!=null){
			b.setUpdate_user_id(loginBean2.getUserId());
			b.setReceive_user_name(loginBean2.getLoginName());
		}
		//更新时间
		b.setUpdate_time(new Date());
		//盘纸
		Double pz=this.updateDoubleData(bean.getZj_pz(),b.getZj_pz());
		b.setZj_pz(pz);
		//水松纸
		Double zj_ssz=this.updateDoubleData(bean.getZj_ssz(), b.getZj_ssz());
		b.setZj_ssz(zj_ssz);
		//滤棒
		Double lb=this.updateDoubleData(bean.getZj_lb(), b.getZj_lb());
		b.setZj_lb(lb);
		//散支烟
		Double szy=this.updateDoubleData(bean.getZj_szy(), b.getZj_szy());
		b.setZj_szy(szy);
		statShiftFLInfoDao.saveOrUpdate(b);
	}

	/**
	 *WCT 辅料要料新增一条记录
	 *shisihai
	 * @param loginBean 大登陆
	 * @param loginBean2小登陆
	 * @param bean
	 * @param eqpType 设备类型（在常用字段维护类中）
	 * @param obj
	 * @return
	 */
	private StatShiftFLInfo insertInfo(LoginBean loginBean, LoginBean loginBean2, StatShiftFLInfo bean,Integer eqpType, Object[] obj) {
		String eqptype=eqpType.toString();
		StatShiftFLInfo b=new StatShiftFLInfo();
		//copy属性
		try {
			BeanConvertor.copyProperties(bean, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		b.setMec_id(eqptype);//设备类型
		b.setEqp_id(StringUtil.convertObjToString(obj[3]));
		b.setTeam_id(StringUtil.convertObjToString(obj[2]));
		b.setShift_id(StringUtil.convertObjToString(obj[1]));
		b.setOid(StringUtil.convertObjToString(obj[0]));
		b.setMat_id(StringUtil.convertObjToString(obj[4]));
		b.setMat_name(StringUtil.convertObjToString(obj[5]));
		b.setCreate_time(new Date());
		if(null!=loginBean&&loginBean.getUserId()!=null){
			b.setUpdate_user_id(loginBean.getUserId());
			b.setReceive_user_name(loginBean.getLoginName());
		}else if(null!=loginBean2&&loginBean2.getUserId()!=null){
			b.setUpdate_user_id(loginBean2.getUserId());
			b.setReceive_user_name(loginBean2.getLoginName());
		}
		b.setDel(0);
		b.setFl_type(1);//要料类型为1
		b.setZc_type(0);//仲裁类型  0-默认 
		b.setStatus(0);// 状态 0-默认
		return b;
	}
	//
	/**根据eqpid获取当前设备正在运行的工单信息
	 * id0,shift1,team2,eqp3,mat4, mat_name5
	 * @param eqpId
	 * @return
	 */
	private List<?> getWorkOrderInfo(String eqpId){
		StringBuffer sb=new StringBuffer();
		List<?> ls=null;
		String date=DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		sb.append(" SELECT a.ID,a.SHIFT,a.TEAM,a.EQP,a.MAT,b.NAME from SCH_WORKORDER a LEFT JOIN MD_MAT b ON a.MAT=b.ID ");
		sb.append(" where '"+date+"' BETWEEN a.STIM AND a.ETIM");
		if(StringUtil.notNull(eqpId)){
			sb.append(" AND a.EQP='"+eqpId+"'");
		}
		ls= statShiftFLInfoDao.queryBySql(sb.toString());
		return ls;
		
	}
	/**
	 * 更新辅料数据（处理null值）
	 * shisihai
	 * @param r 页面接收的数据
	 * @param o 数据库读取的原有数据
	 * @return
	 */
	private Double updateDoubleData(Double r,Double o){
		double total=0.0;
		if(o==null){
			o=0.0;
		}
		if(r==null){
			r=0.0;
		}
		total=o+r;
		return total;
	}



	
}
