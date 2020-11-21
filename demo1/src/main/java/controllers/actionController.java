package controllers;

import IOC.*;
import mycore.Request;
import services.CustomerServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@mycontroller
public class actionController {
	public String name;
	public void setName(String name) {
		this.name=name;
	}
	//value 暂时没有用到
	@myaction(value = "/action/test")
	public void test(Request request) {
		System.out.println("action.test()");
		System.out.println(name);
	}
	//value 暂时没有用到
	@myaction(value = "/action")
	private void action(Request request) {
		System.out.println("action ...\n");
		
		String nameString = request.getParms("name");
		System.out.println("nameString = "+nameString);
		CustomerServlet.doHtml(request, "/hello.html");
	}
}
