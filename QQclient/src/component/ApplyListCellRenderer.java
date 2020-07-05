package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;




/**
 * 用于好友申请列表的界面修饰
 * @author wangzhen
 *
 */
@SuppressWarnings("serial") // Same-version serialization only
public class ApplyListCellRenderer extends JLabel implements ListCellRenderer<Object>{
    /**
       * 初始化
     */
	public ApplyListCellRenderer() {
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
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setPreferredSize(new Dimension(450, 35));
        setText("   " +(String)value);
        // 设置选中和未选中的颜色
        if (isSelected) {
            setBackground(Color.lightGray);
        } else {
            setBackground(Color.white);
        }
        return this;
    }
	

}
