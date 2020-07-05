package model;

import java.util.ArrayList;
import javax.swing.tree.DefaultTreeModel;

import component.FriendNode;
import component.FriendNodeRenderer;
import component.QTree;

/**
 * �����ѯ�����б�����
 * @author wangzhen
 *
 */
public class QueryFriendList{
	/**
	 * �����������
	 */
	private ArrayList<FriendNode> nodes;
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
	public QueryFriendList() {
		model = new DefaultTreeModel(getNode());
		tree = new QTree(model, this);
		tree.setCellRenderer(new FriendNodeRenderer());
	}
	/**
	 * �������ݼ���
	 * @param nodes Ŀ�꼯��
	 */
	public void setData(ArrayList<FriendNode> nodes) {
		this.nodes = nodes;
	}
	/**
	 * ���²�ѯ�����б���Ϣ
	 */
	public void update() {
		model.setRoot(getNode());
	}
	/**
	 * ȡ�����еĲ�ѯ��������
	 * @return ����root�����²�ѯ�����б�
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
	 * ȡ�ò�ѯ�����б���
	 * @return  ���ز�ѯ�����б���
	 */
	public QTree getTree() {
		return tree;
	}

}
