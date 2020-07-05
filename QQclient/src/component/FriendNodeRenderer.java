package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 * 修饰好友列表和查询好友列表的界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial") // Same-version serialization only
public class FriendNodeRenderer extends DefaultTreeCellRenderer {
	
	   /**
      * Configures the renderer based on the passed in components.
      * The value is set from messaging the tree with
      * <code>convertValueToText</code>, which ultimately invokes
      * <code>toString</code> on <code>value</code>.
      * The foreground color is set based on the selection and the icon
      * is set based on the <code>leaf</code> and <code>expanded</code>
      * parameters.
     *
     * @param tree      the receiver is being configured for
     * @param value     the value to render
     * @param sel 		whether node is selected
     * @param expanded  whether node is expanded
     * @param leaf      whether node is a lead node
     * @param row       row index
     * @param hasFocus  whether node has focus
     * @return          the {@code Component} that the renderer uses to draw the value
     */

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		// 将value转化为节点对象
		FriendNode friendNode = (FriendNode) value;
	
		if(friendNode.isLeaf()){
			setPreferredSize(new Dimension(420, 50));
			ImageIcon icon = null;
			if(friendNode.isState()) {
				icon = new ImageIcon("source/online.jpg");
			}else {
				icon = new ImageIcon("source/notOnline.jpg");
			}
			icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			setIcon(icon);
		}	
	
		setBackgroundSelectionColor(Color.lightGray);
		setBackgroundNonSelectionColor(Color.white);
		
		setText(friendNode.getUname());
		setIconTextGap(15);
		
		return this;

	}



}

