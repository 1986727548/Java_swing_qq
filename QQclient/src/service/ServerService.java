package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import component.FriendNode;
import component.GroupNode;
import controller.Controller;
import view.MainFrame;

/**
 * 用于和服务器进行数据传输
 * @author wangzhen
 *
 */
public class ServerService {
	/**
	 * 服务器的输入流
	 */
	private  DataInputStream in;
	/**
	 * 服务器的输出流
	 */
	private  DataOutputStream out;
	/**
	 * 连接服务器所用
	 */
	private  Socket socket;
	/**
	 * 用于udp接收数据
	 */
	private  DatagramSocket ds;

	/**
	 * 和服务器连接
	 */
	public  void requestServerConnetion() {
		try {
			socket = new Socket("localhost", 2003);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			ds = new DatagramSocket(socket.getLocalPort());
		} catch (Exception e) {
			System.err.println("客户端连接失败，位置ServerService->requestServerConnetion");
		}
	}
	/**
	 * 向服务器请求登录
	 * @param uname 用户名
	 * @param psword 密码
	 * @return 返回登录结果（登录成功，登录失败，不可重复登录）
	 */
	public  String requestLogin(String uname, String psword) {
		String res = null;
		try {
			out.writeUTF("login_request");
			out.writeUTF(uname);
			out.writeUTF(psword);
			res = in.readUTF();
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestLogin");
		}
		return res;
	}
	/**
	 * 向服务器响应在线，由服务器想uname用户的好友列表转发
	 * @param uname 用户名
	 * @param myFriends 好友列表
	 */
	public  void responseOnline(String uname, Collection<FriendNode> myFriends) {
		try {
			out.writeUTF("online_response");
			out.writeUTF(uname);
			for(FriendNode node : myFriends) {
				out.writeUTF(node.getUname());
			}
			out.writeUTF("now it's over!!!!");
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->responseOnline");
		}
	}
	/**
	 * 向服务器响应不在线，由服务器想uname用户的好友列表转发
	 * @param uname 用户名
	 * @param myFriends 好友列表
	 */ 
	public  void responseNotOnline(String uname, Collection<FriendNode> myFriends) {
		try {
			out.writeUTF("not_online_response");
			out.writeUTF(uname);
			for(FriendNode node : myFriends) {
				out.writeUTF(node.getUname());
			}
			out.writeUTF("now it's over!!!!");
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->responseNotOnline");
		}
	}
	/**
	 * 向服务器请求注册
	 * @param uname 用户名
	 * @param psword 密码
	 * @return 返回注册结果
	 */
	public  boolean requestLogon(String uname, String psword) {
		try {
			out.writeUTF("logon_request");
			out.writeUTF(uname);
			out.writeUTF(psword);
			if (in.readBoolean()) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestLogon");
		}
		return false;
	}
	/**
	 * 根据string来向服务器进行模糊查询好友
	 * @param string 查询字段
	 * @return 返回查询的好友列表
	 */
	public  ArrayList<FriendNode> requestQueryFriend(String string) {
		ArrayList<FriendNode> nodes = new ArrayList<FriendNode>();
		try {
			out.writeUTF("query_friend_request");
			out.writeUTF(string);
			String status = in.readUTF();
			while (!status.equals("now it's over!!!!")) {
				nodes.add(new FriendNode(status, in.readBoolean()));
				status = in.readUTF();
			}
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->reponseQueryFriend");
		}
		return nodes;
	}
	/**
	 * 向服务器请求添加好友，由服务器转发到des用户
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 */
	public  void requestAddFriend(String ori, String des) {
		try {
			out.writeUTF("add_friend_request");
			out.writeUTF(ori);
			out.writeUTF(des);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestAddFriend");
		}
	}
	/**
	 * 向服务器响应已同意添加好友请求
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 */
	public  void responseAddFriend(String ori, String des) {
		System.out.println("enter responseAddFriend");
		try {
			out.writeUTF("add_friend_response");
			out.writeUTF(ori);
			out.writeUTF(des);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->responseAddFriend");
		}
	}
	/**
	 * 向服务器请求修改群名称
	 * @param node 目标群
	 */
	public  void requestUpdateGroupName(GroupNode node) {
		String groupName = null;
		while (true) {
			groupName = JOptionPane.showInputDialog(null, "群名字长度不能超过10\n不能有,符号", "修改群名称", JOptionPane.DEFAULT_OPTION);
			if (null == groupName || groupName.length() > 0 && groupName.length() <= 10) {
				if(null == groupName) {
					break;
				}else if(groupName.indexOf(',') != -1){
					JOptionPane.showMessageDialog(null, "不能有,符号", "错误", JOptionPane.WARNING_MESSAGE);
				}else {
					break;
				}
				
			}
			if (groupName.length() <= 0 || groupName.length() > 10) {
				JOptionPane.showMessageDialog(null, "名字不符合要求", "错误", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (groupName != null) {
			GroupNode newNode = new GroupNode(groupName, MainFrame.getUname());
			if (Controller.myGroupListContains(newNode)) {
				JOptionPane.showMessageDialog(null, "不能同名", "错误", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					out.writeUTF("update_group_name_request");
					out.writeUTF(MainFrame.getUname());
					out.writeUTF(node.getGname());
					out.writeUTF(newNode.getGname());
					if (in.readBoolean()) {
						JOptionPane.showMessageDialog(null, "修改群名称成功" + "成功", "成功",
								JOptionPane.INFORMATION_MESSAGE);
						Controller.updateGroupName(node, groupName);
					} else {
						JOptionPane.showMessageDialog(null, "修改群名称失败", "错误", JOptionPane.WARNING_MESSAGE);
					}

				} catch (IOException e) {
					System.err.println("错误位置 ServerService->requestUpdateGroupName");
				}
			}
		}
	}
	/**
	 * 向服务器请求创建群聊
	 */
	public  void requestCreateGroup() {
		String groupName = null;
		while (true) {
			groupName = JOptionPane.showInputDialog(null, "群名字长度不能超过10\n不能有,符号", "创建群聊", JOptionPane.DEFAULT_OPTION);
			if (null == groupName || groupName.length() > 0 && groupName.length() <= 10) {
				if(null == groupName) {
					break;
				}else if(groupName.indexOf(',') != -1){
					JOptionPane.showMessageDialog(null, "不能有,符号", "错误", JOptionPane.WARNING_MESSAGE);
				}else {
					break;
				}
				
			}
			if (groupName.length() <= 0 || groupName.length() > 10) {
				JOptionPane.showMessageDialog(null, "名字不符合要求", "错误", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (groupName != null) {
			GroupNode node = new GroupNode(groupName, MainFrame.getUname());
			if (Controller.myGroupListContains(node)) {
				JOptionPane.showMessageDialog(null, "已创建该群聊", "错误", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					out.writeUTF("create_group_request");
					out.writeUTF(MainFrame.getUname());
					out.writeUTF(node.getGname());
					if (in.readBoolean()) {
						JOptionPane.showMessageDialog(null, "创建群聊" + node.getGname() + "成功", "成功",
								JOptionPane.INFORMATION_MESSAGE);
						Controller.addGroup(node);
					} else {
						JOptionPane.showMessageDialog(null, "创建该群聊失败", "错误", JOptionPane.WARNING_MESSAGE);
					}

				} catch (IOException e) {
					System.err.println("错误位置 ServerService->requestCreateGroup");
				}
			}
		}

	}
	/**
	 * 向服务器请求删除群聊
	 * @param node 目标群
	 */
	public  void requestDelGroup(GroupNode node) {
		try {
			out.writeUTF("delete_group_request");
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestDelGroup");
		}
	}
	/**
	 * 向服务器响应已删除该好友
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 */
	public  void responseDelFriend(String ori, String des) {
		try {
			out.writeUTF("del_friend_response");
			out.writeUTF(ori);
			out.writeUTF(des);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->responseDelFriend");
		}
	}
	/**
	 * 向服务器请求好友聊天，由服务器向des用户转发info
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 * @param info 聊天的信息
	 */
	public  void requestFriendChat(String ori, String des, String info) {
		try {
			out.writeUTF("friend_chat_request");
			out.writeUTF(ori);
			out.writeUTF(des);
			out.writeUTF(info);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestFriendChat");
		}
	}
	/**
	 * 向服务器请求群聊天，由服务器向des用户的node群转发info， 一个一个用户来转发的
	 * @param ori 本身用户名
	 * @param des 目的用户名
	 * @param node 群聊
	 * @param info 聊天的信息
	 */
	public  void requestGroupChat(String ori, String des, GroupNode node, String info) {
		try {
			out.writeUTF("group_chat_request");
			out.writeUTF(ori);
			out.writeUTF(des);
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(info);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestGroupChat");
		}
	}
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候向服务器请求获得好友信息
	 * @return 返回自己的好友列表
	 */
	public  ArrayList<FriendNode> firstGetFriendInfoRequest() {
		ArrayList<FriendNode> nodes = new ArrayList<FriendNode>();
		try {
			out.writeUTF("first_get_friend_info_request");
			out.writeUTF(MainFrame.getUname());
			String res = in.readUTF();
			while (!res.equals("now it's over!!!!")) {
				nodes.add(new FriendNode(res, in.readBoolean()));
				res = in.readUTF();
			}
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->firstGetFriendInfoRequest");
		}
		return nodes;
	}
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候向服务器请求获得群聊信息
	 * @return 返回自己的群聊列表
	 */
	public  ArrayList<GroupNode> firstGetGroupInfoRequest() {
		ArrayList<GroupNode> nodes = new ArrayList<GroupNode>();
		try {
			out.writeUTF("first_get_group_info_request");
			out.writeUTF(MainFrame.getUname());
			String res = in.readUTF();
			while (!res.equals("now it's over!!!!")) {
				nodes.add(new GroupNode(res, in.readUTF()));
				res = in.readUTF();
			}
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->firstGetGroupInfoRequest");
		}
		return nodes;
	}
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候向服务器请求获得群聊成员信息
	 * @param node 目标群
	 * @return 返回自己的群聊成员列表
	 */
	public  DefaultListModel<String> firstGetGroupMemberInfoRequest(GroupNode node){
		DefaultListModel<String> list = new DefaultListModel<String>();
		try {
			out.writeUTF("first_get_group_member_info_request");
			out.writeUTF(node.getGname());
			out.writeUTF(node.getOwner());
			String res = in.readUTF();
			while (!res.equals("now it's over!!!!")) {
				list.add(list.getSize(), res);
				res = in.readUTF();
			}
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->firstGetGroupMemberInfoRequest");
		}
		return list;
	}
	/**
	 * 向服务器响应des用户已被邀请进这个node群聊了，然后服务器转发
	 * @param des 目标用户名
	 * @param node 目标群聊名
	 * @param members des用户的node群该有的members群成员
	 */
	public  void responseInviteFriendtoGroup(String des, GroupNode node, ArrayList<String> members) {
		try {
			out.writeUTF("invite_friend_response");
			out.writeUTF(des);
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			for (String string : members) {
				out.writeUTF(string);
			}
			out.writeUTF("now it's over!!!!");

		} catch (Exception e) {
			System.err.println("错误位置 ServerService->responseInviteFriendtoGroup");
		}
	}
	/**
	 * 向群成员响应已经踢掉该成员了
	 * @param des 目标用户名
	 * @param node 目标群聊名
	 * @param member 目标群成员
	 */
	public  void responseKnockFriendFromGroup(String des, GroupNode node, String member) {
		try {
			out.writeUTF("knock_member_response");
			out.writeUTF(des);
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(member);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->responseInviteFriendtoGroup");
		}
	}
	/**
	 * 在数据库的group_info表里插入信息
	 * @param node 目标群聊名
	 * @param member 目标成员名
	 */
	public  void requestInviteGroupMembers(GroupNode node, String member) {
		try {
			out.writeUTF("add_groupMembers_request");
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(member);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestInviteGroupMembers");
		}
	}
	/**
	 * 在数据库的group_info表里删除信息
	 * @param node 目标群聊名
	 * @param member 目标成员名
	 */
	public  void requestDelGroupMembers(GroupNode node, String member) {
		try {
			out.writeUTF("del_groupMembers_request");
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(member);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->requestAddGroupMembers");
		}
	}
	/**
	 * 用udp通信接收来自服务器转发的信息
	 * @return 返回转发的信息包
	 */
	public  String receiver() {
		DatagramPacket packet = null;
		try {
			byte[] buf = new byte[1024];
			packet = new DatagramPacket(buf, buf.length);
			ds.receive(packet);
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->receiver");
		}
		return new String(packet.getData(), 0, packet.getLength());
	}
	/**
	 * 通过向服务器发送关闭请求，来结束服务器线程，本身的线程，和closeLogin互斥
	 */
	public  void closeMain() {
		try {
			out.writeUTF("close_main_request");
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->closeMainConnection");
		}
	}
	/**
	 * 通过向服务器发送关闭请求，来结束服务器线程，和closeMain互斥
	 */
	public  void closeLogin() {
		try {
			out.writeUTF("close_login_request");
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->closeLoginConnection");
		}
	}
	/**
	 * 关闭客户端网络通信的接口
	 */
	public  void closeConnection() {
		try {
			in.close();
			out.close();
			ds.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("错误位置 ServerService->close");
		}
	}
}
