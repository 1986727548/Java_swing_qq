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
 * ����Ⱥ���б��Ⱥ�ĳ�Ա����
 * @author wangzhen
 *
 */
public class MyGroupList{
	/**
	 * ����Ⱥ�ģ�Ⱥ��Ա����
	 */
	private  Map<GroupNode, DefaultListModel<String>> map = new HashMap<GroupNode, DefaultListModel<String>>();
	/**
	 * Ⱥ����ģ��
	 */
	private DefaultTreeModel model;
	/**
	 * ����������ʾ
	 */
	private GroupNode root;
	/**
	 * Ⱥ����
	 */
	private QTree tree;
	/**
	 * ��ʼ��
	 */
	public MyGroupList() {
		// ȡ������
		for(GroupNode node : Controller.getAllGroupsFromFile()) {
			map.put(node, Controller.getAllGroupMembersFromFile(node));
		}
		model = new DefaultTreeModel(getNode());
		tree = new QTree(model, this);
		tree.setCellRenderer(new GroupNodeRenderer());
	}
	/**
	 * ����Ⱥ���б���Ϣ
	 */
	public void update() {
		model.setRoot(getNode());
	}
	/**
	 * ���Ⱥ����Ϣ
	 * @param node Ŀ��Ⱥ��
	 */
	public void add(GroupNode node) {
		map.put(node, new DefaultListModel<String>());
	}
	/**
	 * ɾ��Ⱥ����Ϣ
	 * @param node Ŀ��Ⱥ��
	 */
	public void delete(GroupNode node) {
		map.remove(node);
	}
	/**
	 * ȡ�����е�Ⱥ������
	 * @return ����root������Ⱥ���б�
	 */
	public GroupNode getNode() {
		root = new GroupNode("�ҵ�Ⱥ��", "----------------");
		for(GroupNode node : map.keySet()) {
			root.add(node);
		}
		return root;
	}
	/**
	 * Ⱥ���б��Ƿ����nodeȺ
	 * @param node Ŀ��Ⱥ
	 * @return ���ذ������
	 */
	public boolean contains(GroupNode node) {
		return map.containsKey(node);
	}
	/**
	 * ȡ��Ⱥ���б���
	 * @return  ����Ⱥ���б���
	 */
	public QTree getTree() {
		return tree;
	}
	/**
	 * ȡ��Ⱥ���б���Ϣ
	 * @return Ⱥ���б�
	 */
	public  Collection<GroupNode> getMyGroups(){
		return map.keySet();
	}
	/**
	 * ��Ⱥ���б����Ⱥ�ĳ�Ա
	 * @param node Ŀ��Ⱥ
	 * @param member Ŀ���Ա
	 */
	public  void addGroupMembers(GroupNode node, String member) {
		map.get(node).addElement(member);
	}
	/**
	 * ��Ⱥ���б�ɾ��Ⱥ�ĳ�Ա
	 * @param node Ŀ��Ⱥ
	 * @param member Ŀ���Ա
	 */
	public  void delGroupMembers(GroupNode node, String member) {
		map.get(node).removeElement(member);
	}
	
	/**
	 * �õ�nodeȺ��ȫ����Ա
	 * @param node Ŀ��Ⱥ
	 * @return ����Ⱥ��Ա�б�
	 */
	public  DefaultListModel<String> getMyGroupMembers(GroupNode node){
		return map.get(node);
	}
	/**
	 * ����Ⱥ����
	 * @param node �ɵ�Ⱥ��Ϣ
	 * @param newGname �µ�Ⱥ����
	 */
	public  void updateGroupName(GroupNode node, String newGname) {
		DefaultListModel<String> model = map.get(node);
		map.remove(node);
		map.put(new GroupNode(newGname, node.getOwner()), model);
		Controller.delGroupMemberFile(node);
	}

}
