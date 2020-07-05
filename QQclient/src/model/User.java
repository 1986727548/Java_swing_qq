package model;

/**
 * 用于存储用户信息
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
	@SuppressWarnings("unused")
	private String psword;
	/**
	 * 初始化
	 * @param uname 用户名
	 * @param psword 密码
	 */
	public User(String uname, String psword) {
		super();
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
	
}
