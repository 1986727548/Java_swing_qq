package model;

/**
 * ���ڴ洢�û���Ϣ
 * @author wangzhen
 *
 */
public class User {
	/**
	 * �û���
	 */
	private String uname;
	/**
	 * ����
	 */
	@SuppressWarnings("unused")
	private String psword;
	/**
	 * ��ʼ��
	 * @param uname �û���
	 * @param psword ����
	 */
	public User(String uname, String psword) {
		super();
		this.uname = uname;
		this.psword = psword;
	}
	/**
	 * ȡ���û���
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	
}
