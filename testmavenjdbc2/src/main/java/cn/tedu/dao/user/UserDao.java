package cn.tedu.dao.user;

import java.util.List;

import cn.tedu.entity.User;
import cn.tedu.vo.Page;

public interface UserDao {
	/**
	 * 登录方法
	 * @param user 登录的信息
	 * @return id
	 *         id>0,用户存在
	 *         id<0,用户不存在
	 */
	public Integer login(User user);
	/**
	 * 添加用户
	 * @param user
	 * @return 受影响的行数
	 *         1:成功
	 *         0:失败
	 */
	public Integer addUser(User user);
	/**
	 * 添加用户
	 * @param id  用户的id
	 * @return 受影响的行数
	 *         1:成功
	 *         0:失败
	 */
	public Integer deleteUser(Integer id);
	/**
	 * 修改用户
	 * @param user
	 * @return 受影响的行数
	 *         1:成功
	 *         0:失败
	 */
	public Integer updateUser(User user);
	/**
	 * 根据用户的id查询用户的信息
	 * @param id
	 * @return
	 */
	public User findUserById(Integer id);
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<User> findAllUsers();
	//分页查询
	public Integer getCount1();//获取总记录数
	public List<User> getUsersByPage1(int currentPage,int pageSize);//获取当前页的数据

	//分页+模糊查询
	public Integer getCount2(Page<User> page);
	public List<User> getUsersByPage2(Page<User> page);
}



