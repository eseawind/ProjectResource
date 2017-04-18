package baseCore;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据操作工具
 * @author travler
 *
 */
public class DBClient {
	private Connection conn = null;
	private PreparedStatement prst = null;
	private ResultSet re = null;
	private ResultSetMetaData metaData = null;
	private static ConnectionManager connManager = ConnectionManager.getInstance();
	private volatile static DBClient dbClient;

	private DBClient() {
	};

	public static DBClient getInstance() {
		if (dbClient == null) {
			synchronized (DBClient.class) {
				if (dbClient == null) {
					dbClient = new DBClient();
				}
			}
		}
		return dbClient;
	}

	/**
	 * 查询Map集合   key是列名称大写，值为结果
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<HashMap<String, Object>> getResult(String sql, Object... params) {
		List<HashMap<String, Object>> result = new ArrayList<>();
		HashMap<String, Object> map = null;
		try {
			conn = connManager.getConn();
			prst = conn.prepareStatement(sql);
			connManager.setParams(prst, params);
			re = prst.executeQuery();
			metaData = re.getMetaData();
			int cols = metaData.getColumnCount();
			while (re.next()) {
				map = new HashMap<>();
				for (int i = 1; i <= cols; i++) {
					map.put(metaData.getColumnName(i).toUpperCase(), re.getObject(i));
				}
				result.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connManager.releaseConn(conn, null, prst);
		}
		return result;
	}

	/**
	 * 查询对象集合
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 */
	public <T> List<T> getResult(String sql, Class<T> clazz, Object... params) {
		List<T> datas = new ArrayList<>();
		try {
			List<HashMap<String, Object>> resultMap = getResult(sql, params);
			Field[] fields = clazz.getDeclaredFields();
			for (HashMap<String, Object> dataMap : resultMap) {
				T bean = (T) clazz.newInstance();
				Object val = null;
				boolean flag = false;
				for (Field field : fields) {
					val = dataMap.get(field.getName().toUpperCase());
					flag = field.isAccessible();
					field.setAccessible(true);
					field.set(bean, val);
					field.setAccessible(flag);
				}
				datas.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 修改数据
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {
		int result=0;
		try {
			conn=connManager.getConn();
			prst=conn.prepareStatement(sql);
			connManager.setParams(prst, params);
			result= prst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connManager.releaseConn(conn, null, prst);
		}
		return result;
	}
	
	/**
	 * 批量修改数据
	 * @param sqls
	 * @param paramsList
	 * @return
	 */
	public int batchUpdate(List<String> sqls,List<Object[]> paramsList){
		int result=0;
		try {
			conn=connManager.getConn();
			conn.setAutoCommit(false);
			String sql=null;
			Object[] params=null;
			for (int i=0;i<sqls.size();i++) {
				sql=sqls.get(i);
				params=paramsList.get(i);
				prst=conn.prepareStatement(sql);
				connManager.setParams(prst, params);
				prst.executeUpdate();
				result++;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result=0;
		}finally {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connManager.releaseConn(conn, null, prst);
		}
		return result;
	}
}
