package view;

import java.awt.Toolkit;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import component.FriendNode;
import component.ApplyListCellRenderer;
import controller.Controller;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * ���ڴ�����Ӻ�������Ľ���
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class ApplyFrame extends JFrame{
	/**
	 * ������
	 */
	private final static int W = 265;
	/**
	 * ����߶�
	 */
	private final static int H = 314;
	/**
	 * ����x����λ��
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * ����y����λ��
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * �������󼯺�
	 */
	private static JList<String> list = new JList<String>();
	/**
	 * ������������ģ��
	 */
	private DefaultListModel<String> model = new DefaultListModel<String>();
	/**
	 * ��Ӻ�������
	 * @param apppyString ����������Ϣ
	 */
	public void addApply(String apppyString) {
		if(!model.contains(apppyString)) {
			model.addElement(apppyString);
		}
	}
	/**
	 * ��ʼ��
	 */
	public ApplyFrame() {
		setTitle("\u672A\u5904\u7406\u8BF7\u6C42");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(X, Y, W, H);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setBounds(0, 0, 249, 244);
		getContentPane().add(scrollPane);
		
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ApplyListCellRenderer());
		scrollPane.setViewportView(list);
		
		JButton yesButton = new JButton("\u540C\u610F");
		yesButton.setBounds(24, 248, 93, 23);
		yesButton.addActionListener(new ActionListener() {
			/**
			 * �����������
			 */
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index != -1) {
					// ����������5���ַ�Ҫȥ��
					String string = list.getSelectedValue().substring(5);
					Controller.addFriend(new FriendNode(string, true));
					Controller.responseAddFriend(MainFrame.getUname(), string);
					model.remove(index);
				}
			}
		});
		getContentPane().add(yesButton);
		
		JButton noBbutton = new JButton("\u62D2\u7EDD");
		noBbutton.addActionListener(new ActionListener() {
			/**
			 * ����ܾ�����
			 */
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index != -1) {
					model.remove(index);
				}
			}
		});
		noBbutton.setBounds(127, 248, 93, 23);
		getContentPane().add(noBbutton);
		
		
	}
}
