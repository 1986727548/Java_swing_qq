package dao;

import java.util.ArrayList;

import model.Group;

/**
 * �ٿ�Ⱥ�ģ�Ⱥ��Ա������
 * @author wangzhen
 *
 */
public class GroupDaoImpl extends BaseDao implements GroupDao {


	/**
	 * �û�����һ��Ⱥ��
	 * @param group Ŀ��Ⱥ
	 * @return �ɹ����
	 */
	public boolean create(Group group) {
		return executeUpdate(create_sql, group.getUname(), group.getGname());
	}
	/**
	 * �û�ɾ��һ��Ⱥ��
	 * @param group Ŀ��Ⱥ
	 * @return �ɹ����
	 */
	public boolean delete(Group group) {
		return executeUpdate(delete_sql, group.getGname(), group.getUname());
	}
	/**
	 * ȡ��uname�û�����������Ⱥ�ĵ�����
	 * @param uname Ŀ���û���
	 * @return Ⱥ�Ľ����
	 */
	public ArrayList<Group> getAllGroup(String uname) {
		return executeGroupQuery(get_all_group_sql, uname);
	}
	/**
	 * ȡ��Ⱥ���е�Ⱥ��Ա
	 * @param gname Ŀ��Ⱥ����
	 * @param owner Ŀ��Ⱥ��
	 * @return Ⱥ��Ա�����
	 */
	public ArrayList<String> getAllGroupMember(String gname, String owner) {
		return executeGroupMemberQuery(get_all_groupMember_sql, gname, owner);
	}
	/**
	 * ����һ����Ա��Ⱥ
	 * @param gname Ŀ��Ⱥ����
	 * @param owner Ŀ��Ⱥ��
	 * @param gmember Ŀ���Ա
	 * @return �ɹ����
	 */
	public boolean invite(String owner, String gname, String gmember) {
		return executeUpdate(invite_friend_sql, owner, gname, gmember);
	}
	/**
	 * ��Ⱥ���߳�һ����Ա
	 * @param gname Ŀ��Ⱥ����
	 * @param owner Ŀ��Ⱥ��
	 * @param gmember Ŀ���Ա
	 * @return �ɹ����
	 */
	public boolean knock(String owner, String gname, String gmember) {
		return executeUpdate(knock_friend_sql, owner, gname, gmember);
	}
	/**
	 * ����Ⱥ����
	 * @param owner Ⱥ��
	 * @param oldGname ��Ⱥ����
	 * @param newGname ��Ⱥ����
	 * @return �ɹ����
	 */
	public boolean updateGroupName(String owner, String oldGname, String newGname) {
		return executeUpdate(update_group_admin_sql, newGname, owner, oldGname) &&
			executeUpdate(update_group_info_sql, newGname, owner, oldGname);
	}

}
