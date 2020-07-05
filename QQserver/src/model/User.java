package model;

/**
 * �洢�û���Ϣ
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
	private String psword;
	/**
	 * ����״̬
	 */
	private boolean state;
	/**
	 * ��ʼ��
	 * @param uname �û���
	 * @param state ����״̬
	 */
	public User(String uname, boolean state) {
		this.uname = uname;
		this.state = state;
	}
	/**
	 * ��ʼ��
	 * @param uname �û���
	 * @param psword ����
	 */
	public User(String uname, String psword) {
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
	/**
	 * ȡ������
	 * @return the psword
	 */
	public String getPsword() {
		return psword;
	}
	
	/**
	 * �ж��Ƿ�����
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}
	/**
	 * ��������״̬
	 * @param state Ŀ��״̬
	 */
	public void setState(boolean state) {
		this.state = state;
	}
}
