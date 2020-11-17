package IOC;

import IOC.myautowired;
import IOC.mycomponent;
import mycore.Request;
import servlet.CustomerServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import IOC.myaction;
import testAnna.Interceptor;

@mycontroller
public class actionController {
	public String name;
	public void setName(String name) {
		this.name=name;
	}
	//value 暂时没有用到
	@myaction(value = "get:/A_1")
	public void test() {
		System.out.println("action.test()");
		System.out.println(name);
	}
	//value 暂时没有用到
	@myaction(value = "get:/A_1")
	private void action(Request request) {
		System.out.println("action ...\n");
		
		try {
			String nameString = request.getParms("name");
			System.out.println("nameString = "+nameString);
			CustomerServlet.doHtml(request, "/hello.html");
		} catch (IOException e) {
			System.out.println("action failed");
			try {
				CustomerServlet.doJson(request, "404");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
