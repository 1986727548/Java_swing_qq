package component;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 好友节点，用于好友列表和查询列表
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class FriendNode extends DefaultMutableTreeNode implements Comparable<FriendNode> {
	/**
	 * 好友名称
	 */
	private String uname;
	/**
	 * 好友是否在线
	 */
	private boolean state;
	/**
	 *根据name 初始化
	 * @param name 好友名称
	 */
	public FriendNode(String name) {
		setUname(name);
	}
	
	/**
	 * 重载一下，用于测试
	 */
	public String toString() {
		return "FriendNode [uname=" + uname + ", state=" + state + "]";
	}
	/**
	 * 根据uname, state初始化
	 * @param uname 好友名称
	 * @param state 好友在线状态
	 */
	public FriendNode(String uname, boolean state) {
		super();
		setUname(uname);
		setState(state);
	}
	/** 返回uname
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	/**设置uname的值
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}
	/**返回state
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}
	/**设置state的值
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * 用于map函数确定唯一性
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (state ? 1231 : 1237);
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}

	/**
	 * 用于map函数确定唯一性
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendNode other = (FriendNode) obj;
		if (state != other.state)
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}

	/**
	 * 根据好友名称进行排序
	 */
	public int compareTo(FriendNode o) {
		return uname.compareTo(o.getUname());
	}
	
	
	
}
