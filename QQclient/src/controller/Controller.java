package controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JTree;

import component.FriendNode;
import component.GroupNode;
import service.DataService;
import service.FileService;
import service.ServerService;
import view.FriendChatFrame;
import view.GroupChatFrame;

/**
 * ���ڵ������ַ���
 * @author wangzhen
 *
 */
public class Controller {
	private static ServerService serverService = new ServerService();
	private static DataService dataService = new DataService();
	private static FileService fileService = new FileService();
	/**
	 * �ͷ���������
	 */
	public static void requestServerConnetion() {
		serverService.requestServerConnetion();
	}
	
	/**
	 * ������������¼
	 * @param uname �û���
	 * @param psword ����
	 * @return ���ص�¼�������¼�ɹ�����¼ʧ�ܣ������ظ���¼��
	 */
	public static String requestLogin(String uname, String psword) {
		return serverService.requestLogin(uname, psword);
	}
	
	/**
	 * ���������Ӧ���ߣ��ɷ�������uname�û��ĺ����б�ת��
	 * @param uname �û���
	 * @param myFriends �����б�
	 */
	public static void responseOnline(String uname, Collection<FriendNode> myFriends) {
		serverService.responseOnline(uname, myFriends);
	}
	
	/**
	 * ���������Ӧ�����ߣ��ɷ�������uname�û��ĺ����б�ת��
	 * @param uname �û���
	 * @param myFriends �����б�
	 */ 
	public static void responseNotOnline(String uname, Collection<FriendNode> myFriends) {
		serverService.responseNotOnline(uname, myFriends);
	}
	
	/**
	 * �����������ע��
	 * @param uname �û���
	 * @param psword ����
	 * @return ����ע����
	 */
	public static boolean requestLogon(String uname, String psword) {
		return serverService.requestLogon(uname, psword);
	}
	
	/**
	 * ����string�������������ģ����ѯ����
	 * @param string ��ѯ�ֶ�
	 * @return ���ز�ѯ�ĺ����б�
	 */
	public static ArrayList<FriendNode> requestQueryFriend(String string) {
		return serverService.requestQueryFriend(string);
	}
	
	/**
	 * �������������Ӻ��ѣ��ɷ�����ת����des�û�
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 */
	public static void requestAddFriend(String ori, String des) {
		serverService.requestAddFriend(ori, des);
	}
	
	/**
	 * ���������Ӧ��ͬ����Ӻ�������
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 */
	public static void responseAddFriend(String ori, String des) {
		serverService.responseAddFriend(ori, des);
	}
	
	/**
	 * ���������Ӧ��ɾ���ú���
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 */
	public static void responseDelFriend(String ori, String des) {
		serverService.responseDelFriend(ori, des);
	}
	
	/**
	 * �����������������죬�ɷ�������des�û�ת��info
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 * @param info �������Ϣ
	 */
	public static void requestFriendChat(String ori, String des, String info) {
		serverService.requestFriendChat(ori, des, info);
	}
	
	/**
	 * �����������Ⱥ���죬�ɷ�������des�û���nodeȺת��info�� һ��һ���û���ת����
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 * @param node Ⱥ��
	 * @param info �������Ϣ
	 */
	public static void requestGroupChat(String ori, String des, GroupNode node, String info) {
		serverService.requestGroupChat(ori, des, node, info);
	}
	
	/**
	 * ����������󴴽�Ⱥ��
	 */
	public static void requestCreateGroup() {
		serverService.requestCreateGroup();
	}
	/**
	 * ������������޸�Ⱥ����
	 * @param node Ŀ��Ⱥ
	 */
	public static void requestUpdateGroupName(GroupNode node) {
		serverService.requestUpdateGroupName(node);
	}
	/**
	 * �����������ɾ��Ⱥ��
	 * @param node Ŀ��Ⱥ
	 */
	public static void requestDelGroup(GroupNode node) {
		serverService.requestDelGroup(node);
	}
	
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ��������������ú�����Ϣ
	 * @return �����Լ��ĺ����б�
	 */
	public static ArrayList<FriendNode> firstGetFriendInfoRequest() {
		return serverService.firstGetFriendInfoRequest();
	}
	
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���������������Ⱥ����Ϣ
	 * @return �����Լ���Ⱥ���б�
	 */
	public static ArrayList<GroupNode> firstGetGroupInfoRequest() {
		return serverService.firstGetGroupInfoRequest();
	}
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���������������Ⱥ�ĳ�Ա��Ϣ
	 * @param node Ŀ��Ⱥ
	 * @return �����Լ���Ⱥ�ĳ�Ա�б�
	 */
	public static DefaultListModel<String> firstGetGroupMemberInfoRequest(GroupNode node){
		return serverService.firstGetGroupMemberInfoRequest(node);
	}
	
