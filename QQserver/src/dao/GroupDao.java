package dao;

import java.util.ArrayList;

import model.Group;

/**
 * �ٿ�Ⱥ�ģ�Ⱥ��Ա������
 * @author wangzhen
 *
 */
public interface GroupDao {
	/**
	 * ����Ⱥ��sql���
	 */
	public static String create_sql = "insert into group_admin(uname, gname) values(?, ?) ";
	/**
	 * ɾ��Ⱥ��sql���
	 */
	public static String delete_sql = "delete from group_admin where uname = ? and gname = ?";
	/**
	 * ȡ��Ⱥ����sql���
	 */
	public static String get_all_group_sql = "select gname, uname from group_info where gmember = ? order by gname, uname";
	/**
	 * ȡ��Ⱥ���г�Աsql���
	 */
	public static String get_all_groupMember_sql = "select gmember from group_info where gname = ? and uname = ? order by gmember";
	/**
	 * �����Աsql���
	 */
	public static String invite_friend_sql = "insert into group_info(uname, gname, gmember) values(?, ?, ?)";
	/**
	 * �߳���Աsql���
	 */
	public static String knock_friend_sql = "delete from group_info where uname = ? and gname = ? and gmember = ?";
	/**
	 * ����Ⱥ����sql���
	 */
	public static String update_group_admin_sql = "update group_admin set gname = ? where uname = ? and gname = ?";
	/**
	 * ����Ⱥ����sql���
	 */
	public static String update_group_info_sql = "update group_info set gname = ? where uname = ? and gname = ?";
	/**
	 * �û�����һ��Ⱥ��
	 * @param group Ŀ��Ⱥ
	 * @return �ɹ����
	 */
	public boolean create(Group group);
	/**
	 * �û�ɾ��һ��Ⱥ��
	 * @param group Ŀ��Ⱥ
	 * @return �ɹ����
	 */
	public boolean delete(Group group);
	/**
	 * ȡ��uname�û�����������Ⱥ�ĵ�����
	 * @param uname Ŀ���û���
	 * @return Ⱥ�Ľ����
	 */
	public ArrayList<Group> getAllGroup(String uname);
	/**
	 * ȡ��Ⱥ���е�Ⱥ��Ա
	 * @param gname Ŀ��Ⱥ����
	 * @param owner Ŀ��Ⱥ��
	 * @return Ⱥ��Ա�����
	 */
	public ArrayList<String> getAllGroupMember(String gname, String owner);
	/**
	 * ����һ����Ա��Ⱥ
	 * @param gname Ŀ��Ⱥ����
	 * @param owner Ŀ��Ⱥ��
	 * @param gmember Ŀ���Ա
	 * @return �ɹ����
	 */
	public boolean invite(String owner, String gname, String gmember);
	/**
	 * ��Ⱥ���߳�һ����Ա
	 * @param gname Ŀ��Ⱥ����
	 * @param owner Ŀ��Ⱥ��
	 * @param gmember Ŀ���Ա
	 * @return �ɹ����
	 */
	public boolean knock(String owner, String gname, String gmember);
	/**
	 * ����Ⱥ����
	 * @param owner Ⱥ��
	 * @param oldGname ��Ⱥ����
	 * @param newGname ��Ⱥ����
	 * @return �ɹ����
	 */
	public boolean updateGroupName(String owner, String oldGname, String newGname);
}
