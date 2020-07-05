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
 * ��������б�����
 * @author wangzhen
 *
 */
public class MyFriendList{
	/**
	 * �����������
	 */
	private  Map<String, FriendNode> map = new HashMap<String, FriendNode>();
	/**
	 * ������ģ��
	 */
	private DefaultTreeModel model;
	/**
	 * ����������ʾ
	 */
	private FriendNode root;
	/**
	 * ������
	 */
	private QTree tree;
	/**
	 * ��ʼ��
	 */
	public MyFriendList() {
		// ȡ������
		for(FriendNode node : Controller.getAllFriendsFromFile()) {
			add(node);
		}
		
		model = new DefaultTreeModel(getNode());
		tree = new QTree(model, this);
		tree.setCellRenderer(new FriendNodeRenderer());
	}
	/**
	 * ���º����б���Ϣ
	 */
	public void update() {
		model.setRoot(getNode());
	}
	/**
	 * ��Ӻ�����Ϣ
	 * @param node Ŀ�����
	 */
	public void add(FriendNode node) {
		map.put(node.getUname(), node);
	}
	/**
	 * ɾ��������Ϣ
	 * @param node Ŀ�����
	 */
	public void delete(FriendNode node) {
		map.remove(node.getUname());
	}
	/**
	 * ȡ�����еĺ�������
	 * @return ����root�����º����б�
	 */
	public FriendNode getNode() {
		root = new FriendNode("�ҵĺ���");
		for(FriendNode node : map.values()) {
			root.add(node);
		}
		return root;
	}
	/**
	 * �����б��Ƿ����node����
	 * @param node Ŀ�����
	 * @return ���ذ������
	 */
	public boolean contains(FriendNode node) {
		return map.containsValue(node);
	}
	/**
	 * ���ú�������״̬
	 * @param name Ŀ���������
	 * @param state Ŀ��״̬
	 */
	public void setOnlineState(String name, boolean state) {
		if(map.get(name) != null) {
			map.get(name).setState(state);
		}
	}
	/**
	 * ȡ�ú����б���
	 * @return  ���غ����б���
	 */
	public QTree getTree() {
		return tree;
	}
	/**
	 * ȡ�ú����б���Ϣ
	 * @return �����б�
	 */
	public  Collection<FriendNode> getMyFriends() {
		return map.values();
	}

}
