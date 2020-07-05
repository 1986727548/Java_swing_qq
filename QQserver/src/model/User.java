package model;

/**
 * 存储用户信息
 * @author wangzhen
 *
 */
public class User {
	/**
	 * 用户名
	 */
	private String uname;
	/**
	 * 密码
	 */
	private String psword;
	/**
	 * 在线状态
	 */
	private boolean state;
	/**
	 * 初始化
	 * @param uname 用户名
	 * @param state 在线状态
	 */
	public User(String uname, boolean state) {
		this.uname = uname;
		this.state = state;
	}
	/**
	 * 初始化
	 * @param uname 用户名
	 * @param psword 密码
	 */
	public User(String uname, String psword) {
		this.uname = uname;
		this.psword = psword;
	}
	/**
	 * 取得用户名
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	/**
	 * 取得密码
	 * @return the psword
	 */
	public String getPsword() {
		return psword;
	}
	
	/**
	 * 判断是否在线
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}
	/**
	 * 设置在线状态
	 * @param state 目标状态
	 */
	public void setState(boolean state) {
		this.state = state;
	}
}
