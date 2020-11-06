package cas.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionMap {
	private static Map<String, HttpSession> map = new HashMap<String, HttpSession>();

	public static void put(HttpSession session) {
		map.put(session.getId(), session);
	}

	public static void invalidate(String id) {
		HttpSession session = map.get(id);
		if (session != null) {
			System.out.println("session:"+session);
			session.invalidate();

			System.out.println("销毁会话成功,id=" + id);
			map.remove(id);
		} else {
			System.out.println("销毁失败,没有找到会话,id=" + id);
		}
	}
}
