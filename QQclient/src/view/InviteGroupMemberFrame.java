package view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.UIManager;

import component.FriendNode;
import component.GroupNode;
import component.InviteListCellRenderer;
import controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
/**
 * 邀请群成员界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class InviteGroupMemberFrame extends JFrame {
	/**
	 * 界面宽度
	 */
	private final static int W = 647;
	/**
	 * 界面高度
	 */
	private final static int H = 500;
	/**
	 * 界面x坐标位置
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * 界面y坐标位置
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * 群信息
	 */
	private GroupNode curNode;
	
	/**
	 * 取得群信息
	 * @return 返回群信息
	 */
	public GroupNode getGroupName() {
		return curNode;
	}
	/**
	 * 初始化
	 * @param node 目标群
	 */
	public InviteGroupMemberFrame(GroupNode node) {
		this.curNode = node;
		setTitle("\u9080\u8BF7\u65B0\u6210\u5458");
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("主题风格错误，ChatFrame->ChatFrame()");
		}
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(X, Y, 364, 333);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 348, 262);
		JList<FriendNode> list = new JList<FriendNode>();
		
		DefaultListModel<FriendNode> inviteListModel = new DefaultListModel<FriendNode>();
		DefaultListModel<String> groupMemberModel = Controller.getMyGroupMembers(curNode);
		
		for(FriendNode fNode : Controller.getMyFriends()) {
			if(!groupMemberModel.contains(fNode.getUname()))
				inviteListModel.addElement(fNode);
		}
		list.setModel(inviteListModel);
		list.setCellRenderer(new InviteListCellRenderer());
		
		scrollPane.setViewportView(list);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		JButton button = new JButton("\u9080\u8BF7");
		button.setBounds(120, 266, 93, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValuesList().size() > 0) {
					ArrayList<String> nodes = new ArrayList<String>();
					// 取得邀请的成员
					for(FriendNode node : list.getSelectedValuesList()) {
						nodes.add(node.getUname());
						inviteListModel.removeElement(node);
					}
					// 响应已在群里成员的邀请
					for(int i = 0; i < groupMemberModel.getSize(); i++) {
						if(MainFrame.getUname().equals(groupMemberModel.get(i)))
							continue;
						Controller.responseInviteFriendtoGroup(groupMemberModel.getElementAt(i), curNode, nodes);
					}
					for(String string : nodes) {
						Controller.addGroupMembers(curNode, string);
					}
					
					// 响应邀请的成员
					ArrayList<String> arrayList = new ArrayList<String>();
					System.out.println(groupMemberModel.getSize());
					// 获得群所有成员
					for(int i = 0; i < groupMemberModel.getSize(); i++) {
						if(curNode.getOwner().equals(groupMemberModel.getElementAt(i)))
							continue;
						arrayList.add(groupMemberModel.getElementAt(i));
					}
					// 开始邀请
					for(String string : nodes) {
						Controller.requestInviteGroupMembers(curNode, string);
						Controller.responseInviteFriendtoGroup(string, curNode, arrayList);
					}
					
					
				}
			}
		});
		getContentPane().add(button);
		
	}
}