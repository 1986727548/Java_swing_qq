package component;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * ���ѽڵ㣬���ں����б�Ͳ�ѯ�б�
 * @author wangzhen
 *
 */
@SuppressWarnings("serial")
public class FriendNode extends DefaultMutableTreeNode implements Comparable<FriendNode> {
	/**
	 * ��������
	 */
	private String uname;
	/**
	 * �����Ƿ�����
	 */
	private boolean state;
	/**
	 *����name ��ʼ��
	 * @param name ��������
	 */
	public FriendNode(String name) {
		setUname(name);
	}
	
	/**
	 * ����һ�£����ڲ���
	 */
	public String toString() {
		return "FriendNode [uname=" + uname + ", state=" + state + "]";
	}
	/**
	 * ����uname, state��ʼ��
	 * @param uname ��������
	 * @param state ��������״̬
	 */
	public FriendNode(String uname, boolean state) {
		super();
		setUname(uname);
		setState(state);
	}
	/** ����uname
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	/**����uname��ֵ
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}
	/**����state
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}
	/**����state��ֵ
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * ����map����ȷ��Ψһ��
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (state ? 1231 : 1237);
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}

	/**
	 * ����map����ȷ��Ψһ��
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
	 * ���ݺ������ƽ�������
	 */
	public int compareTo(FriendNode o) {
		return uname.compareTo(o.getUname());
	}
	
	
	
}
