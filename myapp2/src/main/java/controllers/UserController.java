package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DB;
import domains.User;

@WebServlet(value="*.do")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlString = request.getRequestURI();
		if(urlString.endsWith("view.do")){
			request.getRequestDispatcher("WEB-INF/jsp/view.jsp").forward(request, response);
			return;
		}
		else if(urlString.endsWith("logout.do")) {
			response.getWriter().write("logouted");
			return;
		}
		else if(urlString.endsWith("update.do")){
			request.getRequestDispatcher("WEB-INF/jsp/update.jsp").forward(request, response);
			return;
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlString = request.getRequestURI();
		if(urlString.endsWith("update.do")){
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String age=request.getParameter("age");
			String email=request.getParameter("email");
			User u1=DB.updateUser(id,name,age,email);
			HttpSession session = request.getSession();
			session.setAttribute("user", u1);
			System.out.println("upresult="+u1);
			response.sendRedirect(request.getContextPath()+"/view.do");
		}
	}

}
