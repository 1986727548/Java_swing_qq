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
 * 和数据库进行交互，执行查询，更新语句
 * @author wangzhen
 *
 */
public class BaseDao {
	/**
	 * mysql数据库的形式
	 */
	private static String url = "jdbc:mysql://127.0.0.1:3306/db_jhs?characterEncoding=utf8";
	/**
	 * 用户名
	 */
	private static String username = "root";
	/**
	 * 密码
	 */
	private static String password = "root";
	/**
	 * 一个连接
	 */
	private Connection con;
	/**
	 * 预编译
	 */
	private PreparedStatement ps;
	/**
	 * 结果集
	 */
	private ResultSet rs;
	
	/**
	 * 取得数据库的连接
	 * @return 返回数据库的连接
	 */
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println("数据库连接失败, 位置 BaseDao->getConnection");
		}
		return con;
	}

	/**
	 * 向数据库修改数据
	 * @param sql sql语句
	 * @param objects 用于替换的值
	 * @return 成功与否
	 */
	public boolean executeUpdate(String sql, Object... objects) {
		boolean res = false;
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			res = ps.executeUpdate() != 0;
		} catch (Exception e) {
			System.err.println("执行" + sql + "失败, 位置 BaseDao->executeUpdate");
			System.err.println(res);
		} finally {
			close();
		}
		return res;
	}
	
	/**
	 * 向数据库查询user表的数据
	 * @param sql sql语句
	 * @param objects 用于替换的值
	 * @return 返回结果集
	 */
	public ArrayList<User> executeUserQuery(String sql, Object...objects) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("uname"), false));
			}
		} catch (Exception e) {
			System.err.println("执行" + sql + "失败, 位置 BaseDao->executeUserQuery");
		} finally {
			close();
		}
		return users;
	}
	/**
	 * 向数据库查询friend_info表的数据
	 * @param sql sql语句
	 * @param objects 用于替换的值
	 * @return 返回结果集
	 */
	public ArrayList<User> executeFriendQuery(String sql, Object...objects) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("fname"), false));
			}
		} catch (Exception e) {
			System.err.println("执行" + sql + "失败, 位置 BaseDao->executeUserQuery");
		} finally {
			close();
		}
		return users;
	}
	/**
	 * 向数据库查询group_admin表数据
	 * @param sql sql语句
	 * @param objects 替换的值
	 * @return 结果集
	 */
	public ArrayList<Group> executeGroupQuery(String sql, Object...objects) {
		ArrayList<Group> list = new ArrayList<Group>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Group(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {
			System.err.println("执行" + sql + "失败, 位置 BaseDao->executeGroupQuery");
		} finally {
			close();
		}
		return list;
	}
	/**
	 * 向数据库查询group_info表数据
	 * @param sql sql语句
	 * @param objects 替换的值
	 * @return 结果集
	 */
	public ArrayList<String> executeGroupMemberQuery(String sql, Object...objects) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			ps = (PreparedStatement) getConnection().prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("gmember"));
			}
		} catch (Exception e) {
			System.err.println("执行" + sql + "失败, 位置 BaseDao->executeGroupQuery");
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
