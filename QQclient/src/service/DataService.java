package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JTree;

import component.FriendNode;
import component.GroupNode;
import dao.DaoFactory;
import dao.FriendDao;
import dao.GroupDao;
import model.MyFriendList;
import model.MyGroupList;
import model.QueryFriendList;
import view.FriendChatFrame;
import view.GroupChatFrame;

/**
 * ͨ������MyFriendList��QueryFriendList��MyGroupList��FriendDao��GroupDao��Щ��
 * ����������
 * @author wangzhen
 *
 */
public class DataService {
	/**
	 * �����б�
	 */
	private  MyFriendList myFriendList;
	/**
	 * ��ѯ�����б�
	 */
	private  QueryFriendList queryFriendList;
	/**
	 * Ⱥ���б�
	 */
	private  MyGroupList myGroupList;
	/**
	 * �����������
	 */
	private  HashMap<String, FriendChatFrame> fChatMap;
	/**
	 * Ⱥ���������
	 */
	private  HashMap<GroupNode, GroupChatFrame> gChatMap;
	/**
	 * �ٿغ����б�����
	 */
	private  FriendDao fDao = DaoFactory.getFriendDao();
	/**
	 * �ٿ�Ⱥ�ĺ�Ⱥ��Ա����
	 */
	private  GroupDao gDao = DaoFactory.getGroupDao();
	/**
	 * ��ʼ����Ҫ�����ݺͱ���
	 */
	public void initDataService() {
		fChatMap = new HashMap<String, FriendChatFrame>();
		gChatMap = new HashMap<GroupNode, GroupChatFrame>();
		myFriendList = new MyFriendList();
		queryFriendList = new QueryFriendList();
		myGroupList = new MyGroupList();
	}
	/**
	 * ȡ�ú����б���Ϣ��
	 * @return ���غ����б���Ϣ��
	 */
	public  JTree getMyFriendList() {
		if(myFriendList != null) {
			return myFriendList.getTree();
		}
		else {
			return null;
		}
	}
	/**
	 * ȡ�ò�ѯ�����б���Ϣ��
	 * @return ���ز�ѯ�����б���Ϣ��
	 */
	public  JTree getQueryFriendList() {
		if(queryFriendList != null) {
			return queryFriendList.getTree();
		}
		else {
			return null;
		}
	}
	/**
	 * ȡ��Ⱥ���б���Ϣ��
	 * @return ����Ⱥ���б���Ϣ��
	 */
	public  JTree getMyGroupList() {
		if(myGroupList != null) {
			return myGroupList.getTree();
		}else {
			return null;
		}
	}
	/**
	 * ���²�ѯ�����б���������
	 * @param nodes ��ѯ�����б�
	 */
	public  void updateQueryFriendList(ArrayList<FriendNode> nodes) {
		queryFriendList.setData(nodes);
		queryFriendList.update();
	}
	/**
	 * ������б���Ӻ�����Ϣ
	 * @param node Ŀ�����
	 */
	public  void addFriend(FriendNode node) {
		myFriendList.add(node);
		myFriendList.update();
	}
	/**
	 * ������б�ɾ��������Ϣ
	 * @param node Ŀ�����
	 */
	public  void deleteFriend(FriendNode node) {
		myFriendList.delete(node);
		myFriendList.update();
	}
	/**
	 * ��Ⱥ���б����Ⱥ��
	 * @param node Ŀ��Ⱥ
	 */
	public  void addGroup(GroupNode node) {
		myGroupList.add(node);
		myGroupList.update();
	}
	/**
	 * ��Ⱥ���б�ɾ��Ⱥ��
	 * @param node Ŀ��Ⱥ
	 */
	public  void deleteGroup(GroupNode node) {
		myGroupList.delete(node);
		myGroupList.update();
	}
	/**
	 * ��ѯ�����б��Ƿ����node����
	 * @param node Ŀ�����
	 * @return ���ش������
	 */
	public  boolean myFriendListContains(FriendNode node) {
		return myFriendList.contains(node);
	}
	/**
	 * ��ѯȺ���б��Ƿ����nodeȺ
	 * @param node Ŀ��Ⱥ
	 * @return ���ش������
	 */
	public  boolean myGroupListContains(GroupNode node) {
		return myGroupList.contains(node);
	}
	/**
	 * ȡ�ú����б���Ϣ
	 * @return �����б�
	 */
	public  Collection<FriendNode> getMyFriends(){
		return myFriendList.getMyFriends();
	}
	/**
	 * ���ú����б������״̬
	 * @param name Ŀ�����
	 * @param state Ŀ��״̬
	 */
	public  void setOnlineState(String name, boolean state) {
		myFriendList.setOnlineState(name, state);
	}
	/**
	 * ȡ��Ⱥ���б���Ϣ
	 * @return Ⱥ���б�
	 */
	public  Collection<GroupNode> getMyGroups() {
		return myGroupList.getMyGroups();
	}
	/**
	 * �õ�nodeȺ��ȫ��Ⱥ��Ա
	 * @param node Ŀ��Ⱥ
	 * @return ����nodeȺ��Ⱥ��Ա����
	 */
	public  DefaultListModel<String> getMyGroupMembers(GroupNode node){
		return myGroupList.getMyGroupMembers(node);
	}
	
