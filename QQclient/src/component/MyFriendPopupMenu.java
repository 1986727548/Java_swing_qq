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
 * �����б�ĵ����˵�
 * @author wangzhen
 *
 */
public class MyFriendPopupMenu {
	/**
	 * �����˵�
	 */
	private JPopupMenu popupMenu;
	/**
	 * ��ʼ��
	 * @param invoker �����˵������
	 * @param x x����
	 * @param y y����
	 * @param node �����ж�����
	 */
	public MyFriendPopupMenu(Component invoker, int x, int y, FriendNode node) {

		popupMenu = new JPopupMenu();
		JMenuItem addItem = new JMenuItem("��Ӻ���");
		popupMenu.add(addItem);
		// �к��ѵ�ѡ�����ɾ�����Ѳ˵�
		if(node != null) {
			JMenuItem deleteItem = new JMenuItem("ɾ������");
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
