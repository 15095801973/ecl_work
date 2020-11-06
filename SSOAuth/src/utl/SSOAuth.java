package utl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/SSOAuth")
public class SSOAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ConcurrentMap<String, String> accounts;
	private static ConcurrentMap<String, String> SSOIDs;
	private String cookiename = "DesktopSSOID";
	private String domainname = "localhost";

	public void init(ServletConfig config) throws ServletException {
//		domainname = config.getInitParameter("domainname");
//		cookiename = config.getInitParameter("cookiename");
		SSOIDs = new ConcurrentHashMap<String, String>();
		userMsg();
	}
	// 连接数据库查找用户信息
	public void userMsg() {
		    accounts = new ConcurrentHashMap<String, String>();
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8";
			String username = "root";
			String password = "root";
//			try {
//				Class.forName("com.mysql.jdbc.Driver");
//				conn = DriverManager.getConnection(url, username,password);
//				PreparedStatement ps = conn.prepareStatement("select * from user");
//				ResultSet rs = ps.executeQuery();
//				while (rs.next()) {
//					accounts.put(rs.getString("username"), rs.getString("password"));
//					System.out.println(accounts.toString());}
//				
//				rs.close();
//				ps.close();
//				conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			accounts.put("12", "12");

	}
		
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf8");
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf8");
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String result ="failed";
		PrintWriter out = response.getWriter();
		if (action == null) {
			handlerFromLogin(request, response);
		} else if (action.equals("authcookie")) {
			String myCookie = request.getParameter("cookiename");
			if (myCookie != null){
				result = authCookie(myCookie);
			}
			out.print(result);
		    out.close();
		} else if (action.equals("authuser")) {
			result = authNameAndPasswd(request, response);
			out.print(result);
		    out.close();
		} else if (action.equals("logout")) {
			String myCookie = request.getParameter("cookiename");
			logout(myCookie);
			out.close();
		}
		
	}
	/**
	 * 验证用户名、密码，然后跳回原页面
	 */
	private void handlerFromLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String pass = accounts.get(username);
		if ((pass == null) || (!pass.equals(password))) {  //DigestUtils.md5Hex(password)
			request.setAttribute("msg", "登录失败");
			request.getRequestDispatcher("/failed.jsp").forward(request, response);
		} else {
			String gotoURL = request.getParameter("goto") + "index.jsp";
			String newID = createUID();
			System.out.println("ID,"+newID);
			SSOIDs.put(newID, username);

			System.out.println("add2cookie,"+this.cookiename);
			this.add2Cookie(response, this.cookiename, newID, 60 * 1000);
			System.out.println("登录成功, 返回前url:" + gotoURL);
			response.sendRedirect(gotoURL);

		}
	}
	/**
	 * 添加至cookie
	 */
	private void add2Cookie(HttpServletResponse response, String cookieName,
			String cookieValue, int maxAge) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setDomain(this.domainname);//设置域名localhost
		cookie.setPath("/");
		cookie.setMaxAge(maxAge); // cookie一年内有效60*60*24*365
		response.addCookie(cookie);
	}
	/**
	 * 身份验证
	 * @param value
	 * @return
	 */
	public static String authCookie(String value) {
		String result = SSOIDs.get(value);
		if (result == null) {
			result = "failed";
			System.out.println("身份验证失败");
		} else {
			System.out.println("身份验证成功");
		}
		return result;
	}
	/**
	 * 验证用户名和密码    正确返回当前登录用户uid
	 * @param request
	 * @param response
	 * @return
	 */
	protected String authNameAndPasswd(HttpServletRequest request,HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String pass = (String) accounts.get(username);
		if ((pass == null) || (!pass.equals(password)))  //DigestUtils.md5Hex(password)
			return "failed";
		String newID = createUID();
		SSOIDs.put(newID, username);
		return newID;
	}
    /**
     * 创建uid
     * @return  String
     */
	private static String createUID() {
		Date now = new Date();
		long time = now.getTime();
		return "sso" + time;
	}
	/**
	 * 注销
	 * @param UID
	 */
	private void logout(String UID) {
		System.out.println("---退出登录---" + UID);
		SSOIDs.remove(UID);
	}
	
	
}
