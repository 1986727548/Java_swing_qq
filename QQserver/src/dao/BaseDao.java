package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import model.Group;
import model.User;

/**
 * �����ݿ���н�����ִ�в�ѯ���������
 * @author wangzhen
 *
 */
public class BaseDao {
	/**
	 * mysql���ݿ����ʽ
	 */
	private static String url = "jdbc:mysql://127.0.0.1:3306/db_jhs?characterEncoding=utf8";
	/**
	 * �û���
	 */
	private static String username = "root";
	/**
	 * ����
	 */
	private static String password = "root";
	/**
	 * һ������
	 */
	private Connection con;
	/**
	 * Ԥ����
	 */
	private PreparedStatement ps;
	/**
	 * �����
	 */
	private ResultSet rs;
	
	/**
	 * ȡ�����ݿ������
	 * @return �������ݿ������
	 */
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println("���ݿ�����ʧ��, λ�� BaseDao->getConnection");
		}
		return con;
	}

	/**
	 * �����ݿ��޸�����
	 * @param sql sql���
	 * @param objects �����滻��ֵ
	 * @return �ɹ����
	 */
	public boolean executeUpdate(String sql, Object... objects) {
		boolean res = false;
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// ���ò���
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			res = ps.executeUpdate() != 0;
		} catch (Exception e) {
			System.err.println("ִ��" + sql + "ʧ��, λ�� BaseDao->executeUpdate");
			System.err.println(res);
		} finally {
			close();
		}
		return res;
	}
	
	/**
	 * �����ݿ��ѯuser�������
	 * @param sql sql���
	 * @param objects �����滻��ֵ
	 * @return ���ؽ����
	 */
	public ArrayList<User> executeUserQuery(String sql, Object...objects) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// ���ò���
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("uname"), false));
			}
		} catch (Exception e) {
			System.err.println("ִ��" + sql + "ʧ��, λ�� BaseDao->executeUserQuery");
		} finally {
			close();
		}
		return users;
	}
	/**
	 * �����ݿ��ѯfriend_info�������
	 * @param sql sql���
	 * @param objects �����滻��ֵ
	 * @return ���ؽ����
	 */
	public ArrayList<User> executeFriendQuery(String sql, Object...objects) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// ���ò���
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("fname"), false));
			}
		} catch (Exception e) {
			System.err.println("ִ��" + sql + "ʧ��, λ�� BaseDao->executeUserQuery");
		} finally {
			close();
		}
		return users;
	}
	/**
	 * �����ݿ��ѯgroup_admin������
	 * @param sql sql���
	 * @param objects �滻��ֵ
	 * @return �����
	 */
	public ArrayList<Group> executeGroupQuery(String sql, Object...objects) {
		ArrayList<Group> list = new ArrayList<Group>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// ���ò���
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Group(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {
			System.err.println("ִ��" + sql + "ʧ��, λ�� BaseDao->executeGroupQuery");
		} finally {
			close();
		}
		return list;
	}
	/**
	 * �����ݿ��ѯgroup_info������
	 * @param sql sql���
	 * @param objects �滻��ֵ
	 * @return �����
	 */
	public ArrayList<String> executeGroupMemberQuery(String sql, Object...objects) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// ���ò���
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("gmember"));
			}
		} catch (Exception e) {
			System.err.println("ִ��" + sql + "ʧ��, λ�� BaseDao->executeGroupQuery");
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
			
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(con != null)
				con.close();
		} catch (Exception e) {
			System.err.println("error,  BaseDao->close");
		}
	}

}
