package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import controller.Controller;

/**
 * ע�����
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class LogonFrame extends JFrame {
	/**
	 * ������
	 */
	private final static int LOGON_W = 415;
	/**
	 * ����߶�
	 */
	private final static int LOGON_H = 332;
	/**
	 * ����x����λ��
	 */
	private final static int LOGON_X = (Toolkit.getDefaultToolkit().getScreenSize().width - LOGON_W) / 2;
	/**
	 * ����y����λ��
	 */
	private final static int LOGON_Y = (Toolkit.getDefaultToolkit().getScreenSize().height - LOGON_H) / 2;
	/**
	 * �û�����
	 */
	private JTextField unameField;
	/**
	 * ע�ᰴť
	 */
	private JButton logonButton;
	/**
	 * �����
	 */
	private JPasswordField pswordField;
	/**
	 * �ٴ�ȷ�������
	 */
	private JPasswordField okPswdField;
	/**
	 * ��ʼ��
	 */
	public LogonFrame() {
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("���������LogonFrame->LogonFrame()");
		}
		
		setTitle("\u6CE8\u518C");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(LOGON_X, LOGON_Y, LOGON_W, LOGON_H);
		
		JLabel unameLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		unameLabel.setFont(new Font("����", Font.PLAIN, 15));
		unameLabel.setBounds(59, 58, 86, 23);
		getContentPane().add(unameLabel);
		
		JLabel pswordLabel = new JLabel("\u5BC6\u7801\uFF1A");
		pswordLabel.setFont(new Font("����", Font.PLAIN, 15));
		pswordLabel.setBounds(59, 106, 86, 23);
		getContentPane().add(pswordLabel);
		
		JLabel okPswdLabel = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		okPswdLabel.setFont(new Font("����", Font.PLAIN, 15));
		okPswdLabel.setBounds(59, 152, 86, 23);
		getContentPane().add(okPswdLabel);
		
		unameField = new JTextField();
		unameField.setFont(new Font("����", Font.PLAIN, 15));
		unameField.setColumns(10);
		unameField.setBounds(167, 57, 161, 28);
		getContentPane().add(unameField);
		
		logonButton = new JButton("\u6CE8\u518C");
		logonButton.setBackground(new Color(240, 248, 255));
		logonButton.setFont(new Font("����", Font.PLAIN, 15));
		logonButton.setBounds(59, 203, 269, 28);
		getContentPane().add(logonButton);
		
		pswordField = new JPasswordField();
		pswordField.setFont(new Font("����", Font.PLAIN, 10));
		pswordField.setBounds(167, 106, 161, 28);
		getContentPane().add(pswordField);
		
		okPswdField = new JPasswordField();
		okPswdField.setFont(new Font("����", Font.PLAIN, 10));
		okPswdField.setBounds(167, 153, 161, 28);
		getContentPane().add(okPswdField);
		
		addListener();
	}
	/**
	 * Ϊ���������Ӽ�����
	 */
	private void addListener() {
		logonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logonAction();
			}
		});
		unameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = unameField.getText();
				if(s.length() >= 10) {
					JOptionPane.showMessageDialog(LogonFrame.this, "���Ȳ��ܳ���10", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		pswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = new String(pswordField.getPassword());
				if(s.length() >= 10) {
					JOptionPane.showMessageDialog(LogonFrame.this, "���Ȳ��ܳ���10", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		okPswdField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = new String(okPswdField.getPassword());
				if(s.length() >= 10) {
					JOptionPane.showMessageDialog(LogonFrame.this, "���Ȳ��ܳ���10", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	/**
	 * ����ע��
	 */
	private void logonAction() {
		String uname = unameField.getText();
		String psword = new String(pswordField.getPassword());
		String okPswd = new String(okPswdField.getPassword());
		if (uname.equals("")) {
			JOptionPane.showMessageDialog(LogonFrame.this, "�������û���", "����", JOptionPane.WARNING_MESSAGE);
			unameField.setText("");
			unameField.requestFocus();
		} else if(uname.indexOf(',') != -1) {
			JOptionPane.showMessageDialog(LogonFrame.this, "�û������ܰ���,����", "����", JOptionPane.WARNING_MESSAGE);
			unameField.setText("");
			unameField.requestFocus();
		} else if (psword.equals("")) {
			JOptionPane.showMessageDialog(LogonFrame.this, "����������", "����", JOptionPane.WARNING_MESSAGE);
			pswordField.setText("");
			pswordField.requestFocus();
		} else if (okPswd.equals("")) {
			JOptionPane.showMessageDialog(LogonFrame.this, "������ȷ������", "����", JOptionPane.WARNING_MESSAGE);
			okPswdField.setText("");
			okPswdField.requestFocus();
		} else if (!okPswd.equals(psword)) {
			JOptionPane.showMessageDialog(LogonFrame.this, "�������벻һ��", "����", JOptionPane.WARNING_MESSAGE);
			okPswdField.setText("");
			pswordField.setText("");
			pswordField.requestFocus();
		} else {
			// �������������
			if (Controller.requestLogon(uname, psword)) {
				JOptionPane.showMessageDialog(LogonFrame.this, "ע��ɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(LogonFrame.this, "���˺��ѱ�ע��", "ʧ��", JOptionPane.ERROR_MESSAGE);
			}
			unameField.setText("");
			okPswdField.setText("");
			pswordField.setText("");
			unameField.requestFocus();
		}
		
	}
}
