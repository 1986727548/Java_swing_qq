package view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import component.FriendNode;
import controller.Controller;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
/**
 * 添加好友界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class AddFriendFrame extends JFrame {
	/**
	 * 输入好友名称进行模糊查询
	 */
	private JTextField inputTextField;
	/**
	 * 查询按钮
	 */
	private JButton queryButton;
	/**
	 * 用于修饰查询好友列表的面板
	 */
	private JScrollPane scrollPane;
	/**
	 * 初始化
	 */
	public AddFriendFrame() {	
		setTitle("添加好友");
		setBounds(100, 100, 429, 430);
		getContentPane().setLayout(null);
		
		inputTextField = new JTextField();
		inputTextField.setFont(new Font("黑体", Font.PLAIN, 15));
		inputTextField.setBounds(45, 17, 226, 32);
		getContentPane().add(inputTextField);
		inputTextField.setColumns(10);
		
		
		queryButton = new JButton("\u67E5\u8BE2");
		queryButton.setBackground(new Color(245, 255, 250));
		queryButton.setFont(new Font("黑体", Font.PLAIN, 15));
		queryButton.setBounds(303, 17, 66, 32);
		getContentPane().add(queryButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 65, 413, 326);
		
		scrollPane.setViewportView(Controller.getQueryFriendList());
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		addListener();
	}
	/**
	 * 为查询按钮添加监听事件
	 */
	private void addListener() {
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = inputTextField.getText();
				if(!string.equals("")) {
					 ArrayList<FriendNode> nodes = Controller.requestQueryFriend(string);
					 Controller.updateQueryFriendList(nodes);
				}
				inputTextField.setText("");
			}
		});
		
	}

}
