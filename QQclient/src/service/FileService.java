package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import component.FriendNode;
import component.GroupNode;
import controller.Controller;
import view.MainFrame;

/**
 * ��û���ļ�������ʱ
 * ����������ļ�
 * �ӷ�����ȡ�����������
 * @author wangzhen
 *
 */
public class FileService {
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���ڱ������������Ⱥ�ĳ�Ա�ļ�
	 * @param node Ⱥ����Ϣ
	 */
	public  void firstCreateGroupMemberFile(GroupNode node) {
		File fdir =  new File("source/"+ MainFrame.getUname() + "/group");
		try {
			if(!fdir.exists()) {
				fdir.mkdirs();
			}
			File file = new File(fdir.getAbsolutePath() +"/"+ node.getOwner() + "-" + node.getGname() + ".txt");
			if(!file.exists()) {
				file.createNewFile();
				
			}
		} catch (Exception e) {
			System.out.println("FileService->firstCreateGroupMemberFile");
		}
	}
	/**
	 * ����Ⱥ�Ĳ�����ʱ���Ѷ�Ӧ��Ⱥ��Ա�ļ�ɾ��
	 * @param node Ŀ��Ⱥ��
	 */
	public  void delGroupMemberFile(GroupNode node) {
		File file =  new File("source/"+ MainFrame.getUname() + "/group/"+ node.getOwner() + "-" + node.getGname() + ".txt");
		if(file.exists()) {
			file.delete();
		}
	}
	/**
	 * �û���ע����Ϻ��һ�ε�¼���ߴ������������¼�����ʱ���ڱ�����������������ļ�
	 */
	public  void firstLoginCreateNeededFile() {
		File file = new File("source/" + MainFrame.getUname());
		// �����û�Ŀ¼
		if (!file.exists()) {
			file.mkdir();
		}
		// �����û������ļ�
		File friendFile = new File(file.getAbsolutePath() + "/friendList.txt");
		if (!friendFile.exists()) {
			try {
				friendFile.createNewFile();
				ArrayList<FriendNode> list = Controller.firstGetFriendInfoRequest();
				Controller.saveMyFriends(list);
			} catch (IOException e) {
				System.err.println("��������friendList�ļ�ʧ�ܣ� λ��: LoginFrame->initUserNeededFile, �û�" + MainFrame.getUname());
			}
		}
		// �����û�Ⱥ���ļ���Ⱥ��Ա�ļ�
		File groupFile = new File(file.getAbsolutePath() + "/groupList.txt");
		if (!groupFile.exists()) {
			try {
				groupFile.createNewFile();
				ArrayList<GroupNode> list = Controller.firstGetGroupInfoRequest();
				Controller.saveMyGroups(list);
				for(GroupNode node : list) {
					DefaultListModel<String> model = Controller.firstGetGroupMemberInfoRequest(node);
					firstCreateGroupMemberFile(node);
					Controller.saveMyGroupMembers(node, model);
				}
			} catch (IOException e) {
				System.err.println("��������groupList�ļ�ʧ�ܣ� λ��: LoginFrame->initUserNeededFile, �û�" + MainFrame.getUname());
			}
		}

	}

}
