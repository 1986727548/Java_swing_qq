package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import dao.DaoFactory;
import dao.GroupDao;
import dao.UserDao;
import model.Group;
import model.User;

/**
 * 与客户端建立的通信线程
 * @author wangzhen
 *
 */
public class ServerThread extends Thread {
	/**
	 * 通信socket
	 */
	private Socket socket;
	/**
	 * 客户端输入流
	 */
	private DataInputStream dis;
	/**
	 * 客户端输出流
	 */
	private DataOutputStream dos;
	/**
	 * 操控用户，好友数据
	 */
	private UserDao userDao;
	/**
	 * 操控群聊，群成员的数据
	 */
	private GroupDao groupDao;
	/**
	 * 初始化
	 * @param socket 目标socket
	 */
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	/**
	 * 接收来自客户端的请求
	 */
	public void run() {
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			userDao = DaoFactory.getUserDao();
			groupDao = DaoFactory.getGroupDao();
			loop: while (true) {
				String type = dis.readUTF();
				switch (type) {
				case "login_request":
					requestLogin(dis.readUTF(), dis.readUTF());
					break;
				case "online_response":
					responseOnline(dis.readUTF());
					break;
				case "not_online_response":
					responseNotOnline(dis.readUTF());
					break;
				case "logon_request":
					requestLogon(dis.readUTF(), dis.readUTF());
					break;
				case "query_friend_request":
					requestQueryFriend(dis.readUTF());
					break;
				case "create_group_request":
					requestCreateGroup(dis.readUTF(), dis.readUTF());
					break;
				case "update_group_name_request":
					requestUpdateGroupName(dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "delete_group_request":
					requestDeleteGroup(dis.readUTF(), dis.readUTF());
					break;
				case "add_friend_request":
					requestAddFriend(dis.readUTF(), dis.readUTF());
					break;
				case "add_friend_response":
					responseAddFriend(dis.readUTF(), dis.readUTF());
					break;
				case "del_friend_response":
					responseDelFriend(dis.readUTF(), dis.readUTF());
					break;
				case "friend_chat_request":
					requestFriendChat(dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "group_chat_request":
					requestGroupChat(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "first_get_friend_info_request":
					firstGetFriendInfoRequest(dis.readUTF());
					break;
				case "first_get_group_info_request":
					firstGetGroupInfoRequest(dis.readUTF());
					break;
				case "first_get_group_member_info_request":
					firstGetGroupMemberInfoRequest(dis.readUTF(), dis.readUTF());
					break;
				case "invite_friend_response":
					responseInviteFriend(dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "add_groupMembers_request":
					requestAddGroupMember(dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "del_groupMembers_request":
					requestDelGroupMember(dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "knock_member_response":
					responseKnockGroupMember(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF());
					break;
				case "close_main_request":
					requestCloseMainConnection();
					break loop;
				case "close_login_request":
					break loop;
				default:
					break;
				}
			}
			closeSource();

		} catch (Exception e) {
			System.err.println("ServerThread -> run");
		}
	}
	/**
	 * 关闭与客户端建立的通信socket
	 */
	private void closeSource() {
		Server.append(socket + "退出连接" + "\n");
		DataService.remove(socket);
		try {
			dis.close();
			dos.close();
			socket.close();

		} catch (Exception e) {
			System.err.println("ServerThread->closeSource");
		}
	}

	/**
	 * 向数据库请求登录，向客户端返回结果
	 * @param uname 用户名
	 * @param psword 密码
	 */
	private void requestLogin(String uname, String psword) {
		if (DataService.containsUname(uname)) {
			try {
				dos.writeUTF("不能重复登录");
			} catch (IOException e) {
				System.err.println("ServerThread->requestLogin");
			}
			return;
		}
		try {
			if (userDao.login(new User(uname, psword))) {
				dos.writeUTF("登录成功");
				DataService.put(socket, uname);
				//处理离线信息
				if(DataService.getResponseMsg(uname) != null) {
					for(String msg : DataService.getResponseMsg(uname)) {
						send(socket.getInetAddress(), socket.getPort(), msg);
					}
					DataService.getResponseMsg(uname).clear();
				}
				for(User user : userDao.getAllFriends(uname)) {
					if(DataService.containsUname(user.getUname())) {
						send(socket.getInetAddress(), socket.getPort(), "2,online_response," + user.getUname());
					}
				}
			} else {
				dos.writeUTF("登录失败");
			}
		} catch (Exception e) {
			System.err.println("错误位置  server->requestLogin");
		}
	}
	/**
	 * 响应在线，向客户端返回结果
	 * @param ori 目标用户名
	 */
	private void responseOnline(String ori) {
		try {
			String fname = dis.readUTF();
			while(!fname.equals("now it's over!!!!")) {
				Socket socket = DataService.getSocket(fname);
				if(socket != null) {
					send(socket.getInetAddress(), socket.getPort(), "2,online_response," + ori);
				}else {
					DataService.addResponseMsg(fname, "2,online_response," + ori);
				}
				
				fname = dis.readUTF();
			}
		} catch (IOException e) {
			System.err.println("错误位置  server->requestOnline");
		}
		
	}
	/**
	 * 响应不在线，向客户端返回结果
	 * @param ori 目标用户名
	 */
	private void responseNotOnline(String ori) {
		try {
			String fname = dis.readUTF();
			while(!fname.equals("now it's over!!!!")) {
				Socket socket = DataService.getSocket(fname);
				if(socket != null) {
					send(socket.getInetAddress(), socket.getPort(), "2,not_online_response," + ori);
				}else {
					DataService.addResponseMsg(fname, "2,not_online_response," + ori);
				}
				
				fname = dis.readUTF();
			}
		} catch (IOException e) {
			System.err.println("错误位置  server->responseNotOnline");
		}
		
	}

	/**
	 * 向数据库请求注册， 向客户端返回结果
	 * @param uname 用户名
	 * @param psword 密码
	 */
	private void requestLogon(String uname, String psword) {
		try {
			if (userDao.logon(new User(uname, psword))) {
				dos.writeBoolean(true);
			} else {
				dos.writeBoolean(false);
			}
		} catch (Exception e) {
			System.err.println("ServerThread->requestLogon");
		}
	}

	/**
	 * 向数据库请求查询好友，向客户端返回结果
	 * 
	 * @param string 模糊查询串
	 */
	private void requestQueryFriend(String string) {
		try {
			ArrayList<User> users = userDao.getUsersFromQuery(string + "%");
			String selfUname = DataService.getUname(socket);
			for (User user : users) {
				if (selfUname.equals(user.getUname()))
					continue;
				dos.writeUTF(user.getUname());
				if (DataService.containsUname(user.getUname())) {
					user.setState(true);
				}
				dos.writeBoolean(user.isState());
			}
			dos.writeUTF("now it's over!!!!");
		} catch (Exception e) {
			System.err.println("ServerThread->requestQueryFriend");
		}
	}

	/**
	 * 向数据库请求创建群聊，向客户端返回结果
	 * 
	 * @param uname 群主
	 * @param gname 群名字
	 */
	private void requestCreateGroup(String uname, String gname) {
		try {
			if (groupDao.create(new Group(gname, uname))) {
				dos.writeBoolean(true);
			} else {
				dos.writeBoolean(false);
			}
			requestAddGroupMember(uname, gname, uname);
		} catch (Exception e) {
			System.err.println("ServerThread->requestCreateGroup");
		}
	}
	
	private void requestUpdateGroupName(String owner, String oldGname, String newGname) {
		try {
			if(groupDao.updateGroupName(owner, oldGname, newGname)) {
				dos.writeBoolean(true);
				for(String member : groupDao.getAllGroupMember(newGname, owner)) {
					if(member.equals(owner))
						continue;
					Socket socket = DataService.getSocket(member);
					if(socket != null) {
						send(socket.getInetAddress(), socket.getPort(), "4,update_group_name_response," + owner + "," + oldGname + "," + newGname);
					}else {
						DataService.addResponseMsg(member, "4,update_group_name_response," + owner + "," + oldGname + "," + newGname);
					}
					
				}
			}else {
				dos.writeBoolean(false);
			}
			
		} catch (Exception e) {
			System.err.println("ServerThread->requestUpdateGroupName");
		}
	}

	/**
	 * 向数据库请求删除群聊，向客户端返回结果
	 * 
	 * @param uname 群主
	 * @param gname 群名
	 */
	private void requestDeleteGroup(String uname, String gname) {
		try {
			if (groupDao.delete(new Group(uname, gname))) {
				dos.writeBoolean(true);
			} else {
				dos.writeBoolean(false);
			}
		} catch (Exception e) {
			System.err.println("ServerThread->requestDeleteGroup");
		}
	}
	/**
	 * 转发添加好友请求，向客户端返回结果
	 * @param ori 源用户
	 * @param des 目标用户
	 */
	private void requestAddFriend(String ori, String des) {
		Socket socket = DataService.getSocket(des);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "2,add_friend_request," + ori);
		}else {
			DataService.addResponseMsg(des, "2,add_friend_request," + ori);
		}
		
	}
	/**
	 * 响应添加好友，向客户端返回结果
	 * @param ori 源用户
	 * @param des 目标用户
	 */
	private void responseAddFriend(String ori, String des) {
		try {
			userDao.addFriend(ori, des);
		} catch (Exception e) {
			System.err.println("ServerThread->responseAddFriend");
		}
		Socket socket = DataService.getSocket(des);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "2,add_friend_response," + ori);
		}
		else {
			DataService.addResponseMsg(des, "2,add_friend_response," + ori);
		}
	}
	/**
	 * 响应删除好友，向客户端返回结果
	 * @param ori 源用户
	 * @param des 目标用户
	 */
	private void responseDelFriend(String ori, String des) {
		try {
			userDao.delFriend(ori, des);
		} catch (Exception e) {
			System.err.println("ServerThread->responseDelFriend");
		}
		Socket socket = DataService.getSocket(des);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "2,del_friend_response," + ori);
		}else {
			DataService.addResponseMsg(des, "2,del_friend_response," + ori);
		}
			
	}
	/**
	 * 转发好友聊天信息，向客户端返回结果
	 * @param ori 源用户
	 * @param des 目标用户
	 * @param info 聊天信息
	 */
	private void requestFriendChat(String ori, String des, String info) {
		Socket socket = DataService.getSocket(des);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "3,friend_chat_response," + ori + ","  + info);
		}else {
			DataService.addResponseMsg(des, "3,friend_chat_response," + ori + ","  + info);
		}
		
	}
	/**
	 * 转发群聊天信息，向客户端返回结果
	 * @param ori 源用户
	 * @param des 目标用户
	 * @param owner 群主
	 * @param gname 群名字
	 * @param info 聊天信息
	 */
	private void requestGroupChat(String ori, String des, String owner, String gname, String info) {
		Socket socket = DataService.getSocket(des);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "5,group_chat_response," + ori + "," + owner + "," + gname + "," + info);
		}else {
			DataService.addResponseMsg(des, "5,group_chat_response," + ori + "," + owner + "," + gname + "," + info);
		}
		
	}
	/**
	 * 向数据库请求好友信息，向客户端返回结果
	 * @param uname 目标用户名
	 */
	private void firstGetFriendInfoRequest(String uname) {
		try {
			for (User user : userDao.getAllFriends(uname)) {
				dos.writeUTF(user.getUname());
				dos.writeBoolean(DataService.containsUname(user.getUname()));
			}
			dos.writeUTF("now it's over!!!!");
		} catch (Exception e) {
			System.err.println("ServerThread->firstGetFriendInfoRequest");
		}
	}
	/**
	 * 向数据库请求群信息，向客户端返回结果
	 * @param uname 目标用户名
	 */
	private void firstGetGroupInfoRequest(String uname) {
		try {
			for (Group group : groupDao.getAllGroup(uname)) {
				dos.writeUTF(group.getGname());
				dos.writeUTF(group.getUname());
			}
			dos.writeUTF("now it's over!!!!");
		} catch (Exception e) {
			System.err.println("ServerThread->firstGetGroupInfoRequest");
		}
	}
	/**
	 * 向数据库请求群成员信息，向客户端返回结果
	 * @param gname 群名
	 * @param owner 群主
	 */
	private void firstGetGroupMemberInfoRequest(String gname, String owner) {
		try {
			for (String gmember : groupDao.getAllGroupMember(gname, owner)) {
				dos.writeUTF(gmember);
			}
			dos.writeUTF("now it's over!!!!");
		} catch (Exception e) {
			System.err.println("ServerThread->firstGetGroupInfoRequest");
		}
	}
	/**
	 * 响应邀请群成员，向客户端返回结果
	 * @param des 目标用户名
	 * @param owner 群主
	 * @param gname 群名
	 */
	private void responseInviteFriend(String des, String owner, String gname) {
		ArrayList<String> members = new ArrayList<String>();
		try {
			String memString = dis.readUTF();
			while (!memString.equals("now it's over!!!!")) {
				members.add(memString);
				memString = dis.readUTF();
			}
		} catch (Exception e) {
			System.err.println("ServerThread->responseInviteFriend");
		}
		Socket socket = DataService.getSocket(des);
		StringBuilder sb = new StringBuilder();
		sb.append("invite_friend_response");
		sb.append("," + owner);
		sb.append("," + gname);
		for (String string : members) {
			sb.append("," + string);
		}
		int cnt = members.size()+3;
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), cnt+","+sb.toString());
		}else {
			DataService.addResponseMsg(des,  cnt+","+sb.toString());
		}
	}
	/**
	 * 响应踢掉群成员，向客户端返回结果
	 * @param des 目标用户名
	 * @param owner 群主
	 * @param gname 群名
	 * @param member 群成员
	 */
	private void responseKnockGroupMember(String des, String owner, String gname, String member) {
		Socket socket = DataService.getSocket(des);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "4,knock_member_response," + owner + "," + gname + "," + member);
		}else {
			DataService.addResponseMsg(des, "4,knock_member_response," + owner + "," + gname + "," + member);
		}
		
	}
	/**
	 * 向数据库请求插入群成员信息
	 * @param owner 群主
	 * @param gname 群名
	 * @param member 群成员
	 */
	private void requestAddGroupMember(String owner, String gname, String member) {
		groupDao.invite(owner, gname, member);
	}
	/**
	 * 响应踢掉群成员
	 * @param owner 群主
	 * @param gname 群名
	 * @param member 群成员
	 */
	private void requestDelGroupMember(String owner, String gname, String member) {
		groupDao.knock(owner, gname, member);
		Socket socket = DataService.getSocket(member);
		if(socket != null) {
			send(socket.getInetAddress(), socket.getPort(), "3,del_group_response," + owner + "," + gname);
		}else {
			DataService.addResponseMsg(member, "3,del_group_response," + owner + "," + gname);
		}
		
	}
	/**
	 * udp来向客服端转发信息
	 * @param ip ip地址
	 * @param port 端口号
	 * @param content 转发内容
	 */
	private void send(InetAddress ip, int port, String content) {
		try {
			DatagramSocket ds = new DatagramSocket();
			byte[] buf = content.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
			ds.send(packet);
			ds.close();
		} catch (Exception e) {
			System.err.println("ServerThread->send");
		}

	}
	/**
	 * 转发关闭客户端线程请求，向客户端返回结果
	 */
	private void requestCloseMainConnection() {
		send(socket.getInetAddress(), socket.getPort(), "1,close_response");
	}

}
