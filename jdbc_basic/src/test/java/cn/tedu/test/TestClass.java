package cn.tedu.test;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import cn.tedu.dao.common.CommonDao;
import cn.tedu.dao.user.UserDao;
import cn.tedu.dao.user.impl.UserDaoMySql;
import cn.tedu.entity.User;
import cn.tedu.vo.Page;

public class TestClass {
	@Test
	public void testConnection(){
		Connection con;
		try {
			con = CommonDao.getConnection();
			System.out.println(con.isClosed());
			//操作数据
			CommonDao.closeAll(con,null,null);
			System.out.println(con.isClosed());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testAddUser(){
		User user = new User();
		user.setName("bb");
		user.setPassword("bb");
		user.setAge(20);
		user.setAddress("chaoyang");
		UserDao userDao=new UserDaoMySql();
		int rowAffect=userDao.addUser(user);
		System.out.println(rowAffect);
	}
	@Test
	public void testUpdateUser(){
		User user = new User();
		user.setId(2);
		user.setName("bbb");
		user.setPassword("bbb");
		user.setAge(20);
		user.setAddress("yizhuang");
		UserDao userDao=new UserDaoMySql();
		int rowAffect=userDao.updateUser(user);
		System.out.println(rowAffect);
	}
	@Test
	public void deleteUpdateUser(){
		UserDao userDao=new UserDaoMySql();
		int rowAffect=userDao.deleteUser(2);
		System.out.println(rowAffect);
	}
	@Test
	public void testLogin(){
		User user = new User();
		
		UserDao userDao=new UserDaoMySql();
		user.setName("bb");
		user.setPassword("bb");
		int id=userDao.login(user);
		System.out.println(id);
	}
	@Test
	public void testfindUserById(){
		
		UserDao userDao=new UserDaoMySql();
		User user=userDao.findUserById(2);
		
		
		System.out.println(user);
	}
	@Test
	public void testfindAllUsers(){
		
		UserDao userDao=new UserDaoMySql();
		List<User> users=userDao.findAllUsers();
		for(User user:users)
		{
			System.out.println(user);
		}
		
		
	}
	@Test
	public void testFindUsersByPage1(){
		
		UserDao userDao=new UserDaoMySql();
		Page page=new Page();
		page.setCurrentPage(1);
		page.setPageSize(3);
		//查询数据库,获取总记录数
		int totalCount=userDao.getCount1();
		page.setTotalCount(totalCount);
		//计算总页数
		int totalPage=(totalCount%page.getPageSize()==0)? (totalCount/page.getPageSize()):(totalCount/page.getPageSize()+1);
		page.setTotalPage(totalPage);
		
		//计算前一页
		if(page.getCurrentPage()==1){
			page.setPreViousPage(1);
		}else{
			page.setPreViousPage(page.getCurrentPage()-1);
		}
		//计算后一页
		if(page.getCurrentPage()==totalPage){
			page.setNextPage(totalPage);
		}else{
			page.setNextPage(page.getCurrentPage()+1);
		}
		//
		List<User> users=userDao.getUsersByPage1(page.getCurrentPage(), page.getPageSize());
		page.setData(users);
		//到此为止page中的所有数据都存储完了
		System.out.println(page);
	}
	//分页+模糊测试
	@Test
	public void testFindUsersByPage2(){
		
		UserDao userDao=new UserDaoMySql();
		Page page=new Page();
		page.setCurrentPage(1);
		page.setPageSize(3);
		
		page.setKeywords(new String[]{"a","ch"});
		//查询数据库,获取总记录数
		int totalCount=userDao.getCount2(page);
		page.setTotalCount(totalCount);
		//计算总页数
		int totalPage=(totalCount%page.getPageSize()==0)? (totalCount/page.getPageSize()):(totalCount/page.getPageSize()+1);
		page.setTotalPage(totalPage);
		
		//计算前一页
		if(page.getCurrentPage()==1){
			page.setPreViousPage(1);
		}else{
			page.setPreViousPage(page.getCurrentPage()-1);
		}
		//计算后一页
		if(page.getCurrentPage()==totalPage){
			page.setNextPage(totalPage);
		}else{
			page.setNextPage(page.getCurrentPage()+1);
		}
		//
		List<User> users=userDao.getUsersByPage2(page);
		page.setData(users);
		//到此为止page中的所有数据都存储完了
		System.out.println(page);
	}
	@Test
	public void testFindAllUsers_Statement(){
		UserDao userDao=new UserDaoMySql();
		List<User> users = userDao.findAllUsers_Statement();
		for(User user:users){
			System.out.println(user);
		}
	}
	
}
