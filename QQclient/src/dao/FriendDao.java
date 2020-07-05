package dao;

import java.util.ArrayList;
import java.util.Collection;

import component.FriendNode;

/**
 * �ٿغ����б�����
 * @author wangzhen
 *
 */
public interface FriendDao {
	/**
	 * �ӱ����ļ���ȡ�ú����б�
	 * @return �����б�
	 */
	public ArrayList<FriendNode> getAllFriends();
	/**
	 * �򱾻��ļ��洢�����б���Ϣ
	 * @param res �����б�
	 */
	public void saveMyFriends(Collection<FriendNode> res);

}
