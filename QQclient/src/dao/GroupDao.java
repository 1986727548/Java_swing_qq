package dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;

import component.GroupNode;

/**
 * 操控群聊和群成员数据
 * @author wangzhen
 *
 */
public interface GroupDao {
	/**
	 * 从本机文件中取得群聊列表
	 * @return 群聊列表
	 */
	public ArrayList<GroupNode> getAllGroups();
	/**
	 * 从本机文件中取得群聊成员列表
	 * @param node 目标群聊
	 * @return 群聊成员列表
	 */
	public DefaultListModel<String> getAllGroupMembers(GroupNode node);
	/**
	 * 向本机文件存储群聊列表信息
	 * @param res 群聊列表
	 */
	public void saveMyGroups(Collection<GroupNode> res);
	/**
	 * 向本机文件存储群聊成员列表信息
	 * @param node 目标群聊
	 * @param res 群聊成员列表
	 */
	public void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res);
}
