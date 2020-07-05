package dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;

import component.GroupNode;

/**
 * �ٿ�Ⱥ�ĺ�Ⱥ��Ա����
 * @author wangzhen
 *
 */
public interface GroupDao {
	/**
	 * �ӱ����ļ���ȡ��Ⱥ���б�
	 * @return Ⱥ���б�
	 */
	public ArrayList<GroupNode> getAllGroups();
	/**
	 * �ӱ����ļ���ȡ��Ⱥ�ĳ�Ա�б�
	 * @param node Ŀ��Ⱥ��
	 * @return Ⱥ�ĳ�Ա�б�
	 */
	public DefaultListModel<String> getAllGroupMembers(GroupNode node);
	/**
	 * �򱾻��ļ��洢Ⱥ���б���Ϣ
	 * @param res Ⱥ���б�
	 */
	public void saveMyGroups(Collection<GroupNode> res);
	/**
	 * �򱾻��ļ��洢Ⱥ�ĳ�Ա�б���Ϣ
	 * @param node Ŀ��Ⱥ��
	 * @param res Ⱥ�ĳ�Ա�б�
	 */
	public void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res);
}
