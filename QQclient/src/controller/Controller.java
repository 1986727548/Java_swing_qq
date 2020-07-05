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
 * 用于调动各种服务
 * @author wangzhen
 *
 */
public class Controller {
	private static ServerService serverService = new ServerService();
	private static DataService dataService = new DataService();
	private static FileService fileService = new FileService();
	/**
	 * 和服务器连接
	 */
	public static void requestServerConnetion() {
		serverService.requestServerConnetion();
	}
	
	/**
	 * 向服务器请求登录
	 * @param uname 用户名
	 * @param psword 密码
	 * @return 返回登录结果（登录成功，登录失败，不可重复登录）
	 */
	public static String requestLogin(String uname, String psword) {
		return serverService.requestLogin(uname, psword);
	}
	
	/**
	 * 向服务器响应在线，由服务器想uname用户的好友列表转发
	 * @param uname 用户名
	 * @param myFriends 好友列表
	 */
	public static void responseOnline(String uname, Collection<FriendNode> myFriends) {
		serverService.responseOnline(uname, myFriends);
	}
	
	/**
	 * 向服务器响应不在线，由服务器想uname用户的好友列表转发
	 * @param uname 用户名
	 * @param myFriends 好友列表
	 */ 
	public static void responseNotOnline(String uname, Collection<FriendNode> myFriends) {
		serverService.responseNotOnline(uname, myFriends);
	}
	
	/**
	 * 向服务器请求注册
	 * @param uname 用户名
	 * @param psword 密码
	 * @return 返回注册结果
	 */
	public static boolean requestLogon(String uname, String psword) {
		return serverService.requestLogon(uname, psword);
	}
	
	/**
	 * 根据string来向服务器进行模糊查询好友
	 * @param string 查询字段
	 * @return 返回查询的好友列表
	 */
	public static ArrayList<FriendNode> requestQueryFriend(String string) {
		return serverService.requestQueryFriend(string);
	}
	
	/**
	 * 向服务器请求添加好友，由服务器转发到des用户
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 */
	public static void requestAddFriend(String ori, String des) {
		serverService.requestAddFriend(ori, des);
	}
	
	/**
	 * 向服务器响应已同意添加好友请求
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 */
	public static void responseAddFriend(String ori, String des) {
		serverService.responseAddFriend(ori, des);
	}
	
	/**
	 * 向服务器响应已删除该好友
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 */
	public static void responseDelFriend(String ori, String des) {
		serverService.responseDelFriend(ori, des);
	}
	
	/**
	 * 向服务器请求好友聊天，由服务器向des用户转发info
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 * @param info 聊天的信息
	 */
	public static void requestFriendChat(String ori, String des, String info) {
		serverService.requestFriendChat(ori, des, info);
	}
	
	/**
	 * 向服务器请求群聊天，由服务器向des用户的node群转发info， 一个一个用户来转发的
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 * @param node 群聊
	 * @param info 聊天的信息
	 */
	public static void requestGroupChat(String ori, String des, GroupNode node, String info) {
		serverService.requestGroupChat(ori, des, node, info);
	}
	
	/**
	 * 向服务器请求创建群聊
	 */
	public static void requestCreateGroup() {
		serverService.requestCreateGroup();
	}
	/**
	 * 向服务器请求修改群名称
	 * @param node 目标群
	 */
	public static void requestUpdateGroupName(GroupNode node) {
		serverService.requestUpdateGroupName(node);
	}
	/**
	 * 向服务器请求删除群聊
	 * @param node 目标群
	 */
	public static void requestDelGroup(GroupNode node) {
		serverService.requestDelGroup(node);
	}
	
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候向服务器请求获得好友信息
	 * @return 返回自己的好友列表
	 */
	public static ArrayList<FriendNode> firstGetFriendInfoRequest() {
		return serverService.firstGetFriendInfoRequest();
	}
	
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候向服务器请求获得群聊信息
	 * @return 返回自己的群聊列表
	 */
	public static ArrayList<GroupNode> firstGetGroupInfoRequest() {
		return serverService.firstGetGroupInfoRequest();
	}
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候向服务器请求获得群聊成员信息
	 * @param node 目标群
	 * @return 返回自己的群聊成员列表
	 */
	public static DefaultListModel<String> firstGetGroupMemberInfoRequest(GroupNode node){
		return serverService.firstGetGroupMemberInfoRequest(node);
	}
	
	/**
	 * 向服务器响应des用户已被邀请进这个node群聊了，然后服务器转发
	 * @param des 目标用户名
	 * @param node 目标群聊名
	 * @param members des用户的node群该有的members群成员
	 */
	public static void responseInviteFriendtoGroup(String des, GroupNode node, ArrayList<String> members) {
		serverService.responseInviteFriendtoGroup(des, node, members);
	}
	
	/**
	 * 向群成员响应已经踢掉该成员了
	 * @param des 目标用户名
	 * @param node 目标群聊名
	 * @param member 目标群成员
	 */
	public static void responseKnockFriendFromGroup(String des, GroupNode node, String member) {
		serverService.responseKnockFriendFromGroup(des, node, member);
	}
	
