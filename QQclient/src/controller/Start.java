package controller;

import view.LoginFrame;

/**
 * �����ͻ���
 * @author wangzhen
 *
 */
public class Start {
	/**
	 * ��������
	 * @param args ��������
	 */
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
		Controller.requestServerConnetion();

	}

}
