package component;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * 用于群列表
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class GroupNode extends DefaultMutableTreeNode implements Comparable<GroupNode> {
	/**
	 * 群名字
	 */
	private String gname;
	/**
	 * 群主
	 */
	private String owner;
	
	/**
	 * 方便测试
	 */
	public String toString() {
		return "GroupNode [gname=" + gname + ", owner=" + owner + "]";
	}
	/**
	 * owner创建了名为gname的群
	 * @param gname 群名
	 * @param owner 群主
	 */
	public GroupNode(String gname, String owner) {
		super();
		setGname(gname);
		setOwner(owner);
	}
	/**返回群主
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**设置群主
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**返回群名
	 * @return the gname
	 */
	public String getGname() {
		return gname;
	}
	/**设置群名
	 * @param gname the gname to set
	 */
	public void setGname(String gname) {
		this.gname = gname;
	}

	/**
	 *  用于map确保唯一性
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gname == null) ? 0 : gname.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	/**
	 *  用于map确保唯一性
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupNode other = (GroupNode) obj;
		if (gname == null) {
			if (other.gname != null)
				return false;
		} else if (!gname.equals(other.gname))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
	/**
	 * 根据群信息排序先群名字后群主
	 */
	public int compareTo(GroupNode o) {
		if(gname.equals(o.getGname())) {
			return owner.compareTo(o.getOwner());
		}
		return gname.compareTo(o.getGname());
	}
	
	
	
}
