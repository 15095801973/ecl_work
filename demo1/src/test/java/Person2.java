package IOC;

import IOC.myautowired;
import IOC.mycomponent;
import mycore.Request;
import services.CustomerServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import IOC.myaction;
import testAnna.Interceptor;

@mycontroller
public class Person2 {
	public String name;
	public void setName(String name) {
		this.name=name;
	}
	//value 暂时没有用到
	@myaction(value = "/test")
	public void test(Request request) {
		System.out.println("Person.test()");
		System.out.println(name);
	}
	//value 暂时没有用到
	@myaction(value = "/hello")
	private void login(Request request) {
		System.out.println("logging");
		CustomerServlet.doHtml(request, "/hello.html");
	}
}
