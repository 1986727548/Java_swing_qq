package view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;

import component.GroupNode;
import controller.Controller;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
/**
 * 群聊界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class GroupChatFrame extends JFrame {
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
	 * 邀请好友界面
	 */
	private InviteGroupMemberFrame gframe;
	/**
	 * 存储群成员聊天信息
	 */
	private JTextArea infoArea;
	/**
	 * 群信息
	 */
	private GroupNode node;
	/**
	 * 初始化， node1作为界面标题
	 * @param node1 目标群
	 */
	public GroupChatFrame(GroupNode node1) {
		this.node = node1;
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("主题风格错误，ChatFrame->ChatFrame()");
		}

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(X, Y, W, H);
		getContentPane().setLayout(null);

		setTitle(node);

		JScrollPane contentPane = new JScrollPane();
		contentPane.setBounds(0, 0, 494, 323);
		getContentPane().add(contentPane);

		infoArea = new JTextArea();
		infoArea.setEditable(false);
		contentPane.setViewportView(infoArea);

		JScrollPane inputPane = new JScrollPane();
		inputPane.setBounds(0, 322, 494, 97);
		getContentPane().add(inputPane);

		JTextArea inputArea = new JTextArea();
		inputPane.setViewportView(inputArea);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBounds(0, 417, 494, 52);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);

		JButton closeButton = new JButton("\u5173\u95ED");
		closeButton.setBounds(270, 10, 80, 23);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GroupChatFrame.this.dispose();
			}
		});
		buttonPanel.add(closeButton);

		JButton sendButton = new JButton("\u53D1\u9001");
		sendButton.setFont(new Font("幼圆", Font.PLAIN, 15));
		sendButton.setBounds(372, 10, 80, 23);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res = inputArea.getText();
				if(res != null && res.length() > 0) {
					infoArea.append(MainFrame.getUname() + ":\n");
					infoArea.append("\t" + res + "\n");
					DefaultListModel<String> model = Controller.getMyGroupMembers(node);
					for(int j = 0; j < model.getSize(); j++) {
						if(MainFrame.getUname().equals(model.get(j)))
							continue;
						Controller.requestGroupChat(MainFrame.getUname(), model.get(j), node, res);
					}
					inputArea.setText("");
					inputArea.requestFocus();
				}
			}
		});
		buttonPanel.add(sendButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(493, 166, 138, 305);
		getContentPane().add(scrollPane);

		JList<String> list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("幼圆", Font.PLAIN, 15));
		list.setModel(Controller.getMyGroupMembers(node));
		scrollPane.setViewportView(list);

		JLabel label = new JLabel("\u7FA4\u6210\u5458\uFF1A");
		label.setFont(new Font("幼圆", Font.PLAIN, 15));
		label.setBounds(505, 141, 126, 23);
		getContentPane().add(label);

		JButton button = new JButton();
		button.setFont(new Font("幼圆", Font.PLAIN, 15));
		button.setBounds(504, 53, 107, 33);
		getContentPane().add(button);

		JButton delButton = new JButton("\u8E22\u6389\u8BE5\u6210\u5458");
		delButton.setFont(new Font("幼圆", Font.PLAIN, 15));
		delButton.setBounds(504, 98, 107, 33);
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = list.getSelectedValue();
				// 选中要踢掉的成员,自己是该群的群主
				if (null != value && !value.equals(MainFrame.getUname())) {
					Controller.requestDelGroupMembers(node, value);
					DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
					model.removeElement(value);
					for (int j = 0; j < model.getSize(); j++) {
						if (MainFrame.getUname().equals(model.get(j)))
							continue;
						System.out.println("mm->" + model.get(j));
						Controller.responseKnockFriendFromGroup(model.get(j), node, value);
					}
				}
			}
		});
		// 群主权力和成员权力
		if (MainFrame.getUname().equals(node.getOwner())) {
			getContentPane().add(delButton);
			button.setText("解散群聊");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
					for(int j = 0; j < model.getSize(); j++) {
						if (MainFrame.getUname().equals(model.get(j)))
							continue;
						Controller.requestDelGroupMembers(node, model.get(j));
					}
					Controller.requestDelGroupMembers(node, MainFrame.getUname());
					Controller.requestDelGroup(node);
				}
			});
		} else {
			button.setText("退出群聊");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String value = MainFrame.getUname();
					DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();

					for (int j = 0; j < model.getSize(); j++) {
						if (MainFrame.getUname().equals(model.get(j)))
							continue;
						System.out.println("mm->" + model.get(j));
						Controller.responseKnockFriendFromGroup(model.get(j), node, value);
					}

					Controller.requestDelGroupMembers(node, value);

				}
			});
		}

		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				if (gframe != null && gframe.getGroupName().equals(node)) {
					gframe.dispose();
				}
			}

		});

		JButton inviteButton = new JButton("\u9080\u8BF7\u65B0\u6210\u5458");
		inviteButton.setFont(new Font("幼圆", Font.PLAIN, 15));
		inviteButton.setBounds(504, 10, 107, 33);
		inviteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gframe != null) {
					gframe.dispose();
				}
				gframe = new InviteGroupMemberFrame(node);
				gframe.setVisible(true);

			}
		});
		getContentPane().add(inviteButton);

	}
	/**
	 * 添加聊天信息
	 * @param msg 聊天信息
	 */
	public void append(String msg) {
		infoArea.append(msg);
	}
	/**
	 * 设置聊天框的标题
	 * @param node 用作标题
	 */
	public void setTitle(GroupNode node) {
		this.node = node;
		setFont(new Font("System", Font.PLAIN, 14));
		FontMetrics fm = getFontMetrics(getFont());
		String title = node.getGname() + "(" + node.getOwner() + ")";
		int realW = getWidth() / 2 - (fm.stringWidth(title) / 2);
		int needC = realW / fm.stringWidth(" ") - 25;
		setTitle(String.format("%" + needC + "s", "") + title);
	}

}
