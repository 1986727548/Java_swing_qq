package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 修饰群聊列表
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class GroupNodeRenderer extends DefaultTreeCellRenderer {
	 /**
     * Sets the value of the current tree cell to <code>value</code>.
     * If <code>selected</code> is true, the cell will be drawn as if
     * selected. If <code>expanded</code> is true the node is currently
     * expanded and if <code>leaf</code> is true the node represents a
     * leaf and if <code>hasFocus</code> is true the node currently has
     * focus. <code>tree</code> is the <code>JTree</code> the receiver is being
     * configured for.  Returns the <code>Component</code> that the renderer
     * uses to draw the value.
     * <p>
     * The <code>TreeCellRenderer</code> is also responsible for rendering the
     * the cell representing the tree's current DnD drop location if
     * it has one. If this renderer cares about rendering
     * the DnD drop location, it should query the tree directly to
     * see if the given row represents the drop location:
     * <pre>
     *     JTree.DropLocation dropLocation = tree.getDropLocation();
     *     if (dropLocation != null
     *             &amp;&amp; dropLocation.getChildIndex() == -1
     *             &amp;&amp; tree.getRowForPath(dropLocation.getPath()) == row) {
     *
     *         // this row represents the current drop location
     *         // so render it specially, perhaps with a different color
     *     }
     * </pre>
     *
     * @param tree      the receiver is being configured for
     * @param value     the value to render
     * @param sel  whether node is selected
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
		GroupNode groupNode = (GroupNode) value;
	
		if(groupNode.isLeaf()){
			setPreferredSize(new Dimension(420, 50));
			ImageIcon icon = new ImageIcon("source/group.png");
			icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			setIcon(icon);
		}	
	
		setBackgroundSelectionColor(Color.lightGray);
		setBackgroundNonSelectionColor(Color.white);
		
		setText(groupNode.getGname() + "(" + groupNode.getOwner() + ")");
		setIconTextGap(15);
		
		return this;

	}



}

