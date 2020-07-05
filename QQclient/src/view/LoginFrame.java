package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import controller.Controller;
import model.User;
/**
 * ��¼����
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	/**
	 * ������
	 */
	private final static int LOGIN_W = 415;
	/**
	 * ����߶�
	 */
	private final static int LOGIN_H = 332;
	/**
	 * ����x����λ��
	 */
	private final static int LOGIN_X = (Toolkit.getDefaultToolkit().getScreenSize().width - LOGIN_W) / 2;
	/**
	 * ����y����λ��
	 */
	private final static int LOGIN_Y = (Toolkit.getDefaultToolkit().getScreenSize().height - LOGIN_H) / 2;
	/**
	 * �û�����
	 */
	private JTextField unameField;
	/**
	 * �����
	 */
	private JPasswordField pswordField;
	/**
	 * ��¼��ť
	 */
	private JButton loginButton;
	/**
	 * ע�ᰴť
	 */
	private JButton logonBbutton;
	/**
	 * �������������
	 */
	private WindowAdapter adapter;
	/**
	 * ��ʼ��
	 */
	public LoginFrame() {
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("���������LoginFrame->LoginFrame()");
		}

		setTitle("QQ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(LOGIN_X, LOGIN_Y, LOGIN_W, LOGIN_H);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(-6, -10, 409, 139);
		getContentPane().add(bgPanel);

		JLabel bgLabel = new JLabel("");
		bgLabel.setIcon(new ImageIcon("source/login_bg.png"));
		bgPanel.add(bgLabel);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 128, 409, 175);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		JLabel unameLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		unameLabel.setFont(new Font("����", Font.PLAIN, 15));
		unameLabel.setBounds(82, 27, 72, 24);
		mainPanel.add(unameLabel);

		unameField = new JTextField();
		unameField.setFont(new Font("����", Font.PLAIN, 15));
		unameField.setBounds(164, 28, 135, 23);
		unameField.setColumns(10);
		mainPanel.add(unameField);

		JLabel pswordLabel = new JLabel("\u5BC6  \u7801\uFF1A");
		pswordLabel.setFont(new Font("����", Font.PLAIN, 15));
		pswordLabel.setBounds(82, 67, 72, 24);
		mainPanel.add(pswordLabel);

		logonBbutton = new JButton("\u6CE8\u518C");
		logonBbutton.setFont(new Font("����", Font.PLAIN, 15));
		logonBbutton.setBackground(new Color(240, 248, 255));
		logonBbutton.setBounds(217, 108, 72, 23);
		mainPanel.add(logonBbutton);

		loginButton = new JButton("\u767B\u5F55");
		loginButton.setFont(new Font("����", Font.PLAIN, 15));
		loginButton.setBackground(new Color(240, 248, 255));
		loginButton.setBounds(113, 108, 72, 23);
		mainPanel.add(loginButton);

		pswordField = new JPasswordField();
		pswordField.setFont(new Font("����", Font.PLAIN, 10));
		pswordField.setBounds(164, 68, 135, 23);
		mainPanel.add(pswordField);
		
		adapter = new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				Controller.closeLogin();
				Controller.closeConnection();
			}
		};
		
		addWindowListener(adapter);

		addListener();
	}
	/**
	 * Ϊ���������Ӽ�����
	 */
	private void addListener() {
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginAction();
			}
		});
		logonBbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LogonFrame().setVisible(true);				
			}
		});
		unameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = unameField.getText();
				if (s.length() >= 10) {
					JOptionPane.showMessageDialog(LoginFrame.this, "���Ȳ��ܳ���10", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		pswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = new String(pswordField.getPassword());
				if (s.length() >= 10) {
					JOptionPane.showMessageDialog(LoginFrame.this, "���Ȳ��ܳ���10", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}
	/**
	 * �����¼
	 */
	private void loginAction(){
		String uname = unameField.getText();
		String psword = new String(pswordField.getPassword());
		if (uname.equals("")) {
			JOptionPane.showMessageDialog(LoginFrame.this, "�������û���", "����", JOptionPane.WARNING_MESSAGE);
			unameField.setText("");
			unameField.requestFocus();
		} else if (psword.equals("")) {
			JOptionPane.showMessageDialog(LoginFrame.this, "����������", "����", JOptionPane.WARNING_MESSAGE);
			pswordField.setText("");
			pswordField.requestFocus();
		} else {
			// �������������
			String res = Controller.requestLogin(uname, psword);
			if(res.equals("��¼�ɹ�")) {
				MainFrame.setUser(new User(uname, psword));
				Controller.firstLoginCreateNeededFile();
				this.removeWindowListener(adapter);
				this.dispose();
				new MainFrame().setVisible(true);
			} else if(res.equals("�����ظ���¼")) {
				JOptionPane.showMessageDialog(LoginFrame.this, "�����ظ���¼", "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(LoginFrame.this, "�˺Ż��������", "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
			}	
			unameField.setText("");
			pswordField.setText("");
			unameField.requestFocus();
		}
	}
	
	/**
	 * ��������
	 * @param text �û���
	 */
	public void setUText(String text) {
		unameField.setText(text);
	}
	/**
	 * ��������
	 * @param text ����
	 */
	public void setPText(String text) {
		pswordField.setText(text);
	}
	/**
	 * ��������
	 */
	public void doLoginClick() {
		loginButton.doClick();
	}

}
