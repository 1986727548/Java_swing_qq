package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 * 修饰邀请好友进群的界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class InviteListCellRenderer extends JLabel implements ListCellRenderer<FriendNode> {

	public InviteListCellRenderer() {
		setOpaque(true);
	}

	 /**
     * Return a component that has been configured to display the specified
     * value. That component's <code>paint</code> method is then called to
     * "render" the cell.  If it is necessary to compute the dimensions
     * of a list because the list cells do not have a fixed size, this method
     * is called to generate a component on which <code>getPreferredSize</code>
     * can be invoked.
     *
     * @param list The JList we're painting.
     * @param value The value returned by list.getModel().getElementAt(index).
     * @param index The cells index.
     * @param isSelected True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     *
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
	public Component getListCellRendererComponent(JList<? extends FriendNode> list, FriendNode value, int index,
			boolean isSelected, boolean cellHasFocus) {
		FriendNode friendNode = value;

		if (friendNode.isLeaf()) {
			setPreferredSize(new Dimension(420, 50));
			ImageIcon icon = null;
			if (friendNode.isState()) {
				icon = new ImageIcon("source/online.jpg");
			} else {
				icon = new ImageIcon("source/notOnline.jpg");
			}
			icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			setIcon(icon);
		}

		if (isSelected) {
			setBackground(Color.lightGray);
		} else {
			setBackground(Color.white);
		}

		setText(friendNode.getUname());
		setIconTextGap(15);

		return this;
	}

}
