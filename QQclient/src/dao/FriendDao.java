package dao;

import java.util.ArrayList;
import java.util.Collection;

import component.FriendNode;

/**
 * 操控好友列表数据
 * @author wangzhen
 *
 */
public interface FriendDao {
	/**
	 * 从本机文件中取得好友列表
	 * @return 好友列表
	 */
	public ArrayList<FriendNode> getAllFriends();
	/**
	 * 向本机文件存储好友列表信息
	 * @param res 好友列表
	 */
	public void saveMyFriends(Collection<FriendNode> res);

}
