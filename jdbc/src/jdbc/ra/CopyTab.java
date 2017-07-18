package jdbc.ra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CopyTab {
	private static String url=null;//"jdbc:oracle:thin:@10.1.3.205:1521:MESODS1";
	private static String userName=null;//"mesadmin";
	private static String upwd=null;//"MES_GW_1230";
	private static String driverClass=null;//"oracle.jdbc.driver.OracleDriver";
	public static Connection getConn() throws Exception{
		Class.forName(driverClass); //classLoader,加载对应驱动
		Connection connection = (Connection) DriverManager.getConnection(url, userName, upwd);
		return connection;
	}
	public static void main(String[] args) throws Exception {
		Connection connection=getConn();
		String sql = "select table_name from user_tables@mes_prod a where a.table_name not in (select table_name from user_tables)";
	    PreparedStatement pstmt =connection.prepareStatement(sql);
	    ResultSet resultSet=pstmt.executeQuery();
	    String tn=null;
	    while(resultSet.next()){
	    	try {
				tn=resultSet.getObject("TABLE_NAME").toString();
				sql="create table "+tn+" as select * from "+tn+"@mes_prod where 1=2";
				pstmt=connection.prepareStatement(sql);
				pstmt.execute();
			} catch (Exception e) {
				System.err.println("失败的表："+tn);
				//e.printStackTrace();
			}
	        System.out.println("成功："+tn);
	    }
	}
	
}
