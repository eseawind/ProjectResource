package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.lanbao.dac.data.CommonData;
import com.lanbao.dws.model.wct.dac.EquipmentData;

import sqlLiteBase.SqlLiteConnectionFactory;
import sqlLiteBase.SysConstants;
import tools.ToolUtils;

public class MainBussiness {
	private static Logger log=Logger.getLogger(MainBussiness.class);
	private PreparedStatement prst=null;
	private SqlLiteConnectionFactory instance=SqlLiteConnectionFactory.getInstance();
	private Connection conn=null;
	private static MainBussiness mainBuss;
	
	
	private MainBussiness() {
		//初始化表
		createTabs();
	}
	public static MainBussiness getInstance(){
		if(mainBuss==null){
			synchronized(MainBussiness.class){
				if(mainBuss==null){
					mainBuss=new MainBussiness();
				}
			}
		}
		return mainBuss;
	}

	/**
	 * <p>功能描述：创建表，初始化的时候查询表是否存在。如果不存在则创建</p>
	 *shisihai
	 *2016下午3:36:44
	 */
	private void createTabs(){
		try {
			ResultSet resultSet=null;
			conn=instance.getConn();
			prst=conn.prepareStatement("PRAGMA synchronous = OFF;");
			prst.executeUpdate();
			prst=conn.prepareStatement("begin;");
			prst.executeUpdate();
			String tabName=null;
			String isExistsSql=null;
			String createTabSqL=null;
			for (int eqpCode : SysConstants.EQPCODEARRY) {
				tabName=ToolUtils.getTable(eqpCode);
				if( tabName != null ){
					//判断表是否存在
					isExistsSql="SELECT count(*) FROM sqlite_master WHERE type='table' AND name=? ";
					prst=conn.prepareStatement(isExistsSql);
					instance.setParams(prst, tabName);
					resultSet=prst.executeQuery();
					while(resultSet.next()){
						//如果不存在则创建新的数据表
						if(resultSet.getInt(1) == 0){
							createTabSqL="Create  TABLE "+tabName+" (ID integer PRIMARY KEY AUTOINCREMENT,EQP int,SHIFT int,TYPE nvarchar,ONLINE int,CREATETIME ntext(19),DATA ntext)";
							prst=conn.prepareStatement(createTabSqL);
							prst.executeUpdate();
						}
					}
				}
			}
			prst=conn.prepareStatement("commit;");
			prst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("sqlLite 初始化数据表异常！");
		}finally {
			if(conn!=null){
				instance.closeConn(conn, prst);
			}
		}
	}
	
	/**
	 * <p>功能描述：保存DAC快照数据</p>
	 *@param dataMap
	 *shisihai
	 *2016下午4:26:11
	 */
	public void  saveMemoryData(List<EquipmentData> eqpDatas){
		if(eqpDatas.size()>0){
				try {
						conn=instance.getConn();
						prst=conn.prepareStatement("PRAGMA synchronous = OFF;");
						prst.executeUpdate();
						prst=conn.prepareStatement("begin;");
						prst.executeUpdate();
						//表名称
						String tableName=null;
						//设备code
						Integer eqpCode=null;
						//班次
						Integer shift=null;
						//链接状态
						Integer online=null;
						//设备类型
						String type=null;
						//设备数据点数据
						List<CommonData> commonDatas=null;
						String dataStr=null;
						String insertSql=null;
						for (EquipmentData eqpData : eqpDatas) {
							eqpCode=eqpData.getEqp();
							tableName=ToolUtils.getTable(eqpCode);
							shift=eqpData.getShift();
							online=eqpData.isOnline()?1:0;
							type=eqpData.getType();
							commonDatas=ToolUtils.removeData(eqpData.getAllData(),eqpCode);
							dataStr=ToolUtils.listToJsonStr(commonDatas);
							if( tableName != null ){
								insertSql="insert into "+tableName+"(EQP,SHIFT,TYPE,ONLINE,DATA,CREATETIME) values(?,?,?,?,?,?)";
								prst=conn.prepareStatement(insertSql);
								instance.setParams(prst, eqpCode,shift,type,online,dataStr,ToolUtils.formatNowTime());
								prst.executeUpdate();
							}
						}
						prst=conn.prepareStatement("commit;");
						prst.executeUpdate();
				
			} catch (Exception e) {
						e.printStackTrace();
						log.info("sqlLite 保存数据异常！");
			}finally{
						if(conn!=null){
							instance.closeConn(conn, prst);
						}
			}
		}
	}
}
