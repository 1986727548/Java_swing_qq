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
 * ���ںͷ������������ݴ���
 * @author wangzhen
 *
 */
public class ServerService {
	/**
	 * ��������������
	 */
	private  DataInputStream in;
	/**
	 * �������������
	 */
	private  DataOutputStream out;
	/**
	 * ���ӷ���������
	 */
	private  Socket socket;
	/**
	 * ����udp��������
	 */
	private  DatagramSocket ds;

	/**
	 * �ͷ���������
	 */
	public  void requestServerConnetion() {
		try {
			socket = new Socket("localhost", 2003);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			ds = new DatagramSocket(socket.getLocalPort());
		} catch (Exception e) {
			System.err.println("�ͻ�������ʧ�ܣ�λ��ServerService->requestServerConnetion");
		}
	}
	/**
	 * ������������¼
	 * @param uname �û���
	 * @param psword ����
	 * @return ���ص�¼�������¼�ɹ�����¼ʧ�ܣ������ظ���¼��
	 */
	public  String requestLogin(String uname, String psword) {
		String res = null;
		try {
			out.writeUTF("login_request");
			out.writeUTF(uname);
			out.writeUTF(psword);
			res = in.readUTF();
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->requestLogin");
		}
		return res;
	}
	/**
	 * ���������Ӧ���ߣ��ɷ�������uname�û��ĺ����б�ת��
	 * @param uname �û���
	 * @param myFriends �����б�
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
			System.err.println("����λ�� ServerService->responseOnline");
		}
	}
	/**
	 * ���������Ӧ�����ߣ��ɷ�������uname�û��ĺ����б�ת��
	 * @param uname �û���
	 * @param myFriends �����б�
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
			System.err.println("����λ�� ServerService->responseNotOnline");
		}
	}
	/**
	 * �����������ע��
	 * @param uname �û���
	 * @param psword ����
	 * @return ����ע����
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
			System.err.println("����λ�� ServerService->requestLogon");
		}
		return false;
	}
	/**
	 * ����string�������������ģ����ѯ����
	 * @param string ��ѯ�ֶ�
	 * @return ���ز�ѯ�ĺ����б�
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
			System.err.println("����λ�� ServerService->reponseQueryFriend");
		}
		return nodes;
	}
	/**
	 * �������������Ӻ��ѣ��ɷ�����ת����des�û�
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 */
	public  void requestAddFriend(String ori, String des) {
		try {
			out.writeUTF("add_friend_request");
			out.writeUTF(ori);
			out.writeUTF(des);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->requestAddFriend");
		}
	}
	/**
	 * ���������Ӧ��ͬ����Ӻ�������
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 */
	public  void responseAddFriend(String ori, String des) {
		System.out.println("enter responseAddFriend");
		try {
			out.writeUTF("add_friend_response");
			out.writeUTF(ori);
			out.writeUTF(des);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->responseAddFriend");
		}
	}
	/**
	 * ������������޸�Ⱥ����
	 * @param node Ŀ��Ⱥ
	 */
	public  void requestUpdateGroupName(GroupNode node) {
		String groupName = null;
		while (true) {
			groupName = JOptionPane.showInputDialog(null, "Ⱥ���ֳ��Ȳ��ܳ���10\n������,����", "�޸�Ⱥ����", JOptionPane.DEFAULT_OPTION);
			if (null == groupName || groupName.length() > 0 && groupName.length() <= 10) {
				if(null == groupName) {
					break;
				}else if(groupName.indexOf(',') != -1){
					JOptionPane.showMessageDialog(null, "������,����", "����", JOptionPane.WARNING_MESSAGE);
				}else {
					break;
				}
				
			}
			if (groupName.length() <= 0 || groupName.length() > 10) {
				JOptionPane.showMessageDialog(null, "���ֲ�����Ҫ��", "����", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (groupName != null) {
			GroupNode newNode = new GroupNode(groupName, MainFrame.getUname());
			if (Controller.myGroupListContains(newNode)) {
				JOptionPane.showMessageDialog(null, "����ͬ��", "����", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					out.writeUTF("update_group_name_request");
					out.writeUTF(MainFrame.getUname());
					out.writeUTF(node.getGname());
					out.writeUTF(newNode.getGname());
					if (in.readBoolean()) {
						JOptionPane.showMessageDialog(null, "�޸�Ⱥ���Ƴɹ�" + "�ɹ�", "�ɹ�",
								JOptionPane.INFORMATION_MESSAGE);
						Controller.updateGroupName(node, groupName);
					} else {
						JOptionPane.showMessageDialog(null, "�޸�Ⱥ����ʧ��", "����", JOptionPane.WARNING_MESSAGE);
					}

				} catch (IOException e) {
					System.err.println("����λ�� ServerService->requestUpdateGroupName");
				}
			}
		}
	}
	/**
	 * ����������󴴽�Ⱥ��
	 */
	public  void requestCreateGroup() {
		String groupName = null;
		while (true) {
			groupName = JOptionPane.showInputDialog(null, "Ⱥ���ֳ��Ȳ��ܳ���10\n������,����", "����Ⱥ��", JOptionPane.DEFAULT_OPTION);
			if (null == groupName || groupName.length() > 0 && groupName.length() <= 10) {
				if(null == groupName) {
					break;
				}else if(groupName.indexOf(',') != -1){
					JOptionPane.showMessageDialog(null, "������,����", "����", JOptionPane.WARNING_MESSAGE);
				}else {
					break;
				}
				
			}
			if (groupName.length() <= 0 || groupName.length() > 10) {
				JOptionPane.showMessageDialog(null, "���ֲ�����Ҫ��", "����", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (groupName != null) {
			GroupNode node = new GroupNode(groupName, MainFrame.getUname());
			if (Controller.myGroupListContains(node)) {
				JOptionPane.showMessageDialog(null, "�Ѵ�����Ⱥ��", "����", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					out.writeUTF("create_group_request");
					out.writeUTF(MainFrame.getUname());
					out.writeUTF(node.getGname());
					if (in.readBoolean()) {
						JOptionPane.showMessageDialog(null, "����Ⱥ��" + node.getGname() + "�ɹ�", "�ɹ�",
								JOptionPane.INFORMATION_MESSAGE);
						Controller.addGroup(node);
					} else {
						JOptionPane.showMessageDialog(null, "������Ⱥ��ʧ��", "����", JOptionPane.WARNING_MESSAGE);
					}

				} catch (IOException e) {
					System.err.println("����λ�� ServerService->requestCreateGroup");
				}
			}
		}

	}
	/**
	 * �����������ɾ��Ⱥ��
	 * @param node Ŀ��Ⱥ
	 */
	public  void requestDelGroup(GroupNode node) {
		try {
			out.writeUTF("delete_group_request");
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->requestDelGroup");
		}
	}
	/**
	 * ���������Ӧ��ɾ���ú���
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 */
	public  void responseDelFriend(String ori, String des) {
		try {
			out.writeUTF("del_friend_response");
			out.writeUTF(ori);
			out.writeUTF(des);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->responseDelFriend");
		}
	}
	/**
	 * �����������������죬�ɷ�������des�û�ת��info
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 * @param info �������Ϣ
	 */
	public  void requestFriendChat(String ori, String des, String info) {
		try {
			out.writeUTF("friend_chat_request");
			out.writeUTF(ori);
			out.writeUTF(des);
			out.writeUTF(info);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->requestFriendChat");
		}
	}
	/**
	 * �����������Ⱥ���죬�ɷ�������des�û���nodeȺת��info�� һ��һ���û���ת����
	 * @param ori �����û���
	 * @param des Ŀ���û���
	 * @param node Ⱥ��
	 * @param info �������Ϣ
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
			System.err.println("����λ�� ServerService->requestGroupChat");
		}
	}
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ��������������ú�����Ϣ
	 * @return �����Լ��ĺ����б�
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
			System.err.println("����λ�� ServerService->firstGetFriendInfoRequest");
		}
		return nodes;
	}
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���������������Ⱥ����Ϣ
	 * @return �����Լ���Ⱥ���б�
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
			System.err.println("����λ�� ServerService->firstGetGroupInfoRequest");
		}
		return nodes;
	}
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���������������Ⱥ�ĳ�Ա��Ϣ
	 * @param node Ŀ��Ⱥ
	 * @return �����Լ���Ⱥ�ĳ�Ա�б�
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
			System.err.println("����λ�� ServerService->firstGetGroupMemberInfoRequest");
		}
		return list;
	}
	/**
	 * ���������Ӧdes�û��ѱ���������nodeȺ���ˣ�Ȼ�������ת��
	 * @param des Ŀ���û���
	 * @param node Ŀ��Ⱥ����
	 * @param members des�û���nodeȺ���е�membersȺ��Ա
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
			System.err.println("����λ�� ServerService->responseInviteFriendtoGroup");
		}
	}
	/**
	 * ��Ⱥ��Ա��Ӧ�Ѿ��ߵ��ó�Ա��
	 * @param des Ŀ���û���
	 * @param node Ŀ��Ⱥ����
	 * @param member Ŀ��Ⱥ��Ա
	 */
	public  void responseKnockFriendFromGroup(String des, GroupNode node, String member) {
		try {
			out.writeUTF("knock_member_response");
			out.writeUTF(des);
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(member);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->responseInviteFriendtoGroup");
		}
	}
	/**
	 * �����ݿ��group_info���������Ϣ
	 * @param node Ŀ��Ⱥ����
	 * @param member Ŀ���Ա��
	 */
	public  void requestInviteGroupMembers(GroupNode node, String member) {
		try {
			out.writeUTF("add_groupMembers_request");
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(member);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->requestInviteGroupMembers");
		}
	}
	/**
	 * �����ݿ��group_info����ɾ����Ϣ
	 * @param node Ŀ��Ⱥ����
	 * @param member Ŀ���Ա��
	 */
	public  void requestDelGroupMembers(GroupNode node, String member) {
		try {
			out.writeUTF("del_groupMembers_request");
			out.writeUTF(node.getOwner());
			out.writeUTF(node.getGname());
			out.writeUTF(member);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->requestAddGroupMembers");
		}
	}
	/**
	 * ��udpͨ�Ž������Է�����ת������Ϣ
	 * @return ����ת������Ϣ��
	 */
	public  String receiver() {
		DatagramPacket packet = null;
		try {
			byte[] buf = new byte[1024];
			packet = new DatagramPacket(buf, buf.length);
			ds.receive(packet);
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->receiver");
		}
		return new String(packet.getData(), 0, packet.getLength());
	}
	/**
	 * ͨ������������͹ر������������������̣߳�������̣߳���closeLogin����
	 */
	public  void closeMain() {
		try {
			out.writeUTF("close_main_request");
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->closeMainConnection");
		}
	}
	/**
	 * ͨ������������͹ر������������������̣߳���closeMain����
	 */
	public  void closeLogin() {
		try {
			out.writeUTF("close_login_request");
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->closeLoginConnection");
		}
	}
	/**
	 * �رտͻ�������ͨ�ŵĽӿ�
	 */
	public  void closeConnection() {
		try {
			in.close();
			out.close();
			ds.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("����λ�� ServerService->close");
		}
	}
}
