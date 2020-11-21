package controllers;

import IOC.*;
import mycore.MulitpartData;
import mycore.Request;
import services.Constant;
import services.CustomerServlet;
import services.savePngtest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;
@mycontroller
public class uploadController {
	public String name;
	public void setName(String name) {
		this.name=name;
	}

	//value 暂时没有用到
	@myaction(value = "/upload/get")
	private void get(Request request) {
		System.out.println("upload: get ...\n");
		
		CustomerServlet.doHtml(request, "/upload.jsp");
	}
	@myaction(value = "/upload/post")
	private void post(Request request) throws IOException {
		CustomerServlet.doJson(request, "post succeed");
		
	}
}
