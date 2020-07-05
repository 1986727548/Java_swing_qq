package junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.DaoFactory;
import dao.GroupDao;
import model.Group;

/**
 * test group
 * @author wangzhen
 *
 */
public class GroupDaoImplTest {
	/**
	 * 操作数据库
	 */
	GroupDao groupDao = null;
	/**
	 * 初始化
	 */
	@Before
	public void init() {
		groupDao = DaoFactory.getGroupDao();
	}
	/**
	 * test create group
	 */
	@Test
	public void testCreate() {
		assertEquals(groupDao.create(new Group("1", "hello")), true);
		assertEquals(groupDao.create(new Group("1", "hello")), false);
	}
	/**
	 * test delete group
	 */
	@Test
	public void testDelete() {
		assertEquals(groupDao.delete(new Group("1", "hello-world")), false);
	}
	/**
	 * test invite member
	 */
	@Test
	public void testInvite() {
		assertEquals(groupDao.invite("1", "hello", "2"), true);
		assertEquals(groupDao.invite("1", "hello", "2"), false);
	}
	/**
	 * test knock member
	 */
	@Test
	public void testKnock() {
		assertEquals(groupDao.knock("1", "hello", "3"), false);
	}
	/**
	 * test rename group name
	 */
	@Test
	public void testUpdateGroupName() {
		assertEquals(groupDao.updateGroupName("1", "hello,world", "hello123"), false);
	}

}
