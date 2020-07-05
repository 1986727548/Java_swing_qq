package dao;

/**
 * 返回Dao的实例
 * @author wangzhen
 *
 */
public class DaoFactory {
	/**
	 * 取得UserDao的对象
	 * @return 返回UserDao的对象
	 */
	public static UserDao getUserDao(){
		return new UserDaoImpl();
	}
	/**
	 * 取得GroupDao的对象
	 * @return 返回GroupDao的对象
	 */
	public static GroupDao getGroupDao() {
		return new GroupDaoImpl();
	}
}
