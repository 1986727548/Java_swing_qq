package server;

import java.awt.Toolkit;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * ��������
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class Server extends JFrame {
	/**
	 * ������
	 */
	private final static int W = 430;
	/**
	 * ����߶�
	 */
	private final static int H = 350;
	/**
	 * ����x����
	 */
	private final static int X = (Toolkit.getDefaultToolkit().getScreenSize().width - W) / 2;
	/**
	 * ����y����
	 */
	private final static int Y = (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2;
	/**
	 * ��������
	 */
	private static JTextArea textArea;
	/**
	 * ������socket
	 */
	private ServerSocket serverSocket;
	/**
	 * ��������������
	 */
	public void getServer() {
		try {
			serverSocket = new ServerSocket(2003);
			Server.append("�������׽����Ѿ������ɹ�\n");
			while(true) {
				Socket socket = serverSocket.accept();
				new ServerThread(socket).start();
				Server.append(socket + "���ӳɹ�" + "\n");
				Server.append(socket.getPort() + "\n");
				DataService.put(socket, null);
			}
		} catch (Exception e) {
			System.out.println("Server -> getServer");
		}
	}
	/**
	 * �����־
	 * @param string ��־��Ϣ
	 */
	public static void append(String string) {
		textArea.append(string);
	}
	/**
	 * ��ʼ��
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
	 * ��������
	 * @param args ��������
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.setVisible(true);
		server.getServer();
	}
}
