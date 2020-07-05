package view;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import controller.Controller;

import javax.swing.JTextArea;

/**
 * �����������
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class FriendChatFrame extends JFrame {
	/**
	 * ������
	 */
	private final static int W = 500;
	/**
	 * ����߶�
	 */
	private final static int H = 500;
	/**
	 * ����x����λ��
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * ����y����λ��
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * �洢����������Ϣ
	 */
	private JTextArea infoArea;
	/**
	 * ��ʼ���� uname��Ϊ�������
	 * @param uname Ŀ�������
	 */
	public FriendChatFrame(String uname) {
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("���������ChatFrame->ChatFrame()");
		}
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(X, Y, W, H);
		getContentPane().setLayout(null);
		
		setFont(new Font("System", Font.PLAIN, 14));   
		FontMetrics fm = getFontMetrics(getFont());   
		int realW = getWidth()/2 - (fm.stringWidth(uname)/2);  
		int needC = realW/fm.stringWidth(" ") -12;    
		setTitle(String.format("%"+needC+"s", "")+uname);

		JScrollPane contentPane = new JScrollPane();
		contentPane.setBounds(0, 0, 494, 323);
		getContentPane().add(contentPane);
		
		infoArea = new JTextArea();
		infoArea.setEditable(false);
		contentPane.setViewportView(infoArea);
		
		JScrollPane inputPane = new JScrollPane();
		inputPane.setBounds(0, 323, 494, 97);
		getContentPane().add(inputPane);
		
		JTextArea inputArea = new JTextArea();
		inputPane.setViewportView(inputArea);
		
		JButton closeButton = new JButton("\u5173\u95ED");
		closeButton.setBounds(251, 430, 93, 23);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FriendChatFrame.this.dispose();
			}
		});
		getContentPane().add(closeButton);
		
		JButton sendButton = new JButton("\u53D1\u9001");
		sendButton.setBounds(370, 430, 93, 23);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res = inputArea.getText();
				if(res != null && res.length() > 0) {
					infoArea.append(MainFrame.getUname() + ":\n");
					infoArea.append("\t" + res + "\n");
					Controller.requestFriendChat(MainFrame.getUname(), uname, res);
					inputArea.setText("");
					inputArea.requestFocus();
				}
			}
		});
		getContentPane().add(sendButton);
		
	}
	/**
	 * ���������Ϣ
	 * @param msg ������Ϣ
	 */
	public void append(String msg) {
		infoArea.append(msg);
	}
}

