package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理用户上下线和离线消息
 * @author wangzhen
 *
 */
public class DataService {
	/**
	 * 在线用户集
	 */
	private static Map<Socket, String> users = new HashMap<Socket, String>();
	/**
	 * 在线socket的集
	 */
	private static Map<String, Socket> sockets = new HashMap<String, Socket>();
	/**
	 * 缓存信息集
	 */
	private static Map<String, ArrayList<String>> bufferMap = new HashMap<String, ArrayList<String>>();
	/**
	 * 添加用户上线信息
	 * @param socket 目标socket
	 * @param uname 目标用户名
	 */
	public static void put(Socket socket, String uname) {
		users.put(socket, uname);
		if(uname != null) {
			sockets.put(uname, socket);
		}
	}
	/**
	 * 删除用户上线信息
	 * @param socket 目标socket
	 */
	public static void remove(Socket socket) {
		sockets.remove(users.get(socket));
		users.remove(socket);
	}
	/**
	 * 取得socket对应的用户名
	 * @param socket 目标socket
	 * @return 返回对应的用户名
	 */
	public static String getUname(Socket socket) {
		return users.get(socket);
	}
	/**
	 * 该用户是否上线
	 * @param uname 目标用户
	 * @return 成功与否
	 */
	public static boolean containsUname(String uname) {
		return users.containsValue(uname);
	}
	/**
	 * 取得用户名对应的socket
	 * @param uname 目标用户名
	 * @return 返回对应的socket
	 */
	public static Socket getSocket(String uname) {
		return sockets.get(uname);
	}
	/**
	 * 向未在线的用户添加离线消息
	 * @param uname 目标用户
	 * @param msg 消息
	 */
	public static void addResponseMsg(String uname, String msg) {
		if(bufferMap.get(uname) == null) {
			bufferMap.put(uname, new ArrayList<String>());
		}
		bufferMap.get(uname).add(msg);
	}
	/**
	 * 取得用户不在线的消息集合
	 * @param uname 目标用户
	 * @return 消息集合
	 */
	public static ArrayList<String> getResponseMsg(String uname){
		return bufferMap.get(uname);
	}
}
