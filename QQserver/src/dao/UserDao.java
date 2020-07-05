package dao;

import java.util.ArrayList;

import model.User;

/**
 * �ٿ��û�����������
 * @author wangzhen
 *
 */
public interface UserDao {
	/**
	 * ��ѯ�û�sql���
	 */
	public static String login_sql = "select * from user where uname = ? and psword = ?";
	/**
	 * �����û�sql���
	 */
	public static String logon_sql = "insert into user(uname, psword) values(?, ?)";
	/**
	 * ����unameģ����ѯsql���
	 */
	public static String query_user_sql = "select uname from user where uname like ?";
	/**
	 * ����uname��ѯ����sql���
	 */
	public static String get_all_friend_sql = "select fname from friend_info where uname = ?";
	/**
	 * �������sql���
	 */
	public static String add_friend_sql = "insert into friend_info(uname, fname) values(?, ?)";
	/**
	 * ɾ������sql���
	 */
	public static String del_friend_sql = "delete from friend_info where uname = ? and fname = ?";
	
	/**
	 * �жϵ�¼�Ƿ�ɹ�
	 * @param user Ŀ���û���
	 * @return �ɹ����
	 */
	public boolean login(User user);
	/**
	 * �ж�ע���Ƿ�ɹ�
	 * @param user Ŀ���û���
	 * @return �ɹ����
	 */
	public boolean logon(User user);
	/**
	 * ��Ӻ����Ƿ�ɹ�
	 * @param uname Ŀ�����1
	 * @param fname Ŀ�����2
	 * @return �ɹ����
	 */
	public boolean addFriend(String uname, String fname);
	/**
	 * ɾ�������Ƿ�ɹ�
	 * @param uname Ŀ�����1
	 * @param fname Ŀ�����2
	 * @return �ɹ����
	 */
	public boolean delFriend(String uname, String fname);
	/**
	 * ȡ���û����еĺ���
	 * @param uname Ŀ���û���
	 * @return ���ѽ����
	 */
	public ArrayList<User> getAllFriends(String uname);
	/**
	 * ����string����ģ����ѯ�û�
	 * @param string Ŀ��ģ����ѯ��
	 * @return ��ѯ���ѽ����
	 */
	public ArrayList<User> getUsersFromQuery(String string);
}
