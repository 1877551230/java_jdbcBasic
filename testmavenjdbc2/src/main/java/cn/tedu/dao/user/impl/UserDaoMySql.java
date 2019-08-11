package cn.tedu.dao.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			String sql = "select id from t_user where username=? and userpassword=?";
			Object[] params = new Object[] { user.getName(), user.getPassword() };
			
			List<User> users = CommonDao.executeQuery(User.class, sql, params);
			if (user != null && users.size() == 1) {
				id = users.get(0).getId();
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}

	@Override
	public Integer addUser(User user) {
		int rowAffect=0;
		
		String sql = "insert into t_user(username,userpassword,age,address)values(?,?,?,?)";
		Object[] params=new Object[]{
				user.getName(),
				user.getPassword(),
				user.getAge(),
				user.getAddress()
		};
		try {
			rowAffect=CommonDao.executeUpdate(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffect;
	}

	@Override
	public Integer deleteUser(Integer id) {
		int rowAffect=0;
		  
			String sql = "delete from t_user where id=?";
			try {
				rowAffect=CommonDao.executeUpdate(sql, id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return rowAffect;
	}

	@Override
	public Integer updateUser(User user) {
		int rowAffect=0;
		 
			String sql = "update t_user set username=?,userpassword=?,age=?,address=? where id=?";
		try {
			rowAffect=CommonDao.executeUpdate(sql, new Object[]{
					user.getName(),
					user.getPassword(),
					user.getAddress(),
					user.getAge(),
					user.getAddress(),
					user.getId()
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffect;
	}
	@Override
	public User findUserById(Integer id) {
		User user=null;
	
		try {
			String sql = "select id,username,userpassword,age,address from t_user where id=?";
			user = CommonDao.executeQuery(User.class, sql, id).get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users=null;
		
		try {
			String sql = "select id,username name,userpassword password,age,address from t_user";
			users = CommonDao.executeQuery(User.class,sql);
			
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
				String sql = "select id,username uname,userpassword upwd,age,address from t_user limit ?,?";
				Object[] params = new Object[] { (currentPage - 1) * pageSize, pageSize };
				users = CommonDao.executeQuery(User.class, sql, params);
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
			String sql = "select id,username uname,userpassword upwd,age,address from t_user "
					+ "where username like ? and address like ? " + "limit ?,?";
			Object[] params = new Object[] { "%" + page.getKeywords()[0] + "%",
											"%" + page.getKeywords()[1] + "%",
											(page.getCurrentPage() - 1) * page.getPageSize(),
											page.getPageSize() };
			users = CommonDao.executeQuery(User.class, sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}
	

}
