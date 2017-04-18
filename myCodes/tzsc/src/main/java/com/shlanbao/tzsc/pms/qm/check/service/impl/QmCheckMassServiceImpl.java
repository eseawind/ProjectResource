package com.shlanbao.tzsc.pms.qm.check.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.QmMassExcipientDaoI;
import com.shlanbao.tzsc.base.dao.QmMassFirstDaoI;
import com.shlanbao.tzsc.base.dao.QmMassProcessDaoI;
import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.qm.check.beans.QmMassDataBean;
import com.shlanbao.tzsc.pms.qm.check.service.QmCheckMassService;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: QmCheckMassServiceImpl
* @Description: 自检历史记录查询  
* @author luo 
* @date 2015年10月29日 下午1:40:00 
*
 */
@Service
public class QmCheckMassServiceImpl extends BaseService implements QmCheckMassService{
    @Autowired
	private QmMassFirstDaoI firstDao;
    @Autowired
    private QmMassProcessDaoI processDao;
    @Autowired
    private QmMassExcipientDaoI excipientDao;
	
    //查询自己历史记录中所有信息
    @Override
	public QmMassDataBean queryList(QmMassCheck bean) throws Exception {
		QmMassDataBean data=new QmMassDataBean();
		StringBuffer sqlBuffer=new StringBuffer();
		String table;
		String where;
		String lineName;
		//根据班次，班组，日期和设备查询工单信息
		Object[] o=getWorkOrderDes(bean,"2");
		//判断是否有工单，如果没有查询到工单信息，则返回Null
		if(o==null){
			return new QmMassDataBean();
		}
		//获取工单和首检data
		String orderId = getWorkOrderAndFirstCheckData(data, sqlBuffer, o,1);
		
		String sql="SELECT SMALLBOX,BIGBOX from QM_MASS_CHECK where PRO_WORK_ID='"+orderId+"'";
		List<Object[]> ls2=(List<Object[]>) firstDao.queryBySql(sql);
		if(null!=ls2&&ls2.size()>0){
			Object[] o2=ls2.get(0);
			data.setXh(StringUtil.convertObjToString(o2[0]));
			data.setTh(StringUtil.convertObjToString(o2[1]));
		}
		//查询过程检记录（挡车工）
		table="QM_MASS_PROCESS";
		where=" and process_type='D'";
		lineName="SHORT_TIME,PROD_PART,RUN_CONDITION,IS_ERROR,RUN_STEP,RUN_REMARK,BAD_NUM,NUM_UNIT";
		data.setQmMassProcessD(getTableSql(table,where,lineName,orderId,"SHORT_TIME"));
		
		//查询过程检纪录（操作工）
		table="QM_MASS_PROCESS";
		where=" and process_type='C' ";
		lineName="SHORT_TIME,PROD_PART,RUN_CONDITION,IS_ERROR,RUN_STEP,RUN_REMARK,BAD_NUM,NUM_UNIT";
		data.setQmMassProcessC(getTableSql(table,where,lineName,orderId,"SHORT_TIME"));
		
		//查询辅料确认纪录
		table="QM_MASS_EXCIPIENT";
		where="";
		lineName="CHECK_ITEM,CHECK_RATE,CHECK_TIME,IS_ERROR,SUB_STANDARD,ORDER_NUMBER";
		data.setQmMassExcipient(getTableSql(table,where,lineName,orderId,"CAST( ORDER_NUMBER as integer)"));
		return data;
	}
    /**
     * 包装机导出数据
     */
    @Override
    public Map<String, Object> exportCheckInfo(QmMassCheck bean){
    	QmMassDataBean data;
    	try{
    		data=this.queryList(bean);
    		if(!StringUtil.notNull(data.getDate())){
    			data=new QmMassDataBean();
    		}
    	}catch(Exception ex){
    		data=new QmMassDataBean();
    	}
    	Map<String, Object> root = new HashMap<String, Object>();
        root.put("team",data.getTeam());
        root.put("equ",data.getEqu());
        String shift=data.getShift();
        String shiftHtml="";
		if("白班".equals(shift)){
			shiftHtml="( 白√、中、夜  )";
		}else if("中班".equals(shift)){
			shiftHtml="( 白、中√、夜  )";
		}else if("夜班".equals(shift)){
			shiftHtml="( 白、中、夜√  )";
		}
		root.put("shift", shiftHtml);
        root.put("mat", data.getMatName());
        root.put("userD", data.getUserD());
        root.put("userC", data.getUserC());
        root.put("xh", data.getXh());
        root.put("th", data.getTh());
        if(StringUtil.notNull(data.getDate())){
        	String date[]=data.getDate().split("-");
        	if(date.length==3){
    	        root.put("y", date[0]);
    	        root.put("m", date[1]);
    	        root.put("d", date[2]);
            }else{
            	root.put("y", "");
    	        root.put("m", "");
    	        root.put("d", "");
            }
        }else{
        	root.put("y", "");
	        root.put("m", "");
	        root.put("d", "");
        }
        for (String key : root.keySet()) {  
			if(root.get(key)==null){
		    	root.put(key, "");
			}
		} 
        //有缺陷项，增加不合格数及单位 原来值下标/缺陷数下表/单位下标
        data.setQmMassFirstC(addDefectAndUnit(data.getQmMassFirstC(),2,4,5));
        data.setQmMassFirstZ(addDefectAndUnit(data.getQmMassFirstZ(),1,4,5));
        data.setQmMassFirstG(addDefectAndUnit(data.getQmMassFirstG(),1,4,5));
        data.setQmMassProcessD(addDefectAndUnit(data.getQmMassProcessD(),2,7,7));
        data.setQmMassProcessC(addDefectAndUnit(data.getQmMassProcessC(),2,7,7));
        //最大行（从1开始）/最大列（sql查询出的列   从1开始）
        root.put("qmMassFirstC",convertListToArray(data.getQmMassFirstC(),5,6));
        root.put("qmMassFirstZ",convertListToArray(data.getQmMassFirstZ(),5,6));
        root.put("qmMassFirstG",convertListToArray(data.getQmMassFirstG(),5,6));
        root.put("qmMassProcessD",convertListToArray(data.getQmMassProcessD(),21,7));
        root.put("qmMassProcessC",convertListToArray(data.getQmMassProcessC(),21,7));
        //辅料自检自控装置确认记录排序
        Object[][] list=new Object[12][7];
        if(data.getQmMassExcipient()!=null){
	        for(int i=0;i<data.getQmMassExcipient().size();i++){
	        	Object[] o=data.getQmMassExcipient().get(i);
	        	if(StringUtil.notNull(StringUtil.convertObjToString(o[5]))){
	        		if(StringUtil.isInteger(o[5].toString())){
	        			list[Integer.parseInt(o[5].toString())-1] = o;
	        		}
	        	}
	        }
        }
        //补空数据
        for(int i=0;i<list.length;i++){
        	Object[] o=list[i];
	        for(int j=0;j<o.length;j++){
	    		Object temp=o[j];
	    		if(!StringUtil.notNull(StringUtil.convertObjToString(temp))){
	    			o[j]="";
	    		}
	    	}
	        list[i]=o;
        }
        root.put("qmMassExcipient",list);
    	return root;
    }
    /**
     * 卷烟机导出数据
     */
	@Override
	public Map<String, Object> exportRolerCheckInfo(QmMassCheck bean) {

    	QmMassDataBean data;
    	try{
    		data=this.queryRolerCheckDataList(bean);
    		if(!StringUtil.notNull(data.getDate())){
    			data= new QmMassDataBean();
    		}
    	}catch(Exception ex){
    		data= new QmMassDataBean();
    	}
    	Map<String, Object> root = new HashMap<String, Object>();
        root.put("team",data.getTeam());
        root.put("equ",data.getEqu());
        
        String shift=data.getShift();
        String shiftHtml="";
        if("白班".equals(shift)){
			shiftHtml="( 白√、中、夜  )";
		}else if("中班".equals(shift)){
			shiftHtml="( 白、中√、夜  )";
		}else if("夜班".equals(shift)){
			shiftHtml="( 白、中、夜√  )";
		}
		root.put("shift", shiftHtml);
        root.put("mat", data.getMatName());
        root.put("userD", data.getUserD());
        String date=data.getDate();//设置时间
        if(date!=null){
	        root.put("date", date);
	      
        }else{
        	root.put("date", "");
        }
        //如果数据有null，设置为“”
        for (String key : root.keySet()) {  
			if(root.get(key)==null){
		    	root.put(key, "");
			}
		} 
        //有缺陷项，增加不合格数及单位  原来值下标/缺陷数下表/单位下标
        data.setQmMassFirstC(addDefectAndUnit(data.getQmMassFirstC(),3,5,6));
        data.setQmMassFirstZ(addDefectAndUnit(data.getQmMassFirstZ(),1,4,5));
        data.setQmMassFirstG(addDefectAndUnit(data.getQmMassFirstG(),1,4,5));
        data.setQmMassProcessD(addDefectAndUnit(data.getQmMassProcessD(),3,8,9));
        //最大行（从1开始）/最大列（sql查询出的列   从1开始）
        root.put("qmMassFirstC",convertListToArray(data.getQmMassFirstC(),5,7));
        root.put("qmMassFirstZ",convertListToArray(data.getQmMassFirstZ(),5,6));
        root.put("qmMassFirstG",convertListToArray(data.getQmMassFirstG(),5,6));
        root.put("qmMassProcessD",convertListToArray(data.getQmMassProcessD(),24,10));
        root.put("qmMassOnline", convertListToArray(data.getQmMassOnline(), 24, 10));
        root.put("qmMassStem", convertListToArray(data.getQmMassStem(), 3, 5));
        //辅料自检自控装置确认记录排序 行 1开始 列多与sql列
        Object[][] list=new Object[8][6];
        if(data.getQmMassExcipient()!=null){
	        for(int i=0;i<data.getQmMassExcipient().size();i++){
	        	Object[] o=data.getQmMassExcipient().get(i);
	        	if(StringUtil.notNull(StringUtil.convertObjToString(o[5]))){
	        		//把long转int
	        		if(StringUtil.isInteger(o[5].toString())){
	        			list[Integer.parseInt(o[5].toString())-1] = o;
	        		}
	        	}
	        }
        }
        //补空数据
        for(int i=0;i<list.length;i++){
        	Object[] o=list[i];
	        for(int j=0;j<o.length;j++){
	    		Object temp=o[j];
	    		if(!StringUtil.notNull(StringUtil.convertObjToString(temp))){
	    			o[j]="";
	    		}
	    	}
	        list[i]=o;
        }
        root.put("qmMassExcipient",list);
    	return root;
    
	}
    
