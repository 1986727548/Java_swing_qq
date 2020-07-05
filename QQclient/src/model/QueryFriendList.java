package model;

import java.util.ArrayList;
import javax.swing.tree.DefaultTreeModel;

import component.FriendNode;
import component.FriendNodeRenderer;
import component.QTree;

/**
 * 管理查询好友列表数据
 * @author wangzhen
 *
 */
public class QueryFriendList{
	/**
	 * 保存好友数据
	 */
	private ArrayList<FriendNode> nodes;
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
	public QueryFriendList() {
		model = new DefaultTreeModel(getNode());
		tree = new QTree(model, this);
		tree.setCellRenderer(new FriendNodeRenderer());
	}
	/**
	 * 设置数据集合
	 * @param nodes 目标集合
	 */
	public void setData(ArrayList<FriendNode> nodes) {
		this.nodes = nodes;
	}
	/**
	 * 更新查询好友列表信息
	 */
	public void update() {
		model.setRoot(getNode());
	}
	/**
	 * 取到所有的查询好友数据
	 * @return 返回root来更新查询好友列表
	 */
	public FriendNode getNode() {
		root = new FriendNode("");
		if(nodes != null) {
			for(FriendNode node : nodes) {
				root.add(node);
			}
		}
		return root;
	}
	/**
	 * 取得查询好友列表树
	 * @return  返回查询好友列表树
	 */
	public QTree getTree() {
		return tree;
	}

}
