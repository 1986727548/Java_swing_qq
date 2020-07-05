package component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.Controller;
import view.MainFrame;
import view.AddFriendFrame;

/**
 * 好友列表的弹出菜单
 * @author wangzhen
 *
 */
public class MyFriendPopupMenu {
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
	public MyFriendPopupMenu(Component invoker, int x, int y, FriendNode node) {

		popupMenu = new JPopupMenu();
		JMenuItem addItem = new JMenuItem("添加好友");
		popupMenu.add(addItem);
		// 有好友的选项才有删除好友菜单
		if(node != null) {
			JMenuItem deleteItem = new JMenuItem("删除好友");
			popupMenu.add(deleteItem);
			deleteItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.deleteFriend(node);	
					Controller.responseDelFriend(MainFrame.getUname(), node.getUname());
				}
			});
		}
		
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddFriendFrame().setVisible(true);
			}
		});
		
		

		popupMenu.show(invoker, x, y);
	}
}
