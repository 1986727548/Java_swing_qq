package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

import component.FriendNode;
import component.GroupNode;

/**
 * 用户存储文件信息
 * @author wangzhen
 *
 */
public class BaseDao {
	/**
	 * 读取文件信息的变量
	 */
	private BufferedReader reader;
	/**
	 * 写入文件信息的变量
	 */
	private BufferedWriter writer;
	/**
	 * 把好友信息存到文件里
	 * @param file 目标文件
	 * @param res 好友列表
	 */
	public void executeFriendUpdate(File file, Collection<FriendNode> res) {
		List<FriendNode> list = new ArrayList<FriendNode>();
		list.addAll(res);
		// 进行排序
		Collections.sort(list);
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (FriendNode friendNode : list) {
				writer.write(friendNode.getUname());
				writer.newLine();
			}
			writer.flush();
		} catch (Exception e) {
			System.err.println("error, BaseDao->executeFriendUpdate");
		} finally {
			close();
		}
	}
	/**
	 * 从文件里取到好友信息
	 * @param file 目标文件
	 * @return 返回好友列表
	 */
	public ArrayList<FriendNode> executeFriendQuery(File file) {
		ArrayList<FriendNode> list = new ArrayList<FriendNode>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String uname = null;
			while((uname = reader.readLine()) != null) {
				list.add(new FriendNode(uname));
			}
			
		} catch (Exception e) {
			System.err.println("error, BaseDao->executeFriendQuery");
		} finally {
			close();
		}
		return list;
	}
	/**
	 * 把群聊信息存到文件里
	 * @param file 目标文件
	 * @param res 群聊列表
	 */
	public void executeGroupUpdate(File file, Collection<GroupNode> res) {
		List<GroupNode> list = new ArrayList<GroupNode>();
		list.addAll(res);
		// 进行排序
		Collections.sort(list);
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (GroupNode groupNode : list) {
				writer.write(groupNode.getGname() + "," + groupNode.getOwner());
				writer.newLine();
			}
			writer.flush();
		} catch (Exception e) {
			System.err.println("error, BaseDao->executeGroupUpdate");
		} finally {
			close();
		}
		
	}
	/**
	 * 从文件里取到群聊信息
	 * @param file 目标文件
	 * @return 返回群聊列表
	 */
	public ArrayList<GroupNode> executeGroupQuery(File file) {
		ArrayList<GroupNode> list = new ArrayList<GroupNode>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String info = null;
			while((info = reader.readLine()) != null) {
				String string[] = info.split(",");
				list.add(new GroupNode(string[0], string[1]));
			}
		} catch (Exception e) {
			System.err.println("error, BaseDao->executeGroupQuery");
		} finally {
			close();
		}
		return list;
	}
	/**
	 * 把群聊成员信息存到文件里
	 * @param file 目标文件
	 * @param res 群聊成员列表
	 */
	public void executeGroupMembersUpdate(File file, DefaultListModel<String> res) {
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < res.getSize(); i++) {
			list.add(res.get(i));
		}
		// 进行排序
		Collections.sort(list);
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (String string : list) {
				writer.write(string);
				writer.newLine();
			}
			writer.flush();
		} catch (Exception e) {
			System.err.println("error, BaseDao->executeGroupMembersUpdate");
		} finally {
			close();
		}
	}
	/**
	 * 从文件里取到群聊成员信息
	 * @param file 目标文件
	 * @return 返回群聊成员列表
	 */
	public DefaultListModel<String> executeGroupMembersQuery(File file) {
		DefaultListModel<String> list = new DefaultListModel<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String uname = null;
			while((uname = reader.readLine()) != null) {
				list.add(list.getSize(),uname);
			}
			
		} catch (Exception e) {
			System.err.println("error, BaseDao->executeFriendQuery");
		} finally {
			close();
		}
		return list;
	}

	/**
	 * 关闭资源的连接
	 */
	public void close() {
		try {
			if(reader != null)
				reader.close();
			if(writer != null)
				writer.close();
		} catch (Exception e) {
			System.err.println("error, BaseDao->close");
		}
	}

}
