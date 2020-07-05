package controller;

import view.LoginFrame;

/**
 * 启动客户端
 * @author wangzhen
 *
 */
public class Start {
	/**
	 * 启动函数
	 * @param args 暂无作用
	 */
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
		Controller.requestServerConnetion();

	}

}
