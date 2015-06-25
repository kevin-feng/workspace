package org.tsc.core.base;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBToolKit {

	private static final String URL = "jdbc:mysql://localhost:3306/tsc?useUnicode=true&amp;characterEncoding=utf8";
	private static final String username = "root";
	private static final String password = "root";
	private static final String DRIVERNAME = "com.mysql.jdbc.Driver";

//	private static DataSource dataSource = null;
//	static {
//	try {
//	//获取资源文件
//	InputStream in = DBToolKit.class.getClassLoader().getResourceAsStream("cn/langzi/jdbc/DBCP/dbcpconfig.properties");
//	Properties properties = new Properties();
//	//加载资源文件
//	properties.load(in);
//	//建立数据工厂
//	BasicDataSourceFactory dataSourceFactory =  new BasicDataSourceFactory();
//	dataSource = dataSourceFactory.createDataSource(properties);
//	} catch (Exception e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//	}
//	}
//	 public static Connection getConnection() throws SQLException{
//	 return dataSource.getConnection();
//	 }
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(URL, username, password);
//			Context ctx = new InitialContext();
//			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/tsc");
//			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("open : connection");
		return con;
	}
	
	public static void closeConnection(Connection connection) throws SQLException {
		if (connection != null) {
			System.out.println("end : connection");
			connection.close();
		}
	}
	
	public static int update(String sql) {
		// update table1 set field1=value1 where 范围
		PreparedStatement pst = null;
		Connection con = null;
		try {
			con = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			pst = con.prepareStatement(sql);
		int count = pst.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				closeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<Map<String, Object>> select(String sql) {
		// select username,password,phone,email from userinfo where
		// username=’lfd’
//		System.out.println(sql);
		PreparedStatement pst = null;
		Connection con = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		try {
			con = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pst = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i =0;i<rsmd.getColumnCount();i++){
					map.put(rsmd.getColumnName(i+1), rs.getObject(i+1));
				}
				ret.add(map);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				pst.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			try {
				con.clearWarnings();
				closeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return ret;
	}

	public static int delete(String sql) {
		PreparedStatement pst = null;
		Connection con = null;
		try {
			con = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 执行 insert 语句
	 * @param sql
	 * @return  成功执行返回 1；执行错误返回 0
	 */
	public static int insert(String sql) {
		// insert into table1(field1,field2) values(value1,value2)
		PreparedStatement pst = null;
		Connection con = null;
		try {
			con = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(sql);
		}
		
	}
	
	
	/**
	 * 用来执行存储过程，可用来执行不要返回值的sql
	 * @param sql
	 * @return Boolean
	 */
	public static Boolean execute(String sql) {
		Connection con = null;
		Boolean b = null;
		con = getConnection();
		try {
			b = con.createStatement().execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				closeConnection(con);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return b;
	}
}
