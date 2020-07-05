package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * �����û������ߺ�������Ϣ
 * @author wangzhen
 *
 */
public class DataService {
	/**
	 * �����û���
	 */
	private static Map<Socket, String> users = new HashMap<Socket, String>();
	/**
	 * ����socket�ļ�
	 */
	private static Map<String, Socket> sockets = new HashMap<String, Socket>();
	/**
	 * ������Ϣ��
	 */
	private static Map<String, ArrayList<String>> bufferMap = new HashMap<String, ArrayList<String>>();
	/**
	 * ����û�������Ϣ
	 * @param socket Ŀ��socket
	 * @param uname Ŀ���û���
	 */
	public static void put(Socket socket, String uname) {
		users.put(socket, uname);
		if(uname != null) {
			sockets.put(uname, socket);
		}
	}
	/**
	 * ɾ���û�������Ϣ
	 * @param socket Ŀ��socket
	 */
	public static void remove(Socket socket) {
		sockets.remove(users.get(socket));
		users.remove(socket);
	}
	/**
	 * ȡ��socket��Ӧ���û���
	 * @param socket Ŀ��socket
	 * @return ���ض�Ӧ���û���
	 */
	public static String getUname(Socket socket) {
		return users.get(socket);
	}
	/**
	 * ���û��Ƿ�����
	 * @param uname Ŀ���û�
	 * @return �ɹ����
	 */
	public static boolean containsUname(String uname) {
		return users.containsValue(uname);
	}
	/**
	 * ȡ���û�����Ӧ��socket
	 * @param uname Ŀ���û���
	 * @return ���ض�Ӧ��socket
	 */
	public static Socket getSocket(String uname) {
		return sockets.get(uname);
	}
	/**
	 * ��δ���ߵ��û����������Ϣ
	 * @param uname Ŀ���û�
	 * @param msg ��Ϣ
	 */
	public static void addResponseMsg(String uname, String msg) {
		if(bufferMap.get(uname) == null) {
			bufferMap.put(uname, new ArrayList<String>());
		}
		bufferMap.get(uname).add(msg);
	}
	/**
	 * ȡ���û������ߵ���Ϣ����
	 * @param uname Ŀ���û�
	 * @return ��Ϣ����
	 */
	public static ArrayList<String> getResponseMsg(String uname){
		return bufferMap.get(uname);
	}
}
