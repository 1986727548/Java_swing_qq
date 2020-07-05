package junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.DaoFactory;
import dao.UserDao;
import model.User;

/**
 * test user
 * @author wangzhen
 *
 */
public class UserDaoImplTest {
	/**
	 * 操作数据库
	 */
	UserDao userDao = null;
	/**
	 * 初始化
	 */
	@Before
	public void init() {
		userDao = DaoFactory.getUserDao();
	}
	/**
	 * test login
	 */
	@Test
	public void testLogin() {
		assertEquals(userDao.login(new User("1", "1")), true);
		assertEquals(userDao.login(new User("2", "2")), true);
	}
	/**
	 * test logon
	 */
	@Test
	public void testLogon() {
		assertEquals(userDao.logon(new User("1", "1")), false);
		assertEquals(userDao.logon(new User("11122", "11122")), true);
	}
	/**
	 * test add friend
	 */
	@Test
	public void testAddFriend() {
		assertEquals(userDao.addFriend("1", "111"), true);
	}
	/**
	 * test del friend
	 */
	@Test
	public void testDelFriend() {
		assertEquals(userDao.delFriend("1", "111"), true);
	}


}
