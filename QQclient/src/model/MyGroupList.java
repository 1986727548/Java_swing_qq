package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultTreeModel;

import component.GroupNode;
import component.GroupNodeRenderer;
import component.QTree;
import controller.Controller;
/**
 * 管理群聊列表和群聊成员数据
 * @author wangzhen
 *
 */
public class MyGroupList{
	/**
	 * 保存群聊，群成员数据
	 */
	private  Map<GroupNode, DefaultListModel<String>> map = new HashMap<GroupNode, DefaultListModel<String>>();
	/**
	 * 群聊树模型
	 */
	private DefaultTreeModel model;
	/**
	 * 树根，不显示
	 */
	private GroupNode root;
	/**
	 * 群聊树
	 */
	private QTree tree;
	/**
	 * 初始化
	 */
	public MyGroupList() {
		// 取得数据
		for(GroupNode node : Controller.getAllGroupsFromFile()) {
			map.put(node, Controller.getAllGroupMembersFromFile(node));
		}
		model = new DefaultTreeModel(getNode());
		tree = new QTree(model, this);
		tree.setCellRenderer(new GroupNodeRenderer());
	}
	/**
	 * 更新群聊列表信息
	 */
	public void update() {
		model.setRoot(getNode());
	}
	/**
	 * 添加群聊信息
	 * @param node 目标群聊
	 */
	public void add(GroupNode node) {
		map.put(node, new DefaultListModel<String>());
	}
	/**
	 * 删除群聊信息
	 * @param node 目标群聊
	 */
	public void delete(GroupNode node) {
		map.remove(node);
	}
	/**
	 * 取到所有的群聊数据
	 * @return 返回root来更新群聊列表
	 */
	public GroupNode getNode() {
		root = new GroupNode("我的群聊", "----------------");
		for(GroupNode node : map.keySet()) {
			root.add(node);
		}
		return root;
	}
	/**
	 * 群聊列表是否包含node群
	 * @param node 目标群
	 * @return 返回包含与否
	 */
	public boolean contains(GroupNode node) {
		return map.containsKey(node);
	}
	/**
	 * 取得群聊列表树
	 * @return  返回群聊列表树
	 */
	public QTree getTree() {
		return tree;
	}
	/**
	 * 取得群聊列表信息
	 * @return 群聊列表
	 */
	public  Collection<GroupNode> getMyGroups(){
		return map.keySet();
	}
	/**
	 * 向群聊列表添加群聊成员
	 * @param node 目标群
	 * @param member 目标成员
	 */
	public  void addGroupMembers(GroupNode node, String member) {
		map.get(node).addElement(member);
	}
	/**
	 * 向群聊列表删除群聊成员
	 * @param node 目标群
	 * @param member 目标成员
	 */
	public  void delGroupMembers(GroupNode node, String member) {
		map.get(node).removeElement(member);
	}
	
	/**
	 * 得到node群的全部成员
	 * @param node 目标群
	 * @return 返回群成员列表
	 */
	public  DefaultListModel<String> getMyGroupMembers(GroupNode node){
		return map.get(node);
	}
	/**
	 * 更新群名字
	 * @param node 旧的群信息
	 * @param newGname 新的群名字
	 */
	public  void updateGroupName(GroupNode node, String newGname) {
		DefaultListModel<String> model = map.get(node);
		map.remove(node);
		map.put(new GroupNode(newGname, node.getOwner()), model);
		Controller.delGroupMemberFile(node);
	}

}
