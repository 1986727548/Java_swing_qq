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
 * 登录界面
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	/**
	 * 界面宽度
	 */
	private final static int LOGIN_W = 415;
	/**
	 * 界面高度
	 */
	private final static int LOGIN_H = 332;
	/**
	 * 界面x坐标位置
	 */
	private final static int LOGIN_X = (Toolkit.getDefaultToolkit().getScreenSize().width - LOGIN_W) / 2;
	/**
	 * 界面y坐标位置
	 */
	private final static int LOGIN_Y = (Toolkit.getDefaultToolkit().getScreenSize().height - LOGIN_H) / 2;
	/**
	 * 用户名框
	 */
	private JTextField unameField;
	/**
	 * 密码框
	 */
	private JPasswordField pswordField;
	/**
	 * 登录按钮
	 */
	private JButton loginButton;
	/**
	 * 注册按钮
	 */
	private JButton logonBbutton;
	/**
	 * 窗体监听适配器
	 */
	private WindowAdapter adapter;
	/**
	 * 初始化
	 */
	public LoginFrame() {
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.err.println("主题风格错误，LoginFrame->LoginFrame()");
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
		unameLabel.setFont(new Font("黑体", Font.PLAIN, 15));
		unameLabel.setBounds(82, 27, 72, 24);
		mainPanel.add(unameLabel);

		unameField = new JTextField();
		unameField.setFont(new Font("黑体", Font.PLAIN, 15));
		unameField.setBounds(164, 28, 135, 23);
		unameField.setColumns(10);
		mainPanel.add(unameField);

		JLabel pswordLabel = new JLabel("\u5BC6  \u7801\uFF1A");
		pswordLabel.setFont(new Font("黑体", Font.PLAIN, 15));
		pswordLabel.setBounds(82, 67, 72, 24);
		mainPanel.add(pswordLabel);

		logonBbutton = new JButton("\u6CE8\u518C");
		logonBbutton.setFont(new Font("黑体", Font.PLAIN, 15));
		logonBbutton.setBackground(new Color(240, 248, 255));
		logonBbutton.setBounds(217, 108, 72, 23);
		mainPanel.add(logonBbutton);

		loginButton = new JButton("\u767B\u5F55");
		loginButton.setFont(new Font("黑体", Font.PLAIN, 15));
		loginButton.setBackground(new Color(240, 248, 255));
		loginButton.setBounds(113, 108, 72, 23);
		mainPanel.add(loginButton);

		pswordField = new JPasswordField();
		pswordField.setFont(new Font("黑体", Font.PLAIN, 10));
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
	 * 为各个组件添加监听器
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
					JOptionPane.showMessageDialog(LoginFrame.this, "长度不能超过10", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		pswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = new String(pswordField.getPassword());
				if (s.length() >= 10) {
					JOptionPane.showMessageDialog(LoginFrame.this, "长度不能超过10", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}
	/**
	 * 处理登录
	 */
	private void loginAction(){
		String uname = unameField.getText();
		String psword = new String(pswordField.getPassword());
		if (uname.equals("")) {
			JOptionPane.showMessageDialog(LoginFrame.this, "请输入用户名", "错误", JOptionPane.WARNING_MESSAGE);
			unameField.setText("");
			unameField.requestFocus();
		} else if (psword.equals("")) {
			JOptionPane.showMessageDialog(LoginFrame.this, "请输入密码", "错误", JOptionPane.WARNING_MESSAGE);
			pswordField.setText("");
			pswordField.requestFocus();
		} else {
			// 请求服务器操作
			String res = Controller.requestLogin(uname, psword);
			if(res.equals("登录成功")) {
				MainFrame.setUser(new User(uname, psword));
				Controller.firstLoginCreateNeededFile();
				this.removeWindowListener(adapter);
				this.dispose();
				new MainFrame().setVisible(true);
			} else if(res.equals("不能重复登录")) {
				JOptionPane.showMessageDialog(LoginFrame.this, "不能重复登录", "登录失败", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(LoginFrame.this, "账号或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
			}	
			unameField.setText("");
			pswordField.setText("");
			unameField.requestFocus();
		}
	}
	
	/**
	 * 测试所用
	 * @param text 用户名
	 */
	public void setUText(String text) {
		unameField.setText(text);
	}
	/**
	 * 测试所用
	 * @param text 密码
	 */
	public void setPText(String text) {
		pswordField.setText(text);
	}
	/**
	 * 测试所用
	 */
	public void doLoginClick() {
		loginButton.doClick();
	}

}
