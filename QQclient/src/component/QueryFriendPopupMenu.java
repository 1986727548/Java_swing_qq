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
 * ��ѯ�����б�ĵ����˵�
 * @author wangzhen
 *
 */
public class QueryFriendPopupMenu {
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
	
	public QueryFriendPopupMenu(Component invoker, int x, int y, FriendNode node) {
		
		String string = null;
		
		if(Controller.myFriendListContains(node)) {
			string = "����Ϣ";
		}else {
			string = "���";
		}
		
		popupMenu = new JPopupMenu();
		JMenuItem item1 = new JMenuItem(string);
		popupMenu.add(item1);
		
		if(string.equals("���")) {
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
