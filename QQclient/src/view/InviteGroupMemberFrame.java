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
 * ����Ⱥ��Ա����
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class InviteGroupMemberFrame extends JFrame {
	/**
	 * ������
	 */
	private final static int W = 647;
	/**
	 * ����߶�
	 */
	private final static int H = 500;
	/**
	 * ����x����λ��
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * ����y����λ��
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * Ⱥ��Ϣ
	 */
	private GroupNode curNode;
	
	/**
	 * ȡ��Ⱥ��Ϣ
	 * @return ����Ⱥ��Ϣ
	 */
	public GroupNode getGroupName() {
		return curNode;
	}
	/**
	 * ��ʼ��
	 * @param node Ŀ��Ⱥ
	 */
	public InviteGroupMemberFrame(GroupNode node) {
		this.curNode = node;
		setTitle("\u9080\u8BF7\u65B0\u6210\u5458");
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("���������ChatFrame->ChatFrame()");
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
					// ȡ������ĳ�Ա
					for(FriendNode node : list.getSelectedValuesList()) {
						nodes.add(node.getUname());
						inviteListModel.removeElement(node);
					}
					// ��Ӧ����Ⱥ���Ա������
					for(int i = 0; i < groupMemberModel.getSize(); i++) {
						if(MainFrame.getUname().equals(groupMemberModel.get(i)))
							continue;
						Controller.responseInviteFriendtoGroup(groupMemberModel.getElementAt(i), curNode, nodes);
					}
					for(String string : nodes) {
						Controller.addGroupMembers(curNode, string);
					}
					
					// ��Ӧ����ĳ�Ա
					ArrayList<String> arrayList = new ArrayList<String>();
					System.out.println(groupMemberModel.getSize());
					// ���Ⱥ���г�Ա
					for(int i = 0; i < groupMemberModel.getSize(); i++) {
						if(curNode.getOwner().equals(groupMemberModel.getElementAt(i)))
							continue;
						arrayList.add(groupMemberModel.getElementAt(i));
					}
					// ��ʼ����
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