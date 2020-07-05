package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import component.FriendNode;
import view.MainFrame;

/**
 * �ٿغ����б�����
 * @author wangzhen
 *
 */
public class FriendDaoImpl extends BaseDao implements FriendDao {

	/**
	 * �ӱ����ļ���ȡ�ú����б�
	 * @return �����б�
	 */
	public ArrayList<FriendNode> getAllFriends() {
		return executeFriendQuery(new File("source/" + MainFrame.getUname() + "/friendList.txt"));
	}
	/**
	 * �򱾻��ļ��洢�����б���Ϣ
	 * @param res �����б�
	 */
	public void saveMyFriends(Collection<FriendNode> res) {
		executeFriendUpdate(new File("source/" + MainFrame.getUname() + "/friendList.txt"), res);
	}
	

}
