package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;

import component.GroupNode;
import controller.Controller;
import view.MainFrame;
/**
 * �ٿ�Ⱥ�ĺ�Ⱥ��Ա����
 * @author wangzhen
 *
 */
public class GroupDaoImpl extends BaseDao implements GroupDao{
	/**
	 * �ӱ����ļ���ȡ��Ⱥ���б�
	 * @return Ⱥ���б�
	 */
	public ArrayList<GroupNode> getAllGroups() {
		File file = new File("source/" + MainFrame.getUname() + "/groupList.txt");
		return executeGroupQuery(file);
	}

	/**
	 * �򱾻��ļ��洢Ⱥ���б���Ϣ
	 * @param res Ⱥ���б�
	 */
	public void saveMyGroups(Collection<GroupNode> res) {
		File file = new File("source/" + MainFrame.getUname() + "/groupList.txt");
		executeGroupUpdate(file, res);
	}

	/**
	 * �ӱ����ļ���ȡ��Ⱥ�ĳ�Ա�б�
	 * @param node Ŀ��Ⱥ��
	 * @return Ⱥ�ĳ�Ա�б�
	 */
	public DefaultListModel<String> getAllGroupMembers(GroupNode node) {
		File file = new File("source/"+ MainFrame.getUname() + "/group/" + node.getOwner() + "-" + node.getGname() + ".txt");
		return executeGroupMembersQuery(file);
	}

	/**
	 * �򱾻��ļ��洢Ⱥ�ĳ�Ա�б���Ϣ
	 * @param node Ŀ��Ⱥ��
	 * @param res Ⱥ�ĳ�Ա�б�
	 */
	public void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res) {
		File file = new File("source/"+ MainFrame.getUname() + "/group/" + node.getOwner() + "-" + node.getGname() + ".txt");
		if(!file.exists()) {
			Controller.firstCreateGroupMemberFile(node);
		}
		executeGroupMembersUpdate(file, res);
	}
	
	/**
	 *��������
	 */
	public void testAddGroup() {
		saveMyGroups(Controller.getMyGroups());
	}
	/**
	 *��������
	 */
	public void testDelGroup() {
		saveMyGroups(Controller.getMyGroups());
	}
	/**
	 *��������
	 */
	public void testModifyGroup() {
		saveMyGroups(Controller.getMyGroups());
	}
	
	
}