	/**
	 * ���������Ӧdes�û��ѱ���������nodeȺ���ˣ�Ȼ�������ת��
	 * @param des Ŀ���û���
	 * @param node Ŀ��Ⱥ����
	 * @param members des�û���nodeȺ���е�membersȺ��Ա
	 */
	public static void responseInviteFriendtoGroup(String des, GroupNode node, ArrayList<String> members) {
		serverService.responseInviteFriendtoGroup(des, node, members);
	}
	
	/**
	 * ��Ⱥ��Ա��Ӧ�Ѿ��ߵ��ó�Ա��
	 * @param des Ŀ���û���
	 * @param node Ŀ��Ⱥ����
	 * @param member Ŀ��Ⱥ��Ա
	 */
	public static void responseKnockFriendFromGroup(String des, GroupNode node, String member) {
		serverService.responseKnockFriendFromGroup(des, node, member);
	}
	
	/**
	 * �����ݿ��group_info���������Ϣ
	 * @param node Ŀ��Ⱥ����
	 * @param member Ŀ���Ա��
	 */
	public static void requestInviteGroupMembers(GroupNode node, String member) {
		serverService.requestInviteGroupMembers(node, member);
	}
	
	/**
	 * �����ݿ��group_info����ɾ����Ϣ
	 * @param node Ŀ��Ⱥ����
	 * @param member Ŀ���Ա��
	 */
	public static void requestDelGroupMembers(GroupNode node, String member) {
		serverService.requestDelGroupMembers(node, member);
	}
	
	/**
	 * ��udpͨ�Ž������Է�����ת������Ϣ
	 * @return ����ת������Ϣ��
	 */
	public static String receiver() {
		return serverService.receiver();
	}
	
	/**
	 * ͨ������������͹ر������������������̣߳�������̣߳���closeLogin����
	 */
	public static void closeMain() {
		serverService.closeMain();
	}
	
	/**
	 * ͨ������������͹ر������������������̣߳���closeMain����
	 */
	public static void closeLogin() {
		serverService.closeLogin();
	}
	
	/**
	 * �رտͻ�������ͨ�ŵĽӿ�
	 */
	public static void closeConnection() {
		serverService.closeConnection();
	}
	
	/**
	 * �����û��޸ĵ���Ϣ���������/ɾ�����ѣ�����/�˳�Ⱥ��
	 */
	public static void saveSource() {
		saveMyFriends(getMyFriends());
		saveMyGroups(getMyGroups());
		for(GroupNode node : getMyGroups()) {
			saveMyGroupMembers(node, getMyGroupMembers(node));
		}
	}
	
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���ڱ�����������������ļ�
	 */
	public static void firstLoginCreateNeededFile() {
		fileService.firstLoginCreateNeededFile();
	}
	
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���ڱ������������Ⱥ�ĳ�Ա�ļ�
	 * @param node Ŀ��Ⱥ��
	 */
	public static void firstCreateGroupMemberFile(GroupNode node) {
		fileService.firstCreateGroupMemberFile(node);
	}
	/**
	 * ����Ⱥ�Ĳ�����ʱ���Ѷ�Ӧ��Ⱥ��Ա�ļ�ɾ��
	 * @param node Ŀ��Ⱥ��
	 */
	public static void delGroupMemberFile(GroupNode node){
		fileService.delGroupMemberFile(node);
		System.out.println("exit delGroupMemberFile");
	}
	
	/**
	 * ��ʼ����Ҫ�����ݣ���������б�Ⱥ���б���Ϣ
	 */
	public static void initDataService() {
		dataService.initDataService();
	}
	
