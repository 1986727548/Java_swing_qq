package dao;

/**
 * ����Dao��ʵ��
 * @author wangzhen
 *
 */
public class DaoFactory {
	/**
	 * ȡ��FriendDao�Ķ���
	 * @return ����FriendDao�Ķ���
	 */
	public static FriendDao getFriendDao() {
		return new FriendDaoImpl();
	}
	/**
	 * ȡ��GroupDao�Ķ���
	 * @return ����GroupDao�Ķ���
	 */
	public static GroupDao getGroupDao() {
		return new GroupDaoImpl();
	}

}
