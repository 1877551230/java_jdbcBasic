package cn.tedu.dao.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.dao.common.CommonDao;
import cn.tedu.dao.common.RowMapper;
import cn.tedu.dao.user.UserDao;
import cn.tedu.entity.User;
import cn.tedu.vo.Page;

public class UserDaoMySql implements UserDao {

	@Override
	public Integer login(User user) {
		int id = 0;

		try {
			String sql = "select id idd from t_user where username=? and userpassword=?";
			Object[] params = new Object[] { user.getName(), user.getPassword() };
			RowMapper<User> rm = new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs) throws SQLException {
					User user = new User();
					user.setId(rs.getInt("idd"));
					return user;
				}

			};
			List<User> users = CommonDao.executeQuery(rm, sql, params);
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
		int rowAffect = 0;

		String sql = "insert into t_user(username,userpassword,age,address)values(?,?,?,?)";
		Object[] params = new Object[] { user.getName(), user.getPassword(), user.getAge(), user.getAddress() };
		try {
			rowAffect = CommonDao.executeUpdate(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffect;
	}

	@Override
	public Integer deleteUser(Integer id) {
		int rowAffect = 0;

		String sql = "delete from t_user where id=?";
		try {
			rowAffect = CommonDao.executeUpdate(sql, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffect;
	}

	@Override
	public Integer updateUser(User user) {
		int rowAffect = 0;

		String sql = "update t_user set username=?,userpassword=?,age=?,address=? where id=?";
		try {
			rowAffect = CommonDao.executeUpdate(sql, new Object[] {   user.getName(), user.getPassword(),
					 user.getAge(), user.getAddress(),user.getId()});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffect;
	}

	@Override
	public User findUserById(Integer id) {
		List<User> list = new ArrayList<User>();

		try {
			String sql = "select id,username,userpassword,age,address from t_user where id=?";
			RowMapper<User> rm = new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs) throws SQLException {
					User user = new User();
					user.setName(rs.getString("username"));
					user.setId(rs.getInt("id"));
					user.setPassword(rs.getString("userpassword"));
					user.setAge(rs.getInt("age"));
					user.setAddress(rs.getString("address"));
					return user;
				}

			};
			list = CommonDao.executeQuery(rm, sql, id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list.get(0);
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users = null;

		try {
			String sql = "select id,username,userpassword,age,address from t_user";
			users = CommonDao.executeQuery(new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs) throws SQLException {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("username"));
					user.setPassword(rs.getString("userpassword"));
					user.setId(rs.getInt("age"));
					user.setAddress(rs.getString("address"));
					return user;
				}

			}, sql);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}

	// 单纯分页
	@Override
	public Integer getCount1() {
		List<Integer> list=new ArrayList<Integer>();

		try {
			String sql = "select count(id) geshu from t_user";
			RowMapper<Integer> rm = new RowMapper<Integer>(){

				@Override
				public Integer mapRow(ResultSet rs) throws SQLException {
					int count=rs.getInt(1);
					return count;
				}
				
			};
			list=CommonDao.executeQuery(rm, sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list.get(0);
	}

	@Override
	public List<User> getUsersByPage1(int currentPage, int pageSize) {
		List<User> users = new ArrayList<User>();

		try {
			String sql = "select id,username uname,userpassword upwd,age,address from t_user limit ?,?";
			Object[] params = new Object[] { (currentPage - 1) * pageSize, pageSize };
			RowMapper<User> rm = new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs) throws SQLException {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("uname"));
					user.setPassword(rs.getString("upwd"));
					user.setAge(rs.getInt("age"));
					user.setAddress(rs.getString("address"));
					return user;
				}

			};
			users = CommonDao.executeQuery(rm, sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}

	// 分页+模糊
	@Override
	public Integer getCount2(Page<User> page) {
		List<Integer> list=new ArrayList<Integer>();

		try {
			String sql = "select count(id) geshu from t_user " + "where username like ? and address like ?";
			Object[] params = new Object[] { "%" + page.getKeywords()[0] + "%", "%" + page.getKeywords()[1] + "%" };
			RowMapper<Integer> rm=new RowMapper<Integer>(){

				@Override
				public Integer mapRow(ResultSet rs) throws SQLException {
					int count=rs.getInt(1);
					return count;
				}
				
			};
			list = CommonDao.executeQuery(rm, sql, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}

	@Override
	public List<User> getUsersByPage2(Page<User> page) {
		List<User> users = new ArrayList<User>();
	
			try {
				String sql = "select id,username uname,userpassword upwd,age,address from t_user "
						+ "where username like ? and address like ? " + "limit ?,?";
				Object[] params = new Object[] { "%" + page.getKeywords()[0] + "%", "%" + page.getKeywords()[1] + "%",
						(page.getCurrentPage() - 1) * page.getPageSize(), page.getPageSize() };
				RowMapper<User> rm = new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs) throws SQLException {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("uname"));
						user.setPassword(rs.getString("upwd"));
						user.setAge(rs.getInt("age"));
						user.setAddress(rs.getString("address"));
						return user;
					}

				};
				users = CommonDao.executeQuery(rm, sql, params);
			} catch (Exception e) {
				// TODO: handle exception
			}
		return users;
	}

}
