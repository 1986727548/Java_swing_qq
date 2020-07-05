package dao;

import java.util.ArrayList;

import model.Group;

/**
 * 操控群聊，群成员的数据
 * @author wangzhen
 *
 */
public interface GroupDao {
	/**
	 * 创建群聊sql语句
	 */
	public static String create_sql = "insert into group_admin(uname, gname) values(?, ?) ";
	/**
	 * 删除群聊sql语句
	 */
	public static String delete_sql = "delete from group_admin where uname = ? and gname = ?";
	/**
	 * 取得群名称sql语句
	 */
	public static String get_all_group_sql = "select gname, uname from group_info where gmember = ? order by gname, uname";
	/**
	 * 取得群所有成员sql语句
	 */
	public static String get_all_groupMember_sql = "select gmember from group_info where gname = ? and uname = ? order by gmember";
	/**
	 * 邀请成员sql语句
	 */
	public static String invite_friend_sql = "insert into group_info(uname, gname, gmember) values(?, ?, ?)";
	/**
	 * 踢出成员sql语句
	 */
	public static String knock_friend_sql = "delete from group_info where uname = ? and gname = ? and gmember = ?";
	/**
	 * 更新群名字sql语句
	 */
	public static String update_group_admin_sql = "update group_admin set gname = ? where uname = ? and gname = ?";
	/**
	 * 更新群名字sql语句
	 */
	public static String update_group_info_sql = "update group_info set gname = ? where uname = ? and gname = ?";
	/**
	 * 用户创建一个群聊
	 * @param group 目标群
	 * @return 成功与否
	 */
	public boolean create(Group group);
	/**
	 * 用户删除一个群聊
	 * @param group 目标群
	 * @return 成功与否
	 */
	public boolean delete(Group group);
	/**
	 * 取得uname用户创建的所有群聊的名字
	 * @param uname 目标用户名
	 * @return 群聊结果集
	 */
	public ArrayList<Group> getAllGroup(String uname);
	/**
	 * 取得群所有的群成员
	 * @param gname 目标群名字
	 * @param owner 目标群主
	 * @return 群成员结果集
	 */
	public ArrayList<String> getAllGroupMember(String gname, String owner);
	/**
	 * 邀请一名成员入群
	 * @param gname 目标群名字
	 * @param owner 目标群主
	 * @param gmember 目标成员
	 * @return 成功与否
	 */
	public boolean invite(String owner, String gname, String gmember);
	/**
	 * 在群里踢出一名成员
	 * @param gname 目标群名字
	 * @param owner 目标群主
	 * @param gmember 目标成员
	 * @return 成功与否
	 */
	public boolean knock(String owner, String gname, String gmember);
	/**
	 * 更新群名字
	 * @param owner 群主
	 * @param oldGname 旧群名字
	 * @param newGname 新群名字
	 * @return 成功与否
	 */
	public boolean updateGroupName(String owner, String oldGname, String newGname);
}
