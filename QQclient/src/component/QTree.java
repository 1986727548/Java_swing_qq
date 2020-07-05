package component;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import controller.Controller;
import model.MyFriendList;
import model.MyGroupList;
import model.QueryFriendList;
import view.FriendChatFrame;
import view.GroupChatFrame;

/**
 * ֧�ź����б�Ⱥ���б���ѯ�����б�
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class QTree extends JTree{
	/**
	 * ��ʼ��
	 * @param model ĳ�б������ģ��
	 * @param comp ĳ�б������жϵ����˵�
	 */
	public QTree(DefaultTreeModel model, Object comp) {
		setModel(model);
		setUI(new QTreeUI());
		
		setRowHeight(55);
		// ����ʾ��
		setRootVisible(false);
		putClientProperty("JTree.lineStyle", "Horizontal");
		setFont(new Font("΢���ź�", Font.PLAIN, 15));
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				TreePath path = getPathForLocation(x, y);
				if(path != null) {
					if(comp.getClass() == MyGroupList.class) {
						// ������GroupNodeΪ�ڵ���б�
						GroupNode node = (GroupNode)path.getLastPathComponent();
						if (e.getClickCount() == 2 && node.isLeaf()) {// ˫��
							if(Controller.getGroupChatFrame(node) == null) {
								//System.out.println(node);
								GroupChatFrame chatFrame = new GroupChatFrame(node);
								Controller.addGroupChatFrame(node, chatFrame);
							}
							Controller.getGroupChatFrame(node).setVisible(true);
						}
						if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {// �һ�
							new MyGroupPopupMenu(e.getComponent(), x, y, node);
						}
					}else {
						// ������FriendNodeΪ�ڵ���б�
						FriendNode node = (FriendNode) path.getLastPathComponent();
						if (e.getClickCount() == 2 && node.isLeaf()) {
							if(Controller.getFriendChatFrame(node.getUname()) == null) {
								FriendChatFrame chatFrame = new FriendChatFrame(node.getUname());
								Controller.addFriendChatFrame(node.getUname(), chatFrame);
							}
							Controller.getFriendChatFrame(node.getUname()).setVisible(true);
							
						}
						if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
							if(comp.getClass() == QueryFriendList.class) {
								new QueryFriendPopupMenu(e.getComponent(), x, y, node);
							}
							if(comp.getClass() == MyFriendList.class) {
								new MyFriendPopupMenu(e.getComponent(), x, y, node);
							}
						}
					}
				}else {
					// �һ��հ�����
					if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
						if(comp.getClass() == MyFriendList.class) {
							new MyFriendPopupMenu(QTree.this, x, y, null);
						}
						if(comp.getClass() == MyGroupList.class) {
							new MyGroupPopupMenu(QTree.this, x, y, null);
						}
					}
				}
				
			}

		});
		// ���û����꽹�㣬����չʾ��ͬ�ı�����ɫ
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				TreePath path = getPathForLocation(e.getX(), e.getY());
				setSelectionPath(path);
			}
		});
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

}
