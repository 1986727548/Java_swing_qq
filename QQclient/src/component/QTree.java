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
 * 支撑好友列表，群聊列表，查询好友列表
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class QTree extends JTree{
	/**
	 * 初始化
	 * @param model 某列表的数据模型
	 * @param comp 某列表，用来判断弹出菜单
	 */
	public QTree(DefaultTreeModel model, Object comp) {
		setModel(model);
		setUI(new QTreeUI());
		
		setRowHeight(55);
		// 不显示根
		setRootVisible(false);
		putClientProperty("JTree.lineStyle", "Horizontal");
		setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				TreePath path = getPathForLocation(x, y);
				if(path != null) {
					if(comp.getClass() == MyGroupList.class) {
						// 处理以GroupNode为节点的列表
						GroupNode node = (GroupNode)path.getLastPathComponent();
						if (e.getClickCount() == 2 && node.isLeaf()) {// 双击
							if(Controller.getGroupChatFrame(node) == null) {
								//System.out.println(node);
								GroupChatFrame chatFrame = new GroupChatFrame(node);
								Controller.addGroupChatFrame(node, chatFrame);
							}
							Controller.getGroupChatFrame(node).setVisible(true);
						}
						if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {// 右击
							new MyGroupPopupMenu(e.getComponent(), x, y, node);
						}
					}else {
						// 处理以FriendNode为节点的列表
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
					// 右击空白区域
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
		// 设置获得鼠标焦点，用于展示不同的背景颜色
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				TreePath path = getPathForLocation(e.getX(), e.getY());
				setSelectionPath(path);
			}
		});
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

}
