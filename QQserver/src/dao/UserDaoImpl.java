package dao;

import java.util.ArrayList;

import model.User;

/**
 * �ٿ��û�����������
 * @author wangzhen
 *
 */
public class UserDaoImpl extends BaseDao implements UserDao {

	/**
	 * �жϵ�¼�Ƿ�ɹ�
	 * @param user Ŀ���û���
	 * @return �ɹ����
	 */
	public boolean login(User user) {
		return executeUserQuery(login_sql, user.getUname(), user.getPsword()).size() != 0;
	}
	/**
	 * �ж�ע���Ƿ�ɹ�
	 * @param user Ŀ���û���
	 * @return �ɹ����
	 */
	public boolean logon(User user) {
		return executeUpdate(logon_sql, user.getUname(), user.getPsword());
	}
	/**
	 * ��Ӻ����Ƿ�ɹ�
	 * @param uname Ŀ�����1
	 * @param fname Ŀ�����2
	 * @return �ɹ����
	 */
	public boolean addFriend(String uname, String fname) {
		boolean res = executeUpdate(add_friend_sql, fname, uname);
		return executeUpdate(add_friend_sql, uname, fname) && res;
	}
	/**
	 * ɾ�������Ƿ�ɹ�
	 * @param uname Ŀ�����1
	 * @param fname Ŀ�����2
	 * @return �ɹ����
	 */
	public boolean delFriend(String uname, String fname) {
		boolean res = executeUpdate(del_friend_sql, fname, uname);
		return executeUpdate(del_friend_sql, uname, fname) && res;
	}
	/**
	 * ȡ���û����еĺ���
	 * @param uname Ŀ���û���
	 * @return ���ѽ����
	 */
	public ArrayList<User> getAllFriends(String uname) {
		return executeFriendQuery(get_all_friend_sql, uname);
	}
	/**
	 * ����string����ģ����ѯ�û�
	 * @param string Ŀ��ģ����ѯ��
	 * @return ��ѯ���ѽ����
	 */
	public ArrayList<User> getUsersFromQuery(String string) {
		return executeUserQuery(query_user_sql, string);
	}

}
