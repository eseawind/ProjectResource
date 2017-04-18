package sqlLiteBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SqlLiteConnectionFactory {
	private static String url="jdbc:sqlite:D:/sqlLiteDATA/RealTimeDATA.db";
	private final static String driveClass="org.sqlite.JDBC";
	private static Connection conn;
	private static Properties properties;
	
	private static SqlLiteConnectionFactory instance;
	
	private SqlLiteConnectionFactory() {}
	public static SqlLiteConnectionFactory getInstance(){
		if(instance==null){
			synchronized(SqlLiteConnectionFactory.class){
				if(instance==null){
					properties=new Properties();
					ResourceBundle bundle=ResourceBundle.getBundle("base");
					url=bundle.getString("dbUrl");
					instance=new SqlLiteConnectionFactory();
				}
			}
		}
		return instance;
	}

	/**
	 * <p>功能描述：获取sqllite链接</p>
	 *@return
	 *shisihai
	 *2016下午12:54:09
	 */
	public  Connection getConn(){
		try {
			if( conn==null || conn.isClosed() ){
				properties=new Properties();
				//properties.put("date_string_format", "yyyy/MM/dd HH:mm:ss");
				Class.forName(driveClass);
				conn = DriverManager.getConnection(url, properties);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * <p>功能描述：设置参数</p>
	 *@param prst
	 *@param param
	 *shisihai
	 *2016下午1:29:34
	 */
	public  void setParams(PreparedStatement prst,Object...param){
		try {
			for (int i = 0; i < param.length; i++) {
				prst.setObject(i+1, param[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>功能描述：关闭所有链接资源</p>
	 *shisihai
	 *2016下午12:55:39
	 */
	public  void closeConn(Connection conn,PreparedStatement prst){
		try {
			if(prst!=null && !prst.isClosed()){
				prst.close();
				prst=null;
			}
			if(conn!=null && !conn.isClosed()){
				conn.close();
				conn=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
