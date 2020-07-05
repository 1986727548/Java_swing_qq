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
 * 通过调用MyFriendList，QueryFriendList，MyGroupList，FriendDao，GroupDao这些类
 * 来操作数据
 * @author wangzhen
 *
 */
public class DataService {
	/**
	 * 好友列表
	 */
	private  MyFriendList myFriendList;
	/**
	 * 查询好友列表
	 */
	private  QueryFriendList queryFriendList;
	/**
	 * 群聊列表
	 */
	private  MyGroupList myGroupList;
	/**
	 * 好友聊天管理集
	 */
	private  HashMap<String, FriendChatFrame> fChatMap;
	/**
	 * 群聊聊天管理集
	 */
	private  HashMap<GroupNode, GroupChatFrame> gChatMap;
	/**
	 * 操控好友列表数据
	 */
	private  FriendDao fDao = DaoFactory.getFriendDao();
	/**
	 * 操控群聊和群成员数据
	 */
	private  GroupDao gDao = DaoFactory.getGroupDao();
	/**
	 * 初始化必要的数据和变量
	 */
	public void initDataService() {
		fChatMap = new HashMap<String, FriendChatFrame>();
		gChatMap = new HashMap<GroupNode, GroupChatFrame>();
		myFriendList = new MyFriendList();
		queryFriendList = new QueryFriendList();
		myGroupList = new MyGroupList();
	}
	/**
	 * 取得好友列表信息树
	 * @return 返回好友列表信息树
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
	 * 取得查询好友列表信息树
	 * @return 返回查询好友列表信息树
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
	 * 取得群聊列表信息树
	 * @return 返回群聊列表信息树
	 */
	public  JTree getMyGroupList() {
		if(myGroupList != null) {
			return myGroupList.getTree();
		}else {
			return null;
		}
	}
	/**
	 * 更新查询好友列表树的数据
	 * @param nodes 查询好友列表
	 */
	public  void updateQueryFriendList(ArrayList<FriendNode> nodes) {
		queryFriendList.setData(nodes);
		queryFriendList.update();
	}
	/**
	 * 向好友列表添加好友信息
	 * @param node 目标好友
	 */
	public  void addFriend(FriendNode node) {
		myFriendList.add(node);
		myFriendList.update();
	}
	/**
	 * 向好友列表删除好友信息
	 * @param node 目标好友
	 */
	public  void deleteFriend(FriendNode node) {
		myFriendList.delete(node);
		myFriendList.update();
	}
	/**
	 * 向群聊列表添加群聊
	 * @param node 目标群
	 */
	public  void addGroup(GroupNode node) {
		myGroupList.add(node);
		myGroupList.update();
	}
	/**
	 * 向群聊列表删除群聊
	 * @param node 目标群
	 */
	public  void deleteGroup(GroupNode node) {
		myGroupList.delete(node);
		myGroupList.update();
	}
	/**
	 * 查询好友列表是否存在node好友
	 * @param node 目标好友
	 * @return 返回存在与否
	 */
	public  boolean myFriendListContains(FriendNode node) {
		return myFriendList.contains(node);
	}
	/**
	 * 查询群聊列表是否存在node群
	 * @param node 目标群
	 * @return 返回存在与否
	 */
	public  boolean myGroupListContains(GroupNode node) {
		return myGroupList.contains(node);
	}
	/**
	 * 取得好友列表信息
	 * @return 好友列表
	 */
	public  Collection<FriendNode> getMyFriends(){
		return myFriendList.getMyFriends();
	}
	/**
	 * 设置好友列表的在线状态
	 * @param name 目标好友
	 * @param state 目标状态
	 */
	public  void setOnlineState(String name, boolean state) {
		myFriendList.setOnlineState(name, state);
	}
	/**
	 * 取得群聊列表信息
	 * @return 群聊列表
	 */
	public  Collection<GroupNode> getMyGroups() {
		return myGroupList.getMyGroups();
	}
	/**
	 * 得到node群的全部群成员
	 * @param node 目标群
	 * @return 返回node群的群成员集合
	 */
	public  DefaultListModel<String> getMyGroupMembers(GroupNode node){
		return myGroupList.getMyGroupMembers(node);
	}
	
