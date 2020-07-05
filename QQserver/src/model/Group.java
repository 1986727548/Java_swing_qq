package model;
/**
 * 存储群信息
 * @author wangzhen
 *
 */
public class Group {
	/**
	 * 群名字
	 */
	private String gname;
	/**
	 * 群主
	 */
	private String uname;
	/**
	 * 初始化
	 * @param gname 群名字
	 * @param uname 群主
	 */
	public Group(String gname, String uname) {
		this.gname = gname;
		this.uname = uname;
	}
	/**
	 * 取得群名字
	 * @return the gname
	 */
	public String getGname() {
		return gname;
	}
	/**
	 * 取得群主
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	
}
