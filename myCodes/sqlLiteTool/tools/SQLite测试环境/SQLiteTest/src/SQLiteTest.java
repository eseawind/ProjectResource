import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLiteTest {
	public static int nCount=20000000;
	
	public static void main(String[] args) {
		// �����������ʱ��
		long sTime,eTime;
		sTime=System.currentTimeMillis();
		
		//====����SQLite-JDBCִ��Ч��========= 
		try {
			//����sqlite����Դ
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		Connection conn = null;
		try
		{
			// �������ڲ�ѯ��ʽ
			Properties pro = new Properties();
			pro.put("date_string_format", "yyyy-MM-dd HH:mm:ss");
			// �������ݿ�����
			conn = DriverManager.getConnection("jdbc:sqlite:C:/SQLite/Realdatabase.db", pro);
			Statement db = conn.createStatement();
			db.setQueryTimeout(60);  // set timeout to 60 sec.

//			db.executeUpdate("drop table if exists person");
//			db.executeUpdate("create table person (id integer, name string)");
//			db.executeUpdate("insert into RealValue (MachineID,Realvalue,Realdate) values ('1',1117,CURRENT_TIMESTAMP)");
//			ResultSet rs = db.executeQuery("select * from realvalue order by realdate desc ,realtime desc limit 6");
//			while(rs.next())
//			{
//				System.out.println("SQLite���ݲ�ѯ��Realvalue = " + rs.getString("realvalue") + ", Realdate = " + rs.getTimestamp("realdate"));
//			    //System.out.println("SQLite���ݿ��ѯ��Realvalue = " + rs.getString("realvalue") + ", Realdate = " + rs.getDate("realdate")+" "+rs.getTime("realdate"));
//			}
//			//���ٷ�ʽ--SQLite APIֱ��ִ��SQL
//			for(int i=1;i<=nCount;++i){
//				db.executeUpdate("insert into RealValue (MachineID,Realvalue,Realdate) values ('1',"+i+",CURRENT_TIMESTAMP)");
//				System.out.println("����� "+String.valueOf(i)+" ����¼.");
//			}
			
//			//����ʽ,SQLite3ѡ��Ĭ��ֵfull����������ǰ��Ϊoff. 
			db.executeUpdate("PRAGMA synchronous = OFF;");
			db.executeUpdate("begin;");
			for(int i=1;i<=nCount;++i){
				db.executeUpdate("insert into RealValue (MachineID,Realvalue,Realdate,Realtime) values (1,9801,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);");
				//System.out.println("����� "+String.valueOf(i)+" ����¼.");
			}
			db.executeUpdate("commit;");
		}
		catch(SQLException e){
			System.err.println("catch error��"+e.getMessage());
		}
		finally{
			try{
				if(conn != null)
			    	conn.close();
			}
			catch(SQLException e){
				System.err.println("catch error��"+e);
			}
		}
		//============================================
		
		//����SQLite-JDBCִ��ʱ��
		eTime=System.currentTimeMillis();
		System.out.println("SQLite���� "+String.valueOf(nCount)+"����¼��ִ��ʱ�䣺 "+ String.valueOf(eTime-sTime)+" ����.");
	}
}
