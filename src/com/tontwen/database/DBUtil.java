package com.tontwen.database;
import java.sql.*;


public class DBUtil {
	private static Connection conn=null;
	private static PreparedStatement ps = null;//sql command line
	private static ResultSet rs = null;

	private static String url = null;
	private static String username = null;
	private static String password = null;
	
	//connection settings
	static{
		try {
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			url = "jdbc:sqlserver://localhost:1433; DatabaseName=BottleDetectionLine";
			username = "sa";
			password  = "";

			System.out.println(driver);
			Class.forName(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//make connection
	public static Connection getConnection(){
		try {
			conn = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	//
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Query. IMPORTANT!
	public static ResultSet executeQuery(String sql,String[] parameters){
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			if(parameters!=null){
				for(int i=0;i<parameters.length;i++){
					ps.setObject(i+1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	//Update
	public static void executeUpdate(String sql,String[] parameters){
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			if(parameters!=null){
				for(int i=0;i<parameters.length;i++){
					ps.setObject(i+1, parameters[i]);
				}
			}
			System.out.println("dbutil");
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static ResultSet getRs() {
		return rs;
	}
}