    /**
    * @Title: addUnitAndDefect 
    * @Description: 向集合中增加缺陷数及缺陷单位 
    * @param list 集合
    * @param oldVal 原来值的下标
    * @param qxVal 缺陷值的下标
    * @param uom 缺陷值单位的下标
    * @return String  返回类型 
    * @throws
     */
    private List<Object[]> addDefectAndUnit(List<Object[]> list,int oldVal,int qxVal,int uom){
    	if(list!=null){
			for(int i=0;i<list.size();i++){
				Object[] temp=list.get(i);
				if(!"0".equals(temp[qxVal].toString().trim())){
					temp[oldVal]=temp[oldVal]+","+temp[qxVal]+":"+getUomByKey(temp[uom].toString());
					list.set(i, temp);
				}
			}
		}
    	return list;
    }
    //自检质量问题数量的单位：支、包、条
  	private String[] unit_key={"7","8","9","10"};
  	private String[] unit_val={"支","包","条","件"}; 
  	/** 
  	* @Title: getUomByKey 
  	* @Description: 根据单位key获取val 
  	* @param uomKey
  	* @return String    返回类型 
  	* @throws
  	 */
    private String getUomByKey(String uomKey){
    	for(int i=0;i<unit_key.length;i++){
			if(uomKey.equals(unit_key[i])){
				return unit_val[i];
			}
		}
    	return "";
    }
    /**
    * @Title: convertListToArray 
    * @Description: List集合转二维数组 
    * @param objs List集合
    * @param maxRows 最大行数
    * @param maxClos 最大列数
    * @return Object[行][列]    返回类型 
    * @throws
     */
    private Object[][] convertListToArray(List<Object[]> objs,int maxRows,int maxClos){
    	Object[][] o=new Object[maxRows][maxClos];
        for(int i=0;i<maxRows;i++){
        	Object[] obj=null;
        	if(objs!=null&&i<objs.size()){
        		obj=objs.get(i);
        	}else{
        		//向空数据中增加默认值
        		obj=new Object[maxClos];
        		for(int j=0;j<maxClos;j++){
        			obj[j]="";
        		}
        	}
        	o[i]=obj;
        }
    	return o;
    }
    

