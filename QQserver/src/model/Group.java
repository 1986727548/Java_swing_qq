package model;
/**
 * �洢Ⱥ��Ϣ
 * @author wangzhen
 *
 */
public class Group {
	/**
	 * Ⱥ����
	 */
	private String gname;
	/**
	 * Ⱥ��
	 */
	private String uname;
	/**
	 * ��ʼ��
	 * @param gname Ⱥ����
	 * @param uname Ⱥ��
	 */
	public Group(String gname, String uname) {
		this.gname = gname;
		this.uname = uname;
	}
	/**
	 * ȡ��Ⱥ����
	 * @return the gname
	 */
	public String getGname() {
		return gname;
	}
	/**
	 * ȡ��Ⱥ��
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	
}
