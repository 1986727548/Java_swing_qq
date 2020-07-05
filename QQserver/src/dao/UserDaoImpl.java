package dao;

import java.util.ArrayList;

import model.User;

/**
 * 操控用户，好友数据
 * @author wangzhen
 *
 */
public class UserDaoImpl extends BaseDao implements UserDao {

	/**
	 * 判断登录是否成功
	 * @param user 目标用户名
	 * @return 成功与否
	 */
	public boolean login(User user) {
		return executeUserQuery(login_sql, user.getUname(), user.getPsword()).size() != 0;
	}
	/**
	 * 判断注册是否成功
	 * @param user 目标用户名
	 * @return 成功与否
	 */
	public boolean logon(User user) {
		return executeUpdate(logon_sql, user.getUname(), user.getPsword());
	}
	/**
	 * 添加好友是否成功
	 * @param uname 目标好友1
	 * @param fname 目标好友2
	 * @return 成功与否
	 */
	public boolean addFriend(String uname, String fname) {
		boolean res = executeUpdate(add_friend_sql, fname, uname);
		return executeUpdate(add_friend_sql, uname, fname) && res;
	}
	/**
	 * 删除好友是否成功
	 * @param uname 目标好友1
	 * @param fname 目标好友2
	 * @return 成功与否
	 */
	public boolean delFriend(String uname, String fname) {
		boolean res = executeUpdate(del_friend_sql, fname, uname);
		return executeUpdate(del_friend_sql, uname, fname) && res;
	}
	/**
	 * 取得用户所有的好友
	 * @param uname 目标用户名
	 * @return 好友结果集
	 */
	public ArrayList<User> getAllFriends(String uname) {
		return executeFriendQuery(get_all_friend_sql, uname);
	}
	/**
	 * 根据string进行模糊查询用户
	 * @param string 目标模糊查询串
	 * @return 查询好友结果集
	 */
	public ArrayList<User> getUsersFromQuery(String string) {
		return executeUserQuery(query_user_sql, string);
	}

}
