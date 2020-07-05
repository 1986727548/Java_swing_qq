package dao;

/**
 * ����Dao��ʵ��
 * @author wangzhen
 *
 */
public class DaoFactory {
	/**
	 * ȡ��UserDao�Ķ���
	 * @return ����UserDao�Ķ���
	 */
	public static UserDao getUserDao(){
		return new UserDaoImpl();
	}
	/**
	 * ȡ��GroupDao�Ķ���
	 * @return ����GroupDao�Ķ���
	 */
	public static GroupDao getGroupDao() {
		return new GroupDaoImpl();
	}
}
