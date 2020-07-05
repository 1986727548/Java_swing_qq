package server;

import java.awt.Toolkit;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 服务器端
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class Server extends JFrame {
	/**
	 * 界面宽度
	 */
	private final static int W = 430;
	/**
	 * 界面高度
	 */
	private final static int H = 350;
	/**
	 * 界面x坐标
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * 界面y坐标
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * 内容区域
	 */
	private static JTextArea textArea;
	/**
	 * 服务器socket
	 */
	private ServerSocket serverSocket;
	/**
	 * 服务器建立连接
	 */
	public void getServer() {
		try {
			serverSocket = new ServerSocket(2003);
			Server.append("服务器套接字已经创建成功\n");
			while(true) {
				Socket socket = serverSocket.accept();
				new ServerThread(socket).start();
				Server.append(socket + "连接成功" + "\n");
				Server.append(socket.getPort() + "\n");
				DataService.put(socket, null);
			}
		} catch (Exception e) {
			System.out.println("Server -> getServer");
		}
	}
	/**
	 * 添加日志
	 * @param string 日志信息
	 */
	public static void append(String string) {
		textArea.append(string);
	}
	/**
	 * 初始化
	 */
	public Server() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("\u670D\u52A1\u5668\u7AEF\u7A0B\u5E8F");
		setBounds(X, Y, W, H);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 389, 288);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
	}
	/**
	 * 启动函数
	 * @param args 暂无作用
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.setVisible(true);
		server.getServer();
	}
}
