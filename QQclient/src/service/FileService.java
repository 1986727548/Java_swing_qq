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
 * 当没有文件和数据时
 * 建立必需的文件
 * 从服务器取到必需的数据
 * @author wangzhen
 *
 */
public class FileService {
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候在本机创建必需的群聊成员文件
	 * @param node 群聊信息
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
	 * 当该群聊不存在时，把对应的群成员文件删除
	 * @param node 目标群聊
	 */
	public  void delGroupMemberFile(GroupNode node) {
		File file =  new File("source/"+ MainFrame.getUname() + "/group/"+ node.getOwner() + "-" + node.getGname() + ".txt");
		if(file.exists()) {
			file.delete();
		}
	}
	/**
	 * 用户在注册完毕后第一次登录或者从另外的主机登录，这个时候在本机创建必需的数据文件
	 */
	public  void firstLoginCreateNeededFile() {
		File file = new File("source/" + MainFrame.getUname());
		// 建立用户目录
		if (!file.exists()) {
			file.mkdir();
		}
		// 建立用户好友文件
		File friendFile = new File(file.getAbsolutePath() + "/friendList.txt");
		if (!friendFile.exists()) {
			try {
				friendFile.createNewFile();
				ArrayList<FriendNode> list = Controller.firstGetFriendInfoRequest();
				Controller.saveMyFriends(list);
			} catch (IOException e) {
				System.err.println("自主创建friendList文件失败， 位置: LoginFrame->initUserNeededFile, 用户" + MainFrame.getUname());
			}
		}
		// 建立用户群聊文件和群成员文件
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
				System.err.println("自主创建groupList文件失败， 位置: LoginFrame->initUserNeededFile, 用户" + MainFrame.getUname());
			}
		}

	}

}
