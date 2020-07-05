package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import component.GroupNode;
import controller.Controller;
import dao.GroupDaoImpl;
import view.LoginFrame;

/**
 * 测试群聊
 * @author wangzhen
 *
 */
public class GroupDaoImplTest {
	/**
	 * 登录框
	 */
	private LoginFrame frame;
	private GroupDaoImpl impl = new GroupDaoImpl();
	/**
	 * 初始化
	 */
	@Before
	public void init(){
		Controller.requestServerConnetion();
		frame = new LoginFrame();
		frame.setVisible(true);
		frame.setUText("aq");
		frame.setPText("aq");
		frame.doLoginClick();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 开始测试
	 */
	@Test
	public void testGroup() {
		Controller.requestCreateGroup();
		impl.testAddGroup();
		Controller.requestUpdateGroupName(new GroupNode("1", "aq"));
		impl.testModifyGroup();
		System.out.println(Controller.getMyGroups());
	}
	/**
	 * 结束
	 */
	@After
	public void testDeleteGroup() {
		Controller.requestDelGroup(new GroupNode("2", "aq"));
		Controller.requestDelGroupMembers(new GroupNode("2", "aq"), "aq");
		impl.testDelGroup();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Controller.responseNotOnline("ap", Controller.getMyFriends());
		Controller.closeMain();	
		Controller.saveSource();
		Controller.closeConnection();
	}
	

}
