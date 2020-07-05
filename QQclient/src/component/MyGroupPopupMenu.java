package component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.Controller;
import view.MainFrame;

/**
 * Ⱥ���б�ĵ����˵�
 * @author wangzhen
 *
 */
public class MyGroupPopupMenu {
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

	public MyGroupPopupMenu(Component invoker, int x, int y, GroupNode node) {

		popupMenu = new JPopupMenu();
		JMenuItem createItem = new JMenuItem("����Ⱥ��");
		popupMenu.add(createItem);
		
		createItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.requestCreateGroup();
			}
		});
		
		if(node != null && node.getOwner().equals(MainFrame.getUname())) {
			JMenuItem updItem = new JMenuItem("�޸�Ⱥ��");
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
