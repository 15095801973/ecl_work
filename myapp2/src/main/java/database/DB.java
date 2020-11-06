package database;

//import domains.Mapping;
//import domains.ServiceTicket;
//import domains.SessionStorage;
import domains.User;

public class DB {
	public static User createNewUser(String id) {
		UserDao ud;
		User u1 = new User();
		try {
			ud = new UserDao();
			u1.setId(id);
			ud.add(u1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u1;
	}
	public static User getUser(String id) {
		UserDao ud;
		User u1 = new User();
		try {
			ud = new UserDao();
			u1 = ud.get(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return u1;
	}
	public static User updateUser(String id,String name,String age,String email) {
		UserDao ud;
		
		try {
			ud=new UserDao();
			User u1 = new User();
			u1.setId(id);
//			u1.setPwd("pwd");
			u1.setName(name);
			u1.setAge(Integer.parseInt(age));
			u1.setEmail(email);
			ud.update(u1,id);
			return u1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