	/**
	 * 获取卷烟机质量自检纪录
	 */
	@Override
	public QmMassDataBean queryRolerCheckDataList(QmMassCheck bean) throws Exception {
		QmMassDataBean data=new QmMassDataBean();
		StringBuffer sqlBuffer=new StringBuffer();
		String table;
		String where;
		String lineName;
		//根据班次，班组，日期和设备查询工单信息
		Object[] o=getWorkOrderDes(bean,"1");
		//判断是否有工单，如果没有查询到工单信息，则返回Null
		if(o==null){
			return new QmMassDataBean();
		}
		//获取工单和首检data
		String orderId = getWorkOrderAndFirstCheckData(data, sqlBuffer, o,2);
		
		//查询过程检记录（卷烟机挡车工）
		table="QM_MASS_PROCESS";
		where=" and process_type='J'";
		lineName="SHORT_TIME,PROD_PART,RUN_WEIGHT,RUN_CONDITION,IS_ERROR,IS_AGAIN,RUN_STEP,RUN_REMARK,BAD_NUM,NUM_UNIT";
		data.setQmMassProcessD(getTableSql(table,where,lineName,orderId,"SHORT_TIME"));
		//在线物理指标自检纪录（综合测试台）
		table="QM_MASS_ONLINE";
		where="";
		lineName="SHORT_TIME,CHECK_ITEM,UPPER_NUMBER,LOWER_NUMBER,AVG_NUMBER,STANDARD_DIFF,IS_ERROR,IS_AGAIN,ONLINE_STEP,ONLINE_REMARK";
		data.setQmMassOnline(getTableSql(table,where,lineName,orderId,""));
		
		//丝含梗纪录
		table="QM_MASS_STEM";
		where="";
		lineName="SHORT_TIME,CHECK_CONDITION,IS_ERROR,STEM_STEP,STEM_REMARK";
		data.setQmMassStem(getTableSql(table,where,lineName,orderId,"SHORT_TIME"));
		
		
		//查询辅料确认纪录
		table="QM_MASS_EXCIPIENT";
		where="";
		lineName="CHECK_ITEM,CHECK_RATE,CHECK_TIME,IS_ERROR,SUB_STANDARD,ORDER_NUMBER";
		data.setQmMassExcipient(getTableSql(table,where,lineName,orderId,"CAST( ORDER_NUMBER as integer)"));
		return data;
	}
	