	/**
	 * ��nodeȺ���Ⱥ��Ա
	 * @param node Ŀ��Ⱥ
	 * @param member Ŀ��Ⱥ��Ա
	 */
	public  void addGroupMembers(GroupNode node, String member) {
		myGroupList.addGroupMembers(node, member);
	}
	/**
	 * ��nodeȺɾ��Ⱥ��Ա
	 * @param node Ŀ��Ⱥ
	 * @param member Ŀ��Ⱥ��Ա
	 */
	public  void delGroupMembers(GroupNode node, String member) {
		myGroupList.delGroupMembers(node, member);
	}
	/**
	 * �򱾻��ļ��洢�����б���Ϣ
	 * @param res �����б�
	 */
	public  void saveMyFriends(Collection<FriendNode> res) {
		fDao.saveMyFriends(res);
	}
	/**
	 * �򱾻��ļ��洢Ⱥ���б���Ϣ
	 * @param res Ⱥ���б�
	 */
	public  void saveMyGroups(Collection<GroupNode> res) {
		gDao.saveMyGroups(res);
	}
	/**
	 * �򱾻��ļ��洢Ⱥ�ĳ�Ա�б���Ϣ
	 * @param node Ŀ��Ⱥ��
	 * @param res Ⱥ�ĳ�Ա�б�
	 */
	public  void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res) {
		gDao.saveMyGroupMembers(node, res);
	}
	/**
	 * �ӱ����ļ���ȡ�ú����б�
	 * @return �����б�
	 */
	public  ArrayList<FriendNode> getAllFriendsFromFile() {
		return fDao.getAllFriends();
	}
	/**
	 * �ӱ����ļ���ȡ��Ⱥ���б�
	 * @return Ⱥ���б�
	 */
	public  ArrayList<GroupNode> getAllGroupsFromFile() {
		return gDao.getAllGroups();
	}
	/**
	 * �ӱ����ļ���ȡ��Ⱥ�ĳ�Ա�б�
	 * @param node Ŀ��Ⱥ��
	 * @return Ⱥ�ĳ�Ա�б�
	 */
	public  DefaultListModel<String> getAllGroupMembersFromFile(GroupNode node){
		return gDao.getAllGroupMembers(node);
	}
	/**
	 * �����������������Ӻ��������
	 * @param uname Ŀ�����
	 * @param chatFrame Ŀ�������
	 */
	public  void addFriendChatFrame(String uname, FriendChatFrame chatFrame) {
		if(fChatMap.get(uname) == null) {
			fChatMap.put(uname, chatFrame);
		}
	}
	/**
	 *  ���������������ȡ�ú��������
	 * @param uname Ŀ�����
	 * @return ����uname���������
	 */
	public  FriendChatFrame getFriendChatFrame(String uname) {
		return fChatMap.get(uname);
	}
	/**
	 * ���������������ɾ�����������
	 * @param uname Ŀ�����
	 */
	public  void delFriendChatFrame(String uname) {
		fChatMap.remove(uname);
	}
	/**
	 * �������������������Ϣ
	 * @param uname Ŀ�����
	 * @param info ������Ϣ
	 */
	public  void addFriendChatInfo(String uname, String info) {
		if(fChatMap.get(uname) == null) {
			addFriendChatFrame(uname, new FriendChatFrame(uname));
		}
		fChatMap.get(uname).append(info);
	}
	/**
	 * ��Ⱥ���������������Ⱥ�������
	 * @param node Ŀ��Ⱥ��
	 * @param chatFrame Ŀ�������
	 */
	public  void addGroupChatFrame(GroupNode node, GroupChatFrame chatFrame) {
		if(gChatMap.get(node) == null) {
			gChatMap.put(node, chatFrame);
		}
	}
	/**
	 * ��Ⱥ������������ȡ��Ⱥ�������
	 * @param node Ŀ��Ⱥ��
	 * @return ����nodeȺ�����
	 */
	public  GroupChatFrame getGroupChatFrame(GroupNode node) {
		return gChatMap.get(node);
	}
	/**
	 * ��Ⱥ������������ɾ��Ⱥ�������
	 * @param node Ŀ��Ⱥ��
	 */
	public  void delGroupChatFrame(GroupNode node) {
		if(gChatMap.get(node) != null && gChatMap.get(node).isVisible()) {
			gChatMap.get(node).dispose();
		}
		gChatMap.remove(node);
	}
	/**
	 * ��Ⱥ����������������Ϣ
	 * @param node Ŀ��Ⱥ��
	 * @param info ������Ϣ
	 */
	public  void addGroupChatInfo(GroupNode node, String info) {
		if(gChatMap.get(node) == null) {
			addGroupChatFrame(node, new GroupChatFrame(node));
		}
		gChatMap.get(node).append(info);
	}
	
	/**
	 * ����Ⱥ����
	 * @param node �ɵ�Ⱥ��Ϣ
	 * @param newGname �µ�Ⱥ����
	 */
	public  void updateGroupName(GroupNode node, String newGname) {
		myGroupList.updateGroupName(node, newGname);
		myGroupList.update();
		GroupChatFrame chatFrame = getGroupChatFrame(node);
		if(chatFrame != null) {
			gChatMap.remove(node);
			addGroupChatFrame(new GroupNode(newGname, node.getOwner()), chatFrame);
			chatFrame.setTitle(new GroupNode(newGname, node.getOwner()));
		}
	}


}
