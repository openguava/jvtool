package io.github.openguava.jvtool.lang.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * jdbc 工具类
 * @author openguava
 *
 */
public class JdbcUtils {
	
	protected JdbcUtils() {
		super();
	}

	/**
	 * 获取数据库连接对象
	 * @param url
	 * @param info
	 * @return
	 */
	public static Connection getConnection(String url, Properties info) {
		try {
			return DriverManager.getConnection(url,  info);
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取数据库连接对象
	 * @param url
	 * @param user 用户
	 * @param password 密码
	 * @return
	 */
	public static Connection getConnection(String url, String user, String password) {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 Statement 对象 
	 * @param connection
	 * @return
	 */
	public static Statement getStatement(Connection connection) {
		if(connection == null) {
			return null;
		}
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 PreparedStatement 对象
	 * @param connection
	 * @param sql
	 * @return
	 */
	public static PreparedStatement getPreparedStatement(Connection connection, String sql) {
		if(connection == null) {
			return null;
		}
		try {
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 CallableStatement 对象
	 * @param connection
	 * @param sql
	 * @return
	 */
	public static CallableStatement getCallableStatement(Connection connection, String sql) {
		if(connection == null) {
			return null;
		}
		try {
			return connection.prepareCall(sql);
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 设置 Statement 参数
	 * @param psmt 
	 * @param values 参数值
	 * @return
	 */
	public static boolean setStatementParameter(PreparedStatement psmt, Collection<?> values) {
		if(psmt == null || values == null || values.isEmpty()) {
			return false;
		}
		int index = 0;
		for(Object value :values) {
			setStatementParameter(psmt, ++index, value);
		}
		return false;
	}
	
	/**
	 * 设置 Statement 参数
	 * @param <T>
	 * @param psmt
	 * @param values 参数值
	 * @return
	 */
	public static <T> boolean setStatementParameter(PreparedStatement psmt, T[] values) {
		if(psmt == null || values == null || values.length == 0) {
			return false;
		}
		int index = 0;
		for(Object value :values) {
			setStatementParameter(psmt, ++index, value);
		}
		return false;
	}
	
	/**
	 * 设置 Statement 参数
	 * @param psmt
	 * @param index 参数索引(从1开始)
	 * @param value 参数值
	 * @return
	 */
	public static boolean setStatementParameter(PreparedStatement psmt, int index, Object value) {
		if(psmt == null || value == null) {
			return false;
		}
		try {
			if(value instanceof String) {
				psmt.setString(index, (String)value);
			} else if(value instanceof Character) {
				psmt.setString(index, value.toString());
			} else if(value instanceof Boolean) {
				psmt.setBoolean(index, (Boolean)value);
			} else if(value instanceof Byte) {
				psmt.setByte(index, (Byte)value);
			} else if(value instanceof byte[]) {
				psmt.setBytes(index, (byte[])value);
			} else if(value instanceof Integer) {
				psmt.setInt(index, (Integer)value);
			} else if(value instanceof Float) {
				psmt.setFloat(index, (Float)value);
			}  else if(value instanceof Double) {
				psmt.setDouble(index, (Double)value);
			} else if(value instanceof BigDecimal) {
				psmt.setBigDecimal(index, (BigDecimal)value);
			} else if(value instanceof Long) {
				psmt.setLong(index, (Long)value);
			} else if(value instanceof java.util.Date) {
				psmt.setDate(index, new java.sql.Date(((java.util.Date)value).getTime()));
			} else if(value instanceof java.sql.Date) {
				psmt.setDate(index, (java.sql.Date)value);
			} else if(value instanceof java.sql.Time) {
				psmt.setTime(index, (java.sql.Time)value);
			} else {
				psmt.setObject(index, value);
			}
			return true;
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 设置是否提交事务
	 * @param conn
	 * @param autoCommit
	 * @return
	 */
	public static boolean setAutoCommit(Connection conn, boolean autoCommit) {
		if(conn == null) {
			return false;
		}
		try {
			conn.setAutoCommit(autoCommit);
			return true;
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 事务提交
	 * @param conn
	 * @return
	 */
	public static boolean commit(Connection conn) {
		if(conn == null) {
			return false;
		}
		try {
			conn.commit();
			return true;
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 事务回滚
	 * @param conn
	 * @param savepoint
	 * @return
	 */
	public static boolean rollback(Connection conn) {
		return rollback(conn, null);
	}
	
	/**
	 * 事务回滚
	 * @param conn
	 * @param savepoint
	 * @return
	 */
	public static boolean rollback(Connection conn, Savepoint savepoint) {
		if(conn == null) {
			return false;
		}
		try {
			if(savepoint != null) {
				conn.rollback(savepoint);
			} else {
				conn.rollback();
			}
			return true;
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 执行数据库 sql语句更新
	 * @param connection
	 * @param sql
	 * @param values
	 * @return
	 */
	public static int executeUpdate(Connection connection, String sql, Collection<?> values) {
		PreparedStatement psmt = getPreparedStatement(connection, sql);
		if(psmt == null) {
			return -1;
		}
		if(values != null && !values.isEmpty()) {
			setStatementParameter(psmt, values);
		}
		try {
			return psmt.executeUpdate();
		} catch (Exception e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return -1;
		}
	}
	
	/**
	 * 执行数据库 sql语句查询
	 * @param connection
	 * @param sql
	 * @param values
	 * @return
	 */
	public static ResultSet executeQuery(Connection connection, String sql, Collection<?> values) {
		PreparedStatement psmt = getPreparedStatement(connection, sql);
		if(psmt == null) {
			return null;
		}
		if(values != null && !values.isEmpty()) {
			setStatementParameter(psmt, values);
		}
		try {
			return psmt.executeQuery();
		} catch (Exception e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 执行数据库 sql语句查询
	 * @param connection
	 * @param sql
	 * @param values
	 * @return
	 */
	public static List<Map<String, Object>> executeQueryMap(Connection connection, String sql, Collection<?> values) {
		// PreparedStatement
		PreparedStatement psmt = getPreparedStatement(connection, sql);
		if(psmt == null) {
			return null;
		}
		if(values != null && !values.isEmpty()) {
			setStatementParameter(psmt, values);
		}
		// ResultSet
		ResultSet rs = null;
		try {
			rs = psmt.executeQuery();
			return resultSetToList(rs);
		} catch (Exception e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		} finally {
			if(rs != null) {
				close(rs);
			}
			close(psmt);
		}
	}
	
	/**
	 * ResultSet 转 List
	 * @param rs
	 * @return
	 */
	public static List<Map<String, Object>> resultSetToList(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			ResultSetMetaData md = rs.getMetaData();
			// columnCount
			int columnCount = md.getColumnCount();
			// columnNames
			String[] columnNames = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnNames[i] = md.getColumnName(i);
			}
			while (rs.next()) {
				Map<String, Object> item = new HashMap<>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = columnNames[i];
					Object value = rs.getObject(i + 1);
					item.put(columnName, value);
				}
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 关闭 Connection
	 * @param conn
	 * @return
	 */
	public static boolean close(Connection conn) {
		if(conn == null) {
			return false;
		}
		try {
			if(!conn.isClosed()) {
				conn.close();
			}
			return true;
		} catch (SQLRecoverableException e) {
			LogUtils.debug(JdbcUtils.class, e.getMessage(), e);
			return false;
		} catch (SQLException e) {
			LogUtils.error(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 关闭 PreparedStatement 
	 * @param smt
	 * @return
	 */
	public static boolean close(Statement smt) {
		return close(smt, false);
	}
	
	/**
	 * 关闭 PreparedStatement 
	 * @param smt
	 * @param completion
	 * @return
	 */
	public static boolean close(Statement smt, boolean completion) {
		if(smt == null) {
			return false;
		}
		try {
			if(completion) {
				smt.closeOnCompletion();
			} else {
				smt.close();
			}
			return true;
		} catch (SQLException e) {
			LogUtils.debug(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 关闭 ResultSet 对象
	 * @param rs
	 * @return
	 */
	public static boolean close(ResultSet rs) {
		if(rs == null) {
			return false;
		}
		try {
			rs.close();
			return true;
		} catch (SQLException e) {
			LogUtils.debug(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 关闭 Blob 对象
	 * @param b
	 * @return
	 */
	public static boolean close(Blob b) {
		try {
			b.free();
			return true;
		} catch (Exception e) {
			LogUtils.debug(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 关闭 Clob 对象 
	 * @param c
	 * @return
	 */
	public static boolean close(Clob c) {
		try {
			c.free();
			return true;
		} catch (Exception e) {
			LogUtils.debug(JdbcUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 释放 多个对象
	 * @param conn
	 * @param smt
	 * @param rs
	 */
	public static void release(Connection conn, Statement smt, ResultSet rs) {
		close(rs);
		close(smt);
		close(conn);
	}
}
