package view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import component.FriendNode;
import component.GroupNode;
import controller.Controller;
import model.User;

import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Font;

/**
 * 主界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements Runnable{
	/**
	 * 自己账号信息
	 */
	private static User user = null;
	/**
	 * 界面宽度
	 */
	private final static int MAIN_W = 360;
	/**
	 * 界面高度
	 */
	private final static int MAIN_H = 600;
	/**
	 * 界面x坐标位置
	 */
	private final static int MAIN_X = (Toolkit.getDefaultToolkit().getScreenSize().width - MAIN_W) / 2;
	/**
	 * 界面y坐标位置
	 */
	private final static int MAIN_Y = (Toolkit.getDefaultToolkit().getScreenSize().height - MAIN_H) / 2;
	/**
	 * 头部面板
	 */
	private final JPanel headPanel = new JPanel();
	/**
	 * 添加好友按钮
	 */
	private JButton addFriendButton;
	/**
	 * 创建群聊按钮
	 */
	private JButton createGroupButton;
	/**
	 * 申请列表按钮
	 */
	private JButton applyButton;
	/**
	 * 申请界面
	 */
	private ApplyFrame applyFrame = new ApplyFrame();
	/**
	 * 初始化
	 */
	public MainFrame() {
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("主题风格错误，MainFrame->MainFrame()");
		}
		
		Controller.initDataService();

		setTitle("QQ");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(MAIN_X, MAIN_Y, MAIN_W, MAIN_H);
		getContentPane().setLayout(null);
		headPanel.setBounds(0, 0, 352, 128);		
		getContentPane().add(headPanel);
		headPanel.setLayout(null);
		
		JLabel portraitLabel = new JLabel("");
		portraitLabel.setBounds(27, 30, 80, 73);
		ImageIcon portraitIcon = new ImageIcon("source/online.jpg");
		portraitIcon.setImage(portraitIcon.getImage().getScaledInstance(80, 73, Image.SCALE_DEFAULT));
		portraitLabel.setIcon(portraitIcon);
		headPanel.add(portraitLabel);
		
		JLabel unameLabel = new JLabel("\u7528\u6237\u540D\uFF1A" + getUname());
		unameLabel.setFont(new Font("幼圆", Font.PLAIN, 15));
		unameLabel.setBounds(117, 32, 225, 27);
		headPanel.add(unameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 162, 348, 376);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(Controller.getMyFriendList());
		getContentPane().add(scrollPane);
		
		JToggleButton friendButton = new JToggleButton("\u8054\u7CFB\u4EBA");
		friendButton.setFont(new Font("幼圆", Font.PLAIN, 18));
		friendButton.setSelected(true);
		friendButton.setBounds(0, 127, 182, 37);
		getContentPane().add(friendButton);
		
		JToggleButton groupButton = new JToggleButton("\u7FA4\u804A");
		groupButton.setFont(new Font("幼圆", Font.PLAIN, 18));
		groupButton.setBounds(177, 127, 177, 37);
		getContentPane().add(groupButton);
		
		friendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(Controller.getMyFriendList());		
				friendButton.setSelected(true);
				groupButton.setSelected(false);
			}
		});
		
		groupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(Controller.getMyGroupList());	
				friendButton.setSelected(false);
				groupButton.setSelected(true);
			}
		});
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 540, 348, 25);
		getContentPane().add(toolBar);
		
		addFriendButton = new JButton("");
		ImageIcon addFriendIcon = new ImageIcon("source/addFriend.png");
		addFriendIcon.setImage(addFriendIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		addFriendButton.setIcon(addFriendIcon);
		addFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddFriendFrame().setVisible(true);
			}
		});
		toolBar.add(addFriendButton);
		
		createGroupButton = new JButton("");
		ImageIcon createGroupIcon = new ImageIcon("source/createGroup.png");
		createGroupIcon.setImage(createGroupIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		createGroupButton.setIcon(createGroupIcon);
		createGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.requestCreateGroup();
			}
		});
		toolBar.add(createGroupButton);
		
		applyButton = new JButton("");
		ImageIcon applyIcon = new ImageIcon("source/apply.png");
		applyIcon.setImage(applyIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		applyButton.setIcon(applyIcon);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyFrame.setVisible(true);			
			}
		});
		toolBar.add(applyButton);
		
		
		
		new Thread(this).start();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Controller.responseNotOnline(getUname(), Controller.getMyFriends());
				Controller.closeMain();	
			}
		});
	}
	/**
	 * 用于接收来自服务器的信息
	 */
	public void run() {
		Controller.responseOnline(getUname(), Controller.getMyFriends());
		loop:while(true) {
			String temp = Controller.receiver();
			String s[] = temp.split(",", 2);
			String[] strings = s[1].split(",", Integer.parseInt(s[0]));
			switch (strings[0]) {
			case "online_response":
				Controller.setOnlineState(strings[1], true);
				break;
			case "not_online_response":
				Controller.setOnlineState(strings[1], false);
				break;
			case "add_friend_request":
				int res = JOptionPane.showConfirmDialog(MainFrame.this, "是否成为用户" + strings[1] + "的好友", "添加好友请求", JOptionPane.YES_NO_CANCEL_OPTION);
				/*
				 * 0 -> 是
				 * 1 -> 否
				 * 2 -> 取消
				 * -1 -> 点击了关闭按钮
				 */
				if(res == 0) {
					Controller.addFriend(new FriendNode(strings[1], true));
					Controller.responseAddFriend(user.getUname(), strings[1]);
				}
				if(res == 2 || res == -1) {
					applyFrame.addApply("好友请求：" + strings[1]);
				}
				break;
			case "add_friend_response":
				Controller.addFriend(new FriendNode(strings[1], true));
				break;
			case "del_friend_response":
				Controller.deleteFriend(new FriendNode(strings[1]));
				Controller.delFriendChatFrame(strings[1]);
				break;
			case "invite_friend_response":
				GroupNode node = new GroupNode(strings[2], strings[1]);
				Controller.addGroup(node);
				for(int i = 3; i < strings.length; i++) {
					Controller.addGroupMembers(node, strings[i]);
				}
				break; 
			case "update_group_name_response":
				GroupNode node4 = new GroupNode(strings[2], strings[1]);
				Controller.updateGroupName(node4, strings[3]);
				break;
			case "knock_member_response":
				GroupNode node1 = new GroupNode(strings[2], strings[1]);
				Controller.delGroupMembers(node1, strings[3]);
				break;
			case "del_group_response":
				GroupNode node2 = new GroupNode(strings[2], strings[1]);
				Controller.deleteGroup(node2);
				Controller.delGroupMemberFile(node2);
				Controller.delGroupChatFrame(node2);
				break;
			case "friend_chat_response":
				Controller.addFriendChatInfo(strings[1], strings[1] + ":\n");
				Controller.addFriendChatInfo(strings[1], "\t"+strings[2] + "\n");
				break;
			case "group_chat_response":
				GroupNode node3 = new GroupNode(strings[3], strings[2]);
				Controller.addGroupChatInfo(node3, strings[1] + ":\n");
				Controller.addGroupChatInfo(node3, "\t" + strings[4] + "\n");
				break;
			case "close_response":
				break loop;
			default:
				break;
			}
		}
		Controller.saveSource();
		Controller.closeConnection();
	}
	/**
	 * 设置用户信息
	 * @param user 目标用户
	 */
	public static void setUser(User user) {
		MainFrame.user = user;
	}
	/**
	 * 取得用户名
	 * @return 返回用户名
	 */
	public static String getUname() {
		return MainFrame.user.getUname();
	}
}
