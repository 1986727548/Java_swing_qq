package dao;

import java.util.ArrayList;

import model.User;

/**
 * 操控用户，好友数据
 * @author wangzhen
 *
 */
public interface UserDao {
	/**
	 * 查询用户sql语句
	 */
	public static String login_sql = "select * from user where uname = ? and psword = ?";
	/**
	 * 插入用户sql语句
	 */
	public static String logon_sql = "insert into user(uname, psword) values(?, ?)";
	/**
	 * 根据uname模糊查询sql语句
	 */
	public static String query_user_sql = "select uname from user where uname like ?";
	/**
	 * 根据uname查询好友sql语句
	 */
	public static String get_all_friend_sql = "select fname from friend_info where uname = ?";
	/**
	 * 插入好友sql语句
	 */
	public static String add_friend_sql = "insert into friend_info(uname, fname) values(?, ?)";
	/**
	 * 删除好友sql语句
	 */
	public static String del_friend_sql = "delete from friend_info where uname = ? and fname = ?";
	
	/**
	 * 判断登录是否成功
	 * @param user 目标用户名
	 * @return 成功与否
	 */
	public boolean login(User user);
	/**
	 * 判断注册是否成功
	 * @param user 目标用户名
	 * @return 成功与否
	 */
	public boolean logon(User user);
	/**
	 * 添加好友是否成功
	 * @param uname 目标好友1
	 * @param fname 目标好友2
	 * @return 成功与否
	 */
	public boolean addFriend(String uname, String fname);
	/**
	 * 删除好友是否成功
	 * @param uname 目标好友1
	 * @param fname 目标好友2
	 * @return 成功与否
	 */
	public boolean delFriend(String uname, String fname);
	/**
	 * 取得用户所有的好友
	 * @param uname 目标用户名
	 * @return 好友结果集
	 */
	public ArrayList<User> getAllFriends(String uname);
	/**
	 * 根据string进行模糊查询用户
	 * @param string 目标模糊查询串
	 * @return 查询好友结果集
	 */
	public ArrayList<User> getUsersFromQuery(String string);
}
