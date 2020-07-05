package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import component.FriendNode;
import component.FriendNodeRenderer;
import component.QTree;
import controller.Controller;

/**
 * 管理好友列表数据
 * @author wangzhen
 *
 */
public class MyFriendList{
	/**
	 * 保存好友数据
	 */
	private  Map<String, FriendNode> map = new HashMap<String, FriendNode>();
	/**
	 * 好友树模型
	 */
	private DefaultTreeModel model;
	/**
	 * 树根，不显示
	 */
	private FriendNode root;
	/**
	 * 好友树
	 */
	private QTree tree;
	/**
	 * 初始化
	 */
	public MyFriendList() {
		// 取得数据
		for(FriendNode node : Controller.getAllFriendsFromFile()) {
			add(node);
		}
		
		model = new DefaultTreeModel(getNode());
		tree = new QTree(model, this);
		tree.setCellRenderer(new FriendNodeRenderer());
	}
	/**
	 * 更新好友列表信息
	 */
	public void update() {
		model.setRoot(getNode());
	}
	/**
	 * 添加好友信息
	 * @param node 目标好友
	 */
	public void add(FriendNode node) {
		map.put(node.getUname(), node);
	}
	/**
	 * 删除好友信息
	 * @param node 目标好友
	 */
	public void delete(FriendNode node) {
		map.remove(node.getUname());
	}
	/**
	 * 取到所有的好友数据
	 * @return 返回root来更新好友列表
	 */
	public FriendNode getNode() {
		root = new FriendNode("我的好友");
		for(FriendNode node : map.values()) {
			root.add(node);
		}
		return root;
	}
	/**
	 * 好友列表是否包含node好友
	 * @param node 目标好友
	 * @return 返回包含与否
	 */
	public boolean contains(FriendNode node) {
		return map.containsValue(node);
	}
	/**
	 * 设置好友在线状态
	 * @param name 目标好友名字
	 * @param state 目标状态
	 */
	public void setOnlineState(String name, boolean state) {
		if(map.get(name) != null) {
			map.get(name).setState(state);
		}
	}
	/**
	 * 取得好友列表树
	 * @return  返回好友列表树
	 */
	public QTree getTree() {
		return tree;
	}
	/**
	 * 取得好友列表信息
	 * @return 好友列表
	 */
	public  Collection<FriendNode> getMyFriends() {
		return map.values();
	}

}