	/**
	 * 在数据库的group_info表里插入信息
	 * @param node 目标群聊名
	 * @param member 目标成员名
	 */
	public static void requestInviteGroupMembers(GroupNode node, String member) {
		serverService.requestInviteGroupMembers(node, member);
	}
	
	/**
	 * 在数据库的group_info表里删除信息
	 * @param node 目标群聊名
	 * @param member 目标成员名
	 */
	public static void requestDelGroupMembers(GroupNode node, String member) {
		serverService.requestDelGroupMembers(node, member);
	}
	
	/**
	 * 用udp通信接收来自服务器转发的信息
	 * @return 返回转发的信息包
	 */
	public static String receiver() {
		return serverService.receiver();
	}
	
	/**
	 * 通过向服务器发送关闭请求，来结束服务器线程，本身的线程，和closeLogin互斥
	 */
	public static void closeMain() {
		serverService.closeMain();
	}
	
	/**
	 * 通过向服务器发送关闭请求，来结束服务器线程，和closeMain互斥
	 */
	public static void closeLogin() {
		serverService.closeLogin();
	}
	
	/**
	 * 关闭客户端网络通信的接口
	 */
	public static void closeConnection() {
		serverService.closeConnection();
	}
	
	/**
	 * 保存用户修改的信息，例如添加/删除好友，进入/退出群聊
	 */
	public static void saveSource() {
		saveMyFriends(getMyFriends());
		saveMyGroups(getMyGroups());
		for(GroupNode node : getMyGroups()) {
			saveMyGroupMembers(node, getMyGroupMembers(node));
		}
	}
	
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候在本机创建必需的数据文件
	 */
	public static void firstLoginCreateNeededFile() {
		fileService.firstLoginCreateNeededFile();
	}
	
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候在本机创建必需的群聊成员文件
	 * @param node 目标群聊
	 */
	public static void firstCreateGroupMemberFile(GroupNode node) {
		fileService.firstCreateGroupMemberFile(node);
	}
	/**
	 * 当该群聊不存在时，把对应的群成员文件删除
	 * @param node 目标群聊
	 */
	public static void delGroupMemberFile(GroupNode node){
		fileService.delGroupMemberFile(node);
		System.out.println("exit delGroupMemberFile");
	}
	
	/**
	 * 初始化必要的数据，例如好友列表，群聊列表信息
	 */
	public static void initDataService() {
		dataService.initDataService();
	}
	
	/**
	 * 设置好友列表的在线状态
	 * @param name 目标好友
	 * @param state 目标状态
	 */
	public static void setOnlineState(String name, boolean state) {
		dataService.setOnlineState(name, state);
	}
	
	/**
	 * 向本机文件存储好友列表信息
	 * @param res 好友列表
	 */
	public static void saveMyFriends(Collection<FriendNode> res) {
		dataService.saveMyFriends(res);
	}
	
	/**
	 * 向本机文件存储群聊列表信息
	 * @param res 群聊列表
	 */
	public static void saveMyGroups(Collection<GroupNode> res) {
		dataService.saveMyGroups(res);
	}
	
