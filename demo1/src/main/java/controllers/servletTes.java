package controllers;

import IOC.*;
import mycore.Request;
import services.*;
@mycontroller
public class servletTes {
	public String name;
	public void setName(String name) {
		this.name=name;
	}

	//value 暂时没有用到
	@myaction(value = "/servlet/get")
	private void get(Request request) {
		System.out.println("servlet: get ...\n");
		
		//			CustomerServlet.doJson(request, "404");
//		CustomerServlet.doHtml(request, "/upload3.jsp");
		CustomerServlet.doJsp(request, "/upload2.jsp");
	}

}
