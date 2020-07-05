package dao;

import java.util.ArrayList;

import model.Group;

/**
 * 操控群聊，群成员的数据
 * @author wangzhen
 *
 */
public class GroupDaoImpl extends BaseDao implements GroupDao {


	/**
	 * 用户创建一个群聊
	 * @param group 目标群
	 * @return 成功与否
	 */
	public boolean create(Group group) {
		return executeUpdate(create_sql, group.getUname(), group.getGname());
	}
	/**
	 * 用户删除一个群聊
	 * @param group 目标群
	 * @return 成功与否
	 */
	public boolean delete(Group group) {
		return executeUpdate(delete_sql, group.getGname(), group.getUname());
	}
	/**
	 * 取得uname用户创建的所有群聊的名字
	 * @param uname 目标用户名
	 * @return 群聊结果集
	 */
	public ArrayList<Group> getAllGroup(String uname) {
		return executeGroupQuery(get_all_group_sql, uname);
	}
	/**
	 * 取得群所有的群成员
	 * @param gname 目标群名字
	 * @param owner 目标群主
	 * @return 群成员结果集
	 */
	public ArrayList<String> getAllGroupMember(String gname, String owner) {
		return executeGroupMemberQuery(get_all_groupMember_sql, gname, owner);
	}
	/**
	 * 邀请一名成员入群
	 * @param gname 目标群名字
	 * @param owner 目标群主
	 * @param gmember 目标成员
	 * @return 成功与否
	 */
	public boolean invite(String owner, String gname, String gmember) {
		return executeUpdate(invite_friend_sql, owner, gname, gmember);
	}
	/**
	 * 在群里踢出一名成员
	 * @param gname 目标群名字
	 * @param owner 目标群主
	 * @param gmember 目标成员
	 * @return 成功与否
	 */
	public boolean knock(String owner, String gname, String gmember) {
		return executeUpdate(knock_friend_sql, owner, gname, gmember);
	}
	/**
	 * 更新群名字
	 * @param owner 群主
	 * @param oldGname 旧群名字
	 * @param newGname 新群名字
	 * @return 成功与否
	 */
	public boolean updateGroupName(String owner, String oldGname, String newGname) {
		return executeUpdate(update_group_admin_sql, newGname, owner, oldGname) &&
			executeUpdate(update_group_info_sql, newGname, owner, oldGname);
	}

}
