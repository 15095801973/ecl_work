package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB;
import domains.Mapping;
import domains.ServiceTicket;

@WebServlet(value = "/getUser.do")
public class GetUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String CAS_ST = request.getParameter("CAS_ST");
		String host = request.getParameter("host");
		String app = request.getParameter("app");
		String LOCAL_SERVICE = request.getParameter("LOCAL_SERVICE");
		String sessionId = request.getParameter("sessionId");

		// response.sendRedirect("reDirect");
//		System.out.println("in my getUser");
//		response.getWriter().println("local_id");
		DB.addSessionStorage(LOCAL_SERVICE, CAS_ST, sessionId);
		ServiceTicket st = DB.findServiceTicketbySt(CAS_ST);
		System.out.println("GetUserController.find_st: " + st);
		System.out.println("host:" + host + "app:" + app);
		Mapping mapping = DB.findMappingByHostAndAppAndCasUser(host, app, st.getUser());
		if (mapping == null) {
			response.getWriter().println("");
		} else
			response.getWriter().println(mapping.getLocalUser());
	}

}
