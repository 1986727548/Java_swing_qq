package view;

import java.awt.Toolkit;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import component.FriendNode;
import component.ApplyListCellRenderer;
import controller.Controller;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 用于处理添加好友请求的界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class ApplyFrame extends JFrame{
	/**
	 * 界面宽度
	 */
	private final static int W = 265;
	/**
	 * 界面高度
	 */
	private final static int H = 314;
	/**
	 * 界面x坐标位置
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * 界面y坐标位置
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * 好友请求集合
	 */
	private static JList<String> list = new JList<String>();
	/**
	 * 好友请求数据模型
	 */
	private DefaultListModel<String> model = new DefaultListModel<String>();
	/**
	 * 添加好友请求
	 * @param apppyString 好友请求信息
	 */
	public void addApply(String apppyString) {
		if(!model.contains(apppyString)) {
			model.addElement(apppyString);
		}
	}
	/**
	 * 初始化
	 */
	public ApplyFrame() {
		setTitle("\u672A\u5904\u7406\u8BF7\u6C42");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(X, Y, W, H);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setBounds(0, 0, 249, 244);
		getContentPane().add(scrollPane);
		
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ApplyListCellRenderer());
		scrollPane.setViewportView(list);
		
		JButton yesButton = new JButton("\u540C\u610F");
		yesButton.setBounds(24, 248, 93, 23);
		yesButton.addActionListener(new ActionListener() {
			/**
			 * 处理接收请求
			 */
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index != -1) {
					// 好友请求：这5个字符要去掉
					String string = list.getSelectedValue().substring(5);
					Controller.addFriend(new FriendNode(string, true));
					Controller.responseAddFriend(MainFrame.getUname(), string);
					model.remove(index);
				}
			}
		});
		getContentPane().add(yesButton);
		
		JButton noBbutton = new JButton("\u62D2\u7EDD");
		noBbutton.addActionListener(new ActionListener() {
			/**
			 * 处理拒绝请求
			 */
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index != -1) {
					model.remove(index);
				}
			}
		});
		noBbutton.setBounds(127, 248, 93, 23);
		getContentPane().add(noBbutton);
		
		
	}
}
