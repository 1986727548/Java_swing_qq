package component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.Controller;
import view.MainFrame;

/**
 * 群聊列表的弹出菜单
 * @author wangzhen
 *
 */
public class MyGroupPopupMenu {
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

	public MyGroupPopupMenu(Component invoker, int x, int y, GroupNode node) {

		popupMenu = new JPopupMenu();
		JMenuItem createItem = new JMenuItem("创建群聊");
		popupMenu.add(createItem);
		
		createItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.requestCreateGroup();
			}
		});
		
		if(node != null && node.getOwner().equals(MainFrame.getUname())) {
			JMenuItem updItem = new JMenuItem("修改群名");
			popupMenu.add(updItem);
			updItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.requestUpdateGroupName(node);	
				}
			});
		}
		

		popupMenu.show(invoker, x, y);
	}
}
