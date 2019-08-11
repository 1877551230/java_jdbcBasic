package cn.tedu.dao.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 开连接
 * 关连接
 * 通用dml
 * 通用dql
 * @author PC
 *
 */
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
	/**
	 * 通用dml操作
	 * @param sql dml sql语句
	 * @param params dml sql语句的问号?占位符
	 * @return 受影响的行数
	 */
	public static int executeUpdate(String sql,Object...params)throws Exception{
		int rowAffect=0;
		Connection con=getConnection();
		PreparedStatement pstmt=con.prepareStatement(sql);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
		}
		rowAffect=pstmt.executeUpdate();
		closeAll(con,pstmt,null);
		return rowAffect;
	}
	/**
	 * 策略版本的通用查询
	 * @param rm 处理结果集的策略
	 * @param sql  dql的sql语句
	 * @param params  dql的参数
	 * @return List集合
	 * @throws Exception
	 */
	public static <T> List<T> executeQuery(RowMapper<T> rm,String sql,Object...params)throws Exception{
		List<T> list = new ArrayList<T>();
		Connection con=getConnection();
		PreparedStatement pstmt=con.prepareStatement(sql);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
		}
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			T t=rm.mapRow(rs);
			list.add(t);
		}
		closeAll(con,pstmt,rs);
		return list;
	}

}
