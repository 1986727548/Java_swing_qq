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
 * �û��洢�ļ���Ϣ
 * @author wangzhen
 *
 */
public class BaseDao {
	/**
	 * ��ȡ�ļ���Ϣ�ı���
	 */
	private BufferedReader reader;
	/**
	 * д���ļ���Ϣ�ı���
	 */
	private BufferedWriter writer;
	/**
	 * �Ѻ�����Ϣ�浽�ļ���
	 * @param file Ŀ���ļ�
	 * @param res �����б�
	 */
	public void executeFriendUpdate(File file, Collection<FriendNode> res) {
		List<FriendNode> list = new ArrayList<FriendNode>();
		list.addAll(res);
		// ��������
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
	 * ���ļ���ȡ��������Ϣ
	 * @param file Ŀ���ļ�
	 * @return ���غ����б�
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
	 * ��Ⱥ����Ϣ�浽�ļ���
	 * @param file Ŀ���ļ�
	 * @param res Ⱥ���б�
	 */
	public void executeGroupUpdate(File file, Collection<GroupNode> res) {
		List<GroupNode> list = new ArrayList<GroupNode>();
		list.addAll(res);
		// ��������
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
	 * ���ļ���ȡ��Ⱥ����Ϣ
	 * @param file Ŀ���ļ�
	 * @return ����Ⱥ���б�
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
	 * ��Ⱥ�ĳ�Ա��Ϣ�浽�ļ���
	 * @param file Ŀ���ļ�
	 * @param res Ⱥ�ĳ�Ա�б�
	 */
	public void executeGroupMembersUpdate(File file, DefaultListModel<String> res) {
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < res.getSize(); i++) {
			list.add(res.get(i));
		}
		// ��������
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
	 * ���ļ���ȡ��Ⱥ�ĳ�Ա��Ϣ
	 * @param file Ŀ���ļ�
	 * @return ����Ⱥ�ĳ�Ա�б�
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
	 * �ر���Դ������
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
