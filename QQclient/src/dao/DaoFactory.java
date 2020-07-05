package dao;

/**
 * 返回Dao的实例
 * @author wangzhen
 *
 */
public class DaoFactory {
	/**
	 * 取得FriendDao的对象
	 * @return 返回FriendDao的对象
	 */
	public static FriendDao getFriendDao() {
		return new FriendDaoImpl();
	}
	/**
	 * 取得GroupDao的对象
	 * @return 返回GroupDao的对象
	 */
	public static GroupDao getGroupDao() {
		return new GroupDaoImpl();
	}

}
