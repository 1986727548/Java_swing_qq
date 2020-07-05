package component;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * ����Ⱥ�б�
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class GroupNode extends DefaultMutableTreeNode implements Comparable<GroupNode> {
	/**
	 * Ⱥ����
	 */
	private String gname;
	/**
	 * Ⱥ��
	 */
	private String owner;
	
	/**
	 * �������
	 */
	public String toString() {
		return "GroupNode [gname=" + gname + ", owner=" + owner + "]";
	}
	/**
	 * owner��������Ϊgname��Ⱥ
	 * @param gname Ⱥ��
	 * @param owner Ⱥ��
	 */
	public GroupNode(String gname, String owner) {
		super();
		setGname(gname);
		setOwner(owner);
	}
	/**����Ⱥ��
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**����Ⱥ��
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**����Ⱥ��
	 * @return the gname
	 */
	public String getGname() {
		return gname;
	}
	/**����Ⱥ��
	 * @param gname the gname to set
	 */
	public void setGname(String gname) {
		this.gname = gname;
	}

	/**
	 *  ����mapȷ��Ψһ��
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gname == null) ? 0 : gname.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	/**
	 *  ����mapȷ��Ψһ��
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
	 * ����Ⱥ��Ϣ������Ⱥ���ֺ�Ⱥ��
	 */
	public int compareTo(GroupNode o) {
		if(gname.equals(o.getGname())) {
			return owner.compareTo(o.getOwner());
		}
		return gname.compareTo(o.getGname());
	}
	
	
	
}