	/**
	 * 向本机文件存储群聊成员列表信息
	 * @param node 目标群聊
	 * @param res 群聊成员列表
	 */
	public static void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res) {
		dataService.saveMyGroupMembers(node, res);
	}
	
	/**
	 * 取得好友列表信息
	 * @return 好友列表
	 */
	public static Collection<FriendNode> getMyFriends(){
		return dataService.getMyFriends();
	}
	
	/**
	 * 取得群聊列表信息
	 * @return 群聊列表
	 */
	public static Collection<GroupNode> getMyGroups() {
		return dataService.getMyGroups();
	}
	
	/**
	 * 从本机文件中取得好友列表
	 * @return 好友列表
	 */
	public static ArrayList<FriendNode> getAllFriendsFromFile() {
		return dataService.getAllFriendsFromFile();
	}
	
	/**
	 * 从本机文件中取得群聊列表
	 * @return 群聊列表
	 */
	public static ArrayList<GroupNode> getAllGroupsFromFile() {
		return dataService.getAllGroupsFromFile();
	}
	
	/**
	 * 从本机文件中取得群聊成员列表
	 * @param node 目标群聊
	 * @return 群聊成员列表
	 */
	public static DefaultListModel<String> getAllGroupMembersFromFile(GroupNode node){
		return dataService.getAllGroupMembersFromFile(node);
	}
	
	/**
	 * 向好友聊天框管理集合添加好友聊天框
	 * @param uname 目标好友
	 * @param chatFrame 目标聊天框
	 */
	public static void addFriendChatFrame(String uname, FriendChatFrame chatFrame) {
		dataService.addFriendChatFrame(uname, chatFrame);
	}
	
	/**
	 *  向好友聊天框管理集合取得好友聊天框
	 * @param uname 目标好友
	 * @return 返回uname好友聊天框
	 */
	public static FriendChatFrame getFriendChatFrame(String uname) {
		return dataService.getFriendChatFrame(uname);
	}
	
	/**
	 * 向好友聊天框管理集合删除好友聊天框
	 * @param uname 目标好友
	 */
	public static void delFriendChatFrame(String uname) {
		dataService.delFriendChatFrame(uname);
	}
	
	/**
	 * 向好友聊天框添加聊天信息
	 * @param uname 目标好友
	 * @param info 聊天信息
	 */
	public static void addFriendChatInfo(String uname, String info) {
		dataService.addFriendChatInfo(uname, info);
	}
	
	/**
	 * 向群聊聊天框管理集合添加群聊聊天框
	 * @param node 目标群聊
	 * @param chatFrame 目标聊天框
	 */
	public static void addGroupChatFrame(GroupNode node, GroupChatFrame chatFrame) {
		dataService.addGroupChatFrame(node, chatFrame);
	}
	
	/**
	 * 向群聊聊天框管理集合取得群聊聊天框
	 * @param node 目标群聊
	 * @return 返回node群聊天框
	 */
	public static GroupChatFrame getGroupChatFrame(GroupNode node) {
		return dataService.getGroupChatFrame(node);
	}
	
	/**
	 * 向群聊聊天框管理集合删除群聊聊天框
	 * @param node 目标群聊
	 */
	public static void delGroupChatFrame(GroupNode node) {
		dataService.delGroupChatFrame(node);
	}
	
	/**
	 * 向群聊聊天框添加聊天信息
	 * @param node 目标群聊
	 * @param info 聊天信息
	 */
	public static void addGroupChatInfo(GroupNode node, String info) {
		dataService.addGroupChatInfo(node, info);
	}
	
	/**
	 * 取得好友列表信息树
	 * @return 返回好友列表信息树
	 */
	public static JTree getMyFriendList() {
		return dataService.getMyFriendList();
	}
	
	/**
	 * 取得查询好友列表信息树
	 * @return 返回查询好友列表信息树
	 */
	public static JTree getQueryFriendList() {
		return dataService.getQueryFriendList();
	}
	
	/**
	 * 取得群聊列表信息树
	 * @return 返回群聊列表信息树
	 */
	public static JTree getMyGroupList() {
		return dataService.getMyGroupList();
	}
	
	/**
	 * 更新查询好友列表树的数据
	 * @param nodes 查询好友列表
	 */
	public static void updateQueryFriendList(ArrayList<FriendNode> nodes) {
		dataService.updateQueryFriendList(nodes);
	}
	
	/**
	 * 向好友列表添加好友信息
	 * @param node 目标好友
	 */
	public static void addFriend(FriendNode node) {
		dataService.addFriend(node);
	}
	
	/**
	 * 向好友列表删除好友信息
	 * @param node 目标好友
	 */
	public static void deleteFriend(FriendNode node) {
		dataService.deleteFriend(node);
	}

	/**
	 * 向node群添加群成员
	 * @param node 目标群
	 * @param member 目标群成员
	 */
	public static void addGroupMembers(GroupNode node, String member) {
		dataService.addGroupMembers(node, member);
	}
	
	/**
	 * 向node群删除群成员
	 * @param node 目标群
	 * @param member 目标群成员
	 */
	public static void delGroupMembers(GroupNode node, String member) {
		dataService.delGroupMembers(node, member);
	}
	
	/**
	 * 得到node群的全部群成员
	 * @param node 目标群
	 * @return 返回node群的群成员集合
	 */
	public static DefaultListModel<String> getMyGroupMembers(GroupNode node){
		return dataService.getMyGroupMembers(node);
	}
	
	/**
	 * 向群聊列表添加群聊
	 * @param node 目标群
	 */
	public static void addGroup(GroupNode node) {
		if(getMyGroupMembers(node) == null) {
			dataService.addGroup(node);
			addGroupMembers(node, node.getOwner());
		}
	}
	
	/**
	 * 向群聊列表删除群聊
	 * @param node 目标群
	 */
	public static void deleteGroup(GroupNode node) {
		if(getMyGroupMembers(node) != null) {
			dataService.deleteGroup(node);
		}
	}

	/**
	 * 查询好友列表是否存在node好友
	 * @param node 目标好友
	 * @return 返回存在与否
	 */
	public static boolean myFriendListContains(FriendNode node) {
		return dataService.myFriendListContains(node);
	}

	/**
	 * 查询群聊列表是否存在node群
	 * @param node 目标群
	 * @return 返回存在与否
	 */
	public static boolean myGroupListContains(GroupNode node) {
		return dataService.myGroupListContains(node);
	}
	
	/**
	 * 更新群名字
	 * @param node 旧的群信息
	 * @param newGname 新的群名字
	 */
	public static void updateGroupName(GroupNode node, String newGname) {
		dataService.updateGroupName(node, newGname);
	}

}