	/**
	 * 张璐-2015.11.3
	 * 获取装封箱机质量自检纪录
	 */
	@Override
	public QmMassDataBean queryFXJCheckDataList(QmMassCheck bean) throws Exception {
		QmMassDataBean data=new QmMassDataBean();
		StringBuffer sqlBuffer=new StringBuffer();
		String table;
		String where;
		String lineName;
		//根据班次，班组，日期和设备查询工单信息
		Object[] o=getWorkOrderDes(bean,"3");
		//判断是否有工单，如果没有查询到工单信息，则返回Null
		if(o==null){
			return new QmMassDataBean();
		}
		//获取工单和首检data,3传过去的是封箱机的条件
		String orderId = getWorkOrderAndFirstCheckData(data, sqlBuffer, o,3);
		
		//查询过程检记录
		table="QM_MASS_PROCESS";
		where=" and process_type='F'";
		lineName="SHORT_TIME,PROD_PART,RUN_CONDITION,IS_ERROR,IS_AGAIN,RUN_STEP,RUN_REMARK,BAD_NUM,NUM_UNIT";
		data.setQmMassProcessD(getTableSql(table,where,lineName,orderId,"SHORT_TIME"));
		
		//查询辅料确认纪录
		table="QM_MASS_EXCIPIENT";
		where="";
		lineName="CHECK_ITEM,CHECK_RATE,CHECK_TIME,IS_ERROR,SUB_STANDARD,ORDER_NUMBER";
		data.setQmMassExcipient(getTableSql(table,where,lineName,orderId,"CAST( ORDER_NUMBER as integer)"));
		return data;
	}
	
