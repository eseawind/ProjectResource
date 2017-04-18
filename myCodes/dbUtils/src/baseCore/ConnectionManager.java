package baseCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import utils.XmlUtil;
/**
 * ���ݿ����ӹ���
 * @author travler
 *
 */
public class ConnectionManager {
	public static volatile ConnectionManager connManager;
	public static HashMap<String, String> dbConfig = XmlUtil.getDBConfig("sqlLite");
	public static List<Connection> pool = Collections.synchronizedList(new ArrayList<>());
	public static final int MINCONN = Integer.valueOf(dbConfig.get("init"));
	public static final int MAXCONN = Integer.valueOf(dbConfig.get("max"));
	public static int CRUCONN = 0;

	private ConnectionManager() {
		initPool();
	}

	public static ConnectionManager getInstance() {
		if (connManager == null) {
			synchronized (ConnectionManager.class) {
				if (connManager == null) {
					connManager = new ConnectionManager();
				}
			}
		}
		return connManager;
	}

	/**
	 * ��ʼ�����ݿ����ӳ�
	 */
	private void initPool() {
		for (int i = 0; i < MINCONN; i++) {
			pool.add(createConn());
		}
	}

	/**
	 * ��ȡ���ݿ����ӣ���������������򷵻�null
	 * 
	 * @return
	 */
	public  Connection getConn() {
		Connection conn = null;
		try {
			if (CRUCONN <= MAXCONN) {
				if (pool.size() > 0) {
					conn = pool.get(0);
					if (!conn.isValid(1)) {
						conn=getInstance().createConn();
						CRUCONN--;
						pool.set(0, conn);
					}
					pool.remove(0);
				} else if (pool.size() == 0) {
					conn = getInstance().createConn();
					pool.add(conn);
					pool.remove(0);
				}
			} else {
				Thread.sleep(500);
				System.err.println("���ӳ����þ����ݻ���...");
				getConn();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * �ͷ����ݿ�������Դ��������
	 * 
	 * @param conn
	 * @param stmt
	 * @param prst
	 */
	public void releaseConn(Connection conn, Statement stmt, PreparedStatement prst) {
		closeAll(prst, stmt, conn, false);
	}

	/**
	 * ��ȡ�����ݿ�����
	 * @return
	 */
	private Connection createConn() {
		Connection conn = null;
		try {
			Class.forName(dbConfig.get("classDrive"));
			conn = DriverManager.getConnection(dbConfig.get("url"), dbConfig.get("uname"), dbConfig.get("pwd"));
			CRUCONN++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * ��ռλ����ֵ
	 * @param prst
	 * @param params
	 */
	public  void setParams(PreparedStatement prst,Object ... params){
		try {
			for (int i = 0; i < params.length; i++) {
				prst.setObject(i+1, params[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * �ر�����
	 * @param prst
	 * @param stmt
	 * @param conn
	 * @param flag
	 */
	public static void closeAll(PreparedStatement prst, Statement stmt, Connection conn, boolean flag) {
		try {
			if (prst != null) {
				prst.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				if (flag) {
					conn.close();
					CRUCONN--;
				} else {
					pool.add(conn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
