package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;

import component.GroupNode;
import controller.Controller;
import view.MainFrame;
/**
 * 操控群聊和群成员数据
 * @author wangzhen
 *
 */
public class GroupDaoImpl extends BaseDao implements GroupDao{
	/**
	 * 从本机文件中取得群聊列表
	 * @return 群聊列表
	 */
	public ArrayList<GroupNode> getAllGroups() {
		File file = new File("source/" + MainFrame.getUname() + "/groupList.txt");
		return executeGroupQuery(file);
	}

	/**
	 * 向本机文件存储群聊列表信息
	 * @param res 群聊列表
	 */
	public void saveMyGroups(Collection<GroupNode> res) {
		File file = new File("source/" + MainFrame.getUname() + "/groupList.txt");
		executeGroupUpdate(file, res);
	}

	/**
	 * 从本机文件中取得群聊成员列表
	 * @param node 目标群聊
	 * @return 群聊成员列表
	 */
	public DefaultListModel<String> getAllGroupMembers(GroupNode node) {
		File file = new File("source/"+ MainFrame.getUname() + "/group/" + node.getOwner() + "-" + node.getGname() + ".txt");
		return executeGroupMembersQuery(file);
	}

	/**
	 * 向本机文件存储群聊成员列表信息
	 * @param node 目标群聊
	 * @param res 群聊成员列表
	 */
	public void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res) {
		File file = new File("source/"+ MainFrame.getUname() + "/group/" + node.getOwner() + "-" + node.getGname() + ".txt");
		if(!file.exists()) {
			Controller.firstCreateGroupMemberFile(node);
		}
		executeGroupMembersUpdate(file, res);
	}
	
	/**
	 *测试所用
	 */
	public void testAddGroup() {
		saveMyGroups(Controller.getMyGroups());
	}
	/**
	 *测试所用
	 */
	public void testDelGroup() {
		saveMyGroups(Controller.getMyGroups());
	}
	/**
	 *测试所用
	 */
	public void testModifyGroup() {
		saveMyGroups(Controller.getMyGroups());
	}
	
	
}
