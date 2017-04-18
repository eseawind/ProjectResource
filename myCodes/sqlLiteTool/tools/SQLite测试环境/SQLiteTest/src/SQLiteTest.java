import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLiteTest {
	public static int nCount=20000000;
	
	public static void main(String[] args) {
		// 计算程序运行时间
		long sTime,eTime;
		sTime=System.currentTimeMillis();
		
		//====测试SQLite-JDBC执行效率========= 
		try {
			//引用sqlite数据源
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		Connection conn = null;
		try
		{
			// 调整日期查询格式
			Properties pro = new Properties();
			pro.put("date_string_format", "yyyy-MM-dd HH:mm:ss");
			// 创建数据库连接
			conn = DriverManager.getConnection("jdbc:sqlite:C:/SQLite/Realdatabase.db", pro);
			Statement db = conn.createStatement();
			db.setQueryTimeout(60);  // set timeout to 60 sec.

//			db.executeUpdate("drop table if exists person");
//			db.executeUpdate("create table person (id integer, name string)");
//			db.executeUpdate("insert into RealValue (MachineID,Realvalue,Realdate) values ('1',1117,CURRENT_TIMESTAMP)");
//			ResultSet rs = db.executeQuery("select * from realvalue order by realdate desc ,realtime desc limit 6");
//			while(rs.next())
//			{
//				System.out.println("SQLite数据查询：Realvalue = " + rs.getString("realvalue") + ", Realdate = " + rs.getTimestamp("realdate"));
//			    //System.out.println("SQLite数据库查询：Realvalue = " + rs.getString("realvalue") + ", Realdate = " + rs.getDate("realdate")+" "+rs.getTime("realdate"));
//			}
//			//慢速方式--SQLite API直接执行SQL
//			for(int i=1;i<=nCount;++i){
//				db.executeUpdate("insert into RealValue (MachineID,Realvalue,Realdate) values ('1',"+i+",CURRENT_TIMESTAMP)");
//				System.out.println("插入第 "+String.valueOf(i)+" 条记录.");
//			}
			
//			//事务方式,SQLite3选项默认值full，插入数据前改为off. 
			db.executeUpdate("PRAGMA synchronous = OFF;");
			db.executeUpdate("begin;");
			for(int i=1;i<=nCount;++i){
				db.executeUpdate("insert into RealValue (MachineID,Realvalue,Realdate,Realtime) values (1,9801,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);");
				//System.out.println("插入第 "+String.valueOf(i)+" 条记录.");
			}
			db.executeUpdate("commit;");
		}
		catch(SQLException e){
			System.err.println("catch error："+e.getMessage());
		}
		finally{
			try{
				if(conn != null)
			    	conn.close();
			}
			catch(SQLException e){
				System.err.println("catch error："+e);
			}
		}
		//============================================
		
		//计算SQLite-JDBC执行时间
		eTime=System.currentTimeMillis();
		System.out.println("SQLite插入 "+String.valueOf(nCount)+"条记录，执行时间： "+ String.valueOf(eTime-sTime)+" 毫秒.");
	}
}