	/**
	 * 获取卷烟机质量自检纪录
	 * @author 景孟博
	 * @time 2015年11月04日
	 */
	@Override
	public QmMassDataBean queryFilterCheckDataList(QmMassCheck bean) throws Exception {
		QmMassDataBean data=new QmMassDataBean();
		StringBuffer sqlBuffer=new StringBuffer();
		String table;
		String where;
		String lineName;
		//根据班次，班组，日期和设备查询工单信息
		Object[] o=getWorkOrderDes(bean,"4");
		//判断是否有工单，如果没有查询到工单信息，则返回Null
		if(o==null){
			return new QmMassDataBean();
		}
		//获取工单和首检data
		String orderId = getWorkOrderAndFirstCheckData(data, sqlBuffer, o,4);
		
		//查询丝束过程检记录
		table="QM_MASS_PROCESS";
		where=" and process_type='S'";
		lineName="RUN_CONDITION,RUN_STEP,RUN_REMARK";
		data.setQmMassProcessD(getTableSql(table,where,lineName,orderId,""));
		
		//查询产品过程检验记录
		table="QM_MASS_FILTER_PROCESS";
		where="";
		lineName="SHORT_TIME,RUN_CONDITION,WEIGHT_FNUM,WEIGHT_TNUM,WEIGHT_AVG,PRESSURE_FNUM,PRESSURE_TNUM,PRESSURE_AVG,CIRCLE_FNUM,CIRCLE_TNUM,CIRCLE_AVG,HARDNESS_FNUM,HARDNESS_TNUM,HARDNESS_AVG,OTHERS,IS_ERROR,RUN_STEP,RUN_REMARK,BAD_NUM,NUM_UNIT";
		data.setQmMassProcessC(getTableSql(table,where,lineName,orderId,"SHORT_TIME"));
		
		//查询三乙酸甘油酯施加量
		table="QM_MASS_GLYCERINE_PROCESS";
		where="";
		lineName="DAR_WEIGHT,WET_WEIGHT,USE_WEIGHT";
		data.setQmMassSp(getTableSql(table,where,lineName,orderId,""));
		
		//查询辅料确认纪录
		table="QM_MASS_EXCIPIENT";
		where="";
		lineName="CHECK_ITEM,CHECK_RATE,CHECK_TIME,IS_ERROR,SUB_STANDARD,ORDER_NUMBER";
		data.setQmMassExcipient(getTableSql(table,where,lineName,orderId,"CAST( ORDER_NUMBER as integer)"));
		return data;
	}
	
