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
 * ��ͻ��˽�����ͨ���߳�
 * @author wangzhen
 *
 */
public class ServerThread extends Thread {
	/**
	 * ͨ��socket
	 */
	private Socket socket;
	/**
	 * �ͻ���������
	 */
	private DataInputStream dis;
	/**
	 * �ͻ��������
	 */
	private DataOutputStream dos;
	/**
	 * �ٿ��û�����������
	 */
	private UserDao userDao;
	/**
	 * �ٿ�Ⱥ�ģ�Ⱥ��Ա������
	 */
	private GroupDao groupDao;
	/**
	 * ��ʼ��
	 * @param socket Ŀ��socket
	 */
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	/**
	 * �������Կͻ��˵�����
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
	 * �ر���ͻ��˽�����ͨ��socket
	 */
	private void closeSource() {
		Server.append(socket + "�˳�����" + "\n");
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
	 * �����ݿ������¼����ͻ��˷��ؽ��
	 * @param uname �û���
	 * @param psword ����
	 */
	private void requestLogin(String uname, String psword) {
		if (DataService.containsUname(uname)) {
			try {
				dos.writeUTF("�����ظ���¼");
			} catch (IOException e) {
				System.err.println("ServerThread->requestLogin");
			}
			return;
		}
		try {
			if (userDao.login(new User(uname, psword))) {
				dos.writeUTF("��¼�ɹ�");
				DataService.put(socket, uname);
				//����������Ϣ
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
				dos.writeUTF("��¼ʧ��");
			}
		} catch (Exception e) {
			System.err.println("����λ��  server->requestLogin");
		}
	}
	/**
	 * ��Ӧ���ߣ���ͻ��˷��ؽ��
	 * @param ori Ŀ���û���
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
			System.err.println("����λ��  server->requestOnline");
		}
		
	}
	/**
	 * ��Ӧ�����ߣ���ͻ��˷��ؽ��
	 * @param ori Ŀ���û���
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
			System.err.println("����λ��  server->responseNotOnline");
		}
		
	}

	/**
	 * �����ݿ�����ע�ᣬ ��ͻ��˷��ؽ��
	 * @param uname �û���
	 * @param psword ����
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
	 * �����ݿ������ѯ���ѣ���ͻ��˷��ؽ��
	 * 
	 * @param string ģ����ѯ��
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
	 * �����ݿ����󴴽�Ⱥ�ģ���ͻ��˷��ؽ��
	 * 
	 * @param uname Ⱥ��
	 * @param gname Ⱥ����
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
	 * �����ݿ�����ɾ��Ⱥ�ģ���ͻ��˷��ؽ��
	 * 
	 * @param uname Ⱥ��
	 * @param gname Ⱥ��
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
	 * ת����Ӻ���������ͻ��˷��ؽ��
	 * @param ori Դ�û�
	 * @param des Ŀ���û�
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
	 * ��Ӧ��Ӻ��ѣ���ͻ��˷��ؽ��
	 * @param ori Դ�û�
	 * @param des Ŀ���û�
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
	 * ��Ӧɾ�����ѣ���ͻ��˷��ؽ��
	 * @param ori Դ�û�
	 * @param des Ŀ���û�
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
	 * ת������������Ϣ����ͻ��˷��ؽ��
	 * @param ori Դ�û�
	 * @param des Ŀ���û�
	 * @param info ������Ϣ
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
	 * ת��Ⱥ������Ϣ����ͻ��˷��ؽ��
	 * @param ori Դ�û�
	 * @param des Ŀ���û�
	 * @param owner Ⱥ��
	 * @param gname Ⱥ����
	 * @param info ������Ϣ
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
	 * �����ݿ����������Ϣ����ͻ��˷��ؽ��
	 * @param uname Ŀ���û���
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
	 * �����ݿ�����Ⱥ��Ϣ����ͻ��˷��ؽ��
	 * @param uname Ŀ���û���
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
	 * �����ݿ�����Ⱥ��Ա��Ϣ����ͻ��˷��ؽ��
	 * @param gname Ⱥ��
	 * @param owner Ⱥ��
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
	 * ��Ӧ����Ⱥ��Ա����ͻ��˷��ؽ��
	 * @param des Ŀ���û���
	 * @param owner Ⱥ��
	 * @param gname Ⱥ��
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
	 * ��Ӧ�ߵ�Ⱥ��Ա����ͻ��˷��ؽ��
	 * @param des Ŀ���û���
	 * @param owner Ⱥ��
	 * @param gname Ⱥ��
	 * @param member Ⱥ��Ա
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
	 * �����ݿ��������Ⱥ��Ա��Ϣ
	 * @param owner Ⱥ��
	 * @param gname Ⱥ��
	 * @param member Ⱥ��Ա
	 */
	private void requestAddGroupMember(String owner, String gname, String member) {
		groupDao.invite(owner, gname, member);
	}
	/**
	 * ��Ӧ�ߵ�Ⱥ��Ա
	 * @param owner Ⱥ��
	 * @param gname Ⱥ��
	 * @param member Ⱥ��Ա
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
	 * udp����ͷ���ת����Ϣ
	 * @param ip ip��ַ
	 * @param port �˿ں�
	 * @param content ת������
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
	 * ת���رտͻ����߳�������ͻ��˷��ؽ��
	 */
	private void requestCloseMainConnection() {
		send(socket.getInetAddress(), socket.getPort(), "1,close_response");
	}

}
