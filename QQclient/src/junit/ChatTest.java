package junit;

import javax.swing.DefaultListModel;

import org.junit.Before;
import org.junit.Test;

import component.GroupNode;
import controller.Controller;
import view.LoginFrame;
/**
 * �������
 * @author wangzhen
 *
 */
public class ChatTest {
	/**
	 * ��¼��
	 */
	private LoginFrame frame;
	/**
	 * ��ʼ��
	 */
	@Before
	public void init() {
		frame = new LoginFrame();
		frame.setVisible(true);
		Controller.requestServerConnetion();
	}
	/**
	 * ��ʼ����
	 * @throws Exception ˯���쳣
	 */
	@Test
	public void test() throws Exception {
		frame.setUText("1");
		frame.setPText("1");
		frame.doLoginClick();
		for(int i = 0; i < 5; i++) {
			Thread.sleep(2000);
			Controller.requestFriendChat("1", "2", "hello" + i);
		}
		for(int i = 0; i < 5; i++) {
			Thread.sleep(2000);
			DefaultListModel<String> model = Controller.getMyGroupMembers(new GroupNode("999", "1"));
			for(int j = 0; j < model.getSize(); j++) {
				if(model.get(j).equals("1"))
					continue;
				Controller.requestGroupChat("1", model.get(j), new GroupNode("999", "1"), "hello" + i);
			}
			
		}
		Controller.responseNotOnline("1", Controller.getMyFriends());
		Controller.closeMain();	
		Controller.saveSource();
		Controller.closeConnection();
	}

}