	 /**
     * 获取工单和首检的信息
     * @param data
     * @param sqlBuffer
     * @param type 类型 1包装机  2卷烟机
     * @param o
     * @return
     */
	private String getWorkOrderAndFirstCheckData(QmMassDataBean data, StringBuffer sqlBuffer, Object[] o,int type) {
		//工单ID，设备名称，牌号，生产日期,班次，班组
		String orderId=StringUtil.convertObjToString(o[0]);
		data.setEqu(StringUtil.convertObjToString(o[1]));
		data.setMatName(StringUtil.convertObjToString(o[2]));
		data.setDate(StringUtil.convertObjToString(o[3]));
		data.setShift(StringUtil.convertObjToString(o[4]));
		data.setTeam(StringUtil.convertObjToString(o[5]));
		if(type==1){
			//挡车工
			data.setUserD(getProcessSql("D",orderId));
			//操作工
			data.setUserC(getProcessSql("C",orderId));
		}else if(type==2){
			//挡车工
			data.setUserD(getProcessSql("J",orderId));
		}else if(type==3){
			//封箱机 F
			data.setUserD(getProcessSql("F",orderId));
		}else if(type==4){
			//成型机 S
			data.setUserD(getProcessSql("S",orderId));
		}
		
		//操作工首检
		String table="QM_MASS_FIRST";
		//附加条件
		String where=" and check_type='1' ";
		//需要查询的列  (包装机)
		String lineName="CHECK_TIME,CHECK_MATTER,CHECK_CONDITION,CHECK_STEP,FAILURE_NUM,FAILURE_UOM";
		//卷烟机
		if(type==2){
			lineName="CHECK_TIME,CHECK_MATTER,CHECK_WEIGHT,CHECK_CONDITION,CHECK_STEP,FAILURE_NUM,FAILURE_UOM";
		}
		data.setQmMassFirstC(getTableSql(table,where,lineName,orderId,"CHECK_TIME"));
		sqlBuffer.setLength(0);
		
		//质检员首检
		where=" and check_type='2' ";
		lineName="CHECK_TIME,CHECK_CONDITION,CHECK_STEP,ADD_USER_NAME,FAILURE_NUM,FAILURE_UOM";
		data.setQmMassFirstZ(getTableSql(table,where,lineName,orderId,"CHECK_TIME"));
		
		//工段长首检
		where=" and check_type='3' ";
		lineName="CHECK_TIME,CHECK_CONDITION,CHECK_STEP,ADD_USER_NAME,FAILURE_NUM,FAILURE_UOM";
		data.setQmMassFirstG(getTableSql(table,where,lineName,orderId,"CHECK_TIME"));
		return orderId;
	}
	/**
	* @Title: getTableSql 
	* @Description: 根据Sql查询
	* @param sqlBuffer 
	* @param table 表名
	* @param sql 
	* @param lineName 需要查询的列
	* @param orderId 工单ID
	* @param orderString 排序
	* @return StringBuffer    返回类型 
	* @throws
	 */
	private List<Object[]> getTableSql(String table,String sql,String lineName,String orderId,String orderString) {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select ");
		if(StringUtil.notNull(lineName)){
			sqlBuffer.append(lineName);
		}else{
			sqlBuffer.append(" * ");
		}
		sqlBuffer.append(" from "+table+" where QM_CHECK_ID ");
		sqlBuffer.append(" =(SELECT QM_CHECK_ID from QM_MASS_CHECK where PRO_WORK_ID='"+orderId+"') ");
		sqlBuffer.append(" and is_delete='0' ");
		if(sql!=null){
			sqlBuffer.append(sql);
		}
		if(StringUtil.notNull(orderString)){
			sqlBuffer.append(" ORDER BY "+orderString);
		}else{
			sqlBuffer.append(" ORDER BY add_user_time");
		}
		return (List<Object[]>) firstDao.queryBySql(sqlBuffer.toString());
	}
	/**
	* @Title: getProcessSql 
	* @Description: 根据过程自检记录类型和工单获取添加人 
	* @param type
	* @param orderId
	* @return StringBuffer    返回类型 
	* @throws
	 */
	private String getProcessSql(String type,String orderId){
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append(" select top 1 (select name from SYS_USER where id=add_user_id ) as userName from QM_MASS_PROCESS ");
		sqlBuffer.append(" where qm_check_id=(select qm_check_id from QM_MASS_CHECK where pro_work_id='"+orderId+"' ) and PROCESS_TYPE = '"+type+"' order by add_user_time ");
		
		List<?> excipient=excipientDao.queryBySql(sqlBuffer.toString());
		if(excipient!=null&&excipient.size()>0){
			return StringUtil.convertObjToString(excipient.get(0));
		}
		return null;
	}
	/**
	* @Title: getWorkOrderDes
	* @Description: 根据条件班次、班组、设备、和日期查找工单  
	* @param bean
	* @return StringBuffer    返回类型 
	* @throws
	 */
	private Object[] getWorkOrderDes(QmMassCheck bean,String orderType){
		StringBuffer sqlBuffer=new StringBuffer();
		//工单ID，设备名称，牌号，日期,班次，班组
		sqlBuffer.append("select wo.ID,equ.EQUIPMENT_NAME,mat.NAME as matName,convert(varchar(32),DATE,23) as time,shift.NAME as shiftName,team.NAME as teamName  ");
		sqlBuffer.append("from SCH_WORKORDER wo ");
		sqlBuffer.append("left join MD_SHIFT shift on wo.SHIFT=shift.ID ");
		sqlBuffer.append("left join MD_TEAM team on wo.TEAM=team.ID ");
		sqlBuffer.append("left join MD_EQUIPMENT equ on wo.EQP=equ.ID ");
		sqlBuffer.append("left join MD_MAT mat on wo.MAT=mat.ID ");
		sqlBuffer.append("where 1=1 ");
		if(StringUtil.notNull(bean.getTime())){
			sqlBuffer.append(" and CONVERT(varchar(32),wo.DATE,23)= '"+bean.getTime()+"' ");
		}
		if(StringUtil.notNull(bean.getMdShiftId())){
			sqlBuffer.append(" and wo.SHIFT = '"+bean.getMdShiftId()+"'");
		}
		if(StringUtil.notNull(bean.getMdEqmentId())){
			sqlBuffer.append(" and wo.EQP='"+bean.getMdEqmentId()+"'");
		}
		if(StringUtil.notNull(bean.getTeam())){
			sqlBuffer.append(" and wo.TEAM = '"+bean.getTeam()+"'");
		}
		if(StringUtil.notNull(orderType)){
			sqlBuffer.append(" and wo.TYPE = '"+orderType+"'");
		}
		List<Object[]> list=(List<Object[]>) firstDao.queryBySql(sqlBuffer.toString());
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
    /**
     * 张璐-2015.11.4
     * 装封箱机导出数据
     */
    @Override
    public Map<String, Object> exportFXJCheckInfo(QmMassCheck bean){
    	QmMassDataBean data;
    	try{
    		data=this.queryFXJCheckDataList(bean);
    		if(!StringUtil.notNull(data.getDate())){
    			data=new QmMassDataBean();
    		}
    	}catch(Exception ex){
    		data=new QmMassDataBean();
    	}
    	Map<String, Object> root = new HashMap<String, Object>();
        root.put("team",data.getTeam());
        root.put("equ",data.getEqu());
        
        String shift=data.getShift();
        String shiftHtml="";
        if("早班".equals(shift)){
			shiftHtml="早班";
		}else if("中班".equals(shift)){
			shiftHtml="中班";
		}else if("晚班".equals(shift)){
			shiftHtml="晚班";
		}
		root.put("shift", shiftHtml);
        root.put("mat", data.getMatName());
        root.put("userD", data.getUserD());
        if(StringUtil.notNull(data.getDate())){
        	String date[]=data.getDate().split("-");
        	if(date.length==3){
    	        root.put("y", date[0]);
    	        root.put("m", date[1]);
    	        root.put("d", date[2]);
            }else{
            	root.put("y", "");
    	        root.put("m", "");
    	        root.put("d", "");
            }
        }else{
        	root.put("y", "");
	        root.put("m", "");
	        root.put("d", "");
        }
        for (String key : root.keySet()) {  
			if(root.get(key)==null){
		    	root.put(key, "");
			}
		} 
        //有缺陷项，增加不合格数及单位 原来值下标/缺陷数下表/单位下标
        data.setQmMassFirstC(addDefectAndUnit(data.getQmMassFirstC(),2,4,5));
        data.setQmMassFirstZ(addDefectAndUnit(data.getQmMassFirstZ(),1,4,5));
        data.setQmMassFirstG(addDefectAndUnit(data.getQmMassFirstG(),1,4,5));
        data.setQmMassProcessD(addDefectAndUnit(data.getQmMassProcessD(),2,7,8));
        //最大行（从1开始）/最大列（sql查询出的列   从1开始）
        root.put("qmMassFirstC",convertListToArray(data.getQmMassFirstC(),4,4));
        root.put("qmMassFirstZ",convertListToArray(data.getQmMassFirstZ(),4,4));
        root.put("qmMassFirstG",convertListToArray(data.getQmMassFirstG(),4,4));
        root.put("qmMassProcessD",convertListToArray(data.getQmMassProcessD(),20,7));
        //辅料自检自控装置确认记录排序
        Object[][] list=new Object[4][7];
        if(data.getQmMassExcipient()!=null){
	        for(int i=0;i<data.getQmMassExcipient().size();i++){
	        	Object[] o=data.getQmMassExcipient().get(i);
	        	if(StringUtil.notNull(StringUtil.convertObjToString(o[5]))){
	        		if(StringUtil.isInteger(o[5].toString())){
	        			list[Integer.parseInt(o[5].toString())-1] = o;
	        		}
	        	}
	        }
        }
        //补空数据
        for(int i=0;i<list.length;i++){
        	Object[] o=list[i];
	        for(int j=0;j<o.length;j++){
	    		Object temp=o[j];
	    		if(!StringUtil.notNull(StringUtil.convertObjToString(temp))){
	    			o[j]="";
	    		}
	    	}
	        list[i]=o;
        }
        root.put("qmMassExcipient",list);
    	return root;
    }
    
    /**
     * 张璐-2015-11-5
     * 滤棒质量自检记录导出数据
     */
	@Override
	public Map<String, Object> exportFilterCheckDataList(QmMassCheck bean) {

    	QmMassDataBean data;
    	try{
    		data=this.queryFilterCheckDataList(bean);
    		if(!StringUtil.notNull(data.getDate())){
    			data=new QmMassDataBean();
    		}
    	}catch(Exception ex){
    		data=new QmMassDataBean();
    	}
    	Map<String, Object> root = new HashMap<String, Object>();
        root.put("team",data.getTeam());
        root.put("equ",data.getEqu());
        
        String shift=data.getShift();
        String shiftHtml="";
        if("早班".equals(shift)){
			shiftHtml="早班";
		}else if("中班".equals(shift)){
			shiftHtml="中班";
		}else if("晚班".equals(shift)){
			shiftHtml="晚班";
		}
		root.put("shift", shiftHtml);
        root.put("mat", data.getMatName());
        root.put("userD", data.getUserD());
        if(StringUtil.notNull(data.getDate())){
        	String date[]=data.getDate().split("-");
        	if(date.length==3){
    	        root.put("y", date[0]);
    	        root.put("m", date[1]);
    	        root.put("d", date[2]);
            }else{
            	root.put("y", "");
    	        root.put("m", "");
    	        root.put("d", "");
            }
        }else{
        	root.put("y", "");
	        root.put("m", "");
	        root.put("d", "");
        }
        //如果数据有null，设置为“”
        for (String key : root.keySet()) {  
			if(root.get(key)==null){
		    	root.put(key, "");
			}
		} 
        //有缺陷项，增加不合格数及单位  原来值下标/缺陷数下表/单位下标
        data.setQmMassFirstC(addDefectAndUnit(data.getQmMassFirstC(),2,4,5));
        data.setQmMassFirstZ(addDefectAndUnit(data.getQmMassFirstZ(),1,4,5));
        data.setQmMassFirstG(addDefectAndUnit(data.getQmMassFirstG(),1,4,5));
        data.setQmMassProcessC(addDefectAndUnit(data.getQmMassProcessC(),1,18,19));
        //最大行（从1开始）/最大列（sql查询出的列   从1开始）
        root.put("qmMassFirstC",convertListToArray(data.getQmMassFirstC(),4,7));
        root.put("qmMassFirstZ",convertListToArray(data.getQmMassFirstZ(),4,7));
        root.put("qmMassFirstG",convertListToArray(data.getQmMassFirstG(),4,7));
        root.put("qmMassProcessC",convertListToArray(data.getQmMassProcessC(),24,20));
        root.put("qmMassProcessD",convertListToArray(data.getQmMassProcessD(),24,6));
        root.put("qmMassSp", convertListToArray(data.getQmMassSp(),4,5));
        //辅料自检自控装置确认记录排序 行 1开始 列多与sql列
        Object[][] list=new Object[4][7];
        if(data.getQmMassExcipient()!=null){
	        for(int i=0;i<data.getQmMassExcipient().size();i++){
	        	Object[] o=data.getQmMassExcipient().get(i);
	        	if(StringUtil.notNull(StringUtil.convertObjToString(o[5]))){
	        		//把long转int
	        		if(StringUtil.isInteger(o[5].toString())){
	        			list[Integer.parseInt(o[5].toString())-1] = o;
	        		}
	        	}
	        }
        }
        //补空数据
        for(int i=0;i<list.length;i++){
        	Object[] o=list[i];
	        for(int j=0;j<o.length;j++){
	    		Object temp=o[j];
	    		if(!StringUtil.notNull(StringUtil.convertObjToString(temp))){
	    			o[j]="";
	    		}
	    	}
	        list[i]=o;
        }
        root.put("qmMassExcipient",list);
    	return root;
    
	}
}