	/**
	 * 向node群添加群成员
	 * @param node 目标群
	 * @param member 目标群成员
	 */
	public  void addGroupMembers(GroupNode node, String member) {
		myGroupList.addGroupMembers(node, member);
	}
	/**
	 * 向node群删除群成员
	 * @param node 目标群
	 * @param member 目标群成员
	 */
	public  void delGroupMembers(GroupNode node, String member) {
		myGroupList.delGroupMembers(node, member);
	}
	/**
	 * 向本机文件存储好友列表信息
	 * @param res 好友列表
	 */
	public  void saveMyFriends(Collection<FriendNode> res) {
		fDao.saveMyFriends(res);
	}
	/**
	 * 向本机文件存储群聊列表信息
	 * @param res 群聊列表
	 */
	public  void saveMyGroups(Collection<GroupNode> res) {
		gDao.saveMyGroups(res);
	}
	/**
	 * 向本机文件存储群聊成员列表信息
	 * @param node 目标群聊
	 * @param res 群聊成员列表
	 */
	public  void saveMyGroupMembers(GroupNode node, DefaultListModel<String> res) {
		gDao.saveMyGroupMembers(node, res);
	}
	/**
	 * 从本机文件中取得好友列表
	 * @return 好友列表
	 */
	public  ArrayList<FriendNode> getAllFriendsFromFile() {
		return fDao.getAllFriends();
	}
	/**
	 * 从本机文件中取得群聊列表
	 * @return 群聊列表
	 */
	public  ArrayList<GroupNode> getAllGroupsFromFile() {
		return gDao.getAllGroups();
	}
	/**
	 * 从本机文件中取得群聊成员列表
	 * @param node 目标群聊
	 * @return 群聊成员列表
	 */
	public  DefaultListModel<String> getAllGroupMembersFromFile(GroupNode node){
		return gDao.getAllGroupMembers(node);
	}
	/**
	 * 向好友聊天框管理集合添加好友聊天框
	 * @param uname 目标好友
	 * @param chatFrame 目标聊天框
	 */
	public  void addFriendChatFrame(String uname, FriendChatFrame chatFrame) {
		if(fChatMap.get(uname) == null) {
			fChatMap.put(uname, chatFrame);
		}
	}
	/**
	 *  向好友聊天框管理集合取得好友聊天框
	 * @param uname 目标好友
	 * @return 返回uname好友聊天框
	 */
	public  FriendChatFrame getFriendChatFrame(String uname) {
		return fChatMap.get(uname);
	}
	/**
	 * 向好友聊天框管理集合删除好友聊天框
	 * @param uname 目标好友
	 */
	public  void delFriendChatFrame(String uname) {
		fChatMap.remove(uname);
	}
	/**
	 * 向好友聊天框添加聊天信息
	 * @param uname 目标好友
	 * @param info 聊天信息
	 */
	public  void addFriendChatInfo(String uname, String info) {
		if(fChatMap.get(uname) == null) {
			addFriendChatFrame(uname, new FriendChatFrame(uname));
		}
		fChatMap.get(uname).append(info);
	}
	/**
	 * 向群聊聊天框管理集合添加群聊聊天框
	 * @param node 目标群聊
	 * @param chatFrame 目标聊天框
	 */
	public  void addGroupChatFrame(GroupNode node, GroupChatFrame chatFrame) {
		if(gChatMap.get(node) == null) {
			gChatMap.put(node, chatFrame);
		}
	}
	/**
	 * 向群聊聊天框管理集合取得群聊聊天框
	 * @param node 目标群聊
	 * @return 返回node群聊天框
	 */
	public  GroupChatFrame getGroupChatFrame(GroupNode node) {
		return gChatMap.get(node);
	}
	/**
	 * 向群聊聊天框管理集合删除群聊聊天框
	 * @param node 目标群聊
	 */
	public  void delGroupChatFrame(GroupNode node) {
		if(gChatMap.get(node) != null && gChatMap.get(node).isVisible()) {
			gChatMap.get(node).dispose();
		}
		gChatMap.remove(node);
	}
	/**
	 * 向群聊聊天框添加聊天信息
	 * @param node 目标群聊
	 * @param info 聊天信息
	 */
	public  void addGroupChatInfo(GroupNode node, String info) {
		if(gChatMap.get(node) == null) {
			addGroupChatFrame(node, new GroupChatFrame(node));
		}
		gChatMap.get(node).append(info);
	}
	
	/**
	 * 更新群名字
	 * @param node 旧的群信息
	 * @param newGname 新的群名字
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