	/**
	 * ���ú����б������״̬
	 * @param name Ŀ�����
	 * @param state Ŀ��״̬
	 */
	public static void setOnlineState(String name, boolean state) {
		dataService.setOnlineState(name, state);
	}
	
	/**
	 * �򱾻��ļ��洢�����б���Ϣ
	 * @param res �����б�
	 */
	public static void saveMyFriends(Collection<FriendNode> res) {
		dataService.saveMyFriends(res);
	}
	
	/**
	 * �򱾻��ļ��洢Ⱥ���б���Ϣ
	 * @param res Ⱥ���б�
	 */
	public static void saveMyGroups(Collection<GroupNode> res) {
		dataService.saveMyGroups(res);
	}
	
	/**
	 * �򱾻��ļ��洢Ⱥ�ĳ�Ա�б���Ϣ
	 * @param node Ŀ��Ⱥ��
	 * @param res Ⱥ�ĳ�Ա�б�
	 */
	public static void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res) {
		dataService.saveMyGroupMembers(node, res);
	}
	
	/**
	 * ȡ�ú����б���Ϣ
	 * @return �����б�
	 */
	public static Collection<FriendNode> getMyFriends(){
		return dataService.getMyFriends();
	}
	
	/**
	 * ȡ��Ⱥ���б���Ϣ
	 * @return Ⱥ���б�
	 */
	public static Collection<GroupNode> getMyGroups() {
		return dataService.getMyGroups();
	}
	
	/**
	 * �ӱ����ļ���ȡ�ú����б�
	 * @return �����б�
	 */
	public static ArrayList<FriendNode> getAllFriendsFromFile() {
		return dataService.getAllFriendsFromFile();
	}
	
	/**
	 * �ӱ����ļ���ȡ��Ⱥ���б�
	 * @return Ⱥ���б�
	 */
	public static ArrayList<GroupNode> getAllGroupsFromFile() {
		return dataService.getAllGroupsFromFile();
	}
	
	/**
	 * �ӱ����ļ���ȡ��Ⱥ�ĳ�Ա�б�
	 * @param node Ŀ��Ⱥ��
	 * @return Ⱥ�ĳ�Ա�б�
	 */
	public static DefaultListModel<String> getAllGroupMembersFromFile(GroupNode node){
		return dataService.getAllGroupMembersFromFile(node);
	}
	
	/**
	 * �����������������Ӻ��������
	 * @param uname Ŀ�����
	 * @param chatFrame Ŀ�������
	 */
	public static void addFriendChatFrame(String uname, FriendChatFrame chatFrame) {
		dataService.addFriendChatFrame(uname, chatFrame);
	}
	
	/**
	 *  ���������������ȡ�ú��������
	 * @param uname Ŀ�����
	 * @return ����uname���������
	 */
	public static FriendChatFrame getFriendChatFrame(String uname) {
		return dataService.getFriendChatFrame(uname);
	}
	
	/**
	 * ���������������ɾ�����������
	 * @param uname Ŀ�����
	 */
	public static void delFriendChatFrame(String uname) {
		dataService.delFriendChatFrame(uname);
	}
	
	/**
	 * �������������������Ϣ
	 * @param uname Ŀ�����
	 * @param info ������Ϣ
	 */
	public static void addFriendChatInfo(String uname, String info) {
		dataService.addFriendChatInfo(uname, info);
	}
	
	/**
	 * ��Ⱥ���������������Ⱥ�������
	 * @param node Ŀ��Ⱥ��
	 * @param chatFrame Ŀ�������
	 */
	public static void addGroupChatFrame(GroupNode node, GroupChatFrame chatFrame) {
		dataService.addGroupChatFrame(node, chatFrame);
	}
	
	/**
	 * ��Ⱥ������������ȡ��Ⱥ�������
	 * @param node Ŀ��Ⱥ��
	 * @return ����nodeȺ�����
	 */
	public static GroupChatFrame getGroupChatFrame(GroupNode node) {
		return dataService.getGroupChatFrame(node);
	}
	
	/**
	 * ��Ⱥ������������ɾ��Ⱥ�������
	 * @param node Ŀ��Ⱥ��
	 */
	public static void delGroupChatFrame(GroupNode node) {
		dataService.delGroupChatFrame(node);
	}
	
	/**
	 * ��Ⱥ����������������Ϣ
	 * @param node Ŀ��Ⱥ��
	 * @param info ������Ϣ
	 */
	public static void addGroupChatInfo(GroupNode node, String info) {
		dataService.addGroupChatInfo(node, info);
	}
	
	/**
	 * ȡ�ú����б���Ϣ��
	 * @return ���غ����б���Ϣ��
	 */
	public static JTree getMyFriendList() {
		return dataService.getMyFriendList();
	}
	
	/**
	 * ȡ�ò�ѯ�����б���Ϣ��
	 * @return ���ز�ѯ�����б���Ϣ��
	 */
	public static JTree getQueryFriendList() {
		return dataService.getQueryFriendList();
	}
	
	/**
	 * ȡ��Ⱥ���б���Ϣ��
	 * @return ����Ⱥ���б���Ϣ��
	 */
	public static JTree getMyGroupList() {
		return dataService.getMyGroupList();
	}
	
	/**
	 * ���²�ѯ�����б���������
	 * @param nodes ��ѯ�����б�
	 */
	public static void updateQueryFriendList(ArrayList<FriendNode> nodes) {
		dataService.updateQueryFriendList(nodes);
	}
	
	/**
	 * ������б���Ӻ�����Ϣ
	 * @param node Ŀ�����
	 */
	public static void addFriend(FriendNode node) {
		dataService.addFriend(node);
	}
	
	/**
	 * ������б�ɾ��������Ϣ
	 * @param node Ŀ�����
	 */
	public static void deleteFriend(FriendNode node) {
		dataService.deleteFriend(node);
	}

	/**
	 * ��nodeȺ���Ⱥ��Ա
	 * @param node Ŀ��Ⱥ
	 * @param member Ŀ��Ⱥ��Ա
	 */
	public static void addGroupMembers(GroupNode node, String member) {
		dataService.addGroupMembers(node, member);
	}
	
	/**
	 * ��nodeȺɾ��Ⱥ��Ա
	 * @param node Ŀ��Ⱥ
	 * @param member Ŀ��Ⱥ��Ա
	 */
	public static void delGroupMembers(GroupNode node, String member) {
		dataService.delGroupMembers(node, member);
	}
	
	/**
	 * �õ�nodeȺ��ȫ��Ⱥ��Ա
	 * @param node Ŀ��Ⱥ
	 * @return ����nodeȺ��Ⱥ��Ա����
	 */
	public static DefaultListModel<String> getMyGroupMembers(GroupNode node){
		return dataService.getMyGroupMembers(node);
	}
	
	/**
	 * ��Ⱥ���б����Ⱥ��
	 * @param node Ŀ��Ⱥ
	 */
	public static void addGroup(GroupNode node) {
		if(getMyGroupMembers(node) == null) {
			dataService.addGroup(node);
			addGroupMembers(node, node.getOwner());
		}
	}
	
	/**
	 * ��Ⱥ���б�ɾ��Ⱥ��
	 * @param node Ŀ��Ⱥ
	 */
	public static void deleteGroup(GroupNode node) {
		if(getMyGroupMembers(node) != null) {
			dataService.deleteGroup(node);
		}
	}

	/**
	 * ��ѯ�����б��Ƿ����node����
	 * @param node Ŀ�����
	 * @return ���ش������
	 */
	public static boolean myFriendListContains(FriendNode node) {
		return dataService.myFriendListContains(node);
	}

	/**
	 * ��ѯȺ���б��Ƿ����nodeȺ
	 * @param node Ŀ��Ⱥ
	 * @return ���ش������
	 */
	public static boolean myGroupListContains(GroupNode node) {
		return dataService.myGroupListContains(node);
	}
	
	/**
	 * ����Ⱥ����
	 * @param node �ɵ�Ⱥ��Ϣ
	 * @param newGname �µ�Ⱥ����
	 */
	public static void updateGroupName(GroupNode node, String newGname) {
		dataService.updateGroupName(node, newGname);
	}

}
