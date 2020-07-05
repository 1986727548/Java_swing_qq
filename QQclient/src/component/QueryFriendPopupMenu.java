package component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.Controller;
import view.FriendChatFrame;
import view.MainFrame;
/**
 * 查询好友列表的弹出菜单
 * @author wangzhen
 *
 */
public class QueryFriendPopupMenu {
	/**
	 * 弹出菜单
	 */
	private JPopupMenu popupMenu;
	/**
	 * 初始化
	 * @param invoker 弹出菜单的组件
	 * @param x x坐标
	 * @param y y坐标
	 * @param node 进行判断所用
	 */
	
	public QueryFriendPopupMenu(Component invoker, int x, int y, FriendNode node) {
		
		String string = null;
		
		if(Controller.myFriendListContains(node)) {
			string = "发消息";
		}else {
			string = "添加";
		}
		
		popupMenu = new JPopupMenu();
		JMenuItem item1 = new JMenuItem(string);
		popupMenu.add(item1);
		
		if(string.equals("添加")) {
			item1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.requestAddFriend(MainFrame.getUname(), node.getUname());
				}
			});
		}else {
			item1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(Controller.getFriendChatFrame(node.getUname()) == null) {
						FriendChatFrame chatFrame = new FriendChatFrame(node.getUname());
						Controller.addFriendChatFrame(node.getUname(), chatFrame);
					}
					Controller.getFriendChatFrame(node.getUname()).setVisible(true);
				}
			});
		}
		popupMenu.show(invoker, x, y);		
	}
}
