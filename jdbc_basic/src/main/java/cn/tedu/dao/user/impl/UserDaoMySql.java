package cn.tedu.dao.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.dao.common.CommonDao;
import cn.tedu.dao.user.UserDao;
import cn.tedu.entity.User;
import cn.tedu.vo.Page;

public class UserDaoMySql implements UserDao{

	@Override
	public Integer login(User user) {
		int id = 0;
		try {
			
			//1.开连接/获取连接
			Connection con = CommonDao.getConnection();
			//2.构建dql的sql语句
			String sql = "select id idd from t_user where username=? and userpassword=?";
			//3.基于连接把dql的SQL语句运送到数据库,构建执行计划,sql语句中有?占位符,给?占位符赋值
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			//4.基于连接和完整的sql语句,调用executeQuery()方法执行sql语句
			ResultSet rs = pstmt.executeQuery();
			//5.循环遍历ResultSet中的数据,并把数据转存给缓存List集合
			if (rs.next()) {
				id = rs.getInt("idd");//id为结果集的列名字
			}
			//6.关闭连接和相关资源
			CommonDao.closeAll(con, pstmt, rs);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}

	@Override
	public Integer addUser(User user) {
		int rowAffect=0;
		Connection con=null;
		   try {
			//1.开连接/获取连接
			 con = CommonDao.getConnection();
			con.setAutoCommit(false);
			//2.构建dml的sql语句
			String sql = "insert into t_user(username,userpassword,age,address)values(?,?,?,?)";
			//3.基于连接把dml的SQL语句运送到数据库,构建执行计划  sql语句中有?占位符,给?占位符赋值
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getAge());
			pstmt.setString(4, user.getAddress());
			//4.基于连接和完整的sql语句,调用executeUpdate()方法执行sql语句
			rowAffect = pstmt.executeUpdate();
			con.commit();
			//5.关闭连接和相关资源
			CommonDao.closeAll(con, pstmt,null);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return rowAffect;
	}

	@Override
	public Integer deleteUser(Integer id) {
		int rowAffect=0;
		   try {
			//1.开连接/获取连接
			Connection con = CommonDao.getConnection();
			//2.构建dml的sql语句
			String sql = "delete from t_user where id=?";
			//3.基于连接把dml的SQL语句运送到数据库,构建执行计划  sql语句中有?占位符,给?占位符赋值
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,id);
			
			//4.基于连接和完整的sql语句,调用executeUpdate()方法执行sql语句
			rowAffect = pstmt.executeUpdate();
			//5.关闭连接和相关资源
			CommonDao.closeAll(con, pstmt,null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rowAffect;
	}

	@Override
	public Integer updateUser(User user) {
		int rowAffect=0;
		   try {
			//1.开连接/获取连接
			Connection con = CommonDao.getConnection();
			//2.构建dml的sql语句
			String sql = "update t_user set username=?,userpassword=?,age=?,address=? where id=?";
			//3.基于连接把dml的SQL语句运送到数据库,构建执行计划  sql语句中有?占位符,给?占位符赋值
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getAge());
			pstmt.setString(4, user.getAddress());
			pstmt.setInt(5, user.getId());
			
			//4.基于连接和完整的sql语句,调用executeUpdate()方法执行sql语句
			rowAffect = pstmt.executeUpdate();
			//5.关闭连接和相关资源
			CommonDao.closeAll(con, pstmt,null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rowAffect;
	}
	@Override
	public User findUserById(Integer id) {
		User user=null;
		try {
			
			//1.开连接/获取连接
			Connection con = CommonDao.getConnection();
			//2.构建dql的sql语句
			String sql = "select id,username,userpassword,age,address from t_user where id=?";
			//3.基于连接把dql的SQL语句运送到数据库,构建执行计划,sql语句中有?占位符,给?占位符赋值
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			//4.基于连接和完整的sql语句,调用executeQuery()方法执行sql语句
			ResultSet rs = pstmt.executeQuery();
			//5.循环遍历ResultSet中的数据,并把数据转存给缓存List集合
			if (rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("username"));
				user.setPassword(rs.getString("userpassword"));
				user.setAge(rs.getInt("age"));
				user.setAddress(rs.getString("address"));
			}
			//6.关闭连接和相关资源
			CommonDao.closeAll(con, pstmt, rs);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users=new ArrayList<User>();
		try {
			
			//1.开连接/获取连接
			Connection con = CommonDao.getConnection();
			//2.构建dql的sql语句
			String sql = "select id,username,userpassword,age,address from t_user";
			//3.基于连接把dql的SQL语句运送到数据库,构建执行计划,sql语句中有?占位符,给?占位符赋值
			PreparedStatement pstmt = con.prepareStatement(sql);
			//4.基于连接和完整的sql语句,调用executeQuery()方法执行sql语句
			ResultSet rs = pstmt.executeQuery();
			rs.setFetchSize(3);
			//5.循环遍历ResultSet中的数据,并把数据转存给缓存List集合
			while(rs.next()) {
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("username"));
				user.setPassword(rs.getString("userpassword"));
				user.setAge(rs.getInt("age"));
				user.setAddress(rs.getString("address"));
				users.add(user);
			}
			//6.关闭连接和相关资源
			CommonDao.closeAll(con, pstmt, rs);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}
	//单纯分页
	@Override
	public Integer getCount1() {
		Integer count=0;
		try {
			Connection con = CommonDao.getConnection();
			String sql = "select count(id) geshu from t_user";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("geshu");
			}
			CommonDao.closeAll(con, pstmt, rs);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	@Override
	public List<User> getUsersByPage1(int currentPage,int pageSize) {
		List<User> users=new ArrayList<User>();
		try {
			Connection con = CommonDao.getConnection();
			String sql = "select id,username uname,userpassword upwd,age,address from t_user limit ?,?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (currentPage-1)*pageSize);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("uname"));
				user.setPassword(rs.getString("upwd"));;
				user.setAge(rs.getInt("age"));
				user.setAddress(rs.getString("address"));;
				users.add(user);
			}
			CommonDao.closeAll(con, pstmt, rs);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}

	//分页+模糊
	@Override
	public Integer getCount2(Page<User> page) {
		Integer count=0;
		try {
			Connection con = CommonDao.getConnection();
			String sql = "select count(id) geshu from t_user " + "where username like ? and address like ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + page.getKeywords()[0] + "%");
			pstmt.setString(2, "%" + page.getKeywords()[1] + "%");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("geshu");
			}
			CommonDao.closeAll(con, pstmt, rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<User> getUsersByPage2(Page<User> page) {
		List<User> users=new ArrayList<User>();
		try {
			Connection con = CommonDao.getConnection();
			String sql = "select id,username uname,userpassword upwd,age,address from t_user "
					+ "where username like ? and address like ? " + "limit ?,?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + page.getKeywords()[0] + "%");
			pstmt.setString(2, "%" + page.getKeywords()[1] + "%");
			pstmt.setInt(3, (page.getCurrentPage() - 1) * page.getPageSize());
			pstmt.setInt(4, page.getPageSize());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("uname"));
				user.setPassword(rs.getString("upwd"));
				user.setAge(rs.getInt("age"));
				user.setAddress(rs.getString("address"));
				users.add(user);
			}
			CommonDao.closeAll(con, pstmt, rs);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}

	@Override
	public List<User> findAllUsers_Statement() {
		String keyword1="a";
		String keyword2="ch";
		List<User> users=new ArrayList<User>();
		try {
			Connection con = CommonDao.getConnection();
			String sql = "select * from t_user where username like '%"+keyword1+"%' and address like '%"+keyword2+"%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("username"));
				user.setPassword(rs.getString("userpassword"));
				
				user.setAge(rs.getInt("age"));
				user.setAddress(rs.getString("address"));
				
				users.add(user);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}
	

}
