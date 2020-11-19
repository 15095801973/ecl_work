package controllers;

import IOC.myautowired;
import IOC.mycomponent;
import IOC.mycontroller;
import mycore.Request;
import servlet.CustomerServlet;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import IOC.myaction;
import testAnna.Interceptor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
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
