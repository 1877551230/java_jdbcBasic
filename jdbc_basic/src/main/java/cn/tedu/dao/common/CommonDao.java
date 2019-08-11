package cn.tedu.dao.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CommonDao {
	/**
	 * 获取连接的公共方法
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection()throws Exception{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/testdb",
				"root",
				"root");
		return con;
	}
	/**
	 * 关闭资源的公共方法
	 * @param con
	 * @throws Exception
	 */
	public static void closeAll(Connection con,PreparedStatement pstmt,ResultSet rs)throws Exception{
		if(con!=null){
			con.close();
		}
		if(pstmt!=null){
			pstmt.close();
		}
		if(rs!=null){
			rs.close();
		}
	}

}
